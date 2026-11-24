// Use the host's provisioned Chromium over its debugging pipe. No npm packages.
import {spawn} from 'node:child_process';
import {createHash} from 'node:crypto';
import {mkdtempSync, readdirSync, readFileSync, writeFileSync} from 'node:fs';
import {tmpdir} from 'node:os';
import {dirname, join} from 'node:path';
import {fileURLToPath, pathToFileURL} from 'node:url';

const root = dirname(fileURLToPath(import.meta.url));
const chrome = process.argv[2];
if (!chrome) throw new Error('Pass the path to a provisioned Chromium executable.');
const profile = mkdtempSync(join(tmpdir(), 'snake-math-diagram-cdp-'));
const browser = spawn(chrome, ['--headless', '--disable-gpu', '--no-first-run',
  '--no-default-browser-check', '--disable-background-networking',
  '--remote-debugging-pipe', `--user-data-dir=${profile}`],
  {stdio: ['ignore', 'ignore', 'ignore', 'pipe', 'pipe'], windowsHide: true});
let sequence = 0, buffer = '';
const pending = new Map();
browser.stdio[4].on('data', chunk => {
  buffer += chunk.toString();
  let end;
  while ((end = buffer.indexOf('\0')) >= 0) {
    const message = JSON.parse(buffer.slice(0, end));
    buffer = buffer.slice(end+1);
    if (!message.id || !pending.has(message.id)) continue;
    const {resolve, reject, timer} = pending.get(message.id);
    clearTimeout(timer); pending.delete(message.id);
    message.error ? reject(new Error(JSON.stringify(message.error))) : resolve(message.result);
  }
});
function call(method, params = {}, sessionId) {
  const id = ++sequence;
  return new Promise((resolve, reject) => {
    const timer = setTimeout(() => {pending.delete(id); reject(new Error(`Timeout: ${method}`));}, 20000);
    pending.set(id, {resolve, reject, timer});
    browser.stdio[3].write(JSON.stringify({id, method, params, ...(sessionId ? {sessionId} : {})})+'\0');
  });
}
const receipt = [];
try {
  const {targetId} = await call('Target.createTarget', {url: 'about:blank'});
  const {sessionId} = await call('Target.attachToTarget', {targetId, flatten: true});
  await call('Page.enable', {}, sessionId);
  for (const file of readdirSync(root).filter(x => x.endsWith('.html')).sort()) {
    const height = Number(readFileSync(join(root, file), 'utf8').match(/viewBox="0 0 960 (\d+)"/)[1]);
    await call('Emulation.setDeviceMetricsOverride', {width:960, height, deviceScaleFactor:2, mobile:false}, sessionId);
    await call('Page.navigate', {url:pathToFileURL(join(root,file)).href}, sessionId);
    const measured = await call('Runtime.evaluate', {awaitPromise:true, returnByValue:true, expression:`
      new Promise(resolve => {
        function ready() {
          if (document.readyState !== 'complete' || !document.querySelector('svg')) return setTimeout(ready,20);
          document.fonts.ready.then(() => {
            const svg=document.querySelector('svg');
            const outside=[...svg.querySelectorAll('text')].filter(t=>{
              const b=t.getBBox();return b.x<0||b.y<0||b.x+b.width>960||b.y+b.height>${height};
            }).map(t=>t.textContent);
            resolve({title:document.title, width:svg.getBoundingClientRect().width,
              height:svg.getBoundingClientRect().height, outside,
              textCount:svg.querySelectorAll('text').length,
              footer:[...svg.querySelectorAll('text')].slice(-2).map(t=>t.textContent)});
          });
        } ready();
      })`}, sessionId);
    const result=measured.result.value;
    if (result.outside.length) throw new Error(`${file}: clipped text ${result.outside.join(', ')}`);
    const {data} = await call('Page.captureScreenshot', {format:'png', fromSurface:true,
      captureBeyondViewport:true, clip:{x:0,y:0,width:960,height,scale:1}}, sessionId);
    const bytes=Buffer.from(data,'base64');
    if(bytes.readUInt32BE(16)!==1920 || bytes.readUInt32BE(20)!==height*2) throw new Error('Wrong PNG dimensions');
    writeFileSync(join(root,file.replace('.html','.png')),bytes);
    receipt.push({file, ...result, pngWidth:1920,pngHeight:height*2,
      htmlSha256:createHash('sha256').update(readFileSync(join(root,file))).digest('hex'),
      pngSha256:createHash('sha256').update(bytes).digest('hex')});
    console.log(`${file}: complete SVG and footer captured, no clipped text`);
  }
  writeFileSync(join(root,'render-check.json'),JSON.stringify(receipt,null,2)+'\n');
} finally {
  await call('Browser.close').catch(()=>browser.kill());
}

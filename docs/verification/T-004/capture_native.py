"""Capture native Home on the connected API 34 emulator; restores display settings."""
import os
from pathlib import Path
import subprocess
import time
import re
import xml.etree.ElementTree as ET

ADB = str(Path(os.environ['LOCALAPPDATA']) / 'Android/Sdk/platform-tools/adb.exe')
OUT = Path(__file__).parent
SERIAL = os.environ.get('ANDROID_SERIAL', 'emulator-5554')
PREFIX = os.environ.get('CAPTURE_PREFIX', '')

def adb(*args):
    return subprocess.check_output([ADB, '-s', SERIAL, *args])

def shell(*args):
    return adb('shell', *args).decode().strip()

def capture(name):
    for _ in range(15):
        time.sleep(2)
        shell('uiautomator', 'dump', '/sdcard/window.xml')
        tree = adb('shell', 'cat', '/sdcard/window.xml')
        if b"Process system isn't responding" in tree:
            for node in ET.fromstring(tree).iter('node'):
                if node.get('resource-id') == 'android:id/aerr_wait':
                    x1, y1, x2, y2 = map(int, re.findall(r'\d+', node.get('bounds')))
                    shell('input', 'tap', str((x1+x2)//2), str((y1+y2)//2))
            continue
        if any(label in tree for label in (b'PLAY', b'Settings', b'A little game of growth')):
            break
    else:
        raise RuntimeError('Home did not render; refusing to capture a splash screen')
    (OUT / (PREFIX + name + '.xml')).write_bytes(tree)
    (OUT / (PREFIX + name + '.png')).write_bytes(adb('exec-out', 'screencap', '-p'))

if __name__ == '__main__':
    original_font = shell('settings', 'get', 'system', 'font_scale')
    original_airplane = shell('settings', 'get', 'global', 'airplane_mode_on')
    original_size = shell('wm', 'size')
    original_density = shell('wm', 'density')
    observations = []
    try:
        adb('install', '-r', 'app/build/outputs/apk/debug/app-debug.apk')
        for name, width, height, font in [('phone',390,844,1), ('small-phone',320,568,1),
                                          ('large-font',390,844,1.5), ('tablet',1024,768,1)]:
            shell('am', 'force-stop', 'com.acp.snakemath')
            shell('wm', 'density', '320')
            shell('wm', 'size', f'{width*2}x{height*2}')
            shell('settings', 'put', 'system', 'font_scale', str(font))
            shell('am', 'start', '-W', '-n', 'com.acp.snakemath/.app.MainActivity')
            capture(name)
            config = shell('dumpsys', 'activity', 'activities')
            observations.append(f'{name}: {width}x{height}dp, density 320, font {font}\n' + '\n'.join(
                line for line in config.splitlines() if 'mLastReportedConfiguration=' in line or 'mResumedActivity' in line))
            if name in ('small-phone', 'large-font'):
                shell('input', 'swipe', str(width), str(int(height*1.7)), str(width), '200', '450')
                capture(name + '-scrolled')
        shell('cmd', 'connectivity', 'airplane-mode', 'enable')
        shell('am', 'force-stop', 'com.acp.snakemath')
        shell('am', 'start', '-W', '-n', 'com.acp.snakemath/.app.MainActivity')
        capture('tablet-offline')
    finally:
        shell('cmd', 'connectivity', 'airplane-mode', 'enable' if original_airplane == '1' else 'disable')
        shell('settings', 'put', 'system', 'font_scale', original_font if original_font != 'null' else '1.0')
        shell('wm', 'size', original_size.split('Override size: ')[1] if 'Override size:' in original_size else 'reset')
        shell('wm', 'density', original_density.split('Override density: ')[1] if 'Override density:' in original_density else 'reset')
        (OUT / (PREFIX + 'device-observations.txt')).write_text('\n'.join(observations), encoding='utf-8')

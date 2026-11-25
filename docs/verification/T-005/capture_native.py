"""Capture T-005 native content; restore emulator display/font settings on exit."""
import argparse
import json
from pathlib import Path
import re
import subprocess
import time
import xml.etree.ElementTree as ET

parser = argparse.ArgumentParser()
parser.add_argument('--adb', required=True)
parser.add_argument('--serial', required=True)
args = parser.parse_args()
out = Path(__file__).parent / 'captures'
out.mkdir(exist_ok=True)


def adb(*parts, check=True):
    return subprocess.run([args.adb, '-s', args.serial, *parts], check=check, capture_output=True).stdout


def shell(*parts):
    return adb('shell', *parts).decode().strip()


def hierarchy():
    shell('uiautomator', 'dump', '/sdcard/t005-capture.xml')
    return adb('shell', 'cat', '/sdcard/t005-capture.xml')


def node_for(label, attempts=12):
    for _ in range(attempts):
        data = hierarchy()
        for node in ET.fromstring(data).iter('node'):
            if label in (node.get('text'), node.get('content-desc')):
                return node
        time.sleep(.5)
    raise RuntimeError(f'Content not ready: {label}')


def tap(label):
    for attempt in range(12):
        try:
            node = node_for(label, attempts=1)
            break
        except RuntimeError:
            dimensions = re.findall(r'(\d+)x(\d+)', shell('wm', 'size'))[-1]
            width, height = map(int, dimensions)
            start, end = int(height * .8), int(height * .25)
            if attempt >= 6:
                start, end = end, start
            shell('input', 'swipe', str(width // 2), str(start), str(width // 2), str(end), '300')
    else:
        raise RuntimeError(f'Could not reach {label}')
    left, top, right, bottom = map(int, re.findall(r'\d+', node.get('bounds')))
    shell('input', 'tap', str((left + right) // 2), str((top + bottom) // 2))


def capture(name, label):
    node_for(label)
    (out / f'{name}.xml').write_bytes(hierarchy())
    (out / f'{name}.png').write_bytes(adb('exec-out', 'screencap', '-p'))


original_size = shell('wm', 'size')
original_density = shell('wm', 'density')
original_font = shell('settings', 'get', 'system', 'font_scale')
observations = []
try:
    for width, height, scale in [(390, 844, 1), (320, 568, 1.5), (1024, 768, 1)]:
            name = f'{width}x{height}-font-{scale}'
            shell('am', 'force-stop', 'com.acp.snakemath')
            shell('wm', 'density', '320')
            shell('wm', 'size', f'{width * 2}x{height * 2}')
            shell('settings', 'put', 'system', 'font_scale', str(scale))
            shell('am', 'start', '-W', '-n', 'com.acp.snakemath/.app.MainActivity')
            capture(f'{name}-home', 'PLAY')
            tap('PLAY')
            capture(f'{name}-modes-top', 'CHOOSE YOUR MODE')
            shell('input', 'swipe', str(width), str(int(height * 1.7)), str(width), '180', '400')
            tap('Hard')
            capture(f'{name}-modes-bottom', 'Hard')
            tap('PLAY MDAS')
            capture(f'{name}-mdas-hard', 'Gameplay is not available yet.')
            tap('Back to modes')
            shell('input', 'swipe', str(width), '180', str(width), str(int(height * 1.7)), '400')
            tap('PLAY CLASSIC')
            capture(f'{name}-classic', 'Gameplay is not available yet.')
            observations.append(dict(profile=name, api=shell('getprop', 'ro.build.version.sdk'),
                size=shell('wm', 'size'), density=shell('wm', 'density'),
                font=shell('settings', 'get', 'system', 'font_scale'),
                configuration=shell('dumpsys', 'activity', 'activities')))
            (out / 'device-observations.json').write_text(json.dumps(observations, indent=2))
finally:
    for command, original in [('size', original_size), ('density', original_density)]:
        override = re.search(r'Override \w+:\s*(\S+)', original)
        shell('wm', command, override.group(1) if override else 'reset')
    shell('settings', 'put', 'system', 'font_scale', original_font)

"""Build the architecture figures as offline, editable HTML/SVG sources.

Only the Python standard library is needed. PNG export is a separate step.
The original six Mermaid blocks are retained in source-diagrams.json.
"""

from pathlib import Path
from html import escape
import json
import re
import sys

ROOT = Path(__file__).resolve().parent
DOC = ROOT.parents[1] / "architecture.md"
PAPER, INK, MUTED, ACCENT = "#FCFAF1", "#023E21", "#47634F", "#155E2B"
SURFACE, TINT, RULE = "#FFFFFF", "#F0F6CF", "#9BAA9E"


class Figure:
    def __init__(self, slug, title, desc, height=900):
        self.slug, self.title, self.desc, self.height = slug, title, desc, height
        self.back, self.edges, self.labels, self.nodes = [], [], [], []
        self.text(48, 48, title, size=28, weight=600, layer=self.back)
        self.text(48, 76, "SNAKE & MATH / PROPOSED ARCHITECTURE", size=12,
                  color=MUTED, layer=self.back)

    def text(self, x, y, lines, size=16, weight=400, color=INK,
             anchor="start", mono=False, layer=None):
        if isinstance(lines, str):
            lines = lines.split("\n")
        family = "Consolas, monospace" if mono else "Segoe UI, Arial, sans-serif"
        target = self.labels if layer is None else layer
        for i, line in enumerate(lines):
            target.append(f'<text x="{x}" y="{y+i*(size+6)}" font-family="{family}" '
                          f'font-size="{size}" font-weight="{weight}" fill="{color}" '
                          f'text-anchor="{anchor}">{escape(line)}</text>')

    def node(self, key, x, y, w, h, name, detail="", focal=False, fields=None, tag=""):
        fill = TINT if focal else SURFACE
        self.nodes.append(f'<g data-node="{key}"><rect x="{x}" y="{y}" width="{w}" '
                          f'height="{h}" rx="8" fill="{fill}" stroke="{ACCENT if focal else RULE}" '
                          'stroke-width="1.4"/>')
        if fields is not None:
            self.text(x+20, y+30, name, weight=600, layer=self.nodes)
            self.nodes.append(f'<line x1="{x}" y1="{y+48}" x2="{x+w}" y2="{y+48}" stroke="{RULE}"/>')
            self.text(x+20, y+76, fields, size=14, mono=True, layer=self.nodes)
        else:
            if tag:
                self.text(x+20, y+22, tag, size=10, mono=True, color=MUTED, layer=self.nodes)
            self.text(x+w/2, y+(48 if tag else 36), name, weight=600,
                      anchor="middle", layer=self.nodes)
            if detail:
                self.text(x+w/2, y+(72 if tag else 60), detail, size=12,
                          color=MUTED, anchor="middle", layer=self.nodes)
        self.nodes.append('</g>')

    def path(self, points, dashed=False, accent=False):
        # Round each orthogonal elbow with an 8px quadratic corner.
        d = f'M {points[0][0]} {points[0][1]}'
        for i in range(1, len(points)-1):
            a, b, c = points[i-1], points[i], points[i+1]
            before = (b[0] + max(-1, min(1, a[0]-b[0]))*8,
                      b[1] + max(-1, min(1, a[1]-b[1]))*8)
            after = (b[0] + max(-1, min(1, c[0]-b[0]))*8,
                     b[1] + max(-1, min(1, c[1]-b[1]))*8)
            d += f' L {before[0]} {before[1]} Q {b[0]} {b[1]} {after[0]} {after[1]}'
        d += f' L {points[-1][0]} {points[-1][1]}'
        dash = ' stroke-dasharray="5 4"' if dashed else ''
        marker = "arrow-accent" if accent else "arrow"
        self.edges.append(f'<path d="{d}" fill="none" stroke="{ACCENT if accent else MUTED}" '
                          f'stroke-width="1.5"{dash} marker-end="url(#{marker})"/>')

    def label(self, x, y, text, anchor="middle"):
        lines = text.split("\n")
        width = max(len(t) for t in lines)*7.5+16
        left = x-width/2 if anchor == "middle" else x-8
        first_y = y-(len(lines)-1)*18
        self.labels.append(f'<rect x="{left}" y="{first_y-14}" width="{width}" '
                           f'height="{len(lines)*18+4}" rx="2" fill="{PAPER}"/>')
        self.text(x, first_y, lines, size=12, color=MUTED, anchor=anchor)

    def footer(self, lines):
        y = self.height-80
        self.nodes.append(f'<line x1="48" y1="{y-20}" x2="912" y2="{y-20}" stroke="{RULE}"/>')
        self.text(48, y+4, lines, size=12, color=MUTED, layer=self.nodes)

    def save(self):
        svg = (f'<svg viewBox="0 0 960 {self.height}" xmlns="http://www.w3.org/2000/svg" '
               f'role="img" aria-labelledby="{self.slug}-title {self.slug}-desc">'
               f'<title id="{self.slug}-title">{escape(self.title)}</title>'
               f'<desc id="{self.slug}-desc">{escape(self.desc)}</desc><defs>')
        for ident, color in [("arrow", MUTED), ("arrow-accent", ACCENT), ("arrow-link", MUTED)]:
            svg += (f'<marker id="{ident}" markerWidth="8" markerHeight="6" refX="7" refY="3" '
                    f'orient="auto"><polygon points="0 0, 8 3, 0 6" fill="{color}"/></marker>')
        svg += f'</defs><rect width="960" height="{self.height}" fill="{PAPER}"/>'
        svg += '\n'.join(self.back+self.edges+self.labels+self.nodes) + '</svg>'
        html = ('<!DOCTYPE html>\n<html lang="en"><head><meta charset="UTF-8">'
                '<meta name="viewport" content="width=device-width, initial-scale=1">'
                f'<title>{escape(self.title)}</title><style>'
                f'html,body{{margin:0;padding:0;background:{PAPER};color:{INK};}}'
                '.frame{width:100%;min-width:0;max-width:100%;}'
                '.diagram-container{width:100%;overflow-x:auto;}'
                f'svg{{display:block;width:960px;min-width:960px;height:{self.height}px;}}'
                '@media print{.diagram-container{overflow-x:visible;}svg{width:100%;min-width:0;height:auto;}}'
                '</style></head><body><main class="frame"><div class="diagram-container">'
                + svg + '</div></main></body></html>\n')
        (ROOT / f'{self.slug}.html').write_text(html, encoding='utf-8')


def system():
    f = Figure('01-system', 'System and dependency map',
               'Compose forwards commands to a ViewModel, which delegates rules to pure Kotlin engines and persistence to a repository.', 1000)
    f.path([(200,236),(200,360)]); f.label(264,288,'COMMANDS')
    f.path([(320,360),(320,236)],dashed=True); f.label(370,310,'UI STATE')
    f.path([(400,188),(560,188)]); f.label(480,164,'SNAPSHOTS\n+ FRACTION')
    f.path([(400,392),(560,392)]); f.label(480,370,'STATE + CMD')
    f.path([(560,440),(400,440)],dashed=True); f.label(480,488,'NEW STATE\n+ EVENTS')
    f.path([(720,456),(720,600)]); f.label(794,520,'GENERATE\n+ VALIDATE')
    f.path([(240,456),(240,600)]); f.label(320,520,'OBSERVE BESTS\nRECORD SCORE')
    f.path([(240,696),(240,776)]); f.label(330,740,'FLOW READS\nATOMIC WRITES')
    f.node('UI',80,140,320,96,'Compose screens','Swipe input / immutable UI state')
    f.node('DRAW',560,140,320,96,'Canvas renderer','Prepared artwork and snapshots')
    f.node('VM',80,360,320,96,'GameViewModel','Session owner / StateFlow',True)
    f.node('ENGINE',560,360,320,96,'Game engine','Pure Kotlin rules and transitions')
    f.node('MATH',560,600,320,96,'Math engine','Pure Kotlin questions and evaluation')
    f.node('REPO',80,600,320,96,'Preferences repository','Plain Kotlin contract')
    f.node('STORE',80,776,320,96,'Preferences DataStore','Local best scores and settings',tag='STORAGE')
    f.footer(['Solid: commands/delegation. Dashed: returned state. All components run in-process.',
              'The game and math engines have no Android, Compose or persistence dependencies.']); f.save()


def navigation():
    f = Figure('02-navigation','Navigation and screen ownership',
               'Home opens mode selection or Settings; one game destination owns pause and results overlays.', 960)
    f.path([(208,184),(208,260)]); f.label(276,224,'READY')
    f.path([(336,300),(368,300)]); f.label(352,244,'PLAY')
    f.path([(432,260),(432,220),(304,220),(304,260)]); f.label(368,202,'BACK')
    f.path([(624,300),(656,300)]); f.label(640,244,'SELECT MODE')
    f.path([(160,356),(160,536)]); f.label(108,432,'SETTINGS')
    f.path([(256,536),(256,356)]); f.label(208,464,'BACK /\nHOME')
    f.path([(712,356),(712,432),(432,432),(432,536)]); f.label(572,410,'PAUSE / BACK')
    f.path([(624,600),(640,600),(640,332),(656,332)])
    f.edges[-1] = f.edges[-1].replace('L 640 340', 'L 640 440 a 8 8 0 0 1 0 -16 L 640 340')
    f.label(572,484,'RESUME /\nRESTART')
    f.path([(400,536),(400,484),(304,484),(304,356)]); f.label(352,516,'HOME')
    f.path([(744,356),(744,536)]); f.label(768,392,'ROUND\nENDS',anchor='start')
    f.path([(848,536),(848,356)]); f.label(872,484,'TRY AGAIN',anchor='start')
    f.path([(784,632),(784,756),(48,756),(48,316),(80,316)]); f.label(480,736,'HOME / BACK')
    f.node('START',80,112,256,72,'Startup readiness')
    f.node('HOME',80,260,256,96,'Home','PLAY / Settings',True)
    f.node('MODES',368,260,256,96,'Mode selection','Classic or MDAS + difficulty')
    f.node('GAME',656,260,256,96,'Game destination','Fixed mode and round profile')
    f.node('SETTINGS',80,536,256,96,'Settings','Back or Home')
    f.node('PAUSE',368,536,256,96,'Pause overlay','Same game owner / frozen board')
    f.node('RESULT',656,536,256,96,'Results overlay','Same game owner / final score')
    f.footer(['Pause / Back = Pause control or system Back. Round ends = game over or completion.',
              'Try Again retains the mode. Home disposes the session. Pause/results are not new rounds.']); f.save()


def domain():
    f = Figure('03-domain-model','Domain state and transition contract',
               'StepResult returns a GameState with fixed configuration and Classic or MDAS-specific state; all original fields are retained.', 1080)
    f.path([(672,216),(672,284)]); f.label(800,256,'NEXT SNAPSHOT')
    f.path([(480,460),(304,460)]); f.label(392,426,'FIXED ROUND\nCONFIGURATION')
    f.path([(544,636),(544,716),(240,716),(240,780)]); f.label(384,692,'WHEN CLASSIC')
    f.path([(736,636),(736,780)]); f.label(816,700,'WHEN MDAS')
    f.node('StepResult',480,104,384,112,'StepResult',fields=['nextState','events'])
    f.node('GameConfig',48,284,256,328,'GameConfig',fields=['mode','mdasDifficulty','columns','rows',
             'tickInterval','initialLength','scorePerApple','initialLives'])
    f.node('GameState',480,284,384,352,'GameState',focal=True,fields=['sessionId','tickIndex','status',
             'snakeCells','direction','queuedTurns','plannedMove','score','modeState'])
    f.node('ClassicState',96,780,288,112,'ClassicState',fields=['applePosition'])
    f.node('MdasState',592,780,288,160,'MdasState',fields=['lives','question','answerApples'])
    f.footer(['Conceptual fields, not finalized Kotlin signatures. No inheritance or cardinality is implied.',
              'A sealed mode-specific state separates Classic apples from MDAS lives, questions and answers.']); f.save()


def controls():
    f = Figure('04-session-controls','Session lifecycle: start, pause and resume',
               'A first swipe starts a ready round; pausing retains the prior status and returning to the foreground never resumes automatically.', 940)
    f.edges.append(f'<circle cx="200" cy="180" r="6" fill="{INK}"/>')
    f.path([(200,188),(200,284)]); f.label(264,236,'CREATE ROUND')
    f.path([(328,320),(632,320)]); f.label(480,292,'FIRST SWIPE')
    f.path([(800,284),(800,172),(712,172),(712,284)]); f.label(756,140,'BORDER WRAP /\nMDAS BODY PASS')
    f.path([(200,396),(200,488),(400,488),(400,596)]); f.label(128,464,'PAUSE /\nBACKGROUND')
    f.path([(768,396),(768,512),(560,512),(560,596)]); f.label(672,488,'PAUSE / BACK /\nBACKGROUND')
    f.path([(356,652),(48,652),(48,348),(80,348)]); f.label(200,628,'RESUME: WAS READY')
    f.path([(604,652),(912,652),(912,348),(880,348)]); f.label(756,628,'RESUME: WAS RUNNING')
    f.path([(480,596),(480,440),(288,440),(288,396)]); f.label(412,416,'RESTART')
    f.node('READY',80,284,248,112,'READY','No moving loop')
    f.node('RUNNING',632,284,248,112,'RUNNING','One owned tick job',True)
    f.node('PAUSED',356,596,248,112,'PAUSED','Frozen interval and queued turns')
    f.footer(['Resume is explicit and restores READY or RUNNING according to the state before pausing.',
              'Borders and MDAS self-overlap keep RUNNING; neither deducts lives nor resets the round.']); f.save()


def outcomes():
    f = Figure('05-session-outcomes','Session lifecycle: results, retry and disposal',
               'Classic self-hit or five incorrect MDAS answers cause game over; Classic board completion succeeds; retry returns to ready and Home disposes the session.', 1000)
    f.path([(736,236),(736,320),(480,320),(480,428)]); f.label(592,292,'CLASSIC SELF-HIT /\n5TH WRONG ANSWER')
    f.path([(832,236),(832,428)]); f.label(776,372,'BOARD FULL')
    f.path([(352,476),(312,476),(312,204),(288,204)]); f.label(240,344,'TRY AGAIN')
    f.path([(880,476),(920,476),(920,112),(256,112),(256,140)]); f.label(592,92,'TRY AGAIN')
    f.path([(64,188),(40,188),(40,820),(352,820)]); f.label(192,796,'HOME / DISPOSAL')
    f.path([(192,524),(192,744),(416,744),(416,780)]); f.label(208,692,'HOME /\nDISPOSAL',anchor='start')
    f.path([(480,524),(480,780)]); f.label(496,652,'HOME',anchor='start')
    f.path([(768,524),(768,728),(544,728),(544,780)]); f.label(688,704,'HOME')
    f.node('READY',64,140,224,96,'READY','Retry waits for a swipe')
    f.node('RUNNING',656,140,224,96,'RUNNING','Active round')
    f.node('PAUSED',64,428,224,96,'PAUSED','Can return Home')
    f.node('GAME_OVER',352,428,256,96,'GAME_OVER','Classic self-hit / MDAS zero lives',True)
    f.node('COMPLETED',656,428,224,96,'COMPLETED','Classic board filled')
    f.node('END',352,780,256,80,'Session disposed','End marker, not a GameStatus')
    f.footer(['All four original disposal paths remain: READY, PAUSED, GAME_OVER and COMPLETED.',
              'Retry keeps the selected mode, MDAS difficulty and board profile; a new round starts READY.']); f.save()


def sequence(slug, title, actors, messages, height, fragments=(), note=None):
    f = Figure(slug,title,'Time runs top to bottom; message order, optional branches and returned values follow the architecture source.',height)
    positions = {key: 4*round((112+i*(736/(len(actors)-1)))/4) for i,(key,name) in enumerate(actors)}
    for x in positions.values():
        f.back.append(f'<line x1="{x}" y1="212" x2="{x}" y2="{height-(180 if note else 132)}" stroke="{RULE}" stroke-dasharray="4 4"/>')
    for x,y,w,h,guard in fragments:
        f.back.append(f'<rect x="{x}" y="{y}" width="{w}" height="{h}" rx="4" fill="none" stroke="{RULE}"/>')
        f.text(x+12,y+24,'OPT  '+guard,size=12,mono=True,layer=f.back)
    for a,b,y,label,dashed in messages:
        f.path([(positions[a],y),(positions[b],y)],dashed=dashed)
        # Sequence labels have more room between long messages than node connectors.
        f.label((positions[a]+positions[b])/2,y-22,label)
    for key,name in actors:
        x=positions[key]
        f.node(key,x-68,132,136,80,name)
    if note:
        f.text(80,height-136,note,size=12,color=MUTED,layer=f.nodes)
    f.footer(['Solid: request/command. Dashed: returned state or response. Time flows downward.',
              'One authoritative state; no UI or renderer decides gameplay outcomes.']); f.save()


def sequences():
    sequence('06-input-sequence','Commands and ticks: queueing a swipe',
             [('Player','Player'),('UI','Game UI'),('VM','GameViewModel'),('Engine','Game engine')],
             [('Player','UI',300,'Swipe',False),('UI','VM',396,'Turn(direction)',False),
              ('VM','Engine',492,'Reduce current state\nand command',False),
              ('Engine','VM',604,'Queue updated; active move\nstays fixed',True)],820,
             note='The single owned tick job runs only while foreground-eligible and RUNNING.')
    sequence('07-tick-sequence','Commands and ticks: commit and publish',
             [('UI','Game UI'),('VM','GameViewModel'),('Engine','Game engine'),('Math','Math engine'),('Repo','Preferences\nrepository')],
             [('VM','Engine',292,'Commit planned move\nat interval end',False),
              ('Engine','Math',436,'Generate + validate\nexpression / options',False),
              ('Math','Engine',524,'Exact integer question\n+ distinct options',True),
              ('Engine','VM',640,'Committed state, next\nplanned move + events',True),
              ('VM','UI',748,'Immutable GameUiState\n+ render snapshots',True),
              ('VM','Repo',900,'Record best with mode\nand score',False),
              ('Repo','VM',996,'Persisted best or\nstorage failure',True)],1192,
             fragments=[(448,352,328,224,'MDAS needs next question'),(256,808,648,244,'Score increased')])


def rendering():
    f=Figure('08-rendering','Rendering and adaptive layout',
             'Committed state and the frame clock produce one render frame; a shared route feeds body, head and apples into Canvas without affecting rules.',1280)
    f.path([(264,236),(264,280),(376,280),(376,352)])
    f.path([(728,236),(728,304),(584,304),(584,352)]); f.label(816,276,'FRACTION ONLY')
    f.path([(376,448),(376,504),(264,504),(264,576)])
    f.path([(584,448),(584,504),(752,504),(752,576)])
    f.path([(192,672),(192,800)])
    f.path([(336,672),(336,736),(544,736),(544,800)])
    f.path([(896,624),(920,624),(920,1088),(704,1088)])
    f.path([(192,896),(192,976),(400,976),(400,1040)])
    f.path([(544,896),(544,1040)])
    f.node('SNAP',64,140,400,96,'Committed state + planned move','Authoritative grid snapshot')
    f.node('CLOCK',544,140,352,96,'Monotonic frame clock','Visual fraction only')
    f.node('FRAME',280,352,400,96,'RenderFrame + route history','Shared visual input',True)
    f.node('ROUTE',64,576,400,96,'Rounded centerline + tangents','Shared geometry')
    f.node('APPLES',616,576,280,96,'Apples + number labels','Readable above the body')
    f.node('BODY',64,800,256,96,'Continuous body + tail','Shading, spots and taper')
    f.node('HEAD',416,800,256,96,'Prepared atlas head','Rear-feathered attachment')
    f.node('CANVAS',304,1040,400,96,'Compose Canvas','Draws snapshots; never changes rules')
    f.footer(['All head, body and tail geometry uses one route. The clock changes visual interpolation only.',
              'Wrap seams use clipped translated copies; MDAS body crossings retain readable answer labels.']); f.save()


if __name__ == '__main__':
    originals=ROOT/'source-diagrams.json'
    if not originals.exists():
        blocks=re.findall(r'```mermaid\s*\n(.*?)```',DOC.read_text(encoding='utf-8'),re.S)
        if len(blocks)!=6:
            raise RuntimeError(f'Expected six source diagrams, found {len(blocks)}')
        originals.write_text(json.dumps({'source':'docs/architecture.md','diagrams':blocks},indent=2)+'\n',encoding='utf-8')
    for draw in [system,navigation,domain,controls,outcomes,sequences,rendering]:
        draw()
    print('Built eight editable HTML figure sources.')
    if '--embed' in sys.argv:
        current = DOC.read_text(encoding='utf-8')
        blocks = re.findall(r'```mermaid\s*\n(.*?)```', current, re.S)
        if blocks != json.loads(originals.read_text(encoding='utf-8'))['diagrams']:
            raise RuntimeError('Document diagrams differ from the saved originals; review before embedding.')
        images = [
            '![System map: Compose sends commands to GameViewModel, which delegates to pure Kotlin engines and a preferences repository.](diagrams/architecture-figures/01-system.png)',
            '![Navigation: Home opens Modes or Settings; Game owns pause and results overlays with retry and Home paths.](diagrams/architecture-figures/02-navigation.png)',
            '![Conceptual model: StepResult returns GameState with fixed GameConfig and ClassicState or MdasState; all original fields are shown.](diagrams/architecture-figures/03-domain-model.png)',
            '**Start, pause and explicit resume**\n\n![Lifecycle controls: READY starts on the first swipe, RUNNING pauses on background or Back, and explicit resume restores the prior status.](diagrams/architecture-figures/04-session-controls.png)\n\n**Results, retry and disposal**\n\n![Lifecycle outcomes: Classic self-hit or fifth incorrect MDAS answer causes GAME_OVER; filling Classic completes it; retry returns to READY and Home disposes the session.](diagrams/architecture-figures/05-session-outcomes.png)',
            '**Queue the input**\n\n![Input sequence: Player swipe reaches Game UI, GameViewModel and the engine; the queued turn does not change the active move.](diagrams/architecture-figures/06-input-sequence.png)\n\n**Commit the tick and publish state**\n\n![Tick sequence: commit the planned move, optionally generate an MDAS question, publish immutable state, then optionally record the best score.](diagrams/architecture-figures/07-tick-sequence.png)',
            '![Rendering flow: committed state and a visual clock feed RenderFrame, shared route geometry, continuous body and tail, atlas head and answer apples into Compose Canvas.](diagrams/architecture-figures/08-rendering.png)',
        ]
        replacements = iter(images)
        updated = re.sub(r'```mermaid\s*\n.*?```',lambda _: next(replacements),current,flags=re.S)
        old = '**Explore:** [Interactive component diagram](diagrams/architecture.html) · [Editable diagram specification](diagrams/architecture.json). The diagrams below describe the same proposed system at different levels. Boxes represent in-process components; there is no backend.'
        new = '**Diagrams:** All figures below are embedded PNG images, viewable directly in Markdown preview. [Editable sources and verification](diagrams/architecture-figures/README.md) are kept alongside them. The [interactive component diagram](diagrams/architecture.html) and its [specification](diagrams/architecture.json) remain optional references. Figures describe the proposed system; there is no backend, and their review status is unchanged.'
        if old not in updated:
            raise RuntimeError('Expected diagram introduction not found.')
        DOC.write_text(updated.replace(old,new),encoding='utf-8')
        print('Replaced all six Mermaid blocks with eight inline PNG figures.')

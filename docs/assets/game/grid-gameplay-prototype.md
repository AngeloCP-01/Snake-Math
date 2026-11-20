# Grid gameplay prototype — approved baseline

Approved for the Android prototype on 2026-11-20. The live page uses the continuous body/tail and blended head attachment described in [smooth-joins-v3.md](path-snake/smooth-joins-v3.md). See [the Android handoff](../../android-prototype-handoff.md) before beginning native work. Coding is deferred until the user resumes in Android Studio.

Open `snake-grid-gameplay-prototype.html` in a browser. The file embeds the approved artwork, works without a server or packages. No Android application code or project dependencies were changed.

This browser study retains its original **12 × 16 phone board**. The later [screen review](../../design-system/screens-prototype.html) shows the updated **20 × 16 landscape tablet** layout. The Android target is phone portrait only and tablet landscape only; this earlier browser engine does not demonstrate native orientation enforcement.

## What is playable

- Classic: one apple, growth, score, self/wall collision and restart.
- MDAS: persistent arithmetic question, three distinct numbered apples with one correct answer, five lives. Correct answers score and grow; wrong answers cost one life and advance the question. Collisions cost one life, reset the snake and question, then wait for a swipe. Zero lives ends the game.
- Swipe/drag only for steering; no directional controls or keyboard steering. Up to two valid pending turns are queued and immediate reversals are rejected against the last queued direction. Turns apply at cell boundaries.
- Pause/resume, restart, mode selection, grid toggle and PNG save. Backgrounding auto-pauses; resume requires an explicit button action. One animation loop is created at load, not per restart.
- Session-only best scores. Persistent storage, settings, sound and native Android lifecycle integration are outside this throwaway study.

Prototype choices: 12 × 16 board, 50px source cells, 240ms movement tick, +10 for either a Classic apple or correct MDAS answer. Carry these forward as phone-study tuning values; use the documented 20 × 16 profile for landscape tablets. Wrong answers do not grow. No question timer. If fewer reachable positions remain than required for fresh apples, the snake pose resets without an extra life penalty before spawning. A completely filled Classic board finishes the game.

## Engine and rendering separation

The engine uses integer cell coordinates, cardinal direction, a queue, apples, lives and score. Collision includes the current tail only on growing ticks, allowing entry into a vacating tail cell. Question generation uses an explicit precedence evaluator with equal-precedence operations evaluated left to right; it does not evaluate JavaScript strings.

The renderer uses visited cell centers plus the interpolated next movement, makes an open rounded polyline with bounded quadratic bends, and samples it by arc distance. This replaces the free closed demo course. One swept shaded shape supplies both body and tail, with sparse clipped spots. Only the head region of the generated atlas is drawn; its rear is feathered over the body. Artwork width is a visual choice rather than a hard cell-width requirement. Logical collision remains grid-based; decorative overlap at bends does not change occupied cells.

Head position interpolates along cardinal cell-center segments. Head orientation eases through direction changes; body curves round the visited turns. This is a hybrid presentation of strict grid movement, not free steering and not an unconstrained spline. Fractional render positions are visual only.

## Kotlin feasibility and limits

The rendering technique maps to Kotlin/Compose: Canvas paths supply body/tail geometry, clipping and shading; image drawing and transforms place the prepared head. [Android graphics overview](https://developer.android.com/develop/ui/compose/graphics/draw/overview), [DrawScope API](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/drawscope/DrawScope).

Suggested native mapping: grid rules/question evaluator in plain Kotlin; engine snapshot and UI actions through a ViewModel/StateFlow; Compose Canvas reads the snapshot plus render interpolation; head image and feather mask prepared once. Coroutine timing and frame-driven interpolation need lifecycle handling. Neither this architecture nor the actual Android frame rate has been built or measured here. Keep native integration separate from approval of the visual/gameplay study.

## Verification

JavaScript syntax passed. An offscreen native Canvas harness executed the actual page code and checked 18 behavioral assertions: initialization, reversal prevention, Classic growth/scoring, wall game over, legal vacating-tail entry, growing-tail collision, MDAS option placement, correct/wrong answers, collision reset, zero-life game over, precedence, pause/background pause, pointer swipe input and queued turns. It additionally validated 1000 generated question instances and six interpolated render phases. These temporary checks are not an Android/JUnit suite.

The current inspected actual-code render is [grid-mdas-joins-v3.png](path-snake/grid-mdas-joins-v3.png), showing `9 × 6 - 3 = ?`, answer 51. Earlier v1/v2 stills remain historical. The user has approved the current prototype; the offscreen harness does not establish Android behavior or performance.

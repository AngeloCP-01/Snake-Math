# Android prototype handoff

Reconciled: 2026-11-24. The 2026-11-20 browser approval remains the visual/gameplay reference, subject to subsequent native rules below. Android foundation, theme, Home and navigation shell now exist through T-004. Use [the tracker](task-tracker.md) and [T-004 worklog](worklog/T-004-2026-11-22.md) for actual approval, merge and verification evidence; broad device/Studio acceptance remains partial. Native gameplay and performance are not established by browser approval. Dev agreed [P-005 for M1.7](requirements/P-005-play-mode-gameplay-flow.md); [T-005](requirements/T-005-play-mode-gameplay-flow.md) is separately approved on 2026-11-24 for `sol-medium`, with implementation reserved for a new Session B.

## Reference order

| Reference | Use |
| --- | --- |
| [PRD](snake-math-prd.md) | Product rules, Kotlin/Compose stack and architecture |
| [Grid gameplay prototype](assets/game/snake-grid-gameplay-prototype.html) | Approved moving snake; historical Classic/MDAS behavior superseded by native wrap/body/difficulty rules |
| [Smooth-joins v3](assets/game/path-snake/smooth-joins-v3.md) | Final body, tail and head assembly specification |
| [Final MDAS preview](assets/game/path-snake/grid-mdas-joins-v3.png) | Actual-code still for visual comparison |
| [Straight preview](assets/game/path-snake/assembled-joins-straight-v3.png), [curved preview](assets/game/path-snake/assembled-joins-curve-v3.png) | Check the neck and tail transitions |
| [Home mockup](assets/mockups/home-v3.png), [MDAS mockup](assets/mockups/mdas-gameplay-v3.png), [design system](design-system/README.md) | Screen styling, mascot, buttons, palette and typography |
| [Seven-screen design review](design-system/screens-prototype.html), [screen notes](design-system/screen-designs.md) | Proposed phone/tablet screen layouts awaiting review; phone Home remains approved |

The static gameplay mockup does not override the approved moving snake. Use [the approved asset allowlist](assets/README.md) for source artwork. Superseded tile-sprite, segmented body and smooth-body v2 studies have been removed from active folders and preserved in [the deprecated archive](archive/README.md). The browser prototype's grid toggle, PNG export and study controls are development tools.

**Confirmed native Home corrections:** Keep all content visible without scrolling, responsive mascot allocation, 16dp gutters and at least 48dp controls. Preserve both labeled bests, the tighter `0.8.em` multiline title and current gradients/leaves/button/panel details. [DD-008](decisions/DD-008-home-fits-without-scrolling.md) and the [T-004 correction evidence](verification/T-004/review-fixes.md) supersede older mockup spacing and scrolling fallbacks. Scores remain unavailable until persistence is connected. Phone/tablet orientation requests exist; full enforcement and rotation coverage are still pending, not proved by layout fit.

**Agreed entry flow / remaining gaps:** P-005's non-playable M1.7 boundary, inline difficulty/default/retention and process-restoration policy were agreed on 2026-11-24 in [DD-009](decisions/DD-009-m17-navigation-foundation.md), [DD-010](decisions/DD-010-mdas-entry-difficulty.md) and [DD-011](decisions/DD-011-shell-process-restoration.md). T-005 is separately approved; implementation and native verification remain future work. Numeric arithmetic bounds remain open before generator implementation. Other gallery screens/tablet Home composition and loading motion retain their review status; this handoff does not approve them.

## Loading state proposal

A [mascot loading animation](design-system/loading-prototype.html), with [motion notes](design-system/loading-animation.md), is now available for review: a snake in the home mascot's design slithers after a rolling apple marked 0 from left to right, with a deforming S-wave body and matching mascot-style face. It follows the phone-portrait / tablet-landscape policy. It is separate from the gameplay renderer and awaits visual approval. When implemented later, loading ends on actual readiness rather than waiting for a complete animation loop.

## Gameplay baseline to port

**Native movement update, 2026-11-20:** the user confirmed wrap-around borders in both modes. Classic ends only on self-collision; MDAS passes through its own body with no pause/reset/penalty, and loses lives only on incorrect answers. These rules supersede the historical collision rows below. The browser HTML remains unchanged; use the PRD and architecture for native rules and wrap rendering.

**Native arithmetic update, 2026-11-20:** [PRD Section 2.2](snake-math-prd.md#22-mathematical-question-generation) now defines selectable Easy (ages 6–7), Medium (8–9) and Hard (10–12) skill profiles. This supersedes the browser's single question pool for native generation. Numeric bounds and selection UI still need definition; the baseline below continues to describe the reviewed browser behavior.

Rules remain a four-direction grid game. Swipes choose turns at cell boundaries; rendering interpolates between ticks. Rounded visual bends do not permit free steering or change occupied cells.

| Item | Reviewed browser baseline |
| --- | --- |
| Board | Phone: 12 columns × 16 rows. Tablet: 20 columns × 16 rows. Source-canvas cells are 50px, not Android dp. |
| Orientation | Phones portrait only; tablets landscape only, across all seven screens. Native enforcement remains pending. |
| Movement | 240ms per logical tick; begin from a ready state with a swipe |
| Initial snake | Six cells, moving right; see source for starting coordinates |
| Input | Swipe only; at most two queued valid turns; reject reversals against the last queued direction |
| Classic | One apple; +10 and one-cell growth; wall/self collision ends game |
| MDAS | Five lives, persistent expression, three distinct answer values, exactly one correct |
| Correct answer | +10, one-cell growth, next question and three new apples |
| Wrong answer | Lose one life, no growth, next question and three new apples |
| MDAS collision | Lose one life; if lives remain, reset snake and question and wait for a swipe |
| Zero lives | Game over |
| Math | Integer operands and exact division; ×/÷ before +/−, equal precedence left to right; no question timer |
| Placement | Distinct unoccupied positions reachable from the head at spawn time |
| Pause | Explicit resume; backgrounding pauses; one active loop; no catch-up ticks on return |
| Best scores | Browser keeps session-only values; Android target is separate Classic/MDAS bests in Preferences DataStore |

The chosen device profiles are phone 12 × 16 / portrait and tablet 20 × 16 / landscape. Timing, scoring and art scale remain tuning values. Fit square cells to the available board rectangle; resizing must not silently change logical board dimensions during a round. The older playable browser study demonstrates the 12 × 16 phone profile; the screen review now shows both profiles. The former 44dp / 8 × 9 grid suggestion is superseded for this prototype.

Native edge cases and historical differences:

- In Classic, entering the current tail cell is legal only when that tail vacates on a non-growing tick. Check occupancy after wrapping the destination. MDAS allows all body crossings.
- A filled Classic board completes the game.
- The browser resets the pose if too few reachable spaces remain. Keep this fallback only for Classic and use wrap-aware reachability. Native MDAS never resets because of body coverage: prefer clear apple cells, then allow body-overlapping positions if needed, with three distinct apples away from the head and labels visible above the body.
- Current browser distractors can be negative integers. Native answer/distractor ranges must follow the child-focused profiles and bounds agreed under PRD Section 2.2; do not copy negative distractors into every level by default. Automatic difficulty progression remains future work.

## Rendering and assets

Follow [smooth-joins v3](assets/game/path-snake/smooth-joins-v3.md) for the exact assembly contract and source-region coordinates. The renderer draws one continuous shaded body/tail and a raster head. Sparse organic spots and a softly blended neck preserve the approved character. **A one-cell-wide silhouette is not required.**

| File | Android use |
| --- | --- |
| `assets/game/path-snake/snake_path_parts_v1.png` | Gameplay head source; body/tail regions remain unused by the final renderer |
| `assets/game/snake_mascot_v1.png` | Static home mascot; not a moving gameplay snake |
| `assets/game/forest_background_v1.png` | Portrait forest backdrop, crop at edges while preserving clear UI areas |

Keep original PNGs unchanged. Later copy required art into `res/drawable-nodpi/`, preserve alpha and aspect ratio, and cache head cropping/masking. Body shading follows local path cross-sections rather than a screen-aligned gradient. All rendered parts share the same centerline and tangent calculations.

Extend that shared route for native border wrapping using unwrapped coordinates/seam metadata and clipped translated copies, without a diagonal path across the field. MDAS body crossings render normally; apples/labels sit above the body and the head above them.

Future skins can combine unique head artwork, body silhouette, markings and shading in a skin configuration. They need not be palette swaps. Additional skin artwork and a selector are outside the first Android prototype.

Generated reference-based art remains review material; its provenance is recorded in the asset docs. Visual approval is not a rights clearance for publication. Apple, logo, icon and decoration production assets are not finalized.

## Incremental Android implementation sequence

Use native Kotlin, Jetpack Compose and Canvas in the existing single application module. Keep engines pure Kotlin, with future ViewModel/StateFlow ownership and Preferences DataStore added when their approved tasks need them. The foundation/toolchain already exists: inspect build files and accepted decisions rather than creating a second project or copying newer dependency examples. The sequence below describes milestone responsibilities; current progress is tracked separately.

1. Retain the Android project, theme and T-004 Home/navigation shell. Use separately approved T-005 for P-005's agreed M1.7 boundary when extending the host in a new Session B with Dev-selected `sol-medium`. Apply phone portrait/tablet landscape policy to new screens and verify actual platform behavior. Preserve the confirmed Home/title corrections. Themes remain deferred.
2. Port and unit-test grid movement, queued turns, growth and collision rules independently of Compose, using explicit 12 × 16 phone and 20 × 16 tablet configurations. Add the MDAS generator/evaluator and lives transitions with deterministic tests.
3. Reproduce the final snake in a Canvas inspection scene first: straight, rounded turns, short/long bodies and all four directions. Compare neck/tail joins to the linked v3 stills before connecting live movement.
4. Connect engine snapshots to one lifecycle-aware logical loop and frame-driven visual interpolation. Add swipes, ready/pause/game-over states and the MDAS panel/apples. Keep rules outside the renderer.
5. Add local best scores and run the acceptance checks below on an emulator or device. Explain engineering choices as the work progresses; implement incrementally.

## Native acceptance checks

- Both modes follow the updated PRD, including selectable MDAS profiles and wrap-around on all edges. Classic ends only on self-hit; MDAS passes through its body and reaches game over only through incorrect answers. Home PLAY opens the mode picker. Phones stay portrait with 12 × 16 cells, and tablets stay landscape with 20 × 16 cells, across navigation and device rotation.
- Math tests cover the selected profile, precedence, equal-precedence order, exact division and unique options with exactly one correct answer. Engine tests cover growth, queued/reverse turns, wrapping, Classic self-hit/tail occupancy, MDAS repeated-cell pass-through, answer-only life loss, dense-body apple placement and Classic full-board completion.
- Head, body and tail stay aligned during straight movement and turns at several interpolation phases. Neck and tail have no obvious cap, gap or attachment seam; markings remain sparse and clipped inside the silhouette.
- Swipe input is responsive; pause/resume/background/restart create no duplicate loop or unexpected catch-up movement.
- Question, hearts and three answer labels stay readable on smaller screens. Safe-area insets and aspect-ratio changes preserve the board and usable touch targets.
- APK build and actual Android visual/frame-timing checks are recorded separately. Browser/offscreen checks do not count as native validation.

Existing browser-study evidence: the temporary offscreen Canvas harness checked 18 historical gameplay assertions, 1000 generated questions, six gameplay render phases and 648 path geometry combinations. Those are not native tests. T-004 has separate native Home/navigation/component evidence; engine/math/renderer/lifecycle suites still belong to later gameplay tasks. Preserve API 23/API 36, complete rotation, Studio and full visual/screenshot gaps recorded in the T-004 worklog.

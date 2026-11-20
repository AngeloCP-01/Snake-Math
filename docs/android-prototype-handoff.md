# Android prototype handoff

Updated: 2026-11-20. The user approved the current browser gameplay and final snake appearance. **Android coding is deferred until the user moves to Android Studio and asks to begin.** No native project, APK, emulator run or Android performance result is delivered by this documentation handoff.

## Reference order

| Reference | Use |
| --- | --- |
| [PRD](snake-math-prd.md) | Product rules, Kotlin/Compose stack and architecture |
| [Grid gameplay prototype](assets/game/snake-grid-gameplay-prototype.html) | Approved moving snake and Classic/MDAS behavior |
| [Smooth-joins v3](assets/game/path-snake/smooth-joins-v3.md) | Final body, tail and head assembly specification |
| [Final MDAS preview](assets/game/path-snake/grid-mdas-joins-v3.png) | Actual-code still for visual comparison |
| [Straight preview](assets/game/path-snake/assembled-joins-straight-v3.png), [curved preview](assets/game/path-snake/assembled-joins-curve-v3.png) | Check the neck and tail transitions |
| [Home mockup](assets/mockups/home-v3.png), [MDAS mockup](assets/mockups/mdas-gameplay-v3.png), [design system](design-system/README.md) | Screen styling, mascot, buttons, palette and typography |
| [Seven-screen design review](design-system/screens-prototype.html), [screen notes](design-system/screen-designs.md) | Proposed phone/tablet screen layouts awaiting review; phone Home remains approved |

The static gameplay mockup does not override the approved moving snake. Use [the approved asset allowlist](assets/README.md) for source artwork. Superseded tile-sprite, segmented body and smooth-body v2 studies have been removed from active folders and preserved in [the deprecated archive](archive/README.md). The browser prototype's grid toggle, PNG export and study controls are development tools.

## Loading state proposal

A [mascot loading animation](design-system/loading-prototype.html), with [motion notes](design-system/loading-animation.md), is now available for review: a snake in the home mascot's design slithers after a rolling apple marked 0 from left to right, with a deforming S-wave body and matching mascot-style face. It follows the phone-portrait / tablet-landscape policy. It is separate from the gameplay renderer and awaits visual approval. When implemented later, loading ends on actual readiness rather than waiting for a complete animation loop.

## Gameplay baseline to port

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

Preserve these edge cases:

- Entering the current tail cell is legal only when that tail vacates on a non-growing tick.
- A filled Classic board completes the game.
- The browser resets the snake pose without an extra life penalty if too few reachable spaces remain for the next set of apples. Carry this safety fallback into the prototype and review its UX before release.
- Current distractors can be negative integers. Restricting the answer range or adding difficulty progression is a later product change.

## Rendering and assets

Follow [smooth-joins v3](assets/game/path-snake/smooth-joins-v3.md) for the exact assembly contract and source-region coordinates. The renderer draws one continuous shaded body/tail and a raster head. Sparse organic spots and a softly blended neck preserve the approved character. **A one-cell-wide silhouette is not required.**

| File | Android use |
| --- | --- |
| `assets/game/path-snake/snake_path_parts_v1.png` | Gameplay head source; body/tail regions remain unused by the final renderer |
| `assets/game/snake_mascot_v1.png` | Static home mascot; not a moving gameplay snake |
| `assets/game/forest_background_v1.png` | Portrait forest backdrop, crop at edges while preserving clear UI areas |

Keep original PNGs unchanged. Later copy required art into `res/drawable-nodpi/`, preserve alpha and aspect ratio, and cache head cropping/masking. Body shading follows local path cross-sections rather than a screen-aligned gradient. All rendered parts share the same centerline and tangent calculations.

Future skins can combine unique head artwork, body silhouette, markings and shading in a skin configuration. They need not be palette swaps. Additional skin artwork and a selector are outside the first Android prototype.

Generated reference-based art remains review material; its provenance is recorded in the asset docs. Visual approval is not a rights clearance for publication. Apple, logo, icon and decoration production assets are not finalized.

## Incremental Android Studio work, when resumed

Use native Kotlin, Jetpack Compose and Canvas. Start with one application module and a pure Kotlin engine, ViewModel/StateFlow UI state and Preferences DataStore. Choose compatible SDK/build/dependency versions when creating the project; this handoff does not pin a toolchain.

1. Create the Android Studio Compose project, application theme and Home → PLAY → mode selection → gameplay navigation. Apply phone portrait and tablet landscape orientation requirements to all screens. Preserve the approved home button/font treatment. Themes remain deferred.
2. Port and unit-test grid movement, queued turns, growth and collision rules independently of Compose, using explicit 12 × 16 phone and 20 × 16 tablet configurations. Add the MDAS generator/evaluator and lives transitions with deterministic tests.
3. Reproduce the final snake in a Canvas inspection scene first: straight, rounded turns, short/long bodies and all four directions. Compare neck/tail joins to the linked v3 stills before connecting live movement.
4. Connect engine snapshots to one lifecycle-aware logical loop and frame-driven visual interpolation. Add swipes, ready/pause/game-over states and the MDAS panel/apples. Keep rules outside the renderer.
5. Add local best scores and run the acceptance checks below on an emulator or device. Explain engineering choices as the work progresses; implement incrementally.

## Native acceptance checks

- Both modes follow the baseline above; home PLAY opens the mode picker. Phones stay portrait with 12 × 16 cells, and tablets stay landscape with 20 × 16 cells, across navigation and device rotation.
- Math tests cover precedence, equal-precedence order, exact division and unique options with exactly one correct answer. Engine tests cover growth, queued/reverse turns, tail-cell collision, lives, spawning and full-board completion.
- Head, body and tail stay aligned during straight movement and turns at several interpolation phases. Neck and tail have no obvious cap, gap or attachment seam; markings remain sparse and clipped inside the silhouette.
- Swipe input is responsive; pause/resume/background/restart create no duplicate loop or unexpected catch-up movement.
- Question, hearts and three answer labels stay readable on smaller screens. Safe-area insets and aspect-ratio changes preserve the board and usable touch targets.
- APK build and actual Android visual/frame-timing checks are recorded separately. Browser/offscreen checks do not count as native validation.

Existing study evidence: the documented temporary offscreen Canvas harness checked 18 gameplay assertions, 1000 generated questions, six gameplay render phases and 648 path geometry combinations. Those checks support the browser study; an Android test suite still needs to be written when implementation begins.

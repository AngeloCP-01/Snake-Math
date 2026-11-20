# Snake & Math — Design System

**Source of truth:** `docs/assets/mockups/home-v3.png` and `docs/assets/mockups/mdas-gameplay-v3.png`.
These mockups govern screen styling. The [current grid prototype](../assets/game/snake-grid-gameplay-prototype.html) and [smooth-joins v3](../assets/game/path-snake/smooth-joins-v3.md) govern the approved moving snake; see the [Android handoff](../android-prototype-handoff.md). Android coding is deferred.
Colors were sampled from those images. Sizes are in **dp/sp** for a 390dp-wide phone (in the mockups, 1dp ≈ 1.75px).

| File | What it is |
|---|---|
| `design-system.html` | Visual reference. Open it in a browser to see swatches, type and live component specimens. |
| `tokens.json` | Machine-readable tokens: color, type, spacing, radius, elevation, sizes, board, motion. |
| `README.md` | This spec. Read it before building any screen or component. |
| `screens-prototype.html` | Seven PRD screens for phone/tablet, with gallery and click-through review. Proposed designs, awaiting review. |
| `screen-designs.md` | Screen/layout decisions, interaction scope and verification notes. |
| `previews/README.md` | Fourteen current browser-rendered screen exports; reference-only. |
| `loading-prototype.html` | Live slithering mascot/rolling-zero-apple loading motion review, for portrait phone and landscape tablet. |
| `loading-animation.md` | Loading motion contract, asset use and native handoff. |

---

## 1. Principles

1. **A glossy jungle toy box.** Deep forest backgrounds; bright lime, gold and red are the only loud colors. If everything glows, nothing does.
2. **Chunky and touchable.** Everything is rounded and thick: pill buttons, outlined lettering, 3D lips on primary actions. Touch targets are at least 48dp.
3. **Cream for reading, gold for numbers.** Text is cream (`text.primary`). Scores are gold (`text.number`). Lime marks the accent word or the primary action.
4. **The board is sacred.** Decoration lives outside the board or sits faintly at its edges, never on a playable cell with the snake or an apple. Swipe-only: no arrow buttons, ever.
5. **Math is the hero on MDAS screens.** The expression is the largest text on the screen after the logo.

---

## 2. Color

### Surfaces
| Token | Hex | Use |
|---|---|---|
| `forest-950` | `#03200F` | Vignette, deepest shadow |
| `forest-900` | `#023E21` | Game background, home edges |
| `forest-800` | `#08502B` | Home background center glow |
| `panel-800` | `#053F1C` | Stat panels, pill buttons, chips |
| `panel-900` | `#023814` | Question card |
| `panel-line` | `#195C20` | 2dp border on panels and chips |
| `foliage-600` / `foliage-500` | `#227C28` / `#429E27` | Background leaves (far / near) |
| `leaf-400` / `leaf-300` | `#58B437` / `#6CBE3B` | Board frame, leaf accents |

### Content and accents
| Token | Hex | Use |
|---|---|---|
| `cream-100` | `#F7F2BB` | Primary text, labels, icons |
| `cream-title` | `#FDF0B7` | "SNAKE" line of the logo, math expression |
| `lime-text` | `#A6DA24` | Accent words ("LEARNING", "& MATH") |
| `gold-300` | `#F7D548` | All score numbers |
| `ink-800` | `#03481A` | Text and icons on lime (PLAY) |
| `ink-900` | `#03371F` | Logo outline and extrusion |
| `red-500` | `#F9453E` | Hearts / lives |

### Primary action (lime)
Use a vertical gradient: `lime-300 #C1EB43` → `lime-400 #B1E630` → `lime-500 #9EDE1E` → `lime-600 #95DC18`. Add a 6dp bottom lip in `lime-lip #3C851B`.

### Illustration
| Token | Hex |
|---|---|
| Snake body base / edge / center (see §5) | `#8ABA16` / `#739D0D` / about `#A9D91F` |
| Snake spots / dark spots / shadow | `#72A71A` / `#649A16` / `#071611` at 40% |
| Snake face | Comes from the head region of the atlas PNG, not from tokens |
| Apple body / highlight / stem and leaf / shadow | `#D83D26` / `#F17D54` / `#749B36` / `#092019` at 40% |
| Apple number / outline | `#FFF4D3` / `#9A271E` |
| Trophy | `#FCCD20` → `#F5B10A` |
| Rock / shade | `#80776A` / `#615D57` |

**Contrast:** cream on `panel-800` is about 13:1, gold on `panel-800` about 10:1, and `ink-800` on lime about 8:1. All pass WCAG AA.

---

## 3. Typography

| Role | Font | Size / line height (sp) | Notes |
|---|---|---|---|
| `logo.home` | Baloo 2 ExtraBold | 64 / 60 | Uppercase, two lines. 6dp `ink-900` outline, 4dp extrusion. "SNAKE" cream, "& MATH" lime gradient |
| `logo.compact` | Baloo 2 ExtraBold | 26 / 24 | The game header version of the logo, with a leaf sprig |
| `math.expression` | Baloo 2 ExtraBold | 50 / 54 | Cream. Use real `×` and `÷` glyphs |
| `button.primary` | Baloo 2 ExtraBold | 38 / 40 | Uppercase, `ink-800` |
| `number.l` | Baloo 2 ExtraBold | 34 / 36 | Gold. Best score on home |
| `number.m` | Baloo 2 ExtraBold | 26 / 28 | Gold. Score panel |
| `title.s` | Baloo 2 ExtraBold | 20 / 24 | Uppercase cream: "BEST SCORE", "FIND THE ANSWER", mode chip |
| `label.m` | Baloo 2 ExtraBold | 17 / 20 | Uppercase: pill buttons, "SWIPE TO MOVE" |
| `label.s` | Baloo 2 ExtraBold | 14 / 16 | Uppercase: "SCORE", "BEST" |
| `body.m` | Nunito Bold | 17 / 22 | Subtitle "A little game of growth" |

Both fonts are on Google Fonts. Bundle them in `res/font/` rather than using downloadable fonts, so the first frame never shows a fallback font.

---

## 4. Spacing, radius, stroke, elevation

- **Spacing (4dp grid):** 4 · 8 · 12 · 16 · 20 · 24 · 32 · 40. The screen gutter is **16dp**.
- **Radius:** pill buttons, chips and lives use `999`. Stat panels use `16`. The question card and board use `24`. The icon button is a circle.
- **Stroke:** panels and chips use 2dp `panel-line`. The icon button ring is 3dp. The board frame is 6dp. Illustration outlines are about 3dp.
- **Elevation:**
  - *Primary lip:* `0 6dp 0 lime-lip`. When pressed, the button moves down 4dp and the lip shrinks to 2dp.
  - *Panel:* soft drop shadow `0 6dp 14dp rgba(0,0,0,.30)` plus a 4dp inner darkening.
  - *Board:* `0 8dp 20dp rgba(0,0,0,.35)`.

---

## 5. Components

Each entry gives the anatomy, the size, then the tokens.

### Logo lockup
- **Home:** two uppercase lines centered, about 300 × 150dp. Leaf clusters at the top-left and right of "SNAKE", and at the left of "& MATH".
- **Compact:** about 112 × 56dp, top-left of the game header, with a small sprig above the "E".

### Primary button: PLAY
- Pill, 296 × 64dp, lime gradient, 6dp lip, plus a white gloss strip about 40% opacity across the top third.
- Play triangle (28dp, rounded corners) followed by the label, both `ink-800`.
- States:
  - *pressed:* moves down 4dp.
  - *disabled:* 40% saturation, no lip.

### Secondary pill button: THEMES, SETTINGS
- Pill, at least 136 × 48dp, `panel-800` fill, 2dp `panel-line` border.
- 26dp cream icon followed by a `label.m` cream label.
- Two side by side with a 16–20dp gap.

### Stat panel: BEST SCORE
- 270 × 78dp, radius 16, `panel-800` with a 2dp border.
- 48dp trophy on the left. Stacked `title.s` cream label over a `number.l` gold value, centered as a group.

### Score panel: SCORE | BEST
- 60dp tall, radius 16. Two equal columns split by a 2dp `panel-line` divider.
- Each column: `label.s` cream over `number.m` gold.

### Icon button: Pause
- 52dp circle, fill `#197126`, 3dp `#50A037` ring.
- 26dp cream glyph: two rounded bars.

### Mode chip: MDAS LEARNING
- Pill, 40dp tall, `panel-800` with a 2dp border, and a small leaf tucked at the left edge.
- Two-tone `title.s`: "MDAS" cream, "LEARNING" `lime-text`.

### Lives pill
- Pill, 40dp tall, `panel-900` fill. Holds 5 hearts at 26dp with a 4dp gap.
- A lost life is a heart outline at `status.lifeLost`. The mockup shows 5 full hearts; the lost state is our addition.

### Question card
- Full width minus the gutters, radius 24, `panel-900` fill, 2dp border, at least 96dp tall.
- `title.s` "FIND THE ANSWER", with leaf sprigs overlapping the top-left and top-right corners.
- Below it, the `math.expression` line.

### Game board
- **Frame:** 6dp, `leaf-400` with a `leaf-300` top highlight. Curls of leafy frame at all four corners.
- **Field:** `#01411E`, with 1dp grid lines in `#0D5426`, radius 24 outside and 18 inside.
- **Cells:** phones use **12 × 16** in portrait; tablets use **20 × 16** in landscape. Fit square cells to the available board rectangle; source-canvas pixels are not Android dp. Select the profile at session creation and retain it during the round. The mockup's 8 × 9 / 44dp layout is historical.
- The prototype draws an unstyled checkerboard (`#1D3C2C` / `#203F2F`). The app uses the field above instead.
- **Decorations:** grass tufts and small rocks only. They are drawn under the snake and apples and must never share a cell with either.

### Answer apple
- Drawn procedurally inside its cell, with no image (as in the prototype). Body `#D83D26`, highlight `#F17D54`, stem and leaf `#749B36`, soft shadow `#092019` at 40%.
- The number is centered at 0.38 × cell, in `#FFF4D3` with a 2.5px `#9A271E` outline. Use Baloo 2 ExtraBold once the font is bundled.
- All three apples look identical. Never style the correct one differently.

### Snake — approved smooth-joins v3

Use [smooth-joins-v3.md](../assets/game/path-snake/smooth-joins-v3.md) as the renderer contract and the [current grid page](../assets/game/snake-grid-gameplay-prototype.html) as the moving visual reference. Superseded head artwork and body-pod studies are in [the deprecated archive](../archive/README.md). Only assets listed in [the approved allowlist](../assets/README.md) should be considered for Android resources.

- **Head:** original head region from `path-snake/snake_path_parts_v1.png`, cropped to visible bounds, with its rear attachment feathered over the body. Preserve the face, eyes, snout and tongue.
- **Body and tail:** one swept silhouette with continuous local cross-section shading, sparse clipped oval spots and an integrated tapered tail. No body or tail image stamps.
- **Alignment:** every part follows one rounded route derived from grid-cell centers. Interpolate visuals between logical ticks. Width is tunable; a one-cell footprint is not required.
- **Collision:** integer grid cells remain authoritative; art width and tongue do not enlarge the hit area.
- **Color:** the illustration tokens above reflect the current prototype; the exact continuous shading and spot formulas remain in its `smoothBody` function and the v3 specification.
- **Validation:** visual study approved; Android appearance and frame timing remain to be measured.

**Home mascot:** `docs/assets/game/snake_mascot_v1.png` (1254², transparent). It is a static illustration, not a gameplay sprite.

### Swipe hint
- `— SWIPE TO MOVE —` in `label.m` cream, with 28dp dashes on both sides, centered under the board.
- Fade it out after the first successful swipe.

### Board state overlay
- Centered over the board on a `forest-950` scrim at 25%. The card is `panel-900` at 93% with a 2dp `leaf-400` border, radius 18, at most 300dp wide.
- Title is `title.s` at 24sp, cream. Body is Nunito 15/24 at 75% cream.
- Copy from the prototype:

  | State | Title | Body |
  |---|---|---|
  | ready | Swipe to begin | Swipe on the board to set your direction. |
  | paused | Taking a breather | Press Resume when you're ready. |
  | over | Game over | Score N · Press Restart to try again. |
  | won | Garden complete! | Score N · Press Restart to play again. |

- Hidden while running. It never blocks swipes on the board.

### Feedback line
- A single status line under the board, announced politely to screen readers. It replaces the swipe hint once play starts.
- `label.m` in mixed case. Gold `gold-300` for gains, `red-300` for losses.
- Messages:
  - "+10 · Apple collected"
  - "+10 · Correct answer!"
  - "Not quite · the answer was N"
  - "You bumped into your tail." (Classic game over; MDAS body crossings need no collision feedback)
  - "A fresh patch for your next apples." (when the pose resets for fairness)

### Background
- A radial glow (`forest-800` center → `forest-900` → `forest-950` edges).
- Layered leaf silhouettes on both sides (`foliage-600` far, `foliage-500` near).
- Bright leaves in the bottom corners. Nothing busy behind any text.

---

## 5b. Gameplay rules (as implemented in the prototype)

For native arithmetic generation, the updated [PRD Section 2.2](../snake-math-prd.md#22-mathematical-question-generation) defines child-focused difficulty profiles. Native movement also supersedes the browser: both modes wrap across borders; Classic ends only on self-hit; MDAS passes through its body and loses lives only on incorrect answers. It keeps moving without resetting its pose/question on overlap. When necessary, MDAS apples may overlap the body; render their labels above it and the head above them. Classic instructions should say "Wrap around the edges. Avoid your own body." The single question pool and collision rules below remain a historical record of the browser prototype. Difficulty-selection UI still needs to be incorporated into the prepared flow.

The following describes the earlier browser implementation, including superseded collision behavior. Numerical tuning and release polish remain adjustable; see the [handoff](../android-prototype-handoff.md) for native updates and acceptance checks.

- **Input:** swipe or drag only, with a 16px threshold; the dominant axis wins.
  - Up to **2 turns are queued**. A reversal is rejected against the last queued direction.
  - Turns apply at cell boundaries. The first swipe starts the round (state `ready` → `running`).
- **Classic:** one apple; **+10** and grow per apple. A wall or self collision is game over. A full board is "Garden complete!".
- **MDAS:** 5 lives and 3 distinct numbered apples.
  - A correct answer gives **+10** and grows the snake.
  - A wrong answer costs one life, with no growth, and moves to the next question.
  - A collision costs one life and resets the snake and the question, then waits for a swipe.
  - At 0 lives the game is over. There's no question timer.
- **Questions:** forms `a + b × c`, `a × b − d`, `ab ÷ b + c`, `ab ÷ b × c`.
  - Evaluated with an explicit precedence evaluator (× ÷ before + −, left to right), never by evaluating strings. Integer answers only.
  - Wrong answers are distinct values within ±1–9 of the correct one, in a shuffled order.
- **Fairness:**
  - Apples spawn only on cells the head can reach.
  - If the body encloses the head, the pose resets *without* a penalty before new apples spawn.
  - Entering the vacating tail cell is legal unless that tick grows the snake.
- **Lifecycle:** backgrounding auto-pauses; resuming needs an explicit tap. Use one frame loop, never one per restart. Best scores are per session in the prototype, and persist with DataStore in the app.

---

## 6. Screen layouts (phone, 390 × 844dp)

**Home**, top to bottom:
- status bar
- logo (top about 70dp)
- subtitle
- mascot hero (about 300 × 240)
- 24dp gap
- PLAY → separate mode selection screen (Classic Apple / MDAS Learning)
- 20dp gap
- BEST SCORE
- 20dp gap
- THEMES · SETTINGS
- bottom inset

**MDAS gameplay**, top to bottom:
- header row: compact logo · score panel · pause, with a 12dp gap
- 12dp gap
- mode chip · lives pill
- 12dp gap
- question card
- 12dp gap
- board (fills the remaining height, snapped to whole cells)
- 16dp gap
- swipe hint
- bottom inset

**Classic gameplay** is the same without the MDAS question card and lives pill. Keep the selected device profile consistent between modes: 12 × 16 for phones or 20 × 16 for tablets.

---

### Tablet layout and orientation policy

All seven main phone screens and the loading state are portrait only; all seven main tablet screens and the loading state are landscape only. The browser review uses fixed design frames; native orientation enforcement is pending Android implementation.

In landscape gameplay, use a compact left rail for mode, lives and the persistent MDAS question. Expand the board to **20 × 16**, with square cells; at the 1024 × 768 reference size the outer board is approximately 704 × 566px. Keep the same snake proportions and shading. Pause/results preserve the wider field beneath their overlays. Home, mode selection and Settings retain the tablet layouts in the screen gallery.

---

## 7. Jetpack Compose mapping (for implementation)

| Token group | Compose home |
|---|---|
| `color.raw` + `color.semantic` | `core/ui/theme/Color.kt`, exposed through a `SnakeMathColors` `CompositionLocal`. These colors don't map cleanly to Material roles, so don't force them into a Material3 `ColorScheme` |
| `typography.scale` | `core/ui/theme/Type.kt`, as a `SnakeMathTypography` data class with `TextStyle`s |
| `spacing`, `radius`, `size` | `core/ui/theme/Dimens.kt` |
| Components | `core/ui/components/` (`PrimaryButton`, `PillButton`, `StatPanel`, `ScorePanel`, `RoundIconButton`, `ModeChip`, `LivesPill`, `QuestionCard`) |
| Board, snake, apple | `core/ui/drawing/`, drawn on Compose `Canvas` from the `board` and `illustration` tokens. The renderer reads an engine snapshot plus a render fraction (0–1 within the tick) |
| Snake head | Atlas `path-snake/snake_path_parts_v1.png` → `res/drawable-nodpi/`. Load it once as an `ImageBitmap`, pre-feather the head region once, then draw it with `DrawScope.drawImage(image, srcOffset, srcSize, dstOffset, dstSize)` inside `withTransform { translate; rotate }` |
| Game rules and question evaluator | Plain Kotlin in `core/game` and `core/math`, ported from the prototype's engine (no Compose dependency) |
| Mascot, background | Approved PNGs from `docs/assets/game/` → `res/drawable-nodpi/` (keep alpha and aspect ratio) |
| Leaves, rocks, trophy, icons | Vector drawables (`res/drawable/*.xml`) |

Outlined, extruded lettering (logo) is not a plain `Text` style. Draw it twice: first a `drawStyle = Stroke(...)` pass in `ink-900`, then the fill pass on top. Or ship the logo as a vector drawable.

---

## 8. Open items

- **THEMES** is retained in the historical home mockup; a themes screen/skin selector is deferred beyond the initial Android prototype. Settings remains in product scope.
- **Device calibration:** verify phone 12 × 16 portrait and tablet 20 × 16 landscape, including orientation behavior, cell size, answer readability, swipe feel and snake scale on devices before release.
- **Screen review:** the [seven-screen gallery](screens-prototype.html) now proposes mode selection, pause, game over and settings alongside the home/gameplay layouts, for phone and landscape tablet. User approval and native layout validation remain pending.
- **The prototype's chrome is unstyled.** Its HUD, buttons and fonts are placeholders. When building, use the components in §5, not the prototype's HTML styling.
- **Art status** (`docs/assets/game/`): the final gameplay snake appearance is approved; its atlas, mascot and background are review masters. The apple is procedural in the prototype; production logo, icons and decorations remain pending. Reference-based generated art provenance is recorded in the asset notes. Visual approval is not publication rights clearance.

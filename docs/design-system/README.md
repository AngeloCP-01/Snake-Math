# Snake & Math — Design System

**Source of truth:** `docs/assets/mockups/home-v3.png` and `docs/assets/mockups/mdas-gameplay-v3.png`.
Colors were sampled from those images. Sizes are in **dp/sp** for a 390dp-wide phone (in the mockups, 1dp ≈ 1.75px).

| File | What it is |
|---|---|
| `design-system.html` | Visual reference. Open it in a browser to see swatches, type and live component specimens. |
| `tokens.json` | Machine-readable tokens: color, type, spacing, radius, elevation, sizes, board, motion. |
| `README.md` | This spec. Read it before building any screen or component. |

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
| Snake (provisional, see §5) fill / outline / spots | `#BEEF00 → #A5DC00 → #78B700` / `#174C03` / `#448E06`, `#65A700` |
| Snake face, belly, eyes | Come from the head and mascot PNGs, not from tokens |
| Apple body / shade / highlight / leaf | `#D41D21` / `#A81619` / `#F86257` / `#429E27` |
| Apple number / outline | `#FCFAC3` / `#6A0F10` |
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
- **Cells:** target **44dp**. On a phone this gives an **8 × 9** grid. Compute the column count as `floor(availableWidth / 44)`.
- **Decorations:** grass tufts and small rocks only. They are drawn under the snake and apples and must never share a cell with either.

### Answer apple
- About 1.15 × cell, in a glossy red. The leaf points up-right.
- The number is centered in Baloo 2 ExtraBold at 0.5 × cell, cream, with a dark red outline at 0.06 × cell.
- All three apples look identical. Never style the correct one differently.

### Snake (renderer spec) · IN PROGRESS
> The gameplay snake art is still being designed in `docs/assets/game/` (`snake-assembly-prototype.html`, `gameplay-snake-v1.md`). Values here are **provisional** and track that prototype. Update this section when the art is approved.

**Hybrid rendering:** a raster head plus a body drawn on Canvas.
- **Head:** the transparent PNG `snake_head_right_v1.png` (1254², faces right), drawn on top of the body.
  - Fit its *visible* alpha bounds to the head footprint (currently about 2 × cell wide, tongue included). Don't scale the whole square to one cell.
  - The neck anchor is about `(195, 675)` in source pixels. The body runs underneath the short neck stub to hide the seam.
  - For other directions, rotate it about the head cell center. Directional face variants are a later art decision.
- **Body:** one continuous rounded `Path` through cell centers; never stamp tiles or circles. Turn radius is about 0.5 × cell. Layers, bottom to top:
  1. Shadow `#001D14` at 50%, offset 8 down
  2. Outline `#174C03`
  3. Vertical fill gradient `#BEEF00 → #A5DC00 → #78B700`
  4. Highlight `#DCFF61` at 33%, offset up-left
- **Spots:** oval spots alternating `#448E06` / `#65A700` on alternating sides of the centerline, kept inside the body width.
- **Tail:** a tapered point at the end of the same shape. Outline `#174C03`, fill `#9DD400`, highlight stroke `#D8F75A`. No tail image.
- **Collision** stays grid-based. The head art and tongue never enlarge the hit area.
- **Open calibration:** body width relative to the cell, head scale, the neck join, and readability at a cell of about 44dp.

**Home mascot:** `docs/assets/game/snake_mascot_v1.png` (1254², transparent). It's a static illustration, not a gameplay sprite.

### Swipe hint
- `— SWIPE TO MOVE —` in `label.m` cream, with 28dp dashes on both sides, centered under the board.
- Fade it out after the first successful swipe.

### Background
- A radial glow (`forest-800` center → `forest-900` → `forest-950` edges).
- Layered leaf silhouettes on both sides (`foliage-600` far, `foliage-500` near).
- Bright leaves in the bottom corners. Nothing busy behind any text.

---

## 6. Screen layouts (phone, 390 × 844dp)

**Home**, top to bottom:
- status bar
- logo (top about 70dp)
- subtitle
- mascot hero (about 300 × 240)
- 24dp gap
- PLAY
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

**Classic gameplay** is the same without the mode chip and question card. The lives pill is hidden in Classic, so the board grows.

---

## 7. Jetpack Compose mapping (for implementation)

| Token group | Compose home |
|---|---|
| `color.raw` + `color.semantic` | `core/ui/theme/Color.kt`, exposed through a `SnakeMathColors` `CompositionLocal`. These colors don't map cleanly to Material roles, so don't force them into a Material3 `ColorScheme` |
| `typography.scale` | `core/ui/theme/Type.kt`, as a `SnakeMathTypography` data class with `TextStyle`s |
| `spacing`, `radius`, `size` | `core/ui/theme/Dimens.kt` |
| Components | `core/ui/components/` (`PrimaryButton`, `PillButton`, `StatPanel`, `ScorePanel`, `RoundIconButton`, `ModeChip`, `LivesPill`, `QuestionCard`) |
| Board, snake, apple | `core/ui/drawing/`, drawn on Compose `Canvas` from the `board` and `illustration` tokens |
| Mascot, gameplay head, background | Approved PNGs from `docs/assets/game/` → `res/drawable-nodpi/` (keep alpha and aspect ratio) |
| Leaves, rocks, trophy, icons | Vector drawables (`res/drawable/*.xml`) |

Outlined, extruded lettering (logo) is not a plain `Text` style. Draw it twice: first a `drawStyle = Stroke(...)` pass in `ink-900`, then the fill pass on top. Or ship the logo as a vector drawable.

---

## 8. Open items

- **THEMES** appears in the mockup, but the requirements doc lists themes as out of scope. Either add it to the scope or swap the button for something else, such as "HOW TO PLAY".
- **Not in the mockups yet:** the mode select screen (opened by PLAY), Pause, Game Over, and feedback for correct and wrong answers. Build them from these components.
- **Gameplay art is in progress** (`docs/assets/game/`): the snake head, mascot and background are review v1. The apple, logo and decoration assets don't exist yet. The art is AI-generated from Freepik-styled references, which is not a rights clearance, so check the Freepik license before release.

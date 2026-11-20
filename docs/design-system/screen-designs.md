# Seven-screen visual review

Status: proposed screen designs for review, 2026-11-20. These HTML mockups extend the existing design system; they are not a native Android implementation or a replacement for the approved game engine prototype.

Open [the screen gallery](screens-prototype.html), then choose Phone or Tablet. “Click through” opens one screen with working navigation and settings toggles. Each screen/device selection is reflected in the URL. The bottom arrows cycle review screens; they are outside the app frame and are not gameplay controls.

## Direction

The PRD and approved visual system already define the direction: forest surfaces, lime primary buttons with a raised lip, cream labels, gold scores, Baloo 2 display type and Nunito body text. Reuse those components rather than invent another art direction. Foliage stays behind the interface and outside playable cells.

The approved phone home image is displayed unchanged, cropped in CSS to its screen area, with invisible navigation hit areas. The original PNG is untouched. Tablet Home uses the same title treatment, supplied mascot, PLAY, best-score panel and secondary buttons in two columns. THEMES remains visible to preserve the reference, with a “planned for later” notice rather than a new screen.

## Screens

| Screen | Phone, 390 × 844 | Tablet, 1024 × 768 landscape |
| --- | --- | --- |
| Home | Approved image preserved | Mascot/title left; PLAY and score/actions right |
| Mode selection | Two stacked mode cards with individual best scores | Cards side by side, with larger emblems |
| Classic gameplay | Compact header, score/best, board and swipe hint | Mode/instructions left; board right |
| MDAS gameplay | Header, five hearts, persistent question, three answer apples | Compact lives/question rail left; wider 20 × 16 board right |
| Pause | Frozen gameplay under a scrim; Resume, Restart, Home | Same actions in a centered, bounded card |
| Game over | Final/best scores, Try Again and Home | Same focused card over frozen tablet gameplay |
| Settings | Two rows for sound effects/music | Introduction left; sound controls right |

Phones retain **12 × 16** cells in portrait; tablets expand to **20 × 16** in landscape. Both profiles use square cells, preserving the snake's proportions rather than stretching the 12-column image. The tablet board grows from 414px to 704px wide at the 1024 × 768 reference size, with a compact 216px information rail. Boards use the existing final continuous shaded body, sparse spots, integrated tail and feathered head from the current atlas. The layouts reuse the design system's renderer; the approved gameplay engine/rendering code is unchanged (only an obsolete documentation navigation link was repaired). All answer apples use the same appearance. The illustrative expression is `5 + 3 × 2 = ?`; options are 11, 16 and 13.

Orientation is now a product requirement: **phones portrait only, tablets landscape only across all seven screens**. Native orientation enforcement, tablet size classification and font scaling will be implemented and verified in Android Studio. The tablet board profile was updated from the initial 12 × 16 proposal to 20 × 16 at the user's request. The review gallery itself scales its fixed design frames to smaller browser windows.

## Interaction scope

- **Movement rules — confirmed 2026-11-20:** both modes wrap at borders. Classic ends only on self-hit; native copy should explain wrapping and avoiding the body. MDAS passes through its body without a pause/reset/penalty; only incorrect answers cost lives. Existing browser code and exported PNGs still contain older wall-warning copy and need refreshing when the review screens are next revised. For dense MDAS boards, keep answer labels visible above any overlapping body.
- **Home best scores — confirmed 2026-11-20:** the native Home panel shows both Classic and MDAS best scores, clearly labeled, using the existing panel treatment. The single score in the prepared exports is illustrative; those PNGs have not been regenerated for this binding clarification.
- PLAY → mode selection → Classic or MDAS.
- Pause → Resume or Restart retains the selected mode; Home returns to Home.
- Results → Try Again retains the mode; MDAS results show all five hearts depleted.
- Sound/music switches update in-memory review state and expose accessible switch labels/states. Their hit areas are 48px high. Reload resets them.
- Gameboards are static visual compositions in this screen review. Swipe gameplay remains in [the approved playable prototype](../assets/game/snake-grid-gameplay-prototype.html).
- Scores are illustrative. Settings do not change audio or write preferences.

## Exports and verification

[Screen exports](previews/README.md) contain 14 current browser-rendered PNGs, one per screen/device. They are review references, not Android runtime images. Fonts load from Google Fonts; internet access is needed for an uncached first view.

Browser review covered all 14 updated layouts, canvas/head loading and frame bounds, confirming square cells and the correct 12 × 16 / 20 × 16 profiles. The initial review also passed 20 navigation/state checks across phone/tablet. The original source PNGs retain their pre-design checksums. The playable gameplay file differs only in its repaired documentation link; its scripts are unchanged. JavaScript syntax, local links and documentation JSON were checked separately. These checks do not establish Android behavior, accessibility compliance or frame timing.

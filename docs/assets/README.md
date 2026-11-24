# Approved assets

Reconciled 2026-11-24; artwork approval/provenance remains dated 2026-11-20. This is the allowlist for the Android prototype. File version numbers reflect when artwork was generated; the files below remain the approved sources. [The Android handoff](../android-prototype-handoff.md) describes their use.

## Runtime source artwork

Only these three PNGs are allowlisted for Android `res/drawable-nodpi/`. T-004 already bundles the mascot and forest backdrop unchanged; the gameplay head atlas is still a future import. Keep source alpha/aspect ratio and manifest checksums. The agreed [P-005](../requirements/P-005-play-mode-gameplay-flow.md) adds no new raster artwork; its task is separately approved and implementation is pending a new Session B.

| File | Use |
| --- | --- |
| [snake_mascot_v1.png](game/snake_mascot_v1.png) | Static home mascot |
| [forest_background_v1.png](game/forest_background_v1.png) | Forest backdrop |
| [snake_path_parts_v1.png](game/path-snake/snake_path_parts_v1.png) | Gameplay **head source only**, cropped/masked according to [smooth-joins v3](game/path-snake/smooth-joins-v3.md). Its body/tail regions are unused; the renderer draws continuous geometry. |

## Screen references — not runtime images

| File | Use |
| --- | --- |
| [home-v3.png](mockups/home-v3.png) | Approved styling, subject to confirmed native dual-score/no-THEMES/no-scroll/tighter-title adaptations in the [design spec](../design-system/README.md); PLAY opens mode selection |
| [mdas-gameplay-v3.png](mockups/mdas-gameplay-v3.png) | MDAS screen styling; the current prototype overrides the snake illustration |

## Final renderer previews — not runtime images

| File | Use |
| --- | --- |
| [assembled-joins-straight-v3.png](game/path-snake/assembled-joins-straight-v3.png) | Head/body/tail joins in a straight pose |
| [assembled-joins-curve-v3.png](game/path-snake/assembled-joins-curve-v3.png) | Joins and body through turns |
| [grid-mdas-joins-v3.png](game/path-snake/grid-mdas-joins-v3.png) | Final snake on the MDAS board |

## Current review pages

- [Grid gameplay prototype](game/snake-grid-gameplay-prototype.html): approved moving snake and historical browser behavior; current native wrap/body/difficulty rules are in the PRD.
- [Design system](../design-system/README.md): screen styling and components.
- [Seven-screen UI review](../design-system/screens-prototype.html): proposed phone/tablet layouts and [14 PNG exports](../design-system/previews/README.md). These are reference-only and remain outside the runtime asset allowlist.
- [Asset manifest](asset-manifest.json): explicit image roles and SHA-256 checksums. Import runtime sources individually rather than copying this entire directory into an Android project.

## Deprecated material

Superseded mockups, sprite sheets, old head artwork, prototype revisions, screenshots and original inspiration files were removed from the active folders. Their originals and provenance notes are preserved in [the deprecated archive](../archive/README.md). That archive is historical material, outside the approved asset allowlist.

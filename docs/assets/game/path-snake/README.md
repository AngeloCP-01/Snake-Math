# Gameplay snake artwork and renderer

Current: **smooth-joins v3**, approved for the Android prototype on 2026-11-20. Use [smooth-joins-v3.md](smooth-joins-v3.md) for the assembly contract, crop/anchor settings and validation evidence.

The final snake uses a continuous shaded Canvas body with sparse spots, a tail tapered within that same silhouette, and the original raster head feathered at its rear attachment. Only the head image is drawn. Width is flexible; a one-cell silhouette is not a requirement.

## Current references

- [Grid gameplay](../snake-grid-gameplay-prototype.html): approved Classic/MDAS rules and moving snake.
- [Straight joins](assembled-joins-straight-v3.png), [curved joins](assembled-joins-curve-v3.png), [MDAS board](grid-mdas-joins-v3.png): inspected renders from the actual prototype code.
- [Android handoff](../../../android-prototype-handoff.md): next steps when the user resumes coding in Android Studio.

## Source art

[snake_path_parts_v1.png](snake_path_parts_v1.png) is a 2172 × 724 RGBA atlas generated with the approved home mascot as the character/palette reference. Source regions `(x, y, width, height)` are head `(30, 155, 653, 445)`, body pod `(787, 193, 600, 368)` and tail `(1488, 198, 662, 361)`. They are not equal grid tiles. The original atlas is unchanged; the current renderer uses only the head region and prepares its alpha at load time.

This is a proposed 2D treatment informed by published Snake Clash imagery, not a reconstruction of Supercent's renderer or copied game assets. Generation provenance is recorded in [generation-prompt.md](generation-prompt.md); visual approval is not release rights clearance.

## Earlier revisions

Superseded segmented/smooth-body v2 prototypes, old sprite sheets and earlier preview PNGs are preserved only in [the deprecated archive](../../../archive/README.md). The active folders contain the approved v3 previews and current source art. Use the [approved asset allowlist](../../README.md) for Android resource preparation.

No Android project or native performance measurement is provided by these studies. Offscreen rendering/test evidence is documented in the v3 specification.

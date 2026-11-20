# Smooth head and tail joins

Status: approved for the Android prototype on 2026-11-20. This is the current gameplay snake specification. The live [grid gameplay](../snake-grid-gameplay-prototype.html) is the current renderer and behavior reference. Android implementation is pending.

## Approved appearance

- The tail is a tapered extension of the same swept silhouette, using the same cross-sectional shading and markings as the body. No separate tail PNG is drawn, so there is no material or attachment boundary. Its tip is softly rounded.
- The original head PNG remains the face asset. Only its rear 42% receives a gradual transparency mask during extraction; the eyes, snout and tongue remain opaque and unchanged. The body extends under this feathered rear attachment. The source atlas itself is unchanged.
- Grid rules, interpolation, controls, body styling and head scale/anchors are unchanged. The renderer can extrapolate the short tail tip along the trail's first tangent where needed; this is visual geometry only.

The body is one smooth shaded surface with spaced organic spots, replacing the earlier tightly packed pods. A one-cell silhouette width is not required. Preserve the head's face, scale and anchoring while keeping the neck and tail transitions visually continuous.

## Porting contract

Source atlas: [snake_path_parts_v1.png](snake_path_parts_v1.png), 2172 × 724 RGBA. It contains head, body pod and tail artwork, but the final renderer **draws only the head region**. The body and tail are geometry. Superseded head artwork has been removed from the active folders.

Head preparation, once at load time:

1. Crop source region `(x=30, y=155, width=653, height=445)`.
2. Clear pixels with alpha below 70/255; determine content bounds from pixels above 150/255.
3. Crop to those visible bounds, then apply a horizontal alpha mask over the rear 42% of the right-facing cropped image. The mask stays transparent through 12% of that feather region and rises linearly to full opacity at its end. Preserve the remaining face alpha.
4. Rotate the prepared head with the route direction. In the grid prototype its height is `1.1 × bodyWidth` and its longitudinal anchor is 59% of cropped head width. Draw it over the underlying body.

Geometry baseline from the approved grid page (source-canvas units):

- Follow visited cell centers using an open rounded polyline. Quadratic bend radius is bounded by `min(0.36 × cell, 0.45 × incoming length, 0.45 × outgoing length)`.
- Sample cumulative arc distance, deriving local tangents/normals for both shading and markings. Interpolate head travel between logical ticks; ease orientation over the first 35% of a tick. Tail interpolation stops on growing ticks.
- Default body width is `0.7 × cell`, a tunable value. Sweep from half a body width behind the visual tail to `0.2 × bodyWidth` behind the head endpoint. Extrapolate the starting tangent for the short tip where needed.
- Taper radius over the first `1.5 × bodyWidth` using `radius = 0.5 × bodyWidth × sqrt(1 - (1 - t)²)`, where `t` is clamped progress through that distance. This tail is part of the body silhouette.
- Shade with continuous cross-section ribbons: the study uses 28 strips and roughly 2px longitudinal sampling. Clip alternating organic oval spots inside the silhouette, about `0.9 × bodyWidth` apart. See the live page's `smoothBody` function for exact shading and spot formulas.
- Use one underlying snake shadow. Avoid per-segment highlights/shadows that recreate visible rings or packed lobes. Cache reusable images and drawing resources; native sampling density should be profiled at device scale.

All of these are rendering operations. Collision, apple collection and growth remain based on integer grid cells. For the native sequence and verification criteria, see [the Android handoff](../../../android-prototype-handoff.md).

## History and verification

Prior segmented and smooth-body v2 pages are preserved in [the deprecated archive](../../../archive/README.md), alongside their earlier preview images. They are outside the active asset allowlist.

Inspected actual-code renders: `assembled-joins-straight-v3.png`, `assembled-joins-curve-v3.png`, `grid-mdas-joins-v3.png`. The saved MDAS snapshot uses `9 × 6 - 3 = ?`, answer 51. Offscreen controls/animation and 648 geometry combinations passed, along with the existing 18 gameplay checks and 1000 generated question checks. No native Android performance or browser screenshot claim is made.

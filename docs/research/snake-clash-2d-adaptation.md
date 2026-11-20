# Snake Clash! — proposed 2D rendering direction for Snake & Math

> Historical study proposal. The sprite-chain experiment was subsequently refined into the approved [smooth-joins v3](../assets/game/path-snake/smooth-joins-v3.md) continuous body/tail renderer. The source observations and limits below remain research context.

Research date: 2026-11-20. Scope: published visual evidence and native Kotlin adaptation. No prototype or art changes in this research task.

## Evidence and limits

Snake Clash! is listed by Supercent on Google Play. The publisher describes distinct character skins. Its supplied screenshots were inspected, including the dragon gameplay and the pig/zombie/donut promotional image. Visible traits include rounded volume, repeating body sections, characterful heads and strongly differentiated surface designs. These are observations of publisher-provided imagery, not a confirmed description of the implementation. [Google Play listing and screenshots](https://play.google.com/store/apps/details?id=io.supercent.linkedcubic).

The specified [YouTube Playables title](https://www.youtube.com/playables/UgkxEAXMNyB3s1vlIhf7swiCR-shfkYOw2wv) resolves as Snake Clash!, but the web reader exposes only page metadata/footer. Live interaction, movement timing and parity between that build and the mobile screenshots were not verified. Mobile promotional artwork is a design reference, not proof that every detail appears in the Playables build.

No first-party rendering source, asset pipeline description or technical explanation was found in the reviewed material. Do not assert that Snake Clash uses a particular mesh, skinned model, sprite chain, engine or shader based on screenshots or third-party engine detection. Apparent body segments can be produced with several technologies.

## Interpretation for this project's visual target

The useful target is a rounded, substantial character with repeated body forms that feel intentional. It need not look like an invisible join between flat grid tiles. Similar character volume can be suggested in 2D using edge shading, broad highlights, small contact shadows and consistent proportions. This is a design interpretation, not a fact about Supercent's renderer.

The earlier fourteen-piece atlas approach joins straight and corner pieces at cell boundaries. The proposed alternative places repeated overlapping body sprites along a common path. The art is oriented to the path, rather than selecting a separate illustration for each right-angle corner.

## Recommended next study: a 2D path-following sprite chain

This is our proposed adaptation, not a reconstruction of Snake Clash internals.

1. Maintain the game engine's ordered grid cells and four-direction swipe controls.
2. Derive a visual centerline from those cells, with controlled rounded bends. Avoid an unconstrained spline that overshoots into unrelated cells.
3. Interpolate visual travel along the route between logical ticks, keeping game rules authoritative. Interpolate distance and follow the same route through bends; independently sliding corner tiles is not enough.
4. Sample the centerline at consistent distance intervals. Use those positions and their local tangents to position and rotate the repeated body artwork.
5. Draw tailward samples first, then samples nearer the head, so the overlap direction is consistent.
6. Start with body-sample spacing about 20–35% of the visible body width, and tune from an assembled preview. This range is a prototype proposal, not an industry rule or a measured Snake Clash setting.
7. Use one coordinated head with a matching neck transition, overlapping the first body samples. Head silhouettes, shading and connection width must be designed with the body. A previous unrelated mascot cutout should not be reused without this adjustment.
8. Reduce widths over the tail length and finish with matching tail artwork or an integrated cap. Avoid an abrupt separate pointed piece with a transverse outline.

## What assets this would use

| Part | Proposed form | Why |
| --- | --- | --- |
| Head | Transparent, top-down 2D image with a defined neck anchor; directional variants if perspective needs them | Distinct personality without a mismatched upright mascot pose |
| Body | Rounded shaded overlap-friendly image; optional few marking variants | Repeats and rotates along the route, with no straight-versus-corner tile split |
| Tail | Matching tapered image or a controlled taper across the final body samples | Consistent ending without a patched-on seam |
| Decorations | Optional silhouette or surface accents with attachment anchors | Unique skin designs beyond recoloring |

A repeated body image must be designed for overlap: avoid a heavy complete border around every lobe, harsh repeated cast shadows or highlights that become a zipper pattern. Connected appearances still need actual scale and motion inspection.

Each skin can change head shape, body silhouette, spots/bands/scales, texture and tail treatment. Shared connection anchors and a maximum visual-width envelope preserve renderer compatibility. Keep the home mascot separate from this gameplay assembly.

## Kotlin / Compose feasibility

Compose Canvas can draw image assets at controlled sizes and positions; drawing transformations can rotate samples around local anchors. This provides the building blocks for the proposed chain while keeping native Kotlin and Compose. [Graphics overview](https://developer.android.com/develop/ui/compose/graphics/draw/overview), [DrawScope transformations](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/drawscope/package-summary).

For a procedural variant, Compose also supports brushed paths, configurable stroke caps/joins and path clipping. These are tools for geometry, highlights and masks, not a promise that a single global gradient yields cylindrical shading around every bend. [DrawScope](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/drawscope/DrawScope), [Stroke](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/drawscope/Stroke).

If repeating sprites cannot meet the desired smoothness, a textured ribbon is an alternative study. Compose exposes vertex positions and texture coordinates through `Vertices` for `Canvas.drawVertices`, but this requires constructing and testing mesh geometry/UVs; it is not a built-in Snake renderer. Check APIs against the project's eventual Compose version. [Vertices reference](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Vertices).

For the first study, use the sprite chain rather than adding mesh complexity. Profile draw count and bitmap memory at the planned longest snake on an Android device; performance is not yet measured.

## One-grid-cell requirement

One occupied logical cell should remain the width budget. This does not require one image per cell: several small visual samples can lie inside that budget along the centerline. Any extra eyes, fins or decorations must respect the chosen silhouette envelope, or their visual-only status must be explicitly accepted. Define how bends fit the union of occupied cells before claiming strict containment. Drawing and collision must continue to use a common grid transform.

## Review criteria

- One head/body/tail assembly in straight and curved poses, drawn from the actual exported assets.
- Smooth travel through at least two turns, with stable segment spacing, overlap order and markings.
- No exposed attachment outline, thick neck collar, tail cutoff or obvious gap.
- Check actual board scale as well as enlarged images; perceived volume should remain readable at small sizes.
- A genuinely distinct second skin, not only a tint, using the same path renderer.
- Stay with this project's Classic/MDAS rules: this research does not add free-roaming arena combat, joystick controls, opponents or online features.

This is a proposed third rendering study, distinct from the existing tile-sprite and fully procedural prototypes. Those files remain unchanged so they can still be compared.

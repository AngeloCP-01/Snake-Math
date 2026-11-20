# Snake & Math: rendering and skin asset research

> Historical research recommendation. The later approved renderer uses a continuous shaded body/tail and raster head; see [smooth-joins v3](../assets/game/path-snake/smooth-joins-v3.md) and [the Android handoff](../android-prototype-handoff.md). Technical comparisons below are retained for context.

Date: 2026-11-19. Status: research and recommendation, not implementation approval. No new art or gameplay code produced.

## Conclusion

For the approved illustrated, four-direction grid game, evaluate a **coordinated directional sprite set drawn inside Jetpack Compose Canvas**. This is a recommendation for this project's constraints, not a claim about what every commercial Snake game uses.

The previous recommendation to use only a head PNG was incomplete: it did not establish a matching body style or attachment contract. Canvas was not the problem by itself; the prototype used mismatched widths, outlines, attachment positions and shading. A sprite solution needs those same issues resolved explicitly.

## Verified implementation families

| Method | Primary evidence | Implication for this project |
| --- | --- | --- |
| Coordinated sprite pieces | Clear Code's Snake selects directional heads, tails, straight pieces and corners using adjacent cells. | Strong conceptual match for a four-direction illustrated grid snake. |
| Procedural shapes | A published Kotlin/Compose Snake draws body cells using `drawRoundRect`. | Confirms a simple shape-based renderer, not the quality of a richly illustrated character. |
| Textured mesh along a path | PixiJS documents a `MeshRope` with a snake texture and animated points. | A different route for flowing textured bodies; not a ready-made Kotlin/Compose component. |

Sources: [Clear Code source](https://github.com/clear-code-projects/Snake/blob/main/snake.py), [Kotlin source](https://gist.github.com/decodeandroid/09fe5e0c9bc1c7e8427d65b205af83c5), [PixiJS MeshRope](https://pixijs.download/v8.22.0/docs/scene.MeshRope.html). Additional source observations are in [snake-rendering-examples.md](snake-rendering-examples.md).

The examples establish available techniques, not market prevalence. There is no verified implementation behind the supplied reference mockup; a static illustration cannot establish how a working game renders its snake.

## Kotlin / Android: sprites still use Canvas

Compose's `DrawScope.drawImage` supports selecting a source region and drawing it into a destination rectangle. An atlas can therefore contain multiple snake parts. `DrawScope.rotate` provides rotation around a specified pivot. `drawPath` supports procedural geometry as an alternative. These capabilities let us retain the PRD's Kotlin, Compose and Canvas stack. [DrawScope reference](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/drawscope/DrawScope), [transformations reference](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/drawscope/package-summary), [graphics overview](https://developer.android.com/develop/ui/compose/graphics/draw/overview).

Proposed renderer: take the ordered grid positions from the game engine; classify each cell as head, tail, straight or turn; select the skin region; draw it into a common grid rectangle. Source-region metadata and attachment geometry belong to the renderer/skin, not movement or arithmetic rules. Collision continues to use grid cells, independent of decorative eyes or tongue.

Use one board Canvas rather than treating each body piece as a separately laid-out UI widget. Keep decoded skin images available while in use; avoid decoding in the draw loop. Cache reusable drawing resources rather than reallocating them every frame. Compose documents drawing caches and the separation of composition, layout and drawing; these are implementation guidelines, not measured performance claims for this game. [Graphics modifiers](https://developer.android.com/develop/ui/compose/graphics/draw/modifiers), [Compose phases](https://developer.android.com/develop/ui/compose/performance/phases).

Size runtime textures for their displayed use. The existing 1254-pixel head is an art master, not a justified runtime tile size. Preserve transparency for snake sprites and define source rectangles explicitly so transparent margins do not alter attachment positions. Final resource sizes should follow device-scale testing. Android warns about decoded bitmap memory and oversized source images. [Bitmap optimization](https://developer.android.com/develop/ui/compose/graphics/images/optimization).

## Proposed asset contract before generating more art

Baseline per skin: four directional head regions, four directional tail regions, two straight orientations, and four corner orientations. These may be packaged as individual PNGs or an atlas; packaging does not determine how the snake moves. Rotation can reduce authored artwork if the face perspective, markings and lighting remain convincing in every direction. For the current three-quarter face, do not assume rotating one head will look appropriate.

Author the pieces as a family with:

- One common logical tile size and scale. For an initial study, a 128 × 128 tile is a proposed working size, not a platform requirement.
- Identical body connection width and edge-center coordinates at each open connector.
- No end-cap outline across an open connector: the outside silhouette may be outlined, but internal seams must remain open.
- Matching fill colors, boundary gradients, and outline thickness at both sides of each joint.
- Explicit anchors and content bounds for any artwork extending beyond a cell; overhang does not change collision rules.
- A shading convention that works in all authored orientations, with consistent head/body/tail proportions.
- Atlas gutters/edge extrusion where sampling requires it; verify at actual draw scale rather than relying on arbitrary overlap to hide errors.

Generate concept art for appearance, then normalize the approved pieces to this contract in an editable asset workflow. Independent image generations are not reliable evidence of exact matching edges. Do not label a generated sheet production-ready until its actual regions have been assembled and inspected.

## Unique skins

Proposed `SnakeSkin` data should identify its atlas/regions, anchors, visual scale, and optional animation frames. Each skin can supply different face art, spots/bands/scales, texture, outline, head silhouette, tail silhouette and decorative accents. Shared grid-connection geometry preserves compatibility, while art inside and around that geometry can differ. A tint is optional, not the skin architecture.

Keep the home mascot separate from gameplay sprites. Its coiled pose and large presentation scale serve a different purpose from a moving, tile-sized head.

## Movement is a separate decision

A complete sprite sheet solves shape selection, not automatically smooth movement. A fixed-step grid presentation can swap regions at ticks; interpolated motion needs explicit treatment of changing corners and endpoint transitions. Simply sliding full corner tiles independently can introduce gaps or popping. Start by validating topology, then test a moving turn before committing to a smoothing technique. If smoothly deforming illustrated curves are essential and sprite transitions fail the visual target, evaluate a textured-body renderer separately rather than promising a four-piece atlas will solve it.

## Acceptance check for the next prototype

1. Straight runs in horizontal and vertical orientations.
2. All four corner shapes and every head/tail direction.
3. Short snake and long snake; no exposed end caps or inconsistent thickness.
4. Actual rendered board scale and an enlarged view, on light and dark backgrounds.
5. Growth and repeated movement through corners; no changing-width joins, gaps or pops.
6. A second genuinely different skin using the same renderer contract.

Produce the moving assembly from the actual exported pieces; a separately generated illustration is not verification. Approval of that study should precede final artwork for additional skins.

## PRD impact

This retains native Android, Kotlin, Compose Canvas, swipe controls and grid-based game logic. It proposes changing the renderer detail in PRD sections 2.4 and 3.7 from a procedural continuous body path to connected illustrated sprite regions. The visible requirement for a continuous-looking snake remains. The PRD has not been edited; renderer choice remains a proposal pending visual validation.

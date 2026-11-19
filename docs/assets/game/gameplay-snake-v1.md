# Gameplay snake: hybrid rendering proposal

Created `snake_head_right_v1.png` with built-in image generation, matching the approved mascot and gameplay mockup. Transparent head art only; no renderer implementation or assembled-snake validation in this change.

## Parts

- Head: transparent PNG, right-facing base orientation. Draw on top of body; rotate around the head cell center for other directions. The three-quarter face will rotate with the sprite; directional face variants can be considered after visual review.
- Body: continuous rounded Compose Canvas path through the snake's grid cell centers, with green outline, lime fill, highlight and darker green spots. Do not stamp disconnected circle sprites. Paint spots in local path coordinates and clip to body silhouette.
- Tail: taper the end of the same Canvas shape. No separate tail image required.

This follows the continuous-body requirement in PRD sections 2.4 and 3.7 while using raster head art for facial fidelity. It is a proposed hybrid rendering detail, not an already implemented feature.

The generated PNG contains transparent padding. Fit its visible alpha bounds to the intended head footprint rather than scaling the entire square to one cell. Draw the body beneath the short left neck stub, overlapping enough to hide its rear edge; exact scale, attachment anchor and color matching must be calibrated in an assembled preview. Tongue is decorative and should not enlarge collision geometry. Grid-based collision remains independent of rendered pixels. Check readability at actual board scale before shipping.

Place the approved file in the Android module's `res/drawable-nodpi` when integrating. Keep alpha and aspect ratio. No additional body or tail textures are necessary for this approach.

## Generation prompt

Use case: precise-object-edit. Create one production game sprite: ONLY the gameplay snake HEAD on true transparent alpha background. Image 1 is approved mascot identity reference; image 2 shows exact desired gameplay appearance and right-facing head. Preserve recognizable cute lime green head, big expressive glossy dark green eyes with white highlights, small rounded friendly snout, cream/yellow lower jaw, smiling face and tiny forked red tongue. Match the gameplay head from image 2, not a tall upright mascot. Right-facing horizontal travel direction. Slight overhead three-quarter 2D cartoon view as on the game board; two eyes visible. Compact head approximately one grid cell, slightly wider than the body. At LEFT rear include only a SHORT lime neck attachment stub centered horizontally on the travel axis, with clean rounded green rear silhouette designed to overlap a continuous green Canvas body behind it. No tall neck, coiled body, full snake, detached parts, additional sprites, typography, labels, border, grid, apple, foliage or external drop shadow. Entire sprite silhouette and tongue inside image with transparent margins. Single centered head on roughly square high resolution canvas, crisp anti-aliased edge, simple readable at 40–64 px displayed size, smooth shaded green style and dark green contour faithful to approved snake. Head points RIGHT, neck joins LEFT. Genuine transparent background, not checkerboard artwork.

# Snake & Math — game assets

Status: browser gameplay and final snake rendering approved on 2026-11-20. Android coding is deferred until the user resumes in Android Studio.

## Current gameplay references

- [Android handoff](../../android-prototype-handoff.md): start here for native work.
- [Grid gameplay prototype](snake-grid-gameplay-prototype.html) and [behavior notes](grid-gameplay-prototype.md): approved Classic/MDAS baseline.
- [Smooth-joins v3](path-snake/smooth-joins-v3.md): continuous shaded body/tail, sparse spots and rear-feathered head from `path-snake/snake_path_parts_v1.png`. Its body/tail image regions are unused.
- [Final MDAS still](path-snake/grid-mdas-joins-v3.png): actual-code visual comparison.

Use the [approved asset allowlist](../README.md) when preparing Android resources. Superseded tile, procedural-only and segmented studies have been moved into [the deprecated archive](../../archive/README.md), outside the active asset folders.

## Home mascot and background

| File | Dimensions | Format | Intended use |
| --- | --- | --- | --- |
| snake_mascot_v1.png | 1254 × 1254 | RGBA PNG, verified alpha range 0–255 | Static home-screen mascot and visual reference for gameplay character |
| forest_background_v1.png | 841 × 1870 | RGB PNG, opaque | Portrait full-screen forest backdrop with clear center |

Generated with the built-in image generation tool using ../mockups/home-v3.png as the reference. Snake retains approved reference character styling. These are review masters; they are not a rights clearance or original-character replacement.

## Android usage notes

Names use Android resource-compatible lowercase underscores. Once approved, copy to the Android module's res/drawable-nodpi directory (no Android module was modified in this task). Keep the snake PNG alpha; render with aspect ratio preserved. Background can fill the window with center crop; edge foliage may crop on different aspect ratios. Keep text, touch targets and the game grid in Compose, not baked into this image.

The mascot is a static coiled illustration, not an animated or modular gameplay sprite. The approved browser gameplay uses the head from the separate path-snake atlas and draws a continuous body/tail. Native rendering remains pending; see the current references above.

## Snake prompt

Use case: background-extraction. Input image is edit target: approved Snake & Math home screen. Extract ONLY the complete coiled lime green cartoon snake character into a production-quality standalone transparent PNG asset for a native Android game home screen. Preserve the approved character exactly: same upright neck, right-facing three-quarter smiling head, huge glossy dark green eyes with white highlights, lime face and rounded snout, open friendly mouth, red forked tongue, pale yellow striped belly, same darker green oval spots and broad coiled continuous body, same smooth shaded 2D cartoon rendering and dark green outlines. Complete silhouette centered, generous small transparent padding on all sides, entire tongue and coils visible. High resolution roughly 1536 square. Remove EVERYTHING else: no rocks, leaves, ground, shadows outside character, text, logo, buttons, device, background, border or watermark. True transparent alpha, including holes between neck and coils. Not a checkerboard illustration. Do not redesign snake, do not add anatomy or accessories. Single static home mascot asset, not sprite sheet.

## Background prompt

Use case: precise-object-edit. Input image is approved home-screen style reference. Prepare ONLY the in-app forest-green BACKGROUND as a standalone portrait raster asset for native Android Snake & Math game. Fill entire image with background artwork, approximately 1080x2400 portrait proportions. Match the approved screen's rich deep emerald/forest-green gently shaded background, softly layered dark botanical leaf silhouettes along upper sides, slightly brighter stylized lime/leaf-green foliage concentrated at bottom two corners and sparse side edges. Keep center 70 percent of width and upper/middle 80 percent of height calm, dark, low-detail and unobstructed so mascot, title, game board, and UI can be drawn on top later. Subtle soft illumination in center, dark corners, clean smooth 2D cartoon shading consistent with reference, no noisy texture. Small unobtrusive ground-like green shapes at bottom edge only. Remove ALL foreground content: no snake, no eyes, no apple, no stones in central area, no text/title, logo, buttons, trophy, panels, game grid, game board frame, status icons, phone/device, black rounded screen corners, outside blurred presentation backdrop or watermark. This is the actual edge-to-edge opaque in-app backdrop, not a screenshot or phone mockup. No fake transparency. Original palette and foliage feel preserved.


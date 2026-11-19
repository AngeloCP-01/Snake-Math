# Green snake sprite sheet — review draft v1

File: green_snake_sheet_draft_v1.png. Built-in image generation using the approved gameplay mockup and head as references. Verified 1254 × 1254 RGBA PNG with alpha range 0–255. Visual review only, not a normalized runtime atlas.

Four columns, left to right:

| Row | Column 1 | Column 2 | Column 3 | Column 4 |
| --- | --- | --- | --- | --- |
| 1 | Head right | Head down | Head left | Head up |
| 2 | Tail tip right | Tail tip down | Tail tip left | Tail tip up |
| 3 | Horizontal body | Vertical body | Left–bottom corner | Top–left corner |
| 4 | Top–right corner | Right–bottom corner | Empty | Empty |

All fourteen requested pieces are present. Generated connectors are inset and not aligned to exact tile boundaries; edge fringe/stray pixels and inconsistent connector shading remain. Do not use naive equal-cell crops as a verified seamless atlas. After art review: clean edges, normalize connector widths and anchors, export regions, and validate actual assembled pieces and moving turns. No runtime or assembly validation performed.

## Generation prompt

Create a GREEN SNAKE MODULAR GAME SPRITE SHEET draft for visual review. Input image 1 is gameplay art/style reference; input image 2 is approved face identity reference. Preserve cute glossy lime snake, dark green oval spots, cream-yellow lower jaw, huge expressive dark green eyes, friendly smile and tiny red forked tongue, clean shaded cartoon game rendering. True TRANSPARENT alpha background. Square high resolution sheet, precisely FOUR equal columns by FOUR equal rows, uniform cell sizing. Exactly fourteen isolated sprites, one per occupied cell, two empty cells. NO text, labels, grid lines, background, phone, UI, shadows outside sprites, full assembled snakes or extra objects. All pieces belong to ONE coordinated snake, matching body width, outline thickness and shading. Use subtle center highlights and darker edge shading that work on turns. Uniform body connection cross-section 44 percent of cell width, centered on connecting tile edges. Never draw a closing dark outline across an open connection: outlines only follow exposed outside silhouette. Parts should meet neighboring tiles cleanly without knobs, collars, rings, gaps or capped ends.
ROW 1: four HEAD sprites facing right, down, left, up in that order. Direction must be unmistakable. Preserve same friendly face from each appropriate view; not four identical heads. Heads roughly 65 percent cell wide, neck smoothly narrows to body connection width, neck extends to center of rear tile edge, no blunt capped neck, no disconnected collar. Tongue small, contained within sprite cell.
ROW 2: four TAIL sprites with tapered tips pointing right, down, left, up respectively. Thick OPEN body connection at opposite tile edge, same exact width as body, smoothly tapers to rounded pointed tail tip near forward half of cell. Continuous consistent spots and shading. No borders across open thick end.
ROW 3: horizontal STRAIGHT BODY connecting left/right edges; vertical STRAIGHT BODY connecting top/bottom edges; rounded CORNER connecting left and bottom edges; rounded CORNER connecting top and left edges. Body width same across all. No rounded caps at open connectors.
ROW 4: rounded CORNER connecting top and right edges; rounded CORNER connecting right and bottom edges; third cell EMPTY TRANSPARENT; fourth cell EMPTY TRANSPARENT. All corner silhouettes are smooth quarter turns of a thick continuous snake, no isolated discs. Spots must stay fully inside skin. Standardized modular game pieces, consistent visual scale, high readability at small Android board sizes. This is a review draft atlas, prioritize coordinated shape and joins while retaining reference character identity.


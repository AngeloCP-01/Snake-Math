# Snake rendering examples — primary-source research

Research date: 2026-11-19. Scope: actual rendering source and first-party graphics documentation. These examples demonstrate implementation families; they do not establish what proprietary Snake games use. No new game assets or app code were created.

## 1. Grid snake assembled from coordinated sprites

Clear Code's public Pygame Snake source loads four directional heads, four directional tails, two straight body pieces and four corner pieces. `draw_snake()` chooses straight/corner art using the two neighboring cells. Tail orientation depends on its adjacent body cell; head orientation depends on the neck. Each piece is drawn into the same cell-sized rectangle. This is concrete evidence of a 14-image directional set, not just a head with recolored body tiles. [Source: snake.py](https://github.com/clear-code-projects/Snake/blob/main/snake.py).

**Inference for Snake & Math:** a complete coordinated set fits this project's discrete four-direction board. Separate directional art is useful when rotating a three-quarter face would look wrong. A set can be bundled in one atlas or individual images; the atlas is packaging, not a different rendering technique. It will not automatically hide seams: edge positions, body widths, gradients, outlines, and transparent padding need matching specifications.

## 2. Kotlin/Compose examples can use procedural shapes

Arda Kazancı's published Kotlin example stores snake positions and draws each body element with Compose `Canvas` / `drawRect`. It also handles drag gestures. This directly demonstrates a shape-based native Compose route, but its simple rectangles do not validate polished illustrated rendering or a reusable skin system. Treat this as a small rendering example, not a recommended production engine. [Source: SnakeCompose.kt](https://gist.github.com/ardakazanci/9261953a1033553bd946838b92c61801).

**Inference for Snake & Math:** Canvas is the drawing surface and does not force the snake to use plain shapes. Distinct skins could use different geometry, marks and head artwork, but matching illustration shading procedurally requires deliberate art/rendering work. Our failed prototype is evidence of a poor implementation, not evidence that procedural rendering cannot work.

## 3. Continuous snake made from a textured deformable mesh

PixiJS's own working example loads `snake.png`, constructs a `MeshRope` using a series of points, and animates those points. This is an implemented snake animation technique: the artwork bends as a textured mesh rather than selecting grid corner sprites. [First-party example source](https://pixijs.com/8.x/examples/mesh-and-shaders/textured-mesh-basic/).

PixiJS's `MeshRope` documentation explicitly describes texture mapping along a point-defined path, configurable width, and stretching versus repeating textures. [First-party API](https://pixijs.download/v8.22.0/docs/scene.MeshRope.html).

**Inference for Snake & Math:** this is relevant when free-flowing movement or rich continuous patterns are essential. It is a graphics technique to evaluate, not a proposal to adopt PixiJS in a native Kotlin app. A Kotlin equivalent would require its own supported mesh/UV drawing implementation and bend testing. Repeating a body texture alone does not solve custom head and tail transitions.

## Recommendation for the approved illustrated grid style

Start with a **coordinated sprite set rendered inside Compose Canvas**, then evaluate a moving assembly at the real board scale. This recommendation is based on the project's needs, not a claim that every similar commercial game uses sprites.

Proposed full set per skin: 4 heads + 4 tails + 2 straights + 4 corners = 14 directional regions. A rotationally symmetric design can reduce authored regions to head, tail, straight, corner; the approved face and directional lighting make separate direction variants safer until reviewed. Add extra variants only where the design requires them.

Each skin should replace the full region set, not just a tint: its face, silhouette, body marks, texture and tail may change. Shared movement/collision logic remains independent of these assets. This is a proposed skin architecture, not a feature demonstrated by the cited Clear Code project.

Before generating final assets, define a common tile resolution, exact connection width and edge centers, transparent padding, outline rules at open connection edges, shading convention, and head/tail attachment geometry. Preview straight runs, every corner, every head/tail direction, a short snake, growth, and movement through a turn. Static attractive pieces are insufficient proof of a seamless moving character.

If the resulting sprite animation cannot meet the desired smoothness, compare against a textured continuous-body prototype before producing more skins. Keep the approved home mascot as separate promotional/home art; it need not be built from gameplay pieces.

## Limits

No verified source for the supplied reference illustration's underlying game renderer was available. Its attractive static mockup cannot prove it came from a working game or determine whether sprites, paths or meshes were used. No recommendation here depends on guessing a proprietary game's implementation.

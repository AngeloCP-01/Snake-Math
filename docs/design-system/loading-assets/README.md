# Loading review artwork

`mascot-head.png` is the head used by the current slithering loading review. It preserves the home mascot's large eyes, smile, cream muzzle, green spots and forked tongue. The loading body is drawn as deforming Canvas geometry, with a matching cream belly and tapered tail.

This head artwork was reused from the earlier mascot-style head study stored in `docs/archive/deprecated-design-assets-2026-11-20.zip`, entry `docs/assets/game/snake_head_right_v1.png`. The archived grid assembly remains superseded; this file is deliberately scoped to the newly requested **loading animation review**. It is not a replacement for the approved gameplay head or home mascot, and is outside the approved Android runtime asset allowlist until reviewed.

The source PNG is unchanged. At runtime the loading review draws region `(145, 265, 962, 728)`, feathers its rear 25% and anchors it at `(50, 410)` relative to that region. The continuous body extends beneath the feathered neck. See [the motion specification](../loading-animation.md).

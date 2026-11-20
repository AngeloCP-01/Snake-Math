# Snake Clash! — primary visual evidence

> Historical research context. The approved implementation target is [smooth-joins v3](../assets/game/path-snake/smooth-joins-v3.md). Use [the active asset allowlist](../assets/README.md); superseded art/prototypes are in [the deprecated archive](../archive/README.md).

Date: 2026-11-20. Research only; no assets or prototypes changed.

## Verified identity and scope

The Android store listing identifies **Snake Clash!**, package `io.supercent.linkedcubic`, as published by **Supercent, Inc.** Its description explicitly advertises different snake/worm skins. The listing is a primary source for the mobile game and its supplied screenshots; it does not establish that the mobile and YouTube Playables builds use identical assets or renderer versions. [Official Google Play listing](https://play.google.com/store/apps/details?id=io.supercent.linkedcubic).

Supercent's own website also presents Snake Clash as a publishing case study, but discusses progression and live events rather than rendering technology. [Supercent publishing page](https://supercent.io/publishing).

## What was visually inspected

Downloaded temporary copies of three screenshots linked directly from the official Google Play listing, and inspected them:

- **Dragon scene:** red/gold scale or plate forms repeat along a curved body; the broad characterful head and repeating body share material and outline treatment.
- **Rainbow scene:** cyan/violet body sections appear as a regular chain of thick shaded collar/block-like forms following the curve. The colorful head is larger and more expressive than one body section.
- **Large gold and blue snakes:** recurring body forms follow bends, with bright highlights, dark separation and patterned surfaces. Smaller snakes keep recognizable head silhouettes.

These are observations of supplied marketing screenshots, not proof of the live Playables build or internal code. The images convey raised, dimensional forms on a flat arena, with readable silhouettes, head emphasis and strong separation from the background. Their visual continuity comes from consistent scale/material and controlled repetition, rather than requiring every skin to appear as one perfectly smooth tube.

Direct primary screenshot URLs: [dragon](https://play-lh.googleusercontent.com/6yVqgBKdsOr5bd1Zsq56aLt5F1pGSePDg3UvJJ5qFF5ygJdcUdqNzGq5T6NtL769SLv0QSZGchgmnBSaBZni0A=w1000), [rainbow](https://play-lh.googleusercontent.com/pOx8BfDg_Vb9usEU5wOAPVz3JMwEMp7uZE6AV_JkVHNn6qxiuNmHGWWLPkdwVQlD9j9mBWJTDBcuR4gJZ_Vi7nA=w1000), [gold and blue](https://play-lh.googleusercontent.com/3Xq7OPcwZnk7ZSmTw_jrNMtshOWBVwcXvSGMe0uZiIKqjadcKLzO2qn5PGfgmT-M_sIon4P74p9Z-PhkGhfZ=w1000).

## What remains unknown

No first-party source code or technical article was found confirming whether Snake Clash uses separate meshes, a skinned/deformed mesh, sprites, pre-rendered art, or combinations. Perspective and shading alone do not prove the asset format. Third-party engine detection and similarly named web games are insufficient evidence for an authoritative implementation claim.

Supercent publishes its own game videos, useful as further visual references rather than rendering documentation: [Snake Clash Best Game](https://www.youtube.com/watch?v=CA0Iw_q-r_g) and [New Christmas Skin?](https://www.youtube.com/watch?v=daWBYNdbJjc). Those videos were located but not frame-analyzed in this research pass.

## Proposed 2D interpretation — inference, not a finding about Supercent's renderer

Use a coordinated head, a small family of overlapping shaded body sprites, and a tapered tail, placed by distance along a shared path and rotated to its local direction. This differs from selecting four right-angle corner tiles. Bodies can have intentional repeating beads, scales or plates, with a consistent overlap interval and tail-to-head draw order. The head/neck attachment still needs matching scale and geometry.

Original top-down artwork can provide the dimensional feel with painted highlights, darker sides and modest shadows, while remaining fully 2D. Each skin can replace its head silhouette, repeated body motif and tail design. Do not copy Supercent's specific dragon, branded heads or textures. This is an art/rendering proposal; the parent research covers Kotlin feasibility and the grid-movement constraint.

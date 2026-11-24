# Architecture diagram artifacts

Read [the architecture document](../architecture.md) for component contracts, decisions, navigation, state, timing and rendering diagrams. Its figures are now embedded PNGs, readable directly in Markdown preview; [editable sources, regeneration and verification](architecture-figures/README.md) accompany them. The diagrams describe proposed feature architecture; the current native foundation is recorded separately in the tracker.

- [Interactive component map](architecture.html): self-contained HTML; supports light/dark viewing, zoom, tracing and exports.
- [Editable specification](architecture.json): Archify architecture schema, version 1.
- [Browser preview contact sheet](architecture.visual-check.html): light/dark screenshots at the two endpoint viewport sizes.
- [Automated browser receipt](architecture.visual-check.json): measurements and artifact identity.

## Validation receipt

Generated with the installed archify skill on 2026-11-20. One focused label-placement correction round was needed before final delivery. The accepted specification/artifact were not modified after delivery.

| Field | Result |
|---|---|
| Diagram type | `architecture` |
| Specification bytes | 3,740 |
| Specification SHA-256 | `45f03924ac3e1c1da42eedff6f30b6bab5fb1f1a11d52a854ee846f7b97c8ed6` |
| HTML bytes | 805,428 |
| HTML SHA-256 | `0f5a083dac5f0b84d25a90bbc3c40ebeef6ba2df586ebe3cccf2b8ca98caceb8` |
| Deterministic validation | 9/9 showcase checks; zero errors, zero warnings |
| Automated browser evidence | Passed at 1440 × 900, 1600 × 1000, 1920 × 1080 and 2048 × 1320; no page overflow |
| Perceptual visual review | Passed: all four endpoint light/dark screenshots inspected for label/node fit, route clarity and balanced composition |
| Correction rounds | 1 |

The six former Mermaid blocks (now archived in [source-diagrams.json](architecture-figures/source-diagrams.json)) also passed parsing and SVG rendering with the locally cached Mermaid library in headless Chrome during the 2026-11-20 review: system interactions, navigation, domain model, session lifecycle, tick sequence and rendering flow. A separate Android builder review checked the architecture against the approved prototype; the interval planning, pause behavior, placement ownership and fairness-reset details are reflected in the final document. The Home panel decision was subsequently confirmed by the user and recorded in the architecture and screen notes. This is historical evidence; the new PNGs have their own linked verification record.

Automated browser checking initially could not launch Chrome inside the sandbox; the successful rerun used the same artifact outside the sandbox. The automated receipt correctly retains `visualReview: pending`: its scope is measurement and capture. The separate image-based review is recorded above rather than changing the machine receipt.

These checks validate diagram artifacts only. They establish no Android implementation, native test pass or game-performance result.

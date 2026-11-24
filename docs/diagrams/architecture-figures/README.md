# Embedded architecture figures

Created 2026-11-21 at Dev's request to make every diagram in [architecture.md](../../architecture.md) readable in Markdown preview without an external browser. The installed Diagram Design skill `2.6.168` supplied the design guidance, source checks, connector checks and standalone SVG exporter.

PNG is the document-facing format. HTML contains editable inline SVG, and standalone SVG exports are included for vector editing. All files use local system fonts and have no web-font, CDN or viewer requirement. The original six Mermaid blocks are preserved verbatim in [source-diagrams.json](source-diagrams.json) for content comparison, not as a viewing requirement.

This is a presentation change authorized directly by Dev. It does not change product rules, approve the remaining proposed architecture, or alter Android source/build configuration. The existing interactive Archify diagram remains an optional historical reference.

## Figure index and fidelity ledger

| Architecture section / source | Figures | Content retained |
|---|---|---|
| 3 — System map | [01-system.png](01-system.png) | All 7 components and 8 directed interactions |
| 5 — Navigation | [02-navigation.png](02-navigation.png) | All 7 states/destinations and 12 navigation paths; long events are explained by labels, node text and the footer |
| 6 — Domain model | [03-domain-model.png](03-domain-model.png) | All 5 conceptual types, 23 fields and 4 relationships; no cardinality or inheritance invented |
| 7 — Session lifecycle | [04-session-controls.png](04-session-controls.png), [05-session-outcomes.png](05-session-outcomes.png) | All 16 transitions, split into 8 control transitions and 8 outcome/disposal transitions; shared states repeat for context |
| 8 — Commands/ticks | [06-input-sequence.png](06-input-sequence.png), [07-tick-sequence.png](07-tick-sequence.png) | All 6 distinct participants, 11 messages in original order, both optional branches and the single-loop note; participants repeat where needed |
| 9 — Rendering | [08-rendering.png](08-rendering.png) | All 8 components and 9 connections; clock fraction remains visual only |

No source component, field, transition, guard or message was dropped. Layout, typography, line wrapping and abbreviated labels changed; lifecycle and sequence were split for readability. The conceptual class source uses field cards and directed references, avoiding additional UML semantics absent from the original.

Import settings: PNG + HTML + SVG; content-fit 960px-wide canvases, 2× PNG output; faithful detail; engineering audience. State control and terminal outcomes are separate views of the original dense state machine. The sequence split keeps each figure at five lifelines or fewer.

## Documentation skin

The local effective skin uses the project's forest/cream direction, adapted to a light document background. It is defined centrally in `build_diagrams.py`; the installed skill's global profile is unchanged. Dev explicitly authorized proceeding without diagram-creation approvals, so the first-run style and drawing confirmation pauses were not used.

| Role | Value |
|---|---|
| Paper / node surface | `#FCFAF1` / `#FFFFFF` |
| Ink / focal stroke | `#023E21` / `#155E2B` |
| Muted / structural border | `#47634F` / `#9BAA9E` |
| Focal tint | `#F0F6CF` |
| Titles and node names | Segoe UI, then Arial/system sans; 28px / 16px |
| Fields / technical labels | Consolas/system monospace for fields; 14px |
| Captions and connection labels | System sans; 12px |

These are documentation typography choices, not replacements for the app's Baloo 2/Nunito design system. Colors reinforce the diagram; labels, arrows and grouping carry the meaning.

## Rebuild and export

From the repository root, run the generator with an available Python 3 interpreter:

```text
python docs/diagrams/architecture-figures/build_diagrams.py
```

On Windows, regenerate all sources, check them, export SVG and capture PNG with already provisioned executables:

```powershell
./docs/diagrams/architecture-figures/render_figures.ps1 `
    -ChromePath '<installed Chromium executable>' `
    -PythonPath '<installed Python executable>' `
    -DiagramSkillPath '<installed diagram-design skill directory>'
```

The renderer also requires Node.js 22 or newer for its standard-library script. It installs no packages and makes no page-network requests. Browser automation runs headlessly; viewing the generated images does not launch a browser.

The Python Playwright package was unavailable, while a provisioned Chromium browser and Node.js were available. PNG export therefore uses Chromium's debugging pipe through `capture_figures.mjs`, rather than the skill's Python Playwright renderer. The packaged `self_check.py`, `verify_geometry.py` and `export_svg.py --system-fonts` are used directly. Host executable locations are passed at invocation, not committed as configuration.

The skill's bounded Mermaid extractor reported `mermaid_extract: malformed edge at line 55` for the system graph. It was not used to evaluate or render Mermaid. All figures were authored from the saved source labels and relationships and checked against those originals; the class grammar also received a direct field/relationship audit.

`--embed` on the generator is only for the initial replacement: it checks that all six source blocks still exactly match the archived originals before modifying the architecture document. Ordinary regeneration does not edit Markdown.

## Verification

- Packaged self-check: all 8 HTML figures passed.
- Packaged connector geometry check: all 8 figures passed, zero findings.
- Browser text measurement: all 8 figures contained every text bound within the viewBox; [render-check.json](render-check.json) records measured dimensions and footer text.
- PNG outputs: width 1920 pixels, height exactly twice the corresponding SVG viewBox height; complete figures include their footers.
- Visual review: all eight images inspected. Corrections addressed a crowded domain-reference label, labels masking connectors, an unbridged navigation crossing, and viewport capture clipping footer text. Final sources and exports were regenerated together.
- Markdown verification: all 8 images exist; the architecture contains no remaining Mermaid fences; relative file links and whitespace checked.

These checks validate documentation artifacts, not Android behavior or performance. No native build, device test or IDE-preview result is claimed.

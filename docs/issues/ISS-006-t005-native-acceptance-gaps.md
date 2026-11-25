# ISS-006 T-005 native acceptance gaps

**Found by:** Codex · **Date:** 2026-11-24 · **Status:** open

## Symptom

T-005's mode-entry implementation has API 34 evidence, but Studio sync/rendered
previews and broader device acceptance have not been established in this session.
The capture helper also restores a literal `null` font-scale value if the original
setting was unset; this deferred minor does not affect devices with a numeric setting.

## Cause

Verification coverage is bounded by the current execution environment. An unset
font-scale setting needs deletion rather than writing its textual representation.

## How to recognise it again

Check T-005's verification index for actual final receipts. A green layout test does
not establish Studio preview acceptance or supported physical orientation behavior.
For the helper edge case, `adb shell settings get system font_scale` prints `null`.

## Fix or workaround

Keep T-005 partial until its remaining acceptance is evidenced. Carry inherited API
23/API 36 and T-004 visual/Studio gaps forward separately. Use a numeric initial
font-scale setting when running the current capture helper; support for an unset
setting remains deferred. Do not close missing checks using browser evidence.

## Related

- [T-005](../requirements/T-005-play-mode-gameplay-flow.md)
- [Verification](../verification/T-005/README.md), [worklog](../worklog/T-005-2026-11-24.md)
- ISS-001, ISS-002 and the inherited T-004 handoff.

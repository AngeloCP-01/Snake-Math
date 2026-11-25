# T-005 read-only review and fix pass

2026-11-24. One skill-required reviewer context requested `gpt-6.2-sol`/high;
independent runtime ID unknown. Reviewed working diff and untracked sources against
T-005/P-005/DD-009–DD-011. No delegated implementation or second review.

| Finding | Response / evidence |
|---|---|
| Important: selection only distinguished by color | Added failing visible `Selected` assertion; `selection-cue-red.xml` is 2 tests / 1 expected failure. Added resource-backed visible label. Final connected result verifies it. |
| Important: process script reads selected on text node | Actual hierarchy confirmed `checked=true` on Hard's checkable ancestor, `selected=false` on text. Script now asserts checked ancestors, starting Hard/configuration, saved STOPPED Activity state and retained task before relaunch. |
| Important: placeholder layout / sibling assertions absent | Added both placeholder configurations at all six size/font profiles, full unclipped action containment, text fit, and Modes option/action sibling separation. |
| Important: repeated mixed Game returns absent | Added `repeatedMixedReturnsKeepFirstTargetAndOriginalHome`, including stale selection after a start in the same UI turn. |
| Minor: unset font-scale restore writes literal null | Deferred as ISS-006. Numeric initial font settings are supported; current emulator uses 1.0. |

The reviewer found no additional route/configuration/restoration design blocker on
inspection. Final tests and process/capture evidence remain the implementation's
verification responsibility; review alone does not establish Android acceptance.

Additional observed script issues: successful adb pull writes progress to stderr in
Windows PowerShell; wrapper now checks the exit code instead of terminating on that
progress. Backgrounding can leave an Activity PAUSED before STOPPED; the first Classic
attempt could not kill the process. The stricter rerun waits for saved STOPPED state
on the matching app/task record and uses the launcher intent consistently.

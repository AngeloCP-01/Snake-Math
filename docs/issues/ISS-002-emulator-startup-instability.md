# ISS-002 Emulator system services stall during cold boot

**Found by:** Codex · **Date:** 2026-11-21 · **Status:** accepted — local verification limitation

## Symptom

The read-only Pixel 6 Pro API 34 emulator used for T-001 took several minutes
to finish cold boot. The first launch screenshot was covered by a
“System UI isn't responding” dialog. The event log also contained ANRs for
Messaging, Bluetooth and Google Play services. It did not contain a Snake & Math
ANR or crash during the observed run.

## Cause

Unknown. The stalls occurred in emulator system services during cold boot while
the host was also running Android Studio and a first Android build. Host resource
contention was not measured and is not established as the cause.

## How to recognise it again

`sys.boot_completed` is initially empty, system-service `am_anr` events appear,
or the System UI dialog covers the test app despite `am start -W` returning
`Status: ok`. A PID or successful launch command alone misses this condition.

## Fix or workaround

Wait for boot readiness and choose Wait on the system dialog before checking the
app window. In this run the dialog cleared, the blank Activity became visible,
and its original process remained running. The screenshot was inspected, and
the app's AndroidRuntime error log and filtered ANR/crash events were empty.

The local, ignored evidence files are
`app/build/verification/T-001-launch-api34.png` (initial obstruction) and
`app/build/verification/T-001-observed-api34.png` (visible blank Activity).
They can be removed by cleaning build outputs. Repeat on a stable target if
the problem recurs; this run provides no gameplay or performance evidence.

## Related

- [T-001](../requirements/T-001-project-setup.md)
- [T-001 worklog](../worklog/T-001-2026-11-21.md)

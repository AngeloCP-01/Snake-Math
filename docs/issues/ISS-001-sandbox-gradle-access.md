# ISS-001 Sandboxed Gradle cannot compile catalog accessors

**Found by:** Codex · **Date:** 2026-11-21 · **Status:** accepted — environment limitation with verified workaround

## Symptom

During T-001, `gradlew.bat projects` failed before Android project configuration:
`GeneratedClassCompilationException: Unable to compile generated classes`.
The diagnostic `--stacktrace` run showed `java.nio.file.AccessDeniedException`
on `<GRADLE_USER_HOME>/wrapper/dists/gradle-9.6.0-bin/<cache-key>/gradle-9.6.0/lib/gradle-base-services-9.6.0.jar`
while `jdk.nio.zipfs.ZipFileSystem.close` released the compiler's JAR filesystem.

## Cause

The failure is specific to the restricted execution environment in this session.
The exact filesystem operation denied by the sandbox is unknown. The same
project and tool versions passed `projects` when run outside the sandbox using
a fresh single-use daemon. This does not establish a project or JDK defect.

## How to recognise it again

Gradle 9.6.0 on Studio JBR 25.0.4 fails generating version-catalog Java accessors,
with the nested `AccessDeniedException` pointing to Gradle's own JAR, before
application compilation. A successful wrapper `--version` does not exercise
this operation.

## Fix or workaround

Use the normal supported build environment, or request permission to run the
specific build command outside the sandbox. Use `--no-daemon` for the retry so
the build does not reuse a daemon started under the restricted environment.
Do not change tool versions or remove the catalog based on this symptom alone.

The outside-sandbox `projects --console=plain --no-daemon` retry passed and
reported exactly one application module. Remaining Android checks are recorded
in the worklog; this issue is not evidence of a passing app build by itself.

## Related

- [T-001](../requirements/T-001-project-setup.md)
- [DD-003 — Foundation build baseline](../decisions/DD-003-foundation-build-baseline.md)
- [T-001 worklog](../worklog/T-001-2026-11-21.md)

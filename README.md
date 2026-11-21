# Snake & Math

Native Android Snake game project using Kotlin and Jetpack Compose. The M1.2
setup hosts a plain `Snake & Math` / `Compose ready` marker in the production
Activity. The application theme, screens, engines, navigation and persistence
are later work.

Progress and verification evidence live in [the task tracker](docs/task-tracker.md)
and [T-002](docs/requirements/T-002-compose-configuration.md).

## Local setup

Use Android Studio compatible with Android Gradle Plugin 9.4.0, with:

- Gradle 9.6.0, selected by the checked-in wrapper.
- JDK 25 to run Gradle, matching this project's verified Studio JBR setup.
- Android SDK Platform 36 and SDK Build Tools 36.0.0.
- Android SDK Platform Tools and an emulator or development device for launch checks.

AGP provides built-in Kotlin support. The project does not apply a separate
Kotlin Android plugin. Gradle's embedded Kotlin version describes its build DSL;
the application compiler is resolved through AGP.

Open this repository root in Android Studio. Under **Settings > Build, Execution,
Deployment > Build Tools > Gradle**, use the wrapper and select the installed
JBR 25 as the Gradle JDK. Sync the project. Install the required SDK packages
through **Tools > SDK Manager** if missing, and accept their licenses.

Studio can write the SDK location to ignored `local.properties`. For shell builds,
set `JAVA_HOME` to the same JDK and `ANDROID_HOME` to your Android SDK. These are
local settings; do not commit installation paths. For example, in PowerShell,
replace the two placeholders with your actual installation directories:

```powershell
$env:JAVA_HOME = '<JDK 25 installation directory>'
$env:ANDROID_HOME = '<Android SDK directory>'
```

JDK 25 runs the build tools; Java and Kotlin compilation both target JVM 17.
The minimum supported Android API is 23; compile and target API are 36.
JVM bytecode targeting and Android API support are separate settings.

If Studio was already open before these build files were added, reopen the
repository so it imports the Gradle project. In this setup, reopening triggered
the first IDE sync. A plain directory project or file-indexing progress does not
establish a successful Gradle sync. Check the Build/Sync output for the result.

## Build and inspect

The wrapper downloads its pinned, checksummed Gradle distribution on first use.
AGP, Kotlin and other build dependencies also require initial downloads. Gradle
caches these under `GRADLE_USER_HOME` (normally `~/.gradle`) for subsequent builds
and other projects using those versions. No global Gradle installation is needed.

From the repository root on Windows:

```powershell
.\gradlew.bat --version
.\gradlew.bat projects
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug
.\gradlew.bat :app:assembleDebugAndroidTest
.\gradlew.bat :app:connectedDebugAndroidTest
.\gradlew.bat :app:dependencyInsight --dependency kotlin-stdlib --configuration debugRuntimeClasspath
.\gradlew.bat :app:buildEnvironment
```

On macOS/Linux, use `./gradlew` with the same arguments.

- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
- Lint report: `app/build/reports/lint-results-debug.html`

`MainActivityTest.launcherDisplaysComposeSetupMarker` launches the production
Activity and asserts that both marker strings are displayed. It never supplies
test-owned content. Connected tests require a booted emulator or device; set
`ANDROID_SERIAL` to select a target if several are connected. There are no unit
tests yet: `NO-SOURCE` means no unit tests ran and provides no gameplay coverage.

Compose uses the Kotlin-matched compiler plugin and BOM-managed UI/Foundation
libraries. Activity hosting and AndroidX Test versions are pinned separately.
Preview support is available at compile time; full tooling is debug-only.
See the [T-002 worklog](docs/worklog/T-002-2026-11-21.md) for actual versions,
compatibility corrections and verification results.

## Studio preview

Sync the Gradle project after dependency changes. Open
`app/src/main/java/com/acp/snakemath/app/SnakeMathApp.kt`, select **Split** or
**Design**, and build/refresh `SnakeMathAppPreview`. Both marker strings should
render without a repository, device or running game. Record successful Gradle
sync and visible preview separately; an APK build alone verifies neither.

Inspect the APK using the installed SDK tools:

```powershell
& "$env:ANDROID_HOME/build-tools/36.0.0/aapt2.exe" dump badging app/build/outputs/apk/debug/app-debug.apk
```

Expected identity: `com.acp.snakemath`, minimum SDK 23, target SDK 36 and launchable
Activity `com.acp.snakemath.app.MainActivity`.

## Launch verification

Start a development emulator or connect a device with USB debugging enabled.
List targets, then replace the serial placeholder with the selected target:

```powershell
& "$env:ANDROID_HOME/platform-tools/adb.exe" devices -l
$deviceSerial = '<selected emulator or device serial>'
& "$env:ANDROID_HOME/platform-tools/adb.exe" -s $deviceSerial install -r app/build/outputs/apk/debug/app-debug.apk
& "$env:ANDROID_HOME/platform-tools/adb.exe" -s $deviceSerial shell am start -W -n com.acp.snakemath/com.acp.snakemath.app.MainActivity
& "$env:ANDROID_HOME/platform-tools/adb.exe" -s $deviceSerial shell pidof com.acp.snakemath
```

Observe both marker strings on white, clear of system bars, without a startup
crash. Increase the device font size and verify both strings remain readable,
then restore the original setting. This is setup content, not an application
screen design. Record device/API, install and
launch results, and observed-window evidence separately from build and IDE sync.
An existing process alone does not verify the visible window.

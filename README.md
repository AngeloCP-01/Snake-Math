# Snake & Math

Native Android Snake game project using Kotlin and Jetpack Compose. The T-004
foundation hosts a responsive, non-scrolling Home with bundled fonts/artwork,
separate Classic/MDAS best-score labels and a tighter two-line title. PLAY and
Settings open temporary destinations with Back to Home. Production scores show
unavailable values until persistence is implemented. Engines, gameplay, mode
selection and operational settings remain later work.

Progress and verification evidence live in [the task tracker](docs/task-tracker.md)
and [T-004](docs/requirements/T-004-theme-navigation-home.md). See its
[verification index](docs/verification/T-004/README.md) for actual checks and
outstanding device/Studio coverage.

[P-005](docs/requirements/P-005-play-mode-gameplay-flow.md) was agreed by Dev on
2026-11-24 for the M1.7 navigation foundation. [T-005](docs/requirements/T-005-play-mode-gameplay-flow.md)
was separately approved on 2026-11-24 for `sol-medium`; implementation remains a later Session B.

For contributor placement and ownership, see the canonical [package map](docs/architecture.md#4-package-structure-and-construction).
Planned directories appear when approved work adds their first useful source file.

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

`MainActivityTest.launcherDisplaysHome` launches the production Activity and
checks PLAY plus both mode labels. Navigation tests check typed destinations,
Back and stale/repeated callbacks. Home tests inject score values and exercise
bounded layouts, full unclipped containment, text overflow and sibling separation;
negative fixtures check that the assertions reject clipping/overlap. Component
tests cover title spacing, action dimensions and rendered artwork details.
Connected tests require a booted emulator or device; set
`ANDROID_SERIAL` to select a target if several are connected. There are no unit
tests yet: `NO-SOURCE` means no unit tests ran and provides no gameplay coverage.

Compose uses the Kotlin-matched compiler plugin and BOM-managed UI/Foundation
libraries. Activity hosting and AndroidX Test versions are pinned separately.
Preview support is available at compile time; full tooling is debug-only.
See the [T-002 worklog](docs/worklog/T-002-2026-11-21.md) for the Compose baseline
and [T-004 worklog](docs/worklog/T-004-2026-11-22.md) for navigation/font integration
and current verification.

## Studio preview

Sync the Gradle project after dependency changes. Open
`app/src/main/java/com/acp/snakemath/feature/home/HomeScreen.kt`, select **Split**
or **Design**, and build/refresh `HomePreview`. It supplies sample scores and
phone/small-phone/tablet/enlarged-text configurations without live navigation,
repositories or a game loop. Component previews under `core/ui/components/`
cover the logo, PLAY, Settings pill, score panel states,
forest background and temporary destinations. Home's scoped type/dimension
adjustments live in `HomeScreen.kt`; title line spacing lives in `HomeLogo.kt`.
Record successful Gradle sync and visible preview separately; an APK build alone
verifies neither.

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

Observe Home's title, subtitle, mascot, PLAY, both labeled unavailable scores and
Settings together without scrolling, clear of system bars and without a startup
crash. Check PLAY/Settings and explicit/system Back; repeated actions must keep a
sensible stack. Increase font size and check all components remain fully visible,
then restore the original setting. Phone/tablet orientation, offline artwork/fonts
and the complete device coverage require separate observation. Record device/API, install and
launch results, and observed-window evidence separately from build and IDE sync.
An existing process alone does not verify the visible window.

param(
    [Parameter(Mandatory = $true)][string]$AdbPath,
    [Parameter(Mandatory = $true)][string]$Serial,
    [string]$OutputDirectory = "$PSScriptRoot/process-restart"
)
$ErrorActionPreference = 'Stop'
$packageName = 'com.acp.snakemath'
$componentName = "$packageName/.app.MainActivity"
New-Item -ItemType Directory -Force -Path $OutputDirectory | Out-Null
function Invoke-Adb([string[]]$Arguments) {
    # Windows PowerShell treats adb's successful progress on stderr as ErrorRecord.
    $ErrorActionPreference = 'Continue'
    $result = & $AdbPath -s $Serial @Arguments 2>&1
    if ($LASTEXITCODE -ne 0) { throw "adb failed: $Arguments / $result" }
    return ($result -join "`n")
}
function Read-Hierarchy {
    $null = Invoke-Adb @('shell', 'uiautomator', 'dump', '/sdcard/t005-window.xml')
    [xml](Invoke-Adb @('shell', 'cat', '/sdcard/t005-window.xml'))
}
function Find-Label([string]$Label) {
    $tree = Read-Hierarchy
    @($tree.SelectNodes('//node') | Where-Object { $_.text -eq $Label -or $_.'content-desc' -eq $Label }) | Select-Object -First 1
}
function Wait-Label([string]$Label) {
    for ($attempt = 0; $attempt -lt 12; $attempt++) {
        $node = Find-Label $Label
        if ($node) { return $node }
        Start-Sleep -Milliseconds 500
    }
    throw "Visible content did not become ready: $Label"
}
function Assert-Checked([string]$Label) {
    $node = Wait-Label $Label
    $radio = $node.SelectSingleNode("ancestor-or-self::node[@checkable='true'][1]")
    if (!$radio -or $radio.checked -ne 'true') { throw "Expected checked difficulty: $Label" }
}
function Tap-Label([string]$Label) {
    for ($attempt = 0; $attempt -lt 8; $attempt++) {
        $node = Find-Label $Label
        if ($node -and $node.bounds -match '\[(\d+),(\d+)\]\[(\d+),(\d+)\]') {
            $x = [int](($Matches[1] -as [int]) + ($Matches[3] -as [int])) / 2
            $y = [int](($Matches[2] -as [int]) + ($Matches[4] -as [int])) / 2
            $null = Invoke-Adb @('shell', 'input', 'tap', "$([int]$x)", "$([int]$y)")
            return
        }
        $size = Invoke-Adb @('shell', 'wm', 'size')
        $dimensions = [regex]::Matches($size, '(\d+)x(\d+)') | Select-Object -Last 1
        $midX = [int]([int]$dimensions.Groups[1].Value / 2)
        $screenHeight = [int]$dimensions.Groups[2].Value
        $fromY = [int]($screenHeight * .8); $toY = [int]($screenHeight * .3)
        if ($attempt -ge 4) { $swapY = $fromY; $fromY = $toY; $toY = $swapY }
        $null = Invoke-Adb @('shell', 'input', 'swipe', "$midX", "$fromY", "$midX", "$toY", '300')
    }
    throw "Could not reach $Label"
}
function Capture([string]$Name) {
    (Read-Hierarchy).Save((Join-Path $OutputDirectory "$Name.xml"))
    $null = Invoke-Adb @('shell', 'screencap', '-p', '/sdcard/t005-screen.png')
    $null = Invoke-Adb @('pull', '/sdcard/t005-screen.png', (Join-Path $OutputDirectory "$Name.png"))
}
function Task-Id {
    $activities = Invoke-Adb @('shell', 'dumpsys', 'activity', 'activities')
    $match = [regex]::Match($activities, 'ActivityRecord\{[^\r\n]*com\.acp\.snakemath/[^\r\n]* t(\d+)')
    if (!$match.Success) { throw 'Cannot establish existing task identity' }
    $match.Groups[1].Value
}
$receipts = @()
foreach ($caseName in @('modes-hard', 'classic-from-hard', 'mdas-hard')) {
    $null = Invoke-Adb @('shell', 'am', 'start', '-W', '-a', 'android.intent.action.MAIN', '-c', 'android.intent.category.LAUNCHER', '-f', '0x10200000', '-n', $componentName)
    if (!(Find-Label 'PLAY')) {
        if (Find-Label 'Home') { Tap-Label 'Home' } else { Tap-Label 'Back to Home' }
    }
    $null = Wait-Label 'PLAY'; Tap-Label 'PLAY'; Tap-Label 'Hard'; Assert-Checked 'Hard'
    if ($caseName -eq 'classic-from-hard') {
        Tap-Label 'PLAY CLASSIC'; $null = Wait-Label 'Gameplay is not available yet.'
        $null = Wait-Label 'CLASSIC APPLE'
    }
    if ($caseName -eq 'mdas-hard') {
        Tap-Label 'PLAY MDAS'; $null = Wait-Label 'Gameplay is not available yet.'
        $null = Wait-Label 'MDAS LEARNING'; $null = Wait-Label 'Hard'
    }
    Capture "$caseName-before"
    $oldProcess = Invoke-Adb @('shell', 'pidof', $packageName)
    if (!$oldProcess.Trim()) { throw 'No live process before backgrounding' }
    $oldTask = Task-Id
    $null = Invoke-Adb @('shell', 'input', 'keyevent', 'KEYCODE_HOME')
    for ($attempt = 0; $attempt -lt 20; $attempt++) {
        $savedState = Invoke-Adb @('shell', 'dumpsys', 'activity', 'activities')
        $recordPattern = '(?ms)^\s*\* Hist[^\r\n]*com\.acp\.snakemath/[^\r\n]* t' + $oldTask + '\}.*?(?=^\s*\* (?:Hist|Task)|\z)'
        $savedRecord = [regex]::Match($savedState, $recordPattern).Value
        if ($savedRecord -match 'mHaveState=true' -and $savedRecord -match 'state=STOPPED') { break }
        Start-Sleep -Milliseconds 500
    }
    $savedState | Set-Content (Join-Path $OutputDirectory "$caseName-background-activity.txt")
    if ($attempt -eq 20) { throw 'Stopped Activity with saved state not established; check unavailable' }
    $null = Invoke-Adb @('shell', 'am', 'kill', $packageName)
    for ($attempt = 0; $attempt -lt 20; $attempt++) {
        $processList = Invoke-Adb @('shell', 'ps', '-A')
        if ($processList -notmatch "\s$([regex]::Escape($packageName))\s*(?:`r?`n|$)") { break }
        Start-Sleep -Milliseconds 500
    }
    if ($attempt -eq 20) { throw 'Background process was not killed; check unavailable' }
    $savedTask = Task-Id
    if ($oldTask -ne $savedTask) { throw 'Saved task was removed; check unavailable' }
    $launch = Invoke-Adb @('shell', 'am', 'start', '-W', '-a', 'android.intent.action.MAIN', '-c', 'android.intent.category.LAUNCHER', '-f', '0x10200000', '-n', $componentName)
    $null = Wait-Label 'PLAY'
    $newProcess = Invoke-Adb @('shell', 'pidof', $packageName)
    $newTask = Task-Id
    if ($oldProcess -eq $newProcess -or $oldTask -ne $newTask) { throw 'New-process/same-task precondition failed' }
    Capture "$caseName-home"
    Tap-Label 'PLAY'
    Assert-Checked 'Easy'
    Capture "$caseName-easy"
    $receipts += [pscustomobject]@{ case = $caseName; oldPid = $oldProcess; newPid = $newProcess; task = $newTask; launch = $launch }
    $receipts | ConvertTo-Json -Depth 4 | Set-Content (Join-Path $OutputDirectory 'receipts.json')
    Tap-Label 'Back to Home'
}

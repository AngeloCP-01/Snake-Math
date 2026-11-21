param(
    [Parameter(Mandatory = $true)][string]$ChromePath,
    [Parameter(Mandatory = $true)][string]$PythonPath,
    [Parameter(Mandatory = $true)][string]$DiagramSkillPath
)
$ErrorActionPreference = 'Stop'

# The host supplies existing executables; this script installs no dependencies.
& $PythonPath (Join-Path $PSScriptRoot 'build_diagrams.py')
if ($LASTEXITCODE -ne 0) { throw 'Figure generation failed' }

foreach ($figure in Get-ChildItem -LiteralPath $PSScriptRoot -Filter '*.html' | Sort-Object Name) {
    & $PythonPath (Join-Path $DiagramSkillPath 'scripts/self_check.py') $figure.FullName
    if ($LASTEXITCODE -ne 0) { throw "Figure self-check failed: $($figure.Name)" }
    & $PythonPath (Join-Path $DiagramSkillPath 'scripts/verify_geometry.py') $figure.FullName
    if ($LASTEXITCODE -ne 0) { throw "Figure geometry check failed: $($figure.Name)" }
    & $PythonPath (Join-Path $DiagramSkillPath 'scripts/export_svg.py') $figure.FullName --system-fonts
    if ($LASTEXITCODE -ne 0) { throw "SVG export failed: $($figure.Name)" }

    Write-Output "Checked $($figure.BaseName) and exported SVG"
}
& node (Join-Path $PSScriptRoot 'capture_figures.mjs') $ChromePath
if ($LASTEXITCODE -ne 0) { throw 'PNG capture or rendered-text check failed' }

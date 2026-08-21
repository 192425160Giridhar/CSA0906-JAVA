Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "   Building SkyWing Flight Booking Executable Fat JAR    " -ForegroundColor Cyan
Write-Host "=========================================================" -ForegroundColor Cyan

$WorkspaceRoot = $PSScriptRoot
if (-not $WorkspaceRoot) { $WorkspaceRoot = Get-Location }

# 1. Locate javac and jar executables
$JavacCmd = $null
$JarCmd = $null

$cmdJavac = Get-Command javac -ErrorAction SilentlyContinue
if ($cmdJavac) { $JavacCmd = $cmdJavac.Source }

$cmdJar = Get-Command jar -ErrorAction SilentlyContinue
if ($cmdJar) { $JarCmd = $cmdJar.Source }

if (-not $JarCmd) {
    # Search common JDK directories
    $FoundJar = Get-ChildItem -Path "C:\Program Files\Java" -Filter "jar.exe" -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($FoundJar) {
        $JarCmd = $FoundJar.FullName
    }
}

if (-not $JavacCmd) {
    $FoundJavac = Get-ChildItem -Path "C:\Program Files\Java" -Filter "javac.exe" -Recurse -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($FoundJavac) {
        $JavacCmd = $FoundJavac.FullName
    }
}

if (-not $JavacCmd) {
    Write-Error "'javac' compiler not found! Please ensure JDK is installed."
    exit 1
}

if (-not $JarCmd) {
    Write-Error "'jar' tool not found! Please ensure JDK is installed."
    exit 1
}

Write-Host "Using compiler: $JavacCmd" -ForegroundColor DarkGray
Write-Host "Using jar tool: $JarCmd" -ForegroundColor DarkGray

# 2. Clean and create bin
$BinDir = Join-Path $WorkspaceRoot "bin"
if (Test-Path $BinDir) {
    Remove-Item -Recurse -Force $BinDir
}
New-Item -ItemType Directory -Path $BinDir | Out-Null

# 3. Gather java files
$javaFiles = Get-ChildItem -Path (Join-Path $WorkspaceRoot "src") -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }

Write-Host "[1/3] Compiling $($javaFiles.Count) Java source files..." -ForegroundColor Green
$libPath = Join-Path $WorkspaceRoot "lib\*"
& $JavacCmd -encoding UTF-8 -cp $libPath -d $BinDir $javaFiles
if ($LASTEXITCODE -ne 0) {
    Write-Error "Compilation failed."
    exit 1
}

# 4. Extract libraries into bin
Write-Host "[2/3] Extracting dependency libraries (MySQL Connector/J, FlatLaf)..." -ForegroundColor Green
$mysqlJar = Join-Path $WorkspaceRoot "lib\mysql-connector-j-8.4.0.jar"
$flatlafJar = Join-Path $WorkspaceRoot "lib\flatlaf-3.5.4.jar"

Push-Location $BinDir
& $JarCmd -xf $mysqlJar
& $JarCmd -xf $flatlafJar

# Remove signature files if any
Get-ChildItem -Path (Join-Path $BinDir "META-INF") -Filter "*.SF" -ErrorAction SilentlyContinue | Remove-Item -Force
Get-ChildItem -Path (Join-Path $BinDir "META-INF") -Filter "*.DSA" -ErrorAction SilentlyContinue | Remove-Item -Force
Get-ChildItem -Path (Join-Path $BinDir "META-INF") -Filter "*.RSA" -ErrorAction SilentlyContinue | Remove-Item -Force
Pop-Location

# 5. Create Manifest & Package JAR
Write-Host "[3/3] Packaging standalone executable FlightBookingSystem.jar..." -ForegroundColor Green
$ManifestPath = Join-Path $WorkspaceRoot "MANIFEST.MF"
@"
Main-Class: com.flightbooking.Main
Class-Path: .

"@ | Out-File -FilePath $ManifestPath -Encoding ASCII

$JarPath = Join-Path $WorkspaceRoot "FlightBookingSystem.jar"

& $JarCmd -cfm $JarPath $ManifestPath -C $BinDir .

if (Test-Path $ManifestPath) {
    Remove-Item -Force $ManifestPath
}

if (Test-Path $JarPath) {
    $JarSize = (Get-Item $JarPath).Length / 1MB
    Write-Host "=========================================================" -ForegroundColor Cyan
    Write-Host ("SUCCESS: Standalone executable Fat JAR created! ({0:N2} MB)" -f $JarSize) -ForegroundColor Green
    Write-Host "Output: $JarPath" -ForegroundColor Yellow
    Write-Host "=========================================================" -ForegroundColor Cyan
} else {
    Write-Error "Failed to package FlightBookingSystem.jar"
}

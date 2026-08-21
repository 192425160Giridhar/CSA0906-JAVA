@echo off
setlocal enabledelayedexpansion

echo =========================================================
echo    Building SkyWing Flight Booking Executable Fat JAR
echo =========================================================

REM Call PowerShell build script for reliable cross-version packaging
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0build.ps1"
if %ERRORLEVEL% neq 0 (
    echo Build failed with error code %ERRORLEVEL%
    pause
    exit /b %ERRORLEVEL%
)

pause

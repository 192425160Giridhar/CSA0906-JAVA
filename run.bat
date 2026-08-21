@echo off
title SkyWing Flight Booking Management System
echo Launching SkyWing Flight Booking System...

REM Check if FlightBookingSystem.jar exists, if not run build.bat
if not exist FlightBookingSystem.jar (
    echo Executable JAR not found. Building project now...
    call build.bat
)

REM Run the executable jar
java -jar FlightBookingSystem.jar %*
if %ERRORLEVEL% neq 0 (
    echo.
    echo Application exited with error code %ERRORLEVEL%.
    pause
)

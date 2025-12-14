@echo off
title GATLING TEST RUNNER - NHOM 27
cls

:: --- CAU HINH PACKAGE (Sua dong nay neu ten package cua ban khac) ---
set PACKAGE_NAME=electronicshop.simulations

:MENU
cls
echo =====================================================
echo    GATLING AUTOMATION MENU - ELECTRONICS SHOP
echo =====================================================
echo.
echo.   0. Exit 
echo.
echo [GUEST SCENARIOS]
echo    1. Guest - Smoke Test (Test nhanh 1 guest user)
echo    2. Guest - Load Test (Test on dinh voi 50 guest user)
echo    3. Guest - Stress Test (Test voi 500 guest user)
echo.
echo [BUYER SCENARIOS]
echo    4. Buyer - Smoke Test (Test Login va Order voi 1 user)
echo    5. Buyer - Load Test (Test Login va Order voi 50 user)
echo    6. Buyer - Stress Test (Test Login va Order voi 500 user)
echo.
echo [ADMIN SCENARIOS]
echo    7. Admin - Smoke Test 
echo    8. Admin - Volume Load Test 
echo.
echo [INTEGRATED SCENARIOS]
echo    9. Integrated - Smoke Test (Debug)
echo    10. Integrated - FULL LOAD TEST (Bao cao)
echo.
echo =====================================================
set /p opt=">>> Lua chon (0-10): "

:: --- ANH XA LUA CHON ---
if "%opt%"=="0" exit
if "%opt%"=="1" set CLASS_NAME=guest.Guest_01_Smoke
if "%opt%"=="2" set CLASS_NAME=guest.Guest_02_LoadTest
if "%opt%"=="3" set CLASS_NAME=guest.Guest_03_StressTest
if "%opt%"=="4" set CLASS_NAME=buyer.Buyer_01_SmokeTest
if "%opt%"=="5" set CLASS_NAME=buyer.Buyer_02_LoadTest
if "%opt%"=="6" set CLASS_NAME=buyer.Buyer_03_StressTest
if "%opt%"=="7" set CLASS_NAME=admin.Admin_01_SmokeTest
if "%opt%"=="8" set CLASS_NAME=admin.Admin_02_LoadTest
if "%opt%"=="9" set CLASS_NAME=integrated.Integrated_01_SmokeTest
if "%opt%"=="10" set CLASS_NAME=integrated.Integrated_02_LoadTest

:: --- KIEM TRA HOPLE ---
if "%CLASS_NAME%"=="" (
    echo.
    echo [LOI] Lua chon khong hop le! Vui long chon lai.
    timeout /t 2 >nul
    goto MENU
)

:: --- THUC THI LENH MAVEN ---
cls
echo.
echo [DANG CHAY] %PACKAGE_NAME%.%CLASS_NAME% ...
echo -----------------------------------------------------
call .\mvnw.cmd gatling:test "-Dgatling.simulationClass=%PACKAGE_NAME%.%CLASS_NAME%"

echo.
echo -----------------------------------------------------
echo [HOAN TAT] Kiem tra ket qua o tren hoac thu muc target/gatling
echo.
pause
set CLASS_NAME=
goto MENU
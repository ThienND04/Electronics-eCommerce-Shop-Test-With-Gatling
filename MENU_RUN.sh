#!/bin/bash

# --- CAU HINH PACKAGE ---
# Sửa dòng này nếu package của bạn khác
PACKAGE_NAME="electronicshop.simulations"

while true; do
    clear
    echo "====================================================="
    echo "    GATLING AUTOMATION MENU - ELECTRONICS SHOP (LINUX)"
    echo "====================================================="
    echo ""
    echo "    0. Exit"
    echo ""
    echo "[GUEST SCENARIOS]"
    echo "    1. Guest - Smoke Test (Test nhanh 1 guest user)"
    echo "    2. Guest - Load Test (Test on dinh voi 50 guest user)"
    echo "    3. Guest - Stress Test (Test voi 500 guest user)"
    echo ""
    echo "[BUYER SCENARIOS]"
    echo "    4. Buyer - Smoke Test (Test Login va Order voi 1 user)"
    echo "    5. Buyer - Load Test (Test Login va Order voi 50 user)"
    echo "    6. Buyer - Stress Test (Test Login va Order voi 500 user)"
    echo ""
    echo "[ADMIN SCENARIOS]"
    echo "    7. Admin - Smoke Test"
    echo "    8. Admin - Volume Load Test"
    echo ""
    echo "[INTEGRATED SCENARIOS]"
    echo "    9. Integrated - Smoke Test (Debug)"
    echo "    10. Integrated - FULL LOAD TEST (Bao cao)"
    echo ""
    echo "====================================================="
    read -p ">>> Lua chon (0-10): " opt

    CLASS_NAME=""

    case $opt in
        0) echo "Goodbye!"; exit 0 ;;
        1) CLASS_NAME="guest.Guest_01_Smoke" ;;
        2) CLASS_NAME="guest.Guest_02_LoadTest" ;;
        3) CLASS_NAME="guest.Guest_03_StressTest" ;;
        4) CLASS_NAME="buyer.Buyer_01_SmokeTest" ;;
        5) CLASS_NAME="buyer.Buyer_02_LoadTest" ;;
        6) CLASS_NAME="buyer.Buyer_03_StressTest" ;;
        7) CLASS_NAME="admin.Admin_01_SmokeTest" ;;
        8) CLASS_NAME="admin.Admin_02_LoadTest" ;;
        9) CLASS_NAME="integrated.Integrated_01_SmokeTest" ;;
        10) CLASS_NAME="integrated.Integrated_02_LoadTest" ;;
        *) 
            echo ""
            echo "[LOI] Lua chon khong hop le! Vui long chon lai."
            sleep 2
            continue
            ;;
    esac

    # --- THUC THI LENH MAVEN ---
    clear
    echo ""
    echo "[DANG CHAY] $PACKAGE_NAME.$CLASS_NAME ..."
    echo "-----------------------------------------------------"
    
    # Lưu ý: Trên Linux dùng ./mvnw thay vì mvnw.cmd
    # Đảm bảo file mvnw đã được cấp quyền thực thi (chmod +x mvnw)
    ./mvnw gatling:test "-Dgatling.simulationClass=$PACKAGE_NAME.$CLASS_NAME"

    echo ""
    echo "-----------------------------------------------------"
    echo "[HOAN TAT] Kiem tra ket qua o tren hoac thu muc target/gatling"
    echo ""
    read -n 1 -s -r -p "Nhan phim bat ky de quay lai menu..."
done
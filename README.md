# Báo Cáo Kiểm Thử Phần Mềm - Electronics eCommerce Shop

Dự án này chứa các kịch bản kiểm thử (Test Scenarios) và tài liệu liên quan để đánh giá chất lượng, hiệu năng của hệ thống **Electronics eCommerce Shop**.

## 👥 Thành Viên Nhóm

| STT | Họ và Tên | Mã Sinh Viên | Vai trò |
| :--- | :--- | :--- | :--- |
| 1 | **Nguyễn Đức Thiện** | 22021164 | Thành viên  / ... |
| 2 | **Mai Hoàng Bách** | 22021224  | Thành viên / ... |
| 3 | **Vy Anh Dũng** | 22021179  | Thành viên / ... |

---

## 1. Giới Thiệu Dự Án

### Hệ thống cần kiểm thử (System Under Test - SUT)
Chúng tôi thực hiện kiểm thử trên dự án mã nguồn mở **Electronics-eCommerce-Shop-With-Admin-Dashboard-NextJS-NodeJS**.
* **Repo gốc:** [GitHub - Kuzma02](https://github.com/Kuzma02/Electronics-eCommerce-Shop-With-Admin-Dashboard-NextJS-NodeJS)
* **Công nghệ SUT:** Next.js (Frontend), Node.js & Express (Backend), MongoDB.

### Mục tiêu kiểm thử
Dự án này tập trung vào mục tiêu sau:
1.  **Kiểm thử hiệu năng (Performance Testing):** Đánh giá khả năng chịu tải, thời gian phản hồi của server dưới áp lực truy cập lớn (sử dụng Gatling).

---

## 2. Yêu Cầu Tiên Quyết 

Để chạy được dự án và các bài test, máy tính của bạn cần cài đặt:

* **Node.js**: Phiên bản 16.x hoặc 18.x trở lên.
* **Git**: Để clone source code.
* **MongoDB**: Cần có connection string (local hoặc Atlas) để chạy Website.
* **Java JDK** (Nếu dùng Gatling/JMeter): Phiên bản JDK 11 hoặc 17.
* **Maven/Gradle** (Nếu dùng Gatling bản Java/Scala).

---

## 3. Cài Đặt & Cấu Hình

1. Cài đặt và chạy website

- Tham khảo: https://github.com/Kuzma02/Electronics-eCommerce-Shop-With-Admin-Dashboard-NextJS-NodeJS

2.  Clone repo kiểm thử này về máy:

    ```bash
    git clone https://github.com/ThienND04/Electronics-eCommerce-Shop-Test-With-Gatling.git
    cd Electronics-eCommerce-Shop-Test-With-Gatling
    ```

---

## 4. Hướng dẫn chạy test

Dự án bao gồm 4 kịch bản kiểm thử hiệu năng chính, tương ứng với các hành vi người dùng khác nhau trên hệ thống.

### Mô Tả Các Kịch Bản (Scenarios)

1.  **Guest Simulation**:
    * **Mục tiêu:** Kiểm tra hiệu năng hệ thống với các tác vụ read-only (chỉ đọc).
    * **Hành vi:** Truy cập trang chủ, xem danh sách sản phẩm, tìm sản phẩm, xem chi tiết sản phẩm.
    
2.  **Buyer Simulation**:
    * **Mục tiêu:** Kiểm tra quy trình nghiệp vụ mua hàng quan trọng.
    * **Hành vi:** Đăng nhập, Thêm sản phẩm vào giỏ hàng (Add to cart), Tiến hành thanh toán (Checkout).

3.  **Admin Simulation**:
    * **Mục tiêu:** Kiểm tra tải của trang quản trị (Dashboard).
    * **Hành vi:** Đăng nhập Admin, xem Dashboard thống kê, quản lý các sản phẩm, Quản lý các đơn hàng, xem chi tiết đơn hàng, quản lý danh sách người dùng.

4.  **Integrated Simulation**:
    * **Mục tiêu:** Load test toàn hệ thống.
    * **Hành vi:** Chạy đồng thời Guest, Buyer và Admin với tỉ lệ phân bổ traffic thực tế (Ví dụ: 70% Guest, 20% Buyer, 10% Admin) để mô phỏng môi trường thực.

#### Trên Windows

1.  Mở Command Prompt (CMD) hoặc PowerShell tại thư mục gốc của project test.
2.  Chạy lệnh sau:
    ```powershell
    .\MENU_RUN.bat
    ```

#### Trên Linux / macOS

1.  Mở Terminal tại thư mục gốc.
2.  Cấp quyền thực thi (nếu cần) và chạy lệnh:
    ```bash
    chmod +x MENU_RUN.sh
    ```

Sau khi chạy các menu trên, sẽ hiện lên giao diện để chạy các test có dạng như sau: 
    
    ```bash
    GATLING AUTOMATION MENU - ELECTRONICS SHOP
    =====================================================

    0. Exit

    [GUEST SCENARIOS]
    1. Guest - Smoke Test (Test nhanh 1 guest user)
    2. Guest - Load Test (Test on dinh voi 50 guest user)
    3. Guest - Stress Test (Test voi 500 guest user)

    [BUYER SCENARIOS]
    4. Buyer - Smoke Test (Test Login va Order voi 1 user)
    5. Buyer - Load Test (Test Login va Order voi 50 user)
    6. Buyer - Stress Test (Test Login va Order voi 500 user)

    [ADMIN SCENARIOS]
    7. Admin - Smoke Test
    8. Admin - Volume Load Test

    [INTEGRATED SCENARIOS]
    9. Integrated - Smoke Test (Debug)
    10. Integrated - FULL LOAD TEST (Bao cao)

    =====================================================
    >>> Lua chon (0-10):
    ```

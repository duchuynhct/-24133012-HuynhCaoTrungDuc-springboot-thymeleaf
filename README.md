# BÀI TẬP LẬP TRÌNH WEB - SPRING BOOT THYMELEAF CRUD CATEGORY
**TRƯỜNG ĐẠI HỌC CÔNG NGHỆ KỸ THUẬT TP.HCM (HCM-UTE)**  
**KHOA CÔNG NGHỆ THÔNG TIN - BỘ MÔN LẬP TRÌNH WEB**

---

## 📌 THÔNG TIN SINH VIÊN & BÀI TẬP
* **Họ và tên sinh viên:** Huỳnh Cao Trung Đức
* **Mã số sinh viên (MSSV):** 24133012
* **Trường:** Trường Đại học Công nghệ Kỹ thuật TP.HCM (HCM-UTE)
* **Khoa:** Công nghệ Thông tin
* **Bộ môn:** Lập trình Web
* **Đề tài:** Xây Dựng Ứng Dụng Quản Trị Danh Mục (Category CRUD) kết hợp Tìm Kiếm & Phân Trang sử dụng Spring Boot, Thymeleaf, Thymeleaf Layout Dialect, Microsoft SQL Server và Bootstrap 5.
* **Repository GitHub:** [https://github.com/duchuynhct/-24133012-HuynhCaoTrungDuc-springboot-thymeleaf](https://github.com/duchuynhct/-24133012-HuynhCaoTrungDuc-springboot-thymeleaf)

---

## 🚀 CÔNG NGHỆ SỬ DỤNG (TECH STACK)

| Thành Phần | Công Nghệ / Thư Viện | Phiên Bản | Ghi Chú |
| :--- | :--- | :--- | :--- |
| **Ngôn ngữ** | Java Development Kit (JDK) | **25.0.2** | Oracle OpenJDK 25 |
| **Framework** | Spring Boot | **4.1.1 / 3.x** | Spring Web MVC, Spring Data JPA |
| **Web Server** | **Apache Tomcat (nhúng)** | **11.0.24** | Chuẩn Jakarta EE 11 / Servlet 6.1 |
| **View Engine** | **Thymeleaf** | **3.x** | Render HTML chuẩn phía máy chủ |
| **Layout Decorator** | **Thymeleaf Layout Dialect** | **3.x** | Quản lý Master Layout (`layout:decorate`, `layout:fragment`) |
| **Cơ sở dữ liệu** | Microsoft SQL Server | 2019 / 2022 | Database độc lập `SpringBootThymeleafDB` |
| **JDBC Driver** | `mssql-jdbc` | 13.x | Kết nối an toàn qua JDBC |
| **Frontend UI** | Bootstrap & Bootstrap Icons | **5.3.3 / 1.11.3** | Giao diện hiện đại, Responsive toàn diện |
| **Tiện ích Java** | Project Lombok | - | Giảm thiểu mã lặp (`@Data`, `@RequiredArgsConstructor`) |
| **Build Tool** | Apache Maven | **3.9.16** | Tích hợp sẵn Maven Wrapper (`./mvnw`) |
| **Quy trình Git** | Git Flow & Pull Requests | - | Phân tách Feature Branches và tạo PR theo từng giai đoạn |

---

## 🏛️ KIẾN TRÚC HỆ THỐNG (LAYERED ARCHITECTURE)

Dự án tuân thủ nghiêm ngặt mô hình kiến trúc đa tầng (Layered Architecture):

```
vn.trungduc.springboot_admin_crud
├── config
│   └── WebMvcConfig.java                   # Cấu hình phục vụ file tải lên tĩnh (/uploads/**)
├── controller
│   ├── AdminHomeController.java            # Trang chủ Dashboard & Xử lý upload ảnh đại diện
│   └── CategoryController.java             # Điều hướng CRUD, tìm kiếm, phân trang Category
├── entity
│   └── Category.java                       # Thực thể ánh xạ bảng categories trong SQL Server
├── repository
│   └── CategoryRepository.java             # Spring Data JPA Repository (tìm kiếm không phân biệt hoa/thường)
├── service
│   ├── ICategoryService.java               # Interface nghiệp vụ Category
│   └── impl
│       └── CategoryServiceImpl.java       # Hiện thực hóa các logic nghiệp vụ
└── SpringbootAdminCrudApplication.java     # Lớp khởi chạy ứng dụng & Đăng ký LayoutDialect bean
```

**Cấu trúc Giao diện Web Thymeleaf (`src/main/resources`):**
```
src/main/resources
├── static
│   └── images
│       └── default-avatar.svg              # Ảnh đại diện mặc định dạng vector SVG
├── templates
│   ├── layout
│   │   └── admin.html                      # Master Layout (Header có Avatar, Sidebar, Footer HCM-UTE)
│   └── admin
│       ├── home.html                       # Trang tổng quan Admin Dashboard
│       └── category
│           ├── form.html                   # Form Thêm mới / Chỉnh sửa Category (Live Icon Preview)
│           └── list.html                   # Danh sách Category (Tìm kiếm, Sắp xếp 2 chiều, Phân trang)
└── application.properties                  # Cấu hình SQL Server, Hibernate, Thymeleaf & Multipart Upload
```

---

## 🌟 CÁC TÍNH NĂNG NỔI BẬT

### 1. Bố Cục Trang Quản Trị Chuẩn Master Layout (Thymeleaf Layout Dialect)
- **Header:**
  - Thanh điều hướng trên cùng với logo Admin Dashboard và nhãn `Thymeleaf`.
  - Hiển thị **ảnh đại diện sinh viên** dạng hình tròn có viền trắng nổi bật kèm thông tin **Huỳnh Cao Trung Đức (MSSV: 24133012)**.
  - **Tính năng Tải ảnh cá nhân thật từ máy tính:** Người dùng nhấn vào Avatar trên Header hoặc nút *"Đổi ảnh đại diện"* ở Sidebar để mở Modal tải ảnh từ máy tính cá nhân (JPG, PNG, WEBP). Có tính năng **Instant Preview** bằng `FileReader` xem trước ảnh trước khi bấm lưu.
- **Sidebar:**
  - Menu điều hướng trực quan gồm Trang chủ (`/admin/home`) và Quản lý Category (`/admin/categories`).
  - Tự động nhận diện URL hiện tại để kích hoạt class `active` làm nổi bật menu.
- **Content Fragment:**
  - Sử dụng thẻ `<div layout:fragment="content">` để tiêm động nội dung của các trang con mà không cần lặp lại mã nguồn Header, Sidebar, Footer.
- **Footer:**
  - Chân trang cố định chứa đầy đủ thông tin:
    - **Họ & Tên sinh viên:** Huỳnh Cao Trung Đức &mdash; **MSSV:** 24133012
    - **Trường Đại học Công nghệ Kỹ thuật TP.HCM (HCM-UTE)** &bull; Khoa Công nghệ Thông tin &bull; Môn học: Lập trình Web.

### 2. Quản Lý Danh Mục (Category Management - `/admin/categories`)
- **CRUD Đầy Đủ:**
  - **Xem danh sách (Read):** Hiển thị bảng dữ liệu Bootstrap 5 hiện đại với STT tăng dần theo từng trang, ID, Tên danh mục, Biểu tượng, Trạng thái (Hoạt động / Khóa) và Thao tác.
  - **Thêm mới (Create):** Kiểm tra bắt buộc nhập tên danh mục, hỗ trợ chọn icon và trạng thái.
  - **Chỉnh sửa (Update):** Nạp dữ liệu cũ theo ID vào form thông qua `th:object`, bảo lưu ID dạng hidden input.
  - **Xóa (Delete):** Xóa bản ghi kèm hộp thoại xác nhận JavaScript confirm an toàn: `Bạn có chắc chắn muốn xóa danh mục: [...] không?`.
- **Tìm kiếm thông minh (Case-Insensitive Search):**
  - Tìm kiếm theo tên danh mục không phân biệt chữ hoa hay chữ thường (`findByCategoryNameContainingIgnoreCase`).
  - Nút *"Đặt lại"* tiện lợi hiển thị khi có từ khóa để quay về danh sách gốc.
- **Phân trang & Sắp xếp linh hoạt:**
  - Lựa chọn kích thước hiển thị: **5, 10, 20 dòng/trang** (tự động submit form khi thay đổi).
  - Sắp xếp tăng dần / giảm dần theo cột ID và Tên Danh Mục khi nhấp vào tiêu đề cột.
  - **Bảo lưu trạng thái:** Giữ nguyên từ khóa tìm kiếm và kích thước trang khi chuyển qua lại giữa các trang hay khi đổi chiều sắp xếp.
- **Live Icon Preview:**
  - Khi nhập mã class Bootstrap Icon (ví dụ: `bi-phone`, `bi-laptop`, `bi-headphones`, `bi-camera`, `bi-bag`), biểu tượng xem trước bên cạnh ô nhập sẽ thay đổi tức thì giúp trực quan hóa icon danh mục.

---

## 🗄️ HƯỚNG DẪN KHỞI TẠO CƠ SỞ DỮ LIỆU SQL SERVER

Hệ thống sử dụng cơ sở dữ liệu riêng biệt **`SpringBootThymeleafDB`**. Bạn mở **SQL Server Management Studio (SSMS)**, copy toàn bộ script dưới đây và nhấn **Execute (F5)**:

```sql
-- 1. Tạo Database mới dành riêng cho dự án Thymeleaf
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'SpringBootThymeleafDB')
BEGIN
    CREATE DATABASE SpringBootThymeleafDB;
END
GO

USE SpringBootThymeleafDB;
GO

-- 2. Tạo bảng categories nếu chưa có
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'categories')
BEGIN
    CREATE TABLE categories (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        category_name NVARCHAR(255) NOT NULL,
        icon NVARCHAR(255),
        status BIT DEFAULT 1
    );
END
GO

-- 3. Chèn sẵn 15 danh mục mẫu tiếng Việt phong phú để test phân trang và tìm kiếm
IF NOT EXISTS (SELECT 1 FROM categories)
BEGIN
    INSERT INTO categories (category_name, icon, status) VALUES 
    (N'Điện Thoại & Smartphone', 'bi-phone', 1),
    (N'Laptop & Máy Tính Xách Tay', 'bi-laptop', 1),
    (N'Âm Thanh & Tai Nghe', 'bi-headphones', 1),
    (N'Máy Ảnh & Quay Phim', 'bi-camera', 1),
    (N'Đồng Hồ & Smartwatch', 'bi-smartwatch', 1),
    (N'Phụ Kiện Điện Tử', 'bi-plug', 1),
    (N'Thiết Bị Mạng & Wifi', 'bi-router', 1),
    (N'Tivi & Màn Hình Máy Tính', 'bi-tv', 1),
    (N'Thiết Bị Gia Dụng Thông Minh', 'bi-house-gear', 1),
    (N'Thời Trang & Quần Áo Nam Nữ', 'bi-bag', 1),
    (N'Giày Dép & Phụ Kiện', 'bi-handbag', 1),
    (N'Sách & Văn Phòng Phẩm', 'bi-book', 1),
    (N'Dụng Cụ Thể Thao Dã Ngoại', 'bi-trophy', 1),
    (N'Mỹ Phẩm & Chăm Sóc Sức Khỏe', 'bi-heart-pulse', 0),
    (N'Đồ Chơi & Thiết Bị Giải Trí', 'bi-controller', 1);
END
GO

-- Kiểm tra dữ liệu
SELECT * FROM categories ORDER BY id ASC;
GO
```

---

## ⚙️ CẤU HÌNH & KHỞI CHẠY DỰ ÁN

### 1. Cấu hình CSDL trong `application.properties`
Mở file `src/main/resources/application.properties` và điều chỉnh tài khoản/mật khẩu SQL Server cho phù hợp với máy của bạn:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=SpringBootThymeleafDB;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=123456
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
```

### 2. Biên dịch & Chạy dự án bằng Maven Wrapper

- **Biên dịch mã nguồn:**
  ```powershell
  .\mvnw.cmd clean compile
  ```
- **Khởi chạy ứng dụng:**
  ```powershell
  .\mvnw.cmd spring-boot:run
  ```
- **Truy cập hệ thống trên trình duyệt (Port 8081 - tránh trùng port 8080 của dự án cũ):**
  - Trang chủ Dashboard: [http://localhost:8081/admin/home](http://localhost:8081/admin/home)
  - Trang Quản lý Category: [http://localhost:8081/admin/categories](http://localhost:8081/admin/categories)

---

## 🔄 QUY TRÌNH PHÁT TRIỂN THEO GIT FLOW & PULL REQUESTS

Dự án được xây dựng tuần tự qua 4 giai đoạn với các Feature Branch và Pull Request chuẩn mực:

1. **Giai đoạn 1 (PR #1):** `feature/setup-and-domain` &rarr; Cấu hình Maven, Thymeleaf dependencies, kết nối CSDL `SpringBootThymeleafDB`, tầng Entity `Category`, Repository và Service.
2. **Giai đoạn 2 (PR #2):** `feature/thymeleaf-layout` &rarr; Cấu hình Thymeleaf Layout Dialect, Master Layout (`layout/admin.html`), Header Avatar sinh viên Huỳnh Cao Trung Đức kèm tính năng tải ảnh cá nhân thật từ máy tính, Footer HCM-UTE và Admin Dashboard.
3. **Giai đoạn 3 (PR #3):** `feature/category-crud-paging` &rarr; Xây dựng `CategoryController`, giao diện `list.html` (tìm kiếm, sắp xếp 2 chiều, phân trang linh hoạt) và `form.html` (Live Icon Preview, binding Thymeleaf `th:object`).
4. **Giai đoạn 4 (PR #4):** `feature/docs-and-polish` &rarr; Hoàn thiện tài liệu `README.md` chuyên nghiệp, kiểm thử toàn diện và nghiệm thu dự án.

---
&copy; 2026 **Huỳnh Cao Trung Đức - MSSV: 24133012** - Trường Đại học Công nghệ Kỹ thuật TP.HCM (HCM-UTE).
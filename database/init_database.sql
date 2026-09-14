-- =====================================================================
-- BÀI TẬP LẬP TRÌNH WEB - SPRING BOOT THYMELEAF CRUD CATEGORY
-- Họ và tên sinh viên: Huỳnh Cao Trung Đức
-- MSSV: 24133012
-- Trường: Trường Đại học Công nghệ Kỹ thuật TP.HCM (HCM-UTE)
-- Cơ sở dữ liệu: SpringBootThymeleafDB (Tách biệt hoàn toàn với bài cũ)
-- =====================================================================

-- 1. Tạo Database mới nếu chưa tồn tại
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'SpringBootThymeleafDB')
BEGIN
    CREATE DATABASE SpringBootThymeleafDB;
    PRINT N'Đã tạo mới Database SpringBootThymeleafDB thành công!';
END
ELSE
BEGIN
    PRINT N'Database SpringBootThymeleafDB đã tồn tại sẵn.';
END
GO

USE SpringBootThymeleafDB;
GO

-- 2. Tạo bảng categories nếu chưa tồn tại
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'categories')
BEGIN
    CREATE TABLE categories (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        category_name NVARCHAR(255) NOT NULL,
        icon NVARCHAR(255),
        status BIT DEFAULT 1
    );
    PRINT N'Đã tạo bảng categories thành công!';
END
ELSE
BEGIN
    PRINT N'Bảng categories đã tồn tại sẵn.';
END
GO

-- 3. Chèn 15 danh mục mẫu tiếng Việt phong phú để test phân trang và tìm kiếm
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
    
    PRINT N'Đã chèn 15 bản ghi danh mục mẫu thành công!';
END
ELSE
BEGIN
    PRINT N'Bảng categories đã có dữ liệu, không cần chèn lại.';
END
GO

-- 4. Kiểm tra dữ liệu vừa tạo
SELECT * FROM categories ORDER BY id ASC;
GO

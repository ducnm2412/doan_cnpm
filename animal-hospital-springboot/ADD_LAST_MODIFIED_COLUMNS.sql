-- =====================================================
-- Script thêm lại các cột last_modified_by và last_modified_date
-- cho bảng jhi_user
-- =====================================================

USE animalhospital;

-- Thêm lại cột last_modified_by
ALTER TABLE jhi_user 
ADD COLUMN last_modified_by VARCHAR(50) AFTER reset_date;

-- Thêm lại cột last_modified_date
ALTER TABLE jhi_user 
ADD COLUMN last_modified_date TIMESTAMP AFTER last_modified_by;

-- Kiểm tra kết quả
DESCRIBE jhi_user;



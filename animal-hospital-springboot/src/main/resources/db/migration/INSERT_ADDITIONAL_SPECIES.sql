-- =====================================================
-- Script INSERT các loài động vật bổ sung vào bảng species
-- =====================================================

USE animalhospital;

-- Insert các loài động vật mới
INSERT INTO species (name, is_active, created_date) VALUES
('Thỏ', TRUE, NOW()),
('Chuột', TRUE, NOW()),
('Chim', TRUE, NOW()),
('Cá', TRUE, NOW()),
('Rùa', TRUE, NOW()),
('Rắn', TRUE, NOW()),
('Khỉ', TRUE, NOW()),
('Heo', TRUE, NOW()),
('Bò', TRUE, NOW()),
('Dê', TRUE, NOW()),
('Cừu', TRUE, NOW()),
('Trâu', TRUE, NOW())
ON DUPLICATE KEY UPDATE 
    is_active = VALUES(is_active),
    last_modified_date = NOW();

-- Kiểm tra dữ liệu đã insert
SELECT id, name, is_active, created_date, last_modified_date 
FROM species 
ORDER BY created_date DESC;

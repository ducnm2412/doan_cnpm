-- =====================================================
-- Script tạo bảng disease_rabbit (Bệnh của Thỏ)
-- =====================================================

-- Chọn database
USE animalhospital;

-- Table: disease_rabbit (Bệnh của Thỏ)
CREATE TABLE IF NOT EXISTS disease_rabbit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở thỏ',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,thỏ',
    content TEXT NOT NULL COMMENT 'Nội dung chi tiết về bệnh',
    severity_level VARCHAR(50) COMMENT 'Mức độ: MILD, MODERATE, SEVERE, CRITICAL, GENERAL',
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_title (title(255)),
    INDEX idx_keywords (keywords(255)),
    INDEX idx_is_active (is_active),
    FULLTEXT INDEX ft_search (title, keywords, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

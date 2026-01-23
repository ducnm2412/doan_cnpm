-- =====================================================
-- Script tạo các bảng disease cho các loài động vật bổ sung
-- Dựa trên mô hình disease_dog và disease_cat
-- =====================================================

-- Chọn database
USE animalhospital;

-- 1. Table: disease_mouse (Bệnh của Chuột)
CREATE TABLE IF NOT EXISTS disease_mouse (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở chuột',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,chuột',
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

-- 2. Table: disease_bird (Bệnh của Chim)
CREATE TABLE IF NOT EXISTS disease_bird (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở chim',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,chim',
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

-- 3. Table: disease_fish (Bệnh của Cá)
CREATE TABLE IF NOT EXISTS disease_fish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở cá',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,cá',
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

-- 4. Table: disease_turtle (Bệnh của Rùa)
CREATE TABLE IF NOT EXISTS disease_turtle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở rùa',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,rùa',
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

-- 5. Table: disease_snake (Bệnh của Rắn)
CREATE TABLE IF NOT EXISTS disease_snake (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở rắn',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,rắn',
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

-- 6. Table: disease_monkey (Bệnh của Khỉ)
CREATE TABLE IF NOT EXISTS disease_monkey (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở khỉ',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,khỉ',
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

-- 7. Table: disease_pig (Bệnh của Heo)
CREATE TABLE IF NOT EXISTS disease_pig (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở heo',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,heo',
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

-- 8. Table: disease_cow (Bệnh của Bò)
CREATE TABLE IF NOT EXISTS disease_cow (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở bò',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,bò',
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

-- 9. Table: disease_goat (Bệnh của Dê)
CREATE TABLE IF NOT EXISTS disease_goat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở dê',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,dê',
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

-- 10. Table: disease_sheep (Bệnh của Cừu)
CREATE TABLE IF NOT EXISTS disease_sheep (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở cừu',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,cừu',
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

-- 11. Table: disease_buffalo (Bệnh của Trâu)
CREATE TABLE IF NOT EXISTS disease_buffalo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL COMMENT 'Tiêu đề bệnh: Bệnh nôn ở trâu',
    keywords VARCHAR(1000) COMMENT 'Từ khóa: nôn,tiêu chảy,trâu',
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

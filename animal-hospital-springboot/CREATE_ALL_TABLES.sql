-- =====================================================
-- Script tạo tất cả các bảng cho Animal Hospital
-- Database: animalhospital
-- =====================================================

USE animalhospital;

-- =====================================================
-- 1. JHipster Core Tables
-- =====================================================

-- Table: jhi_user
CREATE TABLE IF NOT EXISTS jhi_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(60) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(191) UNIQUE,
    image_url VARCHAR(256),
    activated BOOLEAN NOT NULL DEFAULT FALSE,
    lang_key VARCHAR(10),
    activation_key VARCHAR(20),
    reset_key VARCHAR(20),
    created_by VARCHAR(50) NOT NULL DEFAULT 'system',
    created_date TIMESTAMP,
    reset_date TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_date TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: jhi_authority
CREATE TABLE IF NOT EXISTS jhi_authority (
    name VARCHAR(50) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: jhi_user_authority
CREATE TABLE IF NOT EXISTS jhi_user_authority (
    user_id BIGINT NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority_name),
    CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES jhi_authority(name),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default authorities
INSERT IGNORE INTO jhi_authority (name) VALUES ('ROLE_ADMIN');
INSERT IGNORE INTO jhi_authority (name) VALUES ('ROLE_USER');
INSERT IGNORE INTO jhi_authority (name) VALUES ('ROLE_DOCTOR');
INSERT IGNORE INTO jhi_authority (name) VALUES ('ROLE_ASSISTANT');

-- =====================================================
-- 2. Owner Table
-- =====================================================

CREATE TABLE IF NOT EXISTS owner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    user_id BIGINT,
    CONSTRAINT fk_owner__user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id) ON DELETE SET NULL,
    CONSTRAINT uk_owner_user_id UNIQUE (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 3. Pet Table
-- =====================================================

CREATE TABLE IF NOT EXISTS pet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    species VARCHAR(255) NOT NULL,
    breed VARCHAR(255),
    sex VARCHAR(255),
    date_of_birth DATE,
    weight DECIMAL(21,2),
    allergies VARCHAR(255),
    notes VARCHAR(255),
    image_url VARCHAR(255),
    owner_id BIGINT,
    CONSTRAINT fk_pet_owner_id FOREIGN KEY (owner_id) REFERENCES owner(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 4. Vet Table
-- =====================================================

CREATE TABLE IF NOT EXISTS vet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    license_no VARCHAR(255) NOT NULL,
    specialization VARCHAR(255),
    user_id BIGINT,
    CONSTRAINT fk_vet__user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id) ON DELETE SET NULL,
    CONSTRAINT uk_vet_user_id UNIQUE (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 5. Appointment Table
-- =====================================================

CREATE TABLE IF NOT EXISTS appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    time_start DATETIME(6) NOT NULL,
    time_end DATETIME(6) NULL,
    type VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL COMMENT 'Appointment status: PENDING, APPROVED, REJECTED, SCHEDULED, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED, RESCHEDULED',
    notes VARCHAR(255),
    appointment_type VARCHAR(50) NOT NULL DEFAULT 'NORMAL',
    location_type VARCHAR(50) NOT NULL DEFAULT 'AT_CLINIC',
    pet_id BIGINT,
    vet_id BIGINT,
    owner_id BIGINT,
    CONSTRAINT fk_appointment_pet FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE SET NULL,
    CONSTRAINT fk_appointment_vet FOREIGN KEY (vet_id) REFERENCES vet(id) ON DELETE SET NULL,
    CONSTRAINT fk_appointment_owner FOREIGN KEY (owner_id) REFERENCES owner(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for appointment
CREATE INDEX idx_appointment_pet ON appointment(pet_id);
CREATE INDEX idx_appointment_vet ON appointment(vet_id);
CREATE INDEX idx_appointment_owner ON appointment(owner_id);
CREATE INDEX idx_appointment_type ON appointment(appointment_type);
CREATE INDEX idx_appointment_location ON appointment(location_type);

-- =====================================================
-- 6. Appointment Action Table
-- =====================================================

CREATE TABLE IF NOT EXISTS appointment_action (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    action_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    notes TEXT,
    scheduled_time DATETIME(6),
    completed_time DATETIME(6),
    appointment_id BIGINT,
    assigned_to_id BIGINT,
    created_by_id BIGINT,
    CONSTRAINT fk_appointment_action_appointment_id FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_action_assigned_to_id FOREIGN KEY (assigned_to_id) REFERENCES jhi_user(id) ON DELETE SET NULL,
    CONSTRAINT fk_appointment_action_created_by_id FOREIGN KEY (created_by_id) REFERENCES jhi_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for appointment_action
CREATE INDEX idx_appointment_action_appointment_id ON appointment_action(appointment_id);
CREATE INDEX idx_appointment_action_assigned_to_id ON appointment_action(assigned_to_id);
CREATE INDEX idx_appointment_action_created_by_id ON appointment_action(created_by_id);
CREATE INDEX idx_appointment_action_status ON appointment_action(status);
CREATE INDEX idx_appointment_action_action_type ON appointment_action(action_type);

-- =====================================================
-- 7. Lab Test Table
-- =====================================================

CREATE TABLE IF NOT EXISTS lab_test (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    test_name VARCHAR(100) NOT NULL,
    test_type VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    result TEXT,
    notes TEXT,
    requested_date DATETIME(6),
    completed_date DATETIME(6),
    sample_collected_date DATETIME(6),
    appointment_id BIGINT,
    pet_id BIGINT,
    requested_by_id BIGINT,
    assigned_to_id BIGINT,
    CONSTRAINT fk_lab_test_appointment_id FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    CONSTRAINT fk_lab_test_pet_id FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE SET NULL,
    CONSTRAINT fk_lab_test_requested_by_id FOREIGN KEY (requested_by_id) REFERENCES jhi_user(id) ON DELETE SET NULL,
    CONSTRAINT fk_lab_test_assigned_to_id FOREIGN KEY (assigned_to_id) REFERENCES jhi_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for lab_test
CREATE INDEX idx_lab_test_appointment_id ON lab_test(appointment_id);
CREATE INDEX idx_lab_test_pet_id ON lab_test(pet_id);
CREATE INDEX idx_lab_test_requested_by_id ON lab_test(requested_by_id);
CREATE INDEX idx_lab_test_assigned_to_id ON lab_test(assigned_to_id);
CREATE INDEX idx_lab_test_status ON lab_test(status);
CREATE INDEX idx_lab_test_test_type ON lab_test(test_type);

-- =====================================================
-- 8. Appointment Assistant Table (Many-to-Many)
-- =====================================================

CREATE TABLE IF NOT EXISTS appointment_assistant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6),
    appointment_id BIGINT NOT NULL,
    assistant_id BIGINT NOT NULL,
    CONSTRAINT fk_assistant_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    CONSTRAINT fk_assistant_assistant_id FOREIGN KEY (assistant_id) REFERENCES assistant(id) ON DELETE CASCADE,
    CONSTRAINT uk_assistant_appointment_user UNIQUE (appointment_id, assistant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 9. Appointment Message Table
-- =====================================================

CREATE TABLE IF NOT EXISTS appointment_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(1000) NOT NULL,
    timestamp DATETIME(6) NOT NULL,
    appointment_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    CONSTRAINT fk_message_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    CONSTRAINT fk_message_sender FOREIGN KEY (sender_id) REFERENCES jhi_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for appointment_message
CREATE INDEX idx_message_appointment ON appointment_message(appointment_id);
CREATE INDEX idx_message_sender ON appointment_message(sender_id);

-- =====================================================
-- 10. Appointment History Table
-- =====================================================

CREATE TABLE IF NOT EXISTS appointment_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    timestamp DATETIME(6) NOT NULL,
    details VARCHAR(2000),
    appointment_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_history_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id) ON DELETE CASCADE,
    CONSTRAINT fk_history_user FOREIGN KEY (user_id) REFERENCES jhi_user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes for appointment_history
CREATE INDEX idx_history_appointment ON appointment_history(appointment_id);
CREATE INDEX idx_history_user ON appointment_history(user_id);

-- =====================================================
-- Verification: Show all tables
-- =====================================================

SHOW TABLES;

-- =====================================================
-- End of Script
-- =====================================================


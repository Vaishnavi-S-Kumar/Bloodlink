CREATE DATABASE IF NOT EXISTS bloodlink;

USE bloodlink;


-- =====================================================
-- 1. USERS
-- =====================================================

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    email VARCHAR(100) UNIQUE
);


-- =====================================================
-- 2. DONORS
-- =====================================================

CREATE TABLE IF NOT EXISTS donors (
    donor_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20),
    blood_group VARCHAR(5) NOT NULL,
    phone VARCHAR(15),
    location VARCHAR(100),
    availability VARCHAR(20) DEFAULT 'AVAILABLE',
    last_donation_date DATE,
    eligibility VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


-- =====================================================
-- 3. HOSPITALS
-- =====================================================

CREATE TABLE IF NOT EXISTS hospitals (
    hospital_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    hospital_name VARCHAR(150) NOT NULL,
    registration_number VARCHAR(50),
    location VARCHAR(100),
    contact_number VARCHAR(15),
    email VARCHAR(100),
    type VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


-- =====================================================
-- 4. BLOOD REQUESTS
-- =====================================================

CREATE TABLE IF NOT EXISTS blood_requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    hospital_id INT NOT NULL,
    patient_name VARCHAR(100),
    blood_group VARCHAR(5) NOT NULL,
    units_required INT NOT NULL,
    location VARCHAR(100),
    priority VARCHAR(20) DEFAULT 'NORMAL',
    contact_number VARCHAR(15),
    request_status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hospital_id) REFERENCES hospitals(hospital_id)
);


-- =====================================================
-- 5. DONOR RESPONSES
-- =====================================================

CREATE TABLE IF NOT EXISTS donor_responses (
    response_id INT AUTO_INCREMENT PRIMARY KEY,
    request_id INT NOT NULL,
    donor_id INT NOT NULL,
    response VARCHAR(20) NOT NULL,
    responded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(request_id, donor_id),
    FOREIGN KEY (request_id) REFERENCES blood_requests(request_id),
    FOREIGN KEY (donor_id) REFERENCES donors(donor_id)
);


-- =====================================================
-- 6. DONATION HISTORY
-- =====================================================

CREATE TABLE IF NOT EXISTS donation_history (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    donor_id INT NOT NULL,
    donation_date DATE NOT NULL,
    hospital_name VARCHAR(150),
    blood_group VARCHAR(5),
    FOREIGN KEY (donor_id) REFERENCES donors(donor_id)
);


-- =====================================================
-- 7. NOTIFICATIONS
-- =====================================================

CREATE TABLE IF NOT EXISTS notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message VARCHAR(255) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);


-- =====================================================
-- DEMO DATA
-- =====================================================

-- Demo donor login
INSERT IGNORE INTO users
(username, password, role, status, email)
VALUES
('donor1', 'donor123', 'DONOR', 'APPROVED', 'donor@gmail.com');


-- Demo hospital login
INSERT IGNORE INTO users
(username, password, role, status, email)
VALUES
('hospital1', 'hospital123', 'HOSPITAL', 'APPROVED', 'cityhospital@gmail.com');


-- Demo admin login
INSERT IGNORE INTO users
(username, password, role, status, email)
VALUES
('admin', 'admin123', 'ADMIN', 'APPROVED', 'admin@bloodlink.com');


-- Demo donor details
INSERT IGNORE INTO donors
(user_id, name, age, gender, blood_group, phone, location,
 availability, last_donation_date, eligibility)
SELECT
    user_id,
    'Test Donor',
    20,
    'Female',
    'O+',
    '9876543211',
    'Kollam',
    'AVAILABLE',
    NULL,
    'ELIGIBLE'
FROM users
WHERE username = 'donor1';


-- Demo hospital details
INSERT IGNORE INTO hospitals
(user_id, hospital_name, registration_number, location,
 contact_number, email, type)
SELECT
    user_id,
    'City Hospital',
    'HOSP001',
    'Kollam',
    '9876543210',
    'cityhospital@gmail.com',
    'HOSPITAL'
FROM users
WHERE username = 'hospital1';


-- Demo blood request
INSERT INTO blood_requests
(hospital_id, patient_name, blood_group, units_required,
 location, priority, contact_number)
SELECT
    hospital_id,
    'Demo Patient',
    'O+',
    2,
    'Kollam',
    'EMERGENCY',
    '9876543212'
FROM hospitals
WHERE hospital_name = 'City Hospital'
AND NOT EXISTS (
    SELECT 1
    FROM blood_requests
    WHERE patient_name = 'Demo Patient'
);


-- Demo notification for donor
INSERT INTO notifications
(user_id, message)
SELECT
    user_id,
    'New O+ blood request is available in Kollam.'
FROM users
WHERE username = 'donor1'
AND NOT EXISTS (
    SELECT 1
    FROM notifications
    WHERE user_id = users.user_id
    AND message = 'New O+ blood request is available in Kollam.'
);
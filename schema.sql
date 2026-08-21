-- ==========================================================
-- Flight Booking Management System Database Schema
-- Database: flight_booking_db
-- ==========================================================

CREATE DATABASE IF NOT EXISTS flight_booking_db;
USE flight_booking_db;

-- 1. Flights Table
CREATE TABLE IF NOT EXISTS flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    airline VARCHAR(100) NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_seats CHECK (available_seats >= 0 AND available_seats <= total_seats)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_reference VARCHAR(20) NOT NULL UNIQUE,
    flight_id INT NOT NULL,
    flight_number VARCHAR(20) NOT NULL,
    passenger_name VARCHAR(150) NOT NULL,
    passenger_email VARCHAR(150) NOT NULL,
    passenger_phone VARCHAR(50) NOT NULL,
    seat_class VARCHAR(50) NOT NULL DEFAULT 'ECONOMY',
    seats_booked INT NOT NULL DEFAULT 1,
    total_price DECIMAL(10, 2) NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL DEFAULT 'CONFIRMED',
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample Seed Data
INSERT INTO flights (flight_number, airline, origin, destination, departure_time, arrival_time, total_seats, available_seats, price, status)
VALUES 
('AI-101', 'Air India', 'Delhi (DEL)', 'Mumbai (BOM)', DATE_ADD(NOW(), INTERVAL 2 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 2 DAY), INTERVAL 2 HOUR), 180, 180, 5499.00, 'SCHEDULED'),
('6E-205', 'IndiGo', 'Bangalore (BLR)', 'Delhi (DEL)', DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 3 DAY), INTERVAL 3 HOUR), 180, 175, 6200.00, 'SCHEDULED'),
('EK-500', 'Emirates', 'Mumbai (BOM)', 'Dubai (DXB)', DATE_ADD(NOW(), INTERVAL 4 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 4 DAY), INTERVAL 4 HOUR), 250, 240, 24500.00, 'SCHEDULED'),
('BA-142', 'British Airways', 'Delhi (DEL)', 'London (LHR)', DATE_ADD(NOW(), INTERVAL 5 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 5 DAY), INTERVAL 9 HOUR), 300, 290, 68000.00, 'SCHEDULED'),
('SQ-402', 'Singapore Airlines', 'Chennai (MAA)', 'Singapore (SIN)', DATE_ADD(NOW(), INTERVAL 3 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 3 DAY), INTERVAL 4 HOUR), 220, 218, 28900.00, 'SCHEDULED'),
('UK-820', 'Vistara', 'Hyderabad (HYD)', 'Goa (GOI)', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(DATE_ADD(NOW(), INTERVAL 1 DAY), INTERVAL 1 HOUR), 150, 142, 4300.00, 'SCHEDULED')
ON DUPLICATE KEY UPDATE flight_number = flight_number;

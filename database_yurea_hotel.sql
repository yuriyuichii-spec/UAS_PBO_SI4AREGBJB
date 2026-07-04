-- =========================================================
-- DATABASE: Yurea Grand Hotel - Aplikasi Resepsionis (CRUD)
-- Jalankan script ini di phpMyAdmin (Laragon) -> tab SQL
-- =========================================================

CREATE DATABASE IF NOT EXISTS yurea_hotel;
USE yurea_hotel;

-- 1. TABEL USER (LOGIN)
CREATE TABLE IF NOT EXISTS tbl_user (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    level VARCHAR(20) NOT NULL DEFAULT 'Resepsionis'
);

INSERT INTO tbl_user (username, password, nama_lengkap, level) VALUES
('admin', 'admin123', 'Administrator', 'Admin'),
('resepsionis1', 'resep123', 'Siti Aisyah', 'Resepsionis');

-- 2. TABEL KAMAR (MASTER DATA)
CREATE TABLE IF NOT EXISTS tbl_kamar (
    id_kamar INT AUTO_INCREMENT PRIMARY KEY,
    no_kamar VARCHAR(10) NOT NULL UNIQUE,
    tipe_kamar VARCHAR(30) NOT NULL,
    harga DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Tersedia'
);

INSERT INTO tbl_kamar (no_kamar, tipe_kamar, harga, status) VALUES
('101', 'Standard', 350000, 'Tersedia'),
('102', 'Standard', 350000, 'Tersedia'),
('103', 'Standard', 350000, 'Tersedia'),
('201', 'Deluxe', 550000, 'Tersedia'),
('202', 'Deluxe', 550000, 'Tersedia'),
('301', 'Suite', 900000, 'Tersedia'),
('302', 'Suite', 900000, 'Tersedia');

-- 3. TABEL RESERVASI (BERELASI KE tbl_kamar VIA id_kamar)
CREATE TABLE IF NOT EXISTS tbl_reservasi (
    id_reservasi INT AUTO_INCREMENT PRIMARY KEY,
    id_kamar INT NOT NULL,
    nama_tamu VARCHAR(100) NOT NULL,
    no_identitas VARCHAR(30) NOT NULL,
    tgl_checkin DATE NOT NULL,
    tgl_checkout DATE NOT NULL,
    jumlah_malam INT NOT NULL,
    total_bayar DECIMAL(12,2) NOT NULL,
    status_bayar VARCHAR(20) NOT NULL DEFAULT 'Belum Lunas',
    CONSTRAINT fk_reservasi_kamar FOREIGN KEY (id_kamar)
        REFERENCES tbl_kamar(id_kamar)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- contoh data reservasi
INSERT INTO tbl_reservasi (id_kamar, nama_tamu, no_identitas, tgl_checkin, tgl_checkout, jumlah_malam, total_bayar, status_bayar) VALUES
(1, 'Budi Santoso', '3271010101900001', '2026-07-01', '2026-07-03', 2, 700000, 'Lunas');

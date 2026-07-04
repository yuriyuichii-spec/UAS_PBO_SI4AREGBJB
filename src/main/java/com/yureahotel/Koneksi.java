package com.yureahotel;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 * Class untuk menangani koneksi ke database MySQL (Laragon).
 * Pastikan Laragon (MySQL) sudah running sebelum menjalankan aplikasi.
 */
public class Koneksi {

    // ==== SESUAIKAN DENGAN KONFIGURASI LARAGON ANDA ====
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "yurea_hotel";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // default Laragon: password kosong
    // =====================================================

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?useSSL=false&serverTimezone=Asia/Jakarta";

    public static Connection getKoneksi() {
        Connection konek = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            konek = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Driver MySQL tidak ditemukan!\n"
                    + "Pastikan mysql-connector-j sudah ditambahkan ke Library project.",
                    "Error Driver", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Koneksi database gagal!\n"
                    + "Pastikan Laragon (MySQL) sudah running dan database 'yurea_hotel' sudah dibuat.\n\n"
                    + "Detail error: " + e.getMessage(),
                    "Error Koneksi", JOptionPane.ERROR_MESSAGE);
        }
        return konek;
    }
}

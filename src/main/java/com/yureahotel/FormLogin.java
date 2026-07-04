package com.yureahotel;

import com.yureahotel.ui.LogoPanel;
import com.yureahotel.ui.RoundedButton;
import com.yureahotel.ui.RoundedPanel;
import com.yureahotel.ui.RoundedPasswordField;
import com.yureahotel.ui.RoundedTextField;
import com.yureahotel.ui.Tema;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

/**
 * FORM LOGIN
 * Halaman pertama aplikasi. Memvalidasi username & password ke tbl_user.
 * Tampilan bertema navy & gold ala "Yurea Grand Hotel".
 */
public class FormLogin extends JFrame {

    private RoundedTextField txtUsername;
    private RoundedPasswordField txtPassword;
    private boolean passwordTerlihat = false;

    public FormLogin() {
        setTitle("Login - Yurea Grand Hotel");
        setSize(460, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelUtama = new JPanel(null);
        panelUtama.setBackground(Tema.NAVY_BG);
        setContentPane(panelUtama);

        // ==== Logo & judul ====
        LogoPanel logo = new LogoPanel(80);
        logo.setBounds(190, 25, 80, 94);
        panelUtama.add(logo);

        JLabel lblNama = new JLabel("YUREA GRAND HOTEL", SwingConstants.CENTER);
        lblNama.setFont(Tema.FONT_JUDUL);
        lblNama.setForeground(Tema.GOLD);
        lblNama.setBounds(20, 122, 420, 30);
        panelUtama.add(lblNama);

        JLabel lblTagline = new JLabel("Experience Comfort Beyond Expectations.", SwingConstants.CENTER);
        lblTagline.setFont(Tema.FONT_SUB);
        lblTagline.setForeground(Tema.TEKS_ABU);
        lblTagline.setBounds(20, 150, 420, 20);
        panelUtama.add(lblTagline);

        JLabel lblSistem = new JLabel("Sistem Resepsionis", SwingConstants.CENTER);
        lblSistem.setFont(Tema.FONT_LABEL_BOLD);
        lblSistem.setForeground(Tema.GOLD_LIGHT);
        lblSistem.setBounds(20, 172, 420, 20);
        panelUtama.add(lblSistem);

        // ==== Kartu form ====
        RoundedPanel kartu = new RoundedPanel(Tema.NAVY_CARD, Tema.GOLD.darker());
        kartu.setBounds(30, 210, 400, 340);
        panelUtama.add(kartu);

        JLabel lblUser = new JLabel("Username");
        lblUser.setForeground(Tema.TEKS_PUTIH);
        lblUser.setFont(Tema.FONT_LABEL_BOLD);
        lblUser.setBounds(30, 25, 200, 20);
        kartu.add(lblUser);

        txtUsername = new RoundedTextField();
        txtUsername.setBounds(30, 48, 340, 40);
        kartu.add(txtUsername);

        JLabel lblPass = new JLabel("Password");
        lblPass.setForeground(Tema.TEKS_PUTIH);
        lblPass.setFont(Tema.FONT_LABEL_BOLD);
        lblPass.setBounds(30, 105, 200, 20);
        kartu.add(lblPass);

        txtPassword = new RoundedPasswordField();
        txtPassword.setEchoChar('\u2022');
        txtPassword.setBounds(30, 128, 340, 40);
        kartu.add(txtPassword);

        // tombol mata untuk tampilkan/sembunyikan password
        JButton btnMata = new JButton("\uD83D\uDC41");
        btnMata.setBounds(330, 128, 40, 40);
        btnMata.setBorderPainted(false);
        btnMata.setContentAreaFilled(false);
        btnMata.setFocusPainted(false);
        btnMata.setForeground(Tema.TEKS_ABU);
        btnMata.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMata.addActionListener(e -> {
            passwordTerlihat = !passwordTerlihat;
            txtPassword.setEchoChar(passwordTerlihat ? (char) 0 : '\u2022');
        });
        kartu.add(btnMata);

        RoundedButton btnLogin = new RoundedButton("MASUK", RoundedButton.Gaya.PRIMER);
        btnLogin.setBounds(30, 195, 340, 46);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.addActionListener(e -> prosesLogin());
        kartu.add(btnLogin);

        JLabel lblHint = new JLabel("Default: admin / admin123", SwingConstants.CENTER);
        lblHint.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblHint.setForeground(Tema.GOLD_LIGHT);
        lblHint.setBounds(30, 255, 340, 20);
        kartu.add(lblHint);

        txtPassword.addActionListener(e -> prosesLogin());
    }

    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;

            String sql = "SELECT * FROM tbl_user WHERE username=? AND password=?";
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String namaLengkap = rs.getString("nama_lengkap");
                JOptionPane.showMessageDialog(this,
                        "Login berhasil. Selamat datang, " + namaLengkap + "!");
                new MenuUtama(namaLengkap).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah!",
                        "Login Gagal", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLogin().setVisible(true));
    }
}

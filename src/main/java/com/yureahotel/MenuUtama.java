package com.yureahotel;

import com.yureahotel.ui.LogoPanel;
import com.yureahotel.ui.RoundedButton;
import com.yureahotel.ui.RoundedPanel;
import com.yureahotel.ui.Tema;
import java.awt.*;
import javax.swing.*;

/**
 * FORM MENU UTAMA
 * Dashboard setelah login, berisi navigasi ke Data Kamar dan Data Reservasi.
 * Tampilan bertema navy & gold ala "Yurea Grand Hotel".
 */
public class MenuUtama extends JFrame {

    public MenuUtama(String namaUser) {
        setTitle("Menu Utama - Yurea Grand Hotel");
        setSize(760, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelUtama = new JPanel(null);
        panelUtama.setBackground(Tema.NAVY_BG);
        setContentPane(panelUtama);

        // ==== Header ====
        RoundedPanel header = new RoundedPanel(Tema.NAVY_CARD, null, 0);
        header.setBounds(0, 0, 760, 130);
        panelUtama.add(header);

        LogoPanel logo = new LogoPanel(56);
        logo.setBounds(25, 20, 56, 70);
        header.add(logo);

        JLabel lblJudul = new JLabel("YUREA GRAND HOTEL");
        lblJudul.setForeground(Tema.GOLD);
        lblJudul.setFont(Tema.FONT_JUDUL);
        lblJudul.setBounds(95, 25, 500, 30);
        header.add(lblJudul);

        JLabel lblTagline = new JLabel("Experience Comfort Beyond Expectations.");
        lblTagline.setForeground(Tema.TEKS_ABU);
        lblTagline.setFont(Tema.FONT_SUB);
        lblTagline.setBounds(95, 55, 500, 20);
        header.add(lblTagline);

        JLabel lblUser = new JLabel("\uD83D\uDC64 Login sebagai: " + namaUser);
        lblUser.setForeground(Tema.GOLD_LIGHT);
        lblUser.setFont(Tema.FONT_LABEL_BOLD);
        lblUser.setBounds(95, 82, 400, 20);
        header.add(lblUser);

        // ==== Kartu-kartu menu ====
        JLabel lblMenuTitle = new JLabel("MENU UTAMA");
        lblMenuTitle.setForeground(Tema.TEKS_PUTIH);
        lblMenuTitle.setFont(Tema.FONT_LABEL_BOLD);
        lblMenuTitle.setBounds(30, 155, 300, 20);
        panelUtama.add(lblMenuTitle);

        panelUtama.add(kartuMenu("\uD83D\uDECF\uFE0F", "Data Kamar",
                "Kelola kamar, tipe, harga & status", 30, 190,
                RoundedButton.Gaya.PRIMER, "Buka",
                e -> new FormKamar().setVisible(true)));

        panelUtama.add(kartuMenu("\uD83E\uDDFE", "Data Reservasi",
                "Kelola booking & check-in tamu", 270, 190,
                RoundedButton.Gaya.PRIMER, "Buka",
                e -> new FormReservasi(namaUser).setVisible(true)));

        panelUtama.add(kartuMenu("\u23FB", "Logout",
                "Keluar dari sesi resepsionis", 510, 190,
                RoundedButton.Gaya.DANGER, "Logout",
                e -> {
                    int konfirmasi = JOptionPane.showConfirmDialog(this,
                            "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        new FormLogin().setVisible(true);
                        this.dispose();
                    }
                }));

        // ==== Ringkasan tipe kamar (info visual, sesuai palet tema) ====
        JLabel lblInfoTitle = new JLabel("TIPE KAMAR & WARNA KELAS");
        lblInfoTitle.setForeground(Tema.TEKS_PUTIH);
        lblInfoTitle.setFont(Tema.FONT_LABEL_BOLD);
        lblInfoTitle.setBounds(30, 380, 300, 20);
        panelUtama.add(lblInfoTitle);

        panelUtama.add(kartuTipe(Tema.STANDARD, "STANDARD", "Kamar nyaman fasilitas standar", 30, 410));
        panelUtama.add(kartuTipe(Tema.DELUXE, "DELUXE", "Kamar luas fasilitas premium", 280, 410));
        panelUtama.add(kartuTipe(Tema.SUITE, "SUITE", "Kamar mewah fasilitas eksklusif", 530, 410));

        JLabel lblFooter = new JLabel("Aplikasi CRUD Resepsionis \u2022 Yurea Grand Hotel");
        lblFooter.setForeground(Tema.TEKS_ABU);
        lblFooter.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblFooter.setBounds(30, 505, 500, 20);
        panelUtama.add(lblFooter);
    }

    private RoundedPanel kartuMenu(String ikon, String judul, String deskripsi,
                                    int x, int y, RoundedButton.Gaya gayaTombol, String teksTombol,
                                    java.awt.event.ActionListener aksi) {
        RoundedPanel kartu = new RoundedPanel(Tema.NAVY_CARD, Tema.GOLD.darker());
        kartu.setBounds(x, y, 220, 170);

        JLabel lblIkon = new JLabel(ikon, SwingConstants.CENTER);
        lblIkon.setFont(new Font("SansSerif", Font.PLAIN, 36));
        lblIkon.setBounds(0, 20, 220, 45);
        kartu.add(lblIkon);

        JLabel lblJudul = new JLabel(judul, SwingConstants.CENTER);
        lblJudul.setForeground(Tema.GOLD);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblJudul.setBounds(10, 65, 200, 24);
        kartu.add(lblJudul);

        JLabel lblDesk = new JLabel("<html><div style='text-align:center;'>" + deskripsi + "</div></html>", SwingConstants.CENTER);
        lblDesk.setForeground(Tema.TEKS_ABU);
        lblDesk.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblDesk.setBounds(15, 90, 190, 35);
        kartu.add(lblDesk);

        RoundedButton btn = new RoundedButton(teksTombol, gayaTombol);
        btn.setBounds(50, 128, 120, 34);
        btn.addActionListener(aksi);
        kartu.add(btn);

        return kartu;
    }

    private RoundedPanel kartuTipe(Color warna, String judul, String deskripsi, int x, int y) {
        RoundedPanel kartu = new RoundedPanel(Tema.NAVY_CARD, warna);
        kartu.setBounds(x, y, 210, 70);

        JPanel titik = new JPanel();
        titik.setBackground(warna);
        titik.setBounds(15, 15, 40, 40);
        kartu.add(titik);

        JLabel lblJudul = new JLabel(judul);
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblJudul.setBounds(65, 12, 140, 18);
        kartu.add(lblJudul);

        JLabel lblDesk = new JLabel("<html>" + deskripsi + "</html>");
        lblDesk.setForeground(Tema.TEKS_ABU);
        lblDesk.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblDesk.setBounds(65, 30, 140, 30);
        kartu.add(lblDesk);

        return kartu;
    }
}

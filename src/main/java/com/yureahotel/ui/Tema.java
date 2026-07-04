package com.yureahotel.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * Konstanta warna & font tema "Yurea Grand Hotel" (Navy & Gold).
 * Dipakai bersama oleh semua form supaya tampilan konsisten.
 */
public class Tema {

    // ==== Latar ====
    public static final Color NAVY_BG    = new Color(0x0D, 0x1B, 0x2A); // latar utama
    public static final Color NAVY_CARD  = new Color(0x13, 0x27, 0x3F); // panel/kartu/header
    public static final Color NAVY_INPUT = new Color(0x1B, 0x33, 0x4D); // kotak input

    // ==== Aksen emas ====
    public static final Color GOLD       = new Color(0xD4, 0xAF, 0x37);
    public static final Color GOLD_LIGHT = new Color(0xF5, 0xD7, 0x7A);

    // ==== Warna tipe kamar ====
    public static final Color STANDARD = new Color(0x28, 0xA7, 0x45); // hijau
    public static final Color DELUXE   = new Color(0xC9, 0xA2, 0x27); // gold agak gelap (kontras di badge)
    public static final Color SUITE    = new Color(0x7B, 0x4D, 0xCC); // ungu

    // ==== Warna aksi/status ====
    public static final Color MERAH          = new Color(0xE7, 0x4C, 0x3C);
    public static final Color BIRU_SEKUNDER  = new Color(0x2E, 0x5C, 0x8A);
    public static final Color ABU_NETRAL     = new Color(0x4A, 0x55, 0x68);

    public static final Color TEKS_PUTIH = new Color(0xF0, 0xF0, 0xF0);
    public static final Color TEKS_ABU   = new Color(0xA0, 0xA8, 0xB8);
    public static final Color GARIS_TIPIS = new Color(255, 255, 255, 40);

    // ==== Font ====
    public static final Font FONT_JUDUL_BESAR = new Font("Serif", Font.BOLD, 26);
    public static final Font FONT_JUDUL       = new Font("Serif", Font.BOLD, 20);
    public static final Font FONT_SUB         = new Font("SansSerif", Font.ITALIC, 13);
    public static final Font FONT_LABEL       = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_LABEL_BOLD  = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_INPUT       = new Font("SansSerif", Font.PLAIN, 14);

    public static Color warnaTipeKamar(String tipe) {
        if (tipe == null) return ABU_NETRAL;
        switch (tipe) {
            case "Standard": return STANDARD;
            case "Deluxe":   return DELUXE;
            case "Suite":    return SUITE;
            default:         return ABU_NETRAL;
        }
    }

    public static Color warnaStatusKamar(String status) {
        if (status == null) return ABU_NETRAL;
        switch (status) {
            case "Tersedia":    return STANDARD;
            case "Terisi":      return MERAH;
            case "Maintenance": return ABU_NETRAL;
            default:            return ABU_NETRAL;
        }
    }

    public static Color warnaStatusBayar(String status) {
        return "Lunas".equals(status) ? STANDARD : MERAH;
    }

    /** Terapkan gaya dark-navy pada JComboBox tanpa perlu ubah UI Look and Feel. */
    public static void gayakanComboBox(javax.swing.JComboBox<?> combo) {
        combo.setBackground(NAVY_INPUT);
        combo.setForeground(Color.WHITE);
        combo.setFont(FONT_INPUT);
        combo.setFocusable(false);
        combo.setBorder(javax.swing.BorderFactory.createLineBorder(GARIS_TIPIS, 1, true));
    }

    /** Terapkan gaya dark-navy pada JTable. */
    public static void gayakanTabel(javax.swing.JTable tabel) {
        tabel.setBackground(NAVY_CARD);
        tabel.setForeground(TEKS_PUTIH);
        tabel.setGridColor(new Color(255, 255, 255, 25));
        tabel.setRowHeight(30);
        tabel.setSelectionBackground(BIRU_SEKUNDER);
        tabel.setSelectionForeground(Color.WHITE);
        tabel.setFont(FONT_LABEL);
        tabel.getTableHeader().setBackground(NAVY_INPUT);
        tabel.getTableHeader().setForeground(GOLD);
        tabel.getTableHeader().setFont(FONT_LABEL_BOLD);
        tabel.setShowVerticalLines(false);
        tabel.setFillsViewportHeight(true);
    }
}

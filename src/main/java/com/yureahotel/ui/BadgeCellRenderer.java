package com.yureahotel.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Cell renderer untuk menampilkan nilai kolom JTable sebagai "badge" berwarna,
 * misalnya kolom Tipe Kamar (hijau/gold/ungu) atau Status (hijau/merah).
 */
public class BadgeCellRenderer extends DefaultTableCellRenderer {

    public interface PewarnaBadge {
        Color warnaUntuk(String nilai);
    }

    private final PewarnaBadge pewarna;

    public BadgeCellRenderer(PewarnaBadge pewarna) {
        this.pewarna = pewarna;
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
        setFont(Tema.FONT_LABEL_BOLD);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String teks = value == null ? "" : value.toString();
        Color warna = pewarna.warnaUntuk(teks);
        setText(teks);
        setForeground(Color.WHITE);
        setBackground(isSelected ? warna.darker() : warna);
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        return this;
    }
}

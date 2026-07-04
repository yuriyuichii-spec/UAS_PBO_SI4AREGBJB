package com.yureahotel.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Tombol dengan sudut membulat & warna sesuai kategori aksi
 * (meniru desain tombol pada mockup: Primer/gold, Sekunder/biru, Danger/merah, Neutral/abu).
 */
public class RoundedButton extends JButton {

    public enum Gaya { PRIMER, SEKUNDER, DANGER, NETRAL, SPESIAL }

    private Color warnaDasar;
    private Color warnaHover;
    private final int arc = 14;

    public RoundedButton(String teks, Gaya gaya) {
        super(teks);
        terapkanGaya(gaya);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(10, 20, 10, 20));
    }

    private void terapkanGaya(Gaya gaya) {
        switch (gaya) {
            case PRIMER:
            case SPESIAL:
                warnaDasar = Tema.GOLD;
                warnaHover = Tema.GOLD_LIGHT;
                setForeground(Tema.NAVY_BG);
                break;
            case SEKUNDER:
                warnaDasar = Tema.BIRU_SEKUNDER;
                warnaHover = Tema.BIRU_SEKUNDER.brighter();
                setForeground(Color.WHITE);
                break;
            case DANGER:
                warnaDasar = Tema.MERAH;
                warnaHover = Tema.MERAH.brighter();
                setForeground(Color.WHITE);
                break;
            case NETRAL:
                warnaDasar = Tema.ABU_NETRAL;
                warnaHover = Tema.ABU_NETRAL.brighter();
                setForeground(Color.WHITE);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color warna = getModel().isRollover() ? warnaHover : warnaDasar;
        if (getModel().isPressed()) warna = warna.darker();
        g2.setColor(warna);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();
        super.paintComponent(g);
    }
}

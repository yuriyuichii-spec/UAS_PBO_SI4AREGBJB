package com.yureahotel.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Menggambar monogram logo "Yurea Grand Hotel": lingkaran emas ganda berisi huruf Y
 * dan mahkota kecil di atasnya. Digambar langsung dengan Java2D sehingga
 * tidak memerlukan file gambar/logo eksternal.
 */
public class LogoPanel extends JPanel {

    private final int ukuran;

    public LogoPanel(int ukuran) {
        this.ukuran = ukuran;
        setOpaque(false);
        setPreferredSize(new Dimension(ukuran, ukuran + 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = ukuran / 2;
        int cy = ukuran / 2 + 14;
        int r = ukuran / 2 - 4;

        // dua lingkaran konsentris emas
        g2.setStroke(new BasicStroke(2.4f));
        g2.setColor(Tema.GOLD);
        g2.drawOval(cx - r, cy - r, r * 2, r * 2);
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawOval(cx - r + 6, cy - r + 6, (r - 6) * 2, (r - 6) * 2);

        // huruf Y di tengah
        g2.setFont(new Font("Serif", Font.BOLD, (int) (ukuran * 0.42)));
        FontMetrics fm = g2.getFontMetrics();
        String huruf = "Y";
        int tx = cx - fm.stringWidth(huruf) / 2;
        int ty = cy + fm.getAscent() / 2 - 6;
        g2.setColor(Tema.GOLD);
        g2.drawString(huruf, tx, ty);

        // mahkota kecil di atas lingkaran
        int mx = cx;
        int my = cy - r - 4;
        Polygon mahkota = new Polygon();
        mahkota.addPoint(mx - 11, my + 9);
        mahkota.addPoint(mx - 11, my - 1);
        mahkota.addPoint(mx - 5, my + 5);
        mahkota.addPoint(mx, my - 8);
        mahkota.addPoint(mx + 5, my + 5);
        mahkota.addPoint(mx + 11, my - 1);
        mahkota.addPoint(mx + 11, my + 9);
        g2.setColor(Tema.GOLD);
        g2.fillPolygon(mahkota);

        g2.dispose();
    }
}

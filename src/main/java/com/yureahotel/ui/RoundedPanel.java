package com.yureahotel.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Panel "kartu" dengan sudut membulat dan garis tepi tipis,
 * dipakai sebagai wadah form/section pada semua frame.
 */
public class RoundedPanel extends JPanel {

    private final int arc;
    private final Color warnaDasar;
    private final Color warnaGaris;

    public RoundedPanel(Color warnaDasar, Color warnaGaris) {
        this(warnaDasar, warnaGaris, 18);
    }

    public RoundedPanel(Color warnaDasar, Color warnaGaris, int arc) {
        this.warnaDasar = warnaDasar;
        this.warnaGaris = warnaGaris;
        this.arc = arc;
        setOpaque(false);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(warnaDasar);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        if (warnaGaris != null) {
            g2.setColor(warnaGaris);
            g2.setStroke(new BasicStroke(1.4f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}

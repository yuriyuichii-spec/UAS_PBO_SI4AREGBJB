package com.yureahotel.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * JTextField bersudut membulat dengan tema navy/gold.
 */
public class RoundedTextField extends JTextField {

    private final int arc = 10;

    public RoundedTextField() {
        init();
    }

    public RoundedTextField(String teks) {
        super(teks);
        init();
    }

    private void init() {
        setOpaque(false);
        setBorder(new EmptyBorder(6, 12, 6, 12));
        setBackground(Tema.NAVY_INPUT);
        setForeground(Color.WHITE);
        setCaretColor(Tema.GOLD);
        setFont(Tema.FONT_INPUT);
        setSelectionColor(Tema.BIRU_SEKUNDER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.setColor(Tema.GARIS_TIPIS);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        g2.dispose();
        super.paintComponent(g);
    }
}

package com.yureahotel.ui;

import java.text.DecimalFormat;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Cell renderer untuk menampilkan angka pada JTable sebagai format uang Rupiah rapi,
 * misalnya 350000.0 -> "Rp 350.000". Tidak mengubah data asli, hanya tampilannya.
 */
public class CurrencyCellRenderer extends DefaultTableCellRenderer {

    private static final DecimalFormat FORMAT = new DecimalFormat("#,##0");

    public CurrencyCellRenderer() {
        setHorizontalAlignment(SwingConstants.RIGHT);
        setFont(Tema.FONT_LABEL);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof Number) {
            double angka = ((Number) value).doubleValue();
            setText("Rp " + FORMAT.format(angka));
        } else {
            super.setValue(value);
        }
    }
}

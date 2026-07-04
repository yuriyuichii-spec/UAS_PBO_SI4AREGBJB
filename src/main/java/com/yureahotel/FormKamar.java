package com.yureahotel;

import com.yureahotel.ui.BadgeCellRenderer;
import com.yureahotel.ui.CurrencyCellRenderer;
import com.yureahotel.ui.RoundedButton;
import com.yureahotel.ui.RoundedPanel;
import com.yureahotel.ui.RoundedTextField;
import com.yureahotel.ui.Tema;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * FORM DATA KAMAR
 * CRUD (Create, Read, Update, Delete) untuk tabel tbl_kamar.
 * Tampilan bertema navy & gold, dengan badge warna per tipe & status kamar.
 */
public class FormKamar extends JFrame {

    private JTextField txtId;
    private RoundedTextField txtNoKamar, txtHarga;
    private JComboBox<String> cboTipe, cboStatus;
    private JTable tabel;
    private DefaultTableModel model;

    public FormKamar() {
        setTitle("Data Kamar - Yurea Grand Hotel");
        setSize(820, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(Tema.NAVY_BG);
        setContentPane(panel);

        JLabel lblJudul = new JLabel("\uD83D\uDECF\uFE0F  DATA KAMAR");
        lblJudul.setFont(Tema.FONT_JUDUL);
        lblJudul.setForeground(Tema.GOLD);
        lblJudul.setBounds(25, 15, 400, 34);
        panel.add(lblJudul);

        // ==== Kartu form input ====
        RoundedPanel kartuForm = new RoundedPanel(Tema.NAVY_CARD, Tema.GOLD.darker());
        kartuForm.setBounds(25, 60, 760, 175);
        panel.add(kartuForm);

        txtId = buatLabelField(kartuForm, "ID Kamar", 25, 25, 120, false);
        txtId.setEditable(false);

        txtNoKamar = (RoundedTextField) buatLabelField(kartuForm, "No. Kamar", 165, 25, 180, true);

        JLabel lblTipe = new JLabel("Tipe Kamar");
        lblTipe.setForeground(Tema.TEKS_PUTIH);
        lblTipe.setFont(Tema.FONT_LABEL_BOLD);
        lblTipe.setBounds(365, 25, 150, 20);
        kartuForm.add(lblTipe);
        cboTipe = new JComboBox<>(new String[]{"Standard", "Deluxe", "Suite"});
        cboTipe.setBounds(365, 48, 180, 36);
        Tema.gayakanComboBox(cboTipe);
        kartuForm.add(cboTipe);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setForeground(Tema.TEKS_PUTIH);
        lblStatus.setFont(Tema.FONT_LABEL_BOLD);
        lblStatus.setBounds(565, 25, 150, 20);
        kartuForm.add(lblStatus);
        cboStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi", "Maintenance"});
        cboStatus.setBounds(565, 48, 170, 36);
        Tema.gayakanComboBox(cboStatus);
        kartuForm.add(cboStatus);

        txtHarga = (RoundedTextField) buatLabelField(kartuForm, "Harga/Malam", 25, 90, 180, true);

        // ==== Tombol Aksi ====
        RoundedButton btnTambah = new RoundedButton("+ Tambah", RoundedButton.Gaya.PRIMER);
        btnTambah.setBounds(25, 140, 130, 34);
        btnTambah.addActionListener(e -> tambahData());
        kartuForm.add(btnTambah);

        RoundedButton btnUbah = new RoundedButton("\u270E Ubah", RoundedButton.Gaya.SEKUNDER);
        btnUbah.setBounds(165, 140, 120, 34);
        btnUbah.addActionListener(e -> ubahData());
        kartuForm.add(btnUbah);

        RoundedButton btnHapus = new RoundedButton("\uD83D\uDDD1 Hapus", RoundedButton.Gaya.DANGER);
        btnHapus.setBounds(295, 140, 120, 34);
        btnHapus.addActionListener(e -> hapusData());
        kartuForm.add(btnHapus);

        RoundedButton btnBersih = new RoundedButton("Bersihkan Form", RoundedButton.Gaya.NETRAL);
        btnBersih.setBounds(425, 140, 160, 34);
        btnBersih.addActionListener(e -> bersihkanForm());
        kartuForm.add(btnBersih);

        // ==== Tabel ====
        model = new DefaultTableModel(new Object[]{"ID", "No. Kamar", "Tipe", "Harga", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabel = new JTable(model);
        Tema.gayakanTabel(tabel);
        tabel.getColumnModel().getColumn(2).setCellRenderer(new BadgeCellRenderer(Tema::warnaTipeKamar));
        tabel.getColumnModel().getColumn(3).setCellRenderer(new CurrencyCellRenderer());
        tabel.getColumnModel().getColumn(4).setCellRenderer(new BadgeCellRenderer(Tema::warnaStatusKamar));
        tabel.getSelectionModel().addListSelectionListener(e -> isiFormDariTabel());

        // lebar kolom disesuaikan proporsional (ID kecil, sisanya lebih lega)
        tabel.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        tabel.getColumnModel().getColumn(1).setPreferredWidth(140);  // No. Kamar
        tabel.getColumnModel().getColumn(2).setPreferredWidth(160);  // Tipe
        tabel.getColumnModel().getColumn(3).setPreferredWidth(200);  // Harga
        tabel.getColumnModel().getColumn(4).setPreferredWidth(180);  // Status
        tabel.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBounds(25, 250, 760, 320);
        scroll.getViewport().setBackground(Tema.NAVY_CARD);
        panel.add(scroll);

        tampilkanData();
    }

    /** Helper: buat label + rounded text field sekaligus, dikembalikan JTextField-nya. */
    private JTextField buatLabelField(JPanel induk, String label, int x, int y, int lebar, boolean bisaEdit) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(Tema.TEKS_PUTIH);
        lbl.setFont(Tema.FONT_LABEL_BOLD);
        lbl.setBounds(x, y, lebar, 20);
        induk.add(lbl);

        RoundedTextField field = new RoundedTextField();
        field.setBounds(x, y + 23, lebar, 36);
        if (!bisaEdit) {
            field.setBackground(Tema.NAVY_BG);
            field.setForeground(Tema.TEKS_ABU);
        }
        induk.add(field);
        return field;
    }

    private void tampilkanData() {
        model.setRowCount(0);
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_kamar ORDER BY no_kamar");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_kamar"),
                        rs.getString("no_kamar"),
                        rs.getString("tipe_kamar"),
                        rs.getDouble("harga"),
                        rs.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void isiFormDariTabel() {
        int baris = tabel.getSelectedRow();
        if (baris == -1) return;
        txtId.setText(model.getValueAt(baris, 0).toString());
        txtNoKamar.setText(model.getValueAt(baris, 1).toString());
        cboTipe.setSelectedItem(model.getValueAt(baris, 2).toString());
        txtHarga.setText(model.getValueAt(baris, 3).toString());
        cboStatus.setSelectedItem(model.getValueAt(baris, 4).toString());
    }

    private boolean validasiInput() {
        if (txtNoKamar.getText().trim().isEmpty() || txtHarga.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No. Kamar dan Harga wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(txtHarga.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void tambahData() {
        if (!validasiInput()) return;
        String sql = "INSERT INTO tbl_kamar (no_kamar, tipe_kamar, harga, status) VALUES (?,?,?,?)";
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setString(1, txtNoKamar.getText().trim());
            ps.setString(2, (String) cboTipe.getSelectedItem());
            ps.setDouble(3, Double.parseDouble(txtHarga.getText().trim()));
            ps.setString(4, (String) cboStatus.getSelectedItem());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data kamar berhasil ditambahkan.");
            bersihkanForm();
            tampilkanData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambah data: " + e.getMessage());
        }
    }

    private void ubahData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!");
            return;
        }
        if (!validasiInput()) return;
        String sql = "UPDATE tbl_kamar SET no_kamar=?, tipe_kamar=?, harga=?, status=? WHERE id_kamar=?";
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setString(1, txtNoKamar.getText().trim());
            ps.setString(2, (String) cboTipe.getSelectedItem());
            ps.setDouble(3, Double.parseDouble(txtHarga.getText().trim()));
            ps.setString(4, (String) cboStatus.getSelectedItem());
            ps.setInt(5, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data kamar berhasil diubah.");
            bersihkanForm();
            tampilkanData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }

    private void hapusData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!");
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM tbl_kamar WHERE id_kamar=?";
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data kamar berhasil dihapus.");
            bersihkanForm();
            tampilkanData();
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this,
                    "Kamar tidak bisa dihapus karena masih memiliki data reservasi terkait!",
                    "Gagal Hapus", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNoKamar.setText("");
        txtHarga.setText("");
        cboTipe.setSelectedIndex(0);
        cboStatus.setSelectedIndex(0);
        tabel.clearSelection();
    }
}

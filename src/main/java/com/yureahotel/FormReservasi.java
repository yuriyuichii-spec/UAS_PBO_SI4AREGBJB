package com.yureahotel;

import com.yureahotel.ui.BadgeCellRenderer;
import com.yureahotel.ui.CurrencyCellRenderer;
import com.yureahotel.ui.RoundedButton;
import com.yureahotel.ui.RoundedPanel;
import com.yureahotel.ui.RoundedTextField;
import com.yureahotel.ui.Tema;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * FORM DATA RESERVASI
 * CRUD untuk tabel tbl_reservasi yang BERELASI dengan tbl_kamar (id_kamar).
 * Fitur tambahan: Cetak Struk / Invoice (opsional, untuk nilai tambahan).
 * Tampilan bertema navy & gold, dengan badge warna pada tabel.
 */
public class FormReservasi extends JFrame {

    // ==== Info hotel untuk kop struk (ubah di sini kalau alamat/telp berbeda) ====
    private static final String ALAMAT_HOTEL = "Jl. Ahmad Yani No.123 Banjarmasin";
    private static final String TELP_HOTEL = "(0511) 123456789";

    private JTextField txtId;
    private RoundedTextField txtNama, txtIdentitas, txtCheckin, txtCheckout, txtTotal;
    private JComboBox<String> cboKamar, cboStatusBayar;
    private JTable tabel;
    private DefaultTableModel model;
    private final String namaKasir;

    private final Map<String, Integer> mapKamar = new HashMap<>();
    private final Map<Integer, Double> hargaKamar = new HashMap<>();

    public FormReservasi(String namaKasir) {
        this.namaKasir = namaKasir;
        setTitle("Data Reservasi - Yurea Grand Hotel");
        setSize(950, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(Tema.NAVY_BG);
        setContentPane(panel);

        JLabel lblJudul = new JLabel("\uD83E\uDDFE  DATA RESERVASI TAMU");
        lblJudul.setFont(Tema.FONT_JUDUL);
        lblJudul.setForeground(Tema.GOLD);
        lblJudul.setBounds(25, 15, 450, 34);
        panel.add(lblJudul);

        // ==== Kartu form input ====
        RoundedPanel kartuForm = new RoundedPanel(Tema.NAVY_CARD, Tema.GOLD.darker());
        kartuForm.setBounds(25, 60, 890, 245);
        panel.add(kartuForm);

        // baris 1
        JLabel lblId = new JLabel("ID Reservasi");
        lblId.setForeground(Tema.TEKS_PUTIH);
        lblId.setFont(Tema.FONT_LABEL_BOLD);
        lblId.setBounds(25, 25, 150, 20);
        kartuForm.add(lblId);
        txtId = new RoundedTextField();
        txtId.setBounds(25, 48, 140, 36);
        txtId.setEditable(false);
        ((RoundedTextField) txtId).setBackground(Tema.NAVY_BG);
        txtId.setForeground(Tema.TEKS_ABU);
        kartuForm.add(txtId);

        JLabel lblKamar = new JLabel("Pilih Kamar");
        lblKamar.setForeground(Tema.TEKS_PUTIH);
        lblKamar.setFont(Tema.FONT_LABEL_BOLD);
        lblKamar.setBounds(180, 25, 200, 20);
        kartuForm.add(lblKamar);
        cboKamar = new JComboBox<>();
        cboKamar.setBounds(180, 48, 260, 36);
        Tema.gayakanComboBox(cboKamar);
        cboKamar.addActionListener(e -> hitungTotal());
        kartuForm.add(cboKamar);

        JLabel lblNama = new JLabel("Nama Tamu");
        lblNama.setForeground(Tema.TEKS_PUTIH);
        lblNama.setFont(Tema.FONT_LABEL_BOLD);
        lblNama.setBounds(460, 25, 200, 20);
        kartuForm.add(lblNama);
        txtNama = new RoundedTextField();
        txtNama.setBounds(460, 48, 220, 36);
        kartuForm.add(txtNama);

        JLabel lblStatusBayar = new JLabel("Status Bayar");
        lblStatusBayar.setForeground(Tema.TEKS_PUTIH);
        lblStatusBayar.setFont(Tema.FONT_LABEL_BOLD);
        lblStatusBayar.setBounds(700, 25, 150, 20);
        kartuForm.add(lblStatusBayar);
        cboStatusBayar = new JComboBox<>(new String[]{"Belum Lunas", "Lunas"});
        cboStatusBayar.setBounds(700, 48, 165, 36);
        Tema.gayakanComboBox(cboStatusBayar);
        kartuForm.add(cboStatusBayar);

        // baris 2
        JLabel lblIdentitas = new JLabel("No. KTP/Identitas");
        lblIdentitas.setForeground(Tema.TEKS_PUTIH);
        lblIdentitas.setFont(Tema.FONT_LABEL_BOLD);
        lblIdentitas.setBounds(25, 95, 200, 20);
        kartuForm.add(lblIdentitas);
        txtIdentitas = new RoundedTextField();
        txtIdentitas.setBounds(25, 118, 260, 36);
        kartuForm.add(txtIdentitas);

        JLabel lblCheckin = new JLabel("Check-in (yyyy-MM-dd)");
        lblCheckin.setForeground(Tema.TEKS_PUTIH);
        lblCheckin.setFont(Tema.FONT_LABEL_BOLD);
        lblCheckin.setBounds(300, 95, 200, 20);
        kartuForm.add(lblCheckin);
        txtCheckin = new RoundedTextField(LocalDate.now().toString());
        txtCheckin.setBounds(300, 118, 170, 36);
        txtCheckin.addActionListener(e -> hitungTotal());
        kartuForm.add(txtCheckin);

        JLabel lblCheckout = new JLabel("Check-out (yyyy-MM-dd)");
        lblCheckout.setForeground(Tema.TEKS_PUTIH);
        lblCheckout.setFont(Tema.FONT_LABEL_BOLD);
        lblCheckout.setBounds(485, 95, 200, 20);
        kartuForm.add(lblCheckout);
        txtCheckout = new RoundedTextField(LocalDate.now().plusDays(1).toString());
        txtCheckout.setBounds(485, 118, 170, 36);
        txtCheckout.addActionListener(e -> hitungTotal());
        kartuForm.add(txtCheckout);

        JLabel lblTotal = new JLabel("Total Bayar");
        lblTotal.setForeground(Tema.TEKS_PUTIH);
        lblTotal.setFont(Tema.FONT_LABEL_BOLD);
        lblTotal.setBounds(670, 95, 150, 20);
        kartuForm.add(lblTotal);
        txtTotal = new RoundedTextField();
        txtTotal.setBounds(670, 118, 195, 36);
        txtTotal.setEditable(false);
        txtTotal.setBackground(Tema.NAVY_BG);
        txtTotal.setForeground(Tema.STANDARD);
        kartuForm.add(txtTotal);

        RoundedButton btnHitung = new RoundedButton("\uD83D\uDD01 Hitung Total", RoundedButton.Gaya.SEKUNDER);
        btnHitung.setBounds(670, 160, 195, 32);
        btnHitung.addActionListener(e -> hitungTotal());
        kartuForm.add(btnHitung);

        // ==== Tombol Aksi ====
        RoundedButton btnTambah = new RoundedButton("+ Tambah", RoundedButton.Gaya.PRIMER);
        btnTambah.setBounds(25, 195, 120, 34);
        btnTambah.addActionListener(e -> tambahData());
        kartuForm.add(btnTambah);

        RoundedButton btnUbah = new RoundedButton("\u270E Ubah", RoundedButton.Gaya.SEKUNDER);
        btnUbah.setBounds(155, 195, 110, 34);
        btnUbah.addActionListener(e -> ubahData());
        kartuForm.add(btnUbah);

        RoundedButton btnHapus = new RoundedButton("\uD83D\uDDD1 Hapus", RoundedButton.Gaya.DANGER);
        btnHapus.setBounds(275, 195, 110, 34);
        btnHapus.addActionListener(e -> hapusData());
        kartuForm.add(btnHapus);

        RoundedButton btnBersih = new RoundedButton("Bersihkan", RoundedButton.Gaya.NETRAL);
        btnBersih.setBounds(395, 195, 130, 34);
        btnBersih.addActionListener(e -> bersihkanForm());
        kartuForm.add(btnBersih);

        RoundedButton btnCetak = new RoundedButton("\uD83D\uDDA8 Cetak Struk", RoundedButton.Gaya.SPESIAL);
        btnCetak.setBounds(670, 195, 195, 34);
        btnCetak.addActionListener(e -> cetakStruk());
        kartuForm.add(btnCetak);

        // ==== Tabel ====
        model = new DefaultTableModel(new Object[]{
                "ID", "No. Kamar", "Nama Tamu", "Identitas", "Check-in", "Check-out",
                "Malam", "Total Bayar", "Status", "id_kamar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabel = new JTable(model);
        Tema.gayakanTabel(tabel);
        tabel.getColumnModel().getColumn(7).setCellRenderer(new CurrencyCellRenderer());
        tabel.getColumnModel().getColumn(8).setCellRenderer(new BadgeCellRenderer(Tema::warnaStatusBayar));
        tabel.removeColumn(tabel.getColumnModel().getColumn(9)); // sembunyikan id_kamar
        tabel.getSelectionModel().addListSelectionListener(e -> isiFormDariTabel());

        // lebar kolom disesuaikan proporsional biar tidak terpotong/berantakan
        tabel.getColumnModel().getColumn(0).setPreferredWidth(40);   // ID
        tabel.getColumnModel().getColumn(1).setPreferredWidth(90);   // No. Kamar
        tabel.getColumnModel().getColumn(2).setPreferredWidth(130);  // Nama Tamu
        tabel.getColumnModel().getColumn(3).setPreferredWidth(120);  // Identitas
        tabel.getColumnModel().getColumn(4).setPreferredWidth(95);   // Check-in
        tabel.getColumnModel().getColumn(5).setPreferredWidth(95);   // Check-out
        tabel.getColumnModel().getColumn(6).setPreferredWidth(55);   // Malam
        tabel.getColumnModel().getColumn(7).setPreferredWidth(150);  // Total Bayar
        tabel.getColumnModel().getColumn(8).setPreferredWidth(100);  // Status
        tabel.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBounds(25, 320, 890, 300);
        scroll.getViewport().setBackground(Tema.NAVY_CARD);
        panel.add(scroll);

        muatDaftarKamar();
        tampilkanData();
    }

    /** Ambil daftar kamar dari tbl_kamar untuk mengisi combo box (relasi antar tabel) */
    private void muatDaftarKamar() {
        cboKamar.removeAllItems();
        mapKamar.clear();
        hargaKamar.clear();
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_kamar ORDER BY no_kamar");
            while (rs.next()) {
                int id = rs.getInt("id_kamar");
                String label = rs.getString("no_kamar") + " - " + rs.getString("tipe_kamar")
                        + " (Rp " + String.format("%,.0f", rs.getDouble("harga")) + ")";
                cboKamar.addItem(label);
                mapKamar.put(label, id);
                hargaKamar.put(id, rs.getDouble("harga"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data kamar: " + e.getMessage());
        }
    }

    private void hitungTotal() {
        try {
            String labelKamar = (String) cboKamar.getSelectedItem();
            if (labelKamar == null) return;
            int idKamar = mapKamar.get(labelKamar);
            double harga = hargaKamar.get(idKamar);

            LocalDate checkin = LocalDate.parse(txtCheckin.getText().trim());
            LocalDate checkout = LocalDate.parse(txtCheckout.getText().trim());
            long malam = ChronoUnit.DAYS.between(checkin, checkout);
            if (malam <= 0) malam = 1;

            double total = malam * harga;
            txtTotal.setText(String.valueOf(total));
        } catch (Exception e) {
            // format tanggal belum lengkap, abaikan sampai user selesai mengetik
        }
    }

    private void tampilkanData() {
        model.setRowCount(0);
        String sql = "SELECT r.*, k.no_kamar FROM tbl_reservasi r "
                + "JOIN tbl_kamar k ON r.id_kamar = k.id_kamar ORDER BY r.id_reservasi DESC";
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_reservasi"),
                        rs.getString("no_kamar"),
                        rs.getString("nama_tamu"),
                        rs.getString("no_identitas"),
                        rs.getDate("tgl_checkin").toString(),
                        rs.getDate("tgl_checkout").toString(),
                        rs.getInt("jumlah_malam"),
                        rs.getDouble("total_bayar"),
                        rs.getString("status_bayar"),
                        rs.getInt("id_kamar")
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
        txtNama.setText(model.getValueAt(baris, 2).toString());
        txtIdentitas.setText(model.getValueAt(baris, 3).toString());
        txtCheckin.setText(model.getValueAt(baris, 4).toString());
        txtCheckout.setText(model.getValueAt(baris, 5).toString());
        txtTotal.setText(model.getValueAt(baris, 7).toString());
        cboStatusBayar.setSelectedItem(model.getValueAt(baris, 8).toString());

        int idKamar = (int) model.getValueAt(baris, 9);
        for (Map.Entry<String, Integer> entry : mapKamar.entrySet()) {
            if (entry.getValue() == idKamar) {
                cboKamar.setSelectedItem(entry.getKey());
                break;
            }
        }
    }

    private boolean validasiInput() {
        if (txtNama.getText().trim().isEmpty() || txtIdentitas.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tamu dan identitas wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            LocalDate in = LocalDate.parse(txtCheckin.getText().trim());
            LocalDate out = LocalDate.parse(txtCheckout.getText().trim());
            if (!out.isAfter(in)) {
                JOptionPane.showMessageDialog(this, "Tanggal check-out harus setelah check-in!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Format tanggal harus yyyy-MM-dd, contoh: 2026-07-01",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void tambahData() {
        if (!validasiInput()) return;
        hitungTotal();
        String labelKamar = (String) cboKamar.getSelectedItem();
        int idKamar = mapKamar.get(labelKamar);
        LocalDate in = LocalDate.parse(txtCheckin.getText().trim());
        LocalDate out = LocalDate.parse(txtCheckout.getText().trim());
        long malam = ChronoUnit.DAYS.between(in, out);

        String sql = "INSERT INTO tbl_reservasi (id_kamar, nama_tamu, no_identitas, tgl_checkin, "
                + "tgl_checkout, jumlah_malam, total_bayar, status_bayar) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setInt(1, idKamar);
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, txtIdentitas.getText().trim());
            ps.setDate(4, Date.valueOf(in));
            ps.setDate(5, Date.valueOf(out));
            ps.setInt(6, (int) malam);
            ps.setDouble(7, Double.parseDouble(txtTotal.getText()));
            ps.setString(8, (String) cboStatusBayar.getSelectedItem());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservasi berhasil ditambahkan.");
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
        hitungTotal();
        String labelKamar = (String) cboKamar.getSelectedItem();
        int idKamar = mapKamar.get(labelKamar);
        LocalDate in = LocalDate.parse(txtCheckin.getText().trim());
        LocalDate out = LocalDate.parse(txtCheckout.getText().trim());
        long malam = ChronoUnit.DAYS.between(in, out);

        String sql = "UPDATE tbl_reservasi SET id_kamar=?, nama_tamu=?, no_identitas=?, tgl_checkin=?, "
                + "tgl_checkout=?, jumlah_malam=?, total_bayar=?, status_bayar=? WHERE id_reservasi=?";
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setInt(1, idKamar);
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, txtIdentitas.getText().trim());
            ps.setDate(4, Date.valueOf(in));
            ps.setDate(5, Date.valueOf(out));
            ps.setInt(6, (int) malam);
            ps.setDouble(7, Double.parseDouble(txtTotal.getText()));
            ps.setString(8, (String) cboStatusBayar.getSelectedItem());
            ps.setInt(9, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservasi berhasil diubah.");
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
                "Yakin ingin menghapus data reservasi ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        String sql = "DELETE FROM tbl_reservasi WHERE id_reservasi=?";
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data reservasi berhasil dihapus.");
            bersihkanForm();
            tampilkanData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtIdentitas.setText("");
        txtCheckin.setText(LocalDate.now().toString());
        txtCheckout.setText(LocalDate.now().plusDays(1).toString());
        txtTotal.setText("");
        if (cboKamar.getItemCount() > 0) cboKamar.setSelectedIndex(0);
        cboStatusBayar.setSelectedIndex(0);
        tabel.clearSelection();
    }

    /**
     * FITUR TAMBAHAN (opsional untuk nilai A):
     * Cetak struk/invoice reservasi dengan format resmi (kop hotel, tanggal cetak, kasir, dll).
     */
    private void cetakStruk() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data reservasi pada tabel terlebih dahulu!");
            return;
        }
        int idReservasi = Integer.parseInt(txtId.getText());

        // ambil data terbaru langsung dari database (join ke tbl_kamar untuk tipe & harga)
        String sql = "SELECT r.*, k.no_kamar, k.tipe_kamar, k.harga FROM tbl_reservasi r "
                + "JOIN tbl_kamar k ON r.id_kamar = k.id_kamar WHERE r.id_reservasi = ?";

        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setInt(1, idReservasi);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Data reservasi tidak ditemukan.");
                return;
            }

            DateTimeFormatter fmtTanggal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter fmtWaktuCetak = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            String noKamar = rs.getString("no_kamar");
            String tipeKamar = rs.getString("tipe_kamar");
            double harga = rs.getDouble("harga");
            String nama = rs.getString("nama_tamu");
            String identitas = rs.getString("no_identitas");
            LocalDate checkin = rs.getDate("tgl_checkin").toLocalDate();
            LocalDate checkout = rs.getDate("tgl_checkout").toLocalDate();
            int malam = rs.getInt("jumlah_malam");
            double total = rs.getDouble("total_bayar");
            String statusBayar = rs.getString("status_bayar");

            String garisTebal = "=".repeat(50);
            String garisTipis = "-".repeat(50);

            StringBuilder sb = new StringBuilder();
            sb.append(garisTebal).append("\n");
            sb.append(tengahkan("YUREA GRAND HOTEL", 50)).append("\n");
            sb.append(tengahkan(ALAMAT_HOTEL, 50)).append("\n");
            sb.append(tengahkan("Telp " + TELP_HOTEL, 50)).append("\n");
            sb.append(garisTebal).append("\n");
            sb.append(tengahkan("STRUK RESERVASI HOTEL", 50)).append("\n");
            sb.append(garisTebal).append("\n");
            sb.append(baris("No. Reservasi", String.valueOf(idReservasi)));
            sb.append(baris("Tanggal Cetak", LocalDateTime.now().format(fmtWaktuCetak)));
            sb.append(baris("Kasir", namaKasir));
            sb.append(baris("Nama Tamu", nama));
            sb.append(baris("No. Identitas", identitas));
            sb.append(baris("No. Kamar", noKamar));
            sb.append(baris("Tipe Kamar", tipeKamar + " Room"));
            sb.append(baris("Check-in", checkin.format(fmtTanggal)));
            sb.append(baris("Check-out", checkout.format(fmtTanggal)));
            sb.append(baris("Jumlah Malam", String.valueOf(malam)));
            sb.append(garisTipis).append("\n");
            sb.append(baris("Harga per Malam", "Rp " + String.format("%,.0f", harga)));
            sb.append(baris("Subtotal", "Rp " + String.format("%,.0f", total)));
            sb.append(garisTipis).append("\n");
            sb.append(baris("TOTAL BAYAR", "Rp " + String.format("%,.0f", total)));
            sb.append(baris("Metode Bayar", "Tunai"));
            sb.append(baris("Status Bayar", statusBayar));
            sb.append(garisTebal).append("\n");
            sb.append(tengahkan("Terima kasih telah memilih", 50)).append("\n");
            sb.append(tengahkan("YUREA GRAND HOTEL.", 50)).append("\n");
            sb.append(tengahkan("\"Experience Comfort Beyond Expectations.\"", 50)).append("\n");
            sb.append(tengahkan("Semoga pengalaman menginap Anda", 50)).append("\n");
            sb.append(tengahkan("menyenangkan dan berkesan.", 50)).append("\n");
            sb.append(tengahkan("Kami menantikan kedatangan Anda kembali.", 50)).append("\n");
            sb.append(tengahkan("Safe Journey & See You Again!", 50)).append("\n");
            sb.append(garisTebal).append("\n");
            sb.append("* Simpan struk ini sebagai bukti pembayaran.\n");

            JTextArea area = new JTextArea(sb.toString());
            area.setFont(new Font("Monospaced", Font.PLAIN, 13));
            area.setEditable(false);

            int pilihan = JOptionPane.showConfirmDialog(this, new JScrollPane(area),
                    "Preview Struk Reservasi", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (pilihan == JOptionPane.OK_OPTION) {
                try {
                    area.print(); // memicu dialog print bawaan Java
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Gagal mencetak: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data struk: " + e.getMessage());
        }
    }

    /** Baris "label : nilai" rata kiri sepanjang 18 karakter label, dipakai di struk. */
    private String baris(String label, String nilai) {
        return String.format("%-18s: %s", label, nilai) + "\n";
    }

    /** Menengahkan teks dalam lebar kolom tertentu (untuk kop & penutup struk). */
    private String tengahkan(String teks, int lebar) {
        if (teks.length() >= lebar) return teks;
        int spasi = (lebar - teks.length()) / 2;
        return " ".repeat(spasi) + teks;
    }
}

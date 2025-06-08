package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DlgUnosOpreme extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    private JTextField txtUnosNaziva;
    private JTextField txtUnosKolicine;
    private JTextField txtCijene;
    private JComboBox<String> comboTipArtikla;
    private JComboBox<String> comboTereni;

    private ArrayList<Integer> tipArtiklaIDs = new ArrayList<>();
    private ArrayList<Integer> tereniIDs = new ArrayList<>();

    public static void main(String[] args) {
        try {
            DlgUnosOpreme dialog = new DlgUnosOpreme();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DlgUnosOpreme() {
        setTitle("Unos opreme");
        setBounds(100, 100, 600, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNaziv = new JLabel("Naziv artikla:");
        lblNaziv.setBounds(10, 10, 150, 25);
        contentPanel.add(lblNaziv);

        txtUnosNaziva = new JTextField();
        txtUnosNaziva.setBounds(170, 10, 200, 25);
        contentPanel.add(txtUnosNaziva);

        JLabel lblKolicina = new JLabel("Dostupna količina:");
        lblKolicina.setBounds(10, 45, 150, 25);
        contentPanel.add(lblKolicina);

        txtUnosKolicine = new JTextField();
        txtUnosKolicine.setBounds(170, 45, 200, 25);
        contentPanel.add(txtUnosKolicine);

        JLabel lblCijena = new JLabel("Cijena po danu:");
        lblCijena.setBounds(10, 80, 150, 25);
        contentPanel.add(lblCijena);

        txtCijene = new JTextField();
        txtCijene.setBounds(170, 80, 200, 25);
        contentPanel.add(txtCijene);

        JLabel lblTip = new JLabel("Tip artikla:");
        lblTip.setBounds(10, 115, 150, 25);
        contentPanel.add(lblTip);

        comboTipArtikla = new JComboBox<>();
        comboTipArtikla.setBounds(170, 115, 200, 25);
        contentPanel.add(comboTipArtikla);

        JLabel lblTeren = new JLabel("Teren:");
        lblTeren.setBounds(10, 150, 150, 25);
        contentPanel.add(lblTeren);

        comboTereni = new JComboBox<>();
        comboTereni.setBounds(170, 150, 200, 25);
        contentPanel.add(comboTereni);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Spremi");
        okButton.addActionListener(e -> unesiArtikl());
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Odustani");
        cancelButton.addActionListener(e -> dispose());
        buttonPane.add(cancelButton);

        ucitajTipoveArtikla();
        ucitajTerene();
    }

    private void ucitajTipoveArtikla() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sifra_tipa_artikla, tip_artikla FROM tip_artikla");

            while (rs.next()) {
                tipArtiklaIDs.add(rs.getInt("sifra_tipa_artikla"));
                comboTipArtikla.addItem(rs.getString("tip_artikla"));
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška kod učitavanja tipova: " + ex.getMessage());
        }
    }

    private void ucitajTerene() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sifra_terena, naziv FROM Tereni");

            while (rs.next()) {
                tereniIDs.add(rs.getInt("sifra_terena"));
                comboTereni.addItem(rs.getString("naziv"));
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška kod učitavanja terena: " + ex.getMessage());
        }
    }

    private void unesiArtikl() {
        try {
            String naziv = txtUnosNaziva.getText();
            int kolicina = Integer.parseInt(txtUnosKolicine.getText());
            float cijena = Float.parseFloat(txtCijene.getText());
            int sifraTipa = tipArtiklaIDs.get(comboTipArtikla.getSelectedIndex());
            int sifraTerena = tereniIDs.get(comboTereni.getSelectedIndex());

            Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO artikli (naziv_artikla, dostupna_kolicina, cijena_dan, sifra_tipa_artikla, Sifra_terena) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, naziv);
            stmt.setInt(2, kolicina);
            stmt.setFloat(3, cijena);
            stmt.setInt(4, sifraTipa);
            stmt.setInt(5, sifraTerena);

            stmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Artikl uspješno unesen.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška kod unosa: " + ex.getMessage());
        }
    }
}

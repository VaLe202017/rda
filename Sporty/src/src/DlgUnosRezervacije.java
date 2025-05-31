package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

public class DlgUnosRezervacije extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JComboBox<String> korisnikCombo;
	private JComboBox<String> terenCombo;
	private JDateChooser datePocetak;
	private JDateChooser dateKraj;

	public static void main(String[] args) {
		try {
			DlgUnosRezervacije dialog = new DlgUnosRezervacije();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgUnosRezervacije() {
		setTitle("Unos nove rezervacije");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblKorisnik = new JLabel("Odaberi korisnika:");
		lblKorisnik.setBounds(10, 10, 150, 20);
		contentPanel.add(lblKorisnik);

		korisnikCombo = new JComboBox<>();
		korisnikCombo.setBounds(180, 10, 200, 25);
		contentPanel.add(korisnikCombo);

		JLabel lblTeren = new JLabel("Odaberi teren:");
		lblTeren.setBounds(10, 45, 150, 20);
		contentPanel.add(lblTeren);

		terenCombo = new JComboBox<>();
		terenCombo.setBounds(180, 45, 200, 25);
		contentPanel.add(terenCombo);

		JLabel lblDatumPocetka = new JLabel("Datum iznajmljivanja:");
		lblDatumPocetka.setBounds(10, 80, 150, 20);
		contentPanel.add(lblDatumPocetka);

		datePocetak = new JDateChooser();
		datePocetak.setBounds(180, 80, 200, 25);
		contentPanel.add(datePocetak);

		JLabel lblDatumZavrsetka = new JLabel("Datum vraćanja:");
		lblDatumZavrsetka.setBounds(10, 115, 150, 20);
		contentPanel.add(lblDatumZavrsetka);

		dateKraj = new JDateChooser();
		dateKraj.setBounds(180, 115, 200, 25);
		contentPanel.add(dateKraj);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Spremi");
		okButton.addActionListener(e -> unesiRezervaciju());
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Odustani");
		cancelButton.addActionListener(e -> dispose());
		buttonPane.add(cancelButton);

		ucitajKorisnike();
		ucitajTerene();
	}

	private void ucitajKorisnike() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT sifra_korisnika, ime_korisnika, prezime_korisnika FROM korisnik");

			while (rs.next()) {
				String ime = rs.getString("ime_korisnika");
				String prezime = rs.getString("prezime_korisnika");
				int sifraKorisnika= rs.getInt("sifra_korisnika");
				korisnikCombo.addItem(sifraKorisnika + " - " + ime + " " + prezime);
			}
			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška kod učitavanja korisnika: " + ex.getMessage());
		}
	}

	private void ucitajTerene() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT sifra_terena, naziv FROM Tereni");

			while (rs.next()) {
				int sifraTerena=rs.getInt("sifra_terena");
				String naziv = rs.getString("naziv");
				terenCombo.addItem(sifraTerena+" - "+naziv);
			}
			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška kod učitavanja terena: " + ex.getMessage());
		}
	}

	private void unesiRezervaciju() {
		try {
			if (korisnikCombo.getSelectedItem() == null || terenCombo.getSelectedItem() == null || datePocetak.getDate() == null || dateKraj.getDate() == null) {
				JOptionPane.showMessageDialog(this, "Molimo ispunite sva polja.");
				return;
			}

			int sifraKorisnika = Integer.parseInt(korisnikCombo.getSelectedItem().toString().split(" - ")[0]);
			int sifraTerena = Integer.parseInt(terenCombo.getSelectedItem().toString().split(" - ")[0]);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String datumPocetak = sdf.format(datePocetak.getDate());
			String datumKraj = sdf.format(dateKraj.getDate());

			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO rezervacije (sifra_korisnika, datum_iznajmljivanja, datum_vracanja, sifra_terena) VALUES (?, ?, ?, ?)");
			stmt.setInt(1, sifraKorisnika);
			stmt.setString(2, datumPocetak);
			stmt.setString(3, datumKraj);
			stmt.setInt(4, sifraTerena);
			stmt.executeUpdate();

			conn.close();
			JOptionPane.showMessageDialog(this, "Rezervacija uspješno unesena.");
			dispose();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška kod unosa: " + ex.getMessage());
		}
	}
}

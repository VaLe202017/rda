package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.LinkedHashMap;

public class DlgUnosTerena extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfNaziv;
	private JTextField tfLokacija;
	private JTextField tfRadnoVrijeme;
	private JComboBox<String> cbTereni;
	private final LinkedHashMap<String, Integer> terenMapa = new LinkedHashMap<>();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				DlgUnosTerena dialog = new DlgUnosTerena();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public DlgUnosTerena() {
		setTitle("Unos / Ažuriranje Terena");
		setBounds(100, 100, 450, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblOdabir = new JLabel("Odaberi teren:");
		lblOdabir.setBounds(10, 10, 100, 25);
		contentPanel.add(lblOdabir);

		cbTereni = new JComboBox<>();
		cbTereni.setBounds(120, 10, 250, 25);
		cbTereni.addItem("--- Odaberi teren ---");
		cbTereni.addActionListener(e -> prikaziDetaljeTerena());
		contentPanel.add(cbTereni);

		JLabel lblNaziv = new JLabel("Naziv:");
		lblNaziv.setBounds(10, 60, 100, 25);
		contentPanel.add(lblNaziv);

		tfNaziv = new JTextField();
		tfNaziv.setBounds(120, 60, 250, 25);
		contentPanel.add(tfNaziv);

		JLabel lblLokacija = new JLabel("Lokacija:");
		lblLokacija.setBounds(10, 100, 100, 25);
		contentPanel.add(lblLokacija);

		tfLokacija = new JTextField();
		tfLokacija.setBounds(120, 100, 250, 25);
		contentPanel.add(tfLokacija);

		JLabel lblRadnoVrijeme = new JLabel("Radno vrijeme:");
		lblRadnoVrijeme.setBounds(10, 140, 100, 25);
		contentPanel.add(lblRadnoVrijeme);

		tfRadnoVrijeme = new JTextField();
		tfRadnoVrijeme.setBounds(120, 140, 250, 25);
		contentPanel.add(tfRadnoVrijeme);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnSpremi = new JButton("Spremi novi");
		btnSpremi.addActionListener(e -> unesiTeren());
		buttonPane.add(btnSpremi);

		JButton btnAzuriraj = new JButton("Ažuriraj");
		btnAzuriraj.addActionListener(e -> azurirajTeren());
		buttonPane.add(btnAzuriraj);

		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(e -> dispose());
		buttonPane.add(btnOdustani);

		ucitajTerene();
	}

	private void ucitajTerene() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");

			String sql = "SELECT sifra_terena, naziv FROM Tereni ORDER BY naziv";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			terenMapa.clear();
			cbTereni.removeAllItems();
			cbTereni.addItem("--- Odaberi teren ---");

			while (rs.next()) {
				int sifra = rs.getInt("sifra_terena");
				String naziv = rs.getString("naziv");
				if (naziv != null) {
					terenMapa.put(naziv, sifra);
					cbTereni.addItem(naziv);
				}
			}

			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška pri učitavanju terena: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void prikaziDetaljeTerena() {
		String odabraniNaziv = (String) cbTereni.getSelectedItem();
		if (odabraniNaziv == null || odabraniNaziv.equals("--- Odaberi teren ---")) {
			tfNaziv.setText("");
			tfLokacija.setText("");
			tfRadnoVrijeme.setText("");
			return;
		}

		try {
			int sifra = terenMapa.get(odabraniNaziv);
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");

			String sql = "SELECT * FROM Tereni WHERE sifra_terena = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sifra);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				tfNaziv.setText(rs.getString("naziv"));
				tfLokacija.setText(rs.getString("lokacija"));
				tfRadnoVrijeme.setText(rs.getString("radno_vrijeme"));
			}

			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void unesiTeren() {
		String naziv = tfNaziv.getText().trim();
		String lokacija = tfLokacija.getText().trim();
		String radnoVrijeme = tfRadnoVrijeme.getText().trim();

		if (naziv.isEmpty() || lokacija.isEmpty() || radnoVrijeme.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Sva polja moraju biti ispunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (String key : terenMapa.keySet()) {
			if (key != null && key.equalsIgnoreCase(naziv)) {
				JOptionPane.showMessageDialog(this, "Teren s tim nazivom već postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");

			String sql = "INSERT INTO Tereni (naziv, lokacija, radno_vrijeme) VALUES (?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, naziv);
			pstmt.setString(2, lokacija);
			pstmt.setString(3, radnoVrijeme);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				JOptionPane.showMessageDialog(this, "Teren uspješno unesen.");
				ucitajTerene();
				cbTereni.setSelectedItem(naziv);
			} else {
				JOptionPane.showMessageDialog(this, "Neuspješan unos terena.", "Greška", JOptionPane.ERROR_MESSAGE);
			}

			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

	private void azurirajTeren() {
		String stariNaziv = (String) cbTereni.getSelectedItem();
		if (stariNaziv == null || stariNaziv.equals("--- Odaberi teren ---")) {
			JOptionPane.showMessageDialog(this, "Odaberite teren za ažuriranje.", "Greška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int sifra = terenMapa.get(stariNaziv);
		String naziv = tfNaziv.getText().trim();
		String lokacija = tfLokacija.getText().trim();
		String radnoVrijeme = tfRadnoVrijeme.getText().trim();

		if (naziv.isEmpty() || lokacija.isEmpty() || radnoVrijeme.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Sva polja moraju biti ispunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (String key : terenMapa.keySet()) {
			if (key != null && key.equalsIgnoreCase(naziv) && terenMapa.get(key) != sifra) {
				JOptionPane.showMessageDialog(this, "Naziv terena već postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");

			String sql = "UPDATE Tereni SET naziv = ?, lokacija = ?, radno_vrijeme = ? WHERE sifra_terena = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, naziv);
			pstmt.setString(2, lokacija);
			pstmt.setString(3, radnoVrijeme);
			pstmt.setInt(4, sifra);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				JOptionPane.showMessageDialog(this, "Teren uspješno ažuriran.");
				ucitajTerene();
				cbTereni.setSelectedItem(naziv);
			} else {
				JOptionPane.showMessageDialog(this, "Neuspješno ažuriranje terena.", "Greška", JOptionPane.ERROR_MESSAGE);
			}

			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}

package src;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrikazTerena extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea taPrikazTerena;
	private JComboBox<String> cbLokacije;

	public static void main(String[] args) {
		try {
			PrikazTerena dialog = new PrikazTerena();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PrikazTerena() {
		setTitle("Prikaz terena");
		setBounds(100, 100, 500, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblLokacija = new JLabel("Odaberi lokaciju:");
		lblLokacija.setBounds(10, 10, 120, 25);
		contentPanel.add(lblLokacija);

		cbLokacije = new JComboBox<>();
		cbLokacije.setBounds(130, 10, 200, 25);
		contentPanel.add(cbLokacije);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 460, 250);
		contentPanel.add(scrollPane);

		taPrikazTerena = new JTextArea();
		taPrikazTerena.setEditable(false);
		scrollPane.setViewportView(taPrikazTerena);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnClose = new JButton("Zatvori");
		btnClose.addActionListener(e -> dispose());
		buttonPane.add(btnClose);

		// Učitaj lokacije i dodaj listener
		ucitajLokacije();
		cbLokacije.addActionListener(e -> prikaziTerenPoLokaciji((String) cbLokacije.getSelectedItem()));
	}

	private void ucitajLokacije() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" +
					"user=lvalenta&password=11");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT lokacija FROM Tereni ORDER BY lokacija");

			while (rs.next()) {
				cbLokacije.addItem(rs.getString("lokacija"));
			}

			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void prikaziTerenPoLokaciji(String lokacija) {
	    taPrikazTerena.setText(""); // Očisti prikaz

	    String upit = "SELECT T.naziv, T.lokacija, T.radno_vrijeme, COUNT(R.sifra_terena) AS broj_rezervacija " +
	                  "FROM Tereni T " +
	                  "LEFT JOIN rezervacije R ON T.sifra_terena = R.sifra_terena " +
	                  "WHERE T.lokacija = ? " +
	                  "GROUP BY T.naziv, T.lokacija, T.radno_vrijeme";

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(
	                "jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");

	        PreparedStatement pstmt = conn.prepareStatement(upit);
	        pstmt.setString(1, lokacija);

	        ResultSet rs = pstmt.executeQuery();

	        boolean imaRezultata = false;

	        while (rs.next()) {
	            imaRezultata = true;
	            String naziv = rs.getString("naziv");
	            String radnoVrijeme = rs.getString("radno_vrijeme");
	            int brojRezervacija = rs.getInt("broj_rezervacija");

	            taPrikazTerena.append("Naziv: " + naziv +
	                    ", Radno vrijeme: " + radnoVrijeme +
	                    ", Broj rezervacija: " + brojRezervacija + "\n");
	        }

	        if (!imaRezultata) {
	            taPrikazTerena.setText("Nema dostupnih terena za odabranu lokaciju.");
	        }

	        conn.close();
	    } catch (Exception ex) {
	        taPrikazTerena.setText("Greška: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	}
}

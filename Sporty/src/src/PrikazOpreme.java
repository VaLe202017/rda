package src;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrikazOpreme extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea taPrikazOpreme;
	private JComboBox<String> cbLokacije;

	public static void main(String[] args) {
		try {
			PrikazOpreme dialog = new PrikazOpreme();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PrikazOpreme() {
		setTitle("Prikaz opreme");
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

		taPrikazOpreme = new JTextArea();
		taPrikazOpreme.setEditable(false);
		scrollPane.setViewportView(taPrikazOpreme);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnZatvori = new JButton("Zatvori");
		btnZatvori.addActionListener(e -> dispose());
		buttonPane.add(btnZatvori);

		ucitajArtikle();
		cbLokacije.addActionListener(e -> prikaziOpremuPoLokaciji((String) cbLokacije.getSelectedItem()));
	}

	private void ucitajArtikle() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT naziv FROM Tereni ORDER BY naziv");

			while (rs.next()) {
				cbLokacije.addItem(rs.getString("naziv"));
			}

			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška kod učitavanja naziva terena: " + ex.getMessage());
		}
	}

	private void prikaziOpremuPoLokaciji(String teren) {
		taPrikazOpreme.setText("");
/*
		String upit = "SELECT " +
	            "a.naziv_artikla AS naziv_artikla, " +
	            "a.dostupna_kolicina, " +
	            "a.cijena_dan, " +
	            "t.naziv AS naziv_terena, " +
	            "tip.tip_artikla AS naziv_tipa, " +
	            "COUNT(DISTINCT a.sifra_terena) AS broj_po_terenu " +
	            "FROM artikli a " +
	            "LEFT JOIN Tereni t ON a.sifra_terena = t.sifra_terena " +
	            "LEFT JOIN tip_artikla tip ON a.sifra_tipa_artikla = tip.sifra_tipa_artikla " +
	            "WHERE a.naziv_artikla = ? ";
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
	        PreparedStatement pstmt = conn.prepareStatement(upit);
	        pstmt.setString(1, lokacija);

	        ResultSet rs = pstmt.executeQuery();
	        boolean imaRezultata = false;

	        while (rs.next()) {
	            imaRezultata = true;
	            String naziv_artikla = rs.getString("naziv_artikla");
	            String dostupna_kolicina = rs.getString("dostupna_kolicina");
	            String cijena_dan= rs.getString("cijena_dan");
	            String naziv_terena = rs.getString("naziv_terena");
	            String tip_artikla = rs.getString("naziv_tipa");
	            int sifra_terena = rs.getInt("broj_po_terenu");

	            taPrikazOpreme.append("Naziv artikla: " + naziv_artikla +
	                    ", Dostupna kolicina: " + dostupna_kolicina+
	                    ", Cijena na dan: " + cijena_dan+
	                    ", Naziv terena: " + naziv_terena +
	                    ", Tip artikla: " + tip_artikla +
	                    ", Broj opreme po terenu: " + sifra_terena + "\n");
	        }

	        if (!imaRezultata) {
	            taPrikazOpreme.setText("Nema opreme za odabranu lokaciju.");
	        }

	        conn.close();
	    } catch (Exception ex) {
	        taPrikazOpreme.setText("Greška: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	    */
		String upit = "SELECT \r\n"
				+ "    a.naziv_artikla AS naziv_opreme,\r\n"
				+ "    tip.tip_artikla AS tip_opreme,\r\n"
				+ "    t.lokacija AS lokacija_terena,\r\n"
				+ "    COUNT(DISTINCT s.sifra_narudzbe) AS broj_rezervacija\r\n"
				+ "FROM \r\n"
				+ "    artikli a\r\n"
				+ "LEFT JOIN \r\n"
				+ "    tip_artikla tip ON a.sifra_tipa_artikla = tip.sifra_tipa_artikla\r\n"
				+ "LEFT JOIN \r\n"
				+ "    stavke_narudzbe s ON a.sifra_artikla = s.sifra_artikla\r\n"
				+ "LEFT JOIN \r\n"
				+ "    rezervacije r ON s.sifra_narudzbe = r.sifra_narudzbe\r\n"
				+ "LEFT JOIN \r\n"
				+ "    Tereni t ON a.Sifra_terena = t.sifra_terena\r\n"
				+ "WHERE \r\n"
				+ "    t.naziv = ?\r\n"
				+ "GROUP BY \r\n"
				+ "    a.sifra_artikla, a.naziv_artikla, tip.tip_artikla, t.lokacija;\r\n"
				+ "";

  try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = DriverManager.getConnection(
          "jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");

      PreparedStatement pstmt = conn.prepareStatement(upit);
      pstmt.setString(1, teren);
      ResultSet rs = pstmt.executeQuery();

      boolean imaRezultata = false;

      while (rs.next()) {
          imaRezultata = true;
          String naziv = rs.getString("naziv_opreme");
          String tip = rs.getString("tip_opreme");
          String lokacija = rs.getString("lokacija_terena");
          int brojRezervacija = rs.getInt("broj_rezervacija");

          taPrikazOpreme.append("Naziv: " + naziv +
                  ", Tip: " + (tip != null ? tip : "Nepoznat") +
                  ", Lokacija: " + lokacija +
                  ", Broj rezervacija: " + brojRezervacija + "\n");
      }

      if (!imaRezultata) {
          taPrikazOpreme.setText("Nema opreme za odabrani teren.");
      }

      conn.close();
  } catch (Exception ex) {
      taPrikazOpreme.setText("Greška: " + ex.getMessage());
      ex.printStackTrace();
  }
	}
}

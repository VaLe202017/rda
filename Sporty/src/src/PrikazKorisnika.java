package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class PrikazKorisnika extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea taPrikazKorisnika;

	public static void main(String[] args) {
		try {
			PrikazKorisnika dialog = new PrikazKorisnika();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PrikazKorisnika() {
		setTitle("Prikaz korisnika s rezervacijama");
		setBounds(100, 100, 700, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 660, 280);
		contentPanel.add(scrollPane);

		taPrikazKorisnika = new JTextArea();
		taPrikazKorisnika.setEditable(false);
		scrollPane.setViewportView(taPrikazKorisnika);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		prikazSlozenogUpita();
	}

	public void prikazSlozenogUpita() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" + "user=lvalenta&password=11");
			Statement stmt = conn.createStatement();
			String upit = """
					SELECT 
						k.sifra_korisnika,
						k.ime_korisnika,
						k.prezime_korisnika,
						k.broj_telefona_korisnika,
						k.email_korisnika,
						COUNT(r.sifra_narudzbe) AS broj_rezervacija
					FROM 
						korisnik k
					LEFT JOIN 
						rezervacije r ON k.sifra_korisnika = r.sifra_korisnika
					GROUP BY 
						k.sifra_korisnika, k.ime_korisnika, k.prezime_korisnika, 
						k.broj_telefona_korisnika, k.email_korisnika
					ORDER BY 
						k.sifra_korisnika
				""";

			ResultSet rs = stmt.executeQuery(upit);

			while (rs.next()) {
				String ispis = String.format(
						"%d. %s %s | Tel: %s | Email: %s\n  Broj rezervacija: %d\n\n",
						rs.getInt("sifra_korisnika"),
						rs.getString("ime_korisnika"),
						rs.getString("prezime_korisnika"),
						rs.getString("broj_telefona_korisnika"),
						rs.getString("email_korisnika"),
						rs.getInt("broj_rezervacija")
					);

				taPrikazKorisnika.append(ispis);
			}

			conn.close();
		} catch (Exception ex) {
			taPrikazKorisnika.setText("Greška: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}

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
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DlgPrikazRezervacije extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea taPrikazRezervacija;
	JDateChooser dateChooser;

	public static void main(String[] args) {
		try {
			DlgPrikazRezervacije dialog = new DlgPrikazRezervacije();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgPrikazRezervacije() {
		setBounds(100, 100, 550, 400);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 536, 300);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 516, 240);
		contentPanel.add(scrollPane);

		taPrikazRezervacija = new JTextArea();
		scrollPane.setViewportView(taPrikazRezervacija);

		// JCalendar komponenta
		dateChooser = new JDateChooser();
		dateChooser.setBounds(10, 260, 150, 25);
		contentPanel.add(dateChooser);

		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 300, 536, 31);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		JButton btnFiltriraj = new JButton("Filtriraj po datumu");
		btnFiltriraj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date selectedDate = dateChooser.getDate();
				if (selectedDate != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String datum = sdf.format(selectedDate);
					prikazRezervacijaPoDatumu(datum);
				}
			}
		});
		buttonPane.add(btnFiltriraj);

		prikazSveRezervacije();
	}

	public void prikazSveRezervacije() {
		taPrikazRezervacija.setText("");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
			Statement stmt = conn.createStatement();

			String upit = """
				SELECT r.sifra_narudzbe, k.ime_korisnika, k.prezime_korisnika,
					   t.naziv AS naziv_terena, r.datum_iznajmljivanja,
					   r.datum_vracanja, r.ukupan_iznos
				FROM rezervacije r
				JOIN korisnik k ON r.sifra_korisnika = k.sifra_korisnika
				LEFT JOIN Tereni t ON r.sifra_terena = t.sifra_terena
			""";

			ResultSet rs = stmt.executeQuery(upit);

			while (rs.next()) {
				int sifra = rs.getInt("sifra_narudzbe");
				String ime = rs.getString("ime_korisnika");
				String prezime = rs.getString("prezime_korisnika");
				String teren = rs.getString("naziv_terena");
				String datIzn = rs.getString("datum_iznajmljivanja");
				String datVra = rs.getString("datum_vracanja");
				float iznos = rs.getFloat("ukupan_iznos");

				taPrikazRezervacija.append("Narudzba #" + sifra + ": " + ime + " " + prezime +
					", Teren: " + teren + ", Od: " + datIzn + " do: " + datVra +
					", Iznos: " + iznos + " EUR\n");
			}
			conn.close();
		} catch (Exception ex) {
			System.out.println("Greška: " + ex.getMessage());
		}
	}

	public void prikazRezervacijaPoDatumu(String datum) {
		taPrikazRezervacija.setText("");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
			Statement stmt = conn.createStatement();

			String upit = """
				SELECT r.sifra_narudzbe, k.ime_korisnika, k.prezime_korisnika,
					   t.naziv AS naziv_terena, r.datum_iznajmljivanja,
					   r.datum_vracanja, r.ukupan_iznos
				FROM rezervacije r
				JOIN korisnik k ON r.sifra_korisnika = k.sifra_korisnika
				LEFT JOIN Tereni t ON r.sifra_terena = t.sifra_terena
				WHERE r.datum_iznajmljivanja = '""" + datum + "'";

			ResultSet rs = stmt.executeQuery(upit);

			while (rs.next()) {
				int sifra = rs.getInt("sifra_narudzbe");
				String ime = rs.getString("ime_korisnika");
				String prezime = rs.getString("prezime_korisnika");
				String teren = rs.getString("naziv_terena");
				String datIzn = rs.getString("datum_iznajmljivanja");
				String datVra = rs.getString("datum_vracanja");
				float iznos = rs.getFloat("ukupan_iznos");

				taPrikazRezervacija.append("Narudzba #" + sifra + ": " + ime + " " + prezime +
					", Teren: " + teren + ", Od: " + datIzn + " do: " + datVra +
					", Iznos: " + iznos + " EUR\n");
			}
			conn.close();
		} catch (Exception ex) {
			System.out.println("Greška: " + ex.getMessage());
		}
	}
}

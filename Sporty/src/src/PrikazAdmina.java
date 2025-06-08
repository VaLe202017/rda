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

public class PrikazAdmina extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea taPrikazAdmina;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PrikazAdmina dialog = new PrikazAdmina();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PrikazAdmina() {
		setTitle("Prikaz Admina");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 414, 206);
			contentPanel.add(scrollPane);
			{
				taPrikazAdmina = new JTextArea();
				scrollPane.setViewportView(taPrikazAdmina);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		PrikazAdmina();
	}
	public void PrikazAdmina() {
		try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" + "user=lvalenta&password=11");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Admin");
				while (rs.next()) {
					int id =rs.getInt(1);
					String user = rs.getString(2);
					String pass = rs.getString(3);

						
					taPrikazAdmina.append("ID: "+id+" |username: "+user+" |password: "+pass+"\n");
				}
				taPrikazAdmina.append("\n=== REZERVACIJE ===\n");

		        // Prikaz rezervacija s korisnikom, terenom i artiklima
		        String upit = "SELECT r.sifra_narudzbe, r.datum_iznajmljivanja, r.datum_vracanja, r.ukupan_iznos, " +
		                      "k.ime_korisnika, k.prezime_korisnika, t.naziv AS naziv_terena, a.naziv_artikla " +
		                      "FROM rezervacije r " +
		                      "JOIN korisnik k ON r.sifra_korisnika = k.sifra_korisnika " +
		                      "LEFT JOIN Tereni t ON r.sifra_terena = t.sifra_terena " +
		                      "LEFT JOIN stavke_narudzbe s ON r.sifra_narudzbe = s.sifra_narudzbe " +
		                      "LEFT JOIN artikli a ON s.sifra_artikla = a.sifra_artikla " +
		                      "ORDER BY r.sifra_narudzbe";

		        rs = stmt.executeQuery(upit);

		        int zadnjaNarudzba = -1;
		        while (rs.next()) {
		            int sifraNarudzbe = rs.getInt("sifra_narudzbe");
		            String datumOd = rs.getString("datum_iznajmljivanja");
		            String datumDo = rs.getString("datum_vracanja");
		            float iznos = rs.getFloat("ukupan_iznos");
		            String ime = rs.getString("ime_korisnika");
		            String prezime = rs.getString("prezime_korisnika");
		            String teren = rs.getString("naziv_terena");
		            String artikl = rs.getString("naziv_artikla");

		            if (sifraNarudzbe != zadnjaNarudzba) {
		                if (zadnjaNarudzba != -1) {
		                    taPrikazAdmina.append("\n");
		                }
		                taPrikazAdmina.append("Narudžba #" + sifraNarudzbe +
		                        " | Datum: " + datumOd + " - " + datumDo +
		                        " | Iznos: " + iznos + "\n");
		                taPrikazAdmina.append("Korisnik: " + ime + " " + prezime + "\n");
		                taPrikazAdmina.append("Teren: " + (teren != null ? teren : "Nepoznat") + "\n");
		                taPrikazAdmina.append("Artikli: " + (artikl != null ? artikl : "Nema stavki"));
		            } else {
		                taPrikazAdmina.append(", " + artikl);
		            }

		            zadnjaNarudzba = sifraNarudzbe;
		        }
				conn.close();
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}


}

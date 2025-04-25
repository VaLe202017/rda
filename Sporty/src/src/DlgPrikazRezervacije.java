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

public class DlgPrikazRezervacije extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea taPrikazRezervacija;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgPrikazRezervacije dialog = new DlgPrikazRezervacije();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgPrikazRezervacije() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 436, 234);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 10, 416, 214);
			contentPanel.add(scrollPane);
			{
				taPrikazRezervacija = new JTextArea();
				scrollPane.setViewportView(taPrikazRezervacija);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 232, 436, 31);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		prikazRezervacija();
	}
	public void prikazRezervacija() {
		try {
				Object newInstance = Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM narudzbe");
				while (rs.next()) {
					int sifra_narudzbe =rs.getInt(1);
					int sifra_korisnika = rs.getInt(2);
					String datum_iznajmljivanja = rs.getString(3);
					String datum_vracanja = rs.getString(4);
					int ukupanIznos = rs.getInt(5);
						
					taPrikazRezervacija.append("Sifra narudzbe: "+sifra_narudzbe+" Sifra korisnika: "+sifra_korisnika+" Datum iznajmljivanja: "+datum_iznajmljivanja+" Datum vracanja: "+datum_vracanja+" Ukupan iznos: "+ukupanIznos+"\n");
				}
				conn.close();
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}


}

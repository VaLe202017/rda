package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setTitle("Prikaz Korisnika");
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
				taPrikazKorisnika = new JTextArea();
				scrollPane.setViewportView(taPrikazKorisnika);
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		PrikazTerena();
	}
	public void PrikazTerena() {
		try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" + "user=lvalenta&password=11");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM korisnik");
				while (rs.next()) {
					int sifra_korisnika =rs.getInt(1);
					String ime_korisnika = rs.getString(2);
					String prezime_korisnika = rs.getString(3);
					String broj_telefona_korisnika = rs.getString(4);
					String email_korisnika = rs.getString(5);
						
					taPrikazKorisnika.append(sifra_korisnika+". "+ime_korisnika+" "+prezime_korisnika+" "+broj_telefona_korisnika+" "+email_korisnika+"\n");
				}
				conn.close();
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	
}

package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DlgUnosKorisnika extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textUnosImena;
	private JTextField textUnosPrezimena;
	private JTextField textUnosBroja;
	private JTextField textUnosEmail;

	public static void main(String[] args) {
		try {
			DlgUnosKorisnika dialog = new DlgUnosKorisnika();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgUnosKorisnika() {
		setTitle("Unos korisnika");
		setBounds(100, 100, 511, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblUnosImena = new JLabel("Unos imena");
		lblUnosImena.setBounds(101, 118, 123, 20);
		contentPanel.add(lblUnosImena);

		JLabel lblUnosPrezimena = new JLabel("Unos prezimena");
		lblUnosPrezimena.setBounds(101, 145, 123, 20);
		contentPanel.add(lblUnosPrezimena);

		JLabel lblUnosBroja = new JLabel("Unos broja telefona");
		lblUnosBroja.setBounds(101, 174, 123, 20);
		contentPanel.add(lblUnosBroja);
		
		JLabel lblUnosEmail = new JLabel("Unos e-maila");
		lblUnosEmail.setBounds(101, 204, 123, 20);
		contentPanel.add(lblUnosEmail);
		
		textUnosImena = new JTextField();
		textUnosImena.setBounds(234, 118, 150, 20);
		contentPanel.add(textUnosImena);
		textUnosImena.setColumns(10);
		
		textUnosPrezimena = new JTextField();
		textUnosPrezimena.setBounds(234, 145, 150, 20);
		contentPanel.add(textUnosPrezimena);
		textUnosPrezimena.setColumns(10);
		
		textUnosBroja = new JTextField();
		textUnosBroja.setBounds(234, 174, 150, 20);
		contentPanel.add(textUnosBroja);
		textUnosBroja.setColumns(10);
		
		textUnosEmail = new JTextField();
		textUnosEmail.setBounds(234, 204, 150, 20);
		contentPanel.add(textUnosEmail);
		textUnosEmail.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				ActionListener l = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ime_korisnika = textUnosImena.getText();
						String prezime_korisnika = textUnosPrezimena.getText();
						String broj_telefona_korisnika = textUnosBroja.getText();
						String email_korisnika = textUnosEmail.getText();
						System.out.println("Ime: " + ime_korisnika + ", Prezime: " + prezime_korisnika + ", Broj telefona: " + broj_telefona_korisnika + ", E-mail adresa: " + email_korisnika);

						try {
							Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
							Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" + "user=lvalenta&password=11");
							PreparedStatement stmt = conn.prepareStatement("INSERT INTO korisnik (ime_korisnika,prezime_korisnika,broj_telefona_korisnika,email_korisnika) VALUES(?,?,?,?)");
							stmt.setString(1, ime_korisnika);
							stmt.setString(2, prezime_korisnika);
							stmt.setString(3, broj_telefona_korisnika);
							stmt.setString(4, email_korisnika);
							stmt.execute();

							conn.close();
						} catch (Exception ex) {
							System.out.println(ex.toString());
						}
					}

				};
				okButton.addActionListener(l);

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
	}
}

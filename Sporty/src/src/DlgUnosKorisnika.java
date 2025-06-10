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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.*;

public class DlgUnosKorisnika extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textUnosImena;
	private JTextField textUnosPrezimena;
	private JTextField textUnosBroja;
	private JTextField textUnosEmail;
	private JComboBox<String> comboSpol;
	private JComboBox<String> comboTip;

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
		setBounds(100, 100, 520, 420);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblUnosImena = new JLabel("Unos imena:");
		lblUnosImena.setBounds(100, 40, 130, 20);
		contentPanel.add(lblUnosImena);

		JLabel lblUnosPrezimena = new JLabel("Unos prezimena:");
		lblUnosPrezimena.setBounds(100, 70, 130, 20);
		contentPanel.add(lblUnosPrezimena);

		JLabel lblUnosBroja = new JLabel("Unos broja telefona:");
		lblUnosBroja.setBounds(100, 100, 130, 20);
		contentPanel.add(lblUnosBroja);

		JLabel lblUnosEmail = new JLabel("Unos e-maila:");
		lblUnosEmail.setBounds(100, 130, 130, 20);
		contentPanel.add(lblUnosEmail);

		JLabel lblSpol = new JLabel("Odabir spola:");
		lblSpol.setBounds(100, 160, 130, 20);
		contentPanel.add(lblSpol);

		JLabel lblTip = new JLabel("Tip korisnika:");
		lblTip.setBounds(100, 190, 130, 20);
		contentPanel.add(lblTip);

		textUnosImena = new JTextField();
		textUnosImena.setBounds(240, 40, 150, 20);
		contentPanel.add(textUnosImena);

		textUnosPrezimena = new JTextField();
		textUnosPrezimena.setBounds(240, 70, 150, 20);
		contentPanel.add(textUnosPrezimena);

		textUnosBroja = new JTextField();
		textUnosBroja.setBounds(240, 100, 150, 20);
		contentPanel.add(textUnosBroja);

		textUnosEmail = new JTextField();
		textUnosEmail.setBounds(240, 130, 150, 20);
		contentPanel.add(textUnosEmail);

		comboSpol = new JComboBox<>(new String[]{"Muško", "Žensko", "Drugo"});
		comboSpol.setBounds(240, 160, 150, 20);
		contentPanel.add(comboSpol);

		comboTip = new JComboBox<>(new String[]{"Administrator", "Trener", "Član"});
		comboTip.setBounds(240, 190, 150, 20);
		contentPanel.add(comboTip);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Spremi");
		okButton.addActionListener((ActionEvent e) -> {
			String ime = textUnosImena.getText().trim();
			String prezime = textUnosPrezimena.getText().trim();
			String broj = textUnosBroja.getText().trim();
			String email = textUnosEmail.getText().trim();
			String spol = comboSpol.getSelectedItem().toString();
			String tip = comboTip.getSelectedItem().toString();
			System.out.println("IME: " + ime + ", PREZIME: " + prezime + ", TEL: " + broj + ", EMAIL: " + email + ", SPOL: " + spol + "TIP KORISNIKA: "+tip);

			try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");

				PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO korisnik (ime_korisnika, prezime_korisnika, broj_telefona_korisnika, email_korisnika, spol, tip_korisnika) VALUES (?, ?, ?, ?, ?, ?)"
				);
				stmt.setString(1, ime);
				stmt.setString(2, prezime);
				stmt.setString(3, broj);
				stmt.setString(4, email);
				stmt.setString(5, spol);
				stmt.setString(6, tip);
				stmt.executeUpdate();

				conn.close();
				JOptionPane.showMessageDialog(this, "Korisnik uspješno dodan.");
				dispose();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage());
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Odustani");
		cancelButton.addActionListener(e -> dispose());
		buttonPane.add(cancelButton);
	}
}

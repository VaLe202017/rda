package src;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DlgUnosTerena extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfNaziv;
	private JTextField tfLokacija;
	private JTextField tfRadnoVrijeme;

	public static void main(String[] args) {
		try {
			DlgUnosTerena dialog = new DlgUnosTerena();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgUnosTerena() {
		setTitle("Unos terena");
		setBounds(100, 100, 400, 260);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNaziv = new JLabel("Naziv:");
		lblNaziv.setBounds(10, 20, 100, 25);
		contentPanel.add(lblNaziv);

		tfNaziv = new JTextField();
		tfNaziv.setBounds(120, 20, 200, 25);
		contentPanel.add(tfNaziv);

		JLabel lblLokacija = new JLabel("Lokacija:");
		lblLokacija.setBounds(10, 60, 100, 25);
		contentPanel.add(lblLokacija);

		tfLokacija = new JTextField();
		tfLokacija.setBounds(120, 60, 200, 25);
		contentPanel.add(tfLokacija);

		JLabel lblRadnoVrijeme = new JLabel("Radno vrijeme:");
		lblRadnoVrijeme.setBounds(10, 100, 100, 25);
		contentPanel.add(lblRadnoVrijeme);

		tfRadnoVrijeme = new JTextField();
		tfRadnoVrijeme.setBounds(120, 100, 200, 25);
		contentPanel.add(tfRadnoVrijeme);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnSpremi = new JButton("OK");
		btnSpremi.addActionListener(e -> unesiTeren());
		buttonPane.add(btnSpremi);

		JButton btnOdustani = new JButton("Cancel");
		btnOdustani.addActionListener(e -> dispose());
		buttonPane.add(btnOdustani);
	}

	private void unesiTeren() {
		String naziv = tfNaziv.getText().trim();
		String lokacija = tfLokacija.getText().trim();
		String radnoVrijeme = tfRadnoVrijeme.getText().trim();

		if (naziv.isEmpty() || lokacija.isEmpty() || radnoVrijeme.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Sva polja moraju biti ispunjena.", "Greška", JOptionPane.ERROR_MESSAGE);
			return;
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
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Neuspješan unos terena.", "Greška", JOptionPane.ERROR_MESSAGE);
			}

			conn.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}

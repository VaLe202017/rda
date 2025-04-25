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

public class DlgUnosOpreme extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUnosNaziva;
	private JTextField txtUnosKolicine;
	private JTextField txtCijene;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgUnosOpreme dialog = new DlgUnosOpreme();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgUnosOpreme() {
		setTitle("Unos opreme");
		setBounds(100, 100, 583, 303);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Unos naziva artikla");
		lblNewLabel.setBounds(10, 31, 95, 35);
		contentPanel.add(lblNewLabel);

		JLabel lblUnosKolicine = new JLabel("Unos dostupne količine");
		lblUnosKolicine.setBounds(10, 95, 173, 35);
		contentPanel.add(lblUnosKolicine);

		JLabel lblCijene = new JLabel("Unos cijene/dan");
		lblCijene.setBounds(10, 162, 106, 35);
		contentPanel.add(lblCijene);

		txtUnosNaziva = new JTextField();
		txtUnosNaziva.setBounds(145, 38, 173, 19);
		contentPanel.add(txtUnosNaziva);
		txtUnosNaziva.setColumns(10);

		txtUnosKolicine = new JTextField();
		txtUnosKolicine.setColumns(10);
		txtUnosKolicine.setBounds(145, 102, 173, 19);
		contentPanel.add(txtUnosKolicine);

		txtCijene = new JTextField();
		txtCijene.setColumns(10);
		txtCijene.setBounds(145, 169, 173, 19);
		contentPanel.add(txtCijene);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				ActionListener l = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String naziv_artikla = txtUnosNaziva.getText();
						String dostupna_kolicina = txtUnosKolicine.getText();
						String cijena_dan = txtCijene.getText();
						System.out.println("Naziv: ," + naziv_artikla + " Količina: ," + dostupna_kolicina + " Cijena: ," + cijena_dan);

						try {
							Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
							Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" + "user=lvalenta&password=11");
							PreparedStatement stmt = conn.prepareStatement("INSERT INTO artikli (naziv_artikla," + "dostupna_kolicina," + "Cijena_dan) VALUES(?,?,?)");
							stmt.setString(1, naziv_artikla);
							stmt.setString(2, dostupna_kolicina);
							stmt.setString(3, cijena_dan);
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

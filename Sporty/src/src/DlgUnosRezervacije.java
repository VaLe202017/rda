package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class DlgUnosRezervacije extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUnosVrijemePoc;
	private JTextField txtUnosVrijemeZavr;
	private JTextField txtUnosIme;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgUnosRezervacije dialog = new DlgUnosRezervacije();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgUnosRezervacije() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		contentPanel.setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("140px"), ColumnSpec.decode("109px:grow"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("45px"),
						FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("45px"), },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, RowSpec.decode("19px"), FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));
		{
			JLabel lbUnosVremenaPoc = new JLabel("Unesite početno vrijeme");
			contentPanel.add(lbUnosVremenaPoc, "1, 4, center, center");
		}
		{
			txtUnosVrijemePoc = new JTextField();
			contentPanel.add(txtUnosVrijemePoc, "2, 4, fill, default");
			txtUnosVrijemePoc.setColumns(10);
		}
		{
			JLabel lblUnosZavrsnogVremena = new JLabel("Unesite završno vrijeme");
			contentPanel.add(lblUnosZavrsnogVremena, "1, 6, center, center");
		}
		{
			txtUnosVrijemeZavr = new JTextField();
			contentPanel.add(txtUnosVrijemeZavr, "2, 6, fill, default");
			txtUnosVrijemeZavr.setColumns(10);
		}
		{
			JLabel lblUnosImena = new JLabel("Unesite ime");
			contentPanel.add(lblUnosImena, "1, 8, center, center");
		}
		{
			txtUnosIme = new JTextField();
			contentPanel.add(txtUnosIme, "2, 8, fill, default");
			txtUnosIme.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String pocVrijeme = txtUnosVrijemePoc.getText();
						String zavrVrijeme = txtUnosVrijemeZavr.getText();
						String ime = txtUnosIme.getText();
						System.out.println(
								"Početno vrijeme: " + pocVrijeme + " Završno vrijeme: " + zavrVrijeme + " Ime: " + ime);

						try {
							Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
							Connection conn = DriverManager
									.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?user=lvalenta&password=11");
							PreparedStatement stmt = conn.prepareStatement(
									"INSERT INTO rezervacije (sifra_korisnika,datum_iznajmljivanja, datum_vracanja) VALUES(?,?,?)");
							stmt.setString(1, ime);
							stmt.setString(2, pocVrijeme);
							stmt.setString(3, zavrVrijeme);
							stmt.execute();

							conn.close();
						} catch (Exception ex) {
							System.out.println(ex.toString());
						}

					}
				});
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

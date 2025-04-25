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

public class PrikazTerena extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea taPrikazTerena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PrikazTerena dialog = new PrikazTerena();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PrikazTerena() {
		setTitle("Prikaz terena");
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
				taPrikazTerena = new JTextArea();
				scrollPane.setViewportView(taPrikazTerena);
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
		PrikazTerena();
	}
	public void PrikazTerena() {
		try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" + "user=lvalenta&password=11");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Tereni");
				while (rs.next()) {
					int id =rs.getInt(1);
					String naziv = rs.getString(2);
					String radno_vrijeme = rs.getString(3);
					String lokacija = rs.getString(4);

						
					taPrikazTerena.append(naziv+" "+radno_vrijeme+" "+lokacija+"\n");
				}
				conn.close();
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}


}

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

public class DlgUnosAdmina extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textUnosUser;
	private JTextField textUnosPassword;

	public static void main(String[] args) {
		try {
			DlgUnosAdmina dialog = new DlgUnosAdmina();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgUnosAdmina() {
		setTitle("Unos korisnika");
		setBounds(100, 100, 511, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblUnosUsername = new JLabel("Unos username");
		lblUnosUsername.setBounds(101, 118, 123, 20);
		contentPanel.add(lblUnosUsername);

		JLabel lblUnosPassword = new JLabel("Unos password");
		lblUnosPassword.setBounds(101, 145, 123, 20);
		contentPanel.add(lblUnosPassword);
		
		textUnosUser = new JTextField();
		textUnosUser.setBounds(234, 118, 150, 20);
		contentPanel.add(textUnosUser);
		textUnosUser.setColumns(10);
		
		textUnosPassword = new JTextField();
		textUnosPassword.setBounds(234, 145, 150, 20);
		contentPanel.add(textUnosPassword);
		textUnosPassword.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				ActionListener l = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String username = textUnosUser.getText();
						String password = textUnosPassword.getText();;
						System.out.println("Username: " + username + ", Password: " + password);

						try {
							Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
							Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/lvalenta?" + "user=lvalenta&password=11");
							PreparedStatement stmt = conn.prepareStatement("INSERT INTO Admin (username, password) VALUES(?,?)");
							stmt.setString(1, username);
							stmt.setString(2, password);
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

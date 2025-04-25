package src;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

public class Sporty {

	private JFrame frmUnos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sporty window = new Sporty();
					window.frmUnos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sporty() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUnos = new JFrame();
		frmUnos.setTitle("Unos ");
		frmUnos.setBounds(100, 100, 578, 483);
		frmUnos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnUnosTerena = new JButton("Unos terena");
		btnUnosTerena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgUnosTerena dlg = new DlgUnosTerena();
				dlg.setVisible(true);
			}
		});
		frmUnos.getContentPane().setLayout(new GridLayout(0, 3, 0, 0));
		frmUnos.getContentPane().add(btnUnosTerena);
		
		JLabel label_1 = new JLabel("");
		frmUnos.getContentPane().add(label_1);
		
		JButton btnPrikazTerena = new JButton("Prikaz terena");
		btnPrikazTerena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrikazTerena dlg = new PrikazTerena();
				dlg.setVisible(true);
			}
		});
		frmUnos.getContentPane().add(btnPrikazTerena);
		
		JLabel label_2 = new JLabel("");
		frmUnos.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("");
		frmUnos.getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("");
		frmUnos.getContentPane().add(label_4);
		
		JButton btnUnosOpreme = new JButton("Unos opreme");
		frmUnos.getContentPane().add(btnUnosOpreme);
		
		JLabel label = new JLabel("");
		frmUnos.getContentPane().add(label);
		
		JButton btnPrikazOpreme = new JButton("Prikaz opreme");
		btnPrikazOpreme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frmUnos.getContentPane().add(btnPrikazOpreme);
		
		JLabel label_5 = new JLabel("");
		frmUnos.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("");
		frmUnos.getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("");
		frmUnos.getContentPane().add(label_7);
		
		JButton btnUnosRezervacije = new JButton("Unos rezervacije");
		btnUnosRezervacije.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgUnosRezervacije dlgUnosRez = new DlgUnosRezervacije();
				dlgUnosRez.setVisible(true);

			}
		});
		frmUnos.getContentPane().add(btnUnosRezervacije);
		
		JLabel label_8 = new JLabel("");
		frmUnos.getContentPane().add(label_8);
		
		JButton btnPrikazRezervacije = new JButton("Prikaz rezervacije");
		btnPrikazRezervacije.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					DlgPrikazRezervacije dlgPrikazRezervacije = new DlgPrikazRezervacije();
					dlgPrikazRezervacije.setVisible(true);

			}
		});
		frmUnos.getContentPane().add(btnPrikazRezervacije);
		
		JLabel label_9 = new JLabel("");
		frmUnos.getContentPane().add(label_9);
		
		JLabel label_10 = new JLabel("");
		frmUnos.getContentPane().add(label_10);
		
		JLabel label_11 = new JLabel("");
		frmUnos.getContentPane().add(label_11);
		
		JButton btnUnosKorisnika = new JButton("Unos korisnika");
		frmUnos.getContentPane().add(btnUnosKorisnika);
		
		JLabel label_12 = new JLabel("");
		frmUnos.getContentPane().add(label_12);
		
		JButton btnPrikazKorisnika = new JButton("Prikaz korisnika");
		btnPrikazKorisnika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frmUnos.getContentPane().add(btnPrikazKorisnika);
		
		JLabel label_13 = new JLabel("");
		frmUnos.getContentPane().add(label_13);
		
		JLabel label_14 = new JLabel("");
		frmUnos.getContentPane().add(label_14);
		
		JLabel label_15 = new JLabel("");
		frmUnos.getContentPane().add(label_15);
		
		JButton btnUnos = new JButton("Unos administratora");
		frmUnos.getContentPane().add(btnUnos);
		
		JLabel label_16 = new JLabel("");
		frmUnos.getContentPane().add(label_16);
		
		JButton btnPrikazAdministratora = new JButton("Prikaz administratora");
		frmUnos.getContentPane().add(btnPrikazAdministratora);
		
		JLabel label_17 = new JLabel("");
		frmUnos.getContentPane().add(label_17);
		
		JLabel label_18 = new JLabel("");
		frmUnos.getContentPane().add(label_18);
		
		JLabel label_19 = new JLabel("");
		frmUnos.getContentPane().add(label_19);
		
		JLabel label_20 = new JLabel("");
		frmUnos.getContentPane().add(label_20);
		
		JLabel label_21 = new JLabel("");
		frmUnos.getContentPane().add(label_21);
		
		JLabel label_22 = new JLabel("");
		frmUnos.getContentPane().add(label_22);
		
		JLabel label_23 = new JLabel("");
		frmUnos.getContentPane().add(label_23);
		
		JLabel label_24 = new JLabel("");
		frmUnos.getContentPane().add(label_24);
	}
}

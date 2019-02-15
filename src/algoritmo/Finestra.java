package algoritmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public final class Finestra extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCassa;
	private JTextField txtClienti;
	private static ClienteSetter cs;

	/**
	 * Create the frame.
	 */
	public Finestra() {
		setTitle("Algoritmo Banchiere");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 175);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCassa = new JLabel("Numero valore di cassa");
		lblCassa.setHorizontalAlignment(SwingConstants.CENTER);
		lblCassa.setBounds(10, 30, 175, 25);
		contentPane.add(lblCassa);

		JLabel lblClienti = new JLabel("Numero clienti");
		lblClienti.setHorizontalAlignment(SwingConstants.CENTER);
		lblClienti.setBounds(10, 85, 175, 25);
		contentPane.add(lblClienti);

		txtCassa = new JTextField();
		txtCassa.setBounds(195, 30, 135, 25);
		contentPane.add(txtCassa);
		txtCassa.setColumns(10);

		txtClienti = new JTextField();
		txtClienti.setColumns(10);
		txtClienti.setBounds(195, 85, 135, 25);
		contentPane.add(txtClienti);

		JButton btnCreate = new JButton("Crea");
		btnCreate.setToolTipText("Crea clienti");

		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int nCassa = 0, nClienti = 0;
				boolean flag1 = false, flag2 = false;
				try {
					nCassa = Integer.valueOf(txtCassa.getText());
					if (nCassa <= 0) {
						throw new NumberFormatException();
					}
					flag1 = true;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null,
							"Il valore inserito nella cassa è incorretto, sono necessari valori numerici");
				}

				try {
					nClienti = Integer.valueOf(txtClienti.getText());
					if (nClienti <= 0) {
						throw new NumberFormatException();
					}
					flag2 = true;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Il valore inserito nei Clienti è incorretto");
				}

				if (flag1 && flag2) {
					cs = new ClienteSetter();
					cs.setVisible(true);
					new Main().create(nCassa, nClienti);
					dispose();
				}

			}
		});

		btnCreate.setBounds(385, 85, 85, 25);
		contentPane.add(btnCreate);

	}

	
	/*
	 * Metodo per settare il vettore di clienti nella classe ClienteSetter
	 * 
	 * @param clienti vettore contenente i clienti creati
	 */
	public static void showClientInput(Cliente[] c) {
		cs.setClienti(c);
	}

}

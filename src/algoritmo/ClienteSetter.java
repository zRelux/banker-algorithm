package algoritmo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;

public class ClienteSetter extends JFrame {

	private static final long serialVersionUID = 5204028025197113649L;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblCliente;
	private JButton btnConferma;
	private Cliente[] c;
	private int value = 0;

	/**
	 * Create the frame.
	 */
	public ClienteSetter() {
		c = new Cliente[1];
		setAlwaysOnTop(true);
		setTitle("ClientSetter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		JLabel lblFidoCliente = new JLabel("Fido Cliente");
		lblFidoCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblFidoCliente.setBounds(10, 65, 90, 20);
		contentPane.add(lblFidoCliente);

		textField = new JTextField();
		textField.setBounds(110, 65, 115, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		lblCliente = new JLabel("Cliente");
		lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblCliente.setBounds(10, 25, 215, 20);
		contentPane.add(lblCliente);

		btnConferma = new JButton("Conferma");
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fido = 0;
				boolean flag = false;
				try {
					fido = Integer.valueOf(textField.getText());
					if (fido <= 0) {
						throw new NumberFormatException();
					}
					flag = true;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Valore del fido inserito incorretto");
				}

				if (flag) {
					c[value].setFido(fido);
				}

				if (c[value].getB().getvCassa() < c[value].getFido()) {
					int risposta = JOptionPane.showConfirmDialog(null,
							"Il fido della cassa è inferiore a quello inserito modificare il fido della cassa?",
							"Informazioni", JOptionPane.OK_CANCEL_OPTION);
					if (risposta == JOptionPane.YES_OPTION) {
						c[value].getB().setvCassa(c[value].getFido());

						addCliente();
					}

				} else if (flag) {
					addCliente();
				}

				//se tutti i clienti sono settati 
				if (value == c.length) {
					/*
					 * Ciclo per far partire i thread (clienti)
					 */
					for (int i = 0; i < c.length; i++) {
						c[i].start();
					}
					
					try {
						c[0].getB().sincronizzaPartenza();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//System.out.println("Thread partiti");
				}
			}

		});
		btnConferma.setBounds(120, 105, 100, 25);
		contentPane.add(btnConferma);

		setVisible(true);
	}

	/*
	 * Metodo per aggiungere un cliente alla tabella della finestra
	 */
	public void addCliente() {
		value++; 								// aumento contatore dei clienti
		textField.setText("");
		if (value == c.length) {
			dispose();
		} else {
			setNome(c[value].getName());
		}
	}

	/*
	 * metodo per settare il vettore con i clienti e per settare il nome per il primo cliente
	 * 
	 * @param clienti valore del vettore di clienti
	 */
	
	public void setClienti(Cliente[] clienti) {
		this.c = clienti;
		lblCliente.setText("Cliente " + c[value].getName());
	}	
	
	/*
	 * metodo per settare il nome della Jlabel
	 * 
	 * @param name stringa contenente il nome del cliente
	 */
	private void setNome(String name) {
		lblCliente.setText("Cliente " + name);
	}

}

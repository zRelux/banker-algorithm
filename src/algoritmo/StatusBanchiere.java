package algoritmo;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StatusBanchiere extends JPanel {

	private static final long serialVersionUID = 3955376886814109812L;
	private JLabel lblNumero;
	private JLabel lblStato;
	private static JTable table;
	private static int pagina = 0;
	private int indice = 1;
	private int max;

	/**
	 * Create the frame.
	 */
	public StatusBanchiere() {
		setBounds(0, 0, 450, 400);

		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 95, 415, 265);
		add(scrollPane);

		table = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String[] employee = { "Nome", "Prestito Attuale", "Fido", "Potenziale Richiesta" };

			@Override
			public int getColumnCount() {
				return employee.length;
			}

			@Override
			public String getColumnName(int index) {
				return employee[index];
			}
			
			@Override
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		});
		scrollPane.setViewportView(table);
		table.getTableHeader().setReorderingAllowed(false);
		
		lblStato = new JLabel("Stato:");
		lblStato.setHorizontalAlignment(SwingConstants.CENTER);
		lblStato.setBounds(10, 10, 110, 30);
		add(lblStato);

		lblNumero = new JLabel("");
		lblNumero.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumero.setBounds(365, 55, 60, 30);
		add(lblNumero);
		
		JButton indietro = new JButton("<--");
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(indice >= 1)
					FinestraClienti.cambia(--indice);
			}
		});
		indietro.setBounds(310, 15, 55, 25);
		add(indietro);
		
		JButton avanti = new JButton("-->");
		avanti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(indice <= max)
					FinestraClienti.cambia(++indice);
			}
		});
		avanti.setBounds(370, 15, 55, 25);
		add(avanti);
		setVisible(true);
	}

	
	
	/*
	 * Metodo per riempire la tabella con i clienti
	 * 
	 * @param c vettore contenente i clienti
	 */
	public void createTable(Cliente[] c) {
		pagina++;																			//incremento del valore della pagina
		max = pagina;
		indice = pagina;
		lblNumero.setText(String.valueOf(pagina));											//impostazione del numero di pagina
		lblStato.setText(String.valueOf("Cassa: " + c[0].getB().getvCassa()));				//impostazione del valore della cassa
		for (Cliente cliente : c) {															//ciclo che per ogni cliente
			DefaultTableModel model = (DefaultTableModel) table.getModel();					//creazione del modello per settare le righe della tabella
			if(cliente.getFinito() != 2) {
			model.addRow(new Object[] { cliente.getName(), cliente.getPrestitoAttuale(), cliente.getFido(),			//aggiunta della riga alla tabella
					cliente.getPotenzialeRichiesta() });
			}
		}

	}

	public static int getPagina() {
		return pagina;
	}

}

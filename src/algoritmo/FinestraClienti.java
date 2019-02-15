package algoritmo;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinestraClienti extends JFrame {


	/**
	 * Parametri della classe
	 */
	private static final long serialVersionUID = 1L;										//variabile per rendere unico il frame
	public static final int base = 350;														//base ed altezza del frame
	public static final int altezza = 300;													//altezza del frame
	private static JPanel cards = new JPanel(new CardLayout());								//pannello contenitore degli altri con un card layout che ce lo permette	
	
	
	public FinestraClienti() {
		setTitle("Status");																	// modifica del suo titolo
		initialize();																	// richiamo del metodo inizialize
	}

	/**
	 * Inizializzazione del frame
	 */
	private void initialize() {
		/*
		 * Setup del JFrame
		 */	
		setBounds(100, 100, 450, 420); 													// modifico le sue dimensioni
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 								// aggiungo l'opzione che mi permettere di terminare il processo con la chusura del JFrame
		
		setContentPane(cards);															//imposto come pannello principale cards
		cambia(0);
	}

	/*
	 * Metodo setVisible: ci permette di rendere il JFrame visibile
	 */
	public void setVisible() {														//metodo per rendere visibile il frame 
		setVisible(true);															//chiamo il metodo per rendere visibile il frame
	}
	
	/*
	 * Metodo cambia: ci permette di cambiare il pannello visibile all'interno del JFrame
	 */
	
	public static void aggiungiPanel(Cliente c[]) {
		StatusBanchiere sBanchiere = new StatusBanchiere();
		sBanchiere.createTable(c);
		cards.add(sBanchiere, String.valueOf(StatusBanchiere.getPagina()));
		cambia(StatusBanchiere.getPagina());
	}
	
	
	public static void cambia(int n) {												//metodo che mi permette di cambiare il panel corrente da qualsiasi pannello
		CardLayout cl = (CardLayout)(cards.getLayout());							//creo una variabile CardLayout e la euguali a quella del panel cards
		cl.show(cards, String.valueOf(n));											//mosto con il metodo show il pannello con il numero passato per parametro
	}
	
}

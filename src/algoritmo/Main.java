package algoritmo;

import java.awt.EventQueue;
import java.util.Random;




/*
 * Programma fatto da Leonardo Drici, Steve Franco, Filippo Francesconi
 */
public class Main {

	public static void main(String[] args) {

		/*
		 * Creazione del frame finestra in un thread univoco
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Finestra frame = new Finestra();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void create(int nCassa, int nClienti) {

		String[] nomi = { "Paolo", "Mario", "Luca", "Leonardo", "Steve", "Filippo", "Marco", "Francesco", "Anna", "Annalisa", "Federica", "Federico", "Monica", "Martina", "Michela", "Francesca"};				//vettori di nomi da assegnare ai thread
		Random r = new Random();																			//oggetto random per assegnare i nomi casualmente

		Banchiere b = new Banchiere(); 																		//Creazione del oggetto banchiere

		Cliente c[]; 																						//vettore di clienti

		b.setvCassa(nCassa); 																				//set della cassa tramite input
		
		c = new Cliente[nClienti];																			//creazione del vettore di clienti
		
		/*
		 * Ciclo per la creazione dei clienti e per l'assegnazione di un nome
		 */
		for (int i = 0; i < nClienti; i++) {	
			c[i] = new Cliente(b);

			c[i].setName(nomi[r.nextInt(nomi.length)]);														//modifica del nome del cliente con uno random

		}
		
		b.setC(c);
		
		Finestra.showClientInput(c);
		
		
		
	}
}

package algoritmo;

import sun.security.util.Length;

public class Banchiere {

	private int vCassa = 0;
	private Cliente c[];
	private int indice = 0;
	private boolean usufruibile = false;

	/*
	 * Costruttore del banchiere
	 */
	public Banchiere() {
			
	}

	/*
	 * Metodo che controlla se effettuare il prestito delle risorse
	 * 
	 * @param cliente cliente che richiede il prestito necessario per effetuare i
	 * controlli sul prestito
	 * 
	 * @param richiesta numero di risorse richieste dal cliente
	 * 
	 * @return restisce la richiesta se concessa altrimenti nulla
	 */
	public synchronized int richiediPrestito(Cliente cliente, int richiesta) {
		if ((cliente.getPrestitoAttuale() + richiesta) < cliente.getFido() && vCassa >= richiesta
				&& vCassa >= cliente.getPotenzialeRichiesta()) {
			vCassa -= richiesta;
			return richiesta;
		}

		if ((cliente.getPrestitoAttuale() + richiesta) == cliente.getFido() && vCassa >= richiesta
				&& trovaPossibileCliente(cliente, richiesta)) {
			vCassa -= richiesta;
			return richiesta;
		}

		return 0;
	}

	/*
	 * Metodo che controlla se effettuare il prestito delle risorse
	 * 
	 * @param cliente cliente che richiede il prestito necessario per effetuare i
	 * controlli sul prestito
	 * 
	 * @param richiesta numero di risorse richieste dal cliente
	 * 
	 * @return restisce la richiesta se concessa altrimenti nulla
	 */
	public synchronized int richiediPrestitoWait(Cliente cliente, int richiesta) {
		
		while (!trovaPossibileCliente(cliente, richiesta) || vCassa < richiesta || staRestituendo(cliente, richiesta)) {
			System.out.print(cliente.getName() + " ha richiesto " + richiesta);
			if(!trovaPossibileCliente(cliente, richiesta)) {
				System.out.println(" rifiutata perchè con il fido di " + cliente.getName() + " non sono in grado di completare nessun altro fido");
			}else if(vCassa < richiesta){
				System.out.println(" rifiutata perchè la cassa è minore della richiesta");
			}else if(!staRestituendo(cliente, richiesta)) {
				System.out.println(" rifiutata perchè qualcuno sta restituendo e non posso accedervi");
			}else if(true) {
				System.out.println(" rifiutata perche cassa + prestito attuale minore del fido");
			}
			System.out.println(usufruibile);
			System.out.println();
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println("Richiesta di " + richiesta + " fatta da " + cliente.getName() + " concessa");
		vCassa -= richiesta;
		notifyAll();
		return richiesta;
	}


	/*
	 * Metodo che restituisce vero se si è in grado di completare una potenziale
	 * richiesta con il fido ottenuto dal cliente
	 * 
	 * @param cliente cliente da qui si ottiene il fido
	 * 
	 * @return true se è possibile completare la richiesta di un'altro cliente
	 * 
	 * @return false se non è possibile completare la potenzile richiesta di un
	 * altro cliente
	 */
	public synchronized boolean trovaPossibileCliente(Cliente cliente, int richiesta) {
		for (int i = 0; i < c.length; i++) {
			Cliente client = c[i];
			if ((cliente.getFido() + (vCassa - richiesta) >= client.getPotenzialeRichiesta()) && (client.getFinito() == 0 || ultimo())) {
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * Definisce se è rimasto un solo cliente a fare la richiesta
	 * 
	 * @return restituisce la conferma dello stato
	 */
	public synchronized boolean ultimo() {
		int x = 0;
		for (Cliente cliente : c) {
			if(cliente.getFinito() == 2) {
				x++;
			}		
		}
		return x == (c.length-1);
	}
	
	/*
	 * Metodo che 
	 */
	public synchronized boolean staRestituendo(Cliente cliente, int richiesta) {		
		for (int i = 0; i < c.length; i++) {
			Cliente client = c[i];	
			if(!tuttiDisponibli())
				if ((richiesta <= client.getFido() && client.getFinito() == 1 && usufruibile && prestatbile(cliente, client))) {
					usufruibile = false;
					return true;
				}
			}
		return false;
	}
	
	public synchronized boolean tuttiDisponibli() {
		int x = 0;
		for (Cliente cliente : c) {
			if(cliente.getFinito() == 0) {
				x++;
			}		
		}
		
		System.out.println("X: " + x + " len: " + (c.length - finiti()));
		
		
		if(x != c.length - finiti()) {
			
			return true;
		}else
			return false;
	}

	public int finiti() {
		int x = 0;
		for (Cliente cliente : c) {
			if(cliente.getFinito() == 2) {
				x++;
			}		
		}
		return x;
	}

	/*
	 * Metodo che controlla se con il prestito attuale + la cassa si è in grado di completare il cliente
	 * @param cliente il cliente che fa la richiesta
	 * 
	 * @return se si può effettuare la richiesta
	 */
	public boolean prestatbile(Cliente cliente, Cliente cl) {
		if (vCassa + cliente.getPrestitoAttuale() + cl.getPrestitoAttuale() >= cliente.getFido()) {	
			return true;
		}
		return false;
	}
	
	/*
	 * Metodo che restituisce il prestito aumentando la cassa
	 * 
	 * @param i valore da restituire
	 */
	public synchronized void restituirePrestito(int i) {
		System.out.println(Thread.currentThread().getName() + " ha restituito " + i );
		vCassa += i;
		notifyAll();
	}

	/*
	 * Metodo che crea la tabella sia sulla console che con la parte grafica
	 */
	public synchronized void tabella() {
		System.out.println("\n--------------------------------------------------------------------------------------");
		System.out.printf("%-22s%-22s%-22s%-22s\n","Nome" ,"Prestito attuale", "Fido", "Potenziale Richiesta"); // stampa dei valori per la console
		for (int i = 0; i < c.length; i++) {
			if(c[i].getFinito() != 2) {
				System.out.printf("%-22s%-22s%-22s%-22s%-22s\n", c[i].getName(), c[i].getPrestitoAttuale(), c[i].getFido(), c[i].getPotenzialeRichiesta(), "");
			}else {
				System.out.printf("%-22s%-22s%-22s%-22s%-22s\n", c[i].getName(), "X", "X", "X", "TERMINATO");
			}
		}
		System.out.println("Cassa: " + vCassa);
		System.out.println("\n\n");
		FinestraClienti.aggiungiPanel(c);
	}
	
	/*
	 * Metodo per ottenere il valore della cassa
	 */
	public int getvCassa() {
		return vCassa;
	}

	/*
	 * Metodo per settare il valore della cassa
	 * 
	 * @param vCassa valore da sostituire alla cassa
	 */
	public void setvCassa(int vCassa) {
		this.vCassa = vCassa;
	}

	/*
	 * Metodo che setta il vettore di clienti
	 * 
	 * @param c vettore nuovo con clienti
	 */
	public void setC(Cliente[] c) {
		this.c = c;
	}

	/*
	 * Metodo che definisce i clienti che hanno terminato
	 */
	public synchronized void setFinito() {
		tabella();
	}

	/*
	 * Metodo che permette di sincronizzare la partenza dei clienti
	 */
	public synchronized void sincronizzaPartenza() throws InterruptedException {
		indice++;
		if (indice == c.length + 1) {
			notifyAll();
			new FinestraClienti().setVisible();
		} else {
			wait();
		}

	}

	public boolean isUsufruibile() {
		return usufruibile;
	}

	public synchronized void setUsufruibile(boolean usufruibile) {
		this.usufruibile = usufruibile;
	}

	public int getIndice() {
		return indice;
	}
	
	

}

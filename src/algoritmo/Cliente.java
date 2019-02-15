package algoritmo;

import java.util.Random;

public class Cliente extends Thread {

	private int fido = 0;
	private int prestitoAttuale = 0;
	private int potenzialeRichiesta = 0;
	private Banchiere b;
	private Random r;
	private int finito = 0;

	
	/*
	 * Construttore del Thread Cliente
	 * @param b il banchiere da cui richiedere i soldi
	 * 
	 */
	
	public Cliente(Banchiere b) {
		this.b = b;
		r = new Random();
		setPotenzialeRichiesta();
		isFidoMax();
	}

	
	/*
	 * Metodo run overridato dalla classe Thread
	 * 
	 */
	
	@Override
    public void run() {
		super.run();																				//richiamo del metodo run della superclasse
		try {
			b.sincronizzaPartenza();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}										
    		while(prestitoAttuale < fido){															//se il fido fosse meno del massimo si fa un ciclo per richiedere risorse
    			prestitoAttuale += b.richiediPrestitoWait(this, r.nextInt(potenzialeRichiesta) + 1);//richiede risorse in modo randomico dalla potenziale richiesta
    			setPotenzialeRichiesta();															//aggiornamento della potenziale richiesta
    			b.tabella();
    			try {
					Thread.sleep(r.nextInt(2000) + 1);												//sleep del thread randomico
				} catch (InterruptedException e) {													//cattura di una possibile eccezione
					e.printStackTrace();															//stampa dello stack trace nel caso di un eventuale eccezione
				}		    		
    	}
    	
    	finito = 1;
    	b.setUsufruibile(true);
    																					
    	while(prestitoAttuale > 0){																//se il fido invece ha raggiunto il massimo si fa un ciclo per restituire le risorse
    		int prest = r.nextInt(prestitoAttuale) + 1;											//si salva il valore da prestare generato casualmente
    		b.restituirePrestito(prest);														//si restituisce il prestito					
    		prestitoAttuale -= prest;															//si toglio cio restituito dal totale da restituire(prestito attuale)
    		b.tabella();
    		try {					
				Thread.sleep(r.nextInt(1000) + 1);												//si fa dormire il thread per un valore randomico
			} catch (InterruptedException e) {													//cattura di una possibile eccezione
				e.printStackTrace();															//stampa dello stack trace nel caso di un eventuale eccezione
			}	
		}
    	
    	finito = 2;
    	b.setUsufruibile(true);
    	this.fido = 0;
    	this.potenzialeRichiesta = 0;
    	this.prestitoAttuale = 0;
    	this.setName(getName());																//metodo che modifica il nome per far capire che il thread ha finito
    	b.setFinito();
    }

	/*
	 * Metodo che calcola la potenziale richiesta
	 */
	public void setPotenzialeRichiesta() {
		this.potenzialeRichiesta = (fido - prestitoAttuale);
	}

	/*
	 * Metodo che restituisce il fido
	 * @return fido il valore del fido
	 */
	public int getFido() {
		return fido;
	}

	/*
	 * Metodo che imposta il fido e aggiorna la potenziale richiesta
	 * @param fido valore del fido nuovo
	 */
	public void setFido(int fido) {
		this.fido = fido;
		setPotenzialeRichiesta();
	}
	
	/*
	 * Metodo che definisce se si è raggiunto il fido massimo
	 */
	public boolean isFidoMax() {
		if (fido == prestitoAttuale) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Metodo che restituisce il prestito attuale
	 * @return prestitoAttuale il valore del prestito attuale
	 */
	public int getPrestitoAttuale() {
		return prestitoAttuale;
	}

	/*
	 * Metodo che sovrascire il prestito attuale
	 * @param prestitoAttuale valore del prestito da modificare
	 */
	public void setPrestitoAttuale(int prestitoAttuale) {
		this.prestitoAttuale = prestitoAttuale;
	}

	/*
	 * Metodo che restituisce la potenziale richiesta
	 * @return potenzialeRichiesta restituisce la potenziale richiesta
	 */
	public int getPotenzialeRichiesta() {
		return potenzialeRichiesta;
	}

	/*
	 * Metodo che sovrascire la potenziale richiesta
	 * @param potenziale valore della potenziale richiesta da modificare
	 */
	public void setPotenzialeRichiesta(int potenzialeRichiesta) {
		this.potenzialeRichiesta = potenzialeRichiesta;
	}

	/*
	 * Metodo che restituisce il banchiere posseduto dal cliente(1 per tutti i clienti)
	 * @return valore del banchiere
	 */
	public Banchiere getB() {
		return b;
	}

	/*
	 * Metodo che sovrascire il banchiere attuale
	 * @param b valore del banchiere da modificare
	 */
	public void setB(Banchiere b) {
		this.b = b;
	}

	public int getFinito() {
		return finito;
	}


	public void setFinito(int finito) {
		this.finito = finito;
	}
	
	
	
}

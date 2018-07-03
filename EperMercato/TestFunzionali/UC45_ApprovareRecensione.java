import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class UC45_ApprovareRecensione {
	
	Sistema sistema;
	//variabili booleane create per simulare la risposta a video da parte dell'utente.
	//approvazione: se il Manager del sistema approva la recensione quindi chiede la pubblicazione o se la 
	//              rifiuta quindi ne chiede l'eliminazione
	//confermaOperazione: se il Manager del sistema intende confermare la richiesta inviata
	boolean approvazione, confermaOperazione;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
		//inserisco un nuovo negozio nel sistema e una recensione su di esso
		sistema.inserisciNegozio("basko", "via Procida 1", "010-53421", "baskoProcida@gmail.com", 
								 "paolo", "grisu", "pg@gmail.com", "paogri", "paogri22", false, true);
		sistema.creaRecensione("buon negozio", "buona scelta di prodotti a prezzi onesti", new Date(18,10,10), "negozio",
				   			   "basko", "marros", 4);
		//inserisco due prodotti nel sistema e recensioni collegate ad essi
		sistema.inserisciProdotto("scatolame", "tonno Nostromo 3x100g", 1.22f, "basko");
		sistema.inserisciProdotto("scatolame", "mais Valfrutta 100g", 1.0f, "basko");
		sistema.creaRecensione("buono!", "ottimo rapporto qualita'-prezzo", new Date(18,10,10), "prodotto",
							   "mais Valfrutta 100g", "mariom", 4);
		sistema.creaRecensione("sconsiglio", "confezione bucata", new Date(18,10,11), "prodotto",
							   "mais Valfrutta 100g", "ezior", 2);
		//creo una recensione sul sistema
		sistema.creaRecensione("alla grande!", "perfetto", new Date(18,10,10), "sistema",
							   null, "erisol", 5);
		
	}
	//NOTE: -Recensioni inserite nella lista in ordine (dalla piu' vecchia alla piu' recente).
	//		-Si controlla anche che le recensioni siano state collegate al giusto oggetto, grazie al valore del 'return'
	
	/**
	 *  Scenario principale: Inserimento corretto dei dati richiesti e conseguente
	 *  					 operazione effettuata con successo.
	 */
	@Test
	public void ApprovareRecensione(){
		approvazione = true;
		confermaOperazione = true;
		//Eseguo lettura e approvazione di tutte le recensioni (quindi di tutte le tipologie: "negozio", "prodotto", 
		//"sistema")

		//primo conrollo: recensione letta, approvata e pubblicata. Deve ritornare 100+numero di recensioni 
		//				  collegate a quell'oggetto specifico, quindi 100+1 (per negozio "basko")
		//				  Inoltre devono essere presenti ancora 3 recensioni in attesa di approvazione
		assertEquals(sistema.approvareRecensione(0, approvazione, confermaOperazione),101);
		assertEquals(sistema.getRecensioniDaValutare().size(),3);
			//verifico che la recensione sia stata collegata all'oggetto indicato
		assertEquals(1,sistema.getNegozi().get(0).getRecensioni().size());
		assertEquals("buon negozio",sistema.getNegozi().get(0).getRecensioni().get(0).getTitolo());
		assertEquals("marros",sistema.getNegozi().get(0).getRecensioni().get(0).getAutore());
		
		//secondo conrollo: recensione letta, approvata e pubblicata. Deve ritornare 100+numero di recensioni 
		//				    collegate a quell'oggetto specifico, quindi 100+1 (per prodotto "mais Valfrutta 100g").
		//				    Inoltre devono essere presenti ancora 2 recensioni in attesa di approvazione
		assertEquals(sistema.approvareRecensione(1, approvazione, confermaOperazione),101);
		assertEquals(sistema.getRecensioniDaValutare().size(),2);
			//verifico che la recensione sia stata collegata all'oggetto indicato
			//Nota: "mais Valfrutta 100g" allocato in posizione '1' dell'array prodotti
		assertEquals(1,sistema.getProdotti().get(1).getRecensioni().size());
		assertEquals("buono!",sistema.getProdotti().get(1).getRecensioni().get(0).getTitolo());
		assertEquals("mariom",sistema.getProdotti().get(1).getRecensioni().get(0).getAutore());
		
		//terzo controllo: recensione letta, approvata e pubblicata. Deve ritornare 100+numero di recensioni 
		//				   collegate a quell'oggetto specifico, quindi 100+2 (per prodotto "mais Valfrutta 100g")
		//				   Inoltre devono essere presenti ancora 1 recensioni in attesa di approvazione
		assertEquals(sistema.approvareRecensione(2, approvazione, confermaOperazione),102);
		assertEquals(sistema.getRecensioniDaValutare().size(),1);
			//verifico che la recensione sia stata collegata all'oggetto indicato
			//Nota: "mais Valfrutta 100g" allocato in posizione '1' dell'array prodotti
		assertEquals(2,sistema.getProdotti().get(1).getRecensioni().size());
		assertEquals("sconsiglio",sistema.getProdotti().get(1).getRecensioni().get(1).getTitolo());
		assertEquals("ezior",sistema.getProdotti().get(1).getRecensioni().get(1).getAutore());
		
		//quarto controllo: recensione letta, approvata e pubblicata. Deve ritornare 100+numero di recensioni 
		//				   collegate a quell'oggetto specifico, quindi 100+1 (per sistema)
		//				   Inoltre devono essere presenti ancora 0 recensioni in attesa di approvazione
		assertEquals(sistema.approvareRecensione(3, approvazione, confermaOperazione),101);	
		assertEquals(sistema.getRecensioniDaValutare().size(),0);
			//verifico che la recensione sia stata collegata all'oggetto indicato
		assertEquals(1,sistema.getRecensioniSistema().size());
		assertEquals("alla grande!",sistema.getRecensioniSistema().get(0).getTitolo());
		assertEquals("erisol",sistema.getRecensioniSistema().get(0).getAutore());
	}
	
	/**
	 *  Scenario alternativo 3a: La lista delle recensioni in attesa di valutazione e' vuota.
	 */
	@Test
	public void ApprovareRecensione3a(){
		//questo scenario non dipende da 'approvazione' e 'confermaOperazione'
		approvazione = true;
		confermaOperazione = true;
		//Controllo tutte le recensioni presenti, cosi' da non averne piu' in attesa di approvazione
		sistema.approvareRecensione(0, approvazione, confermaOperazione);
		sistema.approvareRecensione(1, approvazione, confermaOperazione);
		sistema.approvareRecensione(2, approvazione, confermaOperazione);
		sistema.approvareRecensione(3, approvazione, confermaOperazione);
		
		//Ora non ho piu' recensioni in attesa di approvazione
		//primo controllo: deve ritornare il valore 4, associato a lista recensioni in attesa di valutazione vuota
		assertEquals(sistema.approvareRecensione(0, approvazione, confermaOperazione),4);
		//secondo controllo: verifico che effettivamente la lista delle recensioni in attesa di valutazioe e' vuota
		assertEquals(sistema.getRecensioniDaValutare().size(),0);
	}
	
	/**
	 *  Scenario alternativo 6a.3a: Il manager del sistema non approva la recensione e decide di eliminarla.
	 */
	@Test
	public void ApprovareRecensione6a3a(){
		approvazione = false;
		confermaOperazione = true;		
		//primo controllo: deve ritornare il valore 4, associato all'eliminazione della recensione esaminata
		assertEquals(sistema.approvareRecensione(0, approvazione, confermaOperazione),1);
		//secondo controllo: verifico che la recensione non sia piu' presente nella lista (inizialmente 4 recensioni in attesa)
		assertEquals(sistema.getRecensioniDaValutare().size(),3);
	}
	
	/**
	 *  Scenario alternativo 6a.3b: Il manager del sistema non approva la recensione ma annulla l'operazione di eliminazione.
	 */
	@Test
	public void ApprovareRecensione6a3b(){
		approvazione = false;
		confermaOperazione = false;		
		//primo controllo: deve ritornare il valore 3, associato all'annullamento dell'operazione di cancellazione della
		//				   recensione dal sistema
		assertEquals(sistema.approvareRecensione(0, approvazione, confermaOperazione),3);
		//secondo controllo: verifico che le recensioni in attesa siano ancora tutte presenti
		assertEquals(sistema.getRecensioniDaValutare().size(),4);
	}
	
	/**
	 *  Scenario alternativo 8a: Il manager del sistema approva la recensione ma non conferma l'operazione di 
	 *  						 pubblicazione annullandola.
	 */
	@Test
	public void ApprovareRecensione8a(){
		approvazione = true;
		confermaOperazione = false;		
		//primo controllo: deve ritornare il valore 2, associato all'annullamento dell'operazione di pubblicazione della
		//				   recensione
		assertEquals(sistema.approvareRecensione(0, approvazione, confermaOperazione),2);
		//secondo controllo: verifico che le recensioni in attesa siano ancora tutte presenti
		assertEquals(sistema.getRecensioniDaValutare().size(),4);
	}
	
}

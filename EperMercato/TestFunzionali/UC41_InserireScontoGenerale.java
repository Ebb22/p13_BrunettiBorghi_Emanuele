import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class UC41_InserireScontoGenerale {
	Sistema sistema;
	//variabili booleane create per simulare la risposta a video da parte dell'utente.
	//annulla: se l'utente annulla l'operazione prima di inviare la richiesta
	//conferma: se l'utente intende confermare la richiesta inviata
	boolean annulla, conferma;
	
	
	@Before
	public void setUp() throws Exception {
		annulla = false;
		conferma = true;
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
		//inserisco un nuovo sconto generale nel sistema, cosi da poter analizzare gli scenari alternativi
		sistema.inserisciScontoGenerale("ab12e", "1% di sconto per tutti fino ad una spesa di 20€",
										1.0f, 0.0f, 20.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma);
	}
	
	/**
	 *  Scenario principale: Inserimento corretto dei dati richiesti e conseguente
	 *  					 operazione effettuata con successo.
	 */
	@Test
	public void InserireScontoGenerale(){
		annulla = false;
		conferma = true;
		//primo conrollo: il metodo di inserimento del nuovo sconto generale con i dati inseriti deve
		//                ritornare 0(cioè inserimento riuscito)
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
													 20.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),0);
		//secondo controllo: la garanzia di successo e' che lo sconto generale sia stato aggiunto alla
		//                   lista degli sconti generali presenti nel sistema (quindi devono esserci 2 oggetti)
		assertEquals(sistema.getScontiGenerali().size(),2);
		assertEquals(sistema.getScontiGenerali().get(1).getCodiceSconto(),"erec1");
	}
	
	/**
	 *  Scenario alternativo 4a: Il manager del sistema annulla l'operazione di 
	 *                           inserimento (prima di richiedere l'inserimento)
	 */
	@Test
	public void InserireScontoGenerale4a(){
		annulla = true;
		//questo scenario non dipende da 'conferma'
		conferma = true;
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€",  3.0f, 
				                                      20.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),6);
		//verifico che lo sconto generale non sia stato inserito nel sistema
		assertEquals(sistema.getScontiGenerali().size(),1);
	}
	
	/**
	 *  Scenario alternativo 5a: Il Sistema trova errori concettuali sui dati inseriti, possono essere date e
	 *  				         soglie incoerenti o percentuale non sensata (non tra 0 e 100)
	 */
	@Test
	public void InserireScontoGenerale5a(){
		annulla = false;
		//questo scenario non dipende da 'conferma'
		conferma = true;
		//eseguo test per ogni caso, ho distinto i valori ritornati cosi' da poter specificare su quale campo e'
		//stato riscontrato l'errore
		
		//deve tornare errore nelle date inserite (1, data di inizio successiva alla data di fine o data inizio precedente ad oggi)
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
													 20.01f, 1000.0f, new Date(119,8,12), new Date(119,7,12), annulla, conferma),1);
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
													 20.01f, 1000.0f, new Date(117,6,12), new Date(117,7,12), annulla, conferma),1);
		//deve tornare errore nelle soglie inserite (2)
		//soglia massima minore di quella minima
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€",  3.0f, 
													 20.01f, 15.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),2);
		//soglia minima non ammessa
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
				 									 -1.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),2);
		//soglia massima non ammessa
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
				 									 20.01f, 1001.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),2);
		//deve tornare errore nella percentuale inserita (3, percentuale non compresa tra 0.01 e 100.0)
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 113.0f, 
												 	 20.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),3);
		//verifico che lo sconto generale non sia stato inserito nel sistema
		assertEquals(sistema.getScontiGenerali().size(),1);
	}
	
	/**
	 *  Scenario alternativo 5b: Il Sistema trova errori di coerenza con altri sconti gia' presenti nel sistema.
	 *  						 Possono essere riscontrate incoerenze sul codice sconto e sulle soglie.
	 */
	@Test
	public void InserireNegozio5b(){
		annulla = false;
		//questo scenario non dipende da 'conferma'
		conferma = true;
		//eseguo test per ogni caso, ho distinto i valori ritornati cosi' da poter specificare su quale campo e'
		//stato riscontrato l'errore
		
		//deve tornare errore nel codice sconto (4, codice gia' esistente)
		assertEquals(sistema.inserisciScontoGenerale("ab12e", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
													 20.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),4);
		//deve tornare errore nelle soglie inserite (5, soglie gia' ricoperte da un altro sconto)
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
													 10.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),5);
		//verifico che lo sconto generale non sia stato inserito nel sistema
		assertEquals(sistema.getScontiGenerali().size(),1);
	}
	
	/**
	 *  Scenario alternativo 7a: Il manager non conferma l'inserimento (dopo approvazione dei dati)
	 */
	@Test
	public void InserireNegozio7a(){
		annulla = false;
		conferma=false;
		assertEquals(sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
				 20.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma),7);
		//verifico che lo sconto generale non sia stato inserito nel sistema
		assertEquals(sistema.getScontiGenerali().size(),1);
	}

}

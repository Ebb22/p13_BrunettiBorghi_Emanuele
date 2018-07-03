import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class UC43_InserirePremio {
	Sistema sistema;
	//variabile booleane create per simulare la risposta a video da parte dell'utente.
	//annulla se l'utente annulla l'operazione prima di inviare la richiesta
	//conferma se l'utente intende confermare la richiesta inviata (dopo aver passato l'analisi sui dati)
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
		sistema.inserisciPremio("Set di 6 bicchieri colorati", 400, 50, new Date(119,5,12), new Date(119,7,12), annulla, conferma);
	}
	
	/**
	 *  Scenario principale: Inserimento corretto dei dati richiesti e conseguente
	 *  					 operazione effettuata con successo.
	 */
	@Test
	public void InserirePremio(){
		annulla = false;
		conferma = true;
		//primo conrollo: il metodo di inserimento del nuovo premio con i dati inseriti deve
		//                ritornare 0(cioè inserimento riuscito)
		assertEquals(sistema.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), 
								 			 new Date(119,11,1), annulla, conferma),0);
		//secondo controllo: la garanzia di successo è che il premio sia stato aggiunto alla
		//                   lista dei premi presenti nel sistema (quindi devono esserci 2 oggetti)
		assertEquals(sistema.getPremi().size(),2);
		//terzo controllo: verifico che i dati siano stati inseriti correttamente
		assertEquals(sistema.getPremi().get(1).getDescrizione(),"Set di 4 pentole");
		assertEquals(sistema.getPremi().get(1).getPuntiRichiesti(),1200);
		assertEquals(sistema.getPremi().get(1).getPezziDisponibili(),6);
		assertEquals(sistema.getPremi().get(1).getDataInizio(),new Date(119,6,1));
		assertEquals(sistema.getPremi().get(1).getDataFine(),new Date(119,11,1));
	}
	
	/**
	 *  Scenario alternativo 4a: Il manager del sistema annulla l'operazione di 
	 *                           inserimento (prima di inviare i dati al sistema).
	 */
	@Test
	public void InserirePremio4a(){
		annulla = true;
		//questo scenario non dipende da 'conferma'
		conferma = true;
		assertEquals(sistema.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), 
											 new Date(119,11,1), annulla, conferma),4);
		//verifico che il premio non sia stato aggiunto nel sistema
		assertEquals(sistema.getPremi().size(),1);
	}
	
	/**
	 *  Scenario alternativo 5a: Il Sistema trova errori concettuali sui dati inseriti, possono essere su date o pezzi
	 *  						 disponibili.
	 */
	@Test
	public void InserirePremio5a(){
		annulla = false;
		//questo scenario non dipende da 'conferma'
		conferma = true;
		//eseguo test per ogni caso, ho distinto i valori ritornati cosi' da poter specificare su quale campo e'
		//stato riscontrato l'errore
		
		//deve tornare errore nei pezzi disponibili (1, pezzi disponibili non ammessi)
		assertEquals(sistema.inserisciPremio("Set di 4 pentole", 1200, 0, new Date(119,6,1), 
						 							 new Date(119,11,1), annulla, conferma),1);
		//deve tornare errore nelle date inserite (2, data di inizio successiva alla data di fine o se inizio
		//precedente alla data di oggi)
		assertEquals(sistema.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), 
											 new Date(119,5,1), annulla, conferma),2);
		assertEquals(sistema.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(117,6,1), 
											 new Date(119,8,1), annulla, conferma),2);
		//verifico che il premio non sia stato aggiunto nel sistema
		assertEquals(sistema.getPremi().size(),1);
	}
	
	/**
	 *  Scenario alternativo 5b: Il Sistema trova errori di coerenza con altri premi gia' presenti nel sistema.
	 *  						 Possono essere riscontrate incoerenze sulla descrizione del premio.
	 */
	@Test
	public void InserirePremio5b(){
		annulla = false;
		//questo scenario non dipende da 'conferma'
		conferma = true;
		//deve trovare errore con un altro premio gia' presente nel sistema (stessa descrizione)
		assertEquals(sistema.inserisciPremio("Set di 6 bicchieri colorati", 400, 6, new Date(119,6,1), 
											 new Date(119,11,1), annulla, conferma),3); 
		//verifico che il premio non sia stato aggiunto nel sistema
		assertEquals(sistema.getPremi().size(),1);
	}
	
	/**
	 *  Scenario alternativo 7a: Il manager non conferma l'inserimento (dopo approvazione dei dati)
	 */
	@Test
	public void InserirePremio7a(){
		annulla = false;
		conferma=false;
		assertEquals(sistema.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), 
											 new Date(119,11,1), annulla, conferma),5);
		//verifico che il premio non sia stato aggiunto nel sistema
		assertEquals(sistema.getPremi().size(),1);
	}
}

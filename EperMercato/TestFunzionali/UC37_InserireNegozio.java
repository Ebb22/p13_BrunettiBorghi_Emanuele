import static org.junit.Assert.*;
import org.junit.*;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
public class UC37_InserireNegozio {
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
		//inserisco un nuovo negozio nel sistema, cosi da poter analizzare gli scenari alternativi
		sistema.inserisciNegozio("basko", "via Procida 1", "010-53421", "baskoProcida@gmail.com", 
								 "paolo", "grisu", "pg@gmail.com", "paogri", "paogri22", annulla, conferma);
	}
	
	/**
	 *  Scenario principale: Inserimento corretto dei dati richiesti e conseguente
	 *  					 operazione effettuata con successo.
	 */
	@Test
	public void InserireNegozio(){
		annulla = false;
		conferma = true;
		//primo conrollo: il metodo di inserimento del nuovo negozio con i dati inseriti deve
		//                ritornare 0(cioè inserimento riuscito)
		assertEquals(sistema.inserisciNegozio("coop","via garibaldi 8","010-22345","coopGaribaldi@gmail.com", 
											  "mario","rossi","mr@gmail.com","marros","marros22", annulla, conferma),0);
		//secondo controllo: la garanzia di successo e' che il negozio deve essere presente nella lista dei negozi (quindi devono esserci 2 
		//                   oggetti), inoltre verifico che il nome del negozio sia corretto e l'associazione corretta del Manager del negozio
		assertEquals(sistema.getNegozi().size(),2);
		assertEquals(sistema.getNegozi().get(1).getNome(),"coop");
		assertEquals(sistema.getNegozi().get(1).getManagerNegozio().getUsername(), "marros");
	}
	
	/**
	 *  Scenario alternativo 4a: Il manager del sistema annulla l'operazione di 
	 *                           inserimento (prima di richiedere l'inserimento).
	 */
	@Test
	public void InserireNegozio4a(){
		//non dipendente da 'conferma', qualsiasi valore passato andrebbe bene poiche' sara' controllato successivamente.  
		annulla = true;
		conferma = true;
		assertEquals(sistema.inserisciNegozio("coop","via garibaldi 8","010-22345","coopGaribaldi@gmail.com", 
											  "mario","rossi","mr@gmail.com","marros","marros22", annulla, conferma),4);
		assertEquals(sistema.getNegozi().size(),1);
	}
	
	/**
	 *  Scenario alternativo 5a: Il Sistema trova un errore nel nome del nuovo negozio inserito
	 *                           (gia' presente nel sistema).     
	 */
	//NOTA: si considera soltanto il controllo sull'unicita' del nome
	@Test
	public void InserireNegozio5a(){
		//non dipendente da 'conferma'
		annulla = false;
		conferma = true;
		//nome del nuovo negozio gia' esistente nel sistema
		assertEquals(sistema.inserisciNegozio("basko","via garibaldi 8","010-22345","coopGaribaldi@gmail.com", 
											  "erika", "sole", "es@gmail.com", "erisol", "erisol22", annulla, conferma),1);
		assertEquals(sistema.getNegozi().size(),1);
	}
	
	/**
	 *  Scenario alternativo 5b: Il Sistema trova un errore nell'username del manager del negozio
	 *                           (gia' presente nel sistema).
	 */
	@Test
	public void InserireNegozio5b(){
		//non dipendente da 'conferma'
		annulla = false;
		conferma = true;
		//username del nuovo Manager del negozio gia' esistente nel sistema
		assertEquals(sistema.inserisciNegozio("coop","via garibaldi 8","010-22345","coopGaribaldi@gmail.com", 
											  "erika", "sole", "es@gmail.com", "emabru", "erisol22", annulla, conferma),2);
		assertEquals(sistema.getNegozi().size(),1);
	}
	
	/**
	 *  Scenario alternativo 8a: Il Manager del sistema non conferma l'operazione, quindi
	 *                           l'inseriemnto non deve avvenire.
	 */
	@Test
	public void InserireNegozio8a(){
		annulla = false;
		conferma = false;
		assertEquals(sistema.inserisciNegozio("coop","via garibaldi 8","010-22345","coopGaribaldi@gmail.com", 
											  "erika", "sole", "es@gmail.com", "erisol", "erisol22", annulla, conferma),3);
		assertEquals(sistema.getNegozi().size(),1);
	}


}

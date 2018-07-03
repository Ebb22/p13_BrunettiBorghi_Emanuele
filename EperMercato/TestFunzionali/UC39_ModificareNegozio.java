import static org.junit.Assert.*;
import org.junit.*;

import EperMercato.Sistema;
/**
 * @author Brunetti Borghi Emanuele
 */
/**
*  In questo caso d'uso il Manager del sistema potra', una volta selezionato il negozio di proprio interesse, decidere
*  se modificare le caratteristiche del negozio o i dati personali del Manager del negozio. Questa scelta a livello
*  grafico avvera' tramite il click a pulsanti, rispettivamente "modifica caratteristiche negozio" e "modifica 
*  dati manager negozio". Per fare questo a livello di codice si passa un valore boolean (scelta):
*  -true se si vogliono modificare caratteristiche del negozio
*  -false se si vogliono modificare i dati del Manager del negozio
*/
public class UC39_ModificareNegozio {
	Sistema sistema;
	
	//variabili booleane create per simulare la risposta a video da parte dell'utente.
	//annulla: se l'utente annulla l'operazione prima di richiedere la modifica dei dati del negozio selezionato
	//conferma: se l'utente intende confermare la richiesta inviata
	//scelta: rappresenta la scelta del Manager del sistema sull'oggetto su cui apportare modifiche
	boolean annulla, conferma, scelta;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
		//creo un nuovo negozio e il suo manager
		sistema.inserisciNegozio("basko", "via Procida 1", "010-53421", "baskoProcida@gmail.com", 
								 "paolo", "grisu", "pg@gmail.com", "paogri", "paogri22", false, true);
	}
	
	/**
	 *  Scenario principale: Il Manager del sistema comunica di voler modificare le caratteristiche 
	 *  				     del negozio scelto, questa avviene con susccesso.
	 */
	@Test
	public void ModificareNegozio(){
		conferma = true;
		annulla = false;
		//Modifico caratteristiche del negozio (scelta=true)
		scelta = true;
		//primo conrollo: il metodo di modifica delle caratteristiche del negozio (presente nel sistema) con i dati inseriti 
	    //                deve ritornare 0(cioè modifica caratteristiche effettuata con successo)
		assertEquals(sistema.modificaNegozio(scelta, "basko", "Basko", null, "010-223345", null, null, null,
											 null, null, null, annulla, conferma),0);
		//secondo controllo: devono essere ancora presente un solo negozio nel sistema
		assertEquals(sistema.getNegozi().size(),1);
		//terzo controllo: verifico che i dati siano variati solo dove indicato e come indicato
		//NOTA: valore 'null' corrisponde a non voler modificare quel campo
		assertEquals(sistema.getNegozi().get(0).getNome(),"Basko");
		assertEquals(sistema.getNegozi().get(0).getIndirizzo(),"via Procida 1");
		assertEquals(sistema.getNegozi().get(0).getNumeroTelefono(),"010-223345");
		assertEquals(sistema.getNegozi().get(0).getEmail(),"baskoProcida@gmail.com");	
	}
	/**
	 *  Scenario alternativo 2a: Il nome del negozio da modificare non e' presente nel sistema
	 */
	@Test
	public void ModificareNegozio2a(){
		//questo scenario non dipende da 'annulla' e 'conferma'
		conferma = true;
		annulla = false;
		scelta = true;
		assertEquals(sistema.modificaNegozio(scelta, "coop", "Basko", null, "010-223345", null,null, null,
				 							 null, null, null, annulla, conferma),2);
		//primo controllo: Controllo che nessun dato del negozio sia variato
		assertEquals(sistema.getNegozi().get(0).getNome(),"basko");
		assertEquals(sistema.getNegozi().get(0).getIndirizzo(),"via Procida 1");
		assertEquals(sistema.getNegozi().get(0).getNumeroTelefono(),"010-53421");
		assertEquals(sistema.getNegozi().get(0).getEmail(),"baskoProcida@gmail.com");
		//secondo controllo: Controllo che nessun dato del Manager del negozio sia variato
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getNome(), "paolo");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getCognome(), "grisu");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getEmail(), "pg@gmail.com");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getUsername(), "paogri");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getPassword(), "paogri22");	
	}
	
	/**
	 *  Scenario alternativo 4a: Il Manager del sistema comunica di voler modificare i dati perosnali del Manager
	 *  						 del negozio scelto, questa avviene con susccesso.
	 */
	@Test
	public void ModificareNegozio4a(){
		conferma = true;
		annulla = false;
		//Modifico dati personali del Manager del negozio (scelta=false)
		scelta = false;
		//primo conrollo: il metodo di modifica dei dati personali del Manager del negozio (presente nel sistema)
		//				  con i dati inseriti deve ritornare 6(cioè modifica dati effettuata con successo)
		assertEquals(sistema.modificaNegozio(scelta, "basko", null, null, null, null, null, null, 
											 "paolo.grisu@gmail.com", "polgribasko", "polgribasko22", annulla, conferma),6);
		//secondo controllo: devono essere ancora presente un solo negozio nel sistema
		assertEquals(sistema.getNegozi().size(),1);
		//terzo controllo: verifico che i dati siano variati solo dove indicato e come indicato
		//NOTA: parametro 'null' corrisponde a non voler variare quel campo
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getNome(), "paolo");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getCognome(), "grisu");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getEmail(), "paolo.grisu@gmail.com");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getUsername(), "polgribasko");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getPassword(), "polgribasko22");
	}
	
	
	/**
	 *  Scenario alternativo 6a: Il manager trova il negozio desiserato ma  annulla l'operazione
	 *  						 di modifica ancor prima di inserire i dati
	 */
	@Test
	public void ModificareNegozio6a(){
		annulla=true;
		//questo scenario non dipende da 'conferma'
		conferma=true;
		scelta = true;
		assertEquals(sistema.modificaNegozio(scelta, "basko", "Basko", null, "010-223345", null,null, null,
				 							 null, null, null, annulla, conferma),3);
		//primo controllo: Controllo che nessun dato del negozio sia variato
		assertEquals(sistema.getNegozi().get(0).getNome(),"basko");
		assertEquals(sistema.getNegozi().get(0).getIndirizzo(),"via Procida 1");
		assertEquals(sistema.getNegozi().get(0).getNumeroTelefono(),"010-53421");
		assertEquals(sistema.getNegozi().get(0).getEmail(),"baskoProcida@gmail.com");
		//secondo controllo: Controllo che nessun dato del Manager del negozio sia variato
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getNome(), "paolo");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getCognome(), "grisu");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getEmail(), "pg@gmail.com");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getUsername(), "paogri");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getPassword(), "paogri22");	
	}
	
	/**
	 *  Scenario alternativo 7a: Il nuovo nome inserito per il negozio non è valido poiche' gia' esistente nel sistema,
	 *  						  o nuovo Username per il Manager del negozio non valido poiche' gia' esistente
	 */
	@Test
	public void ModificareNegozio7a(){
		annulla=false;
		//questo scenario non dipende da 'conferma'
		conferma=true;
		//inserisco un nuovo negozio cosi' da poter verificare le condizioni imposte
		sistema.inserisciNegozio("coop", "via sesa 1", "010-554321", "coopSesa@gmail.com", 
								 "guido","neri","gn@gmail.com","guiner","guiner22", false, true);
		//modifico caratteristiche di un negozio ma nuovo nome gia' esistente nel sistema
		scelta = true;
		assertEquals(sistema.modificaNegozio(scelta,"basko", "coop", null, "010-223345", null,null, null,
				 							 null, null, null,annulla, conferma),1);
		//primo controllo: le caratteristiche del negozio non devono essere variate (del negozio su cui volevo apportare le modifiche)
		assertEquals(sistema.getNegozi().get(0).getNome(),"basko");
		assertEquals(sistema.getNegozi().get(0).getIndirizzo(),"via Procida 1");
		assertEquals(sistema.getNegozi().get(0).getNumeroTelefono(),"010-53421");
		assertEquals(sistema.getNegozi().get(0).getEmail(),"baskoProcida@gmail.com");
		
		//modifico i dati personali del Manager del negozio per il negozio selezionato, provo ad insierire 
		//un username gia' presente nel sistema
		//Nota: 'emabru' e' l'username del Manager del sistema, 'guiner' usrname del Manager del negozio 'coop'
		scelta = false;
		assertEquals(sistema.modificaNegozio(scelta,"basko", null, null, null, null, null, null,
				 							 null, "emabru", null,annulla, conferma),5);
		assertEquals(sistema.modificaNegozio(scelta,"basko", null, null, null, null, null, null,
				 							 null, "guiner", null,annulla, conferma),5);
		//primo controllo: i dati personali del Manager del negozio non devono essere variati 
		//				   (del negozio su cui volevo apportare le modifiche)
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getNome(), "paolo");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getCognome(), "grisu");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getEmail(), "pg@gmail.com");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getUsername(), "paogri");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getPassword(), "paogri22");	
	}
	
	/**
	 *  Scenario alternativo 8a: Il manager non conferma l'operazione selezionata
	 */
	@Test
	public void ModificareNegozio8a(){
		annulla = false;
		conferma = false;
		//caso in cui non conferma le modifiche inserite nelle caratteristiche del negozio
		scelta = true;
		assertEquals(sistema.modificaNegozio(scelta, "basko", null, null, "010-223345", null, null, null,
											 null, null, null, annulla, conferma),4);
		assertEquals(sistema.getNegozi().get(0).getNome(),"basko");
		assertEquals(sistema.getNegozi().get(0).getIndirizzo(),"via Procida 1");
		assertEquals(sistema.getNegozi().get(0).getNumeroTelefono(),"010-53421");
		assertEquals(sistema.getNegozi().get(0).getEmail(),"baskoProcida@gmail.com");
		
		//caso in cui non conferma le modifiche inserite nei dati personali del Manager del negozio
		scelta = false;
		assertEquals(sistema.modificaNegozio(scelta, "basko", null, null, null, null, "beatrice", "palla",
											 null, null, null, annulla, conferma),4);
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getNome(), "paolo");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getCognome(), "grisu");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getEmail(), "pg@gmail.com");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getUsername(), "paogri");
		assertEquals(sistema.getNegozi().get(0).getManagerNegozio().getPassword(), "paogri22");	
	}
	
}



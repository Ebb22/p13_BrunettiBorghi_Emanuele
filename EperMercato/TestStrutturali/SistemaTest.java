import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class SistemaTest {

	Sistema s;
	boolean annulla, conferma;
	
	@Before
	public void setUp() throws Exception {
		s = new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//effettuo il login ed inserisco un nuovo negozio, quindi effettuo il logout per poter poi valutare 
		//ogni alternativa nei test successivi
		//Nota: decido di inserire un nuovo negozio nel 'before' poiche' utilizzato frequentemente nei test seguenti
		s.login("emabru", "emabru22");
		s.inserisciNegozio("basko", "via Procida 1", "010-53421", "baskoProcida@gmail.com", 
		   		           "paolo", "grisu", "pg@gmail.com", "paogri", "paogri22", false, true);
		s.logout(true);
	}
	
	@Test
	public void SistemaTestCostruttore() {
		assertEquals("emanuele", s.getManagerSistema().getNome());
		assertEquals("brunetti borghi", s.getManagerSistema().getCognome());
		assertEquals("eb@gmail.com",s.getManagerSistema().getEmail());
		assertEquals("emabru", s.getManagerSistema().getUsername());
		assertEquals("emabru22", s.getManagerSistema().getPassword());
		//nella lista negozio sara' presente un negozio poiche' inserito nel 'before' (alla creazione del sistema lista vuota)
		assertEquals(s.getNegozi().size(),1);
		assertTrue(s.getScontiGenerali().isEmpty());
		assertTrue(s.getPremi().isEmpty());
		assertTrue(s.getRecensioniDaValutare().isEmpty());
		assertTrue(s.getRecensioniSistema().isEmpty());
		assertTrue(s.getProdotti().isEmpty());
		//nella lista degli utenti saranno presenti due utenti poiche' inseriti precedentemente,
		//il Manager del sistema e il Manager del negozio (alla creazione del sistema lista vuota)
		assertEquals(s.getUtenti().size(),2);
		assertEquals("emabru",s.getUtenti().get(0).getUsername());
		assertEquals("emabru22",s.getUtenti().get(0).getPassword());
		assertEquals("paogri",s.getUtenti().get(1).getUsername());
		assertEquals("paogri22",s.getUtenti().get(1).getPassword());
	}
	/**
	 * Autenticazione al sistema da parte del Manager del sistema.
	 */
	@Test
	public void SistemaTestLogin() {
		//username errato, autenticazione non eseguita
		assertEquals(s.login("masnar","masnar22"),1);
		assertFalse(s.getManagerSistema().isAutenticato());
		//password errata, autenticazione non eseguita
		assertEquals(s.login("emabru","EMABRU22"),2);
		assertFalse(s.getManagerSistema().isAutenticato());
		//credenziali corrette ed autenticazione eseguita con sucesso
		assertEquals(s.login("emabru","emabru22"),0);
		assertTrue(s.getManagerSistema().isAutenticato());
	}
	
	/**
	 * Deautenticazione al sistema.
	 */
	@Test
	public void SistemaTestLogout() {
		//manager non autenticato nel sistema, non posso chiedere deautenticazione
		assertFalse(s.logout(true));
		//precondizione: devo prima effettuare il login per poi poter richiedere il logout
		s.login("emabru", "emabru22");
		//si richiede la deautenticazione ma non si conferma, quindi non si effettua il logout
		s.logout(false);
		assertTrue(s.getManagerSistema().isAutenticato());	
		//si richiede il logout e si conferma l'operaizone, quindi non si e' piu' autenticati nel sistema
		s.logout(true);
		assertFalse(s.getManagerSistema().isAutenticato());
		
	}
	/**
	 * Inserimento di un nuovo negozio nel sistema.
	 */
	@Test
	public void SistemaTestInserisciNegozio() {
		//dati iniziali, utili per poter analizzare tutti i casi
		annulla = false;
		conferma = true;
		//manager non autenticato nel sistema
		assertEquals(-1,s.inserisciNegozio("coop","via garibaldi 8","010-22345","coopGaribaldi@gmail.com", 
										   "mario","rossi","mr@gmail.com","marros","marros22", annulla, conferma));
		s.login("emabru", "emabru22");
		//inserimento corretto di un negozio e corrispondente Manager del negozio, operazione andata a buon fine
		assertEquals(s.getNegozi().size(),1);
		assertEquals(s.inserisciNegozio("coop","via garibaldi 8","010-22345","coopGaribaldi@gmail.com", 
										"mario","rossi","mr@gmail.com","marros","marros22", annulla, conferma),0);
		assertEquals(s.getNegozi().size(),2);
		//nome del nuovo negozio da inserire gia' esistente nel sistema (deve essere univoco per ipotesi)
		assertEquals(s.inserisciNegozio("coop","via S.Ambrogio 8", "010-123456", "carrefourAmbrogio@gmail.com",
									 	"erika", "solari", "es@gmail.com", "erisol", "erisol22", annulla, conferma),1);
		//manager del sistema inserito gia' esistente, quindi per ipotesi gia' associato ad un altro negozio
		assertEquals(s.inserisciNegozio("carrefour", "via S.Ambrogio 8", "010-123456", "carrefourAmbrogio@gmail.com", 
										"marco","rossi","mr@gmail.com","marros","marros22", annulla, conferma),2);
		//il manager dopo aver inseirto i dati ed aver verificato l'ammissibilita' di questi non conferma l'inserimento
		conferma = false;
		assertEquals(s.inserisciNegozio("carrefour", "via S.Ambrogio 8", "010-123456", "carrefourAmbrogio@gmail.com", 
										"erika", "solari", "es@gmail.com", "erisol", "erisol22", annulla, conferma),3);
		//il manager annulla l'operazione di inserimento ancor prima di aver inviato i dati per l'analisi
		annulla = true;
		assertEquals(s.inserisciNegozio("carrefour", "via S.Ambrogio 8", "010-123456", "carrefourAmbrogio@gmail.com", 
										"erika", "solari", "es@gmail.com", "erisol", "erisol22", annulla, conferma),4);		
	}
	/**
	 * Ricerca di un negozio nel sistema.
	 */
	@Test
	public void SistemaTestRicercaNegozio() {
		//manager non autenticato nel sistema
		assertNull(s.ricercaNegozio("basko"));
		s.login("emabru", "emabru22");
		//creo dati iniziali, utili per poter analizzare tutti i casi
		s.inserisciNegozio("billa","via garibaldi 8","010-22345","billaGaribaldi@gmail.com", 
						   "mario", "rossi", "mr@gmail.com", "marros", "marros22", false, true);
		//negozio ricercato trovato (eseguo tre controlli che mi assicurano la buona riuscita dell'operazione)
		assertEquals(s.ricercaNegozio("basko").size(),1);
		assertEquals(s.ricercaNegozio("basko").get(0).getNome(),"basko");
		assertEquals(s.ricercaNegozio("basko").get(0).getIndirizzo(),"via Procida 1");
		assertEquals(s.ricercaNegozio("basko").get(0).getManagerNegozio().getUsername(),"paogri");
		//negozio ricercato non trovato identico, il sistema restituisce suggerimenti
		//(restituisce negozi con nome paraizle uguale)
		assertEquals(s.ricercaNegozio("b").size(),2);
		assertEquals(s.ricercaNegozio("b").get(0).getNome(),"basko");
		assertEquals(s.ricercaNegozio("b").get(1).getNome(),"billa");
		//negozio non trovato nel sistema e nessun suggerimento restituito
		assertEquals(s.ricercaNegozio("coop").size(),0);		
	}
	/**
	 * Modifica di un negozio(1): nel seguente test si conseidera la modifica delle caratteristiche di un negozio.
	 */
	@Test
	public void SistemaTestModificaNegozio1() {
		//manager non autenticato nel sistema
		assertEquals(s.modificaNegozio(true, "basko", "Basko", null, "010-223345", null, null, null,
				 					   null, null, null, false, true), -1);
		s.login("emabru", "emabru22");
		//setto e creo dati iniziali, utili per poter analizzare tutti i casi
		annulla = false;
		conferma = true;
		//scelta: variabile che indica se modificare caratteristiche negozio (true) o 
		//        dati personali del manager del negozio (false)
		boolean scelta = true;
		//Nota: si eseguono due modifiche per coprire tutti i cammini e condizioni multiple
		s.inserisciNegozio("coop", "via garibaldi 8", "010-22345", "coopGaribaldi@gmail.com", 
						   "mario", "rossi", "mr@gmail.com", "marros", "marros22", annulla, conferma);
		s.inserisciNegozio("carrefour", "via ninja 82", "010-123456", "carrefourNinja@gmail.com", 
						   "nadia", "nango", "nn@gmail.com", "nadnan", "nadnan22", annulla, conferma);
		//negozio trovato e caratteristiche modificate con successo 
		assertEquals(s.modificaNegozio(scelta, "basko", "Basko", null, "010-223345", null, null, null,
									   null, null, null, annulla, conferma),0);
		assertEquals(s.modificaNegozio(scelta, "coop", null, "via Garibaldi 8", null, "coop.Garibaldi@gmail.com", null, null,
									   null, null, null, annulla, conferma),0);
		//il manager dopo aver inseirto i dati ed aver verificato l'ammissibilita' di questi non conferma l'inserimento
		conferma = false;
		assertEquals(s.modificaNegozio(scelta, "Basko", "basko", null, "010-211335", null, null, null,
									   null, null, null, annulla, conferma),4);
		assertEquals(s.modificaNegozio(scelta, "Basko", null, null, "010-211335", null, null, null,
				   					   null, null, null, annulla, conferma),4);
		//nuovo nome inserito gia' esistente (in contrasto con ipotesi di unicita')
		assertEquals(s.modificaNegozio(scelta, "Basko", "carrefour", null, "010-211335", null, null, null,
				   					   null, null, null, annulla, conferma),1);
		//negozio ricercato non esistente nel sistema
		assertEquals(s.modificaNegozio(scelta, "standa", "Standa", null, null, null, null, null,
				   					   null, null, null, annulla, conferma),2);
		//il manager annulla l'operazione ancor prima di inviare le modifiche per l'analisi di ammissinilita'
		annulla = true;
		assertEquals(s.modificaNegozio(scelta, "Basko", "basko", null, "010-211335", null, null, null,
									   null, null, null, annulla, conferma),3);
	}
	/**
	 * Modifica di un negozio(2): nel seguente test si conseidera la modifica dei dati personali del 
	 * 							   manager associato al negozio selezionato.
	 */
	@Test
	public void SistemaTestModificaNegozio2() {
		s.login("emabru", "emabru22");
		//setto e creo dati iniziali, utili per poter analizzare tutti i casi
		annulla = false;
		conferma = true;
		//scelta: variabile che indica se modificare caratteristiche negozio (true) o 
		//		  dati personali del manager del negozio (false)
		//Nota: si eseguono due modifiche per coprire le tutti i cammini e condizioni multiple
		boolean scelta = false;
		s.inserisciNegozio("coop", "via garibaldi 8", "010-22345", "coopGaribaldi@gmail.com", 
				   		   "mario", "rossi", "mr@gmail.com", "marros", "marros22", annulla, conferma);
		//negozio selezionato e dati personali del manager del negozio modificati con successo 
		assertEquals(s.modificaNegozio(scelta, "basko", null, null, null, null, null, null, 
									   "paolo.grisu@gmail.com", "polgribasko", "polgribasko22", annulla, conferma),6);
		assertEquals(s.modificaNegozio(scelta, "coop", null, null, null, null, "Mario", "Rossi", 
				   					   null, null, null, annulla, conferma),6);
		//nuovo username inserito gia' esistente nel sistema
		//Nota: "emabru" username del manager del sistema, quindi non accettabile come nuovo username
		assertEquals(s.modificaNegozio(scelta, "basko", null, null, null, null, null, null, 
				   					   null, "emabru", "baskopol", annulla, conferma),5);
		//il manager dopo aver inseirto i dati ed aver verificato l'ammissibilita' di questi non conferma l'inserimento
		conferma = false;
		assertEquals(s.modificaNegozio(scelta, "basko", null, null, null, null, null, null, 
				   					   null, "polgrisu", "polgrisu22", annulla, conferma),4);
	}
	/**
	 * Elimina un negozio esistente.
	 */
	@Test
	public void SistemaTestEliminaNegozio() {
		assertEquals(s.eliminaNegozio("basko", true),-1);
		s.login("emabru", "emabru22");
		//setto dati iniziali, utili per poter analizzare tutti i casi
		//Nota: inizialmento setto a 'false' la variabile corrispondente alla conferma dell'operazione
		conferma = false;
		//il manager annulla o non conferma l'operazione di rimozione del negozio selezioanto
		assertEquals(s.eliminaNegozio("basko", conferma),2);
		//il negozio ricercato non e' presente nel sistema
		assertEquals(s.eliminaNegozio("coop", conferma),1);
		//il negozio selezionato e' effettivamente eliminato
		conferma = true;
		//un negozio presente nel sistema prima dell'eliminazione
		assertEquals(s.getNegozi().size(),1);
		assertEquals(s.eliminaNegozio("basko", conferma),0);
		//nessun negozio presente nel sistema dopo l'eliminazione
		assertEquals(s.getNegozi().size(),0);
	}
	/**
	 * Inserimento di un nuovo sconto generale.
	 */
	@Test
	public void SistemaTestInserisciScontoGenerale() {
		//dati iniziali, utili per poter analizzare tutti i casi
		annulla = false;
		conferma = true;
		//manager non autenticato nel sistema
		assertEquals(-1,s.inserisciScontoGenerale("ab12e", "3% di sconto per tutti i clienti con una spesa minima di 5€ fino ad una spesa di 50€",
												  3.0f, 5.0f, 50.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		s.login("emabru", "emabru22");
		//inserimento corretto di un nuovo sconto generale
		assertEquals(0,s.getScontiGenerali().size());
		assertEquals(0,s.inserisciScontoGenerale("ab12e", "3% di sconto per tutti i clienti con una spesa minima di 20€ fino ad una spesa di 50€",
				  								 3.0f, 5.0f, 50.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		assertEquals(1,s.getScontiGenerali().size());
		
		//DATI INSERITI INAMISSIBILI CONCETTUALMTE:
		//data di inizio posteriore a quella di fine
		assertEquals(1,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 50€", 5.0f, 
												 50.01f, 1000.0f, new Date(119,9,12), new Date(119,7,12), annulla, conferma));
		//data di inizio antecedente ad oggi
		assertEquals(1,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 50€", 5.0f, 
												 50.01f, 1000.0f, new Date(117,9,12), new Date(119,7,12), annulla, conferma));
		//soglia minima maggiore di quella massima
		assertEquals(2,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 50€", 5.0f, 
											     100.0f, 50.1f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		//soglia minima inferiore a 0 
		assertEquals(2,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 50€", 5.0f, 
			     								 -1.0f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		//soglia massima superiore a 1000 (non ammessa per ipotesi di sistema)
		assertEquals(2,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 50€", 5.0f, 
				 								 50.01f, 2000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		//percentuale non compresa tra 0.1 e 100
		assertEquals(3,s.inserisciScontoGenerale("dvds6", "3% di sconto per tutti su una spesa superiore a 50€", -2.0f, 
												 50.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		assertEquals(3,s.inserisciScontoGenerale("dvds6", "3% di sconto per tutti su una spesa superiore a 50€", 200.0f, 
				 								 50.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		
		//DATI INSERITI INSAMISSIBILI CON QUELLI GIA' PRESENTI NEL SISTEMA:
		//codice sconto alfanumerico gia' esistente (deve essere univoco)
		assertEquals(4,s.inserisciScontoGenerale("ab12e", "5% di sconto per tutti su una spesa superiore a 50€", 5.0f, 
												 50.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		//soglia minima non compatibile con un'altra presente nel sistema (confronti con quelle presenti nel sistema)
			//minima inserita maggiore della minima ma non della massima (per lo stesso sconto)
		assertEquals(5,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 20€", 5.0f, 
												 10.0f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
			//minima inserita minore della minima ma massima minore della minima (per lo stesso sconto)
		assertEquals(5,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 20€", 5.0f, 
				 								 0.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
			//una o entrmabe le soglie inserite uguali ad una esistente (massima o minima) nel sistema
			//Nota: basta il seguente inserimento (oltre a quelli precedenti) per poter soddisfare MCC 
			//		(definizione sotto), quindi l'operatore '|'
		assertEquals(5,s.inserisciScontoGenerale("dvds6", "5% di sconto per tutti su una spesa superiore a 20€", 5.0f, 
				 								 5.0f, 5.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
		
		//Aggiungo un nuovo sconto per poter soddisfare la propieta' BRANCH COVERAGE (tutti i branch coperti)
		assertEquals(0,s.inserisciScontoGenerale("dvds6", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
												 0.1f, 1.0f, new Date(119,5,12), new Date(119,7,12), annulla, conferma));
											 
		//il Manager dopo aver inseirto i dati ed aver verificato l'ammissibilita' di questi non conferma l'inserimento
		conferma = false;
		assertEquals(7,s.inserisciScontoGenerale("a2b8c", "5% di sconto per tutti su una spesa superiore a 100€", 5.0f, 
				 							     100.1f, 1000.0f, new Date(119,1,12), new Date(119,3,12), annulla, conferma));							 
		//il Manager annulla l'operazione di inserimento ancor prima di aver inviato i dati per l'analisi
		annulla = true;
		assertEquals(6,s.inserisciScontoGenerale("a2b8c", "5% di sconto per tutti su una spesa superiore a 100€", 5.0f, 
				   								 100.1f, 1000.0f, new Date(119,1,12), new Date(119,3,12), annulla, conferma));
		
		//NOTA: MCC(MULTIPLE CONDITION COVERAGE): Ogni condizione composta (cioè che contiene AND e/o OR) ogni combinazione delle 
		//                                        condizioni atomiche deve essere coperta durante l’esecuzione dei casi di test			
	}
	/**
	 * Elimina un negozio esistente.
	 */
	@Test
	public void SistemaTestEliminaScontoGenerale() {
		assertEquals(s.eliminaScontoGenerale("ab12e", true),-1);
		s.login("emabru", "emabru22");
		//setto e creo dati iniziali, utili per poter analizzare tutti i casi
		//Nota: inizialmento setto a 'false' la variabile corrispondente alla conferma dell'operazione
		conferma = false;
		s.inserisciScontoGenerale("ab12e", "3% di sconto per tutti i clienti con una spesa minima di 5€ fino ad una spesa di 50€",
								  3.0f, 5.0f, 50.0f, new Date(119,5,12), new Date(119,7,12), false, true);
		
		//il manager non trova lo sconto di proprio interesse poiche' non presente nel sistema (non dipende da conferma)
		assertEquals(s.eliminaScontoGenerale("dvds6", conferma),1);
		//il manager annulla o non conferma l'operazione di rimozione del negozio selezionato
		assertEquals(s.eliminaScontoGenerale("ab12e", conferma),2);
		//il manager trova e seleziona lo sconto di ineresse e lo elimina
		conferma = true;
		assertEquals(s.eliminaScontoGenerale("ab12e", conferma),0);
	}
	/**
	 * Inserimento di un nuovo premio.
	 */
	@Test
	public void SistemaTestInserisciPremio() {
		//dati iniziali, utili per poter analizzare tutti i casi
		annulla = false;
		conferma = true;
		//manager non autenticato nel sistema
		assertEquals(-1,s.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), 
					  					  new Date(119,11,1), annulla, conferma));
		s.login("emabru", "emabru22");
		//inserimento corretto di un nuovo premio
		assertEquals(0,s.getPremi().size());
		assertEquals(0,s.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), 
										 new Date(119,11,1), annulla, conferma));
		assertEquals(1,s.getPremi().size());
		
		//DATI INSERITI INAMISSIBILI CONCETTUALMTE:
		//pezzi disponibili minori di 1
		assertEquals(1,s.inserisciPremio("Set di 6 bicchieri colorati", 1200, 0, new Date(119,6,1), 
				     				     new Date(119,11,1), annulla, conferma));
		//data di inzio di validita' successiva a quella di fine validita'
		assertEquals(2,s.inserisciPremio("Set di 6 bicchieri colorati", 1200, 6, new Date(119,10,1), 
								         new Date(119,8,1), annulla, conferma));
		//data di inzio precedente alla data di inseriemento del premio (quindi precedente ad ora)
		assertEquals(2,s.inserisciPremio("Set di 6 bicchieri colorati", 1200, 6, new Date(118,1,1), 
			     						 new Date(119,11,1), annulla, conferma));
		
		//DATI INSERITI INSAMISSIBILI CON ALTRI GIA' PRESENTI NEL SISTEMA:
		assertEquals(3,s.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), 
										 new Date(119,11,1), annulla, conferma));
		
		//il manager non conferma l'operazione dopo che la buona riuscita sull'analisi dei dati inseriti
		conferma = false;
		assertEquals(5,s.inserisciPremio("Set di 6 bicchieri colorati", 1200, 6, new Date(119,6,1), 
			     						 new Date(119,11,1), annulla, conferma));
		//il manager annulla l'operazione di inserimento ancor prima dell'invio dei dati per l'analisi sulla validita'
		annulla = true;
		assertEquals(4,s.inserisciPremio("Set di 6 bicchieri colorati", 1200, 6, new Date(119,6,1), 
				 						 new Date(119,11,1), annulla, conferma));
	}
	/**
	 * Elimina un premio esistente.
	 */
	@Test
	public void SistemaTestEliminaPremio() {
		assertEquals(s.eliminaPremio(0, true),-1);
		s.login("emabru", "emabru22");
		//setto e creo dati iniziali, utili per poter analizzare tutti i casi
		//Nota: inizialmento setto a 'false' la variabile corrispondente alla conferma dell'operazione.
		//      Il premio inserito avra' 'id' uguale a 0, poiche' asseganto intero che si autoincrementa
		//      ad ogni inserimento partendo da 0.
		conferma = false;
		s.inserisciPremio("Set di 6 bicchieri colorati", 1200, 6, new Date(119,6,1), 
				          new Date(119,11,1), false, true);
		//il manager non trova il premio di proprio interesse poiche' non presente nel sistema (non dipende da conferma)
		assertEquals(s.eliminaPremio(6, conferma),1);
		//il manager annulla o non conferma l'operazione di rimozione del premio selezionato
		assertEquals(s.eliminaPremio(0, conferma),2);
		//il manager trova e seleziona il premio di ineresse e lo elimina
		conferma = true;
		assertEquals(s.eliminaPremio(0, conferma),0);
	}
	/**
	 * Inserire una recensione.
	 */
	//NOTA: METODO NON SVILUPPATO NEL DETTAGLIO POICHE' NON DI INTERESSE NELLA NOSTRA VISIONE DEL SISTEMA, INFATTI
	 //     L'ATTORE PRINCIPALE PER QUESTA FUNZIONE SARA' IL CLIENTE
	@Test
	public void SistemaTestCreaRecensione() {
		s.creaRecensione("buon negozio", "buona scelta di prodotti a prezzi onesti", new Date(18,10,10), "negozio",
	   			   		 "basko", "marros", 4);
		//verifico che la recensione sia stata effettivamente inserita e che i dati siano corretti
		//NOTA: Ogni recensione creata da un utente sara' inserita nella lista delle recensioni da valutare, sara' poi
		//      compito del Manager del sistema decidere se approvarla, quindi pubblicarla, o rifiutarla quindi eliminarla
		assertEquals(s.getRecensioniDaValutare().size(),1);
		assertEquals(s.getRecensioniDaValutare().get(0).getTitolo(),"buon negozio");
		assertEquals(s.getRecensioniDaValutare().get(0).getDescrizione(),"buona scelta di prodotti a prezzi onesti");
		assertEquals(s.getRecensioniDaValutare().get(0).getData(),new Date(18,10,10));
		assertEquals(s.getRecensioniDaValutare().get(0).getOggetto(),"negozio");
		assertEquals(s.getRecensioniDaValutare().get(0).getNomeOggetto(),"basko");
		assertEquals(s.getRecensioniDaValutare().get(0).getAutore(),"marros");	
	}
	/**
	 * Inserire un nuovo prodotto.
	 */
	//NOTA: METODO NON SVILUPPATO NEL DETTAGLIO POICHE' NON DI INTERESSE NELLA NOSTRA VISIONE DEL SISTEMA, INFATTI
	//      L'ATTORE PRINCIPALE PER QUESTA FUNZIONE SARA' IL MANAGER DEL NEGOZIO
	@Test
	public void SistemaTestInserisciProdotto() {
		//provo ad inserire un prodotto, non trovando il negozio associato questo non viene effettuato
		s.inserisciProdotto("sctolame", "tonno Nostromo 3x100g", 1.22f, "coop");
		assertEquals(s.getProdotti().size(),0);
		//inseirsco un negozio, utile per poter analizzare tutti i casi
		s.login("emabru", "emabru22");
		//inserisco un prodotto correttamente (come se il prodotto fosse stato inserito dal Manger del negozio "basko")
		//Nota: negozio 'basko' gia' inserito nel sistema
		s.inserisciProdotto("sctolame", "tonno Nostromo 3x100g", 1.22f, "basko");
		assertEquals(s.getProdotti().size(),1);
		//provo ad inserire un prodotto associando un negozio non esistente, l'inserimento
		//non viene effettuato (rimane un solo prodotto esistente nel sistema)
		s.inserisciProdotto("sctolame", "tonno Nostromo 3x100g", 1.22f, "coop");
		assertEquals(s.getProdotti().size(),1);
	}
	/**
	 * Approvare una recensione.
	 */
	@Test
	public void SistemaTestApprovareRecensione() {
		//dati iniziali, utili per poter analizzare tutti i casi
		//Nota: la variabile booleana 'approva' rappresenta la risposta del manager del sistema 
		//      alla richiesta di approvazione della recensione (true=pubblica, false=elimina)
		boolean approva = true;
		conferma = true;
		assertEquals(-1,s.approvareRecensione(0, approva, conferma));
		
		s.login("emabru", "emabru22");
		//nessuna recensione in attesa di approvazione
		assertEquals(4,s.approvareRecensione(0, approva, conferma));
		
		//aggiungo recensioni per poter valutare tutte le alternative:
			//recensione sul sistema (id=0)
		s.creaRecensione("alla grande!", "perfetto", new Date(18,10,10), "sistema",
				   	     null, "erisol", 5);
			//recensione sul negozio ("basko", inserito nel metodo 'before') (id=1)
			//Nota: aggiungo piu' occorrenze per poter esaminare tutti i casi
		s.inserisciNegozio("carrefour", "via ninja 82", "010-123456", "carrefourNinja@gmail.com", 
				   		   "nadia", "nango", "nn@gmail.com", "nadnan", "nadnan22", annulla, conferma);
		s.creaRecensione("buon negozio", "buona scelta di prodotti a prezzi onesti", new Date(18,10,12), "negozio",
	   			   		 "carrefour", "marros", 4);
			//recensione su un prodotto (id=2, id=3)
			//Nota: aggiungo piu' occorrenze per poter esaminare tutti i casi
		s.inserisciProdotto("scatolame", "tonno Nostromo 3x100g", 1.22f, "basko");
		s.inserisciProdotto("scatolame", "mais Valfrutta 100g", 1.0f, "basko");
		s.creaRecensione("buono!", "ottimo rapporto qualita'-prezzo", new Date(18,10,15), "prodotto",
						 "mais Valfrutta 100g", "mariom", 4);
		s.creaRecensione("sconsiglio", "confezione bucata", new Date(18,10,11), "prodotto",
				   		 "mais Valfrutta 100g", "ezior", 2);
		//controllo che tutte le recensioni sia state inserite e in attesa di approvazione
		assertEquals(4,s.getRecensioniDaValutare().size());
		//NOTA: -ogni volta sara' considerata per la valutazione la recensione inserita da piu' tempo (in accordo con documento 'srs')
		//      -il valore di ritorno per una recensione pubblicata, quindi letta ed approvata, sara' 100+numero di recensioni
		//       collegate a quell'oggetto recensito
		//recensione riguardante il sistema letta e pubblicata
		assertEquals(101, s.approvareRecensione(0, approva, conferma));
		//controllo che recensione sia stata eliminata da quelle in attesa di approvazione
		assertEquals(3,s.getRecensioniDaValutare().size());
		//controllo che la recensione approvata sia stata collegata all'oggetto indicato (in questo caso deve
		//essere presente nella lista delle recensioni del sistema)
		assertEquals(1,s.getRecensioniSistema().size());
		
		//recensione riguardate il negozio "carrefour" letta e pubblicata
		assertEquals(101, s.approvareRecensione(1, approva, conferma));
		assertEquals(2,s.getRecensioniDaValutare().size());
		assertEquals(1,s.getNegozi().get(1).getRecensioni().size());
		
		//recensione riguardate il prodotto "mais Valfrutta 100g" letta e pubblicata
		assertEquals(101, s.approvareRecensione(2, approva, conferma));
		assertEquals(1,s.getRecensioniDaValutare().size());
		assertEquals(1,s.getProdotti().get(1).getRecensioni().size());
		
		//il manager legge la recensione e chiede di pubblicarla ma non conferma l'operazione
		conferma = false;
		assertEquals(2, s.approvareRecensione(3, approva, conferma));
		
		//il manager legge la recensione e chiede di elimanrla ma non conferma l'operazione
		approva = false;
		assertEquals(3, s.approvareRecensione(3, approva, conferma));
		//il maanger legge la recensione, non l'approva e la elimina
		conferma = true;
		assertEquals(1, s.approvareRecensione(3, approva, conferma));
		
		//oggetto indicato non esistente (situazione non possibile, sarebbe un errore del sisetma)
			//id=4
		s.creaRecensione("male", "scarsa qualita'", new Date(18,10,12), "manager",
			   		     "manager carrefour", "frarus", 1);
		assertEquals(5, s.approvareRecensione(60, true, true));
			//id=5
		s.creaRecensione("rivedibile", "poca scelta", new Date(18,10,12), "negozio",
	   		     		 "billa", "silbos", 2);
		assertEquals(5, s.approvareRecensione(5, true, true));
			//id=6
		s.creaRecensione("ottimo", "buon prodotto", new Date(18,10,12), "prodotto",
		     		     "restivoil shampoo 700ml", "silbos", 2);
		assertEquals(5, s.approvareRecensione(6, true, true));
	}
	
}

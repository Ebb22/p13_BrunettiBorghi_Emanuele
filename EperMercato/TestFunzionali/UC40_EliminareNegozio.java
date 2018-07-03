import static org.junit.Assert.*;
import org.junit.*;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
public class UC40_EliminareNegozio {
	
	Sistema sistema;
	
	//variabile booleana creata per simulare la risposta a video da parte dell'utente.
	//conferma se il Manager intende confermare l'eliminazione del negozio selezionato
	//NOTA: non si considera l'annullammento dell'operazione poiche' per come strutturata la funzione 'eliminaNegozio'
	//      non aggiungerebbe niente di utile (sarebbe identico al test con risposta negativa alla
	//	    conferma dell'eliminazione), quindi posso considerare soltanto la variabile
	//		conferma per ricopreire i due scenari (pongo conferma=false anche nel caso di annullamento dell'operazione).
	boolean conferma;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
		//creo un nuovo negozio e il suo manager
		sistema.inserisciNegozio("basko", "via Procida 1", "010-53421", "baskoProcida@gmail.com", 
								 "paolo", "grisu", "pg@gmail.com", "paogri", "paogri22", false, true);
		sistema.inserisciNegozio("coop", "via garibaldi 8", "010-22345", "coopGaribaldi@gmail.com", 
								 "mario", "rossi", "mr@gmail.com", "marros", "marros22", false, true);
		
	}
	
	/**
	 *  Scenario principale: Eliminazione corretta del negozio desiderato.
	 */
	@Test
	public void EliminareNegozio(){
		conferma = true;
		//primo conrollo: l'eliminazione di un negozio effettivamente esistente deve ritornare 0
		assertEquals(sistema.eliminaNegozio("basko", conferma),0);
		//secondo controllo: deve essere presente un solo negozio ("coop") e due utenti ("emabru" e "marros")
		assertEquals(sistema.getNegozi().size(),1);
		assertEquals(sistema.getUtenti().size(),2);
		//terzo controllo: verifico che il negozio presente sia corretto, cioe' eliminato quello voluto
		assertEquals(sistema.getNegozi().get(0).getNome(),"coop");
		assertEquals(sistema.getNegozi().get(0).getIndirizzo(),"via garibaldi 8");
		assertEquals(sistema.getNegozi().get(0).getNumeroTelefono(),"010-22345");
		assertEquals(sistema.getNegozi().get(0).getEmail(),"coopGaribaldi@gmail.com");
		//quarto controllo: verifico che i due utenti nel sistema siano corretti, quindi eliminato il Manager del negozio corretto
		assertEquals(sistema.getUtenti().get(0).getUsername(),"emabru");
		assertEquals(sistema.getUtenti().get(1).getUsername(),"marros");
	}
	
	/**
	 *  Scenario alternativo 3a: Il nome del negozio da eliminare non esiste nel sistema.
	 */
	@Test
	public void EliminareNegozio3a(){
		//questo scenario non dipende da 'conferma'
		conferma=true;
		//primo controllo: il metodo 'eliminaNegozio' deve ritornare la ricerca fallita sul nome, quindi 1
		assertEquals(sistema.eliminaNegozio("billa", conferma),1);
		//secondo controllo: devono ancora essere presenti due negozi nel sistema e tre utenti
		assertEquals(sistema.getNegozi().size(),2);
		assertEquals(sistema.getUtenti().size(),3);
	}
	
	/**
	 *  Scenario alternativo 5a-7a: Il manager annulla l'operazione - non conferma l'eliminazione del negozio.
	 */
	@Test
	public void EliminareNegozio5a7a(){
		conferma=false;
		assertEquals(sistema.eliminaNegozio("basko", conferma),2);
		assertEquals(sistema.getNegozi().size(),2);
		assertEquals(sistema.getUtenti().size(),3);
	}
	
}

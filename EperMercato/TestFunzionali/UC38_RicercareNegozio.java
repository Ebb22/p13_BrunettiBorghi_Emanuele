import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
public class UC38_RicercareNegozio {
	Sistema sistema;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
		//inserisco nuovi negozi nel sistema cosi' da poter analizzare ogni alternativa
		sistema.inserisciNegozio("basko", "via Procida 1", "010-53421", "baskoProcida@gmail.com", 
								 "paolo", "grisu", "pg@gmail.com", "paogri", "paogri22", false, true);
		sistema.inserisciNegozio("base", "via Motta 123", "010-46688", "baseMotta@gmail.com", 
								 "gisueppe", "brana", "gb@gmail.com", "giubra", "giubra22", false, true);
		sistema.inserisciNegozio("billa", "via Casia 28", "010-44322", "billaCasia@gmail.com", 
								 "michele", "mazzu", "mm@gmail.com", "micmaz", "micmaz22", false, true);
	}
	
	/**
	 *  Scenario principale: Ricerca andata a buon fine, vengono mostrati i risultati aspetatti.
	 */
	@Test
	public void InserireNegozio(){
		//Primo controllo: La ricera "basko" deve tornare un solo risultato e deve essere proprio
		//				   il negozio 'basko'
		assertEquals(sistema.ricercaNegozio("basko").size(),1);
		assertEquals(sistema.ricercaNegozio("basko").get(0).getNome(),"basko");
		assertEquals(sistema.ricercaNegozio("basko").get(0).getIndirizzo(),"via Procida 1");
		assertEquals(sistema.ricercaNegozio("basko").get(0).getManagerNegozio().getUsername(),"paogri");
		
		//Secondo controllo: La ricera "bas" deve tornare due risultati e devono essere i negozi
		//				     'basko' e 'base'
		assertEquals(sistema.ricercaNegozio("bas").size(),2);
		assertEquals(sistema.ricercaNegozio("bas").get(0).getNome(),"basko");
		assertEquals(sistema.ricercaNegozio("bas").get(1).getNome(),"base");
		
		//Terzo controllo: La ricera "b" deve tornare tre risultati e devono essere i negozi
		//				   'basko', 'base' e 'billa'
		assertEquals(sistema.ricercaNegozio("b").size(),3);
		assertEquals(sistema.ricercaNegozio("b").get(0).getNome(),"basko");
		assertEquals(sistema.ricercaNegozio("b").get(1).getNome(),"base");
		assertEquals(sistema.ricercaNegozio("b").get(2).getNome(),"billa");
		
	}
	
	/**
	 *  Scenario alternativo 5a: Il sistema non trova nessun negozio compatibile con quello ricercato.
	 */
	@Test
	public void RicercareNegozio5a(){
		//Nessun negozio restituito come compatibile, viene mostrato un messaggio
		assertEquals(sistema.ricercaNegozio("coop").size(),0);
	}
	
	
}

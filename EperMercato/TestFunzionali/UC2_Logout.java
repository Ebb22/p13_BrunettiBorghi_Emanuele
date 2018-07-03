import static org.junit.Assert.*;
import org.junit.*;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
public class UC2_Logout {
	Sistema sistema;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
	}
	
	/**
	 *  Scenario principale: Richiesta di logout confermata ed eseguita con successo.
	 */
	//NOTA: Siccome non si implementa la parte grafica, la richiesta di conferma dell'operazione (per 
	//      seguire il documento "srs") viene inviata tramite una variabile booleana
	@Test
	public void EffettuareLogout(){
		//si richiama la funzione logout passando 'true' alla richiesta di conferma dell'operazione
		sistema.logout(true);
		//controllo: autenticato deve restituire 'false' per poter affermare che il logout è andato a buon fine
		assertFalse(sistema.getManagerSistema().isAutenticato());
	}
	
	/**
	 *  Scenario alternativo 3a: L'utente non conferma l'operazione, quindi il logout non deve avvenire
	 */
	@Test
	public void EffettuareLogout3a(){
		//si richiama la funzione logout passando 'false' alla richiesta di conferma dell'operazione
		sistema.logout(false);
		//controllo: autenticato deve restituire 'true' per poter affermare che il logout non e' stato effettuato
		assertTrue(sistema.getManagerSistema().isAutenticato());
	}
}

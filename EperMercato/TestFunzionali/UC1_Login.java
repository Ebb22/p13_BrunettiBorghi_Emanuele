import static org.junit.Assert.*;
import org.junit.*;

import EperMercato.Sistema;
/**
 * @author Brunetti Borghi Emanuele
 */

public class UC1_Login {
	
	Sistema sistema;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema e gli associo il Manager del sistema
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
	}
	
	
	/**
	 *  Scenario principale: Inserimento delle credenziali corrette e conseguente
	 *  					 autenticazione effettuata con successo.
	 */
	@Test
	public void Autenticarsi(){
		//primo conrollo: il metodo login con le credenziali inserite deve ritornare 0(cioè autenticazione riuscita)
		assertEquals(sistema.login("emabru","emabru22"),0);
		//secondo controllo: attributo 'autenticato' deve essere 'true'
		assertTrue(sistema.getManagerSistema().isAutenticato());
	}
	
	
	/**
	 *  Scenario alternativo 3a: Inserimento delle credenziali con username errato, conseguente segnalazione
	 *                           dell'errore nell'username.
	 */
	@Test
	public void Autenticarsi3a(){
		//primo conrollo: il metodo login con le credenziali inserite deve ritornare 1(cioè username non trovato)
		assertEquals(sistema.login("bruema","emabru22"),1);
		//secondo controllo: attributo 'autenticato' deve essere 'false'
		assertFalse(sistema.getManagerSistema().isAutenticato());
	}
	
	/**
	 *  Scenario alternativo 4a: Inserimento delle credenziali con username corretto e passaword errata,
	 *                           conseguente segnalazione dell'errore nella password.         
	 */
	@Test
	public void Autenticarsi4a(){
		//primo conrollo: il metodo login con le credenziali inserite deve ritornare 2(cioè password non
		//                combacia con username inserito)
		assertEquals(sistema.login("emabru","emabru"),2);
		//secondo controllo: attributo 'autenticato' deve essere 'false'
		assertFalse(sistema.getManagerSistema().isAutenticato());
	}
}

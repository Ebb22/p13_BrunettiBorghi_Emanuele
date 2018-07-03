import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Utente.ManagerNegozio;;

/**
 * @author Brunetti Borghi Emanuele
 */
public class ManagerNegozioTest {

	ManagerNegozio mn;	
	
	@Before
	public void setUp() throws Exception {
		mn = new ManagerNegozio(1, "dario", "rossi", "dario.rossi@gmail.com", "darros", "darros22");
	}
	
	@Test
	public void ManagerNegozioTestCostruttore() {
		assertEquals(1,mn.getId());
		assertEquals("dario", mn.getNome());
		assertEquals("rossi", mn.getCognome());
		assertEquals("dario.rossi@gmail.com", mn.getEmail());
		assertEquals("darros", mn.getUsername());
		assertEquals("darros22", mn.getPassword());
		assertFalse(mn.isAutenticato());
	}
	
	@Test
	public void ManagerNegozioSetterGetterTest() {
		//Test di 'setNome', chiamata a funzione e controllo che sia stata realmente eseguita
		mn.setNome("anna");
		assertEquals("anna", mn.getNome());
		//Test di 'setCognome', chiamata a funzione e controllo che sia stata realmente eseguita
		mn.setCognome("neri");
		assertEquals("neri", mn.getCognome());
		//Test di 'setEmail', chiamata a funzione e controllo che sia stata realmente eseguita
		mn.setEmail("anna.neri@gmail.com");
		assertEquals("anna.neri@gmail.com", mn.getEmail());
		//Test di 'setUsername', chiamata a funzione e controllo che sia stata realmente eseguita
		mn.setUsername("annner");
		assertEquals("annner", mn.getUsername());
		//Test di 'setPassword', chiamata a funzione e controllo che sia stata realmente eseguita
		mn.setPassword("annner22");
		assertEquals("annner22", mn.getPassword());
		//Test di 'setAtenticato', chiamata a funzione e controllo che sia stata realmente eseguita
		mn.setAutenticato(true);
		assertTrue(mn.isAutenticato());
	}
	
	@Test
	public void ManagerNegozioTestModifica() {
		//creo un nuovo Manager cosi' da poter analizzare tutte le alternative
		ManagerNegozio mn2 = new ManagerNegozio(2, "carlo", "viola", "carlo.viola@gmail.com", "carvio", "carvio22");
		
		mn.modifica("Dario", "Rossi", null, null, null);
		assertEquals(1,mn.getId());
		assertEquals("Dario", mn.getNome());
		assertEquals("Rossi", mn.getCognome());
		assertEquals("dario.rossi@gmail.com", mn.getEmail());
		assertEquals("darros", mn.getUsername());
		assertEquals("darros22", mn.getPassword());
		assertFalse(mn.isAutenticato());
		
		mn2.modifica(null, null, "carloviola@libero.it", "carlov", "carlov22");
		assertEquals(2,mn2.getId());
		assertEquals("carlo", mn2.getNome());
		assertEquals("viola", mn2.getCognome());
		assertEquals("carloviola@libero.it", mn2.getEmail());
		assertEquals("carlov", mn2.getUsername());
		assertEquals("carlov22", mn2.getPassword());
		assertFalse(mn2.isAutenticato());
	}
	
	
}

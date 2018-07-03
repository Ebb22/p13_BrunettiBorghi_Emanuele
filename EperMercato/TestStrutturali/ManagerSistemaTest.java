import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import Utente.ManagerSistema;

/**
 * @author Brunetti Borghi Emanuele
 */
public class ManagerSistemaTest {

	@Test
	public void ManagerSistemaTestCostruttore() {
		ManagerSistema ms = new ManagerSistema(0, "remo", "baglio", "remo.baglio@gmail.com", "rembag", "rembag22");
		assertEquals(0,ms.getId());
		assertEquals("remo", ms.getNome());
		assertEquals("baglio", ms.getCognome());
		assertEquals("remo.baglio@gmail.com", ms.getEmail());
		assertEquals("rembag", ms.getUsername());
		assertEquals("rembag22", ms.getPassword());
		assertFalse(ms.isAutenticato());
	}
	
}

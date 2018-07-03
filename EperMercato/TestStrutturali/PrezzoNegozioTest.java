import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import dataType.PrezzoNegozio;

/**
 * @author Brunetti Borghi Emanuele
 */
public class PrezzoNegozioTest {

	PrezzoNegozio pn;
		
	@Test
	public void PrezzoNegozioSetterGetterTest() {
		pn = new PrezzoNegozio();
		pn.setPrezzo(5.0f);
		assertEquals(5.0f, pn.getPrezzo(), 0);
		pn.setNegozio(null);
		assertNull(pn.getNegozio());
	}
	
}

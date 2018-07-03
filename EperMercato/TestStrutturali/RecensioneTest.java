import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Recensione;


/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class RecensioneTest {
	
	Recensione r;
	
	@Before
	public void setUp() throws Exception {
		r = new Recensione(0, "Il meglio", "Rapporto qualita'-prezzo senza eguali", 4, new Date(118,7,10), "negozio",
	   			   		   "basko", "saramerlo");
	}
	
	@Test
	public void RecensioneTestCostruttore() {
		assertNotNull(r);
		assertEquals(0, r.getId());
		assertEquals("Il meglio", r.getTitolo());
		assertEquals("Rapporto qualita'-prezzo senza eguali", r.getDescrizione());
		assertEquals(4, r.getVoto());
		assertEquals(new Date(118,7,10),r.getData());
		assertEquals("negozio", r.getOggetto());
		assertEquals("basko", r.getNomeOggetto());
		assertEquals("saramerlo", r.getAutore());
		assertFalse(r.isVisionata());
	}
	
	@Test
	public void RecensioneSetterGetterTest() {
		//Test di 'setTitolo', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setTitolo("Cibo scaduto");
		assertEquals("Cibo scaduto", r.getTitolo());
		//Test di 'setDescrizione', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setDescrizione("Mi e' arrivato cibo scaduto, da denuncia!");
		assertEquals("Mi e' arrivato cibo scaduto, da denuncia!", r.getDescrizione());
		//Test di 'setVoto', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setVoto(1);
		assertEquals(1, r.getVoto());
		//Test di 'setData', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setData(new Date(118,0,10));
		assertEquals(new Date(118,0,10),r.getData());
		//Test di 'setOggetto', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setOggetto("sistema");
		assertEquals("sistema", r.getOggetto());
		//Test di 'setNomeOggetto', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setNomeOggetto(null);
		assertNull(r.getNomeOggetto());
		//Test di 'setAutore', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setAutore("tom");
		assertEquals("tom", r.getAutore());
		//Test di 'setVisionata', chiamata a funzione e controllo che sia stata realmente eseguita
		r.setVisionata(true);
		assertTrue(r.isVisionata());
	}
	
}

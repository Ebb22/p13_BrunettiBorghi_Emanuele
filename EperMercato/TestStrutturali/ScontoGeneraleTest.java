import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.ScontoGenerale;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class ScontoGeneraleTest {
	
	ScontoGenerale sg;
	
	@Before
	public void setUp() throws Exception {
		sg = new ScontoGenerale(0, "ab12e", "1% di sconto per tutti fino ad una spesa di 20€",
								1.0f, 0.0f, 20.0f, new Date(119,5,12), new Date(119,7,12));
	}
	
	@Test
	public void ScontoGeneraleTestCostruttore() {
		assertNotNull(sg);
		assertEquals(0, sg.getId());
		assertEquals("ab12e", sg.getCodiceSconto());
		assertEquals("1% di sconto per tutti fino ad una spesa di 20€", sg.getDescrizione());
		assertEquals(1.0f, sg.getPercentuale(), 0);
		assertEquals(0.0f, sg.getSogliaMin(), 0);
		assertEquals(20.0f, sg.getSogliaMax(), 0);
		assertEquals(new Date(119,5,12), sg.getDataInizio());
		assertEquals(new Date(119,7,12), sg.getDataFine());
	}
	
	@Test
	public void ScontoGeneraleSetterGetterTest() {
		//Test di 'setDescrizione', chiamata a funzione e controllo che sia stata realmente eseguita
		sg.setCodiceSconto("ase30");
		assertEquals("ase30", sg.getCodiceSconto());
		//Test di 'setDescrizione', chiamata a funzione e controllo che sia stata realmente eseguita
		sg.setDescrizione("2% su tutti gli acquisti da piu' di 20€ fino al limite massimo");
		assertEquals("2% su tutti gli acquisti da piu' di 20€ fino al limite massimo", sg.getDescrizione());
		//Test di 'setPercentuale', chiamata a funzione e controllo che sia stata realmente eseguita
		sg.setPercentuale(2.0f);
		assertEquals(2.0f, sg.getPercentuale(), 0);
		//Test di 'setSogliaMin', chiamata a funzione e controllo che sia stata realmente eseguita
		sg.setSogliaMin(20.01f);
		assertEquals(20.01f, sg.getSogliaMin(), 0);
		//Test di 'setSogliaMin', chiamata a funzione e controllo che sia stata realmente eseguita
		sg.setSogliaMin(20.01f);
		assertEquals(20.01f, sg.getSogliaMin(), 0);
		//Test di 'setSogliaMax', chiamata a funzione e controllo che sia stata realmente eseguita
		sg.setSogliaMax(1000.0f);
		assertEquals(1000.0f, sg.getSogliaMax(), 0);
	}

}

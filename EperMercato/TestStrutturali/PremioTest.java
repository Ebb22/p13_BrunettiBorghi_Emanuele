import static org.junit.Assert.*;
import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Premio;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class PremioTest {
	
	Premio p;
	
	@Before
	public void setUp() throws Exception {
		p = new Premio(0,"Set posate per 6 persone", 550, 40, new Date(18,6,28), new Date(18,8,28));
	}
	
	@Test
	public void PremioTestCostruttore() {		
		assertNotNull(p);
		assertEquals(0, p.getId());
		assertEquals("Set posate per 6 persone", p.getDescrizione());
		assertEquals(550, p.getPuntiRichiesti());
		assertEquals(40, p.getPezziDisponibili());
		assertEquals(new Date(18,6,28), p.getDataInizio());
		assertEquals(new Date(18,8,28), p.getDataFine());
	}
	
	@Test
	public void PremioSetterGetterTest() {
		//Test di 'setDescrizione', chiamata a funzione e controllo che sia stata realmente eseguita
		p.setDescrizone("Set bicchieri per 6 persone");
		assertEquals(p.getDescrizione(),"Set bicchieri per 6 persone");
	
		//Test di 'setPuntiRichiesti', chiamata a funzione e controllo che sia stata realmente eseguita
		p.setPuntiRichiesti(400);
		assertEquals(p.getPuntiRichiesti(), 400);
	
		//Test di 'setPezziDisponibili', chiamata a funzione e controllo che sia stata realmente eseguita
		p.setPezziDisponibili(35);
		assertEquals(p.getPezziDisponibili(), 35);
		
		//Test di 'setDataInizio', chiamata a funzione e controllo che sia stata realmente eseguita
		p.setDataInizio(new Date(18,7,30));
		assertEquals(p.getDataInizio(), new Date(18,7,30));
		
		//Test di 'setDataFine', chiamata a funzione e controllo che sia stata realmente eseguita
		p.setDataFine(new Date(18,9,30));
		assertEquals(p.getDataFine(), new Date(18,9,30));
	}
	
}

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Negozio;
import EperMercato.Prodotto;
import EperMercato.Recensione;
import dataType.PrezzoNegozio;

/**
 * @author Brunetti Borghi Emanuele
 */
public class ProdottoTest {

	Prodotto p;
	Negozio n;
	
	@Before
	public void setUp() throws Exception {
		n = new Negozio(0, "basko", "via Procida 1", "010-53421", "baskoProcida@gmail.com", null);
		p = new Prodotto(0, "bevande", "angelo poretti birra 3 luppoli ml330x3", 2.15f, n);
	}
	
	@Test
	public void ProdottoTestCostruttore() {
		assertNotNull(p);
		assertEquals(0,p.getId());
		assertEquals("angelo poretti birra 3 luppoli ml330x3", p.getNome());
		assertEquals("bevande", p.getCategoria());		
		//creo un nuovo negozio cosi' da verificare la funzione 'getPrezzoInNegozio'. Restituisce PrezzoNegozio
		//del prodotto per il negozio indicato, da cui posso estrarre il prezzo e il negozio ('null' se il
		//negozio indicato non ha quel prodotto tra i suoi offerti)
		Negozio n1 = new Negozio(1, "coop", "via Corso 1", "010-43452", "coopCorso@gmail.com", null);
		assertEquals(2.15f, p.getPrezzoInNegozio(n).getPrezzo(),0);
		assertEquals(n, p.getPrezzoInNegozio(n).getNegozio());
		assertNull(p.getPrezzoInNegozio(n1));
	}
	
	@Test
	public void ProdottoSetterGetterTest() {
		//Test di 'setRecensioni', chiamata a funzione e controllo che sia stata realmente eseguita
		ArrayList<Recensione> r2= new ArrayList<Recensione>();
		//Aggiungo una recensione cosi da poter verificare che realmente sia stato eseguito il set,
		//quindi la dimensione della lista recensioni deve essere 1
		r2.add(null);
		p.setRecensioni(r2);
		assertEquals(r2, p.getRecensioni());
		assertEquals(p.getRecensioni().size(),1);
		
		p.setCategoria("surgelati");
		assertEquals("surgelati", p.getCategoria());
		p.setNome("cucciolone algida gr.80x6");
		assertEquals("cucciolone algida gr.80x6", p.getNome());
		
		//Test di 'setPrezzoNegozio', chiamata a funzione e controllo che sia stata realmente eseguita
		ArrayList<PrezzoNegozio> pn= new ArrayList<PrezzoNegozio>();
		pn.add(null);
		p.setPrezzoInNegozio(pn);
		assertEquals(pn, p.getPrezzoInNegozio());
		assertEquals(p.getPrezzoInNegozio().size(),1);
	}
	
	/**
	 *  Test per il metodo 'addRecensione', aggiungo due recensioni con parametri tutti 'null' tranne per
	 *  l'identificativo (id) cosi' da poter verificare che siano state inserite entrambe e inoltre 
	 *  eseguo una verifica sull'id cosi' da poter affermare con certezza che siano state inserite correttamente.
	 */
	@Test
	public void ProdottoTestAddRecensione() {
		Recensione r1 = new Recensione(0,null,null,0,null,null,null,null);
		Recensione r2 = new Recensione(1,null,null,0,null,null,null,null);
		p.addRecensione(r1);
		assertEquals(p.getRecensioni().size(),1);
		assertEquals(p.getRecensioni().get(0).getId(),0);
		p.addRecensione(r2);
		assertEquals(p.getRecensioni().size(),2);
		assertEquals(p.getRecensioni().get(1).getId(),1);
	}
	
	@Test
	public void ProdottoTestEquals() {
		//confronto il prodotto con se stesso, deve ritornare 'true'
		assertTrue(p.equals(p));
		//creo due prodotti per coprire tutti i casi, un prodotto con stessa categoria
		//ed uno con stesso nome e categoria differente (non sensato ma utile per il mio test)
		Prodotto p2 = new Prodotto(1, "bevande", "coca-cola ml.2000", 2.10f, n);
		Prodotto p3= new Prodotto(2, "carni", "angelo poretti birra 3 luppoli ml330x3", 2.11f, n);
		assertFalse(p.equals(p2));
		assertFalse(p.equals(p3));
		//confronto un prodotto con un altro oggetto, deve ritornare 'false'
		assertFalse(p.equals(n));
	}
	
}

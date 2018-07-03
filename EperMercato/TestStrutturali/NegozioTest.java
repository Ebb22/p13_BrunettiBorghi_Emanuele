import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import EperMercato.Negozio;
import EperMercato.Prodotto;
import EperMercato.Recensione;
import Utente.ManagerNegozio;

/**
 * @author Brunetti Borghi Emanuele
 */
public class NegozioTest {

	Negozio n;
	ManagerNegozio mn;
	
	@Before
	public void setUp() throws Exception {
		mn = new ManagerNegozio(1, "Luca", "Neri", "lucaNeri@gmai.com", "lucner", "lucner22");
		n=new Negozio(1, "coop", "via G.Mameli 8", "010-12345", "coopMameli@gmail.com", mn);
	}

	@Test
	public void NegozioTestCostruttore() {
		ArrayList<Recensione> recensioni= new ArrayList<Recensione>();
		ArrayList<Prodotto> prodotti= new ArrayList<Prodotto>();
		assertNotNull(n);
		assertEquals(1, n.getId());
		assertEquals("coop", n.getNome());
		assertEquals("via G.Mameli 8", n.getIndirizzo());
		assertEquals("010-12345", n.getNumeroTelefono());
		assertEquals("coopMameli@gmail.com", n.getEmail());
		assertEquals(mn, n.getManagerNegozio());
		assertEquals(recensioni, n.getRecensioni());
		assertEquals(prodotti, n.getProdotti());
	}
	
	@Test
	public void NegozioSetterGetterTest() {
		//Test di 'setNome', chiamata a funzione e controllo che sia stata realmente eseguita
		n.setNome("iperCoop");
		assertEquals("iperCoop", n.getNome());
		
		//Test di 'setIndirizzo', chiamata a funzione e controllo che sia stata realmente eseguita
		n.setIndirizzo("via Leone 28");
		assertEquals("via Leone 28", n.getIndirizzo());
		
		//Test di 'setNemeroTelefono', chiamata a funzione e controllo che sia stata realmente eseguita
		n.setNumeroTelefono("010-54321");
		assertEquals("010-54321", n.getNumeroTelefono());
		
		//Test di 'setEmail', chiamata a funzione e controllo che sia stata realmente eseguita
		n.setEmail("iperCoopLeone@gmail.com");
		assertEquals("iperCoopLeone@gmail.com", n.getEmail());
		
		//Test di 'setManagerNegozio', chiamata a funzione e controllo che sia stata realmente eseguita
		ManagerNegozio mn2 = new ManagerNegozio(2, "Maria", "Mara", "mariaMara@gmail.com", "marmar", "marmar22");
		n.setManagerNegozio(mn2);
		assertEquals(mn2, n.getManagerNegozio());
		
		//Test di 'setRecensioni', chiamata a funzione e controllo che sia stata realmente eseguita
		ArrayList<Recensione> r2= new ArrayList<Recensione>();
		//Aggiungo una recensione cosi da poter verificare che realmente e' stato eseguito il set,
		//quindi la dimensione della lista recensioni deve essere 1
		r2.add(null);
		n.setRecensioni(r2);
		assertEquals(r2, n.getRecensioni());
		assertEquals(n.getRecensioni().size(),1);	
		
		//Test di 'setProdotti', chiamata a funzione e controllo che sia stata realmente eseguita
		ArrayList<Prodotto> p= new ArrayList<Prodotto>();
		//Aggiungo un prodotto cosi da poter verificare che realmente e' stato eseguito il set,
		//quindi la dimensione della lista prodotti deve essere 1
		p.add(null);
		n.setProdotti(p);
		assertEquals(p, n.getProdotti());
		assertEquals(n.getProdotti().size(),1);		
	}
	
	/**
	 *  Test per il metodo 'addRecensione', aggiungo due recensioni con parametri tutti 'null' tranne per
	 *  l'identificativo (id) cosi' da poter verificare che siano state inserite entrambe e inoltre 
	 *  eseguo una verifica sull'id cosi' da poter affermare con certezza che siano state inserite correttamente.
	 */
	@Test
	public void NegozioTestAddRecensione() {
		Recensione r1 = new Recensione(0,null,null,0,null,null,null,null);
		Recensione r2 = new Recensione(1,null,null,0,null,null,null,null);
		n.addRecensione(r1);
		assertEquals(n.getRecensioni().size(),1);
		assertEquals(n.getRecensioni().get(0).getId(),0);
		assertEquals(n.getRecensioni().get(0),r1);
		
		n.addRecensione(r2);
		assertEquals(n.getRecensioni().size(),2);
		assertEquals(n.getRecensioni().get(1).getId(),1);
		assertEquals(n.getRecensioni().get(1),r2);
	}
	
	/**
	 *  Test per il metodo 'addProdotti', aggiungo due recensioni cosi' da poter verificare che siano state inserite entrambe 
	 *  ed inoltre eseguo una verifica sui dati cosi' da poter affermare con certezza che siano state inserite correttamente.
	 */
	@Test
	public void NegozioTestAddProdotto() {
		Prodotto p1 = new Prodotto(0, "scatolame", "tonno Nostromo 3x100g", 1.22f, n);
		Prodotto p2 = new Prodotto(1, null, null, 0.0f, null);
		n.addProdotto(p1);
		assertEquals(n.getProdotti().size(),1);
		assertEquals(n.getProdotti().get(0).getId(),0);
		assertEquals(n.getProdotti().get(0).getNome(),"tonno Nostromo 3x100g");
		assertEquals(n.getProdotti().get(0).getCategoria(),"scatolame");
		assertEquals(n.getProdotti().get(0).getPrezzoInNegozio(n).getPrezzo(),1.22f,0);
		assertEquals(n.getProdotti().get(0).getPrezzoInNegozio(n).getNegozio(),n);
		assertEquals(n.getProdotti().get(0),p1);
		
		n.addProdotto(p2);
		assertEquals(n.getProdotti().size(),2);
		assertEquals(n.getProdotti().get(1).getId(),p2.getId());
		assertNull(n.getProdotti().get(1).getNome());
		assertNull(n.getProdotti().get(1).getCategoria());
	}

}

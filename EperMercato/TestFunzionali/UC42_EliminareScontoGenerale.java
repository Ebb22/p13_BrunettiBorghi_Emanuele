import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class UC42_EliminareScontoGenerale {

	Sistema sistema;
	
	//variabile booleana creata per simulare la risposta a video da parte dell'utente.
	//conferma se il Manager intende confermare l'eliminazione dello sconto generale selezionato.
	//NOTA: non si considera l'annullammento dell'operazione poiche' per come strutturata la funzione
	//		'eliminaScontoGenerale' non aggiungerebbe niente di utile (sarebbe identico al test con 
	// 		risposta negativa alla conferma dell'eliminazione), quindi posso considerare soltanto la variabile
	//		conferma per ricopreire i due scenari (pongo conferma=false anche nel caso di annullamento dell'operazione).
	boolean conferma;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
		//creo e inserisco sconti genereali nel sistema
		sistema.inserisciScontoGenerale("ab12e", "1% di sconto per tutti fino ad una spesa di 20€",
										1.0f, 0.0f, 20.0f, new Date(119,5,12), new Date(119,7,12), false, true);
		sistema.inserisciScontoGenerale("erec1", "3% di sconto per tutti su una spesa superiore a 20€", 3.0f, 
										20.01f, 1000.0f, new Date(119,5,12), new Date(119,7,12), false, true);
	}
	
	/**
	 *  Scenario principale: Eliminazione dello sconto generale desiderato eseguita correttamente
	 */
	@Test
	public void EliminareScontoGenerale(){
		conferma = true;
		//primo conrollo: l'eliminazione di un negozio effettivamente esistente deve ritornare 0
		assertEquals(sistema.eliminaScontoGenerale("erec1", conferma),0);
		//secondo controllo: deve essere presente un solo sconto generale nel sistema
		assertEquals(sistema.getScontiGenerali().size(),1);
		//terzo controllo: verifico che il negozio presente sia corretto, cioe' eliminato quello voluto
		assertEquals(sistema.getScontiGenerali().get(0).getCodiceSconto(),"ab12e");
		assertEquals(sistema.getScontiGenerali().get(0).getDescrizione(),"1% di sconto per tutti fino ad una spesa di 20€");
		assertEquals(sistema.getScontiGenerali().get(0).getPercentuale(),1.0f,0.0f);
		assertEquals(sistema.getScontiGenerali().get(0).getSogliaMin(),0.0f,0.0f);
		assertEquals(sistema.getScontiGenerali().get(0).getSogliaMax(),20.0f,0.0f);
		assertEquals(sistema.getScontiGenerali().get(0).getDataInizio(),new Date(119,5,12));
		assertEquals(sistema.getScontiGenerali().get(0).getDataFine(),new Date(119,7,12));
	}
	
	/**
	 *  Scenario alternativo 3a: Il codice dello sconto generale da eliminare non esiste nel sistema
	 */
	@Test
	public void EliminareScontoGenerale3a(){
		//questo scenario non dipende da 'conferma'
		conferma=true;
		//primo controllo: il metodo 'eliminaScontoGenerale' deve ritornare la ricerca fallita sul codice, quindi 1
		assertEquals(sistema.eliminaScontoGenerale("ea29d", conferma),1);
		//secondo controllo: devono ancora essere presenti due sconti generali nel sistema
		assertEquals(sistema.getScontiGenerali().size(),2);
	}
	
	/**
	 *  Scenario alternativo 5a-7a: Il manager annulla l'operazione - non conferma l'eliminazione del negozio
	 */
	@Test
	public void EliminareScontoGenerale5a7a(){
		conferma=false;
		//primo controllo: il metodo 'eliminaScontoGenerale' deve ritornare la non conferma o annullamento
		//                 dell'operazione, quindi 1
		assertEquals(sistema.eliminaScontoGenerale("erec1", conferma),2);
		//secondo controllo: devono ancora essere presenti due sconti generali nel sistema
		assertEquals(sistema.getScontiGenerali().size(),2);
	}
}

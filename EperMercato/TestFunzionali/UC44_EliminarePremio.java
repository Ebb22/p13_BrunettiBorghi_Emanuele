import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import EperMercato.Sistema;

/**
 * @author Brunetti Borghi Emanuele
 */
@SuppressWarnings("deprecation")
public class UC44_EliminarePremio {
Sistema sistema;
	
	//variabile booleana creata per simulare la risposta a video da parte dell'utente.
	//conferma se il Manager intende confermare l'eliminazione dello sconto generale selezionato.
	//NOTA: non si considera l'annullammento dell'operazione poiche' per come strutturata la funzione
	//		'eliminaPremio' non aggiungerebbe niente di utile (sarebbe identico al test con 
	// 		risposta negativa alla conferma dell'eliminazione), quindi posso considerare soltanto la variabile
	//		conferma per ricopreire i due scenari (pongo conferma=false anche nel caso di annullamento dell'operazione).
	boolean conferma;
	
	@Before
	public void setUp() throws Exception {
		//creo il Sistema, il Manager del sistema e gli associo
		sistema=new Sistema("emanuele", "brunetti borghi", "eb@gmail.com", "emabru", "emabru22");
		//Pre-condizione: manager del sistema autenticato
		sistema.login("emabru", "emabru22");
		//creo e inserisco premi nel sistema
		sistema.inserisciPremio("Set di 4 pentole", 1200, 6, new Date(119,6,1), new Date(119,11,1), false, true);
		sistema.inserisciPremio("Set di 6 bicchieri colorati", 400, 50, new Date(119,5,12), new Date(119,7,12), false, true);
		
	}
	
	/**
	 *  Scenario principale: Eliminazione del premio desiderato eseguita correttamente.
	 */
	@Test
	public void EliminarePremio(){
		conferma = true;
		//primo conrollo: l'eliminazione di un premio effettivamente esistente deve ritornare 0
		assertEquals(sistema.eliminaPremio(1, conferma),0);
		//secondo controllo: deve essere presente un solo premio nel sistema
		assertEquals(sistema.getPremi().size(),1);
		//terzo controllo: verifico che il premio rimasto sia corretto, cioe' eliminato quello voluto
		assertEquals(sistema.getPremi().get(0).getId(),0);
		assertEquals(sistema.getPremi().get(0).getDescrizione(),"Set di 4 pentole");
		assertEquals(sistema.getPremi().get(0).getPuntiRichiesti(),1200);
		assertEquals(sistema.getPremi().get(0).getPezziDisponibili(),6);
		assertEquals(sistema.getPremi().get(0).getDataInizio(),new Date(119,6,1));
		assertEquals(sistema.getPremi().get(0).getDataFine(), new Date(119,11,1));
	}
	
	/**
	 *  Scenario alternativo 3a: Il premio da eliminare non esiste nel sistema.
	 */
	@Test
	public void EliminareScontoGenerale3a(){
		//questo scenario non dipende da 'conferma'
		conferma=true;
		//primo controllo: il metodo 'eliminaPremio' deve ritornare la ricerca fallita sul codice, quindi 1
		assertEquals(sistema.eliminaPremio(5, conferma),1);
		//secondo controllo: devono ancora essere presenti due premi nel sistema
		assertEquals(sistema.getPremi().size(),2);
	}
	
	/**
	 *  Scenario alternativo 5a-7a: Il manager annulla l'operazione - non conferma l'eliminazione del premio.
	 */
	@Test
	public void EliminareScontoGenerale5a7a(){
		conferma=false;
		assertEquals(sistema.eliminaPremio(0, conferma),2);
		assertEquals(sistema.getPremi().size(),2);
	}
}

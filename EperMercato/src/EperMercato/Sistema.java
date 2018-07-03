package EperMercato;
import Utente.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

 
/**
 * @author Brunetti Borghi Emanuele
 */
public class Sistema {
	//Associo al Sistema il proprio Manager, infatti in accordo con i documenti
	//"srs" e "sdd" al Sistema deve essere associato un solo manager e viceversa
	private ManagerSistema managerSistema;
	
	//STRUTTURE
	//Lista degli oggetti previsti nel Sistema
	private ArrayList<Negozio> negozi;
	private ArrayList<ScontoGenerale> scontiGenerali;
	private ArrayList<Premio> premi;
	private ArrayList<Prodotto> prodotti;
	private ArrayList<Recensione> recensioniDaValutare;
	private ArrayList<Recensione> recensioniSistema;
	private ArrayList<Utente> utenti;
	
	//PARAMETRI
	//creo delle variabili AtomicInteger cosi' da poter assegnare correttamente gli id 
	//in maniera autoincrementale (ogni nuovo oggetto avra' un identificativo univoco subito
	//successivo rispetto all'oggetto precedente)
	private AtomicInteger indiceUtenti;
	private AtomicInteger indiceNegozi;
	private AtomicInteger indiceScontiGenerali;
	private AtomicInteger indicePremi;
	private AtomicInteger indiceRecensioni;
	private AtomicInteger indiceProdotti;
	
	
	//Costruttore del Sistema
	public Sistema(String nome, String cognome, String email, String username, String pass){
		//creo il manager del sistema (ipotizzato obbligatorio al momento della creazione del sistema)
		indiceUtenti = new AtomicInteger(0);
		managerSistema = new ManagerSistema(this.indiceUtenti.getAndIncrement(), nome, cognome, email, username, pass);
		//carico strutture
		negozi = new ArrayList<Negozio>();
		scontiGenerali = new ArrayList<ScontoGenerale>();
		premi=new ArrayList<Premio>();
		recensioniDaValutare = new ArrayList<Recensione>();
		recensioniSistema = new ArrayList<Recensione>();
		prodotti = new ArrayList<Prodotto>();
		utenti = new ArrayList<Utente>();
		utenti.add(managerSistema);
		//carico parametri
		indiceNegozi = new AtomicInteger(0);
		indiceScontiGenerali = new AtomicInteger(0);
		indicePremi = new AtomicInteger(0);
		indiceRecensioni = new AtomicInteger(0);
		indiceProdotti = new AtomicInteger(0);	
	}
	
	//METODI GET
	public ManagerSistema getManagerSistema() {
		return managerSistema;
	}
	public ArrayList<Negozio> getNegozi() {
		return negozi;
	}
	public ArrayList<ScontoGenerale> getScontiGenerali() {
		return scontiGenerali;
	}
	public ArrayList<Premio> getPremi() {
		return premi;
	}
	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}
	public ArrayList<Recensione> getRecensioniDaValutare() {
		return recensioniDaValutare;
	}
	public ArrayList<Recensione> getRecensioniSistema() {
		return recensioniSistema;
	}
	public ArrayList<Utente> getUtenti() {
		return utenti;
	}

	//METODI
	/**
	 * Funzione per l'autenticazione da parte del Manager del sistema.
	 * @param  username	String
	 * @param  password	String
	 * @return int: 0,se autenticato correttamente;
	 * 		        1,se username non trovato;
	 * 		        2,se username trovato ma password non concorde.
	 */
	public int login(String username, String pass){
		if (managerSistema.getUsername().equals(username)){
			if (managerSistema.getPassword().equals(pass)){
				managerSistema.setAutenticato(true);
				return 0; //autenticato correttamente
			}
				 
			else return 2; //password non concorde con username
		}else
			return 1; //username non trovato
	}
	
	/**
	 * Funzione per la deautenticazione da parte del Manager del sistema.
	 * @param  conferma	boolean:	rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return boolean: true,se eseguita correttamente;
	 *  			    false,se non eseguita.
	 */
	public boolean logout(boolean conferma){
		if(!managerSistema.isAutenticato())
			return false;
		if (conferma){
			managerSistema.setAutenticato(false);
			return true; //Logout effettuato con successo
		}
		else 
			return false; //Operazione annullata
	}

	/**
	 * Funzione per l'inserimento di un nuovo negozio nel sistema. 
	 * Nota: da ipotesi ad un negozio deve essere collegato un Manager del negozio.
	 * @param	nomeN			String:	 	nome del negozio da inserire (deve esssere univoco)
	 * @param	indirizzoN		String:	 	indirizzo del negozio da inserire
	 * @param	numTelN			String:	 	numero di telefono del negozio da inserire
	 * @param	emailN			String:		indirizzo email del negozio da inserire
	 * @param	nomeM			String:	 	nome del Manager del negozio
	 * @param	cognomeM		String:	 	cognome del Manager del negozio
	 * @param	emailM			String:   	email del Manager del negozio
	 * @param	usernameM		String:		username del Manager del negozio con cui si autentichera' al sistema (deve essere univoco)
	 * @param	passwordM		String:	 	password del Manager del negozio collegata all'username con cui si autentichera'
	 * @param  	annulla			boolean:	variabile booleana che rappresenta l'annullamento dell'operazione da parte dell'utente
	 * @param 	conferma		boolean:	variabile booleana che rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return int: -1,se manager del sistema non autenticato;
	 * 			     0,se inserimento eseguito con successo;
	 *               1,se nome gia' esistente tra la lista dei negozi presenti nel sistema;
	 *               2,se il manager del negozio inserito e' gia' associato ad un altro negozio;
	 *			     3,se non confermata l'operazione;
	 * 	             4,se annullata subito l'operazione.
	 */
	public int inserisciNegozio(String nomeN, String indirizzoN, String numTelN, String emailN,
								String nomeM, String cognomeM, String emailM, String usernameM, String passM, 
								boolean annulla, boolean conferma){
		if(!managerSistema.isAutenticato())
			return -1; //Utente non autenticato
		Iterator<Negozio> iteratorNegozi = negozi.iterator();
		if(!annulla){
			while (iteratorNegozi.hasNext()){
				Negozio n = iteratorNegozi.next();
				if(n.getNome().equals(nomeN))
					return 1; //nome negozio gia' presente
			}
			for(Utente u: utenti){
				if(u.getUsername().equals(usernameM))
					return 2; //username inserito gia' esistente nel sistema
			}
			if(!conferma)
				return 3; //operazione non confermata
			ManagerNegozio managerNegozio=new ManagerNegozio(indiceUtenti.getAndIncrement(), nomeM, cognomeM, emailM, usernameM, passM);
			utenti.add(managerNegozio);
			negozi.add(new Negozio(this.indiceNegozi.getAndIncrement(), nomeN, indirizzoN, numTelN, emailN, managerNegozio));
				return 0; //inserimento eseguito con successo
		}else
			return 4; //operazione annullata
	}

	/**
	 * Funzione per la rierca di un negozio nella lista dei negozi presenti nel sistema, si 
	 * considera soltanto il nome poiche' considerato univoco.
	 * @param  nome	  String:	nome del negozio da cercare (deve essere univoco)
	 * @return	ArrayList<Negozio>:  null,se manager del sistema non autenticato;
	 * 								 trovato,se negozio ricercato trovato identico; 
	 * 		   						 lista,se nome del negozio non trovato identico allora si suggerisce una lista di
	 * 									   simili (che puo' essere vuota, in tal caso di mostra un messaggio).
	 * 		  
	 */
	public ArrayList<Negozio> ricercaNegozio(String nome){
		if(!managerSistema.isAutenticato())
			return null;
		Iterator<Negozio> iteratorNegozi = negozi.iterator();
		ArrayList<Negozio> lista = new ArrayList<Negozio>();
		ArrayList<Negozio> trovato = new ArrayList<Negozio>();
		while (iteratorNegozi.hasNext()){
			Negozio n = iteratorNegozi.next();
			if(n.getNome().equals(nome)){
				trovato.add(n);
				return trovato; //negozio trovato identico a quello ricercato
			}
			if(n.getNome().contains(nome)){
				lista.add(n);
			}
		}
		if(lista.size()==0)
			System.out.println("Negozio non trovato, riprovare!");
		return lista; //negozi suggeriti come simili
	}
	
	/**
	 * Funzione per la modifica di un negozio esistente nella lista dei negozi presenti nel sistema, si considera
	 * soltanto il nome per la selezione di un negozio nella lista dei negozi poiche' considerato univoco.
	 * @param  scelta			boolean:	variabile boolean che rappresenta la scelta da parte del manager del sistema
	 * 									 	sull'oggetto da modificare (true se si vogliono modificare le caratteristiche 
	 * 									 	del negozio, false se si vogliono modificare i dati del manager del negozio)		
	 * @param  nomeNegozio 		String:	 	nome del negozio di cui si vogliono modificare i dati
	 * @param  newNomeN			String:	 	nuovo nome che si vuole assegnare al negozio
	 * @param  newIndirizzoN	String:	 	nuovo indirizzo che si vuole assegnare al negozio
	 * @param  newNumTelN		String:	 	nuovo numero di telefono che si vuole assegnare al negozio
	 * @param  newEmailN		String:	 	nuova email che si vuole assegnare al negozio
	 * @param  newNomeM			String:		nuovo nome che si vuole assegnare al manager del negozio
	 * @param  newCognomeM		String:		nuovo cognnome che si vuole assegnare al manager del negozio
	 * @param  newEmailM		String:	 	nuova emial che si vuole assegnare al manager del negozio
	 * @param  newUsernameM		String:	 	nuovo username che si vuole assegnare al manager del negozio
	 * @param  newPasswordM		String:	 	nuova password che si vuole assegnare al manager del negozio
	 * @param  annulla			boolean:	 variabile booleana che rappresenta l'annullamento dell'operazione da parte dell'utente
	 * @param  conferma			boolean:	 variabile booleana che rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return int: -1,se manager del sistema non autenticato;
	 * 			     0,se dati inseriti validi e modifiche apportate correttamente alle caratteristiche del negozio;
	 * 		         1,se nuovo nome inserito non valido poiche' gia' esistente del sistema;
	 * 		         2,se negozio da modificare non esistente nel sistema;
	 * 			     3,se il Manager annulla l'operazione prima di richiedere la modifica;
	 * 			     4,se il Manager non conferma l'operazione di modifica dopo l'approvazione dei dati;
	 * 			     5,se nuovo username inserito gia' esistente nel sistema;
	 * 			     6,se dati inseriti validi e modifiche apportate correttamente ai dati personali del manager del negozio. 
	 */ 
	public int modificaNegozio(boolean scelta, String nomeNegozio, String newNomeN, String newIndirizzoN, String newNumTelN,
							   String newEmailN, String newNomeM, String newCognomeM, String newEmailM, String newUsernameM,
							   String newPassM, boolean annulla, boolean conferma){
		if(!managerSistema.isAutenticato())
			return -1;
		Iterator<Negozio> iteratorNegozi = negozi.iterator();
		while (iteratorNegozi.hasNext()){
			Negozio n = iteratorNegozi.next();
			if(n.getNome().equals(nomeNegozio)){
				if(annulla)
					return 3; //utente esce dalla pagina del negozio, quindi annulla l'operazione
				if(scelta){
					if (newNomeN!=null){
						for(Negozio negozio: negozi){
							if(newNomeN==negozio.getNome())
								return 1; //nuovo nome inserito non ammesso perche' gia' esistente
						}
						if(!conferma)
							return 4; //utente non conferma le modfiche inserite
						n.setNome(newNomeN);
					}
					if(!conferma)
						return 4; //utente non conferma le modfiche inserite
					if(newIndirizzoN!=null)
						n.setIndirizzo(newIndirizzoN);
					if(newNumTelN!=null)
						n.setNumeroTelefono(newNumTelN);
					if(newEmailN!=null)
						n.setEmail(newEmailN);
					return 0; //modifiche caratteristiche negozio inserite correttamente
				}else{
					if(newUsernameM!=null){
						for(Utente utente: utenti){
						if(utente.getUsername().equals(newUsernameM))
							return 5; //Username gia' eistente
						}
					}
					if(!conferma)
						return 4; //utente non conferma le modfiche inserite
					if(newNomeM!=null)
						n.getManagerNegozio().setNome(newNomeM);
					if(newCognomeM!=null)
						n.getManagerNegozio().setCognome(newCognomeM);
					if(newEmailM!=null)
						n.getManagerNegozio().setEmail(newEmailM);
					if(newUsernameM!=null)
						n.getManagerNegozio().setUsername(newUsernameM);
					if(newPassM!=null)
						n.getManagerNegozio().setPassword(newPassM);
					return 6; //modifiche Manager del negozio inserite correttamente	
				}				
			}
		}
		return 2; //negozio cercato non esistente
	}
	
	/**
	 * Funzione per la rimozione di un negozio esistente, si considera soltanto il nome per la selezione di 
	 * un negozio nella lista dei negozi poiche' considerato univoco.
	 * @param  nome			String:	  nome del negozio da eliminare dal sistema
	 * @param  conferma		boolean:  variabile booleana che rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return int: -1,se manager del sistema non autenticato;
	 * 				 0,se negozio rimosso correattamente;
	 * 		         1,se negozio ricercato non e' presente nel sistema;
	 * 		         2,se il Manager non conferma l'operazione di cancellazione dopo aver trovato il negozio ricercato.
	 */
	public int eliminaNegozio(String nome, boolean conferma){
		if(!managerSistema.isAutenticato())
			return -1;
		Iterator<Negozio> iteratorNegozi = negozi.iterator();
		while (iteratorNegozi.hasNext()){
			Negozio n = iteratorNegozi.next();
			if(n.getNome().equals(nome)){
				if (!conferma)
					return 2; //utente esce dalla pagina delle caratteristiche del negozio, quindi annulla l'operazione
				negozi.remove(n);
				utenti.remove(n.getManagerNegozio());
				return 0; //rimozione del negozio desiderato avvenuta con successo
			}
		}
		return 1; //negozio cercato non esistente
	}

	/**
	 * Funzione per l'inserimento di un nuovo sconto generale. 
	 * NOTA: In accordo con il documento 'srs' la spesa massima e' fissata non oltre i 1000€, quindi uno sconto si basa su una
	 * 		 soglia massima di 1000€.
	 * @param   codice			String:			codice alfanumerico da inserire al momento del checkout del carrello per usufruire dello sconto
	 * @param	descr			String:			descrizione del premio che si vuole inserire
	 * @param	percentuale		float:		 	percentuale applicato sul totale del carrello prima dell'acquisto
	 * @param	min				float:			soglia minima da superare nel carrello per poter usufruire dello sconto
	 * @param	max				float:			soglia massima del carrello su cui si applica la percentuale indicata
	 * @param   inizio			Date:			data di inizio validita' dello sconto
	 * @param   fine			Date:			data di fine validita' dello sconto
	 * @param  	annulla			boolean: 		variabile booleana che rappresenta l'annullamento dell'operazione da parte dell'utente
	 * @param   conferma 		boolean:		variabile booleana che rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return  int:  -1,se manager del sistema non autenticato;
	 * 				   0,se inserimento eseguito con successo;
	 *                 1,se date inserite non coerenti (inizio prima della fine);
	 *                 2,se soglie inserite non coerenti (soglia minima non minore della soglia massima o soglie non valide);
	 *			       3,se percentuale inserita non ammissibile (non compresa tra 0 e 100);
	 * 	               4,se il codice sconto associato allo sconto e' gia' presente nel sistema;
	 * 				   5,se le soglie inserite sono in conflitto con altre gia' presenti nel sistema;
	 * 				   6,se il manager annulla l'operaione uscendo dalla pagina;
	 * 				   7,se il manager risponde negativamente alla richiesta di conferma dell'operazione.
	 */
	public int inserisciScontoGenerale(String codice, String descr, float percentuale, float min,
			                            float max, Date inizio, Date fine, boolean annulla, boolean conferma){
		if(!managerSistema.isAutenticato())
			return -1;
		if(annulla)
			return 6; //il Manager esce dalla pagina dedicata alla creazione di un nuovo sconto generale 
		
		//analizzo la valididta' dei dati inseriti
		if (inizio.compareTo(fine)>0)
			return 1; //date inserite non valide (inizio posteriore a fine)
		if(inizio.before(new java.sql.Date(new java.util.Date().getTime())))
			return 1; //date inserite non valide (inizio precedente ad oggi)
		if(min>max)
			return 2; //errore nelle soglie inserite (soglia massima minore di quella minima)
		if(min<0.0f)
			return 2; //errore nelle soglie inserite (soglia minima sotto zero euro non possibile)
		if(max>1000.0f)
			return 2; //errore nelle soglie inserite (soglia massima sopra i mille euro non possibile)
		if((percentuale<0.01f)||(percentuale>100.0))
			return 3; //percentuale inserita non valida
		
		//controllo che i dati inseriti siano compatibili con quelli gia' presenti nel sistema
		Iterator<ScontoGenerale> iteratorScontiGenerali = scontiGenerali.iterator();
		while (iteratorScontiGenerali.hasNext()){
			ScontoGenerale sconto = iteratorScontiGenerali.next();
			if(sconto.getCodiceSconto().equals(codice))
				return 4; //codice di sconto alfanumerico gia' esistente
			//controllo le se soglie inserite siano compatibili con quelle gia' presenti nel sistema per altri sconti generali
			if(sconto.getSogliaMin()<min){
				if(!(sconto.getSogliaMax()<min))
					return 5; //soglia incoerente con un altro sconto inserito nel sistema
			}
			if(sconto.getSogliaMin()>min){
				if(!(sconto.getSogliaMin()>max))
					return 5; //soglia incoerente con un altro sconto inserito nel sistema
			}			
			if(Math.abs(sconto.getSogliaMin()-min)==0|Math.abs(sconto.getSogliaMin()-max)==0)
				return 5; //soglia incoerente con un altro sconto inserito nel sistema
		}		
		//dati approvati, posso inserire lo sconto
		if(!conferma)
			return 7; //utente non conferma l'inserimento dello sconto
		scontiGenerali.add(new ScontoGenerale(this.indiceScontiGenerali.getAndIncrement(), codice, descr,
				percentuale, min, max, inizio, fine));
		return 0; //sconto generale inserito correttamnete	
	}
	
	/**
	 * Funzione per la rimozione di uno sconto generale dal sistema. Si considera soltanto il codice sconto 
	 * per la selezione dello sconto tra la lista degli sconti, poiche' considerato univoco.
	 * @param  codice		String:	  codice alfanumerico univoco per ogni sconto generale
	 * @param  conferma		boolean:  variabile booleana che rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return int: -1,se manager del sistema non autenticato;
	 * 			     0,se negozio rimosso correattamente;
	 * 		         1,se negozio ricercato non e' presente nel sistema;
	 * 		         2,se il Manager non conferma l'operazione di cancellazione dopo aver trovato il negozio ricercato.
	 */
	public int eliminaScontoGenerale(String codice, boolean conferma){//VEDERE SE METTERE ANNULLA
		if(!managerSistema.isAutenticato())
			return -1;
		Iterator<ScontoGenerale> iteratorScontiGenerali = scontiGenerali.iterator();
		while (iteratorScontiGenerali.hasNext()){
			ScontoGenerale sconto = iteratorScontiGenerali.next();
			if(sconto.getCodiceSconto().equals(codice)){
				if (!conferma)
					return 2; //utente esce dalla pagina delle caratteristiche del negozio, quindi annulla l'operazione
				scontiGenerali.remove(sconto);
				return 0; //rimozione del negozio desiderato avvenuta con successo
			}
		}
		return 1; //negozio cercato non esistente
	}
	
	/**
	 * Funzione per l'inserimento di un nuovo premio nel sistema. 
	 * @param  descr		String:	  descrizione del premio che si vuole inserire
	 * @param  punti		int:	  numero di punti richiesti per poter 'acquistare' il premio
	 * @param  disponibili	int: 	  numero di pezzi disponibili per quel premio
	 * @param  inizio		Date:	  data di inizio validita' del premio
	 * @param  fine			Date:	  data di fine validita' del premio
	 * @param  conferma 	boolean:  variabile booleana che rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return int: -1,se manager del sistema non autenticato;
	 * 				 0,se inserimento eseguito con successo;
	 *            	 1,se numero pezzi non ammissibile, cioe' minore di 1;
	 *             	 2,se date inserite  non coerenti (inizio prima della fine);
	 *			  	 3,se descrizione del premio gia' esistente nel sistema;
	 * 	             4,se annullata subito l'operazione.
	 */
	public int inserisciPremio(String descr, int punti, int disponibili, Date inizio, Date fine,
							   boolean annulla, boolean conferma){
		if(!managerSistema.isAutenticato())
			return -1;
		if(annulla)
			return 4;//il Manager esce dalla pagina dedicata alla creazione di un nuovo sconto generale 
		
		//analizzo la valididta' dei dati inseriti
		if(disponibili<1)
			return 1; //numero pezzi disponibili non ammissibile
		if (inizio.compareTo(fine)>0)
			return 2; //date inserite non valide
		if(inizio.before(new java.sql.Date(new java.util.Date().getTime())))
			return 2; //date inserite non valide
		Iterator<Premio> iteratorPremi = premi.iterator();
		while (iteratorPremi.hasNext()){
			Premio p = iteratorPremi.next();
			if(p.getDescrizione().equals(descr))
				return 3; //premio gia' presente nel sistema
		}
		if(!conferma)
			return 5; //il manager non conferma l'inserimento dopo il successo dell'analisi sulla vilidta' dei dati
		premi.add(new Premio(this.indicePremi.getAndIncrement(), descr, punti, disponibili, inizio, fine));
		return 0; //premio aggiunto con successo
	}
	
	/**
	 * Funzione per la rimozione di un premio dal sistema, si considera soltanto l'identificativo 
	 * per la selezione di un premio tra la lista dei premi.
	 * @param  id			String:	 	codice univoco per ogni premio
	 * @param  conferma		boolean:	variabile booleana che rappresenta la risposta alla richiesta di conferma dell'operazione
	 * @return int: -1,se manager del sistema non autenticato;
	 * 				 0,se premio rimosso correattamente;
	 * 		         1,se premio ricercato non e' presente nel sistema;
	 * 		         2,se il Manager non conferma l'operazione di cancellazione dopo aver trovato il premio desisderato.
	 */
	public int eliminaPremio(int id, boolean conferma){
		if(!managerSistema.isAutenticato())
			return -1;
		Iterator<Premio> iteratorPremi = premi.iterator();
		while (iteratorPremi.hasNext()){
			Premio p = iteratorPremi.next();
			if(p.getId()==id){
				if (!conferma)
					return 2; //utente esce dalla lista dei premi, quindi annulla l'operazione
				premi.remove(p);
				return 0; //rimozione del premio desiderato eseguita con successo
			}
		}
		return 1;  //premio cercato non esistente
	}
	
	/**
	 * Funzione per la creazione di una recensione, non implementata nel dettaglio poiche' non prevista 
	 * nella nostra visione del sistema lato Manager sistema.
	 * @param  titolo			String:	 titolo della recensione
	 * @param  descr			String:  descrizione della recensione
	 * @param  data				Date:	 data in cui e' stata scritta la recensione
	 * @param  oggetto			String:	 oggetto o categoria della recensione (puo' essere: "prodotto", "negozio" o "sistema")
	 * @param  nomeOggetto		String:	 nome specifico dell'oggetto per la categoria indicata (per "sistema" non necessario)
	 * @param  autore			String:	 nome dell'autore della recensione
	 * @param  voto				int:	 voto della recensione
	 */
	public void creaRecensione(String titolo, String descr, Date data, String oggetto, String nomeOggetto, 
								  String autore, int voto){
		recensioniDaValutare.add(new Recensione(this.indiceRecensioni.getAndIncrement(),titolo, descr, voto,  
												data, oggetto, nomeOggetto, autore));
	}
	
	/**
	 * Funzione per l'inserimento di un prodotto nel sistema, non implementata nel dettaglio poiche' non prevista 
	 * nella nostra visione del sistema lato Manager sistema.
	 * @param  cat				String:	 categoria del prodotto (es. scatolame, bevande, ...)
	 * @param  nome				String:	 nome del prodotto
	 * @param  prezzo			float:	 prezzo del prodotto
	 * @param  negozio			Negozio: negozio offerente il prodotto (collegato al prezzo)
	 */
	public void inserisciProdotto(String cat, String nome, float prezzo, String negozio){
		Iterator<Negozio> iteratorNegozi = negozi.iterator();
		while (iteratorNegozi.hasNext()){
			Negozio n = iteratorNegozi.next();
			if(n.getNome().equals(negozio)){
				Prodotto p= new Prodotto(this.indiceProdotti.getAndIncrement(), cat, nome, prezzo, n);
				n.addProdotto(p);
				prodotti.add(p);
				break;
			}
		}	
	}
	/**
	 * Funzione per la valutazione di una recensione in attesa di approvazione. Si considera soltanto l'identificativo 
	 * per la selezione di una recensione tra la lista delle recensioni in attesa di approvazione.
	 * Ogni recensione, se approvata, sara' pubblicata e collegata all'oggetto indicato (es. se la recensione riguarda
	 * il "tonno Nostromo 3x100g", allora questa sara' inserita tra le recensioni di quel prodotto), invece se non 
	 * approvata sara' eliminata dal sistema.
	 * @param  approva					boolean:	variabile booleana che rappresenta l'approvazione del Manager dopo la 
	 * 												lettura (pubblica(true) o elimina(false))
	 * @param  confermaOperazione		boolean:    variabile booleana che rappresenta la risposta alla richiesta di conferma
	 * 									            all'operaizone selezioata (pubblicazione o rimozione)
	 * @return int:  -1,se manager del sistema non autenticato;
	 * 				 100+numeroRecensioniCollegate,se recensione approvata e pubblicata (inoltre mi permette di verificare
	 * 											   se recensione collegata allo specifico oggetto indicato);
	 * 		       	 1,se recensione esaminata ma non approvata, quindi eliminata;
	 * 		         2,se il Manager non conferma l'operazione di pubblicazione;
	 * 			     3,se il Manager non conferma l'operazione di rimozione;
	 * 			     4,se non ci sono recensioni in attesa di approvazione;
	 *               5,se oggetto recensito non esistente nel sistema, quindi se vi e' un errore.
	 */
	public int approvareRecensione(int id, boolean approva, boolean confermaOperazione){
		if(!managerSistema.isAutenticato())
			return -1;
		if (recensioniDaValutare.size()==0)
			return 4; //Non ci sono recensioni in attesa di approvazione
		Iterator<Recensione> iteratorRecensioni = recensioniDaValutare.iterator();
		while (iteratorRecensioni.hasNext()){
			Recensione r = iteratorRecensioni.next();
			if(r.getId()==id){
				r.setVisionata(true);
				if(approva){	
					if(confermaOperazione){
						if (r.getOggetto().equals("prodotto")){
							for(Prodotto p: prodotti){
								if(p.getNome().equals(r.getNomeOggetto())){
									p.addRecensione(r);
									recensioniDaValutare.remove(r);
									return 100+p.getRecensioni().size();//recensione selezionata approvata, quindi pubblicata e collegata al prodotto indicato
								}
							}
						}if(r.getOggetto().equals("negozio")){
							for(Negozio n: negozi){
								if(n.getNome().equals(r.getNomeOggetto())){
									n.addRecensione(r);
									recensioniDaValutare.remove(r);
									return 100+n.getRecensioni().size();//recensione selezionata approvata, quindi pubblicata e collegata al negozio indicato
								}
							}
						}if(r.getOggetto().equals("sistema")){
							recensioniSistema.add(r);
							recensioniDaValutare.remove(r);
							return 100+this.recensioniSistema.size();//recensione selezionata approvata, quindi pubblicata tra le recensioni del Sistema generale
						}						
					}else
						return 2; //pubblicazione non confermata (dopo averla analizzata)
				}					
				else{
					if(confermaOperazione){
						recensioniDaValutare.remove(r);
						return 1; //recensione selezionata non approvata, quindi eliminata dal sistema
					}else
						return 3; //rimozione non confermata (dopo averla analizzata)
				}	
			}
		}
		return 5; //l'oggetto indicato non e' presente nel sistema
	}
	
}

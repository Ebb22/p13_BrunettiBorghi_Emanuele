package EperMercato;
import java.util.ArrayList;

import Utente.*;

/**
 * @author Brunetti Borghi Emanuele
 */
public class Negozio {
	//Associo al Negozio il proprio Manager, infatti in accordo con i documenti
	//"srs" e "sdd" al Negozio deve essere associato un solo manager e viceversa
	private ManagerNegozio managerNegozio;
	//ATTRIBUTI
	private int id;
	private String nome;
	private String indirizzo;
	private String numeroTelefono;
	private String email;
	//Lista delle recensioni ricevute dal negozio
	private ArrayList<Recensione> recensioni;
	//Lista dei prodotti inseriti dal negozio
	private ArrayList<Prodotto> prodotti;
	
	//COSTRUTTORI
	public Negozio(int id, String nome, String indirizzo, String numTel, String email, ManagerNegozio managerNegozio) {
		this.id=id;
		this.nome=nome;
		this.indirizzo=indirizzo;
		this.numeroTelefono=numTel;
		this.email=email;
		this.managerNegozio=managerNegozio;
		recensioni=new ArrayList<Recensione>();
		prodotti= new ArrayList<Prodotto>();
	}

	//METODI GET
	public int getId(){
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	public String getEmail() {
		return email;
	}
	public ManagerNegozio getManagerNegozio() {
		return managerNegozio;
	}
	public ArrayList<Recensione> getRecensioni() {
		return recensioni;
	}
	public ArrayList<Prodotto> getProdotti() {
		return prodotti;
	}

	//METODI SET
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setManagerNegozio(ManagerNegozio managerNegozio) {
		this.managerNegozio = managerNegozio;
	}
	public void setRecensioni(ArrayList<Recensione> newRecensioni) {
		this.recensioni = newRecensioni;
	}
	public void setProdotti(ArrayList<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}
	
	//FUNZIONI
	/**
	 * Metodo per aggiungere una recensione alla lista delle recensioni.
	 * @param r		Recensione	nuova recensione da inserire nella lista
	 */
	public void addRecensione(Recensione r){
		recensioni.add(r);
	}
	/**
	 * Metodo per aggiungere un nuovo prodotto nella lista dei prodotti inseriti nel sistema.
	 * @param p		Prodotto	nuovo prodotto da inserire nella lista dei prodotti proposti
	 */
	public void addProdotto(Prodotto p){
		prodotti.add(p);
	}
	
}

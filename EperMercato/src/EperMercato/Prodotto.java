package EperMercato;

import java.util.ArrayList;

import dataType.PrezzoNegozio;

/**
 * @author Brunetti Borghi Emanuele
 */
public class Prodotto {
	//ATTRIBUTI
	private int id;
	private String categoria;
	private String nome;
	//Lista dei negozi offerenti questo prodotto con il proprio prezzo proposto
	private ArrayList<PrezzoNegozio> prezzoInNegozio;
	//Lista delle recensioni riguardanti il prodtto
	private ArrayList<Recensione> recensioni;
	
	//COSTRUTTORI
	public Prodotto(int id, String cat, String nome, float prezzo, Negozio negozio){
		this.id=id;
		this.categoria=cat;
		this.nome=nome;
		prezzoInNegozio = new ArrayList<PrezzoNegozio>();
		this.addPrezzoNeogzio(prezzo, negozio);
		recensioni=new ArrayList<Recensione>();
	}
	
	//METODI GET
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getCategoria(){
		return categoria;
	}
	/**
	 * Metodo get per avere il prezzo del prodotto associato al negozio passato (se esistente, altrimenti null).
	 * @param	n	Negozio		negozio di cui voglio sapere il prezzo del prodotto
	 * @return  PrezzoNegozio	prezzoInNegozio,se trovo il negozio passato tra quelli offerenti del prodotto
	 * 							null,se il negozio passato non offre il prodotto
	 */
	public PrezzoNegozio getPrezzoInNegozio(Negozio n){
		for(PrezzoNegozio pn: prezzoInNegozio){
			if(pn.getNegozio().equals(n))
				return pn;
		}
		return null;
	}
	public ArrayList<PrezzoNegozio> getPrezzoInNegozio(){
		return prezzoInNegozio;
	}
	public ArrayList<Recensione> getRecensioni() {
		return recensioni;
	}
	
	
	//METODI SET
	public void setCategoria(String categoria){
		this.categoria=categoria;
	}
	public void setNome(String nome){
		this.nome=nome;
	}
	public void setPrezzoInNegozio(ArrayList<PrezzoNegozio> prezzoInNegozio) {
		this.prezzoInNegozio=prezzoInNegozio;
	}
	
	public void setRecensioni(ArrayList<Recensione> recensioni) {
		this.recensioni=recensioni;
	}
	
	//METODI
	/**
	 * Metodo per aggiungere una recensione alla lista delle recensioni personali del prodotto.
	 * @param	r	Recensione	recensione da aggiungere
	 */
	public void addRecensione(Recensione r){
		recensioni.add(r);
	}
	/**
	 * Metodo per aggiungere un nuovo prezzo collegato ad un determinato negozio.
	 * @param	prezzo		float		prezzo del prodotto offerto dal negozio
	 * @param	negozio		String		nome del negozio offerente per il prodotto
	 */
	public void addPrezzoNeogzio(float prezzo, Negozio negozio){
		PrezzoNegozio pn = new PrezzoNegozio();
		pn.setNegozio(negozio);
		pn.setPrezzo(prezzo);
		prezzoInNegozio.add(pn);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Prodotto)) 
			return false;
		Prodotto p = (Prodotto) obj;
		return(this.nome.equals(p.getNome()) && this.categoria.equals(p.getCategoria()));
	}

}

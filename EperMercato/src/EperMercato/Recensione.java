package EperMercato;

import java.sql.Date;

/**
 * @author Brunetti Borghi Emanuele
 */
public class Recensione {
	//ATTRIBUTI
	private int id;
	private String titolo;
	private String descrizione;
	private Date data;
	private String oggetto;
	private String nomeOggetto;
	private String autore;
	private int voto;
	private boolean visionata = false;
	
	//COSTRUTTORI
	public Recensione(int id, String titolo, String descr, int voto, Date data, String oggetto, String nomeOgg,
					  String autore){
		this.setId(id);
		this.setTitolo(titolo);
		this.setDescrizione(descr);
		this.setVoto(voto);
		this.setData(data);
		this.setOggetto(oggetto);
		this.setNomeOggetto(nomeOgg);
		this.setAutore(autore);
	}
	
	
	//METODI GET
	public int getId() {
		return id;
	}
	public String getTitolo() {
		return titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public Date getData() {
		return data;
	}
	public String getOggetto() {
		return oggetto;
	}
	public String getNomeOggetto() {
		return nomeOggetto;
	}
	public String getAutore() {
		return autore;
	}
	public int getVoto() {
		return voto;
	}
	public boolean isVisionata() {
		return visionata;
	}
	
	
	//METODI SET
	public void setId(int id) {
		this.id = id;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public void setNomeOggetto(String nomeOggetto) {
		this.nomeOggetto = nomeOggetto;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public void setVoto(int voto) {
		this.voto = voto;
	}
	public void setVisionata(boolean visionata) {
		this.visionata = visionata;
	}

}

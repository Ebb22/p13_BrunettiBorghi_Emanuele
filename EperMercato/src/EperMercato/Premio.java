package EperMercato;

import java.sql.Date;

/**
 * @author Brunetti Borghi Emanuele
 */
public class Premio {
	//ATTRIBUTI
	private int id;
	private String descrizione;
	private int puntiRichiesti;
	private int pezziDisponibili;	
	private Date dataInizio;
	private Date dataFine;
	
	//COSTRUTTORI
	public Premio(int id, String descr, int punti, int disponibili, Date inizio, Date fine){
		this.id=id;
		this.descrizione=descr;
		this.puntiRichiesti=punti;
		this.pezziDisponibili=disponibili;
		this.dataInizio=inizio;
		this.dataFine=fine;
	}
	
	//METODI GET
	public int getId() {
		return id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public int getPuntiRichiesti() {
		return puntiRichiesti;
	}
	public int getPezziDisponibili() {
		return pezziDisponibili;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	
	//METODI SET
	public void setDescrizone(String descrizione){
		this.descrizione = descrizione;
	}
	public void setPuntiRichiesti(int puntiRichiesti) {
		this.puntiRichiesti = puntiRichiesti;
	}
	public void setPezziDisponibili(int pezziDisponibili) {
		this.pezziDisponibili = pezziDisponibili;
	}
	public void setDataInizio(Date dataInizio){
		this.dataInizio = dataInizio;
	}
	public void setDataFine(Date dataFine){
		this.dataFine = dataFine;
	}
}	


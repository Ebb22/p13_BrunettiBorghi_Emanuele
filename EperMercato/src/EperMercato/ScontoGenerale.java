package EperMercato;

import java.sql.Date;

/**
 * @author Brunetti Borghi Emanuele
 */
// NOTA: con sconto generale si intende uno sconto proposto dal sistema sul totale
//       della spesa senza distinzioni su prodotti o negozi
public class ScontoGenerale {
	//ATTRIBUTI
	private int id;
	private String codiceSconto;
	private String descrizione;
	private float percentuale;
	private float sogliaMin;
	private float sogliaMax;
	private Date dataInizio;
	private Date dataFine;
	
	//COSTRUTTORI
	public ScontoGenerale(int id, String codice, String descr, float perc, float min, float max, Date inizio, Date fine){
		this.id=id;
		this.setCodiceSconto(codice);
		this.setDescrizione(descr);
		this.setPercentuale(perc);
		this.setSogliaMin(min);
		this.setSogliaMax(max);
		this.setDataInizio(inizio);
		this.setDataFine(fine);
	}

	//METODI GET
	public int getId() {
		return id;
	}
	public String getCodiceSconto() {
		return codiceSconto;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public float getPercentuale() {
		return percentuale;
	}
	public float getSogliaMin() {
		return sogliaMin;
	}
	public float getSogliaMax() {
		return sogliaMax;
	}
	public Date getDataInizio() {
		return dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}

	//METODI SET
	public void setCodiceSconto(String codiceSconto) {
		this.codiceSconto = codiceSconto;
	}
	public void setDescrizione(String descrizione){
		this.descrizione = descrizione;
	}
	public void setPercentuale(float percentuale){
		this.percentuale = percentuale;
	}
	public void setSogliaMin(float sogliaMin) {
		this.sogliaMin = sogliaMin;
	}
	public void setSogliaMax(float sogliaMax) {
		this.sogliaMax = sogliaMax;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
		
}
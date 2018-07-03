package dataType;

import EperMercato.Negozio;

/**
 * @author Brunetti Borghi Emanuele
 */
public class PrezzoNegozio {
	//ATTRIBUTI
	private float prezzo;
	private Negozio negozio;
	
	//METODI GET
	public float getPrezzo() {
		return prezzo;
	}
	public Negozio getNegozio() {
		return negozio;
	}
	
	//METODI SET
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public void setNegozio(Negozio negozio) {
		this.negozio = negozio;
	}
}

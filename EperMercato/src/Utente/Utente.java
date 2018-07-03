package Utente;

/**
 * @author Brunetti Borghi Emanuele
 */
public abstract class Utente {
	//ATTRIBUTI
	private int id;
	private String nome;
	private String cognome;
	private String email;
	private String username;
	private String password;
	private boolean autenticato;
	
	//METODI GET
	public int getId(){
		return id;
	}
	public boolean isAutenticato() {
		return autenticato;
	}
	public String getNome(){
		return nome;
	}
	public String getCognome(){
		return cognome;
	}
	public String getEmail(){
		return email;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	
	//METODI SET
	public void setAutenticato(boolean autenticato) {
		this.autenticato = autenticato;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//METODI
	public void inserisciUtente(int id, String nome, String cognome, String email, String username, String pass){
		this.id=id;
		this.setNome(nome);
		this.setCognome(cognome);
		this.setEmail(email);
		this.setUsername(username);
		this.setPassword(pass);
		this.setAutenticato(false);
	}
	
}

package Utente;

/**
 * @author Brunetti Borghi Emanuele
 */
public class ManagerNegozio extends Utente{
	//COSTRUTTORI
	public ManagerNegozio(int id, String nome, String cognome, String email, String username, String pass){
		this.inserisciUtente(id, nome, cognome, email, username, pass);
	}
	
	//METODI
	public void modifica(String nome, String cogn, String email, String username, String pass){
		if(nome != null)
			setNome(nome);
		if(cogn != null)
			setCognome(cogn);
		if(email != null)
			setEmail(email);
		if(username != null)
			setUsername(username);
		if(pass != null)
			setPassword(pass);
	}
	
}

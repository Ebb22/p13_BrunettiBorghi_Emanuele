package Utente;

/**
 * @author Brunetti Borghi Emanuele
 */
public class ManagerSistema extends Utente{
	//COSTRUTTORI
	public ManagerSistema(int id, String nome, String cognome, String email, String username, String pass){
		this.inserisciUtente(id, nome, cognome, email, username, pass);
	}
	

}

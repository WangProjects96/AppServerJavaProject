package exceptions;

@SuppressWarnings("serial")
public class AboExistePasException extends Exception {
	
	public String toString(){
		return "Aucun abonné ne correspond au numéro d'abonné saisi";
	}
}

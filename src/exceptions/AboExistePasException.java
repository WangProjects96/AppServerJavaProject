package exceptions;

@SuppressWarnings("serial")
public class AboExistePasException extends Exception {
	
	public String toString(){
		return "Aucun abonn� ne correspond au num�ro d'abonn� saisi";
	}
}

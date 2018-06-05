package exceptions;

@SuppressWarnings("serial")
public class DocExistePasException extends Exception {
	public String toString(){
		return "Le livre indiqué n'existe pas";
	}
}

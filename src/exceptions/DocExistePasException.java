package exceptions;

@SuppressWarnings("serial")
public class DocExistePasException extends Exception {
	public String toString(){
		return "Le livre indiqu� n'existe pas";
	}
}

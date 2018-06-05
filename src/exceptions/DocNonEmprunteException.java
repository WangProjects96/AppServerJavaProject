package exceptions;

@SuppressWarnings("serial")
public class DocNonEmprunteException extends Exception {
	public String toString(){
		return "Ce livre n'a pas été emprunté";
	}
}

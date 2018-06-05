package exceptions;

@SuppressWarnings("serial")
public class EmprunteException extends PasLibreException{
	public String toString(){
		return "Désolé, ce livre a déjà été emprunté";
	}
}

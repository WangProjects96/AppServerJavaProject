package exceptions;

@SuppressWarnings("serial")
public class AbonneInterditException extends PasLibreException {
	public String toString(){
		return "Désolé, votre compte a été bloqué";
	}
}

package exceptions;

@SuppressWarnings("serial")
public class AbonneInterditException extends PasLibreException {
	public String toString(){
		return "D�sol�, votre compte a �t� bloqu�";
	}
}

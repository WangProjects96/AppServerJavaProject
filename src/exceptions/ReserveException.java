package exceptions;

@SuppressWarnings("serial")
public class ReserveException extends PasLibreException{
	public String toString(){
		return "Désolé, ce livre a déjà été réservé";
	}
}

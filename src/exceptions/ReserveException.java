package exceptions;

@SuppressWarnings("serial")
public class ReserveException extends PasLibreException{
	public String toString(){
		return "D�sol�, ce livre a d�j� �t� r�serv�";
	}
}

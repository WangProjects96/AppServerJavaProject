package exceptions;

@SuppressWarnings("serial")
public class EmprunteException extends PasLibreException{
	public String toString(){
		return "D�sol�, ce livre a d�j� �t� emprunt�";
	}
}

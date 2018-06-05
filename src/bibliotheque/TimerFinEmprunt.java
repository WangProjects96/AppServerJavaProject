package bibliotheque;

import java.util.TimerTask;

public class TimerFinEmprunt extends TimerTask {
	
	private Livre livre;

	public TimerFinEmprunt(Livre livre) {
		this.livre = livre;
	}

	@Override
	public void run() {
		System.out.println("Le livre " + livre.numero() +  " aurait du être rendu");
		livre.retardConstate();
	}

}

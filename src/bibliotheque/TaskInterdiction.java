package bibliotheque;

import java.util.TimerTask;

public class TaskInterdiction extends TimerTask {
	
	private Abonne abo;

	public TaskInterdiction(Abonne abo) {
		this.abo = abo;
	}

	@Override
	public void run() {
		abo.finInterdiction();
		System.out.println("L'interdiction de l'abonne " + abo.numero() + " prend fin");
	}

}

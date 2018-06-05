package bibliotheque;

import java.util.Timer;
import java.util.TimerTask;

public class TimerFinResa extends TimerTask{
	
	private Livre livre;
	private Timer timer;

	public TimerFinResa(Livre livre, Timer timer) {
		this.livre = livre;
		this.timer = timer;
	}

	@Override
	public void run() {
		this.livre.retour();
		this.timer.cancel();
	}

}

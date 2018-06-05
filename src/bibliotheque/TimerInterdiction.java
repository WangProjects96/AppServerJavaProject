package bibliotheque;

import java.util.Timer;
import java.util.TimerTask;

public class TimerInterdiction extends Timer{

	private TimerTask task;
	private Abonne abo;
	
	public void lancer(Abonne abo, long delai){
		task = new TaskInterdiction(abo);
		this.abo = abo;
		this.schedule(task, delai);
	}
	
	public void relancer(long delai){
		task.cancel();
		task = new TaskInterdiction(abo);
		this.schedule(task, delai);
	}
}

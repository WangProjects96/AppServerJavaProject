package bibliotheque;

import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TimerInactif extends Timer{

	private TimerTask task;
	private Socket sock;
	
	public void lancer(Socket sock, long delai){
		task = new TaskInactif(sock, this);
		this.sock = sock;
		this.schedule(task, delai);
	}
	
	public void relancer(long delai){
		task.cancel();
		task = new TaskInactif(sock, this);
		this.schedule(task, delai);
	}
}

package bibliotheque;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TaskInactif extends TimerTask{
	
	private Socket laSocket;
	private Timer timer;

	public TaskInactif(Socket laSocket, Timer timerInactif) {
		this.laSocket = laSocket;
		this.timer = timerInactif;
	}

	@Override
	public void run() {
		try {
			laSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.timer.cancel();
	}

}

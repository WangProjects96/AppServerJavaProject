package bibliotheque;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class AServeur implements Runnable{

	private ServerSocket socket_serv;


	protected AServeur(int port) throws IOException {
		setSocket_serv(new ServerSocket(port));
	}

	@Override
	public abstract void run();


	// restituer les ressources --> finalize
	protected void finalize() throws Throwable {
		this.getSocket_serv().close();
	}

	// lancement du serveur
	public void lancer() {
		new Thread(this).start();
	}

	public ServerSocket getSocket_serv() {
		return socket_serv;
	}

	public void setSocket_serv(ServerSocket socket_serv) {
		this.socket_serv = socket_serv;
	}


}

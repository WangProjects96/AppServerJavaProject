package serviceRetour;

import java.io.IOException;

import bibliotheque.AServeur;

public class ServeurRetour extends AServeur{

	public ServeurRetour(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		try {
			while(true)
				new ServiceRetour(getSocket_serv().accept()).lancer();
		}
		catch (IOException e) {
			try {getSocket_serv().close();} catch (IOException e1) {

			}
			System.err.println("Pb sur le port d'écoute : " + e);
		}		
	}

}

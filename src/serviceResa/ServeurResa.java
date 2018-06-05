package serviceResa;

import java.io.IOException;

import bibliotheque.AServeur;

public class ServeurResa extends AServeur{

	public ServeurResa(int port) throws IOException {
		super(port);
	}

	@Override
	public void run() {
		try {
			while(true)
				new ServiceResa(getSocket_serv().accept()).lancer();
		}
		catch (IOException e) {
			try {getSocket_serv().close();} catch (IOException e1) {

			}
			System.err.println("Pb sur le port d'écoute : " + e);
		}		
	}
}

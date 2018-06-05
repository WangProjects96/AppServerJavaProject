package serviceRetour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import bibliotheque.Bibliotheque;
import bibliotheque.TimerInactif;
import exceptions.DocExistePasException;
import exceptions.DocNonEmprunteException;

public class ServiceRetour implements Runnable {

	private Socket client;
	/* A changer pour 3 minutes : 180000 */
	private static final long DELAI_INACTIVITE = 10 * 1000;

	public ServiceRetour(Socket accept) {
		this.client = accept;
	}

	public void lancer() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		BufferedReader sin;
		PrintWriter sout = null;
		try {
			sin = new BufferedReader(new InputStreamReader(client.getInputStream()));
			sout = new PrintWriter(client.getOutputStream(), true);

			String livre, abime;
			boolean bAbime = false;

			TimerInactif t = new TimerInactif();
			t.lancer(client, DELAI_INACTIVITE);
			livre = sin.readLine();
			abime = sin.readLine();
			if(abime.equals("oui"))
				bAbime = true;
			Bibliotheque.retour(livre, bAbime);
			sout.println("Le retour a bien été enregistré");
			
		} catch (SocketException e) {
			System.out.println("Service Retour, Connection reset : inactive");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocExistePasException e) {
			sout.println(e.toString());
		} catch (DocNonEmprunteException e) {
			sout.println(e.toString());
		}

		try {
			this.client.close();
		} catch (IOException e) {
		}
	}

}

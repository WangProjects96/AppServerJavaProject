package serviceEmprunt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import bibliotheque.Bibliotheque;
import bibliotheque.TimerInactif;
import exceptions.AboExistePasException;
import exceptions.AbonneInterditException;
import exceptions.DocExistePasException;
import exceptions.PasLibreException;

public class ServiceEmprunt implements Runnable {

	private Socket client;
	/* A changer pour 3 minutes : 180000 */
	private static final long DELAI_INACTIVITE = 10 * 1000;

	public ServiceEmprunt(Socket accept) {
		this.client = accept;
	}

	@Override
	public void run() {
		BufferedReader sin;
		PrintWriter sout = null;
		try {
			sin = new BufferedReader(new InputStreamReader(client.getInputStream()));
			sout = new PrintWriter(client.getOutputStream(), true);

			String id, livre;
			TimerInactif t = new TimerInactif();
			t.lancer(client, DELAI_INACTIVITE);
			id = sin.readLine();

			t.relancer(DELAI_INACTIVITE);
			livre = sin.readLine();

			Bibliotheque.emprunter(id, livre);
			sout.println("L'emprunt a bien �t� enregistr�");

		} catch (SocketException e) {
			System.out.println("Service Emprunt, Connection reset : inactive");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocExistePasException e) {
			sout.println(e.toString());
		} catch (AboExistePasException e) {
			sout.println(e.toString());
		} catch (AbonneInterditException e) {
			sout.println(e.toString());
		} catch (PasLibreException e) {
			sout.println(e.toString());
		}

		try {
			this.client.close();
		} catch (IOException e) {
		}
	}

	public void lancer() {
		new Thread(this).start();
	}

}

package serviceResa;

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

public class ServiceResa implements Runnable {

	private Socket client;
	/* A changer pour 10 minutes : 600000 */
	private static final long DELAI_INACTIVITE = 10 * 1000;

	public ServiceResa(Socket accept) {
		this.client = accept;
	}

	public void lancer() {
		new Thread(this).start();
	}

	@Override
	public void run() {

		BufferedReader sin;
		PrintWriter sout = null;
		String id = null, livre = null;
		try {
			sin = new BufferedReader(new InputStreamReader(client.getInputStream()));
			sout = new PrintWriter(client.getOutputStream(), true);

			TimerInactif t = new TimerInactif();
			t.lancer(client, DELAI_INACTIVITE);
			id = sin.readLine();

			t.relancer(DELAI_INACTIVITE);
			livre = sin.readLine();

			Bibliotheque.reserver(id, livre);
			sout.println("Votre réservation a bien été enregistrée, vous avez 2h pour venir retirer le livre");

		} catch (SocketException e) {
			System.out.println("Service Resa, Connection reset : inactive");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocExistePasException e) {
			sout.println("Le livre indiqué n'existe pas");
		} catch (AboExistePasException e) {
			sout.println("Aucun abonné ne correspond au numéro d'abonné saisi");
		} catch (AbonneInterditException e) {
			sout.println(e.toString());
		} catch (PasLibreException e) {
			sout.println(e.toString());
			try {
				Bibliotheque.placerAlerte(id, livre);
			} catch (DocExistePasException | AboExistePasException e1) {
				e1.printStackTrace();
			}
		}

		try {
			this.client.close();
		} catch (IOException e) {
		}
	}

}

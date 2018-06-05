package serviceResa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class AppliClientResa {

	private static final String ADR_IP_BIBLIO = "localhost";
	private static final int PORT_RESERVATION = 2500;

	public static void main(String[] args) {
		Socket laSocket = null;
		try {
			laSocket = new Socket(ADR_IP_BIBLIO, PORT_RESERVATION);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(laSocket.getOutputStream(), true);
			/* bonjour */
			System.out.println("Bienvenue sur votre système de réservation : ");
			System.out.println("Vous pouvez ici réserver un livre disponible ");
			System.out.println("et passer le chercher dans les 2 heures");

			/* saisie des données */
			@SuppressWarnings("resource")
			Scanner clavier = new Scanner(System.in);
			System.out.println("Votre numéro d'abonné, svp :");
			int numAbonné = clavier.nextInt();
			socketOut.println(numAbonné);
			System.out.println("Le numéro de livre que vous souhaitez réserver :");
			int numDocument = clavier.nextInt();
			/* envoi des données au service */
			socketOut.println(numDocument);
			/* réception de la réponse et affichage de cette réponse */
			System.out.println(socketIn.readLine());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("Vous avez été déconnecté pour cause d'inactivité");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// fermeture de la connexion
		try {
			laSocket.close();
		} catch (IOException e) {
		}
	}

}

package serviceRetour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class AppliClientRetour {

	private static final String ADR_IP_BIBLIO = "localhost";
	private static final int PORT_RETOUR = 2700;

	public static void main(String[] args) {
		Socket laSocket = null;
		try {
			laSocket = new Socket(ADR_IP_BIBLIO, PORT_RETOUR);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(laSocket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(laSocket.getOutputStream(), true);
			/* bonjour */
			System.out.println("Bienvenue sur votre système de retour: ");
			/* saisie des données */
			@SuppressWarnings("resource")
			Scanner clavier = new Scanner(System.in);
			System.out.println("Le numéro de livre que vous souhaitez retourner :");
			int numDocument = clavier.nextInt();
			System.out.println("Le livre est-il âbimé ? (oui/non) :");
			clavier.nextLine();
			String abime = clavier.nextLine();
			/* envoi des données au service */
			socketOut.println(numDocument);
			socketOut.println(abime);
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

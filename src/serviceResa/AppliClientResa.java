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
			System.out.println("Bienvenue sur votre syst�me de r�servation : ");
			System.out.println("Vous pouvez ici r�server un livre disponible ");
			System.out.println("et passer le chercher dans les 2 heures");

			/* saisie des donn�es */
			@SuppressWarnings("resource")
			Scanner clavier = new Scanner(System.in);
			System.out.println("Votre num�ro d'abonn�, svp :");
			int numAbonn� = clavier.nextInt();
			socketOut.println(numAbonn�);
			System.out.println("Le num�ro de livre que vous souhaitez r�server :");
			int numDocument = clavier.nextInt();
			/* envoi des donn�es au service */
			socketOut.println(numDocument);
			/* r�ception de la r�ponse et affichage de cette r�ponse */
			System.out.println(socketIn.readLine());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("Vous avez �t� d�connect� pour cause d'inactivit�");
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

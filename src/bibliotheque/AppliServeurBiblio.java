package bibliotheque;

import java.io.IOException;

import serviceEmprunt.ServeurEmprunt;
import serviceResa.ServeurResa;
import serviceRetour.ServeurRetour;

public class AppliServeurBiblio {

	private final static int PORT_RESA = 2500;
	private final static int PORT_EMPRUNT = 2600;
	private final static int PORT_RETOUR = 2700;

	public static void main(String[] args) {

		/* Ajout des abonnés et des livres */
		for (int i = 0; i < 10; ++i) {
			Bibliotheque.ajouterLivre(new Livre());
		}

		Bibliotheque.recupAbos("./src/abos.txt");

		try {
			new ServeurResa(PORT_RESA).lancer();
			System.out.println("ServeurResa lance sur le port " + PORT_RESA);
			new ServeurEmprunt(PORT_EMPRUNT).lancer();
			System.out.println("ServeurEmprunt lance sur le port " + PORT_EMPRUNT);
			new ServeurRetour(PORT_RETOUR).lancer();
			System.out.println("ServeurRetour lance sur le port " + PORT_RETOUR);
		} catch (IOException e) {
			System.err.println("Pb lors de la création du serveur : " + e);
		}
	}

}

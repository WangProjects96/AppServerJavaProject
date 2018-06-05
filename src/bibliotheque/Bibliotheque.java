package bibliotheque;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.AboExistePasException;
import exceptions.DocExistePasException;
import exceptions.DocNonEmprunteException;
import exceptions.PasLibreException;

public class Bibliotheque {

	/*
	 * On utiliserait la classe Vector dans le cas où on gérerait l'ajout et la
	 * suppression de documents/abonnes dans un contexte concurrentiel
	 */
	private static ArrayList<Document> docs = new ArrayList<Document>();
	private static ArrayList<Abonne> abonnes = new ArrayList<Abonne>();

	
	
	public static void recupAbos(String emplacement) {
		try {
			Scanner in = new Scanner(new FileInputStream(emplacement));
			while (in.hasNextLine()) {
				abonnes.add(new Abonne(in.nextLine()));
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("impossible d'ouvrir le fichier :p");
		}
	}
	
	
	public static void ajouterLivre(Livre livre) {
		docs.add(livre);
	}

	public static void ajouterAbo(Abonne abonne) {
		abonnes.add(abonne);
	}

	/*
	 * Synchronized non requis puisque l'on ne gère pas l'ajout/supression
	 * d'abonnes
	 */
	private static Abonne getAbo(String id) throws AboExistePasException {
		for (Abonne d : abonnes)
			if (d.numero() == Integer.parseInt(id))
				return d;
		throw new AboExistePasException();
	}

	/*
	 * Synchronized non requis puisque l'on ne gère pas l'ajout/supression de
	 * documents
	 */
	public static Document getDoc(String numDoc) throws DocExistePasException {
		for (Document d : docs)
			if (d.numero() == Integer.parseInt(numDoc))
				return d;
		throw new DocExistePasException();
	}

	/* Méthodes relatives à la réservation */

	public static void reserver(String id, String numDoc)
			throws DocExistePasException, AboExistePasException, PasLibreException {
		getDoc(numDoc).reserver(getAbo(id));
	}

	/* Méthodes relatives à l'emprunt */

	public static void emprunter(String id, String doc)
			throws DocExistePasException, AboExistePasException, PasLibreException {
		getDoc(doc).emprunter(getAbo(id));
	}

	/* Méthodes relatives au retour */

	public static void retour(String doc, boolean abime) throws DocExistePasException, DocNonEmprunteException {
		Document livre = getDoc(doc);
		Abonne abo = ((Livre) livre).getDetenteur();
		if (((Livre) livre).estEnRetard() || abime)
			abo.interdire();
		getDoc(doc).retour();
	}

	/* Placement d'alerte */

	public static void placerAlerte(String id, String livre) throws DocExistePasException, AboExistePasException {
		Livre l = (Livre)getDoc(livre);
		Abonne a = getAbo(id);
		a.alerter(l);
		/*if(!a.dejaAlerte(l)){
			new Alert((Livre)getDoc(livre), getAbo(id)).lancer();
			a.alerter(l);
		}*/		
	}

}

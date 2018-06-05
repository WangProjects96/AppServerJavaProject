package bibliotheque;

import java.util.Vector;

public class Abonne {

	/* A changer pour 1 mois (30jours) : 2592000000 */
	private final static long DELAI_INTERDICTION = 1000 * 20;
	private int numAbo;
	private static int cpt = 1;
	private boolean interditEmprunt;
	private TimerInterdiction timerInterdiction;
	private String mail;
	private Vector<Document> alertes;

	public Abonne(String mail) {
		this.numAbo = cpt++;
		this.interditEmprunt = false;
		this.mail = mail;
		this.alertes = new Vector<Document>();
	}

	public int numero() {
		return numAbo;
	}

	public boolean estInterdit() {
		return this.interditEmprunt;
	}

	public synchronized void interdire() {
		this.interditEmprunt = true;
		/*
		 * Synchronized au cas o� le scheduler interrompt ici et que la fonction
		 * finInterdiction() est appel�e. Quand on rentre dans la fonction
		 * interdire(), le booleen interditEmprunt doit rester � true, ce qui ne
		 * serit pas le cas si finInterdiction() �tait appel�e.
		 */
		if (this.timerInterdiction != null)
			this.timerInterdiction.relancer(DELAI_INTERDICTION);
		else {
			this.timerInterdiction = new TimerInterdiction();
			this.timerInterdiction.lancer(this, DELAI_INTERDICTION);
		}
	}

	/*
	 * Synchronized au cas o� la fonction interdire() est appel�e : il ne
	 * faudrait pas cancel le timer
	 */
	public synchronized void finInterdiction() {
		this.interditEmprunt = false;
		this.timerInterdiction.cancel();
		this.timerInterdiction = null;
	}

	public String mail() {
		return this.mail;
	}

	/*
	 * Synchronized car on suppose que l'abonn� puisse effectuer plusieurs
	 * r�servations en m�me temps (sur plusieurs postes par exemple). Il serait
	 * donc possible que cette fonction soit appel�e simultan�ment plusieurs
	 * fois sur le m�me abonn�
	 */
	public void alerter(Document doc) {
		synchronized (this) {
			if (!alertes.contains(doc)) {
				alertes.add(doc);
				new Alert((Livre) doc, this).lancer();
			}
		}
	}

	public void supprAlerte(Document doc) {
		alertes.remove(doc);
	}

}

package bibliotheque;

import java.util.Timer;

import exceptions.AbonneInterditException;
import exceptions.DocNonEmprunteException;
import exceptions.EmprunteException;
import exceptions.ReserveException;

public class Livre implements Document {

	/* A changer pour 2 heures : 7200000 */
	private final static long DELAI_RESA = 20 * 1000;
	/* A changer pour 2 semaines : 1468800000 */
	private final static long DELAI_EMPRUNT = 10 * 1000;
	private int num;
	private boolean emprunte;
	private boolean reserve;
	private boolean retard;
	private static int cpt = 1;
	private Timer timerEmprunt;
	private Abonne detenteur;

	public Livre() {
		num = cpt++;
		emprunte = false;
		reserve = false;
		retard = false;
	}

	@Override
	public int numero() {
		return num;
	}

	/*
	 * Synchronized imbriqués car le statut de l'abonné ne doit pas être modifié
	 * au cours de la réservation
	 */
	@Override
	public void reserver(Abonne ab) throws ReserveException, AbonneInterditException, EmprunteException {
		synchronized (ab) {
			if (ab.estInterdit())
				throw new AbonneInterditException();
			/*
			 * L'abonné doit toujours être autorisé à réserver lorsque l'on
			 * passe à la modification du livre
			 */
			synchronized (this) {
				if (reserve) {
					throw new ReserveException();
				} else if (emprunte) {
					throw new EmprunteException();
				}
				/*
				 * Les étapes suivantes doivent être réalisées uniquement si les
				 * tests ci-dessus ont renvoyé false, le livre ne doit donc pas
				 * avoir été modifié
				 */
				reserve = true;
				Timer timer = new Timer();
				timer.schedule(new TimerFinResa(this, timer), DELAI_RESA);
				detenteur = ab;
			}
		}
	}

	@Override
	public void emprunter(Abonne ab) throws EmprunteException, AbonneInterditException, ReserveException {
		synchronized (ab) {
			if (ab.estInterdit())
				throw new AbonneInterditException();
			synchronized (this) {
				if (reserve && !ab.equals(detenteur)) {
					throw new ReserveException();
				} else if (emprunte) {
					throw new EmprunteException();
				}
				emprunte = true;
				reserve = false;
				this.timerEmprunt = new Timer();
				this.timerEmprunt.schedule(new TimerFinEmprunt(this), DELAI_EMPRUNT);
				detenteur = ab;
			}
		}
	}

	@Override
	public void retour() {
		synchronized (this) {
			if (reserve) {
				detenteur = null;
				reserve = false;
			} else if (emprunte) {
				detenteur = null;
				emprunte = false;
				if (!retard)
					this.timerEmprunt.cancel();
				retard = false;
			}
			this.notifyAll(); //le livre a changer d'etat, donc il dit au wait de livre que c'est plus en wait
		}
	}

	public void retardConstate() {
		retard = true;
		this.detenteur.interdire();
		this.timerEmprunt.cancel();
	}

	public boolean estEnRetard() {
		return this.retard;
	}

	public Abonne getDetenteur() throws DocNonEmprunteException {
		if (this.detenteur != null)
			return this.detenteur;
		throw new DocNonEmprunteException();
	}

	public boolean estDisponible() {
		return !reserve && !emprunte;
	}
}

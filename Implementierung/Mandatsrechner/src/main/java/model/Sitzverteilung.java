package main.java.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Diese Klasse repraesentiert die Sitzverteilung einer Bundestagswahl.
 */
public class Sitzverteilung implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fï¿½r das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -6027948741318694048L;

	/** Die Liste aller Abgeordneten in dieser Sitzverteilung. */
	private LinkedList<Kandidat> abgeordnete;

	/** Enthält Informationen zur Sitzverteilung */
	private BerichtDaten bericht;

	/**
	 * Parametrisierter Konstruktor zum Erzeugen von Sitzverteilungen.
	 * 
	 * @param abgeordnete
	 *            Die Liste aller Abgeordneten in dieser Sitzverteilung.
	 * @param bericht
	 *            Der Bericht wie diese Sitzverteilung zustande gekommen ist.
	 */
	public Sitzverteilung(LinkedList<Kandidat> abgeordnete, BerichtDaten bericht) {
		setAbgeordnete(abgeordnete);
		setBericht(bericht);
	}

	/**
	 * Fuegt einen Kandidaten zur Liste hinzu.
	 * 
	 * @param kandidat
	 *            wird zur Liste hinzugefuegt
	 */
	public void addAbgeordnete(Kandidat kandidat) {
		getAbgeordnete().add(kandidat);
	}

	/**
	 * Gibt die Liste aller Abgeordneten.
	 * 
	 * @return die Liste aller Abgeordneten
	 */
	public LinkedList<Kandidat> getAbgeordnete() {
		return this.abgeordnete;
	}

	/**
	 * Gibt die Anzahl der Sitze einer gegebenen Partei zurück.
	 * 
	 * @param partei
	 *            Die Partei zu der die Sitzanzahl gesucht ist.
	 * @return Die Anzahl der Sitze einer gegebenen Partei.
	 */
	public int getAnzahlSitze(Partei partei) {
		int sitze = 0;
		for (final Kandidat abgeordneter : this.abgeordnete) {
			if (abgeordneter.getPartei().getName().equals(partei.getName())) {
				sitze++;
			}
		}
		return sitze;
	}

	/**
	 * Gibt den Bericht zurück
	 * 
	 * @return den Bericht
	 */
	public BerichtDaten getBericht() {
		return this.bericht;
	}

	/**
	 * Setzt die Liste aller Abgeordneten.
	 * 
	 * @param abgeordnete
	 *            die Liste aller Abgeordneten.
	 * @throws IllegalArgumentException
	 *             wenn der Parameter abgeordnete null ist.
	 */
	public final void setAbgeordnete(LinkedList<Kandidat> abgeordnete)
			throws IllegalArgumentException {
		if (abgeordnete == null) {
			throw new IllegalArgumentException(
					"Parameter 'abgeordnete' ist null!");
		}
		this.abgeordnete = abgeordnete;
	}

	/**
	 * Setzt den neuen Bericht
	 * 
	 * @param bericht
	 *            der neue Bericht
	 * @throws IllegalArgumentException
	 *             wenn der Bericht null ist
	 */
	public final void setBericht(BerichtDaten bericht) {
		if (bericht == null) {
			throw new IllegalArgumentException("Bericht ist null");
		}
		this.bericht = bericht;

	}

}

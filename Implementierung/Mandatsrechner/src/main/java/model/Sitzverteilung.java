package main.java.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Diese Klasse repraesentiert die Sitzverteilung einer Bundestagswahl.
 */
public class Sitzverteilung implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die f�r das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -6027948741318694048L;

	/** Die Liste aller Abgeordneten in dieser Sitzverteilung. */
	private LinkedList<Kandidat> abgeordnete;

	/** Der Bericht wie diese Sitzverteilung zustande gekommen ist. */
	private String bericht;

	/**
	 * Parametrisierter Konstruktor zum Erzeugen von Sitzverteilungen.
	 * 
	 * @param abgeordnete
	 *            Die Liste aller Abgeordneten in dieser Sitzverteilung.
	 * @param bericht
	 *            Der Bericht wie diese Sitzverteilung zustande gekommen ist.
	 */
	public Sitzverteilung(LinkedList<Kandidat> abgeordnete, String bericht) {
		this.setAbgeordnete(abgeordnete);
		this.setBericht(bericht);
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
	 * Setzt die Liste aller Abgeordneten.
	 * 
	 * @param abgeordnete
	 *            die Liste aller Abgeordneten.
	 * @throws IllegalArgumentException
	 *             wenn der Parameter abgeordnete null ist.
	 */
	public void setAbgeordnete(LinkedList<Kandidat> abgeordnete)
			throws IllegalArgumentException {
		if (abgeordnete == null) {
			throw new IllegalArgumentException(
					"Parameter 'abgeordnete' ist null!");
		}
		this.abgeordnete = abgeordnete;
	}

	/**
	 * Gibt den Bericht dieser Sitzverteilung.
	 * 
	 * @return den Bericht dieser Sitzverteilung
	 */
	public String getBericht() {
		return this.bericht;
	}

	/**
	 * Setzt den Bericht dieser Sitzverteilung.
	 * 
	 * @param bericht
	 *            den Bericht dieser Sitzverteilung
	 * @throws IllegalArgumentException
	 *             wenn der Parameter bericht null ist.
	 */
	public void setBericht(String bericht) throws IllegalArgumentException {
		if (bericht == null) {
			throw new IllegalArgumentException("Parameter 'bericht' ist null!");
		}
		this.bericht = bericht;
	}
	
	/**
	 * Fügt einen neuen Eintrag als eine neue Zeile in den Bericht hinzu
	 * @param eintrag die neue Zeile im Bericht
	 */
	public void addBerichtEintrag(String eintrag){
		this.bericht += "\n"+eintrag;
	}

	/**
	 * Fuegt einen Kandidaten zur Liste hinzu.
	 * 
	 * @param kandidat
	 *            wird zur Liste hinzugefuegt
	 */
	public void addAbgeordnete(Kandidat kandidat) {
		this.getAbgeordnete().add(kandidat);
	}

}

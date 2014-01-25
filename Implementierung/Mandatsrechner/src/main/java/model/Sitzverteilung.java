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

	/** Enth�lt Informationen zur Sitzverteilung*/
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
	 * Setzt den neuen Bericht
	 * @param bericht der neue Bericht
	 * @throws wenn der Bericht null ist
	 */
	public void setBericht(BerichtDaten bericht) {
		if(bericht == null){
			throw new IllegalArgumentException("Bericht ist null");
		}
		this.bericht = bericht;
		
	}
	
	/**
	 * Gibt den Bericht zur�ck
	 * @return den Bericht
	 */
	public BerichtDaten getBericht() {
		return this.bericht;
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

package model;

import java.util.LinkedList;

/**
 * Diese Klasse repräsentiert die Sitzverteilung einer Bundestagswahl.
 */
public class Sitzverteilung {
	
	/** Die Liste aller Abgeordneten in dieser Sitzverteilung. */
	private LinkedList<Kandidat> abgeordnete;
	
	/** Der Bericht wie diese Sitzverteilung zustande gekommen ist. */
	private String bericht;
	
	
	/**
	 * Parametrisierter Konstruktor zum erzeugen von Sitzverteilungen.
	 */
	public Sitzverteilung(LinkedList<Kandidat> abgeordnete, String bericht) {
		this.setAbgeordnete(abgeordnete);
		this.setBericht(bericht);
	}

	/**
	 * Gibt die Liste aller Abgeordneten.
	 * @return die Liste aller Abgeordneten
	 */
	public LinkedList<Kandidat> getAbgeordnete() {
		return abgeordnete;
	}

	/**
	 * Setzt die Liste aller Abgeordneten.
	 * @param abgeordnete die Liste aller Abgeordneten
	 */
	public void setAbgeordnete(LinkedList<Kandidat> abgeordnete) {
		if (abgeordnete == null) {
			throw new IllegalArgumentException("Parameter 'abgeordnete' ist null!");
		}
		this.abgeordnete = abgeordnete;
	}

	/**
	 * Gibt den Bericht dieser Sitzverteilung.
	 * @return den Bericht dieser Sitzverteilung
	 */
	public String getBericht() {
		return bericht;
	}

	/**
	 * Setzt den Bericht dieser Sitzverteilung.
	 * @param bericht den Bericht dieser Sitzverteilung
	 */
	public void setBericht(String bericht) {
		if (bericht == null) {
			throw new IllegalArgumentException("Parameter 'bericht' ist null!");
		}
		this.bericht = bericht;
	}
	
}

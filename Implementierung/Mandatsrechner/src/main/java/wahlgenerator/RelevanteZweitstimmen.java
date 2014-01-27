package main.java.wahlgenerator;

import java.io.Serializable;

import main.java.model.Stimme;

/*
 * Bei mindestens einer Partei muss der prozentuale Anteil ihrer relevanten
 * Zweitstimmen größer als der prozentuale Anteil ihrer Mandate sein.
 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf
 * Landeslisten abgegeben werden, die keine überhangmandate erzielen
 */ 

/**
 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf Landeslisten
 * abgegeben werden, die keine Überhangmandate erzielen.
 */
public class RelevanteZweitstimmen extends  Stimme implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die für das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -2753165575954824955L;

	/**
	 * Parametrisierter Konstruktor zum erzeugen von Zweitstimmen.
	 * 
	 * @param anzahl
	 *            Die Anzahl der Stimmen.
	 */
	public RelevanteZweitstimmen(int anzahl) {
		this.setAnzahl(anzahl);

	}

	@Override
	public void setAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}

		this.anzahl = anzahl;
		
	}

	@Override
	public void erhoeheAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		
		this.setAnzahl(this.anzahl + anzahl);
		
	}

	@Override
	public Stimme deepCopy() {
		throw new IllegalAccessError("Nicht implementiert!");
	}

}

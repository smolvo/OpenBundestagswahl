package wahlgenerator;

import java.io.Serializable;

import model.Gebiet;
import model.Partei;
import model.Stimme;

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
public class RelevanteZweitstimmen extends Stimme implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die für das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -2753165575954824955L;


	/** Die zugehörige Partei. */
	private Partei partei;

	/**
	 * Parametrisierter Konstruktor zum erzeugen von Zweitstimmen.
	 * 
	 * @param anzahl
	 *            Die Anzahl der Stimmen.
	 * @param gebiet
	 *            Das zugehörige Gebiet.
	 * @param partei
	 *            Die zugehörige Partei.
	 */
	public RelevanteZweitstimmen(int anzahl, Partei partei) {
		this.setAnzahl(anzahl);
		this.setPartei(partei);
	}

	/**
	 * Gibt die zugehörige Partei zurück.
	 * 
	 * @return die zugehörige Partei
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Setzt die zugehörige Partei dieser Zweitstimme.
	 * 
	 * @param partei
	 *            die zugehörige Partei
	 * @throws IllegalArgumentException
	 *             wenn der Parameter partei null ist.
	 */
	public void setPartei(Partei partei) throws IllegalArgumentException {
		if (partei == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"partei\" ist null!");
		}
		this.partei = partei;
	}




}

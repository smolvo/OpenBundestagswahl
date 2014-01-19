package wahlgenerator;

import java.io.Serializable;

import model.Gebiet;
import model.Partei;
import model.Stimme;

/*
 * Bei mindestens einer Partei muss der prozentuale Anteil ihrer relevanten
 * Zweitstimmen gr��er als der prozentuale Anteil ihrer Mandate sein.
 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf
 * Landeslisten abgegeben werden, die keine �berhangmandate erzielen
 */

/**
 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf Landeslisten
 * abgegeben werden, die keine �berhangmandate erzielen.
 */
public class RelevanteZweitstimmen extends Stimme implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die f�r das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -2753165575954824955L;


	/** Die zugeh�rige Partei. */
	private Partei partei;

	/**
	 * Parametrisierter Konstruktor zum erzeugen von Zweitstimmen.
	 * 
	 * @param anzahl
	 *            Die Anzahl der Stimmen.
	 * @param gebiet
	 *            Das zugeh�rige Gebiet.
	 * @param partei
	 *            Die zugeh�rige Partei.
	 */
	public RelevanteZweitstimmen(int anzahl, Partei partei) {
		this.setAnzahl(anzahl);
		this.setPartei(partei);
	}

	/**
	 * Gibt die zugeh�rige Partei zur�ck.
	 * 
	 * @return die zugeh�rige Partei
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Setzt die zugeh�rige Partei dieser Zweitstimme.
	 * 
	 * @param partei
	 *            die zugeh�rige Partei
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

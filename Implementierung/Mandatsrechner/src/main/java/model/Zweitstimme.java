package main.java.model;

import java.io.Serializable;

/**
 * Assoziationsklasse die Anzahl aller Zweitstimmen beinhaltet und mit den
 * Klassen Deutschland und Partei zusammen arbeitet. Ausserdem erbt diese von
 * der Klasse Stimme.
 */
public class Zweitstimme extends Stimme implements Serializable,
		Comparable<Zweitstimme> {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
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
	public Zweitstimme(int anzahl, Gebiet gebiet, Partei partei) {
		setAnzahl(anzahl);
		setGebiet(gebiet);
		setPartei(partei);
	}

	@Override
	public int compareTo(Zweitstimme andere) {
		return Integer.compare(andere.getAnzahl(), getAnzahl());
	}

	@Override
	public Stimme deepCopy() {
		return new Zweitstimme(this.anzahl, this.gebiet, this.partei);
	}

	@Override
	public void erhoeheAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		if (getGebiet().getWahlberechtigte() < getGebiet()
				.getAnzahlZweitstimmen() + anzahl) {
			throw new IllegalArgumentException(
					"Anzahl der Zweitstimmen > Anzahl der Wahlberechtigten!");
		}
		this.anzahl += anzahl;
	}

	/**
	 * Diese Methode vermindert die Anzahl an Zweitstimmen.
	 * 
	 * @param stimmanzahl
	 *            Stimmenanzahl
	 * @throws IllegalArgumentException
	 */
	public void erniedrigeAnzahl(int stimmanzahl) {
		if (stimmanzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		if (this.anzahl - stimmanzahl < 0) {
			throw new IllegalArgumentException("Stimmzahlen wären kleiner 0!");
		}
		this.anzahl -= stimmanzahl;
	}

	/**
	 * Gibt die zugehoerige Partei zurï¿½ck.
	 * 
	 * @return die zugehoerige Partei
	 */
	public Partei getPartei() {
		return this.partei;
	}

	@Override
	public boolean isErststimme() {
		return false;
	}

	@Override
	public boolean isZweitstimme() {
		return true;
	}

	@Override
	public final void setAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		/*
		 * if (this.getGebiet().getWahlberechtigte() <
		 * (this.getGebiet().getZweitstimmeGesamt() - this.getAnzahl() +
		 * anzahl)) { throw new IllegalArgumentException(
		 * "Anzahl der Zweitstimmen > Anzahl der Wahlberechtigten!"); }
		 */
		this.anzahl = anzahl;
	}

	/**
	 * Setzt die zugehoerige Partei dieser Zweitstimme.
	 * 
	 * @param partei
	 *            die zugehoerige Partei
	 * @throws IllegalArgumentException
	 *             wenn der Parameter partei null ist.
	 */
	public final void setPartei(Partei partei) throws IllegalArgumentException {
		if (partei == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"partei\" ist null!");
		}
		this.partei = partei;
	}

}

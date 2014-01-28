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

	/** Die zugehï¿½rige Partei. */
	private Partei partei;

	/**
	 * Parametrisierter Konstruktor zum erzeugen von Zweitstimmen.
	 * 
	 * @param anzahl
	 *            Die Anzahl der Stimmen.
	 * @param gebiet
	 *            Das zugehï¿½rige Gebiet.
	 * @param partei
	 *            Die zugehï¿½rige Partei.
	 */
	public Zweitstimme(int anzahl, Gebiet gebiet, Partei partei) {
		this.setAnzahl(anzahl);
		this.setGebiet(gebiet);
		this.setPartei(partei);
	}

	/**
	 * Gibt die zugehoerige Partei zurï¿½ck.
	 * 
	 * @return die zugehoerige Partei
	 */
	public Partei getPartei() {
		return this.partei;
	}

	/**
	 * Setzt die zugehoerige Partei dieser Zweitstimme.
	 * 
	 * @param partei
	 *            die zugehoerige Partei
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

	@Override
	public int compareTo(Zweitstimme andere) {
		return Integer.compare(andere.getAnzahl(), this.getAnzahl());
	}

	@Override
	public void setAnzahl(int anzahl) throws IllegalArgumentException {
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

	@Override
	public void erhoeheAnzahl(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		if (this.getGebiet().getWahlberechtigte() < (this.getGebiet()
				.getAnzahlZweitstimmen() + anzahl)) {
			throw new IllegalArgumentException(
					"Anzahl der Zweitstimmen > Anzahl der Wahlberechtigten!");
		}
		this.anzahl += anzahl;
	}

	@Override
	public Stimme deepCopy() {
		return new Zweitstimme(this.anzahl, this.gebiet, this.partei);
	}
	

	
	public void erniedrigeAnzahl(int stimmanzahl) throws IllegalArgumentException {
		if (stimmanzahl < 0) {
			throw new IllegalArgumentException("Anzahl ist negativ!");
		}
		if ((anzahl - stimmanzahl) < 0) {
			throw new IllegalArgumentException("Stimmzahlen wären kleiner 0!");
		}
		this.anzahl -= stimmanzahl;
	}


}

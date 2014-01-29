package main.java.stimmgewichtsimulator;

import main.java.model.Bundesland;
import main.java.model.Partei;


/**
 * Diese Klasse repräsentiert eine Sprungstelle in der Simulation des negativen Stimmgewichts.
 * 
 * Eine Sprungstelle ist eine Veränderung der Anzahl der Mandate 
 * für eine bestimmte Partei in einem bestimmten Bundesland 
 */
public class Sprungstelle {

	/** Die Partei bei der dieser Sprung aufgetreten ist. */
	private Partei partei;
	
	/** Das Bundesland bei dem dieser Sprung aufgetreten ist. */
	private Bundesland bundesland;
	
	/** Die Zweitstimmenanzahl zu dem dieser Sprung aufgetreten ist. */
	private int zweitstimmzahl;
	
	/** Die Richtung in die dieser Sprung (Mandatszahl) ging. (positiv oder negativ) */
	private Richtung richtung;

	
	/**
	 * Erzeugt Sprungstellen und setzt alle Attribute.
	 * 
	 * @param partei Die Partei bei der dieser Sprung aufgetreten ist.
	 * @param bundesland Das Bundesland bei dem dieser Sprung aufgetreten ist.
	 * @param zweitstimmzahl Die Zweitstimmenanzahl zu dem dieser Sprung aufgetreten ist.
	 * @param richtung Die Richtung in die dieser Sprung ging. (positiv oder negativ)
	 */
	public Sprungstelle(Partei partei, Bundesland bundesland, int zweitstimmzahl, Richtung richtung) {
		this.setPartei(partei);
		this.setBundesland(bundesland);
		this.setZweitstimmzahl(zweitstimmzahl);
		this.setRichtung(richtung);
	}

	/**
	 * Gibt die Partei bei der dieser Sprung aufgetreten ist zurück.
	 * @return Die Partei bei der dieser Sprung aufgetreten ist.
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Setzt die Partei bei der dieser Sprung aufgetreten ist.
	 * @param partei Die Partei bei der dieser Sprung aufgetreten ist.
	 */
	public void setPartei(Partei partei) {
		if (partei == null) {
			throw new IllegalArgumentException("Der Parameter \"partei\" ist null!");
		}
		this.partei = partei;
	}

	/**
	 * Gibt das Bundesland bei der dieser Sprung aufgetreten ist zurück.
	 * @return Das Bundesland bei der dieser Sprung aufgetreten ist.
	 */
	public Bundesland getBundesland() {
		return bundesland;
	}

	/**
	 * Setzt das Bundesland bei der dieser Sprung aufgetreten ist.
	 * @param bundesland Das Bundesland bei der dieser Sprung aufgetreten ist.
	 */
	public void setBundesland(Bundesland bundesland) {
		if (bundesland == null) {
			throw new IllegalArgumentException("Der Parameter \"bundesland\" ist null!");
		}
		this.bundesland = bundesland;
	}

	/**
	 * Gibt die Zweitstimmzahl zu dem dieser Sprung aufgetreten ist zurück.
	 * @return Die Zweitstimmzahl zu dem dieser Sprung aufgetreten ist.
	 */
	public int getZweitstimmzahl() {
		return zweitstimmzahl;
	}

	/**
	 * Setzt die Zweitstimmzahl zu dem dieser Sprung aufgetreten ist.
	 * @param zweitstimmzahl die Zweitstimmzahl zu dem dieser Sprung aufgetreten ist.
	 */
	public void setZweitstimmzahl(int zweitstimmzahl) {
		if (zweitstimmzahl < 0) {
			throw new IllegalArgumentException("Der Parameter \"zweitstimmzahl\" ist negativ!");
		}
		this.zweitstimmzahl = zweitstimmzahl;
	}

	/**
	 * Gibt die Richtung in die dieser Sprung ging (positiv oder negativ) zurück.
	 * @return Die Richtung in die dieser Sprung ging. (positiv oder negativ)
	 */
	public Richtung getRichtung() {
		return richtung;
	}

	/**
	 * Setzt die Richtung in die dieser Sprung ging. (positiv oder negativ).
	 * @param richtung Die Richtung in die dieser Sprung ging. (positiv oder negativ)
	 */
	public void setRichtung(Richtung richtung) {
		if (richtung == null) {
			throw new IllegalArgumentException("Der Parameter \"richtung\" ist null!");
		}
		this.richtung = richtung;
	}
	
}

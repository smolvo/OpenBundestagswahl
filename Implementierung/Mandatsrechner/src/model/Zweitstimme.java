package model;

import java.io.Serializable;

/**
 * Assoziationsklasse die Anzahl aller Zweitstimmen beinhaltet und mit den Klassen 
 * Deutschland und Partei zusammen arbeitet. Außerdem erbt diese von der Klasse Stimme.
 */
public class Zweitstimme extends Stimme implements Serializable {
	
	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -2753165575954824955L;
	
	/** Das zugehörige Gebiet. */
	private Gebiet gebiet;
	
	/** Die zugehörige Partei. */
	private Partei partei;
	
	
	/**
	 * Parametrisierter Konstruktor zum erzeugen von Zweitstimmen.
	 * @param anzahl Die Anzahl der Stimmen.
	 * @param gebiet Das zugehörige Gebiet.
	 * @param partei Die zugehörige Partei.
	 */
	public Zweitstimme(int anzahl, Gebiet gebiet, Partei partei) {
		this.setAnzahl(anzahl);
		this.setGebiet(gebiet);
		this.setPartei(partei);
	}
	
	/**
	 * Gibt die zugehörige Partei zurück.
	 * @return die zugehörige Partei
	 */
	public Partei getPartei() {
		return partei;
	}
	
	/**
	 * Setzt die zugehörige Partei dieser Zweitstimme.
	 * @param partei die zugehörige Partei
	 * @throws IllegalArgumentException wenn der Parameter partei null ist.
	 */
	public void setPartei(Partei partei) throws IllegalArgumentException {
		if (partei == null) {
			throw new IllegalArgumentException("Der Parameter \"partei\" ist null!");
		}
		this.partei = partei;
	}
	
	/**
	 * Gibt das zugehörige Gebiet zurück
	 * @return das zugehörige Gebiet
	 */
	public Gebiet getGebiet() {
		return gebiet;
	}
	
	/**
	 * Setzt das zugehörige Gebiet dieser Zweitstimme.
	 * @param gebiet das zugehörige Gebiet
	 * @throws IllegalArgumentException wenn der Parameter gebiet null ist.
	 */
	public void setGebiet(Gebiet gebiet) throws IllegalArgumentException {
		if (gebiet == null) {
			throw new IllegalArgumentException("Der Parameter \"gebiet\" ist null!");
		}
		this.gebiet = gebiet;
	}

}

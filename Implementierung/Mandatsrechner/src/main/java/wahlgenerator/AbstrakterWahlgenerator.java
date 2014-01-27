package main.java.wahlgenerator;

import java.util.List;

import main.java.model.Bundestagswahl;

/** 
 * Mit dieser Klasse können Bundestagswahl- Objekte anhand gegebener
 * Stimmanteile auf Bundesebene generiert werden.
 */
public abstract class AbstrakterWahlgenerator {

	/** Die Basiswahl mit relevanten Strukturdaten. */
	private Bundestagswahl basisWahl;
	
	/** Die Liste der Stimmanteile pro Partei. */
	private List<Stimmanteile> stimmanteile;
	
	/** Die Anzahl der Erststimmen aller Wahlberechtigten. */
	private Integer anzahlErststimmen;
	
	/** Die Anzahl der Zweitstimmen aller Wahlberechtigten. */
	private Integer anzahlZweitstimmen;
	
	
	/**
	 * Parametrisierter Konstruktor.
	 * 
	 * @param basisWahl Die Basiswahl mit relevanten Strukturdaten.
	 * @param stimmanteile Die Liste der Stimmanteile pro Partei.
	 */
	public AbstrakterWahlgenerator(Bundestagswahl basisWahl, List<Stimmanteile> stimmanteile) {
		this.basisWahl = basisWahl;
		this.stimmanteile = stimmanteile;
		
		berechneGesamtanzahlStimmen();
	}	
	
	/**
	 * Erzeugt eine neue Bundestagswahl auf der Grundlage der basisWahl und füllt diese
	 *
	 * @return generierte Bundestagswahl
	 */
	public Bundestagswahl erzeugeBTW() {
		// TODO
		return null;
	}
	
	/**
	 * Bestimmt anzahlZweitstimmen und anzahlErststimmen
	 */
	private void berechneGesamtanzahlStimmen() {
		
		this.setAnzahlErststimmen(
				this.basisWahl.getDeutschland().getAnzahlErststimmen());
		this.setAnzahlZweitstimmen(
				this.basisWahl.getDeutschland().getAnzahlZweitstimmen());
		
		//Debug.print("AnzahlErststimmen Gesamt DEUTSCHLAND: " + this.getAnzahlErststimmen());
		//Debug.print("AnzahlZweitstimmen Gesamt DEUTSCHLAND: " + this.getAnzahlZweitstimmen());
	}
	
	/**
	 * Diese Methode verteilt alle Erst- und Zweitstimmen auf die Wahlkreise
	 * der Bundestagswahl.
	 * @param btw Die Bundestagswahl auf diejenige die Stimmen verteilt werden sollen
	 */
	public abstract void verteileStimmen(Bundestagswahl btw);

	/**
	 * Gibt die Basiswahl mit relevanten Strukturdaten zurück.
	 * @return Die Basiswahl mit relevanten Strukturdaten.
	 */
	public Bundestagswahl getBasisWahl() {
		return basisWahl;
	}

	/**
	 * Setzt die Basiswahl mit relevanten Strukturdaten.
	 * @param basisWahl Die Basiswahl mit relevanten Strukturdaten.
	 * @throws IllegalArgumentException wenn der Parameter basisWahl null ist.
	 */
	public void setBasisWahl(Bundestagswahl basisWahl) throws IllegalArgumentException {
		if (basisWahl == null) {
			throw new IllegalArgumentException("Der Parameter \"basisWahl\" ist null!");
		}
		this.basisWahl = basisWahl;
	}

	/**
	 * Gibt die Liste der Stimmanteile pro Partei zurück.
	 * @return die Liste der Stimmanteile pro Partei.
	 */
	public List<Stimmanteile> getStimmanteile() {
		return stimmanteile;
	}

	/**
	 * Setzt die Liste der Stimmanteile pro Partei.
	 * @param stimmanteile Die Liste der Stimmanteile pro Partei.
	 * @throws IllegalArgumentException Wenn der Parameter stimmanteile leer oder null ist.
	 */
	public void setStimmanteile(List<Stimmanteile> stimmanteile) throws IllegalArgumentException {
		if (stimmanteile == null || stimmanteile.isEmpty()) {
			throw new IllegalArgumentException("Der Parameter \"stimmanteile\" ist leer oder null!");
		}
		this.stimmanteile = stimmanteile;
	}

	/**
	 * Gibt die Gesamtanzahl der Erststimmen zurück.
	 * @return die Gesamtzahl der Erststimmen
	 */
	public Integer getAnzahlErststimmen() {
		return anzahlErststimmen;
	}
	
	/**
	 * Setzt die Gesamtzahl der Erststimmen.
	 * @param anzahlErststimmen Die Gesamtzahl der Erststimmen.
	 * @throws IllegalArgumentException Wenn der Parameter anzahlErststimmen ist negativ ist.
	 */
	public void setAnzahlErststimmen(Integer anzahlErststimmen) throws IllegalArgumentException {
		if (anzahlErststimmen < 0) {
			throw new IllegalArgumentException("Der Parameter \"anzahlErststimmen\" ist negativ!");
		}
		this.anzahlErststimmen = anzahlErststimmen;
	}

	/**
	 * Gibt die Gesamtanzahl der Zweitstimmen zurück.
	 * @return die Gesamtzahl der Zweitstimmen
	 */
	public Integer getAnzahlZweitstimmen() {
		return anzahlZweitstimmen;
	}

	/**
	 * Setzt die Gesamtzahl der Zweitstimmen.
	 * @param anzahlZweitstimmen Die Gesamtzahl der Zweitstimmen.
	 * @throws IllegalArgumentException Wenn der Parameter anzahlZweitstimmen ist negativ ist.
	 */
	public void setAnzahlZweitstimmen(Integer anzahlZweitstimmen) throws IllegalArgumentException {
		if (anzahlZweitstimmen < 0) {
			throw new IllegalArgumentException("Der Parameter \"anzahlErststimmen\" ist negativ!");
		}
		this.anzahlZweitstimmen = anzahlZweitstimmen;
	}
	
}

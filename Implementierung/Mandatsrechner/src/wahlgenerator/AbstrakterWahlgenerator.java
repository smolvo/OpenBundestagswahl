/**
 * 
 */
package wahlgenerator;

import java.util.List;

import model.Bundestagswahl;

/** 
 * 
 * Mit dieser Klasse können Bundestagswahl- Objekte anhand gegebener
 * Stimmanteile auf Bundesebene generiert werden
 *
 * @author Manuel
 *
 */
public abstract class AbstrakterWahlgenerator {

	/** Die Basiswahl, mit relevanten Strukturdaten */
	private Bundestagswahl basisWahl;
	
	/** Die vom Benutzer gegebenen Stimmanteile */
	private List<Stimmanteile> stimmanteile;
	
	/** Die Anzahl der Erststimmen aller Wahlberechtigten */
	private Integer anzahlErststimmen;
	
	/*TODO
	/bin mir nicht ganz sicher, was mit anzahlZweistimmen bzw
	 anzahlErststimmen gemeint ist
	*/
	/** Die Anzahl der Zweitstimmen aller Wahlberechtigten */
	private Integer anzahlZweitstimmen;
	
	/**
	 * Der Konstruktor dieser Klasse
	 * @param basisWahl
	 * @param stimmanteile
	 */
	public AbstrakterWahlgenerator(Bundestagswahl basisWahl, List<Stimmanteile> stimmanteile)  {
		this.basisWahl = basisWahl;
		this.stimmanteile = stimmanteile;
		
		berechneGesamtanzahlStimmen();
	}
	
	
	/**
	 * Berechnet anzahlZweitstimmen und anzahlErststimmen
	 * Hierzu werden die Stimmanteile mithilfe der Anzahl aller Wahlberechtiger
	 * in absolute Zahlen umgerechnet
	 */
	private void berechneGesamtanzahlStimmen() {
		//TODO
	}
	
	
	/**
	 * Erzeugt eine neue Bundestagswahl auf der Grundlage der basisWahl und füllt diese
	 *
	 * @return erstlle Bundestagswahl
	 */
	public Bundestagswahl erzeugeBTW() {
		//TODO
		return null;
	}
	
	/**
	 * Diese Methode verteilt alle Erst- und Zweitstimmen auf die Wahlkreise
	 * der Bundestagswahl.
	 */
	public abstract void verteileStimmen();
}

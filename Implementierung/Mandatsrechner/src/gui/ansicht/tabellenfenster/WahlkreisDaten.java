package gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import model.Erststimme;
import model.Zweitstimme;

/**
 * Diese Klasse repräsentiert die Daten der Wahlkreisansichtstabelle.
 * 
 */
public class WahlkreisDaten {

	/** Liste der Parteien */
	private LinkedList<String> parteien;

	/** Liste der Namen der Kandidaten */
	private LinkedList<String> kandidaten;

	/** Liste der Zweitstimmen */
	private LinkedList<Zweitstimme> zweitstimmen;

	/** Liste der Erststimmen */
	private LinkedList<Erststimme> erststimmen;

	/** Liste der prozentualen Zweitstimmen */
	private LinkedList<String> zweitProzent;

	/** Liste der prozentualen Erststimmen */
	private LinkedList<String> erstProzent;

	/** Liste der Direktmandate */
	private LinkedList<Boolean> direktmandate;

	/**
	 * Der Konstruktor initialisiert alle Listen.
	 */
	public WahlkreisDaten() {
		this.parteien = new LinkedList<String>();
		this.kandidaten = new LinkedList<String>();
		this.zweitstimmen = new LinkedList<Zweitstimme>();
		this.erststimmen = new LinkedList<Erststimme>();
		this.zweitProzent = new LinkedList<String>();
		this.erstProzent = new LinkedList<String>();
		this.direktmandate = new LinkedList<Boolean>();
	}

	/**
	 * Diese Methode fügt eine gesamte Zeile der Tabelle hinzu.
	 * 
	 * @param partei
	 *            Partei
	 * @param name
	 *            Kandidatenname
	 * @param zweitStimme
	 *            Zweitstimmenanzahl
	 * @param erstStimme
	 *            Erststimmenanzahl
	 * @param zweitProzent
	 *            prozentuale Zweitstimmenanzahl
	 * @param erstProzent
	 *            prozentuale Erststimmenanzahl
	 * @param direktmandat
	 *            ob Direktmandat
	 */
	public void addZeile(String partei, String name, Zweitstimme zweitStimme,
			Erststimme erstStimme, String zweitProzent, String erstProzent,
			Boolean direktmandat) {
		if (zweitStimme != null) {
			this.zweitstimmen.add(zweitStimme);
		}
		if (erstStimme != null) {
			this.erststimmen.add(erstStimme);
		}
		stringCheck(partei, this.parteien);
		stringCheck(name, this.kandidaten);
		stringCheck(zweitProzent, this.zweitProzent);
		stringCheck(erstProzent, this.erstProzent);
		this.direktmandate.add(direktmandat);
	}

	/**
	 * Diese Methode überprüft, ob ein String null ist, wenn nicht wird es der
	 * Liste angehangen,
	 * 
	 * @param string
	 *            String
	 * @param list
	 *            Liste
	 */
	private void stringCheck(String string, LinkedList<String> list) {
		if (string != null) {
			list.add(string);
		} else {
			list.add("-");
		}
	}

	/**
	 * Gibt eine bestimmte Partei zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Partei
	 */
	public String getParteiName(int index) {
		return parteien.get(index);
	}

	/**
	 * Gibt eine Kandidatennamen zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Kandidat
	 */
	public String getKandidatName(int index) {
		return kandidaten.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Zweitstimme
	 */
	public Zweitstimme getZweitstimmen(int index) {
		return zweitstimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Erststimmenanzahl zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Zweitstimme
	 */
	public Erststimme getErststimmen(int index) {
		return erststimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenprozentanzahl zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Prozentanzahl
	 */
	public String getZweitprozent(int index) {
		return zweitProzent.get(index);
	}

	/**
	 * Gibt eine bestimmte Erststimmenprozentanzahl zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Prozentanzahl
	 */
	public String getErstprozent(int index) {
		return erstProzent.get(index);
	}

	/**
	 * Gibt an ob ein Kandidat ein Direktmandat hat oder nicht.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Direktmandate
	 */
	public Boolean getDirektmandate(int index) {
		return direktmandate.get(index);
	}

	/**
	 * Diese Methode gibt die Anzahl an Zeilen aus.
	 * 
	 * @return Zeilenanzahl
	 */
	public int size() {
		return this.parteien.size();
	}
}

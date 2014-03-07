package main.java.gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import main.java.model.Erststimme;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse reprï¿½sentiert die Daten der Wahlkreisansichtstabelle.
 * 
 */
public class WahlkreisDaten {

	/** Liste der Parteien */
	private final LinkedList<String> parteien;

	/** Liste der Namen der Kandidaten */
	private final LinkedList<String> kandidaten;

	/** Liste der Zweitstimmen */
	private final LinkedList<Zweitstimme> zweitstimmen;

	/** Liste der Erststimmen */
	private final LinkedList<Erststimme> erststimmen;

	/** Liste der prozentualen Zweitstimmen */
	private final LinkedList<String> zweitProzent;

	/** Liste der prozentualen Erststimmen */
	private final LinkedList<String> erstProzent;

	/** Liste der Direktmandate */
	private final LinkedList<String> direktmandate;

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
		this.direktmandate = new LinkedList<String>();
	}

	/**
	 * Diese Methode fï¿½gt eine gesamte Zeile der Tabelle hinzu.
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
			String direktmandat) {
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
	 * Gibt an ob ein Kandidat ein Direktmandat hat oder nicht.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengrï¿½ï¿½e.
	 * @return Direktmandate
	 */
	public String getDirektmandate(int index) {
		if (index < 0 || index > this.direktmandate.size()) {
			throw new IllegalArgumentException(
					"Index auï¿½erhalb Listengrï¿½ï¿½e.");
		}
		return this.direktmandate.get(index);
	}

	/**
	 * Gibt eine bestimmte Erststimmenprozentanzahl zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengrï¿½ï¿½e.
	 * @return Prozentanzahl
	 */
	public String getErstprozent(int index) {
		if (index < 0 || index > this.erstProzent.size()) {
			throw new IllegalArgumentException(
					"Index auï¿½erhalb Listengrï¿½ï¿½e.");
		}
		return this.erstProzent.get(index);
	}

	/**
	 * Gibt eine bestimmte Erststimmenanzahl zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengroeï¿½e.
	 * @return Zweitstimme
	 */
	public Erststimme getErststimmen(int index) {
		if (index < 0 || index > this.erststimmen.size()) {
			throw new IllegalArgumentException(
					"Index auï¿½erhalb Listengrï¿½ï¿½e.");
		}
		return this.erststimmen.get(index);
	}

	/**
	 * Gibt eine Kandidatennamen zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengroeï¿½e ist.
	 * @return Kandidat
	 */
	public String getKandidatName(int index) {
		if (index < 0 || index > this.kandidaten.size()) {
			throw new IllegalArgumentException(
					"Index auï¿½erhalb Listengrï¿½ï¿½e.");
		}
		return this.kandidaten.get(index);
	}

	/**
	 * Gibt eine bestimmte Partei zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengrï¿½ï¿½en.
	 * @return Partei
	 */
	public String getParteiName(int index) {
		if (index < 0 || index > this.parteien.size()) {
			throw new IllegalArgumentException(
					"Index auï¿½erhalb Listengrï¿½ï¿½e.");
		}
		return this.parteien.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenprozentanzahl zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengrï¿½ï¿½e.
	 * @return Prozentanzahl
	 */
	public String getZweitprozent(int index) {
		if (index < 0 || index > this.zweitProzent.size()) {
			throw new IllegalArgumentException(
					"Index auï¿½erhalb Listengrï¿½ï¿½e.");
		}
		return this.zweitProzent.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengrï¿½ï¿½e ist.
	 * @return Zweitstimme
	 */
	public Zweitstimme getZweitstimmen(int index) {
		if (index < 0 || index > this.zweitstimmen.size()) {
			throw new IllegalArgumentException(
					"Index auï¿½erhalb Listengrï¿½ï¿½e.");
		}
		return this.zweitstimmen.get(index);
	}

	/**
	 * Diese Methode gibt die Anzahl an Zeilen aus.
	 * 
	 * @return Zeilenanzahl
	 */
	public int size() {
		return this.parteien.size();
	}

	/**
	 * Diese Methode ï¿½berprï¿½ft, ob ein String null ist, wenn nicht wird es
	 * der Liste angehangen,
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
}

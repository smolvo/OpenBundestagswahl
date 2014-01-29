package main.java.gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import main.java.model.Erststimme;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse repr�sentiert die Daten der Wahlkreisansichtstabelle.
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
	private LinkedList<String> direktmandate;

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
	 * Diese Methode f�gt eine gesamte Zeile der Tabelle hinzu.
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
	 * Diese Methode �berpr�ft, ob ein String null ist, wenn nicht wird es der
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
	 * Gibt eine bestimmte Partei zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index au�erhalb der Listengr��en.
	 * @return Partei
	 */
	public String getParteiName(int index) {
		if (index < 0 || index > parteien.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return parteien.get(index);
	}

	/**
	 * Gibt eine Kandidatennamen zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index au�erhalb der Listengroe�e ist.
	 * @return Kandidat
	 */
	public String getKandidatName(int index) {
		if (index < 0 || index > kandidaten.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return kandidaten.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index au�erhalb der Listengr��e ist.
	 * @return Zweitstimme
	 */
	public Zweitstimme getZweitstimmen(int index) {
		if (index < 0 || index > zweitstimmen.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return zweitstimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Erststimmenanzahl zur�ck.
	 * 
	 * @param index
	 *            Listenindex 
	 * @throws IllegalArgumentException wenn der Index au�erhalb der Listengroe�e.
	 * @return Zweitstimme
	 */
	public Erststimme getErststimmen(int index) {
		if (index < 0 || index > erststimmen.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return erststimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenprozentanzahl zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException wenn der Index au�erhalb der Listengr��e.
	 * @return Prozentanzahl
	 */
	public String getZweitprozent(int index) {
		if (index < 0 || index > zweitProzent.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return zweitProzent.get(index);
	}

	/**
	 * Gibt eine bestimmte Erststimmenprozentanzahl zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException wenn der Index au�erhalb der Listengr��e.
	 * @return Prozentanzahl
	 */
	public String getErstprozent(int index) {
		if (index < 0 || index > erstProzent.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return erstProzent.get(index);
	}

	/**
	 * Gibt an ob ein Kandidat ein Direktmandat hat oder nicht.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException wenn der Index au�erhalb der Listengr��e.
	 * @return Direktmandate
	 */
	public String getDirektmandate(int index) {
		if (index < 0 || index > direktmandate.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
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

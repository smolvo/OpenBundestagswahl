package main.java.gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import main.java.model.Zweitstimme;

/**
 * Diese Klasse repräsentiert die Daten der Bundesansichtstabelle.
 * 
 */
public class BundDaten {

	/** Liste der Parteien */
	private LinkedList<String> parteien;

	/** Liste der Zweitstimmen */
	private LinkedList<Zweitstimme> stimmen;

	/** Liste der prozentualen Zweitstimmen */
	private LinkedList<String> prozent;

	/** Liste der Sitze */
	private LinkedList<String> sitze;

	/** Liste der Direktmandate */
	private LinkedList<String> direktmandate;

	/** Liste der Überhangsmandate */
	private LinkedList<String> ueberhangsmandate;

	/** Liste der Ausgleichsmandate */
	private LinkedList<String> ausgleichsmandate;

	/**
	 * Der Konstruktor initialisiert alle Listen.
	 */
	public BundDaten() {
		this.parteien = new LinkedList<String>();
		this.stimmen = new LinkedList<Zweitstimme>();
		this.prozent = new LinkedList<String>();
		this.sitze = new LinkedList<String>();
		this.direktmandate = new LinkedList<String>();
		this.ueberhangsmandate = new LinkedList<String>();
		this.ausgleichsmandate = new LinkedList<String>();
	}

	/**
	 * Diese Methode fügt eine gesamte Zeile der Tabelle hinzu.
	 * 
	 * @param partei
	 *            die Parte
	 * @param stimme
	 *            ihre Zweitstimmenanzahl
	 * @param prozent
	 *            ihre prozentuale Zweitstimmenanzahl
	 * @param sitze
	 *            erhaltene Sitze
	 * @param direktmandat
	 *            Anzahl Direktkandidaten
	 * @param ueberhangsmandat
	 *            Anzahl Überhangsmandate
	 * @param ausgleichsmandat
	 *            Anzahl Ausgleichsmandate
	 */
	public void addZeile(String partei, Zweitstimme stimme, String prozent,
			String sitze, String direktmandat, String ueberhangsmandat,
			String ausgleichsmandat) {
		if (stimme != null) {
			this.stimmen.add(stimme);
		}
		stringCheck(partei, this.parteien);
		stringCheck(prozent, this.prozent);
		stringCheck(sitze, this.sitze);
		stringCheck(direktmandat, this.direktmandate);
		stringCheck(ueberhangsmandat, this.ueberhangsmandate);
		stringCheck(ausgleichsmandat, this.ausgleichsmandate);
	}

	/**
	 * Diese Methode überprüft, ob ein String null ist, wenn nicht wird er der
	 * Liste angehangen,
	 * 
	 * @param string
	 *            String
	 * @param list
	 *            Liste
	 * @throws IllegalArgumentException
	 *             wenn die Liste null ist.
	 */
	private void stringCheck(String string, LinkedList<String> list) {
		if (list == null) {
			throw new IllegalArgumentException("Liste ist null");
		}
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
	 * @throws IllegalArgumentException
	 *             wenn der Index außerhalb der Listengroeße ist.
	 * @return Partei die benoetigte Partei
	 */
	public String getParteien(int index) {
		if (index < 0 || index > parteien.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return parteien.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index außerhalb der Listengroeße ist.
	 * @return Zweitstimme
	 */
	public Zweitstimme getStimmen(int index) {
		if (index < 0 || index > stimmen.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return stimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Prozentanzahl zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index außerhalb der Listengroeße ist.
	 * @return Prozentanzahl
	 */
	public String getProzent(int index) {
		if (index < 0 || index > prozent.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return prozent.get(index);
	}

	/**
	 * Gibt eine bestimmte Sitzanzahl zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index außerhalb der Listengroeße ist.
	 * @return Sitzanzahl
	 */
	public String getSitze(int index) {
		if (index < 0 || index > sitze.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return sitze.get(index);
	}

	/**
	 * Gibt eine bestimmte Anzahl Direktmandate zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index außerhalb der Listengroeße ist.
	 * @return Direktmandate
	 */
	public String getDirektmandate(int index) {
		if (index < 0 || index > direktmandate.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return direktmandate.get(index);
	}

	/**
	 * Gibt eine bestimmte Überhangsmandate zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index außerhalb der Listengroeße ist.
	 * @return Überhangsmandate
	 */
	public String getUeberhangsmandate(int index) {
		if (index < 0 || index > ueberhangsmandate.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return ueberhangsmandate.get(index);
	}

	/**
	 * Gibt eine bestimmte Ausgleichsmandate zurück.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index außerhalb der Listengroeße ist.
	 * @return Ausgleichsmandate die benoetigte Ausgleichsmandate
	 */
	public String getAusgleichsmandate(int index) {
		if (index < 0 || index > ausgleichsmandate.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return ausgleichsmandate.get(index);
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

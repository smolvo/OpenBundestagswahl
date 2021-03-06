package main.java.gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import main.java.model.Zweitstimme;

/**
 * Diese Klasse reprï¿½sentiert die Daten der Landesansichtstabelle.
 * 
 */
public class LandDaten {

	/** Liste der Parteien */
	private final LinkedList<String> parteien;

	/** Liste der Zweitstimmen */
	private final LinkedList<Zweitstimme> stimmen;

	/** Liste der prozentualen Zweitstimmen */
	private final LinkedList<String> prozent;

	/** Liste der Direktmandate */
	private final LinkedList<String> direktmandate;

	/** Liste der ï¿½berhangsmandate */
	private final LinkedList<String> ueberhangsmandate;

	/**
	 * Der Konstruktor initialisiert alle Listen.
	 */
	public LandDaten() {
		this.parteien = new LinkedList<String>();
		this.stimmen = new LinkedList<Zweitstimme>();
		this.prozent = new LinkedList<String>();
		this.direktmandate = new LinkedList<String>();
		this.ueberhangsmandate = new LinkedList<String>();
	}

	/**
	 * Diese Methode fï¿½gt eine gesamte Zeile der Tabelle hinzu.
	 * 
	 * @param partei
	 *            die Parte
	 * @param stimme
	 *            ihre Zweitstimmenanzahl
	 * @param prozent
	 *            ihre prozentuale Zweitstimmenanzahl
	 * @param direktmandat
	 *            Anzahl Direktkandidaten
	 * @param ueberhangsmandat
	 *            Anzahl ï¿½berhangsmandate
	 * @throws IllegalArgumentException
	 *             wenn das Zweitstimmen-Objekt null ist.
	 */
	public void addZeile(String partei, Zweitstimme stimme, String prozent,
			String direktmandat, String ueberhangsmandat) {
		if (stimme == null) {
			throw new IllegalArgumentException("Zweitstimmen ist null.");
		}
		this.stimmen.add(stimme);
		stringCheck(partei, this.parteien);
		stringCheck(prozent, this.prozent);
		stringCheck(direktmandat, this.direktmandate);
		stringCheck(ueberhangsmandat, this.ueberhangsmandate);
	}

	/**
	 * Gibt eine bestimmte Anzahl Direktmandate zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengroeï¿½e ist.
	 * @return Direktmandate
	 */
	public String getDirektmandate(int index) {
		if (index < 0 || index > this.direktmandate.size()) {
			throw new IllegalArgumentException("Index ausserhalb Listengrösse.");
		}
		return this.direktmandate.get(index);
	}

	/**
	 * Gibt eine bestimmte Partei zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengroeï¿½e ist.
	 * @return Partei
	 */
	public String getParteien(int index) {
		if (index < 0 || index > this.parteien.size()) {
			throw new IllegalArgumentException("Index ausserhalb Listengrösse.");
		}
		return this.parteien.get(index);
	}

	/**
	 * Gibt eine bestimmte Prozentanzahl zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengroeï¿½e ist.
	 * @return Prozentanzahl
	 */
	public String getProzent(int index) {
		if (index < 0 || index > this.prozent.size()) {
			throw new IllegalArgumentException("Index ausserhalb Listengrösse.");
		}
		return this.prozent.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengroeï¿½e ist.
	 * @return Zweitstimme
	 */
	public Zweitstimme getStimmen(int index) {
		if (index < 0 || index > this.stimmen.size()) {
			throw new IllegalArgumentException("Index ausserhalb Listengrösse.");
		}
		return this.stimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte ï¿½berhangsmandate zurï¿½ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @throws IllegalArgumentException
	 *             wenn der Index auï¿½erhalb der Listengroeï¿½e ist.
	 * @return ï¿½berhangsmandate
	 */
	public String getUeberhangsmandate(int index) {
		if (index < 0 || index > this.ueberhangsmandate.size()) {
			throw new IllegalArgumentException("Index ausserhalb Listengrösse.");
		}
		return this.ueberhangsmandate.get(index);
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
	 * @throws wenn
	 *             die Liste null ist.
	 */
	private void stringCheck(String string, LinkedList<String> list) {
		if (list == null) {
			throw new IllegalArgumentException("Liste ist null.");
		}
		if (string != null) {
			list.add(string);
		} else {
			list.add("-");
		}
	}
}

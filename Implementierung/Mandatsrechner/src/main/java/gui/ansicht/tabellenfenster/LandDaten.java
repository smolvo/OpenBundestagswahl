package main.java.gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import main.java.model.Zweitstimme;

/**
 * Diese Klasse repr�sentiert die Daten der Landesansichtstabelle.
 * 
 */
public class LandDaten {

	/** Liste der Parteien */
	private LinkedList<String> parteien;

	/** Liste der Zweitstimmen */
	private LinkedList<Zweitstimme> stimmen;

	/** Liste der prozentualen Zweitstimmen */
	private LinkedList<String> prozent;

	/** Liste der Direktmandate */
	private LinkedList<String> direktmandate;

	/** Liste der �berhangsmandate */
	private LinkedList<String> ueberhangsmandate;

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
	 * Diese Methode f�gt eine gesamte Zeile der Tabelle hinzu.
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
	 *            Anzahl �berhangsmandate
	 */
	public void addZeile(String partei, Zweitstimme stimme, String prozent,
			String direktmandat, String ueberhangsmandat) {
		if (stimme != null) {
			this.stimmen.add(stimme);
		}
		stringCheck(partei, this.parteien);
		stringCheck(prozent, this.prozent);
		stringCheck(direktmandat, this.direktmandate);
		stringCheck(ueberhangsmandat, this.ueberhangsmandate);
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
	 * @return Partei
	 */
	public String getParteien(int index) {
		if (index < 0 || index > parteien.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return parteien.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Zweitstimme
	 */
	public Zweitstimme getStimmen(int index) {
		if (index < 0 || index > stimmen.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return stimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Prozentanzahl zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Prozentanzahl
	 */
	public String getProzent(int index) {
		if (index < 0 || index > prozent.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return prozent.get(index);
	}

	/**
	 * Gibt eine bestimmte Anzahl Direktmandate zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @return Direktmandate
	 */
	public String getDirektmandate(int index) {
		if (index < 0 || index > direktmandate.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return direktmandate.get(index);
	}

	/**
	 * Gibt eine bestimmte �berhangsmandate zur�ck.
	 * 
	 * @param index
	 *            Listenindex
	 * @return �berhangsmandate
	 */
	public String getUeberhangsmandate(int index) {
		if (index < 0 || index > ueberhangsmandate.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return ueberhangsmandate.get(index);
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

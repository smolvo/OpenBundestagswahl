package gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import model.Zweitstimme;

/**
 * Diese Klasse repräsentiert die Daten der Landesansichtstabelle.
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
	
	/** Liste der Überhangsmandate */
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
	 * Diese Methode fügt eine gesamte Zeile der Tabelle hinzu.
	 * @param partei die Parte
	 * @param stimme ihre Zweitstimmenanzahl
	 * @param prozent ihre prozentuale Zweitstimmenanzahl
	 * @param direktmandat Anzahl Direktkandidaten
	 * @param ueberhangsmandat Anzahl Überhangsmandate
	 */
	public void addPartei(String partei, Zweitstimme stimme, String prozent, String direktmandat,
			String ueberhangsmandat) {
		if (partei != null) {
			this.parteien.add(partei);
		} else {
			this.parteien.add("-");
		}
		if (stimme != null) {
			this.stimmen.add(stimme);
		}
		if (prozent != null) {
			this.prozent.add(prozent);
		} else {
			this.prozent.add("-");
		}
		if (direktmandat != null) {
			this.direktmandate.add(direktmandat);
		} else {
			this.direktmandate.add("-");
		}
		if (ueberhangsmandat != null) {
			this.ueberhangsmandate.add(ueberhangsmandat);
		} else {
			this.ueberhangsmandate.add("-");
		}
	}
	
	/**
	 * Gibt eine bestimmte Partei zurück.
	 * @param index Listenindex
	 * @return Partei
	 */
	public String getParteien(int index) {
		return parteien.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zurück.
	 * @param index Listenindex
	 * @return Zweitstimme
	 */
	public Zweitstimme getStimmen(int index) {
		return stimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Prozentanzahl zurück.
	 * @param index Listenindex
	 * @return Prozentanzahl
	 */
	public String getProzent(int index) {
		return prozent.get(index);
	}

	/**
	 * Gibt eine bestimmte Anzahl Direktmandate zurück.
	 * @param index Listenindex
	 * @return Direktmandate
	 */
	public String getDirektmandate(int index) {
		return direktmandate.get(index);
	}

	/**
	 * Gibt eine bestimmte Überhangsmandate zurück.
	 * @param index Listenindex
	 * @return Überhangsmandate
	 */
	public String getUeberhangsmandate(int index) {
		return ueberhangsmandate.get(index);
	}
	
	/**
	 * Diese Methode gibt die Anzahl an Zeilen aus.
	 * @return Zeilenanzahl
	 */
	public int size() {
		return this.parteien.size();
	}
}

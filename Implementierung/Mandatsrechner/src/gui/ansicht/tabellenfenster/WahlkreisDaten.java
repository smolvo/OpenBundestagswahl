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
		this.zweitstimmen = new LinkedList<Zweitstimme>();
		this.erststimmen = new LinkedList<Erststimme>();
		this.zweitProzent = new LinkedList<String>();
		this.erstProzent = new LinkedList<String>();
		this.direktmandate = new LinkedList<Boolean>();
	}
	
	/**
	 * Diese Methode fügt eine gesamte Zeile der Tabelle hinzu.
	 * @param partei die Parte
	 * @param stimme ihre Zweitstimmenanzahl
	 * @param prozent ihre prozentuale Zweitstimmenanzahl
	 * @param direktmandat Anzahl Direktkandidaten
	 * @param ueberhangsmandat Anzahl Überhangsmandate
	 */
	public void addParteien(String partei, Zweitstimme zweitStimme, Erststimme erstStimme, 
			String zweitProzent, String erstProzent, Boolean direktmandat) {
		if (partei != null) {
			this.parteien.add(partei);
		} else {
			this.parteien.add("-");
		}
		if (zweitStimme != null) {
			this.zweitstimmen.add(zweitStimme);
		}
		if (erstStimme != null) {
			this.erststimmen.add(erstStimme);
		}
		if (zweitProzent != null) {
			this.zweitProzent.add(zweitProzent);
		} else {
			this.zweitProzent.add("-");
		}
		if (erstProzent != null) {
			this.erstProzent.add(erstProzent);
		} else {
			this.erstProzent.add("-");
		}
		if (direktmandat != null) {
			this.direktmandate.add(direktmandat);
		} else {
			this.direktmandate.add(false);
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
	public Zweitstimme getZweitstimmen(int index) {
		return zweitstimmen.get(index);
	}
	
	/**
	 * Gibt eine bestimmte Erststimmenanzahl zurück.
	 * @param index Listenindex
	 * @return Zweitstimme
	 */
	public Erststimme getErststimmen(int index) {
		return erststimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenprozentanzahl zurück.
	 * @param index Listenindex
	 * @return Prozentanzahl
	 */
	public String getZweitprozent(int index) {
		return zweitProzent.get(index);
	}
	
	/**
	 * Gibt eine bestimmte Erststimmenprozentanzahl zurück.
	 * @param index Listenindex
	 * @return Prozentanzahl
	 */
	public String getErstprozent(int index) {
		return erstProzent.get(index);
	}

	/**
	 * Gibt an ob ein Kandidat ein Direktmandat hat oder nicht.
	 * @param index Listenindex
	 * @return Direktmandate
	 */
	public Boolean getDirektmandate(int index) {
		return direktmandate.get(index);
	}
	
	/**
	 * Diese Methode gibt die Anzahl an Zeilen aus.
	 * @return Zeilenanzahl
	 */
	public int size() {
		return this.parteien.size();
	}
}

package gui.ansicht.tabellenfenster;

import java.util.LinkedList;

import model.Zweitstimme;

/**
 * Diese Klasse repr�sentiert die Daten der Bundesansichtstabelle.
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
	
	/** Liste der �berhangsmandate */
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
	 * Diese Methode f�gt eine gesamte Zeile der Tabelle hinzu.
	 * @param partei die Parte
	 * @param stimme ihre Zweitstimmenanzahl
	 * @param prozent ihre prozentuale Zweitstimmenanzahl
	 * @param sitze erhaltene Sitze
	 * @param direktmandat Anzahl Direktkandidaten
	 * @param ueberhangsmandat Anzahl �berhangsmandate
	 * @param ausgleichsmandat Anzahl Ausgleichsmandate
	 */
	public void addPartei(String partei, Zweitstimme stimme, String prozent, String sitze, String direktmandat,
			String ueberhangsmandat, String ausgleichsmandat) {
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
		if (sitze != null) {
			this.sitze.add(sitze);
		} else {
			this.sitze.add("-");
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
		if (ausgleichsmandat != null) {
			this.ausgleichsmandate.add(ausgleichsmandat);
		} else {
			this.ausgleichsmandate.add("-");
		}
	}
	
	/**
	 * Gibt eine bestimmte Partei zur�ck.
	 * @param index Listenindex
	 * @return Partei
	 */
	public String getParteien(int index) {
		return parteien.get(index);
	}

	/**
	 * Gibt eine bestimmte Zweitstimmenanzahl zur�ck.
	 * @param index Listenindex
	 * @return Zweitstimme
	 */
	public Zweitstimme getStimmen(int index) {
		return stimmen.get(index);
	}

	/**
	 * Gibt eine bestimmte Prozentanzahl zur�ck.
	 * @param index Listenindex
	 * @return Prozentanzahl
	 */
	public String getProzent(int index) {
		return prozent.get(index);
	}
	
	/**
	 * Gibt eine bestimmte Sitzanzahl zur�ck.
	 * @param index Listenindex
	 * @return Sitzanzahl
	 */
	public String getSitze(int index) {
		return sitze.get(index);
	}

	/**
	 * Gibt eine bestimmte Anzahl Direktmandate zur�ck.
	 * @param index Listenindex
	 * @return Direktmandate
	 */
	public String getDirektmandate(int index) {
		return direktmandate.get(index);
	}

	/**
	 * Gibt eine bestimmte �berhangsmandate zur�ck.
	 * @param index Listenindex
	 * @return �berhangsmandate
	 */
	public String getUeberhangsmandate(int index) {
		return ueberhangsmandate.get(index);
	}
	
	/**
	 * Gibt eine bestimmte Ausgleichsmandate zur�ck.
	 * @param index Listenindex
	 * @return Ausgleichsmandate
	 */
	public String getAusgleichsmandate(int index) {
		return ausgleichsmandate.get(index);
	}
	
	/**
	 * Diese Methode gibt die Anzahl an Zeilen aus.
	 * @return Zeilenanzahl
	 */
	public int size() {
		return this.parteien.size();
	}
}

package gui;

/**
 * Diese Klasse hält alle Dateien die eine Partei betreffen,
 * um diese im Tabellenfenster anzeigen zu lassen.
 *
 */
public class GUIPartei {

	/** repräsentiert den Namen der Partei */
	private String name;

	/** repräsentiert die Zweitstimmen der Partei */
	private int zweitStimme;

	/** repräsentiert die Erststimmen der Partei */
	private int erstStimme;

	/** repräsentiert prozentualen Zweitstimmen der Partei */
	private double prozentualeZweit;
	
	/** repräsentiert prozentualen Erststimmen der Partei */
	private double prozentualeErst;

	/** repräsentiert die Sitze der Partei */
	private int sitze;

	/** repräsentiert die Direktmandaten der Partei */
	private int direktmandate;

	/** repräsentiert die Überhangsmandaten der Partei */
	private int ueberhangsmandate;

	/** repräsentiert die Ausgleichsmandate der Partei */
	private int ausgleichsmandate;
	
	/** zeigt an ob ein Kandidat ein Direktmandat hat */
	private boolean hatDirektmandat;

	/**
	 * Konstruktor
	 * @param name Name
	 * @param zweitStimme Anzahl Zweitstimmen
	 * @param erstStimme Anzahl Erstimmen
	 * @param prozentualeZweit prozentuale Anzahl Zweitstimmen
	 * @param prozentualeErst prozentuale Anzahl Erststimmen
	 * @param sitze Anzahl Sitze
	 * @param direktmandate Anzahl Direktmandate
	 * @param ueberhangsmandate Anzahl Überhangsmandate
	 * @param ausgleichsmandate Anzahl Ausgleichsmandate
	 * @param hatDirektmandat mandat
	 */
	public GUIPartei(String name, int zweitStimme, int erstStimme,
			double prozentualeZweit, double prozentualeErst, int sitze,
			int direktmandate, int ueberhangsmandate, int ausgleichsmandate,
			boolean hatDirektmandat) {
		this.name = name;
		this.zweitStimme = zweitStimme;
		this.erstStimme = erstStimme;
		this.prozentualeZweit = prozentualeZweit;
		this.prozentualeErst = prozentualeErst;
		this.sitze = sitze;
		this.direktmandate = direktmandate;
		this.ueberhangsmandate = ueberhangsmandate;
		this.ausgleichsmandate = ausgleichsmandate;
		this.hatDirektmandat = hatDirektmandat;
	}
	
	/**
	 * Gibt den Namen aus.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen.
	 * @return name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gibt die Zweitstimmenanzahl aus.
	 * @return Zweitstimmenanzahl
	 */
	public int getZweitStimme() {
		return zweitStimme;
	}

	/**
	 *  Setzt die Zweitstimmenanzahl.
	 * @param zweitStimme anzahl
	 */
	public void setZweitStimme(int zweitStimme) {
		this.zweitStimme = zweitStimme;
	}

	/**
	 * Gibt die Erststimmenanzahl aus.
	 * @return Erststimmenanzahl
	 */
	public int getErstStimme() {
		return erstStimme;
	}

	/**
	 * Setzt die Anzahl an Erststimmen.
	 * @param erstStimme Anzahl
	 */
	public void setErstStimme(int erstStimme) {
		this.erstStimme = erstStimme;
	}

	/**
	 * Gibt die prozentuale Zweitstimmenanzahl aus.
	 * @return prozentuale Zweitstimmenanzahl
	 */
	public double getProzentualeZweit() {
		return prozentualeZweit;
	}

	/**
	 * Setzt die prozentuale Zweitstimmenanzahl.
	 * @param prozentualeZweit Anzahl
	 */
	public void setProzentualeZweit(double prozentualeZweit) {
		this.prozentualeZweit = prozentualeZweit;
	}

	/**
	 * Gibt die prozentuale Erststimmenanzahl aus.
	 * @return prozentuale Erststimmenanzahl
	 */
	public double getProzentualeErst() {
		return prozentualeErst;
	}

	/**
	 * Setzt die prozentuale Erststimmenanzahl.
	 * @param prozentualeErst Anzahl
	 */
	public void setProzentualeErst(double prozentualeErst) {
		this.prozentualeErst = prozentualeErst;
	}

	/**
	 * Gibt die Sitzanzahl aus.
	 * @return Sitzanzahl
	 */
	public int getSitze() {
		return sitze;
	}

	/**
	 * Setzt die Anzahl der Sitze.
	 * @param sitze Anzahl
	 */
	public void setSitze(int sitze) {
		this.sitze = sitze;
	}

	/**
	 * Gibt die Direktmandatanzahl aus.
	 * @return Direktmandatanzahl
	 */
	public int getDirektmandate() {
		return direktmandate;
	}

	/**
	 * Setzt die Anzahl an Direktmandaten.
	 * @param direktmandate Anzahl
	 */
	public void setDirektmandate(int direktmandate) {
		this.direktmandate = direktmandate;
	}

	/**
	 * Gibt die Überhangsmandatanzahl aus.
	 * @return Überhangsmandatanzahl
	 */
	public int getUeberhangsmandate() {
		return ueberhangsmandate;
	}

	/**
	 * Setzt die Anzahl an Überhangsmandaten.
	 * @param ueberhangsmandate Anzahl
	 */
	public void setUeberhangsmandate(int ueberhangsmandate) {
		this.ueberhangsmandate = ueberhangsmandate;
	}

	/**
	 * Gibt die Ausgleichsmandatanzahl aus.
	 * @return Ausgleichsmandatanzahl
	 */
	public int getAusgleichsmandate() {
		return ausgleichsmandate;
	}

	/**
	 * Setzt die Anzahl an Ausgleichsmandaten.
	 * @param ausgleichsmandate Anzahl
	 */
	public void setAusgleichsmandate(int ausgleichsmandate) {
		this.ausgleichsmandate = ausgleichsmandate;
	}

	/**
	 * Gibt an, ob ein Kandidat ein Direktmandat hat.
	 * @return true false
	 */
	public boolean isHatDirektmandat() {
		return hatDirektmandat;
	}

	/**
	 * Setzt ein Direktmandat fest.
	 * @param hatDirektmandat ob oder nicht
	 */
	public void setHatDirektmandat(boolean hatDirektmandat) {
		this.hatDirektmandat = hatDirektmandat;
	}
}

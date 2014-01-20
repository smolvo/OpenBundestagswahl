package gui;

import java.util.List;

import model.Stimme;

/**
 * Diese Klasse hält alle Dateien die eine Partei betreffen, um diese im
 * Tabellenfenster anzeigen zu lassen.
 * 
 */
public class GUIPartei {

	/** repräsentiert die Sitze der Partei */
	private int sitze;

	/** repräsentiert die Direktmandaten der Partei */
	private int direktmandate;

	/** repräsentiert die Überhangsmandaten der Partei */
	private int ueberhangsmandate;

	/** repräsentiert die Ausgleichsmandate der Partei */
	private int ausgleichsmandate;

	/**
	 * Konstruktor
	 * 
	 * @param sitze
	 *            Anzahl Sitze
	 * @param direktmandate
	 *            Anzahl Direktmandate
	 * @param ueberhangsmandate
	 *            Anzahl Überhangsmandate
	 * @param ausgleichsmandate
	 *            Anzahl Ausgleichsmandate
	 */
	public GUIPartei(int sitze, int direktmandate, int ueberhangsmandate,
			int ausgleichsmandate) {
		this.sitze = sitze;
		this.direktmandate = direktmandate;
		this.ueberhangsmandate = ueberhangsmandate;
		this.ausgleichsmandate = ausgleichsmandate;
	}

	/**
	 * Gibt die Sitzanzahl aus.
	 * 
	 * @return Sitzanzahl
	 */
	public int getSitze() {
		return sitze;
	}

	/**
	 * Setzt die Anzahl der Sitze.
	 * 
	 * @param sitze
	 *            Anzahl
	 */
	public void setSitze(int sitze) {
		this.sitze = sitze;
	}

	/**
	 * Gibt die Direktmandatanzahl aus.
	 * 
	 * @return Direktmandatanzahl
	 */
	public int getDirektmandate() {
		return direktmandate;
	}

	/**
	 * Setzt die Anzahl an Direktmandaten.
	 * 
	 * @param direktmandate
	 *            Anzahl
	 */
	public void setDirektmandate(int direktmandate) {
		this.direktmandate = direktmandate;
	}

	/**
	 * Gibt die Überhangsmandatanzahl aus.
	 * 
	 * @return Überhangsmandatanzahl
	 */
	public int getUeberhangsmandate() {
		return ueberhangsmandate;
	}

	/**
	 * Setzt die Anzahl an Überhangsmandaten.
	 * 
	 * @param ueberhangsmandate
	 *            Anzahl
	 */
	public void setUeberhangsmandate(int ueberhangsmandate) {
		this.ueberhangsmandate = ueberhangsmandate;
	}

	/**
	 * Gibt die Ausgleichsmandatanzahl aus.
	 * 
	 * @return Ausgleichsmandatanzahl
	 */
	public int getAusgleichsmandate() {
		return ausgleichsmandate;
	}

	/**
	 * Setzt die Anzahl an Ausgleichsmandaten.
	 * 
	 * @param ausgleichsmandate
	 *            Anzahl
	 */
	public void setAusgleichsmandate(int ausgleichsmandate) {
		this.ausgleichsmandate = ausgleichsmandate;
	}
}

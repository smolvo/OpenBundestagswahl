package gui;

import java.util.List;

import model.Stimme;

/**
 * Diese Klasse h�lt alle Dateien die eine Partei betreffen,
 * um diese im Tabellenfenster anzeigen zu lassen.
 *
 */
public class GUIPartei {

	/** repr�sentiert prozentualen Zweitstimmen der Partei */
	private double prozentualeZweit;

	/** repr�sentiert die Sitze der Partei */
	private int sitze;

	/** repr�sentiert die Direktmandaten der Partei */
	private int direktmandate;

	/** repr�sentiert die �berhangsmandaten der Partei */
	private int ueberhangsmandate;

	/** repr�sentiert die Ausgleichsmandate der Partei */
	private int ausgleichsmandate;

	/**
	 * Konstruktor
	 * @param prozentualeZweit prozentuale Anzahl Zweitstimmen
	 * @param sitze Anzahl Sitze
	 * @param direktmandate Anzahl Direktmandate
	 * @param ueberhangsmandate Anzahl �berhangsmandate
	 * @param ausgleichsmandate Anzahl Ausgleichsmandate
	 */
	public GUIPartei(double prozentualeZweit, int sitze, int direktmandate, 
			int ueberhangsmandate, int ausgleichsmandate) {
		this.prozentualeZweit = prozentualeZweit;
		this.sitze = sitze;
		this.direktmandate = direktmandate;
		this.ueberhangsmandate = ueberhangsmandate;
		this.ausgleichsmandate = ausgleichsmandate;
	}
	
	/**
	 * Diese Methode gibt eine prozentuale Stimmenanzahl aus.
	 * @param stimme stimme im Verh�ltnis zu
	 * @param stimmen allen Stimmen
	 * @return prozentualer Anteil
	 */
	public double prozBerechnen(Stimme stimme, List<Stimme> stimmen) {
		int gesamt = 0;
		for (Stimme st : stimmen) {
			gesamt += st.getAnzahl();
		}
		return (Math.rint(((double) stimme.getAnzahl() / (double) gesamt) * 1000) / 10);
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
	 * Gibt die �berhangsmandatanzahl aus.
	 * @return �berhangsmandatanzahl
	 */
	public int getUeberhangsmandate() {
		return ueberhangsmandate;
	}

	/**
	 * Setzt die Anzahl an �berhangsmandaten.
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
}

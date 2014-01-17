package gui;

import model.Erststimme;
import model.Partei;
import model.Zweitstimme;

/**
 * Diese Klasse hält alle Dateien die einen Kandidaten betreffen,
 * um diese im Tabellenfenster anzeigen zu lassen.
 *
 */
public class GUIKandidat {

	/** repräsentiert die Partei */
	private String partei;
	
	/** repräsentiert den Namen der Partei des Kandidaten */
	private String name;
	
	/** repräsentiert die Anzahl Erststimmen */
	private Erststimme erststimmen;
	
	/** repräsentiert die prozentuale Anzahl Erststimmen */
	private double prozErst;
	
	/** repräsentiert die Anzahl Zweitstimmen */
	private Zweitstimme zweitstimme;
	
	/** repräsentiert die prozentuale Anzahl Zweitstimmen */
	private double prozZweit;
	
	/** zeigt an ob der Kandidat ein Direktmandat hat */
	private boolean direktman;

	/**
	 * Konstruktor definiert ein neues Objekt.
	 * @param partei Parteiname
	 * @param name Kandidatenname
	 * @param erststimmen Erststimmenanzahl
	 * @param prozErst prozentual
	 * @param zweitstimme Zweitstimmenanzahl
	 * @param prozZweit prozentual
	 * @param direktman ob Direktmandat
	 */
	public GUIKandidat(String partei, String name, Erststimme erststimmen, double prozErst,
			Zweitstimme zweitstimme, double prozZweit, boolean direktman) {
		this.partei = partei;
		this.name = name;
		this.erststimmen = erststimmen;
		this.prozErst = prozErst;
		this.zweitstimme = zweitstimme;
		this.prozZweit = prozZweit;
		this.direktman = direktman;
	}
	
	/**
	 * Gibt die Partei aus.
	 * @return partei
	 */
	public String getPartei() {
		return partei;
	}
	
	/**
	 * Setzt die Partei des Kandidaten.
	 * @param partei Partei
	 */
	public void setPartei(Partei partei) {
		this.partei = partei.getName();
	}
	
	/**
	 * Gibt den Namen aus.
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen.
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gibt die Anzahl Erststimmen aus.
	 * @return Anzahl
	 */
	public Erststimme getErststimmen() {
		return erststimmen;
	}

	/**
	 * Setzt die Anzahl Erststimmen.
	 * @param erststimmen Anzahl
	 */
	public void setErststimmen(Erststimme erststimmen) {
		this.erststimmen = erststimmen;
	}

	/**
	 * Gibt die prozentuale Anzahl Erststimmen aus.
	 * @return prozentuale Anzahl
	 */
	public double getProzErst() {
		return prozErst;
	}

	/**
	 * Setzt die Anzahl prozentualer Erststimmen.
	 * @param prozErst Anzahl
	 */
	public void setProzErst(double prozErst) {
		this.prozErst = prozErst;
	}

	/**
	 * Gibt die Anzahl prozentualer Zweitstimmen aus.
	 * @return Anzahl
	 */
	public Zweitstimme getZweitstimme() {
		return zweitstimme;
	}

	/**
	 * Setzt die Anzahl prozentualer Zweitstimmen.
	 * @param zweitstimme Anzahl
	 */
	public void setZweitstimme(Zweitstimme zweitstimme) {
		this.zweitstimme = zweitstimme;
	}

	/**
	 * Gibt die prozentuale Anzahl Zweitstimmen aus.
	 * @return Anzahl
	 */
	public double getProzZweit() {
		return prozZweit;
	}

	/**
	 * Setzt die prozentuale Anzahl Zweitstimmen.
	 * @param prozZweit Anzahl
	 */
	public void setProzZweit(double prozZweit) {
		this.prozZweit = prozZweit;
	}

	/**
	 * Gibt Direktmandat aus.
	 * @return true false
	 */
	public boolean isDirektman() {
		return direktman;
	}

	/**
	 * Setzt Direktmandat.
	 * @param direktman true false
	 */
	public void setDirektman(boolean direktman) {
		this.direktman = direktman;
	}
}

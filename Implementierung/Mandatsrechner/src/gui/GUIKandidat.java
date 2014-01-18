package gui;

import java.util.List;

import model.Erststimme;
import model.Partei;
import model.Stimme;
import model.Zweitstimme;

/**
 * Diese Klasse hält alle Dateien die einen Kandidaten betreffen,
 * um diese im Tabellenfenster anzeigen zu lassen.
 *
 */
public class GUIKandidat {
	
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
	 * @param prozErst prozentual
	 * @param zweitstimme Zweitstimmenanzahl
	 * @param prozZweit prozentual
	 * @param direktman ob Direktmandat
	 */
	public GUIKandidat(double prozErst,
			Zweitstimme zweitstimme, double prozZweit, boolean direktman) {
		this.prozErst = prozErst;
		this.zweitstimme = zweitstimme;
		this.prozZweit = prozZweit;
		this.direktman = direktman;
	}
	
	/**
	 * Diese Methode gibt eine prozentuale Stimmenanzahl aus.
	 * @param stimme stimme im Verhältnis zu
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

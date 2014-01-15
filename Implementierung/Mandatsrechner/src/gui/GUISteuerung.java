package gui;

import model.Bundestagswahl;
import model.Gebiet;

public class GUISteuerung {
	
	private Bundestagswahl btw;
	
	private WahlFenster wahlfenster;

	public GUISteuerung(Bundestagswahl btw, WahlFenster wahlfenster) {
		this.btw = btw;
		this.wahlfenster = wahlfenster;
	}
	
	/**
	 * Gibt die Bundestagswahl aus.
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBtw() {
		return btw;
	}
	
	/**
	 * Setzt die Bundestagswahl.
	 * @param btw Bundestagswahl
	 */
	public void setBtw(Bundestagswahl btw) {
		this.btw = btw;
	}

	/**
	 * 
	 * @return
	 */
	public WahlFenster getWahlfenster() {
		return wahlfenster;
	}

	public void setWahlfenster(WahlFenster wahlfenster) {
		this.wahlfenster = wahlfenster;
	}
	
	public void aktualisiereWahlfenster(Gebiet gebiet) {
		this.wahlfenster.wechsleAnsicht(gebiet);
	}
}

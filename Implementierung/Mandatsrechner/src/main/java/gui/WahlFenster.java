package main.java.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.gui.ansicht.Ansicht;
import main.java.model.Bundestagswahl;
import main.java.model.Gebiet;

/**
 * Diese Klasse repräsentiert die allgemeine Darstellung einer Wahl Sie setzt
 * sich aus den drei Ansichten Bundesansicht, Landesansicht, Wahlkreisansicht
 * zusammen
 * 
 * @author Manuel
 * 
 */
public class WahlFenster extends JPanel {

	private static final long serialVersionUID = -6169514140570698059L;

	/** repräsentiert den Namen des Tabs */
	private final String name;

	/** repräsentiert die geladene Bundestagswahl */
	private final Bundestagswahl btw;

	/** repräsentiert die aktuelle Ansicht */
	private Ansicht aktuelleAnsicht;

	/** repräsentiert die aktuelle Steuerung des Wahlfensters */
	private final GUISteuerung steuerung;

	/**
	 * repräsentiert das zuletzt gezeigte Gebiet, so dass zurück gesprungen
	 * werden kann
	 */
	// private Ansicht letzteAnsicht;

	/**
	 * der Konstruktor der Klase
	 * 
	 * @param btw
	 *            Bundestagswahl
	 */
	public WahlFenster(Bundestagswahl btw) {
		this.btw = btw;
		this.name = btw.getName();
		this.steuerung = new GUISteuerung(btw, this);
		this.aktuelleAnsicht = new Ansicht(btw.getDeutschland(), this);
		setLayout(new BorderLayout());
		this.add(aktuelleAnsicht, BorderLayout.CENTER);
	}

	/**
	 * Wechselt die Ansicht.
	 * 
	 * @param gebiet
	 *            neues Gebiet
	 */
	public void wechsleAnsicht(Gebiet gebiet) {
		this.aktuelleAnsicht.ansichtAendern(gebiet);
		this.add(aktuelleAnsicht);
	}
	
	/**
	 * Gibt den Namen aus.
	 * 
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die GUISteuerung aus.
	 * 
	 * @return GUI Steuerung
	 */
	public GUISteuerung getSteuerung() {
		return steuerung;
	}

	/**
	 * Gibt die Bundestagswahl aus.
	 * 
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBtw() {
		return btw;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}

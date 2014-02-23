package main.java.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import main.java.gui.ansicht.Ansicht;
import main.java.model.Bundestagswahl;
import main.java.model.Gebiet;

/**
 * Diese Klasse reprï¿½sentiert die allgemeine Darstellung einer Wahl Sie setzt
 * sich aus den drei Ansichten Bundesansicht, Landesansicht, Wahlkreisansicht
 * zusammen
 * 
 * @author Manuel
 * 
 */
public class WahlFenster extends JPanel {

	private static final long serialVersionUID = -6169514140570698059L;

	/** reprï¿½sentiert den Namen des Tabs */
	private final String name;

	/** reprï¿½sentiert die geladene Bundestagswahl */
	private final Bundestagswahl btw;

	/** reprï¿½sentiert die aktuelle Ansicht */
	private Ansicht aktuelleAnsicht;

	/** reprï¿½sentiert die aktuelle Steuerung des Wahlfensters */
	private final GUISteuerung steuerung;

	/** reprï¿½sentiert das aktuelle Programmfenster */
	private final Programmfenster pf;

	/**
	 * reprï¿½sentiert das zuletzt gezeigte Gebiet, so dass zurï¿½ck gesprungen
	 * werden kann
	 */
	// private Ansicht letzteAnsicht;

	/**
	 * der Konstruktor der Klase
	 * 
	 * @param pf
	 *            Programmfenster
	 * @param btw
	 *            Bundestagswahl
	 * @throws IllegalArgumentException
	 *             wenn die Bundestagswahl null ist.
	 */
	public WahlFenster(Bundestagswahl btw, Programmfenster pf) {
		if (btw == null) {
			throw new IllegalArgumentException("Bundestagswahl ist null.");
		}
		this.pf = pf;
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
	 * @throws IllegalArgumentException
	 *             wenn das Gebiet null ist.
	 */
	public void wechsleAnsicht(Gebiet gebiet) {
		if (gebiet == null) {
			throw new IllegalArgumentException("Gebiet ist null.");
		}
		this.aktuelleAnsicht.ansichtAendern(gebiet);
		this.add(aktuelleAnsicht);
	}

	/**
	 * Gibt den Namen aus.
	 * 
	 * @return Name
	 */
	@Override
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

	/**
	 * Gibt das Programmfenster
	 * 
	 * @return the pf
	 */
	public Programmfenster getPf() {
		return pf;
	}

}

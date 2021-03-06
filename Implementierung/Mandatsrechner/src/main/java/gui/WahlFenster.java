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
	private final Ansicht aktuelleAnsicht;

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
		this.aktuelleAnsicht = new Ansicht(btw, this);
		setLayout(new BorderLayout());
		this.add(this.aktuelleAnsicht, BorderLayout.CENTER);
	}

	/**
	 * Gibt die Ansicht aus.
	 * 
	 * @return aktuelle Ansicht
	 */
	public Ansicht getAnsicht() {
		return this.aktuelleAnsicht;
	}

	/**
	 * Gibt die Bundestagswahl aus.
	 * 
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBtw() {
		return this.btw;
	}

	/**
	 * Gibt den Namen aus.
	 * 
	 * @return Name
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt das Programmfenster
	 * 
	 * @return the pf
	 */
	public Programmfenster getPf() {
		return this.pf;
	}

	/**
	 * Gibt die GUISteuerung aus.
	 * 
	 * @return GUI Steuerung
	 */
	public GUISteuerung getSteuerung() {
		return this.steuerung;
	}

	@Override
	public String toString() {
		return this.name;
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
		this.add(this.aktuelleAnsicht);
	}

}

package main.java.gui.ansicht.diagrammfenster;

import javax.swing.JPanel;

import main.java.gui.BerichtTableModel;
import main.java.gui.BerichtsFenster;
import main.java.gui.ansicht.Ansicht;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Gebiet;
import main.java.model.Wahlkreis;

/**
 * Diese Klasse repräsentiert das Diagrammfenster einer Ansicht. In diesem
 * werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * 
 */
public class DiagrammFenster extends JPanel {

	private static final long serialVersionUID = -523400111719339965L;

	/** repräsentiert die Ansicht des Diagrammfensters */
	private final Ansicht ansicht;

	/**
	 * Der Konstruktor initialisiert ein neues Diagrammfenster.
	 * 
	 * @param ansicht
	 *            die Ansicht
	 */
	public DiagrammFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}

	/**
	 * Diese Methode identifiziert das Gebiets-Objekt.
	 * 
	 * @param gebiet
	 *            Gebiet-Objekt welches visualisiert werden soll
	 * @throw NullPointerException
	 */
	public void erstelleDiagramm(Gebiet gebiet) {
		if (gebiet == null) {
			throw new NullPointerException("Kein Gebiet gefunden.");
		}
		if (gebiet instanceof Deutschland) {
			Deutschland land = (Deutschland) gebiet;
			erstelleDiagramm(land);
		} else if (gebiet instanceof Bundesland) {
			Bundesland bundLand = (Bundesland) gebiet;
			erstelleDiagramm(bundLand);
		} else {
			Wahlkreis wk = (Wahlkreis) gebiet;
			erstelleDiagramm(wk);
		}
	}

	/**
	 * Erstellt das Diagramm der Bundesansicht.
	 * 
	 * @param land
	 *            Deutschland
	 */
	public void erstelleDiagramm(Deutschland land) {
		new BundDiagramm(land, this);
	}

	/**
	 * Erstellt das Diagramm der Landesansicht.
	 * 
	 * @param bundLand
	 *            das Budnesland-Objekt
	 * @throws IllegalArgumentException
	 *             wenn das Bundesland-Objekt null ist.
	 */
	public void erstelleDiagramm(Bundesland bundLand) {
		if (bundLand == null) {
			throw new IllegalArgumentException("Bundesland ist null.");
		}
		new LandDiagramm(bundLand, this);
	}

	/**
	 * Erstellt das Diagramm der Wahlkreisansicht.
	 * 
	 * @param wk
	 *            Wahlkreis-Objekt
	 * @throws IllegalArgumentException
	 *             wenn das Wahlkreis-Objekt null ist.
	 * 
	 */
	public void erstelleDiagramm(Wahlkreis wk) {
		if (wk == null) {
			throw new IllegalArgumentException("Wahlkreis ist null.");
		}
		new WahlkreisDiagramm(wk, this);
	}

	/**
	 * Diese Methode öffnet ein BerichtsFenster, in dem die Sitze der Verteilung
	 * näher erläutert werden.
	 * 
	 * @param btw
	 *            Bundestagswahl-Objekt welches visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Bundestagswahl-Objekt null ist.
	 */
	public void zeigeSitzverteilung(Bundestagswahl btw) {
		if (btw == null) {
			throw new IllegalArgumentException("Keine Bundestagswahl gefunden.");
		}
		BerichtTableModel tabelle = new BerichtTableModel(btw
				.getSitzverteilung().getBericht());
		new BerichtsFenster(tabelle);
	}

	/**
	 * Gibts die Ansicht aus.
	 * 
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}

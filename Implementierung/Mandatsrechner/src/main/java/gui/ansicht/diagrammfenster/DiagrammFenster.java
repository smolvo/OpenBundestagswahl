package main.java.gui.ansicht.diagrammfenster;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import main.java.gui.BerichtTableModel;
import main.java.gui.BerichtsFenster;
import main.java.gui.VergleichsFenster;
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
 * @author Anton
 * 
 */
public class DiagrammFenster extends JPanel {

	/** repräsentiert die Ansicht des Diagrammfensters */
	private Ansicht ansicht;
	
	/**
	 * Der Konstruktor erzeugt initialisiert ein neues Diagrammfenster.
	 * @param ansicht die Ansicht
	 */
	public DiagrammFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}
	
	/**
	 * Diese Methode identifiziert das Gebiets-Objekt.
	 * 
	 * @param gebiet
	 *            Gebiet-Objekt welches visualisiert werden soll
	 */
	public void erstelleDiagramm(Gebiet gebiet) {

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
		BundDiagramm dia = new BundDiagramm(land, this);
	}

	/**
	 * Erstellt das Diagramm der Landesansicht.
	 * 
	 * @param land
	 *            Deutschland
	 */
	public void erstelleDiagramm(Bundesland bundLand) {
		LandDiagramm dia = new LandDiagramm(bundLand, this);
	}

	/**
	 * Erstellt das Diagramm der Wahlkreisansicht.
	 * 
	 * @param land
	 *            Deutschland
	 */
	public void erstelleDiagramm(Wahlkreis wk) {
		WahlkreisDiagramm dia = new WahlkreisDiagramm(wk, this);
	}

	/**
	 * Diese Methode öffnet ein BerichtsFenster, in dem die Sitze der Verteilung
	 * näher erläutert werden.
	 * 
	 * @param btw
	 *            Bundestagswahl-Objekt welches visualisiert werden soll
	 */
	public void zeigeSitzverteilung(Bundestagswahl btw) {
		BerichtTableModel tabelle = new BerichtTableModel(btw.getSitzverteilung().getBericht());
		BerichtsFenster bericht = new BerichtsFenster(tabelle);
	}
	
	/**
	 * Gibts die Ansicht aus.
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}

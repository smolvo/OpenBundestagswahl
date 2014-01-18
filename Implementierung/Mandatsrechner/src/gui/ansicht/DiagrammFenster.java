package gui.ansicht;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.Bundesland;
import model.Bundestagswahl;
import model.Deutschland;
import model.Gebiet;
import model.Wahlkreis;

/**
 * Diese Klasse repr�sentiert das Diagrammfenster einer Ansicht.
 * In diesem werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * @author Anton
 *
 */
public class DiagrammFenster extends JPanel {

	/** repr�sentiert die Ansicht in der sich das Diagrammfenster befindet */
	private final Ansicht ansicht;
	
	/**
	 * Erstellt ein neues Diagrammfenster.
	 * @param ansicht die aktuelle Ansicht
	 */
	public DiagrammFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}
	
	/**
	 *  Diese Methode identifiziert das Gebiets-Objekt.
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
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
	 * @param land Deutschland
	 */
	public void erstelleDiagramm(Deutschland land) {
		BundDiagramm dia = new BundDiagramm(land);
		this.add(dia);
	}
	
	/**
	 * Erstellt das Diagramm der Landesansicht.
	 * @param land Deutschland
	 */
	public void erstelleDiagramm(Bundesland bundLand) {
		LandDiagramm dia = new LandDiagramm(bundLand);
		this.add(dia, BorderLayout.CENTER);
	}
	
	/**
	 * Erstellt das Diagramm der Wahlkreisansicht.
	 * @param land Deutschland
	 */
	public void erstelleDiagramm(Wahlkreis wk) {
		
	}
	
	/**
	 * Diese Methode �ffnet ein BerichtsFenster, in dem die Sitze der Verteilung
	 * n�her erl�utert werden.
	 * @param btw Bundestagswahl-Objekt welches visualisiert werden soll
	 */
	public void zeigeSitzverteilung(Bundestagswahl btw) {
		
	}
	
	/**
	 * Gibt die Ansicht aus.
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}

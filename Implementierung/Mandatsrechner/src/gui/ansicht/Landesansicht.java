package gui.ansicht;

import model.Bundesland;
import model.Bundestagswahl;
import model.Deutschland;
import model.Gebiet;

/**
 * Diese Klasse repräsentiert die Ansicht auf ein bestimmtes Bundesland.
 *
 */
public class Landesansicht extends Ansicht {

	
	/**
	 * Konstruktor initialisiert Fenster.
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
	 */
	public Landesansicht(Gebiet gebiet) {
		aktuellesGebiet = gebiet;
		Bundesland land = (Bundesland) gebiet;
		layoutSetzen();
		Bundesland bl = (Bundesland) gebiet;
		tabellenFenster.tabellenFuellen(bl);
		diagrammFenster.erstelleDiagramm(bl);
	}



}

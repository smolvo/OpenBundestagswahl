package gui.ansicht;

import model.Deutschland;
import model.Gebiet;
import model.Bundestagswahl;
import model.Wahlkreis;

/**
 * Diese Klasse repräsentiert die Ansicht auf einen bestimmten Wahlkreis.
 *
 */
public class Wahlkreisansicht extends Ansicht {

	
	/**
	 * Konstruktor initialisiert Fenster.
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
	 */
	public Wahlkreisansicht(Gebiet gebiet) {
		aktuellesGebiet = gebiet;
		Wahlkreis land = (Wahlkreis) gebiet;
		layoutSetzen();
		Wahlkreis wk = (Wahlkreis) gebiet;
		tabellenFenster.tabellenFuellen(wk);
		diagrammFenster.erstelleDiagramm(wk);
	}

}

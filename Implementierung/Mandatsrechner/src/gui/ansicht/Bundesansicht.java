package gui.ansicht;

import model.Bundesland;
import model.Bundestagswahl;
import model.Deutschland;
import model.Gebiet;


/**
 * Diese Klasse repräsentiert die Ansicht auf das gesamte Land.
 *
 */
public class Bundesansicht extends Ansicht {


	/**
	 * Konstruktor initialisiert Fenster.
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
	 */
	public Bundesansicht(Gebiet gebiet) {
		aktuellesGebiet = gebiet;
		Deutschland land = (Deutschland) gebiet;
		layoutSetzen();
		tabellenFenster.tabellenFuellen(land);
		diagrammFenster.erstelleDiagramm(land);
		kartenFenster.zeigeInformationen(land);
	}


	

}

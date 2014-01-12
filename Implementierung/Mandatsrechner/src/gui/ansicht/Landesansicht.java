package gui.ansicht;

import model.Bundesland;
import model.Bundestagswahl;
import model.Gebiet;

/**
 * Diese Klasse repräsentiert die Ansicht auf ein bestimmtes Bundesland.
 *
 */
public class Landesansicht extends Ansicht {

	/**
	 * Durch diese Methode werden, je nach Ansichtsart, Daten in den 
	 * drei verschiedenen Fenstern angezeigt.
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
	 */
	public void zeigeKomponenten(Gebiet gebiet) {
		Bundesland bl = (Bundesland) gebiet;
		tabellenFenster.tabellenFuellen(bl);
		diagrammFenster.erstelleDiagramm(bl);
	}

}

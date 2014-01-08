package gui;

import model.Gebiet;
import model.Bundestagswahl;

/**
 * Diese Klasse repräsentiert die Ansicht auf einen bestimmten Wahlkreis.
 *
 */
public class Wahlkreisansicht extends Ansicht {

	/**
	 * Durch diese Methode werden, je nach Ansichtsart, Daten in den 
	 * drei verschiedenen Fenstern angezeigt.
	 * @param gebiet Gebiets-Objekt welches visualisiert werden sollen
	 */
	public void zeigeKomponenten(Gebiet gebiet) {
		tabellenFenster.tabellenFuellen(gebiet);
		diagrammFenster.erstelleDiagramm(gebiet);
	}

}

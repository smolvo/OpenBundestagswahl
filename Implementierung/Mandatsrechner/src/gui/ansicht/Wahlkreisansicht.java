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
	 * Konstruktor initialiesiert Fenster.
	 * @param gebiet gebiet
	 */
	public Wahlkreisansicht(Gebiet gebiet) {
		zeigeKomponenten(gebiet);
	}
	
	/**
	 * Durch diese Methode werden, je nach Ansichtsart, Daten in den 
	 * drei verschiedenen Fenstern angezeigt.
	 * @param gebiet Gebiets-Objekt welches visualisiert werden sollen
	 */
	public void zeigeKomponenten(Gebiet gebiet) {
		Wahlkreis land = (Wahlkreis) gebiet;
		layoutSetzen();
		Wahlkreis wk = (Wahlkreis) gebiet;
		tabellenFenster.tabellenFuellen(wk);
		diagrammFenster.erstelleDiagramm(wk);
	}

}

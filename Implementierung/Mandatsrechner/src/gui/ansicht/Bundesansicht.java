package gui.ansicht;

import model.Bundestagswahl;
import model.Deutschland;
import model.Gebiet;


/**
 * Diese Klasse repräsentiert die Ansicht auf das gesamte Land.
 *
 */
public class Bundesansicht extends Ansicht {


	
	/**
	 * Durch diese Methode werden, je nach Ansichtsart, Daten in den 
	 * drei verschiedenen Fenstern angezeigt.
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
	 */
	public void zeigeKomponenten(Gebiet gebiet) {
		Deutschland land = (Deutschland) gebiet;
		layoutSetzen();
		tabellenFenster.tabellenFuellen(land);
		diagrammFenster.erstelleDiagramm(land);
		kartenFenster.zeigeInformationen(land);
	}

}

package gui;

import model.Bundestagswahl;
import model.Gebiet;


/**
 * Diese Klasse repräsentiert die Ansicht auf das gesamte Land.
 *
 */
public class Bundesansicht extends Ansicht {


	
	/**
	 * Durch diese Methode werden, je nach Ansichtsart, Daten in den 
	 * drei verschiedenen Fenstern angezeigt.
	 * @param btw Bundestagswahl-Objekt welches visualisiert werden soll
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
	 */
	public void zeigeKomponenten(Bundestagswahl btw, Gebiet gebiet) {
		tabellenFenster.BATabellenFuellen(btw);
		diagrammFenster.erstelleDiagramm(btw);
		kartenFenster.zeigeInformationen(btw);
	}

}

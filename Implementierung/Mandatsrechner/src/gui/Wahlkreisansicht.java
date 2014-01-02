package gui;

/**
 * Diese Klasse repräsentiert die Ansicht auf einen bestimmten Wahlkreis.
 *
 */
public class Wahlkreisansicht extends Ansicht {

	/**
	 * Dieser Konstruktor erstellt eine neue Wahlkreisansicht des Wahlfensters.
	 * @param tabellenFenster das Tabellenfenster der Ansicht
	 * @param diagrammFenster das Diagrammfenster der Ansicht
	 * @param kartenFenster das Kartenfenster der Ansicht
	 */
	public Wahlkreisansicht(TabellenFenster tabellenFenster,
			DiagrammFenster diagrammFenster, KartenFenster kartenFenster) {
		super(tabellenFenster, diagrammFenster, kartenFenster);
	}

}

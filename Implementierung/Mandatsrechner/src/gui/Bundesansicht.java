package gui;


/**
 * Diese Klasse repräsentiert die Ansicht auf das gesamte Land.
 *
 */
public class Bundesansicht extends Ansicht {

	/**
	 * Dieser Konstruktor erstellt eine neue Bundesansicht des Wahlfensters.
	 * @param tabellenFenster das Tabellenfenster der Ansicht
	 * @param diagrammFenster das Diagrammfenster der Ansicht
	 * @param kartenFenster das Kartenfenster der Ansicht
	 */
	public Bundesansicht(TabellenFenster tabellenFenster,
			DiagrammFenster diagrammFenster, KartenFenster kartenFenster) {
		super(tabellenFenster, diagrammFenster, kartenFenster);
	}

}

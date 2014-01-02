package gui;

/**
 * Diese Klasse repräsentiert die Ansicht auf ein bestimmtes Bundesland.
 *
 */
public class Landesansicht extends Ansicht {

	/**
	 * Dieser Konstruktor erstellt eine neue Landesansicht des Wahlfensters.
	 * @param tabellenFenster das Tabellenfenster der Ansicht
	 * @param diagrammFenster das Diagrammfenster der Ansicht
	 * @param kartenFenster das Kartenfenster der Ansicht
	 */
	public Landesansicht(TabellenFenster tabellenFenster,
			DiagrammFenster diagrammFenster, KartenFenster kartenFenster) {
		super(tabellenFenster, diagrammFenster, kartenFenster);
	}

}

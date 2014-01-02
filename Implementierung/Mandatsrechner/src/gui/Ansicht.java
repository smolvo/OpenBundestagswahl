package gui;

import model.Bundestagswahl;

/**
 * Die abstrakte Klasse Ansicht, der grafischen Benutzeroberfläche.
 * @author Batman
 *
 */
public abstract class Ansicht {

	/** Eine Ansicht hat ein Tabellenfenster. */
	private TabellenFenster tabellenFenster;
	
	/** Eine Ansicht hat ein Diagrammfenster. */
	private DiagrammFenster diagrammFenster;
	
	/** Eine Ansicht hat ein Kartenfenster. */
	private KartenFenster kartenFenster;
	
	/**
	 * Der Konstruktor der Klasse generiert eine neue Ansicht.
	 * @param tabellenFenster das Tabellenfenster der Ansicht
	 * @param diagrammFenster das Diagrammfenster der Ansicht
	 * @param kartenFenster das Kartenfenster der Ansicht
	 */
	public Ansicht(TabellenFenster tabellenFenster,
			DiagrammFenster diagrammFenster, KartenFenster kartenFenster) {
		this.tabellenFenster = tabellenFenster;
		this.diagrammFenster = diagrammFenster;
		this.kartenFenster = kartenFenster;
	}
	
	/**
	 * Durch diese Methode werden, je nach Ansichtsart, Daten in den 
	 * drei verschiedenen Fenstern angezeigt.
	 * @param btw Bundestagswahl-Objekt welches visualisiert werden soll
	 */
	public void zeigeKomponenten(Bundestagswahl btw) {
		tabellenFenster.tabellenFuellen(btw);
		diagrammFenster.erstelleDiagramm(btw);
		kartenFenster.zeigeInformationen(btw);
	}

	/**
	 * Holt das Tabellenfenster der Ansicht.
	 * @return aktuelles Tabellenfenster
	 */
	public TabellenFenster getTabellenFenster() {
		return tabellenFenster;
	}
	
	/**
	 * Setzt das Tabellenfenster der Ansicht.
	 * @param tabellenFenster neues Tabellenfenster
	 */
	public void setTabellenFenster(TabellenFenster tabellenFenster) {
		this.tabellenFenster = tabellenFenster;
	}

	/**
	 * Holt das Diagrammfenster der Ansicht.
	 * @return aktuelles Diagrammfenster
	 */
	public DiagrammFenster getDiagrammFenster() {
		return diagrammFenster;
	}

	/**
	 * Setzt das Diagrammfenster der Ansicht.
	 * @param diagrammFenster neues Diagrammfenster
	 */
	public void setDiagrammFenster(DiagrammFenster diagrammFenster) {
		this.diagrammFenster = diagrammFenster;
	}

	/**
	 * Holt das Kartenfenster der Ansicht.
	 * @return aktuelles Kartenfenster
	 */
	public KartenFenster getKartenFenster() {
		return kartenFenster;
	}

	/**
	 * Setzt das Kartenfenster der Ansicht.
	 * @param kartenFenster neues Kartenfenster
	 */
	public void setKartenFenster(KartenFenster kartenFenster) {
		this.kartenFenster = kartenFenster;
	}
}

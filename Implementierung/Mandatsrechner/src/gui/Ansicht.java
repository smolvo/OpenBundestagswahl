package gui;

import model.Bundestagswahl;
import model.Gebiet;

/**
 * Die abstrakte Klasse Ansicht, der grafischen Benutzeroberfläche.
 * @author Batman
 *
 */
public abstract class Ansicht {

	/** Eine Ansicht hat ein Tabellenfenster. */
	protected TabellenFenster tabellenFenster;
	
	/** Eine Ansicht hat ein Diagrammfenster. */
	protected DiagrammFenster diagrammFenster;
	
	/** Eine Ansicht hat ein Kartenfenster. */
	protected KartenFenster kartenFenster;
	
	
	/**
	 * Durch diese Methode werden, je nach Ansichtsart, Daten in den 
	 * drei verschiedenen Fenstern angezeigt.
	 * @param gebiet Gebiet-Objekt welches visualisiert werden soll
	 */
	public abstract void zeigeKomponenten(Gebiet gebiet);

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

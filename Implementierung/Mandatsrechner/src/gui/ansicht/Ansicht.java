package gui.ansicht;

import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.ansicht.tabellenfenster.TabellenFenster;
import model.Bundestagswahl;
import model.Gebiet;

/**
 * Die abstrakte Klasse Ansicht, der grafischen Benutzeroberfl�che.
 * @author Batman
 *
 */
public abstract class Ansicht extends JPanel {

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

	public void layoutSetzen() {
		//Allgemeine Anpassungen des Wahlfensters
				this.tabellenFenster = new TabellenFenster();
				//this.kartenFenster = new KartenFenster();
				//this.diagrammFenster = new DiagrammFenster();
				
				GridLayout linkeSpalteLay= new GridLayout(2,1,5, 5);
				GridLayout rechteSpalteLay= new GridLayout(1,1,5, 5);
				GridLayout gridLay = new GridLayout(1, 2, 5, 5);
				
				JPanel linkeSpalte = new JPanel();
				linkeSpalte.setLayout(linkeSpalteLay);
				JPanel rechteSpalte = new JPanel();
				rechteSpalte.setLayout(rechteSpalteLay);
				
				rechteSpalte.add(tabellenFenster);
				linkeSpalte.add(kartenFenster);
				linkeSpalte.add(diagrammFenster);

				this.setLayout(gridLay);
				this.add(linkeSpalte);
				this.add(rechteSpalte);

				this.setVisible(true);
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

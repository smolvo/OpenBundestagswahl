package gui.ansicht;

import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.ansicht.tabellenfenster.TabellenFenster;
import model.Bundesland;
import model.Bundestagswahl;
import model.Deutschland;
import model.Gebiet;
import model.Wahlkreis;

/**
 * Die abstrakte Klasse Ansicht, der grafischen Benutzeroberfläche.
 * @author Batman
 *
 */
public class Ansicht extends JPanel {

	/** Eine Ansicht hat ein Tabellenfenster. */
	protected TabellenFenster tabellenFenster;
	
	/** Eine Ansicht hat ein Diagrammfenster. */
	protected DiagrammFenster diagrammFenster;
	
	/** Eine Ansicht hat ein Kartenfenster. */
	protected KartenFenster kartenFenster;
	
	/** Das im Moment angezeigte Gebiet */
	protected Gebiet aktuellesGebiet;
	
	/**
	 * Der Konstruktor setzt eine neue Ansicht.
	 * @param gebiet Gebiet
	 */
	public Ansicht(Deutschland land) {
		layoutSetzen();
		kartenFenster.zeigeInformationen(land);
		tabellenFenster.tabellenFuellen(land);
		diagrammFenster.erstelleDiagramm(land);
	}
	
	public void ansichtAendern(Gebiet gebiet) {
		this.remove(diagrammFenster);
		this.remove(tabellenFenster);
		if (gebiet instanceof Deutschland) {
			aktuellesGebiet = gebiet;
			layoutSetzen();
			Deutschland land = (Deutschland) gebiet;
			tabellenFenster.tabellenFuellen(land);
			diagrammFenster.erstelleDiagramm(land);
		} else if (gebiet instanceof Bundesland) {
			aktuellesGebiet = gebiet;
			layoutSetzen();
			Bundesland bundLand = (Bundesland) gebiet;
			tabellenFenster.tabellenFuellen(bundLand);
			diagrammFenster.erstelleDiagramm(bundLand);
		} else {
			aktuellesGebiet = gebiet;
			layoutSetzen();
			Wahlkreis wk = (Wahlkreis) gebiet;
			tabellenFenster.tabellenFuellen(wk);
			diagrammFenster.erstelleDiagramm(wk);
		}
	}
	
	/**
	 * Durch diese Methode wird das allgemeine Layout der Ansichten gesetzt
	 */
	public void layoutSetzen() {
		
		//Allgemeine Anpassungen des Wahlfensters
		this.tabellenFenster = new TabellenFenster();
		this.kartenFenster = new KartenFenster();
		this.diagrammFenster = new DiagrammFenster();
			
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


	/**
	 * @return the aktuellesGebiet
	 */
	public Gebiet getAktuellesGebiet() {
		return aktuellesGebiet;
	}


	/**
	 * @param aktuellesGebiet the aktuellesGebiet to set
	 */
	public void setAktuellesGebiet(Gebiet aktuellesGebiet) {
		this.aktuellesGebiet = aktuellesGebiet;
	}
	
	
}

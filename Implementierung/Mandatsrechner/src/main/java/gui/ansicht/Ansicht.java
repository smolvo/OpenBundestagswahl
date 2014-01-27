package main.java.gui.ansicht;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import main.java.gui.WahlFenster;
import main.java.gui.ansicht.diagrammfenster.DiagrammFenster;
import main.java.gui.ansicht.tabellenfenster.TabellenFenster;
import main.java.model.Deutschland;
import main.java.model.Gebiet;

/**
 * Die abstrakte Klasse Ansicht, der grafischen Benutzeroberfläche.
 * 
 * @author Anton
 * 
 */
public class Ansicht extends JPanel {

	/** Eine Ansicht hat ein Tabellenfenster. */
	private TabellenFenster tabellenFenster;

	/** Eine Ansicht hat ein Diagrammfenster. */
	private DiagrammFenster diagrammFenster;

	/** Eine Ansicht hat ein Kartenfenster. */
	private KartenFenster kartenFenster;

	/** repräsentiert das Wahlfenster in welchem sich die Ansicht befindet */
	private final WahlFenster fenster;

	/** repräsentiert das Layout */
	private GridBagConstraints gbc;

	/** Das im Moment angezeigte Gebiet */
	private Gebiet aktuellesGebiet;

	/**
	 * Der Konstruktor setzt eine neue Ansicht.
	 * 
	 * @param land
	 *            Deutschland
	 * @param fenster
	 *            das Wahlfenster der Ansicht
	 */
	public Ansicht(Deutschland land, WahlFenster fenster) {
		this.fenster = fenster;
		this.tabellenFenster = new TabellenFenster(this);
		this.tabellenFenster.tabellenFuellen(land);
		this.diagrammFenster = new DiagrammFenster(this);
		this.diagrammFenster.erstelleDiagramm(land);
		this.kartenFenster = new KartenFenster(this);
		this.kartenFenster.zeigeInformationen(land);
		this.gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		initialisieren();
		this.setMinimumSize(new Dimension(1024, 768));
	}

	/**
	 * Diese Methode ändert die aktuelle Ansicht in eine neue.
	 * 
	 * @param gebiet
	 *            Gebiet, welches angezeigt werden soll
	 */
	public void ansichtAendern(Gebiet gebiet) {
		remove(tabellenFenster);
		remove(diagrammFenster);
		this.tabellenFenster = new TabellenFenster(this);
		this.tabellenFenster.tabellenFuellen(gebiet);
		this.diagrammFenster = new DiagrammFenster(this);
		this.diagrammFenster.erstelleDiagramm(gebiet);
		layoutSetzen();
	}

	/**
	 * Durch diese Methode wird das allgemeine Layout der Ansichten gesetzt
	 */
	private void layoutSetzen() {
		gbc.ipadx = 50;
		gbc.ipady = 50;
		
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagrammFenster, gbc);

		gbc.weightx = 1;
		gbc.weighty = 1.5;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = gbc.gridheight * 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(tabellenFenster, gbc);
	}

	/**
	 * Diese Methode wird vom Konstruktor verwendet, um das Kartenfenster zu
	 * setzen.
	 */
	private void initialisieren() {
		gbc.ipadx = 50;
		gbc.ipady = 50;
		
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		add(kartenFenster, gbc);

		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagrammFenster, gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(tabellenFenster, gbc);
	}

	/**
	 * Holt das Tabellenfenster der Ansicht.
	 * 
	 * @return aktuelles Tabellenfenster
	 */
	public TabellenFenster getTabellenFenster() {
		return tabellenFenster;
	}

	/**
	 * Setzt das Tabellenfenster der Ansicht.
	 * 
	 * @param tabellenFenster
	 *            neues Tabellenfenster
	 */
	public void setTabellenFenster(TabellenFenster tabellenFenster) {
		this.tabellenFenster = tabellenFenster;
	}

	/**
	 * Holt das Diagrammfenster der Ansicht.
	 * 
	 * @return aktuelles Diagrammfenster
	 */
	public DiagrammFenster getDiagrammFenster() {
		return diagrammFenster;
	}

	/**
	 * Setzt das Diagrammfenster der Ansicht.
	 * 
	 * @param diagrammFenster
	 *            neues Diagrammfenster
	 */
	public void setDiagrammFenster(DiagrammFenster diagrammFenster) {
		this.diagrammFenster = diagrammFenster;
	}

	/**
	 * Holt das Kartenfenster der Ansicht.
	 * 
	 * @return aktuelles Kartenfenster
	 */
	public KartenFenster getKartenFenster() {
		return kartenFenster;
	}

	/**
	 * Setzt das Kartenfenster der Ansicht.
	 * 
	 * @param kartenFenster
	 *            neues Kartenfenster
	 */
	public void setKartenFenster(KartenFenster kartenFenster) {
		this.kartenFenster = kartenFenster;
	}

	/**
	 * Gibt das Wahlfenster aus.
	 * 
	 * @return Wahlfenster
	 */
	public WahlFenster getFenster() {
		return fenster;
	}

	/**
	 * @return the aktuellesGebiet
	 */
	public Gebiet getAktuellesGebiet() {
		return aktuellesGebiet;
	}

	/**
	 * @param aktuellesGebiet
	 *            the aktuellesGebiet to set
	 */
	public void setAktuellesGebiet(Gebiet aktuellesGebiet) {
		this.aktuellesGebiet = aktuellesGebiet;
	}
}

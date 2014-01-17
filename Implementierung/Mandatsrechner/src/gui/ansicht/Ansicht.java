package gui.ansicht;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.WahlFenster;
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
	private TabellenFenster tabellenFenster;
	
	/** Eine Ansicht hat ein Diagrammfenster. */
	private DiagrammFenster diagrammFenster;
	
	/** Eine Ansicht hat ein Kartenfenster. */
	private KartenFenster kartenFenster;
	
	/** repräsentiert das Wahlfenster in welchem sich die Ansicht befindet */
	private WahlFenster fenster;
	
	/** repräsentiert das Layout */
	private GridBagConstraints gbc;
	
	/** Das im Moment angezeigte Gebiet */
	private Gebiet aktuellesGebiet;
	
	/**
	 * Der Konstruktor setzt eine neue Ansicht.
	 * @param gebiet Gebiet
	 */
	public Ansicht(Deutschland land, WahlFenster fenster) {
		this.tabellenFenster = new TabellenFenster();
		this.tabellenFenster.tabellenFuellen(land);
		this.diagrammFenster = new DiagrammFenster();
		this.diagrammFenster.erstelleDiagramm(land);
		this.kartenFenster = new KartenFenster();
		this.kartenFenster.zeigeInformationen(land);
		this.fenster = fenster;
		this.gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		layoutSetzen();
	}
	
	/**
	 * Diese Methode ändert die aktuelle Ansicht in eine neue.
	 * @param gebiet Gebiet, welches angezeigt werden soll
	 */
	public void ansichtAendern(Gebiet gebiet) {
		remove(tabellenFenster);
		remove(diagrammFenster);
		this.tabellenFenster = new TabellenFenster();
		this.tabellenFenster.tabellenFuellen(gebiet);
		this.diagrammFenster = new DiagrammFenster();
		this.diagrammFenster.erstelleDiagramm(gebiet);
		aktualisieren();
	}
	
	/**
	 * Durch diese Methode wird das allgemeine Layout der Ansichten gesetzt
	 */
	private void layoutSetzen() {
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		add(kartenFenster, gbc);
		
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
	
	private void aktualisieren() {
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

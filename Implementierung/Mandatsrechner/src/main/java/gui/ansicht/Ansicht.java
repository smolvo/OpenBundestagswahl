package main.java.gui.ansicht;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.gui.WahlFenster;
import main.java.gui.ansicht.diagrammfenster.DiagrammFenster;
import main.java.gui.ansicht.tabellenfenster.TabellenFenster;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Gebiet;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repräsentiert die Ansicht, auf der sicht das Tabellen-, Karten-
 * und Diagrammfenster befinden.
 * 
 */
public class Ansicht extends JPanel {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = 7008080710664890075L;

	/** Eine Ansicht hat ein Tabellenfenster. */
	private TabellenFenster tabellenFenster;

	/** Eine Ansicht hat ein Diagrammfenster. */
	private DiagrammFenster diagrammFenster;

	/** das Panel auf dem der Berechne-Knopf sitzt */
	private JPanel berechnePanel;

	/** Eine Ansicht hat ein Kartenfenster. */
	private KartenFenster kartenFenster;

	/** repräsentiert das Wahlfenster in welchem sich die Ansicht befindet */
	private final WahlFenster fenster;

	/** repräsentiert das Layout */
	private GridBagConstraints gbc;

	/** Das im Moment angezeigte Gebiet */
	private Gebiet aktuellesGebiet;

	/** zeigt an ob eine Stimme geändert wurde */
	private boolean wurdeVeraendert;
	
	private Bundestagswahl btw;
	
	/**
	 * Der Konstruktor setzt eine neue Ansicht.
	 * 
	 * @param land
	 *            Deutschland
	 * @param fenster
	 *            das Wahlfenster der Ansicht
	 * @throws IllegalArgumentException
	 *             wenn die Parameter null sind.
	 */
	public Ansicht(Bundestagswahl btw, WahlFenster fenster) {
		if (btw == null || fenster == null) {
			throw new IllegalArgumentException("Einer der Parameter ist null.");
		}
		
		this.btw = btw;
		this.wurdeVeraendert = false;
		this.fenster = fenster;
		this.tabellenFenster = new TabellenFenster(this);
		this.tabellenFenster.tabellenFuellen(btw.getDeutschland());
		this.diagrammFenster = new DiagrammFenster(this, btw);
		this.diagrammFenster.erstelleDiagramm(btw.getDeutschland(), btw);
		this.kartenFenster = new KartenFenster(this);
		this.kartenFenster.zeigeInformationen(btw.getDeutschland());
		this.gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		initialisieren();
		this.setMinimumSize(new Dimension(1024, 768));
	}

	/**
	 * Diese Methode wird vom Konstruktor verwendet, um das Kartenfenster zu
	 * setzen.
	 */
	private void initialisieren() {
		gbc.ipadx = 50;
		gbc.ipady = 50;

		gbc.weightx = 0.75;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridheight = 1;
		add(kartenFenster, gbc);

		gbc.weightx = 0.75;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridheight = 1;
		add(diagrammFenster, gbc);

		gbc.weightx = 1.25;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(tabellenFenster, gbc);
	}

	/**
	 * Diese Methode ändert die aktuelle Ansicht in eine neue.
	 * 
	 * @param gebiet
	 *            Gebiet, welches angezeigt werden soll
	 */
	public void ansichtAendern(Gebiet gebiet) {
		if (gebiet != null) {
			this.aktuellesGebiet = gebiet;
			remove(tabellenFenster);
			remove(diagrammFenster);
			this.tabellenFenster = new TabellenFenster(this);
			this.tabellenFenster.tabellenFuellen(gebiet);
			this.diagrammFenster = new DiagrammFenster(this, this.btw);
			this.diagrammFenster.erstelleDiagramm(gebiet, this.btw);
			layoutSetzen();
		}
	}

	/**
	 * Durch diese private Methode wird das Diagramm- und das Tabellenfenster
	 * gesetzt.
	 */
	private void layoutSetzen() {
		gbc.ipadx = 50;
		gbc.ipady = 50;

		if (!this.wurdeVeraendert) {
			gbc.weightx = 0.65;
			gbc.weighty = 1;
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridheight = 1;
			add(diagrammFenster, gbc);
		}

		gbc.weightx = 1.35;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridheight = 2;
		add(tabellenFenster, gbc);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn eine Stimme geändert wurde und eine
	 * neue Berechnung notwendig ist.
	 */
	public void berechnungNotwendig() {
		this.wurdeVeraendert = true;
		remove(diagrammFenster);
		this.diagrammFenster = new DiagrammFenster(this, this.btw);

		this.berechnePanel = new JPanel();
		this.berechnePanel.setSize(new Dimension(100, 100));

		JButton berechne = new JButton("Berechne");
		berechne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				wurdeVeraendert = false;
				entferneBerechneKnopf();
				berechnePanel = null;
				Steuerung.getInstance().berechneSitzverteilung();
				fenster.getSteuerung().aktualisiereWahlfenster(aktuellesGebiet);
			}

		});
		berechne.setSize(new Dimension(150, 50));
		this.berechnePanel.add(berechne, BorderLayout.CENTER);

		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(berechnePanel, gbc);
	}

	/**
	 * Diese Methode entfernt den Berechenknopf.
	 */
	public void entferneBerechneKnopf() {
		this.remove(berechnePanel);
		this.berechnePanel = null;
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
	 * @throws NullPointerException
	 */
	public void setTabellenFenster(TabellenFenster tabellenFenster) {
		if (tabellenFenster == null) {
			throw new IllegalArgumentException("Fenster ist leer");
		}
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
	 * @throws NullPointerException
	 */
	public void setDiagrammFenster(DiagrammFenster diagrammFenster) {
		if (diagrammFenster == null) {
			throw new IllegalArgumentException("Fenster ist leer");
		}
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
	 * @throws IllegalArgumentException
	 *             wenn das Kartenfenster-objekt null ist.
	 */
	public void setKartenFenster(KartenFenster kartenFenster) {
		if (kartenFenster == null) {
			throw new IllegalArgumentException("Fenster ist null");
		}
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
	 * @throws IllegalArgumentException
	 *             wenn das Gebiet-Objekt null ist.
	 */
	public void setAktuellesGebiet(Gebiet aktuellesGebiet) {
		if (aktuellesGebiet == null) {
			throw new IllegalArgumentException("Gebiet ist null");
		}
		this.aktuellesGebiet = aktuellesGebiet;
	}
}

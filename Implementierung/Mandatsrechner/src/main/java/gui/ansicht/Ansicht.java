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
	private final GridBagConstraints gbc;

	/** Das im Moment angezeigte Gebiet */
	private Gebiet aktuellesGebiet;

	/** zeigt an ob eine Stimme geändert wurde */
	private boolean wurdeVeraendert;

	private final Bundestagswahl btw;

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
		setLayout(new GridBagLayout());
		initialisieren();
		setMinimumSize(new Dimension(1024, 768));
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
			remove(this.tabellenFenster);
			remove(this.diagrammFenster);
			this.tabellenFenster = new TabellenFenster(this);
			this.tabellenFenster.tabellenFuellen(gebiet);
			this.diagrammFenster = new DiagrammFenster(this, this.btw);
			this.diagrammFenster.erstelleDiagramm(gebiet, this.btw);
			layoutSetzen();
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn eine Stimme geändert wurde und eine
	 * neue Berechnung notwendig ist.
	 */
	public void berechnungNotwendig() {
		this.wurdeVeraendert = true;
		remove(this.diagrammFenster);
		this.diagrammFenster = new DiagrammFenster(this, this.btw);

		this.berechnePanel = new JPanel();
		this.berechnePanel.setSize(new Dimension(100, 100));

		final JButton berechne = new JButton("Berechne");
		berechne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Ansicht.this.wurdeVeraendert = false;
				entferneBerechneKnopf();
				Ansicht.this.berechnePanel = null;
				Steuerung.getInstance().berechneSitzverteilung();
				Ansicht.this.fenster.getSteuerung().aktualisiereWahlfenster(
						Ansicht.this.aktuellesGebiet);
			}

		});
		berechne.setSize(new Dimension(150, 50));
		this.berechnePanel.add(berechne, BorderLayout.CENTER);

		this.gbc.weightx = 0.5;
		this.gbc.weighty = 0.5;
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.gbc.fill = GridBagConstraints.BOTH;
		add(this.berechnePanel, this.gbc);
	}

	/**
	 * Diese Methode entfernt den Berechenknopf.
	 */
	public void entferneBerechneKnopf() {
		if (this.berechnePanel != null) {
			this.remove(this.berechnePanel);
			this.berechnePanel = null;
		}
	}

	/**
	 * @return the aktuellesGebiet
	 */
	public Gebiet getAktuellesGebiet() {
		return this.aktuellesGebiet;
	}

	/**
	 * Holt das Diagrammfenster der Ansicht.
	 * 
	 * @return aktuelles Diagrammfenster
	 */
	public DiagrammFenster getDiagrammFenster() {
		return this.diagrammFenster;
	}

	/**
	 * Gibt das Wahlfenster aus.
	 * 
	 * @return Wahlfenster
	 */
	public WahlFenster getFenster() {
		return this.fenster;
	}

	/**
	 * Holt das Kartenfenster der Ansicht.
	 * 
	 * @return aktuelles Kartenfenster
	 */
	public KartenFenster getKartenFenster() {
		return this.kartenFenster;
	}

	/**
	 * Holt das Tabellenfenster der Ansicht.
	 * 
	 * @return aktuelles Tabellenfenster
	 */
	public TabellenFenster getTabellenFenster() {
		return this.tabellenFenster;
	}

	/**
	 * Diese Methode wird vom Konstruktor verwendet, um das Kartenfenster zu
	 * setzen.
	 */
	private void initialisieren() {
		this.gbc.ipadx = 50;
		this.gbc.ipady = 50;

		this.gbc.weightx = 1;
		this.gbc.weighty = 1;
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.gbc.gridheight = 1;
		add(this.kartenFenster, this.gbc);

		this.gbc.weightx = 1;
		this.gbc.weighty = 1;
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.gbc.gridheight = 1;
		add(this.diagrammFenster, this.gbc);

		this.gbc.weightx = 2;
		this.gbc.weighty = 1;
		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.gbc.gridheight = 2;
		this.gbc.fill = GridBagConstraints.BOTH;
		add(this.tabellenFenster, this.gbc);
	}

	/**
	 * Durch diese private Methode wird das Diagramm- und das Tabellenfenster
	 * gesetzt.
	 */
	private void layoutSetzen() {
		this.gbc.ipadx = 50;
		this.gbc.ipady = 50;

		if (!this.wurdeVeraendert) {
			this.gbc.weightx = 1;
			this.gbc.weighty = 1;
			this.gbc.gridx = 0;
			this.gbc.gridy = 1;
			this.gbc.fill = GridBagConstraints.BOTH;
			this.gbc.gridheight = 1;
			add(this.diagrammFenster, this.gbc);
		}

		this.gbc.weightx = 2;
		this.gbc.weighty = 1;
		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.gbc.gridheight = 2;
		add(this.tabellenFenster, this.gbc);
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
}

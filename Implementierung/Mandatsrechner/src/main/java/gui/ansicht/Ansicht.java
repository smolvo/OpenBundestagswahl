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
import main.java.model.Deutschland;
import main.java.model.Gebiet;
import main.java.steuerung.Steuerung;

/**
 * Die abstrakte Klasse Ansicht, der grafischen Benutzeroberfl�che.
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

	/** repr�sentiert das Wahlfenster in welchem sich die Ansicht befindet */
	private final WahlFenster fenster;

	/** repr�sentiert das Layout */
	private GridBagConstraints gbc;

	/** Das im Moment angezeigte Gebiet */
	private Gebiet aktuellesGebiet;
	
	/** zeigt an ob eine Stimme ge�ndert wurde */
	private boolean wurdeVeraendert;

	/**
	 * Der Konstruktor setzt eine neue Ansicht.
	 * 
	 * @param land
	 *            Deutschland
	 * @param fenster
	 *            das Wahlfenster der Ansicht
	 */
	public Ansicht(Deutschland land, WahlFenster fenster) {
		this.wurdeVeraendert = false;
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
		add(kartenFenster, gbc);

		gbc.weightx = 0.75;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
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
	 * Diese Methode �ndert die aktuelle Ansicht in eine neue.
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
	 * Durch diese private Methode wird das Diagramm- und das Tabellenfenster gesetzt.
	 */
	private void layoutSetzen() {
		gbc.ipadx = 50;
		gbc.ipady = 50;
		
		if (!this.wurdeVeraendert) {
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.fill = GridBagConstraints.BOTH;
			add(diagrammFenster, gbc);
		}

		gbc.weightx = 1;
		gbc.weighty = 1.5;
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = gbc.gridheight * 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(tabellenFenster, gbc);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn eine Stimme ge�ndert wurde
	 * und eine neue Berechnung notwendig ist.
	 */
	public void berechnungNotwendig() {
		this.wurdeVeraendert = true;
		remove(diagrammFenster);
		this.diagrammFenster = new DiagrammFenster(this);
		
		JButton berechne = new JButton("Berechne");
		berechne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Steuerung.getInstance().berechneSitzverteilung();
				wurdeVeraendert = false;
			}
			
		});
		berechne.setSize(new Dimension(150, 50));
		this.diagrammFenster.add(berechne, BorderLayout.CENTER);
		
		
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(diagrammFenster, gbc);
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
		if(tabellenFenster == null){
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
	 */
	public void setDiagrammFenster(DiagrammFenster diagrammFenster) {
		if(diagrammFenster == null){
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
	 */
	public void setKartenFenster(KartenFenster kartenFenster) {
		if(kartenFenster == null){
			throw new IllegalArgumentException("Fenster ist leer");
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
	 */
	public void setAktuellesGebiet(Gebiet aktuellesGebiet) {
		if(aktuellesGebiet == null){
			throw new IllegalArgumentException("Gebiet ist leer");
		}
		this.aktuellesGebiet = aktuellesGebiet;
	}
}

package main.java.gui.ansicht.diagrammfenster;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.gui.BerichtTableModel;
import main.java.gui.BerichtsFenster;
import main.java.gui.ansicht.Ansicht;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Gebiet;
import main.java.model.Wahlkreis;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse reprï¿½sentiert das Diagrammfenster einer Ansicht. In diesem
 * werden bestimmmte Daten eines BTW-Objektes angezeigt.
 * 
 */
public class DiagrammFenster extends JPanel {

	private static final long serialVersionUID = -523400111719339965L;

	/** reprï¿½sentiert die Ansicht des Diagrammfensters */
	private final Ansicht ansicht;

	/** reprï¿½sentiert den Berichtsknopf */
	private final JButton bericht;

	/** reprï¿½sentiert die LayoutConstraints */
	private final GridBagConstraints gbc;

	/**
	 * Der Konstruktor initialisiert ein neues Diagrammfenster.
	 * 
	 * @param ansicht
	 *            die Ansicht
	 */
	public DiagrammFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
		this.bericht = new JButton("Bericht anzeigen");
		this.bericht.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				zeigeSitzverteilung(Steuerung.getInstance().getBtw());
			}

		});
		this.gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
	}

	/**
	 * Diese Methode identifiziert das Gebiets-Objekt.
	 * 
	 * @param gebiet
	 *            Gebiet-Objekt welches visualisiert werden soll
	 * @throw NullPointerException
	 */
	public void erstelleDiagramm(Gebiet gebiet) {
		if (gebiet == null) {
			throw new NullPointerException("Kein Gebiet gefunden.");
		}
		this.gbc.weightx = 1;
		this.gbc.weighty = 1;
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.gbc.fill = GridBagConstraints.BOTH;
		if (gebiet instanceof Deutschland) {
			Deutschland land = (Deutschland) gebiet;
			erstelleDiagramm(land);
		} else if (gebiet instanceof Bundesland) {
			Bundesland bundLand = (Bundesland) gebiet;
			erstelleDiagramm(bundLand);
		} else {
			Wahlkreis wk = (Wahlkreis) gebiet;
			erstelleDiagramm(wk);
		}
	}

	/**
	 * Erstellt das Diagramm der Bundesansicht.
	 * 
	 * @param land
	 *            Deutschland
	 */
	public void erstelleDiagramm(Deutschland land) {
		if (land == null) {
			throw new IllegalArgumentException("Deutschland ist null.");
		}
		this.add(new BundDiagramm(land), gbc);
		this.gbc.weighty = 0.1;
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.gbc.fill = GridBagConstraints.BOTH;
		this.add(bericht, gbc);
	}

	/**
	 * Erstellt das Diagramm der Landesansicht.
	 * 
	 * @param bundLand
	 *            das Budnesland-Objekt
	 * @throws IllegalArgumentException
	 *             wenn das Bundesland-Objekt null ist.
	 */
	public void erstelleDiagramm(Bundesland bundLand) {
		if (bundLand == null) {
			throw new IllegalArgumentException("Bundesland ist null.");
		}
		this.add(new LandDiagramm(bundLand), gbc);
	}

	/**
	 * Erstellt das Diagramm der Wahlkreisansicht.
	 * 
	 * @param wk
	 *            Wahlkreis-Objekt
	 * @throws IllegalArgumentException
	 *             wenn das Wahlkreis-Objekt null ist.
	 * 
	 */
	public void erstelleDiagramm(Wahlkreis wk) {
		if (wk == null) {
			throw new IllegalArgumentException("Wahlkreis ist null.");
		}
		this.add(new WahlkreisDiagramm(wk), gbc);
	}

	/**
	 * Diese Methode ï¿½ffnet ein BerichtsFenster, in dem die Sitze der Verteilung
	 * nï¿½her erlï¿½utert werden.
	 * 
	 * @param btw
	 *            Bundestagswahl-Objekt welches visualisiert werden soll
	 * @throws IllegalArgumentException
	 *             wenn das Bundestagswahl-Objekt null ist.
	 */
	public void zeigeSitzverteilung(Bundestagswahl btw) {
		if (btw == null) {
			throw new IllegalArgumentException("Keine Bundestagswahl gefunden.");
		}
		BerichtTableModel tabelle = new BerichtTableModel(btw
				.getSitzverteilung().getBericht());
		new BerichtsFenster(tabelle);
	}

	/**
	 * Gibts die Ansicht aus.
	 * 
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.ansicht.Ansicht;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Bundestagswahl;
import model.Gebiet;

/**
 * Diese Klasse repräsentiert die allgemeine Darstellung einer Wahl
 * Sie setzt sich aus den drei Ansichten Bundesansicht, Landesansicht, Wahlkreisansicht zusammen
 * @author Manuel
 *
 */
public class WahlFenster extends JPanel {

	/** repräsentiert den Namen des Tabs */
	private String name;
	
	/** repräsentiert die geladene Bundestagswahl */
	private Bundestagswahl btw;
	
	/** repräsentiert die aktuelle Ansicht */
	private Ansicht aktuelleAnsicht;
	
	/** repräsentiert die aktuelle Steuerung des Wahlfensters */
	private GUISteuerung steuerung;

	/** repräsentiert das zuletzt gezeigte Gebiet, so dass zurück gesprungen werden kann*/
//	private Ansicht letzteAnsicht;
	
	/**
	 * der Konstruktor der Klase
	 * @param btw
	 */
	public WahlFenster(Bundestagswahl btw) {
		this.btw = btw;
		this.name = btw.getName();
		this.aktuelleAnsicht = new Ansicht(btw.getDeutschland());
		this.add(aktuelleAnsicht);
		this.steuerung = new GUISteuerung(btw, this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	
	public void wechsleAnsicht(Gebiet gebiet) {
		this.aktuelleAnsicht.setDiagrammFenster(null);
		this.aktuelleAnsicht.setTabellenFenster(null);
		this.aktuelleAnsicht.ansichtAendern(gebiet);
		this.add(aktuelleAnsicht);
	}

	public GUISteuerung getSteuerung() {
		return steuerung;
	}

	public void setSteuerung(GUISteuerung steuerung) {
		this.steuerung = steuerung;
	}
	
	
	//TODO
	public void zurueckButton() {
		JButton zurueck = new JButton(new ImageIcon("src/gui/resources/images/ansichtZurueck.png"));
		zurueck.setSize(70, 50);
		
		//Erstelle anonymen ActionListener für den Zurück- Knopf
				ActionListener listener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
//						WahlFenster.this.wechsleAnsicht(letzteAnsicht.getAktuellesGebiet());
					}
				};
			zurueck.addActionListener(listener);
		
	}

	public Bundestagswahl getBtw() {
		return btw;
	}

	public void setBtw(Bundestagswahl btw) {
		this.btw = btw;
	}
}

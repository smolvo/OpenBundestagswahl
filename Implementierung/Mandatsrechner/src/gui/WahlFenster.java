package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.ansicht.Ansicht;
import gui.ansicht.Bundesansicht;
import gui.ansicht.KartenFenster;
import gui.ansicht.Landesansicht;
import gui.ansicht.Wahlkreisansicht;
import gui.ansicht.tabellenfenster.TabellenFenster;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import model.Bundesland;
import model.Bundestagswahl;
import model.Deutschland;
import model.Gebiet;

/**
 * Diese Klasse repräsentiert die allgemeine Darstellung einer Wahl
 * Sie setzt sich aus den drei Ansichten Bundesansicht, Landesansicht, Wahlkreisansicht zusammen
 * @author Manuel
 *
 */
public class WahlFenster extends JPanel{

	/** repräsentiert den Namen des Tabs */
	private String name;
	
	/** repräsentiert die geladene Bundestagswahl */
	private Bundestagswahl btw;
	
	/** repräsentiert die aktuelle Ansicht */
	private Ansicht aktuelleAnsicht;
	
	/** repräsentiert die aktuelle Steuerung des Wahlfensters */
	private GUISteuerung steuerung;

	/** repräsentiert das zuletzt gezeigte Gebiet, so dass zurück gesprungen werden kann*/
	private Ansicht letzteAnsicht;
	
	/**
	 * der Konstruktor der Klase
	 * @param btw
	 */
	public WahlFenster(Bundestagswahl btw) {
		this.btw = btw;
		this.name = btw.getName();
		this.aktuelleAnsicht = new Bundesansicht(btw.getDeutschland());
		aktuelleAnsicht.erstelleKartenfenster(btw.getDeutschland());
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
		if (gebiet instanceof Deutschland) {
			this.aktuelleAnsicht.removeAll();
			this.aktuelleAnsicht = new Bundesansicht(gebiet);
			aktuelleAnsicht.erstelleKartenfenster(btw.getDeutschland());
			this.add(aktuelleAnsicht);
		} else if (gebiet instanceof Bundesland) {
			this.aktuelleAnsicht.removeAll();
			this.aktuelleAnsicht = new Landesansicht(gebiet);
			aktuelleAnsicht.erstelleKartenfenster(btw.getDeutschland());
			this.add(aktuelleAnsicht);
		} else {
			this.aktuelleAnsicht.removeAll();
			this.aktuelleAnsicht = new Wahlkreisansicht(gebiet);
			aktuelleAnsicht.erstelleKartenfenster(btw.getDeutschland());
			this.add(aktuelleAnsicht);
		}
	}
	
	
	/*
	public void wechsleAnsicht(Gebiet gebiet) {
	
		aktuelleAnsicht.removeAll();
		if (gebiet instanceof Deutschland) {
			aktuelleAnsicht = new Bundesansicht(gebiet);
		} else if (gebiet instanceof Bundesland) {
			this.aktuelleAnsicht = new Landesansicht(gebiet);
		} else {
			this.aktuelleAnsicht = new Wahlkreisansicht(gebiet);
		}
		add(aktuelleAnsicht);
	}
*/

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
						WahlFenster.this.wechsleAnsicht(letzteAnsicht.getAktuellesGebiet());
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

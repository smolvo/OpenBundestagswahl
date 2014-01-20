package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.ansicht.Ansicht;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import steuerung.Steuerung;
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
	 * @param btw Bundestagswahl
	 */
	public WahlFenster(Bundestagswahl btw) {
		this.btw = btw;
		this.name = btw.getName();
		this.steuerung = new GUISteuerung(btw, this, new Steuerung());
		this.aktuelleAnsicht = new Ansicht(btw.getDeutschland(), this);
		setLayout(new BorderLayout());
		this.add(aktuelleAnsicht, BorderLayout.CENTER);
	}
	 
	/**
	 * Wechselt die Ansicht.
	 * @param gebiet neues Gebiet
	 */
	public void wechsleAnsicht(Gebiet gebiet) {
		this.aktuelleAnsicht.ansichtAendern(gebiet);
		this.add(aktuelleAnsicht);
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

	/**
	 * Gibt den Namen aus.
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen.
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gibt die GUISteuerung aus.
	 * @return GUI Steuerung
	 */
	public GUISteuerung getSteuerung() {
		return steuerung;
	}
	
	/**
	 * Setzt die GUI Steuerung.
	 * @param steuerung GUI Steuerung
	 */
	public void setSteuerung(GUISteuerung steuerung) {
		this.steuerung = steuerung;
	}

	/**
	 * Gibt die Bundestagswahl aus.
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBtw() {
		return btw;
	}

	/**
	 * Setzt die Bundestagswahl.
	 * @param btw Bundestagswahl
	 */
	public void setBtw(Bundestagswahl btw) {
		this.btw = btw;
	}
}

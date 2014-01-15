package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import gui.ansicht.Ansicht;
import gui.ansicht.Bundesansicht;
import gui.ansicht.KartenFenster;
import gui.ansicht.Landesansicht;
import gui.ansicht.Wahlkreisansicht;
import gui.ansicht.tabellenfenster.TabellenFenster;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

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

	/**
	 * der Konstruktor der Klase
	 * @param btw
	 */
	public WahlFenster(Bundestagswahl btw) {
		this.btw = btw;
		this.name = btw.getName();
		this.aktuelleAnsicht = new Bundesansicht(btw.getDeutschland());
		this.add(aktuelleAnsicht);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	public void wechsleAnsicht(Ansicht a) {
		aktuelleAnsicht = a;
	}
	
	/* wird theoretisch nicht mehr benötigt -Anton
	 * 
	public void bundestagswahlDarstellen() {
		Deutschland deutschland = btw.getDeutschland();
		
		if(aktuelleAnsicht.equals(bundesansicht)) {
			
	
		//Kartenfenster
		KartenFenster karte = new KartenFenster();
		karte.zeigeInformationen(deutschland);			

		linkeSpalte.add(karte);
				
			
		//Tabellenfenster
		TabellenFenster tabelle = new TabellenFenster();
		tabelle.tabellenFuellen(deutschland);
		bundesansicht.setTabellenFenster(tabelle);
		
		
		rechteSpalte.add(tabelle);
		
		
		//Diagrammfenster
		JPanel diagramme = new JPanel();
		diagramme.setSize(400, 400);
		diagramme.setBackground(new Color(255, 0, 0));
		diagramme.add(new JLabel("Diagramme"));
		
		
		linkeSpalte.add(diagramme);
		 
	
		
		} else if (aktuelleAnsicht.equals(landesansicht)) {
			//TODO
		} else {
			//TODO
		}
		
		
	
		
	}
	*/
	
}

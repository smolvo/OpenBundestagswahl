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

	private String name;
	
	private Bundesansicht bundesansicht;
	private Landesansicht landesansicht;
	private Wahlkreisansicht wahlkreisansicht;
	
	private Bundestagswahl btw;
	private Ansicht aktuelleAnsicht;
	
	JPanel linkeSpalte;
	JPanel rechteSpalte;

	/**
	 * der Konstruktor der Klase
	 * @param btw
	 */
	public WahlFenster(Bundestagswahl btw) {
		super();
		//Allgemeine Anpassungen des Wahlfensters
		GridLayout linkeSpalteLay= new GridLayout(2,1,5, 5);
		GridLayout rechteSpalteLay= new GridLayout(1,1,5, 5);
		GridLayout gridLay = new GridLayout(1, 2, 5, 5);
		
		linkeSpalte = new JPanel();
		linkeSpalte.setLayout(linkeSpalteLay);
		rechteSpalte = new JPanel();
		rechteSpalte.setLayout(rechteSpalteLay);
		
		this.setLayout(gridLay);
		this.add(linkeSpalte);
		this.add(rechteSpalte);

		this.btw = btw;
		this.bundesansicht = new Bundesansicht();
		this.landesansicht = new Landesansicht();
		this.wahlkreisansicht = new Wahlkreisansicht();
		this.name = btw.getName();
		this.aktuelleAnsicht = bundesansicht;
		
		bundestagswahlDarstellen();
		setVisible(true);
		
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
	
}

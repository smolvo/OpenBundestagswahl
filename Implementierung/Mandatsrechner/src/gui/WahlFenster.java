package gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Bundestagswahl;
import model.Gebiet;

public class WahlFenster extends JPanel{

	private String name;
	
	private Bundesansicht bundesansicht;
	private Landesansicht landesansicht;
	private Wahlkreisansicht wahlkreisansicht;
	
	private Bundestagswahl btw;

	public WahlFenster(String name) {
		super();
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	public void wechsleAnsicht() {
		
	}
	
	public void bundestagswahlDarstellen(Bundestagswahl btw) {
		Gebiet deutschland = btw.getDeutschland();
	}
	
}

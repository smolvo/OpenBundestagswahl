package main.java.gui.ansicht;

import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JTabbedPane;

import main.java.model.Bundesland;
import main.java.model.Deutschland;

/**
 * Diese Klasse repräsentiert das Kartenfenster einer Ansicht.
 * Es werden die Bundesländer aufgelistet, und wenn möglich
 * eine kartographische Ansicht des Landes.
 *
 */
public class KartenFenster extends JTabbedPane {

	/** repräsentiert die Ansicht in der sich das Kartenfenster befindet */
	private Ansicht ansicht;
	
	/**
	 * Der Konstruktor erstellt ein neues Kartenfenster.
	 * @param ansicht Ansicht
	 */
	public KartenFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}
	
	/**
	 *  Array mit den Namen aller Bundesländer
	 */
	String[] alleLaender = new String[] {"Baden-Württemberg", "Bayern", "Berlin", "Brandenburg", 
			 "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern",
			 "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz",
			 "Saarland", "Sachsen-Anhalt", "Sachsen",
			 "Schleswig-Holstein", "Thüringen"};
	
	/**
	 * Diese Methode listet die Bundesländer auf und erstellt,
	 * wenn möglich, eine kartographische Ansicht.
	 * @param land Deutschland-Objekt welches visualisiert werden soll
	 */
	public void zeigeInformationen(Deutschland land) {
		DeutschlandKarte d = new DeutschlandKarte(land);
		if (pruefeLaender(land)) {
			this.addTab("Kartenansicht", d);
		} else {
			this.addTab("Kartenansicht", null);
			this.setEnabledAt(0, false);
		}
		
		/* kreiiert die Listenansicht */
		Listenansicht liste = new Listenansicht(land, this);
		this.add("Listenansicht", liste);
	}

	/**
	 * Diese private Methode überprüft ob die Liste von Bundesländern den
	 * reellen entspricht.
	 * @param land enthält alle Bundesländer
	 * @return wahr oder falsch
	 */
	private boolean pruefeLaender(Deutschland land) {
		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		Collections.sort(bundeslaender);
		boolean gleich = true;
		for (int i = 0; i == 15; i++) {
			if (alleLaender[i] != bundeslaender.get(i).getName()) {
				gleich = false;
			}
		}
		return gleich;	
	}

	/**
	 * Gibt die Ansicht aus.
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}

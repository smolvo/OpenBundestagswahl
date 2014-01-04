package gui;

import java.util.LinkedList;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.Bundesland;
import model.Bundestagswahl;

/**
 * Diese Klasse repr�sentiert das Kartenfenster einer Ansicht.
 * Es werden die Bundesl�nder aufgelistet, und wenn m�glich
 * eine kartographische Ansicht des Landes.
 * @author Batman
 *
 */
public class KartenFenster extends JTabbedPane {

	


	/**
	 * Diese Methode listet die Bundesl�nder auf und erstellt,
	 * wenn m�glich, eine kartographische Ansicht.
	 * @param btw Bundestagswahl-Objekt welches visualisiert werden soll
	 */
	public void zeigeInformationen(Bundestagswahl btw) {
		
		
		
		/* kreiiert die Listenansicht */
		Listenansicht liste = new Listenansicht(btw.getDeutschland());
		this.add("Listenansicht", liste);
	}

	/**
	 * Diese Methode f�rbt die Bundesl�nder nach den Parteien, die die meisten
	 * Zweitstimmen haben.
	 * @param btw Bundestagswahl-Objekt welches visualisiert werden soll
	 */
	private void faerbeBundeslaender(Bundestagswahl btw) {
		LinkedList<Bundesland> bundeslaender = btw.getDeutschland().getBundeslaender();
		boolean alleLaender = true;
		for (Bundesland bund : bundeslaender) {
			if (bundeslaender.size() != 16)
					return;
			
			if (!((bund.getName().equals("Baden-W�rttemberg")) || (bund.getName().equals("Bayern")) ||
					(bund.getName().equals("Berlin")) || (bund.getName().equals("Brandenburg")) || 
					(bund.getName().equals("Bremen")) || (bund.getName().equals("Hamburg")) || 
					(bund.getName().equals("Hessen")) || (bund.getName().equals("Mecklenburg-Vorpommern")) || 
					(bund.getName().equals("Niedersachsen")) || (bund.getName().equals("Nordrhein-Westfalen")) || 
					(bund.getName().equals("Rheinland-Pfalz")) || (bund.getName().equals("Saarland")) || 
					(bund.getName().equals("Sachsen")) || (bund.getName().equals("Sachsen-Anhalt")) || 
					(bund.getName().equals("Schleswig-Holstein")) || (bund.getName().equals("Th�ringen")))) {
				alleLaender = false;
				return;
			}
		}
			if (alleLaender == false) {
			return;
		} else {
			DeutschlandKarte d = new DeutschlandKarte(btw.getDeutschland());
			this.addTab("Deutschland", d);
			this.addTab("Hallo", new JTextArea());
		}
	}
}

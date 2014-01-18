package gui.ansicht;

import java.util.LinkedList;

import javax.swing.JTabbedPane;

import model.Bundesland;
import model.Deutschland;

/**
 * Diese Klasse repr�sentiert das Kartenfenster einer Ansicht.
 * Es werden die Bundesl�nder aufgelistet, und wenn m�glich
 * eine kartographische Ansicht des Landes.
 *
 */
public class KartenFenster extends JTabbedPane {

	/** repr�sentiert die Ansicht in der sich das Kartenfenster befindet */
	private Ansicht ansicht;
	
	/**
	 * Der Konstruktor erstellt ein neues Kartenfenster.
	 * @param ansicht Ansicht
	 */
	public KartenFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}
	
	
	/**
	 * Diese Methode listet die Bundesl�nder auf und erstellt,
	 * wenn m�glich, eine kartographische Ansicht.
	 * @param land Deutschland-Objekt welches visualisiert werden soll
	 */
	public void zeigeInformationen(Deutschland land) {
		DeutschlandKarte d = new DeutschlandKarte(land);
		if (ueberpruefeBundeslaender(land.getBundeslaender())) {
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
	 * Diese private Methode �berpr�ft ob die Liste von BUndesl�ndern den
	 * reellen entspricht.
	 * @param bundeslaender Liste der Bundesl�nder
	 * @return wahr oder falsch
	 */
	private boolean ueberpruefeBundeslaender(LinkedList<Bundesland> bundeslaender) {
		for (Bundesland bund : bundeslaender) {
			if (bundeslaender.size() != 16)
					return false;
			
			if (!((bund.getName().equals("Baden-W�rttemberg")) || (bund.getName().equals("Bayern")) ||
					(bund.getName().equals("Berlin")) || (bund.getName().equals("Brandenburg")) || 
					(bund.getName().equals("Bremen")) || (bund.getName().equals("Hamburg")) || 
					(bund.getName().equals("Hessen")) || (bund.getName().equals("Mecklenburg-Vorpommern")) || 
					(bund.getName().equals("Niedersachsen")) || (bund.getName().equals("Nordrhein-Westfalen")) || 
					(bund.getName().equals("Rheinland-Pfalz")) || (bund.getName().equals("Saarland")) || 
					(bund.getName().equals("Sachsen")) || (bund.getName().equals("Sachsen-Anhalt")) || 
					(bund.getName().equals("Schleswig-Holstein")) || (bund.getName().equals("Th�ringen")))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gibt die Ansicht aus.
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}

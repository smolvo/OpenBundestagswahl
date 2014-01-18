package gui.ansicht;

import java.util.LinkedList;

import javax.swing.JTabbedPane;

import model.Bundesland;
import model.Deutschland;

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
	 * Diese Methode listet die Bundesländer auf und erstellt,
	 * wenn möglich, eine kartographische Ansicht.
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
	 * Diese private Methode überprüft ob die Liste von BUndesländern den
	 * reellen entspricht.
	 * @param bundeslaender Liste der Bundesländer
	 * @return wahr oder falsch
	 */
	private boolean ueberpruefeBundeslaender(LinkedList<Bundesland> bundeslaender) {
		for (Bundesland bund : bundeslaender) {
			if (bundeslaender.size() != 16)
					return false;
			
			if (!((bund.getName().equals("Baden-Württemberg")) || (bund.getName().equals("Bayern")) ||
					(bund.getName().equals("Berlin")) || (bund.getName().equals("Brandenburg")) || 
					(bund.getName().equals("Bremen")) || (bund.getName().equals("Hamburg")) || 
					(bund.getName().equals("Hessen")) || (bund.getName().equals("Mecklenburg-Vorpommern")) || 
					(bund.getName().equals("Niedersachsen")) || (bund.getName().equals("Nordrhein-Westfalen")) || 
					(bund.getName().equals("Rheinland-Pfalz")) || (bund.getName().equals("Saarland")) || 
					(bund.getName().equals("Sachsen")) || (bund.getName().equals("Sachsen-Anhalt")) || 
					(bund.getName().equals("Schleswig-Holstein")) || (bund.getName().equals("Thüringen")))) {
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

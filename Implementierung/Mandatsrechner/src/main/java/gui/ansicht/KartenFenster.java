package main.java.gui.ansicht;

import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JTabbedPane;

import main.java.model.Bundesland;
import main.java.model.Deutschland;

/**
 * Diese Klasse reprï¿½sentiert das Kartenfenster einer Ansicht. Es werden die
 * Bundeslï¿½nder aufgelistet, und wenn mï¿½glich eine kartographische Ansicht des
 * Landes.
 * 
 */
public class KartenFenster extends JTabbedPane {

	private static final long serialVersionUID = 6496086485300101407L;

	/** reprï¿½sentiert die Ansicht in der sich das Kartenfenster befindet */
	private Ansicht ansicht;

	/**
	 * Der Konstruktor erstellt ein neues Kartenfenster.
	 * 
	 * @param ansicht
	 *            Ansicht
	 */
	public KartenFenster(Ansicht ansicht) {
		this.ansicht = ansicht;
	}

	/**
	 * Array mit den Namen aller Bundeslï¿½nder
	 */
	String[] alleLaender = new String[] { "Baden-Württemberg", "Bayern",
			"Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen",
			"Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen",
			"Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt",
			"Schleswig-Holstein", "Thüringen" };

	/**
	 * Diese Methode listet die Bundeslï¿½nder auf und erstellt, wenn mï¿½glich,
	 * eine kartographische Ansicht.
	 * 
	 * @param land
	 *            Deutschland-Objekt welches visualisiert werden soll
	 */
	public void zeigeInformationen(Deutschland land) {

		DeutschlandKarte d = new DeutschlandKarte(land);
		Listenansicht liste = new Listenansicht(land, this);
		if (pruefeLaender(land)) {
			this.addTab("Kartenansicht", d);
			this.add("Listenansicht", liste);
		} else {
			this.addTab("Kartenansicht", null);
			this.setEnabledAt(0, false);
			this.add("Listenansicht", liste);
			this.setSelectedIndex(1);
		}
	}

	/**
	 * Diese private Methode ï¿½berprï¿½ft ob die Liste von Bundeslï¿½ndern den
	 * reellen entspricht.
	 * 
	 * @param land
	 *            enthï¿½lt alle Bundeslï¿½nder
	 * @return wahr oder falsch
	 */
	private boolean pruefeLaender(Deutschland land) {
		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		Collections.sort(bundeslaender);
		boolean gleich = true;
		for (int i = 0; i < 16; i++) {
			if (!alleLaender[i].equals(bundeslaender.get(i).getName())) {
				gleich = false;
			}
		}
		return gleich;
	}

	/**
	 * Gibt die Ansicht aus.
	 * 
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return ansicht;
	}
}

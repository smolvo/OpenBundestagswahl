package main.java.gui.ansicht;

import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JTabbedPane;

import main.java.model.Bundesland;
import main.java.model.Deutschland;

/**
 * Diese Klasse repräsentiert das Kartenfenster einer Ansicht. Es werden die
 * Bundesländer aufgelistet, und wenn möglich eine kartographische Ansicht des
 * Landes.
 * 
 */
public class KartenFenster extends JTabbedPane {

	private static final long serialVersionUID = 6496086485300101407L;

	/** reprï¿½sentiert die Ansicht in der sich das Kartenfenster befindet */
	private final Ansicht ansicht;

	/**
	 * Array mit den Namen aller Bundeslï¿½nder
	 */
	String[] alleLaender = new String[] { "Baden-Württemberg", "Bayern",
			"Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen",
			"Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen",
			"Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt",
			"Schleswig-Holstein", "Thüringen" };

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
	 * Gibt die Ansicht aus.
	 * 
	 * @return Ansicht
	 */
	public Ansicht getAnsicht() {
		return this.ansicht;
	}

	/**
	 * Diese private Methode überprüft ob die Liste von Bundesländern den
	 * reellen entspricht.
	 * 
	 * @param land
	 *            enthält alle Bundesländer
	 * @return wahr oder falsch
	 */
	private boolean pruefeLaender(Deutschland land) {
		final LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		Collections.sort(bundeslaender);
		boolean gleich = true;
		for (int i = 0; i < 16; i++) {
			if (!this.alleLaender[i].equals(bundeslaender.get(i).getName())) {
				gleich = false;
			}
		}
		return gleich;
	}

	/**
	 * Diese Methode listet die Bundeslï¿½nder auf und erstellt, wenn möglich,
	 * eine kartographische Ansicht.
	 * 
	 * @param land
	 *            Deutschland-Objekt welches visualisiert werden soll
	 */
	public void zeigeInformationen(Deutschland land) {

		final DeutschlandKarte d = new DeutschlandKarte(land);
		final Listenansicht liste = new Listenansicht(land, this);
		if (pruefeLaender(land)) {
			this.addTab("Kartenansicht", d);
			this.add("Listenansicht", liste);
		} else {
			this.addTab("Kartenansicht", null);
			setEnabledAt(0, false);
			this.add("Listenansicht", liste);
			setSelectedIndex(1);
		}
	}
}

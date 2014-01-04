package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.Bundesland;
import model.Deutschland;


/**
 * Diese Klasse repräsentiert die kartographische Ansicht im Kartenfenster.
 *
 */
public class DeutschlandKarte extends JPanel {

	/** alle Bundesländer, nötig für Färbung */
	private Deutschland land;
	
	/**
	 * Konstruktor der Klasse.
	 * @param land das gesamte Land
	 */
	public DeutschlandKarte(Deutschland land) {
		this.land = land;
	}

	/**
	 * Diese Methode setzt die Deutschland Karte nach den Farben der
	 * Parteien, die die meisten Zweitstimmen bekommen haben.
	 */
	public void paintComponent(Graphics g) {
		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		
		for (Bundesland bl :bundeslaender) {
			if (bl.getName().equals("Baden-Württemberg")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				} else {
					ImageIcon badenWuerttemberg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Baden-Württemberg.png");
					badenWuerttemberg.paintIcon(this, g, 30, 229);
				}
			} else if (bl.getName().equals("Bayern")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Bayern.png");
					
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Bayern.png");
					bayern.paintIcon(this, g, 79, 202);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Bayern.png");
					bayern.paintIcon(this, g, 79, 202);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Bayern.png");
					bayern.paintIcon(this, g, 79, 202);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Bayern.png");
					bayern.paintIcon(this, g, 79, 202);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Bayern.png");
					bayern.paintIcon(this, g, 79, 202);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Bayern.png");
					bayern.paintIcon(this, g, 79, 202);
				} else {
					ImageIcon bayern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Bayern.png");
					bayern.paintIcon(this, g, 79, 202);
				}
			} else if (bl.getName().equals("Berlin")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				} else {
					ImageIcon berlin = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Berlin.png");
					berlin.paintIcon(this, g, 207, 108);
				}
			} else if (bl.getName().equals("Brandenburg")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				} else {
					ImageIcon brandenburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Brandenburg.png");
					brandenburg.paintIcon(this, g, 167, 75);
				}
			} else if (bl.getName().equals("Bremen")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AFD\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				} else {
					ImageIcon bremen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Bremen.png");
					bremen.paintIcon(this, g, 84, 70);
				}
			} else if (bl.getName().equals("Hamburg")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Afd\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				} else {
					ImageIcon hamburg = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Hamburg.png");
					hamburg.paintIcon(this, g, 119, 54);
				}
			} else if (bl.getName().equals("Hessen")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				} else {
					ImageIcon hessen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Hessen.png");
					hessen.paintIcon(this, g, 47, 147);
				}
			} else if (bl.getName().equals("Mecklenburg-Vorpommern")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				} else {
					ImageIcon mecklenburgVorpommern = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Mecklenburg-Vorpommern.png");
					mecklenburgVorpommern.paintIcon(this, g, 145, 27);
				}
			} else if (bl.getName().equals("Niedersachsen")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				} else {
					ImageIcon niedersachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Niedersachsen.png");
					niedersachsen.paintIcon(this, g, 30, 45);
				}
			} else if (bl.getName().equals("Nordrhein-Westfalen")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				} else {
					ImageIcon nordrheinWestfalen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Nordrhein-Westfalen.png");
					nordrheinWestfalen.paintIcon(this, g, 0, 100);
				}
			} else if (bl.getName().equals("Rheinland-Pfalz")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				} else {
					ImageIcon rheinlandPfalz = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Rheinland-Pfalz.png");
					rheinlandPfalz.paintIcon(this, g, 1, 170);
				}
			} else if (bl.getName().equals("Saarland")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				} else {
					ImageIcon saarland = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Saarland.png");
					saarland.paintIcon(this, g, 5, 227);
				}
			} else if (bl.getName().equals("Sachsen")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				} else {
					ImageIcon sachsen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Sachsen.png");
					sachsen.paintIcon(this, g, 169, 154);
				}
			} else if (bl.getName().equals("Sachsen-Anhalt")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				} else {
					ImageIcon sachsenAnhalt = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Sachsen-Anhalt.png");
					sachsenAnhalt.paintIcon(this, g, 134, 89);
				}
			} else if (bl.getName().equals("Schleswig-Holstein")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				} else {
					ImageIcon schleswigHolstein = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Schleswig-Holstein.png");
					schleswigHolstein.paintIcon(this, g, 94, 0);
				}
			} else if (bl.getName().equals("Thüringen")) {
				if (bl.getFarbe().equals(Color.BLACK)) {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\CDU\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				} else if (bl.getFarbe().equals(Color.RED)) {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\SPD\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				} else if (bl.getFarbe().equals(Color.YELLOW)) {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\FDP\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				} else if (bl.getFarbe().equals(Color.BLUE)) {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\AfD\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				} else if (bl.getFarbe().equals(Color.PINK)) {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE LINKE\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				} else if (bl.getFarbe().equals(Color.GREEN)) {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\DIE GRÜNEN\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				} else if (bl.getFarbe().equals(Color.ORANGE)) {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\Piraten\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				} else {
					ImageIcon thueringen = new ImageIcon("src\\gui\\resources\\Bundeslaender\\andere\\Thüringen.png");
					thueringen.paintIcon(this, g, 108, 150);
				}
			}
		}
	}
}

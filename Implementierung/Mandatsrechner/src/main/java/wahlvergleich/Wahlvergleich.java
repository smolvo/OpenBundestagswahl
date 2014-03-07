package main.java.wahlvergleich;

import java.util.Collections;
import java.util.LinkedList;

import main.java.model.Bundestagswahl;
import main.java.model.Partei;

/**
 * Diese Klasse implementiert den Vergleich zwischen zwei Bundestagswahlen.
 * 
 * @author Anton
 * 
 */
public class Wahlvergleich {

	private final Bundestagswahl btw1;
	private final Bundestagswahl btw2;

	/**
	 * Der Konstruktor initialisiert zwei Bundestagswahlen
	 * 
	 * @param btw1
	 *            Bundestagswahl 1
	 * @param btw2
	 *            Bundestagswahl 2
	 */
	public Wahlvergleich(Bundestagswahl btw1, Bundestagswahl btw2) {
		this.btw1 = btw1;
		this.btw2 = btw2;
	}

	/**
	 * gibt die Bundestagswahl 1 zurï¿½ck
	 * 
	 * @return die erste Bundestagswahl
	 */
	public Bundestagswahl getBtw1() {
		return this.btw1;
	}

	/**
	 * gibt die Bundestagswahl 2 zurï¿½ck
	 * 
	 * @return die zweite Bndestagswahl
	 */
	public Bundestagswahl getBtw2() {
		return this.btw2;
	}

	/**
	 * Diese Methode vergleich zwei Bundestagswahlen.
	 * 
	 * @return alle Daten, die in der Tabelle stehen sollen
	 */
	public WahlvergleichDaten wahlvergleich() {
		final WahlvergleichDaten daten = new WahlvergleichDaten();
		final LinkedList<Partei> wahl1Parteien = this.btw1.getParteien();
		final LinkedList<Partei> wahl2Parteien = this.btw2.getParteien();
		Collections.sort(wahl1Parteien);
		Collections.sort(wahl2Parteien);
		// suche die Parteien, die in beiden Wahlen dabei war
		for (final Partei parteiWahl1 : wahl1Parteien) {
			for (final Partei parteiWahl2 : wahl2Parteien) {
				if (parteiWahl1.getName().equals(parteiWahl2.getName())) {
					// Name der aktuell im Vergleich befindlichen Partei
					final String partei = parteiWahl1.getName();
					// Anzahl Erststimmen der ersten Partei
					final int anzahlEinsErst = this.btw1.getDeutschland()
							.getAnzahlErststimmen(parteiWahl1);
					// Anzahl der Erststimmen der zweiten Partei
					final int anzahlZweiErst = this.btw2.getDeutschland()
							.getAnzahlErststimmen(parteiWahl2);
					// errechnet die prozentuale Anzahl Erststimmen von Partei 1
					final double prozentEinsErst = Math
							.rint((double) anzahlEinsErst
									/ (double) this.btw1.getDeutschland()
											.getAnzahlErststimmen() * 1000) / 10;
					// errechnet die prozentuale Anzahl Erststimmen von Partei 2
					final double prozentZweiErst = Math
							.rint((double) anzahlZweiErst
									/ (double) this.btw2.getDeutschland()
											.getAnzahlErststimmen() * 1000) / 10;
					// errechnet die Differenz der Erststimmen
					final int diffErst = anzahlEinsErst - anzahlZweiErst;
					// Anzahl Zweitstimmen der ersten Partei
					final int anzahlEinsZweit = parteiWahl1
							.getZweitstimmeGesamt();
					// Anzahl Zweitstimmen der zweiten Partei
					final int anzahlZweiZweit = parteiWahl2
							.getZweitstimmeGesamt();
					// errechnet den prozentualen Anteil Zweitstimmen von Partei
					// 1
					final double prozentEinsZweit = Math
							.rint((double) anzahlEinsZweit
									/ (double) this.btw1.getDeutschland()
											.getAnzahlZweitstimmen() * 1000) / 10;
					// errechnet den prozentualen Anteil Zweitstimmen von Partei
					// 2
					final double prozentZweiZweit = Math
							.rint((double) anzahlZweiZweit
									/ (double) this.btw2.getDeutschland()
											.getAnzahlZweitstimmen() * 1000) / 10;
					// errechnet die Differenz der Zweitstimmen
					final int diffZweit = anzahlEinsZweit - anzahlZweiZweit;
					daten.addZeile(partei, Integer.toString(anzahlEinsErst),
							Double.toString(prozentEinsErst),
							Integer.toString(diffErst),
							Integer.toString(anzahlEinsZweit),
							Double.toString(prozentEinsZweit),
							Integer.toString(diffZweit),
							Integer.toString(anzahlZweiErst),
							Double.toString(prozentZweiErst),
							Integer.toString(anzahlZweiZweit),
							Double.toString(prozentZweiZweit));

				}
			}
		}
		return daten;
	}
}

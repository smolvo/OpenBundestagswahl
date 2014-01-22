package wahlvergleich;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.Bundestagswahl;
import model.Erststimme;
import model.Partei;
import model.Zweitstimme;

/**
 * Diese Klasse implementiert den Vergleich zwischen zwei
 * Bundestagswahlen.
 * @author Anton
 *
 */
public class Wahlvergleich {
	
	private Bundestagswahl btw1;
	private Bundestagswahl btw2;
	
	/**
	 * Der Konstruktor initialisiert die Differenzen.
	 */
	public Wahlvergleich(Bundestagswahl btw1, Bundestagswahl btw2) {
		this.btw1 = btw1;
		this.btw2 = btw2;
	}
	
	
	
	public Bundestagswahl getBtw1() {
		return btw1;
	}



	public Bundestagswahl getBtw2() {
		return btw2;
	}



	/**
	 * Diese Methode vergleich zwei Bundestagswahlen.
	 * @return alle Daten, die in der Tabelle stehen sollen
	 */
	public WahlvergleichDaten wahlvergleich() {
		WahlvergleichDaten daten = new WahlvergleichDaten();
		// Liste der Parteien aus Bundestagswahl 1
		LinkedList<Partei> wahl1Parteien = btw1.getParteien();
		// Liste der Parteien aus Bundestagswahl 2
		LinkedList<Partei> wahl2Parteien = btw2.getParteien();
		// Liste 1 wird alphabetisch sortiert
		Collections.sort(wahl1Parteien);
		for (Partei parteiWahl1: wahl1Parteien) {
			 for (Partei parteiWahl2: wahl2Parteien) {
				 if (parteiWahl1.getName().equals(parteiWahl2.getName())) {
					// Name der aktuell im Vergleich befindlichen Partei
					String partei = parteiWahl1.getName();
					// index der ersten Partei aus BTW 1
					
//					int indexErststimmePartei1 = btw1.getDeutschland().getErststimmen().indexOf(parteiWahl1);
					// index der zweiten Partei aus BTW2
//					int indexErststimmePartei2 = btw2.getDeutschland().getErststimmen().indexOf(parteiWahl2);
					// Anzahl Erststimmen der ersten Partei
					int anzahlEinsErst = btw1.getDeutschland().getErststimmenAnzahl(parteiWahl1);
					// Anzahl der Erststimmen der zweiten Partei
					int anzahlZweiErst = btw2.getDeutschland().getErststimmenAnzahl(parteiWahl2);
					// errechnet die prozentuale Anzahl Erststimmen von Partei 1
					double prozentEinsErst = (Math.rint(((double) anzahlEinsErst / (double) btw1.getDeutschland().getErststimmeGesamt()) * 1000) / 10);
					// errechnet die prozentuale Anzahl Erststimmen von Partei 2
					double prozentZweiErst = (Math.rint(((double) anzahlZweiErst / (double) btw2.getDeutschland().getErststimmeGesamt()) * 1000) / 10);
					// errechnet die Differenz der Erststimmen
					int diffErst = anzahlEinsErst - anzahlZweiErst;
					// Anzahl Zweitstimmen der ersten Partei
					int anzahlEinsZweit = parteiWahl1.getZweitstimmeGesamt();
					// Anzahl Zweitstimmen der zweiten Partei
					int anzahlZweiZweit = parteiWahl2.getZweitstimmeGesamt();
					// errechnet den prozentualen Anteil Zweitstimmen von Partei 1
					double prozentEinsZweit = (Math.rint(((double) anzahlEinsZweit / (double) btw1.getDeutschland().getZweitstimmeGesamt()) * 1000) / 10);
					// errechnet den prozentualen Anteil Zweitstimmen von Partei 2
					double prozentZweiZweit = (Math.rint(((double) anzahlZweiZweit / (double) btw2.getDeutschland().getZweitstimmeGesamt()) * 1000) / 10);
					// errechnet die Differenz der Zweitstimmen
					int diffZweit = anzahlEinsZweit - anzahlZweiZweit;
					
					daten.addZeile(partei,
							Integer.toString(anzahlEinsErst),
							Double.toString(prozentEinsErst),
							Integer.toString(diffErst),
							Integer.toString(anzahlZweiErst),
							Double.toString(prozentZweiErst),
							Integer.toString(anzahlEinsZweit),
							Double.toString(prozentEinsZweit),
							Integer.toString(diffZweit),
							Integer.toString(anzahlZweiZweit),
							Double.toString(prozentZweiZweit));
				 }
			 
		}
	}
		return daten;
}
}

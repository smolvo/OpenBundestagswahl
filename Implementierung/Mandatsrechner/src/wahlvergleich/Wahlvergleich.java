package wahlvergleich;

import java.util.LinkedList;
import java.util.List;

import model.Bundestagswahl;
import model.Partei;
import model.Zweitstimme;

/**
 * Diese Klasse implementiert den Vergleich zwischen zwei
 * Bundestagswahlen.
 * @author Anton
 *
 */
public class Wahlvergleich {

	/** repräsentiert alle Differenzen aller Parteien */
	private LinkedList<Parteidifferenz> differenzen;
	
	/**
	 * Der Konstruktor initialisiert die Differenzen.
	 */
	public Wahlvergleich() {
		this.differenzen = new LinkedList<Parteidifferenz>();
	}
	
	/**
	 * Diese Methode vergleich zwei Bundestagswahlen.
	 * @param btw1 erste Bundestagswahl
	 * @param btw2 zweite Bundestagswahl
	 * @return alle Daten, die in der Tabelle stehen sollen
	 */
	public WahlvergleichDaten wahlvergleich(Bundestagswahl btw1, Bundestagswahl btw2) {
		WahlvergleichDaten daten = new WahlvergleichDaten();
		List<Zweitstimme> parteien1 = btw1.getDeutschland().getZweitstimmen();
		partei1.sort();
		List<Zweitstimme> parteien2 = btw2.getDeutschland().getZweitstimmen();
		partei2.sort();
		for (Zweitstimme zw1 : parteien1) {
			Partei partei1 = zw1.getPartei();
			Partei partei2 = null;
			// suchen, ob die Partei an der zweiten Bundestagswahl teilgenommen hat
			for (Zweitstimme zw2 : parteien2) {
				if (partei1.equals(zw2.getPartei())) {
					partei2 = zw2.getPartei();
				}
			}
			String partei = partei1.getName();
			String anzahlEinsErst = zw1.getAnzahl();
			if (partei2 != null) {

			} else {
				
			}
		}
		
		daten.addZeile(partei, anzahlEinsErst, prozentEinsErst, diffErst, anzahlEinsZweit, prozentEinsZweit, diffZweit, anzahlZweiErst, prozentZweiErst, anzahlZweiZweit, prozentZweiZweit);
		return daten;
	}
}

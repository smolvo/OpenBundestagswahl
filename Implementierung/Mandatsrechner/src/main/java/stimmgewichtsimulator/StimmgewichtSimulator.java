package main.java.stimmgewichtsimulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import test.java.Debug;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse soll in der Lage sein zu einer gegebenen Bundestagswahl durch
 * Simulation den Effekt des negativen Stimmgewichts zu finden. Daf�r werden
 * Sprungstellen, insbesondere negative, gesucht, d.h. Stellen, an denen nach
 * Zweitstimmen�nderung die Mandatsanzahl "springt".
 * 
 */

public class StimmgewichtSimulator {

	/**
	 * Die Ausgangsbundestagswahl, zu der negatives Stimmgewicht gefunden werden
	 * soll
	 */
	private Bundestagswahl ausgangsWahl;

	/**
	 * Die erste deep-copy der Ausgangsbundestagswahl
	 */
	private Bundestagswahl kopie1;

	/**
	 * Die zweite deep-copy der Ausgangsbundestagswahl
	 */
	private Bundestagswahl kopie2;

	/**
	 * Generator f�r zuf�llige Zahlen
	 */
	private Random rand;

	/**
	 * Der Wahlkreis, in dem zuletzt Zweitstimmen ge�ndert wurden
	 */
	private Wahlkreis letzterWK;

	/**
	 * Die Partei, f�r die zuletzt Zweitstimmen ge�ndert wurden
	 */
	private Partei letztePartei;

	/**
	 * Der Wahlkreis, in dem zuletzt Zweitstimmen ge�ndert wurden
	 */
	private Bundesland letztesBundesland;

	/**
	 * Ein konstanter Faktor, um den Zweitstimmen erh�ht bzw. erniedrigt werden
	 * sollen
	 */
	private final int stimmanzahl = 1000;

	/**
	 * Eine Liste von Zweitstimmen, die w�hrend eines Simulationsschritt
	 * ge�ndert wurden
	 */

	private List<Zweitstimme> geaenderteZweitstimmenInWahlkreise;

	/**
	 * Eine Liste von Sprungstellen
	 */
	private List<Sprungstelle> sprungStellen;

	/**
	 * der Konstruktor der Klasse
	 * 
	 * @param ausgangsWahl
	 *            die Ausgangswahl
	 */
	public StimmgewichtSimulator(Bundestagswahl ausgangsWahl) {

		this.rand = new Random();
		this.sprungStellen = new ArrayList<>();
		this.geaenderteZweitstimmenInWahlkreise = new ArrayList<>();

		// Sitzverteilung f�r Ausgangswahl berechnen

		this.setAusgangsWahl(Mandatsrechner2009.getInstance()
				.berechneSainteLague(ausgangsWahl));

		// Die erste und zweite Kopie der Ausgangsbundestagswahl erzeugen
		try {
			this.setKopie1(ausgangsWahl.deepCopy());
			this.setKopie2(ausgangsWahl.deepCopy());
		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

	/**
	 * Es werden die Zweitstimmen einer relevanten Partei in einem geeigneten
	 * Bundesland schrittweise um einen konstanten Faktor erh�ht bzw.
	 * erniedrigt.
	 * 
	 * Dabei werden sie in beide Richtungen auf Bundeslandebene um maximal 20%
	 * variiert. Des Weiteren geschieht eine Erh�hung/ Erniedrigung auf
	 * Wahlkreisebe maximal solange, bis sie nicht mehr erh�ht werden k�nnen,
	 * weil sie gr��er als die Anzahl der Wahlberechtigten sind, bzw. nicht mehr
	 * erniedrigt werden k�nnen, weil sie kleiner als 0 w�ren.
	 * 
	 * Falls ein negatives Stimmgewicht im Vergleich zu der
	 * Ausgangsbundestagswahl ermittelt wurde, bricht die Suche ebenfalls ab
	 * 
	 * @return true wenn negatives Stimmgewicht aufgetreten ist
	 */
	public boolean berechneNegStimmgewicht() {

		LinkedList<Partei> parteien = this.getKopie1().getParteien();

		Collections.sort(parteien, Partei.NACH_UEBERHANGMANDATEN);

		// Iterieren �ber alle Parteien
		for (Partei partei : parteien) {
			letztePartei = partei;

			List<Bundesland> bundeslaender = this.getKopie1().getDeutschland()
					.getBundeslaender();
			// Iterieren �ber alle Bundesl�nder
			for (Bundesland bundesland : bundeslaender) {
				letztesBundesland = bundesland;

				// die Anzahl der Zweitstimmen werden pro Bundesland und Partei
				// um 20% variiert
				// setzt die obere Begrenzung f�r die Simulation
				int obereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 1.2);

				// setzt die untere Begrenzung f�r die Simulation
				int untereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 0.8);

				while (bundesland.getAnzahlZweitstimmen(partei) + stimmanzahl < obereBegrenzung) {

					// erh�ht die Zweitstimmen f�r gegebene Partei in gegebenem
					// Bundesland
					this.erhoeheZweitstimmen(kopie1, partei, bundesland);

					// check nach Sprungstellen
					this.vergleicheSitzverteilungen(partei, true);

					// passt Kopie2 an Kopie1 an
					this.passeKopie2An();
				}

				// setzt alle Erh�hungen, die an Zweitstimmen in Bundesl�ndern
				// vorgenommen wurden, zur�ck
				setzeKopienAufAusgangswahl(true);

				while (bundesland.getAnzahlZweitstimmen(partei) - stimmanzahl > untereBegrenzung) {

					this.erniedrigeZweitstimmen(kopie1, partei, bundesland);

					// check nach Sprungstellen
					this.vergleicheSitzverteilungen(partei, false);

					// passt Kopie2 an Kopie1 an
					this.passeKopie2An();
				}

				// setzt alle Erniedrigungen, die an Zweitstimmen in
				// Bundesl�ndern vorgenommen wurden, zur�ck
				setzeKopienAufAusgangswahl(false);

			}
		}

		return false;

	}

	/**
	 * Passt kopie2 an kopie1 an, d.h. kopiert die Werte von kopie1 in kopie2
	 */
	private void passeKopie2An() {

		// eigentlicher Kopiervorgang
		try {

			setKopie2(kopie1.deepCopy());

			Debug.print("Anpassung vorgenommen");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Setzt Kopie1 wieder auf ihre Ausgangswerte zur�ck. Wenn der Parameter der
	 * Methode true ist, wurden in der Simulation Zweitstimmen erh�ht, die jetzt
	 * wieder erniedrigt werden m�ssen. Ist der Parameter false wurden
	 * Zweitstimmen erniedrigt, die in dieser Methode wieder erh�ht werden
	 * m�ssen.
	 * 
	 * @param variante
	 *            boolean, der die Variante der zuvor genutzten Simulation
	 *            beschreibt
	 */
	private void setzeKopienAufAusgangswahl(boolean variante) {

		// vorher erh�hte Zweitstimmen werden jetzt erniedrigt
		if (variante) {
			for (int i = 0; i < geaenderteZweitstimmenInWahlkreise.size(); i++) {
				Zweitstimme z = geaenderteZweitstimmenInWahlkreise.get(i);

				z.erniedrigeAnzahl(stimmanzahl);

			}

			// vorher erniedrigte Zweitstimmen werden jetzt erh�ht
		} else {
			for (int i = 0; i < geaenderteZweitstimmenInWahlkreise.size(); i++) {
				Zweitstimme z = geaenderteZweitstimmenInWahlkreise.get(i);

				z.erhoeheAnzahl(stimmanzahl);

			}

		}

		// berechnet Sitzverteilung von kopie1 neu
		setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(kopie1));

		// passt kopie2 an kopie1 an
		this.passeKopie2An();

		geaenderteZweitstimmenInWahlkreise = new ArrayList<>();

	}

	/**
	 * Erh�ht die Zweitstimmen eines Bundeslands f�r eine Partei um einen
	 * konstanten Faktor und speichert die ge�nderteten Zweitstimmen in eine
	 * Liste
	 * 
	 * @param bundestagswahl
	 * @param partei
	 * @param bundesland
	 */
	private void erhoeheZweitstimmen(Bundestagswahl bundestagswahl,
			Partei partei, Bundesland bundesland) {
		Wahlkreis wk;

		do {
			// w�hlt einen zuf�lligen Wahlkreis solange, bis einer gefunden
			// worden ist,
			// dessen Zweitstimmen f�r gegebene Partei nach Addieren des
			// konstanten Faktors < Wahlberechtigte des Wahlkreises sind

			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);

		} while ((wk.getAnzahlZweitstimmen(partei) + stimmanzahl) > wk
				.getWahlberechtigte());

		// erh�he in dem zuvor zuf�llig gew�hlten Wahlkreis die Zweitstimme um
		// den konstanten Faktor
		wk.getZweitstimme(partei).erhoeheAnzahl(stimmanzahl);

		Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName()
				+ " (" + bundesland.getName() + ") um " + stimmanzahl + " auf "
				+ wk.getAnzahlZweitstimmen(partei) + " ("
				+ bundesland.getAnzahlZweitstimmen(partei) + ") erh�ht.");

		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				this.kopie1));

		letzterWK = wk;

		geaenderteZweitstimmenInWahlkreise.add(wk.getZweitstimme(partei));

	}

	/**
	 * Erniedrigt die Zweitstimmen eines Bundeslands f�r eine Partei um einen
	 * konstanten Faktor und speichert die ge�nderteten Zweitstimmen in eine
	 * Liste
	 * 
	 * @param bundestagswahl
	 * @param partei
	 * @param bundesland
	 */

	private void erniedrigeZweitstimmen(Bundestagswahl bundestagswahl,
			Partei partei, Bundesland bundesland) {
		Wahlkreis wk;

		do {
			// w�hlt einen zuf�lligen Wahlkreis solange, bis einer gefunden
			// worden ist,
			// dessen Zweitstimmen f�r gegebene Partei nach Abzug des konstanten
			// Faktors > 0 sind

			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);
		} while ((wk.getAnzahlZweitstimmen(partei) - stimmanzahl) < 0);

		// erniedrige in dem zuvor zuf�llig gew�hlten Wahlkreis die Zweitstimme
		// um den konstanten Faktor
		wk.getZweitstimme(partei).erniedrigeAnzahl(stimmanzahl);

		Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName()
				+ " (" + bundesland.getName() + ") um " + stimmanzahl + " auf "
				+ wk.getAnzahlZweitstimmen(partei) + " ("
				+ bundesland.getAnzahlZweitstimmen(partei) + ") erniedrigt.");

		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				this.kopie1));

		letzterWK = wk;

		geaenderteZweitstimmenInWahlkreise.add(wk.getZweitstimme(partei));

	}

	/**
	 * Berechnet die Mandatszahl f�r eine Partei f�r eine Bundestagswahl
	 * 
	 * @param p
	 *            die Partei
	 * @param w
	 *            die Bundestagswahl
	 * @return int Mandatszahl
	 */
	private int mandatsZahlBerechnen(Partei p, Bundestagswahl w) {
		int mandatsZahlAlt = 0;
		Sitzverteilung sitze = w.getSitzverteilung();

		for (Kandidat kandidat : sitze.getAbgeordnete()) {
			if (kandidat.getPartei().getName().equals(p.getName())) {
				mandatsZahlAlt++;
			}
		}
		return mandatsZahlAlt;

	}

	/**
	 * Vergleicht die Sitzverteilungen der beiden in Stimmgewicht gegebenen
	 * Bundestagswahlen. Wenn die Sitzanzahl f�r die gegebene Partei in der
	 * Ausgangsbundestagswahl gr��er ist als in der neu berechneten
	 * Bundestagswahl, tritt negativer Stimmeffekt auf und es wird false
	 * ausgegeben, andernfalls true
	 * 
	 * @param p
	 *            die Partei, deren Sitze betrachtet werden
	 * @return true, wenn negatives Stimmgewicht aufgetreten ist
	 */

	private void vergleicheSitzverteilungen(Partei p, boolean variante) {

		setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(kopie1));
		setKopie2(Mandatsrechner2009.getInstance().berechneSainteLague(kopie2));

		int mandatsZahlAlt = mandatsZahlBerechnen(p, this.kopie2);
		int mandatsZahlNeu = mandatsZahlBerechnen(p, this.kopie1);

		int ende = letzterWK.getZweitstimme(letztePartei).getAnzahl();
		int anfang = letzterWK.getZweitstimme(letztePartei).getAnzahl()
				- stimmanzahl;

		// Zweitstimmen wurden erh�ht
		if (variante) {

			// Sprungstelle aufgetreten?
			if (mandatsZahlNeu > mandatsZahlAlt) {
				// f�gt eine neue positive Sprungstelle hinzu
				Debug.print("----------> Positive Sprungstelle gefunden");
				fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
						Richtung.POSITIV);
			} else if (mandatsZahlNeu < mandatsZahlAlt) {
				// f�gt eine neue negative Sprungstelle hinzu
				Debug.print("----------> Negative Sprungstelle gefunden");
				fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
						Richtung.NEGATIV);
			}

			// Zweitstimmen wurden erniedrigt
		} else {
			// Sprungstelle aufgetreten?
			if (mandatsZahlNeu < mandatsZahlAlt) {
				// f�gt eine neue positive Sprungstelle hinzu
				Debug.print("----------> Positive Sprungstelle gefunden");
				fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
						Richtung.POSITIV);

			}

			else if (mandatsZahlNeu > mandatsZahlAlt) {
				// f�gt eine neue negative Sprungstelle hinzu
				Debug.print("----------> Negative Sprungstelle gefunden");
				fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
						Richtung.NEGATIV);
			}
		}

	}

	/**
	 * Berechnet zu gegebenen Grenzen genau die Anzahl an Zweitstimmen, bei der
	 * sich die Sprungstelle befindet
	 * 
	 * @param anfang
	 *            untere Grenze
	 * @param ende
	 *            obere Grenze
	 * @return int Anzahl an Zweitstimmen, die Sprungstelle ergeben
	 */

	private int getSprungstelle(int anfang, int ende) {
		int aenderung = (anfang + ende) / 2;

		Bundestagswahl kopie3 = null;
		try {
			kopie3 = kopie1.deepCopy();
		} catch (IOException e) {

			e.printStackTrace();
		}

		List<Partei> parteien = kopie3.getParteien();
		List<Wahlkreis> wahlkreise = kopie3.getDeutschland().getWahlkreise();
		for (Wahlkreis wk : wahlkreise) {
			for (Partei partei : parteien) {
				if (partei.getName().equals(letztePartei.getName())
						&& wk.getName().equals(letzterWK.getName())) {
					wk.getZweitstimme(partei).setAnzahl(aenderung);
				}
			}
		}

		kopie3 = Mandatsrechner2009.getInstance().berechneSainteLague(kopie3);

		int mandatsZahlAlt = mandatsZahlBerechnen(this.letztePartei,
				this.kopie2);
		int mandatsZahlNeu = mandatsZahlBerechnen(this.letztePartei, kopie3);

		// aenderung wird zum neuen Anfangswert
		if (mandatsZahlAlt > mandatsZahlNeu) {
			return getSprungstelle(aenderung, ende);

			// aenderung wird zum neuen Endwert
		} else if (mandatsZahlAlt < mandatsZahlNeu) {
			return getSprungstelle(anfang, aenderung);
			// Sprungstelle gefunden
		} else {
			return aenderung;
		}

	}

	/**
	 * Erstellt ein neues Sprungstellen-Objekt und f�gt es der
	 * Sprungstellenliste hinzu
	 * 
	 * @param anzahlZweitstimmen
	 * @param richtung
	 */

	private void fuegeSprungstelleHinzu(int anzahlZweitstimmen,
			Richtung richtung) {

		Sprungstelle s = new Sprungstelle(letztePartei, letztesBundesland,
				anzahlZweitstimmen, richtung);
		this.sprungStellen.add(s);
	}

	/**
	 * @return the Ausgangswahl
	 */
	public Bundestagswahl getAusgangsWahl() {
		return ausgangsWahl;
	}

	/**
	 * Setzt eine neue Ausgangswahl
	 * 
	 * @param ausgangsWahl
	 *            neue Ausgangswahl
	 */
	public void setAusgangsWahl(Bundestagswahl ausgangsWahl) {
		if (ausgangsWahl == null) {
			throw new IllegalArgumentException(
					"�bergebene Bundestagswahl war null");
		} else {
			this.ausgangsWahl = ausgangsWahl;
		}

	}

	/**
	 * @return the zweite Kopie
	 */
	public Bundestagswahl getKopie2() {
		return kopie2;
	}

	/**
	 * @param wahl
	 *            the wahl to set
	 */
	public void setKopie2(Bundestagswahl wahl) {
		if (wahl == null) {
			throw new IllegalArgumentException(
					"�bergebene Bundestagswahl war null");
		} else {
			this.kopie2 = wahl;
		}

	}

	/**
	 * @return the erste Kopie
	 */

	public Bundestagswahl getKopie1() {
		return kopie1;
	}

	/**
	 * @param wahl
	 *            the wahl to set
	 */
	public void setKopie1(Bundestagswahl wahl) {
		if (wahl == null) {
			throw new IllegalArgumentException(
					"�bergebene bundestagswahl war null.");
		} else {
			this.kopie1 = wahl;
		}

	}

}

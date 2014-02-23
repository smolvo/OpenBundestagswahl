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
 * Simulation den Effekt des negativen Stimmgewichts zu finden. Dafï¿½r werden
 * Sprungstellen, insbesondere negative, gesucht, d.h. Stellen, an denen nach
 * Zweitstimmenï¿½nderung die Mandatsanzahl "springt".
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
	 * Generator fï¿½r zufï¿½llige Zahlen
	 */
	private Random rand;

	/**
	 * Der Wahlkreis, in dem zuletzt Zweitstimmen geï¿½ndert wurden
	 */
	private Wahlkreis letzterWK;

	/**
	 * Die Partei, fï¿½r die zuletzt Zweitstimmen geï¿½ndert wurden
	 */
	private Partei letztePartei;

	/**
	 * Der Wahlkreis, in dem zuletzt Zweitstimmen geï¿½ndert wurden
	 */
	private Bundesland letztesBundesland;

	/**
	 * Ein konstanter Faktor, um den Zweitstimmen erhï¿½ht bzw. erniedrigt werden
	 * sollen
	 */
	private final int stimmanzahl = 1000;

	/**
	 * Eine Liste von Zweitstimmen, die wï¿½hrend eines Simulationsschritt
	 * geï¿½ndert wurden
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

		// Sitzverteilung fï¿½r Ausgangswahl berechnen

		Debug.setLevel(0);
		this.setAusgangsWahl(Mandatsrechner2009.getInstance()
				.berechneSainteLague(ausgangsWahl));
		Debug.setLevel(6);
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
	 * Bundesland schrittweise um einen konstanten Faktor erhï¿½ht bzw.
	 * erniedrigt.
	 * 
	 * Dabei werden sie in beide Richtungen auf Bundeslandebene um maximal 20%
	 * variiert. Des Weiteren geschieht eine Erhï¿½hung/ Erniedrigung auf
	 * Wahlkreisebe maximal solange, bis sie nicht mehr erhï¿½ht werden kï¿½nnen,
	 * weil sie grï¿½ï¿½er als die Anzahl der Wahlberechtigten sind, bzw. nicht mehr
	 * erniedrigt werden kï¿½nnen, weil sie kleiner als 0 wï¿½ren.
	 * 
	 * Falls ein negatives Stimmgewicht im Vergleich zu der
	 * Ausgangsbundestagswahl ermittelt wurde, bricht die Suche ebenfalls ab
	 * 
	 * @return true wenn negatives Stimmgewicht aufgetreten ist
	 */
	public boolean berechneNegStimmgewicht() {

		LinkedList<Partei> parteien = this.getKopie1().getParteien();

		Collections.sort(parteien, Partei.NACH_UEBERHANGMANDATEN);

		// Iterieren ï¿½ber alle Parteien
		for (Partei partei : parteien) {
			letztePartei = partei;

			if (!partei.getName().equals("CDU")) {
				continue;
			}

			List<Bundesland> bundeslaender = this.getKopie1().getDeutschland()
					.getBundeslaender();
			// Iterieren ï¿½ber alle Bundeslï¿½nder
			for (Bundesland bundesland : bundeslaender) {
				letztesBundesland = bundesland;

				if (!bundesland.getName().equals("Sachsen")) {
					continue;
				}
				// die Anzahl der Zweitstimmen werden pro Bundesland und Partei
				// um 20% variiert
				// setzt die obere Begrenzung fï¿½r die Simulation
				int obereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 1.4);

				// setzt die untere Begrenzung fï¿½r die Simulation
				int untereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 0.6);

				while (bundesland.getAnzahlZweitstimmen(partei) + stimmanzahl <= obereBegrenzung) {

					// erhï¿½ht die Zweitstimmen fï¿½r gegebene Partei in gegebenem
					// Bundesland
					this.erhoeheZweitstimmen(kopie1, partei, bundesland);

					// check nach Sprungstellen
					this.vergleicheSitzverteilungen(partei, true);

					// passt Kopie2 an Kopie1 an
					this.passeKopie2An();

					if (bundesland.getAnzahlZweitstimmen(partei) + stimmanzahl > obereBegrenzung) {
						Debug.print("Obere Grenze erreicht!", 5);
					}
				}

				// setzt alle Erhï¿½hungen, die an Zweitstimmen in Bundeslï¿½ndern
				// vorgenommen wurden, zurï¿½ck
				setzeKopienAufAusgangswahl(true);

				while (bundesland.getAnzahlZweitstimmen(partei) - stimmanzahl >= untereBegrenzung) {

					this.erniedrigeZweitstimmen(kopie1, partei, bundesland);

					// check nach Sprungstellen
					this.vergleicheSitzverteilungen(partei, false);

					// passt Kopie2 an Kopie1 an
					this.passeKopie2An();

					if (bundesland.getAnzahlZweitstimmen(partei) - stimmanzahl < untereBegrenzung) {
						Debug.print("Untere Grenze erreicht!", 5);
					}
				}

				// setzt alle Erniedrigungen, die an Zweitstimmen in
				// Bundeslï¿½ndern vorgenommen wurden, zurï¿½ck
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

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Setzt Kopie1 wieder auf ihre Ausgangswerte zurï¿½ck. Wenn der Parameter der
	 * Methode true ist, wurden in der Simulation Zweitstimmen erhï¿½ht, die jetzt
	 * wieder erniedrigt werden mï¿½ssen. Ist der Parameter false wurden
	 * Zweitstimmen erniedrigt, die in dieser Methode wieder erhï¿½ht werden
	 * mï¿½ssen.
	 * 
	 * @param variante
	 *            boolean, der die Variante der zuvor genutzten Simulation
	 *            beschreibt
	 */
	private void setzeKopienAufAusgangswahl(boolean variante) {

		// vorher erhï¿½hte Zweitstimmen werden jetzt erniedrigt
		if (variante) {
			for (int i = 0; i < geaenderteZweitstimmenInWahlkreise.size(); i++) {
				Zweitstimme z = geaenderteZweitstimmenInWahlkreise.get(i);

				z.erniedrigeAnzahl(stimmanzahl);

			}

			// vorher erniedrigte Zweitstimmen werden jetzt erhï¿½ht
		} else {
			for (int i = 0; i < geaenderteZweitstimmenInWahlkreise.size(); i++) {
				Zweitstimme z = geaenderteZweitstimmenInWahlkreise.get(i);

				z.erhoeheAnzahl(stimmanzahl);

			}

		}

		// berechnet Sitzverteilung von kopie1 neu
		Debug.setLevel(0);
		setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(kopie1));
		Debug.setLevel(6);
		// passt kopie2 an kopie1 an
		this.passeKopie2An();

		geaenderteZweitstimmenInWahlkreise = new ArrayList<>();

	}

	/**
	 * Erhï¿½ht die Zweitstimmen eines Bundeslands fï¿½r eine Partei um einen
	 * konstanten Faktor und speichert die geï¿½nderteten Zweitstimmen in eine
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
			// wï¿½hlt einen zufï¿½lligen Wahlkreis solange, bis einer gefunden
			// worden ist,
			// dessen Zweitstimmen fï¿½r gegebene Partei nach Addieren des
			// konstanten Faktors < Wahlberechtigte des Wahlkreises sind

			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);

		} while ((wk.getAnzahlZweitstimmen(partei) + stimmanzahl) > wk
				.getWahlberechtigte());

		// erhï¿½he in dem zuvor zufï¿½llig gewï¿½hlten Wahlkreis die Zweitstimme um
		// den konstanten Faktor
		wk.getZweitstimme(partei).erhoeheAnzahl(stimmanzahl);

		Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName()
				+ " (" + bundesland.getName() + ") um " + stimmanzahl + " auf "
				+ wk.getAnzahlZweitstimmen(partei) + " ("
				+ bundesland.getAnzahlZweitstimmen(partei) + ") erhï¿½ht."
				+ " Sitze: " + partei.getAnzahlMandate(), 5);

		Debug.setLevel(0);
		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				this.kopie1));
		Debug.setLevel(6);
		letzterWK = wk;

		geaenderteZweitstimmenInWahlkreise.add(wk.getZweitstimme(partei));

	}

	/**
	 * Erniedrigt die Zweitstimmen eines Bundeslands fï¿½r eine Partei um einen
	 * konstanten Faktor und speichert die geï¿½nderteten Zweitstimmen in eine
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
			// wï¿½hlt einen zufï¿½lligen Wahlkreis solange, bis einer gefunden
			// worden ist,
			// dessen Zweitstimmen fï¿½r gegebene Partei nach Abzug des konstanten
			// Faktors > 0 sind

			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);
		} while ((wk.getAnzahlZweitstimmen(partei) - stimmanzahl) < 0);

		// erniedrige in dem zuvor zufï¿½llig gewï¿½hlten Wahlkreis die Zweitstimme
		// um den konstanten Faktor
		wk.getZweitstimme(partei).erniedrigeAnzahl(stimmanzahl);

		Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName()
				+ " (" + bundesland.getName() + ") um " + stimmanzahl + " auf "
				+ wk.getAnzahlZweitstimmen(partei) + " ("
				+ bundesland.getAnzahlZweitstimmen(partei) + ") erniedrigt."
				+ " Sitze: " + partei.getAnzahlMandate(), 5);

		Debug.setLevel(0);
		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				this.kopie1));
		Debug.setLevel(6);

		letzterWK = wk;

		geaenderteZweitstimmenInWahlkreise.add(wk.getZweitstimme(partei));

	}

	/**
	 * Berechnet die Mandatszahl fï¿½r eine Partei fï¿½r eine Bundestagswahl
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
	 * Bundestagswahlen. Wenn die Sitzanzahl fï¿½r die gegebene Partei in der
	 * Ausgangsbundestagswahl grï¿½ï¿½er ist als in der neu berechneten
	 * Bundestagswahl, tritt negativer Stimmeffekt auf und es wird false
	 * ausgegeben, andernfalls true
	 * 
	 * @param p
	 *            die Partei, deren Sitze betrachtet werden
	 * @return true, wenn negatives Stimmgewicht aufgetreten ist
	 */

	private void vergleicheSitzverteilungen(Partei p, boolean variante) {

		Debug.setLevel(0);
		setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(kopie1));
		setKopie2(Mandatsrechner2009.getInstance().berechneSainteLague(kopie2));
		Debug.setLevel(6);

		int mandatsZahlAlt = mandatsZahlBerechnen(p, this.kopie2);
		int mandatsZahlNeu = mandatsZahlBerechnen(p, this.kopie1);

		int ende = letzterWK.getZweitstimme(letztePartei).getAnzahl();
		int anfang = letzterWK.getZweitstimme(letztePartei).getAnzahl()
				- stimmanzahl;

		// Zweitstimmen wurden erhï¿½ht
		if (variante) {

			// Sprungstelle aufgetreten?
			if (mandatsZahlNeu > mandatsZahlAlt) {
				// fï¿½gt eine neue positive Sprungstelle hinzu
				// Debug.print("----------> Positive Sprungstelle gefunden");
				System.err
						.println("----------> Positive Sprungstelle gefunden");
				// fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
				// Richtung.POSITIV);
			} else if (mandatsZahlNeu < mandatsZahlAlt) {
				// fï¿½gt eine neue negative Sprungstelle hinzu
				// Debug.print("----------> Negative Sprungstelle gefunden");
				System.err
						.println("----------> Negative Sprungstelle gefunden");
				// fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
				// Richtung.NEGATIV);
			}

			// Zweitstimmen wurden erniedrigt
		} else {
			// Sprungstelle aufgetreten?
			if (mandatsZahlNeu < mandatsZahlAlt) {
				// fï¿½gt eine neue positive Sprungstelle hinzu
				// Debug.print("----------> Positive Sprungstelle gefunden");
				System.err
						.println("----------> Positive Sprungstelle gefunden");
				// fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
				// Richtung.POSITIV);

			}

			else if (mandatsZahlNeu > mandatsZahlAlt) {
				// fï¿½gt eine neue negative Sprungstelle hinzu
				// Debug.print("----------> Negative Sprungstelle gefunden");
				System.err
						.println("----------> Negative Sprungstelle gefunden");
				// fuegeSprungstelleHinzu(getSprungstelle(anfang, ende),
				// Richtung.NEGATIV);
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

		Debug.setLevel(0);
		kopie3 = Mandatsrechner2009.getInstance().berechneSainteLague(kopie3);
		Debug.setLevel(6);

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
	 * Erstellt ein neues Sprungstellen-Objekt und fï¿½gt es der
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
					"ï¿½bergebene Bundestagswahl war null");
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
					"ï¿½bergebene Bundestagswahl war null");
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
					"ï¿½bergebene bundestagswahl war null.");
		} else {
			this.kopie1 = wahl;
		}

	}

}

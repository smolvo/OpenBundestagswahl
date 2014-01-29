package main.java.wahlgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;
import test.java.Debug;

/**
 * Diese Klasse soll in der Lage sein zu einer gegebenen Bundestagswahl durch
 * Simulation den Effekt des negativen Stimmgewichts zu finden. Dafür werden
 * Sprungstellen, insbesondere negative, gesucht, d.h. Stellen, an denen nach
 * Zweitstimmenänderung die Mandatsanzahl "springt".
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
	 * Generator für zufällige Zahlen
	 */
	private Random rand;

	/**
	 * Der Wahlkreis, in dem zuletzt Zweitstimmen geändert wurden
	 */
	private Wahlkreis letzterWK;

	/**
	 * Die Partei, für die zuletzt Zweitstimmen geändert wurden
	 */
	private Partei letztePartei;

	/**
	 * Der Wahlkreis, in dem zuletzt Zweitstimmen geändert wurden
	 */
	private Bundesland letztesBundesland;

	/**
	 * Ein konstanter Faktor, um den Zweitstimmen erhöht bzw. erniedrigt werden
	 * sollen
	 */
	private final int stimmanzahl = 1000;

	/**
	 * Eine Liste von Zweitstimmen, die während eines Simulationsschritt
	 * geändert wurden
	 */

	private List<Zweitstimme> geaenderteZweitstimmenInWahlkreise;

	/**
	 * der Konstruktor der Klasse
	 * 
	 * @param ausgangsWahl
	 *            die Ausgangswahl
	 */
	public StimmgewichtSimulator(Bundestagswahl ausgangsWahl) {

		this.rand = new Random();
		geaenderteZweitstimmenInWahlkreise = new ArrayList<>();

		// Sitzverteilung für Ausgangswahl berechnen

		this.setAusgangsWahl(Mandatsrechner2009.getInstance()
				.berechneSainteLague(ausgangsWahl));

		// Die erste und zweite Kopie der Ausgangsbundestagswahl erzeugen
		try {
			this.setKopie1(ausgangsWahl.deepCopy());
			this.setKopie2(ausgangsWahl.deepCopy());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * Es werden die Zweitstimmen einer relevanten Partei in einem geeigneten
	 * Bundesland schrittweise um einen konstanten Faktor erhöht bzw.
	 * erniedrigt.
	 * 
	 * Dabei werden sie in beide Richtungen auf Bundeslandebene um maximal 20%
	 * variiert. Des Weiteren geschieht eine Erhöhung/ Erniedrigung auf
	 * Wahlkreisebe maximal solange, bis sie nicht mehr erhöht werden können,
	 * weil sie größer als die Anzahl der Wahlberechtigten sind, bzw. nicht mehr
	 * erniedrigt werden können, weil sie kleiner als 0 wären.
	 * 
	 * Falls ein negatives Stimmgewicht im Vergleich zu der
	 * Ausgangsbundestagswahl ermittelt wurde, bricht die Suche ebenfalls ab
	 * 
	 * @return true wenn negatives Stimmgewicht aufgetreten ist
	 */
	public boolean berechneNegStimmgewicht() {

		// sortiert die Parteien nach Anzahl ihrer Überhangmandate
		LinkedList<Partei> parteienSortiertKopie1 = this.getKopie1()
				.getParteien();

		Collections.sort(parteienSortiertKopie1, Partei.NACH_UEBERHANGMANDATEN);

		// Iterieren über alle Parteien
		for (Partei partei : parteienSortiertKopie1) {
			letztePartei = partei;

			List<Bundesland> bundeslaender = this.getKopie1().getDeutschland()
					.getBundeslaender();
			// Iterieren über alle Bundesländer
			for (Bundesland bundesland : bundeslaender) {
				letztesBundesland = bundesland;

				// die Anzahl der Zweitstimmen werden pro Bundesland und Partei
				// um 20% variiert
				// setzt die obere Begrenzung für die Simulation
				int obereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 1.2);

				// setzt die untere Begrenzung für die Simulation
				int untereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 0.8);

				while (bundesland.getAnzahlZweitstimmen(partei) + stimmanzahl < obereBegrenzung) {

					// erhöht die Zweitstimmen für gegebene Partei in gegebenem
					// Bundesland
					this.erhoeheZweitstimmen(kopie1, partei, bundesland);

					// check auf negatives Stimmgewicht

					if (this.vergleicheSitzverteilungen(partei)) {

						Debug.print("Negatives Stimmgewicht gefunden");
						return true;
					}

					// passt Kopie2 an Kopie1 an
					this.passeKopie2An();
				}

				// setzt alle in einem Simulationsschritt vorgenommenen
				// Änderungen an Zweitstimmen
				// wieder zurück auf ihre ursprünglichen Werte
				setzeKopienAufAusgangswahl(true);

				while (bundesland.getAnzahlZweitstimmen(partei) - stimmanzahl > untereBegrenzung) {

					this.erniedrigeZweitstimmen(kopie1, partei, bundesland);

					// check auf negatives Stimmgewicht

					if (this.vergleicheSitzverteilungen2(partei)) {

						System.err
								.println("NEGATIVES STIMMGEWICHT GEFUNDEN !!!!!!!!!!!! YAYYY");
						return true;
					}

					// passt Kopie2 an Kopie1 an
					this.passeKopie2An();
				}

				// setzt alle in einem Simulationsschritt vorgenommenen
				// Änderungen an Zweitstimmen
				// wieder zurück auf ihre ursprünglichen Werte
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
			kopie2 = kopie1.deepCopy();
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

	}

	/**
	 * Setzt Kopie1 wieder auf ihre Ausgangswerte zurück. Wenn der Parameter der
	 * Methode true ist, wurden in der Simulation Zweitstimmen erhöht, die jetzt
	 * wieder erniedrigt werden müssen. Ist der Parameter false wurden
	 * Zweitstimmen erniedrigt, die in dieser Methode wieder erhöht werden
	 * müssen.
	 * 
	 * @param variante
	 *            boolean, der die Variante der zuvor genutzten Simulation
	 *            beschreibt
	 */
	private void setzeKopienAufAusgangswahl(boolean variante) {

		// vorher erhöhte Zweitstimmen werden jetzt erniedrigt
		if (variante) {
			for (int i = 0; i < geaenderteZweitstimmenInWahlkreise.size(); i++) {
				Zweitstimme z = geaenderteZweitstimmenInWahlkreise.get(i);

				z.erniedrigeAnzahl(stimmanzahl);

			}

			// vorher erniedrigte Zweitstimmen werden jetzt erhöht
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
	 * Erhöht die Zweitstimmen eines Bundeslands für eine Partei um einen
	 * konstanten Faktor und speichert die geänderteten Zweitstimmen in eine
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
			// wählt einen zufälligen Wahlkreis solange, bis einer gefunden
			// worden ist,
			// dessen Zweitstimmen für gegebene Partei nach Addieren des
			// konstanten Faktors < Wahlberechtigte des Wahlkreises sind

			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);

		} while ((wk.getAnzahlZweitstimmen(partei) + stimmanzahl) > wk
				.getWahlberechtigte());

		// erhöhe in dem zuvor zufällig gewählten Wahlkreis die Zweitstimme um
		// den konstanten Faktor
		wk.getZweitstimme(partei).erhoeheAnzahl(stimmanzahl);

		/*
		 * Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName() +
		 * " (" + bundesland.getName() + ") um " + stimmanzahl + " auf " +
		 * wk.getAnzahlZweitstimmen(partei) + " (" +
		 * bundesland.getAnzahlZweitstimmen(partei) + ") erhöht.");
		 */

		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				this.kopie1));

		letzterWK = wk;

		geaenderteZweitstimmenInWahlkreise.add(wk.getZweitstimme(partei));

	}

	/**
	 * Erniedrigt die Zweitstimmen eines Bundeslands für eine Partei um einen
	 * konstanten Faktor und speichert die geänderteten Zweitstimmen in eine
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
			// wählt einen zufälligen Wahlkreis solange, bis einer gefunden
			// worden ist,
			// dessen Zweitstimmen für gegebene Partei nach Abzug des konstanten
			// Faktors > 0 sind

			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);
		} while ((wk.getAnzahlZweitstimmen(partei) - stimmanzahl) < 0);

		// erniedrige in dem zuvor zufällig gewählten Wahlkreis die Zweitstimme
		// um den konstanten Faktor
		wk.getZweitstimme(partei).erniedrigeAnzahl(stimmanzahl);

		/*
		 * Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName() +
		 * " (" + bundesland.getName() + ") um " + stimmanzahl + " auf " +
		 * wk.getAnzahlZweitstimmen(partei) + " (" +
		 * bundesland.getAnzahlZweitstimmen(partei) + ") erniedrigt.");
		 */

		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				this.kopie1));

		letzterWK = wk;

		geaenderteZweitstimmenInWahlkreise.add(wk.getZweitstimme(partei));

	}

	/**
	 * Vergleicht die Sitzverteilungen der beiden in Stimmgewicht gegebenen
	 * Bundestagswahlen. Wenn die Sitzanzahl für die gegebene Partei in der
	 * Ausgangsbundestagswahl größer ist als in der neu berechneten
	 * Bundestagswahl, tritt negativer Stimmeffekt auf und es wird false
	 * ausgegeben, andernfalls true
	 * 
	 * @param p
	 *            die Partei, deren Sitze betrachtet werden
	 * @return true, wenn negatives Stimmgewicht aufgetreten ist
	 */

	// TODO private setzen, nur zum testen public
	private boolean vergleicheSitzverteilungen(Partei p) {

		Sitzverteilung neu = this.kopie1.getSitzverteilung();
		Sitzverteilung alt = this.kopie2.getSitzverteilung();

		int mandatsZahlAlt = 0;
		for (Kandidat kandidatAlt : alt.getAbgeordnete()) {
			if (kandidatAlt.getPartei().getName().equals(p.getName())) {
				mandatsZahlAlt++;
			}
		}

		int mandatsZahlNeu = 0;
		for (Kandidat kandidatNeu : neu.getAbgeordnete()) {
			if (kandidatNeu.getPartei().getName().equals(p.getName())) {
				mandatsZahlNeu++;
			}
		}
		System.out.println(p.getName() + " " + letztesBundesland.getName()
				+ ": Mandatszahl alt: " + mandatsZahlAlt + " Mandatszahl neu: "
				+ p.getAnzahlMandate());
		if (mandatsZahlNeu > mandatsZahlAlt) {
			System.err.println("Positive Sprungstelle entdeckt: "
					+ letztePartei.getName() + " " + letzterWK.getName() + " "
					+ letztesBundesland.getName());

			int ende = letztesBundesland.getAnzahlZweitstimmen(letztePartei);
			letzterWK.getZweitstimme(letztePartei)
					.erniedrigeAnzahl(stimmanzahl);
			int anfang = letztesBundesland.getAnzahlZweitstimmen(letztePartei);
			System.out.println("Anfang: " + anfang + " Ende: " + ende);
			getSprungstelle(anfang, ende);
		}
		return (mandatsZahlNeu < mandatsZahlAlt);

	}

	private boolean vergleicheSitzverteilungen2(Partei p) {

		Sitzverteilung neu = this.kopie1.getSitzverteilung();
		Sitzverteilung alt = this.kopie2.getSitzverteilung();

		int mandatsZahlAlt = 0;
		for (Kandidat kandidatAlt : alt.getAbgeordnete()) {
			if (kandidatAlt.getPartei().getName().equals(p.getName())) {
				mandatsZahlAlt++;
			}
		}

		int mandatsZahlNeu = 0;
		for (Kandidat kandidatNeu : neu.getAbgeordnete()) {
			if (kandidatNeu.getPartei().getName().equals(p.getName())) {
				mandatsZahlNeu++;
			}
		}
		System.out.println(p.getName() + ": Mandatszahl alt: " + mandatsZahlAlt
				+ " Mandatszahl neu: " + p.getAnzahlMandate());
		if (mandatsZahlNeu < mandatsZahlAlt) {
			System.err.println("Positive Sprungstelle entdeckt: "
					+ letztePartei.getName() + " " + letzterWK.getName() + " "
					+ letztesBundesland.getName());

			int ende = letztesBundesland.getAnzahlZweitstimmen(letztePartei);
			letzterWK.getZweitstimme(letztePartei)
					.erniedrigeAnzahl(stimmanzahl);
			int anfang = letztesBundesland.getAnzahlZweitstimmen(letztePartei);
			System.out.println("Anfang: " + anfang + " Ende: " + ende);
			// +getSprungstelle(anfang, ende));
		}
		return (mandatsZahlNeu > mandatsZahlAlt);

	}

	private int getSprungstelle(int anfang, int ende) {
		// System.out.println("Sprungstelle wird berechnet...");
		int aenderung = (anfang + ende) / 2;

		letzterWK.getZweitstimme(letztePartei).setAnzahl(aenderung);
		Sitzverteilung neu = this.kopie1.getSitzverteilung();
		Sitzverteilung alt = this.kopie2.getSitzverteilung();
		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				kopie1));

		int mandatsZahlAlt = 0;
		for (Kandidat kandidatAlt : alt.getAbgeordnete()) {
			if (kandidatAlt.getPartei().getName()
					.equals(letztePartei.getName())) {
				mandatsZahlAlt++;
			}
		}

		int mandatsZahlNeu = 0;
		for (Kandidat kandidatNeu : neu.getAbgeordnete()) {
			if (kandidatNeu.getPartei().getName()
					.equals(letztePartei.getName())) {
				mandatsZahlNeu++;
			}
		}

		System.err.println(aenderung + " MandatszahlAlt: " + mandatsZahlAlt
				+ " MandatszahlNeu: " + mandatsZahlNeu);
		if (mandatsZahlAlt > mandatsZahlNeu) {
			return getSprungstelle(aenderung, ende);

		} else if (mandatsZahlAlt < mandatsZahlNeu) {
			return getSprungstelle(anfang, aenderung);
		} else {
			return aenderung;
		}

	}

	/**
	 * @return the bw
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
					"Übergebene Bundestagswahl war null");
		} else {
			this.ausgangsWahl = ausgangsWahl;
		}

	}

	/**
	 * @return the verwandteWahl
	 */
	public Bundestagswahl getKopie2() {
		return kopie2;
	}

	/**
	 * @param verwandteWahl
	 *            the verwandteWahl to set
	 */
	public void setKopie2(Bundestagswahl verwandteWahl) {
		if (verwandteWahl == null) {
			throw new IllegalArgumentException(
					"Übergebene Bundestagswahl war null");
		} else {
			this.kopie2 = verwandteWahl;
		}

	}

	public Bundestagswahl getKopie1() {
		return kopie1;
	}

	public void setKopie1(Bundestagswahl wahlKopie) {
		if (wahlKopie == null) {
			throw new IllegalArgumentException(
					"Übergebene bundestagswahl war null.");
		} else {
			this.kopie1 = wahlKopie;
		}

	}

}

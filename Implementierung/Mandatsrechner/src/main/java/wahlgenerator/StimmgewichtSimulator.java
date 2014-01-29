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
	 * der Konstruktor der Klasse
	 * 
	 * @param ausgangsWahl
	 *            die Ausgangswahl
	 */
	public StimmgewichtSimulator(Bundestagswahl ausgangsWahl) {

		this.rand = new Random();
		geaenderteZweitstimmenInWahlkreise = new ArrayList<>();

		// Sitzverteilung f�r Ausgangswahl berechnen

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

		// sortiert die Parteien nach Anzahl ihrer �berhangmandate
		LinkedList<Partei> parteienSortiertKopie1 = this.getKopie1()
				.getParteien();

		Collections.sort(parteienSortiertKopie1, Partei.NACH_UEBERHANGMANDATEN);

		// Iterieren �ber alle Parteien
		for (Partei partei : parteienSortiertKopie1) {
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

					// check auf negatives Stimmgewicht

					if (this.vergleicheSitzverteilungen(partei)) {

						Debug.print("Negatives Stimmgewicht gefunden");
						return true;
					}

					// passt Kopie2 an Kopie1 an
					this.passeKopie2An();
				}

				// setzt alle in einem Simulationsschritt vorgenommenen
				// �nderungen an Zweitstimmen
				// wieder zur�ck auf ihre urspr�nglichen Werte
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
				// �nderungen an Zweitstimmen
				// wieder zur�ck auf ihre urspr�nglichen Werte
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

		/*
		 * Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName() +
		 * " (" + bundesland.getName() + ") um " + stimmanzahl + " auf " +
		 * wk.getAnzahlZweitstimmen(partei) + " (" +
		 * bundesland.getAnzahlZweitstimmen(partei) + ") erh�ht.");
		 */

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
	 * Bundestagswahlen. Wenn die Sitzanzahl f�r die gegebene Partei in der
	 * Ausgangsbundestagswahl gr��er ist als in der neu berechneten
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
					"�bergebene Bundestagswahl war null");
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
					"�bergebene Bundestagswahl war null");
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
					"�bergebene bundestagswahl war null.");
		} else {
			this.kopie1 = wahlKopie;
		}

	}

}

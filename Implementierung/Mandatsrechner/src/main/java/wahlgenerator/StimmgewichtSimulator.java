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
import main.java.model.Landesliste;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;
import test.java.Debug;

/**
 * Diese Klasse soll in der Lage sein zu einer Bundestagswahl durch Simulation
 * eine "verwandte" Bundestagswahl in der Art zu erzeugen, dass zwischen diesen
 * zwei Bundestagswahlen der Effekt des negativen Stimmgewichts auftritt
 * 
 */

public class StimmgewichtSimulator {

	/**
	 * Die Ausgangsbundestagswahl, zu der eine "verwandte" Wahl erzeugt werden
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
	 * Parteien, bei denen negatives Stimmgewicht auftreten kann
	 */
	private List<Partei> relevanteParteien = new ArrayList<Partei>();

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

		// Mandate für Ausgangswahl berechnen
		Debug.setAktiv(false);
		this.setAusgangsWahl(Mandatsrechner2009.getInstance()
				.berechneSainteLague(ausgangsWahl));
		Debug.setAktiv(true);
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
	 * erniedrigt. Dies geschieht maximal solange bis sie nicht mehr erhöht
	 * werden können, weil sie größer als die Anzahl der Wahlberechtigten sind,
	 * bzw. nicht mehr erniedrigt werden können, weil sie kleiner 0 wären. Falls
	 * ein negatives Stimmgewicht im Vergleich zu der Ausgangsbundestagswahl
	 * ermittelt wurde, bricht die Suche ebenfalls ab
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
			// if (!partei.getName().equals("SPD")
			// && !partei.getName().equals("CDU")) {
			// continue;
			// }

			if (!partei.getName().equals("SPD")) {
				continue;
			}

			List<Bundesland> bundeslaender = this.getKopie1().getDeutschland()
					.getBundeslaender();
			// Iterieren über alle Bundesländer
			for (Bundesland bundesland : bundeslaender) {
				// TODO entfernen wenn alles funktioniert
				if (!bundesland.getName().equals("Hamburg")) {
					continue;
				}

				// if (!bundesland.getName().equals("Brandenburg") &&
				// !bundesland.getName().equals("Bayern")) {
				// continue;
				// }

				// die Anzahl der Zweitstimmen werden pro Bundesland und Partei
				// um 20% variiert
				// setzt die obere Begrenzung für die Simulation
				int obereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 1.1);

				// setzt die untere Begrenzung für die Simulation
				int untereBegrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 0.9);

				while (bundesland.getAnzahlZweitstimmen(partei) + stimmanzahl < obereBegrenzung) {

					// erhöht die Zweitstimmen für gegebene Partei in gegebenem
					// Bundesland
					this.erhoeheZweitstimmen(kopie1, partei, bundesland);

					// check auf negatives Stimmgewicht

					if (this.vergleicheSitzverteilungen(partei)) {

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
				System.err.println("AUF AUSGANGSWAHL ZURÜCKSETZEN");
				setzeKopienAufAusgangswahl(true);

				Debug.print(partei.getName() + " " + bundesland.getName() + " "
						+ bundesland.getAnzahlZweitstimmen(partei));

				Debug.setAktiv(false);
				// berechnet Sitzverteilung von kopie1 neu
				setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
						kopie1));
				Debug.setAktiv(true);
				this.passeKopie2An();

				System.err.println("ERNIEDRIGUNG BEGINNT");

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
				System.err.println("AUF AUSGANGSWAHL ZURÜCKSETZEN");
				setzeKopienAufAusgangswahl(false);

				Debug.print(partei.getName() + " " + bundesland.getName() + " "
						+ bundesland.getAnzahlZweitstimmen(partei));

				Debug.setAktiv(false);
				// berechnet Sitzverteilung von kopie1 neu
				setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
						kopie1));
				Debug.setAktiv(true);
				this.passeKopie2An();

			}
		}

		return false;

	}

	/**
	 * Passt kopie2 an kopie1 an, d.h. kopiert die Werte von kopie1 in kopie2
	 */
	private void passeKopie2An() {

		// Debug-Informationen
		Debug.setAktiv(false);

		for (Wahlkreis wk1 : this.kopie1.getDeutschland().getWahlkreise()) {
			if (wk1.getName().equals(letzterWK.getName())) {
				Debug.print("A WK1: " + wk1.getName() + " Stimmen: "
						+ wk1.getAnzahlZweitstimmen());
			}
		}
		for (Wahlkreis wk2 : this.kopie2.getDeutschland().getWahlkreise()) {
			if (wk2.getName().equals(letzterWK.getName())) {
				Debug.print("A WK2: " + wk2.getName() + " Stimmen: "
						+ wk2.getAnzahlZweitstimmen());
			}
		}

		// eigentlicher Kopiervorgang
		try {
			kopie2 = kopie1.deepCopy();
		} catch (IOException e) { // TODO Auto-generated
			e.printStackTrace();
		}

		// Debug-Informationen
		for (Wahlkreis wk1 : this.kopie1.getDeutschland().getWahlkreise()) {
			if (wk1.getName().equals(letzterWK.getName())) {
				Debug.print("B WK1: " + wk1.getName() + " Stimmen: "
						+ wk1.getAnzahlZweitstimmen());
			}
		}
		for (Wahlkreis wk2 : this.kopie2.getDeutschland().getWahlkreise()) {
			if (wk2.getName().equals(letzterWK.getName())) {
				Debug.print("B WK2: " + wk2.getName() + " Stimmen: "
						+ wk2.getAnzahlZweitstimmen());
			}
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
	 *            boolean, der die Variante der genutzten Simulation beschreibt
	 */
	private void setzeKopienAufAusgangswahl(boolean variante) {
		// Ausgabe von Debug-Informationen
		Debug.setAktiv(true);

		for (int i = 0; i < geaenderteZweitstimmenInWahlkreise.size(); i++) {
			Zweitstimme z = geaenderteZweitstimmenInWahlkreise.get(i);

			Debug.print("Partei vor Zurücksetzung " + z.getPartei().getName()
					+ " " + z.getGebiet().getName() + " " + z.getAnzahl());
		}

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

		// Ausgabe von Debug-Informationen
		for (int i = 0; i < geaenderteZweitstimmenInWahlkreise.size(); i++) {
			Zweitstimme z = geaenderteZweitstimmenInWahlkreise.get(i);

			Debug.print("Partei nach Zurücksetzung: " + z.getPartei().getName()
					+ " " + z.getGebiet().getName() + " " + z.getAnzahl());
		}

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
		Debug.setAktiv(true);

		Wahlkreis wk;

		do {
			// wählt einen zufälligen Wahlkreis
			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);

		} while ((wk.getAnzahlZweitstimmen() + stimmanzahl) > wk
				.getWahlberechtigte());

		wk.getZweitstimme(partei).erhoeheAnzahl(stimmanzahl);

		Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName()
				+ " (" + bundesland.getName() + ") um " + stimmanzahl + " auf "
				+ wk.getAnzahlZweitstimmen(partei) + " ("
				+ bundesland.getAnzahlZweitstimmen(partei) + ") erhöht.");

		Debug.setAktiv(false);
		this.setKopie1(Mandatsrechner2009.getInstance().berechneSainteLague(
				this.kopie1));

		letzterWK = wk;

		geaenderteZweitstimmenInWahlkreise.add(wk.getZweitstimme(partei));

	}

	/*
	 * private void veraendereZweitstimmen(Bundestagswahl bundestagswahl, Partei
	 * partei, Bundesland bundesland, boolean variante) { Debug.setAktiv(true);
	 * Wahlkreis wk; if (variante) { do { // wählt einen zufälligen Wahlkreis
	 * int i = rand.nextInt(bundesland.getWahlkreise().size()); wk =
	 * bundesland.getWahlkreise().get(i);
	 * 
	 * } while ((wk.getAnzahlZweitstimmen() + stimmanzahl) > wk
	 * .getWahlberechtigte());
	 * 
	 * wk.getZweitstimme(partei).erhoeheAnzahl(stimmanzahl);
	 * 
	 * Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName() + " ("
	 * + bundesland.getName() + ") um " + stimmanzahl + " auf " +
	 * wk.getAnzahlZweitstimmen(partei) + " (" +
	 * bundesland.getAnzahlZweitstimmen(partei) + ") erhöht."); } else {
	 */
	private void erniedrigeZweitstimmen(Bundestagswahl bundestagswahl,
			Partei partei, Bundesland bundesland) {
		Debug.setAktiv(true);
		Wahlkreis wk;
		do { //
				// wählt einen zufälligen Wahlkreis
			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);
		} while ((wk.getAnzahlZweitstimmen(partei) - stimmanzahl) < 0);

		wk.getZweitstimme(partei).erniedrigeAnzahl(stimmanzahl);

		Debug.print(partei.getName() + ": Zweitstimmen in " + wk.getName()
				+ " (" + bundesland.getName() + ") um " + stimmanzahl + " auf "
				+ wk.getAnzahlZweitstimmen(partei) + " ("
				+ bundesland.getAnzahlZweitstimmen(partei) + ") erniedrigt.");

		Debug.setAktiv(false);
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
		System.out.println(p.getName() + ": Mandatszahl alt: " + mandatsZahlAlt
				+ " Mandatszahl neu: " + p.getAnzahlMandate());
		if (mandatsZahlNeu > mandatsZahlAlt) {
			System.err.println("Positive Sprungstelle entdeckt: "
					+ letztePartei.getName() + " " + letzterWK.getName() + " "
					+ letzterWK.getZweitstimme(letztePartei).getAnzahl());
			int ende = letzterWK.getZweitstimme(letztePartei).getAnzahl();
			letzterWK.getZweitstimme(letztePartei)
					.erniedrigeAnzahl(stimmanzahl);
			int anfang = letzterWK.getZweitstimme(letztePartei).getAnzahl();
			// System.out.println("Anfange: " + anfang + " Ende: " +ende + " "
			// +getSprungstelle(anfang, ende));
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
					+ letzterWK.getZweitstimme(letztePartei).getAnzahl());
			int ende = letzterWK.getZweitstimme(letztePartei).getAnzahl();
			letzterWK.getZweitstimme(letztePartei)
					.erniedrigeAnzahl(stimmanzahl);
			int anfang = letzterWK.getZweitstimme(letztePartei).getAnzahl();
			// System.out.println("Anfange: " + anfang + " Ende: " +ende + " "
			// +getSprungstelle(anfang, ende));
		}
		return (mandatsZahlNeu > mandatsZahlAlt);

	}

	private int getSprungstelle(int anfang, int ende) {
		System.out.println("Sprungstelle wird berechnet...");
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

		if (mandatsZahlAlt > mandatsZahlNeu) {
			return getSprungstelle(aenderung, ende);

		} else if (mandatsZahlAlt < mandatsZahlNeu) {
			return getSprungstelle(anfang, aenderung);
		} else {
			return aenderung;
		}

	}

	/**
	 * Erhöht für eine Partei in einem Bundesland solange ihre relevanten ZS um
	 * einen konkreten Wert, bis ihr Anteil an der Gesamtheit größer ist als der
	 * Anteil an Mandaten
	 * 
	 * Relevante ZS sind ZS, die nur auf Landeslisten ohne Überhangmandate
	 * abgegeben wurden
	 * 
	 * @param p
	 *            die Partei, bei der Zweitstimmen erhöht werden sollen
	 */

	// TODO vllt muss noch gleichzeitig in einem anderen Wahlkreis des
	// Bundeslandes für die Partei die Zweitstimmenzahl
	// erniedrigt werden

	public void erhoeheRelevantenAnteil(Partei p) {

		List<Landesliste> alleLandeslisten = p.getLandesliste();
		List<Landesliste> ohneUeberhang = new ArrayList<Landesliste>();

		// wählt alle Landeslisten, auf die keine Überhangmandate fallen
		for (Landesliste land : alleLandeslisten) {
			if (p.getAnzahlMandate(Mandat.UEBERHANGMADAT, land.getBundesland()) == 0) {
				ohneUeberhang.add(land);
			}

			// wählt aus den Landeslisten ohne Überhangmandat eine zufällige
			// Landesliste aus
			int i = rand.nextInt(ohneUeberhang.size());
			Landesliste l = ohneUeberhang.get(i);

			// wählt aus der zufällig erhaltenen Landesliste einen
			// zufälligen Wahlkreis
			i = rand.nextInt(l.getBundesland().getWahlkreise().size());
			Wahlkreis wk = l.getBundesland().getWahlkreise().get(i);

			// in diesem Wahlkreis wird nun die Zweitstimmenanzahl erhöht
			// TODO System.out.println entfernen
			// System.out.print("ZS im WK " + wk + " von "
			// + wk.getZweitstimme(p).getAnzahl());
			wk.getZweitstimme(p).erhoeheAnzahl(1000);

			// Sitzverteilung neu berechnen
			// System.out.println(" auf " + wk.getZweitstimme(p).getAnzahl()
			// + " erhöht");
			long start = System.currentTimeMillis();
			// this.setVerwandteWahl(rechner.berechneAlles(this.verwandteWahl));
			Debug.print("Laufzeit Mandatsrechner"
					+ (start - System.currentTimeMillis()) + "ms");
			// System.out.print("Relevante ZS von "
			// + p.getRelevanteZweitstimmen().getAnzahl());

			// relevante ZS neu berechnen
			berechneRelevanteZweitstimmen();
			// System.out.print(" auf "
			// + p.getRelevanteZweitstimmen().getAnzahl());

		}
	}

	/**
	 * Diese Methode überprüft, ob bei einer gegebenen Partei ihr Anteil an
	 * relativen Zweitstimmen größer ist als ihr Anteil an Mandaten. Die Anteile
	 * werden bundesweit betrachtet
	 * 
	 * @param p
	 *            Partei, bei der die Anteile überprüft werden sollen
	 * @return true, wenn Anteil rel. ZS > Anteil Mandate
	 */
	// TODO private setzen, nur fürs testen public
	public boolean bedingungErfuellt(Partei p) {
		List<Partei> alleParteien = this.kopie2.getParteien();

		// berechnet insgesamte Anzahl an relevanten Zweitstimmen, d.h. addiert
		// die relevanten Zweitstimmen aller Parteien

		int relevanteZweitstimmenGesamt = 0;
		for (Partei x : alleParteien) {
			relevanteZweitstimmenGesamt += x.getRelevanteZweitstimmen()
					.getAnzahl();
		}

		float anteilRelevanteZweitstimmen = 0;
		float anteilMandate = 0;
		// berechnet den Anteil an relevanten Zweitstimmen

		anteilRelevanteZweitstimmen = (float) p.getRelevanteZweitstimmen()
				.getAnzahl() / (float) relevanteZweitstimmenGesamt;

		// berechnet den Anteil an Mandaten

		anteilMandate = (float) p.getAnzahlMandate()
				/ (float) this.kopie2.getSitzverteilung().getAbgeordnete()
						.size();

		System.out.println(p.getName() + " " + anteilMandate + " "
				+ anteilRelevanteZweitstimmen);
		if (anteilRelevanteZweitstimmen > anteilMandate) {
			return true;
		}

		return false;
	}

	/**
	 * In dieser Methode werden die Parteien gewählt, deren prozentualer Anteil
	 * an relevanten Zweitstimmen größer als der prozentuale Anteil an Mandaten
	 * ist
	 * 
	 * @return Liste an relevanten Parteien, d.h. diejenigen, die das
	 *         beschriebene Merkmal aufweisen
	 */

	// TODO private setzten, nur zum testen public
	public List<Partei> waehleParteien() {
		List<Partei> alleParteien = this.kopie2.getParteien();

		List<Partei> relevanteParteien = new ArrayList<Partei>();
		for (Partei p : alleParteien) {
			if (bedingungErfuellt(p)) {
				relevanteParteien.add(p);
			}
		}
		return relevanteParteien;
	}

	/**
	 * Diese Methode wählt, von den Parteien abhängig, die Bundesländer, für die
	 * negatives Stimmgewicht auftreten kann
	 * 
	 * @param p
	 *            Partei
	 * @return Liste an Bundesländern
	 */

	// TODO private setzten, nur zum testen public
	public List<Bundesland> waehleBundeslaender(Partei p) {
		List<Landesliste> landeslisten = p.getLandesliste();
		List<Bundesland> relevanteBundeslaender = new ArrayList<Bundesland>();

		for (Landesliste l : landeslisten) {
			// überprüfe, ob die Partei in einem Bundesland Überhandmandate hat
			// falls ja, kann negatives Stimmgewicht auftreten und sie ist
			// relevant
			if (l.getKandidaten(Mandat.UEBERHANGMADAT).size() == 0) {
				relevanteBundeslaender.add(l.getBundesland());
			}
		}
		return relevanteBundeslaender;
	}

	/**
	 * Berechnet für alle Parteien die relevanten Zweitstimmen
	 * 
	 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf
	 * Landeslisten abgegeben werden, die keine Uberhangmandate erzielen
	 * 
	 */

	// TODO private setzten, nur zum testen public
	public void berechneRelevanteZweitstimmen() {

		for (Partei p : this.kopie2.getParteien()) {
			List<Landesliste> landeslisten = p.getLandesliste();
			int anzahl = 0;

			for (Landesliste l : landeslisten) {

				// überprüft, ob Partei Zweitstimmen in einem Bundesland
				// erhalten
				// hat, in dem es keine Überhangmandate hält
				if (l.getKandidaten(Mandat.UEBERHANGMADAT).size() == 0) {
					anzahl += l.getBundesland().getAnzahlZweitstimmen(p);
				}
			}

			p.setRelevanteZweitstimmen(new RelevanteZweitstimmen(anzahl));
			// System.out.println(p.getName() + " " +
			// p.getRelevanteZweitstimmen());

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
	 * @return the relevanteParteien
	 */
	public List<Partei> getRelevanteParteien() {
		return relevanteParteien;
	}

	/**
	 * @param relevanteParteien
	 *            the relevanteParteien to set
	 */
	public void setRelevanteParteien(List<Partei> relevanteParteien) {
		if (relevanteParteien == null) {
			throw new IllegalArgumentException(
					"Liste mit relevanten Parteien war null.");
		} else {
			this.relevanteParteien = relevanteParteien;
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

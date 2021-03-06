package main.java.mandatsrechner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList; //Spaeter rausmachen
import java.util.List;
import java.util.Random;
import java.util.Set;

import main.java.model.BerichtDaten;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;
import test.java.Debug;

/**
 * Diese Klasse berechnet die Sitzverteilung des deutschen Bundestages mithilfe
 * des Bundeswahlgesetzes 2008. Diese Klasse wurde zuletzt bei der Wahl 2009
 * verwendet.
 * 
 */
public class Mandatsrechner2009 {

	/** Konstante die bei der Ueberpruefung der Sperrklausel benoetigt wird **/
	private final static int MINDIREKTMANDATE = 3;

	/** Entwurfsmuster: Einzelstück */
	private static Mandatsrechner2009 instanz;

	/**
	 * Gibt die Instanz des Mandatsrechner zurueck
	 * 
	 * @return die Instanz
	 */
	public static Mandatsrechner2009 getInstance() {
		if (Mandatsrechner2009.instanz == null) {
			Mandatsrechner2009.instanz = new Mandatsrechner2009();
		}
		return Mandatsrechner2009.instanz;
	}

	private Mandatsrechner2009() {

	}

	/**
	 * Berechnet die Wahlkreissieger jedes Wahlkreises und erstellt einen
	 * Eintrag im Bericht.Die Methode ist oeffentlich da diese Methode auch von
	 * Mandatsrechner2013 genutzt wird.
	 * 
	 * @param bundestagswahl
	 *            Die zu berechnende Bundestagswahl.
	 * @return die berechnete Bundestagswahl mit Direktmandate.
	 * @throws IllegalArgumentException
	 *             die Bundestagswahl null ist.
	 */
	protected Bundestagswahl berechneDirektmandat(Bundestagswahl bundestagswahl)
			throws IllegalArgumentException {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException("Bundestagswahl ist null.");
		}
		for (final Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {
			for (final Wahlkreis wahlkreis : bundesland.getWahlkreise()) {
				int max = 0;
				Kandidat gewinner = null;
				// Liste mit Kandidaten die die hÃ¶chste Erststimmenanzahl haben
				// Der Gweinner wird ausgelost;
				LinkedList<Kandidat> moeglicheKandidaten = new LinkedList<Kandidat>();

				// Suche den Kandidaten mit den meisten Erststimmen.
				for (final Erststimme erst : wahlkreis
						.getErststimmenProPartei()) {
					if (max < erst.getAnzahl()) {

						// vorherige Liste löschen
						moeglicheKandidaten = new LinkedList<Kandidat>();
						// pot. Kandidaten in die Liste einfÃ¼gen
						moeglicheKandidaten.add(erst.getKandidat());
						max = erst.getAnzahl();
					} else if (max == erst.getAnzahl()) {
						moeglicheKandidaten.add(erst.getKandidat());
					}
				}

				if (moeglicheKandidaten.size() == 1) {
					// Kandidaten Mandat zuweisen und als Wahlkreissieger in
					// den Wahlkreis eintragen
					gewinner = moeglicheKandidaten.get(0);
				} else {
					final Random genrandom = new Random();
					// Bestimme zufälligen Kandidaten
					gewinner = moeglicheKandidaten.get(genrandom
							.nextInt(moeglicheKandidaten.size()));
				}
				/*
				 * bekommt ein Direktmandat und wird als Wahlkreissieger im
				 * Wahlklreis eingetragen. Falls max = 0 ist, gibt es keine
				 * Erststimmen in diesem Wahlkreis.
				 */
				if (max != 0) {
					gewinner.setMandat(Mandat.DIREKTMANDAT);
					wahlkreis.setWahlkreisSieger(gewinner);
					bundestagswahl.getSitzverteilung().addAbgeordnete(gewinner);
					// Eintrag in der Sitzverteilung erstellen
					bundestagswahl
							.getSitzverteilung()
							.getBericht()
							.zeileHinzufuegen(gewinner.getName(),
									gewinner.getPartei().getName(),
									Mandat.DIREKTMANDAT.toString(),
									bundesland.getName(), wahlkreis.getName());
				} else {
					Debug.print("Wahlkreis hat keine Erststimmen!", 3);
					// throw new
					// IllegalArgumentException("Wahlkreis hat keine Erststimmen!");
				}

			}
		}
		return bundestagswahl;
	}

	/**
	 * Berechnet die Sitzverteilung nach D'Hondt. Diese Methode wird vorerst
	 * nicht genutzt. Sie koennte als alternative Berechnungsmethode genutzt
	 * werden oder man koennte mit ihr die Benachteiligung der kleineren
	 * Parteien anzeigen.
	 * 
	 * @param bundestagswahl
	 *            die zu berechnende Bundestagswahl
	 * @throws IllegalArgumentException
	 *             die Bundestagswahl leer ist.
	 * @return die berechnete Bundestagswahl
	 */
	public Bundestagswahl berechneHondt(Bundestagswahl bundestagswahl)
			throws IllegalArgumentException {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException("Bundestagswahl ist null");
		}
		// Initialisierung:
		initialisiere(bundestagswahl);
		berechneDirektmandat(bundestagswahl);
		final LinkedList<Partei> relevanteParteien = berechneRelevanteParteien(bundestagswahl);

		// Berechnung der Sitzverteilung nach d'Hondt.

		// Hashmap die die verteilte Sitzanzahl der Partei speichert.
		final HashMap<Partei, Integer> parteiSitze = new HashMap<Partei, Integer>();
		// Hashmap die die berechneten Stimmen der relvanten Parteien speichert.
		final HashMap<Partei, LinkedList<Double>> parteiStimme = new HashMap<Partei, LinkedList<Double>>();

		final float zuteilungsdivisor = berechneZuteilungsdivisor(bundestagswahl);

		for (final Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {

			final int sitzeBundesland = Math.round(bundesland
					.getEinwohnerzahl() / zuteilungsdivisor);
			// Maps vorbereiten

			for (final Partei partei : relevanteParteien) {

				parteiSitze.put(partei, 0);
				// Queue für die Berechnung der Zweitstimmen
				final LinkedList<Double> berechneteZweitstimmen = new LinkedList<Double>();
				berechneteZweitstimmen.add((double) bundesland
						.getAnzahlZweitstimmen(partei));
				parteiStimme.put(partei, berechneteZweitstimmen);

			}
			// Restliche Stimmen für die Partei bestimmen
			for (int i = 1; i < sitzeBundesland; i++) {
				final Set<Partei> set = parteiStimme.keySet();
				final Iterator<Partei> iterator = set.iterator();

				while (iterator.hasNext()) {
					final Partei key = iterator.next();
					parteiStimme.get(key).add(
							bundesland.getAnzahlZweitstimmen(key)
									/ Math.pow(2, i));
				}
			}

			for (int i = 0; i < sitzeBundesland; i++) {
				Partei sieger = null;
				LinkedList<Double> siegerliste = null;
				// Speichert den größten Wert
				double maximum = 0;
				// Speichert den aktuellen Wert der Partei
				double mom = 0;
				final Set<Partei> set = parteiStimme.keySet();
				final Iterator<Partei> iterator = set.iterator();

				while (iterator.hasNext()) {
					final Partei key = iterator.next();

					mom = parteiStimme.get(key).get(1);

					if (mom > maximum) {
						sieger = key;
						maximum = mom;
						siegerliste = parteiStimme.get(key);
					}
				}
				// Der Gewinner wurde gefunden. Jetzt wird die Sitzanzahl der
				// Partei erhöht und der Eintrag aus der Liste gelöscht
				siegerliste.removeFirst();
				parteiSitze.put(sieger, parteiSitze.get(sieger) + 1);

			}

		}
		return bundestagswahl;
	}

	/**
	 * Berechnet die relevanten Parteien für die Berechnung. Als Bedingung wird
	 * die Sperrklausel gesetzt, wird diese erfuellt, wird dies in der Partei
	 * vermerkt. Die Methode ist oeffentlich da diese Methode auch von
	 * Mandatsrechner2013 genutzt wird.
	 * 
	 * @param bundestagswahl
	 *            Die Bundestagswahl mit den zu berechnenden Parteien.
	 * @throws IllegalArgumentException
	 *             die Bundestagswahl leer ist oder die Anzahl negativ ist.
	 * @return die Liste mit den relevanten Parteien
	 */
	protected LinkedList<Partei> berechneRelevanteParteien(
			Bundestagswahl bundestagswahl) throws IllegalArgumentException {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException(
					"Bundestagswahl leer oder die Sperrklauselanzahl ist negativ.");
		}
		final LinkedList<Partei> relevanteParteien = new LinkedList<Partei>();

		// 5-Prozent Klausel:
		final int sperrklauselAnzahl = bundestagswahl.getDeutschland()
				.getAnzahlZweitstimmen() / 20;
		for (final Partei part : bundestagswahl.getParteien()) {
			if (part.getZweitstimmeGesamt() >= sperrklauselAnzahl
					|| part.getAnzahlDirektmandate() >= Mandatsrechner2009.MINDIREKTMANDATE) {
				// Partei im Bundestag falls Anforderungen erfuellt sind.
				part.setImBundestag(true);
				// Partei in die Liste hinzufuegen
				if (!relevanteParteien.contains(part)) {
					relevanteParteien.add(part);
				}
			} else {
				// Ansonsten werden die Zweitstimmen der Partei nicht gewertet.
				part.setImBundestag(false);
			}

		}

		return relevanteParteien;
	}

	/**
	 * Berechnet die Sitzverzeilung mit Hilfe des Sainte-Lauge-Verfahren. Dabei
	 * werden keine Ausgleichsmandate berechnet.
	 * 
	 * @param bundestagswahl
	 *            die zu berechnende Bundestagswahl.
	 * @throws IllegalArgumentException
	 *             wenn das Bundestagswahl-Objekt leer ist.
	 * @return die berechnete Bundestagswahl.
	 */
	public Bundestagswahl berechneSainteLague(Bundestagswahl bundestagswahl)
			throws IllegalArgumentException {

		Debug.print("Mandatsrechnung gestartet!", 4);

		if (bundestagswahl == null) {
			throw new IllegalArgumentException(
					"Bundestagswahl-Objekt ist leer!");
		}
		// Initialisierung:
		initialisiere(bundestagswahl);

		// Direktmandate bestimmen
		bundestagswahl = berechneDirektmandat(bundestagswahl);

		// Relevante Parteien bestimmen
		final LinkedList<Partei> relevanteParteien = berechneRelevanteParteien(bundestagswahl);

		// Zuteilungsdivisor bestimmen
		final float zuteilungsdivisor = berechneZuteilungsdivisor(bundestagswahl);

		float landesdivisor = 0;

		for (final Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {

			final int sitzeBundesland = runden(bundesland.getEinwohnerzahl()
					/ zuteilungsdivisor, false);

			final int relevanteZweitstimmenSumme = getRelevanteZweitstimmenSumme(
					relevanteParteien, bundesland);
			landesdivisor = relevanteZweitstimmenSumme / sitzeBundesland;

			int sitzePartei = 0;
			boolean underflow = false, overflow = false;
			while (sitzePartei != sitzeBundesland) {
				sitzePartei = 0;

				for (final Partei part : relevanteParteien) {
					if (bundesland.getAnzahlZweitstimmen(part) / landesdivisor < 0) {
						// throw new IllegalArgumentException("Fehlerhafte ");
						System.err.println(bundesland
								.getAnzahlZweitstimmen(part)
								+ " "
								+ landesdivisor);
						System.exit(0);
					}
					sitzePartei += runden(
							bundesland.getAnzahlZweitstimmen(part)
									/ landesdivisor, false);
				}
				// System.out.println("Landesdivisor: " + landesdivisor + " - "
				// + sitzePartei + " " + sitzeBundesland);

				if (sitzePartei == sitzeBundesland) {
					break;
				} else if (sitzePartei < sitzeBundesland) {
					if (landesdivisor <= 99) {
						landesdivisor -= 0.1;
					} else {
						landesdivisor -= 99;
					}

					if (overflow) {
						underflow = true;
					}
				} else {
					// sitzanzahl > sitzeBundesland
					landesdivisor += 100;
					if (overflow && underflow) {
						// System.exit(0);
						break;
					} else {
						overflow = true;
						underflow = false;
					}
				}
			}

			for (final Partei part : relevanteParteien) {
				// Direktmandate einer Partei im Bundesland
				final int direktmandate = part.getAnzahlMandate(
						Mandat.DIREKTMANDAT, bundesland);

				final int mindestSitzanzahl = runden(
						bundesland.getAnzahlZweitstimmen(part) / landesdivisor,
						false);
				// Systemprintln(mindestSitzanzahl);
				// Wichtig zur Bestimmung von Ueberhangmandate
				int diffKandidat = mindestSitzanzahl - direktmandate;

				part.addMindestsitzanzahl(bundesland,
						Math.max(direktmandate, mindestSitzanzahl));
				if (diffKandidat >= 0) {
					for (int i = 0; i < diffKandidat; i++) {
						// Nehme aus der Bundestagswahl die Landesliste der
						// Partei und fuege den i-ten Listenkandidaten in die
						// Sitzverteilung hinzu
						// Dabei darf der Kandidat kein Wahlkreissieger sein
						if (bundesland.getLandesliste(part)
								.getListenkandidaten().size() >= i + 1) {
							final Kandidat neuerAbgeordneter = bundesland
									.getLandesliste(part).getListenkandidaten()
									.get(i);
							// Hat der Kandidat schon ein Mandat?
							if (neuerAbgeordneter.getMandat() == Mandat.KEINMANDAT) {
								bundestagswahl.getSitzverteilung()
										.addAbgeordnete(neuerAbgeordneter);
								neuerAbgeordneter
										.setMandat(Mandat.LISTENMANDAT);
								bundestagswahl
										.getSitzverteilung()
										.getBericht()
										.zeileHinzufuegen(
												neuerAbgeordneter.getName(),
												neuerAbgeordneter.getPartei()
														.getName(),
												Mandat.LISTENMANDAT.toString(),
												bundesland.getName(), "");
							} else {
								// Kandidat hat schon ein Mandat, deswegen wird
								// diffKandidat erhoeht, damit die Schleife den
								// fehlenden Kandidaten ausgleicht. Der Kandidat
								// wird sozusagen Uebersprungen.
								diffKandidat++;
							}
						} else {
							Debug.print(
									"Notice: Kein Listenkandidat gefunden.", 4);
							// Landesliste erschoepft.
						}
					}
				} else {
					for (int i = 0; i < Math.abs(diffKandidat); i++) {
						part.incrementUeberhangMandate(bundesland);
					}

				}

			}

			Debug.print("\nLandesdivisor " + bundesland.getName() + ": "
					+ landesdivisor, 5);
			int sum = 0;
			for (final Partei part : relevanteParteien) {

				Debug.print(
						"" + part.getName() + ": "
								+ bundesland.getAnzahlZweitstimmen(part)
								+ " - " + part.getMindestsitzanzahl(bundesland),
						5);
				sum += part.getMindestsitzanzahl(bundesland);
			}
			Debug.print("Summe: " + sum, 5);

		}

		Debug.print("\nSitzverteilung", 5);
		int summe = 0;
		for (final Partei partei : relevanteParteien) {
			Debug.print(
					partei.getName() + ": " + partei.getMindestsitzAnzahl(), 5);
			summe += partei.getMindestsitzAnzahl();
		}
		Debug.print("Summe: " + summe, 5);
		Debug.print("ENDE BTW 2009", 5);
		return bundestagswahl;
	}

	/**
	 * Berechnet den Zuteilungsdivisor, damit die Sitzanzahl Deutschland auf die
	 * Bundeslaender verteilt wird.
	 * 
	 * @param bundestagswahl
	 *            die zu berechnende Bundestagswahl mit den Wahlkreisen.
	 * @return einen geeigneten Zuteilungsdivisor.
	 * @throws IllegalArgumentException
	 *             wenn das Bundestagswahl-Objekt null ist.
	 */
	protected float berechneZuteilungsdivisor(Bundestagswahl bundestagswahl)
			throws IllegalArgumentException {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException("Bundestagswahl ist null!");
		}
		// BTW 2013: 598 Mindestsitze.
		final int minSitze = bundestagswahl.getDeutschland().getWahlkreise()
				.size() * 2;
		Debug.print("Anzahl der Wahlkreise: " + minSitze, 4);
		float zuteilungsdivisor = runden(bundestagswahl.getDeutschland()
				.getEinwohneranzahl() / minSitze, false);
		int sitzanzahl = 0;
		while (sitzanzahl != minSitze) {
			sitzanzahl = 0;
			/*
			 * Summiere die Einwohnerzahl aller Bundeslaender mit dem
			 * Zuteilungsdivisor.
			 */
			for (final Bundesland bundesland : bundestagswahl.getDeutschland()
					.getBundeslaender()) {
				sitzanzahl += runden(bundesland.getEinwohnerzahl()
						/ zuteilungsdivisor, false);
			}
			if (sitzanzahl < minSitze) {
				/*
				 * Erniedrige Zuteilungsdivisor falls die Sitzanzahl kleiner ist
				 * als die minimale Anzahl an Sitzen.
				 */
				zuteilungsdivisor -= 99;
			} else if (sitzanzahl > minSitze) {
				/*
				 * Andernfalls erhoehe Zuteilungsdivisor.
				 */
				zuteilungsdivisor += 100;
			}
		}
		return zuteilungsdivisor;
	}

	/**
	 * Gibt die Summe der Zweitstimmen einer Menge von Parteien in einem
	 * bestimmten Bundesland zurueck.
	 * 
	 * @param parteien
	 *            Liste an Parteien.
	 * @param bl
	 *            ausgewaehltes Bundesland.
	 * @throws IllegalArgumentException
	 *             wenn die Eingabeparameter null sind.
	 * 
	 * @return anzahl der Zweitstimmen
	 */
	public int getRelevanteZweitstimmenSumme(List<Partei> parteien,
			Bundesland bl) throws IllegalArgumentException {
		if (parteien == null || bl == null) {
			throw new IllegalArgumentException("Eingabeparameter sind null");
		}
		int anzahl = 0;
		for (final Partei partei : parteien) {
			anzahl += bl.getAnzahlZweitstimmen(partei);
		}
		return anzahl;
	}

	/**
	 * Fuehrt eine Initialisierung der Mandate durch.
	 * 
	 * @param bundestagswahl
	 *            die zu zuruecksetzende Bundestagswahl.
	 * @throws IllegalArgumentException
	 *             wenn die Bundestagswahl null ist.
	 */
	public void initialisiere(Bundestagswahl bundestagswahl) {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException("Bundestagswahl ist null");
		}
		bundestagswahl.setSitzverteilung(new Sitzverteilung(
				new LinkedList<Kandidat>(), new BerichtDaten()));
		// Setze alle Kandidaten auf wieder zurueck
		for (final Partei partei : bundestagswahl.getParteien()) {
			// Setze alle Mandate zurueck.
			partei.resetPartei();
		}

		/*
		 * Setzt alle Farben auf NULL
		 */
		for (final Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {
			bundesland.setFarbe(null);
		}
	}

	/**
	 * Rundet die Kommazahl mit dem gewuenschten Rundungsalgprithmus auf oder
	 * ab.
	 * 
	 * @param zahl
	 *            die zu rundende zahl.
	 * @param randomize
	 *            soll die Funktion bei einer Kommastelle zufaellig gerundet
	 *            werden?
	 * @throws IllegalArgumentException
	 *             zahl negativ ist.
	 * @return die gerundete zahl.
	 */
	protected int runden(float zahl, boolean randomize)
			throws IllegalArgumentException {
		if (zahl < 0) {
			throw new IllegalArgumentException("Zahl ist negativ.");
		}
		// Erste nachkommestelle rausfischen.
		final int kommastelle = (int) ((zahl - (int) zahl) * 10);
		int gerundet = 0;
		if (randomize && kommastelle == 5) {
			final int rand = Math.random() < 0.5 ? 0 : 1;
			if (rand == 1) {
				gerundet = (int) Math.ceil(zahl);
			} else {
				gerundet = (int) Math.floor(zahl);
			}
		} else if (kommastelle >= 5) {
			gerundet = (int) Math.ceil(zahl);
		} else {
			gerundet = (int) Math.floor(zahl);
		}
		return gerundet;
	}

	/**
	 * Öffentliche Methode zum Testen der berechneDirektmandat-Methode.
	 * 
	 * @param bundestagswahl
	 *            die zu testende Bundestagswahl.
	 * @return das berechnete Ergebnis
	 */
	public Bundestagswahl testBerechneDirektmandat(Bundestagswahl bundestagswahl) {
		return berechneDirektmandat(bundestagswahl);
	}

	/**
	 * Öffentliche Klasse zum Testen der Berechnung der relevanten Parteien.
	 * 
	 * @param bundestagswahl
	 *            das zu testende Objekt
	 * @return die Liste mit den relevanten Parteien
	 */
	public LinkedList<Partei> testBerechneRelevanteParteien(
			Bundestagswahl bundestagswahl) {
		return berechneRelevanteParteien(bundestagswahl);
	}

	/**
	 * Öffentliche Methode zum Testen der Methode berechneZuteilungsdivisor
	 * 
	 * @param bundestagswahl
	 *            das zu testende Objekt
	 * @return das Ergebnis der Berechnung
	 */
	public float testBerechneZuteilungsdivisor(Bundestagswahl bundestagswahl) {
		return berechneZuteilungsdivisor(bundestagswahl);
	}

	/**
	 * Öffentliche Methode zum Testen der Methode runden().
	 * 
	 * @param zahl
	 *            die zu testende Gleitkommazahl.
	 * @param randomize
	 *            soll Zufall im Test eingeschaltet sein?
	 * @return das Ergebnis der Berechnung.
	 */
	public int testRunden(float zahl, boolean randomize) {
		return runden(zahl, randomize);
	}
}

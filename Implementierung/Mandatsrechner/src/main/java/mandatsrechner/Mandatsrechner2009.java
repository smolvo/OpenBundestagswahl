package main.java.mandatsrechner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList; //Spaeter rausmachen
import java.util.List;
import java.util.Set;
import java.util.Random;

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
 * @author 13genesis37
 * 
 */
public class Mandatsrechner2009 {

	/** Konstante die bei der Ueberpruefung der Sperrklausel benoetigt wird **/
	private final static int MINDIREKTMANDATE = 3;

	/** Entwurfsmuster: Einzelstï¿½ck */
	private static Mandatsrechner2009 instanz;

	private Mandatsrechner2009() {

	}

	/**
	 * Gibt die Instanz des Mandatsrechner zurueck
	 * 
	 * @return die Instanz
	 */
	public static Mandatsrechner2009 getInstance() {
		if (instanz == null) {
			instanz = new Mandatsrechner2009();
		}
		return instanz;
	}

	/**
	 * Fuehrt eine Initialisierung der Mandate durch.
	 * 
	 * @param bundestagswahl
	 *            die zu zuruecksetzende Bundestagswahl.
	 */
	public void initialisiere(Bundestagswahl bundestagswahl) {
		bundestagswahl.setSitzverteilung(new Sitzverteilung(
				new LinkedList<Kandidat>(), new BerichtDaten()));
		// Setze alle Kandidaten auf wieder zurueck
		for (Partei partei : bundestagswahl.getParteien()) {
			// Setze Ausgleichs- und Ueberhangmandate zurueck.
			partei.resetUeberhangMandate();
			partei.resetAusgleichsMandate();
			// Setze alle Mandate zurueck.
			for (Kandidat kandidat : partei.getMitglieder()) {
				kandidat.setMandat(Mandat.KEINMANDAT);
			}
		}

		/*
		 * Setzt alle Farben auf NULL
		 */
		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {
			bundesland.setFarbe(null);
		}
	}

	/**
	 * Berechnet die Wahlkreissieger jedes Wahlkreises und erstellt einen
	 * Eintrag im Bericht.Die Methode ist oeffentlich da diese Methode auch von
	 * Mandatsrechner2013 genutzt wird.
	 * 
	 * @param bundestagswahl
	 *            Die zu berechnende Bundestagswahl
	 * @return die berechnete Bundestagswahl mit Direktmandate
	 * @throws IllegalArgumentException
	 *             die Bundestagswahl null ist
	 */
	protected Bundestagswahl berechneDirektmandat(Bundestagswahl bundestagswahl) {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException("Bundestagswahl ist null.");
		}
		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {
			for (Wahlkreis wahlkreis : bundesland.getWahlkreise()) {
				int max = 0;
				Kandidat gewinner = null;
				// Liste mit Kandidaten die die hÃ¶chste Erststimmenanzahl haben
				// Der Gweinner wird ausgelost;
				LinkedList<Kandidat> moeglicheKandidaten = new LinkedList<Kandidat>();

				// Suche den Kandidaten mit den meisten Erststimmen.
				for (Erststimme erst : wahlkreis.getErststimmenProPartei()) {
					if (max < erst.getAnzahl()) {

						// vorherige Liste lÃ¶schen
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
					Random genrandom = new Random();
					// Bestimme zufÃ¤lligen Kandidaten
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
	 * Berechnet die relevanten Parteien fÃ¼r die Berechnung. Als Bedingung wird
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
			Bundestagswahl bundestagswahl) {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException(
					"Bundestagswahl leer oder die Sperrklauselanzahl ist negativ.");
		}
		LinkedList<Partei> relevanteParteien = new LinkedList<Partei>();

		// 5-Prozent Klausel:
		int sperrklauselAnzahl = bundestagswahl.getDeutschland()
				.getAnzahlZweitstimmen() / 20;
		for (Partei part : bundestagswahl.getParteien()) {
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
	 * Berechnet den Zuteilungsdivisor, damit die Sitzanzahl Deutschland auf die
	 * Bundeslaender verteilt wird
	 * 
	 * @param bundestagswahl
	 *            die zu berechnende Bundestagswahl mit den Wahlkreisen
	 * @return einen geeigneten Zuteilungsdivisor
	 */
	protected float berechneZuteilungsdivisor(Bundestagswahl bundestagswahl) {
		// BTW 2013: 598 Mindestsitze.
		int minSitze = bundestagswahl.getDeutschland().getWahlkreise().size() * 2;
		Debug.print("Anzahl der Wahlkreise: " + minSitze, 4);
		float zuteilungsdivisor = this.runden(bundestagswahl.getDeutschland()
				.getEinwohneranzahl() / minSitze, false);
		int sitzanzahl = 0;
		while (sitzanzahl != minSitze) {
			sitzanzahl = 0;
			/*
			 * Summiere die Einwohnerzahl aller Bundeslaender mit dem
			 * Zuteilungsdivisor.
			 */
			for (Bundesland bundesland : bundestagswahl.getDeutschland()
					.getBundeslaender()) {
				sitzanzahl += this.runden(bundesland.getEinwohnerzahl()
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
	protected int runden(float zahl, boolean randomize) {
		if (zahl < 0) {
			throw new IllegalArgumentException("Zahl ist negativ.");
		}
		// Erste nachkommestelle rausfischen.
		int kommastelle = (int) ((zahl - (int) zahl) * 10);
		int gerundet = 0;
		if (randomize && kommastelle == 5) {
			int rand = (Math.random() < 0.5) ? 0 : 1;
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
	public Bundestagswahl berechneHondt(Bundestagswahl bundestagswahl) {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException("Bundestagswahl ist null");
		}
		// Initialisierung:
		this.initialisiere(bundestagswahl);
		berechneDirektmandat(bundestagswahl);
		LinkedList<Partei> relevanteParteien = berechneRelevanteParteien(bundestagswahl);

		// Berechnung der Sitzverteilung nach d'Hondt.

		// Hashmap die die verteilte Sitzanzahl der Partei speichert.
		HashMap<Partei, Integer> parteiSitze = new HashMap<Partei, Integer>();
		// Hashmap die die berechneten Stimmen der relvanten Parteien speichert.
		HashMap<Partei, LinkedList<Double>> parteiStimme = new HashMap<Partei, LinkedList<Double>>();

		float zuteilungsdivisor = berechneZuteilungsdivisor(bundestagswahl);

		int sitzanzahl = 0;
		int sitzebl = 0;

		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {

			int sitzeBundesland = Math.round(bundesland.getEinwohnerzahl()
					/ zuteilungsdivisor);
			// Maps vorbereiten

			for (Partei partei : relevanteParteien) {

				parteiSitze.put(partei, 0);
				// Queue fÃ¼r die Berechnung der Zweitstimmen
				LinkedList<Double> berechneteZweitstimmen = new LinkedList<Double>();
				berechneteZweitstimmen.add((double) bundesland
						.getAnzahlZweitstimmen(partei));
				parteiStimme.put(partei, berechneteZweitstimmen);

			}
			// Restliche Stimmen fÃ¼r die Partei bestimmen
			for (int i = 1; i < sitzeBundesland; i++) {
				Set<Partei> set = parteiStimme.keySet();
				Iterator<Partei> iterator = set.iterator();

				while (iterator.hasNext()) {
					Partei key = iterator.next();
					parteiStimme.get(key).add(
							bundesland.getAnzahlZweitstimmen(key)
									/ (Math.pow(2, i)));
					// Systemprint("");
				}
			}

			for (int i = 0; i < sitzeBundesland; i++) {
				Partei sieger = null;
				LinkedList<Double> siegerliste = null;
				// Speichert den grÃ¶ÃŸten Wert
				double maximum = 0;
				// Speichert den aktuellen Wert der Partei
				double mom = 0;
				Set<Partei> set = parteiStimme.keySet();
				Iterator<Partei> iterator = set.iterator();

				while (iterator.hasNext()) {
					Partei key = iterator.next();

					mom = parteiStimme.get(key).get(1);

					if (mom > maximum) {
						sieger = key;
						maximum = mom;
						siegerliste = parteiStimme.get(key);
					}
				}
				// Der Gewinner wurde gefunden. Jetzt wird die Sitzanzahl der
				// Partei erhÃ¶ht und der Eintrag aus der Liste gelÃ¶scht
				siegerliste.removeFirst();
				parteiSitze.put(sieger, parteiSitze.get(sieger) + 1);

			}

			Set<Partei> set = parteiSitze.keySet();
			Iterator<Partei> iterator = set.iterator();

			while (iterator.hasNext()) {
				Partei key = iterator.next();
				sitzanzahl += bundesland.getDirektMandate(key).size(); // Math.max(parteiSitze.get(key),bundesland.getDirektMandate(key).size());

				// Systemprintln("B: (" + sitzeBundesland + ") " + bundesland
				// + " P:" + key.getName() + " : " + parteiSitze.get(key));
			}
			sitzebl += sitzeBundesland;

		}
		// Systemprintln(sitzanzahl);
		// Systemprintln(sitzebl);

		return bundestagswahl;
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
	public Bundestagswahl berechneSainteLague(Bundestagswahl bundestagswahl) {
		if (bundestagswahl == null) {
			throw new IllegalArgumentException(
					"Bundestagswahl-Objekt ist leer!");
		}
		// Initialisierung:
		this.initialisiere(bundestagswahl);

		// Direktmandate bestimmen
		bundestagswahl = this.berechneDirektmandat(bundestagswahl);

		// Relevante Parteien bestimmen
		LinkedList<Partei> relevanteParteien = this
				.berechneRelevanteParteien(bundestagswahl);

		// Zuteilungsdivisor bestimmen
		float zuteilungsdivisor = this
				.berechneZuteilungsdivisor(bundestagswahl);

		float landesdivisor = 0;

		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {

			/*
			 * if(!bundesland.getName().equals("Thï¿½ringen")){ continue; }
			 */
			int sitzeBundesland = this.runden(bundesland.getEinwohnerzahl()
					/ zuteilungsdivisor, false);

			int relevanteZweitstimmenSumme = this
					.getRelevanteZweitstimmenSumme(relevanteParteien,
							bundesland);
			landesdivisor = relevanteZweitstimmenSumme / sitzeBundesland;

			int sitzePartei = 0;
			while (sitzePartei != sitzeBundesland) {
				sitzePartei = 0;

				for (Partei part : relevanteParteien) {

					sitzePartei += this.runden(
							bundesland.getAnzahlZweitstimmen(part)
									/ landesdivisor, false);
				}
				if (sitzePartei == sitzeBundesland) {
					break;
				} else if (sitzePartei < sitzeBundesland) {
					landesdivisor -= 99;
				} else {
					// sitzanzahl > sitzeBundesland
					landesdivisor += 100;
				}
			}

			for (Partei part : relevanteParteien) {
				// Direktmandate einer Partei im Bundesland
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKTMANDAT,
						bundesland);

				int mindestSitzanzahl = this.runden(
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
							Kandidat neuerAbgeordneter = bundesland
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
							Debug.print("Notice: Kein Listenkandidat gefunden.", 4);
							// Landesliste erschoepft.
						}
					}
				} else {
					for (int i = 0; i < Math.abs(diffKandidat); i++) {
						part.incrementUeberhangMandate(bundesland);
					}

				}

			}


			Debug.print("\nLandesdivisor " + bundesland.getName() + ": " + landesdivisor, 5);
			int sum = 0;
			for (Partei part : relevanteParteien) {

				Debug.print("" + part.getName() + ": "
						+ bundesland.getAnzahlZweitstimmen(part) + " - "
						+ part.getMindestsitzanzahl(bundesland), 5);
				sum += part.getMindestsitzanzahl(bundesland);
			}
			Debug.print("Summe: " + sum, 5);
				
		}

		Debug.print("\nSitzverteilung", 5);
		int summe = 0;
		for (Partei partei : relevanteParteien) {
			Debug.print(partei.getName() + ": " + partei.getMindestsitzAnzahl(), 5);
			summe += partei.getMindestsitzAnzahl();
		}
		Debug.print("Summe: " + summe, 5);
		Debug.print("ENDE BTW 2009", 5);
		return bundestagswahl;
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
			Bundesland bl) {
		if (parteien == null || bl == null) {
			throw new IllegalArgumentException("Eingabeparameter sind null");
		}
		int anzahl = 0;
		for (Partei partei : parteien) {
			anzahl += bl.getAnzahlZweitstimmen(partei);
		}
		return anzahl;
	}
}

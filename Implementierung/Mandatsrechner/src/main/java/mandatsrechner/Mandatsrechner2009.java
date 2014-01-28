package main.java.mandatsrechner;

import main.java.model.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList; //Spaeter rausmachen
import java.util.Set;

import test.java.Debug;

public class Mandatsrechner2009 {

	/** Konstante die bei der Ueberpruefung der Sperrklausel benoetigt wird **/
	private final int MINDIREKTMANDATE = 3;

	/** Entwurfsmuster: Einzelst�ck */
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
	 * @param bundestagswahl
	 */
	public void initialisiere(Bundestagswahl bundestagswahl){
		bundestagswahl.setSitzverteilung(new Sitzverteilung(
				new LinkedList<Kandidat>(), new BerichtDaten()));
		// Setze alle Kandidaten auf wieder zurueck
		for (Partei partei : bundestagswahl.getParteien()) {
			
			partei.resetUeberhangMandate();
			
			for (Kandidat kandidat : partei.getMitglieder()) {
				kandidat.setMandat(Mandat.KEINMANDAT);
			}
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
	 * @throws wenn
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
				
				for (Erststimme erst : wahlkreis.getErststimmenProPartei()) {
					// TODO parallelitaet!
					// TODO Kandidaten mit gleicher Erststimmenanzahl
					//System.out.println(max+" "+erst.getAnzahl());
					if (max < erst.getAnzahl()) {
						// Kandidaten Mandat zuweisen und als Wahlkreissieger in
						// den Wahlkreis eintragen
						gewinner = erst.getKandidat();
						max = erst.getAnzahl();
					}
				}
				/*
				 * bekommt ein Direktmandat und wird als Wahlkreissieger im
				 * Wahlklreis eingetragen
				 */
				if(max != 0){
					//System.out.println("Direktmandat: "+gewinner.getPartei()+ " "+ bundesland.getName());
					gewinner.setMandat(Mandat.DIREKTMANDAT);
					wahlkreis.setWahlkreisSieger(gewinner);
					bundestagswahl.getSitzverteilung().addAbgeordnete(gewinner);
					// Eintrag in der Sitzverteilung erstellen
					bundestagswahl
							.getSitzverteilung()
							.getBericht()
							.zeileHinzufuegen(gewinner.getName(),
									gewinner.getPartei().getName(),
									Mandat.DIREKTMANDAT.toString(), bundesland.getName(),
									wahlkreis.getName());
				}

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
	 * @param sperrklauselAnzahl
	 *            Prozentsatz der mindest-benoetigten Zweitstimmen.
	 * @throws wenn
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
		int sperrklauselAnzahl = bundestagswahl.getDeutschland()
				.getAnzahlZweitstimmen() / 20;
		for (Partei part : bundestagswahl.getParteien()) {
			if (part.getZweitstimmeGesamt() >= sperrklauselAnzahl
					|| part.getAnzahlDirektmandate() >= this.MINDIREKTMANDATE) {
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
		int minSitze = bundestagswahl.getDeutschland().getWahlkreise().size() * 2;
		Debug.print("Anzahl der Wahlkreise: " + minSitze);
		float zuteilungsdivisor = bundestagswahl.getDeutschland()
				.getEinwohneranzahl() / minSitze;
		int sitzanzahl = 0;
		while (sitzanzahl != minSitze) {
			sitzanzahl = 0;
			for (Bundesland bundesland : bundestagswahl.getDeutschland()
					.getBundeslaender()) {
				sitzanzahl += this.runden(bundesland.getEinwohnerzahl()
						/ zuteilungsdivisor,false);
			}
			//Debug.print("Test: " + sitzanzahl + " - " + minSitze);
			if(sitzanzahl == minSitze){
				// Falls dieser Beak nicht drin ist, passt die Anzahl nicht!
				Debug.print("ZTD: "+zuteilungsdivisor);
				break;
			} else if (sitzanzahl < minSitze) {
				zuteilungsdivisor -= 10;
			} else {
				// sitzanzahl > minSitze
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
	 * @throws wenn
	 *             zahl negativ ist.
	 * @return die gerundete zahl.
	 */
	protected int runden(float zahl, boolean randomize) {
		if (zahl < 0) {
			throw new IllegalArgumentException("Zahl ist negativ.");
		}
		int kommastelle = (int) ((zahl - (int) zahl) * 10);
		// System.err.println(kommastelle);
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
		//System.err.println("###### "+zahl+" "+gerundet);
		return gerundet;
		// return Math.round(zahl);
	}

	/**
	 * Berechnet die Sitzverteilung nach D'Hondt. Diese Methode wird vorerst
	 * nicht genutzt. Sie koennte als alternative Berechnungsmethode genutzt
	 * werden oder man koennte mit ihr die Benachteiligung der kleineren
	 * Parteien anzeigen.
	 * 
	 * @param bundestagswahl
	 *            die zu berechnende Bundestagswahl
	 * @throws wenn
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
				// Queue für die Berechnung der Zweitstimmen
				LinkedList<Double> berechneteZweitstimmen = new LinkedList<Double>();
				berechneteZweitstimmen.add((double) bundesland
						.getAnzahlZweitstimmen(partei));
				parteiStimme.put(partei, berechneteZweitstimmen);

			}
			// Restliche Stimmen für die Partei bestimmen
			for (int i = 1; i < sitzeBundesland; i++) {
				Set<Partei> set = parteiStimme.keySet();
				Iterator<Partei> iterator = set.iterator();

				while (iterator.hasNext()) {
					Partei key = (Partei) iterator.next();
					parteiStimme.get(key).add(
							(double) bundesland.getAnzahlZweitstimmen(key)
									/ (Math.pow(2, (double) i)));
					System.out.print("");
				}
			}

			for (int i = 0; i < sitzeBundesland; i++) {
				Partei sieger = null;
				LinkedList<Double> siegerliste = null;
				// Speichert den größten Wert
				double maximum = 0;
				// Speichert den aktuellen Wert der Partei
				double mom = 0;
				Set<Partei> set = parteiStimme.keySet();
				Iterator<Partei> iterator = set.iterator();

				while (iterator.hasNext()) {
					Partei key = (Partei) iterator.next();

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

			Set<Partei> set = parteiSitze.keySet();
			Iterator<Partei> iterator = set.iterator();

			while (iterator.hasNext()) {
				Partei key = (Partei) iterator.next();
				sitzanzahl += bundesland.getDirektMandate(key).size();// Math.max(parteiSitze.get(key),bundesland.getDirektMandate(key).size());

				System.out.println("B: (" + sitzeBundesland + ") " + bundesland
						+ " P:" + key.getName() + " : " + parteiSitze.get(key));
			}
			sitzebl += sitzeBundesland;

		}
		System.out.println(sitzanzahl);
		System.out.println(sitzebl);

		return bundestagswahl;
	}
	/**
	 * Berechnet die Sitzverzeilung mit Hilfe des Sainte-Lauge-Verfahren. Dabei
	 * werden keine Ausgleichsmandate berechnet.
	 * 
	 * @param bundestagswahl
	 *            die zu berechnende Bundestagswahl.
	 * @return die berechnete Bundestagswahl.
	 */
	public Bundestagswahl berechneSainteLague(Bundestagswahl bundestagswahl) {
		// Initialisierung:
		this.initialisiere(bundestagswahl);
		
		// Direktmandate bestimmen
		bundestagswahl = this.berechneDirektmandat(bundestagswahl);
		
		// Relevante Parteien bestimmen
		LinkedList<Partei> relevanteParteien = this.berechneRelevanteParteien(bundestagswahl);
		
		// Zuteilungsdivisor bestimmen
		float zuteilungsdivisor = this.berechneZuteilungsdivisor(bundestagswahl);
		if (Debug.isAktiv()) {
			System.out.println("Zuteilungsdivisor: " + zuteilungsdivisor);
			int summe = 0;
			for (Bundesland bl : bundestagswahl.getDeutschland().getBundeslaender()) {
				int zahl = this.runden(bl.getEinwohnerzahl() / zuteilungsdivisor, false);
				summe += zahl;
				System.out.println(bl.getName() + ": " + zahl + " Einwohner: "
						+ bl.getEinwohnerzahl());
			}
			System.out.println("Summe aller Sitze: " + summe);
		}
		
		float landesdivisor = 0;
		
		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {

			int sitzeBundesland = this.runden(bundesland.getEinwohnerzahl()
					/ zuteilungsdivisor, false);
			landesdivisor = bundesland.getAnzahlZweitstimmen() / sitzeBundesland;

			//System.err.println("SitzeBundesland "+sitzeBundesland+" "+bl.getEinwohnerzahl()+" "+zuteilungsdivisor);
			int sitzePartei = 0;
			while (sitzePartei != sitzeBundesland) {
				sitzePartei = 0;

				for (Partei part : relevanteParteien) {

					sitzePartei += this.runden(
							bundesland.getAnzahlZweitstimmen(part)
									/ landesdivisor, false);
				}
				if(sitzePartei == sitzeBundesland) {
					break;
				}else if (sitzePartei < sitzeBundesland) {
					landesdivisor -= 10;
				} else {
					// sitzanzahl > sitzeBundesland
					landesdivisor += 10;
				}
			}

			for (Partei part : relevanteParteien) {
				//Debug.print(part.getName() + "Anzahl Mandate: "+part.getAnzahlMandate());
				// Direktmandate einer Partei im Bundesland
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKTMANDAT,
						bundesland);

				int mindestSitzanzahl = this.runden(
						bundesland.getAnzahlZweitstimmen(part) / landesdivisor,
						false);
				
				// Wichtig zur Bestimmung von Ueberhangmandate
				int diffKandidat = mindestSitzanzahl - direktmandate;

				part.addMindestsitzanzahl(bundesland,
						Math.max(direktmandate, mindestSitzanzahl));
				/*if(part.getName().equals("CDU") && bundesland.getName().equals("Saarland")){
					System.out.println("###Landesdivisor: "+landesdivisor+" Zweitstimmen: "+bundesland.getAnzahlZweitstimmen(part)+" | " + direktmandate+" "+mindestSitzanzahl+" "+Math.max(direktmandate, mindestSitzanzahl)+" Diff:"+diffKandidat);
				}*/
				if (diffKandidat >= 0) {
					for (int i = 0; i < diffKandidat; i++) {
						// Nehme aus der Bundestagswahl die Landesliste der
						// Partei und fuege den i-ten Listenkandidaten in die
						// Sitzverteilung hinzu
						// Dabei darf der Kandidat kein Wahlkreissieger sein
						if (bundesland.getLandesliste(part)
								.getListenkandidaten().size() >= i + 1) {
							// TODO Testen
							Kandidat neuerAbgeordneter = bundesland
									.getLandesliste(part).getListenkandidaten()
									.get(i);
							// Hat der Kandidat schon ein Mandat?
							if (neuerAbgeordneter.getMandat() == Mandat.KEINMANDAT) {
								bundestagswahl.getSitzverteilung()
										.addAbgeordnete(neuerAbgeordneter);
								neuerAbgeordneter.setMandat(Mandat.LISTENMANDAT);
								bundestagswahl
										.getSitzverteilung()
										.getBericht()
										.zeileHinzufuegen(neuerAbgeordneter.getName(),
												neuerAbgeordneter.getPartei().getName(),
												Mandat.LISTENMANDAT.toString(), bundesland.getName(),
												"");
							} else {
								// Kandidat hat schon ein Mandat, deswegen wird
								// diffKandidat erhoeht, damit die Schleife den
								// fehlenden Kandidaten ausgleicht. Der Kandidat
								// wird sozusagen Uebersprungen.
								//System.out.println("Diffkandidat: "+diffKandidat);
								diffKandidat++;
							}
						} else {
							//throw new IllegalArgumentException("Mieeeeep. Kein Listenkandidat gefunden.");
							// TODO negatives Stimmengewicht
						}
					}
				} else {
					//System.err.println(bundesland.getName()+" - "+part.getName()+" - "+Math.abs(diffKandidat));
					//Kandidat ueberhangmandat;
					for (int i = 0; i < Math.abs(diffKandidat); i++) {
						/*ueberhangmandat = bundesland.getDirektMandate(part).get(i);
						ueberhangmandat.setMandat(Mandat.UEBERHANGMADAT);
						bundestagswahl.getSitzverteilung().getBericht()
						.zeileHinzufuegen(ueberhangmandat.getName(),
								ueberhangmandat.getPartei().getName(),
								Mandat.UEBERHANGMADAT.toString(), 
								bundesland.getName(),
								"");*/
						part.incrementUeberhangMandate(bundesland);
					}
					
				}

			}
			if (Debug.isAktiv()) {
				
				Debug.print("\nLandesdivisor " + bundesland.getName() + ": "
						+ landesdivisor);
				int sum = 0;
				for (Partei part : relevanteParteien) {

					Debug.print("" + part.getName() + ": "
							+ bundesland.getAnzahlZweitstimmen(part) + " - "
							+ part.getMindestsitzanzahl(bundesland));
					sum += part.getMindestsitzanzahl(bundesland);
					//Debug.print("Anzahl Mandate: "+part.getAnzahlMandate());
				}
				Debug.print("Summe: " + sum);

			}

		}

		Debug.print("\nSitzverteilung");
		int summe = 0;
		for (Partei partei : relevanteParteien) {
			/*
			 * if(partei.getName().equals("SPD")){ for(Bundesland bl :
			 * bw.getDeutschland().getBundeslaender()){
			 * System.out.println(bl
			 * .getName()+": "+partei.getMindestsitzanzahl(bl)); } }
			 */
			Debug.print(partei.getName() + ": "
					+ partei.getMindestsitzAnzahl());
			summe += partei.getMindestsitzAnzahl();
		}
		Debug.print("Summe: " + summe);
		return bundestagswahl;
	}
}

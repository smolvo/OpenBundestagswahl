package mandatsrechner;

import model.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList; //Spaeter rausmachen
import java.util.Set;

import test.Debug;

public class Mandatsrechner2009 {

	/** Konstante die bei der Ueberpruefung der Sperrklausel benoetigt wird**/
	private final int MINDIREKTMANDATE = 3;

	/** Entwurfsmuster: Einzelst�ck */
	private static Mandatsrechner2009 instanz; 
	
	private Mandatsrechner2009(){
		
	}
	
	
	public static Mandatsrechner2009 getInstance(){
		if(instanz == null){
			instanz = new Mandatsrechner2009();
		}
		return instanz;
	}
	/**
	 * Berechnet die Wahlkreissieger jedes Wahlkreises und erstellt einen
	 * Eintrag im Bericht.Die Methode ist oeffentlich da diese Methode auch von
	 * Mandatsrechner2013 genutzt wird.
	 * 
	 * @param bundestagswahl
	 *            Die zu berechnende Bundestagswahl
	 * @return die berechnete Bundestagswahl mit Direktmandate
	 * @throws wenn die Bundestagswahl null ist
	 */
	protected Bundestagswahl berechneDirektmandat(Bundestagswahl bundestagswahl) {
		if(bundestagswahl == null){
			throw new IllegalArgumentException("Bundestagswahl ist null.");
		}
		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {
			for (Wahlkreis wahlkreis : bundesland.getWahlkreise()) {
				int max = 0;
				Kandidat gewinner = null;
				for (Erststimme erst : wahlkreis.getErststimmen()) {
					// TODO parallelitaet!
					// TODO Kandidaten mit gleicher Erststimmenanzahl

					if (max < erst.getAnzahl()) {
						// Kandidaten Mandat zuweisen und als Wahlkreissieger in
						// den Wahlkreis eintragen
						gewinner = erst.getKandidat();
						max = erst.getAnzahl();
					}
				}
				/*
				 * bekommt ein Direktmandat und wird als Wahlkreissieger
				 * im Wahlklreis eingetragen
				 */
				gewinner.setMandat(Mandat.DIREKTMANDAT);
				wahlkreis.setWahlkreisSieger(gewinner);
				bundestagswahl.getSitzverteilung().addAbgeordnete(gewinner);
				// Eintrag in der Sitzverteilung erstellen
				bundestagswahl.getSitzverteilung().addBerichtEintrag(
						gewinner.getName() + " von der "
								+ gewinner.getPartei().getName()
								+ " gewinnt im Wahlkreis "
								+ wahlkreis.getName() + " in "
								+ bundesland.getName() + " ein "
								+ Mandat.DIREKTMANDAT.toString());
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
	 * @throws wenn die Bundestagswahl leer ist oder die Anzahl negativ ist.
	 * @return die Liste mit den relevanten Parteien
	 */
	protected LinkedList<Partei> berechneRelevanteParteien(
			Bundestagswahl bundestagswahl) {
		if(bundestagswahl == null){
			throw new IllegalArgumentException("Bundestagswahl leer oder die Sperrklauselanzahl ist negativ.");
		}
		LinkedList<Partei> relevanteParteien = new LinkedList<Partei>();
		int sperrklauselAnzahl = bundestagswahl.getDeutschland().getZweitstimmeGesamt()/20;
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
		float zuteilungsdivisor = bundestagswahl.getDeutschland()
				.getEinwohneranzahl() / minSitze;
		int sitzanzahl;
		for (;;) {
			sitzanzahl = 0;
			for (Bundesland bundesland : bundestagswahl.getDeutschland()
					.getBundeslaender()) {
				sitzanzahl += Math.round(bundesland.getEinwohnerzahl()
						/ zuteilungsdivisor);
			}
			if (sitzanzahl == minSitze) {
				break;
			} else if (sitzanzahl < minSitze) {
				zuteilungsdivisor -= 1;
			} else {
				// sitzanzahl > minSitze
				zuteilungsdivisor += 1;
			}
		}

		return zuteilungsdivisor;
	}

	/**
	 * Rundet die Kommazahl mit dem gew�nschten Rundungsalgprithmus auf oder ab.
	 * 
	 * @param zahl
	 *            die zu rundende zahl.
	 * @throws wenn zahl negativ ist.
	 * @return die gerundete zahl.
	 */
	protected int runden(float zahl, boolean randomize) {
		if(zahl < 0){
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
		} else if (kommastelle > 5) {
			gerundet = (int) Math.ceil(zahl);
		} else {
			gerundet = (int) Math.floor(zahl);
		}
		// System.err.println("###### "+zahl+" "+gerundet);
		return gerundet;
		// return Math.round(zahl);
	}

	/**
	 * Berechnet die Sitzverteilung nach D'Hondt. Diese Methode wird vorerst
	 * nicht genutzt. Sie koennte als alternative Berechnungsmethode genutzt
	 * werden oder man koennte mit ihr die Benachteiligung der kleineren Parteien
	 * anzeigen.
	 * 
	 * @param bundestagswahl
	 *            die zu berechnende Bundestagswahl
	 * @throws wenn die Bundestagswahl leer ist.
	 * @return die berechnete Bundestagswahl
	 */
	public Bundestagswahl berechneHondt(Bundestagswahl bundestagswahl) {
		if(bundestagswahl == null ){
			throw new IllegalArgumentException("Bundestagswahl ist null");
		}
		// Initialisierung:
		int sperrklauselAnzahl = bundestagswahl.getDeutschland()
				.getZweitstimmeGesamt() / 20;
		bundestagswahl.setSitzverteilung(new Sitzverteilung(
				new LinkedList<Kandidat>(), ""));
		// Setze alle Kandidaten auf wieder zurueck
		for (Partei partei : bundestagswahl.getParteien()) {
			for (Kandidat kandidat : partei.getMitglieder()) {
				kandidat.setMandat(Mandat.KEINMANDAT);
			}
		}
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
						.getZweitstimmenAnzahl(partei));
				parteiStimme.put(partei, berechneteZweitstimmen);

			}
			// Restliche Stimmen für die Partei bestimmen
			for (int i = 1; i < sitzeBundesland; i++) {
				Set<Partei> set = parteiStimme.keySet();
				Iterator<Partei> iterator = set.iterator();

				while (iterator.hasNext()) {
					Partei key = (Partei) iterator.next();
					parteiStimme.get(key).add(
							(double) bundesland.getZweitstimmenAnzahl(key)
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
	 * @param bundestagswahl die zu berechnende Bundestagswahl.
	 * @return die berechnete Bundestagswahl.
	 */
	public Bundestagswahl berechneSainteLague(Bundestagswahl bundestagswahl) {
		// Initialisierung:
		bundestagswahl.setSitzverteilung(new Sitzverteilung(
				new LinkedList<Kandidat>(), ""));
		// Setze alle Kandidaten auf wieder zurueck
		for (Partei partei : bundestagswahl.getParteien()) {
			for (Kandidat kandidat : partei.getMitglieder()) {
				kandidat.setMandat(Mandat.KEINMANDAT);
			}
		}
		//Direktmandate bestimmen
		bundestagswahl = berechneDirektmandat(bundestagswahl);
		LinkedList<Partei> relevanteParteien = berechneRelevanteParteien(bundestagswahl);
		float zuteilungsdivisor = this
				.berechneZuteilungsdivisor(bundestagswahl);
		float landesdivisor = 0;

		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {

			int sitzeBundesland = this.runden(bundesland.getEinwohnerzahl()
					/ zuteilungsdivisor, false);
			landesdivisor = bundesland.getZweitstimmeGesamt() / sitzeBundesland;

			// System.err.println("SitzeBundesland "+sitzeBundesland+" "+bl.getEinwohnerzahl()+" "+zuteilungsdivisor);
			for (;;) {
				int sitzePartei = 0;

				for (Partei part : relevanteParteien) {
					
					sitzePartei += this.runden(
							bundesland.getZweitstimmenAnzahl(part)
									/ landesdivisor, false);
				}
				if (sitzePartei == sitzeBundesland) {
					break;
				} else if (sitzePartei < sitzeBundesland) {
					landesdivisor -= 10;
				} else {
					// sitzanzahl > sitzeBundesland
					landesdivisor += 10;
				}
			}

			for (Partei part : relevanteParteien) {
				//Direktmandate einer Partei im Bundesland
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKTMANDAT,
						bundesland);

				int mindestSitzanzahl = this.runden(
						bundesland.getZweitstimmenAnzahl(part) / landesdivisor,
						false);
				//Wichtig zur Bestimmung von Ueberhangmandate
				int diffKandidat = mindestSitzanzahl - direktmandate;
				
				part.addMindestsitzanzahl(bundesland,
						Math.max(direktmandate, mindestSitzanzahl));
				// System.err.println(direktmandate+" "+mindestSitzanzahl+" "+Math.max(direktmandate,
				// mindestSitzanzahl));
				if (diffKandidat > 0) {
					for (int i = 0; i <= diffKandidat; i++) {
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
								neuerAbgeordneter.setMandat(Mandat.MANDAT);
								bundestagswahl.getSitzverteilung().addBerichtEintrag(
										neuerAbgeordneter.getName() + " von der "
												+ neuerAbgeordneter.getPartei().getName()
												+ " bekommt in dem Bundesland "
												+ bundesland.getName() + " ein "
												+ Mandat.MANDAT.toString());
							} else {
								// Kandidat hat schon ein Mandat, deswegen wird
								// diffKandidat erhoeht, damit die Schleife den
								// fehlenden Kandidaten ausgleicht. Der Kandidat
								// wird sozusagen Uebersprungen.
								diffKandidat++;
							}
						} else {
							// TODO negatives Stimmengewicht
						}
					}
				} else {
					// System.err.println("-"+Math.abs(diffKandidat));
					String neuerEintrag = "Die Kandidaten ";
					for (int i = 0; i < Math.abs(diffKandidat); i++) {
						bundesland.getDirektMandate(part).get(i)
								.setMandat(Mandat.UEBERHANGMADAT);
						neuerEintrag += bundesland.getDirektMandate(part).get(i).getName()+" ,";
					}
					neuerEintrag += " sind in dem Bundesland "+bundesland.getName()+" Ueberhangmandate";
					bundestagswahl.getSitzverteilung().addBerichtEintrag(neuerEintrag);
				}

			}
			if (Debug.isAktiv()) {
				Debug.print("\nLandesdivisor " + bundesland.getName()
						+ ": " + landesdivisor);
				int sum = 0;
				for (Partei part : relevanteParteien) {

					Debug.print("" + part.getName() + ": "
							+ bundesland.getZweitstimmenAnzahl(part) + " - "
							+ part.getMindestsitzanzahl(bundesland));
					sum += part.getMindestsitzanzahl(bundesland);
				}
				Debug.print("Summe: " + sum);

			}

		}

		return bundestagswahl;
	}

	public Bundestagswahl berechneAlles(Bundestagswahl bundestagswahl) {
		
		//Momentaner Platzhalter, es wird die Methode berechneSainteLague() benutzt.
		return this.berechneSainteLague(bundestagswahl);
		
		/*
		// Initialisierung:
		this.sperrklauselAnzahl = bw.getDeutschland().getZweitstimmeGesamt() / 20;
		bw.setSitzverteilung(new Sitzverteilung(new LinkedList<Kandidat>(), ""));
		this.relevanteParteien = new LinkedList<Partei>();
		// bw.getSitzverteilung().setAbgeordnete(new LinkedList<Kandidat>());
		// Setze alle Kandidaten auf wieder zurueck
		for (Partei partei : bw.getParteien()) {
			for (Kandidat kandidat : partei.getMitglieder()) {
				kandidat.setMandat(Mandat.KEINMANDAT);
			}
		}
		// **Sitze fuer jedes Bundesland mithilge des zuteilungsdivisor
		// berechnen
		float zuteilungsdivisor = 0;
		boolean isCorrect = false;
		int sitzanzahl;
		zuteilungsdivisor = bw.getDeutschland().getEinwohneranzahl() / minSitze;
		while (!isCorrect) {
			sitzanzahl = 0;
			for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
				// TODO Nach erster Nachkommastelle
				sitzanzahl += Math.round(bl.getEinwohnerzahl()
						/ zuteilungsdivisor);
			}
			if (sitzanzahl == minSitze) {
				isCorrect = true;
			} else if (sitzanzahl < minSitze) {
				zuteilungsdivisor -= 10;
			} else {
				// sitzanzahl > minSitze
				zuteilungsdivisor += 10;
			}
		}
		// **Ende
		// System.err.println(zuteilungsdivisor);
		if (super.debug) {
			System.out.println("Zuteilungsdivisor: " + zuteilungsdivisor);
			int summe = 0;
			for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
				int zahl = Math
						.round(bl.getEinwohnerzahl() / zuteilungsdivisor);
				summe += zahl;
				System.out.println(bl.getName() + ": " + zahl);
			}
			System.out.println("Summe aller Sitze: " + summe);
		}

		// **Direkmandate bestimmen
		for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
			for (Wahlkreis wk : bl.getWahlkreise()) {
				int max = 0;
				Kandidat gewinner = null;
				for (Erststimme erst : wk.getErststimmen()) {
					// TODO parallelitaet!
					// TODO Kandidaten mit gleicher Erststimmenanzahl

					if (max < erst.getAnzahl()) {
						// Kandidaten Mandat zuweisen und als Wahlkreissieger in
						// den Wahlkreis eintragen
						gewinner = erst.getKandidat();
						max = erst.getAnzahl();
					}
				}

				gewinner.setMandat(Mandat.DIREKTMANDAT);
				wk.setWahlkreisSieger(gewinner);
				bw.getSitzverteilung().addAbgeordnete(gewinner);
			}

		}

		// **Ende
		// **relevanten Parteien bestimmen

		LinkedList<Partei> alleParteien = bw.getParteien(); // Alle Parteien der
															// Bundestagswahl
		// relevante Parteien

		for (Partei part : alleParteien) {

			if (part.getZweitstimmeGesamt() >= this.sperrklauselAnzahl
					|| part.getAnzahlDirektmandate() >= this.minDirektmandate) {
				// Partei im Bundestag falls Anforderungen erfuellt sind.
				part.setImBundestag(true);
				// Partei in die Liste hinzufuegen
				if (!relevanteParteien.contains(part)) {
					relevanteParteien.add(part);
				}
			} else {
				// Ansonsten ist die Partei nicht im Bundestag.
				part.setImBundestag(false);
			}

		}

		if (this.debug) {
			System.out.println("\nParteien im Bundestag:");
			for (Partei part : bw.getParteien()) {
				System.out.println(part.getName() + ": "
						+ ((part.isImBundestag()) ? "Ja" : "Nein"));
			}
		}

		float landesdivisor = 0;
		for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {

			int sitzeBundesland = this.runden(bl.getEinwohnerzahl()
					/ zuteilungsdivisor, false);
			landesdivisor = bl.getZweitstimmeGesamt() / sitzeBundesland;
			isCorrect = false;
			// System.err.println("SitzeBundesland "+sitzeBundesland+" "+bl.getEinwohnerzahl()+" "+zuteilungsdivisor);
			while (!isCorrect) {
				int sitzePartei = 0;

				for (Partei part : relevanteParteien) {
					// TODO Nach erster Nachkommastelle
					sitzePartei += this.runden(bl.getZweitstimmenAnzahl(part)
							/ landesdivisor, false);
				}
				if (sitzePartei == sitzeBundesland) {
					isCorrect = true;
				} else if (sitzePartei < sitzeBundesland) {
					landesdivisor -= 10;
				} else {
					// sitzanzahl > sitzeBundesland
					landesdivisor += 10;
				}
			}

			for (Partei part : relevanteParteien) {
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKTMANDAT,
						bl);

				int mindestSitzanzahl = this.runden(
						bl.getZweitstimmenAnzahl(part) / landesdivisor, false);
				int diffKandidat = mindestSitzanzahl - direktmandate;
				part.addMindestsitzanzahl(bl,
						Math.max(direktmandate, mindestSitzanzahl));
				// System.err.println(direktmandate+" "+mindestSitzanzahl+" "+Math.max(direktmandate,
				// mindestSitzanzahl));
				if (diffKandidat > 0) {
					for (int i = 0; i <= diffKandidat; i++) {
						// Nehme aus der Bundestagswahl die Landesliste der
						// Partei und fuege den i-ten Listenkandidaten in die
						// Sitzverteilung hinzu
						// Dabei darf der Kandidat kein Wahlkreissieger sein
						if (bl.getLandesliste(part).getListenkandidaten()
								.size() >= i + 1) {
							// TODO Testen
							Kandidat neuerAbgeordneter = bl
									.getLandesliste(part).getListenkandidaten()
									.get(i);
							// Hat der Kandidat schon ein Mandat?
							if (neuerAbgeordneter.getMandat() == Mandat.KEINMANDAT) {
								bw.getSitzverteilung().addAbgeordnete(
										bl.getLandesliste(part)
												.getListenkandidaten().get(i));
								bl.getLandesliste(part).getListenkandidaten()
										.get(i).setMandat(Mandat.MANDAT);
							} else {
								// Kandidat hat schon ein Mandat, deswegen wird
								// diffKandidat erhoeht, damit die Schleife den
								// fehlenden Kandidaten ausgleicht. Der Kandidat
								// wird sozusagen Uebersprungen.
								diffKandidat++;
							}
						} else {
							// TODO negatives Stimmengewicht
						}
					}
				} else {
					// System.err.println("-"+Math.abs(diffKandidat));
					for (int i = 0; i < Math.abs(diffKandidat); i++) {
						bl.getDirektMandate(part).get(i)
								.setMandat(Mandat.UEBERHANGMADAT);
					}
				}

			}
			if (this.debug) {
				System.out.println("\nLandesdivisor " + bl.getName() + ": "
						+ landesdivisor);
				int sum = 0;
				for (Partei part : relevanteParteien) {
					System.out.println("" + part.getName() + ": "
							+ bl.getZweitstimmenAnzahl(part) + " - "
							+ part.getMindestsitzanzahl(bl));
					sum += part.getMindestsitzanzahl(bl);
				}
				System.out.println("Summe: " + sum);

			}

		}
		if (this.debug) {
			System.out.println("\nSitzverteilung");
			int summe = 0;
			for (Partei partei : relevanteParteien) {
				System.out.println(partei.getName() + ": "
						+ partei.getMindestsitzAnzahl());
				summe += partei.getMindestsitzAnzahl();
			}
			System.out.println("Summe: " + summe);
		}
		System.out.println(bw.getSitzverteilung().getAbgeordnete().size());
		return bw;
		*/
	}


}

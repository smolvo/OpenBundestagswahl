package main.java.mandatsrechner;

import java.util.LinkedList;
import java.util.List;

import test.java.Debug;
import main.java.model.BerichtDaten;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;

/**
 * Mandatsrechner mit dem Verteilungsverfahren von SainteLague/Schepers. Diese
 * Klasse ist ähnlich aufgebaut wie Mandatsrechner2009 nur das auch
 * Ausgleichmandate berechnet werden.
 * 
 */
public class Mandatsrechner2013 {

	/*
	 * Da der Mandatsrechner 2009 fast das gleiche Verafahren benutzt werden die
	 * Funktionen der Klasse für die Berechnung der Sitzverteilung in dieser
	 * Klasse genutzt.
	 */
	private Mandatsrechner2009 rechner2009 = Mandatsrechner2009.getInstance();

	/** Entwurdmuster: Einzelstueck */
	private static Mandatsrechner2013 instanz;

	/**
	 * Privater Konstruktor fuer das Entwurfsmuster Einzelstueck.
	 */
	public Mandatsrechner2013() {
	}

	/**
	 * Gibt die Instanz der Klasse zurueck.
	 * 
	 * @return die Instanz der Klasse.
	 */
	public static Mandatsrechner2013 getInstance() {
		if (instanz == null) {
			instanz = new Mandatsrechner2013();
		}
		return instanz;
	}

	public Bundestagswahl berechneAlles(Bundestagswahl bw) {
		
		// Initialisierung:
		bw.setSitzverteilung(new Sitzverteilung(new LinkedList<Kandidat>(), new BerichtDaten()));
		// Setze alle Kandidaten auf wieder zurueck
		for (Partei partei : bw.getParteien()) {
			for (Kandidat kandidat : partei.getMitglieder()) {
				kandidat.setMandat(Mandat.KEINMANDAT);
			}
		}

		// Berechne Direktmandate
		this.rechner2009.berechneDirektmandat(bw);

		// Bestimme Relevante Parteien.
		List<Partei> relevanteParteien = this.rechner2009
				.berechneRelevanteParteien(bw);

		// Bestimme die Mandate der Zweitstimmen ( + Unterverteilung)
		this.berechneZweitstimmenMandate(bw, relevanteParteien);

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
		

		// Ausgleichsmandate (Schritt 3 - Oberverteilung)
		this.berechneSitzeZweitstimmenverhaeltnis(bw, relevanteParteien);

		return bw;

	}

	

	private float berechneParteidivisor(List<Partei> relevanteParteien) {

		/*
		 * Finden eines geeigneten minimalen Parteidivisors um die anzahl der
		 * neuen Mindestsitze zu bestimmen. Der Parteidivisor ist
		 * min(zweitstimme einer partei / (mindestsitze - 0.5));
		 */
		float parteidivisor = 0;
		for (Partei partei : relevanteParteien) {
			if (parteidivisor == 0) {
				parteidivisor = (float) (partei.getZweitstimmeGesamt() / (partei
						.getMindestsitzAnzahl() - 0.5));
			} else {
				parteidivisor = (float) Math.min(parteidivisor, (partei
						.getZweitstimmeGesamt() / (partei
						.getMindestsitzAnzahl() - 0.5)));
			}
			// System.err.println(partei.getZweitstimmeGesamt() + " "+
			// partei.getMindestsitzAnzahl() +" "+parteidivisor);
		}

		if (Debug.isAktiv()) {
			Debug.print("\nAlt Parteidivisor: " + parteidivisor);
			int summe = 0;
			for (Partei partei : relevanteParteien) {
				System.out
						.println(partei.getName()
								+ ": "
								+ ((int) (partei.getZweitstimmeGesamt() / parteidivisor)));
				summe += ((int) (partei.getZweitstimmeGesamt() / parteidivisor));
			}
			Debug.print("Summe: " + summe);

		}

		/**
		 * Der Parteidivisor wird so lange erniedrigt, bis alle Parteien ihre
		 * Anzahl an Mindestsitze erfuellen.
		 * 
		 */
		boolean isCorrect = false;
		while (!isCorrect) {
			isCorrect = true;
			for (Partei partei : relevanteParteien) {
				int mindestSitze = partei.getMindestsitzAnzahl();
				// System.out.println(Math.round(partei.getZweitstimmeGesamt() /
				// parteidivisor)+ " " + mindestSitze);
				if (Math.round(partei.getZweitstimmeGesamt() / parteidivisor) < mindestSitze) {
					isCorrect = false;
					break;
				}
			}
			if (!isCorrect) {
				parteidivisor -= 1;
			}
		}

		if (Debug.isAktiv()) {
			Debug.print("\nNeu Parteidivisor: " + parteidivisor);
			int summe = 0;
			for (Partei partei : relevanteParteien) {
				Debug.print(partei.getName()
						+ ": "
						+ (Math.round(partei.getZweitstimmeGesamt()
								/ parteidivisor)));
				summe += (Math.round(partei.getZweitstimmeGesamt()
						/ parteidivisor));
			}
			Debug.print("Summe: " + summe);
		}
		return parteidivisor;
	}




	private void berechneZweitstimmenMandate(Bundestagswahl bw,
			List<Partei> relevanteParteien) {
		float zuteilungsdivisor = this.rechner2009
				.berechneZuteilungsdivisor(bw);
		if (Debug.isAktiv()) {
			System.out.println("Zuteilungsdivisor: " + zuteilungsdivisor);
			int summe = 0;
			for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
				int zahl = this.rechner2009.runden(bl.getEinwohnerzahl() / zuteilungsdivisor, false);
				summe += zahl;
				System.out.println(bl.getName() + ": " + zahl + " Einwohner: "
						+ bl.getEinwohnerzahl());
			}
			System.out.println("Summe aller Sitze: " + summe);
		}
		float landesdivisor = 0;
		boolean isCorrect = false;
		for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {

			int sitzeBundesland = this.rechner2009.runden(bl.getEinwohnerzahl()
					/ zuteilungsdivisor, false);
			landesdivisor = bl.getAnzahlZweitstimmen() / sitzeBundesland;
			isCorrect = false;
			// System.err.println("SitzeBundesland "+sitzeBundesland+" "+bl.getEinwohnerzahl()+" "+zuteilungsdivisor);
			while (!isCorrect) {
				int sitzePartei = 0;

				for (Partei part : relevanteParteien) {
					// TODO Nach erster Nachkommastelle
					sitzePartei += this.rechner2009.runden(bl.getAnzahlZweitstimmen(part)
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

				int mindestSitzanzahl = this.rechner2009.runden(
						bl.getAnzahlZweitstimmen(part) / landesdivisor, false);
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
								bw
								.getSitzverteilung()
								.getBericht()
								.zeileHinzufuegen(neuerAbgeordneter.getName(),
										neuerAbgeordneter.getPartei().getName(),
										Mandat.MANDAT.toString(), bl.getName(),
										"");
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
					Kandidat ueberhangmandat;
					for (int i = 0; i < Math.abs(diffKandidat); i++) {
						ueberhangmandat = bl.getDirektMandate(part).get(i);
						ueberhangmandat.setMandat(Mandat.UEBERHANGMADAT);
						bw.getSitzverteilung().getBericht()
						.zeileHinzufuegen(ueberhangmandat.getName(),
								ueberhangmandat.getPartei().getName(),
								Mandat.UEBERHANGMADAT.toString(), 
								bl.getName(),
								"");
					}
					
				}

			}
			if (Debug.isAktiv()) {
				System.out.println("\nLandesdivisor " + bl.getName() + ": "
						+ landesdivisor);
				int sum = 0;
				for (Partei part : relevanteParteien) {

					System.out.println("" + part.getName() + ": "
							+ bl.getAnzahlZweitstimmen(part) + " - "
							+ part.getMindestsitzanzahl(bl));
					sum += part.getMindestsitzanzahl(bl);
				}
				System.out.println("Summe: " + sum);

			}

		}
	}

	private void berechneSitzeZweitstimmenverhaeltnis(Bundestagswahl bw,
			List<Partei> relevanteParteien) {
		/**
		 * Die Zweitstimmen einer Partei dividiert durch den parteidivisor
		 * ergeben nun die neue Mindestanzahl an Sitzen. Nun wird ein
		 * Multiplikator bestimmt, der die Ausgleichsmandate einer Parei auf die
		 * Landeslisten dieser Partei im Verh�ltnis der Zweitstimmen verteilt.
		 */
		float parteidivisor = this.berechneParteidivisor(relevanteParteien);
		boolean isCorrect = false;
		for (Partei partei : relevanteParteien) {
			int neueSitzanzahl = this.rechner2009.runden(partei.getZweitstimmeGesamt()
					/ parteidivisor, true);
			int diffSitze = neueSitzanzahl - partei.getMindestsitzAnzahl();
			// System.out.println(partei.getName() + " " + diffSitze);
			if (diffSitze > 0) {
				isCorrect = false;
				float multiplikator = 0.1f;
				while (!isCorrect) {
					int sitzeBundesland = 0;
					for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
						sitzeBundesland += this.rechner2009
								.runden(partei.getMindestsitzanzahl(bl)
										* multiplikator, true);

					}

					// System.err.println(partei.getName() + " " +
					// multiplikator);
					if (sitzeBundesland == neueSitzanzahl) {
						isCorrect = true;
					} else if (sitzeBundesland < neueSitzanzahl) {
						multiplikator += 0.1f;
					} else {
						multiplikator -= 0.001f;
					}
				}
				for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
					int sitzeBundesland = this.rechner2009.runden(
							partei.getMindestsitzanzahl(bl) * multiplikator,
							true);
					/*
					 * if(partei.getName().equals("SPD") &&
					 * bl.getName().equals("Bayern")){
					 * System.out.println(bl.getName
					 * ()+": "+partei.getMindestsitzanzahl
					 * (bl)+" "+sitzeBundesland); List<Kandidat> kandidaten =
					 * partei.getMitglieder(); int count = 1; for(Kandidat k :
					 * kandidaten){
					 * 
					 * if(!k.getMandat().equals(Mandat.KEINMANDAT) &&
					 * k.getLandesliste()!=null &&
					 * k.getLandesliste().getBundesland().equals(bl) ){
					 * System.out.println(count+": "+k.getName()+
					 * " "+k.getMandat()); count++; }
					 * 
					 * } }
					 */
					if (sitzeBundesland != partei.getMindestsitzanzahl(bl)) {
						int diffSitzeBundesland = sitzeBundesland
								- partei.getMindestsitzanzahl(bl);
						for (int i = 0; i < diffSitzeBundesland; i++) {
							Kandidat neuerAbgeordneter = partei
									.getLandesliste(bl).getListenkandidaten()
									.get(i);
							if (neuerAbgeordneter == null) {
								// Negatives Stimmgewicht.
								System.err
										.println("Kein Abgeordneter gefunden.");
							} else if (neuerAbgeordneter.getMandat() == Mandat.KEINMANDAT) {
								bw.getSitzverteilung().addAbgeordnete(
										neuerAbgeordneter);
								neuerAbgeordneter
										.setMandat(Mandat.AUSGLEICHSMANDAT);
								bw
								.getSitzverteilung()
								.getBericht()
								.zeileHinzufuegen(neuerAbgeordneter.getName(),
										neuerAbgeordneter.getPartei().getName(),
										Mandat.AUSGLEICHSMANDAT.toString(), bl.getName(),
										"");
							} else {

								diffSitzeBundesland += 1;
							}
							// System.err.println("Add "+diffSitzeBundesland+" candidates from "+partei.getName()+" to "+bl.getName());
						}

					}
				}

			}

		}
	}

}

package main.java.mandatsrechner;

import java.util.LinkedList;
import java.util.List;

import test.java.Debug;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.model.Partei;

/**
 * Mandatsrechner mit dem Verteilungsverfahren von SainteLague/Schepers. Diese
 * Klasse ist ähnlich aufgebaut wie Mandatsrechner2009 nur das auch
 * Ausgleichmandate berechnet werden.
 * 
 */
public class Mandatsrechner2013 {

	/*
	 * Da der Mandatsrechner 2009 fast das gleiche Verafahren benutzt werden die
	 * Funktionen der Klasse fuer die Berechnung der Sitzverteilung in dieser
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
		
		//this.rechner2009.initialisiere(bw);

		// Berechne Direktmandate
		//this.rechner2009.berechneDirektmandat(bw);

		

		// Bestimme die Mandate der Zweitstimmen ( + Unterverteilung)
		bw = this.berechneSainteLague(bw);
		//this.rechner2009.berechneSainteLague(bw);
		// Bestimme Relevante Parteien.
		List<Partei> relevanteParteien = this.rechner2009.berechneRelevanteParteien(bw);
			
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

		/*if (Debug.isAktiv()) {
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

		}*/

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


	private void berechneSitzeZweitstimmenverhaeltnis(Bundestagswahl bw,
			List<Partei> relevanteParteien) {
		/**
		 * Die Zweitstimmen einer Partei dividiert durch den parteidivisor
		 * ergeben nun die neue Mindestanzahl an Sitzen. Nun wird ein
		 * Multiplikator bestimmt, der die Ausgleichsmandate einer Parei auf die
		 * Landeslisten dieser Partei im Verh�ltnis der Zweitstimmen verteilt.
		 */
		float parteidivisor = this.berechneParteidivisor(relevanteParteien);
		//boolean isCorrect = false;
		for (Partei partei : relevanteParteien) {
			int neueSitzanzahl = Math.round(partei.getZweitstimmeGesamt()
					/ parteidivisor);
			int diffSitze = neueSitzanzahl - partei.getAnzahlMandate(); //partei.getMindestsitzAnzahl();
			//System.out.println(partei.getName() + " " + diffSitze + " = " +neueSitzanzahl+ " - "+partei.getAnzahlMandate());
			if (diffSitze > 0) {
				//isCorrect = false;
				float multiplikator = 0.1f;
				int sitzeBundesland = 0;
				while (sitzeBundesland != neueSitzanzahl) {
					sitzeBundesland = 0;
					for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
						sitzeBundesland += this.rechner2009
								.runden(partei.getMindestsitzanzahl(bl)
										* multiplikator, true);

					}

					// System.err.println(partei.getName() + " " +
					// multiplikator);
					if (sitzeBundesland == neueSitzanzahl) {
						break;
					} else if (sitzeBundesland < neueSitzanzahl) {
						multiplikator += 0.1f;
					} else {
						multiplikator -= 0.001f;
					}
				}
				for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
					sitzeBundesland = this.rechner2009.runden(
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
							if(partei.getLandesliste(bl).getListenkandidaten().size() <= i){
								// Wahlgenerator verursacht Wahlen ohne Landeslisten.
								break;
							}
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

			} else {
				
				Debug.print("Ausgleichsmandate: "+partei.getName()+" ("+diffSitze+")");
				List<Kandidat> abgeordnete = partei.getMitglieder(Mandat.LISTENMANDAT);
				for (int i = abgeordnete.size() - 1; i>=(abgeordnete.size() + diffSitze); i--) {
					//System.out.println("-");
					abgeordnete.get(i).setMandat(Mandat.KEINMANDAT);
				}
			}

		}
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
		this.rechner2009.initialisiere(bundestagswahl);
		
		// Direktmandate bestimmen
		bundestagswahl = this.rechner2009.berechneDirektmandat(bundestagswahl);
		
		// Relevante Parteien bestimmen
		LinkedList<Partei> relevanteParteien = this.rechner2009.berechneRelevanteParteien(bundestagswahl);
		
		// Zuteilungsdivisor bestimmen
		float zuteilungsdivisor = this
				.rechner2009.berechneZuteilungsdivisor(bundestagswahl);
		if (Debug.isAktiv()) {
			System.out.println("Zuteilungsdivisor: " + zuteilungsdivisor);
			int summe = 0;
			for (Bundesland bl : bundestagswahl.getDeutschland().getBundeslaender()) {
				int zahl = this.rechner2009.runden(bl.getEinwohnerzahl() / zuteilungsdivisor, false);
				summe += zahl;
				System.out.println(bl.getName() + ": " + zahl + " Einwohner: "
						+ bl.getEinwohnerzahl());
			}
			System.out.println("Summe aller Sitze: " + summe);
		}
		
		float landesdivisor = 0;
		
		for (Bundesland bundesland : bundestagswahl.getDeutschland()
				.getBundeslaender()) {

			int sitzeBundesland = this.rechner2009.runden(bundesland.getEinwohnerzahl()
					/ zuteilungsdivisor, false);
			landesdivisor = bundesland.getAnzahlZweitstimmen() / sitzeBundesland;

			//System.err.println("SitzeBundesland "+sitzeBundesland+" "+bl.getEinwohnerzahl()+" "+zuteilungsdivisor);
			int sitzePartei = 0;
			while (sitzePartei != sitzeBundesland) {
				sitzePartei = 0;

				for (Partei part : relevanteParteien) {

					sitzePartei += this.rechner2009.runden(
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
				// Direktmandate einer Partei im Bundesland
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKTMANDAT,
						bundesland);

				int mindestSitzanzahl = this.rechner2009.runden(
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
				}
				Debug.print("Summe: " + sum);

			}

		}

		return bundestagswahl;
	}

}

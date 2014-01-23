package mandatsrechner;

import model.*;

import java.util.LinkedList; //Spaeter rausmachen

public class Mandatsrechner2009 extends Mandatsrechner {

	private static Mandatsrechner2009 instance;
	private final int minSitze = 598;
	private float zuteilungsdivisor = 0;

	private int sperrklauselAnzahl;
	private final int minDirektmandate = 3;
	/** relevante Dateien **/
	private LinkedList<Partei> relevanteParteien;

	public static Mandatsrechner2009 getInstance() {
		if (Mandatsrechner2009.instance == null) {
			Mandatsrechner2009.instance = new Mandatsrechner2009();
		}
		return Mandatsrechner2009.instance;
	}

	/**
	 * Setzt die neue Parteiliste
	 * 
	 * @param parteiliste
	 *            die neue Parteiliste
	 */
	public void setRelevanteParteien(LinkedList<Partei> parteiliste) {
		if (parteiliste == null) {
			throw new IllegalArgumentException("Parteiliste ist leer");
		}
		this.relevanteParteien = parteiliste;
	}

	/**
	 * Gibt die Liste mit relevanten Parteien
	 * 
	 * @return die Liste mit den relevanten Parteien
	 */
	public LinkedList<Partei> getRelevanteParteien() {
		return this.relevanteParteien;
	}

	public Bundestagswahl berechneAlles(Bundestagswahl bw) {

		// Initialisierung:
		this.sperrklauselAnzahl = bw.getDeutschland().getZweitstimmeGesamt() / 20;
		bw.setSitzverteilung(new Sitzverteilung(new LinkedList<Kandidat>(), ""));
		this.relevanteParteien = new LinkedList<Partei>();
		// bw.getSitzverteilung().setAbgeordnete(new LinkedList<Kandidat>());
		//Setze alle Kandidaten auf wieder zurueck
		for (Partei partei : bw.getParteien()) {
			for (Kandidat kandidat : partei.getMitglieder()) {
				kandidat.setMandat(Mandat.KEINMANDAT);
			}
		}
		// **Sitze fuer jedes Bundesland mithilge des zuteilungsdivisor
		// berechnen
		zuteilungsdivisor = 0;
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
		//System.err.println(zuteilungsdivisor);
		if (super.debug) {
			System.out.println("Zuteilungsdivisor: " + zuteilungsdivisor);
			int summe = 0;
			for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
				int zahl = Math.round(bl.getEinwohnerzahl()
						/ zuteilungsdivisor);
				summe += zahl;
				System.out
						.println(bl.getName()
								+ ": "
								+ zahl);
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
				
				gewinner.setMandat(Mandat.DIREKMANDAT);
				wk.setWahlkreisSieger(gewinner);
				bw.getSitzverteilung().addAbgeordnete(gewinner);
			}
		
		}
		
		// **Ende
		// **relevanten Parteien bestimmen
		/*
		 * Hier findet die Ueberpruefung der Sperrklausel, doch wo sind alle
		 * Parteien? Ich brï¿½uchte eine Liste von allen Parteien die ï¿½berprï¿½ft
		 * werden mï¿½ssen die die Bedingungen erfï¿½llen (Sperrklausel) kommen in
		 * den Bundestag (Flag setzen) Als platzhalter erstelle ich vorerst eine
		 * Liste alleParteien
		 */
		// Platzhalter
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
					/ zuteilungsdivisor);
			landesdivisor = bl.getZweitstimmeGesamt() / sitzeBundesland;
			isCorrect = false;
			//System.err.println("SitzeBundesland "+sitzeBundesland+" "+bl.getEinwohnerzahl()+" "+zuteilungsdivisor);
			while (!isCorrect) {
				int sitzePartei = 0;

				for (Partei part : relevanteParteien) {
					// TODO Nach erster Nachkommastelle
					sitzePartei += this.runden(bl.getZweitstimmenAnzahl(part)
							/ landesdivisor);
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
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKMANDAT,
						bl);
				
				int mindestSitzanzahl = this.runden(bl
						.getZweitstimmenAnzahl(part) / landesdivisor);
				int diffKandidat = mindestSitzanzahl - direktmandate;
				part.addMindestsitzanzahl(bl,
						Math.max(direktmandate, mindestSitzanzahl));
				//System.err.println(direktmandate+" "+mindestSitzanzahl+" "+Math.max(direktmandate, mindestSitzanzahl));
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
							//Hat der Kandidat schon ein Mandat?
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
					//System.err.println("-"+Math.abs(diffKandidat));
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
					System.out.println(""
							+ part.getName()
							+ ": "
							+ bl.getZweitstimmenAnzahl(part)
							+ " - "
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
				System.out.println(partei.getName() + ": " + partei.getMindestsitzAnzahl());
				summe +=  partei.getMindestsitzAnzahl();
			}
			System.out.println("Summe: " + summe);
		}
		return bw;
	}

	@Override
	public Bundestagswahl berechne(Bundestagswahl bw) {
		// TODO Sitzanzahl als konstante setzen

		float bundestagsdivisor = bw.getDeutschland().getEinwohneranzahl() / 598;

		for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
			// TODO Divisor muss so angepasst werden damit die Anzahl der Sitze
			// stimmen
			float sitzeBundesland = bl.getEinwohnerzahl() / bundestagsdivisor;
			berechne(bl, (int) sitzeBundesland);
		}
		return null;
	}

	@Override
	protected Bundesland berechne(Bundesland bl, int sitzeBundesland) {
		// TODO Auto-generated method stub

		int zweitstimmenanzahl = 0;
		for (int i = 0; i < bl.getZweitstimmen().size(); i++) {
			zweitstimmenanzahl += bl.getZweitstimmen().get(i).getAnzahl();
		}
		// Mindestsitzanzahl in landesliste abspeichern
		// Landesdivisor muss immer angepasst werden
		float landesdivisor = zweitstimmenanzahl / sitzeBundesland;

		for (Wahlkreis wk : bl.getWahlkreise()) {
			berechne(wk);
		}

		// TODO Mehr Direktmandaten als mindesitze?

		// TODO Sitze in landesliste setzen

		// TODO Restliche Sitze mit Mandate fï¿½llen

		return bl;
	}

	@Override
	protected Wahlkreis berechne(Wahlkreis wk) {
		// TODO Auto-generated method stub
		if (wk == null) {
			throw new IllegalArgumentException("Wahlkreis ist leer");
		}
		int max = 0;
		Kandidat gewinner = null;
		for (Erststimme erst : wk.getErststimmen()) {
			// TODO parallelitï¿½t!
			// TODO Kandidaten mit gleicher Erststimmenanzahl

			if (max < erst.getAnzahl()) {
				// Kandidaten Mandat zuweisen und als Wahlkreissieger in den
				// Wahlkreis eintragen
				gewinner = erst.getKandidat();
				max = erst.getAnzahl();
			}
		}
		gewinner.setMandat(Mandat.DIREKMANDAT);
		wk.setWahlkreisSieger(gewinner);
		// TODO Eintrag im Bericht fï¿½r den Direktmandat setzen
		return wk;
	}

	@Override
	protected void erstelleBericht(String zeile) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Rundet die Kommazahl mit dem gewünschten
	 * Rundungsalgprithmus auf oder ab.
	 * @param zahl
	 * 		die zu rundende zahl.
	 * @return
	 * 		die gerundete zahl.
	 */
	public int runden(float zahl) {
		
		int kommastelle = (int) ((zahl - (int) zahl) * 10);
		//System.err.println(kommastelle);
		int gerundet = 0;
		/*if (kommastelle == 5) {
			int rand = (Math.random() < 0.5) ? 0 : 1;
			if (rand == 1) {
				gerundet = (int) Math.ceil(zahl);
			} else {
				gerundet = (int) Math.floor(zahl);
			}
		} else */
		if (kommastelle > 5) {
			gerundet = (int) Math.ceil(zahl);
		} else {
			gerundet = (int) Math.floor(zahl);
		}
		//System.err.println("###### "+zahl+" "+gerundet);
		return gerundet;
		//return Math.round(zahl);
	}
	
	public float getZuteilungsdivisor(){
		return this.zuteilungsdivisor;
	}
}

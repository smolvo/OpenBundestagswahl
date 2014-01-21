package mandatsrechner;

import model.*;

import java.util.LinkedList; //Spaeter rausmachen

public class Mandatsrechner2009 extends Mandatsrechner {

	private static Mandatsrechner2009 instance;
	private final int minSitze = 598;
	private float zuteilungsdivisor = 0;

	private int sperrklauselAnzahl;
	private final int minDirektmandate = 3;
	/** relevante Dateien**/
	private LinkedList<Partei> relevanteParteien = new LinkedList<Partei>();
	public static Mandatsrechner2009 getInstance() {
		if (Mandatsrechner2009.instance == null) {
			Mandatsrechner2009.instance = new Mandatsrechner2009();
		}
		return Mandatsrechner2009.instance;
	}
	
	/**
	 * Setzt die neue Parteiliste
	 * @param parteiliste die neue Parteiliste
	 */
	public void setRelevanteParteien(LinkedList<Partei> parteiliste){
		if(parteiliste == null){
			throw new IllegalArgumentException("Parteiliste ist leer");
		}
		this.relevanteParteien = parteiliste;
	}
	
	/**
	 * Gibt die Liste mit relevanten Parteien
	 * @return die Liste mit den relevanten Parteien
	 */
	public LinkedList<Partei> getRelevanteParteien(){
		return this.relevanteParteien;
	}
	
	public Bundestagswahl berechneAlles(Bundestagswahl bw) {

		// Initialisierung:
		this.sperrklauselAnzahl = bw.getDeutschland().getZweitstimmeGesamt() / 20;
		bw.setSitzverteilung(new Sitzverteilung(new LinkedList<Kandidat>(), ""));
		//bw.getSitzverteilung().setAbgeordnete(new LinkedList<Kandidat>());

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
				zuteilungsdivisor -= 0.1;
			} else {
				// sitzanzahl > minSitze
				zuteilungsdivisor += 0.1;
			}
		}
		// **Ende

		if (super.debug) {
			System.out.println("Zuteilungsdivisor: " + zuteilungsdivisor);
			for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
				System.out
						.println(bl.getName()
								+ ": "
								+ Math.round(bl.getEinwohnerzahl()
										/ zuteilungsdivisor));
			}
		}

		// **Direkmandate bestimmen
		for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
			for (Wahlkreis wk : bl.getWahlkreise()) {
				int max = 0;
				Kandidat gewinner = null;
				for (Erststimme erst : wk.getErststimmen()) {
					// TODO parallelit�t!
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
		 * Hier findet die �berpr�fung der Sperrklausel, doch wo sind alle
		 * Parteien? Ich br�uchte eine Liste von allen Parteien die �berpr�ft
		 * werden m�ssen die die Bedingungen erf�llen (Sperrklausel) kommen in
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
				// Partei im Bundestag falls Anforderungen erf�llt sind.
				part.setImBundestag(true);
				// Partei in die Liste hinzuf�gen
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

			int sitzeBundesland = Math.round(bl.getEinwohnerzahl()
					/ zuteilungsdivisor);
			landesdivisor = bl.getZweitstimmeGesamt() / sitzeBundesland;
			isCorrect = false;
			// System.err.println(sitzeBundesland+" "+landesdivisor);
			while (!isCorrect) {
				int sitzePartei = 0;

				for (Partei part : relevanteParteien) {
					// TODO Nach erster Nachkommastelle
					sitzePartei += Math.round(bl.getZweitstimmenAnzahl(part)
							/ landesdivisor);
				}
				// System.out.println(sitzePartei + " " + sitzeBundesland);
				if (sitzePartei == sitzeBundesland) {
					isCorrect = true;
				} else if (sitzePartei < sitzeBundesland) {
					landesdivisor -= 1;
				} else {
					// sitzanzahl > sitzeBundesland
					landesdivisor += 1;
				}
			}

			for (Partei part : relevanteParteien) {
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKMANDAT,
						bl);
				int mindestSitzanzahl = Math.round(bl
						.getZweitstimmenAnzahl(part) / landesdivisor);
				int diffKandidat = mindestSitzanzahl - direktmandate;
				part.addMindestsitzanzahl(bl,
						Math.max(direktmandate, mindestSitzanzahl));
				if (diffKandidat > 0) {
					for (int i = 0; i <= diffKandidat; i++) {
						// Nehme aus der Bundestagswahl die Landesliste der
						// Partei und f�ge den i-ten Listenkandidaten in die
						// Sitzverteilung hinzu
						if (bl.getLandesliste(part).getListenkandidaten()
								.size() >= i + 1) {
							// TODO Testen
							bw.getSitzverteilung().addAbgeordnete(
									bl.getLandesliste(part)
											.getListenkandidaten().get(i));
							bl.getLandesliste(part).getListenkandidaten()
									.get(i).setMandat(Mandat.MANDAT);
						} else {
							// TODO negatives Stimmengewicht
						}
					}
				} else {
					for(int i = 0 ; i < diffKandidat ; i++){
						bl.getDirektMandate(part).get(i).setMandat(Mandat.UEBERHANGMADAT);
					}
				}

			}
			if (this.debug) {
				System.out.println("\nLandesdivisor " + bl.getName() + ": "
						+ landesdivisor);
				for (Partei part : relevanteParteien) {
					System.out.println("Sitze "
							+ part.getName()
							+ ": "
							+ Math.round(bl.getZweitstimmenAnzahl(part)
									/ landesdivisor));
				}

			}

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

		// TODO Restliche Sitze mit Mandate f�llen

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
			// TODO parallelit�t!
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
		// TODO Eintrag im Bericht f�r den Direktmandat setzen
		return wk;
	}

	@Override
	protected void erstelleBericht(String zeile) {
		// TODO Auto-generated method stub

	}

}

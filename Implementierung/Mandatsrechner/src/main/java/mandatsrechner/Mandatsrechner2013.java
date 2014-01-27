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
		bw = this.rechner2009.berechneSainteLague(bw);
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
		boolean isCorrect = false;
		for (Partei partei : relevanteParteien) {
			int neueSitzanzahl = Math.round(partei.getZweitstimmeGesamt()
					/ parteidivisor);
			int diffSitze = neueSitzanzahl - partei.getMindestsitzAnzahl();
			System.out.println(partei.getName() + " " + diffSitze);
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

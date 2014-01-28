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

		// Bestimme die Mandate der Zweitstimmen ( + Unterverteilung)
		this.rechner2009.berechneSainteLague(bw);
		
		// Bestimme Relevante Parteien.
		List<Partei> relevanteParteien = this.rechner2009.berechneRelevanteParteien(bw);
			
		Debug.print("\nSitzverteilung");
		int summe = 0;
		for (Partei partei : relevanteParteien) {
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
			//System.err.println(partei.getName()+" Zweitstimmengesamt: "+partei.getZweitstimmeGesamt()+" Mindestsitzanzahl:"+partei.getMindestsitzAnzahl()+" parteidivisor:"+parteidivisor);
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
		 * TODO: Ordentliche Schleife
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
			//System.out.println("Parteidivisor: "+parteidivisor);
			//System.out.println(partei.getName() + " " + diffSitze + " = " +neueSitzanzahl+ "("+(partei.getZweitstimmeGesamt() / parteidivisor)+") - "+partei.getAnzahlMandate());
			
			if (diffSitze > 0) {
				//isCorrect = false;
				int sitzeBundesland = 0;
				float divisor = 0f;
				
				divisor = partei.getZweitstimmeGesamt() / neueSitzanzahl;
				
				int insgesamt = 0;
				while (insgesamt != neueSitzanzahl) {
					insgesamt = 0;
					for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
						
						sitzeBundesland = this.rechner2009.runden(bl.getAnzahlZweitstimmen(partei) / divisor, false);
						//System.out.println(partei.getName()+" - "+bl.getName()+": "+this.rechner2009.runden(bl.getAnzahlZweitstimmen(partei) / divisor, false)+" - Zweitstimmen:"+bl.getAnzahlZweitstimmen(partei)+" Divisor:"+divisor);
						if(sitzeBundesland < bl.getDirektMandate(partei).size()) {
							insgesamt += bl.getDirektMandate(partei).size();
						}else{
							insgesamt += sitzeBundesland;
						}
						if(partei.getName().equals("SPD")){
							//.out.println("Magic: "+insgesamt+" "+bl.getDirektMandate(partei).size()+" "+sitzeBundesland);
							//System.out.println("BUNDESLAND:"+bl.getName()+" "+partei.getName()+" Gefundener Divisor: "+divisor+" insgesamt:"+insgesamt+" neueSitzanzahl:"+neueSitzanzahl+" ");

						}
					}
					if(insgesamt == neueSitzanzahl){
						if(partei.getName().equals("SPD")){
							System.out.println(partei.getName()+" Gefundener Divisor: "+divisor+" insgesamt:"+insgesamt+" neueSitzanzahl:"+neueSitzanzahl+" ");
						}
						break;
					}else if(insgesamt > neueSitzanzahl){
						divisor += 1;
						//System.out.println("erhoehe");
					}else{
						divisor -= 1;
						//System.out.println("erniedrige");
					}
				}
				
				
				for (Bundesland bl : bw.getDeutschland().getBundeslaender()) {
					
					sitzeBundesland = this.rechner2009.runden(bl.getAnzahlZweitstimmen(partei) / divisor, false);
					//System.out.println(partei.getName()+" "+bl.getName() +" Neue Sitze im Bundesland: "+sitzeBundesland);
					if(partei.getName().equals("SPD")){
						System.out.println(bl.getName()+" "+partei.getName()+" "+bl.getAnzahlZweitstimmen(partei)+" / "+divisor+" = "+(bl.getAnzahlZweitstimmen(partei) / divisor)+" Gerundet:"+sitzeBundesland);
					}
					
					// Gibt es ausgleichsmandate?
					if (sitzeBundesland != partei.getMindestsitzanzahl(bl)) {
						// Anzahl der Ausgleichmandate:
						int diffSitzeBundesland = sitzeBundesland
								- partei.getMindestsitzanzahl(bl);
						//if(partei.getName().equals("SPD")){
							//if(diffSitzeBundesland<0){
								//System.out.println(partei.getName()+" "+bl.getName()+" Ausgleichsmandate:"+diffSitzeBundesland+" Sitzebundesland:"+sitzeBundesland+" Mindestsitzanzahl:"+partei.getMindestsitzanzahl(bl)+ "\t"+Math.max(sitzeBundesland,partei.getMindestsitzanzahl(bl)));
							//}
						//}
						// Suche Kandidaten ohne Mandat und fuege sie als Ausgleichsmandat hinzu.
						
						for (int i = 0; i < diffSitzeBundesland; i++) {
							if(partei.getLandesliste(bl).getListenkandidaten().size() <= i){
								// Wahlgenerator verursacht Wahlen ohne Landeslisten.
								System.err.println("Keine Listenplaetze mehr :(");
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

								diffSitzeBundesland++;
							}
						}

					}
					
				}
			} else {
				if (diffSitze != 0) {
					throw new IllegalArgumentException("Fehler bei den Ausgleichsmandaten. Mindestsitzanzahl nicht erfuellt.");
				}
			}
			
		}
	}
}

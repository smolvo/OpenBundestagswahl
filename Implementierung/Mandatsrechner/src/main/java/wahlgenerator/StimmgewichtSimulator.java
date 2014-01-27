package main.java.wahlgenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import test.java.Debug;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Landesliste;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Diese Klasse soll in der Lage sein, zu einer von NegStimmgewichtWahlgenerator
 * erzeugten Bundestagswahl durch Simulation eine "verwandte" Bundestagswahl in
 * der Art zu erzeugen, dass zwischen diesen zwei Bundestagswahlen der Effekt
 * des negativen Stimmgewichts auftritt
 * 
 */

public class StimmgewichtSimulator {

	/**
	 * Die Ausgangsbundestagswahl, zu der eine "verwandte" Wahl erzeugt werden
	 * soll
	 */
	private Bundestagswahl ausgangsWahl;

	private Bundestagswahl kopie1;
	/**
	 * Die zur Ausgangswahl "verwandte" Bundestagswahl
	 */

	private Bundestagswahl kopie2;

	/**
	 * Parteien, bei denen negatives Stimmgewicht auftreten kann
	 */
	private List<Partei> relevanteParteien = new ArrayList<Partei>();

	/**
	 * Der Mandatsrechner mit dem gültigen Wahlgesetz von 2009
	 */
	private Mandatsrechner2009 rechner;

	/**
	 * Generator für zufällige Zahlen
	 */
	private Random rand;

	private Wahlkreis letzterWK;

	private Partei letztePartei;

	/*
	 * Bei mindestens einer Partei muss der prozentuale Anteil ihrer relevanten
	 * Zweitstimmen größer als der prozentuale Anteil ihrer Mandate sein.
	 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf
	 * Landeslisten abgegeben werden, die keine Uberhangmandate erzielen
	 */
	/**
	 * der Konstruktor der Klasse
	 * 
	 * @param ausgangsWahl
	 *            die Ausgangswahl
	 */
	public StimmgewichtSimulator(Bundestagswahl ausgangsWahl) {

		this.rand = new Random();

		this.rechner = Mandatsrechner2009.getInstance();

		// Mandate für Ausgangswahl berechnen
		this.setAusgangsWahl(rechner.berechneSainteLague(ausgangsWahl));

		try {
			this.setKopie1(ausgangsWahl.deepCopy());
			this.setKopie2(ausgangsWahl.deepCopy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.setVerwandteWahl(ausgangsWahl);

		// this.berechneRelevanteZweitstimmen();

		// this.setRelevanteParteien(waehleParteien());
	}

	/**
	 * Es werden die Zweitstimmen einer relevanten Partei in einem geeigneten
	 * Bundeesland schrittweise um den konstanten Faktor 5 erhöht. Dies
	 * geschieht maximal solange bis sie nicht mehr erhöht werden können, weil
	 * sie größer als die Anzahl der Wahlberechtigten sind. Falls ein negatives
	 * Stimmgewicht im Vergleich zu der Ausgangsbundestagswahl ermittelt wurde,
	 * bricht die Suche ebenfalls ab
	 * 
	 * @return true wenn negatives Stimmgewicht eingetreten ist
	 */
	public boolean berechneNegStimmgewicht() {

		LinkedList<Partei> parteienSortiertKopie1 = this.getKopie1()
				.getParteien();
		LinkedList<Partei> parteienSortiertKopie2 = this.getKopie2()
				.getParteien();

		Collections.sort(parteienSortiertKopie1, Partei.NACH_UEBERHANGMANDATEN);
		Collections.sort(parteienSortiertKopie2, Partei.NACH_UEBERHANGMANDATEN);

		for (Partei partei : parteienSortiertKopie1) {
			// List<Bundesland> bundeslaenderSortiert =
			// this.getVerwandteWahl().getDeutschland().getBundeslaender();
			// Collections.sort(bundeslaenderSortiert,
			// partei.BLAENDER_NACH_UEBERHANGMANDATEN);

			List<Bundesland> bundeslaender = this.getKopie1().getDeutschland()
					.getBundeslaender();

			for (Bundesland bundesland : bundeslaender) {
				// TODO entfernen wenn alles funktioniert
				//if (!bundesland.getName().equals("Brandenburg")) {
				//	continue;
				//}
				int begrenzung = (int) (bundesland
						.getAnzahlZweitstimmen(partei) * 1.2);
				try {
					kopie1 = ausgangsWahl.deepCopy();
					kopie2 = ausgangsWahl.deepCopy();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				while (bundesland.getAnzahlZweitstimmen(partei) < begrenzung) {

					this.erhoeheZweitstimmen(kopie1, partei, bundesland);

					// check auf negatives Stimmgewicht
					if (vergleicheSitzverteilungen(partei)) {
						Debug.print("NEGATIVES STIMMGEWICHT GEFUNDEN !!!!!!!!!!!! YAYYY");
						return true;
					}

					
					
					/*
					long start = System.currentTimeMillis();

					letztePartei = partei;
					for (Wahlkreis w : kopie2.getDeutschland().getWahlkreise()) {
						for (Partei party : kopie2.getParteien()) {
							if (w.getName().equals(letzterWK.getName()) && party.getName().equals(letztePartei.getName())) {
								Debug.print(w.getName() + " ");
								w.getZweitstimme(party).erhoeheAnzahl(
										1000);
							}
						}

					}
					
					
					Debug.print("Laufzeit Anpassung von Kopie2 ohne deep copy "
							+ (System.currentTimeMillis() - start) + "ms");
					*/		
					
					long start2 = System.currentTimeMillis();
					try {
						kopie2 = kopie1.deepCopy();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Debug.print("Laufzeit Anpassung von Kopie2 mit deep copy " + (System.currentTimeMillis() - start2) + "ms");
					
				}
			}

		}
		return false;
	}

	private void erhoeheZweitstimmen(Bundestagswahl bundestagswahl,
			Partei partei, Bundesland bundesland) {

		int stimmanzahl = 10000;
		
		Wahlkreis wk;
		do {
			// wählt einen zufälligen Wahlkreis
			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);
			// System.out.print("Zweitstimmenanzahl im WK " + wk + " von " +
			// wk.getZweitstimme(partei).getAnzahl());
			System.out.println(wk.getAnzahlZweitstimmen() + " + " + stimmanzahl + " > " + wk.getWahlberechtigte() 
					+ " = " + ((wk.getAnzahlZweitstimmen() + stimmanzahl) > wk.getWahlberechtigte()) + " " + wk.getName());
		} while ((wk.getAnzahlZweitstimmen() + stimmanzahl) > wk.getWahlberechtigte());

		wk.getZweitstimme(partei).erhoeheAnzahl(stimmanzahl);

		// System.out.println(" auf " + wk.getZweitstimme(partei).getAnzahl() +
		// " erhöht");
		Debug.print("Anzahl Zweitstimmen von " + partei.getName() + " in " + bundesland.getName() + ": " + bundesland.getAnzahlZweitstimmen(partei));
		
		
		long start = System.currentTimeMillis();
		Debug.setAktiv(false);
		this.setKopie1(rechner.berechneSainteLague(this.kopie1));
		Debug.setAktiv(true);
		Debug.print("Laufzeit Mandatsrechner: " + (System.currentTimeMillis() - start) + "ms");

		letzterWK = wk;

	}

	private void erniedrigeZweitstimmen(Bundestagswahl bundestagswahl,
			Partei partei, Bundesland bundesland) {

		int stimmanzahl = 10000;
		
		Wahlkreis wk;
		do {
			// wählt einen zufälligen Wahlkreis
			int i = rand.nextInt(bundesland.getWahlkreise().size());
			wk = bundesland.getWahlkreise().get(i);
			// System.out.print("Zweitstimmenanzahl im WK " + wk + " von " +
			// wk.getZweitstimme(partei).getAnzahl());
			System.out.println(wk.getAnzahlZweitstimmen() + " + " + stimmanzahl + " > " + wk.getWahlberechtigte() 
					+ " = " + ((wk.getAnzahlZweitstimmen() + stimmanzahl) > wk.getWahlberechtigte()) + " " + wk.getName());
		} while ((wk.getAnzahlZweitstimmen() - stimmanzahl) < 0);

		wk.getZweitstimme(partei).erniedrigeAnzahl(stimmanzahl);

		// System.out.println(" auf " + wk.getZweitstimme(partei).getAnzahl() +
		// " erhöht");
		Debug.print("Anzahl Zweitstimmen von " + partei.getName() + " in " + bundesland.getName() + ": " + bundesland.getAnzahlZweitstimmen(partei));
		
		
		long start = System.currentTimeMillis();
		Debug.setAktiv(false);
		this.setKopie1(rechner.berechneSainteLague(this.kopie1));
		Debug.setAktiv(true);
		Debug.print("Laufzeit Mandatsrechner: " + (System.currentTimeMillis() - start) + "ms");

		letzterWK = wk;

	}
	/**
	 * Vergleicht die Sitzverteilungen der beiden in Stimmgewicht gegebenen
	 * Bundestagswahlen. Wenn die Sitzanzahl für die gegebene Partei in der
	 * Ausgangsbundestagswahl größer ist als in der neu berechneten
	 * Bundestagswahl, tritt negativer Stimmeffekt auf und es wird false
	 * ausgegeben, andernfalls true
	 * 
	 * @param p
	 *            die Partei, deren Sitze betrachtet werden
	 * @return true, wenn negatives Stimmgewicht aufgetreten ist
	 */

	// TODO private setzen, nur zum testen public
	private boolean vergleicheSitzverteilungen(Partei p) {
		// TODO
		// this.setVerwandteWahl(Mandatsrechner2009.getInstance().berechneAlles(
		// verwandteWahl));

		Sitzverteilung neu = this.kopie1.getSitzverteilung();
		Sitzverteilung alt = this.kopie2.getSitzverteilung();

		int mandatsZahlAlt = 0;
		for (Kandidat a : alt.getAbgeordnete()) {
			if (a.getPartei().getName().equals(p.getName())) {
				mandatsZahlAlt++;
			}
		}

		int mandatsZahlNeu = 0;
		for (Kandidat a : neu.getAbgeordnete()) {
			if (a.getPartei().getName().equals(p.getName())) {
				mandatsZahlNeu++;
			}
		}
		System.out.println("    Mandatszahl alt: " + mandatsZahlAlt
				+ " Mandatszahl neu: " + p.getAnzahlMandate() +"ZS neu in D: " + p.getZweitstimmeGesamt());
		return (mandatsZahlNeu < mandatsZahlAlt);

	}

	/**
	 * Erhöht für eine Partei in einem Bundesland solange ihre relevanten ZS um
	 * einen konkreten Wert, bis ihr Anteil an der Gesamtheit größer ist als der
	 * Anteil an Mandaten
	 * 
	 * Relevante ZS sind ZS, die nur auf Landeslisten ohne Überhangmandate
	 * abgegeben wurden
	 * 
	 * @param p
	 *            die Partei, bei der Zweitstimmen erhöht werden sollen
	 */

	// TODO vllt muss noch gleichzeitig in einem anderen Wahlkreis des
	// Bundeslandes für die Partei die Zweitstimmenzahl
	// erniedrigt werden

	public void erhoeheRelevantenAnteil(Partei p) {

		List<Landesliste> alleLandeslisten = p.getLandesliste();
		List<Landesliste> ohneUeberhang = new ArrayList<Landesliste>();

		// wählt alle Landeslisten, auf die keine Überhangmandate fallen
		for (Landesliste land : alleLandeslisten) {
			if (p.getAnzahlMandate(Mandat.UEBERHANGMADAT, land.getBundesland()) == 0) {
				ohneUeberhang.add(land);
			}

			// wählt aus den Landeslisten ohne Überhangmandat eine zufällige
			// Landesliste aus
			int i = rand.nextInt(ohneUeberhang.size());
			Landesliste l = ohneUeberhang.get(i);

			// wählt aus der zufällig erhaltenen Landesliste einen
			// zufälligen Wahlkreis
			i = rand.nextInt(l.getBundesland().getWahlkreise().size());
			Wahlkreis wk = l.getBundesland().getWahlkreise().get(i);

			// in diesem Wahlkreis wird nun die Zweitstimmenanzahl erhöht
			// TODO System.out.println entfernen
			// System.out.print("ZS im WK " + wk + " von "
			// + wk.getZweitstimme(p).getAnzahl());
			wk.getZweitstimme(p).erhoeheAnzahl(1000);

			// Sitzverteilung neu berechnen
			// System.out.println(" auf " + wk.getZweitstimme(p).getAnzahl()
			// + " erhöht");
			long start = System.currentTimeMillis();
			// this.setVerwandteWahl(rechner.berechneAlles(this.verwandteWahl));
			Debug.print("Laufzeit Mandatsrechner"
					+ (start - System.currentTimeMillis()) + "ms");
			// System.out.print("Relevante ZS von "
			// + p.getRelevanteZweitstimmen().getAnzahl());

			// relevante ZS neu berechnen
			berechneRelevanteZweitstimmen();
			// System.out.print(" auf "
			// + p.getRelevanteZweitstimmen().getAnzahl());

		}
	}

	/**
	 * Diese Methode überprüft, ob bei einer gegebenen Partei ihr Anteil an
	 * relativen Zweitstimmen größer ist als ihr Anteil an Mandaten. Die Anteile
	 * werden bundesweit betrachtet
	 * 
	 * @param p
	 *            Partei, bei der die Anteile überprüft werden sollen
	 * @return true, wenn Anteil rel. ZS > Anteil Mandate
	 */
	// TODO private setzen, nur fürs testen public
	public boolean bedingungErfuellt(Partei p) {
		List<Partei> alleParteien = this.kopie2.getParteien();

		// berechnet insgesamte Anzahl an relevanten Zweitstimmen, d.h. addiert
		// die relevanten Zweitstimmen aller Parteien

		int relevanteZweitstimmenGesamt = 0;
		for (Partei x : alleParteien) {
			relevanteZweitstimmenGesamt += x.getRelevanteZweitstimmen()
					.getAnzahl();
		}

		float anteilRelevanteZweitstimmen = 0;
		float anteilMandate = 0;
		// berechnet den Anteil an relevanten Zweitstimmen

		anteilRelevanteZweitstimmen = (float) p.getRelevanteZweitstimmen()
				.getAnzahl() / (float) relevanteZweitstimmenGesamt;

		// berechnet den Anteil an Mandaten

		anteilMandate = (float) p.getAnzahlMandate()
				/ (float) this.kopie2.getSitzverteilung().getAbgeordnete()
						.size();

		System.out.println(p.getName() + " " + anteilMandate + " "
				+ anteilRelevanteZweitstimmen);
		if (anteilRelevanteZweitstimmen > anteilMandate) {
			return true;
		}

		return false;
	}

	/**
	 * In dieser Methode werden die Parteien gewählt, deren prozentualer Anteil
	 * an relevanten Zweitstimmen größer als der prozentuale Anteil an Mandaten
	 * ist
	 * 
	 * @return Liste an relevanten Parteien, d.h. diejenigen, die das
	 *         beschriebene Merkmal aufweisen
	 */

	// TODO private setzten, nur zum testen public
	public List<Partei> waehleParteien() {
		List<Partei> alleParteien = this.kopie2.getParteien();

		List<Partei> relevanteParteien = new ArrayList<Partei>();
		for (Partei p : alleParteien) {
			if (bedingungErfuellt(p)) {
				relevanteParteien.add(p);
			}
		}
		return relevanteParteien;
	}

	/**
	 * Diese Methode wählt, von den Parteien abhängig, die Bundesländer, für die
	 * negatives Stimmgewicht auftreten kann
	 * 
	 * @param p
	 *            Partei
	 * @return Liste an Bundesländern
	 */

	// TODO private setzten, nur zum testen public
	public List<Bundesland> waehleBundeslaender(Partei p) {
		List<Landesliste> landeslisten = p.getLandesliste();
		List<Bundesland> relevanteBundeslaender = new ArrayList<Bundesland>();

		for (Landesliste l : landeslisten) {
			// überprüfe, ob die Partei in einem Bundesland Überhandmandate hat
			// falls ja, kann negatives Stimmgewicht auftreten und sie ist
			// relevant
			if (l.getKandidaten(Mandat.UEBERHANGMADAT).size() == 0) {
				relevanteBundeslaender.add(l.getBundesland());
			}
		}
		return relevanteBundeslaender;
	}

	/**
	 * Berechnet für alle Parteien die relevanten Zweitstimmen
	 * 
	 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf
	 * Landeslisten abgegeben werden, die keine Uberhangmandate erzielen
	 * 
	 */

	// TODO private setzten, nur zum testen public
	public void berechneRelevanteZweitstimmen() {

		for (Partei p : this.kopie2.getParteien()) {
			List<Landesliste> landeslisten = p.getLandesliste();
			int anzahl = 0;

			for (Landesliste l : landeslisten) {

				// überprüft, ob Partei Zweitstimmen in einem Bundesland
				// erhalten
				// hat, in dem es keine Überhangmandate hält
				if (l.getKandidaten(Mandat.UEBERHANGMADAT).size() == 0) {
					anzahl += l.getBundesland().getAnzahlZweitstimmen(p);
				}
			}

			p.setRelevanteZweitstimmen(new RelevanteZweitstimmen(anzahl));
			// System.out.println(p.getName() + " " +
			// p.getRelevanteZweitstimmen());

		}

	}

	/**
	 * @return the bw
	 */
	public Bundestagswahl getAusgangsWahl() {
		return ausgangsWahl;
	}

	/**
	 * Setzt eine neue Ausgangswahl
	 * 
	 * @param ausgangsWahl
	 *            neue Ausgangswahl
	 */
	public void setAusgangsWahl(Bundestagswahl ausgangsWahl) {
		if (ausgangsWahl == null) {
			throw new IllegalArgumentException(
					"Übergebene Bundestagswahl war null");
		} else {
			this.ausgangsWahl = ausgangsWahl;
		}

	}

	/**
	 * @return the relevanteParteien
	 */
	public List<Partei> getRelevanteParteien() {
		return relevanteParteien;
	}

	/**
	 * @param relevanteParteien
	 *            the relevanteParteien to set
	 */
	public void setRelevanteParteien(List<Partei> relevanteParteien) {
		if (relevanteParteien == null) {
			throw new IllegalArgumentException(
					"Liste mit relevanten Parteien war null.");
		} else {
			this.relevanteParteien = relevanteParteien;
		}

	}

	/**
	 * @return the verwandteWahl
	 */
	public Bundestagswahl getKopie2() {
		return kopie2;
	}

	/**
	 * @param verwandteWahl
	 *            the verwandteWahl to set
	 */
	public void setKopie2(Bundestagswahl verwandteWahl) {
		if (verwandteWahl == null) {
			throw new IllegalArgumentException(
					"Übergebene Bundestagswahl war null");
		} else {
			this.kopie2 = verwandteWahl;
		}

	}

	public Bundestagswahl getKopie1() {
		return kopie1;
	}

	public void setKopie1(Bundestagswahl wahlKopie) {
		this.kopie1 = wahlKopie;
	}

}

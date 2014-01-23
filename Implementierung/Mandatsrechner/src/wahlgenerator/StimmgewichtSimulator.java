package wahlgenerator;

import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import mandatsrechner.Mandatsrechner2009;
import model.Bundesland;
import model.Bundestagswahl;
import model.Kandidat;
import model.Landesliste;
import model.Mandat;
import model.Partei;
import model.Sitzverteilung;
import model.Wahlkreis;
import model.Zweitstimme;

/**
 * Diese Klasse soll in der Lage sein, zu einer von NegStimmgewichtWahlgenerator
 * erzeugten Bundestagswahl durch Simulation eine "verwandte" Bundestagswahl in
 * der Art zu erzeugen, dass zwischen diesen zwei Bundestagswahlen der Effekt
 * des negativen Stimmgewichts auftritt
 * 
 */

public class StimmgewichtSimulator {

	/**
	 * Die Ausgangsbundestagswahl, zu der eine "verwandte" Wahl erzeugt werden soll
	 */
	private Bundestagswahl ausgangsWahl;

	/**
	 * Die zur Ausgangswahl "verwandte" Bundestagswahl
	 */
	private Bundestagswahl verwandteWahl;

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
		try {
			// TODO Sitzverteilung von bw berechnen
			this.setAusgangsWahl(rechner.berechneAlles(ausgangsWahl));
			
			this.setVerwandteWahl(ausgangsWahl.deepCopy());

			
			this.berechneRelevanteZweitstimmen();
			// this.setRelevanteParteien(waehleParteien());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		for (Partei p : relevanteParteien) {
			List<Bundesland> relevanteBundeslaender = waehleBundeslaender(p);
			List<Zweitstimme> zweitstimmen = p.getZweitstimme();
			for (Bundesland b : relevanteBundeslaender) {
				for (Zweitstimme z : zweitstimmen) {
					if (z.getGebiet().equals(b)) {
						// && z.getAnzahl() + 5 < Anzahl Wahlberechtigte TODO
						z.erhoeheAnzahl(5);
						// z.setAnzahl(z.getAnzahl() + 5);

						// negatives Stimmgewicht aufgetreten?
						if (vergleicheSitzverteilungen(p)) {
							return true;
						}
					}
				}
			}

		}

		return false;
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
		this.setVerwandteWahl(Mandatsrechner2009.getInstance().berechneAlles(
				verwandteWahl));

		Sitzverteilung alt = this.ausgangsWahl.getSitzverteilung();
		Sitzverteilung neu = this.verwandteWahl.getSitzverteilung();

		int mandatsZahlAlt = 0;
		for (Kandidat a : alt.getAbgeordnete()) {
			if (a.getPartei().equals(p)) {
				mandatsZahlAlt++;
			}
		}

		int mandatsZahlNeu = 0;
		for (Kandidat a : neu.getAbgeordnete()) {
			if (a.getPartei().equals(p)) {
				mandatsZahlNeu++;
			}
		}
		System.out.println("Mandatszahl alt: " + mandatsZahlAlt
				+ " Mandatszahl neu: " + mandatsZahlNeu);
		return (mandatsZahlNeu < mandatsZahlAlt);

	}

	/**
	 * Erhöht für eine Partei in einem Bundesland solange ihre relevanten ZS um
	 * einen konkreten Wert, bis ihr Anteil an der Gesamtheit größer ist
	 * als der Anteil an Mandaten
	 * 
	 * Relevante ZS sind ZS, die nur auf Landeslisten ohne Überhangmandate abgegeben wurden
	 * @param p
	 *            die Partei, bei der Zweitstimmen erhöht werden sollen
	 */

	// TODO vllt muss noch gleichzeitig in einem anderen Wahlkreis des
	// Bundeslandes für die Partei die Zweitstimmenzahl
	// erniedrigt werden

	public void erhoeheRelevantenAnteil(Partei p) {
		
			List<Landesliste> alleLandeslisten = p.getLandesliste();
			List<Landesliste> ohneUeberhang = new ArrayList<Landesliste>();

			//wählt alle Landeslisten, auf die keine Überhangmandate fallen
			for (Landesliste land : alleLandeslisten) {
				if (p.getAnzahlMandate(Mandat.UEBERHANGMADAT,
						land.getBundesland()) == 0) {
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
				System.out.print("ZS im WK " + wk + " von "
						+ wk.getZweitstimme(p).getAnzahl());
				wk.getZweitstimme(p).erhoeheAnzahl(10);

				//Sitzverteilung neu berechnen
				System.out.println(" auf " + wk.getZweitstimme(p).getAnzahl()
						+ " erhöht");
				this.setVerwandteWahl(rechner.berechneAlles(this.verwandteWahl));
				System.out.print("Relevante ZS von "
						+ p.getRelevanteZweitstimmen().getAnzahl());
				
				//relevante ZS neu berechnen
				berechneRelevanteZweitstimmen();
				System.out.print(" auf "
						+ p.getRelevanteZweitstimmen().getAnzahl());

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
		List<Partei> alleParteien = this.verwandteWahl.getParteien();

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
				/ (float) this.verwandteWahl.getSitzverteilung()
						.getAbgeordnete().size();

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
		List<Partei> alleParteien = this.verwandteWahl.getParteien();

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

		for (Partei p : this.verwandteWahl.getParteien()) {
			List<Landesliste> landeslisten = p.getLandesliste();
			int anzahl = 0;

			for (Landesliste l : landeslisten) {

				// überprüft, ob Partei Zweitstimmen in einem Bundesland
				// erhalten
				// hat, in dem es keine Überhangmandate hält
				if (l.getKandidaten(Mandat.UEBERHANGMADAT).size() == 0) {
					anzahl += l.getBundesland().getZweitstimmenAnzahl(p);
				}
			}

			p.setRelevanteZweitstimmen(new RelevanteZweitstimmen(anzahl));

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
	public Bundestagswahl getVerwandteWahl() {
		return verwandteWahl;
	}

	/**
	 * @param verwandteWahl
	 *            the verwandteWahl to set
	 */
	public void setVerwandteWahl(Bundestagswahl verwandteWahl) {
		if (verwandteWahl == null) {
			throw new IllegalArgumentException(
					"Übergebene Bundestagswahl war null");
		} else {
			this.verwandteWahl = verwandteWahl;
		}

	}

}

package wahlgenerator;

import java.io.IOException;
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
import model.Zweitstimme;

/**
 * Diese Klasse soll in der Lage sein, zu einer von NegStimmgewichtWahlgenerator
 * erzeugten Bundestagswahl durch Simulation eine "verwandte" Bundestagswahl in
 * der Art zu erzeugen, dass zwischen diesen zwei Bundestagswahlen der Effekt
 * des negativen Stimmgewichts auftritt
 * 
 * @author Manuel
 * 
 */

public class StimmgewichtSimulator {

	/**
	 * Die Ausgangsbundestagswahl, zu der eine "verwandte" Wahl erzeugt werden
	 * soll
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

		try {
			 // TODO Sitzverteilung von bw berechnen
			//this.setAusgangsWahl(Mandatsrechner2009.getInstance()
				//	.berechneAlles(ausgangsWahl));
			this.setAusgangsWahl(ausgangsWahl);
			//TODO Fehler bei deepCopy
			this.setVerwandteWahl(ausgangsWahl.deepCopy());
			//this.setVerwandteWahl(ausgangsWahl);
			//this.berechneRelevanteZweitstimmen();
			//this.setRelevanteParteien(waehleParteien());

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
	
	//TODO private setzen, nur zum testen public
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
		System.out.println("Mandatszahl alt: " + mandatsZahlAlt + " Mandatszahl neu: " + mandatsZahlNeu);
		return (mandatsZahlNeu < mandatsZahlAlt);

	}

	/**
	 * In dieser Methode werden die Parteien gewählt, deren prozentualer Anteil
	 * an relevanten Zweitstimmen größer als der prozentuale Anteil an Mandaten
	 * ist
	 * 
	 * @return Liste an relevanten Parteien, d.h. diejenigen, die das
	 *         beschriebene Merkmal aufweisen
	 */
	
	//TODO private setzten, nur zum testen public
	public List<Partei> waehleParteien() {
		List<Partei> alleParteien = this.ausgangsWahl.getParteien();

		List<Partei> relevanteParteien = new ArrayList<Partei>();

		// berechnet insgesamte Anzahl an relevanten Zweitstimmen, d.h. addiert
		// die relevanten Zweitstimmen aller Parteien

		int relevanteZweitstimmenGesamt = 0;
		for (Partei p : alleParteien) {
			relevanteZweitstimmenGesamt += p.getRelevanteZweitstimmen()
					.getAnzahl();
		}

		// berechnet für jede Partei jeweils den Anteil ihrer relevanten
		// Zweitstimmen und den Anteil ihrer Mandate
		float anteilRelevanteZweitstimmmen = 0;
		float anteilMandate = 0;
		for (Partei p : alleParteien) {
			anteilRelevanteZweitstimmmen = p.getRelevanteZweitstimmen()
					.getAnzahl() / relevanteZweitstimmenGesamt;
			// anteilMandate = p.getAnzahlMandate() / TODO Anzahl Sitze des
			// Bundestags;

			if (anteilRelevanteZweitstimmmen > anteilMandate) {
				relevanteParteien.add(p);
			}
		}
		return null;
	}

	/**
	 * Diese Methode wählt, von den Parteien abhängig, die Bundesländer, für die
	 * negatives Stimmgewicht auftreten kann
	 * 
	 * @param p
	 *            Partei
	 * @return Liste an Bundesländern
	 */
	
	//TODO private setzten, nur zum testen public
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
	
	//TODO private setzten, nur zum testen public
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

package wahlgenerator;

import java.util.List;

import model.Bundesland;
import model.Bundestagswahl;
import model.Landesliste;
import model.Mandat;
import model.Partei;
import model.Zweitstimme;

/**
 * Diese Klasse soll in der Lage sein, zu einer von NegStimmgewichtWahlgenerator
 * erzeugten Bundestagswahl durch Simulation eine verwandte Bundestagswahl in
 * der Art zu erzeugen, dass zwischen diesen zwei Bundestagswahlen der Effekt
 * des negativen Stimmgewichts auftritt
 * 
 * @author Manuel
 * 
 */

public class StimmgewichtSimulator {

	/**
	 * Die Ausgangsbundestagswahl, zu der eine verwandte Wahl erzeugt werden
	 * soll
	 */
	private Bundestagswahl bw;

	/*
	 * Bei mindestens einer Partei muss der prozentuale Anteil ihrer relevanten
	 * Zweitstimmen größer als der prozentuale Anteil ihrer Mandate sein.
	 * Relevante Zweitstimmen sind all diejenigen Zweitstimmen, die auf
	 * Landeslisten abgegeben werden, die keine überhangmandate erzielen
	 */
	/**
	 * der Konstruktor der Klasse
	 */
	public StimmgewichtSimulator(Bundestagswahl bw) {

	}

	public boolean berechneNegStimmgewicht() {
		List<Partei> parteien = waehleParteien();
		List<Bundesland> bundeslaender = waehleBundeslaender();

		return false;
	}

	private boolean vergleicheSitzverteilungen() {
		return true;
	}

	/**
	 * In dieser Methode werden die Parteien gewählt, deren prozentualer Anteil
	 * ihrer relevanten Zweitstimmen größer als der prozentuale Anteil ihrer
	 * Mandate ist
	 * 
	 * @return Liste an Parteien, die das beschriebene Merkmal aufweisen
	 */
	private List<Partei> waehleParteien() {
		List<Partei> alleParteien = this.bw.getParteien();
		List<Zweitstimme> alleZweitstimmenObjekt = this.bw.get
		
		
		//berechnet relevante Zweistimmen für alle Parteien
		for(Partei p : alleParteien) {
			
			List<Landesliste> landesliste = p.getLandesliste();
			
			for(int i = 0; i < landesliste.size(); i++) {
				
				//überprüft, ob Partei Zweitstimmen in einem Bundesland erhalten hat, in dem es keine Überhangmandate hält
				if (landesliste.get(i).getKandidaten(Mandat.UEBERHANGMADAT).size() == 0) {
					p.addRelevanteZweitstimmen(new RelevanteZweitstimmen(p.getZweitstimme(landesliste.get(i).getBundesland()), p));
				}
			}
		}
		return null;
	}

	private List<Bundesland> waehleBundeslaender() {
		return null;
	}

	/**
	 * @return the bw
	 */
	public Bundestagswahl getBw() {
		return bw;
	}

	/**
	 * @param bw
	 *            the bw to set
	 */
	public void setBw(Bundestagswahl bw) {
		if (bw == null) {
			throw new IllegalArgumentException(
					"Übergebene Bundestagswahl war null");
		} else {
			this.bw = bw;
		}

	}

}

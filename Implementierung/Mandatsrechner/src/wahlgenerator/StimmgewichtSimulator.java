package wahlgenerator;

import java.util.ArrayList;
import java.util.List;

import model.Bundesland;
import model.Bundestagswahl;
import model.Landesliste;
import model.Mandat;
import model.Partei;
import model.Sitzverteilung;
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
		this.setBw(bw);
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
	 * an relevanten Zweitstimmen größer als der prozentuale Anteil an Mandate
	 * ist
	 * 
	 * @return Liste an relevanten Parteien, d.h. diejenigen, die das
	 *         beschriebene Merkmal aufweisen
	 */
	private List<Partei> waehleParteien() {
		List<Partei> alleParteien = this.bw.getParteien();

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

	private List<Bundesland> waehleBundeslaender() {
		return null;
	}

	/**
	 * Berechnet für alle Parteien die relevanten Zweitstimmen
	 * 
	 */
	private void berechneRelevanteZweitstimmen() {

		for (Partei p : this.bw.getParteien()) {
			List<Landesliste> landesliste = p.getLandesliste();
			int anzahl = 0;

			for (int i = 0; i < landesliste.size(); i++) {

				// überprüft, ob Partei Zweitstimmen in einem Bundesland
				// erhalten
				// hat, in dem es keine Überhangmandate hält
				if (landesliste.get(i).getKandidaten(Mandat.UEBERHANGMADAT)
						.size() == 0) {
					anzahl += p.getZweitstimme(landesliste.get(i)
							.getBundesland());

				}
			}

			p.setRelevanteZweitstimmen(new RelevanteZweitstimmen(anzahl));
		}
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

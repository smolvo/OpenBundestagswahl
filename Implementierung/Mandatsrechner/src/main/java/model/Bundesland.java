package main.java.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasse die die Bundeslaender repraesentiert. Unterklasse von Gebiet.
 */
public class Bundesland extends Gebiet implements Serializable,
		Comparable<Bundesland> {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = 1614716230171638779L;

	/** Einwohnerzahl des Bundeslandes. */
	private int einwohnerzahl;

	/** Farbe des Bundeslandes. */
	private Color farbe;

	/** Liste mit den Wahlkreisen im Bundesland. */
	private LinkedList<Wahlkreis> wahlkreise = new LinkedList<Wahlkreis>();

	/** Liste mit den vertrettenden Parteien im Bundesland. */
	private LinkedList<Partei> parteien = new LinkedList<Partei>();

	/** Liste mit den Landeslisten des Bundeslandes. */
	private List<Landesliste> landesliste = new LinkedList<Landesliste>();

	/**
	 * Parametrisierter Konstruktor fuer Bundeslaender. Listen werden seperat
	 * hinzugefuegt.
	 * 
	 * @param name
	 *            Der name des Bundeslandes.
	 * @param einwohnerzahl
	 *            Die Anzahl der Einwohner.
	 */
	public Bundesland(String name, int einwohnerzahl) {
		this.setName(name);
		this.setEinwohnerzahl(einwohnerzahl);
	}

	/**
	 * Gibt die Einwohnerzahl zurueck.
	 * 
	 * @return die Einwohnerzahl.
	 */
	public int getEinwohnerzahl() {
		return this.einwohnerzahl;
	}

	/**
	 * Setzt die Einwohnerzahl.
	 * 
	 * @param einwohnerzahl
	 *            wird neu gesetzt.
	 * @throws IllegalArgumentException
	 *             wenn die zahl negativ ist.
	 */
	public void setEinwohnerzahl(int einwohnerzahl)
			throws IllegalArgumentException {
		if (einwohnerzahl < 0) {
			throw new IllegalArgumentException("Einwohnerzahl ist kleiner 0");
		}
		this.einwohnerzahl = einwohnerzahl;
	}

	/**
	 * Gibt die Farbe des Bundeslandes zurueck.
	 * 
	 * @return die Farbe.
	 */
	public Color getFarbe() {
		return farbe;
	}

	/**
	 * Setzt die Farbe des Bundeslandes.
	 * 
	 * @param farbe
	 *            des Bundeslandes.
	 * @throws IllegalArgumentException
	 *             wenn die Farbe leer ist.
	 */
	public void setFarbe(Color farbe) throws IllegalArgumentException {
		if (farbe == null) {
			throw new IllegalArgumentException("Farbe ist leer");
		}
		this.farbe = farbe;
	}

	/**
	 * Gibt eine Liste mit den Wahlkreisen zurueck.
	 * 
	 * @return die Liste mit Wahlkreisen.
	 */
	public LinkedList<Wahlkreis> getWahlkreise() {
		return this.wahlkreise;
	}

	/**
	 * Setzt eine neue Liste mit Wahlkreisen.
	 * 
	 * @param wahlkreise
	 *            die neue Liste.
	 * @throws IllegalArgumentException
	 *             wenn die Liste leer ist.
	 */
	public void setWahlkreise(LinkedList<Wahlkreis> wahlkreise)
			throws IllegalArgumentException {
		if (wahlkreise == null || wahlkreise.isEmpty()) {
			throw new IllegalArgumentException("Wahlkreisliste ist leer");
		}
		this.wahlkreise = wahlkreise;
	}

	/**
	 * Gibt die Liste mit Parteien zurueck.
	 * 
	 * @return die Liste mit Parteien.
	 */
	public LinkedList<Partei> getParteien() {
		return this.parteien;
	}

	/**
	 * Setzt eine neue Liste mit Parteien.
	 * 
	 * @param parteien
	 *            die neue Liste.
	 * @throws IllegalArgumentException
	 *             wenn die Liste leer ist.
	 */
	public void setParteien(LinkedList<Partei> parteien)
			throws IllegalArgumentException {
		if (parteien == null || parteien.isEmpty()) {
			throw new IllegalArgumentException("Name ist leer");
		}
		this.parteien = parteien;
	}

	/**
	 * Fuegt eine neue Partei zur Liste hinzu.
	 * 
	 * @param partei
	 *            ist der neue Partei.
	 * @throws IllegalArgumentException
	 *             wenn die Partei null ist.
	 */
	public void addPartei(Partei partei) {
		if (partei == null) {
			throw new IllegalArgumentException("Partei ist leer!");
		}
		this.parteien.add(partei);
	}

	/**
	 * Fuegt einen neuen Wahlkreis hinzu.
	 * 
	 * @param wahlkreis
	 *            Der Wahlkreis der hinzugef�gt wird
	 * @throws IllegalArgumentException
	 *             wenn der Wahlkreis null ist.
	 */
	public void addWahlkreis(Wahlkreis wahlkreis) {
		if (wahlkreis == null) {
			throw new IllegalArgumentException("Wahlkreis ist leer!");
		}
		this.wahlkreise.add(wahlkreis);
	}

	@Override
	public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Erststimme> getErststimmen() {
		List<Erststimme> erststimmen = new LinkedList<Erststimme>();
		int[] tempStimmen = new int[this.wahlkreise.get(0).getErststimmen()
				.size()];
		for (int i = 0; i < this.wahlkreise.size(); i++) {
			List<Erststimme> wahlkreisErststimme = wahlkreise.get(i)
					.getErststimmen();
			for (int j = 0; j < wahlkreisErststimme.size(); j++) {
				tempStimmen[j] += wahlkreisErststimme.get(j).getAnzahl();
			}
		}
		for (int i = 0; i < tempStimmen.length; i++) {
			erststimmen.add(new Erststimme(tempStimmen[i], this, new Kandidat(
					"Unbekannt", "Unbekannt", 0, Mandat.KEINMANDAT, null)));
		}

		return erststimmen;
	}

	@Override
	public List<Zweitstimme> getZweitstimmen() {
		List<Zweitstimme> zweitstimmen = new LinkedList<Zweitstimme>();
		int[] tempZweitstimmen = new int[this.wahlkreise.get(0)
				.getZweitstimmen().size()];
		for (int i = 0; i < this.wahlkreise.size(); i++) {
			List<Zweitstimme> wahlkreisZweitstimme = wahlkreise.get(i)
					.getZweitstimmen();
			for (int j = 0; j < wahlkreisZweitstimme.size(); j++) {
				tempZweitstimmen[j] += wahlkreisZweitstimme.get(j).getAnzahl();
			}
		}
		for (int i = 0; i < tempZweitstimmen.length; i++) {
			zweitstimmen
					.add(new Zweitstimme(tempZweitstimmen[i], this,
							this.wahlkreise.get(0).getZweitstimmen().get(i)
									.getPartei()));
		}

		return zweitstimmen;
	}

	@Override
	public int getWahlberechtigte() {
		int wahlberechtigte = 0;
		for (int i = 0; i < this.wahlkreise.size(); i++) {
			wahlberechtigte += wahlkreise.get(i).getWahlberechtigte();
		}
		return wahlberechtigte;
	}

	@Override
	public void setWahlberechtigte(int wahlberechtigte) {
		// TODO Auto-generated method stub

	}

	/**
	 * Setzt eine neue Liste von Landeslisten.
	 * 
	 * @param landesliste
	 *            Die Landesliste die gesetzt wird.
	 * @throws IllegalArgumentException
	 *             wenn die Liste leer ist.
	 */
	public void setLandeliste(List<Landesliste> landesliste)
			throws IllegalArgumentException {
		if (landesliste == null || landesliste.isEmpty()) {
			throw new IllegalArgumentException("landesliste ist leer");
		}
		this.landesliste = landesliste;
	}

	/**
	 * Gibt die Liste aller Landeslisten dieses Bundeslandes zurueck.
	 * 
	 * @return die Landesliste dieses Bundeslandes.
	 */
	public List<Landesliste> getLandesliste() {
		return this.landesliste;
	}

	/**
	 * Fuegt eine neue Landesliste zum Bundeslandes hinzu.
	 * 
	 * @param landesliste
	 *            Die Landesliste die hinzugefuegt wird.
	 * @throws IllegalArgumentException
	 *             wenn die Landesliste null ist.
	 */
	public void addLandesliste(Landesliste landesliste) {
		if (landesliste == null) {
			throw new IllegalArgumentException("Landesliste ist null.");
		}
		this.landesliste.add(landesliste);
	}

	@Override
	public int compareTo(Bundesland andere) {
		return this.getName().compareTo(andere.getName());
	}

	@Override
	public int getZweitstimmenAnzahl(Partei partei) {
		int anzahl = 0;
		for (Wahlkreis wk : this.getWahlkreise()) {
			anzahl += wk.getZweitstimmenAnzahl(partei);
		}
		return anzahl;
	}

	/**
	 * Gibt die Landesliste zur jeweiligen Partei zurueck.
	 * 
	 * @param partei
	 *            die gewuenschte Partei.
	 * @return die Landesliste der Partei im Bundesland.
	 * @throws IllegalArgumentException
	 *             wenn die Partei null ist.
	 */
	public Landesliste getLandesliste(Partei partei) {
		if (partei == null) {
			throw new IllegalArgumentException("Partei ist null.");
		}
		Landesliste result = null;
		for (Landesliste land : this.getLandesliste()) {
			if (land.getPartei().equals(partei)) {
				result = land;
			}
		}
		return result;

	}

	/**
	 * Gibt eine Liste mit den Kandidaten die ein Direktmandate zurueck.
	 * 
	 * @param part
	 *            die Partei der Kandidaten.
	 * @return Liste mit den Direktmandate einer Partei.
	 */
	public LinkedList<Kandidat> getDirektMandate(Partei part) {
		if (part == null) {
			throw new IllegalArgumentException("Partei ist leer");
		}
		LinkedList<Kandidat> direktmandate = new LinkedList<Kandidat>();
		for (Wahlkreis wk : this.getWahlkreise()) {
			if (wk.getWahlkreisSieger().getPartei() == part) {
				direktmandate.add(wk.getWahlkreisSieger());
			}
		}
		return direktmandate;
	}
}

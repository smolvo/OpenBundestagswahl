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

	/** Liste mit den im Bundesland vertretenen Parteien */
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
		setName(name);
		setEinwohnerzahl(einwohnerzahl);
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
	 *            Der Wahlkreis der hinzugefï¿½gt wird
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
	public int compareTo(Bundesland andere) {
		return getName().compareTo(andere.getName());
	}

	/**
	 * Equals Methode der Bundesland-Klasse
	 * 
	 * @param land
	 *            vergleichsland
	 * @return true false
	 */
	public boolean equals(Bundesland land) {
		if (land.getEinwohnerzahl() == this.einwohnerzahl
				&& land.getWahlkreise().equals(this.wahlkreise)
				&& land.getParteien().equals(this.parteien)
				&& land.getLandesliste().equals(this.landesliste)) {
			return true;
		}
		return false;
	}

	/**
	 * Ermittelt die Partei mit der hoechsten Anzahl zweitstimmen. Bei zwei
	 * Parteien mit gleicher Anzahl zweitstimmen wird die erste in der Liste
	 * zuruckgegeben
	 * 
	 * @return die Partei mit den meisten Zweitstimmen
	 */
	public Partei ermittleStaerkstePartei() {
		Partei staerkstePartei = null;
		int maxStimmzahl = 0;
		for (final Partei partei : this.parteien) {
			final int aktuellerWert = this.getAnzahlZweitstimmen(partei);
			if (aktuellerWert > maxStimmzahl) {
				maxStimmzahl = aktuellerWert;
				staerkstePartei = partei;
			}
		}

		return staerkstePartei;

	}

	@Override
	public int getAnzahlErststimmen() {
		int anzahl = 0;
		for (final Wahlkreis wahlkreis : getWahlkreise()) {
			anzahl += wahlkreis.getAnzahlErststimmen();
		}
		return anzahl;
	}

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	@Override
	public int getAnzahlErststimmen(Partei partei) {
		int anzahl = 0;
		for (final Wahlkreis wk : getWahlkreise()) {
			anzahl += wk.getAnzahlErststimmen(partei);
		}
		return anzahl;
	}

	@Override
	public int getAnzahlZweitstimmen() {
		int anzahl = 0;
		for (final Wahlkreis wahlkreis : getWahlkreise()) {
			anzahl += wahlkreis.getAnzahlZweitstimmen();
		}
		return anzahl;
	}

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	@Override
	public int getAnzahlZweitstimmen(Partei partei) {
		int anzahl = 0;
		for (final Wahlkreis wk : getWahlkreise()) {
			anzahl += wk.getAnzahlZweitstimmen(partei);
		}
		return anzahl;
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
		final LinkedList<Kandidat> direktmandate = new LinkedList<Kandidat>();
		for (final Wahlkreis wk : getWahlkreise()) {
			if (wk.getWahlkreisSieger().getPartei().equals(part)) {
				direktmandate.add(wk.getWahlkreisSieger());
			}
		}
		return direktmandate;
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
	 * Gibt die Farbe des Bundeslandes zurueck.
	 * 
	 * @return die Farbe.
	 */
	public Color getFarbe() {
		return this.farbe;
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
		for (final Landesliste land : this.getLandesliste()) {
			if (land.getPartei().equals(partei)) {
				result = land;
			}
		}
		return result;

	}

	/**
	 * Gibt die Liste mit Parteien zurueck.
	 * 
	 * @return die Liste mit Parteien.
	 */
	public LinkedList<Partei> getParteien() {
		return this.parteien;
	}

	@Override
	public int getWahlberechtigte() {
		int wahlberechtigte = 0;
		for (int i = 0; i < this.wahlkreise.size(); i++) {
			wahlberechtigte += this.wahlkreise.get(i).getWahlberechtigte();
		}
		return wahlberechtigte;
	}

	/**
	 * Gibt eine Liste mit den Wahlkreisen zurueck.
	 * 
	 * @return die Liste mit Wahlkreisen.
	 */
	public LinkedList<Wahlkreis> getWahlkreise() {
		return this.wahlkreise;
	}

	public List<Zweitstimme> getZweitstimmenProPartei() {
		final List<Zweitstimme> zweitstimmen = new LinkedList<Zweitstimme>();
		final int[] tempZweitstimmen = new int[this.wahlkreise.get(0)
				.getZweitstimmenProPartei().size()];
		for (int i = 0; i < this.wahlkreise.size(); i++) {
			final List<Zweitstimme> wahlkreisZweitstimme = this.wahlkreise.get(
					i).getZweitstimmenProPartei();
			for (int j = 0; j < wahlkreisZweitstimme.size(); j++) {
				tempZweitstimmen[j] += wahlkreisZweitstimme.get(j).getAnzahl();
			}
		}
		for (int i = 0; i < tempZweitstimmen.length; i++) {
			zweitstimmen.add(new Zweitstimme(tempZweitstimmen[i], this,
					this.wahlkreise.get(0).getZweitstimmenProPartei().get(i)
							.getPartei()));
		}

		return zweitstimmen;
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
	 * Setzt die Farbe des Bundeslandes. Akzeptiert null als Argument damit die
	 * Farbe im Generator zurueckgesetzt werden kann.
	 * 
	 * @param farbe
	 *            des Bundeslandes.
	 */
	public void setFarbe(Color farbe) {
		this.farbe = farbe;
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
	 * Setzt eine neue Liste mit Parteien.
	 * 
	 * @param parteien
	 *            die neue Liste.
	 * @throws IllegalArgumentException
	 *             wenn die Liste leer ist.
	 */
	public void setParteien(LinkedList<Partei> parteien)
			throws IllegalArgumentException {
		if (parteien == null) {
			throw new IllegalArgumentException(
					"Der Parameter \"parteien\" ist null!");
		}
		this.parteien = parteien;
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

}

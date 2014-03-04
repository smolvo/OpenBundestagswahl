package main.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasse die alle Bundeslaender beinhaltet.
 * 
 */
public class Deutschland extends Gebiet implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -2346463735187246165L;

	/** Liste mit den enthaltenden Bundeslaender. */
	private LinkedList<Bundesland> bundeslaender = new LinkedList<Bundesland>();

	/** Einwohnerzahl in Deutschland */
	private int einwohneranzahl;

	/**
	 * Ist die Anzahl an Zweitstimmen mit der eine Partei sicher in Bundestag
	 * ist
	 */
	private int sperrklauselAnzahl;

	/**
	 * Angepasster Konstruktor.
	 * 
	 * @param name
	 *            Der Name.
	 */
	public Deutschland(String name) {
		this.setName(name);
	}

	/**
	 * Angepasster Konstruktor.
	 * 
	 * @param name
	 *            Der Name.
	 * @param zweitstimme
	 *            Die Liste aller Zweitstimmenobjekte (pro Partei und Gebiet).
	 */
	public Deutschland(String name, LinkedList<Zweitstimme> zweitstimme) {
		this.setName(name);
	}

	/**
	 * Gibt eine Liste mit den Bundeslaender zurueck.
	 * 
	 * @return die Liste mit Bundeslaender.
	 */
	public LinkedList<Bundesland> getBundeslaender() {
		return this.bundeslaender;
	}

	/**
	 * Setzt eine neue Liste mit Bundeslaemder.
	 * 
	 * @param bundeslaender
	 *            die neue Liste.
	 * @throws IllegalArgumentException
	 *             wenn die Liste leer ist.
	 */
	public void setBundeslaender(LinkedList<Bundesland> bundeslaender)
			throws IllegalArgumentException {
		if (bundeslaender == null || bundeslaender.isEmpty()) {
			throw new IllegalArgumentException("Wahlkreisliste ist leer");
		}
		this.bundeslaender = bundeslaender;
	}

	/**
	 * Fuegt ein Bundesland zur Liste hinzu.
	 * 
	 * @param bundesland
	 *            ist das neue Bundesland.
	 */
	public void addBundesland(Bundesland bundesland) {
		if (bundesland == null) {
			throw new IllegalArgumentException("Bundesland ist leer!");
		}
		this.bundeslaender.add(bundesland);
	}


	public List<Zweitstimme> getZweitstimmenProPartei() {

	
		List<Zweitstimme> zweitstimmen = new LinkedList<Zweitstimme>();
		int[] tempZweitstimmen = new int[this.bundeslaender.get(0)
				.getZweitstimmenProPartei().size()];
		for (int i = 0; i < this.bundeslaender.size(); i++) {
			List<Zweitstimme> bundeslaenderZweitstimme = bundeslaender.get(i)
					.getZweitstimmenProPartei();
			for (int j = 0; j < bundeslaenderZweitstimme.size(); j++) {
				tempZweitstimmen[j] += bundeslaenderZweitstimme.get(j)
						.getAnzahl();
			}
		}
		for (int i = 0; i < tempZweitstimmen.length; i++) {
			zweitstimmen.add(new Zweitstimme(tempZweitstimmen[i], this,
					this.bundeslaender.get(0).getZweitstimmenProPartei().get(i)
							.getPartei()));
		}

		return zweitstimmen;
	}
	


	@Override
	public int getWahlberechtigte() {
		int wahlberechtigte = 0;
		for (int i = 0; i < this.bundeslaender.size(); i++) {
			wahlberechtigte += bundeslaender.get(i).getWahlberechtigte();
		}
		return wahlberechtigte;
	}

	/**
	 * Berechnet und gibt die Einwohnerzahl zurueck.
	 * 
	 * @return die Einwohnerzahl.
	 */
	public int getEinwohneranzahl() {
		// Einwohneranzahl zuruecksetzen
		einwohneranzahl = 0;
		for (Bundesland bl : this.bundeslaender) {
			einwohneranzahl += bl.getEinwohnerzahl();
		}
		return einwohneranzahl;
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
		for (Bundesland bl : this.getBundeslaender()) {
			anzahl += bl.getAnzahlZweitstimmen(partei);
		}
		return anzahl;
	}

	/**
	 * Gibt die anzahl der Erststimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	@Override
	public int getAnzahlErststimmen(Partei partei) {
		int anzahl = 0;
		for (Bundesland bl : this.getBundeslaender()) {
			anzahl += bl.getAnzahlErststimmen(partei);

		}
		return anzahl;
	}

	/**
	 * Gibt eine Liste der Wahlkreise aller Bundeslaender zurueck.
	 * 
	 * @return eine Liste der Wahlkreise aller Bundeslaender
	 */
	public ArrayList<Wahlkreis> getWahlkreise() {
		ArrayList<Wahlkreis> alleWk = new ArrayList<>();
		for (Bundesland bl : this.getBundeslaender()) {
			alleWk.addAll(bl.getWahlkreise());
		}
		return alleWk;
	}

	@Override
	public int getAnzahlErststimmen() {
		int anzahl = 0;
		for (Bundesland bundesland : this.getBundeslaender()) {
			anzahl += bundesland.getAnzahlErststimmen();
		}
		return anzahl;
	}

	@Override
	public int getAnzahlZweitstimmen() {
		int anzahl = 0;
		for (Bundesland bundesland : this.getBundeslaender()) {
			anzahl += bundesland.getAnzahlZweitstimmen();
		}
		return anzahl;
	}
}

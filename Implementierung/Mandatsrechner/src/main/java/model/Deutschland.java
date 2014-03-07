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
	 * Angepasster Konstruktor.
	 * 
	 * @param name
	 *            Der Name.
	 */
	public Deutschland(String name) {
		setName(name);
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
		setName(name);
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

	@Override
	public int getAnzahlErststimmen() {
		int anzahl = 0;
		for (final Bundesland bundesland : getBundeslaender()) {
			anzahl += bundesland.getAnzahlErststimmen();
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
		for (final Bundesland bl : getBundeslaender()) {
			anzahl += bl.getAnzahlErststimmen(partei);

		}
		return anzahl;
	}

	@Override
	public int getAnzahlZweitstimmen() {
		int anzahl = 0;
		for (final Bundesland bundesland : getBundeslaender()) {
			anzahl += bundesland.getAnzahlZweitstimmen();
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
		for (final Bundesland bl : getBundeslaender()) {
			anzahl += bl.getAnzahlZweitstimmen(partei);
		}
		return anzahl;
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
	 * Berechnet und gibt die Einwohnerzahl zurueck.
	 * 
	 * @return die Einwohnerzahl.
	 */
	public int getEinwohneranzahl() {
		// Einwohneranzahl zuruecksetzen
		this.einwohneranzahl = 0;
		for (final Bundesland bl : this.bundeslaender) {
			this.einwohneranzahl += bl.getEinwohnerzahl();
		}
		return this.einwohneranzahl;
	}

	@Override
	public int getWahlberechtigte() {
		int wahlberechtigte = 0;
		for (int i = 0; i < this.bundeslaender.size(); i++) {
			wahlberechtigte += this.bundeslaender.get(i).getWahlberechtigte();
		}
		return wahlberechtigte;
	}

	/**
	 * Gibt eine Liste der Wahlkreise aller Bundeslaender zurueck.
	 * 
	 * @return eine Liste der Wahlkreise aller Bundeslaender
	 */
	public ArrayList<Wahlkreis> getWahlkreise() {
		final ArrayList<Wahlkreis> alleWk = new ArrayList<>();
		for (final Bundesland bl : getBundeslaender()) {
			alleWk.addAll(bl.getWahlkreise());
		}
		return alleWk;
	}

	public List<Zweitstimme> getZweitstimmenProPartei() {

		final List<Zweitstimme> zweitstimmen = new LinkedList<Zweitstimme>();
		final int[] tempZweitstimmen = new int[this.bundeslaender.get(0)
				.getZweitstimmenProPartei().size()];
		for (int i = 0; i < this.bundeslaender.size(); i++) {
			final List<Zweitstimme> bundeslaenderZweitstimme = this.bundeslaender
					.get(i).getZweitstimmenProPartei();
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
}

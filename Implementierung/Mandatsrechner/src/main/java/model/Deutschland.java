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
	
	/** Automatisch generierte serialVersionUID die fuer das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -2346463735187246165L;
	
	/** Liste mit den enthaltenden Bundeslaender. */
	private LinkedList<Bundesland> bundeslaender = new LinkedList<Bundesland>();
	
	/**Einwohnerzahl in Deutschland*/
	private int einwohneranzahl;
	
	/** Ist die Anzahl an Zweitstimmen mit der eine Partei sicher in Bundestag ist */
	private int sperrklauselAnzahl;
	
	
	/**
	 * Angepasster Konstruktor.
	 * @param name Der Name.
	 * @param wahlberechtigte Die Anzahl der Wahlberechtigten.
	 */
	public Deutschland(String name) {
		this.setName(name);
	}
	
	/**
	 * Angepasster Konstruktor.
	 * @param name Der Name.
	 * @param wahlberechtigte Die Anzahl der Wahlberechtigten.
	 * @param zweitstimme Die Liste aller Zweitstimmenobjekte (pro Partei und Gebiet).
	 */
	public Deutschland(String name, LinkedList<Zweitstimme> zweitstimme) {
		this.setName(name);
	}
	
	/**
	 * Gibt eine Liste mit den Bundeslaender zurueck.
	 * @return die Liste mit Bundeslaender.
	 */
	public LinkedList<Bundesland> getBundeslaender() {
		return this.bundeslaender;
	}
	
	/**
	 * Setzt eine neue Liste mit Bundeslaemder.
	 * @param bundeslaender die neue Liste.
	 * @throws IllegalArgumentException wenn die Liste leer ist.
	 */
	public void setBundeslaender(LinkedList<Bundesland> bundeslaender) throws IllegalArgumentException {
		if (bundeslaender == null || bundeslaender.isEmpty()) {
			throw new IllegalArgumentException("Wahlkreisliste ist leer");
		}
		this.bundeslaender = bundeslaender;
	}
	
	/**
	 * Fuegt ein Bundesland zur Liste hinzu.
	 * @param bundesland ist das neue Bundesland.
	 */
	public void addBundesland(Bundesland bundesland) {
		if (bundesland == null) {
		      throw new IllegalArgumentException("Bundesland ist leer!");
		}
		this.bundeslaender.add(bundesland);
	}

	@Override
	public List<Erststimme> getErststimmenProPartei() {
		
		List<Erststimme> erststimmen = new LinkedList<Erststimme>();
		int[] tempStimmen = new int[this.bundeslaender.get(0).getErststimmenProPartei().size()];
		for (int i = 0; i < this.bundeslaender.size(); i++) {
			List<Erststimme> bundeslaenderErststimme = bundeslaender.get(i).getErststimmenProPartei();
			for (int j = 0; j < bundeslaenderErststimme.size(); j++) {
				tempStimmen[j] += bundeslaenderErststimme.get(j).getAnzahl();
			}
		}
		for (int i = 0; i < tempStimmen.length; i++) {
			erststimmen.add(new Erststimme(
					tempStimmen[i],
					this,
					new Kandidat("Unbekannt", "Unbekannt", 0, Mandat.KEINMANDAT, null)));
		}
		
		return erststimmen;
	}

	
	
	@Override
	public List<Zweitstimme> getZweitstimmenProPartei() {
		
		List<Zweitstimme> zweitstimmen = new LinkedList<>();
		
		for (Bundesland bundesland : this.getBundeslaender()) {
			zweitstimmen.addAll(bundesland.getZweitstimmenProPartei());
		}
		
		/*
		// TODO Auto-generated method stub
		List<Zweitstimme> zweitstimmen = new LinkedList<Zweitstimme>();
		int[] tempZweitstimmen = new int[this.bundeslaender.get(0).getZweitstimmen().size()];
		for (int i = 0; i < this.bundeslaender.size(); i++) {
			List<Zweitstimme> bundeslaenderZweitstimme = bundeslaender.get(i).getZweitstimmen();
			for (int j = 0; j < bundeslaenderZweitstimme.size(); j++) {
				tempZweitstimmen[j] += bundeslaenderZweitstimme.get(j).getAnzahl();
			}
		}
		for (int i = 0; i < tempZweitstimmen.length; i++) {
			zweitstimmen.add(new Zweitstimme(
					tempZweitstimmen[i],
					this,
					this.bundeslaender.get(0).getZweitstimmen().get(i).getPartei()));
		}
		*/
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
	 * @return die Einwohnerzahl.
	 */
	public int getEinwohneranzahl() {
		//Einwohneranzahl zuruecksetzen
		einwohneranzahl = 0;
		for (Bundesland bl : this.bundeslaender) {
			einwohneranzahl += bl.getEinwohnerzahl();
		}
		return einwohneranzahl;
	}

	/**
	 * Berechnet 5% aller Zweitstimmen in Deutschland und gibt diese Zahl zurueck.
	 * @return 5% der Zweitstimmen in Deutschland.
	 */
	public int getSperrklauselAnzahl() {
		sperrklauselAnzahl = zweitstimmeGesamt / 20;
		return sperrklauselAnzahl;
	}

	@Override
	public int getAnzahlZweitstimmen(Partei partei) {
		int anzahl = 0;
	
		// durchlaufe alle Zweitstimmen und summiere die Anzahlen
		for (Zweitstimme zweitstimme : this.getZweitstimmenProPartei()) {
			anzahl += zweitstimme.getAnzahl();
		}
		
		return anzahl;
	}
	
	/**
	 * Gibt die Gesamtanzahl an Erststimmen für die uebergebene Partei zurueck
	 * @param partei die partei deren Erststimmen ermittelt werden sollen
	 * @return die gesamstanzahl der Erststimmen
	 */
	public int getAnzahlErststimmen(Partei partei) {
		int anzahl = 0;
		
		// durchlaufe alle Erststimmen und summiere die Anzahlen
		for (Erststimme erststimme : this.getErststimmenProPartei()) {
			anzahl += erststimme.getAnzahl();
		}
		
		return anzahl;
	}
	
	/**
	 * Gibt eine Liste der Wahlkreise aller Bundeslaender zurueck.
	 * @return eine Liste der Wahlkreise aller Bundeslaender
	 */
	public ArrayList<Wahlkreis> getWahlkreise() {
		ArrayList<Wahlkreis> alleWk = new ArrayList<>();
		for (Bundesland bl : this.getBundeslaender()) {
			alleWk.addAll(bl.getWahlkreise());
		}
		return alleWk;
	}
}

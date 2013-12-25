package model;

import java.util.LinkedList;
/**
 * Klasse die alle Bundesländer beinhaltet. 
 * @author Nick
 *
 */
public class Deutschland extends Gebiet {
	
	/**
	 * Liste mit den enthaltenden Bundesländer.
	 */
	LinkedList<Bundesland> bundeslaender = new LinkedList<Bundesland>();
	
	/**
	 * Das verbundene Zweitstimmen-Objekt.
	 */
	Zweitstimme zweitstimme;
	/**
	 * Angepasster Konstruktor.
	 * @param name
	 * @param wahlberechtigte
	 * @param zweitstimme
	 */
	public Deutschland(String name, int wahlberechtigte, Zweitstimme zweitstimme){
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
		this.setZweitstimme(zweitstimme);
	}
	/**
	 * Gibt eine Liste mit den Bundesländer zurück.
	 * @return die Liste mit Bundesländer
	 */
	public LinkedList<Bundesland> getBundeslaender() {
		return bundeslaender;
	}
	
	/**
	 * Setzt eine neue Liste mit Bundeslämder.
	 * @param bundeslaender die neue Liste
	 * @exception wenn die Liste leer ist
	 */
	public void setBundeslaender(LinkedList<Bundesland> bundeslaender) {
		if(bundeslaender == null || bundeslaender.isEmpty()){
			throw new IllegalArgumentException("Wahlkreisliste ist leer");
		}
		this.bundeslaender = bundeslaender;
	}
	
	/**
	 * Fügt ein Bundesland zur Liste hinzu.
	 * @param bundesland ist das neue Bundesland
	 */
	public void addBundesland(Bundesland bundesland){
		if (bundesland == null) {
		      throw new IllegalArgumentException("Bundesland ist leer!");
		}
		this.bundeslaender.add(bundesland);
	}

	/**
	 * Gibt das Zweitstimme-Objekt zurück
	 * @return das Zweitstimme-Objekt
	 */
	public Zweitstimme getZweitstimme() {
		return zweitstimme;
	}

	/**
	 * Setzt das Zweitstimme-Objekt
	 * @param zweitstimme das Objekt
	 * @exception wenn das Zweitstimme-Objekt leer ist
	 */
	public void setZweitstimme(Zweitstimme zweitstimme) {
		if (zweitstimme == null) {
		      throw new IllegalArgumentException("Zweitstimme ist leer!");
		}
		this.zweitstimme = zweitstimme;
	}
}

package model;


import java.util.LinkedList;

/**
 * Klasse die die Bundesländer repräsentiert. Unterklasse von Gebiet.
 * @author Nick
 *
 */
public class Bundesland extends Gebiet{

	/**
	 * Einwohnerzahl des Bundeslandes.
	 */
	private int einwohnerzahl;
	/**
	 * Farbe des Bundeslandes
	 */
	private Farbe farbe;
	/**
	 * Liste mit den Wahlkreisen im Bundesland
	 */
	private LinkedList<Wahlkreis> wahlkreise = new LinkedList<Wahlkreis>();
	/**
	 * Liste mit den vertrettenden Parteien im Bundesland
	 */
	private LinkedList<Partei> parteien = new LinkedList<Partei>();
	/**
	 * Angepasster Konstruktor. Listen werden seperat hinzugefügt
	 * @param name
	 * @param wahlberechtigte
	 * @param einwohnerzahl
	 */
	public Bundesland(String name, int wahlberechtigte, int einwohnerzahl){
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
		this.setEinwohnerzahl(einwohnerzahl);
	}
	
	/**
	 * Gibt die Einwohnerzahl zurück
	 * @return die Einwohnerzahl
	 */
	public int getEinwohnerzahl() {
		return einwohnerzahl;
	}
	
	/**
	 * Setzt die Einwohnerzahl
	 * @param einwohnerzahl wird neu gesetzt
	 * @exception wenn die zahl negativ ist
	 */
	public void setEinwohnerzahl(int einwohnerzahl) {
		if(einwohnerzahl < 0){
			throw new IllegalArgumentException("Einwohnerzahl ist kleiner 0");
		}
		this.einwohnerzahl = einwohnerzahl;
	}
	
	/**
	 * Gibt die Farbe des Bundeslandes zurück
	 * @return die Farbe
	 */
	public Farbe getFarbe() {
		return farbe;
	}
	
	/**
	 * Setzt die Farbe des Bundeslandes
	 * @param farbe des Bundeslandes
	 * @exception wenn die Farbe leer ist
	 */
	public void setFarbe(Farbe farbe) {
		if(farbe == null){
			throw new IllegalArgumentException("Farbe ist leer");
		}
		this.farbe = farbe;
	}
	
	/**
	 * Gibt eine Liste mit den Wahlkreisen zurück
	 * @return die Liste mit Wahlkreisen
	 */
	public LinkedList<Wahlkreis> getWahlkreise() {
		return wahlkreise;
	}
	
	/**
	 * Setzt eine neue Liste mit Wahlkreisen
	 * @param wahlkreise die neue Liste
	 * @exception wenn die Liste leer ist
	 */
	public void setWahlkreise(LinkedList<Wahlkreis> wahlkreise) {
		if(wahlkreise == null || wahlkreise.isEmpty()){
			throw new IllegalArgumentException("Wahlkreisliste ist leer");
		}
		this.wahlkreise = wahlkreise;
	}
	
	/**
	 * Gibt die Liste mit Parteien zurück
	 * @return die Liste mit Parteien
	 */
	public LinkedList<Partei> getParteien() {
		return parteien;
	}
	
	/**
	 * Setzt eine neue Liste mit Parteien
	 * @param parteien die neue Liste
	 * @exception wenn die Liste leer ist
	 */
	public void setParteien(LinkedList<Partei> parteien) {
		if(parteien == null || parteien.isEmpty()){
			throw new IllegalArgumentException("Name ist leer");
		}
		this.parteien = parteien;
	}
	
	/**
	 * Fügt eine Partei zur Liste hinzu
	 * @param partei ist der neue Partei
	 */
	public void addPartei(Partei partei){
		if (partei == null) {
		      throw new IllegalArgumentException("Partei ist leer!");
		}
		this.parteien.add(partei);
	}
	
	/**
	 * Fügt eine Partei zur Liste hinzu
	 * @param partei ist der neue Partei
	 */
	public void addWahlkreis(Wahlkreis wahlkreis){
		if (wahlkreis == null) {
		      throw new IllegalArgumentException("Wahlkreis ist leer!");
		}
		this.wahlkreise.add(wahlkreis);
	}
	
}

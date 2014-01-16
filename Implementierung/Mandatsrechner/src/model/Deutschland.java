package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasse die alle Bundesländer beinhaltet. 
 *
 */
public class Deutschland extends Gebiet implements Serializable {
	
	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -2346463735187246165L;
	
	/** Liste mit den enthaltenden Bundesländer. */
	private LinkedList<Bundesland> bundeslaender = new LinkedList<Bundesland>();
	
	/**Einwohnerzahl in Deutschland*/
	private int einwohneranzahl;
	
	/**Zweitstimmenanzahl in ganz Deutschland*/
	private int zweitstimmeGesamt;
	
	/** Ist die Anzahl an Zweitstimmen mit der eine Partei sicher in Bundestag ist */
	private int sperrklauselAnzahl;
	/**
	 * Angepasster Konstruktor.
	 * @param name Der Name
	 * @param wahlberechtigte Die Anzahl der Wahlberechtigten
	 */
	public Deutschland(String name, int wahlberechtigte) {
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
	}
	
	/**
	 * Angepasster Konstruktor.
	 * @param name Der Name
	 * @param wahlberechtigte Die Anzahl der Wahlberechtigten
	 * @param zweitstimme Die Liste aller Zweitstimmenobjekte (pro Partei und Gebiet)
	 */
	public Deutschland(String name, int wahlberechtigte, LinkedList<Zweitstimme> zweitstimme) {
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
		this.setZweitstimmen(zweitstimme);
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
	 * @throws IllegalArgumentException wenn die Liste leer ist
	 */
	public void setBundeslaender(LinkedList<Bundesland> bundeslaender) throws IllegalArgumentException {
		if (bundeslaender == null || bundeslaender.isEmpty()) {
			throw new IllegalArgumentException("Wahlkreisliste ist leer");
		}
		this.bundeslaender = bundeslaender;
	}
	
	/**
	 * Fügt ein Bundesland zur Liste hinzu.
	 * @param bundesland ist das neue Bundesland
	 */
	public void addBundesland(Bundesland bundesland) {
		if (bundesland == null) {
		      throw new IllegalArgumentException("Bundesland ist leer!");
		}
		this.bundeslaender.add(bundesland);
	}

	@Override
	public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Erststimme> getErststimmen() {
		List<Erststimme> erststimmen = new LinkedList<Erststimme>();
		int[] tempStimmen = new int[this.bundeslaender.get(0).getErststimmen().size()];
		for (int i = 0; i < this.bundeslaender.size(); i++) {
			List<Erststimme> bundeslaenderErststimme = bundeslaender.get(i).getErststimmen();
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
	public List<Zweitstimme> getZweitstimmen() {
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

	@Override
	public void setWahlberechtigte(int wahlberechtigte) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Berechnet und gibt die Einwohnerzahl
	 * @return die Einwohnerzahl
	 */
	public int getEinwohneranzahl() {
		//Einwohneranzahl zurücksetzen
		einwohneranzahl = 0;
		for(Bundesland bl : this.bundeslaender){
			einwohneranzahl += bl.getEinwohnerzahl();
		}
		return einwohneranzahl;
	}
	/* Notwendig?
	public void setEinwohneranzahl(int einwohneranzahl) {
		this.einwohneranzahl = einwohneranzahl;
	}
	*/

	/**
	 * Gibt die Zweitstimmenanzahl aller Parteien in Deutschland
	 * @return die Zweistimmenanzahl aller PArtein
	 */
	public int getZweitstimmeGesamt() {
		zweitstimmeGesamt = 0;
		for(Zweitstimme zweit : getZweitstimmen()){
			zweitstimmeGesamt += zweit.getAnzahl();
		}
		return zweitstimmeGesamt;
	}

	/**
	 * Berechnet 5% aller Zweitstimmen in Deutschland und gibt diese Zahl zurueck
	 * @return 5% der Zweitstimmen in Deutschland
	 */
	public int getSperrklauselAnzahl(){
		sperrklauselAnzahl = zweitstimmeGesamt / 20;
		return sperrklauselAnzahl;
	}
}

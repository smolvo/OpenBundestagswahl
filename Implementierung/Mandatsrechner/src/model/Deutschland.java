	package model;

import java.util.LinkedList;
import java.util.List;
/**
 * Klasse die alle Bundesländer beinhaltet. 
 *
 */
public class Deutschland extends Gebiet implements Cloneable {
	
	/** Liste mit den enthaltenden Bundesländer. */
	LinkedList<Bundesland> bundeslaender = new LinkedList<Bundesland>();
	
	/**
	 * Angepasster Konstruktor.
	 * @param name
	 * @param wahlberechtigte
	 */
	public Deutschland(String name, int wahlberechtigte){
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
	}
	
	/**
	 * Angepasster Konstruktor.
	 * @param name
	 * @param wahlberechtigte
	 * @param zweitstimme
	 */
	public Deutschland(String name, int wahlberechtigte, LinkedList<Zweitstimme> zweitstimme){
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
	 * Erzeugt eine tiefe Kopie dieses Objekts und gibt diese zurück.
	 * @return eine tiefe Kopie dieses Objekts
	 */
	@Override
	public Deutschland clone() {
		// TODO ... ;-)
		throw new UnsupportedOperationException("Noch nicht implementiert...");
	}

	@Override
	public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Erststimme> getErststimmen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zweitstimme> getZweitstimmen() {
		// TODO Auto-generated method stub
		List<Zweitstimme> zweitstimmen = new LinkedList<Zweitstimme>();
		int[] tempZweitstimmen = new int[this.bundeslaender.get(0).getZweitstimmen().size()];
		for(int i=0;i<this.bundeslaender.size();i++){
			List<Zweitstimme> bundeslaenderZweitstimme = bundeslaender.get(i).getZweitstimmen();
			for(int j=0;j<bundeslaenderZweitstimme.size();j++){
				tempZweitstimmen[j]+=bundeslaenderZweitstimme.get(j).getAnzahl();
			}
		}
		for(int i=0;i<tempZweitstimmen.length;i++){
			zweitstimmen.add(new Zweitstimme(tempZweitstimmen[i], this, this.bundeslaender.get(0).getZweitstimmen().get(i).getPartei()));
		}
		
		return zweitstimmen;
	}

	@Override
	public int getWahlberechtigte() {
		// TODO Auto-generated method stub
		int wahlberechtigte = 0;
		for(int i=0;i<this.bundeslaender.size();i++){
			wahlberechtigte+=bundeslaender.get(i).getWahlberechtigte();
		}
		return wahlberechtigte;
	}

	@Override
	public void setWahlberechtigte(int wahlberechtigte) {
		// TODO Auto-generated method stub
		
	}
	
}

package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Assoziationsklasse zwischen Partei und Bundesland. 
 * Dies Klasse beinhaltet eine Menge an Kandidaten.
 */
public class Landesliste {

	/** Die zugeh�rige Partei. */
	private Partei partei;
	
	/** Das zugeh�rige Bundesland. */
	private Bundesland bundesland;
	
	/** Die Liste mit den Kandidaten der Partei im Bundesland */
	private List<Kandidat> listenkandidaten = new ArrayList<Kandidat>();

	/**
	 * Parametrisierter Konstruktur.
	 * Kandidaten werden hier noch keine hinzugef�gt.
	 * @param partei
	 * @param bundesland
	 */
	public Landesliste(Partei partei, Bundesland bundesland) {
		this.setBundesland(bundesland);
		this.setPartei(partei);
	}
	
	/**
	 * Gibt das Partei-Objekt zur�ck
	 * @return das Partei-Objekt
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Setzt das Partei-Objekt
	 * @param partei das Partei-Objekt
	 * @exception wenn das Partei-Objekt leer ist
	 */
	public void setPartei(Partei partei) {
		if (partei == null) {
		      throw new IllegalArgumentException("Partei ist leer!");
		}
		this.partei = partei;
	}

	/**
	 * Gibt das Bundesland-Objekt zur�ck
	 * @return das Bundesland-Objekt
	 */
	public Bundesland getBundesland() {
		return bundesland;
	}

	/**
	 * Setzt das Bundesland-Objekt
	 * @param bundesland das Bundesland-Objekt
	 * @exception wenn das Bundesland-Objekt leer ist
	 */
	public void setBundesland(Bundesland bundesland) {
		if (bundesland == null) {
		      throw new IllegalArgumentException("Bundesland ist leer!");
		}
		this.bundesland = bundesland;
	}

	/**
	 * Gibt die Liste mit den Listenkandidaten zur�ck
	 * @return die Liste mit den Listenkandidaten
	 */
	public List<Kandidat> getListenkandidaten() {
		return listenkandidaten;
	}

	/**
	 * Setzt die Liste der Listenkandidaten
	 * @param listenkandidaten die Liste mit den Kandidaten
	 * @exception wenn die leer ist
	 */
	public void setListenkandidaten(LinkedList<Kandidat> listenkandidaten) {
		if (listenkandidaten == null || listenkandidaten.isEmpty()) {
		      throw new IllegalArgumentException("Liste ist leer!");
		}
		this.listenkandidaten = listenkandidaten;
	}
	
	/**
	 * F�gt einen Kandidaten zur Liste hinzu
	 * @param kandidat ist der neue Kandidat
	 */
	public void addKandidat(int pos,Kandidat kandidat) {
		if (kandidat == null) {
		      throw new IllegalArgumentException("Kandidat ist leer!");
		}
		if(pos>=this.listenkandidaten.size()){
			for(int i=this.listenkandidaten.size();i<=pos;i++){
				this.listenkandidaten.add(i,null);
			}
		}
		//System.out.println(pos+" "+kandidat.getName());
		this.listenkandidaten.set(pos, kandidat);
		//this.listenkandidaten.add(kandidat);
	}
	
	/**
	 * Gibt alle Kandidaten der Liste mit dem gew�nschten Mandat zur�ck
	 * @param mandat das gew�nschte Mandat
	 * @return die Liste mit den Listenkandidaten
	 */
	public LinkedList<Kandidat> getKandidaten(Mandat mandat) {
		LinkedList<Kandidat> res = new LinkedList<Kandidat>();
		for (Kandidat kandidat: this.listenkandidaten) {
			if (kandidat.getMandat() == mandat) {
				res.add(kandidat);
			}
		}
		return res;
	}
}

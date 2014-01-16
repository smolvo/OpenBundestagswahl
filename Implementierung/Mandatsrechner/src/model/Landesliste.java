package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Assoziationsklasse zwischen Partei und Bundesland. 
 * Dies Klasse beinhaltet eine Menge an Kandidaten.
 */
public class Landesliste implements Serializable {

	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -6647931864369484778L;

	/** Die zugehörige Partei. */
	private Partei partei;
	
	/** Das zugehörige Bundesland. */
	private Bundesland bundesland;
	
	/** Die Liste mit den Kandidaten der Partei im Bundesland */
	private List<Kandidat> listenkandidaten = new ArrayList<Kandidat>();
	
	private int mindestSitzanzahl;

	/**
	 * Parametrisierter Konstruktur.
	 * Kandidaten werden hier noch keine hinzugefügt.
	 * @param partei Die zugehörige Partei.
	 * @param bundesland Das zugehörige Bundesland.
	 */
	public Landesliste(Partei partei, Bundesland bundesland) {
		this.setBundesland(bundesland);
		this.setPartei(partei);
	}
	
	/**
	 * Gibt das Partei-Objekt zurück
	 * @return das Partei-Objekt
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Setzt das Partei-Objekt
	 * @param partei das Partei-Objekt
	 * @throws IllegalArgumentException wenn das Partei-Objekt null ist
	 */
	public void setPartei(Partei partei) throws IllegalArgumentException {
		if (partei == null) {
		      throw new IllegalArgumentException("Partei ist leer!");
		}
		this.partei = partei;
	}

	/**
	 * Gibt das Bundesland-Objekt zurück
	 * @return das Bundesland-Objekt
	 */
	public Bundesland getBundesland() {
		return bundesland;
	}

	/**
	 * Setzt das Bundesland-Objekt
	 * @param bundesland das Bundesland-Objekt
	 * @throws IllegalArgumentException wenn das Bundesland-Objekt null ist
	 */
	public void setBundesland(Bundesland bundesland) throws IllegalArgumentException {
		if (bundesland == null) {
		      throw new IllegalArgumentException("Bundesland ist null!");
		}
		this.bundesland = bundesland;
	}

	/**
	 * Gibt die Liste mit den Listenkandidaten zurück
	 * @return die Liste mit den Listenkandidaten
	 */
	public List<Kandidat> getListenkandidaten() {
		return listenkandidaten;
	}

	/**
	 * Setzt die Liste der Listenkandidaten
	 * @param listenkandidaten die Liste mit den Kandidaten
	 * @throws IllegalArgumentException wenn die null ist
	 */
	public void setListenkandidaten(LinkedList<Kandidat> listenkandidaten) throws IllegalArgumentException {
		if (listenkandidaten == null || listenkandidaten.isEmpty()) {
		      throw new IllegalArgumentException("Liste ist null oder leer!");
		}
		this.listenkandidaten = listenkandidaten;
	}
	
	/**
	 * Fügt einen Kandidaten zur Liste hinzu
	 * @param pos Listenposition des Kandidaten
	 * @param kandidat ist der neue Kandidat
	 * @throws IllegalArgumentException wenn der kandidat null ist
	 */
	public void addKandidat(int pos, Kandidat kandidat) throws IllegalArgumentException {
		if (kandidat == null) {
		      throw new IllegalArgumentException("Kandidat ist null!");
		}
		if (pos >= this.listenkandidaten.size()) {
			for (int i = this.listenkandidaten.size(); i <= pos; i++) {
				this.listenkandidaten.add(i, null);
			}
		}
		//System.out.println(pos+" "+kandidat.getName());
		this.listenkandidaten.set(pos, kandidat);
		//this.listenkandidaten.add(kandidat);
	}
	
	/**
	 * Gibt alle Kandidaten der Liste mit dem gewünschten Mandat zurück
	 * @param mandat das gewünschte Mandat
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
	
	/**
	 * Gibt die Mindestsitzanzahl der Partei zurück
	 * @return die Mindestsitzanzahl der Partei zurück
	 */
	public int getMindestSitzanzahl() {
		return mindestSitzanzahl;
	}

	/**
	 * Setzt die mindestsitzanzahl der partei
	 * @param mindestSitzanzahl der partei
	 */
	public void setMindestSitzanzahl(int mindestSitzanzahl) {
		if(mindestSitzanzahl < 0){
			throw new IllegalArgumentException("Sitzanzahl negativ");
		}
		this.mindestSitzanzahl = mindestSitzanzahl;
	}
}

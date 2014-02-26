package main.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Assoziationsklasse zwischen Partei und Bundesland. Diese Klasse beinhaltet
 * eine Menge an Kandidaten.
 */
public class Landesliste implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fï¿½r das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -6647931864369484778L;

	/** Die zugehoerige Partei. */
	private Partei partei;

	/** Das zugehoerige Bundesland. */
	private Bundesland bundesland;

	/** Die Liste mit den Kandidaten der Partei im Bundesland */
	private List<Kandidat> listenkandidaten = new ArrayList<Kandidat>();

	/** Die mindestSitzanzahl im Bundesland. */
	private int mindestSitzanzahl;

	/**
	 * Parametrisierter Konstruktur. Kandidaten werden hier noch keine
	 * hinzugefuegt.
	 * 
	 * @param partei
	 *            Die zugehoerige Partei.
	 * @param bundesland
	 *            Das zugehoerige Bundesland.
	 */
	public Landesliste(Partei partei, Bundesland bundesland) {
		this.setBundesland(bundesland);
		this.setPartei(partei);
	}

	/**
	 * Gibt das Partei-Objekt zurueck.
	 * 
	 * @return das Partei-Objekt.
	 */
	public Partei getPartei() {
		return this.partei;
	}

	/**
	 * Setzt das Partei-Objekt.
	 * 
	 * @param partei
	 *            das Partei-Objekt.
	 * @throws IllegalArgumentException
	 *             wenn das Partei-Objekt null ist.
	 */
	public void setPartei(Partei partei) throws IllegalArgumentException {
		if (partei == null) {
			throw new IllegalArgumentException("Partei ist leer!");
		}
		this.partei = partei;
	}

	/**
	 * Gibt das Bundesland-Objekt zurueck.
	 * 
	 * @return das Bundesland-Objekt.
	 */
	public Bundesland getBundesland() {
		return this.bundesland;
	}

	/**
	 * Setzt das Bundesland-Objekt.
	 * 
	 * @param bundesland
	 *            das Bundesland-Objekt.
	 * @throws IllegalArgumentException
	 *             wenn das Bundesland-Objekt null ist.
	 */
	public void setBundesland(Bundesland bundesland)
			throws IllegalArgumentException {
		if (bundesland == null) {
			throw new IllegalArgumentException("Bundesland ist null!");
		}
		this.bundesland = bundesland;
	}

	/**
	 * Gibt die Liste mit den Listenkandidaten zurueck.
	 * 
	 * @return die Liste mit den Listenkandidaten.
	 */
	public List<Kandidat> getListenkandidaten() {
		return this.listenkandidaten;
	}

	/**
	 * Setzt die Liste der Listenkandidaten.
	 * 
	 * @param listenkandidaten
	 *            die Liste mit den Kandidaten.
	 * @throws IllegalArgumentException
	 *             wenn die null ist.
	 */
	public void setListenkandidaten(LinkedList<Kandidat> listenkandidaten)
			throws IllegalArgumentException {
		if (listenkandidaten == null || listenkandidaten.isEmpty()) {
			throw new IllegalArgumentException("Liste ist null oder leer!");
		}
		this.listenkandidaten = listenkandidaten;
	}

	/**
	 * Fuegt einen Kandidaten zur Liste hinzu.
	 * 
	 * @param pos
	 *            Listenposition des Kandidaten.
	 * @param kandidat
	 *            ist der neue Kandidat.
	 * @throws IllegalArgumentException
	 *             wenn der kandidat null ist.
	 */
	public void addKandidat(int pos, Kandidat kandidat)
			throws IllegalArgumentException {
		if (kandidat == null) {
			throw new IllegalArgumentException("Kandidat ist null!");
		}
		if (pos >= this.listenkandidaten.size()) {
			for (int i = this.listenkandidaten.size(); i <= pos; i++) {
				this.listenkandidaten.add(i, null);
			}
		}
		// System.out.println(pos+" "+kandidat.getName());
		this.listenkandidaten.set(pos, kandidat);
		// this.listenkandidaten.add(kandidat);
	}

	/**
	 * Gibt alle Kandidaten der Liste mit dem gewï¿½nschten Mandat zurueck.
	 * 
	 * @param mandat
	 *            das gewuenschte Mandat.
	 * @return die Liste mit den Listenkandidaten.
	 */
	public LinkedList<Kandidat> getKandidaten(Mandat mandat) {
		LinkedList<Kandidat> res = new LinkedList<Kandidat>();
		for (Kandidat kandidat : this.listenkandidaten) {
			if (kandidat.getMandat() == mandat) {
				res.add(kandidat);
			}
		}
		return res;
	}

	/**
	 * Gibt die Mindestsitzanzahl der Partei zurueck
	 * 
	 * @return die Mindestsitzanzahl der Partei zurueck
	 */
	public int getMindestSitzanzahl() {
		return this.mindestSitzanzahl;
	}

	/**
	 * Setzt die mindestsitzanzahl der partei.
	 * 
	 * @param mindestSitzanzahl
	 *            der partei.
	 */
	public void setMindestSitzanzahl(int mindestSitzanzahl) {
		if (mindestSitzanzahl < 0) {
			throw new IllegalArgumentException("Sitzanzahl negativ");
		}
		this.mindestSitzanzahl = mindestSitzanzahl;
	}
	
	/**
	 * Vergleicht zwei Landeslisten.
	 * @param liste zu vergleichende Liste
	 * @return true false
	 */
	public boolean equals(Landesliste liste) {
		if (liste.getPartei().equals(this.partei) && liste.getBundesland().equals(this.bundesland) 
				&& liste.getListenkandidaten().equals(this.listenkandidaten) 
				&& (liste.getMindestSitzanzahl() == this.mindestSitzanzahl)) {
			return true;
		}
		return false;
	}
}

package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Diese Klasse repräsentiert eine Partei.
 */
public class Partei implements Serializable {
	
	/** Automatisch generierte serialVersionUID die für das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -6711521521647518265L;

	/** Die Liste aller Landeslisten dieser Partei. */
	private List<Landesliste> landesliste;
	
	/** Die Mitgliederliste dieser Partei. */
	private LinkedList<Kandidat> mitglieder;
	
	/** Der Name dieser Partei. */
	private String name;
	
	/** Das Kürzel dieser Partei. */
	private String kuerzel;
	
	/** Die Farbe dieser Partei. */
	private Color farbe;
	
	/** Die Zweitstimmenliste (pro Gebiet) */
	private LinkedList<Zweitstimme> zweitstimme;
	
	
	/**
	 * Parametrisierter Konstruktor.
	 * Die Mitgliederliste und Landesliste wird hier erzeugt aber nicht befüllt.
	 * 
	 * @param name Der Name dieser Partei.
	 * @param kuerzel Das Kürzel dieser Partei.
	 * @param farbe Farbe dieser Partei
	 */
	public Partei(String name, String kuerzel, Color farbe) {
		this.setName(name);
		this.setKuerzel(kuerzel);
		this.setFarbe(farbe);
		this.mitglieder = new LinkedList<Kandidat>();
		this.landesliste = new ArrayList<Landesliste>();
	}
	
	/**
	 * Parametrisierter Konstruktor.
	 * Die Mitgliederliste wird hier nur erzeugt aber nicht befüllt.
	 * @param landesliste Die Liste aller Landeslisten dieser Partei.
	 * @param name Der Name dieser Partei.
	 * @param kuerzel Das Kürzel dieser Partei.
	 * @param farbe Die Farbe dieser Partei.
	 */
	public Partei(List<Landesliste> landesliste, String name, String kuerzel, Color farbe) {
		this.setLandesliste(landesliste);
		this.setName(name);
		this.setKuerzel(kuerzel);
		this.setFarbe(farbe);
		this.mitglieder = new LinkedList<Kandidat>();
	}
	
	/**
	 * Gibt das Landesliste-Objekt zurück
	 * @return das Landesliste-Objekt
	 */
	public List<Landesliste> getLandesliste() {
		return this.landesliste;
	}

	/**
	 * Setzt das neue Landesliste-Objekt
	 * @param landesliste das neue Objekt
	 * @throws IllegalArgumentException wenn der Parameter landesliste null ist 
	 */
	public void setLandesliste(List<Landesliste> landesliste) throws IllegalArgumentException {
		if (landesliste == null) {
			throw new IllegalArgumentException("Landesliste-Objekt ist null!");
		}
		this.landesliste = landesliste;
	}
	
	/**
	 * Fügt eine Landesliste zur Liste aller Landeslisten dieser Partei hinzu.
	 * @param landesliste Die Liste aller Landeslisten dieser Partei.
	 */
	public void addLandesliste(Landesliste landesliste) {
		this.landesliste.add(landesliste);
	}

	/**
	 * Gibt die Liste der Mitglieder zurück
	 * @return Liste der Mitglieder
	 */
	public LinkedList<Kandidat> getMitglieder() {
		return mitglieder;
	}

	/**
	 * Setzt eine neue Liste als Mitgliederliste
	 * @param mitglieder der Partei
	 * @throws IllegalArgumentException wenn der Parameter mitglieder null ist 
	 */
	public void setMitglieder(LinkedList<Kandidat> mitglieder) throws IllegalArgumentException {
		if (mitglieder.equals(null)) {
			throw new IllegalArgumentException("Wahlkreissieger ist leer!");
		}
		this.mitglieder = mitglieder;
	}
	
	/**
	 * Fügt ein neues Mitglied zur Liste hinzu
	 * @param mitglied das neue Mitglied
	 * @throws IllegalArgumentException wenn das Mitglied null ist
	 */
	public void addMitglied(Kandidat mitglied) throws IllegalArgumentException {
		if (mitglied == null) {
			throw new IllegalArgumentException("Mitglied ist null!");
		}
		this.mitglieder.add(mitglied);
	}
	
	/**
	 * Gibt alle Mitglieder mit dem gewünschten Mandat zurück
	 * @param mandat das gewünschte Mandat
	 * @return die Liste mit den Mitgliedern
	 */
	public LinkedList<Kandidat> getMitglieder(Mandat mandat) {
		LinkedList<Kandidat> res = new LinkedList<Kandidat>();
		for (Kandidat kandidat: this.mitglieder) {
			if (kandidat.getMandat() == mandat) {
				res.add(kandidat);
			}
		}
		return res;
	}
	
	/**
	 * Gibt den Namen der Partei zurück
	 * @return den Namen der Partei
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den neuen Namen der Partei
	 * @param name der neue Name
	 * @throws IllegalArgumentException wenn der Parameter name null ist.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name ist leer");
		}
		this.name = name;
	}

	/**
	 * Gibt das Kürzel der Partei zurück
	 * @return das Kürzel der Partei
	 */
	public String getKuerzel() {
		return kuerzel;
	}

	/**
	 * Setzt das Kürzel der Partei
	 * @param kuerzel der Partei
	 * @throws IllegalArgumentException wenn der Parameter kuerzel null ist.
	 */
	public void setKuerzel(String kuerzel) throws IllegalArgumentException {
		if (kuerzel == null || kuerzel.isEmpty()) {
			throw new IllegalArgumentException("Name ist leer");
		}
		this.kuerzel = kuerzel;
	}

	/**
	 * Gibt die Farbe der Partei zurück
	 * @return die Farbe der Partei
	 */
	public Color getFarbe() {
		return farbe;
	}

	/**
	 * Setzt die Farbe der Partei
	 * @param farbe der Partei
	 * @throws IllegalArgumentException wenn der Parameter farbe null ist.
	 */
	public void setFarbe(Color farbe) throws IllegalArgumentException {
		if (farbe == null) {
			throw new IllegalArgumentException("Farbe ist leer");
		}
		this.farbe = farbe;
	}
	
	/**
	 * Gibt das Zweitstimmen-Objekt zurück
	 * @return das Zweitstimmen-Objekt
	 */
	public LinkedList<Zweitstimme> getZweitstimme() {
		return zweitstimme;
	}
	
	/**
	 * Setzt die Liste aller Zweitstimmen pro Gebiet.
	 * @param zweitstimme Die Liste aller Zweitstimmen pro Gebiet.
	 * @throws IllegalArgumentException wenn die Liste aller Zweitstimmen (pro Gebiet) null ist.
	 */
	public void setZweitstimme(LinkedList<Zweitstimme> zweitstimme) throws IllegalArgumentException {
		if (zweitstimme == null) {
			throw new IllegalArgumentException("Zeitstimme-Objekt ist leer");
		}
		this.zweitstimme = zweitstimme;
	}
	
}

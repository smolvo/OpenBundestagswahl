package model;

import java.awt.Color;
import java.util.LinkedList;

/**
 * Diese Klasse repräsentiert eine Partei.
 */
public class Partei implements Cloneable {
	
	/** Landesliste-Objekt */
	private Landesliste landesliste;
	
	/** Mitglieder der Partei */
	private LinkedList<Kandidat> mitglieder;
	
	/** Name der Partei */
	private String name;
	
	/** Kürzel der Partei */
	private String kuerzel;
	
	/** Farbe der Partei */
	private Color farbe;
	
	/** Zweitstimme */
	private LinkedList<Zweitstimme> zweitstimme;
	
	/**
	 * Parametrisierter Konstruktor.
	 * Die Mitgliederliste wird hier nur erzeugt aber nicht befüllt.
	 * @param name
	 * @param kuerzel
	 * @param farbe
	 */
	public Partei(String name, String kuerzel, Color farbe) {
		this.setName(name);
		this.setKuerzel(kuerzel);
		this.setFarbe(farbe);
		this.mitglieder = new LinkedList<Kandidat>();
	}
	
	/**
	 * Parametrisierter Konstruktor.
	 * Die Mitgliederliste wird hier nur erzeugt aber nicht befüllt.
	 * @param landesliste
	 * @param name
	 * @param kuerzel
	 * @param farbe
	 */
	public Partei(Landesliste landesliste, String name, String kuerzel, Color farbe) {
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
	public Landesliste getLandesliste() {
		return landesliste;
	}

	/**
	 * Setzt das neue Landesliste-Objekt
	 * @param landesliste das neue Objekt
	 * @exception wenn das Objekt leer ist
	 */
	public void setLandesliste(Landesliste landesliste) {
		if(landesliste == null) {
			throw new IllegalArgumentException("Landesliste-Objekt ist leer!");
		}
		this.landesliste = landesliste;
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
	 */
	public void setMitglieder(LinkedList<Kandidat> mitglieder) {
		if(mitglieder.equals(null)) {
			throw new IllegalArgumentException("Wahlkreissieger ist leer!");
		}
		this.mitglieder = mitglieder;
	}
	
	/**
	 * Fügt ein neues Mitglied zur Liste hinzu
	 * @param mitglied das neue Mitglied
	 * @exception wenn das Mitglied leer ist
	 */
	public void AddMitglied(Kandidat mitglied) {
		if(mitglied == null) {
			throw new IllegalArgumentException("Mitglied ist leer!");
		}
		this.mitglieder.add(mitglied);
	}
	
	/**
	 * Gibt alle Mitglieder mit dem gewünschten Mandat zurück
	 * @param mandat das gewünschte Mandat
	 * @return die Liste mit den Mitgliedern
	 */
	public LinkedList<Kandidat> GetMitglieder(Mandat mandat) {
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
	 * @exception wenn der name leer ist
	 */
	public void setName(String name) {
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
	 * @exception wenn das Kürzel leer ist
	 */
	public void setKuerzel(String kuerzel) {
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
	 * @exception wenn die Farbe leer ist
	 */
	public void setFarbe(Color farbe) {
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
	 * Setzt das Zweitstimmen-Objekt
	 * @param zweitstimme 
	 * @exception wenn das Objekt leer ist
	 */
	public void setZweitstimme(LinkedList<Zweitstimme> zweitstimme) {
		if (zweitstimme == null) {
			throw new IllegalArgumentException("Zeitstimme-Objekt ist leer");
		}
		this.zweitstimme = zweitstimme;
	}
	
	/**
	 * Erzeugt eine tiefe Kopie dieses Objekts und gibt diese zurück.
	 * @return eine tiefe Kopie dieses Objekts
	 */
	@Override
	public Partei clone() {
		// TODO ... ;-)
		throw new UnsupportedOperationException("Noch nicht implementiert...");
	}
	
	
}

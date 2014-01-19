package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import wahlgenerator.RelevanteZweitstimmen;

/**
 * Diese Klasse repräsentiert eine Partei.
 */
public class Partei implements Serializable, Comparable<Partei> {

	/**
	 * Automatisch generierte serialVersionUID die für das De-/Serialisieren
	 * verwendet wird.
	 */
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
	private LinkedList<Zweitstimme> zweitstimme = null;

	/** Ist die Partei im Bundestag */
	private boolean imBundestag;

	/** Gesamte Anzahl an Zweitstimmen im Land */
	private int zweitstimmeGesamt;

	/** Die relevanten Zweistimmen der Partei */
	private RelevanteZweitstimmen relevanteZweitstimmen;
	
	private HashMap<Bundesland,Integer> mindestSitzanzahl = new HashMap<Bundesland,Integer>();

	/**
	 * Parametrisierter Konstruktor. Die Mitgliederliste und Landesliste wird
	 * hier erzeugt aber nicht befüllt.
	 * 
	 * @param name
	 *            Der Name dieser Partei.
	 * @param kuerzel
	 *            Das Kürzel dieser Partei.
	 * @param farbe
	 *            Farbe dieser Partei
	 */
	public Partei(String name, String kuerzel, Color farbe) {
		this.setName(name);
		this.setKuerzel(kuerzel);
		this.setFarbe(farbe);
		this.mitglieder = new LinkedList<Kandidat>();
		this.landesliste = new ArrayList<Landesliste>();
		this.zweitstimme = new LinkedList<Zweitstimme>();
	}

	/**
	 * Parametrisierter Konstruktor. Die Mitgliederliste wird hier nur erzeugt
	 * aber nicht befüllt.
	 * 
	 * @param landesliste
	 *            Die Liste aller Landeslisten dieser Partei.
	 * @param name
	 *            Der Name dieser Partei.
	 * @param kuerzel
	 *            Das Kürzel dieser Partei.
	 * @param farbe
	 *            Die Farbe dieser Partei.
	 */
	public Partei(List<Landesliste> landesliste, String name, String kuerzel,
			Color farbe) {
		this.setLandesliste(landesliste);
		this.setName(name);
		this.setKuerzel(kuerzel);
		this.setFarbe(farbe);
		this.mitglieder = new LinkedList<Kandidat>();
	}

	/**
	 * Gibt das Landesliste-Objekt zurück
	 * 
	 * @return das Landesliste-Objekt
	 */
	public List<Landesliste> getLandesliste() {
		return this.landesliste;
	}

	/**
	 * Setzt das neue Landesliste-Objekt
	 * 
	 * @param landesliste
	 *            das neue Objekt
	 * @throws IllegalArgumentException
	 *             wenn der Parameter landesliste null ist
	 */
	public void setLandesliste(List<Landesliste> landesliste)
			throws IllegalArgumentException {
		if (landesliste == null) {
			throw new IllegalArgumentException("Landesliste-Objekt ist null!");
		}
		this.landesliste = landesliste;
	}

	/**
	 * Fügt eine Landesliste zur Liste aller Landeslisten dieser Partei hinzu.
	 * 
	 * @param landesliste
	 *            Die Liste aller Landeslisten dieser Partei.
	 */
	public void addLandesliste(Landesliste landesliste) {
		this.landesliste.add(landesliste);
	}

	/**
	 * Gibt die Liste der Mitglieder zurück
	 * 
	 * @return Liste der Mitglieder
	 */
	public LinkedList<Kandidat> getMitglieder() {
		return mitglieder;
	}

	/**
	 * Setzt eine neue Liste als Mitgliederliste
	 * 
	 * @param mitglieder
	 *            der Partei
	 * @throws IllegalArgumentException
	 *             wenn der Parameter mitglieder null ist
	 */
	public void setMitglieder(LinkedList<Kandidat> mitglieder)
			throws IllegalArgumentException {
		if (mitglieder.equals(null)) {
			throw new IllegalArgumentException("Wahlkreissieger ist leer!");
		}
		this.mitglieder = mitglieder;
	}

	/**
	 * Fügt ein neues Mitglied zur Liste hinzu
	 * 
	 * @param mitglied
	 *            das neue Mitglied
	 * @throws IllegalArgumentException
	 *             wenn das Mitglied null ist
	 */
	public void addMitglied(Kandidat mitglied) throws IllegalArgumentException {
		if (mitglied == null) {
			throw new IllegalArgumentException("Mitglied ist null!");
		}
		this.mitglieder.add(mitglied);
	}

	/**
	 * Gibt alle Mitglieder mit dem gewünschten Mandat zurück
	 * 
	 * @param mandat
	 *            das gewünschte Mandat
	 * @return die Liste mit den Mitgliedern
	 */
	public LinkedList<Kandidat> getMitglieder(Mandat mandat) {
		LinkedList<Kandidat> res = new LinkedList<Kandidat>();
		for (Kandidat kandidat : this.mitglieder) {
			if (kandidat.getMandat() == mandat) {
				res.add(kandidat);
			}
		}
		return res;
	}

	/**
	 * Gibt den Namen der Partei zurück
	 * 
	 * @return den Namen der Partei
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den neuen Namen der Partei
	 * 
	 * @param name
	 *            der neue Name
	 * @throws IllegalArgumentException
	 *             wenn der Parameter name null ist.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name ist leer");
		}
		this.name = name;
	}

	/**
	 * Gibt das Kürzel der Partei zurück
	 * 
	 * @return das Kürzel der Partei
	 */
	public String getKuerzel() {
		return kuerzel;
	}

	/**
	 * Setzt das Kürzel der Partei
	 * 
	 * @param kuerzel
	 *            der Partei
	 * @throws IllegalArgumentException
	 *             wenn der Parameter kuerzel null ist.
	 */
	public void setKuerzel(String kuerzel) throws IllegalArgumentException {
		if (kuerzel == null || kuerzel.isEmpty()) {
			throw new IllegalArgumentException("Name ist leer");
		}
		this.kuerzel = kuerzel;
	}

	/**
	 * Gibt die Farbe der Partei zurück
	 * 
	 * @return die Farbe der Partei
	 */
	public Color getFarbe() {
		return farbe;
	}

	/**
	 * Setzt die Farbe der Partei
	 * 
	 * @param farbe
	 *            der Partei
	 * @throws IllegalArgumentException
	 *             wenn der Parameter farbe null ist.
	 */
	public void setFarbe(Color farbe) throws IllegalArgumentException {
		if (farbe == null) {
			throw new IllegalArgumentException("Farbe ist leer");
		}
		this.farbe = farbe;
	}

	/**
	 * Gibt das Zweitstimmen-Objekt zurück
	 * 
	 * @return das Zweitstimmen-Objekt
	 */
	public LinkedList<Zweitstimme> getZweitstimme() {
		return zweitstimme;
	}

	/**
	 * Setzt die Liste aller Zweitstimmen pro Gebiet.
	 * 
	 * @param zweitstimme
	 *            Die Liste aller Zweitstimmen pro Gebiet.
	 * @throws IllegalArgumentException
	 *             wenn die Liste aller Zweitstimmen (pro Gebiet) null ist.
	 */
	public void setZweitstimme(LinkedList<Zweitstimme> zweitstimme)
			throws IllegalArgumentException {
		if (zweitstimme == null) {
			throw new IllegalArgumentException("Zeitstimme-Objekt ist leer");
		}
		this.zweitstimme = zweitstimme;
	}

	public void addZweitstimme(Zweitstimme zweitstimme) {
		if (zweitstimme == null) {
			throw new IllegalArgumentException("Zeitstimme-Objekt ist leer");
		}
		this.zweitstimme.add(zweitstimme);
	}

	/**
	 * Gibt die Anzahl an Direkmandate zurück
	 * 
	 * @return die Anzahl an Direktmandate in der Partei
	 */
	public int getAnzahlDirektmandate() {
		int res = 0;
		for (Kandidat kandidat : getMitglieder()) {
			if (kandidat.getMandat().equals(Mandat.DIREKMANDAT)) {
				res++;
			}
		}
		return res;
	}

	/**
	 * Gibt zurück ob die Partei im Bundestag
	 * 
	 * @return ob Partei im Bundestag ist
	 */
	public boolean isImBundestag() {
		return imBundestag;
	}

	/**
	 * Bestimmt ob die Partei im Bundestag ist oder nicht
	 * 
	 * @param imBundestag
	 *            ob PArtei im Bundestag ist oder nicht
	 */
	public void setImBundestag(boolean imBundestag) {
		this.imBundestag = imBundestag;
	}

	/**
	 * Gibt die Anzahl an Zweistimmen in Deutschland zurück
	 * 
	 * @return die Anzahl an Zweitstimmen in Deutschland
	 */
	public int getZweitstimmeGesamt() {
		zweitstimmeGesamt = 0;
		for (Zweitstimme zweit : zweitstimme) {
			zweitstimmeGesamt += zweit.getAnzahl();
		}
		return zweitstimmeGesamt;
	}

	@Override
	public int compareTo(Partei andere) {
		return this.getName().compareTo(andere.getName());
	}

	/**
	 * @return the relevanteZweitstimmen
	 */
	public RelevanteZweitstimmen getRelevanteZweitstimmen() {
		return relevanteZweitstimmen;
	}

	/**
	 * @param relevanteZweitstimmen
	 *            the relevanteZweitstimmen to set
	 */
	public void setRelevanteZweitstimmen(
			RelevanteZweitstimmen relevanteZweitstimmen) {
		if (relevanteZweitstimmen == null) {
			throw new IllegalArgumentException(
					"relevante Zweitstimmen war null.");
		} else {
			this.relevanteZweitstimmen = relevanteZweitstimmen;
		
		}

	}



	/**
	 * Gibt die Anzahl der Zweitstimmen der Partei in dem jeweiligen Gebiet
	 * 
	 * @param gebiet das Gebiet, zu dem dei Zweitstimmenanzahl gegeben werden soll
	 * @return die Anzahl der Zweitstimmen
	 */

	public int getZweitstimme(Wahlkreis gebiet) {
		int anzahlZweitstimmen = 0; 
		for (Zweitstimme z : zweitstimme) {
			if (z.getGebiet().equals(gebiet)) {
				anzahlZweitstimmen = z.getAnzahl();
			}
			
		}
		return anzahlZweitstimmen;

	}
	
	/**
	 * Gibt an, wie häufig der im Parameter gegebene Mandattyp in dem gegebenem Bundesland auftritt 
	 * @param m Mandattyp
	 * @param b Bundesland
	 * @return die Anzahl der Mandate eines gegebenen Typs in einem Gebiet
	 */
	public int getAnzahlMandate(Mandat m, Bundesland b) {
		if (m == null || b == null) {
			throw new IllegalArgumentException("Mandat oder Bundesland waren null.");
		} 
		int anzahlMandate = 0;
		for (Kandidat kandidat : getMitglieder()) {
			if (kandidat.getMandat().equals(m) && kandidat.getPartei().getLandesliste(b).equals(b)) {
				anzahlMandate++;
			}
		}
		return anzahlMandate;
	}
	
	/**
	 * Gibt die Landesliste zurück, die zu dem gegeneben Bundesland passt
	 * @param b das Bundesland, zu dem die Landesliste gesucht wird
	 * @return Landesliste
	 */
	public Landesliste getLandesliste(Bundesland b) {
		if (b == null) {
			throw new IllegalArgumentException("Bundesland war null.");
		}
		Landesliste gesuchteLandesliste = null;
		for (Landesliste l : this.landesliste) {
			if (l.getBundesland().equals(b)) {
				gesuchteLandesliste = l;
			}
		}
		return gesuchteLandesliste;
	}
	
	public void addMindestsitzanzahl(Bundesland bl, int mindestSitzanzahl){
		this.mindestSitzanzahl.put(bl, mindestSitzanzahl);
	}

	
	/**
	 * Gibt an, wie viele Mandate eine Partei insgesamt besitzt
	 * @return die Anzahl an Mandate
	 */
	public int getAnzahlMandate() {
		int anzahlMandate = 0;
		for (Kandidat kandidat : getMitglieder()) {
			if (!kandidat.getMandat().equals(Mandat.KEINMANDAT)) {
				anzahlMandate++;
			}
		}
		return anzahlMandate;
	}
}

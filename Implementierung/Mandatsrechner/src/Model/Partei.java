package Model;


import java.util.LinkedList;
/**
 * Klass die die Parteien repr�sentiert . 
 * @author Nick
 *
 */
public class Partei {
	/**
	 * Landesliste-Objekt
	 */
	private Landesliste landesliste;
	/**
	 * Mitglieder der Partei
	 */
	private LinkedList<Kandidat> mitglieder = new LinkedList<Kandidat>();

	/**
	 * Name der Partei
	 */
	private String name;
	/**
	 * K�rzel der Partei
	 */
	private String kuerzel;
	/**
	 * Farbe der Partei
	 */
	private Farbe farbe;
	
	/**
	 * Angepasster Konstruktor. Listen werden danach bef�llt
	 * @param landesliste
	 * @param name
	 * @param kuerzel
	 * @param farbe
	 */
	public Partei(Landesliste landesliste, String name, String kuerzel, Farbe farbe){
		this.setLandesliste(landesliste);
		this.setName(name);
		this.setKuerzel(kuerzel);
		this.setFarbe(farbe);
	}
	/**
	 * Gibt das Landesliste-Objekt zur�ck
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
		if(landesliste == null){
			throw new IllegalArgumentException("Landesliste-Objekt ist leer!");
		}
		this.landesliste = landesliste;
	}

	/**
	 * Gibt die Liste der Mitglieder zur�ck
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
		if(mitglieder.equals(null)){
			throw new IllegalArgumentException("Wahlkreissieger ist leer!");
		}
		this.mitglieder = mitglieder;
	}
	/**
	 * F�gt ein neues Mitglied zur Liste hinzu
	 * @param mitglied das neue Mitglied
	 * @exception wenn das Mitglied leer ist
	 */
	public void AddMitglied(Kandidat mitglied) {
		if(mitglied == null){
			throw new IllegalArgumentException("Mitglied ist leer!");
		}
		this.mitglieder.add(mitglied);
	}
	
	/**
	 * Gibt alle Mitglieder mit dem gew�nschten Mandat zur�ck
	 * @param mandat das gew�nschte Mandat
	 * @return die Liste mit den Mitgliedern
	 */
	public LinkedList<Kandidat> GetMitglieder(Mandat mandat){
		LinkedList<Kandidat> res = new LinkedList<Kandidat>();
		for(Kandidat kandidat: this.mitglieder){
			if(kandidat.getMandat() == mandat){
				res.add(kandidat);
			}
		}
		return res;
	}
	
	/**
	 * Gibt den Namen der Partei zur�ck
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
		if(name == null || name.isEmpty()){
			throw new IllegalArgumentException("Name ist leer");
		}
		this.name = name;
	}

	/**
	 * Gibt das K�rzel der Partei zur�ck
	 * @return das K�rzel der Partei
	 */
	public String getKuerzel() {
		return kuerzel;
	}

	/**
	 * Setzt das K�rzel der Partei
	 * @param kuerzel der Partei
	 * @exception wenn das K�rzel leer ist
	 */
	public void setKuerzel(String kuerzel) {
		if(kuerzel == null || kuerzel.isEmpty()){
			throw new IllegalArgumentException("Name ist leer");
		}
		this.kuerzel = kuerzel;
	}

	/**
	 * Gibt die Farbe der Partei zur�ck
	 * @return die Farbe der Partei
	 */
	public Farbe getFarbe() {
		return farbe;
	}

	/**
	 * Setzt die Farbe der Partei
	 * @param farbe der Partei
	 * @exception wenn die Farbe leer ist
	 */
	public void setFarbe(Farbe farbe) {
		if(farbe == null){
			throw new IllegalArgumentException("Farbe ist leer");
		}
		this.farbe = farbe;
	}
	
}

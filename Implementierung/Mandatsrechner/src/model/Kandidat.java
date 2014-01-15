package model;

/**
 * Klasse repräsentiert einen Kandidaten.
 * Dieser kann zur einer Partei gehöhren und ein Mandat besitzen. 
 */
public class Kandidat {
	
	private String name;
	
	private String vorname;
	
	private int geburtsjahr;
	
	/** Das Mandat des Kandidaten */
	private Mandat mandat;

	/** Die Partei des Kandidaten. Diese muss nicht unbedingt vorhanden sein. */
	private Partei partei;
	
	/** Das Erststimme-Objekt des Kandidaten */
	private Erststimme erststimme;
	
	/**
	 * Konstruktor der Klasse. Es können dadurch alle Attribute gleichzeitig gesetzt werden
	 * @param mandat 
	 * @param partei
	 * @param erststimme
	 */
	public Kandidat(String name, String vorname, int geburtsjahr, Mandat mandat, Partei partei, Erststimme erststimme){
		this.setInfo(name,vorname,geburtsjahr);
		this.setMandat(mandat);
		this.setErststimme(erststimme);
		this.setPartei(partei);
	}
	
	public Kandidat(String name, String vorname, int geburtsjahr, Mandat mandat, Partei partei){
		this.setInfo(name,vorname,geburtsjahr);
		this.setMandat(mandat);
		this.setPartei(partei);
	}
	
	public Kandidat(Mandat mandat, Partei partei, Erststimme erststimme){
		this.setMandat(mandat);
		this.setErststimme(erststimme);
		this.setPartei(partei);
	}
	
	/**
	 * Gibt das Mandat des Kandidaten zurück
	 * @return das Mandat des Kandidaten
	 */
	public Mandat getMandat() {
		return mandat;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getVorname(){
		return this.vorname;
	}
	
	public int getGeburtsjahr(){
		return this.geburtsjahr;
	}
	
	public void setInfo(String name, String vorname, int geburtsjahr){
		if(vorname.equals(null)|| name.equals(null)){
			throw new IllegalArgumentException("Kandidat hat keine Namen.");
		}
		this.name = name;
		this.vorname = vorname;
		this.geburtsjahr = geburtsjahr;
	}
	
	/**
	 * Setzt das Mandat des Kandidaten
	 * @param mandat des Kandidaten
	 * @exception wenn das Mandat leer ist
	 */
	public void setMandat(Mandat mandat) {
		if (mandat.equals(null)) {
		      throw new IllegalArgumentException("Mandat ist leer!");
		}
		this.mandat = mandat;
	}

	/**
	 * Gibt die Partei des Kandidaten zurück
	 * @return die Partei des Kandidaten
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Setzt die Partei des Kandidaten
	 * @param partei des Kandidaten
	 * @exception wenn die Partei leer ist
	 */
	public void setPartei(Partei partei) {
		/*Kandidat kann auch keiner Partei zugeordnet sein!
		 * if (partei.equals(null)) {
		      throw new IllegalArgumentException("Partei ist leer!");
		}*/
		this.partei = partei;
	}

	/**
	 * Gibt das Erststimme-Objekt zurück
	 * @return das Erststimme-Objekt
	 */
	public Erststimme getErststimme() {
		return erststimme;
	}

	/**
	 * Setzt das Erstimme-Objekt
	 * @param erststimme das Objekt
	 * @exception wenn das Erstimme-Objekt leer ist
	 */
	public void setErststimme(Erststimme erststimme) {
		if (erststimme.equals(null)) {
		      throw new IllegalArgumentException("Erststimme ist leer!");
		}
		this.erststimme = erststimme;
	}
	
}

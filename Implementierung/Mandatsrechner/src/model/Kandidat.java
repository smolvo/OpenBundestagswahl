package model;

import java.io.Serializable;

/**
 * Klasse repr�sentiert einen Kandidaten.
 * Dieser kann zur einer Partei geh�hren und ein Mandat besitzen. 
 */
public class Kandidat implements Serializable {
	
	/** Automatisch generierte serialVersionUID die f�r das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -1812822920360318561L;

	/** Der Familienname des Kandidaten. */
	private String name;
	
	/** Der Vorname des Kandidaten. */
	private String vorname;
	
	/** Das Geburtsjahr des Kandidaten. */
	private int geburtsjahr;
	
	/** Das Mandat des Kandidaten. */
	private Mandat mandat;

	/** Die Partei des Kandidaten. Diese muss nicht unbedingt vorhanden sein. */
	private Partei partei;
	
	/** Das Erststimme-Objekt des Kandidaten. */
	private Erststimme erststimme;
	
	
	/**
	 * Parametrisierter Konstruktor mit dem alle Attribute gesetzt werden k�nnen.
	 * 
	 * @param name Der Familienname des Kandidaten.
	 * @param vorname Der Vorname des Kandidaten.
	 * @param geburtsjahr Das Geburtsjahr des Kandidaten.
	 * @param mandat Das Mandat des Kandidaten.
	 * @param partei Die Partei des Kandidaten.
	 * @param erststimme Das Erststimme-Objekt des Kandidaten.
	 */
	public Kandidat(String name, String vorname, int geburtsjahr, Mandat mandat, Partei partei, Erststimme erststimme) {
		this.setInfo(name, vorname, geburtsjahr);
		this.setMandat(mandat);
		this.setErststimme(erststimme);
		this.setPartei(partei);
	}
	
	/**
	 * Parametrisierter Konstruktor mit dem alle Attribute
	 * bis auf das Erstimmen-Objekt gesetzt werden k�nnen.
	 * 
	 * @param name Der Familienname des Kandidaten.
	 * @param vorname Der Vorname des Kandidaten.
	 * @param geburtsjahr Das Geburtsjahr des Kandidaten.
	 * @param mandat Das Mandat des Kandidaten.
	 * @param partei Die Partei des Kandidaten.
	 */
	public Kandidat(String name, String vorname, int geburtsjahr, Mandat mandat, Partei partei) {
		this.setInfo(name, vorname, geburtsjahr);
		this.setMandat(mandat);
		this.setPartei(partei);
	}
	
	/**
	 * Parametrisierter Konstruktor mit dem die Attribute mandat, partei und erstimme
	 * gesetzt werden k�nnen.
	 * 
	 * @param mandat Das Mandat des Kandidaten.
	 * @param partei Die Partei des Kandidaten.
	 * @param erststimme Das Erststimme-Objekt des Kandidaten.
	 */
	public Kandidat(Mandat mandat, Partei partei, Erststimme erststimme) {
		this.setMandat(mandat);
		this.setErststimme(erststimme);
		this.setPartei(partei);
	}
	
	/**
	 * Gibt das Mandat des Kandidaten zur�ck
	 * @return das Mandat des Kandidaten
	 */
	public Mandat getMandat() {
		return mandat;
	}
	
	/**
	 * Gibt den Familiennamen des Kandidaten zur�ck.
	 * @return den Familiennamen des Kandidaten
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gibt den Vornamen des Kandidaten zur�ck.
	 * @return den Vornamen des Kandidaten
	 */
	public String getVorname() {
		return this.vorname;
	}
	
	/**
	 * Gibt das Geburtsjahr des Kandidaten zur�ck.
	 * @return das Geburtsjahr des Kandidaten
	 */
	public int getGeburtsjahr() {
		return this.geburtsjahr;
	}
	
	/**
	 * Setzt Familienname, Vorname und Geburtsjahr des Kandidaten.
	 * @param name der Familiennamen des Kandidaten
	 * @param vorname der Vorname des Kandidaten
	 * @param geburtsjahr das Geburtsjahr des Kandidaten
	 * @throws IllegalArgumentException wenn name oder vorname null ist
	 */
	public void setInfo(String name, String vorname, int geburtsjahr) throws IllegalArgumentException {
		if (vorname.equals(null) || name.equals(null)) {
			throw new IllegalArgumentException("Kandidat hat keine Namen.");
		}
		this.name = name;
		this.vorname = vorname;
		this.geburtsjahr = geburtsjahr;
	}
	
	/**
	 * Setzt das Mandat des Kandidaten
	 * @param mandat des Kandidaten
	 * @throws IllegalArgumentException wenn das Mandat leer ist
	 */
	public void setMandat(Mandat mandat) throws IllegalArgumentException {
		if (mandat.equals(null)) {
		      throw new IllegalArgumentException("Mandat ist null!");
		}
		this.mandat = mandat;
	}

	/**
	 * Gibt die Partei des Kandidaten zur�ck
	 * @return die Partei des Kandidaten
	 */
	public Partei getPartei() {
		return partei;
	}

	/**
	 * Setzt die Partei des Kandidaten
	 * @param partei des Kandidaten
	 */
	public void setPartei(Partei partei) {
		/*Kandidat kann auch keiner Partei zugeordnet sein!
		 * if (partei.equals(null)) {
		      throw new IllegalArgumentException("Partei ist leer!");
		}*/
		this.partei = partei;
	}

	/**
	 * Gibt das Erststimme-Objekt zur�ck
	 * @return das Erststimme-Objekt
	 */
	public Erststimme getErststimme() {
		return erststimme;
	}

	/**
	 * Setzt das Erstimme-Objekt
	 * @param erststimme das Objekt
	 * @throws IllegalArgumentException wenn das Erstimme-Objekt leer ist
	 */
	public void setErststimme(Erststimme erststimme) {
		if (erststimme.equals(null)) {
		      throw new IllegalArgumentException("Erststimme ist leer!");
		}
		this.erststimme = erststimme;
	}
	
}

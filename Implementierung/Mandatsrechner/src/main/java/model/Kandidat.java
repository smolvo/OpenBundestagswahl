package main.java.model;

import java.io.Serializable;

/**
 * Klasse repraesentiert einen Kandidaten. Dieser kann zur einer Partei
 * gehoehren und ein Mandat besitzen.
 */
public class Kandidat implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
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

	/** Zugehoerige Landesliste. */
	private Landesliste landesliste;

	/**
	 * Parametrisierter Konstruktor mit dem alle Attribute gesetzt werden
	 * koennen.
	 * 
	 * @param name
	 *            Der Familienname des Kandidaten.
	 * @param vorname
	 *            Der Vorname des Kandidaten.
	 * @param geburtsjahr
	 *            Das Geburtsjahr des Kandidaten.
	 * @param mandat
	 *            Das Mandat des Kandidaten.
	 * @param partei
	 *            Die Partei des Kandidaten.
	 * @param erststimme
	 *            Das Erststimme-Objekt des Kandidaten.
	 */
	public Kandidat(String name, String vorname, int geburtsjahr,
			Mandat mandat, Partei partei, Erststimme erststimme) {
		this.setInfo(name, vorname, geburtsjahr);
		this.setMandat(mandat);
		this.setErststimme(erststimme);
		this.setPartei(partei);
	}

	/**
	 * Parametrisierter Konstruktor mit dem alle Attribute bis auf das
	 * Erstimmen-Objekt gesetzt werden koennen.
	 * 
	 * @param name
	 *            Der Familienname des Kandidaten.
	 * @param vorname
	 *            Der Vorname des Kandidaten.
	 * @param geburtsjahr
	 *            Das Geburtsjahr des Kandidaten.
	 * @param mandat
	 *            Das Mandat des Kandidaten.
	 * @param partei
	 *            Die Partei des Kandidaten.
	 */
	public Kandidat(String name, String vorname, int geburtsjahr,
			Mandat mandat, Partei partei) {
		this.setInfo(name, vorname, geburtsjahr);
		this.setMandat(mandat);
		this.setPartei(partei);
	}

	/**
	 * Parametrisierter Konstruktor mit dem die Attribute mandat, partei und
	 * erstimme gesetzt werden koennen.
	 * 
	 * @param mandat
	 *            Das Mandat des Kandidaten.
	 * @param partei
	 *            Die Partei des Kandidaten.
	 * @param erststimme
	 *            Das Erststimme-Objekt des Kandidaten.
	 */
	public Kandidat(Mandat mandat, Partei partei, Erststimme erststimme) {
		this.setMandat(mandat);
		this.setErststimme(erststimme);
		this.setPartei(partei);
	}

	/**
	 * Gibt das Mandat des Kandidaten zurueck.
	 * 
	 * @return das Mandat des Kandidaten.
	 */
	public Mandat getMandat() {
		return this.mandat;
	}

	/**
	 * Gibt den Familiennamen des Kandidaten zurueck.
	 * 
	 * @return den Familiennamen des Kandidaten.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt den Vornamen des Kandidaten zurueck.
	 * 
	 * @return den Vornamen des Kandidaten.
	 */
	public String getVorname() {
		return this.vorname;
	}

	/**
	 * Gibt das Geburtsjahr des Kandidaten zurueck.
	 * 
	 * @return das Geburtsjahr des Kandidaten.
	 */
	public int getGeburtsjahr() {
		return this.geburtsjahr;
	}

	/**
	 * Setzt Familienname, Vorname und Geburtsjahr des Kandidaten.
	 * 
	 * @param name
	 *            der Familiennamen des Kandidaten.
	 * @param vorname
	 *            der Vorname des Kandidaten.
	 * @param geburtsjahr
	 *            das Geburtsjahr des Kandidaten.
	 * @throws IllegalArgumentException
	 *             wenn name oder vorname null ist.
	 */
	public void setInfo(String name, String vorname, int geburtsjahr)
			throws IllegalArgumentException {
		if (vorname == null || name == null || vorname.isEmpty() || name.isEmpty()) {
			throw new IllegalArgumentException("Der Parameter \"vorname\" und/oder \"name\" ist null oder ein leerer String!");
		}
		this.name = name;
		this.vorname = vorname;
		this.geburtsjahr = geburtsjahr;
	}

	/**
	 * Setzt das Mandat des Kandidaten.
	 * 
	 * @param mandat
	 *            des Kandidaten.
	 * @throws IllegalArgumentException
	 *             wenn das Mandat leer ist.
	 */
	public void setMandat(Mandat mandat) throws IllegalArgumentException {
		if (mandat == null) {
			throw new IllegalArgumentException("Mandat ist null!");
		}
		this.mandat = mandat;
	}

	/**
	 * Gibt die Partei des Kandidaten zurueck.
	 * 
	 * @return die Partei des Kandidaten.
	 */
	public Partei getPartei() {
		return this.partei;
	}

	/**
	 * Setzt die Partei des Kandidaten.
	 * 
	 * @param partei
	 *            des Kandidaten.
	 */
	public void setPartei(Partei partei) {
		/*
		 * Kandidat kann auch keiner Partei zugeordnet sein!
		 */
		this.partei = partei;
	}

	/**
	 * Gibt das Erststimme-Objekt zurueck.
	 * 
	 * @return das Erststimme-Objekt.
	 */
	public Erststimme getErststimme() {
		return this.erststimme;
	}

	/**
	 * Setzt das Erstimme-Objekt.
	 * 
	 * @param erststimme
	 *            das Objekt.
	 * @throws IllegalArgumentException
	 *             wenn das Erstimme-Objekt leer ist.
	 */
	public void setErststimme(Erststimme erststimme) {
		if (erststimme == null) {
			throw new IllegalArgumentException("Der Parameter \"erststimme\" ist null!");
		}
		this.erststimme = erststimme;
	}

	/**
	 * Gibt die Landesliste dieses Kandidaten zurueck.
	 * 
	 * @return Zugehoerige LAndesliste.
	 */
	public Landesliste getLandesliste() {
		return landesliste;
	}

	/**
	 * Setzt die Landesliste dieses Kandidaten.
	 * 
	 * @param landestliste
	 *            Betroffene Landesliste.
	 */
	public void setLandesliste(Landesliste landestliste) {
		this.landesliste = landestliste;
	}
	
	/**
	 * Equals-Methode der Klasse.
	 * @param kan vergleichskandidat
	 * @return true false
	 */
	public boolean equals(Kandidat kan) {
		if ((kan.getName().equals(this.name))
				&& (kan.getVorname().equals(this.vorname))
				&& (kan.getGeburtsjahr() == this.geburtsjahr)
				&& (kan.getPartei().equals(this.partei))) {
			return true;
		}
		return false;
	}
}

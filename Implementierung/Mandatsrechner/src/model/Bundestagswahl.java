package model;

import java.util.LinkedList;

/**
 * Diese Klasse repräsentiert eine Bundestagswahl.
 */
public class Bundestagswahl implements Cloneable {
	
	/** Der Name dieser Bundestagswahl. */
	private String name;
	
	/** Das Deutschland-Objekt, welches alle Bundesländer und Wahlkreise enthält. */
	private Deutschland deutschland;
	
	/** Eine Liste aller Parteien die an dieser Bundestagswahl teilnehmen. */
	private LinkedList<Partei> parteien;
	
	
	/**
	 * Parametrisierter Konstruktor für Bundestagswahlen.
	 */
	public Bundestagswahl(String name, Deutschland deutschland, LinkedList<Partei> parteien) {
		if (name == null)
			throw new IllegalArgumentException("Parameter 'name' ist NULL!");
		if (deutschland == null)
			throw new IllegalArgumentException("Parameter 'deutschland' ist NULL!");
		if (parteien == null)
			throw new IllegalArgumentException("Parameter 'parteien' ist NULL!");
		this.name = name;
		this.deutschland = deutschland;
		this.parteien = parteien;
	}
	
	/** Gibt den Namen dieser Bundestagswahl. */
	public String getName() {
		return name;
	}

	/** Setzt den Namen dieser Bundestagswahl. */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Setzt das Deutschland-Objekt dieser Bundestagswahl. */
	public Deutschland getDeutschland() {
		return deutschland;
	}

	/** Gibt das Deutschland-Objekt dieser Bundestagswahl. */
	public void setDeutschland(Deutschland deutschland) {
		this.deutschland = deutschland;
	}
	
	/** Gibt die Liste der Parteien dieser Bundestagswahl. */
	public LinkedList<Partei> getParteien() {
		return parteien;
	}

	/** Setzt die Liste der Parteien dieser Bundestagswahl. */
	public void setParteien(LinkedList<Partei> parteien) {
		this.parteien = parteien;
	}

	/**
	 * Erzeugt eine tiefe Kopie dieses Objekts und gibt es zurück.
	 * @return eine tiefe Kopie dieses Objekts
	 */
	@Override
	public Bundestagswahl clone() {
		throw new UnsupportedOperationException("Noch nicht implementiert...");
	}
	
}

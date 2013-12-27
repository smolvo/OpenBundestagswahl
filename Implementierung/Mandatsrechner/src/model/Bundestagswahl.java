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
	
	/** Die berechnete Sitzverteilung dieser Bundestagswahl. */
	private Sitzverteilung sitzverteilung;
	
	
	/**
	 * Parametrisierter Konstruktor für Bundestagswahlen.
	 * @param name
	 * @param deutschland
	 * @param parteien
	 */
	public Bundestagswahl(String name, Deutschland deutschland, LinkedList<Partei> parteien) {
		this.setName(name);
		this.setDeutschland(deutschland);
		this.setParteien(parteien);
		// Sitzverteilung wird hier nicht gesetzt sondern muss vom Mandatsrechner berechnet werden.
	}
	
	/**
	 * Gibt den Namen dieser Bundestagswahl.
	 * @return den Namen dieser Bundestagswahl.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen dieser Bundestagswahl.
	 * @param name der Name dieser Bundestagswahl
	 */
	public void setName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Parameter 'name' ist null!");
		this.name = name;
	}
	
	/**
	 * Gibt das Deutschland-Objekt dieser Bundestagswahl.
	 * @return das Deutschland-Objekt dieser Bundestagswahl.
	 */
	public Deutschland getDeutschland() {
		return deutschland;
	}

	/**
	 * Setzt das Deutschland-Objekt dieser Bundestagswahl.
	 * @param deutschland das Deutschland-Objekt dieser Bundestagswahl
	 */
	public void setDeutschland(Deutschland deutschland) {
		if (deutschland == null)
			throw new IllegalArgumentException("Parameter 'deutschland' ist null!");
		this.deutschland = deutschland;
	}
	
	/**
	 * Gibt die Liste der Parteien dieser Bundestagswahl.
	 * @return die Liste der Parteien dieser Bundestagswahl.
	 */
	public LinkedList<Partei> getParteien() {
		return parteien;
	}

	/**
	 * Setzt die Liste der Parteien dieser Bundestagswahl.
	 * @param parteien die Liste der Parteien dieser Bundestagswahl.
	 */
	public void setParteien(LinkedList<Partei> parteien) {
		if (parteien == null)
			throw new IllegalArgumentException("Parameter 'parteien' ist null!");
		this.parteien = parteien;
	}

	/**
	 * Gibt die berechnete Sitzverteilung dieser Bundestagswahl.
	 * @return die berechnete Sitzverteilung dieser Bundestagswahl
	 */
	public Sitzverteilung getSitzverteilung() {
		return sitzverteilung;
	}

	/**
	 * Setzt die berechnete Sitzverteilung dieser Bundestagswahl.
	 * @param sitzverteilung die berechnete Sitzverteilung dieser Bundestagswahl
	 */
	public void setSitzverteilung(Sitzverteilung sitzverteilung) {
		this.sitzverteilung = sitzverteilung;
	}

	/**
	 * Erzeugt eine tiefe Kopie dieses Objekts und gibt diese zurück.
	 * @return eine tiefe Kopie dieses Objekts
	 */
	@Override
	public Bundestagswahl clone() {
		// TODO ... ;-)
		throw new UnsupportedOperationException("Noch nicht implementiert...");
	}
	
}

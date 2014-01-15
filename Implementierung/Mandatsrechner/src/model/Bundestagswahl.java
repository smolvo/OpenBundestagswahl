package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Diese Klasse repr�sentiert eine Bundestagswahl.
 */
public class Bundestagswahl implements Serializable {
	
	/** Automatisch generierte serialVersionUID die f�r das De-/Serialisieren verwendet wird. */
	private static final long serialVersionUID = -3148791539244130382L;

	/** Der Name dieser Bundestagswahl. */
	private String name;
	
	/** Das Deutschland-Objekt, welches alle Bundesl�nder und Wahlkreise enth�lt. */
	private Deutschland deutschland;
	
	/** Eine Liste aller Parteien die an dieser Bundestagswahl teilnehmen. */
	private LinkedList<Partei> parteien;
	
	/** Die berechnete Sitzverteilung dieser Bundestagswahl. */
	private Sitzverteilung sitzverteilung;
	
	
	/**
	 * Parametrisierter Konstruktor f�r Bundestagswahlen.
	 * @param name Der Name dieser Bundestagswahl.
	 * @param deutschland Das Deutschland-Objekt, welches alle Bundesl�nder und Wahlkreise enth�lt.
	 * @param parteien Eine Liste aller Parteien die an dieser Bundestagswahl teilnehmen.
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
		if (name == null) {
			throw new IllegalArgumentException("Parameter 'name' ist null!");
		}
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
		if (deutschland == null) {
			throw new IllegalArgumentException("Parameter 'deutschland' ist null!");
		}
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
		if (parteien == null) {
			throw new IllegalArgumentException("Parameter 'parteien' ist null!");
		}
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
	 * Erzeugt durch Serialisierung eine tiefe Kopie dieses Objekts und gibt diese zur�ck.
	 * @return eine tiefe Kopie dieses Objekts
	 * @throws IOException Beim Serialisieren oder Deserialisieren
	 * @throws ClassNotFoundException Falls die Klasse beim deserialisieren nicht gefunden wird
	 */
	public Bundestagswahl deepCopy() throws IOException, ClassNotFoundException {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			
			// serialisiere und �bergebe das Objekt
			oos.writeObject(this);
			oos.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bin);
			
			// gib das geklonte Objekt zur�ck
			return (Bundestagswahl) ois.readObject();			
		} catch (Exception e) {
			throw(e);
		} finally {
			oos.close();
			ois.close();
		}
	}
	
}

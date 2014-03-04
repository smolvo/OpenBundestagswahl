package main.java.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Klasse die mit der Deutschland-Klasse, der Kandidat-Klasse und der
 * Erststimmen-Klasse zusammenarbeitet. Sie erbt von der Oberklasse Gebiet.
 */
public class Wahlkreis extends Gebiet implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fï¿½r das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = 8492979454628956125L;

	/** Die Anzahl der Wahlberechtigten. */
	private int wahlberechtigte;

	/** Offiziell zugeordnete Nummer. */
	private int wahlkreisnummer;

	/** Kandidat mit den meisten Erststimmen. */
	private Kandidat wahlkreisSieger;

	/** Die Liste aller Erstimmen-Objekte (pro Partei). */
	private LinkedList<Erststimme> erststimmen;

	/** Die Liste aller Zweitstimmen-Objekte (pro Partei). */
	private LinkedList<Zweitstimme> zweitstimmen;

	/**
	 * Parametrisierter Konstruktor zum erzeugen von Wahlkreisen.
	 * 
	 * @param name
	 *            Der Name dieses Wahlkreises.
	 * @param wahlberechtigte
	 *            Die Anzahl der Wahlberechtigten.
	 * @throws IllegalArgumentException
	 *             wenn die Eingabeparameter null sind.
	 */
	public Wahlkreis(String name, int wahlberechtigte) {
		if (name == null || wahlberechtigte < 0) {
			throw new IllegalArgumentException("Eingabeparameter sind null");
		}
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
		this.erststimmen = new LinkedList<>();
		this.zweitstimmen = new LinkedList<>();
	}

	/**
	 * Parametrisierter Konstruktor zum erzeugen von Wahlkreisen.
	 * 
	 * @param name
	 *            Der Name dieses Wahlkreises.
	 * @param wahlberechtigte
	 *            Die Anzahl der Wahlberechtigten.
	 * @param erststimmen
	 *            Die Liste aller Erstimmen-Objekte (pro Partei).
	 * @throws IllegalArgumentException
	 *             wenn die Eingabeparameter null sind.
	 */
	public Wahlkreis(String name, int wahlberechtigte,
			LinkedList<Erststimme> erststimmen) {
		if (name == null || wahlberechtigte < 0 || erststimmen == null) {
			throw new IllegalArgumentException("Eingabeparameter sind null");
		}
		this.setName(name);
		this.setWahlberechtigte(wahlberechtigte);
		this.setErststimmen(erststimmen);
		this.zweitstimmen = new LinkedList<>();
	}

	/**
	 * Gibt das Erststimme-Objekt das sowohl zu diesem Wahlkreis, als auch zur
	 * gegebenen Partei zugehï¿½rig ist zurï¿½ck.
	 * 
	 * @param partei
	 *            die zugehï¿½rige Partei zur erwarteten Erststimme
	 * @return das Erststimme-Objekt das sowohl zu diesem Wahlkreis, als auch
	 *         zur gegebenen Partei zugehï¿½rig ist
	 */
	public Erststimme getErststimme(Partei partei) {
		Erststimme ergebnis = null;
		for (Erststimme erst : this.erststimmen) {
			if (erst.getKandidat().getPartei() != null
					&& erst.getKandidat().getPartei().equals(partei)) {
				ergebnis = erst;
			}
		}
		return ergebnis;
	}

	/**
	 * Gibt den Kandidaten mit den meisten Erststimmen zurueck.
	 * 
	 * @return Kandidat mit den meisten Erststimmen.
	 */
	public Kandidat getWahlkreisSieger() {
		return this.wahlkreisSieger;
	}

	/**
	 * Setzt den Wahlkreissieger.
	 * 
	 * @param wahlkreisSieger
	 *            der Kandidat mit den meisten Stimmen.
	 * @throws IllegalArgumentException
	 *             wenn der Wahlkreissieger leer ist.
	 */
	public void setWahlkreisSieger(Kandidat wahlkreisSieger)
			throws IllegalArgumentException {
		if (wahlkreisSieger == null) {
			throw new IllegalArgumentException("Wahlkreissieger ist leer!");
		}
		this.wahlkreisSieger = wahlkreisSieger;
	}

	/**
	 * Gibt das Erststimme-Objekt zurueck.
	 * 
	 * @return alle Erststimmen des Wahlkreises
	 */
	
	public LinkedList<Erststimme> getErststimmenProPartei() {
		return this.erststimmen;
	}

	/**
	 * Gibt das Zweitstimme-Objekt zurueck
	 * 
	 * @return alle Zweitstimmen des Wahlkreises
	 */
	
	public LinkedList<Zweitstimme> getZweitstimmenProPartei() {
		return this.zweitstimmen;
	}

	/**
	 * Setzt die Liste aller Erstimmen-Objekte (pro Partei).
	 * 
	 * @param erststimmen
	 *            Die Liste aller Erstimmen-Objekte (pro Partei).
	 * @throws IllegalArgumentException
	 *             wenn die Liste aller Erstimmen-Objekte null ist.
	 */
	public void setErststimmen(LinkedList<Erststimme> erststimmen)
			throws IllegalArgumentException {
		if (erststimmen == null) {
			throw new IllegalArgumentException(
					"Parameter \"erststimmen\" ist null!");
		}
		this.erststimmen = erststimmen;
	}

	/**
	 * Setzt die Liste aller Zweitstimmen-Objekte (pro Partei).
	 * 
	 * @param zweitstimmen
	 *            Die Liste aller Zweitstimmen-Objekte (pro Partei).
	 * @throws IllegalArgumentException
	 *             wenn die Liste aller Zweitstimmen-Objekte null ist.
	 */
	public void setZweitstimmen(LinkedList<Zweitstimme> zweitstimmen)
			throws IllegalArgumentException {
		if (zweitstimmen == null) {
			throw new IllegalArgumentException(
					"Parameter \"zweitstimmen\" ist null!");
		}
		this.zweitstimmen = zweitstimmen;
	}

	@Override
	public int getWahlberechtigte() {
		return this.wahlberechtigte;
	}

	/**
	 * Diese Methode setzt die Anzahl Wahlberechtigter.
	 * 
	 * @param wahlberechtigte
	 *            Wahlberechtigte
	 * @throws IllegalArgumentException
	 */
	public void setWahlberechtigte(int wahlberechtigte) {
		if (wahlberechtigte < 0) {
			throw new IllegalArgumentException(
					"Der Parameter \"wahlberechtigte\" ist negativ!");
		}
		this.wahlberechtigte = wahlberechtigte;
	}

	/**
	 * Gibt die Wahlkreisnummer zurueck.
	 * 
	 * @return die Wahlkreisnummer
	 */
	public int getWahlkreisnummer() {
		return wahlkreisnummer;
	}

	/**
	 * Setzt die Wahlkreisnummer.
	 * 
	 * @param wahlkreisnummer
	 *            Offiziell zugeordnete Nummer dieses Wahlkreises.
	 * @throws IllegalArgumentException
	 *             wenn der Parameter \"wahlkreisnummer\" ist negativ ist.
	 */
	public void setWahlkreisnummer(int wahlkreisnummer)
			throws IllegalArgumentException {
		if (wahlkreisnummer < 0) {
			throw new IllegalArgumentException(
					"Der Parameter \"wahlkreisnummer\" ist negativ!");
		}
		this.wahlkreisnummer = wahlkreisnummer;
	}

	/**
	 * Gibt das Zweitstimme-Objekt der gegebenen Partei zurï¿½ck und null, wenn
	 * kein solches Objekt mit der gegebenen Partei existiert.
	 * 
	 * @param partei
	 *            die Partei deren Zweitstimme Objekt gesucht werden soll
	 * @return das Zweitstimme-Objekt der gegebenen Partei zurï¿½ck und null, wenn
	 *         kein solches Objekt mit der gegebenen Partei existiert.
	 * @throws IllegalArgumentException
	 *             wenn die Partei null ist.
	 */
	public Zweitstimme getZweitstimme(Partei partei) {
		if (partei == null) {
			throw new IllegalArgumentException("Partei ist null");
		}
		for (Zweitstimme zweitStimme : this.getZweitstimmenProPartei()) {
			if (zweitStimme.getPartei().equals(partei)) {
				return zweitStimme;
			}
		}
		return null;
	}

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * 
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	@Override
	public int getAnzahlZweitstimmen(Partei partei) {
		int anzahl = 0;
		for (Zweitstimme zweitStimme : this.zweitstimmen) {
			if (zweitStimme.getPartei().getName().equals(partei.getName())) {
				anzahl = zweitStimme.getAnzahl();
			}
		}
		return anzahl;
	}

	/**
	 * Gibt die anzahl der Zweitstimmen einer bestimmten Partei zurï¿½ck.
	 * @param partei
	 *            Die Partei zu der die Stimmen gegeben werden sollen.
	 * @return Die anzahl der Zweitstimmen einer bestimmten Partei.
	 */
	@Override
	public int getAnzahlErststimmen(Partei partei) {
		int anzahl = 0;
		for (Erststimme erststimme : this.getErststimmenProPartei()) {
			if (partei.getName().equals(
					erststimme.getKandidat().getPartei().getName())) {
				anzahl = erststimme.getAnzahl();
			}
		}
		return anzahl;
	}

	/**
	 * Gibt die Erststimmenanzahl aller Parteien im Wahlkreis.
	 * 
	 * @return die Erststimmenanzahl aller Partein.
	 */
	@Override
	public int getAnzahlErststimmen() {
		int erststimmeGesamt = 0;
		for (Erststimme erst : getErststimmenProPartei()) {
			erststimmeGesamt += erst.getAnzahl();
		}
		return erststimmeGesamt;
	}
	
	/**
	 * Gibt die Zweitstimmenanzahl aller Parteien im Wahlkreis.
	 * 
	 * @return die Zweitstimmenanzahl aller Partein.
	 */
	@Override
	public int getAnzahlZweitstimmen() {
		int anzahl = 0;
		for (Zweitstimme zweitstimme : this.getZweitstimmenProPartei()) {
			anzahl += zweitstimme.getAnzahl();
		}
		return anzahl;
	}

}

package model;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import chronik.Chronik;
import test.Debug;
/**
 * Diese Klasse repraesentiert eine Bundestagswahl.
 */
public class Bundestagswahl implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -3148791539244130382L;

	/** Der Name dieser Bundestagswahl. */
	private String name;

	/**
	 * Das Deutschland-Objekt, welches alle Bundeslaender und Wahlkreise
	 * enthaelt.
	 */
	private Deutschland deutschland;

	/** Eine Liste aller Parteien die an dieser Bundestagswahl teilnehmen. */
	private LinkedList<Partei> parteien;

	/** Die berechnete Sitzverteilung dieser Bundestagswahl. */
	private Sitzverteilung sitzverteilung;
	
	/** Chronik-Container */
	private Chronik chronik;

	/**
	 * Parametrisierter Konstruktor fuer Bundestagswahlen.
	 * 
	 * @param name
	 *            Der Name dieser Bundestagswahl.
	 * @param deutschland
	 *            Das Deutschland-Objekt, welches alle Bundeslï¿½nder und
	 *            Wahlkreise enthaelt.
	 * @param parteien
	 *            Eine Liste aller Parteien die an dieser Bundestagswahl
	 *            teilnehmen.
	 */
	public Bundestagswahl(String name, Deutschland deutschland,
			LinkedList<Partei> parteien) {
		this.setName(name);
		this.setDeutschland(deutschland);
		this.setParteien(parteien);
		// Sitzverteilung wird hier nicht gesetzt sondern muss vom
		// Mandatsrechner berechnet werden.
		
		this.chronik = new Chronik();
	}

	/**
	 * Gibt den Namen dieser Bundestagswahl.
	 * 
	 * @return den Namen dieser Bundestagswahl.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setzt den Namen dieser Bundestagswahl.
	 * 
	 * @param name
	 *            der Name dieser Bundestagswahl.
	 */
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Parameter 'name' ist null!");
		}
		this.name = name;
	}

	/**
	 * Gibt das Deutschland-Objekt dieser Bundestagswahl.
	 * 
	 * @return das Deutschland-Objekt dieser Bundestagswahl.
	 */
	public Deutschland getDeutschland() {
		return this.deutschland;
	}

	/**
	 * Setzt das Deutschland-Objekt dieser Bundestagswahl.
	 * 
	 * @param deutschland
	 *            das Deutschland-Objekt dieser Bundestagswahl.
	 * @throws IllegalArgumentException
	 *             wenn das Deutschland-Objekt null ist.
	 */
	public void setDeutschland(Deutschland deutschland) {
		if (deutschland == null) {
			throw new IllegalArgumentException(
					"Parameter 'deutschland' ist null!");
		}
		this.deutschland = deutschland;
	}

	/**
	 * Gibt die Liste der Parteien dieser Bundestagswahl.
	 * 
	 * @return die Liste der Parteien dieser Bundestagswahl.
	 * 
	 */
	public LinkedList<Partei> getParteien() {
		return this.parteien;
	}

	/**
	 * Setzt die Liste der Parteien dieser Bundestagswahl.
	 * 
	 * @param parteien
	 *            die Liste der Parteien dieser Bundestagswahl.
	 * @throws IllegalArgumentException
	 *             wenn die Liste null ist.
	 */
	public void setParteien(LinkedList<Partei> parteien) {
		if (parteien == null) {
			throw new IllegalArgumentException("Parameter 'parteien' ist null!");
		}
		this.parteien = parteien;
	}

	/**
	 * Gibt die berechnete Sitzverteilung dieser Bundestagswahl.
	 * 
	 * @return die berechnete Sitzverteilung dieser Bundestagswahl.
	 */
	public Sitzverteilung getSitzverteilung() {
		return this.sitzverteilung;
	}

	/**
	 * Setzt die berechnete Sitzverteilung dieser Bundestagswahl.
	 * 
	 * @param sitzverteilung
	 *            die berechnete Sitzverteilung dieser Bundestagswahl.
	 */
	public void setSitzverteilung(Sitzverteilung sitzverteilung) {
		this.sitzverteilung = sitzverteilung;
	}

	/**
	 * Erzeugt durch Serialisierung eine tiefe Kopie dieses Objekts und gibt
	 * diese zurueck.
	 * 
	 * @return eine tiefe Kopie dieses Objekts.
	 * @throws IOException
	 *             Beim Serialisieren oder Deserialisieren.
	 */
	public Bundestagswahl deepCopy() throws IOException {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		Bundestagswahl result = null;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);

			// serialisiere und ï¿½bergebe das Objekt
			oos.writeObject(this);
			oos.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(
					bos.toByteArray());
			ois = new ObjectInputStream(bin);

			// gib das geklonte Objekt zurï¿½ck
			result = (Bundestagswahl) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			oos.close();
			ois.close();
		}

		return result;
	}
	
	/**
	 * Verändere die Stimmen in einer Bundestagswahl.
	 * Speichert vorher die aktuelle Bundestagswahl in der Chronik.
	 * @param stimme
	 * 		die zu verändernde stimme.
	 * @return 
	 * 		true wenn erfolgreich.
	 */
	public boolean setzeStimme(Stimme stimme) {
		// TODO
		boolean success = true;
		try {
			this.chronik.sichereBundestagswahl(this.deepCopy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}
		if (stimme instanceof Erststimme) {
			success = this.setzeStimmenAnzahl((Erststimme) stimme);
		} else if (stimme instanceof Zweitstimme) {
			success = this.setzeStimmenAnzahl((Zweitstimme) stimme);
		} else {
			success = false;
		}
		
		if (!success /*&& !this.pruefeDaten*/) {
			// TODO
			Bundestagswahl revert = this.chronik.restauriereBundestagswahl();
			//this.setDeutschland(revert.deutschland);
			//this.setName(revert)
		}
		return success;
	}
	
	/**
	 * Erststimme kann/darf nur in Wahlkreisen veraendert
	 * werden.
	 * @param stimme
	 * 				
	 * @return
	 */
	private boolean setzeStimmenAnzahl(Erststimme stimme) {
		System.out.println("Setze erststimme");
		boolean success = false;
		for (Wahlkreis wk : this.deutschland.getWahlkreise()) {
			 if (wk.equals(stimme.getGebiet())) {
				 for (Erststimme erststimme : wk.getErststimmen()) {
					 if (erststimme.getKandidat().equals(stimme.getKandidat())) {
						 erststimme.setAnzahl(stimme.getAnzahl());
						 success = true;
						 break;
					 }
				 }
				 break;
			 }
		}
		return success;
	}
	
	/**
	 * Setzt die ZweitstimmenAnzahl
	 * @param stimme 
	 * @return
	 **/
	private boolean setzeStimmenAnzahl (Zweitstimme stimme) {
		Debug.print("Setze zweitstimme");
		boolean success = false;
		if (stimme.getGebiet() instanceof Deutschland) {
			// TODO
		} else if (stimme.getGebiet() instanceof Bundesland) {
			// TODO
		} else if (stimme.getGebiet() instanceof Wahlkreis) {
			// TODO
		} else {
			success = false;
		}
		return success;
	}

	/**
	 * Diese Methode gibt die letzte Bundestagswahl aus.
	 * @return letzte Bundestagswahl
	 */
	public Bundestagswahl getAlteBTW() {
		return this.chronik.restauriereBundestagswahl();
	}

}

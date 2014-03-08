package main.java.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import main.java.chronik.Chronik;
import test.java.Debug;

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
	private final Chronik chronik;

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
		setName(name);
		setDeutschland(deutschland);
		setParteien(parteien);
		// Sitzverteilung wird hier nicht gesetzt sondern muss vom
		// Mandatsrechner berechnet werden.

		this.chronik = new Chronik();
	}

	/**
	 * Erzeugt durch Serialisierung eine tiefe Kopie dieses Objekts und gibt
	 * diese zurueck.
	 * 
	 * @return eine tiefe Kopie dieses Objekts.
	 */
	public Bundestagswahl deepCopy() {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		Bundestagswahl result = null;

		try {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);

			// serialisiere und übergebe das Objekt
			oos.writeObject(this);
			oos.flush();
			final ByteArrayInputStream bin = new ByteArrayInputStream(
					bos.toByteArray());
			ois = new ObjectInputStream(bin);

			// gib das geklonte Objekt zurück
			result = (Bundestagswahl) ois.readObject();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
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
	 * Gibt den Namen dieser Bundestagswahl.
	 * 
	 * @return den Namen dieser Bundestagswahl.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sucht nach einer Partei mit dem gegebenen Namen in dieser Bundestagswahl
	 * und gibt diese zurück.
	 * 
	 * Gibt null zurück wenn keine Partei mit exakt diesem Namen gefunden wurde.
	 * 
	 * @param name
	 *            der Name der Partei die gesucht wird
	 * @return eine Partei mit dem gegebenen Namen
	 */
	public Partei getParteiByName(String name) {
		Partei result = null;

		for (final Partei partei : getParteien()) {
			if (partei.getName().equals(name)) {
				result = partei;
				break;
			}
		}

		return result;
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
	 * Gibt die berechnete Sitzverteilung dieser Bundestagswahl.
	 * 
	 * @return die berechnete Sitzverteilung dieser Bundestagswahl.
	 */
	public Sitzverteilung getSitzverteilung() {
		return this.sitzverteilung;
	}

	/**
	 * Prueft, ob eine Stimme zum wiederherstellen vorhanden ist.
	 * 
	 * @return true wenn es vorhanden ist.
	 */
	public boolean hatStimmenZumWiederherstellen() {
		return this.chronik.hatStimmenZumWiederherstellen();
	}

	/**
	 * Prueft, ob eine Stimme zum zuruecksetzen vorhanden ist.
	 * 
	 * @return true wenn es vorhanden ist.
	 */
	public boolean hatStimmenZumZuruecksetzen() {
		return this.chronik.hatStimmenZumZuruecksetzen();
	}

	/**
	 * Setzt das Deutschland-Objekt dieser Bundestagswahl.
	 * 
	 * @param deutschland
	 *            das Deutschland-Objekt dieser Bundestagswahl.
	 * @throws IllegalArgumentException
	 *             wenn das Deutschland-Objekt null ist.
	 */
	public final void setDeutschland(Deutschland deutschland) {
		if (deutschland == null) {
			throw new IllegalArgumentException(
					"Parameter 'deutschland' ist null!");
		}
		this.deutschland = deutschland;
	}

	/**
	 * Setzt den Namen dieser Bundestagswahl.
	 * 
	 * @param name
	 *            der Name dieser Bundestagswahl.
	 */
	public final void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Parameter 'name' ist null!");
		}
		this.name = name;
	}

	/**
	 * Setzt die Liste der Parteien dieser Bundestagswahl.
	 * 
	 * @param parteien
	 *            die Liste der Parteien dieser Bundestagswahl.
	 * @throws IllegalArgumentException
	 *             wenn die Liste null ist.
	 */
	public final void setParteien(LinkedList<Partei> parteien) {
		if (parteien == null) {
			throw new IllegalArgumentException("Parameter 'parteien' ist null!");
		}
		this.parteien = parteien;
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
	 * Veraendere die Stimmen in einer Bundestagswahl. Speichert vorher die
	 * aktuelle Bundestagswahl in der Chronik.
	 * 
	 * @param stimme
	 *            die zu veraendernde stimme.
	 * @param chronik
	 *            bei true wird die veraenderte Stimme in der Chronik
	 *            aufgezeichnet.
	 * @return true wenn erfolgreich.
	 */
	public boolean setzeStimme(Stimme stimme, boolean chronik) {
		Stimme alteStimme = null;
		boolean success = false;
		if (stimme.isErststimme()) {
			alteStimme = this.setzeStimmenAnzahl((Erststimme) stimme);
		} else if (stimme instanceof Zweitstimme) {
			alteStimme = this.setzeStimmenAnzahl((Zweitstimme) stimme);
		}

		if (alteStimme == null) {
			// throw new IllegalArgumentException("Stimme nicht gefunden.");
			success = false;
		} else {
			if (chronik) {
				this.chronik.sichereStimme(alteStimme, stimme);
			}
			success = true;
		}
		return success;
	}

	/**
	 * Erststimme kann/darf nur in Wahlkreisen veraendert werden.
	 * 
	 * @param stimme
	 * 
	 * @return
	 */
	private Erststimme setzeStimmenAnzahl(Erststimme stimme) {
		Debug.print("Setze erststimme", 5);
		Erststimme alteErststimme = null;
		for (final Wahlkreis wk : this.deutschland.getWahlkreise()) {
			if (wk.equals(stimme.getGebiet())) {
				for (final Erststimme erststimme : wk.getErststimmenProPartei()) {
					if (erststimme.getKandidat().equals(stimme.getKandidat())) {
						Debug.print("Aendere Erststimme in: "
								+ stimme.getGebiet().getName() + " "
								+ stimme.getKandidat().getPartei().getName()
								+ " " + stimme.getAnzahl(), 4);

						// this.chronik.sichereStimme(erststimme);
						alteErststimme = (Erststimme) erststimme.deepCopy();
						erststimme.setAnzahl(stimme.getAnzahl());

						break;
					}
				}
				break;
			}
		}
		return alteErststimme;
	}

	/**
	 * Setzt die ZweitstimmenAnzahl
	 * 
	 * @param stimme
	 * @return die bisherige Zweitstimme.
	 **/
	private Zweitstimme setzeStimmenAnzahl(Zweitstimme stimme) {

		Zweitstimme alteZweitstimme = null;
		for (final Wahlkreis wk : this.deutschland.getWahlkreise()) {
			if (wk.equals(stimme.getGebiet())) {
				for (final Zweitstimme zweitstimme : wk
						.getZweitstimmenProPartei()) {
					if (zweitstimme.getPartei().equals(stimme.getPartei())) {

						alteZweitstimme = (Zweitstimme) zweitstimme.deepCopy();
						zweitstimme.setAnzahl(stimme.getAnzahl());
						break;
					}
				}
				break;
			}
		}

		return alteZweitstimme;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Falls man eine Stimme bereits zurueckgesetzt hat, kann man dies auch
	 * wieder herstellen.
	 * 
	 * @return true wenn erfolgreich
	 */
	public boolean wiederherstellen() {
		final Stimme alteStimme = this.chronik.wiederherstellenStimme();
		return setzeStimme(alteStimme, false);
	}

	/**
	 * Sobald eine Stimme in der GUI geandert wurde, ist es moeglich die Stimme
	 * wieder zurueck zu setzen.
	 * 
	 * @return ob erfolgreich oder nicht
	 */
	public boolean zurueckSetzen() {
		final Stimme alteStimme = this.chronik.zuruecksetzenStimme();
		return setzeStimme(alteStimme, false);
	}
}

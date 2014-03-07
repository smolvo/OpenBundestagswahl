package main.java.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Diese Klasse enthï¿½lte alle Daten, die zum korrekten Anzeigen eines
 * Berichtsfensters benï¿½tigt werden.
 * 
 */
public class BerichtDaten implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -8855434025330264096L;

	/** repräsentiert den Namen des Politikers */
	private final LinkedList<String> name;

	/** repräsentiert die Partei des Politikers */
	private final LinkedList<String> partei;

	/** repräsentiert das Mandat des Politikers */
	private final LinkedList<String> mandat;

	/** repräsentiert das Bundesland, in dem das Mandat geholt wurde */
	private final LinkedList<String> bundesland;

	/** repräsentiert den Wahlkreis, in dem ein Direktmandat geholt wurde */
	private final LinkedList<String> wahlkreis;

	/**
	 * Der Konstruktor initialisiert alle Listen.
	 */
	public BerichtDaten() {
		this.name = new LinkedList<String>();
		this.partei = new LinkedList<String>();
		this.mandat = new LinkedList<String>();
		this.bundesland = new LinkedList<String>();
		this.wahlkreis = new LinkedList<String>();
	}

	/**
	 * gibt alle Bundeslï¿½nder aus.
	 * 
	 * @return Liste der Bundeslï¿½nder
	 */
	public LinkedList<String> getBundeslaender() {
		return this.bundesland;
	}

	/**
	 * Gibt das Bundesland aus.
	 * 
	 * @param index
	 *            Index
	 * @return Bundesland
	 */
	public String getBundesland(int index) {
		return this.bundesland.get(index);
	}

	/**
	 * Gibt das Mandat aus.
	 * 
	 * @param index
	 *            Index
	 * @return Mandat
	 */
	public String getMandat(int index) {
		return this.mandat.get(index);
	}

	/**
	 * Gibt einen Namen aus.
	 * 
	 * @param index
	 *            Index
	 * @return Name
	 */
	public String getName(int index) {
		return this.name.get(index);
	}

	/**
	 * Gibt eine Partei aus.
	 * 
	 * @param index
	 *            Index
	 * @return Partei
	 */
	public String getPartei(int index) {
		return this.partei.get(index);
	}

	/**
	 * Gibt den Wahlkreis aus.
	 * 
	 * @param index
	 *            Index
	 * @return Wahlkreis
	 */
	public String getWahlkreis(int index) {
		return this.wahlkreis.get(index);
	}

	/**
	 * Gibt die Zweilenanzahl aus.
	 * 
	 * @return Zweilenanzahl
	 */
	public int size() {
		return this.name.size();
	}

	/**
	 * Diese Methode überprüft, ob ein String null ist, wenn nicht wird es der
	 * Liste angehangen,
	 * 
	 * @param string
	 *            String
	 * @param list
	 *            Liste
	 */
	private void stringCheck(String string, LinkedList<String> list) {
		if (string != null) {
			list.add(string);
		} else {
			list.add("-");
		}
	}

	/**
	 * Entfernt eine Zeile mit allen Beiträgen aus dem Bericht
	 * 
	 * @param zeile
	 *            die zu entfernende Zeile
	 * @throws IllegalArgumentException
	 *             wenn die Zeile außerhalb des Wertebereichs liegt.
	 */
	public void zeileEntfernen(int zeile) {
		if (zeile < 0 || zeile >= this.name.size()) {
			throw new IllegalArgumentException(
					"Zeile ist außerhalb des gültigen Wertebereichs.");
		}
		this.name.remove(zeile);
		this.partei.remove(zeile);
		this.mandat.remove(zeile);
		this.bundesland.remove(zeile);
		this.wahlkreis.remove(zeile);
	}

	/**
	 * Fügt eine Zeile in die Tabelle hinzu.
	 * 
	 * @param name
	 *            Name
	 * @param partei
	 *            Partei
	 * @param mandat
	 *            Mandat
	 * @param bundesland
	 *            Bundesland
	 * @param wahlkreis
	 *            Wahlkreis
	 */
	public void zeileHinzufuegen(String name, String partei, String mandat,
			String bundesland, String wahlkreis) {
		stringCheck(name, this.name);
		stringCheck(partei, this.partei);
		stringCheck(mandat, this.mandat);
		stringCheck(bundesland, this.bundesland);
		stringCheck(wahlkreis, this.wahlkreis);
	}
}

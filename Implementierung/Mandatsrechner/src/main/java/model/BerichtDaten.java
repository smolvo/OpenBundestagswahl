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
	private LinkedList<String> name;

	/** repräsentiert die Partei des Politikers */
	private LinkedList<String> partei;

	/** repräsentiert das Mandat des Politikers */
	private LinkedList<String> mandat;

	/** repräsentiert das Bundesland, in dem das Mandat geholt wurde */
	private LinkedList<String> bundesland;

	/** repräsentiert den Wahlkreis, in dem ein Direktmandat geholt wurde */
	private LinkedList<String> wahlkreis;

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

	/**
	 * Entfernt eine Zeile mit allen Beiträgen aus dem Bericht
	 * @param zeile die zu entfernende Zeile
	 * @throws IllegalArgumentException wenn die Zeile außerhalb des Wertebereichs liegt.
	 */
	public void zeileEntfernen(int zeile) {
		if (zeile < 0 || zeile >= name.size()) {
			throw new IllegalArgumentException(
					"Zeile ist außerhalb des gültigen Wertebereichs.");
		}
		name.remove(zeile);
		partei.remove(zeile);
		mandat.remove(zeile);
		bundesland.remove(zeile);
		wahlkreis.remove(zeile);
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
	 * Gibt einen Namen aus.
	 * 
	 * @param index
	 *            Index
	 * @return Name
	 */
	public String getName(int index) {
		return name.get(index);
	}

	/**
	 * Gibt eine Partei aus.
	 * 
	 * @param index
	 *            Index
	 * @return Partei
	 */
	public String getPartei(int index) {
		return partei.get(index);
	}

	/**
	 * Gibt das Mandat aus.
	 * 
	 * @param index
	 *            Index
	 * @return Mandat
	 */
	public String getMandat(int index) {
		return mandat.get(index);
	}

	/**
	 * Gibt das Bundesland aus.
	 * 
	 * @param index
	 *            Index
	 * @return Bundesland
	 */
	public String getBundesland(int index) {
		return bundesland.get(index);
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
	 * Gibt den Wahlkreis aus.
	 * 
	 * @param index
	 *            Index
	 * @return Wahlkreis
	 */
	public String getWahlkreis(int index) {
		return wahlkreis.get(index);
	}

	/**
	 * Gibt die Zweilenanzahl aus.
	 * 
	 * @return Zweilenanzahl
	 */
	public int size() {
		return this.name.size();
	}
}

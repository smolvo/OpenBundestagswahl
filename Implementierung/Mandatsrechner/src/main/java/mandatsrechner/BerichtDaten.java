package mandatsrechner;

import java.util.LinkedList;

public class BerichtDaten {

	/** repr�sentiert den Namen des Politikers */
	private LinkedList<String> name;
	
	/** repr�sentiert die Partei des Politikers */
	private LinkedList<String> partei;
	
	/** repr�sentiert das Mandat des Politikers */
	private LinkedList<String> mandat;
	
	/** repr�sentiert das Bundesland, in dem das Mandat geholt wurde*/
	private LinkedList<String> bundesland;
	
	/** repr�sentiert den Wahlkreis, in dem ein Direktmandat geholt wurde */
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
	 * F�gt eine Zeile in die Tabelle hinzu.
	 * @param name Name 
	 * @param partei Partei
	 * @param mandat Mandat
	 * @param bundesland Bundesland
	 * @param wahlkreis Wahlkreis
	 */
	public void zeileHinzufuegen(String name, String partei, String mandat, String bundesland, String wahlkreis) {
		stringCheck(name, this.name);
		stringCheck(partei, this.partei);
		stringCheck(mandat, this.mandat);
		stringCheck(bundesland, this.bundesland);
		stringCheck(wahlkreis, this.wahlkreis);
	}
	
	/**
	 * Diese Methode �berpr�ft, ob ein String null ist, wenn nicht wird es der
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
	 * @param index Index
	 * @return Name
	 */
	public String getName(int index) {
		return name.get(index);
	}

	/**
	 * Gibt eine Partei aus.
	 * @param index Index
	 * @return Partei
	 */
	public String getPartei(int index) {
		return partei.get(index);
	}

	/**
	 * Gibt das Mandat aus.
	 * @param index Index
	 * @return Mandat
	 */
	public String getMandat(int index) {
		return mandat.get(index);
	}

	/**
	 * Gibt das Bundesland aus.
	 * @param index Index
	 * @return Bundesland
	 */
	public String getBundesland(int index) {
		return bundesland.get(index);
	}

	/**
	 * Gibt den Wahlkreis aus.
	 * @param index Index
	 * @return Wahlkreis
	 */
	public String getWahlkreis(int index) {
		return wahlkreis.get(index);
	}
	
	/**
	 * Gibt die Zweilenanzahl aus.
	 * @return Zweilenanzahl
	 */
	public int size() {
		return this.name.size();
	}
}

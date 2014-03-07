package main.java.wahlvergleich;

import java.util.LinkedList;

/**
 * Diese Klasse hält alle relevanten Daten, die zum erstellen der Tabelle im
 * Vergleichsfenster benötigt werden.
 * 
 * @author Anton
 * 
 */
public class WahlvergleichDaten {

	/** repräsentiert die Liste der Parteien */
	private final LinkedList<String> parteien;

	/** repräsentiert die Anzahl Erststimmen der ersten Wahl */
	private final LinkedList<String> anzahlEinsErst;

	/** repräsentiert die prozentuale Anzahl Erststimmen der ersten Wahl */
	private final LinkedList<String> prozentEinsErst;

	/** repräsentiert die Differenz der Erststimmen */
	private final LinkedList<String> diffErst;

	/** repräsentiert die Anzahl Zweitstimmen der ersten Wahl */
	private final LinkedList<String> anzahlEinsZweit;

	/** repräsentiert die prozentuale Anzahl Zweitstimmen der ersten Wahl */
	private final LinkedList<String> prozentEinsZweit;

	/** repräsentiert die Differenz der Zweitstimmen */
	private final LinkedList<String> diffZweit;

	/** repräsentiert die Anzahl der Erststimmen der zweiten Wahl */
	private final LinkedList<String> anzahlZweiErst;

	/** repräsentiert die prozentuale Anzahl der Erststimmen der zweiten Wahl */
	private final LinkedList<String> prozentZweiErst;

	/** repräsentiert die Anzahl der Zweitstimmen der zweiten Wahl */
	private final LinkedList<String> anzahlZweiZweit;

	/** repräsentiert die prozentuale Anzahl der Zweitstimmen der zweiten Wahl */
	private final LinkedList<String> prozentZweiZweit;

	/**
	 * Der Konstruktor initialisiert alle Listen.
	 */
	public WahlvergleichDaten() {
		this.parteien = new LinkedList<String>();
		this.anzahlEinsErst = new LinkedList<String>();
		this.prozentEinsErst = new LinkedList<String>();
		this.diffErst = new LinkedList<String>();
		this.anzahlEinsZweit = new LinkedList<String>();
		this.prozentEinsZweit = new LinkedList<String>();
		this.diffZweit = new LinkedList<String>();
		this.anzahlZweiErst = new LinkedList<String>();
		this.prozentZweiErst = new LinkedList<String>();
		this.anzahlZweiZweit = new LinkedList<String>();
		this.prozentZweiZweit = new LinkedList<String>();
	}

	/**
	 * Diese Methode fügt der Tabelle eine weitere Zeile hinzu.
	 * 
	 * @param partei
	 *            der Parteiname
	 * @param anzahlEinsErst
	 *            die Anzahl Erststimmen der ersten Wahl
	 * @param prozentEinsErst
	 *            die proz. Anzahl Erststimmen der ersten Wahl
	 * @param diffErst
	 *            die Differenz der Erststimmen
	 * @param anzahlEinsZweit
	 *            die Anzahl Zweitstimmen der ersten Wahl
	 * @param prozentEinsZweit
	 *            die proz. Anzahl Zweitstimmen der ersten Wahl
	 * @param diffZweit
	 *            die Differenz der Zweitstimmen
	 * @param anzahlZweiErst
	 *            die Anzahl Erststimmen der zweiten Wahl
	 * @param prozentZweiErst
	 *            die Anzahl proz. Erststimmen der zweiten Wahl
	 * @param anzahlZweiZweit
	 *            die Anzahl Zweitstimmen der zweiten Wahl
	 * @param prozentZweiZweit
	 *            die proz. Anzahl Zweitstimmen der zweiten Wahl
	 */
	public void addZeile(String partei, String anzahlEinsErst,
			String prozentEinsErst, String diffErst, String anzahlEinsZweit,
			String prozentEinsZweit, String diffZweit, String anzahlZweiErst,
			String prozentZweiErst, String anzahlZweiZweit,
			String prozentZweiZweit) {
		stringCheck(partei, this.parteien);
		stringCheck(anzahlEinsErst, this.anzahlEinsErst);
		stringCheck(prozentEinsErst, this.prozentEinsErst);
		stringCheck(diffErst, this.diffErst);
		stringCheck(anzahlEinsZweit, this.anzahlEinsZweit);
		stringCheck(prozentEinsZweit, this.prozentEinsZweit);
		stringCheck(diffZweit, this.diffZweit);
		stringCheck(anzahlZweiErst, this.anzahlZweiErst);
		stringCheck(prozentZweiErst, this.prozentZweiErst);
		stringCheck(anzahlZweiZweit, this.anzahlZweiZweit);
		stringCheck(prozentZweiZweit, this.prozentZweiZweit);
	}

	/**
	 * Gibt die Anzahl der Erststimmen der ersten Wahl einer Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlEinsErst(int index) {
		if (index < 0 || index > this.anzahlEinsErst.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.anzahlEinsErst.get(index);
	}

	/**
	 * Gibt die Anzahl der Zweitstimmen der ersten Wahl einer Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlEinsZweit(int index) {
		if (index < 0 || index > this.anzahlEinsZweit.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.anzahlEinsZweit.get(index);
	}

	/**
	 * Gibt die Anzahl der Erststimmen der zweiten Wahl einer Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlZweiErst(int index) {
		if (index < 0 || index > this.anzahlZweiErst.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.anzahlZweiErst.get(index);
	}

	/**
	 * Gibt die Anzahl der Zweitstimmen der zweiten Wahl einer Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlZweiZweit(int index) {
		if (index < 0 || index > this.anzahlZweiZweit.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.anzahlZweiZweit.get(index);
	}

	/**
	 * Gibt die Differenz der Erststimmen einer Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Differenz
	 */
	public String getDiffErst(int index) {
		if (index < 0 || index > this.diffErst.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.diffErst.get(index);
	}

	/**
	 * Gibt die Differenz der Zweitstimmen einer Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Differenz
	 */
	public String getDiffZweit(int index) {
		if (index < 0 || index > this.diffZweit.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.diffZweit.get(index);
	}

	/**
	 * Gibt eine Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Partei
	 */
	public String getParteien(int index) {
		if (index < 0 || index > this.parteien.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.parteien.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Erststimmen der ersten Wahl einer Partei
	 * zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentEinsErst(int index) {
		if (index < 0 || index > this.prozentEinsErst.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.prozentEinsErst.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Zweitstimmen der ersten Wahl einer Partei
	 * zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentEinsZweit(int index) {
		if (index < 0 || index > this.prozentZweiZweit.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.prozentEinsZweit.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Erststimmen der zweiten Wahl einer Partei
	 * zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentZweiErst(int index) {
		if (index < 0 || index > this.prozentZweiErst.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.prozentZweiErst.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Zweitstimmen der zweiten Wahl einer
	 * Partei zurück.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentZweiZweit(int index) {
		if (index < 0 || index > this.prozentZweiZweit.size()) {
			throw new IllegalArgumentException("Index außerhalb Listengröße.");
		}
		return this.prozentZweiZweit.get(index);
	}

	/**
	 * Diese Methode gibt die Anzahl an Zeilen aus.
	 * 
	 * @return Zeilenanzahl
	 */
	public int size() {
		return this.parteien.size();
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
}

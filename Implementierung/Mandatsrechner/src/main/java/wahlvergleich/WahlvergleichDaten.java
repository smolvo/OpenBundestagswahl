package main.java.wahlvergleich;

import java.util.LinkedList;

/**
 * Diese Klasse h�lt alle relevanten Daten, die zum erstellen der Tabelle im
 * Vergleichsfenster ben�tigt werden.
 * 
 * @author Anton
 * 
 */
public class WahlvergleichDaten {

	/** repr�sentiert die Liste der Parteien */
	private LinkedList<String> parteien;

	/** repr�sentiert die Anzahl Erststimmen der ersten Wahl */
	private LinkedList<String> anzahlEinsErst;

	/** repr�sentiert die prozentuale Anzahl Erststimmen der ersten Wahl */
	private LinkedList<String> prozentEinsErst;

	/** repr�sentiert die Differenz der Erststimmen */
	private LinkedList<String> diffErst;

	/** repr�sentiert die Anzahl Zweitstimmen der ersten Wahl */
	private LinkedList<String> anzahlEinsZweit;

	/** repr�sentiert die prozentuale Anzahl Zweitstimmen der ersten Wahl */
	private LinkedList<String> prozentEinsZweit;

	/** repr�sentiert die Differenz der Zweitstimmen */
	private LinkedList<String> diffZweit;

	/** repr�sentiert die Anzahl der Erststimmen der zweiten Wahl */
	private LinkedList<String> anzahlZweiErst;

	/** repr�sentiert die prozentuale Anzahl der Erststimmen der zweiten Wahl */
	private LinkedList<String> prozentZweiErst;

	/** repr�sentiert die Anzahl der Zweitstimmen der zweiten Wahl */
	private LinkedList<String> anzahlZweiZweit;

	/** repr�sentiert die prozentuale Anzahl der Zweitstimmen der zweiten Wahl */
	private LinkedList<String> prozentZweiZweit;

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
	 * Diese Methode f�gt der Tabelle eine weitere Zeile hinzu.
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
		stringCheck(diffZweit, this.anzahlZweiErst);
		stringCheck(prozentZweiErst, this.prozentZweiErst);
		stringCheck(anzahlZweiZweit, this.anzahlZweiZweit);
		stringCheck(prozentZweiZweit, this.prozentZweiZweit);
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
	 * Diese Methode gibt die Anzahl an Zeilen aus.
	 * 
	 * @return Zeilenanzahl
	 */
	public int size() {
		return this.parteien.size();
	}

	/**
	 * Gibt eine Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Partei
	 */
	public String getParteien(int index) {
		if (index < 0 || index > parteien.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return parteien.get(index);
	}

	/**
	 * Gibt die Anzahl der Erststimmen der ersten Wahl einer Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlEinsErst(int index) {
		if (index < 0 || index > anzahlEinsErst.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return anzahlEinsErst.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Erststimmen der ersten Wahl einer Partei
	 * zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentEinsErst(int index) {
		if (index < 0 || index > prozentEinsErst.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return prozentEinsErst.get(index);
	}

	/**
	 * Gibt die Differenz der Erststimmen einer Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Differenz
	 */
	public String getDiffErst(int index) {
		if (index < 0 || index > diffErst.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return diffErst.get(index);
	}

	/**
	 * Gibt die Anzahl der Zweitstimmen der ersten Wahl einer Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlEinsZweit(int index) {
		if (index < 0 || index > anzahlEinsZweit.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return anzahlEinsZweit.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Zweitstimmen der ersten Wahl einer Partei
	 * zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentEinsZweit(int index) {
		if (index < 0 || index > prozentZweiZweit.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return prozentEinsZweit.get(index);
	}

	/**
	 * Gibt die Differenz der Zweitstimmen einer Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Differenz
	 */
	public String getDiffZweit(int index) {
		if (index < 0 || index > diffZweit.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return diffZweit.get(index);
	}

	/**
	 * Gibt die Anzahl der Erststimmen der zweiten Wahl einer Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlZweiErst(int index) {
		if (index < 0 || index > anzahlZweiErst.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return anzahlZweiErst.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Erststimmen der zweiten Wahl einer Partei
	 * zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentZweiErst(int index) {
		if (index < 0 || index > prozentZweiErst.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return prozentZweiErst.get(index);
	}

	/**
	 * Gibt die Anzahl der Zweitstimmen der zweiten Wahl einer Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getAnzahlZweiZweit(int index) {
		if (index < 0 || index > anzahlZweiZweit.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return anzahlZweiZweit.get(index);
	}

	/**
	 * Gibt die prozentuale Anzahl der Zweitstimmen der zweiten Wahl einer
	 * Partei zur�ck.
	 * 
	 * @param index
	 *            Index
	 * @return Anzahl
	 */
	public String getProzentZweiZweit(int index) {
		if (index < 0 || index > prozentZweiZweit.size()) {
			throw new IllegalArgumentException("Index au�erhalb Listengr��e.");
		}
		return prozentZweiZweit.get(index);
	}
}

package main.java.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import test.java.Debug;
import main.java.wahlgenerator.RelevanteZweitstimmen;

/**
 * Diese Klasse repraesentiert eine Partei.
 */
public class Partei implements Serializable, Comparable<Partei> {

	/**
	 * Automatisch generierte serialVersionUID die fï¿½r das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -6711521521647518265L;

	/** Die Liste aller Landeslisten dieser Partei. */
	private List<Landesliste> landesliste;

	/** Die Mitgliederliste dieser Partei. */
	private LinkedList<Kandidat> mitglieder;

	/** Der Name dieser Partei. */
	private String name;

	/** Die Farbe dieser Partei. */
	private Color farbe;

	/** Die Zweitstimmenliste (pro Gebiet) */
	private LinkedList<Zweitstimme> zweitstimme;

	/** Ist die Partei im Bundestag */
	private boolean imBundestag;

	/** Gesamte Anzahl an Zweitstimmen im Land */
	private int zweitstimmeGesamt;

	/** Die relevanten Zweistimmen der Partei */
	private RelevanteZweitstimmen relevanteZweitstimmen;

	/** Die Mindestsitzanzahl pro Bundesland dieser Partei. */
	private HashMap<Bundesland, Integer> mindestSitzanzahl;

	/** Die Anzahl der ï¿½berhangmandate pro Bundesland dieser Partei. */
	private HashMap<Bundesland, Integer> ueberhangMandate;

	/** Die Anzahl der Ausgleichsmandate pro Bundesland dieser Partei. */
	private HashMap<Bundesland, Integer> ausgleichsMandate;

	/**
	 * Comparator zum sortieren von Parteien nach der Anzahl der
	 * ï¿½berhangsmandate.
	 */
	public static final Comparator<Partei> NACH_UEBERHANGMANDATEN = new Comparator<Partei>() {

		@Override
		public int compare(Partei part1, Partei part2) {

			if (part1 == null || part2 == null) {
				throw new IllegalArgumentException(
						"Einer der Partei-Parameter ist null!");
			}

			int part1UeMand = part1.getAnzahlMandate(Mandat.UEBERHANGMADAT);
			int part2UeMand = part2.getAnzahlMandate(Mandat.UEBERHANGMADAT);

			Debug.print(part1.getName() + " " + part1UeMand, 5);
			Debug.print(part2.getName() + " " + part2UeMand, 5);

			int result;

			if (part1UeMand < part2UeMand) {
				result = 1;
			} else if (part1UeMand > part2UeMand) {
				result = -1;
			} else {
				result = 0;
			}

			return result;
		}
	};

	/*
	 * public static final Comparator<Bundesland>
	 * BLAENDER_NACH_UEBERHANGMANDATEN = new Comparator<Bundesland>() {
	 * 
	 * @Override public int compare(Bundesland land1, Bundesland land2) {
	 * 
	 * if (land1 == null || land2 == null) { throw new
	 * IllegalArgumentException("Einer der Bundesland-Parameter ist null!"); }
	 * 
	 * int land1UeMand =
	 * land1.getLandesliste(Partei.).getKandidaten(Mandat.UEBERHANGMADAT
	 * ).size(); int land2UeMand =
	 * land2.getLandesliste(Partei.this).getKandidaten
	 * (Mandat.UEBERHANGMADAT).size();
	 * 
	 * int result;
	 * 
	 * if (land1UeMand > land2UeMand) { result = 1; } else if (land1UeMand <
	 * land2UeMand) { result = -1; } else { result = 0; }
	 * 
	 * return result; } };
	 */

	/**
	 * Parametrisierter Konstruktor. Die Mitgliederliste und Landesliste wird
	 * hier erzeugt aber nicht befï¿½llt.
	 * 
	 * @param name
	 *            Der Name dieser Partei.
	 * @param farbe
	 *            Farbe dieser Partei
	 */
	public Partei(String name, Color farbe) {
		this.setName(name);
		this.setFarbe(farbe);
		this.mitglieder = new LinkedList<Kandidat>();
		this.landesliste = new ArrayList<Landesliste>();
		this.zweitstimme = new LinkedList<Zweitstimme>();
		this.mindestSitzanzahl = new HashMap<Bundesland, Integer>();
		this.ueberhangMandate = new HashMap<Bundesland, Integer>();
	}

	/**
	 * Parametrisierter Konstruktor. Die Mitgliederliste wird hier nur erzeugt
	 * aber nicht befï¿½llt.
	 * 
	 * @param landesliste
	 *            Die Liste aller Landeslisten dieser Partei.
	 * @param name
	 *            Der Name dieser Partei.
	 * @param farbe
	 *            Die Farbe dieser Partei.
	 */
	public Partei(List<Landesliste> landesliste, String name, Color farbe) {
		this.setLandesliste(landesliste);
		this.setName(name);
		this.setFarbe(farbe);
		this.zweitstimme = new LinkedList<>();
		this.mitglieder = new LinkedList<Kandidat>();
		this.mindestSitzanzahl = new HashMap<Bundesland, Integer>();
		this.ueberhangMandate = new HashMap<Bundesland, Integer>();
	}

	/**
	 * Gibt das Landesliste-Objekt zurï¿½ck
	 * 
	 * @return das Landesliste-Objekt
	 */
	public List<Landesliste> getLandesliste() {
		return this.landesliste;
	}

	/**
	 * Setzt das neue Landesliste-Objekt
	 * 
	 * @param landesliste
	 *            das neue Objekt
	 * @throws IllegalArgumentException
	 *             wenn der Parameter landesliste null ist
	 */
	public void setLandesliste(List<Landesliste> landesliste)
			throws IllegalArgumentException {
		if (landesliste == null) {
			throw new IllegalArgumentException("Landesliste-Objekt ist null!");
		}
		this.landesliste = landesliste;
	}

	/**
	 * Fï¿½gt eine Landesliste zur Liste aller Landeslisten dieser Partei hinzu.
	 * 
	 * @param landesliste
	 *            Die Liste aller Landeslisten dieser Partei.
	 */
	public void addLandesliste(Landesliste landesliste) {
		this.landesliste.add(landesliste);
	}

	/**
	 * Gibt die Liste der Mitglieder zurï¿½ck
	 * 
	 * @return Liste der Mitglieder
	 */
	public LinkedList<Kandidat> getMitglieder() {
		return mitglieder;
	}

	/**
	 * Setzt eine neue Liste als Mitgliederliste
	 * 
	 * @param mitglieder
	 *            der Partei
	 * @throws IllegalArgumentException
	 *             wenn der Parameter mitglieder null ist
	 */
	public void setMitglieder(LinkedList<Kandidat> mitglieder)
			throws IllegalArgumentException {
		if (mitglieder == null) {
			throw new IllegalArgumentException("Der Parameter \"mitglieder\" ist null!");
		}
		this.mitglieder = mitglieder;
	}

	/**
	 * Fï¿½gt ein neues Mitglied zur Liste hinzu
	 * 
	 * @param mitglied
	 *            das neue Mitglied
	 * @throws IllegalArgumentException
	 *             wenn das Mitglied null ist
	 */
	public void addMitglied(Kandidat mitglied) throws IllegalArgumentException {
		if (mitglied == null) {
			throw new IllegalArgumentException("Mitglied ist null!");
		}
		this.mitglieder.add(mitglied);
	}

	/**
	 * Gibt alle Mitglieder mit dem gewï¿½nschten Mandat zurueck.
	 * 
	 * @param mandat
	 *            das gewuenschte Mandat.
	 * @return die Liste mit den Mitgliedern.
	 */
	public LinkedList<Kandidat> getMitglieder(Mandat mandat) {
		LinkedList<Kandidat> res = new LinkedList<Kandidat>();
		for (Kandidat kandidat : this.mitglieder) {
			if (kandidat.getMandat() == mandat) {
				res.add(kandidat);
			}
		}
		return res;
	}

	/**
	 * Gibt den Namen der Partei zurueck
	 * 
	 * @return den Namen der Partei
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setzt den neuen Namen der Partei.
	 * 
	 * @param name
	 *            der neue Name.
	 * @throws IllegalArgumentException
	 *             wenn der Parameter name null ist.
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name ist leer");
		}
		this.name = name;
	}

	/**
	 * Gibt die Farbe der Partei zurueck.
	 * 
	 * @return die Farbe der Partei.
	 */
	public Color getFarbe() {
		if (this.farbe == null) {
			return new Color(0, 0, 0, 0);
		}
		return this.farbe;
	}

	/**
	 * Setzt die Farbe der Partei.
	 * 
	 * @param farbe
	 *            der Partei.
	 * @throws IllegalArgumentException
	 *             wenn der Parameter farbe null ist.
	 */
	public void setFarbe(Color farbe) throws IllegalArgumentException {
		if (farbe == null) {
			throw new IllegalArgumentException("Farbe ist leer");
		}
		this.farbe = farbe;
	}

	/**
	 * Gibt das Zweitstimmen-Objekt zurueck
	 * 
	 * @return das Zweitstimmen-Objekt
	 */
	public LinkedList<Zweitstimme> getZweitstimme() {
		return zweitstimme;
	}

	/**
	 * Setzt die Liste aller Zweitstimmen pro Gebiet.
	 * 
	 * @param zweitstimme
	 *            Die Liste aller Zweitstimmen pro Gebiet.
	 * @throws IllegalArgumentException
	 *             wenn die Liste aller Zweitstimmen (pro Gebiet) null ist.
	 */
	public void setZweitstimme(LinkedList<Zweitstimme> zweitstimme)
			throws IllegalArgumentException {
		if (zweitstimme == null) {
			throw new IllegalArgumentException("Zeitstimme-Objekt ist leer");
		}
		this.zweitstimme = zweitstimme;
	}

	/**
	 * Fuegt ein Zweitstimmenobjekt zur Liste hinzu.
	 * 
	 * @param zweitstimme
	 *            das Objekt
	 * @throws IllegalArgumentException
	 *             wenn das Zweitstimmen-Objekt null ist.
	 */
	public void addZweitstimme(Zweitstimme zweitstimme) {
		if (zweitstimme == null) {
			throw new IllegalArgumentException("Zeitstimme-Objekt ist leer");
		}
		this.zweitstimme.add(zweitstimme);
	}

	/**
	 * Gibt die Anzahl an Direkmandate zurueck.
	 * 
	 * @return die Anzahl an Direktmandate in der Partei.
	 */
	public int getAnzahlDirektmandate() {
		int res = 0;
		for (Kandidat kandidat : getMitglieder()) {
			if (kandidat.getMandat().equals(Mandat.DIREKTMANDAT)) {
				res++;
			}
		}
		return res;
	}

	/**
	 * Gibt zurueck ob die Partei im Bundestag.
	 * 
	 * @return ob Partei im Bundestag ist.
	 */
	public boolean isImBundestag() {
		return this.imBundestag;
	}

	/**
	 * Bestimmt ob die Partei im Bundestag ist oder nicht.
	 * 
	 * @param imBundestag
	 *            ob Partei im Bundestag ist oder nicht.
	 */
	public void setImBundestag(boolean imBundestag) {
		this.imBundestag = imBundestag;
	}

	/**
	 * Gibt die Anzahl an Zweistimmen in Deutschland zurueck.
	 * 
	 * @return die Anzahl an Zweitstimmen in Deutschland.
	 */
	public int getZweitstimmeGesamt() {
		zweitstimmeGesamt = 0;
		for (Zweitstimme zweit : zweitstimme) {
			zweitstimmeGesamt += zweit.getAnzahl();
		}
		return zweitstimmeGesamt;
	}

	@Override
	public int compareTo(Partei andere) {
		return Integer.compare(this.getZweitstimmeGesamt(),
				andere.getZweitstimmeGesamt());
	}

	/**
	 * Gibt die relevanten Zweitstimmen zurueck.
	 * 
	 * @return die relevanteZweitstimmen.
	 */
	public RelevanteZweitstimmen getRelevanteZweitstimmen() {
		return relevanteZweitstimmen;
	}

	/**
	 * Setzt die relevanten Zweitstimmen.
	 * 
	 * @param relevanteZweitstimmen
	 *            relevante Zweitstimmen
	 * @throws IllegalArgumentException
	 *             wenn die relevanten Zweitstimmen null sind.
	 * 
	 * 
	 */
	public void setRelevanteZweitstimmen(
			RelevanteZweitstimmen relevanteZweitstimmen) {
		if (relevanteZweitstimmen == null) {
			throw new IllegalArgumentException(
					"relevante Zweitstimmen war null.");
		} else {
			this.relevanteZweitstimmen = relevanteZweitstimmen;

		}

	}

	/**
	 * Gibt die Anzahl der Zweitstimmen der Partei in dem jeweiligen Wahlkreis.
	 * 
	 * @param wk
	 *            der Wahlkreis, zu dem die Zweitstimmenanzahl gegeben werden
	 *            soll.
	 * @return die Anzahl der Zweitstimmen.
	 */

	public int getZweitstimme(Wahlkreis wk) {
		int anzahlZweitstimmen = 0;
		for (Zweitstimme z : zweitstimme) {
			if (z.getGebiet().equals(wk)) {
				anzahlZweitstimmen = z.getAnzahl();
			}

		}
		return anzahlZweitstimmen;

	}

	/**
	 * Gibt an, wie haeufig der im Parameter gegebene Mandattyp in dem gegebenem
	 * Bundesland auftritt.
	 * 
	 * @param m
	 *            Mandattyp.
	 * @param b
	 *            Bundesland.
	 * @return die Anzahl der Mandate eines gegebenen Typs in einem Gebiet.
	 */
	public int getAnzahlMandate(Mandat m, Bundesland b) {
		if (m == null || b == null) {
			throw new IllegalArgumentException(
					"Mandat oder Bundesland waren null.");
		}
		int anzahlMandate = 0;
		/*
		 * Kandidaten (Direktmandate) muessen nicht in der Landesliste sein!!!
		 * Wichtig!!! Bitte beachten.
		 */
		if (m.equals(Mandat.DIREKTMANDAT)) {
			for (Wahlkreis wk : b.getWahlkreise()) {
				if (wk.getWahlkreisSieger() != null
						&& wk.getWahlkreisSieger().getPartei().equals(this)) {
					anzahlMandate++;
				}
			}
		} else {
			for (Kandidat kandidat : this.getMitglieder()) {
				Landesliste landesliste = kandidat.getLandesliste();
				if (landesliste != null && kandidat.getMandat().equals(m)
						&& landesliste.getBundesland().equals(b)) {
					anzahlMandate++;
				}
			}
		}
		return anzahlMandate;
	}

	/**
	 * Gibt die Landesliste zurueck, die zu dem gegeneben Bundesland passt.
	 * 
	 * @param b
	 *            das Bundesland, zu dem die Landesliste gesucht wird.
	 * @return Landesliste
	 * @throws IllegalArgumentException
	 *             wenn das Bundesland null ist.
	 */
	public Landesliste getLandesliste(Bundesland b) {
		if (b == null) {
			throw new IllegalArgumentException("Bundesland war null.");
		}
		Landesliste gesuchteLandesliste = null;
		for (Landesliste l : this.landesliste) {
			if (l.getBundesland().equals(b)) {
				gesuchteLandesliste = l;
			}
		}
		return gesuchteLandesliste;
	}

	/**
	 * Fuegt eine neue Mindestsitzanzahl fuer ein Bundesland hinzu.
	 * 
	 * @param bl
	 *            das Bundesland.
	 * @param mindestSitzanzahl
	 *            die dazugoerige Mindestsitzanzahl.
	 */
	public void addMindestsitzanzahl(Bundesland bl, int mindestSitzanzahl) {
		if (bl == null || mindestSitzanzahl < 0) {
			throw new IllegalArgumentException("Die Eingabeparameter sind null");
		}
		this.mindestSitzanzahl.put(bl, mindestSitzanzahl);
	}

	/**
	 * Gibt die mindestsitzanzahl dieser Partei fï¿½r ein Bundesland zurï¿½ck.
	 * 
	 * @param bl
	 *            das gesuchte Bundesland.
	 * @return gibt 0 zurï¿½ck falls key nicht gefunden wurde.
	 */
	public int getMindestsitzanzahl(Bundesland bl) {
		int anzahl = this.mindestSitzanzahl.get(bl);

		return anzahl;
	}

	/**
	 * Gibt an, wie viele Mandate eine Partei insgesamt besitzt
	 * 
	 * @return die Anzahl an Mandate
	 */
	public int getAnzahlMandate() {
		int anzahlMandate = 0;
		for (Kandidat kandidat : getMitglieder()) {
			if (!kandidat.getMandat().equals(Mandat.KEINMANDAT)) {
				anzahlMandate++;
			}
		}
		return anzahlMandate;
	}

	/**
	 * Gibt an, wie viel Mandate eine Partei insgesamt besitzt
	 * 
	 * @param mandat
	 *            bestimmter Mandat
	 * @return die Anzahl an Mandate
	 */
	public int getAnzahlMandate(Mandat mandat) {
		int anzahlMandate = 0;
		for (Kandidat kandidat : getMitglieder()) {
			if (kandidat.getMandat().equals(mandat)) {
				anzahlMandate++;
			}
		}
		return anzahlMandate;
	}

	/**
	 * Gibt die Summe der Mindestsitze aller Bundeslaender von einer Partei
	 * zurueck.
	 * 
	 * @return anzahl der Mindestsitze einer Partei.
	 */
	public int getMindestsitzAnzahl() {
		int anzahl = 0;
		Set<Bundesland> set = this.mindestSitzanzahl.keySet();
		Iterator<Bundesland> i = set.iterator();

		while (i.hasNext()) {
			Bundesland key = i.next();
			anzahl += this.mindestSitzanzahl.get(key);
		}
		return anzahl;
	}

	/**
	 * Gibt alle Ueberhangsmandate dieser Partei zurueck.
	 * 
	 * @return die Anzahl der Ueberhangsmandate.
	 */
	public int getUeberhangMandate() {
		int anzahl = 0;
		Set<Bundesland> set = this.ueberhangMandate.keySet();
		Iterator<Bundesland> i = set.iterator();

		while (i.hasNext()) {
			Bundesland key = i.next();
			anzahl += this.ueberhangMandate.get(key);
		}
		return anzahl;
	}

	/**
	 * Gibt die Anzahl der Ueberhangsmandate fuer einen bestimmten Bundesland
	 * zurueck.
	 * 
	 * @param bl
	 *            ausgewaehltes Bundesland.
	 * @return die anzahl der Ueberhangsmandate.
	 */
	public int getUeberhangMandate(Bundesland bl) {
		int anzahl = 0;
		Set<Bundesland> set = this.ueberhangMandate.keySet();
		Iterator<Bundesland> i = set.iterator();

		while (i.hasNext()) {
			Bundesland key = i.next();
			if (key.equals(bl)) {
				anzahl += this.ueberhangMandate.get(key);
			}
		}
		return anzahl;
	}

	/**
	 * Erhoeht die Anzahl der Ueberhangsmandate fuer ein bestimmtes Bundesland
	 * um eins.
	 * 
	 * @param bl
	 *            das ausgewaehlte
	 */
	public void incrementUeberhangMandate(Bundesland bl) {
		int value = 1;
		if (this.ueberhangMandate.containsKey(bl)) {
			value = this.ueberhangMandate.get(bl);
			value += 1;
		}
		this.ueberhangMandate.put(bl, value);
	}

	/**
	 * Setzt alle Ueberhangmandate zurueck.
	 */
	public void resetUeberhangMandate() {
		this.ueberhangMandate = new HashMap<Bundesland, Integer>();
	}

	/**
	 * Gibt alle Ausgleichsmandate zurueck.
	 * 
	 * @return die Anzahl der Ausgleichmandate.
	 */
	public int getAusgleichsMandate() {
		int anzahl = 0;
		Set<Bundesland> set = this.ausgleichsMandate.keySet();
		Iterator<Bundesland> i = set.iterator();

		while (i.hasNext()) {
			Bundesland key = i.next();
			anzahl += this.ausgleichsMandate.get(key);
		}
		return anzahl;
	}

	/**
	 * Gibt die Anzahl der Ausgleichsmandate pro Bundesland zurueck.
	 * 
	 * @param bl
	 *            das ausgewaehlte Bundesland.
	 * @return die Anzahl der Ausgleichsmandate.
	 */
	public int getAusgleichsMandate(Bundesland bl) {
		int anzahl = 0;
		Set<Bundesland> set = this.ausgleichsMandate.keySet();
		Iterator<Bundesland> i = set.iterator();

		while (i.hasNext()) {
			Bundesland key = i.next();
			if (key.equals(bl)) {
				anzahl += this.ausgleichsMandate.get(key);
			}
		}
		return anzahl;
	}

	/**
	 * Erhoeht die Anzahl der Ausgleichsmandate fuer ein bestimmtes Bundesland
	 * um eins. Der Wert vom key wird 1 gesetzt, falls es noch nicht existert.
	 * 
	 * @param bl
	 *            das ausgewaehlte Bundesland.
	 */
	public void incrementAusgleichsMandate(Bundesland bl) {
		int value = 1;
		if (this.ausgleichsMandate.containsKey(bl)) {
			value = this.ausgleichsMandate.get(bl);
			value += 1;
		}
		this.ausgleichsMandate.put(bl, value);
	}

	/**
	 * Erniedrigt die Anzahl der Ausgleichsmandate fuer ein bestimmtes
	 * Bundesland. Der Wert vom key wird -1 gesetzt, falls es noch nicht
	 * existiert.
	 * 
	 * @param bl
	 *            das ausgewaehlte Bundesland.
	 * 
	 */
	public void decrementAusgleichsMandate(Bundesland bl) {
		int value = -1;
		if (this.ausgleichsMandate.containsKey(bl)) {
			value = this.ausgleichsMandate.get(bl);
			value -= 1;
		}
		this.ausgleichsMandate.put(bl, value);
	}

	/**
	 * Setzt die Anzahl der Ausgleichsmandate zurueck.
	 */
	public void resetAusgleichsMandate() {
		this.ausgleichsMandate = new HashMap<Bundesland, Integer>();
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Setzt die Mandate der Partei zurück.
	 */
	public void resetPartei() {
		this.ueberhangMandate = new HashMap<Bundesland, Integer>();
		this.ausgleichsMandate = new HashMap<Bundesland, Integer>();
		for (Kandidat kandidat : this.mitglieder) {
			kandidat.setMandat(Mandat.KEINMANDAT);
		}
	}
}

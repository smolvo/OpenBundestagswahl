package main.java.steuerung;

import java.io.File;
import java.util.LinkedList;

import main.java.gui.VergleichsFenster;
import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Stimme;
import main.java.wahlgenerator.Stimmanteile;
import main.java.wahlgenerator.Wahlgenerator;
import main.java.wahlvergleich.Wahlvergleich;

/**
 * Diese Klasse repräsentiert die Hauptsteuerung des Programmes.
 * 
 * @author Anton
 * 
 */
public class Steuerung {

	/** zeigt an ob ein Steuerungs-Objekt bereits existiert */
	private static Steuerung instance;

	/** repräsentiert die aktuelle Bundestagswahl mit der gearbeitet wird */
	private Bundestagswahl btw;

	/**
	 * Privater Konstruktor, wegen Benutzung des Singleton-Patterns.
	 */
	private Steuerung() {
	}

	/**
	 * Diese Methode importiert eine neue Bundestagswahl in das Programm mit
	 * einem Vektor, der gefüllt ist mit den dazu relevanten Daten.
	 * 
	 * @param csvDateien
	 *            relevante Daten
	 * @return neue Bundestagswahl
	 * @throws NullPointerException
	 */
	public Bundestagswahl importieren(File[] csvDateien) {
		if (csvDateien == null) {
			throw new NullPointerException("Keine Daten gefunden.");
		}
		ImportExportManager i = new ImportExportManager();
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.btw = w;
		return w;
	}

	/**
	 * Diese Methode exportiert die aktuelle Bundestagswahl auf den angegebenen
	 * Pfad.
	 * 
	 * @param pfad
	 *            Pfad
	 * @throws NullPointerException
	 */
	public void exportieren(String pfad) {
		if (pfad == null) {
			throw new NullPointerException("Kein Pfad angegeben.");
		}
		ImportExportManager i = new ImportExportManager();
		i.exportieren(pfad, btw);
	}

	/**
	 * Diese Methode startet eine neue Berechnung der Sitzverteilung der
	 * aktuellen Bundestagswahl.
	 * 
	 * @param btw
	 *            aktuelle Bundestagswahl
	 * @throws NullPointerException
	 */
	public void berechneSitzverteilung() {
		if (this.btw == null) {
			throw new NullPointerException("Keine Bundestagswahl vorhanden.");
		}
		this.btw = Mandatsrechner2013.getInstance().berechne(this.btw);
	}

	/**
	 * Diese Methode generiert eine zufällige Wahl auf Grund bestimmter
	 * Stimmenanteile.
	 * 
	 * @param anteile
	 *            die Stimmenanteile
	 * @throws NullPointerException
	 */
	public Bundestagswahl zufaelligeWahlgenerierung(Bundestagswahl btw,
			LinkedList<Stimmanteile> anteile, String name) {
		if ((btw == null) || (anteile == null) || (name == null)) {
			throw new NullPointerException("Einer der Parameter ist null.");
		}
		Wahlgenerator wg = new Wahlgenerator(btw, anteile);
		Bundestagswahl neueBtw = wg.erzeugeBTW(name);
		return neueBtw;
	}

	public void negStimmgewichtGenerierung(Stimmanteile anteile) {
		// TODO
	}

	/**
	 * Diese Methode aktualisiert den Datensatz, sobald eine bestimmte Anzahl an
	 * Erst- oder Zweitstimmen geändert wurde.
	 * 
	 * @param stimme
	 *            die betroffene Stimme
	 * @param anzahl
	 *            der neue Wert
	 * @return true or false
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public boolean aktualisiereDaten(Stimme stimme, int anzahl) {
		if (stimme == null) {
			throw new NullPointerException("Keine Stimme gefunden.");
		}
		if (anzahl < 0) {
			throw new IllegalArgumentException("Anzahl muss größer Null sein.");
		}
		stimme.setAnzahl(anzahl);
		boolean erfolg = this.btw.setzeStimme(stimme);
		return erfolg;
	}

	/**
	 * Durch diese Methode wird die aktuelle Bundestagswahl mit einer
	 * ausgewählten Bundestagswahl verglichen.
	 * 
	 * @param vergleichsWahl
	 *            andere Wahl
	 * @throws NullPointerException
	 */
	public void vergleicheWahlen(Bundestagswahl vergleichsWahl) {
		if ((this.btw == null) || (vergleichsWahl == null)) {
			throw new NullPointerException("Eine der beiden Wahlen ist null.");
		}
		Wahlvergleich vergleich = new Wahlvergleich(this.btw, vergleichsWahl);
		VergleichsFenster fenster = new VergleichsFenster(vergleich);
		fenster.setVisible(true);
	}

	/**
	 * Mit dieser Methode wird das Programm eine Stimmenänderung zurück gesetzt.
	 * Es wird ausgegeben, ob dies erfolgreich war.
	 * 
	 * @return true false
	 */
	public boolean zurueckSetzen() {
		return this.btw.zurueckSetzen();
	}

	/**
	 * Gibt die aktuelle Steuerung aus.
	 * 
	 * @return Steuerung
	 */
	public static Steuerung getInstance() {
		if (instance == null) {
			instance = new Steuerung();
		}
		return instance;
	}

	/**
	 * Gibt die Bundestagswahl aus.
	 * 
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBtw() {
		return btw;
	}

	/**
	 * Setzt die Bundestagswahl.
	 * 
	 * @param btw
	 *            Bundestagswahl
	 * @throws NullPointerException
	 */
	public void setBtw(Bundestagswahl btw) {
		if (btw == null) {
			throw new NullPointerException("Übergebene Bundestagswahl ist null");
		}
		this.btw = btw;
	}
}

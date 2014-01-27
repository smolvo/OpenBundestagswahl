package main.java.steuerung;

import java.io.File;

import main.java.gui.VergleichsFenster;
import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Stimme;
import main.java.wahlgenerator.Stimmanteile;
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
	 */
	public Bundestagswahl importieren(File[] csvDateien) {
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
	 */
	public void exportieren(String pfad) {
		ImportExportManager i = new ImportExportManager();
		i.exportieren(pfad, btw);
	}

	/**
	 * Diese Methode startet eine neue Berechnung der Sitzverteilung der
	 * aktuellen Bundestagswahl.
	 * 
	 * @param btw
	 *            aktuelle Bundestagswahl
	 */
	public void berechneSitzverteilung() {
		this.btw = Mandatsrechner2013.getInstance().berechneAlles(this.btw);
	}

	/**
	 * Diese Methode generiert eine zufällige Wahl auf Grund bestimmter
	 * Stimmenanteile.
	 * 
	 * @param anteile
	 *            die Stimmenanteile
	 */
	public Bundestagswahl zufaelligeWahlgenerierung(Stimmanteile anteile) {
		// TODO
		return null;
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
	 */
	public boolean aktualisiereDaten(Stimme stimme, int anzahl) {
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
	 */
	public void vergleicheWahlen(Bundestagswahl vergleichsWahl) {
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
	 */
	public void setBtw(Bundestagswahl btw) {
		this.btw = btw;
	}
}

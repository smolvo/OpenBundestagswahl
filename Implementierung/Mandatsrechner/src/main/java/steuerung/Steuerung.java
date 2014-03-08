package main.java.steuerung;

import java.io.File;
import java.util.List;

import main.java.gui.VergleichsFenster;
import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Stimme;
import main.java.wahlgenerator.Stimmanteile;
import main.java.wahlgenerator.Wahlgenerator;
import main.java.wahlvergleich.Wahlvergleich;

/**
 * Diese Klasse repräsentiert die Hauptsteuerung des Programmes und dient als
 * Fassade zwischen GUI und Model.
 * 
 */
public class Steuerung {

	/** zeigt an ob ein Steuerungs-Objekt bereits existiert */
	private static Steuerung instance;

	/**
	 * Gibt die aktuelle Steuerung aus.
	 * 
	 * @return Steuerung
	 */
	public static Steuerung getInstance() {
		if (Steuerung.instance == null) {
			Steuerung.instance = new Steuerung();
		}
		return Steuerung.instance;
	}

	/** repräsentiert die aktuelle Bundestagswahl mit der gearbeitet wird */
	private Bundestagswahl btw;

	/**
	 * Privater Konstruktor, wegen Benutzung des Singleton-Patterns.
	 */
	private Steuerung() {
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
		if (stimme == null) {
			throw new IllegalArgumentException("Keine Stimme gefunden.");
		}
		if (anzahl < 0) {
			throw new IllegalArgumentException(
					"Anzahl muss grï¿½ï¿½er Null sein.");
		}
		final Stimme neueStimme = stimme.deepCopy();
		neueStimme.setAnzahl(anzahl);
		return this.btw.setzeStimme(neueStimme, true);
	}

	/**
	 * Diese Methode startet eine neue Berechnung der Sitzverteilung der
	 * aktuellen Bundestagswahl.
	 * 
	 */
	public void berechneSitzverteilung() {
		if (this.btw == null) {
			throw new IllegalArgumentException(
					"Keine Bundestagswahl vorhanden.");
		}
		this.btw = Mandatsrechner2013.getInstance().berechne(this.btw);
	}

	/**
	 * Diese Methode exportiert die aktuelle Bundestagswahl auf den angegebenen
	 * Pfad.
	 * 
	 * @param pfad
	 *            Pfad
	 */
	public void exportieren(String pfad) {
		if (pfad == null) {
			throw new IllegalArgumentException("Kein Pfad angegeben.");
		}
		final ImportExportManager i = new ImportExportManager();
		i.exportieren(pfad, this.btw);
	}

	/**
	 * Gibt die Bundestagswahl aus.
	 * 
	 * @return Bundestagswahl
	 */
	public Bundestagswahl getBtw() {
		return this.btw;
	}

	/**
	 * Diese Methode importiert eine neue Bundestagswahl in das Programm mit
	 * einem Vektor, der gefüllt ist mit den dazu relevanten Daten.
	 * 
	 * @param csvDateien
	 *            relevante Daten
	 * @return neue Bundestagswahl
	 * @throws IllegalArgumentException
	 */
	public Bundestagswahl importieren(File[] csvDateien)
			throws IllegalArgumentException {
		if (csvDateien == null) {
			throw new IllegalArgumentException("Keine Daten gefunden.");
		}
		final ImportExportManager i = new ImportExportManager();

		return i.importieren(csvDateien);
	}

	/**
	 * Setzt die Bundestagswahl.
	 * 
	 * @param btw
	 *            Bundestagswahl
	 */
	public void setBtw(Bundestagswahl btw) {
		if (btw == null) {
			throw new IllegalArgumentException(
					"ï¿½bergebene Bundestagswahl ist null");
		}
		this.btw = btw;
	}

	/**
	 * Durch diese Methode wird die aktuelle Bundestagswahl mit einer
	 * ausgewählten Bundestagswahl verglichen.
	 * 
	 * @param vergleichsWahl
	 *            andere Wahl
	 */
	public void vergleicheWahlen(Bundestagswahl vergleichsWahl) {
		if (this.btw == null || vergleichsWahl == null) {
			throw new IllegalArgumentException(
					"Eine der beiden Wahlen ist null.");
		}
		final Wahlvergleich vergleich = new Wahlvergleich(this.btw,
				vergleichsWahl);
		final VergleichsFenster fenster = new VergleichsFenster(vergleich);
		fenster.setLocationRelativeTo(null);
		fenster.setVisible(true);
	}

	/**
	 * Mit dieser Methode wird das Programm eine Stimmenänderung
	 * wiederhergestellt.
	 * 
	 * @return true false
	 */
	public boolean wiederherrstellen() {
		return this.btw.wiederherstellen();
	}

	/**
	 * Diese Methode generiert eine zufällige Wahl auf Grund bestimmter
	 * Stimmenanteile.
	 * 
	 * @param btw
	 *            Die BasisWahl
	 * @param anteile
	 *            die Stimmenanteile
	 * @param name
	 *            Der Name der BTW
	 * @return gibt die generierte Wahl zurück
	 */
	public Bundestagswahl zufaelligeWahlgenerierung(Bundestagswahl btw,
			List<Stimmanteile> anteile, String name) {
		if (btw == null || anteile == null || name == null) {
			throw new IllegalArgumentException("Einer der Parameter ist null.");
		}
		final Wahlgenerator wg = new Wahlgenerator(btw, anteile);
		return wg.erzeugeBTW(name);
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
}

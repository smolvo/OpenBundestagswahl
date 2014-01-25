package main.java.importexport;

import java.io.File;

import main.java.model.Bundestagswahl;

/**
 * Abstrakte Crawler-Klasse, die es erm�glicht,
 * f�r beliebige Bundestagswahlen eine Importm�glichkeit
 * anzubieten. 
 * @author 13genesis37
 *
 */
public abstract class Crawler {

	/**
	 * Informationen zum Crawler (z.B. die Quelle)
	 * @return ein String zur Identifikation des Crawlers.
	 */
	public abstract String getCrawlerInformation();
	
	/**
	 * Erzeugt aus einer Menge an CSV-Dateien ein
	 * Bundestagswahl objekt.
	 * @param csvDateien Eine Menge an Dateien.
	 * @return ein neues Bundestagswahl-Objekt.
	 */
	public abstract Bundestagswahl erstelleBundestagswahl(File[] csvDateien);
}

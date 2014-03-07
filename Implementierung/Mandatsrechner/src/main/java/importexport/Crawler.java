package main.java.importexport;

import java.io.File;

import main.java.model.Bundestagswahl;

/**
 * Abstrakte Crawler-Klasse, die es ermï¿½glicht, fï¿½r beliebige
 * Bundestagswahlen eine Importmï¿½glichkeit anzubieten.
 * 
 * @author 13genesis37
 * 
 */
public abstract class Crawler {

	/**
	 * Erzeugt aus einer Menge an CSV-Dateien ein Bundestagswahl objekt.
	 * 
	 * @param csvDateien
	 *            Eine Menge an Dateien.
	 * @return ein neues Bundestagswahl-Objekt.
	 */
	public abstract Bundestagswahl erstelleBundestagswahl(File[] csvDateien);

	/**
	 * Informationen zum Crawler (z.B. die Quelle)
	 * 
	 */
	public abstract void getCrawlerInformation();
}

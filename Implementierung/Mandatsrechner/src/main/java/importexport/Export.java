package main.java.importexport;

import main.java.model.Bundestagswahl;

/**
 * Exportiert eine Bundestaswahl. Es wird hierbei nur eine Ergebnis-Datei
 * erstellt. Es ist kompatibel zu externen Dateien wie der Bewerber-Datei.
 * 
 * @author 13genesis37
 * 
 */
public abstract class Export {
	/**
	 * Exportiert eine Bundestagswahl in ein bestimmtes Pfad.
	 * 
	 * @param pfad
	 *            Der Pfad an den die exportierte BTW gespeichert werden soll.
	 * @param bw
	 *            eine Bundestagswahl
	 * @return true bei Erfolg. Ansonsten false.
	 */
	public abstract boolean exportieren(String pfad, Bundestagswahl bw);
}

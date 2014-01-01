package importexport;

import java.io.File;

import model.Bundestagswahl;

/**
 * Der ImportExportManager ist zustaendig fuer das Erstellen
 * von "Bundestagswahl"-Objekten.
 * @author Enes Oerdek
 *
 */
public class ImportExportManager {
	/**
	 * Ein Array, dass alle vorhandenen Crawler-Algorithmen
	 * beinhaltet.
	 */
	private Crawler crawler[];
	
	/**
	 * Hier koennen weitere Algorithmen ergaenzt werden.
	 */
	public ImportExportManager(){
		crawler = new Crawler[1];
		crawler[0] = new Crawler2013();
	}

	/**
	 * Importiert eine Datei.
	 * @param csvDatei Datei die importiert werden soll.
	 * @return das Ergebnis-Objekt.
	 */
	public Bundestagswahl importieren(File csvDatei){
		return null;
	}

	public boolean exportieren(String pfad){
		return false;
	}
	
	private boolean pruefeDateityp(File csvDatei){
		return false;
	}
	
	private boolean leseCSVDatei(File csvDatei){
		return false;
	}
}

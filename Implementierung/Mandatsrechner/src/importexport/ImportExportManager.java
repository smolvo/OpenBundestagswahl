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
		
		Bundestagswahl imported = null;
		if(this.pruefeDateityp(csvDatei)){
				imported = this.leseCSVDatei(csvDatei);
		}
		return imported;
	}

	public boolean exportieren(String pfad){
		return false;
	}
	
	private boolean pruefeDateityp(File csvDatei){
		boolean isCSV = false;
		String fileName = csvDatei.getName();
		String fileExtension = fileName.substring(fileName.length()-4, fileName.length());
		
		//System.out.println(fileExtension);
		if(fileExtension.equals(".csv")){
			isCSV = true;
		}
		// else: Es handelt sich um keine CSV-Datei.
		return isCSV;
	}
	
	private Bundestagswahl leseCSVDatei(File csvDatei){
		Bundestagswahl imported = null;
		for(int i=0; i<this.crawler.length;i++){
			System.out.println(crawler[i].getCrawlerInformation());
			imported = crawler[i].erstelleBundestagswahl(csvDatei);
			if(imported!=null){
				break; 
			}
		}
		
		// if imported==null > Keine gültige Datei.
		
		return imported;
		
	}
}

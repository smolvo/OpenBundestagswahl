package importexport;

import java.io.File;

import model.Bundestagswahl;

public class ImportExportManager {
	private Crawler crawler[];
	
	public ImportExportManager(){
		crawler = new Crawler[1];
		crawler[0] = new Crawler2013();
	}

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

package mandatsrechner;

import importexport.ImportExportManager;

import java.io.File;

import model.Bundestagswahl;

public class MandatsrechnerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block	
			e1.printStackTrace();
			System.out.println("Leine gültige CSV-Datei :/");
		}
		
		Mandatsrechner2013 m = Mandatsrechner2013.getInstance();
		
		if (w != null) {
			Bundestagswahl newW = m.berechneAlles(w);
			newW = m.berechneAlles(newW);
		}
	}

}

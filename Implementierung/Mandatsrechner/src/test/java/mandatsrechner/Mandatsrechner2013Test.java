package test.java.mandatsrechner;

import java.io.File;

import test.java.Debug;
import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;

public class Mandatsrechner2013Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Debug.setAktiv(true);
		
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File("src/main/resources/importexport/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block	
			e1.printStackTrace();
			System.out.println("Leine g�ltige CSV-Datei :/");
		}
		
		Mandatsrechner2013 m = Mandatsrechner2013.getInstance();
		
		if (w != null) {
			//System.out.println("Test");
			Bundestagswahl newW = m.berechneAlles(w);
			//newW = m.berechneAlles(newW);
			
			//Debug.print(w.getSitzverteilung().getBericht());
			//System.out.println(w.getSitzverteilung().getAbgeordnete().size());
		}
	}

}

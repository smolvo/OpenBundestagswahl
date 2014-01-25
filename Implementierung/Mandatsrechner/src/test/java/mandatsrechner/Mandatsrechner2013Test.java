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
			System.out.println("Leine g�ltige CSV-Datei :/");
		}
		
		Mandatsrechner2013 m = new Mandatsrechner2013();
		
		if (w != null) {
			Bundestagswahl newW = m.berechneAlles(m.berechneAlles(m.berechneAlles(w)));
			//newW = m.berechneAlles(newW);
			
			Debug.print(w.getSitzverteilung().getBericht());
			//System.out.println(w.getSitzverteilung().getAbgeordnete().size());
		}
	}

}

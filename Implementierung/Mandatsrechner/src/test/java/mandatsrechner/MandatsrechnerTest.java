package test.java.mandatsrechner;

import java.io.File;

import test.java.Debug;
import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundestagswahl;

public class MandatsrechnerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Debug.setAktiv(true);
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("src/main/resources/importexport/Ergebnis2009.csv");
		csvDateien[1] = new File("src/main/resources/importexport/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block	
			e1.printStackTrace();
			System.out.println("Leine gueltige CSV-Datei :/");
		}
		
		Mandatsrechner2009 rechner = Mandatsrechner2009.getInstance();
		
		if (w != null) {
			Bundestagswahl newW = rechner.berechneHondt(w);
			//newW = rechner.berechneAlles(newW);
			//Debug.isAktiv();
			//Debug.print(newW.getSitzverteilung().getBericht());
			
			//System.out.println(newW.getSitzverteilung().getAbgeordnete().size());
			

		}
	}
}

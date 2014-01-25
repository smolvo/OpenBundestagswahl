package mandatsrechner;

import importexport.ImportExportManager;

import java.io.File;

import test.Debug;
import model.Bundestagswahl;

public class MandatsrechnerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2009.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block	
			e1.printStackTrace();
			System.out.println("Leine g�ltige CSV-Datei :/");
		}
		
		Mandatsrechner2009 rechner = new Mandatsrechner2009();
		Debug.isAktiv();
		if (w != null) {
			Bundestagswahl newW = rechner.berechneAlles(w);
			newW = rechner.berechneAlles(newW);
			Debug.isAktiv();
			Debug.print(newW.getSitzverteilung().getBericht());
			
			//System.out.println(newW.getSitzverteilung().getAbgeordnete().size());
			

		}
	}
}

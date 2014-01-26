package test.java.importexport;

import java.io.File;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;

/**
 * 
 */
public class ImportExportTest {

	/**
	 * @param args Argumente
	 */
	public static void main(String[] args) {
		
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
			System.out.println("Leine gültige CSV-Datei :/");
		}
		
		i.exportieren("src/main/resources/importexport/Exported.csv", w);
		//System.out.println(csvDatei.getName());
		//int integer = Integer.parseInt("");
		
		System.out.println(w.getDeutschland().getWahlkreise().get(0).getAnzahlZweitstimmen());
		System.out.println(w.getDeutschland().getWahlkreise().get(0).hashCode());
		
		// clone durch Serialisierung
		System.out.println("\n\n------------  clone  ------------");
		try {
			// clone
			Bundestagswahl w2 = w.deepCopy();
			
			System.out.println(w2.getDeutschland().getWahlkreise().get(0).getAnzahlZweitstimmen());
			System.out.println(w2.getDeutschland().getWahlkreise().get(0).hashCode());
			
			// ein paar kleine Tests
			System.out.println(w.getDeutschland().getBundeslaender().getFirst().getName());
			System.out.println(w2.getDeutschland().getBundeslaender().getFirst().getName());
			
			w2.getDeutschland().getBundeslaender().getFirst().setName("BLABLA");
			
			System.out.println(w.getDeutschland().getBundeslaender().getFirst().getName());
			System.out.println(w2.getDeutschland().getBundeslaender().getFirst().getName());
			
			w2.getDeutschland().getBundeslaender().set(0, w2.getDeutschland().getBundeslaender().getLast());
			
			System.out.println(w.getDeutschland().getBundeslaender().getFirst().getName());
			System.out.println(w2.getDeutschland().getBundeslaender().getFirst().getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

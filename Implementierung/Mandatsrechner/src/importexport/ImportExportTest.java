package importexport;

import java.io.File;

import model.Bundestagswahl;

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
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w = i.importieren(csvDateien);
		
		i.exportieren("files/Exported.csv", w);
		//System.out.println(csvDatei.getName());
		//int integer = Integer.parseInt("");
		
		
		
		// clone durch Serialisierung
		System.out.println("\n\n------------  clone  ------------");
		try {
			// clone
			Bundestagswahl w2 = w.deepCopy();
			
			
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

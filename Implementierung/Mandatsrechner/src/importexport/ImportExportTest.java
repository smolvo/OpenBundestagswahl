package importexport;

import java.io.File;

import model.Bundestagswahl;

public class ImportExportTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImportExportManager i = new ImportExportManager();
		File csvDatei = new File("files/Ergebnis2013.csv");
		Bundestagswahl w = i.importieren(csvDatei);
		
		i.exportieren("files/Exported.csv",w);
		//System.out.println(csvDatei.getName());
		//int integer = Integer.parseInt("");
	}

}

package test.java.wahlgenerator;
import java.io.File;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.*;
import main.java.model.*;

public class Wahlgnerator2009Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2009.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl bundestagswahl = null;
		try {
			bundestagswahl = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Keine g�ltige CSV-Datei :/");
		}
		
		Mandatsrechner2009 rechner2009 = Mandatsrechner2009.getInstance();
		
		
		if (bundestagswahl != null) {
			Bundestagswahl newW = rechner2009.berechneSaintLague(bundestagswahl);
			Bundestagswahl newnewW = rechner2009.berechneHondt(bundestagswahl);
			//newW = m.berechneAlles(newW);
		}
	}
}

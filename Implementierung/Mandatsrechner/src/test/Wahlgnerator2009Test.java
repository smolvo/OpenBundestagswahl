package test;
import importexport.ImportExportManager;

import java.io.File;

import model.*;
import mandatsrechner.*;

public class Wahlgnerator2009Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl bundestagswahl = null;
		try {
			bundestagswahl = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Keine g�ltige CSV-Datei :/");
		}
		
		Mandatsrechner2009 rechner2009 = new Mandatsrechner2009();
		
		
		if (bundestagswahl != null) {
			Bundestagswahl newW = rechner2009.berechneAlles(bundestagswahl);
			//newW = m.berechneAlles(newW);
		}
	}
}

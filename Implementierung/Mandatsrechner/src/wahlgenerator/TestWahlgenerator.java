package wahlgenerator;

import importexport.ImportExportManager;

import java.io.File;
import java.util.LinkedList;

import model.Bundestagswahl;

public class TestWahlgenerator {

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
		
		
		// Wahlgenerierung
		
		LinkedList<Stimmanteile> stimmAnt = new LinkedList<>();
		stimmAnt.add(new Stimmanteile(w.getParteien().getFirst(), 0.3f, 0.3f));
		stimmAnt.add(new Stimmanteile(w.getParteien().getLast(), 0.7f, 0.7f));
		Wahlgenerator wg = new Wahlgenerator(w, stimmAnt);
		
		Bundestagswahl genWahl = wg.erzeugeBTW();
		
		System.out.println(w.getName());
		
	}

}

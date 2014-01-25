package main.java.wahlgenerator;

import java.io.File;
import java.util.LinkedList;

import test.java.Debug;
import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;

/**
 * Einfache Testklasse zum Wahlgenerator.
 */
public class TestWahlgenerator {

	/**
	 * Main Methode zum testen
	 * @param args Argumente
	 */
	public static void main(String[] args) {
		
		Debug.setAktiv(true);
		
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Leine gültige CSV-Datei :/");
		}
		
		
		// Wahlgenerierung
		
		LinkedList<Stimmanteile> stimmAnt = new LinkedList<>();
		stimmAnt.add(new Stimmanteile(w.getParteien().getFirst(), 0.3f, 0.3f));
		stimmAnt.add(new Stimmanteile(w.getParteien().get(3), 0.7f, 0.7f));
		Wahlgenerator wg = new Wahlgenerator(w, stimmAnt);
		
		long start = System.currentTimeMillis();
		
		Bundestagswahl genWahl = wg.erzeugeBTW();
		
		System.out.println(System.currentTimeMillis() - start + "ms Laufzeit");
		
		System.out.println(genWahl.getDeutschland().getBundeslaender().get(7).getName());
		System.out.println(genWahl.getDeutschland().getBundeslaender().get(7).getErststimmen().get(0).getAnzahl());
		
		System.out.println(w.getDeutschland().getBundeslaender().get(7).getName());
		System.out.println(w.getDeutschland().getBundeslaender().get(7).getErststimmen().get(0).getAnzahl());
		
		i.exportieren("files/random.csv", genWahl);
		
		
	}

}

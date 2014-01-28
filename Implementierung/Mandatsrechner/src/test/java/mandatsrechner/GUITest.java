package test.java.mandatsrechner;

import java.io.File;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Mandat;
import main.java.model.Partei;

public class GUITest {

	public static void main(String[] args) {
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Leine g�ltige CSV-Datei :/");
		}

		Mandatsrechner2013 m = Mandatsrechner2013.getInstance();
		Bundestagswahl newW = m.berechneAlles(w);

		for (Partei partei : newW.getParteien()) {
			System.out.println(partei.getName()
					+ ": "
					+ (partei.getAnzahlMandate(Mandat.DIREKTMANDAT) + partei
							.getAnzahlMandate(Mandat.UEBERHANGMADAT)) + " "
					+ partei.getAnzahlMandate(Mandat.UEBERHANGMADAT) + " "
					+ partei.getAnzahlMandate(Mandat.AUSGLEICHSMANDAT) + " "
					+ partei.getAnzahlMandate(Mandat.LISTENMANDAT) + " "
					+ partei.getAnzahlMandate());
		}
	}

}

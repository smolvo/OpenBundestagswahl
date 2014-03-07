package test.java.mandatsrechner;

import java.io.File;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Mandat;
import main.java.model.Partei;

public class GUITest {

	public static void main(String[] args) {
		final ImportExportManager i = new ImportExportManager();
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (final Exception e1) {

			e1.printStackTrace();
			System.out.println("Leine gï¿½ltige CSV-Datei :/");
		}

		final Mandatsrechner2013 m = Mandatsrechner2013.getInstance();
		final Bundestagswahl newW = m.berechne(w);

		for (final Partei partei : newW.getParteien()) {
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

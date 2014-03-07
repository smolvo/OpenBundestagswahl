package test.java.importexport;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;
import main.java.wahlgenerator.Stimmanteile;
import main.java.wahlgenerator.Wahlgenerator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class ImportExportTest {

	private static ImportExportManager i;
	private static String[] filePaths;

	/**
	 * @throws java.lang.Exception
	 *             Eine Ausnahme.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ImportExportTest.i = new ImportExportManager();
		ImportExportTest.filePaths = new String[8];
		ImportExportTest.filePaths[0] = "src/main/resources/importexport/Ergebnis2013.csv";
		ImportExportTest.filePaths[1] = "src/main/resources/importexport/Wahlbewerber2013.csv";
		ImportExportTest.filePaths[2] = "src/main/resources/importexport/Exported.csv";
		ImportExportTest.filePaths[3] = "src/main/resources/importexport/Exported2.csv";
		ImportExportTest.filePaths[4] = "src/main/resources/importexport/random.csv";
		ImportExportTest.filePaths[5] = "src/main/resources/config.csv";

		ImportExportTest.filePaths[6] = "src/main/resources/importexport/generierteWahl.csv";

		ImportExportTest.filePaths[7] = "src/main/resources/importexport/Ergebnis2013-ISO.csv";
	}

	/**
	 * @throws java.lang.Exception
	 *             exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// nur für export Test benötigte Dateien wieder löschen
		new File(ImportExportTest.filePaths[2]).delete();
		new File(ImportExportTest.filePaths[3]).delete();
		new File(ImportExportTest.filePaths[4]).delete();
		new File(ImportExportTest.filePaths[6]).delete();
	}

	/**
	 * Basistest. Testet das Import/Export Modul auf Funktionalitaet.
	 */
	@Test
	public void basisTest() {
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(ImportExportTest.filePaths[0]);
		csvDateien[1] = new File(ImportExportTest.filePaths[1]);

		Bundestagswahl w = null;

		w = ImportExportTest.i.importieren(csvDateien);

		assertNotNull(w);

		final boolean result = ImportExportTest.i.exportieren(
				ImportExportTest.filePaths[2], w);
		assertTrue(result);
	}

	/**
	 * Importiert eine Datei, Exportiert sie, importiert sie dann erneut und
	 * exportiert sie dann nochmal.
	 */
	@Test
	public void doubleImportExportTest() {
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(ImportExportTest.filePaths[0]);
		csvDateien[1] = new File(ImportExportTest.filePaths[1]);

		Bundestagswahl w = null;

		w = ImportExportTest.i.importieren(csvDateien);

		assertNotNull(w);

		boolean result = ImportExportTest.i.exportieren(
				ImportExportTest.filePaths[2], w);

		assertTrue(result);

		csvDateien[0] = new File(ImportExportTest.filePaths[2]);
		csvDateien[1] = new File(ImportExportTest.filePaths[1]);

		w = null;

		w = ImportExportTest.i.importieren(csvDateien);

		assertNotNull(w);

		result = ImportExportTest.i.exportieren(ImportExportTest.filePaths[3],
				w);

		assertTrue(result);

	}

	/**
	 * 
	 */
	@Test
	public void exportGenerierteWahlTest() {

		/*
		 * Importiere Wahl 2013 wie im BasisTest
		 */
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(ImportExportTest.filePaths[0]);
		csvDateien[1] = new File(ImportExportTest.filePaths[1]);
		Bundestagswahl wahl2013 = null;
		wahl2013 = ImportExportTest.i.importieren(csvDateien);

		/*
		 * Generiere eine neue Wahl basierend auf dieser Dieser Code wird
		 * bereits im WahlgeneratorTest getestet
		 */
		final LinkedList<Stimmanteile> anteile = new LinkedList<>();
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("CDU"), 40, 40));
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("SPD"), 30, 30));
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("GRÜNE"), 20, 20));
		anteile.add(new Stimmanteile(wahl2013.getParteiByName("FDP"), 10, 10));
		final Wahlgenerator wg = new Wahlgenerator(wahl2013, anteile);
		final Bundestagswahl generierteWahl = wg.erzeugeBTW("Test");

		ImportExportTest.i.exportieren(ImportExportTest.filePaths[6],
				generierteWahl);
		csvDateien[0] = new File(ImportExportTest.filePaths[6]);
		final Bundestagswahl importierteGenerierteWahl = ImportExportTest.i
				.importieren(csvDateien);

		assertNotNull("Importierte Wahl ist Null!", importierteGenerierteWahl);
	}

	/**
	 * 
	 */
	@Test
	public void importOtherCharsetFile() {
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(ImportExportTest.filePaths[7]);
		csvDateien[1] = new File(ImportExportTest.filePaths[1]);
		ImportExportTest.i.importieren(csvDateien);
	}

	/**
	 * @throws java.lang.Exception
	 *             exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 *             exception
	 */
	@After
	public void tearDown() throws Exception {

	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void ungueltigeWahlbewerberDateiTest() {
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(ImportExportTest.filePaths[0]);
		csvDateien[1] = new File(ImportExportTest.filePaths[0]);

		final Bundestagswahl w = ImportExportTest.i.importieren(csvDateien);
		assertNotNull(w);

		// boolean result = i.exportieren(filePaths[2], w);
		// assertTrue(result);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void ungueltigeWahlDateiTest() {
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(ImportExportTest.filePaths[5]);
		csvDateien[1] = new File(ImportExportTest.filePaths[1]);
		Bundestagswahl w = null;
		w = ImportExportTest.i.importieren(csvDateien);
		assertNull(w);
	}

	/**
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void ungueltigeWahlUndWahlbewerberDateiTest() {
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(ImportExportTest.filePaths[5]);
		csvDateien[1] = new File(ImportExportTest.filePaths[0]);

		Bundestagswahl w = null;
		w = ImportExportTest.i.importieren(csvDateien);

		assertNull(w);
	}

}

package test.java.chronik;

import static org.junit.Assert.assertTrue;

import java.io.File;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.java.Debug;

/**
 * Eine Klasse zum Testen der Chronik.
 * @author 13genesis37
 *
 */
public class ChronikTest {
	private static Bundestagswahl btw = null;
	
	/**
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		Debug.setLevel(5);

		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		btw = null;
		try {
			btw = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Leine gï¿½ltige CSV-Datei :/");
		}
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Basistest.
	 */
	@Test
	public final void test() {
		assertTrue(true);
	}
}

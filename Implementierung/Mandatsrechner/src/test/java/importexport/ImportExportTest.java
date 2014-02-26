package test.java.importexport;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.importexport.ImportExportManager;
import main.java.model.Bundestagswahl;

/**
 * 
 */
public class ImportExportTest {

	private static ImportExportManager i;
	private static String[] filePaths;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 i = new ImportExportManager();
		 filePaths = new String[5];
		 filePaths[0] = "src/main/resources/importexport/Ergebnis2013.csv";
		 filePaths[1] = "src/main/resources/importexport/Wahlbewerber2013.csv";
		 filePaths[2] = "src/main/resources/importexport/Exported.csv";
		 filePaths[3] = "src/main/resources/importexport/Exported2.csv";
		 filePaths[4] = "src/main/resources/importexport/random.csv";
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
	 * Basistest. Testet das Import/Export Modul auf 
	 * Funktionalitaet.
	 */
	@Test
	public void basisTest() {
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(filePaths[0]);
		csvDateien[1] = new File(filePaths[1]);
		
		Bundestagswahl w = null;

		try {
			w = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(w);
		
		boolean result = i.exportieren(filePaths[2], w);
		assertTrue(result);
	}
	
	/**
	 * Importiert eine Datei, Exportiert sie,
	 * importiert sie dann erneut und exportiert
	 * sie dann nochmal.
	 */
	@Test
	public void doubleImportExportTest() {
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(filePaths[0]);
		csvDateien[1] = new File(filePaths[1]);
		
		Bundestagswahl w = null;

		try {
			w = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(w);
		
		boolean result = i.exportieren(filePaths[2], w);
		
		assertTrue(result);
		
		csvDateien[0] = new File(filePaths[2]);
		csvDateien[1] = new File(filePaths[1]);
		
		
		w = null;

		try {
			w = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(w);
		
		result = i.exportieren(filePaths[3], w);
		
		assertTrue(result);
		
	}

}

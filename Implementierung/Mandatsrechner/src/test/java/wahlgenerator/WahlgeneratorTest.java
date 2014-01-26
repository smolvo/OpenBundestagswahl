/**
 * 
 */
package test.java.wahlgenerator;

import static org.junit.Assert.*;

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

import test.java.Debug;

/**
 * @author Simon
 *
 */
public class WahlgeneratorTest {

	private static Bundestagswahl wahl1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Debug.setAktiv(true);
		
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File("src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl1 = null;
		try {
			wahl1 = i.importieren(csvDateien);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Leine gültige CSV-Datei :/");
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
	 * Test method for {@link main.java.wahlgenerator.Wahlgenerator#verteileStimmen(model.Bundestagswahl)}.
	 */
	@Test
	public final void testVerteileStimmen() {
		
		// Wahlgenerierung
		
				LinkedList<Stimmanteile> stimmAnt = new LinkedList<>();
				stimmAnt.add(new Stimmanteile(wahl1.getParteien().getFirst(), 0.3f, 0.3f));
				stimmAnt.add(new Stimmanteile(wahl1.getParteien().get(3), 0.5f, 0.7f));
				Wahlgenerator wg = new Wahlgenerator(wahl1, stimmAnt);
				
				long start = System.currentTimeMillis();
				
				Bundestagswahl genWahl = wg.erzeugeBTW();
				
				System.out.println(System.currentTimeMillis() - start + "ms Laufzeit");
				
				System.out.println(genWahl.getDeutschland().getBundeslaender().get(7).getName());
				System.out.println(genWahl.getDeutschland().getBundeslaender().get(7).getErststimmen().get(0).getAnzahl());
				
				System.out.println(wahl1.getDeutschland().getBundeslaender().get(7).getName());
				System.out.println(wahl1.getDeutschland().getBundeslaender().get(7).getErststimmen().get(0).getAnzahl());
				
				//i.exportieren("src/main/resources/importexport/random.csv", genWahl);
		
	}

}

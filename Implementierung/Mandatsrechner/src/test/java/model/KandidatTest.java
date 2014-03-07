/**
 * 
 */
package test.java.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import main.java.importexport.ImportExportManager;
import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Die erwarteten Testergebnisse basieren auf die, aus der csv- Datei zur
 * Bundestagswahl 2013 stammenden, Daten
 * 
 * 
 * Die Einwohnerzahlen stammen aus der im Programm bereitgestellten config-
 * Datei
 */
@RunWith(Parameterized.class)
public class KandidatTest {

	private static Bundestagswahl ausgangsWahl;
	private static Bundestagswahl testWahl;
	private static Bundesland testBundesland;
	private static List<Bundesland> testBundeslaender;
	private static Kandidat testKandidat;

	// creates the test data
	@Parameters
	public static Collection<Object[]> data() {
		final Object[][] data = new Object[][] { { "" }, { null } };
		return Arrays.asList(data);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final ImportExportManager i = new ImportExportManager();
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");

		try {
			KandidatTest.ausgangsWahl = i.importieren(csvDateien);
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.out.println("Keine gï¿½ltige CSV-Datei :/");
		}

		Mandatsrechner2013.getInstance().berechne(KandidatTest.ausgangsWahl);

	}

	private final String ungueltigerParameter;

	private final String gueltigerParameter = "test";

	public KandidatTest(String parameter) {
		this.ungueltigerParameter = parameter;

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		KandidatTest.testWahl = KandidatTest.ausgangsWahl.deepCopy();
		KandidatTest.testBundeslaender = KandidatTest.testWahl.getDeutschland()
				.getBundeslaender();
		// Testbundesland ist Schleswig-Holstein
		KandidatTest.testBundesland = KandidatTest.testBundeslaender.get(0);
		KandidatTest.testBundesland.getWahlkreise();
		KandidatTest.testKandidat = KandidatTest.testWahl.getParteien()
				.getFirst().getLandesliste().get(0).getListenkandidaten()
				.get(0);
	}

	@Test
	public void testEquals() {
		final Kandidat kand1 = new Kandidat("Merkel", "Manfred", 1963,
				Mandat.DIREKTMANDAT, KandidatTest.testKandidat.getPartei());
		final Kandidat kand2 = new Kandidat("Merkel", "Momo", 1963,
				Mandat.DIREKTMANDAT, KandidatTest.testKandidat.getPartei());

		assertEquals(false, kand1.equals(kand2));
	}

	@Test
	public void testKonstruktor() {

		final Kandidat kand = new Kandidat("Merkel", "Manfred", 1963,
				Mandat.DIREKTMANDAT, KandidatTest.testKandidat.getPartei());

		assertEquals("Manfred", kand.getVorname());
		assertEquals("Merkel", kand.getName());
		assertEquals("Merkel", kand.getName());
		assertEquals(Mandat.DIREKTMANDAT, kand.getMandat());
		assertEquals(KandidatTest.testKandidat.getPartei(), kand.getPartei());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testKonstruktor2() {
		new Kandidat("Merkel", "Manfred", 1963, Mandat.DIREKTMANDAT,
				KandidatTest.testKandidat.getPartei(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetErststimme() {
		KandidatTest.testKandidat.setErststimme(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInfo() {
		KandidatTest.testKandidat.setInfo(this.ungueltigerParameter,
				this.gueltigerParameter, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInfo2() {
		KandidatTest.testKandidat.setInfo(this.gueltigerParameter,
				this.ungueltigerParameter, 0);
	}

}

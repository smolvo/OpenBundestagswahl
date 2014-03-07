package test.java.wahlvergleich;

import static org.junit.Assert.assertEquals;

import java.io.File;

import main.java.model.Bundestagswahl;
import main.java.steuerung.Steuerung;
import main.java.wahlvergleich.Wahlvergleich;
import main.java.wahlvergleich.WahlvergleichDaten;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Diese Klasse vergleicht die Bundestagswahl 2013 mit der 2009. Orakel wurde
 * manuell errechnet.
 * 
 */
public class WahlvergleichTest2 {

	/** repräsentiert die Wahl2013 */
	private static Bundestagswahl wahl1;

	/** repräsentiert die Wahl 2009 */
	private static Bundestagswahl wahl2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		final File[] wahl13 = new File[2];
		final File[] wahl09 = new File[2];
		wahl13[0] = new File("src/main/resources/importexport/Ergebnis2013.csv");
		wahl13[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl09[0] = new File("src/main/resources/importexport/Ergebnis2009.csv");
		wahl09[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		WahlvergleichTest2.wahl1 = Steuerung.getInstance().importieren(wahl13);
		WahlvergleichTest2.wahl2 = Steuerung.getInstance().importieren(wahl09);
	}

	/** repräsentiert die Daten des Wahlvergleichs */
	private WahlvergleichDaten daten;

	@Test
	public void erstimmenDiffTest() {
		assertEquals("770", this.daten.getDiffErst(0));
		assertEquals("-61969", this.daten.getDiffErst(1));
		assertEquals("0", this.daten.getDiffErst(2));
		assertEquals("-13370", this.daten.getDiffErst(3));
		assertEquals("-3278", this.daten.getDiffErst(4));
		assertEquals("-16906", this.daten.getDiffErst(5));
		assertEquals("-9971", this.daten.getDiffErst(6));
		assertEquals("-4608", this.daten.getDiffErst(7));
		assertEquals("920", this.daten.getDiffErst(8));
		assertEquals("-802", this.daten.getDiffErst(9));
		assertEquals("-3894", this.daten.getDiffErst(10));
		assertEquals("-2762", this.daten.getDiffErst(11));
		assertEquals("-133307", this.daten.getDiffErst(12));
		assertEquals("916853", this.daten.getDiffErst(13));
		assertEquals("-3047851", this.daten.getDiffErst(14));
		assertEquals("353079", this.daten.getDiffErst(15));
		assertEquals("-796826", this.daten.getDiffErst(16));
	}

	@Before
	public void setUp() throws Exception {
		final Wahlvergleich vergleich = new Wahlvergleich(
				WahlvergleichTest2.wahl1, WahlvergleichTest2.wahl2);
		this.daten = vergleich.wahlvergleich();
	}

	@After
	public void tearDown() throws Exception {
		this.daten = null;
	}

	@Test
	public void zweitstimmenDiffTest() {
		assertEquals("-1894", this.daten.getDiffZweit(0));
		assertEquals("0", this.daten.getDiffZweit(1));
		assertEquals("1607", this.daten.getDiffZweit(2));
		assertEquals("-113269", this.daten.getDiffZweit(3));
		assertEquals("-23746", this.daten.getDiffZweit(4));
		assertEquals("-25892", this.daten.getDiffZweit(5));
		assertEquals("-21828", this.daten.getDiffZweit(6));
		assertEquals("-5042", this.daten.getDiffZweit(7));
		assertEquals("-31265", this.daten.getDiffZweit(8));
		assertEquals("5639", this.daten.getDiffZweit(9));
		assertEquals("9084", this.daten.getDiffZweit(10));
		assertEquals("-102203", this.daten.getDiffZweit(11));
		assertEquals("-74697", this.daten.getDiffZweit(12));
		assertEquals("111307", this.daten.getDiffZweit(13));
		assertEquals("-4232547", this.daten.getDiffZweit(14));
		assertEquals("413331", this.daten.getDiffZweit(15));
		assertEquals("-949215", this.daten.getDiffZweit(16));
	}

}

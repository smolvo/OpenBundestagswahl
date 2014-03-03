package test.java.wahlvergleich;

import static org.junit.Assert.*;

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
 * Diese Klasse vergleich die Bundestagswahl 2013 mit der 2009.
 * Orakel wurde manuell errechnet.
 *
 */
public class WahlvergleichTest2 {

	/** repräsentiert die Wahl2013 */
	private static Bundestagswahl wahl1;
	
	/** repräsentiert die Wahl 2009 */
	private static Bundestagswahl wahl2;
	
	/** repräsentiert die Daten des Wahlvergleichs */
	private WahlvergleichDaten daten;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		File[] wahl13 = new File[2];
		File[] wahl09 = new File[2];
		wahl13[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		wahl13[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl09[0] = new File(
				"src/main/resources/importexport/Ergebnis2009.csv");
		wahl09[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl1 = Steuerung.getInstance().importieren(wahl13);
		wahl2 = Steuerung.getInstance().importieren(wahl09);		
	}
	
	@Before
	public void setUp() throws Exception {
		Wahlvergleich vergleich = new Wahlvergleich(wahl1, wahl2);
		this.daten = vergleich.wahlvergleich();
	}
	
	@After
	public void tearDown() throws Exception {
		this.daten = null;
	}
	
	@Test
	public void erstimmenDiffTest() {
		assertEquals("770", daten.getDiffErst(0));
		assertEquals("-61969", daten.getDiffErst(1));
		assertEquals("0", daten.getDiffErst(2));
		assertEquals("-13370", daten.getDiffErst(3));
		assertEquals("-3278", daten.getDiffErst(4));
		assertEquals("-16906", daten.getDiffErst(5));
		assertEquals("-9971", daten.getDiffErst(6));
		assertEquals("-4608", daten.getDiffErst(7));
		assertEquals("920", daten.getDiffErst(8));
		assertEquals("-802", daten.getDiffErst(9));
		assertEquals("-3894", daten.getDiffErst(10));
		assertEquals("-2762", daten.getDiffErst(11));
		assertEquals("-133307", daten.getDiffErst(12));
		assertEquals("916853", daten.getDiffErst(13));
		assertEquals("-3047851", daten.getDiffErst(14));
		assertEquals("353079", daten.getDiffErst(15));
		assertEquals("-796826", daten.getDiffErst(16));
	}
	
	@Test
	public void zweitstimmenDiffTest() {
		assertEquals("-1894", daten.getDiffZweit(0));
		assertEquals("0", daten.getDiffZweit(1));
		assertEquals("1607", daten.getDiffZweit(2));
		assertEquals("-113269", daten.getDiffZweit(3));
		assertEquals("-23746", daten.getDiffZweit(4));
		assertEquals("-25892", daten.getDiffZweit(5));
		assertEquals("-21828", daten.getDiffZweit(6));
		assertEquals("-5042", daten.getDiffZweit(7));
		assertEquals("-31265", daten.getDiffZweit(8));
		assertEquals("5639", daten.getDiffZweit(9));
		assertEquals("9084", daten.getDiffZweit(10));
		assertEquals("-102203", daten.getDiffZweit(11));
		assertEquals("-74697", daten.getDiffZweit(12));
		assertEquals("111307", daten.getDiffZweit(13));
		assertEquals("-4232547", daten.getDiffZweit(14));
		assertEquals("413331", daten.getDiffZweit(15));
		assertEquals("-949215", daten.getDiffZweit(16));
	}
	
}

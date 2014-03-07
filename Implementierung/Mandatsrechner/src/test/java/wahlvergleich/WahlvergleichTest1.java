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
 * Diese Klasse testet den Wahlvergleich. Es werden hier zwei identische Wahlen
 * verglichen.
 * 
 */
public class WahlvergleichTest1 {

	/** repräsentiert die unverfälschte Wahl2013 */
	private static Bundestagswahl wahl1;

	/** repräsentiert die unverfälschte Wahl2013 */
	private static Bundestagswahl wahl2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		WahlvergleichTest1.wahl1 = Steuerung.getInstance().importieren(
				csvDateien);
		WahlvergleichTest1.wahl2 = Steuerung.getInstance().importieren(
				csvDateien);
	}

	/** rerpäsentiert die temporäre Wahl 1, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl1;

	/** rerpäsentiert die temporäre Wahl 2, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl2;

	@Before
	public void setUp() throws Exception {
		this.cloneWahl1 = WahlvergleichTest1.wahl1.deepCopy();
		this.cloneWahl2 = WahlvergleichTest1.wahl2.deepCopy();
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl1 = null;
		this.cloneWahl2 = null;
	}

	@Test
	public void wahlvergleichTest() {
		final Wahlvergleich vergleich = new Wahlvergleich(this.cloneWahl1,
				this.cloneWahl2);
		vergleich.getBtw1();
		vergleich.getBtw2();
		final WahlvergleichDaten daten = vergleich.wahlvergleich();

		// vergleiche die Anzahl der Erststimmen
		for (int i = 0; i < daten.size(); i++) {
			assertEquals(daten.getAnzahlEinsErst(i), daten.getAnzahlZweiErst(i));
		}

		// vergleich die Anzahl Zweitstimmen
		for (int i = 0; i < daten.size(); i++) {
			assertEquals(daten.getAnzahlEinsZweit(i),
					daten.getAnzahlZweiZweit(i));
		}

		// vergleiche die Anzahl prozentualer Erststimmen
		for (int i = 0; i < daten.size(); i++) {
			assertEquals(daten.getProzentEinsErst(i),
					daten.getProzentZweiErst(i));
		}

		// vergleiche die Anzahl prozentualer Zweitstimmen
		for (int i = 0; i < daten.size(); i++) {
			assertEquals(daten.getProzentEinsZweit(i),
					daten.getProzentZweiZweit(i));
		}

		// vergleiche die Differenzen
		for (int i = 0; i < daten.size(); i++) {
			assertEquals("0", daten.getDiffErst(i));
			assertEquals("0", daten.getDiffZweit(i));
		}

		// letztes Listenelement ist die CDU
		assertEquals("CDU", daten.getParteien(daten.size() - 1));
	}
}
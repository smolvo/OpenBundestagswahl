package test.java.model;

import static org.junit.Assert.assertEquals;

import java.io.File;

import main.java.model.Bundestagswahl;
import main.java.model.Erststimme;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Diese Klasse testet die Funktion der Wahlkreis-Klasse.
 * 
 */
public class WahlkreisTest {

	/** repräsentiert die unverfälschte Wahl2013 */
	private static Bundestagswahl wahl;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		WahlkreisTest.wahl = Steuerung.getInstance().importieren(csvDateien);
	}

	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;

	/**
	 * Testet die Erststimmenanzahl
	 */
	@Test
	public void getAnzahlErststimmenTest() {
		// Wahlkreis: Flensburg - Schleswig
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		assertEquals(160526, wk.getAnzahlErststimmen());
	}

	/**
	 * Testet die Zweitstimmenanzahl für eine Partei
	 */
	@Test
	public void getAnzahlZweitstimmenTest() {
		// Wahlkreis: Nordfriesland - Dithmarschen Nord
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(1);
		final Partei spd = wk.getZweitstimmenProPartei().get(1).getPartei();
		assertEquals(38590, wk.getAnzahlZweitstimmen(spd));
	}

	/**
	 * Testet die Erststimmenanzahl für eine bestimmte Partei
	 */
	@Test
	public void getErststimmenAnzahlTest() {
		// Wahlkreis: Hamburg-Mitte
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(1).getWahlkreise().get(0);
		final Partei cdu = wk.getErststimmenProPartei().get(0).getKandidat()
				.getPartei();
		assertEquals(46753, wk.getAnzahlErststimmen(cdu));
	}

	/**
	 * Testet Erstimmenausgabe für eine Partei
	 */
	@Test
	public void getErststimmeTest() {
		// Wahlkreis: Nordfriesland - Dithmarschen Nord
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(1);
		final Partei spd = wk.getZweitstimmenProPartei().get(1).getPartei();
		final Erststimme spdErst = wk.getErststimme(spd);
		assertEquals(41714, spdErst.getAnzahl());
	}

	/**
	 * Testet Zweitstimmenausgabe für eine Partei
	 */
	@Test
	public void getZweitstimmeTest() {
		// Wahlkreis: Nordfriesland - Dithmarschen Nord
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(1);
		final Partei spd = wk.getZweitstimmenProPartei().get(1).getPartei();
		final Zweitstimme spdErst = wk.getZweitstimme(spd);
		assertEquals(38590, spdErst.getAnzahl());
	}

	/**
	 * Alle nachfolgenden Tests sind Exceptionwruf bei null-Eingaben
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getZweitstimmeTestFail() {
		// Wahlkreis: Nordfriesland - Dithmarschen Nord
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(1);
		wk.getZweitstimme(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void konstruktorTest1() {
		new Wahlkreis(null, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void konstruktorTest2() {
		new Wahlkreis("Südpfalz", -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void konstruktorTest3() {
		new Wahlkreis("Südpfalz", 11, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setErststimmenFail() {
		// Wahlkreis: Flensburg - Schleswig
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		wk.setErststimmen(null);
	}

	@Before
	public void setUp() throws Exception {
		this.cloneWahl = WahlkreisTest.wahl.deepCopy();
	}

	@Test(expected = IllegalArgumentException.class)
	public void setWahlberechtigteFail() {
		// Wahlkreis: Flensburg - Schleswig
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		wk.setWahlberechtigte(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setWahlkreisnummerFail() {
		// Wahlkreis: Flensburg - Schleswig
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		wk.setWahlkreisnummer(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setWahlkreisSiegerFail() {
		// Wahlkreis: Flensburg - Schleswig
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		wk.setWahlkreisSieger(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setZweitstimmenFail() {
		// Wahlkreis: Flensburg - Schleswig
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		wk.setZweitstimmen(null);
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}

}

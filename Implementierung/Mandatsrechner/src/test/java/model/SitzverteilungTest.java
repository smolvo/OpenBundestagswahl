package test.java.model;

import static org.junit.Assert.assertEquals;

import java.io.File;

import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SitzverteilungTest {

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
		SitzverteilungTest.wahl = Steuerung.getInstance().importieren(
				csvDateien);
	}

	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;

	/**
	 * Testet ob 2 Parteien die korrekte Sitzanzahl haben
	 */
	@Test
	public void getAnzahlSitze() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final Partei spd = this.cloneWahl.getParteien().get(1);
		assertEquals(255, this.cloneWahl.getSitzverteilung()
				.getAnzahlSitze(cdu));
		assertEquals(193, this.cloneWahl.getSitzverteilung()
				.getAnzahlSitze(spd));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setAbgeordneteFail() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		this.cloneWahl.getSitzverteilung().setAbgeordnete(null);
	}

	/**
	 * Alle nachfolgenden Tests sind Exceptionwruf bei null-Eingaben
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setBerichtFail() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		this.cloneWahl.getSitzverteilung().setBericht(null);
	}

	@Before
	public void setUp() throws Exception {
		this.cloneWahl = SitzverteilungTest.wahl.deepCopy();
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}
}

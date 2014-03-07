package test.java.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.util.LinkedList;

import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.model.Sitzverteilung;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BundestagswahlTest {

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
		BundestagswahlTest.wahl = Steuerung.getInstance().importieren(
				csvDateien);
	}

	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;

	@Test
	public void getParteienTest() {
		final LinkedList<Partei> parteien = this.cloneWahl.getParteien();
		assertEquals(35, parteien.size());
	}

	@Test
	public void getPartyByNameTest() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final Partei part = this.cloneWahl.getParteiByName("CDU");
		assertEquals(cdu, part);
	}

	@Test
	public void nameTest() {
		assertEquals("Bundestagswahl 2013", this.cloneWahl.getName());
		this.cloneWahl.setName("hallo");
		assertEquals("hallo", this.cloneWahl.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setDeutschlandFail() {
		this.cloneWahl.setDeutschland(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setParteienFail() {
		this.cloneWahl.setParteien(null);
	}

	@Before
	public void setUp() throws Exception {
		this.cloneWahl = BundestagswahlTest.wahl.deepCopy();
	}

	@Test
	public void sitzplatzverteilungTest() {
		final Sitzverteilung vert = this.cloneWahl.getSitzverteilung();
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		assertNotEquals(vert, this.cloneWahl.getSitzverteilung());
		this.cloneWahl.setSitzverteilung(vert);
		assertEquals(vert, this.cloneWahl.getSitzverteilung());
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetName() {
		this.cloneWahl.setName(null);
	}
}

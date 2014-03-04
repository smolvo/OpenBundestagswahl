package test.java.model;

import static org.junit.Assert.*;

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
	
	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		wahl = Steuerung.getInstance().importieren(csvDateien);
	}
	
	@Before
	public void setUp() throws Exception {
		this.cloneWahl = wahl.deepCopy();
	}
	
	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}

	@Test
	public void nameTest() {
		assertEquals("Bundestagswahl 2013", cloneWahl.getName());
		cloneWahl.setName("hallo");
		assertEquals("hallo", cloneWahl.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setDeutschlandFail() {
		cloneWahl.setDeutschland(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setParteienFail() {
		cloneWahl.setParteien(null);
	}
	
	@Test
	public void getParteienTest() {
		LinkedList<Partei> parteien = cloneWahl.getParteien();
		assertEquals(35, parteien.size());
	}
	
	@Test
	public void sitzplatzverteilungTest() {
		Sitzverteilung vert = this.cloneWahl.getSitzverteilung();
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		assertNotEquals(vert, this.cloneWahl.getSitzverteilung());
		this.cloneWahl.setSitzverteilung(vert);
		assertEquals(vert, this.cloneWahl.getSitzverteilung());
	}
	
	@Test
	public void getPartyByNameTest() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		Partei part = this.cloneWahl.getParteiByName("CDU");
		assertEquals(cdu, part);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetName() {
		cloneWahl.setName(null);
	}
}

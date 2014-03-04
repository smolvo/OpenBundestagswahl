package test.java.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Landesliste;
import main.java.model.Partei;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Diese Klasse testet die Methoden der Partei-Klasse.
 *
 */
public class ParteiTest {
	
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
	public void addLandesListeTest() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		List<Landesliste> listen = cdu.getLandesliste();
		Landesliste liste = this.cloneWahl.getParteien().get(1).getLandesliste().get(3);
		boolean enthalten = false;
		for (Landesliste list : listen) {
			if (list.equals(liste)) {
				enthalten = true;
			}
		}
		assertFalse(enthalten);
		
		cdu.addLandesliste(liste);
		
		for (Landesliste list : listen) {
			if (list.equals(liste)) {
				enthalten = true;
			}
		}
		assertTrue(enthalten);
	}
	
	@Test
	public void setLandesListetest() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		List<Landesliste> liste = new LinkedList<Landesliste>();
		Landesliste landesliste = cdu.getLandesliste().get(0);
		liste.add(landesliste);
		cdu.setLandesliste(liste);
		assertEquals(1, cdu.getLandesliste().size());
		assertEquals(landesliste, cdu.getLandesliste());
	}
}

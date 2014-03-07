package test.java.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;

import main.java.mandatsrechner.Mandatsrechner2009;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Landesliste;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Diese Klasse testet die zwei Methoden der Landesliste.
 * 
 */
public class LandeslisteTest {

	/** repräsentiert die unverfälschte Wahl2013 */
	private static Bundestagswahl wahl;

	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;

	public static Landesliste testLandesliste;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		LandeslisteTest.wahl = Steuerung.getInstance().importieren(csvDateien);
	}

	@Test
	public void addKandidatTest() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final Kandidat amann = this.cloneWahl.getParteien().get(1)
				.getMitglieder().get(0);
		final Landesliste holstein = cdu.getLandesliste().get(0);
		// Amann will in Schleswig-Holstein für die CDU kandidieren!!
		holstein.addKandidat(0, amann);
		assertEquals(amann, holstein.getListenkandidaten().get(0));
	}

	@Test
	public void getKandidatenTest() {
		final Mandatsrechner2009 rechner = Mandatsrechner2009.getInstance();
		// hole Herrn Wellenreuther
		final Kandidat wellenreuther = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(7).getWahlkreise().get(13)
				.getErststimmenProPartei().get(0).getKandidat();
		rechner.initialisiere(this.cloneWahl);
		rechner.testBerechneDirektmandat(this.cloneWahl);
		final Landesliste bw = this.cloneWahl.getParteien().get(0)
				.getLandesliste().get(7);
		final LinkedList<Kandidat> direkte = bw
				.getKandidaten(Mandat.DIREKTMANDAT);
		boolean isDirekt = false;
		for (final Kandidat kan : direkte) {
			if (kan.equals(wellenreuther)) {
				isDirekt = true;
			}
		}
		assertTrue(isDirekt);
	}

	@Before
	public void setUp() throws Exception {
		this.cloneWahl = LandeslisteTest.wahl.deepCopy();
		LandeslisteTest.testLandesliste = this.cloneWahl.getParteien().get(0)
				.getLandesliste().get(0);
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetBundesland() {
		LandeslisteTest.testLandesliste.setBundesland(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetListenkandidaten1() {
		LandeslisteTest.testLandesliste.setListenkandidaten(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetListenkandidaten2() {
		final LinkedList<Kandidat> listenkandidaten = new LinkedList<Kandidat>();
		LandeslisteTest.testLandesliste.setListenkandidaten(listenkandidaten);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetMindestsitzanzahl1() {
		LandeslisteTest.testLandesliste.setMindestSitzanzahl(-1);
	}

	@Test
	public void testSetMindestsitzanzahl2() {
		LandeslisteTest.testLandesliste.setMindestSitzanzahl(0);
		assertEquals(0, LandeslisteTest.testLandesliste.getMindestSitzanzahl());
	}

	@Test
	public void testSetMindestsitzanzahl3() {
		LandeslisteTest.testLandesliste.setMindestSitzanzahl(10);
		assertEquals(10, LandeslisteTest.testLandesliste.getMindestSitzanzahl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPartei() {
		LandeslisteTest.testLandesliste.setPartei(null);
	}
}

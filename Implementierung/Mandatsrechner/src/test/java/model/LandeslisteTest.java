package test.java.model;

import static org.junit.Assert.*;

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
	public void addKandidatTest() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		Kandidat amann = this.cloneWahl.getParteien().get(1).getMitglieder().get(0);
		Landesliste holstein = cdu.getLandesliste().get(0);
		// Amann will in Schleswig-Holstein für die CDU kandidieren!!
		holstein.addKandidat(0, amann);
		assertEquals(amann, holstein.getListenkandidaten().get(0));
	}
	
	@Test
	public void getKandidatenTest() {
		Mandatsrechner2009 rechner = Mandatsrechner2009.getInstance();
		// hole Herrn Wellenreuther
		Kandidat wellenreuther = cloneWahl.getDeutschland().getBundeslaender().get(7).getWahlkreise().get(13).getErststimmenProPartei().get(0).getKandidat();
		rechner.initialisiere(cloneWahl);
		rechner.testBerechneDirektmandat(cloneWahl);
		Landesliste bw = this.cloneWahl.getParteien().get(0).getLandesliste().get(7);
		LinkedList<Kandidat> direkte = bw.getKandidaten(Mandat.DIREKTMANDAT);
		boolean isDirekt = false;
		for (Kandidat kan : direkte) {
			if (kan.equals(wellenreuther)) {
				isDirekt = true;
			}
		}
		assertTrue(isDirekt);
	}

}

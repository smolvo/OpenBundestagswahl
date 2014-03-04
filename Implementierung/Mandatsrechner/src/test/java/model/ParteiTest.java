package test.java.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Landesliste;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;
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
		Partei cduNeu = new Partei(liste, cdu.getName(),cdu.getFarbe());
		cdu.setLandesliste(liste);
		assertEquals(1, cdu.getLandesliste().size());
		assertEquals(cdu.getLandesliste(), cduNeu.getLandesliste());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setMitgliederFail() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setMitglieder(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addMitgliedFail() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.addMitglied(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameFail1() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setName(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameFail2() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setName("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getColorFail() {
		Partei z = this.cloneWahl.getParteien().get(32);
		z.setFarbe(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setLandeslisteFail() {
		Partei z = this.cloneWahl.getParteien().get(32);
		z.setLandesliste(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getAnzahlMandateFail1() {
		Partei z = this.cloneWahl.getParteien().get(32);
		Bundesland land = cloneWahl.getDeutschland().getBundeslaender().get(0);
		z.getAnzahlMandate(null, land);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getAnzahlMandateFail2() {
		Partei z = this.cloneWahl.getParteien().get(32);
		z.getAnzahlMandate(Mandat.DIREKTMANDAT, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getLandeslisteFail() {
		Partei z = this.cloneWahl.getParteien().get(32);
		z.getLandesliste(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addMindestsitzanzahlFail1() {
		Partei z = this.cloneWahl.getParteien().get(32);
		Bundesland land = cloneWahl.getDeutschland().getBundeslaender().get(0);
		z.addMindestsitzanzahl(land, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addMindestsitzanzahlFail2() {
		Partei z = this.cloneWahl.getParteien().get(32);
		z.addMindestsitzanzahl(null, 19);
	}
	
	@Test
	public void zweitstimmeTest() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		Partei spd = this.cloneWahl.getParteien().get(1);
		LinkedList<Zweitstimme> cduStimmen = cdu.getZweitstimme();
		spd.setZweitstimme(cduStimmen);
		assertEquals(cduStimmen, spd.getZweitstimme());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void zweitstimmeTestFail() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setZweitstimme(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addZweitstimmeFail() {
		Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.addZweitstimme(null);
	}
	
	@Test
	public void getMitgliederTest() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		LinkedList<Kandidat> direkte = cdu.getMitglieder(Mandat.DIREKTMANDAT);
		assertEquals(191, direkte.size());
	}
	
	@Test
	public void getAnzahlDirektmandateTest() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(191, cdu.getAnzahlDirektmandate());
	}
	
	@Test
	public void istImBundestagTest() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		assertTrue(cdu.isImBundestag());
	}
	
	@Test
	public void getZweitstimmenTest() {
		// Wahlkreis: Flensburg - Schleswig
		Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender().get(0).getWahlkreise().get(0);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		int anzahl = cdu.getZweitstimme(wk);
		assertEquals(61347, anzahl);
	}
	
	@Test
	public void getAnzahlMandateTest() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(191, cdu.getAnzahlMandate(Mandat.DIREKTMANDAT));
		
	}
	
	@Test
	public void getUeberhangMandateTest() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(4, cdu.getUeberhangMandate());		
		cdu.resetUeberhangMandate();
		assertEquals(0, cdu.getUeberhangMandate());		
	}
	
	@Test
	public void getAusgleichsMandateTest() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(13, cdu.getAusgleichsMandate());		
		cdu.resetAusgleichsMandate();
		assertEquals(0, cdu.getAusgleichsMandate());		
	}
	
	@Test
	public void getAusgleichsMandateBLTest() {
		Mandatsrechner2013.getInstance().berechne(cloneWahl);
		Partei cdu = this.cloneWahl.getParteien().get(0);
		Bundesland sh = this.cloneWahl.getDeutschland().getBundeslaender().get(0);	
		assertEquals(1, cdu.getAusgleichsMandate(sh));	
	}
	
}

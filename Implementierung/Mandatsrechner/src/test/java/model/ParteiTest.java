package test.java.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Wahl 2013
		final File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		ParteiTest.wahl = Steuerung.getInstance().importieren(csvDateien);
	}

	/** rerpäsentiert die temporäre Wahl, die für jeden Test neu sein muss */
	private Bundestagswahl cloneWahl;

	/**
	 * Es wird getestet, ob es möglich ist eine Landesliste zu einer Liste von
	 * Landeslisten hinzuzufügen
	 */
	@Test
	public void addLandesListeTest() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final List<Landesliste> listen = cdu.getLandesliste();
		final Landesliste liste = this.cloneWahl.getParteien().get(1)
				.getLandesliste().get(3);
		boolean enthalten = false;
		for (final Landesliste list : listen) {
			if (list.equals(liste)) {
				enthalten = true;
			}
		}
		assertFalse(enthalten);

		cdu.addLandesliste(liste);

		for (final Landesliste list : listen) {
			if (list.equals(liste)) {
				enthalten = true;
			}
		}
		assertTrue(enthalten);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addMindestsitzanzahlFail1() {
		final Partei z = this.cloneWahl.getParteien().get(32);
		final Bundesland land = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0);
		z.addMindestsitzanzahl(land, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addMindestsitzanzahlFail2() {
		final Partei z = this.cloneWahl.getParteien().get(32);
		z.addMindestsitzanzahl(null, 19);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addMitgliedFail() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.addMitglied(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addZweitstimmeFail() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.addZweitstimme(null);
	}

	/**
	 * Holt die Direktmandate der CDU
	 */
	@Test
	public void getAnzahlDirektmandateTest() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(191, cdu.getAnzahlDirektmandate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getAnzahlMandateFail1() {
		final Partei z = this.cloneWahl.getParteien().get(32);
		final Bundesland land = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0);
		z.getAnzahlMandate(null, land);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getAnzahlMandateFail2() {
		final Partei z = this.cloneWahl.getParteien().get(32);
		z.getAnzahlMandate(Mandat.DIREKTMANDAT, null);
	}

	/**
	 * Zählt die Direktmandate der CDU
	 */
	@Test
	public void getAnzahlMandateTest() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(191, cdu.getAnzahlMandate(Mandat.DIREKTMANDAT));

	}

	/**
	 * Testet die Ausgleichsmandate der CDU in einem Bundesland
	 */
	@Test
	public void getAusgleichsMandateBLTest() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final Bundesland sh = this.cloneWahl.getDeutschland()
				.getBundeslaender().get(0);
		assertEquals(1, cdu.getAusgleichsMandate(sh));
	}

	/**
	 * Testet die Anzahl der Ausgleichsmandate
	 */
	@Test
	public void getAusgleichsMandateTest() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(13, cdu.getAusgleichsMandate());
		cdu.resetAusgleichsMandate();
		assertEquals(0, cdu.getAusgleichsMandate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getColorFail() {
		final Partei z = this.cloneWahl.getParteien().get(32);
		z.setFarbe(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getLandeslisteFail() {
		final Partei z = this.cloneWahl.getParteien().get(32);
		z.getLandesliste(null);
	}

	/**
	 * Holt alle Mitglieder der CDU mit dem Direktmandat
	 */
	@Test
	public void getMitgliederTest() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final LinkedList<Kandidat> direkte = cdu
				.getMitglieder(Mandat.DIREKTMANDAT);
		assertEquals(191, direkte.size());
	}

	/**
	 * Testet die Anzahl der Überhangsmandate
	 */
	@Test
	public void getUeberhangMandateTest() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		assertEquals(4, cdu.getUeberhangMandate());
		cdu.resetUeberhangMandate();
		assertEquals(0, cdu.getUeberhangMandate());
	}

	/**
	 * Testet das auffinden der Zweitstimmen der CDU in einem Wahlkreis
	 */
	@Test
	public void getZweitstimmenTest() {
		// Wahlkreis: Flensburg - Schleswig
		final Wahlkreis wk = this.cloneWahl.getDeutschland().getBundeslaender()
				.get(0).getWahlkreise().get(0);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final int anzahl = cdu.getZweitstimme(wk);
		assertEquals(61347, anzahl);
	}

	/**
	 * Testet ob die CDU im Bundestag ist
	 */
	@Test
	public void istImBundestagTest() {
		Mandatsrechner2013.getInstance().berechne(this.cloneWahl);
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		assertTrue(cdu.isImBundestag());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setLandeslisteFail() {
		final Partei z = this.cloneWahl.getParteien().get(32);
		z.setLandesliste(null);
	}

	/**
	 * Das Setzen einer Landesliste wird getestet
	 */
	@Test
	public void setLandesListetest() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final List<Landesliste> liste = new LinkedList<Landesliste>();
		final Landesliste landesliste = cdu.getLandesliste().get(0);
		liste.add(landesliste);
		final Partei cduNeu = new Partei(liste, cdu.getName(), cdu.getFarbe());
		cdu.setLandesliste(liste);
		assertEquals(1, cdu.getLandesliste().size());
		assertEquals(cdu.getLandesliste(), cduNeu.getLandesliste());
	}

	/**
	 * Alle nachfolgenden Tests sind Exceptionwruf bei null-Eingaben
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setMitgliederFail() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setMitglieder(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNameFail1() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNameFail2() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setName("");
	}

	@Before
	public void setUp() throws Exception {
		this.cloneWahl = ParteiTest.wahl.deepCopy();
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}

	/**
	 * Die Landesliste der SPD wird durch die der CDU ersetzt
	 */
	@Test
	public void zweitstimmeTest() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		final Partei spd = this.cloneWahl.getParteien().get(1);
		final LinkedList<Zweitstimme> cduStimmen = cdu.getZweitstimme();
		spd.setZweitstimme(cduStimmen);
		assertEquals(cduStimmen, spd.getZweitstimme());
	}

	@Test(expected = IllegalArgumentException.class)
	public void zweitstimmeTestFail() {
		final Partei cdu = this.cloneWahl.getParteien().get(0);
		cdu.setZweitstimme(null);
	}
}

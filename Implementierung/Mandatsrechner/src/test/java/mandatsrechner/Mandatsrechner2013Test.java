/**
 * 
 */
package test.java.mandatsrechner;

import static org.junit.Assert.*;

import java.io.File;

import main.java.mandatsrechner.Mandatsrechner2013;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Kandidat;
import main.java.model.Mandat;
import main.java.steuerung.Steuerung;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.java.Debug;

/**
 * 
 *
 */
public class Mandatsrechner2013Test {

	private Mandatsrechner2013 rechner;

	private static Bundestagswahl wahl;

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
		this.rechner = Mandatsrechner2013.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		this.cloneWahl = null;
	}
	
	@Test 
	public void berechneHauptWahl() {
		int spd = 0, cdu = 0, csu = 0, gruene = 0, linke = 0;
		this.rechner.berechne(this.cloneWahl);
		for (Kandidat kandidat : this.cloneWahl.getSitzverteilung().getAbgeordnete()) {
				switch (kandidat.getPartei().getName()) {
					case "CDU":
						cdu++;
						break;
					case "SPD":
						spd++;
						break;
					case "CSU":
						csu++;
						break;
					case "GRÜNE":
						gruene++;
						break;
					case "DIE LINKE":
						linke++;
						break;
					default:
						fail("Ungültige Partei");
			}
		}	
		assertEquals(193, spd);
		assertEquals(63, gruene);
		assertEquals(64, linke);
		assertEquals(56, csu);
		assertEquals(255, cdu);
	}
	
	@Test
	public void berechneAlteWahl(){
		//Importieren der Wahl 2009
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2009.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		Bundestagswahl alteWahl = Steuerung.getInstance().importieren(csvDateien);
		
		for (Bundesland bundesland : alteWahl.getDeutschland().getBundeslaender()) {
			switch(bundesland.getName()){
			case "Baden-Württemberg":
				bundesland.setEinwohnerzahl(9483476);
				break;
			case "Bayern":
				bundesland.setEinwohnerzahl(11344794);
				break;
			case "Berlin":
				bundesland.setEinwohnerzahl(2951272);
				break;
			case "Brandenburg":
				bundesland.setEinwohnerzahl(2457703);
				break;
			case "Bremen":
				bundesland.setEinwohnerzahl(578369);
				break;
			case "Hamburg":
				bundesland.setEinwohnerzahl(1526860);
				break;
			case "Hessen":
				bundesland.setEinwohnerzahl(5390683);
				break;
			case "Mecklenburg-Vorpommern":
				bundesland.setEinwohnerzahl(1625022);
				break;
			case "Niedersachsen":
				bundesland.setEinwohnerzahl(7423245);
				break;
			case "Nordrhein-Westfalen":
				bundesland.setEinwohnerzahl(16046200);
				break;
			case "Rheinland-Pfalz":
				bundesland.setEinwohnerzahl(3720049);
				break;
			case "Saarland":
				bundesland.setEinwohnerzahl(944527);
				break;
			case "Sachsen":
				bundesland.setEinwohnerzahl(4077550);
				break;
			case "Sachsen-Anhalt":
				bundesland.setEinwohnerzahl(2339042);
				break;
			case "Schleswig-Holstein":
				bundesland.setEinwohnerzahl(2687035);
				break;
			case "Thüringen":
				bundesland.setEinwohnerzahl(2220669);
				break;
			default:
				fail("Unbekanntes Bundesland!");
			}
			
		}
		
		int spd = 0, cdu = 0, csu = 0, gruene = 0, linke = 0, fdp = 0;
		this.rechner.berechne(alteWahl);
		for (Kandidat kandidat : alteWahl.getSitzverteilung().getAbgeordnete()) {
				switch (kandidat.getPartei().getName()) {
					case "CDU":
						cdu++;
						break;
					case "SPD":
						spd++;
						break;
					case "CSU":
						break;
					case "GRÜNE":
						gruene++;
						break;
					case "DIE LINKE":
						linke++;
						break;
					case "FDP":
						fdp++;
						break;
					default:
						fail("Ungültige Partei");
			}
		}
		
		assertEquals(164, spd);
		assertEquals(76, gruene);
		assertEquals(85, linke);
		assertEquals(195, cdu);
		assertEquals(104, fdp);
		
	}
	
	@Test (timeout = 10000)
	public void berechneLaufzeit(){
		this.rechner.berechne(cloneWahl);
	}
	@Test
	public void kandidatenTest(){
		this.rechner.berechne(cloneWahl);
		for(Kandidat kandidat : cloneWahl.getSitzverteilung().getAbgeordnete()){
			if(kandidat.getMandat().equals(Mandat.KEINMANDAT)){
				fail("Abgeordneter ohne Mandat in der Sitzverteilung");
			}
		}
	}
}

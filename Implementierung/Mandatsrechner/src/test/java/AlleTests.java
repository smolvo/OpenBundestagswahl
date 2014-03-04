package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.chronik.ChronikTest;
import test.java.config.ConfigTest;
import test.java.importexport.ImportExportTest;
import test.java.mandatsrechner.Mandatsrechner2009Test;
import test.java.mandatsrechner.Mandatsrechner2013Test;
import test.java.model.AlleModelTests;
import test.java.model.BundeslandTest;
import test.java.model.DeutschlandTest;
import test.java.model.LandeslisteTest;
import test.java.model.ParteiTest;
import test.java.model.WahlkreisTest;
import test.java.model.ZweitstimmeTest;
import test.java.wahlgenerator.WahlgeneratorTest;
import test.java.wahlvergleich.WahlvergleichTest1;

@RunWith(Suite.class)
@SuiteClasses({
	ChronikTest.class,
	ConfigTest.class,
	ImportExportTest.class,
	Mandatsrechner2009Test.class,
	Mandatsrechner2013Test.class,
	AlleModelTests.class,
	WahlgeneratorTest.class,
	WahlvergleichTest1.class,
	
})
public class AlleTests {

}

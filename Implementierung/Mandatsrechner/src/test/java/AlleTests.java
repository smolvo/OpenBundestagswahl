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
import test.java.wahlgenerator.WahlgeneratorTest;
import test.java.wahlvergleich.WahlvergleichTest1;
import test.java.wahlvergleich.WahlvergleichTest2;

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
	WahlvergleichTest2.class,
	
})
public class AlleTests {

}

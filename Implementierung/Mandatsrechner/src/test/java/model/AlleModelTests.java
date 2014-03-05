package test.java.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BundeslandTest.class, 
	BundestagswahlTest.class,
	DeutschlandTest.class, 
	ErststimmeTest.class, 
	GebietTest.class, 
	KandidatTest.class,
	LandeslisteTest.class, 
	ParteiTest.class, 
	SitzverteilungTest.class,
	WahlkreisTest.class, 
	ZweitstimmeTest.class })
public class AlleModelTests {

}

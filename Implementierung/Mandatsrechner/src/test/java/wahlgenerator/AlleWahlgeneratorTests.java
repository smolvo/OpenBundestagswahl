package test.java.wahlgenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ StimmgewichtSimulatorTest.class,
		StimmgewichtWahlgeneratorTest.class, WahlgeneratorTest.class,
		WahlgeneratorTestAlt.class })
public class AlleWahlgeneratorTests {

}

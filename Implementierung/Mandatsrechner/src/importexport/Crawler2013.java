package importexport;

import java.io.File;

import model.Bundestagswahl;

public class Crawler2013 extends Crawler {
	
	
	@Override
	public Bundestagswahl erstelleBundestagswahl(File csvDatei) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exportieren(String pfad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCrawlerInformation() {
		// TODO Auto-generated method stub
		return "Crawler 2013 - Example: http://www.bundeswahlleiter.de/de/bundestagswahlen/BTW_BUND_13/veroeffentlichungen/ergebnisse/kerg.csv";
	}

}

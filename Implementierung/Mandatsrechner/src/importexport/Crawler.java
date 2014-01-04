package importexport;

import java.io.File;

import model.Bundestagswahl;

public abstract class Crawler {

	public abstract String getCrawlerInformation();
	
	public abstract Bundestagswahl erstelleBundestagswahl(File csvDatei);
	
	public abstract boolean exportieren(String pfad);
}

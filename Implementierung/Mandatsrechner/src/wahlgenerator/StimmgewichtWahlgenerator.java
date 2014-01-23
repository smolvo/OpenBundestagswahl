package wahlgenerator;

import java.util.List;
import model.Bundestagswahl;

/**
 * Erzeugt Bundestagswahl, die die Voraussetzungen erfüllen, welche für die
 * Simulation des negativen Stimmgewichts benötigt werden
 */

public class StimmgewichtWahlgenerator extends AbstrakterWahlgenerator {


	public StimmgewichtWahlgenerator(Bundestagswahl basisWahl,
			List<Stimmanteile> stimmanteile) {
		super(basisWahl, stimmanteile);
		// TODO Auto-generated constructor stub
	}


	public void verteileStimmen() {
		// TODO

		/*
		 * Bei mindestens einer Partei muss der prozentuale Anteil ihrer
		 * relevanten Zweitstimmen größer als der prozentuale Anteil ihrer
		 * Mandate sein. Relevante Zweitstimmen sind all diejenigen
		 * Zweitstimmen, die auf Landeslisten abgegeben werden, die keine
		 * überhangmandate erzielen
		 */

	}

	@Override
	public void verteileStimmen(Bundestagswahl btw) {
		// TODO Auto-generated method stub

	}

}

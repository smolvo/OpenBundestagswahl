/**
 * 
 */
package wahlgenerator;

import java.util.List;

import model.Bundestagswahl;

/**
 * @author Manuel
 *
 *Erzeugt Bundestagswahl, die die Voraussetzungen erf�llen, welche f�r die Simulation des negativen Stimmgewichts
 *ben�tigt werden
 **/


public class NegStimmgewichtWahlgenerator extends AbstrakterWahlgenerator {

	

	public NegStimmgewichtWahlgenerator(Bundestagswahl basisWahl,
			List<Stimmanteile> stimmanteile) {
		super(basisWahl, stimmanteile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void verteileStimmen() {
		// TODO
		
		/* Bei mindestens einer Partei muss der prozentuale Anteil ihrer relevanten Zweitstimmen
		gr��er als der prozentuale Anteil ihrer Mandate sein. Relevante Zweitstimmen
		sind all diejenigen Zweitstimmen, die auf Landeslisten abgegeben werden, die keine
		�berhangmandate erzielen*/
		
		
	}

}

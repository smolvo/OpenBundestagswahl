package gui.ansicht;

import model.Stimme;

/**
 * Diese Klasse repr�sentiert eine �nderbare Zelle im Tabellenfenster.
 * @author Batman
 *
 */
public class TabellenZelle {

	/** repr�sentiert die korrespondierende Stimme */
	private Stimme stimme;
	
	/**
	 * Der Konstruktor der Klasse, der eine neue Tabellenzelle anlegt.
	 * @param stimme die korrespondierende Stimme
	 */
	public TabellenZelle(Stimme stimme) {
		this.stimme = stimme;
	}

	/**
	 * Holt das Stimmen-Objekt, welches zu dieser Zelle geh�rt.
	 * @return korrespondierende Stimme
	 */
	public Stimme getStimme() {
		return stimme;
	}

	/**
	 * Setzt das Stimmen-Objekt der Zelle.
	 * @param stimme neues Stimmen-Objekt
	 */
	public void setStimme(Stimme stimme) {
		this.stimme = stimme;
	}
}

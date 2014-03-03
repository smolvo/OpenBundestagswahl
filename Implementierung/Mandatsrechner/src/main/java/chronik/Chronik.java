package main.java.chronik;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import test.java.Debug;

import main.java.model.Stimme;

/**
 * Die Chronik setzt Stimmen, die in der GUI verändert wurden zurück. Es können
 * höchstens so viele Stimmen zurückgesetzt werden, wie in maxStimmen.
 * 
 */
public class Chronik implements Serializable {

	/**
	 * Automatisch generierte serialVersionUID die fuer das De-/Serialisieren
	 * verwendet wird.
	 */
	private static final long serialVersionUID = -9220795187148958406L;

	/**
	 * Anzahl der maximalen Stimmen, die zurückgesetzt werden können.
	 */
	private final int maxStimmen = 5;

	/**
	 * Ein Stack mit den letzten Stimmen
	 */
	List<Stimme[]> stimmen = new ArrayList<Stimme[]>();
	
	/**
	 * Ein Pointer auf das aktuelle Element in der Liste.
	 */
	private int aktuellesElement = 0;
	
	/**
	 * Sichert eine Stimme. Wird bei jeder Veränderung einer Stimme aufgerufen.
	 * Die Operation entspricht dem push Befehl eines Stacks.
	 * 
	 * @param alteStimme
	 *           	die Veränderte Stimme.
	 * @param aktuelleStimme
	 * 				die aktuelle Stimme.
	 * @throws IllegalArgumentException
	 *             wenn die Stimme null ist
	 */
	public void sichereStimme(Stimme alteStimme, Stimme aktuelleStimme) {
		if (alteStimme == null || aktuelleStimme == null) {
			throw new IllegalArgumentException("Stimme ist null");
		}
		
		Stimme[] stimmenArray = new Stimme[2];
		stimmenArray[0] = alteStimme.deepCopy();
		stimmenArray[1] = aktuelleStimme.deepCopy();
		this.stimmen.add(this.aktuellesElement, stimmenArray);
		this.aktuellesElement++;

		if (this.stimmen.size() > (this.aktuellesElement)) {
			for (int i = this.aktuellesElement; i < this.stimmen.size(); i++) {
				Debug.print("Chronik - Loesche Element an Stelle " + i, 5);
				this.stimmen.remove(this.aktuellesElement + 1);
			}
		}
	}

	/**
	 * Setzt eine Stimme zurueck.
	 * @return
	 * 		die alte Stimme
	 */
	public Stimme zuruecksetzenStimme() {
		Stimme alteStimme = null;
		if (this.hatStimmenZumZuruecksetzen()) {
			this.aktuellesElement--;
			alteStimme = this.stimmen.get(this.aktuellesElement)[0];
		}
		return alteStimme;
	}
	
	/**
	 * Stellt eine bereits zurueckgesetzte Stimme
	 * wieder her.
	 * @return
	 * 		die alte Stimme
	 */
	public Stimme wiederherstellenStimme() {
		Stimme alteStimme = null;
		if (this.hatStimmenZumWiederherstellen()) {
			alteStimme = this.stimmen.get(this.aktuellesElement)[1];
			this.aktuellesElement++;
		}
		return alteStimme;
	}
	
	/**
	 * Prueft, ob eine Stimme zum zuruecksetzen vorhanden
	 * ist.
	 * @return
	 * 		true wenn es zuruecksetzbar ist.
	 */
	public boolean hatStimmenZumZuruecksetzen() {
		return this.aktuellesElement > 0;
	}
	
	/**
	 * Prueft, ob eine Stimme wiederherstellbar ist.
	 * @return
	 * 		true, wenn es wiederherstellbar ist.
	 */
	public boolean hatStimmenZumWiederherstellen () {
		return this.stimmen.size() - 1 > this.aktuellesElement;
	}
	
	/**
	 * 
	 */
	/*public void debug() {
		Debug.print("Chronik Debug. Current Element: " + this.aktuellesElement + " / " + (this.stimmen.size() - 1), 5);
		for (int i = 0; i < this.stimmen.size(); i++) {
			if (this.stimmen.get(i) instanceof Erststimme) {
				Erststimme erststimme = (Erststimme) this.stimmen.get(i);
				Debug.print(i + " Erststimme:\t\t" + erststimme.getGebiet().getName() + "\t" + erststimme.getAnzahl(), 5);
			} else if (this.stimmen.get(i) instanceof Zweitstimme) {
				Zweitstimme zweitstimme = (Zweitstimme) this.stimmen.get(i);
				Debug.print(i + " Zweitstimme:\t" + zweitstimme.getGebiet().getName() + "\t" + zweitstimme.getAnzahl(), 5);
			} else {
				Debug.print(i + "Unkown", 5);
			}
		}
	}*/
	
	
}

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
	List<Stimme> stimmen = new ArrayList<Stimme>();
	
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
		
		/*if (this.stimmen.size() > maxStimmen) {
			this.stimmen.remove(0);
		}*/
		Debug.print("Chronik - Aktuelles Element: " + this.aktuellesElement, 4);
		/*if (this.aktuellesElement >= this.stimmen.size()) {
			this.stimmen.remove(this.aktuellesElement);
		}*/
		this.stimmen.add(this.aktuellesElement, alteStimme.deepCopy());
		this.aktuellesElement++;
		/*if (this.aktuellesElement >= this.stimmen.size()) {
			this.stimmen.remove(this.aktuellesElement);
		}*/
		this.stimmen.add(this.aktuellesElement, aktuelleStimme.deepCopy());
		if (this.stimmen.size() > (this.aktuellesElement + 1)) {
			for (int i = this.aktuellesElement + 1; i < this.stimmen.size(); i++) {
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
			alteStimme = this.stimmen.get(this.aktuellesElement);
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
			this.aktuellesElement++;
			return this.stimmen.get(this.aktuellesElement);
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
	
}

package main.java.chronik;

import java.io.Serializable;
import java.util.Stack;

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
	Stack<Stimme> stimmen = new Stack<Stimme>();

	/**
	 * Sichert eine Stimme. Wird bei jeder Veränderung einer Stimme aufgerufen.
	 * Die Operation entspricht dem push Befehl eines Stacks.
	 * 
	 * @param s
	 *            die Veränderte Stimme.
	 * @throws IllegalArgumentException
	 *             wenn die Stimme null ist
	 */
	public void sichereStimme(Stimme s) {
		if (s == null) {
			throw new IllegalArgumentException("Stimme ist null");
		}

		if (this.stimmen.size() > maxStimmen) {
			this.stimmen.remove(0);
		}

		this.stimmen.push(s.deepCopy());
	}

	/**
	 * Restauriert die zuletzt hinzugefügte Stimme. Entspricht dem pull Befehl
	 * eines Stacks.
	 * 
	 * @return die zuletzt hinzugefügte Stimme.
	 */
	public Stimme restauriereStimme() {
		Stimme stimme = null;
		if (this.stimmen.size() > 0) {
			stimme = this.stimmen.pop();
		}
		return stimme;
	}
}

package chronik;

import java.util.Stack;

import model.Stimme;

/**
 * Die Chronik setzt Stimmen, die in der GUI ver�ndert
 * wurden zur�ck. Es k�nnen h�chstens so viele Stimmen 
 * zur�ckgesetzt werden, wie in maxStimmen.
 * @author 13genesis37
 *
 */
public class Chronik {
	
	/**
	 * Anzahl der maximalen Stimmen, die zur�ckgesetzt
	 * werden k�nnen.
	 */
	final private int maxStimmen = 5; 
	
	/**
	 * Ein Stack mit den letzten Stimmen
	 */
	Stack<Stimme> stimmen = new Stack<Stimme>();
	/**
	 * Sichert eine Stimme. Wird bei jeder Ver�nderung
	 * einer Stimme aufgerufen. Die Operation entspricht
	 * dem push Befehl eines Stacks.
	 * @param s die Ver�nderte Stimme.
	 */
	public void sichereStimme(Stimme s) {
		
		if (this.stimmen.size() > maxStimmen) {
			this.stimmen.remove(0);
		}
		
		this.stimmen.push(s);
	}
	
	/**
	 * Restauriert die zuletzt hinzugef�gte Stimme.
	 * Entspricht dem pull Befehl eines Stacks.
	 * @return die zuletzt hinzugef�gte Stimme.
	 */
	public Stimme restauriereStimme() {
		Stimme s = null;
		if (this.stimmen.size() > 0) {
			s = this.stimmen.pop();
		}
		return s;
	}
}

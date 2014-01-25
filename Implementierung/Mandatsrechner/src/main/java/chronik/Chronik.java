package main.java.chronik;


import java.io.Serializable;
import java.util.Stack;

import main.java.model.Bundestagswahl;

/**
 * Die Chronik setzt Stimmen, die in der GUI ver�ndert
 * wurden zur�ck. Es k�nnen h�chstens so viele Bundestagswahlen 
 * zur�ckgesetzt werden, wie in maxBundestagswahl.
 * @author 13genesis37
 *
 */
public class Chronik implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9220795187148958406L;

	/**
	 * Anzahl der maximalen Bundestagswahlen, die zur�ckgesetzt
	 * werden k�nnen.
	 */
	final private int maxBundestagswahlen = 5; 
	
	/**
	 * Ein Stack mit den letzten Stimmen
	 */
	Stack<Bundestagswahl> bundestagswahlen = new Stack<Bundestagswahl>();
	/**
	 * Sichert eine Bundestagswahl. Wird bei jeder Ver�nderung
	 * einer Bundestagswahl aufgerufen. Die Operation entspricht
	 * dem push Befehl eines Stacks.
	 * @param s die Ver�nderte Bundestagswahl.
	 */
	public void sichereBundestagswahl(Bundestagswahl s) {
		
		if (this.bundestagswahlen.size() > maxBundestagswahlen) {
			this.bundestagswahlen.remove(0);
		}
		
		this.bundestagswahlen.push(s);
	}
	
	/**
	 * Restauriert die zuletzt hinzugef�gte Bundestagswahl.
	 * Entspricht dem pull Befehl eines Stacks.
	 * @return die zuletzt hinzugef�gte Bundestagswahl.
	 */
	public Bundestagswahl restauriereBundestagswahl() {
		Bundestagswahl btw = null;
		if (this.bundestagswahlen.size() > 0) {
			btw = this.bundestagswahlen.pop();
		}
		return btw;
	}
}

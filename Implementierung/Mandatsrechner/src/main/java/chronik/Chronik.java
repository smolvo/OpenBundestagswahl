package main.java.chronik;


import java.io.Serializable;
import java.util.Stack;

import main.java.model.Bundestagswahl;

/**
 * Die Chronik setzt Stimmen, die in der GUI verändert
 * wurden zurück. Es können höchstens so viele Bundestagswahlen 
 * zurückgesetzt werden, wie in maxBundestagswahl.
 * @author 13genesis37
 *
 */
public class Chronik implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9220795187148958406L;

	/**
	 * Anzahl der maximalen Bundestagswahlen, die zurückgesetzt
	 * werden können.
	 */
	final private int maxBundestagswahlen = 5; 
	
	/**
	 * Ein Stack mit den letzten Stimmen
	 */
	Stack<Bundestagswahl> bundestagswahlen = new Stack<Bundestagswahl>();
	/**
	 * Sichert eine Bundestagswahl. Wird bei jeder Veränderung
	 * einer Bundestagswahl aufgerufen. Die Operation entspricht
	 * dem push Befehl eines Stacks.
	 * @param s die Veränderte Bundestagswahl.
	 */
	public void sichereBundestagswahl(Bundestagswahl s) {
		
		if (this.bundestagswahlen.size() > maxBundestagswahlen) {
			this.bundestagswahlen.remove(0);
		}
		
		this.bundestagswahlen.push(s);
	}
	
	/**
	 * Restauriert die zuletzt hinzugefügte Bundestagswahl.
	 * Entspricht dem pull Befehl eines Stacks.
	 * @return die zuletzt hinzugefügte Bundestagswahl.
	 */
	public Bundestagswahl restauriereBundestagswahl() {
		Bundestagswahl btw = null;
		if (this.bundestagswahlen.size() > 0) {
			btw = this.bundestagswahlen.pop();
		}
		return btw;
	}
}

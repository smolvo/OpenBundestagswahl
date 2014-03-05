package main.java;

import main.java.gui.Programmfenster;
import test.java.Debug;
/**
 * Klasse die das Programm startet
 *
 */
public class Main {
	/**
	 * Hier startet das Programm.
	 * 
	 * @param args
	 *            Startargumente
	 */
	public static void main(String[] args) {
		Debug.setLevel(0);
		new Programmfenster();
	}
}

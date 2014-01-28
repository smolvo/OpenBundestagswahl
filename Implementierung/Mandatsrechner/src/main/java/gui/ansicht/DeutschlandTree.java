package main.java.gui.ansicht;

import javax.swing.JTree;

import main.java.model.Deutschland;

/**
 * Diese Klasse repräsentiert den Baum der im Kartenfenster in Form einer Liste
 * angezeigt wird.
 * 
 */
public class DeutschlandTree extends JTree {

	private static final long serialVersionUID = -5349967918003364640L;

	/**
	 * Der Konstruktor erzeugt einen neuen Baum
	 * 
	 * @param wurzel
	 *            Wurzel
	 */
	public DeutschlandTree(Deutschland wurzel) {
		super(new DeutschlandModel(wurzel));
	}
}

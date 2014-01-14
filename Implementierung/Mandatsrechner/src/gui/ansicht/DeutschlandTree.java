package gui.ansicht;

import javax.swing.JTree;

import model.Deutschland;

/**
 * Diese Klasse repr�sentiert den Baum der im Kartenfenster
 * in Form einer Liste angezeigt wird.
 *
 */
public class DeutschlandTree extends JTree {

	/** repr�sentiert das Baummodell */
	private DeutschlandModel model;
	
	/**
	 * Der Konstruktor erzeugt einen neuen Baum
	 * @param wurzel Wurzel
	 */
	public DeutschlandTree(Deutschland wurzel) {
		this.model = new DeutschlandModel(wurzel);
	}
}

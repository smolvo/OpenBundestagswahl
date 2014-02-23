package main.java.gui.ansicht;

import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import main.java.gui.GUISteuerung;
import main.java.gui.WahlFenster;
import main.java.model.Deutschland;
import main.java.model.Gebiet;

/**
 * Diese Klasse reprï¿½sentiert die Listenansicht des Kartenfensters. In ihr wird
 * eine Verzeichnisstruktur angelegt die Deutschland, alle Bundeslï¿½nder und alle
 * Wahlkreise enthï¿½lt.
 * 
 */
public class Listenansicht extends JScrollPane implements TreeSelectionListener {

	private static final long serialVersionUID = 3812495274375926111L;

	/** reprï¿½sentiert das dazugehï¿½rige Kartenfenster */
	private KartenFenster kartenfenster;

	/**
	 * Im Konstruktor der Klasse wird eine Baumstruktur angelegt. Die erste
	 * Stufe bildet Deutschland, die zweite alle Bundeslï¿½nder und die dritte die
	 * Wahlkreise der Bundeslï¿½nder.
	 * 
	 * @param land
	 *            alle Bundeslï¿½nder
	 * @param kartenfenster
	 *            das Kartenfenster
	 */
	public Listenansicht(Deutschland land, KartenFenster kartenfenster) {
		this.kartenfenster = kartenfenster;
		DeutschlandTree tree = new DeutschlandTree(land);
		tree.addTreeSelectionListener(this);
		this.setViewportView(tree);
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		WahlFenster wahlfenster = kartenfenster.getAnsicht().getFenster();
		GUISteuerung steuerung = wahlfenster.getSteuerung();
		Gebiet geb = (Gebiet) e.getPath().getLastPathComponent();
		steuerung.aktualisiereWahlfenster(geb);
	}
}

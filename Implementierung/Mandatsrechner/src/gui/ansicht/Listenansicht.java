package gui.ansicht;

import gui.GUISteuerung;
import gui.WahlFenster;

import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import model.Deutschland;
import model.Gebiet;

/**
 * Diese Klasse repr�sentiert die Listenansicht des Kartenfensters.
 *
 */
public class Listenansicht extends JScrollPane implements TreeSelectionListener {
	
	/** repr�sentiert das dazugeh�rige Kartenfenster */
	private KartenFenster kartenfenster;
	
	/**
	 * Im Konstruktor der Klasse wird eine Baumstruktur angelegt.
	 * Die erste Stufe bildet Deutschland, die zweite alle Bundesl�nder
	 * und die dritte die Wahlkreise der Bundesl�nder.
	 * @param land alle Bundesl�nder
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

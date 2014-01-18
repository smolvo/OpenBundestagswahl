package gui.ansicht;

import gui.GUISteuerung;
import gui.WahlFenster;

import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import model.Deutschland;
import model.Gebiet;

/**
 * Diese Klasse repräsentiert die Listenansicht des Kartenfensters.
 *
 */
public class Listenansicht extends JScrollPane implements TreeSelectionListener {
	
	/**
	 * Im Konstruktor der Klasse wird eine Baumstruktur angelegt.
	 * Die erste Stufe bildet Deutschland, die zweite alle Bundesländer
	 * und die dritte die Wahlkreise der Bundesländer.
	 * @param land alle Bundesländer
	 */
	public Listenansicht(Deutschland land) {
		DeutschlandTree tree = new DeutschlandTree(land);
        tree.addTreeSelectionListener(this);
		this.setViewportView(tree);		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		WahlFenster wahlfenster = (WahlFenster) this.getParent().getParent().getParent();
		GUISteuerung steuerung = wahlfenster.getSteuerung();
		Gebiet geb = (Gebiet) e.getPath().getLastPathComponent();
		steuerung.aktualisiereWahlfenster(geb);
	}
}

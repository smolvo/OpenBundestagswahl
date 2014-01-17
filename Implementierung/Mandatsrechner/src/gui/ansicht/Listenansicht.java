package gui.ansicht;

import gui.GUISteuerung;
import gui.WahlFenster;

import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Bundesland;
import model.Deutschland;
import model.Gebiet;
import model.Wahlkreis;

/**
 * Diese Klasse repräsentiert die Listenansicht des Kartenfensters.
 *
 */
public class Listenansicht extends JScrollPane implements TreeExpansionListener {
	
	/**
	 * Im Konstruktor der Klasse wird eine Baumstruktur angelegt.
	 * Die erste Stufe bildet Deutschland, die zweite alle Bundesländer
	 * und die dritte die Wahlkreise der Bundesländer.
	 * @param land alle Bundesländer
	 */
	public Listenansicht(Deutschland land) {
		DeutschlandTree tree = new DeutschlandTree(land);
        tree.addTreeExpansionListener(this);
		this.setViewportView(tree);		
	}

	@Override
	public void treeExpanded(TreeExpansionEvent event) {
		WahlFenster wahlfenster = (WahlFenster) this.getParent().getParent().getParent();
		GUISteuerung steuerung = wahlfenster.getSteuerung();
		Gebiet geb = (Gebiet) event.getPath().getLastPathComponent();
		steuerung.aktualisiereWahlfenster(geb);
	}

	@Override
	public void treeCollapsed(TreeExpansionEvent event) {
		
	}
}

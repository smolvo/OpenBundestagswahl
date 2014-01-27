package main.java.gui.ansicht;

import java.util.Vector;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import main.java.model.Bundesland;
import main.java.model.Deutschland;
import main.java.model.Wahlkreis;

/**
 * Diese Klasse repräsentiert die Vorlage der Baumstruktur der Bundes- und
 * Wahlkreisanzeige in der Liste des Kartenfensters.
 * 
 * @author Anton
 * 
 */
public class DeutschlandModel implements TreeModel {

	/** repräsentiert die Wurzel des Baumes */
	private Deutschland wurzel;

	/** repräsentiert die Liste von Listenern */
	private Vector<TreeModelListener> treeModelListeners = new Vector<TreeModelListener>();

	/**
	 * Der Konstruktor der einen neuen Baum instanziiert.
	 * 
	 * @param land
	 *            Wurzel
	 */
	public DeutschlandModel(Deutschland land) {
		this.wurzel = land;
	}

	@Override
	public Object getRoot() {
		return this.wurzel;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent instanceof Deutschland) {
			Deutschland land = (Deutschland) parent;
			return land.getBundeslaender().get(index);
		} else if (parent instanceof Bundesland) {
			Bundesland bundLand = (Bundesland) parent;
			return bundLand.getWahlkreise().get(index);
		} else {
			return null;
		}
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof Deutschland) {
			Deutschland land = (Deutschland) parent;
			return land.getBundeslaender().size();
		} else if (parent instanceof Bundesland) {
			Bundesland bundLand = (Bundesland) parent;
			return bundLand.getWahlkreise().size();
		} else {
			return -1;
		}
	}

	@Override
	public boolean isLeaf(Object node) {
		return node instanceof Wahlkreis;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		System.out.println("*** valueForPathChanged : " + path + " --> "
				+ newValue);
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof Deutschland) {
			Deutschland land = (Deutschland) parent;
			Bundesland bundLand = (Bundesland) child;
			return land.getBundeslaender().indexOf(bundLand);
		} else if (parent instanceof Bundesland) {
			Bundesland bundLand = (Bundesland) parent;
			Wahlkreis wahlKreis = (Wahlkreis) child;
			return bundLand.getWahlkreise().indexOf(wahlKreis);
		} else {
			return -1;
		}
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		treeModelListeners.addElement(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		treeModelListeners.removeElement(l);
	}

}

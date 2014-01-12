package gui.ansicht;

import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Bundesland;
import model.Deutschland;
import model.Wahlkreis;

/**
 * Diese Klasse repr�sentiert die Listenansicht des Kartenfensters.
 *
 */
public class Listenansicht extends JScrollPane {
	
	/**
	 * Im Konstruktor der Klasse wird eine Baumstruktur angelegt.
	 * Die erste Stufe bildet Deutschland, die zweite alle Bundesl�nder
	 * und die dritte die Wahlkreise der Bundesl�nder.
	 * @param land alle Bundesl�nder
	 */
	public Listenansicht (Deutschland land) {

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Deutschland");

		LinkedList<Bundesland> bundeslaender = land.getBundeslaender();
		
		for (Bundesland bl : bundeslaender) {
			DefaultMutableTreeNode bLand = new DefaultMutableTreeNode(bl.getName()); 
			root.add(bLand);
			
			LinkedList<Wahlkreis> wahlkreise = bl.getWahlkreise();
			
			for (Wahlkreis wk : wahlkreise)
				bLand.add(new DefaultMutableTreeNode(wk.getName()));
			
		}
		
		JTree tree = new JTree(root);
		
		this.setViewportView(tree);
		
	}
	
}

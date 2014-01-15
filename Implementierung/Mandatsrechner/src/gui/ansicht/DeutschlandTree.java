package gui.ansicht;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import model.Deutschland;

/**
 * Diese Klasse repräsentiert den Baum der im Kartenfenster
 * in Form einer Liste angezeigt wird.
 *
 */
public class DeutschlandTree extends JTree {

	/** repräsentiert das Baummodell */
	private DeutschlandModel model;
	
	/**
	 * Der Konstruktor erzeugt einen neuen Baum
	 * @param wurzel Wurzel
	 */
	public DeutschlandTree(Deutschland wurzel) {
		super(new DeutschlandModel(wurzel));
		/*getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        Icon personIcon = null;
        renderer.setLeafIcon(personIcon);
        renderer.setClosedIcon(personIcon);
        renderer.setOpenIcon(personIcon);
        setCellRenderer(renderer);*/
	}
}

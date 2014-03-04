package main.java.gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.java.gui.ansicht.diagrammfenster.DiagrammFenster;

/**
 * Diese Klasse repräsentiert das Berichtsfenster. In diesem werden Daten
 * angezeigt, die erläutern sollen wie ein Abgeordneter an sein Mandat gekommen
 * ist.
 * 
 * 
 */
public class BerichtsFenster extends JDialog {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -6882730327089576613L;

	/**
	 * Der Konstruktor erstellt ein neues Berichtsfenster.
	 * 
	 * @param tabellenModell
	 *            die Berichtstabelle
	 * @param diagrammFenster 
	 * @throws IllegalArgumentException
	 *             wenn das tabellenModell Objekt null ist
	 */
	public BerichtsFenster(BerichtTableModel tabellenModell, final DiagrammFenster diagrammFenster) {
		if (tabellenModell == null) {
			throw new NullPointerException("Kein Tabellenmodell gefunden.");
		}
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				diagrammFenster.bericht.setEnabled(true);
				dispose();
			}
		});
		this.setSize(new Dimension(1024, 600));
		this.setLocationRelativeTo(null);
		this.setTitle("Mandatsübersicht");
		TableRowSorter<TableModel> sorterBericht = new TableRowSorter<TableModel>();
		JTable jTabelle = new JTable(tabellenModell);
		JScrollPane pane = new JScrollPane(jTabelle);
		jTabelle.setRowSorter(sorterBericht);
		sorterBericht.setModel(jTabelle.getModel());
		this.add(pane);
		this.setVisible(true);
		
	}
}

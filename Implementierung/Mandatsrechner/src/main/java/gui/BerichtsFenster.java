package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Diese Klasse repräsentiert das Berichtsfenster.
 * In diesem werden Daten angezeigt, die erläutern
 * sollen wie ein Abgeordneter an sein Mandat gekommen
 * ist.
 * @author Anton
 *
 */
public class BerichtsFenster extends JFrame {

	/**
	 * Der Konstruktor erstellt ein neues Berichtsfenster.
	 * @param tabellenModell die Berichtstabelle
	 */
	public BerichtsFenster(BerichtTableModel tabellenModell) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(1024, 768);
		this.setTitle("Mandatsübersicht");
		JTable jTabelle = new JTable(tabellenModell);
		JScrollPane pane = new JScrollPane(jTabelle);
		this.add(pane);
		this.setVisible(true);
	}
}

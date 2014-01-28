package main.java.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Diese Klasse repr�sentiert das Berichtsfenster. In diesem werden Daten
 * angezeigt, die erl�utern sollen wie ein Abgeordneter an sein Mandat gekommen
 * ist.
 * 
 * @author Anton
 * 
 */
public class BerichtsFenster extends JDialog {

	/**
	 * Der Konstruktor erstellt ein neues Berichtsfenster.
	 * 
	 * @param tabellenModell
	 *            die Berichtstabelle
	 * @throw NullPointerException
	 */
	public BerichtsFenster(BerichtTableModel tabellenModell) {
		if (tabellenModell == null) {
			throw new NullPointerException("Kein Tabellenmodell gefunden.");
		}
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setSize(new Dimension(1024, 768));
		this.setLocationRelativeTo(null);
		this.setTitle("Mandats�bersicht");
		JTable jTabelle = new JTable(tabellenModell);
		JScrollPane pane = new JScrollPane(jTabelle);
		this.add(pane);
		this.setVisible(true);
	}
}

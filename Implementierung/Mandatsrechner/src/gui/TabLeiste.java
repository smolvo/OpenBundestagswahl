package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Diese Klasse repr�sentiert die Tab- Leiste des Programmfensters
 * Jeder Tab steht f�r eine im Programm ge�ffnete Bundestagswahl
 *
 * @author Manuel
 *
 */
public class TabLeiste extends JTabbedPane {

	private List<WahlFenster> wahlen = new ArrayList<WahlFenster>();
	

	public TabLeiste(Programmfenster pf) {
		
		//allgemeine Einstellungen der Tab- Leiste
		this.setTabLayoutPolicy(TOP);
		this.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);

		//neuen Tab f�r jede aktuelle Wahl
		for (WahlFenster w : pf.getWahlen()) {
			wahlen.add(w);
			neuerTab(w, w.getName());
		}		
		neuerTabButton(new JPanel());
		setVisible(true);
	}
	
	/**
	 * 
	 * Diese Methode f�gt den "+"- Button, mit dem ein neuer Tab ge�ffnet werden kann, hinzu
	 *
	 * @param c
	 */
	public void neuerTabButton(final JComponent c) {
		add(c);
		int pos = indexOfComponent(c);
		
		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		JPanel pnlTab = new JPanel(f);
		pnlTab.setOpaque(false);
		pnlTab.setFocusable(false);
		
		JButton neuerTab = new JButton();
		neuerTab.setOpaque(false);
		neuerTab.setIcon(new ImageIcon("src/gui/resources/images/neuerTab.png"));
		
		neuerTab.setBorder(null);
		
		neuerTab.setContentAreaFilled(false);
		neuerTab.setFocusPainted(false);
		neuerTab.setFocusable(false);

		pnlTab.add(neuerTab);
		setTabComponentAt(pos, pnlTab);
		//Erstelle anonymen ActionListener f�r den "+" Knopf
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(c);
				new ImportDialog(TabLeiste.this);
				
				neuerTabButton(c);
			}
		};
		neuerTab.addActionListener(listener);

	}

	/**
	 * Diese Methode f�gt zur Tab- Leiste einen weiteren Tab hinzu. Dabei
	 * besitzt der Tab einen Schlie�en- Button
	 * 
	 * @param tabs
	 *            die Tab- Leiste
	 * @param c
	 *            Inhalt des Tabs
	 * @param wahlName
	 *            Name der im Tab gezeigten Wahl
	 */

	public void neuerTab(final JComponent c, final String wahlName) {
		add(c);
		int pos = indexOfComponent(c);

	
		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		JPanel tab = new JPanel(f);
		tab.setOpaque(false);

		
		JLabel lblTitle = new JLabel(wahlName);

		JButton schliessen = new JButton();
		schliessen.setOpaque(false);

		schliessen.setIcon(new ImageIcon(
				"src/gui/resources/images/tabSchlie�en.png"));

		schliessen.setBorder(null);

		schliessen.setFocusable(false);

		tab.add(lblTitle);
		tab.add(schliessen);
		setTabComponentAt(pos, tab);


		//Erstelle anonymen ActionListener f�r den "x" Knopf
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*Wird versucht, einen Tab zu schlie�en, wird zuerst gefragt, ob dieser Tab gespeichert
				werden soll */
				int eingabe = JOptionPane.showConfirmDialog(null,
                        "Soll Datei gespeichert werden?",
                        "Einverst�ndnis",
                        JOptionPane.YES_NO_CANCEL_OPTION);
				
				if(eingabe == 0) {
					new ExportDialog(TabLeiste.this);
				} else
					if ( eingabe == 1) {
						remove(c);
					}
				
			}
		};

		schliessen.addActionListener(listener);
		setSelectedComponent(c);
	
	}
}

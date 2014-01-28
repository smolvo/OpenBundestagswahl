package main.java.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import main.java.gui.dialoge.ExportDialog;
import main.java.gui.dialoge.ImportDialog;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repr�sentiert die Tab- Leiste des Programmfensters Jeder Tab
 * steht f�r eine im Programm ge�ffnete Bundestagswahl
 * 
 * @author Manuel
 * 
 */
public class TabLeiste extends JTabbedPane {

	/** repr�sentiert das Programmfenster der Tableiste */
	private final Programmfenster pf;
	
	/** repr�sentiert den Plus-Knopf der Tableiste */
	private final JComponent plusButton = new JPanel();

	/**
	 * Der Konstruktor der Tableiste
	 * 
	 * @param pf
	 *            Programmfenster
	 */
	public TabLeiste(Programmfenster pf) {
		this.pf = pf;
		// allgemeine Einstellungen der Tab- Leiste
		this.setTabLayoutPolicy(TOP);
		this.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		neuerTabButton();
		setVisible(true);
	}

	/**
	 * 
	 * Diese Methode f�gt der Tableiste einen "+"- Button hinzu Wenn man darauf
	 * klickt, �ffnet sich der Dialog zum Importieren einer neuern Wahl
	 * 
	 * @param c
	 *            JComponent
	 */
	public void neuerTabButton() {
		add(plusButton);
		int pos = indexOfComponent(plusButton);

		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		JPanel pnlTab = new JPanel(f);
		pnlTab.setOpaque(false);

		JButton neuerTab = new JButton();
		neuerTab.setOpaque(false);
		neuerTab.setIcon(new ImageIcon("src/main/resources/gui/images/neuerTab.png"));

		neuerTab.setBorder(null);
		neuerTab.setContentAreaFilled(false);
		neuerTab.setFocusPainted(false);
		neuerTab.setFocusable(false);

		pnlTab.add(neuerTab);
		setTabComponentAt(pos, pnlTab);

		this.setEnabledAt(pos, false);
		// Erstelle anonymen ActionListener f�r den "+" Knopf
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(plusButton);
				importiere();
				neuerTabButton();
			}
		};
		neuerTab.addActionListener(listener);
	}
	
	/**
	 * Diese private Methode wird vom Plus-Knopf verwendet um eine Wahl zu importieren.
	 */
	private void importiere() {
		if (pf.getiD() == null) {
			ImportDialog iD = new ImportDialog(pf);
			pf.setiD(iD);
			pf.getiD().importiereWahl();
		} else {
			pf.getiD().importiereWahl();
		}
	}
		

	/**
	 * Diese Methode f�gt zur Tableiste einen weiteren Tab hinzu. Dabei besitzt
	 * der Tab einen Schlie�en- Button
	 * 
	 * @param c
	 *            Inhalt des Tabs
	 * @param wahlName
	 *            Name der im Tab gezeigten Wahl
	 */

	public void neuerTab(final WahlFenster c, final String wahlName) {
		if ((c == null) || (wahlName == null)) {
			throw new NullPointerException("Einer der zwei Parameter ist null.");
		}
		add(c);
		int pos = indexOfComponent(c);

		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		JPanel tab = new JPanel(f);
		tab.setOpaque(false);

		JLabel lblTitle = new JLabel(wahlName);

		JButton schliessen = new JButton();
		schliessen.setOpaque(false);

		schliessen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/tabSchlie�en.png"));

		schliessen.setBorder(null);

		schliessen.setFocusable(false);

		tab.add(lblTitle);
		tab.add(schliessen);
		setTabComponentAt(pos, tab);
		
		// Erstelle anonymen ActionListener f�r den "x" Knopf
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				/*
				 * Wird versucht, einen Tab zu schlie�en, wird zuerst gefragt,
				 * ob dieser Tab gespeichert werden soll
				 */
				int eingabe = JOptionPane.showConfirmDialog(null,
						"Soll Datei gespeichert werden?", "Einverst�ndnis",
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (eingabe == 0) {
					new ExportDialog(TabLeiste.this);
				} else if (eingabe == 1) {
					remove(c);
					pf.getWahlen().remove(c);
					setSelectedComponent(TabLeiste.this.getComponentAt(0));
				}

			}
		};

		this.remove(plusButton);
		neuerTabButton();
		schliessen.addActionListener(listener);
		Steuerung.getInstance().setBtw(c.getBtw());

	}

	/**
	 * Gibt das Programmfenster aus
	 * @return Programmfenster
	 */
	public Programmfenster getPf() {
		return pf;
	}
}

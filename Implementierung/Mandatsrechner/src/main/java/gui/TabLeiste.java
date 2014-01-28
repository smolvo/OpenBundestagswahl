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

/**
 * Diese Klasse repräsentiert die Tab- Leiste des Programmfensters Jeder Tab
 * steht für eine im Programm geöffnete Bundestagswahl
 * 
 * @author Manuel
 * 
 */
public class TabLeiste extends JTabbedPane {

	/** repräsentiert das Programmfenster der Tableiste */
	private final Programmfenster pf;
	
	/** repräsentiert den Plus-Knopf der Tableiste */
	private final JComponent plusButton = new JPanel();

	/**
	 * Der Konstruktor der Tableiste
	 * 
	 * @param pf
	 *            Programmfenster
	 * @throw NullPointerException
	 */
	public TabLeiste(Programmfenster pf) {
		if (pf == null) {
			throw new NullPointerException("Kein Programmfenster gefunden.");
		}
		this.pf = pf;
		// allgemeine Einstellungen der Tab- Leiste
		this.setTabLayoutPolicy(TOP);
		this.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		neuerTabButton();
	}		

	/**
	 * Diese Methode fügt zur Tableiste einen weiteren Tab hinzu. Dabei besitzt
	 * der Tab einen Schließen- Button
	 * 
	 * @param c
	 *            Inhalt des Tabs
	 * @param wahlName
	 *            Name der im Tab gezeigten Wahl
	 * @throw NullPointerException
	 */

	public void neuerTab(final WahlFenster c, final String wahlName) {
		if ((c == null) || (wahlName == null)) {
			throw new NullPointerException("Einer der zwei Parameter ist null.");
		}
		this.add(c);
		int pos = this.indexOfComponent(c);

		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		JPanel tab = new JPanel(f);
		tab.setOpaque(false);

		JLabel lblTitle = new JLabel(wahlName);

		JButton schliessen = new JButton();
		schliessen.setOpaque(false);
		schliessen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/tabSchließen.png"));
		schliessen.setBorder(null);
		schliessen.setFocusable(false);

		tab.add(lblTitle);
		tab.add(schliessen);
		setTabComponentAt(pos, tab);
		
		// Erstelle anonymen ActionListener für den "x" Knopf
		schliessen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Wird versucht, einen Tab zu schließen, wird zuerst gefragt,
				 * ob dieser Tab gespeichert werden soll
				 */
				int eingabe = JOptionPane.showConfirmDialog(null,
						"Soll Datei gespeichert werden?", "Einverständnis",
						JOptionPane.YES_NO_CANCEL_OPTION);

				if (eingabe == 0) {
					new ExportDialog(TabLeiste.this);
				} else if (eingabe == 1) {
					remove(c);
					pf.getWahlen().remove(c);
					setSelectedComponent(TabLeiste.this.getComponentAt(0));
				}
			}
		});

		this.remove(plusButton);
		neuerTabButton();
		this.setSelectedComponent(c);
	}
	
	/**
	 * Diese Methode fügt der Tableiste einen "+"- Button hinzu Wenn man darauf
	 * klickt, öffnet sich der Dialog zum Importieren einer neuern Wahl.
	 */
	public void neuerTabButton() {
		this.add(plusButton);
		int pos = this.indexOfComponent(plusButton);

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
		this.setTabComponentAt(pos, pnlTab);

		this.setEnabledAt(pos, false);
		// Erstelle anonymen ActionListener für den "+" Knopf
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
	 * Diese private Methode wird vom Plus-Knopf verwendet, um eine Wahl zu importieren.
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
	 * Gibt das Programmfenster aus
	 * @return Programmfenster
	 */
	public Programmfenster getPf() {
		return pf;
	}
}

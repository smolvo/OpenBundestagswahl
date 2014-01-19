package gui;

import gui.dialoge.ExportDialog;
import gui.dialoge.ImportDialog;

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
 * Diese Klasse repräsentiert die Tab- Leiste des Programmfensters Jeder Tab
 * steht für eine im Programm geöffnete Bundestagswahl
 * 
 * @author Manuel
 * 
 */
public class TabLeiste extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1890561914275560319L;
	private List<WahlFenster> wahlen = new ArrayList<WahlFenster>();
	private Programmfenster pf;

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

		// neuen Tab für jede aktuelle Wahl
		for (int i = 0; i < pf.getWahlen().size(); i++) {
			WahlFenster w = pf.getWahlen().get(i);
			wahlen.add(w);
			neuerTab(w, w.getName());

		}
		neuerTabButton(new JPanel());
		setVisible(true);
	}

	/**
	 * 
	 * Diese Methode fügt der Tableiste einen "+"- Button hinzu Wenn man darauf
	 * klickt, öffnet sich der Dialog zum Importieren einer neuern Wahl
	 * 
	 * @param c
	 *            JComponent
	 */
	public void neuerTabButton(final JComponent c) {
		add(c);
		int pos = indexOfComponent(c);

		FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		JPanel pnlTab = new JPanel(f);
		pnlTab.setOpaque(false);

		JButton neuerTab = new JButton();
		neuerTab.setOpaque(false);
		neuerTab.setIcon(new ImageIcon("src/gui/resources/images/neuerTab.png"));

		neuerTab.setBorder(null);
		neuerTab.setContentAreaFilled(false);
		neuerTab.setFocusPainted(false);
		neuerTab.setFocusable(false);

		pnlTab.add(neuerTab);
		setTabComponentAt(pos, pnlTab);

		this.setEnabledAt(pos, false);
		// Erstelle anonymen ActionListener für den "+" Knopf
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
	 * Diese Methode fügt zur Tableiste einen weiteren Tab hinzu. Dabei besitzt
	 * der Tab einen Schließen- Button
	 * 
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
				"src/gui/resources/images/tabSchließen.png"));

		schliessen.setBorder(null);

		schliessen.setFocusable(false);

		tab.add(lblTitle);
		tab.add(schliessen);
		setTabComponentAt(pos, tab);

		pf.setWahlen(wahlen);
		// Erstelle anonymen ActionListener für den "x" Knopf

		ActionListener listener = new ActionListener() {
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
				}

			}
		};

		schliessen.addActionListener(listener);
		setSelectedComponent(c);

	}

	/**
	 * @return the pf
	 */
	public Programmfenster getPf() {
		return pf;
	}

	/**
	 * @param pf
	 *            the pf to set
	 */
	public void setPf(Programmfenster pf) {
		this.pf = pf;
	}

}

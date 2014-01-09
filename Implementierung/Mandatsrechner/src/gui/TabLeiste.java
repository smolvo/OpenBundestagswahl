package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabLeiste extends JTabbedPane {

	private List<WahlFenster> wahlen = new ArrayList<WahlFenster>();
	

	public TabLeiste(Programmfenster pf) {
		
		//allgemeine Einstellungen der Tab- Leiste
		this.setTabLayoutPolicy(TOP);
		this.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);

		//neuen Tab für jede aktuelle Wahl
		for (WahlFenster w : pf.getWahlen()) {
			wahlen.add(w);
			neuerTab(w, w.getName());
		}		
		neuerTabButton(new JPanel());
		setVisible(true);
	}
	
	/**
	 * 
	 * Diese Methode fügt den "+"- Button, mit dem ein neuer Tab geöffnet werden kann, hinzu
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
		//Erstelle anonymen ActionListener für den "+" Knopf
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(c);
				new ImportDialog();
				neuerTab(new JPanel(), "neu");
				neuerTabButton(c);
			}
		};
		neuerTab.addActionListener(listener);

	}

	/**
	 * Diese Methode fügt zur Tab- Leiste einen weiteren Tab hinzu. Dabei
	 * besitzt der Tab einen Schließen- Button
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

		JButton schließen = new JButton();
		schließen.setOpaque(false);

		schließen.setIcon(new ImageIcon(
				"src/gui/resources/images/tabSchließen.png"));

		schließen.setBorder(null);

		schließen.setFocusable(false);

		tab.add(lblTitle);
		tab.add(schließen);
		setTabComponentAt(pos, tab);


		//Erstelle anonymen ActionListener für den "x" Knopf
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(c);
			}
		};

		schließen.addActionListener(listener);
		setSelectedComponent(c);
	
	}
}

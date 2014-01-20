package gui;

import importexport.ImportExportManager;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Bundestagswahl;

/**
 * Die Klasse Programmfenster repräsentiert die allgemeine Ansicht des Programms
 * 
 * @author Manuel
 * 
 */

public final class Programmfenster extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6856366470846315047L;
	private Menu menu = null;
	private List<WahlFenster> wahlen = new ArrayList<WahlFenster>();
	private TabLeiste tabs;

	/**
	 * Die Main- Klasse des Programms. Hier startet das Programm.
	 * 
	 * @param args
	 *            Startargumente
	 */
	public static void main(String[] args) {

		new Programmfenster();
	}

	/**
	 * Der Konstruktor des Programmfensters
	 */
	public Programmfenster() {
		// allgemeine Anpassungen des Programmfensters
		setTitle("Mandatsverteilung im Deutschen Bundestag");
		this.setMinimumSize(new Dimension(1024, 768));
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Image icon = new ImageIcon("src/gui/resources/images/wahl2.png")
				.getImage();
		setIconImage(icon);

		// Menü- Leiste erstellen
		menu = new Menu(this);
		setJMenuBar(menu);

		// Wahl 2013
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		Bundestagswahl w = null;
		try {
			w = i.importieren(csvDateien);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wahlen.add(new WahlFenster(w));

		// Tab- Leiste erstellen
		tabs = new TabLeiste(this);
		this.add(tabs);

		setVisible(true);
		pack();
	}

	/**
	 * Diese Methode wechselt zu einem anderen geöffneten Tab
	 * 
	 * @param wf
	 *            Tab, zu dem gewechselt werden soll
	 */

	public void wechsleTab(WahlFenster wf) {
		for (WahlFenster w : wahlen) {
			if (w.equals(wf)) {
				tabs.setSelectedComponent(wf);
			}

		}
	}

	/**
	 * Gibt das Menü zurück
	 * 
	 * @return menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * Setzt das Menü
	 * 
	 * @param menu das Menü
	 */
	public void setMenu(Menu menu) {
		if (menu == null) {
			throw new IllegalArgumentException("Menü war null.");
		} else {
			this.menu = menu;
		}

	}

	/**
	 * Gibt die Liste an Wahlfenstern zurück
	 * 
	 * @return Wahlfenster
	 */
	public List<WahlFenster> getWahlen() {
		return wahlen;
	}

	/**
	 * Setzt die Wahlfenster
	 * 
	 * @param wahlen
	 *            Wahlfenster
	 */
	public void setWahlen(List<WahlFenster> wahlen) {
		if (wahlen == null) {
			throw new IllegalArgumentException("Wahlfenster waren null.");
		} else {
			this.wahlen = wahlen;
		}
	}

	/**
	 * Gibt die Tableiste zurück
	 * 
	 * @return Tableiste
	 */
	public TabLeiste getTabs() {
		return tabs;
	}

	/**
	 * Setzt eine neue Tableiste
	 * 
	 * @param tabs die Tableiste
	 */
	public void setTabs(TabLeiste tabs) {
		if (tabs == null) {
			throw new IllegalArgumentException("Tableiste war null.");
		} else {
			this.tabs = tabs;
		}
	}

}

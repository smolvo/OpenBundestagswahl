package main.java.gui;

import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import test.java.Debug;
import main.java.gui.dialoge.ImportDialog;
import main.java.model.Bundestagswahl;
import main.java.steuerung.Steuerung;

/**
 * Die Klasse Programmfenster repräsentiert die allgemeine Ansicht des Programms
 * 
 * @author Manuel
 * 
 */

public final class Programmfenster extends JFrame {

	private static final long serialVersionUID = -7954024258913364304L;

	/** repräsentiert die Menüleiste */
	private final Menu menu;

	/** repräsentiert die Liste der aktuellgeladenen Wahlen */
	private List<WahlFenster> wahlen = new ArrayList<WahlFenster>();

	/** repräsentiert die Tableiste */
	private TabLeiste tabs;

	/** repräsentiert den ImportDialog des Programms */
	private ImportDialog iD;

	/**
	 * Die Main- Klasse des Programms. Hier startet das Programm.
	 * 
	 * @param args
	 *            Startargumente
	 */
	public static void main(String[] args) {
		Debug.setLevel(6);
		new Programmfenster();
	}

	/**
	 * Der Konstruktor des Programmfensters
	 */
	public Programmfenster() {
		// allgemeine Anpassungen des Programmfensters
		setTitle("OpenBundestagswahl");
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Image icon = new ImageIcon("src/main/resources/gui/images/wahl2.png")
				.getImage();
		this.setIconImage(icon);

		// Menü- Leiste erstellen
		menu = new Menu(this);
		setJMenuBar(menu);

		// Tab- Leiste erstellen
		tabs = new TabLeiste(this);
		this.add(tabs);

		// Wahl 2013
		File[] csvDateien = new File[2];
		csvDateien[0] = new File(
				"src/main/resources/importexport/Ergebnis2013.csv");
		csvDateien[1] = new File(
				"src/main/resources/importexport/Wahlbewerber2013.csv");
		Bundestagswahl w = Steuerung.getInstance().importieren(csvDateien);

		wahlHinzufuegen(w);

		this.setVisible(true);
		pack();
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	/**
	 * Fügt eine Wahl hinzu.
	 * 
	 * @param w
	 *            Wahl
	 * @throws IllegalArgumentException
	 *             wenn das Bundestagswahl-Objekt null ist.
	 */
	public void wahlHinzufuegen(Bundestagswahl w) {
		if (w == null) {
			throw new IllegalArgumentException("Bundestagswahl ist leer.");
		}
		Steuerung.getInstance().setBtw(w);
		Steuerung.getInstance().berechneSitzverteilung();
		WahlFenster fenster = new WahlFenster(w, this);
		this.wahlen.add(fenster);
		this.tabs.neuerTab(fenster, w.getName());
	}

	/**
	 * Gibt das Menü zurück.
	 * 
	 * @return Menü
	 */
	public Menu getMenu() {
		return menu;
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
	 * @throws IllegalArgumentException
	 *             wenn das Listenobjekt null ist.
	 */
	public void setWahlen(List<WahlFenster> wahlen) {
		if (wahlen == null) {
			throw new IllegalArgumentException("Wahlfenster sind null.");
		} else {
			this.wahlen = wahlen;
		}
	}

	/**
	 * Gibt die Tableiste zurück.
	 * 
	 * @return Tableiste
	 */
	public TabLeiste getTabs() {
		return tabs;
	}

	/**
	 * Setzt eine neue Tableiste.
	 * 
	 * @param tabs
	 *            die Tableiste
	 * @throws IllegalArgumentException
	 *             wenn die TabLeiste null ist.
	 */
	public void setTabs(TabLeiste tabs) {
		if (tabs == null) {
			throw new IllegalArgumentException("Tableiste war null.");
		} else {
			this.tabs = tabs;
		}
	}

	/**
	 * Diese Methode gibt eine Liste an Bundestagswahlen aus, die in das
	 * Programm geladen wurden.
	 * 
	 * @return Bundestagswahl
	 */
	public List<Bundestagswahl> getBundestagswahlen() {
		List<Bundestagswahl> btws = new LinkedList<Bundestagswahl>();
		for (WahlFenster fenster : this.wahlen) {
			btws.add(fenster.getBtw());
		}
		return btws;
	}

	/**
	 * Gibt den ImportDialog aus.
	 * 
	 * @return ImportDialog
	 */
	public ImportDialog getiD() {
		return iD;
	}

	/**
	 * Setzt den ImportDialog.
	 * 
	 * @param iD
	 *            ImportDialog
	 * @throws IllegalArgumentException
	 *             wenn das ImportDialog-Objekt null ist.
	 */
	public void setiD(ImportDialog iD) {
		if (iD == null) {
			throw new IllegalArgumentException("Keinen ImportDialog gefunden.");
		}
		this.iD = iD;
	}

}

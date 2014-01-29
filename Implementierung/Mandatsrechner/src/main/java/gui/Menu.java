package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import main.java.gui.dialoge.AboutDialog;
import main.java.gui.dialoge.ExportDialog;

import main.java.gui.dialoge.GeneratorDialog;
import main.java.gui.dialoge.HandbuchDialog;
import main.java.gui.dialoge.ImportDialog;
import main.java.gui.dialoge.LizenzDialog;
import main.java.gui.dialoge.VergleichDialog;
import main.java.steuerung.Steuerung;

/**
 * 
 * Diese Klasse repräsentiert die Menü- Leiste des Programmfensters.
 * 
 */
public class Menu extends JMenuBar {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -2922572375430998442L;

	/** repräsentiert das Programmfenster */
	private final Programmfenster pf;
	
	/** repräsentiert den Menüleisteneintrag Datei */
	private final JMenu datei;
	
	/** repräsentiert den Menüleisteneintrag Bearbeiten */
	private final JMenu bearbeiten;
	
	/** repräsentiert den Menüleisteneintrag Extras */
	private final JMenu extras;
	
	/** repräsentiert den Menüleisteneintrag Hilfe */
	private final JMenu hilfe;
	
	/** repräsentiert den Menüleisteneintrag Wahldaten Generieren */
	private final JMenu wahldatenGenerieren;

	/** repräsentiert den Importieren Eintrag */
	private final JMenuItem importieren;
	
	/** repräsentiert den Speichern Eintrag */
	private final JMenuItem speichern;
	
	/** repräsentiert den Beenden Eintrag */
	private final JMenuItem beenden;
	
	/** repräsentiert den Rückgängig Eintrag */
	private final JMenuItem rueckgaengig;
	
	/** repräsentiert den Wiederherstellen Eintrag */
	private final JMenuItem wiederherstellen;
	
	/** repräsentiert den Vergleichen Eintrag */
	private final JMenuItem vergleichen;
	
	/** repräsentiert den negatives Stimmgewicht generieren Eintrag */
	private final JMenuItem negativesStimmgewicht;
	
	/** repräsentiert den Zufällige Wahl generieren Eintrag */
	private final JMenuItem zufaelligeWahl;
	
	/** repräsentiert den Handbuch Eintrag */
	private final JMenuItem handbuch;
	
	/** repräsentiert den About Eintrag */
	private final JMenuItem about;
	
	/** repräsentiert den Lizenz Eintrag */
	private final JMenuItem lizenz;
	
	/** repräsentiert den Einstellungen Eintrag */
	private final JMenuItem einstellungen;
	
	

	/**
	 * Der Konstruktor des Menüs
	 * 
	 * @param pf
	 *            Programmfenster
	 */
	public Menu(Programmfenster pf) {
		this.pf = pf;
		datei = new JMenu("Datei");
		bearbeiten = new JMenu("Bearbeiten");
		extras = new JMenu("Extras");
		hilfe = new JMenu("Hilfe");

		// der Menu-Reiter "Datei"
		importieren = new JMenuItem("Importieren");
		importieren.setIcon(new ImageIcon(
				"src/main/resources/gui/images/importieren.png"));
		speichern = new JMenuItem("Speichern");
		speichern.setIcon(new ImageIcon(
				"src/main/resources/gui/images/speichern.png"));
		beenden = new JMenuItem("Beenden");
		beenden.setIcon(new ImageIcon("src/main/resources/gui/images/beenden.png"));

		datei.add(importieren);
		datei.add(speichern);
		datei.addSeparator();
		datei.add(beenden);

		// der Menu-Reiter "Bearbeiten"
		rueckgaengig = new JMenuItem("Rückgängig");
		rueckgaengig.setIcon(new ImageIcon(
				"src/main/resources/gui/images/rueckgaengig.png"));
		rueckgaengig.setEnabled(true);
		wiederherstellen = new JMenuItem("Wiederherstellen");
		wiederherstellen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/wiederherstellen.png"));

		bearbeiten.add(rueckgaengig);
		bearbeiten.add(wiederherstellen);

		// der Menu-Reiter "Extras"
		vergleichen = new JMenuItem("Vergleichen");
		vergleichen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/vergleichen.png"));
		wahldatenGenerieren = new JMenu("Wahldaten generieren");
		negativesStimmgewicht = new JMenuItem(
				"negatives Stimmgewicht generieren");
		zufaelligeWahl = new JMenuItem("zufällige Wahl generieren");
		einstellungen = new JMenuItem("Einstellungen");
		einstellungen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/einstellungen.png"));

		extras.add(vergleichen);
		extras.add(wahldatenGenerieren);
		extras.add(einstellungen);

		wahldatenGenerieren.add(negativesStimmgewicht);
		wahldatenGenerieren.add(zufaelligeWahl);

		// der Menu-Reiter "Hilfe"
		handbuch = new JMenuItem("Handbuch");
		handbuch.setIcon(new ImageIcon("src/main/resources/gui/images/handbuch.png"));
		about = new JMenuItem("About");
		about.setIcon(new ImageIcon("src/main/resources/gui/images/about.png"));
		lizenz = new JMenuItem("Lizenz");
		lizenz.setIcon(new ImageIcon("src/main/resources/gui/images/gpl.png"));
		
		hilfe.add(handbuch);
		hilfe.add(about);
		hilfe.add(lizenz);

		rueckgaengig.setEnabled(false);
		wiederherstellen.setEnabled(false);

		this.add(datei);
		this.add(bearbeiten);
		this.add(extras);
		this.add(hilfe);

		MenuListener m = new MenuListener(this);
		importieren.addActionListener(m);
		speichern.addActionListener(m);
		beenden.addActionListener(m);
		rueckgaengig.addActionListener(m);
		wiederherstellen.addActionListener(m);
		vergleichen.addActionListener(m);
		negativesStimmgewicht.addActionListener(m);
		zufaelligeWahl.addActionListener(m);
		handbuch.addActionListener(m);
		about.addActionListener(m);
		lizenz.addActionListener(m);
		
		setVisible(true);
	}

	/**
	 * eingebetteter Listener zur Menu- Leiste
	 * 
	 */
	private class MenuListener implements ActionListener {

		/** rerpäsentiert das Menü */
		private Menu menu;

		/**
		 * Der Konstruktor initialisiert den Listener.
		 * @param menu Menü
		 */
		public MenuListener(Menu menu) {
			super();
			this.menu = menu;
		}
		
		/**
		 * Diese Methode importiert eine Datei.
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

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == menu.importieren) {
				importiere();
			} else if (e.getSource() == menu.speichern) {
				new ExportDialog(pf);
			} else if (e.getSource() == menu.beenden) {
				int eingabe = JOptionPane.showConfirmDialog(null,
						"Soll Programm wirklich beendet werden?",
						"Einverständnis", JOptionPane.YES_NO_OPTION);
				if (eingabe == 0) {
					System.exit(0);
				}
			} else if (e.getSource() == menu.rueckgaengig) {
				Steuerung.getInstance().zurueckSetzen();
			} else if (e.getSource() == menu.wiederherstellen) {
				// TODO wiederherstellen
			} else if (e.getSource() == menu.vergleichen) {
				new VergleichDialog(pf);
			} else if (e.getSource() == menu.negativesStimmgewicht) {
				// TODO negatives Stimmgewicht simulieren
			} else if (e.getSource() == menu.zufaelligeWahl) {
				new GeneratorDialog(pf.getBundestagswahlen(), pf);
			} else if (e.getSource() == menu.handbuch) {
				new HandbuchDialog();
			} else if (e.getSource() == menu.about) {
				new AboutDialog();
			} else if (e.getSource() == menu.lizenz) {
				new LizenzDialog();
			}
		}
	}
}

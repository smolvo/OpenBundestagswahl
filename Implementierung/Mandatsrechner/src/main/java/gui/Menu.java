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
 * Diese Klasse repr�sentiert die Men�- Leiste des Programmfensters.
 * 
 */
public class Menu extends JMenuBar {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -2922572375430998442L;

	/** repr�sentiert das Programmfenster */
	private final Programmfenster pf;
	
	/** repr�sentiert den Men�leisteneintrag Datei */
	private final JMenu datei;
	
	/** repr�sentiert den Men�leisteneintrag Bearbeiten */
	private final JMenu bearbeiten;
	
	/** repr�sentiert den Men�leisteneintrag Extras */
	private final JMenu extras;
	
	/** repr�sentiert den Men�leisteneintrag Hilfe */
	private final JMenu hilfe;
	
	/** repr�sentiert den Men�leisteneintrag Wahldaten Generieren */
	private final JMenu wahldatenGenerieren;

	/** repr�sentiert den Importieren Eintrag */
	private final JMenuItem importieren;
	
	/** repr�sentiert den Speichern Eintrag */
	private final JMenuItem speichern;
	
	/** repr�sentiert den Beenden Eintrag */
	private final JMenuItem beenden;
	
	/** repr�sentiert den R�ckg�ngig Eintrag */
	private final JMenuItem rueckgaengig;
	
	/** repr�sentiert den Wiederherstellen Eintrag */
	private final JMenuItem wiederherstellen;
	
	/** repr�sentiert den Vergleichen Eintrag */
	private final JMenuItem vergleichen;
	
	/** repr�sentiert den negatives Stimmgewicht generieren Eintrag */
	private final JMenuItem negativesStimmgewicht;
	
	/** repr�sentiert den Zuf�llige Wahl generieren Eintrag */
	private final JMenuItem zufaelligeWahl;
	
	/** repr�sentiert den Handbuch Eintrag */
	private final JMenuItem handbuch;
	
	/** repr�sentiert den About Eintrag */
	private final JMenuItem about;
	
	/** repr�sentiert den Lizenz Eintrag */
	private final JMenuItem lizenz;
	
	/** repr�sentiert den Einstellungen Eintrag */
	private final JMenuItem einstellungen;
	
	

	/**
	 * Der Konstruktor des Men�s
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
		rueckgaengig = new JMenuItem("R�ckg�ngig");
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
		zufaelligeWahl = new JMenuItem("zuf�llige Wahl generieren");
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

		/** rerp�sentiert das Men� */
		private Menu menu;

		/**
		 * Der Konstruktor initialisiert den Listener.
		 * @param menu Men�
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
						"Einverst�ndnis", JOptionPane.YES_NO_OPTION);
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

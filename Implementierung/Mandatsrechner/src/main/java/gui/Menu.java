package main.java.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import main.java.gui.dialoge.AboutDialog;
import main.java.gui.dialoge.EinstellungenDialog;
import main.java.gui.dialoge.ExportDialog;
import main.java.gui.dialoge.ImportDialog;
import main.java.gui.dialoge.VergleichDialog;
import main.java.steuerung.Steuerung;

/**
 * 
 * Diese Klasse repräsentiert die Menü- Leiste des Programmfensters
 * 
 */
public class Menu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -40733287939914615L;
	private Programmfenster pf;
	private JMenu datei;
	private JMenu bearbeiten;
	private JMenu extras;
	private JMenu hilfe;
	private JMenu wahldatenGenerieren;

	private JMenuItem importieren;
	private JMenuItem speichern;
	private JMenuItem beenden;
	private JMenuItem rueckgaengig;
	private JMenuItem wiederherstellen;
	private JMenuItem vergleichen;
	private JMenuItem negativesStimmgewicht;
	private JMenuItem zufaelligeWahl;
	private JMenuItem handbuch;
	private JMenuItem about;
	private JMenuItem einstellungen;
	
	

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

		hilfe.add(handbuch);
		hilfe.add(about);

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

		setVisible(true);
	}

	/**
	 * eingebetteter Listener zur Menu- Leiste
	 * 
	 */
	private class MenuListener implements ActionListener {

		private Menu menu;

		public MenuListener(Menu menu) {
			super();
			this.menu = menu;
		}
		
		private void importiere() {
			if (pf.getiD() == null) {
				ImportDialog iD = new ImportDialog(pf.getTabs());
				pf.setiD(iD);
				pf.getiD().importiereWahl(pf.getTabs());
			} else {
				pf.getiD().importiereWahl(pf.getTabs());
			}
		}
			

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == menu.importieren) {
				importiere();
				
			} else if (e.getSource() == menu.speichern) {
				new ExportDialog(pf.getTabs());
			} else if (e.getSource() == menu.beenden) {

				int eingabe = JOptionPane.showConfirmDialog(null,
						"Soll Programm wirklich beendet werden?",
						"Einverständnis", JOptionPane.YES_NO_OPTION);

				if (eingabe == 0) {
					System.exit(0);
				}

			}

			else if (e.getSource() == menu.rueckgaengig) {
				Steuerung.getInstance().zurueckSetzen();
			} else if (e.getSource() == menu.wiederherstellen) {
				// TODO wiederherstellen
			} else if (e.getSource() == menu.vergleichen) {
				new VergleichDialog(pf.getTabs());
			} else if (e.getSource() == menu.negativesStimmgewicht) {
				// TODO negatives Stimmgewicht simulieren
			} else if (e.getSource() == menu.zufaelligeWahl) {
				new GeneratorFenster(pf.getBundestagswahlen(), pf);
			} else if (e.getSource() == menu.handbuch) {
				// TODO Handbuch
			} else if (e.getSource() == menu.about) {
				new AboutDialog();
			} else if (e.getSource() == menu.einstellungen) {
				new EinstellungenDialog();
			}

		}
	

	}
	
	
	
}

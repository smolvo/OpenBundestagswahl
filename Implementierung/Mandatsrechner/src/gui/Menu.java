package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * Diese Klasse repräsentiert die Menü- Leiste des Programmfensters
 *
 */
public class Menu extends JMenuBar {

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
	
	/*
	 * Der Konstruktor der Menü- Leiste
	 */
	public Menu() {
		datei = new JMenu("Datei");
		bearbeiten = new JMenu("Bearbeiten");
		extras = new JMenu("Extras");
		hilfe = new JMenu("Hilfe");
		
		//der Menu-Reiter "Datei"
		importieren = new JMenuItem("Importieren");
		speichern = new JMenuItem("Speichern");
		beenden = new JMenuItem("Beenden");
		
		datei.add(importieren);
		datei.add(speichern);
		datei.add(beenden);
		
		
		
		//der Menu-Reiter "Bearbeiten"
		rueckgaengig = new JMenuItem("Rückgängig");
		wiederherstellen = new JMenuItem("Wiederherstellen");

		bearbeiten.add(rueckgaengig);
		bearbeiten.add(wiederherstellen);
		
	
		
		//der Menu-Reiter "Extras"
		vergleichen = new JMenuItem("Vergleichen");
		wahldatenGenerieren = new JMenu("Wahldaten generieren");
		negativesStimmgewicht = new JMenuItem("negatives Stimmgewicht generieren");
		zufaelligeWahl = new JMenuItem("zufällige Wahl generieren");		
		einstellungen = new JMenuItem("Einstellungen");
		
		extras.add(vergleichen);
		extras.add(wahldatenGenerieren);
		extras.add(einstellungen);
		
		wahldatenGenerieren.add(negativesStimmgewicht);
		wahldatenGenerieren.add(zufaelligeWahl);
		
		
		//der Menu-Reiter "Hilfe"
		handbuch = new JMenuItem("Handbuch");
		about = new JMenuItem("About");
		about.setIcon(new ImageIcon("src/gui/resources/images/About.png"));
		
		hilfe.add(handbuch);
		hilfe.add(about);
		
		
		
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

		public void actionPerformed(ActionEvent e)
		  {
			 if (e.getSource() == menu.importieren) {
				JDialog importDialog = new JDialog();
				importDialog.setSize(200,200);
				importDialog.add(new JLabel("Importieren.."));
				importDialog.setVisible(true);
			 } else if (e.getSource() == menu.speichern) {
				//TODO export
			 } else if (e.getSource() == menu.beenden) {
				 System.exit(0);
			 } else if (e.getSource() == menu.rueckgaengig) {
				//TODO rückgängig
			 } else if (e.getSource() == menu.wiederherstellen) {
				//TODO wiederherstellen
			 } else if (e.getSource() == menu.vergleichen) {
				//TODO vergleichen
			 } else if (e.getSource() == menu.negativesStimmgewicht) {
				//TODO negatives Stimmgewicht simulieren
			 } else if (e.getSource() == menu.zufaelligeWahl) {
				//TODO zufällige Wahl generieren
			 } else if (e.getSource() == menu.handbuch) {
				//TODO Handbuch
			 } else if (e.getSource() == menu.about) {
				//TODO About
			 }
			 
		  }

	
	}
}

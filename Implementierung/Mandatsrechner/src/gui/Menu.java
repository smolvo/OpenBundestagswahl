package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * 
 * Diese Klasse repräsentiert die Menü- Leiste des Programmfensters
 *
 */
public class Menu extends JMenuBar {

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
	
	/*
	 * Der Konstruktor der Menü- Leiste
	 */
	public Menu(Programmfenster pf) {
		this.pf = pf;
		datei = new JMenu("Datei");
		bearbeiten = new JMenu("Bearbeiten");
		extras = new JMenu("Extras");
		hilfe = new JMenu("Hilfe");
		
		//der Menu-Reiter "Datei"
		importieren = new JMenuItem("Importieren");
		importieren.setIcon(new ImageIcon("src/gui/resources/images/importieren.png"));
		speichern = new JMenuItem("Speichern");
		speichern.setIcon(new ImageIcon("src/gui/resources/images/speichern.png"));
		beenden = new JMenuItem("Beenden");
		beenden.setIcon(new ImageIcon("src/gui/resources/images/beenden.png"));
		
		datei.add(importieren);
		datei.add(speichern);
		datei.addSeparator();
		datei.add(beenden);
		
		
		
		//der Menu-Reiter "Bearbeiten"
		rueckgaengig = new JMenuItem("Rückgängig");
		rueckgaengig.setIcon(new ImageIcon("src/gui/resources/images/rueckgaengig.png"));
		wiederherstellen = new JMenuItem("Wiederherstellen");
		wiederherstellen.setIcon(new ImageIcon("src/gui/resources/images/wiederherstellen.png"));
		
		bearbeiten.add(rueckgaengig);
		bearbeiten.add(wiederherstellen);
		
	
		
		//der Menu-Reiter "Extras"
		vergleichen = new JMenuItem("Vergleichen");
		vergleichen.setIcon(new ImageIcon("src/gui/resources/images/vergleichen.png"));
		wahldatenGenerieren = new JMenu("Wahldaten generieren");
		negativesStimmgewicht = new JMenuItem("negatives Stimmgewicht generieren");
		zufaelligeWahl = new JMenuItem("zufällige Wahl generieren");		
		einstellungen = new JMenuItem("Einstellungen");
		einstellungen.setIcon(new ImageIcon("src/gui/resources/images/einstellungen.png"));
		
		extras.add(vergleichen);
		extras.add(wahldatenGenerieren);
		extras.add(einstellungen);
		
		wahldatenGenerieren.add(negativesStimmgewicht);
		wahldatenGenerieren.add(zufaelligeWahl);
		
		
		//der Menu-Reiter "Hilfe"
		handbuch = new JMenuItem("Handbuch");
		handbuch.setIcon(new ImageIcon("src/gui/resources/images/handbuch.png"));
		about = new JMenuItem("About");
		about.setIcon(new ImageIcon("src/gui/resources/images/about.png"));
		
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

		public void actionPerformed(ActionEvent e)
		  {
			 if (e.getSource() == menu.importieren) {
				new ImportDialog(pf.getTabs());
			 } else if (e.getSource() == menu.speichern) {
				 new ExportDialog(pf.getTabs());
			 } else if (e.getSource() == menu.beenden) {
					
					int eingabe = JOptionPane.showConfirmDialog(null,
	                        "Soll Programm wirklich beendet werden?",
	                        "Einverständnis",
	                        JOptionPane.YES_NO_OPTION);
					
					if(eingabe == 0) {
						 System.exit(0);
					}
					
				}
				
			  else if (e.getSource() == menu.rueckgaengig) {
				//TODO rückgängig
			 } else if (e.getSource() == menu.wiederherstellen) {
				//TODO wiederherstellen
			 } else if (e.getSource() == menu.vergleichen) {
				new VergleichsDialog();
			 } else if (e.getSource() == menu.negativesStimmgewicht) {
				//TODO negatives Stimmgewicht simulieren
			 } else if (e.getSource() == menu.zufaelligeWahl) {
				//TODO zufällige Wahl generieren
			 } else if (e.getSource() == menu.handbuch) {
				//TODO Handbuch
			 } else if (e.getSource() == menu.about) {
				new AboutDialog();
			 }
			 
		  }

	
	}
}

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
 * Diese Klasse repräsentiert die Menä- Leiste des Programmfensters.
 * 
 */
public class Menu extends JMenuBar {

	/**
	 * eingebetteter Listener zur Menu- Leiste
	 * 
	 */
	private class MenuListener implements ActionListener {

		/** rerpäsentiert das Menä */
		private final Menu menu;

		/**
		 * Der Konstruktor initialisiert den Listener.
		 * 
		 * @param menu
		 *            Menü
		 */
		public MenuListener(Menu menu) {
			super();
			this.menu = menu;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == this.menu.importieren) {
				importiere();
			} else if (e.getSource() == this.menu.speichern) {
				new ExportDialog(Menu.this.pf);
			} else if (e.getSource() == this.menu.beenden) {
				final int eingabe = JOptionPane.showConfirmDialog(null,
						"Soll Programm wirklich beendet werden?",
						"Einverständnis", JOptionPane.YES_NO_OPTION);
				if (eingabe == 0) {
					System.exit(0);
				}
			} else if (e.getSource() == this.menu.rueckgaengig) {
				Steuerung.getInstance().zurueckSetzen();
				Steuerung.getInstance().berechneSitzverteilung();
				setzeSichtbarkeit();
				final WahlFenster fenster = (WahlFenster) Menu.this.pf
						.getTabs().getSelectedComponent();
				Menu.this.pf
						.getTabs()
						.getWahlfenster()
						.getSteuerung()
						.aktualisiereWahlfenster(
								fenster.getAnsicht().getAktuellesGebiet());

			} else if (e.getSource() == this.menu.wiederherstellen) {
				Steuerung.getInstance().wiederherrstellen();
				Steuerung.getInstance().berechneSitzverteilung();
				setzeSichtbarkeit();
				final WahlFenster fenster = (WahlFenster) Menu.this.pf
						.getTabs().getSelectedComponent();
				Menu.this.pf
						.getTabs()
						.getWahlfenster()
						.getSteuerung()
						.aktualisiereWahlfenster(
								fenster.getAnsicht().getAktuellesGebiet());
			} else if (e.getSource() == this.menu.vergleichen) {
				new VergleichDialog(Menu.this.pf);
			} else if (e.getSource() == this.menu.zufaelligeWahl) {
				new GeneratorDialog(Menu.this.pf.getBundestagswahlen(),
						Menu.this.pf);
			} else if (e.getSource() == this.menu.handbuch) {
				new HandbuchDialog();
			} else if (e.getSource() == this.menu.about) {
				new AboutDialog();
			} else if (e.getSource() == this.menu.lizenz) {
				new LizenzDialog();
			}
		}

		/**
		 * Diese Methode importiert eine Datei.
		 */
		private void importiere() {
			if (Menu.this.pf.getiD() == null) {
				final ImportDialog iD = new ImportDialog(Menu.this.pf);
				Menu.this.pf.setiD(iD);
				Menu.this.pf.getiD().importiereWahl();
			} else {
				Menu.this.pf.getiD().importiereWahl();
			}
		}
	}

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

	// /** repräsentiert den Menäleisteneintrag Wahldaten Generieren */
	// private final JMenu wahldatenGenerieren;

	/** repräsentiert den Menüleisteneintrag Hilfe */
	private final JMenu hilfe;

	/** repräsentiert den Importieren Eintrag */
	private final JMenuItem importieren;

	/** repräsentiert den Speichern Eintrag */
	private final JMenuItem speichern;

	/** repräsentiert den Beenden Eintrag */
	private final JMenuItem beenden;

	/** repräsentiert den Räckgängig Eintrag */
	private final JMenuItem rueckgaengig;

	/** repräsentiert den Wiederherstellen Eintrag */
	private final JMenuItem wiederherstellen;

	/** repräsentiert den Vergleichen Eintrag */
	private final JMenuItem vergleichen;

	/** repräsentiert den Zufällige Wahl generieren Eintrag */
	private final JMenuItem zufaelligeWahl;

	/** repräsentiert den Handbuch Eintrag */
	private final JMenuItem handbuch;

	/** repräsentiert den About Eintrag */
	private final JMenuItem about;

	/** repräsentiert den Lizenz Eintrag */
	private final JMenuItem lizenz;

	/**
	 * Der Konstruktor des Menäs
	 * 
	 * @param pf
	 *            Programmfenster
	 * @throws IllegalArgumentException
	 *             wenn das Programmfenster null ist.
	 */
	public Menu(Programmfenster pf) {
		if (pf == null) {
			throw new IllegalArgumentException("Eingabe-Parameter sind null.");
		}
		this.pf = pf;
		this.datei = new JMenu("Datei");
		this.bearbeiten = new JMenu("Bearbeiten");
		this.extras = new JMenu("Extras");
		this.hilfe = new JMenu("Hilfe");

		// der Menu-Reiter "Datei"
		this.importieren = new JMenuItem("Importieren");
		this.importieren.setIcon(new ImageIcon(
				"src/main/resources/gui/images/importieren.png"));
		this.speichern = new JMenuItem("Speichern");
		this.speichern.setIcon(new ImageIcon(
				"src/main/resources/gui/images/speichern.png"));
		this.beenden = new JMenuItem("Beenden");
		this.beenden.setIcon(new ImageIcon(
				"src/main/resources/gui/images/beenden.png"));

		this.datei.add(this.importieren);
		this.datei.add(this.speichern);
		this.datei.addSeparator();
		this.datei.add(this.beenden);

		// der Menu-Reiter "Bearbeiten"
		this.rueckgaengig = new JMenuItem("Rückgängig");
		this.rueckgaengig.setIcon(new ImageIcon(
				"src/main/resources/gui/images/rueckgaengig.png"));

		this.wiederherstellen = new JMenuItem("Wiederherstellen");
		this.wiederherstellen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/wiederherstellen.png"));

		this.bearbeiten.add(this.rueckgaengig);
		this.bearbeiten.add(this.wiederherstellen);

		// der Menu-Reiter "Extras"
		this.vergleichen = new JMenuItem("Vergleichen");
		this.vergleichen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/vergleichen.png"));
		this.zufaelligeWahl = new JMenuItem("Wahl generieren");

		this.extras.add(this.vergleichen);
		this.extras.add(this.zufaelligeWahl);

		// der Menu-Reiter "Hilfe"
		this.handbuch = new JMenuItem("Handbuch");
		this.handbuch.setIcon(new ImageIcon(
				"src/main/resources/gui/images/handbuch.png"));
		this.about = new JMenuItem("About");
		this.about.setIcon(new ImageIcon(
				"src/main/resources/gui/images/about.png"));
		this.lizenz = new JMenuItem("Lizenz");
		this.lizenz.setIcon(new ImageIcon(
				"src/main/resources/gui/images/gpl.png"));

		this.hilfe.add(this.handbuch);
		this.hilfe.add(this.about);
		this.hilfe.add(this.lizenz);

		this.rueckgaengig.setEnabled(false);
		this.wiederherstellen.setEnabled(false);

		this.add(this.datei);
		this.add(this.bearbeiten);
		this.add(this.extras);
		this.add(this.hilfe);

		final MenuListener m = new MenuListener(this);
		this.importieren.addActionListener(m);
		this.speichern.addActionListener(m);
		this.beenden.addActionListener(m);
		this.rueckgaengig.addActionListener(m);
		this.wiederherstellen.addActionListener(m);
		this.vergleichen.addActionListener(m);
		this.zufaelligeWahl.addActionListener(m);
		this.handbuch.addActionListener(m);
		this.about.addActionListener(m);
		this.lizenz.addActionListener(m);

		setVisible(true);
	}

	/**
	 * Setzt die Sichtbarkeit des Rueckgaengig JMenu Items
	 * 
	 * @param verfuegbarkeit
	 *            Boolean
	 */
	public void setzeSichtbarkeit() {
		if (Steuerung.getInstance().getBtw().hatStimmenZumWiederherstellen()) {
			this.wiederherstellen.setEnabled(true);
		} else {
			this.wiederherstellen.setEnabled(false);
		}
		if (!Steuerung.getInstance().getBtw().hatStimmenZumZuruecksetzen()) {
			this.rueckgaengig.setEnabled(false);
		} else {
			this.rueckgaengig.setEnabled(true);
		}

	}
}

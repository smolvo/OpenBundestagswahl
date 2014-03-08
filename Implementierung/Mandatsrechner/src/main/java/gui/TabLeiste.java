package main.java.gui;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.gui.dialoge.ExportDialog;
import main.java.gui.dialoge.ImportDialog;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repräsentiert die Tab- Leiste des Programmfensters Jeder Tab
 * steht für eine im Programm geöffnete Bundestagswahl
 * 
 */
public class TabLeiste extends JTabbedPane {

	/** Automatisch generierte serialVersionUID */
	private static final long serialVersionUID = -2584456939802031910L;

	/** repräsentiert das Programmfenster der Tableiste */
	private final Programmfenster pf;

	/** repräsentiert den Plus-Knopf der Tableiste */
	private final JComponent plusButton = new JPanel();

	/**
	 * Der Konstruktor der Tableiste
	 * 
	 * @param pf
	 *            Programmfenster
	 * @throws IllegalArgumentException
	 *             wenn das Programmfenster-Objekt null ist.
	 */
	public TabLeiste(final Programmfenster pf) {
		if (pf == null) {
			throw new IllegalArgumentException("Kein Programmfenster gefunden.");
		}
		this.pf = pf;
		// allgemeine Einstellungen der Tab- Leiste
		setTabLayoutPolicy(SwingConstants.TOP);
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		neuerTabButton();
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				final int i = getSelectedIndex();
				if (i > 0) {
					Steuerung.getInstance().setBtw(
							pf.getWahlen().get(i).getBtw());
				}

			}

		});
	}

	/**
	 * Gibt das Programmfenster aus
	 * 
	 * @return Programmfenster
	 */
	public Programmfenster getPf() {
		return this.pf;
	}

	/**
	 * Gibt das index-te Wahlfenster aus.
	 * 
	 * @return Wahlfenster
	 */
	public WahlFenster getWahlfenster() {
		return (WahlFenster) getSelectedComponent();
	}

	/**
	 * Diese private Methode wird vom Plus-Knopf verwendet, um eine Wahl zu
	 * importieren.
	 */
	private void importiere() {
		if (this.pf.getiD() == null) {
			final ImportDialog iD = new ImportDialog(this.pf);
			this.pf.setiD(iD);
			this.pf.getiD().importiereWahl();
		} else {
			this.pf.getiD().importiereWahl();
		}
	}

	/**
	 * Diese Methode fügt zur Tableiste einen weiteren Tab hinzu. Dabei besitzt
	 * der Tab einen Schließen- Button
	 * 
	 * @param c
	 *            Inhalt des Tabs
	 * @param wahlName
	 *            Name der im Tab gezeigten Wahl
	 * @throws IllegalArgumentException
	 *             wenn die Eingabeparameter null sind.
	 */

	public void neuerTab(final WahlFenster c, final String wahlName) {
		if (c == null || wahlName == null) {
			throw new IllegalArgumentException(
					"Einer der zwei Parameter ist null.");
		}
		this.add(c);
		final int pos = indexOfComponent(c);

		final FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		final JPanel tab = new JPanel(f);
		tab.addMouseListener(new MouseAdapter() {

			// bei Tabwechsel wird die Bundestagswahl des Tabs in die Steuerung
			// geladen
			@Override
			public void mouseClicked(MouseEvent e) {
				Steuerung.getInstance().setBtw(c.getBtw());
				setSelectedComponent(c);
			}

		});
		tab.setOpaque(false);

		final JLabel lblTitle = new JLabel(wahlName);

		final JButton schliessen = new JButton();
		schliessen.setOpaque(false);
		schliessen.setIcon(new ImageIcon(
				"src/main/resources/gui/images/tabSchließen.png"));
		schliessen.setBorder(null);
		schliessen.setFocusable(false);

		tab.add(lblTitle);
		tab.add(schliessen);
		setTabComponentAt(pos, tab);

		// Erstelle anonymen ActionListener für den "x" Knopf
		schliessen.addMouseListener(new MouseAdapter() {
			/*
			 * Wird versucht, einen Tab zu schließen, wird zuerst gefragt, ob
			 * dieser Tab gespeichert werden soll
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if (TabLeiste.this.pf.getWahlen().size() > 1) {
					final int eingabe = JOptionPane.showConfirmDialog(null,
							"Soll Datei gespeichert werden?", "Einverständnis",
							JOptionPane.YES_NO_CANCEL_OPTION);

					if (eingabe == 0) {
						new ExportDialog(TabLeiste.this.pf);
						final WahlFenster naechst = (WahlFenster) TabLeiste.this
								.getComponentAt(0);
						setSelectedComponent(naechst);
						Steuerung.getInstance().setBtw(naechst.getBtw());
					} else if (eingabe == 1) {
						remove(c);
						TabLeiste.this.pf.getWahlen().remove(c);
						final WahlFenster naechst = (WahlFenster) TabLeiste.this
								.getComponentAt(0);
						setSelectedComponent(naechst);
						Steuerung.getInstance().setBtw(naechst.getBtw());
					}
				}
			}
		});

		this.remove(this.plusButton);
		neuerTabButton();
		setSelectedComponent(c);
	}

	/**
	 * Diese Methode fügt der Tableiste einen "+"- Button hinzu Wenn man darauf
	 * klickt, öffnet sich der Dialog zum Importieren einer neuern Wahl.
	 */
	public final void neuerTabButton() {
		this.add(this.plusButton);
		final int pos = indexOfComponent(this.plusButton);

		final FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);
		final JPanel pnlTab = new JPanel(f);
		pnlTab.setOpaque(false);

		final JButton neuerTab = new JButton();
		neuerTab.setOpaque(false);
		neuerTab.setIcon(new ImageIcon(
				"src/main/resources/gui/images/neuerTab.png"));

		neuerTab.setBorder(null);
		neuerTab.setContentAreaFilled(false);
		neuerTab.setFocusPainted(false);
		neuerTab.setFocusable(false);

		pnlTab.add(neuerTab);
		setTabComponentAt(pos, pnlTab);

		setEnabledAt(pos, false);
		// Erstelle anonymen ActionListener für den "+" Knopf
		neuerTab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				remove(TabLeiste.this.plusButton);
				importiere();
				neuerTabButton();
			}
		});
	}
}

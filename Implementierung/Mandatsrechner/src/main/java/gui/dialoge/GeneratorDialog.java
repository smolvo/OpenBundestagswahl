package main.java.gui.dialoge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.gui.Programmfenster;
import main.java.model.Bundestagswahl;
import main.java.model.Partei;
import main.java.steuerung.Steuerung;
import main.java.wahlgenerator.Stimmanteile;

/**
 * Diese Klasse repräsentiert das GeneratorFenster. In ihr können Erst- und
 * Zweitstimmenanteile für vorgegebene Parteien einer zuvor ausgesuchten
 * Bundestagswahl festegelegt werden.
 * 
 * @author Anton
 * 
 */
public class GeneratorDialog extends JDialog {

	private static final long serialVersionUID = 3558472603505570667L;

	/** repräsentiert die Liste der Basiswahlen */
	private final List<Bundestagswahl> wahlen;

	/**
	 * repräsentiert die Wahl die gerade vom Benutzer als Standardwahl
	 * festgelegt wurde
	 */
	private Bundestagswahl ausgesuchteWahl;

	private final JDialog generatorDialog;

	/** Basiswahl */
	private JLabel basiswahl;

	/** Stimmenanteile */
	private JLabel stimmenanteile;

	/** Label für den neuen Namen */
	private JLabel neueWahlNameLabel;

	/** gesamte Erststimmen */
	private JLabel gesamtErst;

	/** gesamte Zweitstimmen */
	private JLabel gesamtZweit;

	/** Combobox um die Basiswahl auszusuchen */
	private JComboBox<Bundestagswahl> basiswahlAuswahl;

	/** Textfeld in dem man den neuen Namen eingeben kann */
	private JTextField neueWahlNameBox;

	/** panel im ScrollPane */
	private JPanel hauptPanel;

	/** ScrollPane */
	private JScrollPane pane;

	/** Generiere Knopf */
	private JButton generiere;

	/** die aktuelle Anzahl der Erststimmen, die der Nutzer eingegeben hat */
	private int gesamtErststimmen = 0;

	/** die aktuelle Anzahl der Zweitstimmen, die der Nutzer eingegeben hat */
	private int gesamtZweitstimmen = 0;

	/** reprï¿½sentiert das Programmfenster */
	private final Programmfenster pf;

	/**
	 * Der Konstruktor legt das Layout fest und initialisiert das Fenster.
	 * 
	 * @param basiswahlen
	 *            die Liste an Basiswahlen
	 * @param pf
	 *            das Programmfenster
	 */
	public GeneratorDialog(List<Bundestagswahl> basiswahlen, Programmfenster pf) {
		this.pf = pf;
		this.wahlen = basiswahlen;
		this.ausgesuchteWahl = basiswahlen.get(0);
		this.generatorDialog = new JDialog();
		this.generatorDialog.setTitle("Wahlgenerierung");
		this.generatorDialog.setSize(435, 640);
		this.generatorDialog.setResizable(false);
		this.generatorDialog.setModal(true);
		this.generatorDialog.setLocationRelativeTo(null);
		this.generatorDialog.setLayout(null);
		initialisiere(this.wahlen);
		this.generatorDialog.setVisible(true);
	}

	/**
	 * Diese Methode ï¿½berprï¿½ft, ob die Gesamtanzahl Erst- oder Zweitstimmen
	 * grï¿½ï¿½er als 100 ist.
	 */
	private void check() {
		if (this.neueWahlNameBox.getText().equals("")) {
			this.generiere.setEnabled(false);
			return;
		}
		if (this.gesamtErststimmen <= 100 && this.gesamtZweitstimmen <= 100) {
			this.generiere.setEnabled(true);
		}
		if (this.gesamtErststimmen > 100) {
			this.gesamtErst.setForeground(Color.RED);
		}
		if (this.gesamtZweitstimmen > 100) {
			this.gesamtZweit.setForeground(Color.RED);
		}
		if (this.gesamtErststimmen <= 100) {
			this.gesamtErst.setForeground(Color.GREEN.darker());
		}
		if (this.gesamtZweitstimmen <= 100) {
			this.gesamtZweit.setForeground(Color.GREEN.darker());
		}
	}

	/**
	 * Erstellt eine Liste mit Stimmanteilen aus einem Vektor von Parteien,
	 * einem Vektor von Erst- und einem Vektor vo Zweitstimmen.
	 * 
	 * @param parteien
	 *            Parteien
	 * @param erst
	 *            Erststimmen
	 * @param zweit
	 *            Zweitstimmen
	 * @return Liste Anteile
	 */
	private LinkedList<Stimmanteile> erstelleStimmanteile(Partei[] parteien,
			int[] erst, int[] zweit) {
		final LinkedList<Stimmanteile> anteile = new LinkedList<Stimmanteile>();
		for (int i = 0; i < parteien.length; i++) {
			final Stimmanteile anteil = new Stimmanteile(parteien[i], erst[i],
					zweit[i]);
			anteile.add(anteil);
		}
		return anteile;
	}

	/**
	 * Diese Methode gibt die aktuellen Erststimmen-Sliderwerte aus in einem
	 * Vektor aus.
	 * 
	 * @return alle Werte
	 */
	private int[] erstToIntegers() {
		final int[] erststimmenAnteile = new int[this.hauptPanel
				.getComponentCount() - 1];

		for (int i = 1; i < this.hauptPanel.getComponentCount(); i++) {
			final JPanel panel = (JPanel) this.hauptPanel.getComponent(i);
			final JSlider slider = (JSlider) panel.getComponent(2);
			erststimmenAnteile[i - 1] = slider.getValue();
		}
		return erststimmenAnteile;
	}

	/**
	 * Diese Methode wird vom Konstruktor verwendet, um die statischen
	 * Komponenten des Fensters zu initialisieren.
	 */
	private void initialisiere(List<Bundestagswahl> wahlen) {
		// Basis Auswahl
		final Bundestagswahl[] wahlenArray = wahlen
				.toArray(new Bundestagswahl[wahlen.size()]);
		this.basiswahl = new JLabel("Basiswahl: ");
		this.basiswahl.setBounds(5, 5, 90, 20);
		this.basiswahlAuswahl = new JComboBox<Bundestagswahl>(wahlenArray);
		this.basiswahlAuswahl.setBounds(165, 5, 200, 20);

		this.basiswahlAuswahl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				final JComboBox<Bundestagswahl> box = (JComboBox<Bundestagswahl>) e
						.getSource();
				GeneratorDialog.this.ausgesuchteWahl = (Bundestagswahl) box
						.getSelectedItem();
				resetPane();
			}

		});

		// neuer Name TextField
		this.neueWahlNameLabel = new JLabel("Name der neuen Wahl: ");
		this.neueWahlNameLabel.setBounds(5, 40, 150, 20);
		this.neueWahlNameBox = new JTextField();
		this.neueWahlNameBox.setBounds(165, 40, 150, 20);

		this.neueWahlNameBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				check();
			}

		});

		// Stimmen Anteile und JScrollPane
		this.stimmenanteile = new JLabel(
				"Stimmenanteile:                                    Erststimmen          Zweitstimmen");
		this.stimmenanteile.setBounds(5, 70, 435, 30);
		setPane();

		// gesamt Labels
		this.gesamtErst = new JLabel("Erststimmen gesamt: 0" + "%");
		this.gesamtErst.setBounds(30, 530, 160, 20);
		this.gesamtErst.setForeground(Color.GREEN.darker());
		this.gesamtZweit = new JLabel("Zweitstimmen gesamt: 0" + "%");
		this.gesamtZweit.setBounds(220, 530, 170, 20);
		this.gesamtZweit.setForeground(Color.GREEN.darker());

		// Generiere-Button
		this.generiere = new JButton("Generiere");
		this.generiere.setBounds(160, 560, 115, 30);
		this.generiere.setEnabled(false);
		this.generiere.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ueberpruefeParteien()) {
					GeneratorDialog.this.generiere.setEnabled(false);
					final Partei[] parteien = panesToParteien();
					final int[] erst = erstToIntegers();
					final int[] zweit = zweitToIntegers();
					final LinkedList<Stimmanteile> anteile = erstelleStimmanteile(
							parteien, erst, zweit);
					final String name = GeneratorDialog.this.neueWahlNameBox
							.getText();
					final Bundestagswahl btw = Steuerung.getInstance()
							.zufaelligeWahlgenerierung(
									GeneratorDialog.this.ausgesuchteWahl,
									anteile, name);
					GeneratorDialog.this.pf.wahlHinzufuegen(btw);
					GeneratorDialog.this.generiere.setEnabled(true);
					GeneratorDialog.this.generatorDialog.dispose();
				} else {
					JOptionPane.showMessageDialog((JButton) e.getSource(),
							"Es dï¿½rfen keine Parteien doppelt vorkommen.",
							"Meldung", JOptionPane.INFORMATION_MESSAGE, null);
				}

			}

		});

		this.generatorDialog.add(this.basiswahl);
		this.generatorDialog.add(this.basiswahlAuswahl);
		this.generatorDialog.add(this.neueWahlNameLabel);
		this.generatorDialog.add(this.neueWahlNameBox);
		this.generatorDialog.add(this.stimmenanteile);
		this.generatorDialog.add(this.gesamtErst);
		this.generatorDialog.add(this.gesamtZweit);
		this.generatorDialog.add(this.generiere);
	}

	/**
	 * Diese Methode gibt die Liste der ausgewï¿½hlten Parteien in einem String
	 * Array aus.
	 * 
	 * @return Liste der Parteien
	 */
	private Partei[] panesToParteien() {
		final Partei[] parteien = new Partei[this.hauptPanel
				.getComponentCount() - 1];

		for (int i = 1; i < this.hauptPanel.getComponentCount(); i++) {
			final JPanel panel = (JPanel) this.hauptPanel.getComponent(i);
			@SuppressWarnings("unchecked")
			final JComboBox<String> pane = (JComboBox<String>) panel
					.getComponent(1);
			parteien[i - 1] = (Partei) pane.getSelectedItem();
		}
		return parteien;
	}

	/**
	 * Setzt ein neues Pane.
	 */
	private void resetPane() {
		this.remove(this.pane);
		setPane();
	}

	/**
	 * Diese Methode setzt das ScrollPane zurï¿½ck, im Falle der Benutzer will
	 * eine andere Wahl als Ausgangswahl festlegen.
	 */
	private void setPane() {
		this.hauptPanel = new JPanel();
		this.hauptPanel.setBounds(5, 5, 350, 0);
		this.hauptPanel.setLayout(null);

		// plus-Button
		final int y = this.hauptPanel.getHeight();
		final JPanel subPanel = new JPanel();
		subPanel.setBounds(10, y + 10, 390, 60);
		final JButton plus = new JButton();
		plus.setIcon(new ImageIcon("src/main/resources/gui/images/neuerTab.png"));
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				check();
				zeileHinzufuegen();
			}

		});
		plus.setBounds(5, 5, 10, 10);
		plus.setPreferredSize(new Dimension(11, 11));
		subPanel.add(plus);
		this.hauptPanel.add(subPanel);

		this.pane = new JScrollPane();
		this.pane.setBounds(5, 110, 420, 400);
		this.pane.setViewportView(this.hauptPanel);
		this.pane.setHorizontalScrollBar(null);

		this.generatorDialog.add(this.pane);
		this.pane.repaint();
	}

	/**
	 * Diese Methode wird verwendet, um ein Array aufzusummieren. Wird
	 * verwendet, um die gesamte Anzahl Erst- und Zweitstimmen aufzusummieren.
	 * 
	 * @param stimmen
	 *            Stimmenanzahl Vektor
	 * @return insgesamt
	 */
	private int stimmenGesamt(int[] stimmen) {
		int summe = 0;
		for (final int element : stimmen) {
			summe += element;
		}
		return summe;
	}

	/**
	 * Diese Methode ï¿½berprï¿½ft, ob der Benutzer fï¿½r eine Partei mehrere
	 * Anteile angegeben hat.
	 * 
	 * @return true, false
	 */
	private boolean ueberpruefeParteien() {
		final Partei[] parteien = panesToParteien();
		for (int i = 0; i < this.hauptPanel.getComponentCount() - 1; i++) {
			for (int j = i; j < this.hauptPanel.getComponentCount() - 1; j++) {
				if (i != j) {
					if (parteien[i].getName().equals(parteien[j].getName())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer eine Stimmanteilangabe
	 * löschen möchte.
	 * 
	 * @param panel
	 *            Panel das gelï¿½scht werden soll
	 */
	private void zeileEntfernen(JPanel panel) {

		if (panel == null) {
			throw new IllegalArgumentException("Parameter \"panel\" ist null!");
		}

		this.hauptPanel.setPreferredSize(new Dimension(this.hauptPanel
				.getWidth(), this.hauptPanel.getHeight() - 70));

		int index = -1;
		// finde panel
		if (this.hauptPanel != null) {
			for (int i = 0; i < this.hauptPanel.getComponentCount(); i++) {
				if (this.hauptPanel.getComponent(i) == panel) {
					index = i;
				}
			}
		}
		if (index > -1) {
			this.hauptPanel.remove(panel);
			for (int i = index; i < this.hauptPanel.getComponentCount(); i++) {
				final JPanel verschieben = (JPanel) this.hauptPanel
						.getComponent(i);
				verschieben.setBounds(verschieben.getX(),
						verschieben.getY() - 70, verschieben.getWidth(),
						verschieben.getHeight());
			}

			// plus verschieben
			final JPanel plus = (JPanel) this.hauptPanel.getComponent(0);
			plus.setBounds(plus.getX(), plus.getY() - 70, plus.getWidth(),
					plus.getHeight());
		}
		this.repaint();
	}

	/**
	 * Diese Methode wird ausgefï¿½hrt sobald der Benutzer weitere
	 * Stimmenanteile fï¿½r eine Partei angeben will.
	 */
	private void zeileHinzufuegen() {
		this.hauptPanel.setPreferredSize(new Dimension(this.hauptPanel
				.getWidth(), this.hauptPanel.getHeight() + 70));

		// plus verschieben
		final JPanel plus = (JPanel) this.hauptPanel.getComponent(0);
		final int y = plus.getY();
		plus.setBounds(plus.getX(), plus.getY() + 70, plus.getWidth(),
				plus.getHeight());

		final JPanel subPanel = new JPanel();
		subPanel.setBounds(10, y, 390, 60);

		final JButton minus = new JButton();
		minus.setIcon(new ImageIcon(
				"src/main/resources/gui/images/tabSchlieï¿½en.png"));
		minus.setBounds(5, 5, 10, 10);
		minus.setPreferredSize(new Dimension(11, 11));
		minus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final JButton button = (JButton) e.getSource();
				zeileEntfernen((JPanel) button.getParent());
			}
		});
		final List<Partei> parteien = this.ausgesuchteWahl.getParteien();
		final Partei[] parteiArray = parteien.toArray(new Partei[parteien
				.size()]);
		final JComboBox<Partei> box = new JComboBox<Partei>(parteiArray);
		box.setBounds(20, 5, 40, 20);
		box.setPreferredSize(new Dimension(140, 20));
		final JSlider erst = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
		erst.setBounds(70, 5, 50, 20);
		erst.setPreferredSize(new Dimension(100, 50));
		erst.setMajorTickSpacing(50);
		erst.setMinorTickSpacing(10);
		erst.setPaintLabels(true);
		erst.setPaintTicks(true);
		erst.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				GeneratorDialog.this.generiere.setEnabled(false);
				final int[] erstStimmen = erstToIntegers();
				GeneratorDialog.this.gesamtErststimmen = stimmenGesamt(erstStimmen);
				GeneratorDialog.this.gesamtErst.setText("Erststimmen gesamt: "
						+ GeneratorDialog.this.gesamtErststimmen + "%");
				check();
			}

		});

		final JSlider zweit = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
		zweit.setBounds(130, 5, 50, 20);
		zweit.setPreferredSize(new Dimension(100, 50));
		zweit.setMajorTickSpacing(50);
		zweit.setMinorTickSpacing(10);
		zweit.setPaintLabels(true);
		zweit.setPaintTicks(true);
		zweit.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				GeneratorDialog.this.generiere.setEnabled(false);
				final int[] zweitStimmen = zweitToIntegers();
				GeneratorDialog.this.gesamtZweitstimmen = stimmenGesamt(zweitStimmen);
				GeneratorDialog.this.gesamtZweit
						.setText("Zweitstimmen gesamt: "
								+ GeneratorDialog.this.gesamtZweitstimmen + "%");
				check();
			}

		});

		subPanel.add(minus);
		subPanel.add(box);
		subPanel.add(erst);
		subPanel.add(zweit);
		this.hauptPanel.add(subPanel);
		this.pane.setViewportView(this.hauptPanel);
		this.generatorDialog.add(this.pane);
		this.generatorDialog.repaint();
	}

	/**
	 * Diese Methode gibt die aktuellen Zweitstimmen-Sliderwerte aus in einem
	 * Vektor aus.
	 * 
	 * @return alle Werte
	 */
	private int[] zweitToIntegers() {
		final int[] zweitstimmenAnteile = new int[this.hauptPanel
				.getComponentCount() - 1];

		for (int i = 1; i < this.hauptPanel.getComponentCount(); i++) {
			final JPanel panel = (JPanel) this.hauptPanel.getComponent(i);
			final JSlider slider = (JSlider) panel.getComponent(3);
			zweitstimmenAnteile[i - 1] = slider.getValue();
		}
		return zweitstimmenAnteile;
	}
}
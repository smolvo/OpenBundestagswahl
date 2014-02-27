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
	private List<Bundestagswahl> wahlen;

	/**
	 * repräsentiert die Wahl die gerade vom Benutzer als Standardwahl
	 * festgelegt wurde
	 */
	private Bundestagswahl ausgesuchteWahl;

	private JDialog generatorDialog;

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
	private Programmfenster pf;

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
		generatorDialog.setTitle("Wahlgenerierung");
		generatorDialog.setSize(435, 640);
		generatorDialog.setResizable(false);
		generatorDialog.setModal(true);
		generatorDialog.setLocationRelativeTo(null);
		generatorDialog.setLayout(null);
		initialisiere(wahlen);
		generatorDialog.setVisible(true);
	}

	/**
	 * Diese Methode wird vom Konstruktor verwendet, um die statischen
	 * Komponenten des Fensters zu initialisieren.
	 */
	private void initialisiere(List<Bundestagswahl> wahlen) {
		// Basis Auswahl
		Bundestagswahl[] wahlenArray = wahlen.toArray(new Bundestagswahl[wahlen
				.size()]);
		this.basiswahl = new JLabel("Basiswahl: ");
		this.basiswahl.setBounds(5, 5, 90, 20);
		this.basiswahlAuswahl = new JComboBox<Bundestagswahl>(wahlenArray);
		this.basiswahlAuswahl.setBounds(165, 5, 200, 20);

		this.basiswahlAuswahl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Bundestagswahl> box = (JComboBox) e.getSource();
				ausgesuchteWahl = (Bundestagswahl) box.getSelectedItem();
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
					generiere.setEnabled(false);
					Partei[] parteien = panesToParteien();
					int[] erst = erstToIntegers();
					int[] zweit = zweitToIntegers();
					LinkedList<Stimmanteile> anteile = erstelleStimmanteile(
							parteien, erst, zweit);
					String name = neueWahlNameBox.getText();
					Bundestagswahl btw = Steuerung.getInstance()
							.zufaelligeWahlgenerierung(ausgesuchteWahl,
									anteile, name);
					pf.wahlHinzufuegen(btw);
					generiere.setEnabled(true);
					generatorDialog.dispose();
				} else {
					JOptionPane.showMessageDialog((JButton) e.getSource(),
							"Es dï¿½rfen keine Parteien doppelt vorkommen.",
							"Meldung", JOptionPane.INFORMATION_MESSAGE, null);
				}

			}

		});

		generatorDialog.add(this.basiswahl);
		generatorDialog.add(this.basiswahlAuswahl);
		generatorDialog.add(this.neueWahlNameLabel);
		generatorDialog.add(this.neueWahlNameBox);
		generatorDialog.add(this.stimmenanteile);
		generatorDialog.add(this.gesamtErst);
		generatorDialog.add(this.gesamtZweit);
		generatorDialog.add(this.generiere);
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
		int y = this.hauptPanel.getHeight();
		JPanel subPanel = new JPanel();
		subPanel.setBounds(10, y + 10, 390, 60);
		JButton plus = new JButton();
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
		hauptPanel.add(subPanel);

		this.pane = new JScrollPane();
		this.pane.setBounds(5, 110, 420, 400);
		this.pane.setViewportView(hauptPanel);
		this.pane.setHorizontalScrollBar(null);

		generatorDialog.add(pane);
		this.pane.repaint();
	}

	/**
	 * Setzt ein neues Pane.
	 */
	private void resetPane() {
		this.remove(pane);
		setPane();
	}

	/**
	 * Diese Methode wird ausgefï¿½hrt sobald der Benutzer weitere Stimmenanteile
	 * fï¿½r eine Partei angeben will.
	 */
	private void zeileHinzufuegen() {
		this.hauptPanel.setPreferredSize(new Dimension(this.hauptPanel
				.getWidth(), this.hauptPanel.getHeight() + 70));

		// plus verschieben
		JPanel plus = (JPanel) this.hauptPanel.getComponent(0);
		int y = plus.getY();
		plus.setBounds(plus.getX(), plus.getY() + 70, plus.getWidth(),
				plus.getHeight());

		JPanel subPanel = new JPanel();
		subPanel.setBounds(10, y, 390, 60);

		JButton minus = new JButton();
		minus.setIcon(new ImageIcon(
				"src/main/resources/gui/images/tabSchlieï¿½en.png"));
		minus.setBounds(5, 5, 10, 10);
		minus.setPreferredSize(new Dimension(11, 11));
		minus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				zeileEntfernen((JPanel) button.getParent());
			}
		});
		List<Partei> parteien = this.ausgesuchteWahl.getParteien();
		Partei[] parteiArray = parteien.toArray(new Partei[parteien.size()]);
		JComboBox<Partei> box = new JComboBox<Partei>(parteiArray);
		box.setBounds(20, 5, 40, 20);
		box.setPreferredSize(new Dimension(140, 20));
		JSlider erst = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
		erst.setBounds(70, 5, 50, 20);
		erst.setPreferredSize(new Dimension(100, 50));
		erst.setMajorTickSpacing(50);
		erst.setMinorTickSpacing(10);
		erst.setPaintLabels(true);
		erst.setPaintTicks(true);
		erst.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				generiere.setEnabled(false);
				int[] erstStimmen = erstToIntegers();
				gesamtErststimmen = stimmenGesamt(erstStimmen);
				gesamtErst.setText("Erststimmen gesamt: " + gesamtErststimmen
						+ "%");
				check();
			}

		});

		JSlider zweit = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
		zweit.setBounds(130, 5, 50, 20);
		zweit.setPreferredSize(new Dimension(100, 50));
		zweit.setMajorTickSpacing(50);
		zweit.setMinorTickSpacing(10);
		zweit.setPaintLabels(true);
		zweit.setPaintTicks(true);
		zweit.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				generiere.setEnabled(false);
				int[] zweitStimmen = zweitToIntegers();
				gesamtZweitstimmen = stimmenGesamt(zweitStimmen);
				gesamtZweit.setText("Zweitstimmen gesamt: "
						+ gesamtZweitstimmen + "%");
				check();
			}

		});

		subPanel.add(minus);
		subPanel.add(box);
		subPanel.add(erst);
		subPanel.add(zweit);
		this.hauptPanel.add(subPanel);
		this.pane.setViewportView(this.hauptPanel);
		generatorDialog.add(pane);
		generatorDialog.repaint();
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer eine Stimmanteilangabe
	 * löschen möchte.
	 * 
	 * @param panel
	 *            Panel das gelï¿½scht werden soll
	 */
	private void zeileEntfernen(JPanel panel) {
		this.hauptPanel.setPreferredSize(
				new Dimension(this.hauptPanel.getWidth(), this.hauptPanel.getHeight() - 70));

		int index = -1;
		// finde panel
		if (panel != null && this.hauptPanel != null) {
			for (int i = 0; i < hauptPanel.getComponentCount(); i++) {
				if (hauptPanel.getComponent(i) == panel) {
					index = i;
				}
			}
		}
		if (index > -1) {
			this.hauptPanel.remove(panel);
			for (int i = index; i < hauptPanel.getComponentCount(); i++) {
				JPanel verschieben = (JPanel) this.hauptPanel.getComponent(i);
				verschieben.setBounds(verschieben.getX(),
						verschieben.getY() - 70, verschieben.getWidth(),
						verschieben.getHeight());
			}

			// plus verschieben
			JPanel plus = (JPanel) this.hauptPanel.getComponent(0);
			plus.setBounds(plus.getX(), plus.getY() - 70, plus.getWidth(),
					plus.getHeight());
		}
		this.repaint();
	}

	/**
	 * Diese Methode ï¿½berprï¿½ft, ob der Benutzer fï¿½r eine Partei mehrere Anteile
	 * angegeben hat.
	 * 
	 * @return true, false
	 */
	private boolean ueberpruefeParteien() {
		Partei[] parteien = panesToParteien();
		for (int i = 0; i < hauptPanel.getComponentCount() - 1; i++) {
			for (int j = i; j < hauptPanel.getComponentCount() - 1; j++) {
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
	 * Diese Methode gibt die Liste der ausgewï¿½hlten Parteien in einem String
	 * Array aus.
	 * 
	 * @return Liste der Parteien
	 */
	private Partei[] panesToParteien() {
		Partei[] parteien = new Partei[hauptPanel.getComponentCount() - 1];

		for (int i = 1; i < hauptPanel.getComponentCount(); i++) {
			JPanel panel = (JPanel) hauptPanel.getComponent(i);
			JComboBox<String> pane = (JComboBox) panel.getComponent(1);
			parteien[i - 1] = (Partei) pane.getSelectedItem();
		}
		return parteien;
	}

	/**
	 * Diese Methode gibt die aktuellen Erststimmen-Sliderwerte aus in einem
	 * Vektor aus.
	 * 
	 * @return alle Werte
	 */
	private int[] erstToIntegers() {
		int[] erststimmenAnteile = new int[hauptPanel.getComponentCount() - 1];

		for (int i = 1; i < hauptPanel.getComponentCount(); i++) {
			JPanel panel = (JPanel) hauptPanel.getComponent(i);
			JSlider slider = (JSlider) panel.getComponent(2);
			erststimmenAnteile[i - 1] = slider.getValue();
		}
		return erststimmenAnteile;
	}

	/**
	 * Diese Methode gibt die aktuellen Zweitstimmen-Sliderwerte aus in einem
	 * Vektor aus.
	 * 
	 * @return alle Werte
	 */
	private int[] zweitToIntegers() {
		int[] zweitstimmenAnteile = new int[hauptPanel.getComponentCount() - 1];

		for (int i = 1; i < hauptPanel.getComponentCount(); i++) {
			JPanel panel = (JPanel) hauptPanel.getComponent(i);
			JSlider slider = (JSlider) panel.getComponent(3);
			zweitstimmenAnteile[i - 1] = slider.getValue();
		}
		return zweitstimmenAnteile;
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
		for (int i = 0; i < stimmen.length; i++) {
			summe += stimmen[i];
		}
		return summe;
	}

	/**
	 * Diese Methode ï¿½berprï¿½ft, ob die Gesamtanzahl Erst- oder Zweitstimmen
	 * grï¿½ï¿½er als 100 ist.
	 */
	private void check() {
		if (this.neueWahlNameBox.getText().equals("")) {
			generiere.setEnabled(false);
			return;
		}
		if (gesamtErststimmen <= 100 && gesamtZweitstimmen <= 100) {
			generiere.setEnabled(true);
		}
		if (gesamtErststimmen > 100) {
			gesamtErst.setForeground(Color.RED);
		}
		if (gesamtZweitstimmen > 100) {
			gesamtZweit.setForeground(Color.RED);
		}
		if (gesamtErststimmen <= 100) {
			gesamtErst.setForeground(Color.GREEN.darker());
		}
		if (gesamtZweitstimmen <= 100) {
			gesamtZweit.setForeground(Color.GREEN.darker());
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
		LinkedList<Stimmanteile> anteile = new LinkedList<Stimmanteile>();
		for (int i = 0; i < parteien.length; i++) {
			Stimmanteile anteil = new Stimmanteile(parteien[i], erst[i],
					zweit[i]);
			anteile.add(anteil);
		}
		return anteile;
	}
}
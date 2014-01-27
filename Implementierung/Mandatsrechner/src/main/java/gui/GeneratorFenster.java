package main.java.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
public class GeneratorFenster extends JFrame {

	/** repräsentiert die Liste der Basiswahlen */
	private List<Bundestagswahl> wahlen;

	/**
	 * repräsentiert die Wahl die gerade vom Benutzer als Standardwahl
	 * festgelegt wurde
	 */
	private Bundestagswahl ausgesuchteWahl;

	/** Basiswahl */
	private JLabel basiswahl;

	/** Stimmenanteile */
	private JLabel stimmenanteile;
	
	/** gesamte Erststimmen */
	private JLabel gesamtErst;
	
	/** gesamte Zweitstimmen */
	private JLabel gesamtZweit;

	/** Combobox um die Basiswahl auszusuchen */
	private JComboBox<Bundestagswahl> basiswahlAuswahl;

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
	
	/** repräsentiert das Programmfenster */
	private Programmfenster pf;

	/**
	 * Der Konstruktor legt das Layout fest und initialisiert das Fenster.
	 */
	public GeneratorFenster(List<Bundestagswahl> basiswahlen, Programmfenster pf) {
		this.pf = pf;
		this.wahlen = basiswahlen;
		this.ausgesuchteWahl = basiswahlen.get(0);
		this.setTitle("Wahlgenerierung");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(500, 50, 435, 600);
		this.setLayout(null);
		initialisiere(wahlen);
		this.setVisible(true);
	}

	/**
	 * Diese Methode wird vom Konstruktor verwendet, um die statischen
	 * Komponenten des Fensters zu initialisieren.
	 */
	private void initialisiere(List<Bundestagswahl> wahlen) {
		Bundestagswahl[] wahlenArray = wahlen.toArray(new Bundestagswahl[wahlen
				.size()]);
		this.basiswahl = new JLabel("Basiswahl: ");
		basiswahl.setBounds(5, 5, 90, 20);
		this.stimmenanteile = new JLabel(
				"Stimmenanteile:                                    Erststimmen          Zweitstimmen");
		stimmenanteile.setBounds(5, 40, 435, 30);
		this.basiswahlAuswahl = new JComboBox<Bundestagswahl>(wahlenArray);
		basiswahlAuswahl.setBounds(90, 5, 200, 20);
		basiswahlAuswahl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Bundestagswahl> box = (JComboBox) e.getSource();
				ausgesuchteWahl = (Bundestagswahl) box.getSelectedItem();
				resetPane();
			}

		});

		setPane();

		this.gesamtErst = new JLabel("Erststimmen gesamt: 0" + "%");
		this.gesamtErst.setBounds(30, 500, 160, 20);
		this.gesamtErst.setForeground(Color.GREEN);
		
		this.gesamtZweit = new JLabel("Zweitstimmen gesamt: 0" + "%");
		this.gesamtZweit.setBounds(220, 500, 170, 20);
		this.gesamtZweit.setForeground(Color.GREEN);
		

		this.generiere = new JButton("Generiere");
		this.generiere.setBounds(160, 530, 115, 30);
		this.generiere.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ueberpruefeParteien()) {
					generiere.setEnabled(false);
					Partei[] parteien = panesToParteien();
					int[] erst = erstToIntegers();
					int[] zweit = zweitToIntegers();
					LinkedList<Stimmanteile> anteile = erstelleStimmanteile(parteien, erst, zweit);
					Bundestagswahl btw = Steuerung.getInstance().zufaelligeWahlgenerierung(ausgesuchteWahl, anteile);
					WahlFenster neuesFenster = new WahlFenster(btw);
					pf.getWahlen().add(neuesFenster);
					pf.getTabs().neuerTab(neuesFenster, ausgesuchteWahl.getName());
					generiere.setEnabled(true);					
				} else {
					JOptionPane.showMessageDialog((JButton) e.getSource(),
							"Es dürfen keine Parteien doppelt vorkommen.",
							"Meldung", JOptionPane.INFORMATION_MESSAGE, null);
				}

			}

		});

		this.add(basiswahl);
		this.add(stimmenanteile);
		this.add(gesamtErst);
		this.add(gesamtZweit);
		this.add(basiswahlAuswahl);
		this.add(generiere);
	}

	/**
	 * Diese Methode setzt das ScrollPane zurück, im Falle der Benutzer will
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
				generiere.setEnabled(false);
				zeileHinzufuegen();
			}

		});
		plus.setBounds(5, 5, 10, 10);
		plus.setPreferredSize(new Dimension(11, 11));
		subPanel.add(plus);
		hauptPanel.add(subPanel);

		this.pane = new JScrollPane();
		this.pane.setBounds(5, 80, 420, 400);
		this.pane.setViewportView(hauptPanel);
		this.pane.setHorizontalScrollBar(null);

		this.add(pane);
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
	 * Diese Methode wird ausgeführt sobald der Benutzer weitere Stimmenanteile
	 * für eine Partei angeben will.
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
				"src/main/resources/gui/images/tabSchließen.png"));
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
		JSlider erst = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
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
				gesamtErst.setText("Erststimmen gesamt: " + gesamtErststimmen + "%");
				checkStimmen();
			}
			
		});
		
		JSlider zweit = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
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
				gesamtZweit.setText("Zweitstimmen gesamt: " + gesamtZweitstimmen + "%");
				checkStimmen();
			}
			
		});


		subPanel.add(minus);
		subPanel.add(box);
		subPanel.add(erst);
		subPanel.add(zweit);
		this.hauptPanel.add(subPanel);
		this.pane.setViewportView(this.hauptPanel);
		this.add(pane);
		this.repaint();
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer eine Stimmanteilangabe
	 * löschen möchte.
	 * 
	 * @param panel
	 *            Panel das gelöscht werden soll
	 */
	private void zeileEntfernen(JPanel panel) {
		this.hauptPanel.setPreferredSize(new Dimension(this.hauptPanel
				.getWidth(), this.hauptPanel.getHeight() - 70));

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
	 * Diese Methode überprüft, ob der Benutzer für eine Partei mehrere Anteile
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
	 * Diese Methode gibt die Liste der ausgewählten Parteien in einem String
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
	 * Diese Methode wird verwendet, um ein Array aufzusummieren.
	 * Wird verwendet, um die gesamte Anzahl Erst- und Zweitstimmen
	 * aufzusummieren.
	 * @param stimmen Stimmenanzahl Vektor
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
	 * Diese Methode überprüft, ob die Gesamtanzahl Erst- oder
	 * Zweitstimmen größer als 100 ist.
	 */
	private void checkStimmen() {
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
			gesamtErst.setForeground(Color.GREEN);
		}
		if (gesamtZweitstimmen <= 100) {
			gesamtZweit.setForeground(Color.GREEN);
		}
	}
	
	/**
	 * Erstellt eine Liste mit Stimmanteilen aus einem Vektor von Parteien,
	 * einem Vektor von Erst- und einem Vektor vo Zweitstimmen.
	 * @param parteien Parteien
	 * @param erst Erststimmen
	 * @param zweit Zweitstimmen
	 * @return Liste Anteile
	 */
	private LinkedList<Stimmanteile> erstelleStimmanteile(Partei[] parteien, int[] erst, int[] zweit) {
		LinkedList<Stimmanteile> anteile = new LinkedList<Stimmanteile>();
		for (int i = 0; i < parteien.length; i++) {
			Stimmanteile anteil = new Stimmanteile(parteien[i], erst[i], zweit[i]);
			anteile.add(anteil);
		}
		return anteile;
	}
}
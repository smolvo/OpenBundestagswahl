package main.java.gui;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import main.java.model.Bundestagswahl;

/**
 * Diese Klasse repräsentiert das GeneratorFenster.
 * In ihr können Erst- und Zweitstimmenanteile für vorgegebene
 * Parteien einer zuvor ausgesuchten Bundestagswahl 
 * festegelegt werden.
 * @author Anton
 *
 */
public class GeneratorFenster extends JFrame {
	
	/** repräsentiert die Liste der Basiswahlen */
	private List<Bundestagswahl> wahlen;
	
	/** Basiswahl */
	private JLabel basiswahl;
	
	/** Stimmenanteile */
	private JLabel stimmenanteile;
	
	/** Combobox um die Basiswahl auszusuchen */
	private JComboBox<Bundestagswahl> basiswahlAuswahl;
	
	/** panel im ScrollPane */
	private JPanel hauptPanel;
	
	/**	ScrollPane */
	private JScrollPane pane;
	
	/** Generiere Knopf */
	private JButton generiere;
	
	/**
	 * Der Konstruktor legt das Layout fest und initialisiert das
	 * Fenster.
	 */
	public GeneratorFenster(List<Bundestagswahl> basiswahlen) {
		this.wahlen = basiswahlen;
		this.setTitle("Wahlgenerierung");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBounds(500, 50, 335, 600);
		this.setLayout(null);
		initialisiere(wahlen);
		this.setVisible(true);
	}
	
	/**
	 * Diese Methode wird vom Konstruktor verwendet, um die
	 * statischen Komponenten des Fensters zu initialisieren.
	 */
	private void initialisiere(List<Bundestagswahl> wahlen) {
		Bundestagswahl[] wahlenArray = wahlen.toArray(new Bundestagswahl[wahlen.size()]);
		this.basiswahl = new JLabel("Basiswahl: ");
		basiswahl.setBounds(5, 5, 90, 20);
		this.stimmenanteile = new JLabel("Stimmenanteile:  Erststimmen          Zweitstimmen");
		stimmenanteile.setBounds(5, 40, 350, 30);
		this.basiswahlAuswahl = new JComboBox<Bundestagswahl>(wahlenArray);
		basiswahlAuswahl.setBounds(90, 5, 200, 20);
		basiswahlAuswahl.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				resetPane();
			}
			
		});

		resetPane();

		this.generiere = new JButton("Generiere");
		this.generiere.setBounds(100, 510,115, 30);
		this.generiere.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ueberpruefeParteien()) {
					int[] erst = erstToIntegers();
					int[] zweit = zweitToIntegers();
					for (int i = 0; i < erst.length; i++) {
						System.out.println(erst[i] + " " + zweit[i]);
					}
				} else {
					JOptionPane.showMessageDialog((JButton)e.getSource(),
							"Es dürfen keine Parteien doppelt vorkommen.", "Meldung",
							JOptionPane.INFORMATION_MESSAGE, null);
				}
				
			}
			
		});
		
		this.add(basiswahl);
		this.add(stimmenanteile);
		this.add(basiswahlAuswahl);
		this.add(generiere);
	}
	
	/**
	 * Diese Methode setzt das ScrollPane zurück, im Falle
	 * der Benutzer will eine andere Wahl als Ausgangswahl
	 * festlegen.
	 */
	private void resetPane() {
		if (pane != null) {
			this.remove(this.pane);
			this.repaint();
		}
		this.hauptPanel = new JPanel();
		this.hauptPanel.setBounds(5, 5, 300, 0);
		this.hauptPanel.setLayout(null);
		
		// plus-Button
		int y = this.hauptPanel.getHeight();
		JPanel subPanel = new JPanel();
		subPanel.setBounds(10, y + 10, 290, 33);
		JButton plus = new JButton();
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				zeileHinzufuegen();
			}
			
		});
		plus.setBounds(5, 5, 10, 10);
		plus.setPreferredSize(new Dimension(11, 11));
		subPanel.add(plus);
		hauptPanel.add(subPanel);
		
		this.pane = new JScrollPane();
		this.pane.setBounds(5, 80, 320, 400);
		this.pane.setViewportView(hauptPanel);
		this.pane.setHorizontalScrollBar(null);

		this.add(pane);
		this.pane.repaint();
	}
	
	/**
	 * Diese Methode wird ausgeführt sobald der Benutzer
	 * weitere Stimmenanteile für eine Partei angeben will.
	 */
	public void zeileHinzufuegen() {
		this.hauptPanel.setPreferredSize(new Dimension(this.hauptPanel.getWidth(), this.hauptPanel.getHeight() + 60));
		
		// plus verschieben
		JPanel plus = (JPanel) this.hauptPanel.getComponent(0);
		int y = plus.getY();
		plus.setBounds(plus.getX(), plus.getY() + 60, plus.getWidth(), plus.getHeight());
		
		JPanel subPanel = new JPanel();
		subPanel.setBounds(10, y, 290, 40);
		
		JButton minus = new JButton();
		minus.setBounds(5, 5, 10, 10);
		minus.setPreferredSize(new Dimension(11, 11));
		minus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				zeileEntfernen((JPanel) button.getParent());
			}
		});
		JComboBox<String> box = new JComboBox<String>(new String[] {"SPD", "CDU"});
		box.setBounds(20, 5, 40, 20);
		JSlider erst = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		erst.setBounds(70, 5, 50, 20);
		erst.setPreferredSize(new Dimension(100, 30));
		erst.setMajorTickSpacing(10);
		erst.setPaintTicks(true);
		JSlider zweit = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		zweit.setBounds(130, 5, 50, 20);
		zweit.setPreferredSize(new Dimension(100, 30));
		zweit.setMajorTickSpacing(10);
		zweit.setPaintTicks(true);

		subPanel.add(minus);
		subPanel.add(box);
		subPanel.add(erst);
		subPanel.add(zweit);
		this.hauptPanel.add(subPanel);
		this.pane.setViewportView(this.hauptPanel);
		this.add(pane);
	}
	
	public void zeileEntfernen(JPanel panel) {
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
				verschieben.setBounds(verschieben.getX(), verschieben.getY() - 50, verschieben.getWidth(), verschieben.getHeight());
			}

			// plus verschieben
			JPanel plus = (JPanel) this.hauptPanel.getComponent(0);
			plus.setBounds(plus.getX(), plus.getY() - 50, plus.getWidth(), plus.getHeight());
		}
	}
	
	/**
	 * Diese Methode überprüft, ob der Benutzer für eine Partei
	 * mehrere Anteile angegeben hat.
	 * @return true, false
	 */
	public boolean ueberpruefeParteien() {
		String[] parteien = scrollPaneToStrings();
		for (int i = 0; i < hauptPanel.getComponentCount() - 1; i++) {
			for (int j = i; j < hauptPanel.getComponentCount() - 1; j++) {
				if (i != j) {
					if (parteien[i].equals(parteien[j])) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Diese Methode gibt die Liste der ausgewählten Parteien in einem
	 * String Array aus.
	 * @return Liste der Parteien
	 */
	private String[] scrollPaneToStrings() {
		String[] parteien = new String[hauptPanel.getComponentCount() - 1];
		
		for (int i = 1; i < hauptPanel.getComponentCount(); i++) {
			JPanel panel = (JPanel) hauptPanel.getComponent(i);
			JComboBox<String> pane = (JComboBox) panel.getComponent(1);
			parteien[i - 1] = (String) pane.getSelectedItem();		
		}
		
		return parteien;
	}
	
	/**
	 * Diese Methode gibt die aktuellen Erststimmen-Sliderwerte aus in einem
	 * Vektor aus.
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
}

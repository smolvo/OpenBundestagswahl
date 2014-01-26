package main.java.gui.dialoge;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.java.gui.WahlFenster;

public class DialogTest {

	public static void main(String[] args) {
		JFrame wahlAussuche = new JFrame();
		wahlAussuche.setSize(400, 300);
		wahlAussuche.setLayout(new BorderLayout());
		JLabel text = new JLabel("Mit welcher Wahl vergleichen?");
		TabComboBox auswahl = new TabComboBox(null);
		JComboBox<WahlFenster> box = new JComboBox<WahlFenster>(auswahl);
		wahlAussuche.add(text, BorderLayout.NORTH);
		wahlAussuche.add(box, BorderLayout.SOUTH);
		wahlAussuche.setVisible(true);
	}
}

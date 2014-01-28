package main.java.gui.dialoge;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.java.gui.Programmfenster;
import main.java.gui.TabLeiste;
import main.java.gui.WahlFenster;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repräsentiert den Vergleichsdialog. Er wird aufgerufen wenn ein
 * Vergleich geschehen soll.
 * 
 * @author Anton
 * 
 */
public class VergleichDialog {

	/**
	 * Der Konstruktor erstellt einen Vergleichsdialog.
	 * 
	 * @param pf das Programmfenster
	 * 
	 * @throws NullPointerException
	 */
	public VergleichDialog(Programmfenster pf) {
		if (pf == null) {
			throw new NullPointerException("Geben Sie ein Programmfenster an.");
		}
		if (pf.getWahlen().size() < 2) {
			JOptionPane.showMessageDialog(pf,
					"Bitte importieren Sie mindestens zwei Wahlen.", "Meldung",
					JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			JFrame wahlAussuche = new JFrame();
			wahlAussuche.setBounds(550, 250, 200, 150);
			wahlAussuche.setResizable(false);
			wahlAussuche.setLayout(null);
			JLabel text = new JLabel("Mit welcher Wahl vergleichen?");
			text.setBounds(5, 5, 190, 40);
			JComboBox<WahlFenster> box = erstelleBox(pf.getWahlen());
			box.setBounds(15, 45, 150, 25);
			box.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<WahlFenster> cb = (JComboBox<WahlFenster>) e
							.getSource();
					WahlFenster wahlfenster = (WahlFenster) cb
							.getSelectedItem();
					Steuerung.getInstance().vergleicheWahlen(
							wahlfenster.getBtw());
				}

			});
			wahlAussuche.add(text);
			wahlAussuche.add(box);
			wahlAussuche.setVisible(true);
		}
	}

	/**
	 * Diese Methode wird von dem Konstruktor verwendet, um eine ComboBox zu
	 * erstellen.
	 * 
	 * @param list
	 *            Liste der Wahlen
	 * @return ComboBox
	 * @throw NullpointerException
	 */
	private JComboBox<WahlFenster> erstelleBox(List<WahlFenster> list) {
		if (list == null) {
			throw new NullPointerException();
		}
		WahlFenster[] namen = new WahlFenster[list.size()];
		for (int i = 0; i < namen.length; i++) {
			namen[i] = list.get(i);
		}
		JComboBox<WahlFenster> box = new JComboBox<WahlFenster>(namen);
		return box;
	}
}

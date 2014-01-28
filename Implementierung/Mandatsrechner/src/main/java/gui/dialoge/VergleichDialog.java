package main.java.gui.dialoge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
public class VergleichDialog extends JDialog {

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
			final JDialog wahlAuswahl = new JDialog();
			wahlAuswahl.setSize(200, 200);
			wahlAuswahl.setLocationRelativeTo(null);
			wahlAuswahl.setModal(true);
			wahlAuswahl.setResizable(false);
			wahlAuswahl.setLayout(null);
			JLabel text = new JLabel("Mit welcher Wahl vergleichen?");
			text.setBounds(5, 5, 190, 40);
			final JComboBox<WahlFenster> box = erstelleBox(pf.getWahlen());
			box.setBounds(10, 45, 180, 25);
			JButton vergleichBestaetigen = new JButton("Vergleichen");
			vergleichBestaetigen.setBounds(20, 90, 160, 25);
			vergleichBestaetigen.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					WahlFenster wahlfenster = (WahlFenster) box.getSelectedItem();
					Steuerung.getInstance().vergleicheWahlen(wahlfenster.getBtw());
					wahlAuswahl.dispose();
				}

			});
			wahlAuswahl.add(text);
			wahlAuswahl.add(box);
			wahlAuswahl.add(vergleichBestaetigen);
			wahlAuswahl.setVisible(true);
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

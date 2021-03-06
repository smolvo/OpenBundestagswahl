package main.java.gui.dialoge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.java.gui.Programmfenster;
import main.java.gui.WahlFenster;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse reprï¿½sentiert den Vergleichsdialog. Er wird aufgerufen wenn
 * ein Vergleich geschehen soll.
 * 
 * @author Anton
 * 
 */
public class VergleichDialog extends JDialog {

	private static final long serialVersionUID = 9047126629592533696L;

	/**
	 * Der Konstruktor erstellt einen Vergleichsdialog.
	 * 
	 * @param pf
	 *            das Programmfenster
	 * 
	 * @throws NullPointerException
	 */
	public VergleichDialog(Programmfenster pf) {
		if (pf == null) {
			throw new NullPointerException("Geben Sie ein Programmfenster an.");
		}
		if (pf.getWahlen().size() <= 1) {
			JOptionPane
					.showMessageDialog(
							pf,
							"Es müssen mindestens zwei Wahlen zum Vergleich vorhanden sein.",
							"Meldung", JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			final JDialog wahlAuswahl = new JDialog();
			wahlAuswahl.setSize(200, 200);
			wahlAuswahl.setLocationRelativeTo(null);
			wahlAuswahl.setModal(true);
			wahlAuswahl.setResizable(false);
			wahlAuswahl.setLayout(null);
			final JLabel text = new JLabel("Mit welcher Wahl vergleichen?");
			text.setBounds(5, 5, 190, 40);
			final JComboBox<WahlFenster> box = erstelleBox(pf.getWahlen());
			box.setBounds(10, 45, 180, 25);
			final JButton vergleichBestaetigen = new JButton("Vergleichen");
			vergleichBestaetigen.setBounds(20, 90, 160, 25);
			vergleichBestaetigen.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					final WahlFenster wahlfenster = (WahlFenster) box
							.getSelectedItem();
					Steuerung.getInstance().vergleicheWahlen(
							wahlfenster.getBtw());
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

		int listenIndex = 0;
		int arrayIndex = 0;
		final WahlFenster[] namen = new WahlFenster[list.size() - 1];

		while (listenIndex < list.size()) {
			if (!list.get(listenIndex).getBtw()
					.equals(Steuerung.getInstance().getBtw())) {
				namen[arrayIndex] = list.get(listenIndex);
				arrayIndex++;
			}

			listenIndex++;
		}
		final JComboBox<WahlFenster> box = new JComboBox<WahlFenster>(namen);
		return box;
	}
}

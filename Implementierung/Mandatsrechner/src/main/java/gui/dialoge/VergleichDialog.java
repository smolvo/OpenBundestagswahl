package main.java.gui.dialoge;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.java.gui.Programmfenster;
import main.java.gui.TabLeiste;
import main.java.gui.WahlFenster;
import main.java.steuerung.Steuerung;

/**
 * Diese Klasse repräsentiert den Vergleichsdialog.
 * Er wird aufgerufen wenn ein Vergleich geschehen
 * soll.
 * @author Anton
 *
 */
public class VergleichDialog {

	/** repräsentiert die geladenen Wahlen */
	TabLeiste tabs;

	/** repräsentiert das Programmfenster */
	Programmfenster pf;

	/**
	 * Der Konstruktor erstellt einen Vergleichsdialog.
	 * @param tabs
	 */
	public VergleichDialog(TabLeiste tabs) {

		
		this.tabs = tabs;
		this.pf = tabs.getPf();
		
		if (tabs.getTabCount() < 2) {
			JOptionPane.showMessageDialog(pf,
					"Bitte importieren Sie mindestens zwei Wahlen.", "Meldung",
					JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			JFrame wahlAussuche = new JFrame();
			wahlAussuche.setSize(200, 150);
			wahlAussuche.setLayout(new BorderLayout());
			JLabel text = new JLabel("Mit welcher Wahl vergleichen?");
			JComboBox<WahlFenster> box = erstelleBox(tabs);
			box.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JComboBox<WahlFenster> cb = (JComboBox<WahlFenster>)e.getSource();
					WahlFenster wahlfenster = (WahlFenster)cb.getSelectedItem();
			        Steuerung.getInstance().vergleicheWahlen(wahlfenster.getBtw());
				}
				
			});
			wahlAussuche.add(text, BorderLayout.PAGE_START);
			wahlAussuche.add(box, BorderLayout.CENTER);
			wahlAussuche.setVisible(true);
		}
	}
	
	/**
	 * Diese Methode wird von dem Konstruktor verwendet,
	 * um eine ComboBox zu erstellen.
	 * @param tabs Tableiste
	 * @return ComboBox
	 */
	private JComboBox<WahlFenster> erstelleBox(TabLeiste tabs) {
		WahlFenster[] namen = new WahlFenster[tabs.getWahlfenster().size()];
		for (int i = 0; i < namen.length; i++) {
			namen[i] = tabs.getWahlfenster().get(i);
		}
		JComboBox<WahlFenster> box = new JComboBox<WahlFenster>(namen);
		return box;
	}
}

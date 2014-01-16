package gui.dialoge;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * 
 * @author Manuel
 *
 * Diese Klasse repräsentiert die "About"- Informationen, d.h. Wissenswertes zu dem Programm
 */
public class AboutDialog extends JDialog {

	JLabel informationen;

	public AboutDialog() {

		// allgemeine Anpassungen des Fensters
		setTitle("About");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setResizable(true);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		GridLayout layout = new GridLayout(2, 1);
		layout.setHgap(3);
		layout.setVgap(3);
		setLayout(layout);
		
		
		
		// TODO
		informationen = new JLabel("Infos zum Programm.");
		this.add(informationen);
		JButton schliessen = new JButton("schliessen");
		schliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		this.add(schliessen);
		setVisible(true);
	}

}

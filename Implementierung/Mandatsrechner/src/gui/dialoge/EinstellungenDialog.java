
package gui.dialoge;


import importexport.ImportExportManager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.Bundestagswahl;

/**
 * 
 * @author Manuel
 *
 * 
 */
public class EinstellungenDialog extends JDialog {

	
	
	JPanel buttons = new JPanel();
	JButton ok = new JButton("OK");
	JButton abbrechen = new JButton("Abbrechen");
	
	JPanel bundeslaender = new JPanel();

	Bundestagswahl w;

	public EinstellungenDialog(){

		// allgemeine Anpassungen des Fensters
		setTitle("Einstellungen");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setLayout(new BorderLayout());
	
		//Wahl 2013
		ImportExportManager i = new ImportExportManager();
		File[] csvDateien = new File[2];
		csvDateien[0] = new File("files/Ergebnis2013.csv");
		csvDateien[1] = new File("files/Wahlbewerber2013.csv");
		w = i.importieren(csvDateien);
		
		
		EinstellungenListener el = new EinstellungenListener();
		ok.addActionListener(el);
		abbrechen.addActionListener(el);
		
		//Button- Leiste am unteren Fensterrand
		buttons.setLayout(new GridLayout(1,2));
		buttons.add(ok);
		buttons.add(abbrechen);
		
		

		//Auflistung der Bundesländer und ihrer Wahlberechtigtenzahlen		
		GridLayout layout = new GridLayout(w.getDeutschland().getBundeslaender().size(), 2);
		layout.setHgap(3);
		layout.setVgap(3);
		bundeslaender.setLayout(layout);
		
		for (int j = 0; j < w.getDeutschland().getBundeslaender().size(); j++) {
			bundeslaender.add(new JLabel(w.getDeutschland().getBundeslaender().get(j).getName()));
			JTextField wahlberechtigtenZahl = new JTextField(Integer.toString(w.getDeutschland().getBundeslaender().get(j).getWahlberechtigte()));
			
			bundeslaender.add(wahlberechtigtenZahl);
		}
		
		this.add(bundeslaender, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.PAGE_END);
		this.setVisible(true);
	}

	
	public class EinstellungenListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(ok)) {
				//TODO ins Model speichern
				
			} else if (e.getSource().equals(abbrechen)) {
				EinstellungenDialog.this.dispose();
			}
			
		}
		
	
		
	}


}

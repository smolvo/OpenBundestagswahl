package main.java.gui.dialoge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;




public class AboutDialog extends JDialog{
	
	private static final String ABOUT_FILE = 
		"<html>\n"
				+ "<head>\n"
				+ "<title>About</title>\n"
				+ "<link type=\"text/css\" href=\"style.css\" rel=\"stylesheet\"/>\n"
						+ "</head>\n"
						+ "<body>\n"
						+ "<div id=\"container\">\n"
								+ "<p align=\"center\">\n"
										+ "<img src=\"KIT-Logo.png\" height=\"100\"/>\n"
												+ "</p>\n"
												+ "<h1>Bundestagswahl</h1>\n"
												+ "	Dieses Programm wurde im Rahmen eines Projektes des KIT (Karlsruhe Institute of Technologie) \n"
												+ "erstellt und soll die Sitzverteilung des deutschen Bundestages gem&auml;&szlig; der \n"
												+ "gesetzlichen Bestimmungen exakt berechnen.\n"
		
+ "<h3>Entwickler</h3>\n"
+ "Philipp L&ouml;wer <a href=\"\">Mail-Adresse</a><br/>\n"
+ "Simon Sch&uuml;rg <a href=\"mailto:simon.schuerg@gmail.com\">simon.schuerg@gmail.com</a><br/>\n"
		+ "Manuel Olk <a href=\"\">Mail-Adresse</a><br/>\n"
		+ "Nick Vlassoff <a href=\"\">Mail-Adresse</a><br/>\n"
		+ "Anton Mehlmann <a href=\"\">Mail-Adresse</a><br/>\n"
		+ "Enes &Ouml;rdek <a href=\"mailto:13genesis37@gmail.com\">13genesis37@gmail.com</a>\n"
		
+ "<h3>Verwendete Bibliotheken</h3>\n"
+ "Die folgenden verwendeten Bibliotheken sind unter der <b>GNU Lesser General Public License</b> lizensiert.\n"
+ "<ul>\n"
+ "<li>JFreeChart</li>\n"
+ "<li>JCommon</li>\n"
+ "<ul>\n"
		
+ "</div>\n"
+ "</body>\n"
+ "</html>\n";
			
	
	public AboutDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run () {
				JEditorPane jEditorPane = new JEditorPane();
				jEditorPane.setEditable(false);
				
				JScrollPane scrollPane = new JScrollPane(jEditorPane);
				
				HTMLEditorKit kit = new HTMLEditorKit();
				jEditorPane.setEditorKit(kit);
				Document doc = kit.createDefaultDocument();
				jEditorPane.setDocument(doc);
				
				jEditorPane.setText(ABOUT_FILE);
				add(scrollPane, BorderLayout.CENTER);
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				setSize(new Dimension(600, 600));
				pack();
				setVisible(true);
			}
		});
	}
}
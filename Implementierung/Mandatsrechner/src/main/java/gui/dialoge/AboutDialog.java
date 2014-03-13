package main.java.gui.dialoge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileReader;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class AboutDialog {

	public AboutDialog() {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				FileReader fr = null;

				try {
					fr = new FileReader(new File(
							"src/main/resources/hilfe/about.html"));
					final JEditorPane editor = new JEditorPane();
					editor.setContentType("text/html");
					editor.read(fr, "HTML");

					final JFrame frame = new JFrame("About");
					frame.setPreferredSize(new Dimension(670, 660));
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.setLayout(new BorderLayout());
					frame.add(new JScrollPane(editor));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

				} catch (final Exception e) {
					e.printStackTrace();
				} finally {
					try {
						fr.close();
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
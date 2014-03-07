package main.java.gui.dialoge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileReader;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class LizenzDialog {

	public LizenzDialog() {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				FileReader fr = null;

				try {
					fr = new FileReader(new File(
							"src/main/resources/hilfe/lizenz.html"));
					final JEditorPane editor = new JEditorPane();
					editor.setContentType("text/html");
					editor.read(fr, "HTML");

					final JFrame frame = new JFrame("Lizenz");
					frame.setPreferredSize(new Dimension(675, 500));
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.setLayout(new BorderLayout());
					final ImageIcon icon = new ImageIcon(
							"src/main/resources/hilfe/License-GPL3.png");
					final JLabel label = new JLabel();
					label.setIcon(icon);
					frame.add(label);
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
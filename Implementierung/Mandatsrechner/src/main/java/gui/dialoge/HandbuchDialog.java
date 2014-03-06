package main.java.gui.dialoge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.io.File;
import java.io.FileReader;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


public class HandbuchDialog {
	
	public HandbuchDialog() {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				
				FileReader fr = null;
				
				try {
					fr = new FileReader(new File("src/main/resources/hilfe/tutorial2.html"));
					JEditorPane editor = new JEditorPane();
					editor.setContentType("text/html");
					editor.read(fr, "HTML");
					
					JFrame frame = new JFrame ("How to use");
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.setLayout(new BorderLayout());
					frame.add(new JScrollPane(editor));
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						fr.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
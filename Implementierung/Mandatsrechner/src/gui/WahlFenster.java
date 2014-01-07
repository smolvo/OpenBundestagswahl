package gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class WahlFenster extends JPanel{

	private String name;

	public WahlFenster(String name) {
		super();
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

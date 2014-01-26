package main.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Config {

	
	private static Config instance;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private Map<String,List> data = new HashMap<String,List>();
	private File config;
	
	
	
	public Config () {
		config = new File("src/main/resources/config.csv");
		if(config.exists()){
			try {
				this.importConfig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			throw new IllegalArgumentException("Keine Konfigurationsdatei gefunden.");
		}
	}

	public static Config getInstance() {
		if (Config.instance == null) {
			Config.instance = new Config();
		}
		return Config.instance;
	}
	
	private boolean importConfig() throws Exception {
		BufferedReader read = new BufferedReader(new FileReader(this.config));
		
		String line = null;
		String[] parts;
		int lineNumber = 0;
		String lastHead = "";
		while ((line = read.readLine()) != null) {
			lineNumber++;
			parts = line.split(Pattern.quote(";"));
			if(){
				
			}else{
				
			}
		}
		return false;

	}
	
	
}

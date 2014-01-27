package main.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Config {

	private String configPath = "src/main/resources/config.csv";
	private static Config instance;
	private final static String defaultKey = "sonstige";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Config c = Config.getInstance();
		c.debugConfig();
		try {
			c.exportConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private Map<String,List<String[]>> data = new HashMap<String,List<String[]>>();
	private File config;
	
	
	
	public Config () {
		config = new File(this.configPath);
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

	/**
	 * Gibt eine Instanz der Config-Klasse zurueck. Bitte nur fuers
	 * Debuggen verwenden! Ansonsten getConfig mit key uerbergeben.
	 * @return
	 * 			ein Objekt der Klasse Config.
	 */
	public static Config getInstance() {
		if (Config.instance == null) {
			Config.instance = new Config();
		}
		return Config.instance;
	}
	
	/**
	 * Gibt die Einstellung fuer ein bestimmtes Key zurueck.
	 * @param key
	 * 			die gesuchte Einstellung. Darf null sein, in diesem
	 * 			Falle ist wird die standardeinstellung
	 * 			uerbernommen.
	 * @return
	 * 			eine Liste an String arrays. null falls es
	 * 			keine Einstellungen zu einem bestimmten 
	 * 			key vorhanden sind.
	 */
	public static List<String[]> getConfig (String key) {
		if(key == null){
			key = Config.defaultKey;
		}
		return Config.getInstance().data.get(key);
	}
	
	/**
	 * Setzt eine Einstellung in der Config.
	 * Falls key null ist, wird der standartwert genommen,
	 * dies ist "sonstiges".
	 * @param key 
	 * 			darf null sein.
	 * @param value
	 * 			der zu speichernde wert fuer
	 * 			das key.
	 * @throws Exception
	 */
	public static void setConfig (String key, List<String[]> value) throws Exception {
		if(key == null){
			key = Config.defaultKey;
		}
		Config config = Config.getInstance();
		config.data.put(key, value);
		config.exportConfig();
	}
	
	/**
	 * Importiert die Config Datei.
	 * @throws Exception
	 */
	private void importConfig() throws Exception {
		BufferedReader read = new BufferedReader(new FileReader(this.config));
		String line;
		List<String[]> lines = new ArrayList<String[]>();
		while ((line = read.readLine()) != null) {
			lines.add(line.split(Pattern.quote("\t")));
		}
		//int headSize = 0;
		//String curHead = "";
		for (int i = 0; i < lines.size(); i++) {
			System.err.println("Line "+i);
			if(lines.get(i).length == 1 ){
				if(lines.get(i)[0]=="") {
					continue;
				}else{
					//this.data.put(this.getField(lines.get(i)[0]), null);
					//curHead = lines.get(i)[0];
					
					System.out.println(lines.get(i)[0]);
					List<String[]> dataContent = new ArrayList<String[]>();
					//int headSize = lines.get(++i).length;
					int j = i;
					j++; // Überspringe die Kopfzeile (Namen der Zellen);
					while (lines.get(j)[0]!="") {
						j++;
						System.out.println(lines.get(j)[0]+" "+lines.get(j)[1]);
						if(lines.get(j)!=null){
							dataContent.add(lines.get(j));
						}
						if (lines.size()<=(j+1) || lines.get(j+1).length == 0) {
							j++;
							break;
						}
					}
					this.data.put(lines.get(i)[0],dataContent);
					i = j;
					
				}
			}else{
				throw new IllegalArgumentException("Fehlerhafte Konfigurationsdatei.");
			}
		}

	}
	
	private void exportConfig () throws Exception {
		FileWriter f = new FileWriter(new File("src/main/resources/config2.csv"));
		BufferedWriter bf = new BufferedWriter(f);
		
		Set<String> set = this.data.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			List<String[]> dataContent = this.data.get(key);
			bf.write(key+"\n");
			String[] head = this.getHead(key);
			dataContent.add(0,head);
			for (String[] line : dataContent) {
				
				for(int j=0; j < line.length; j++){
					bf.write(line[j]);
					if(j+1 < line.length){
						bf.write("\t");
					}
				}

				bf.write("\n");
			}
			if(i.hasNext()){
				bf.write("\n");
			}
		}
		bf.flush();
		f.close();
		
	}
	
	private String[] getHead (String field) {
		String[] head;
		if (field.equals("einwohnerzahl")) {
			head = new String[]{"Name","Anzahl"};
		}else if (field.equals("farben_parteien")){
			head = new String[]{"Name","Rot","Grün","Blau"};
		}else {
			head = new String[]{"Feld","Inhalt"};
		}
		
		return head;
	}
	
	/**
	 * Debuggt die Configuration. Gibt aus was in dem "data"
	 * Attribut vorhanden ist.
	 */
	private void debugConfig(){
		Set<String> set = this.data.keySet();
		Iterator<String> i = set.iterator();
		System.out.println("++++Debugging of Config++++");
		while (i.hasNext()) {
			String key = (String) i.next();
			List<String[]> dataContent = this.data.get(key);
			System.out.println("# "+key);
			for (String[] line : dataContent) {
				for (String l : line) {
					System.out.print(l+"\t");
				}
				System.out.print("\n");
			}
		}
	}
}

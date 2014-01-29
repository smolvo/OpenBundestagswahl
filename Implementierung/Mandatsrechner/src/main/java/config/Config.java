package main.java.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Speichert Daten ueber Sessions hinweg. Falls nur einfache Daten (z.B. ein
 * String) gespeichert werden sollen, so kann die Funktion getConfigField und
 * setConfigField verwendet werden.
 * 
 */
public class Config {

	private static Config instance;

	private final String configPath = "src/main/resources/config.csv";

	private final String defaultKey = "sonstige";

	private Map<String, List<String[]>> data;

	private File config;

	/**
	 * Privater Konstruktor für das Entwurfsmuster Einzelstueck
	 */
	private Config() {

		this.config = new File(this.configPath);
		this.data = new HashMap<String, List<String[]>>();

		if (config.exists()) {
			try {
				this.importConfig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException(
					"Keine Konfigurationsdatei gefunden.");
		}
	}

	/**
	 * Gibt eine Instanz der Config-Klasse zurueck. Bitte nur fuers Debuggen
	 * verwenden! Ansonsten getConfig mit key uerbergeben.
	 * 
	 * @return ein Objekt der Klasse Config.
	 */
	public static Config getInstance() {
		if (Config.instance == null) {
			Config.instance = new Config();
		}
		return Config.instance;
	}

	/**
	 * Gibt die Einstellung fuer ein bestimmtes Key zurueck.
	 * 
	 * @param key
	 *            die gesuchte Einstellung. Darf null sein, in diesem Falle ist
	 *            wird die standardeinstellung uerbernommen.
	 * @return eine Liste an String arrays. null falls es keine Einstellungen zu
	 *         einem bestimmten key vorhanden sind.
	 */
	public List<String[]> getConfig(String key) {
		if (key == null) {
			key = this.defaultKey;
		}
		return Config.getInstance().data.get(key);
	}

	/**
	 * Gibt einzelne Konfigurationsfelder zurueck, die pro Feld nur ein Wert
	 * gespeichert haben
	 * 
	 * @param key
	 *            der Schlüssel
	 * @return das Konfiguartionsfeld
	 */
	public String getConfigField(String key) {
		List<String[]> list = this.getConfig(null);
		String value = "";
		for (String[] field : list) {
			if (field[0].equals(key)) {
				value = field[1];
				break;
			}
		}
		return value;
	}

	/**
	 * Setzt eine Einstellung in der Config. Falls key null ist, wird der
	 * standartwert genommen, dies ist "sonstiges".
	 * 
	 * @param key
	 *            darf null sein.
	 * @param value
	 *            der zu speichernde wert fuer das key.
	 * @throws Exception
	 *             wenn die Datei nicht geschrieben oder gelanden werden kann.
	 */
	public void setConfig(String key, List<String[]> value) throws Exception {
		if (key == null) {
			key = this.defaultKey;
		}
		Config config = Config.getInstance();
		config.data.put(key, value);
		config.exportConfig();
	}

	/**
	 * Setzt ein neues Configfield
	 * 
	 * @param key
	 *            die Bezeichnung
	 * @param value
	 *            der Werte des Felds
	 * @throws Exception
	 *             wenn die Datei nicht geschrieben oder geladen werden kann
	 */
	public void setConfigField(String key, String value) throws Exception {
		Config config = Config.getInstance();
		List<String[]> list = this.getConfig(null);
		boolean found = false;
		for (String[] field : list) {
			if (field[0].equals(key)) {
				found = true;
				field[1] = value;
				break;
			}
		}
		if (!found) {
			list.add(new String[] { key, value });
		}
		config.exportConfig();
	}

	/**
	 * Importiert die Config Datei.
	 * 
	 * @throws Exception
	 */
	private void importConfig() throws Exception {
		BufferedReader read = new BufferedReader(new FileReader(this.config));
		String line;
		List<String[]> lines = new ArrayList<String[]>();
		while ((line = read.readLine()) != null) {
			lines.add(line.split(Pattern.quote("\t")));
		}
		// Buffer schließen
		read.close();
		// int headSize = 0;
		// String curHead = "";
		for (int i = 0; i < lines.size(); i++) {
			// System.err.println("Line "+i);
			if (lines.get(i).length == 1) {
				if (lines.get(i)[0] == "") {
					continue;
				} else {
					// this.data.put(this.getField(lines.get(i)[0]), null);
					// curHead = lines.get(i)[0];

					// System.out.println(lines.get(i)[0]);
					List<String[]> dataContent = new ArrayList<String[]>();
					// int headSize = lines.get(++i).length;
					int j = i;
					j++; // Überspringe die Kopfzeile (Namen der Zellen);
					while (lines.get(j)[0] != "") {
						j++;
						// System.out.println(j);
						if (lines.size() <= j) {
							break;
						}
						if (lines.get(j) != null) {
							dataContent.add(lines.get(j));
						}
						if (lines.size() <= (j + 1)
								|| lines.get(j + 1).length == 0) {
							j++;
							break;
						}
					}
					this.data.put(lines.get(i)[0], dataContent);
					i = j;

				}
			} else {
				throw new IllegalArgumentException(
						"Fehlerhafte Konfigurationsdatei.");
			}
		}
	}

	/**
	 * Exportiert die Datei
	 * 
	 * @throws IOException
	 *             wenn die Datei nicht exportiert werden kann
	 */
	private void exportConfig() throws IOException {
		FileWriter f = new FileWriter(new File(this.configPath));
		BufferedWriter bf = new BufferedWriter(f);

		Set<String> set = this.data.keySet();
		Iterator<String> i = set.iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			List<String[]> dataContent = this.data.get(key);
			bf.write(key + "\n");
			String[] head = this.getHead(key);
			dataContent.add(0, head);
			for (String[] line : dataContent) {

				for (int j = 0; j < line.length; j++) {
					bf.write(line[j]);
					if (j + 1 < line.length) {
						bf.write("\t");
					}
				}

				bf.write("\n");
			}
			if (i.hasNext()) {
				bf.write("\n");
			}
		}
		bf.flush();
		f.close();

	}

	/**
	 * Gibt den Kopf der zu importierenden Dateien zurück
	 * 
	 * @param field
	 *            die Einheit mit den Werten
	 * @return den Kopf der Einheiz
	 * @throws wenn
	 *             die Einheit null ist
	 */
	private String[] getHead(String field) {
		if (field == null) {
			throw new IllegalArgumentException("String ist null.");
		}
		String[] head;
		if (field.equals("einwohnerzahl")) {
			head = new String[] { "Name", "Anzahl" };
		} else if (field.equals("farben_parteien")) {
			head = new String[] { "Name", "Rot", "Grün", "Blau" };
		} else {
			head = new String[] { "Feld", "Inhalt" };
		}

		return head;
	}

	/**
	 * Gibt die gesamte Konfiguration in lesbarer Form aus.
	 * 
	 * @return den String mit dem Ergebnis
	 */
	@Override
	public String toString() {
		Set<String> set = this.data.keySet();
		Iterator<String> i = set.iterator();
		String result = "";
		while (i.hasNext()) {
			String key = (String) i.next();
			List<String[]> dataContent = this.data.get(key);
			result += "# " + key + "\n";
			for (String[] line : dataContent) {
				for (String l : line) {
					result += l + "\t";
				}
				result += "\n";
			}
		}
		return result;
	}
}

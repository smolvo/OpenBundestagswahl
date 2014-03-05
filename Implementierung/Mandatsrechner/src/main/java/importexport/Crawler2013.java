package main.java.importexport;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import test.java.Debug;

import main.java.config.Config;
import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Erststimme;
import main.java.model.Kandidat;
import main.java.model.Landesliste;
import main.java.model.Mandat;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Der Crawler importiert Wahlen (2013) mithilfe einer Ergebnis-datei und einer
 * Bewerber-Datei.
 * 
 */
public class Crawler2013 extends Crawler {

	/**
	 * Gibt an, an ab welcher Spalte der Ergebnis-Datei die Parteien anfangen.
	 */
	private int parteiOffset = 4;

	/**
	 * Importiert csv-Dateien, in dem Format des Bundeswahlleiters im Jahre
	 * 2013.
	 * 
	 * @param csvDateien
	 *            Die eingabedateien.
	 * @throws IllegalArgumentException
	 * 			eingabeFehler
	 * @return die importierte Bundestagswahl
	 */
	public Bundestagswahl erstelleBundestagswahl(File[] csvDateien) throws IllegalArgumentException {

		Bundestagswahl imported = null;
		boolean error = false;

		/*
		 * x = Spalten Number. Enthaelt eine Liste aller Parteien. Reihenfolge
		 * relevant.
		 */
		List<String> columns = new ArrayList<String>();

		/*
		 * x = Reihennummer. y = 0:Nr. 1:Gebiet. 2:Gehoert zu.
		 */
		// String[][] rows = null;
		List<String[]> rows = new ArrayList<String[]>();

		/*
		 * x = Reihe (Gebiet). y = Spalte (Partei). z = Feld
		 * (ErstStimme/Zweitstimme).
		 */
		// int[][][] value;
		List<int[][]> values = new ArrayList<int[][]>();

		/*
		 * Enthï¿½lt eine Rohe Liste mit allen Bewerbern.
		 */
		List<String[]> bewerber = new ArrayList<String[]>();

		String bwName = "";
		BufferedReader read;
		try {
			read = new BufferedReader(new FileReader(csvDateien[0]));
			int lineNumber = -1;
			String line = null;

			String[] parts;
			int maxColumn = 0;

			while ((line = read.readLine()) != null) {
				++lineNumber;
				parts = line.split(Pattern.quote(";"));
				switch (lineNumber) {
				case 0:
					/* Enthaelt den Namen der Wahl. */
					if (parts.length == 1) {
						bwName = line;
					} else {
						error = true;
					}
					break;
				case 1:
					if (parts.length != 1) {
						error = true;
					}
					break;
				case 2:
					/* Extrahiere die Parteinamen aus den Spalten. */
					if (parts.length > 10) {

						for (int i = (parteiOffset - 1); i < parts.length
								&& !error; i += 4) {
							if (!parts[i].equals("")) {
								columns.add(parts[i]);
							} else {
								error = true;
							}
						}
						maxColumn = parts.length;
					} else {
						error = true;
					}
					break;
				case 3:
				case 4:
					break;
				default:
					if (parts.length == 1) {
						break;
					} else {
						if (parts.length <= 3 || parts[0].equals("") || parts[1].equals("")) {
							error = true;
						} else {
							rows.add(new String[] {
									parts[0],
									parts[1].substring(1, parts[1].length() - 1),
									parts[2] });
						}

						int[][] tempInt = new int[columns.size()][2];

						for (int i = (parteiOffset - 1); i < maxColumn
								&& !error; i += parteiOffset) {
							int currentColumn = (i - 3) / 4;
							try {
								if (i >= parts.length || parts[i].equals("")) {
									tempInt[currentColumn][0] = 0;
								} else {
									tempInt[currentColumn][0] = Integer
											.parseInt(parts[i]);
								}

								if ((i + 2) >= parts.length
										|| parts[i + 2].equals("")) {
									tempInt[currentColumn][1] = 0;
								} else {
									tempInt[currentColumn][1] = Integer
											.parseInt(parts[i + 2]);
								}

							} catch (NumberFormatException e) {
								error = true;
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						values.add(this.deepCopy(tempInt));
					}
				}
			}
			read.close();

			/*
			 * Jetzt kommt die Bewerber-Datei.
			 */
			if (csvDateien.length > 1) {
				try {
					bewerber = this.getBewerber(csvDateien[1]);
				} catch (IOException e) {
					error = true;
				}
			} else {
				error = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			error = true;
		} catch (Exception e) {
			e.printStackTrace();
			error = true;
		}

		if (!error) {
			imported = this.erstelleBundestagwahl(bwName, columns, rows,
					values, bewerber);
		}

		return imported;
	}

	/**
	 * Extrahiert die CSV-Datei mit den Bewerbern und gibt Rohobjekte zurück.
	 * 
	 * @param csvDatei
	 *            die Wahlbewerber CSV-Datei
	 * @return rohdaten mit bewerbern.
	 * @throws IOException
	 */
	private List<String[]> getBewerber(File csvDatei) throws IOException, IllegalArgumentException {
		List<String[]> bewerber = new ArrayList<String[]>();
		BufferedReader read = new BufferedReader(new FileReader(csvDatei));
		int lineNumber = -1;
		String line = null;
		String[] parts;
		while ((line = read.readLine()) != null) {
			++lineNumber;
			parts = line.split(Pattern.quote(";"));
			if (lineNumber == 0) {
				continue;
			} else {

				String[] names = parts[0].split(Pattern.quote(", "));

				/*
				 * if(lineNumber<100){
				 * System.out.println(names[0]+"|"+names[1]+"|"
				 * +parts[1]+"|"+parts[2]+"|"+parts[3]+" "); }
				 */
				if (parts.length == 4) {
					bewerber.add(new String[] { names[0], names[1], parts[1],
							parts[2], parts[3], "", "" });
				} else if (parts.length == 6) {
					bewerber.add(new String[] { names[0], names[1], parts[1],
							parts[2], parts[3], parts[4], parts[5] });
				} else {
					throw new IllegalArgumentException("Keine gueltige Wahlbewerber-Datei.");
				}

			}
		}
		read.close();
		return bewerber;
	}

	/**
	 * Verarbeitet die Rohdaten in ein "Bundestagswahl"-Objekt.
	 * 
	 * @param name
	 * @param columns
	 * @param rows
	 * @param values
	 * @param bewerber
	 * @return
	 */
	private Bundestagswahl erstelleBundestagwahl(String name,
			List<String> columns, List<String[]> rows, List<int[][]> values,
			List<String[]> bewerber) {
		Bundestagswahl created = null;

		String tempNummer = "0";
		boolean error = false;

		// Erzeugen der Parteien:
		// int parteiOffset = 4;
		LinkedList<Partei> parteien = new LinkedList<Partei>();
		for (int i = parteiOffset; i < columns.size(); i++) {
			Partei p = new Partei(columns.get(i), this.getParteiFarbe(columns
					.get(i)));
			parteien.add(p);
		}

		Deutschland deutschland = null;
		String nrDeutschland = "0";
		for (int i = rows.size() - 1; i >= 0; i--) {
			if (rows.get(i)[2].equals("")) {

				deutschland = new Deutschland("Deutschland");
				nrDeutschland = (rows.get(i)[0]);

			}
		}

		// Erzeuge Kandidaten
		List<Kandidat> kandidaten = new ArrayList<Kandidat>();
		/*
		 * 0 = Bewerber im Wahlkreis 1 = Bewerber auf Landesliste 2 =
		 * Landeslisten-Platz
		 */
		List<String[]> kandidatenInfo = new ArrayList<String[]>();

		for (int i = 0; i < bewerber.size(); i++) {
			String[] iterative = bewerber.get(i);
			for (int j = 0; j < parteien.size(); j++) {
				if (iterative[3].equals(parteien.get(j).getName())) {
					Kandidat mitglied = new Kandidat(iterative[0],
							iterative[1], Integer.parseInt(iterative[2]),
							Mandat.KEINMANDAT, parteien.get(j));
					kandidaten.add(mitglied);
					kandidatenInfo.add(new String[] { iterative[4],
							iterative[5], iterative[6] });

					parteien.get(j).addMitglied(mitglied);
					break;
				}

			}
		}

		// Erzeuge Bundeslaender.
		for (int i = 0; i < rows.size() && !error; i++) {
			if (rows.get(i)[2].equals(nrDeutschland + "")) {
				Bundesland b = new Bundesland(rows.get(i)[1],
						this.getEinwohnerzahl(rows.get(i)[1]));
				tempNummer = rows.get(i)[0];
				LinkedList<Partei> blParteien = new LinkedList<Partei>();
				for (int j = 0; j < parteien.size(); j++) {
					if (values.get(i)[parteiOffset + j][0] != 0
							|| values.get(i)[parteiOffset + j][1] != 0) {
						blParteien.add(parteien.get(j));
					}
				}
				b.setParteien(blParteien);
				for (int j = 0; j < rows.size() && !error; j++) {
					if (rows.get(j)[2].equals(tempNummer)) {
						Wahlkreis w = new Wahlkreis(rows.get(j)[1],
								values.get(j)[0][0]);
						w.setWahlkreisnummer(Integer.parseInt(rows.get(j)[0]));

						LinkedList<Erststimme> erststimme = new LinkedList<Erststimme>();
						LinkedList<Zweitstimme> zweitstimme = new LinkedList<Zweitstimme>();
						for (int k = 0; k < parteien.size(); k++) {
							Kandidat kandidat = null;
							for (int l = 0; l < kandidaten.size(); l++) {

								if (!kandidatenInfo.get(l)[0].equals("")
										&& kandidatenInfo.get(l)[0].equals(rows
												.get(j)[0])
										&& kandidaten.get(l).getPartei() == parteien
												.get(k)) {
									kandidat = kandidaten.get(l);
									break;
								}
							}
							if (kandidat == null) {
								// unbekannte Kandidaten haben jetzt auch
								// Parteizugehï¿½rigkeiten
								kandidat = new Kandidat("-", "-", 0,
										Mandat.KEINMANDAT, parteien.get(k));
							}
							erststimme.add(new Erststimme(
									values.get(j)[parteiOffset + k][0], w,
									kandidat));
							Zweitstimme parteiZweitstimme = new Zweitstimme(
									values.get(j)[parteiOffset + k][1], w,
									parteien.get(k));
							zweitstimme.add(parteiZweitstimme);
							parteien.get(k).addZweitstimme(parteiZweitstimme);
						}
						w.setErststimmen(erststimme);
						w.setZweitstimmen(zweitstimme);

						b.addWahlkreis(w);
					}
				}
				deutschland.addBundesland(b);
			}
		}

		if (kandidaten.size() > 0) {

			// Erzeuge eigentliche Landesliste fï¿½r jede Partei.
			LinkedList<Bundesland> bundeslaender = deutschland
					.getBundeslaender();

			for (int i = 0; i < bundeslaender.size() && !error; i++) {
				for (int j = 0; j < parteien.size() && !error; j++) {
					Landesliste landesliste = new Landesliste(parteien.get(j),
							bundeslaender.get(i));
					for (int k = 0; k < kandidaten.size() && !error; k++) {
						if (kandidaten.get(k).getPartei() == parteien.get(j)
								&& this.getBundeslandName(
										kandidatenInfo.get(k)[1]).equals(
										bundeslaender.get(i).getName())) {
							kandidaten.get(k).setLandesliste(landesliste);
							landesliste
									.addKandidat(
											Integer.parseInt(kandidatenInfo
													.get(k)[2]) - 1, kandidaten
													.get(k));
						}
					}
					bundeslaender.get(i).addLandesliste(landesliste);
					parteien.get(j).addLandesliste(landesliste);

				}
			}
		}

		if (!error) {
			created = new Bundestagswahl(name, deutschland, parteien);
		}
		return created;
	}

	@Override
	public void getCrawlerInformation() {
		
		Debug.print("Crawler 2013 - Example: http://www.bundeswahlleiter.de/de/bundestagswahlen/BTW_BUND_13/veroeffentlichungen/ergebnisse/kerg.csv", 4);
	}

	/**
	 * Kopiert ein zweidimensionales Integer-Array.
	 * 
	 * @param array
	 *            Das zu kopierende Array.
	 * @return das neue Array-Objekt.
	 */
	private int[][] deepCopy(int[][] array) {
		int xLength = array.length;
		int yLength = array[0].length;
		int[][] copy = new int[xLength][yLength];
		for (int i = 0; i < xLength; i++) {
			for (int j = 0; j < yLength; j++) {
				copy[i][j] = array[i][j];
			}
		}

		return copy;
	}

	private String getBundeslandName(String kuerzel) {

		String name = "";
		switch (kuerzel) {
		case "BW":
			name = "Baden-Württemberg";
			break;
		case "BY":
			name = "Bayern";
			break;
		case "BE":
			name = "Berlin";
			break;
		case "BB":
			name = "Brandenburg";
			break;
		case "HB":
			name = "Bremen";
			break;
		case "HH":
			name = "Hamburg";
			break;
		case "HE":
			name = "Hessen";
			break;
		case "MV":
			name = "Mecklenburg-Vorpommern";
			break;
		case "NI":
			name = "Niedersachsen";
			break;
		case "NW":
			name = "Nordrhein-Westfalen";
			break;
		case "RP":
			name = "Rheinland-Pfalz";
			break;
		case "SL":
			name = "Saarland";
			break;
		case "SN":
			name = "Sachsen";
			break;
		case "ST":
			name = "Sachsen-Anhalt";
			break;
		case "SH":
			name = "Schleswig-Holstein";
			break;
		case "TH":
			name = "Thüringen";
			break;
		default:
			throw new IllegalArgumentException("Ungültiges Bundesland-Kürzel in der Wahlbewerber-Datei. (Kürzel: " + kuerzel + ")");
		}
		return name;
	}

	/**
	 * Gibt die Einwohnerzahl aller Bundesländer zurück. Falls ein Bundesland
	 * nicht gefunden wird, wird der default-Wert zurückgegeben. Dies betrï¿½gt 0.
	 * 
	 * @param name
	 *            Name des Bundeslandes
	 * @return die anzahl der Einwohner (Quelle: Bundeswahlleiter)
	 */
	private int getEinwohnerzahl(String name) {
		int anzahl = 0;

		/*
		 * switch (name) { case "Baden-Wï¿½rttemberg": anzahl = 9482902;//9483500;
		 * break; case "Bayern": anzahl = 11353264;//11352000; break; case
		 * "Berlin": anzahl = 3025288; //3019900; break; case "Brandenburg":
		 * anzahl = 2418267; //2420700; break; case "Bremen": anzahl =
		 * 575805;//575300; break; case "Hamburg": anzahl = 1559655; //1558300;
		 * break; case "Hessen": anzahl = 5388350; //5390000; break; case
		 * "Mecklenburg-Vorpommern": anzahl = 1585032;//1587000; break; case
		 * "Niedersachsen": anzahl = 7354892; //7354900; break; case
		 * "Nordrhein-Westfalen": anzahl = 15895182; //15906800; break; case
		 * "Rheinland-Pfalz": anzahl = 3672888; //3675300; break; case
		 * "Saarland": anzahl = 919402; //919600; break; case "Sachsen": anzahl
		 * = 4005278; //4007100; break; case "Sachsen-Anhalt": anzahl = 2247673;
		 * //2252200; break; case "Schleswig-Holstein": anzahl = 2686085;
		 * //2687200; break; case "Thï¿½ringen": anzahl = 2154202; //2157700;
		 * break; default: anzahl = 0; }
		 */
		List<String[]> einwohnerzahlen = Config.getInstance().getConfig(
				"einwohnerzahl");
		boolean found = false;
		for (String[] blEinwohner : einwohnerzahlen) {
			if (blEinwohner[0].equals(name)) {
				try {
					anzahl = Integer.parseInt(blEinwohner[1]);
				} catch (Exception e) {
					e.printStackTrace();
					anzahl = 0;
				}
				found = true;
				break;
			}
		}
		if (!found) {
			anzahl = 0;
		}
		return anzahl;
	}

	/**
	 * Gibt die Farbe einer Partei zurück.
	 * 
	 * @param parteiName
	 *            Der Name der gewünschten Partei.
	 * @return Gibt ein Farben-Objekt zurück.
	 */
	private Color getParteiFarbe(String parteiName) {
		Color color = Color.GRAY;
		// String parteiname = partei.getName();
		/*
		 * switch (parteiName) { case "SPD": color = color.RED; break; case
		 * "CDU": case "CSU": color = color.BLACK; break; case "FDP": color =
		 * color.YELLOW; break; case "DIE LINKE": color = color.RED.darker();
		 * break; case "GRï¿½NE": color = color.GREEN.darker(); break; case
		 * "PIRATEN": color = color.ORANGE; break; default: color = color.GRAY;
		 * 
		 * }
		 */
		List<String[]> farben = Config.getInstance().getConfig(
				"farben_parteien");
		for (String[] farbe : farben) {
			if (farbe[0].equals(parteiName)) {
				try {
					int red = Integer.parseInt(farbe[1]);
					int green = Integer.parseInt(farbe[2]);
					int blue = Integer.parseInt(farbe[3]);
					color = new Color(red, green, blue);
				} catch (Exception e) {
					e.printStackTrace();
					color = Color.GRAY;
				}
				break;
			}
		}
		return color;

	}

	private void debugDeutschland(Deutschland deutschland) {
		System.out.println(deutschland.getName() + ":");
		List<Bundesland> bundeslaender = deutschland.getBundeslaender();
		for (int i = 0; i < 2; i++) {
			System.out.println("-> " + bundeslaender.get(i).getName());

			/*
			 * List<Landesliste> landesliste =
			 * bundeslaender.get(i).getLandesliste();
			 * System.out.println("Size: "+landesliste.size()); for(int
			 * j=0;j<landesliste.size();j++){
			 * System.out.println("--> "+landesliste
			 * .get(j).getPartei().getName()); List<Kandidat> listenkandidaten =
			 * landesliste.get(j).getListenkandidaten();
			 * System.out.println("Kandidaten: "+listenkandidaten.size());
			 * for(int k=0;k<listenkandidaten.size();k++){
			 * System.out.println("---> Name: "
			 * +listenkandidaten.get(k).getName()
			 * );//+" - Vorname:"+listenkandidaten
			 * .get(k).getVorname()+" - Geb: "
			 * +listenkandidaten.get(k).getGeburtsjahr()); }
			 * 
			 * }
			 */
			List<Wahlkreis> wahlkreise = bundeslaender.get(i).getWahlkreise();
			for (int j = 0; j < wahlkreise.size(); j++) {
				System.out.println("--> " + wahlkreise.get(j).getName() + " ("
						+ wahlkreise.get(j).getWahlkreisnummer() + ")");
				List<Zweitstimme> zweitstimmen = wahlkreise.get(j)
						.getZweitstimmenProPartei();
				List<Erststimme> erststimmen = wahlkreise.get(j)
						.getErststimmenProPartei();
				for (int k = 0; k < zweitstimmen.size(); k++) {
					System.out.println("--->"
							+ zweitstimmen.get(k).getPartei().getName()
							+ " Erststimmen: " + erststimmen.get(k).getAnzahl()
							+ " (Direktkandidat: "
							+ erststimmen.get(k).getKandidat().getName()
							+ ") - Zweitstimmen: "
							+ zweitstimmen.get(k).getAnzahl());
				}
			}
		}
	}
	
}

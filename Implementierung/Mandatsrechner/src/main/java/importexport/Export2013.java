package main.java.importexport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.java.model.Bundesland;
import main.java.model.Bundestagswahl;
import main.java.model.Deutschland;
import main.java.model.Erststimme;
import main.java.model.Gebiet;
import main.java.model.Partei;
import main.java.model.Wahlkreis;
import main.java.model.Zweitstimme;

/**
 * Exportiert eine BTW in dem selben Format, wie eine CSV-Datei der
 * Bundestagswahl 2013 des Bundeswahlleiters. Das exportierte ist kompatibel zur
 * Wahlbewerber-Datei.
 * 
 * @author 13genesis37
 * 
 */
public class Export2013 extends Export {
	/*
	 * Eine Konstante die zeigt, in welchem Spaltenblock die Parteien anfangen.
	 */
	private int parteiOffset = 4;

	@Override
	public boolean exportieren(String pfad, Bundestagswahl bw) {
		boolean success = false;
		try {
			FileWriter f = new FileWriter(new File(pfad));
			BufferedWriter bf = new BufferedWriter(f);
			bf.write(bw.getName()
					+ "\n\nNr;Gebiet;gehï¿½rt;Wahlberechtigte;;;;Wï¿½hler;;;;Ungï¿½ltige;;;;Gï¿½ltige;;;;");

			List<Partei> parteien = bw.getParteien();
			for (int i = 0; i < parteien.size(); i++) {
				bf.write(parteien.get(i).getName() + ";;;;");
			}
			bf.write("\n;;zu;");

			for (int i = 0; i < parteien.size() + parteiOffset; i++) {
				bf.write("Erststimmen;;Zweitstimmen;;");
			}
			bf.write("\n\n");

			List<Bundesland> bundeslaender = bw.getDeutschland()
					.getBundeslaender();

			int relevanteNr = 1;

			for (int i = 0; i < bundeslaender.size(); i++) {
				List<Wahlkreis> wahlkreise = bundeslaender.get(i)
						.getWahlkreise();
				for (int j = 0; j < wahlkreise.size(); j++) {
					bf.write(wahlkreise.get(j).getWahlkreisnummer() + ";\""
							+ wahlkreise.get(j).getName() + "\";" + relevanteNr
							+ ";" + wahlkreise.get(j).getWahlberechtigte()
							+ ";;;;");
					//List<Erststimme> erststimmen = wahlkreise.get(j)
							//.getErststimmenProPartei();
					//List<Zweitstimme> zweitstimmen = wahlkreise.get(j)
							//.getZweitstimmenProPartei();
					for (int k = 0; k < (parteiOffset - 1); k++) {
						bf.write(";;;;");
					}
					for (int k = 0; k < parteien.size(); k++) {
						String[] field = new String[2];
						field[0] = this.getAnzahlErststimmeProPartei(parteien.get(k), wahlkreise.get(j)) + ""; //erststimmen.get(k).getAnzahl() + "";
						field[1] = this.getAnzahlZweitstimmeProPartei(parteien.get(k), wahlkreise.get(j)) + ""; //zweitstimmen.get(k).getAnzahl() + "";

						if (field[0].equals("0")) {
							field[0] = "";
						}
						if (field[1].equals("0")) {
							field[1] = "";
						}
						bf.write(field[0] + ";;" + field[1] + ";;");
					}
					bf.write("\n");
				}

				bf.write(relevanteNr + ";\"" + bundeslaender.get(i).getName()
						+ "\";99;" + bundeslaender.get(i).getWahlberechtigte()
						+ ";;;;");
				//List<Zweitstimme> zweitstimmen = bundeslaender.get(i)
						//.getZweitstimmenProPartei();
				//List<Erststimme> erststimmen = bundeslaender.get(i)
						//.getErststimmenProPartei();
				for (int k = 0; k < (parteiOffset - 1); k++) {
					bf.write(";;;;");
				}
				for (int k = 0; k < parteien.size(); k++) {
					String[] field = new String[2];
					field[0] = this.getAnzahlErststimmeProPartei(parteien.get(k), bundeslaender.get(i)) + ""; //erststimmen.get(k).getAnzahl() + "";
					field[1] = this.getAnzahlZweitstimmeProPartei(parteien.get(k), bundeslaender.get(i)) + ""; //zweitstimmen.get(k).getAnzahl() + "";

					if (field[0].equals("0")) {
						field[0] = "";
					}
					if (field[1].equals("0")) {
						field[1] = "";
					}
					bf.write(field[0] + ";;" + field[1] + ";;");
				}

				bf.write("\n\n");
				relevanteNr++;

			}

			bf.write("99;\"Bundesgebiet\";;"
					+ bw.getDeutschland().getWahlberechtigte() + ";;;;");
			//List<Erststimme> erststimmen = bw.getDeutschland()
					//.getErststimmenProPartei();
			//List<Zweitstimme> zweitstimmen = bw.getDeutschland()
					//.getZweitstimmenProPartei();
			for (int k = 0; k < (parteiOffset - 1); k++) {
				bf.write(";;;;");
			}
			for (int k = 0; k < parteien.size(); k++) {
				String[] field = new String[2];
				field[0] = this.getAnzahlErststimmeProPartei(parteien.get(k), bw.getDeutschland()) + ""; //erststimmen.get(k).getAnzahl() + "";
				field[1] = this.getAnzahlZweitstimmeProPartei(parteien.get(k), bw.getDeutschland()) + ""; //zweitstimmen.get(k).getAnzahl() + "";

				if (field[0].equals("0")) {
					field[0] = "";
				}
				if (field[1].equals("0")) {
					field[1] = "";
				}
				bf.write(field[0] + ";;" + field[1] + ";;");
			}

			bf.flush();
			f.close();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return success;
	}
	
	
	private int getAnzahlErststimmeProPartei (Partei partei, Gebiet gebiet) {
		if(gebiet instanceof Wahlkreis){
			Wahlkreis wk = (Wahlkreis) gebiet;
			return wk.getAnzahlErststimmen(partei);
		}else if(gebiet instanceof Bundesland){
			Bundesland bl = (Bundesland) gebiet;
			int sum = 0;
			for (Wahlkreis wk : bl.getWahlkreise()) {
				sum += getAnzahlErststimmeProPartei(partei, wk);
			}
			return sum;
		}else if (gebiet instanceof Deutschland) {
			Deutschland dl = (Deutschland) gebiet;
			int sum = 0;
			for (Bundesland bl : dl.getBundeslaender()) {
				sum += getAnzahlErststimmeProPartei(partei, bl);
			}
			return sum;
		}else{
			throw new IllegalArgumentException("WTF!?");

		}
	}
	
	private int getAnzahlZweitstimmeProPartei (Partei partei, Gebiet gebiet) {
		if(gebiet instanceof Wahlkreis){
			Wahlkreis wk = (Wahlkreis) gebiet;
			return wk.getAnzahlZweitstimmen(partei);
		}else if(gebiet instanceof Bundesland){
			Bundesland bl = (Bundesland) gebiet;
			int sum = 0;
			for (Wahlkreis wk : bl.getWahlkreise()) {
				sum += getAnzahlZweitstimmeProPartei(partei, wk);
			}
			return sum;
		}else if (gebiet instanceof Deutschland) {
			Deutschland dl = (Deutschland) gebiet;
			int sum = 0;
			for (Bundesland bl : dl.getBundeslaender()) {
				sum += getAnzahlZweitstimmeProPartei(partei, bl);
			}
			return sum;
		}else{
			throw new IllegalArgumentException("WTF!?");

		}
	}
	
}

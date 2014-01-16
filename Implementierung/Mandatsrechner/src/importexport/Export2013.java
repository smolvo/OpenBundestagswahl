package importexport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Bundesland;
import model.Bundestagswahl;
import model.Erststimme;
import model.Partei;
import model.Wahlkreis;
import model.Zweitstimme;

/**
 * Exportiert eine BTW in dem selben Format, wie
 * eine CSV-Datei der Bundestagswahl 2013 des 
 * Bundeswahlleiters. Das exportierte ist 
 * kompatibel zur Wahlbewerber-Datei.
 * @author 13genesis37
 *
 */
public class Export2013 extends Export {
	/*
	 * Eine Konstante die zeigt, in welchem Spaltenblock
	 * die Parteien anfangen.
	 */
	private int parteiOffset = 4;
	@Override
	public boolean exportieren(String pfad, Bundestagswahl bw) {
		// TODO Auto-generated method stub
		try {
			FileWriter f = new FileWriter(new File(pfad));
			BufferedWriter bf = new BufferedWriter(f);
			bf.write(bw.getName() + "\n\nNr;Gebiet;gehört;Wahlberechtigte;;;;Wähler;;;;Ungültige;;;;Gültige;;;;");
			
			List<Partei> parteien = bw.getParteien();
			for (int i = 0; i < parteien.size(); i++) {
				bf.write(parteien.get(i).getName() + ";;;;");
			}
			bf.write("\n;;zu;");
			
			for (int i = 0; i < parteien.size() + parteiOffset; i++) {
				bf.write("Erststimmen;;Zweitstimmen;;");
			}
			bf.write("\n\n");
			
			List<Bundesland> bundeslaender = bw.getDeutschland().getBundeslaender();
			
			int relevanteNr = 1;
			
			for (int i = 0; i < bundeslaender.size(); i++) {
				List<Wahlkreis> wahlkreise = bundeslaender.get(i).getWahlkreise();
				for (int j = 0; j < wahlkreise.size(); j++) {
					bf.write(wahlkreise.get(j).getWahlkreisnummer() + ";\"" + wahlkreise.get(j).getName() + "\";" + relevanteNr + ";" + wahlkreise.get(j).getWahlberechtigte() + ";;;;");
					List<Erststimme> erststimmen = wahlkreise.get(j).getErststimmen();
					List<Zweitstimme> zweitstimmen = wahlkreise.get(j).getZweitstimmen();
					for (int k = 0; k < 3; k++) {
						bf.write(";;;;");
					}
					for (int k = 0; k < zweitstimmen.size(); k++) {
						String[] field = new String[2];
						field[0] = erststimmen.get(k).getAnzahl() + "";
						field[1] = zweitstimmen.get(k).getAnzahl() + "";
						
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
				
				bf.write(relevanteNr + ";\"" + bundeslaender.get(i).getName() + "\";99;" + bundeslaender.get(i).getWahlberechtigte() + ";;;;");
				List<Zweitstimme> zweitstimmen = bundeslaender.get(i).getZweitstimmen();
				List<Erststimme> erststimmen = bundeslaender.get(i).getErststimmen();
				for (int k = 0; k < 3; k++) {
					bf.write(";;;;");
				}
				for (int k = 0; k < zweitstimmen.size(); k++) {
					String[] field = new String[2];
					field[0] = erststimmen.get(k).getAnzahl() + "";
					field[1] = zweitstimmen.get(k).getAnzahl() + "";
					
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
			
			bf.write("99;\"Bundesgebiet\";;" + bw.getDeutschland().getWahlberechtigte() + ";;;;");
			List<Erststimme> erststimmen = bw.getDeutschland().getErststimmen();
			List<Zweitstimme> zweitstimmen = bw.getDeutschland().getZweitstimmen();
			for (int k = 0; k < 3; k++) {
				bf.write(";;;;");
			}
			for (int k = 0; k < zweitstimmen.size(); k++) {
				String[] field = new String[2];
				field[0] = erststimmen.get(k).getAnzahl() + "";
				field[1] = zweitstimmen.get(k).getAnzahl() + "";
				
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

}

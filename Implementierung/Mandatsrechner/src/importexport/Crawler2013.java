package importexport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.Bundestagswahl;

public class Crawler2013 extends Crawler {
	
	
	@Override
	public Bundestagswahl erstelleBundestagswahl(File csvDatei) {
		// TODO Auto-generated method stub
		Bundestagswahl imported = null;
		boolean error = false;
		
		/*
		 * x = Spalten Number.
		 * Enthaelt eine Liste aller Parteien. Reihenfolge relevant.
		 */
		List<String> columns = new ArrayList<String>();
		
		/*
		 * x = Reihennummer.
		 * y = 0:Nr. 1:Gebiet. 2:Gehoert zu.
		 */
		//String[][] rows = null;
		List<String[]> rows = new ArrayList<String[]>();
	
		/*
		 * x = Reihe (Gebiet).
		 * y = Spalte (Partei).
		 * z = Feld (ErstStimme/Zweitstimme). 
		 */
		//int[][][] value;
		List<int[][]> value = new ArrayList<int[][]>();
		
		
		String bwName = "";
		
		try{
			BufferedReader read = new BufferedReader(new FileReader(csvDatei));
			int lineNumber = -1;
			String line = null;
			
			
			String[] parts;
			while((line = read.readLine()) != null){
				++lineNumber;
				parts = line.split(Pattern.quote(";"));
				switch(lineNumber){
					case 0:
						if(parts.length==1){
							bwName = line;
						}else{
							error=true;
						}
						break;
					case 1:
						if(parts.length!=1){
							error=true;
						}
						break;
					case 2:
						if(parts.length>10){
							
							for(int i=3; i<parts.length && !error; i+=4){
								//System.out.println(i+": "+parts[i]);
								if(!parts[i].equals("")){
									columns.add(parts[i]);
								}else{
									error=true;
								}
								
							}
						}else{
							error=true;
						}
						break;
					case 3:
					case 4:
						break;
					default:
						if(parts.length==1){
							break;
						}else{
							int colNumber = 0;
							if(parts[0].equals("")|| parts[1].equals("")){
								error = true;
							}else{
								rows.add(lineNumber-5, new String[]{parts[0],parts[1],parts[2]});
							}
							
							for(int i=3; i<parts.length && !error; i+=4){
								int[][] tempInt = new int[columns.size()][2];
								int currentColumn = (i-3)/4;
								try{
									if(parts[i].equals("")){
										tempInt[currentColumn][0] = 0;
									}else{
										tempInt[currentColumn][0] = Integer.parseInt(parts[i]);
									}
									if(parts[i+2].equals("")){
										tempInt[currentColumn][1] = 0;
									}else{
										tempInt[currentColumn][1] = Integer.parseInt(parts[i+2]);
									}
								}catch(NumberFormatException e){
									error = true;
									e.printStackTrace();
								}
								value.add(lineNumber-5,tempInt);
								
							}
						}
						
				}
			}
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
			error = true;
		}catch(Exception e){
			e.printStackTrace();
			error = true;
		}
		
		if(!error){
			//Erzeuge BW-Element.
		}
		
		return imported;
	}

	@Override
	public boolean exportieren(String pfad) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCrawlerInformation() {
		// TODO Auto-generated method stub
		return "Crawler 2013 - Example: http://www.bundeswahlleiter.de/de/bundestagswahlen/BTW_BUND_13/veroeffentlichungen/ergebnisse/kerg.csv";
	}

}

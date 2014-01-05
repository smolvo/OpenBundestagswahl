package importexport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import model.Bundestagswahl;

public class Crawler2013 extends Crawler {
	
	/**
	 * TODO:
	 * tempInt.clone does not work. Deep copy?
	 */
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
		List<int[][]> values = new ArrayList<int[][]>();
		
		
		String bwName = "";
		BufferedReader read;
		try{
			read = new BufferedReader(new FileReader(csvDatei));
			int lineNumber = -1;
			String line = null;
			
			
			String[] parts;
			int maxColumn=0;
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
							maxColumn = parts.length;
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
							if(parts[0].equals("")|| parts[1].equals("")){
								error = true;
							}else{
								//System.out.println(lineNumber-5);
								rows.add(new String[]{parts[0],parts[1],parts[2]});
								//System.out.println(rows.get(0)[1]);
							}
							//System.err.println(parts.length+" "+columns.size()+" "+parts[1]);
							//int[][] tempInt = new int[columns.size()][2];
							//int[][] tempInt = new int[columns.size()][2];
							int[][] tempInt =new int[columns.size()][2];
							
							for(int i=3; i<maxColumn && !error; i+=4){
								
								
								int currentColumn = (i-3)/4;
								try{
									if(i>=parts.length || parts[i].equals("")){
										
										tempInt[currentColumn][0] = 0;
									}else{
										tempInt[currentColumn][0] = Integer.parseInt(parts[i]);
									}
									
									if((i+2)>=parts.length || parts[i+2].equals("")){
										tempInt[currentColumn][1] = 0;
									}else{
										tempInt[currentColumn][1] = Integer.parseInt(parts[i+2]);
									}
									//System.out.println(Arrays.toString(tempInt[currentColumn]));
								}catch(NumberFormatException e){
									error = true;
									e.printStackTrace();
								}catch(Exception e){
									e.printStackTrace();
								}
								
								values.add(lineNumber-5,this.deepCopy(tempInt));
							}
						}
						//System.err.println("------>"+Arrays.toString(values.get(lineNumber-5)[0]));
						/*if(lineNumber>5){
							System.exit(0);
						}*/
						
				}
			}
			
			read.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
			error = true;
		}catch(Exception e){
			e.printStackTrace();
			error = true;
		}
		
		if(!error){
			//Erzeuge BW-Element.
			for(int i=0;i<6;i++){
				String[] test1 = rows.get(i);
				System.out.println(test1[1]+":");
				for(int j=0;j<columns.size();j++){
					
					System.out.println("-> "+columns.get(j));
					
					int[][] tempInt = values.get(i);
					System.out.println(Arrays.toString(tempInt[j]));
					//System.out.println("--> Erststimmen: "+tempInt[j][0]);
					//System.out.println("--> Zweitstimmen: "+tempInt[j][1]);
				}
			}
		}else{
			System.err.println("Error.");
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
	
	private int[][] deepCopy(int[][] array){
		int xLength = array.length;
		int yLength = array[0].length;
		int[][] copy = new int[xLength][yLength];
		for(int i=0; i<xLength;i++){
			for(int j=0;j<yLength;j++){
				copy[i][j] = array[i][j];
			}
		}
		
		return copy;
	}

}

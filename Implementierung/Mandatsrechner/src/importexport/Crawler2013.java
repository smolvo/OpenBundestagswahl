package importexport;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import model.*;

public class Crawler2013 extends Crawler {
	
	private int parteiOffset = 4;
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
								//System.out.println(parts[0]);
							}
							//System.err.println(parts.length+" "+columns.size()+" "+parts[1]);
							
							int[][] tempInt=new int[columns.size()][2];
							
							for(int i=3; i<maxColumn && !error; i+=4){
								//int[][] tempInt =new int[columns.size()][2];
								
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
									
								}catch(NumberFormatException e){
									error = true;
									e.printStackTrace();
								}catch(Exception e){
									e.printStackTrace();
								}
								
								
							}
							values.add(this.deepCopy(tempInt));
							
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
			/*for(int i=12;i<15;i++){
				String[] test1 = rows.get(i);
				System.out.println(test1[1]+":");
				//System.out.println("--> Wahlberechtigte: "+values.get(i)[0][0]);
				for(int j=0;j<columns.size();j++){
					
					System.out.println("-> "+columns.get(j));
					
					int[][] tempInt = values.get(i);
					System.out.println(Arrays.toString(tempInt[j]));
					
					System.out.println("--> Erststimmen: "+tempInt[j][0]);
					//System.out.println("--> Zweitstimmen: "+tempInt[j][1]);
				}
				System.out.println("\n");
			}*/
			
			imported = this.erstelleBundestagwahl(bwName, columns, rows, values);
		}else{
			System.err.println("Error.");
		}
		
		return imported;
	}

	@Override
	public boolean exportieren(String pfad, Bundestagswahl bw) {
		// TODO Auto-generated method stub
		boolean error = false;
		try {
			FileWriter f = new FileWriter(new File(pfad));
			BufferedWriter bf = new BufferedWriter(f);
			bf.write(bw.getName()+"\n\nNr;Gebiet;gehört;Wahlberechtigte;;;;Wähler;;;;Ungültige;;;;Gültige;;;;");
			
			List<Partei> parteien = bw.getParteien();
			for(int i=0; i< parteien.size();i++){
				bf.write(parteien.get(i).getName()+";;;;");
			}
			bf.write("\n;;zu;");
			
			for(int i=0;i<parteien.size()+parteiOffset;i++){
				bf.write("Erststimmen;;Zweitstimmen;;");
			}
			bf.write("\n\n");
			
			List<Bundesland> bundeslaender = bw.getDeutschland().getBundeslaender();
			
			int relevanteNr = 1;
			int unrelevanteNr = 1;
			
			for(int i = 0; i<bundeslaender.size();i++){
				List<Wahlkreis> wahlkreise = bundeslaender.get(i).getWahlkreise();
				for(int j=0; j<wahlkreise.size();j++){
					bf.write(unrelevanteNr+";"+wahlkreise.get(j).getName()+";"+relevanteNr+";"+wahlkreise.get(j).getWahlberechtigte()+";;;;");
					//List<Erststimme> erststimmen = wahlkreise.get(j).getErststimme();
					List<Zweitstimme> zweitstimmen = wahlkreise.get(j).getZweitstimmen();
					for(int k=0;k<3;k++){
						bf.write(";;;;");
					}
					for(int k=0;k<zweitstimmen.size();k++){
						String field = zweitstimmen.get(k).getAnzahl()+"";
						if(field.equals("0")){
							field="";
						}
						bf.write(";;"+field+";;");
					}
					bf.write("\n");
					unrelevanteNr++;
				}
				
				bf.write(relevanteNr+";"+bundeslaender.get(i).getName()+";99;"+bundeslaender.get(i).getWahlberechtigte()+";;;;");
				List<Zweitstimme> zweitstimmen = bundeslaender.get(i).getZweitstimmen();
				for(int k=0;k<3;k++){
					bf.write(";;;;");
				}
				for(int k=0;k<zweitstimmen.size();k++){
					String field = zweitstimmen.get(k).getAnzahl()+"";
					if(field.equals("0")){
						field="";
					}
					bf.write(";;"+field+";;");
				}
				
				
				bf.write("\n\n");
				relevanteNr++;
				
			}
			
			bf.write("99;\"Bundesgebiet\";;"+bw.getDeutschland().getWahlberechtigte()+";;;;");
			List<Zweitstimme> zweitstimmen = bw.getDeutschland().getZweitstimmen();
			for(int k=0;k<3;k++){
				bf.write(";;;;");
			}
			for(int k=0;k<zweitstimmen.size();k++){
				String field = zweitstimmen.get(k).getAnzahl()+"";
				if(field.equals("0")){
					field="";
				}
				bf.write(";;"+field+";;");
			}
			
			bf.flush();
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error = true;
		}
		
		
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
	
	private Bundestagswahl erstelleBundestagwahl(String name, List<String> columns, List<String[]> rows, List<int[][]> values){
		Bundestagswahl created = null;
		
		String tempNummer = "0";
		boolean error = false;
		
		// Erzeugen der Parteien:
		//int parteiOffset = 4;
		LinkedList<Partei> parteien = new LinkedList<Partei>(); //new Partei[columns.size()-parteiOffset];
		for(int i=parteiOffset; i<columns.size();i++){
			// TODO: Kuerzel und Farbe?
			Partei p = new Partei(columns.get(i),"Test",Color.BLACK);
			parteien.add(p);
		}
		
		Deutschland deutschland = null;
		String nrDeutschland = "0";
		for(int i=rows.size()-1; i>=0; i--){
			if(rows.get(i)[2].equals("")){
				deutschland = new Deutschland(rows.get(i)[1],values.get(i)[0][0]);
				nrDeutschland = (rows.get(i)[0]);
				
			}
		}

		// Erzeuge Bundeslaender. TODO: Landesliste.
		for(int i=0;i<rows.size() && !error ;i++){
			//System.out.println(rows.get(i)[2]);
			if(rows.get(i)[2].equals(nrDeutschland+"")){
				// TODO: Einwohnerzahl (letzter Parameter)
				Bundesland b = new Bundesland(rows.get(i)[1],0);
				//System.out.println(rows.get(i)[1]);
				tempNummer = rows.get(i)[0];
				
				// Erzeuge Wahlkreise. TODO: Erststimme & Zweitstimme.
				for(int j=0;j<rows.size() && !error;j++){
					if(rows.get(j)[2].equals(tempNummer)){
						Wahlkreis w = new Wahlkreis(rows.get(j)[1],values.get(j)[0][0]);
						//List<Erststimme> erststimme = new ArrayList<Erststimme>();
						LinkedList<Zweitstimme> zweitstimme = new LinkedList<Zweitstimme>();
						for(int k=0;k<parteien.size();k++){
							//erststimme.add(new Erststimme(values.get(j)[parteiOffset+k][0],w,parteien.get(k)));
							zweitstimme.add(new Zweitstimme(values.get(j)[parteiOffset+k][1],parteien.get(k),w));
						}
						//w.setErststimmen(erststimme);
						w.setZweitstimmen(zweitstimme);
						
						b.addWahlkreis(w);
					}
				}
				deutschland.addBundesland(b);
			}
		}
		
		/*System.out.println(deutschland.getName()+":");
		List<Bundesland> bundeslaender = deutschland.getBundeslaender();
		for(int i=0; i<2;i++){
			System.out.println("-> "+bundeslaender.get(i).getName());
			List<Wahlkreis> wahlkreise = bundeslaender.get(i).getWahlkreise();
			for(int j=0;j<wahlkreise.size();j++){
				System.out.println("--> "+wahlkreise.get(j).getName());
				List<Zweitstimme> zweitstimmen = wahlkreise.get(j).getZweitstimmen();
				for(int k = 0; k<zweitstimmen.size();k++){
					System.out.println("--->"+zweitstimmen.get(k).getPartei().getName()+" - Zweitstimmen: "+zweitstimmen.get(k).getAnzahl());
				}
			}
		}*/
		
		if(!error){
			created = new Bundestagswahl(name,deutschland,parteien);
		}
		return created;
	}

}

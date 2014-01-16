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

/**
 * Der Crawler importiert Wahlen (2013) mithilfe einer
 * Ergebnis-datei und einer Bewerber-Datei.
 * @author 13genesis37
 *
 */
public class Crawler2013 extends Crawler {
	
	/**
	 * Gibt an, an ab welcher Spalte der Ergebnis-Datei
	 * die Parteien anfangen.
	 */
	private int parteiOffset = 4;

	/**
	 * TODO
	 */
	public Bundestagswahl erstelleBundestagswahl(File[] csvDateien) {

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
		
		/*
		 * Enthält eine Rohe Liste mit allen Bewerbern.
		 */
		List<String[]> bewerber = new ArrayList<String[]>();
		
		
		String bwName = "";
		BufferedReader read;
		try{
			read = new BufferedReader(new FileReader(csvDateien[0]));
			int lineNumber = -1;
			String line = null;
			
			
			String[] parts;
			int maxColumn=0;
			while((line = read.readLine()) != null){
				++lineNumber;
				parts = line.split(Pattern.quote(";"));
				switch(lineNumber){
					case 0:
						/*
						 * Enthält den Namen der Wahl.
						 */
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
						/*
						 * Extrahiere die Parteinamen aus den Spalten.
						 */
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
								rows.add(new String[]{parts[0],parts[1].substring(1,parts[1].length()-1),parts[2]});
							}
							
							int[][] tempInt=new int[columns.size()][2];
							
							for(int i=3; i<maxColumn && !error; i+=4){
								int currentColumn = (i - 3)/4;
								try {
									if (i>=parts.length || parts[i].equals("")) {
										
										tempInt[currentColumn][0] = 0;
									} else{
										tempInt[currentColumn][0] = Integer.parseInt(parts[i]);
									}
									
									if((i+2)>=parts.length || parts[i+2].equals("")){
										tempInt[currentColumn][1] = 0;
									}else{
										tempInt[currentColumn][1] = Integer.parseInt(parts[i+2]);
									}
									
								} catch(NumberFormatException e) {
									error = true;
									e.printStackTrace();
								} catch(Exception e) {
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
			if(csvDateien.length>1){
				
				read = new BufferedReader(new FileReader(csvDateien[1]));
				lineNumber = -1;
				line = null;
				while((line = read.readLine()) != null){
					++lineNumber;
					parts = line.split(Pattern.quote(";"));
					if(lineNumber==0){
						continue;
					}else{
						
						String[] names = parts[0].split(Pattern.quote(", "));
						/*if(lineNumber<100){
							System.out.println(names[0]+"|"+names[1]+"|"+parts[1]+"|"+parts[2]+"|"+parts[3]+" ");
						}*/
						if(parts.length==4){
							bewerber.add(new String[]{names[0],names[1],parts[1],parts[2],parts[3],"",""});
						}else{
							bewerber.add(new String[]{names[0],names[1],parts[1],parts[2],parts[3],parts[4],parts[5]});
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
			
			imported = this.erstelleBundestagwahl(bwName, columns, rows, values, bewerber);
		}else{
			System.err.println("Error.");
		}
		
		return imported;
	}

	/**
	 * Verarbeitet die Rohdaten in ein "Bundestagswahl"-Objekt.
	 * @param name
	 * @param columns
	 * @param rows
	 * @param values
	 * @param bewerber
	 * @return
	 */
	private Bundestagswahl erstelleBundestagwahl(String name, List<String> columns, List<String[]> rows, List<int[][]> values, List<String[]> bewerber){
		Bundestagswahl created = null;
		
		String tempNummer = "0";
		boolean error = false;
		
		// Erzeugen der Parteien:
		//int parteiOffset = 4;
		LinkedList<Partei> parteien = new LinkedList<Partei>(); //new Partei[columns.size()-parteiOffset];
		for(int i=parteiOffset; i<columns.size();i++){
			// TODO: Kuerzel und Farbe?
			Partei p = new Partei(columns.get(i),columns.get(i),Color.BLACK);
			parteien.add(p);
		}
		
		Deutschland deutschland = null;
		String nrDeutschland = "0";
		for(int i=rows.size()-1; i>=0; i--){
			if(rows.get(i)[2].equals("")){
				//deutschland = new Deutschland(rows.get(i)[1],values.get(i)[0][0]);
				deutschland = new Deutschland("Deutschland",values.get(i)[0][0]);
				nrDeutschland = (rows.get(i)[0]);
				
			}
		}
		
		// Erzeuge Kandidaten
		List<Kandidat> kandidaten = new ArrayList<Kandidat>();
		/*
		 * 0 = Bewerber im Wahlkreis
		 * 1 = Bewerber auf Landesliste
		 * 2 = Landeslisten-Platz
		 */
		List<String[]> kandidatenInfo = new ArrayList<String[]>();
		
		for(int i=0;i<bewerber.size();i++){
			String[] iterative = bewerber.get(i);
			for(int j=0;j<parteien.size();j++){
				//System.err.println(iterative[3]+" "+parteien.get(j).getName());
				if(iterative[3].equals(parteien.get(j).getName())){
					//System.err.println(iterative[0]+" "+iterative[1]);
					Kandidat mitglied = new Kandidat(iterative[0],iterative[1],Integer.parseInt(iterative[2]),Mandat.KEINMANDAT,parteien.get(j));
					kandidaten.add(mitglied);
					kandidatenInfo.add(new String[]{iterative[4],iterative[5],iterative[6]});
					parteien.get(j).addMitglied(mitglied);
					break;
				}
				
			}
		}
		
		// Erzeuge Bundeslaender. TODO: Landesliste.
		for(int i=0;i<rows.size() && !error ;i++){
			//System.out.println(rows.get(i)[2]);
			if(rows.get(i)[2].equals(nrDeutschland+"")){
				// TODO: Einwohnerzahl (letzter Parameter)
				Bundesland b = new Bundesland(rows.get(i)[1],this.getEinwohnerzahl(rows.get(i)[1]));
				//System.out.println(rows.get(i)[1]);
				tempNummer = rows.get(i)[0];
				
				// Erzeuge Wahlkreise. TODO: Erststimme & Zweitstimme.
				for(int j=0;j<rows.size() && !error;j++){
					if(rows.get(j)[2].equals(tempNummer)){
						Wahlkreis w = new Wahlkreis(rows.get(j)[1],values.get(j)[0][0]);
						w.setWahlkreisnummer(Integer.parseInt(rows.get(j)[0]));
						
						LinkedList<Erststimme> erststimme = new LinkedList<Erststimme>();
						LinkedList<Zweitstimme> zweitstimme = new LinkedList<Zweitstimme>();
						for(int k=0;k<parteien.size();k++){
							Kandidat kandidat = null;
							for(int l=0; l<kandidaten.size();l++){
								
								if(!kandidatenInfo.get(l)[0].equals("") && kandidatenInfo.get(l)[0].equals(rows.get(j)[0]) && kandidaten.get(l).getPartei()==parteien.get(k)){
									kandidat = kandidaten.get(l);
									break;
								}
							}
							if(kandidat==null){
								kandidat = new Kandidat("Unbekannt","Unbekannt",0,Mandat.KEINMANDAT,null);
							}
							erststimme.add(new Erststimme(values.get(j)[parteiOffset+k][0],w,kandidat));
							zweitstimme.add(new Zweitstimme(values.get(j)[parteiOffset+k][1], w, parteien.get(k)));
						}
						w.setErststimmen(erststimme);
						w.setZweitstimmen(zweitstimme);
						
						b.addWahlkreis(w);
					}
				}
				deutschland.addBundesland(b);
			}
		}
		
		if(kandidaten.size()>0){
			
			// Schritt 1: Erzeuge Kandidaten und teile ihnen eine Partei zu.
			/*List<Kandidat> kandidaten = new ArrayList<Kandidat>();
			for(int i=0;i<landesliste.size();i++){
				String[] felder = landesliste.get(i);
				for(int j=0; j<parteien.size();j++){
					if(parteien.get(j).getKuerzel()==felder[3]){
						kandidaten.add(new Kandidat(felder[0],felder[1],Integer.parseInt(felder[2]),Mandat.KEINMANDAT,parteien.get(j)));
						break;
					}
				}
			}*/
			
			// Schritt 2: Erzeuge eigentliche Landesliste für jede Partei.
			LinkedList<Bundesland> bundeslaender = deutschland.getBundeslaender();
			
			for(int i=0;i<bundeslaender.size();i++){
				for(int j=0;j<parteien.size();j++){
					Landesliste landesliste = new Landesliste(parteien.get(j),bundeslaender.get(i));
					//System.err.println(kandidaten.size());
					for(int k=0;k<kandidaten.size();k++){
						/*if(kandidaten.get(k).getPartei()==parteien.get(j)){
							System.err.println(kandidaten.get(k).getPartei().getName()+" "+this.getBundeslandName(kandidatenInfo.get(k)[1])+" "+bundeslaender.get(i).getName());
						}*/
						if(kandidaten.get(k).getPartei()==parteien.get(j) && this.getBundeslandName(kandidatenInfo.get(k)[1]).equals(bundeslaender.get(i).getName())){
							//System.err.println(Integer.parseInt(kandidatenInfo.get(k)[2])+" "+kandidaten.get(k).getName());
							landesliste.addKandidat(Integer.parseInt(kandidatenInfo.get(k)[2])-1, kandidaten.get(k));
						}
						/*String[] felder = kandidaten.get(i);
						if(parteien.get(j).getKuerzel() == felder[3]){
							Kandidat kandidat = (new Kandidat(felder[0],felder[1],Integer.parseInt(felder[2]),Mandat.KEINMANDAT,parteien.get(j)));
							l.addKandidat(Integer.parseInt(felder[6])-1, kandidat);
						}*/
					}
					/*for(int k=0;k<landesliste.getListenkandidaten().size();k++){
						System.out.println((k+1)+": "+landesliste.getListenkandidaten().get(k)+"-");
						if(landesliste.getListenkandidaten().get(k)!=null){
								System.out.println(landesliste.getListenkandidaten().get(k).getName());
						}
					}*/
					//System.exit(0);
					bundeslaender.get(i).addLandesliste(landesliste);
					parteien.get(j).addLandesliste(landesliste);
					
				}
			}
		}
		
		System.out.println(deutschland.getName()+":");
		List<Bundesland> bundeslaender = deutschland.getBundeslaender();
		for(int i=0; i<2;i++){
			System.out.println("-> "+bundeslaender.get(i).getName());
			
			/*List<Landesliste> landesliste = bundeslaender.get(i).getLandesliste();
			System.out.println("Size: "+landesliste.size());
			for(int j=0;j<landesliste.size();j++){
				System.out.println("--> "+landesliste.get(j).getPartei().getName());
				List<Kandidat> listenkandidaten = landesliste.get(j).getListenkandidaten();
				System.out.println("Kandidaten: "+listenkandidaten.size());
				for(int k=0;k<listenkandidaten.size();k++){
					System.out.println("---> Name: "+listenkandidaten.get(k).getName());//+" - Vorname:"+listenkandidaten.get(k).getVorname()+" - Geb: "+listenkandidaten.get(k).getGeburtsjahr());
				}
				
			}*/
			List<Wahlkreis> wahlkreise = bundeslaender.get(i).getWahlkreise();
			for(int j=0;j<wahlkreise.size();j++){
				System.out.println("--> "+wahlkreise.get(j).getName()+" ("+wahlkreise.get(j).getWahlkreisnummer()+")");
				List<Zweitstimme> zweitstimmen = wahlkreise.get(j).getZweitstimmen();
				List<Erststimme> erststimmen = wahlkreise.get(j).getErststimmen();
				for(int k = 0; k<zweitstimmen.size();k++){
					System.out.println("--->"+zweitstimmen.get(k).getPartei().getName()+" Erststimmen: "+erststimmen.get(k).getAnzahl()+" (Direktkandidat: "+erststimmen.get(k).getKandidat().getName()+") - Zweitstimmen: "+zweitstimmen.get(k).getAnzahl());
				}
			}
		}
		
		if(!error){
			created = new Bundestagswahl(name,deutschland,parteien);
		}
		return created;
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
			
			for(int i = 0; i<bundeslaender.size();i++){
				List<Wahlkreis> wahlkreise = bundeslaender.get(i).getWahlkreise();
				for(int j=0; j<wahlkreise.size();j++){
					bf.write(wahlkreise.get(j).getWahlkreisnummer()+";\""+wahlkreise.get(j).getName()+"\";"+relevanteNr+";"+wahlkreise.get(j).getWahlberechtigte()+";;;;");
					List<Erststimme> erststimmen = wahlkreise.get(j).getErststimmen();
					List<Zweitstimme> zweitstimmen = wahlkreise.get(j).getZweitstimmen();
					for(int k=0;k<3;k++){
						bf.write(";;;;");
					}
					for(int k=0;k<zweitstimmen.size();k++){
						String[] field = new String[2];
						field[0] = erststimmen.get(k).getAnzahl()+"";
						field[1] = zweitstimmen.get(k).getAnzahl()+"";
						
						if(field[0].equals("0")){
							field[0]="";
						}
						if(field[1].equals("0")){
							field[1]="";
						}
						bf.write(field[0]+";;"+field[1]+";;");
					}
					bf.write("\n");
				}
				
				bf.write(relevanteNr+";\""+bundeslaender.get(i).getName()+"\";99;"+bundeslaender.get(i).getWahlberechtigte()+";;;;");
				List<Zweitstimme> zweitstimmen = bundeslaender.get(i).getZweitstimmen();
				List<Erststimme> erststimmen = bundeslaender.get(i).getErststimmen();
				for(int k=0;k<3;k++){
					bf.write(";;;;");
				}
				for(int k=0;k<zweitstimmen.size();k++){
					String[] field = new String[2];
					field[0] = erststimmen.get(k).getAnzahl()+"";
					field[1] = zweitstimmen.get(k).getAnzahl()+"";
					
					if(field[0].equals("0")){
						field[0]="";
					}
					if(field[1].equals("0")){
						field[1]="";
					}
					bf.write(field[0]+";;"+field[1]+";;");
				}
				
				
				bf.write("\n\n");
				relevanteNr++;
				
			}
			
			bf.write("99;\"Bundesgebiet\";;"+bw.getDeutschland().getWahlberechtigte()+";;;;");
			List<Erststimme> erststimmen = bw.getDeutschland().getErststimmen();
			List<Zweitstimme> zweitstimmen = bw.getDeutschland().getZweitstimmen();
			for(int k=0;k<3;k++){
				bf.write(";;;;");
			}
			for(int k=0;k<zweitstimmen.size();k++){
				String[] field = new String[2];
				field[0] = erststimmen.get(k).getAnzahl()+"";
				field[1] = zweitstimmen.get(k).getAnzahl()+"";
				
				if(field[0].equals("0")){
					field[0]="";
				}
				if(field[1].equals("0")){
					field[1]="";
				}
				bf.write(field[0]+";;"+field[1]+";;");
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
	
	private String getBundeslandName(String kuerzel){
		String name = "";
		switch(kuerzel){
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
				name = "-";
		}
		return name;
	}
	
	private int getEinwohnerzahl(String name){
		int anzahl=0;
		switch(name){
			case "Baden-Württemberg":
				anzahl = 9483500;
			break;
			case "Bayern":
				anzahl=11352000;
			break;
			case "Berlin":
				anzahl=3019900;
			break;
			case "Brandenburg":
				anzahl=2420700;
			break;
			case "Bremen":
				anzahl=575300;
			break;
			case "Hamburg":
				anzahl=1558300;
			break;
			case "Hessen":
				anzahl=5390000;
			break;
			case "Mecklenburg-Vorpommern":
				anzahl=1587000;
			break;
			case "Niedersachsen":
				anzahl=7354900;
			break;
			case "Nordrhein-Westfalen":
				anzahl=15906800;
			break;
			case "Rheinland-Pfalz":
				anzahl=3675300;
			break;
			case "Saarland":
				anzahl=919600;
			break;
			case "Sachsen":
				anzahl=4007100;
			break;
			case "Sachsen-Anhalt":
				anzahl=2252200;
			break;
			case "Schleswig-Holstein":
				anzahl=2687200;
			break;
			case "Thüringen":
				anzahl=2157700;
			break;
			default:
				anzahl=0;
		}
		return anzahl;
	}
	

}

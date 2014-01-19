package mandatsrechner;

import model.*;
import java.util.LinkedList; //Später rausmachen

public class Mandatsrechner2009 extends Mandatsrechner{
	
	private static Mandatsrechner2009 instance;
	private final int minSitze = 598;
	private float zuteilungsdivisor = 0;

	private int sperrklauselAnzahl;
	private final int minDirektmandate = 3;
	
	
	
	public static Mandatsrechner2009 getInstance() {
		if(Mandatsrechner2009.instance==null){
			Mandatsrechner2009.instance = new Mandatsrechner2009();
		}
		return Mandatsrechner2009.instance;
	}
	
	public Bundestagswahl berechneAlles(Bundestagswahl bw){
		
		// Initialisierung:
		this.sperrklauselAnzahl = bw.getDeutschland().getZweitstimmeGesamt() / 20;
		
		
		//**Sitze für jedes Bundesland mithilge des zuteilungsdivisor berechnen
		zuteilungsdivisor = 0;
		boolean isCorrect = false;
		int sitzanzahl;
		zuteilungsdivisor = bw.getDeutschland().getEinwohneranzahl() / minSitze;
		while(!isCorrect){
			sitzanzahl = 0;
			for(Bundesland bl : bw.getDeutschland().getBundeslaender()){
				//TODO Nach erster Nachkommastelle
				sitzanzahl += Math.round(bl.getEinwohnerzahl()/zuteilungsdivisor);
			}
			if(sitzanzahl == minSitze){
				isCorrect = true;
			}else if(sitzanzahl < minSitze){
				zuteilungsdivisor -= 0.1;
			}else{
				//sitzanzahl > minSitze
				zuteilungsdivisor += 0.1;
			}
		}
		//**Ende
		
		if(super.debug){
			System.out.println("Zuteilungsdivisor: "+zuteilungsdivisor);
			for(Bundesland bl : bw.getDeutschland().getBundeslaender()){
				System.out.println(bl.getName()+": "+Math.round(bl.getEinwohnerzahl()/zuteilungsdivisor));
			}
		}
		
		//**Direkmandate bestimmen
		for(Bundesland bl : bw.getDeutschland().getBundeslaender()){
			for(Wahlkreis wk : bl.getWahlkreise()){
				int max = 0;
				Kandidat gewinner = null;
				for(Erststimme erst : wk.getErststimmen()){
					//TODO parallelität!
					//TODO Kandidaten mit gleicher Erststimmenanzahl
					
					if(max < erst.getAnzahl()){
						//Kandidaten Mandat zuweisen und als Wahlkreissieger in den Wahlkreis eintragen
						gewinner = erst.getKandidat();
						max = erst.getAnzahl();
					}
				}
				gewinner.setMandat(Mandat.DIREKMANDAT);
				wk.setWahlkreisSieger(gewinner);
			}
		}	
		//**Ende
		//**relevanten Parteien bestimmen
			/*
			 * Hier findet die Überprüfung der Sperrklausel, doch wo sind alle Parteien?
			 * Ich bräuchte eine Liste von allen Parteien die überprüft werden müssen
			 * die die Bedingungen erfüllen (Sperrklausel) kommen in den Bundestag (Flag setzen)
			 * Als platzhalter erstelle ich vorerst eine Liste alleParteien
			 */	
		//Platzhalter 
		LinkedList<Partei> alleParteien = bw.getParteien(); // Alle Parteien der Bundestagswahl
		//relevante Parteien
		LinkedList<Partei> relevanteParteien = new LinkedList<Partei>();
			
		for(Partei part : alleParteien){
				/*
				 * Enes:
				 * Brauchen wir getSperrklauselAnzahl wirklich? Du hast da die 5% Klausel hardgecoded, es ist
				 * aber nicht in jeder Wahl 5%, es kann sich bei der nächsten Wahl z.B. ändern. Ich würde daher diese
				 * Variable in "Deutschland" entfernen und sie nur in dieser Klasse behalten.
				 * 
				 * Bei der nachfolgenden Abfrage ist soviel ich sehe etwas fehlerhaft
				 */
				/*if(part.getZweitstimmeGesamt() <= bw.getDeutschland().getSperrklauselAnzahl() || part.getAnzahlDirektmandate() <= 3){ //TODO 3 als Konstante setzen
					//Sperrklausel erfüllt
					part.setImBundestag(true);
					
				}else if(part.getAnzahlDirektmandate() >= 1 ){ // TODO 1 als Konstante setzen? Enes: Man brauch kein else-if
					
					//Partei verliert Zweitstimmen aber die Direktmandate kommen noch in den Bundestag
					part.setImBundestag(false);
				}*/
				
			if(part.getZweitstimmeGesamt() >= this.sperrklauselAnzahl || part.getAnzahlDirektmandate() >= this.minDirektmandate) {
				// Partei im Bundestag falls Anforderungen erfüllt sind.
				part.setImBundestag(true);
				//Partei in die Liste hinzufügen
				if(!relevanteParteien.contains(part)){
					relevanteParteien.add(part);
				}
			} else {
				// Ansonsten ist die Partei nicht im Bundestag.
				part.setImBundestag(false);
			}
					
		}
			
		if(this.debug){
			System.out.println("\nParteien im Bundestag:");
			for(Partei part : bw.getParteien()){
				System.out.println(part.getName()+": "+((part.isImBundestag())?"Ja":"Nein"));
			}
		}
		
		float landesdivisor=0;
		for(Bundesland bl : bw.getDeutschland().getBundeslaender()){
			
			int sitzeBundesland = Math.round(bl.getEinwohnerzahl()/zuteilungsdivisor);
			landesdivisor = bl.getZweitstimmeGesamt() / sitzeBundesland;
			isCorrect = false;
			//System.err.println(sitzeBundesland+" "+landesdivisor);
			while(!isCorrect){
				int sitzePartei = 0;
				
				for(Partei part : relevanteParteien){
					//TODO Nach erster Nachkommastelle
					sitzePartei += Math.round(bl.getZweitstimmenAnzahl(part)/landesdivisor);
				}
				//System.out.println(sitzePartei + " " + sitzeBundesland);
				if(sitzePartei == sitzeBundesland){
					isCorrect = true;
				}else if(sitzePartei < sitzeBundesland){
					landesdivisor -= 0.5;
				}else{
					//sitzanzahl > sitzeBundesland
					landesdivisor += 0.5;
				}
			}
			
			for(Partei part : relevanteParteien){
				int direktmandate = part.getAnzahlMandate(Mandat.DIREKMANDAT, bl);
				int mindestSitzanzahl = 
				part.addMindestsitzanzahl(bl, );

			}
			if(this.debug){
				System.out.println("\nLandesdivisor "+bl.getName()+": "+landesdivisor);
				for(Partei part : relevanteParteien){
					System.out.println("Sitze "+part.getName()+": "+Math.round(bl.getZweitstimmenAnzahl(part)/landesdivisor));
				}
				
			}
		}
		
		
		
		
		
		return bw;
	}
	
	@Override
	public Bundestagswahl berechne(Bundestagswahl bw) {
		// TODO Sitzanzahl als konstante setzen
		
		float bundestagsdivisor = bw.getDeutschland().getEinwohneranzahl() / 598;
		
		for(Bundesland bl : bw.getDeutschland().getBundeslaender()){
			// TODO Divisor muss so angepasst werden damit die Anzahl der Sitze stimmen
			float sitzeBundesland = bl.getEinwohnerzahl() / bundestagsdivisor;
			berechne(bl, (int) sitzeBundesland);
		}
		return null;
	}

	@Override
	protected Bundesland berechne(Bundesland bl, int sitzeBundesland) {
		// TODO Auto-generated method stub
		
		int zweitstimmenanzahl = 0;
		for(int i = 0; i < bl.getZweitstimmen().size(); i++){
			zweitstimmenanzahl += bl.getZweitstimmen().get(i).getAnzahl();
		}
		//Mindestsitzanzahl in landesliste abspeichern
		//Landesdivisor muss immer angepasst werden
		float landesdivisor =  zweitstimmenanzahl / sitzeBundesland;
		
		for(Wahlkreis wk : bl.getWahlkreise()){
			berechne(wk);
		}
		
		// TODO Mehr Direktmandaten als mindesitze?
		
		// TODO Sitze in landesliste setzen
		
	
		
		// TODO Restliche Sitze mit Mandate füllen
		
		return bl;
	}

	@Override
	protected Wahlkreis berechne(Wahlkreis wk) {
		// TODO Auto-generated method stub
		if(wk == null){
			throw new IllegalArgumentException("Wahlkreis ist leer");
		}
		int max = 0;
		Kandidat gewinner = null;
		for(Erststimme erst : wk.getErststimmen()){
			//TODO parallelität!
			//TODO Kandidaten mit gleicher Erststimmenanzahl
			
			if(max < erst.getAnzahl()){
				//Kandidaten Mandat zuweisen und als Wahlkreissieger in den Wahlkreis eintragen
				gewinner = erst.getKandidat();
				max = erst.getAnzahl();
			}
		}
		gewinner.setMandat(Mandat.DIREKMANDAT);
		wk.setWahlkreisSieger(gewinner);
		//TODO Eintrag im Bericht für den Direktmandat setzen
		return wk;
	}

	@Override
	protected void erstelleBericht(String zeile) {
		// TODO Auto-generated method stub
		
	}

}

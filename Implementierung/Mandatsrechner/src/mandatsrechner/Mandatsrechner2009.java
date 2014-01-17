package mandatsrechner;

import model.*;
import java.util.LinkedList; //Später rausmachen

public class Mandatsrechner2009 extends Mandatsrechner{

	private final int minSitze = 598;
	private float zuteilungsdivisor = 0;
	@Override
	public Mandatsrechner getInstance() {
		if(super.instance==null){
			super.instance = new Mandatsrechner2009();
		}
		return super.instance;
	}

	public Bundestagswahl berechneAlles(Bundestagswahl bw){
 
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
			LinkedList<Partei> alleParteien = new LinkedList<Partei>();
		//relevante Parteien
			LinkedList<Partei> relevanteParteien = new LinkedList<Partei>();
			
			for(Partei part : alleParteien){
				if(part.getZweitstimmeGesamt() <= bw.getDeutschland().getSperrklauselAnzahl() || part.getAnzahlDirektmandate() <= 3){ //TODO 3 als Konstante setzen
					//Sperrklausel erfüllt
					part.setImBundestag(true);
					
				}else if(part.getAnzahlDirektmandate() >= 1 ){ // TODO 1 als Konstante setzen?
					//Partei verliert Zweitstimmen aber die Direktmandate kommen noch in den Bundestag
					part.setImBundestag(false);
				}
			}
			//Nun wurden die Parteien bestimmt, die Berechnung wichtig sind
		//**Ende
		//**
		
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

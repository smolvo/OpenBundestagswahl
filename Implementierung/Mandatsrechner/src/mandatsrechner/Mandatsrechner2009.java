package mandatsrechner;

import model.*;

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
		
		for(Zweitstimme zweit: bl.getZweitstimmen()){
			
		}
		
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

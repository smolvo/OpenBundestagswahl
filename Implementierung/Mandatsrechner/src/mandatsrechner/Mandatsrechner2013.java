package mandatsrechner;

import java.util.List;

import model.Bundesland;
import model.Bundestagswahl;
import model.Mandat;
import model.Wahlkreis;
import model.Partei;

public class Mandatsrechner2013 extends Mandatsrechner {

	private static Mandatsrechner2013 instance;
	private Mandatsrechner2009 rechner2009;
	
	public Mandatsrechner2013(){
		this.rechner2009 = new Mandatsrechner2009();
	}
	
	public static Mandatsrechner2013 getInstance() {
		if(Mandatsrechner2013.instance==null){
			Mandatsrechner2013.instance = new Mandatsrechner2013();
		}
		return Mandatsrechner2013.instance;
	}

	public Bundestagswahl berechneAlles(Bundestagswahl bw){
		bw = rechner2009.berechneAlles(bw);
		float parteidivisor = 0;
		boolean isCorrect = false;
		
		List<Partei> relevanteParteien = rechner2009.getRelevanteParteien();
		
		for (Partei partei : relevanteParteien) {
			if (parteidivisor == 0) {
				parteidivisor = partei.getZweitstimmeGesamt() / partei.getAnzahlMandate();
			} else {
				parteidivisor = Math.min(parteidivisor,  partei.getZweitstimmeGesamt() / partei.getAnzahlMandate());
			}
		}
		
		if (rechner2009.debug) {
			System.out.println("\nAlt Parteidivisor: " + parteidivisor);
			for (Partei partei : relevanteParteien) {
				System.out.println(partei.getName() + ": " + ((int) (partei.getZweitstimmeGesamt() / parteidivisor)));
			}
			
		}
		
		while (!isCorrect) {
			isCorrect = true;
			for (Partei partei : relevanteParteien) {
				int mindestSitze = partei.getMindestsitzAnzahl();
				if (Math.floor(partei.getZweitstimmeGesamt() / parteidivisor) < mindestSitze) {
					isCorrect = false;
					break;
				}
			}
			if (!isCorrect) {
				parteidivisor += 1;
			}
		}
		
	
		
		if (rechner2009.debug) {
			System.out.println("\nNeu Parteidivisor: " + parteidivisor);
			for (Partei partei : relevanteParteien) {
				System.out.println(partei.getName() + ": Direktmandate-"+partei.getAnzahlMandate(Mandat.DIREKMANDAT)+" Sitze-" + ((int) (partei.getZweitstimmeGesamt() / parteidivisor)));
			}
		}
		return bw;
		
	}
	
	@Override
	public Bundestagswahl berechne(Bundestagswahl bw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Bundesland berechne(Bundesland bl, int sitzeBundesland) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Wahlkreis berechne(Wahlkreis wk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void erstelleBericht(String zeile) {
		// TODO Auto-generated method stub
		
	}

}

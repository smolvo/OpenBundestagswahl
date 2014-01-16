package mandatsrechner;

import model.Bundesland;
import model.Bundestagswahl;
import model.Wahlkreis;

public class Mandatsrechner2013 extends Mandatsrechner {

	@Override
	public Mandatsrechner getInstance() {
		if(super.instance==null){
			super.instance = new Mandatsrechner2013();
		}
		return super.instance;
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

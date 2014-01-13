package mandatsrechner;

import model.Bundesland;
import model.Bundestagswahl;
import model.Wahlkreis;

public class Mandatsrechner2009 extends Mandatsrechner{

	@Override
	public Mandatsrechner getInstance() {
		if(super.instance==null){
			super.instance = new Mandatsrechner2009();
		}
		return super.instance;
	}

	@Override
	public Bundestagswahl berechne(Bundestagswahl bw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Bundesland berechne(Bundesland bl) {
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

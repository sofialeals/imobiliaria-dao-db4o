package DAOdb4o;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;

import Classes.Condominio;

public class FiltroNumMoradores implements Evaluation {
	
	private int numMoradores;
	
	public FiltroNumMoradores(int numMoradores) {
		this.numMoradores = numMoradores;
	}

	public void evaluate(Candidate candidate) {
	Condominio c = (Condominio) candidate.getObject();
	if(c.getMoradores().size() == numMoradores) {
		candidate.include(true);
	} else {
		candidate.include(false);	
	}
	}
}

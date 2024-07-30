package DAOdb4o;

import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import Classes.Boleto;

public class DAOBoleto extends DAO<Boleto>{
	
	public Boleto read (Object chave) {
		int codBarras = (int) chave;
		Query q = manager.query();
		q.constrain(Boleto.class);
		q.descend("codBarras").constrain(codBarras);
		List<Boleto> resultados = q.execute();
		
		if (resultados.size() > 0) {
			return resultados.get(0);
		}
		else {
			return null;
		}
	}
	
	public List<Boleto> boletosNP(Object chave){
		int ano = (int) chave;
		Query q = manager.query();
		q.constrain(Boleto.class);
		q.descend("data").descend("year").constrain(ano);
		q.descend("pagou").constrain(false);
		
		List<Boleto> resultados = q.execute();
		
		if (resultados.size() > 0) {
			return resultados;
		}
		else {
			return null;
		}
	}
	
	public List<Boleto> boletosNPMorador(Object chave){
		String cpf = (String) chave;
		Query q = manager.query();
		q.constrain(Boleto.class);
		q.descend("morador").descend("cpf").constrain(cpf);
		q.descend("pagou").constrain(false);
		
		List<Boleto> resultados = q.execute();
		
		return resultados;
	}
}

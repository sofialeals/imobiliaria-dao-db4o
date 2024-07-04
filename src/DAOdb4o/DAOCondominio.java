package DAOdb4o;

import java.util.List;
import com.db4o.query.Query;
import Classes.Condominio;
import DAOdb4o.FiltroNumMoradores;

public class DAOCondominio extends DAO<Condominio>{
	
	public Condominio read (Object chave) {
		int id = (int) chave;
		Query q = manager.query();
		q.constrain(Condominio.class);
		q.descend("id").constrain(id);
		List<Condominio> resultados = q.execute();
		
		if (resultados.size() > 0) {
			return resultados.get(0);
		}
		else {
			return null;
		}
	}
	
	public void create(Condominio objeto){
		int novoid = super.gerarId();
		objeto.setID(novoid);
		manager.store(objeto);
	}
	
	public List<Condominio> ocupacaoEdifs(Object chave){
		int numMoradores = (int) chave;
		Query q = manager.query();
		q.constrain(Condominio.class);
		q.constrain(new FiltroNumMoradores(numMoradores));
		List<Condominio> resultados = q.execute();
		
		if (resultados.size() > 0) {
			return resultados;
		}
		else {
			return null;
		}
	}
	
	public boolean condExiste (Object chave) {
		String endereco = (String) chave;
		Query q = manager.query();
		q.constrain(Condominio.class);
		q.descend("endereco").constrain(endereco);
		List<Condominio> resultados = q.execute();
		
		if (resultados.size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
}

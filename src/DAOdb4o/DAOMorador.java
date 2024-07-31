package DAOdb4o;

import java.time.LocalDate;
import java.util.List;
import com.db4o.query.Query;
import Classes.Morador;

public class DAOMorador extends DAO<Morador> {
	
	public Morador read (Object chave) {
		String cpf = (String) chave;
		Query q = manager.query();
		q.constrain(Morador.class);
		q.descend("cpf").constrain(cpf);
		List<Morador> resultados = q.execute();
		
		if (resultados.size() > 0) {
			return resultados.get(0);
		}
		else {
			return null;
		}
	}
	
	public List<Morador> inadsCondX(Object chave) {
		int idCond = (int) chave;
		Query q = manager.query();
		q.constrain(Morador.class);
		q.descend("boletos").descend("pagou").constrain(false);
		q.descend("condominios").descend("id").constrain(idCond);
		q.descend("boletos").descend("data").descend("year").constrain(LocalDate.now().getYear());
		List<Morador> resultados = q.execute();
		
		return resultados;
	}
}

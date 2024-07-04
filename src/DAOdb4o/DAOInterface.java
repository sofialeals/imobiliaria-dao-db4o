package DAOdb4o;

import java.util.List;

public interface DAOInterface<T> {
	public void create(T objeto);
	public T read(Object chave);
	public T update(T objeto);
	public void delete(T objeto) ;
	public List<T> readAll();
	public void deleteAll();
}

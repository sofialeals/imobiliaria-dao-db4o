package DAOdb4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import Classes.Boleto;
import Classes.Condominio;
import Classes.Morador;

public class Util {
	private static ObjectContainer manager = null;
	
	public static ObjectContainer conectarBanco() {
		if(manager != null) {
			return manager;
		}
		
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration();
		config.common().messageLevel(0);
		config.common().objectClass(Morador.class).cascadeOnDelete(false);
		config.common().objectClass(Morador.class).cascadeOnUpdate(true);
		config.common().objectClass(Morador.class).cascadeOnActivate(true);
		config.common().objectClass(Condominio.class).cascadeOnDelete(false);
		config.common().objectClass(Condominio.class).cascadeOnUpdate(true);
		config.common().objectClass(Condominio.class).cascadeOnActivate(true);
		config.common().objectClass(Boleto.class).cascadeOnDelete(false);
		config.common().objectClass(Boleto.class).cascadeOnUpdate(true);
		config.common().objectClass(Boleto.class).cascadeOnActivate(true);
		
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}


	public static void desconectarBanco() {
		if(manager != null) {
			manager.close();
			manager = null;
		}
	}
}
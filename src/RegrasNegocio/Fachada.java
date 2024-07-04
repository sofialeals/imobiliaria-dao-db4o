package RegrasNegocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Classes.Boleto;
import Classes.Condominio;
import Classes.Morador;
import DAOdb4o.DAO;
import DAOdb4o.DAOBoleto;
import DAOdb4o.DAOCondominio;
import DAOdb4o.DAOMorador;

public class Fachada {
	private Fachada() {}
	
	private static DAOBoleto daoboleto = new DAOBoleto();
	private static DAOCondominio daocondominio = new DAOCondominio();
	private static DAOMorador daomorador = new DAOMorador();
	
// INICIALIZAÇÃO E FINALIZAÇÃO
	public static void inicializar(){
		DAO.open();
		Fachada.adcBoleto();
	}
	public static void finalizar(){
		DAO.close();
	}
	
// MÉTODOS DO BOLETO
	public static Boleto gerarBoleto(Condominio condominio, Morador morador, Integer apartamento, double valor) {
		DAO.begin();
		LocalDate data = LocalDate.now();
		int codBarras = Fachada.gerarCodBarras();
		Boleto boleto = Fachada.buscarBoleto(codBarras);
		
		while(boleto != null) {
			codBarras = Fachada.gerarCodBarras();
			boleto = Fachada.buscarBoleto(codBarras);
		}
		
		boleto = new Boleto(codBarras, condominio, morador, data, apartamento, valor);
		daoboleto.create(boleto);
		morador.adcBoleto(boleto);
		daomorador.update(morador);
		DAO.commit();
		return boleto;
	}
	
	public static void excluirBoleto(int codBarras) throws Exception{
		DAO.begin();
		Boleto boleto = Fachada.buscarBoleto(codBarras);
		
		if(boleto == null) {
			throw new Exception("O boleto de código "+codBarras+" não existe.");
		}
		
		daoboleto.delete(boleto);
		DAO.commit();
	}
	
	public static List<Boleto> listarBoletos(){
		List<Boleto> boletos = daoboleto.readAll();
		return boletos;
	}
	
	public static boolean pagarBoleto(int codBoleto) {
		DAO.begin();
		Boleto boleto = Fachada.buscarBoleto(codBoleto);
		Morador morador = boleto.getMorador();
		Condominio condominio = boleto.getCondominio();
		Integer apartamento = boleto.getApartamento();
		
		for(Boleto b : morador.getBoletos()) {
			if(b.getData().getMonthValue() == LocalDate.now().getMonthValue() - 1) {
				if(b.getApartamento() == apartamento && b.getCondominio() == condominio && b.getPagou() == false) {
					return false;
				}
			}
		}
		boleto.pagar();
		daoboleto.update(boleto);
		DAO.commit();
		
		return true;
	}
	
	public static List<Boleto> boletosNP(int ano){
		List<Boleto> boletosNP = daoboleto.boletosNP(ano);
		return boletosNP;
	}
	
	public static int gerarCodBarras() {
		Random random = new Random();
        int codBarras = random.nextInt(100000);;
        
        int tam = String.valueOf(codBarras).length();
        
        if (tam < 5) {
            int complemento = 5 - tam;
            String numFormatado = "0".repeat(complemento) + String.valueOf(codBarras);
            codBarras = Integer.parseInt(numFormatado);
        }
        
        return codBarras;
	}
	
	public static Boleto buscarBoleto(int codBarras) {
		Boleto boleto = daoboleto.read(codBarras);
		
		return boleto;
	}
	
	public static void adcBoleto() {
		LocalDate dataAtual = LocalDate.now();
		if(Fachada.listarMoradores().size() > 0) {
			for(Morador m : Fachada.listarMoradores()) {
				if(m.getBoletos().size() > 0) {
					for(Boleto b : m.getBoletos()) {
						if(m.getCondominios().contains(b.getCondominio())) {
							if(dataAtual.getMonthValue() > b.getData().getMonthValue() || (dataAtual.getMonthValue() == 1 && b.getData().getMonthValue() == 12)) {
								Fachada.gerarBoleto(b.getCondominio(), m, b.getApartamento(), b.getValor());
							}
						}
					}
				}
			}
		}
		
	}
	
// MÉTODOS DO CONDOMÍNIO
	public static Condominio cadastrarCondominio(String nome, String endereco) throws Exception{
		DAO.begin();
		boolean cadastrado = daocondominio.condExiste(endereco);
		if(cadastrado) {
			throw new Exception("Um condomínio com o endereço "+endereco+" já está cadastrado.");
		}
		
		Condominio condominio = new Condominio(nome, endereco);
		daocondominio.create(condominio);
		DAO.commit();
		return condominio;
	}
	
	public static void excluirCondominio(int id) throws Exception{
		DAO.begin();
		Condominio condominio = Fachada.buscarCondominio(id);
		
		if(condominio == null) {
			throw new Exception("O condomínio de ID "+id+" não está cadastrado.");
		} else if(condominio.getMoradores().size() > 0) {
			throw new Exception("O condomínio não pode ser excluído porque ainda tem moradores registrados.");
		}
		
		daocondominio.delete(condominio);
		DAO.commit();
	}
	
	public static List<Condominio> listarCondominios(){
		List<Condominio> condominios = daocondominio.readAll();
		return condominios;
	}
	
	public static List<Condominio> ocupacaoEdifs(int numMoradores){
		List<Condominio> ocupacaoEdifs = daocondominio.ocupacaoEdifs(numMoradores);
		return ocupacaoEdifs;
	}
	
	public static Condominio buscarCondominio(int id) {
		Condominio condominio = daocondominio.read(id);
		
		return condominio;
	}

// MÉTODOS DO MORADOR
	public static Morador cadastrarMorador(String nome, String cpf) throws Exception{
		DAO.begin();
		Morador morador = Fachada.buscarMorador(cpf);
		if(morador != null) {
			throw new Exception("O morador de CPF "+cpf+" já está cadastrado.");
		}
		
		morador = new Morador(nome, cpf);
		daomorador.create(morador);
		DAO.commit();
		return morador;
	}
	
	public static void excluirMorador(String cpf) throws Exception{
		DAO.begin();
		Morador morador = Fachada.buscarMorador(cpf);
		
		if(morador == null) {
			throw new Exception("O morador de CPF "+cpf+" não está cadastrado.");
		}
		
		if(morador.getCondominios().size() > 0) {
			for(Condominio c : morador.getCondominios()) {
				c.rmvMorador(morador);
			}
		}
		
		if(morador.getBoletos().size() > 0) {
			for(Boleto b : morador.getBoletos()) {
				if(b.getPagou() == false) {
					throw new Exception("O morador "+morador.getNome()+" não pode ser excluído porque ainda existem boletos pendentes no seu nome.");
				}
				daoboleto.delete(b);
			}
		}
		
		daomorador.delete(morador);
		DAO.commit();
	}
	
	public static List<Morador> listarMoradores(){
		List<Morador> moradores = daomorador.readAll();
		return moradores;
	}
	
	public static List<Morador> inadsCondX(int idCond){
		List<Morador> inadsCondX = daomorador.inadsCondX(idCond);
		return inadsCondX;
	}
	
	public static Morador buscarMorador(String cpf) {
		Morador morador = daomorador.read(cpf);
		return morador;
	}
	
// REGRAS DE NEGÓCIO
	public static void criarContrato(int idCond, String cpfMorador, double valorAluguel) {
		DAO.begin();
		
		Condominio condominio = Fachada.buscarCondominio(idCond);
		Morador morador = Fachada.buscarMorador(cpfMorador);
		
		ArrayList<Integer> aparts = condominio.getApartamentos();
		Integer numApart;
		
		if(aparts.size() == 0) {
			condominio.adcApartamento(1);
			numApart = 1;
		} else {
			int indiceNull = aparts.indexOf(null);
			if(indiceNull == -1) {
				Integer ultAp = aparts.get(aparts.size() - 1);
				numApart = ultAp + 1;
				condominio.adcApartamento(numApart);
			} else {
				numApart = indiceNull + 1;
				aparts.add(indiceNull, numApart);
			}
		}
		
		condominio.adcMorador(morador);
		morador.adcCondominio(condominio);
		morador.adcApartamento(numApart);
		Fachada.gerarBoleto(condominio, morador, numApart, valorAluguel);
		daocondominio.update(condominio);
		daomorador.update(morador);
		
		DAO.commit();
	}
	
	public static void encerrarContrato(int idCond, String cpfMorador) {
		DAO.begin();
		
		Condominio condominio = Fachada.buscarCondominio(idCond);
		Morador morador = Fachada.buscarMorador(cpfMorador);
		
		for(Integer ap : condominio.getApartamentos()) {
			if(morador.getApartamentos().contains(ap)) {
				morador.rmvApartamento(ap);
				int indiceApart = condominio.getApartamentos().indexOf(ap);
				condominio.getApartamentos().add(indiceApart, null);
			}
		}
		
		condominio.rmvMorador(morador);
		morador.rmvCondominio(condominio);
		daocondominio.update(condominio);
		daomorador.update(morador);
		
		DAO.commit();
	}
}
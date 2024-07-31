package Classes;
import java.util.ArrayList;
import java.util.List;

public class Condominio {
	private int id;
	private String nome;
	private String endereco;
	private ArrayList<Morador> moradores;
	private ArrayList<Integer> apartamentos;

	public Condominio(String nome, String endereco) {
		this.nome = nome;
		this.endereco = endereco;
		this.moradores = new ArrayList<Morador>();
		this.apartamentos = new ArrayList<Integer>();
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int novoID) {
		this.id = novoID;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public String toString() {
		return "- Condomínio "+nome+" | ID: "+id+
				"\nEndereço: "+endereco;
	}
	
// MÉTODOS DE APARTAMENTO	
	
	public void adcApartamento(Integer apartamento) {
		apartamentos.add(apartamento);
	}
	
	public ArrayList<Integer> getApartamentos() {
		return apartamentos;
	}
	
// MÉTODOS DE MORADOR
	
	public void adcMorador(Morador morador) {
		moradores.add(morador);
	}
	
	public void rmvMorador(Morador morador) {
		moradores.remove(morador);
	}
	
	public ArrayList<Morador> getMoradores() {
		return moradores;
	}
}
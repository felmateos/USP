package banco;

public abstract class Pessoa {
	String nome;
	int cpf;
	
	Pessoa(String nome, int cpf){
		this.nome = nome;
		this.cpf = cpf;
	}
	
	abstract String retornaTipo();
}

package banco;

public class ClienteEspecial extends Cliente{
	static final int dividaMaxima = 50000;
	static final String tipo = "CE";
	
	/* Construtor da Classe ClienteEspecial
	 * Este construtor invoca o construtor da classe Cliente.
	 */
	ClienteEspecial(String nome, int cpf, int valor){
		super(nome, cpf, valor);
	}
	
	/* Metodo que retorna o valor do atributo tipo do objeto atual */
	String retornaTipo() {
		return tipo;
	}
        
	/* Método para obter emprestimo usando o valor passado como parametro.
         * Os valores da divida e da conta corrente são incrementados pelo valor passado com parametro, retornando true.
         * Caso não se satisfaçam os requisitos o método retornará false.
         */
	public boolean obterEmprestimo(int valor) {
		if(valor <= dividaMaxima && valor > 0 && (valor + getValorDaDivida()) <= dividaMaxima){
                        setValorDaDivida(valor + getValorDaDivida());
                        setValorContaCorrente(valor + getValorContaCorrente());
                        return true;
                }
		return false;
	}
}

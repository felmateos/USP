package banco;

public class Gerente extends Pessoa implements InterfaceGerente{
	private static final String tipo = "G";
	private Cliente[] clientes;
	private int numClientes;
        private int[] cpfs = new int[20];

	/* Construtor da Classe Gerente
	 * Este construtor invoca o construtor da classe Pessoa e inicializa os dois atributos 
	 * do objeto que esta sendo instanciado.
	 */
	Gerente(String nome, int cpf){
		super(nome, cpf);
		clientes = new Cliente[20];
		numClientes = 0;
	}

	
	/* Metodo que retorna o valor do atributo tipo do objeto atual */
	String retornaTipo() {
		return tipo;
	}
	
	
	/* Metodo para imprimir informacoes sobre os clientes gerenciados pelo gerente atual
	 */
	void imprimirClientes(){
		Cliente atual;
		System.out.println("\tNumero de clientes: " + numClientes);
		for (int c=0; c < numClientes; c++){
			atual = clientes[c];
			System.out.println("\t" + (c+1) + "\t" + atual.retornaTipo() + "\t" + atual.nome + "\t" + atual.cpf + "\t" + atual.getValorContaCorrente() + "\t" + atual.getValorDaDivida() + "\t" + atual.negativado());
		}
	}


	/* Metodo para adicionar um cliente no arranjo de clientes do gerente atual.
	 * Caso o numero de clientes seja igual a 20, nao deve adicionar e deve retornar false.
	 * Caso contrario, ha duas situacoes: 
	 *   1a: o cliente ja consta no arranjo de clientes (verifique isso usando o numero do cpf),
	 *       neste caso o cliente nao deve ser reinserido e o metodo deve retornar false; 
	 *   2a: o cliente passado como parametro nao consta no arranjo de clientes: o cliente 
	 *       deve ser adicionado na posicao numClientes, o atributo numClientes deve ser 
	 *       incrementado em 1 e o metodo deve retornar true. 
	 */
        public boolean checaCpf(int cpf, int[] cpfs){
                for (int n = 0; n < cpfs.length; n++) {
                        if (cpf == cpfs[n] && n!= numClientes) return false;
                }
                return true;
        }
	public boolean adicionarCliente(Cliente cliente) {
                if(numClientes == 20) return false;
                cpfs[numClientes] = cliente.cpf;
                if(checaCpf(cliente.cpf, cpfs)){
                        clientes[numClientes] = cliente;
                        numClientes++;
                        return true;
                }
                return false;   
	}
	
	/* Metodo para cobrar os emprestimos de todos os clientes do gerente atual.
	 * Para cada um dos clientes presentes no arranjo clientes, este metodo deve:
	 *   - nao fazer nada para o cliente, caso seu valorDaDivida seja igual a zero
	 *   - caso contrario, ha duas situacoes:
	 *     1a) se o valorContaCorrente do cliente for maior ou igual ao valorDaDivida, deve
	 *         fazer o cliente pagar a divida, isto e, o valorContaCorrente sera atualizado, 
	 *         descontando-se o valor da divida e o valorDaDivida sera zerado.
	 *     2a) se o valorContaCorrente do cliente for menor do que o valorDaDivida, 
	 *         deve fazer o cliente pagar parte da divida, isto e, o valorDaDivida 
	 *         sera atualizado, tendo seu valor diminuido pelo valorContaCorrente e 
	 *         o valorContaCorrente sera zerado.
	 */
	public void cobrarTodosEmprestimos() {
                for (int i = 0; i < numClientes; i++) {
                        int subContaCorrente = clientes[i].getValorContaCorrente() - clientes[i].getValorDaDivida();
                        int subValorDaDivida = clientes[i].getValorDaDivida() - clientes[i].getValorContaCorrente();
                        if(clientes[i].getValorDaDivida() > 0){
                                if(clientes[i].getValorContaCorrente() >= clientes[i].getValorDaDivida()){
                                        clientes[i].setValorContaCorrente(subContaCorrente);
                                        clientes[i].setValorDaDivida(0);
                                }
                                else{
                                        clientes[i].setValorDaDivida(subValorDaDivida);
                                        clientes[i].setValorContaCorrente(0);
                                }
                        }
                }
	}
}
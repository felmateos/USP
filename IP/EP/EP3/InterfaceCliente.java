package banco;

/* Interface para definir quais metodos devem ser implementados na classe Cliente 
 * A descricao dos metodos se encontra na classe Cliente. 
 */
public interface InterfaceCliente {
	boolean obterEmprestimo(int valor);
	boolean pagarEmprestimo(int valor);
	boolean negativado();
	boolean realizarSaque(int valor);
}

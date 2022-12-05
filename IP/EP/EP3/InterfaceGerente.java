package banco;

/* Interface para definir quais metodos devem ser implementados na classe Gerente 
 * A descricao dos metodos se encontra na classe Gerente. 
 */
public interface InterfaceGerente {
	boolean adicionarCliente(Cliente cliente);
	void cobrarTodosEmprestimos();
}

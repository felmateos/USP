import java.util.Scanner;

/*********************************************************************/
/**   ACH2001 - Introdução à Programação                            **/
/**   EACH-USP - Primeiro Semestre de 2020                          **/
/**   código da turma: 2020102 - Prof: Luciano Antonio Digiampietri **/
/**                                                                 **/
/**   Primeiro Exercí­cio-Programa                                   **/
/**                                                                 **/
/**                                                                 **/
/**   15/04/2020                                                    **/
/*********************************************************************/

public class CaixaEletronico {
        static Scanner entrada = new Scanner(System.in);
        
        // variavel global valor
        static int valor;
        
	// Número de cédulas de B$50,00
	static int n50;
	
	// Número de cédulas de B$10,00
	static int n10;

	// Número de cédulas de B$5,00
	static int n5;
	
	// Número de cédulas de B$1,00
	static int n1;

	static void fazRetirada(int valor) {
            
            // verifica se o valor é positivo
            if(valor >= 0){
                // determina a quantidade de cédulas a serem utilizadas
                n50 = valor/50;
                int sobra50 = valor%50;
                n10 = sobra50/10;
                int sobra10 = sobra50%10;
                n5 = sobra10/5;
                int sobra5 = sobra10%5;
                n1 = sobra5/1;
                
                // mostra para o usuário quantas cédulas e tipo de cada uma a serem restiradas
                System.out.println("notas de 50: " + n50);
                System.out.println("notas de 10: " + n10);
                System.out.println("notas de 5: " + n5);
                System.out.println("notas de 1: " + n1);
            }
            else{
                System.out.println("o valor digitado deve ser poisitivo!!!");
            }
            
	}
        
	public static void main(String[] args) {
            
            // pergunta ao usuário a quantia a ser retirada
            int valor;
            System.out.println("quanto deseja retirar?");
            valor = entrada.nextInt();
            // executa o método fazRetirada
            fazRetirada(valor);
            
	}
}

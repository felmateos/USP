package raiz.java;

/*********************************************************************/
/**   ACH2001 - Introdução à Programção                             **/
/**   EACH-USP - Primeiro Semestre de 2020                          **/
/**   2020102 - Luciano Antonio Digiampietri                        **/
/**                                                                 **/
/**   Segundo Exercí­cio-Programa                                    **/
/**                                                                 **/
/**   Felipe Mateos Castro de Souza                  11796909       **/
/**                                                                 **/
/**   06/05/2020                                                    **/
/*********************************************************************/

/*
	Cálculo para raiz quadrada
*/
public class Raiz{
    /*
            Calcula a raiz quadrada de um valor, com uma determinada
            precisão. Retorna esse valor, ou -1 quando:
            * a < 0
            * epsilon <= 0
            * epsilon >= 1

            Ou 0 (zero) quando a = 0

            Parâmetro:
                    a - valor cuja raiz quadrada será calculada
                    epsilon - precisão do cálculo
    */

    static double raizQuadrada(double a, double epsilon) {
        /*Variável que contém o limite máximo de aproximações,
        não necessáriamnete serão feitas 30 aproximações*/
        int limMax = 30;

        //Vetor que aloca o valor de cada aproximação
        double aproxParciais[] = new double[limMax];

        /*Vetor que aloca o valor da diferença de cada aproximação
        com a aproximação anterior(|xi+1 − xi|)*/
        double difAprox[] = new double[aproxParciais.length];

        //Variável que contém a quantidade de aproximações feitas
        int qtdAprox = 1;
        
        //loop que permite a alocação de cada aproximação dentro do vetro "aproxParciais"
        for (int i = 0; i<aproxParciais.length; i++) {
            if (i == 0) {
                //Cálculo para a primeira aproximação (x0)
                aproxParciais[i]=a/2;
            }
            else{
                //Cáculo para as aproximações a partir da segunda (x1)
                aproxParciais[i] = (((aproxParciais[i-1])+(a/(aproxParciais[i-1])))*1/2);
                
                //Cáculo para calcular a diferença entre aproximações
                difAprox[i] = -(aproxParciais[i]-aproxParciais[i-1]);
            } 
            /*Caso a diferença entre aproximações for menor que "epsilon" o loop
            será interrompido*/
            if (i!=0 && difAprox[i]<epsilon) {
                break;
            }
            //Caso contrário é adicionado uma unidade à variável "qtdAprox"
            else{
                qtdAprox++;
            }
        }
        //Variável que aloca o valor da última aproximação, ou seja a raiz
        double resultadoRaiz = aproxParciais[qtdAprox-1];
        
        //Retorna o valor de "resultadoRaiz"
        return resultadoRaiz;
    }

    public static void main(String[] args) {
        //Variável que contém o valor que terá sua raiz obtida
        double valor = 63;
        
        //Variável que contém o valor que será usado como parâmetro de precisão
        double precisao = 0.0001;
        
        //Exibe a raiz de "valor" utilizando o método "raizQuadrada"
        System.out.println("Raiz de: " + valor + " = " + raizQuadrada(valor, precisao));
    }
}
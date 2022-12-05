import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class ep2 {
    
    public static void main(String[] args) {
        lerArquivo(); //invoca o método de leitura do arquivo vetorN.txt, seu funcionamento está descrito abaixo.
    }
    
    /* O método abaixo tem a função de perguntar ao usuário qual algoritmo de ordenação ele deseja, sendo essas as opções:
    (1) binaryInsertionSort, (2) binaryInsertionSortR, (3) insertionSort, (4) insertionSortR e (5) Sair.
    
    Também caberá ao usuário escolher qual arquivo vetorN.txt será organizado pelo algoritmo escolhido anteriormente,
    modificando o valor da String "caminho".
    */
    public static void lerArquivo() {
        BufferedReader bR = null;
        String[] vetor = null;
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o numero correspodente a algum opção abaixo:\n(1) binaryInsertionSort\n(2) binaryInsertionSortR"
                            + "\n(3) insertionSort\n(4) insertionSortR\n(5) Sair");
        n = sc.nextInt();
        if (n == 5) System.exit(0);
        String caminho = ""; // Digite aqui o caminho de veotrN.txt ou digite quando o programa for executado
        if (caminho.isEmpty()){
            System.out.println("Digite o caminho do seu arquivo com o vetorN.txt escolhido");
            caminho = sc.next();
        }
        try{
            bR = new BufferedReader(new FileReader(caminho));
            String row = "";
            int k = 0;
            long[] tempos = new long[50];
            while((row = bR.readLine()) != null){   
                vetor = row.replaceAll("\\.0", ",").replaceAll("\\s", "").split(",");
                int[] results = new int[vetor.length];
                for (int i = 0; i < vetor.length; i++) {
                    try {
                        results[i] = Integer.parseInt(vetor[i]);
                    } catch (NumberFormatException nsfw) {
                        nsfw.printStackTrace();
                        System.exit(0);
                    };
                }
                long tempoInicial = System.nanoTime();
                if (n == 1) binaryInsertionSort(results);
                if (n == 2) binaryInsertionSortR(results, results.length);
                if (n == 3) insertionSort(results);
                if (n == 4) insertionSortR(results, 1);
                long tempoFinal = System.nanoTime();
                long tempo = (tempoFinal - tempoInicial);
                tempos[k] = tempo;
                //System.out.println("\n" + Arrays.toString(results));
                //System.out.println("\n Tempo: " + tempo );
                k++; 
            }
            //if (k == tempos.length) System.out.println("media: " + mediaTempos(tempos));
        } catch (FileNotFoundException e){
            System.out.println("nao encontrado");
            System.exit(0);
            e.printStackTrace();
        } catch (IOException e) {
            System.exit(0);
            e.printStackTrace();
        }
        sc.close();
    }
    
    /*O algoritmo abaixo ordena os valores contidos no vetor fornecido, de forma iterativa
    usando o método auxiliar binarySearch também iterativo.*/
    private static void binaryInsertionSort(int[] v) {
        for (int i = 1; i < v.length; i++) {
            int comp = v[i];
            int ant = i - 1;
            if (i >= 2) {
                //invoca o método de busca binária iterativa para encontrar a posição em que o valor deve ser inserido
                int pos = binarySearch(v, comp, i);
                for (int j = i; j > pos; j--) {
                    v[j] = v[j - 1];
                    if (j - 1 == pos) v[pos] = comp;
                }
            } 
            else if (comp < v[ant]){
                int aux = comp;
                v[i] = v[ant];
                v[ant] = aux;
            }
        }
    }
    
    /*Método auxiliar que faz uma busca binária iterativa*/
    private static int binarySearch(int[] array, int comp, int lim){
        int left = 0;
        int right = lim - 1;
        while (left <= right) {
            int mid = left + ((right - left) /2);
            if ((comp >= array[mid]) && (comp <= array[mid + 1])) return (mid + 1);
            else if (comp < array[mid]) right = mid - 1;
            else left = mid + 1;
        }
        return 0;
    }
    
    /*O algoritmo abaixo ordena os valores contidos no vetor fornecido, de forma recursiva
    usando o método auxiliar binarySearch também recursivo.*/
    private static int binaryInsertionSortR(int[] v, int n) {
        if(n == v.length) n -= 1;
        if(n <= 1){
            if (v[n] < v[0]) {
                int aux = v[0];
                v[0] = v[n];
                v[n] = aux;
            }  
        }
        else {
            //Chamada recursiva do próprio método
            int ult = binaryInsertionSortR(v, n - 1) + 1;
            int comp = v[n];
            //invoca o método de busca binária recursiva para encontrar a posição em que o valor deve ser inserido
            int pos = binarySearchR(v, comp, 0, ult, ult);
            for (int i = n; i > pos; i--) {
                v[i] = v[i - 1];
                if (i - 1 == pos) v[pos] = comp;
            }
        }
        return n;
    }
    
    /*Método auxiliar que faz uma busca binária recursiva*/
    private static int binarySearchR(int[] array, int comp, int left, int right, int lim){
        int mid = left + ((right - left) /2);
        if (right == 1) mid = 1;
        if (mid == lim) return mid;
        if ((comp >= array[mid]) && (comp <= array[mid + 1])) return (mid + 1);
        if ((mid) == 0)  return 0;
        if (comp < array[mid]) return binarySearchR(array, comp, left, mid - 1, lim); //Chamada recursiva do próprio método
        else return binarySearchR(array, comp, mid + 1, right, lim);//Chamada recursiva do próprio método
    }
    
    //O algoritmo abaixo ordena os valores contidos no vetor fornecido, de forma iterativa
    private static void insertionSort(int[] v) {
        int x, j;
        for (int i = 1; i < v.length; i++) {
            x = v[i];
            j = i - 1;
            while ((j >= 0) && (v[j] > x)) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = x;
        }
    }
  
    //O algoritmo abaixo ordena os valores contidos no vetor fornecido, de forma recursiva
    private static void insertionSortR(int[] v, int i) {
        int x, j;
        if (i < v.length) {
            x = v[i];
            j = i - 1;
            while ((j >= 0) && (v[j] > x)) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = x;
            i++;
            insertionSortR(v, i); //Chamada recursiva do próprio método
        }
    }
}
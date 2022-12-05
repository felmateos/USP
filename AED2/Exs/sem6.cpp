#include <iostream>

#define MAX 100

typedef struct {
    int v1;
    int v2;
    int endereco;
} registroPrin;

typedef struct adj {
    int v;
    int custo;
    struct adj* prox;
} NO;

typedef struct {
    NO* inicio;
} VERTICE;

registroPrin* criaTabela(char* caminho){
    FILE* arq;
    registroPrin* vetorArestas = (registroPrin *) malloc(sizeof (registroPrin));
    int test;
    int prox = 0;
    if ((arq = fopen(caminho, "r")) == NULL) {
        printf("Arquivo nao encontrado...\n");
    } else {
        while (feof(arq) == 0 && prox < MAX) {
            //printf("%d", prox);
            fread(&vetorArestas[prox].v1, sizeof(int),1,arq);
            if(vetorArestas[prox].v1 < 0 || vetorArestas[prox].v1 > 9) {
                vetorArestas[prox].v1 = NULL;
                break;
            }
            fread(&vetorArestas[prox].v2, sizeof(int),1,arq);
            vetorArestas[prox].endereco = prox;
            prox++;
            fseek(arq, sizeof (int), SEEK_CUR);
        }
    }
    fclose(arq);
    return vetorArestas;
}

int main () {
    return 0;
}
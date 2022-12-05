#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <malloc.h>

typedef struct adj {
    int v;
    int p;
    struct adj *prox;
} NO;

typedef struct {
int v1;
int v2;
int custo;
} REGISTRO;

typedef struct vertice {
    NO* inicio;
    int chave;
    int flag;
} VERTICE;

typedef struct grafo {
    int n;
    int arestas;
    VERTICE *adj;
} GRAFO;

GRAFO* criaGrafo(int v) {
    GRAFO* gr = (GRAFO*)malloc(sizeof(GRAFO));
    gr->arestas = 0;
    gr->n = v;
    gr->adj = (VERTICE*)malloc(v*sizeof(VERTICE));
    int i;
    for(i=0; i<v; i++) gr->adj[i].inicio = NULL;
    return gr;
}

NO* criaAdj(int v, int peso) {
    NO* temp = (NO*)malloc(sizeof(NO));
    temp->v = v;
    temp->p = peso;
    temp->prox = NULL;
    return temp;
}

bool criaAresta(GRAFO* gr, int vi, int vf, int peso) {
    if (!gr) return(false);
    if(vf<0 || vf >= gr->n || vi<0) return false;
    NO* novo = criaAdj(vf,peso);
    novo->prox = gr->adj[vi].inicio;
    gr->adj[vi].inicio = novo;
    gr->arestas++;
    return true;
}

GRAFO* arquivoGrafo(char caminho, int v) {
    FILE *arq;
    int peso, x, y, a, b, c;
    if ((arq=fopen(caminho,"rb"))==NULL) {
        printf("Arquivo nao encontrado...\n");
    }
    else {
        GRAFO* gr = criaGrafo(v);
        while (feof(arq)==0) {
            a = fread(x, sizeof(int),1,arq);
            b = fread(y, sizeof(int),1,arq);
            c = fread(peso, sizeof(int),1,arq);
            criaAresta(gr, x, y, peso);
        }
        fclose(arq);
        return gr;
    }

}
int main (){
    return 0;
}
//--------------------------------------------------------------
// NOME : Felipe Mateos Castro de Souza
//--------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <malloc.h>


// 11796909 ESCREVA O NROUSP AQUI
int nroUSP() {
    return 11796909;
}

//-------------------------------------------

// nos das listas de adjacencias
typedef struct adj {
    int v;
    struct adj* prox;
} NO;

// vertices
typedef struct {
    NO* inicio;
    int flag;
} VERTICE;


// funcao principal
NO* caminhos_max_d(VERTICE* g, int n, int x, int y, int d);

// funcao para tornar todos os flags do grafo em 0
void resetaFlags(VERTICE* g, int n);

// funcao de busca em DFS, percorre os caminhos até o alvo
NO* DFS(VERTICE* gr, int d, int i, int alvo, NO* ant, NO* prim, NO* ofc);

// armazena o caminho percorrido ate o vertice atual
NO* entraListaTemp(NO* ant, int v);

// armazena a lista criada em "antraListaTemp" em uma nova lista, caso satisfaca a distancia e o vertice alvo
void complementaListaFinal(NO* prim, NO* ofc);

//------------------------------------------
// O EP consiste em implementar esta funcao
// e outras funcoes auxiliares que esta
// necessitar
//------------------------------------------

// -----------------------------------------

void resetaFlags(VERTICE* g, int n) {
    for (int i = 1; i <= n; ++i) {
        if(g[i].inicio == NULL) break;
        g[i].flag = 0;
    }
}

NO* entraListaTemp(NO* ant, int v) {
    NO* novo = (NO*) malloc( sizeof (NO));
    novo->v = v;
    novo->prox = NULL;
    if (ant) ant->prox = novo;
    return novo;
}

void complementaListaFinal(NO* prim, NO* ofc) {
    NO* ant = NULL;
    NO* novo = NULL;
    NO* ini = NULL;
    while (true) {
        if (ofc->prox == NULL) break;
        ofc = ofc->prox;
    }
    while (prim) {
        novo = (NO*) malloc(sizeof (NO));
        novo->v = prim->v;
        novo->prox = NULL;
        if (!ini) ini = novo;
        if (ant) ant->prox = novo;
        ant = novo;
        prim = prim->prox;
    }
    if (ofc->v == -1) {
        ofc->v = ini->v;
        ofc->prox = ini->prox;
    }
    else ofc->prox = ini;
}

NO* DFS(VERTICE* gr, int d, int i, int alvo, NO* ant, NO* prim, NO* ofc) {
    if (d == -1) return ofc;
    ant = entraListaTemp(ant, i);
    if (!prim) prim = ant;
    if (i == alvo) {
        complementaListaFinal(prim, ofc);
        return ofc;
    }
    gr[i].flag = 1;
    NO* p = gr[i].inicio;
    while (p) {
        if (gr[p->v].flag == 0) DFS(gr, d-1, p->v, alvo, ant, prim, ofc);
        p = p->prox;
    }
    gr[i].flag = 0;
    return ofc;
}

NO* caminhos_max_d(VERTICE* g, int n, int x, int y, int d) {
    resetaFlags(g, n);
    NO* ofc = (NO*) malloc(sizeof (NO));
    ofc->v = -1;
    ofc->prox = NULL;
    NO* prim = DFS(g, d, x, y, NULL, NULL, ofc);
    if (prim->v == -1) return NULL;
    return prim;
}

//---------------------------------------------------------
// use main() para fazer chamadas de teste ao seu programa
// mas nao entregue o codido de main() nem inclua nada
// abaixo deste ponto
//---------------------------------------------------------

int main()
{
    if (nroUSP()==0) printf("\n\nNro USP nao informado!!!\n\n");
}

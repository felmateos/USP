#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <malloc.h>


const char* nroUSP() {
    return("");
}


const char* nome() {
    return("");
}

// elemento da matriz
typedef struct estr {
    int valor;
    int lin;
    int col;
    struct estr* prox;
} NO;

// funcao principal
NO* copiaTransposta(NO* p);

// funcao que organiza a matriz
NO* insertionSort(NO* p);

// funcao auxiliar de insertionSort
NO* comparador(NO* novo, NO* atual, NO* prim);

// deleta a matriz auxiliar criada em copiaTransposta
void deletarMatriz(NO* p);

//------------------------------------------
// O EP consiste em implementar esta funcao
// e outras funcoes auxiliares que esta
// necessitar
//------------------------------------------

NO* comparador(NO* novo, NO* atual, NO* prim) {
    NO* ant = NULL;
    do {
        if (!atual) {
            ant->prox = novo;
            return prim;
        }
        if (novo->lin > atual->lin) {
            ant = atual;
            atual = atual->prox;
        }
        if (atual && (novo->lin < atual->lin)) {
            if (ant) ant->prox = novo;
            else prim = novo;
            novo->prox = atual;
            return prim;
        }
        if (atual && (novo->lin == atual->lin)) {
            if (novo->col > atual->col) {
                ant = atual;
                atual = atual->prox;
            }
            else {
                ant->prox = novo;
                novo->prox = atual;
                return prim;
            }
        }
    } while (true);
}

NO* insertionSort(NO* p) {
    NO* prim = NULL;
    NO* novo = NULL;
    NO* ant = NULL;
    do {
        novo = (NO*) malloc(sizeof (NO));
        novo->valor = p->valor;
        novo->lin = p->lin;
        novo->col = p->col;
        novo->prox = NULL;
        if (!prim) prim = novo;
        if (!ant) {
            ant = novo;
            p = p->prox;
        } else {
            prim = comparador(novo, prim, prim);
            ant = novo;
            if (p->prox == NULL) return prim;
            p = p->prox;
        }
    } while(true);
}

void deletarMatriz(NO* p) {
    NO* ant = NULL;
    do {
        ant = p;
        p = p->prox;
        free(ant);
        if (!p->prox) {
            free(p);
            break;
        }
    } while (true);
}

NO* copiaTransposta(NO* p) {
    NO* resp = NULL;
    NO* novo = NULL;
    NO* ant = NULL;
    NO* prim = NULL;
    do {
        novo = (NO*) malloc(sizeof (NO));
        novo->valor = p->valor;
        novo->lin = p->col;
        novo->col = p->lin;
        novo->prox = NULL;
        if (ant) ant->prox = novo;
        else prim = novo;
        ant = novo;
        p = p->prox;
    } while(p);
    resp = insertionSort(prim);
    deletarMatriz(prim);
    return resp;
}

//----------------------------------------------------------------
// use main() apenas para fazer chamadas de teste ao seu programa
//----------------------------------------------------------------

int main() {

}

// por favor nao inclua nenhum c�digo abaixo da fun��o main()

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <conio.h>
#include <malloc.h>


char* nroUSP() {
    return("");
}


char* nome() {
    return("");
}

// elemento da lista
typedef struct estr {
    int valor;
    struct estr* prox;
} NO;

// funcao principal
NO* uniao(NO* p1, NO* p2);

// funcao para eliminar as repetições
NO* eliminarRepeticoes(NO* p);

//------------------------------------------
// O EP consiste em implementar esta funcao
// e outras funcoes auxiliares que esta
// necessitar
//------------------------------------------

NO* uniao(NO* p1, NO* p2) {
    NO* inicio = NULL;
    NO* fim = NULL;
    NO* novo = NULL;
    NO* resp = NULL;
    if (!p1 && !p2)  return NULL;
    do {
        if (!p1 || (p2->valor <= p1->valor)) {
            novo = (NO *) malloc(sizeof(NO));
            novo->valor = p2->valor;
            novo->prox = NULL;
            p2 = p2->prox;
        }
        else if (p2->valor >= p1->valor) {
            novo = (NO *) malloc(sizeof(NO));
            novo->valor = p1->valor;
            novo->prox = NULL;
            p1 = p1->prox;
        }
        if (fim) fim->prox = novo;
        else inicio = novo;
        fim = novo;
    } while (p1 || p2);
    resp = inicio;
    eliminarRepeticoes(resp);
    return resp;
}

NO* eliminarRepeticoes(NO* p) {
    NO* ant = NULL;
    NO* inicio = p;
    do {
        if (ant) {
            if (ant->valor == p->valor) {
                NO* aux = p->prox;
                free(p);
                p = aux;
                ant->prox = p;
            } else {
                ant = p;
                p = p->prox;
            }
        } else {
            ant = p;
            p = p->prox;
        }
    } while (p);
    return inicio;
}

//----------------------------------------------------------------
// use main() apenas para fazer chamadas de teste ao seu programa
//----------------------------------------------------------------

int main() {
}

// por favor nao inclua nenhum código abaixo da função main()
#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <string.h>


const char* nroUSP() {
    return("");
}

// no da arvore
typedef struct estr {
    int chave;
    struct estr *esq;
    struct estr *dir;
} NO;

typedef struct estruturaPilha {
    NO* ramo;
    struct estruturaPilha *prox;
} ITEM;

typedef struct PILHA {
    struct estruturaPilha *topo;
} PILHA;

void organizar(NO* *raiz); // Funcao principal que tem como objetivo corrigir uma arvore "quase" ABB com até 1 erro

void criarPilha(PILHA* pi); // Cria uma pilha com topo = NULL

void empilhar(NO* n, int d, PILHA* pi); // Percorre a arvore em ordem decrescente e usa a funcao "push" para empilha-la

void push(PILHA* pi, NO* n); // Funcao para empilhar cada NO da arvore

NO* verificaImpostor(PILHA* pi); // Percorre a pilha em ordem crescente na busca por uma chave que fuja da ordem crescente

bool verificaRepeticoes(PILHA* pi, int ch); // Verifica se existem chaves repetidas na pilha

NO* deletaNO(NO* n); // Deleta o NO com a chave errada e retorna o substituto, caso ele exista

NO* acheSeuPai(NO* p, NO* i, int d, NO* res); // Encontra a posicao para o substituto, caso ele exista

void liberaPilha(PILHA* pi); // Deleta cada ITEM que pertence a pilha usada anteriormente

//------------------------------------------
// O EP consiste em implementar esta funcao
//------------------------------------------

void criarPilha(PILHA* pi) {
    pi->topo = NULL;
}

void push(PILHA* pi, NO* n) {
    ITEM* novo = (ITEM*) malloc(sizeof (ITEM));
    novo->ramo = n;
    novo->prox = NULL;
    if (pi->topo) novo->prox = pi->topo;
    pi->topo = novo;
}

bool verificaRepeticoes(PILHA* pi, int ch) {
    ITEM* atual = pi->topo;
    bool rep = false;
    do {
        if (!atual) break;
        if (atual->ramo->chave == ch) {
            if (rep) return true;
            rep = true;
        }
        atual = atual->prox;
    } while (true);
    return false;
}

NO* verificaImpostor(PILHA* pi) {
    if (!pi->topo) return NULL;
    ITEM* atual = pi->topo;
    ITEM* prox = pi->topo->prox;
    ITEM* suspeito1 = NULL;
    ITEM* suspeito2 = NULL;
    ITEM* comp = NULL;
    bool suspeito = false;
    do {
        if (!atual || (!prox && !suspeito)) break;
        if (prox == NULL && suspeito) {
            if (verificaRepeticoes(pi, suspeito1->ramo->chave)) return suspeito1->ramo;
            if (verificaRepeticoes(pi, suspeito2->ramo->chave)) return suspeito2->ramo;
            comp = pi->topo;
            do {
                if (comp == suspeito1) return suspeito1->ramo;
                if ((comp->prox == suspeito1) && (suspeito2->ramo->chave <= comp->ramo->chave)) return suspeito2->ramo;
                comp = comp->prox;
            } while (true);
        }
        if (atual->ramo->chave >= prox->ramo->chave) {
            if (suspeito) return suspeito1->ramo;
            suspeito1 = atual;
            suspeito2 = prox;
            prox = prox->prox;
            suspeito = true;
        }
        else {
            if (suspeito) return suspeito2->ramo;
            atual = prox;
            prox = prox->prox;
        }
    } while (true);
    return NULL;
}

void empilhar(NO* n, int d, PILHA* pi) { //working
    if (n) {
        empilhar(n->dir, d+1, pi);
        push(pi, n);
        empilhar(n->esq, d+1, pi);
    }
}

void liberaPilha(PILHA* pi) {
    ITEM* atual = pi->topo;
    if (!pi->topo->prox) {
        free(atual);
        pi->topo = NULL;
        return;
    }
    ITEM* prox = atual->prox;
    if (prox) {
        free(atual);
        pi->topo = prox;
        liberaPilha(pi);
    }
}

NO* acheSeuPai(NO* p, NO* i, int d, NO* res) {
    if (p) {
        if (res) return res;
        res = acheSeuPai(p->dir, i, d+1, res);
        if (p->dir && p->dir == i) return p;
        if (p->esq && p->esq == i) return p;
        res = acheSeuPai(p->esq, i, d+1, res);
    }
    return res;
}

NO* deletaNO(NO* n) {
    NO* target = n;
    NO* pai = NULL;
    if(n->esq) {
        pai = n;
        n = n->esq;
    }
    do {
        if(!n->dir) break;
        pai = n;
        n = n->dir;
    } while (true);
    if (!pai) {
        n = NULL;
        free(n);
        return NULL;
    }
    if (pai->esq == n) {
        if (!n->esq) pai->esq = NULL;
        else pai->esq = n->esq;
    }
    else if (pai->dir == n) {
        if (!n->esq) pai->dir = NULL;
        else pai->dir = n->esq;
    }
    n->esq = target->esq;
    n->dir = target->dir;
    free(target);
    return n;
}

void organizar(NO* *raiz) {
    NO* p = *raiz;
    PILHA* pi = (PILHA*) malloc(sizeof (PILHA));
    criarPilha(pi);
    empilhar(p, 1, pi);
    NO* impostor = verificaImpostor(pi);
    if (impostor) {
        NO *pai = acheSeuPai(p, impostor, 1, NULL);
        if (pai) {
            if (pai->dir == impostor) pai->dir = deletaNO(impostor);
            if (pai->esq == impostor) pai->esq = deletaNO(impostor);
        } else *raiz = deletaNO(impostor);
    }
    liberaPilha(pi);
}

//---------------------------------------------------------
// use main() para fazer chamadas de teste ao seu programa
//---------------------------------------------------------

int main() {
}

// por favor nao inclua nenhum código abaixo da função main()

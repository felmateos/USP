//--------------------------------------------------------------
// NOME : FELIPE MATEOS CASTRO DE SOUZA
//--------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>

// 11796909 ESCREVA O NROUSP AQUI
int nroUSP() {
    return 11796909;
}

//-------------------------------------------

// CHLINK eh um par <chave,link> - dentro de cada página há 3 CHLINKs
typedef struct {
    int chave;
    int linkdir;
} CHLINK;

// definicao de pagina da árvore / registro do arquivo
typedef struct {
    int np; // armazene aqui o nro da pagina no arquivo
    int cont; // armazene aqui a quantidade de chaves existentes no vetor CHLINK[]
    CHLINK item[3]; // a chave[0] eh desprezada, e o linkdir[0] eh na verdade o link esquerdo da chave[1]
} PAGINA;


// funcao principal
void inserir(char nomearq[], int* raiz, int ch);

// funcao que cria uma pagina com uma chave sem link algum
PAGINA* criaPaginaS(int np, int chave, int plinkdir, int slinkdir);

// funcao que busca pela chave a ser inserida, caso nao a encontre sera feita a insercao da chave
int buscaChaveR(int* raiz, PAGINA* pag, FILE* arq, int ch, int np, PAGINA* nch, char nomearq[], bool* desbalanceada);

// funcao que faz a insercao da chave na posicao correta
bool insercaoPos(int* raiz, PAGINA* pag, PAGINA* nch, char nomearq[], int posicao);

// funcao que faz o split da pagina
int splitPos(PAGINA* pag, PAGINA* nch, char nomearq[], int posicao);

//------------------------------------------
// O EP consiste em implementar esta funcao
// e outras funcoes auxiliares que esta
// necessitar
//------------------------------------------

PAGINA* criaPaginaS(int np, int chave, int plinkdir, int slinkdir) {
    PAGINA* npag = (PAGINA*) malloc(sizeof (PAGINA));
    for (int i = 0; i < 3; ++i) {
        npag->item[i].chave = -1;
        npag->item[i].linkdir = -1;
    }
    npag->item[1].chave = chave;
    npag->item[0].linkdir = plinkdir;
    npag->item[1].linkdir = slinkdir;
    npag->np = np;
    npag->cont = 1;
    return npag;
}

int splitPos(PAGINA* pag, PAGINA* nch, char nomearq[], int posicao) {
    FILE* arqw = fopen(nomearq, "rb+");
    fseek(arqw,0,SEEK_END);
    PAGINA* npag = NULL;
    int np = ftell(arqw)/(sizeof (PAGINA));
    if (posicao != 1) npag = criaPaginaS(ftell(arqw)/(sizeof (PAGINA)), nch->item[1].chave, nch->item[0].linkdir, nch->item[1].linkdir);
    else npag = criaPaginaS(ftell(arqw)/(sizeof (PAGINA)), pag->item[2].chave, nch->item[1].linkdir, pag->item[2].linkdir);
    fwrite(npag, sizeof (PAGINA), 1, arqw);
    fclose(arqw);
    return np;
}

bool insercaoPos(int* raiz, PAGINA* pag, PAGINA* nch, char nomearq[], int posicao) {
    FILE* arqw = fopen(nomearq, "rb+");
    bool overflow = false;
    if (pag->cont == 1) {
        if (posicao == 0) {
            pag->item[2] = pag->item[1];
            pag->item[1] = nch->item[1];
            pag->item[0] = nch->item[0];
            pag->cont = 2;
        }
        else {
            pag->item[2] = nch->item[1];
            pag->item[1].linkdir = nch->item[0].linkdir;
            pag->cont = 2;
        }
    } else {
        if (posicao == 0) {
            nch->item[0].linkdir = splitPos(pag, nch, nomearq, posicao);
            nch->item[1].chave = pag->item[1].chave;
            nch->item[1].linkdir = pag->np;
            pag->item[1].chave = pag->item[2].chave;
            pag->item[0].linkdir = pag->item[1].linkdir;
            pag->item[1].linkdir = pag->item[2].linkdir;
            pag->item[2].linkdir = -1;
        }
        else if (posicao == 2) {
            nch->item[1].linkdir = splitPos(pag, nch, nomearq, posicao);
            nch->item[1].chave = pag->item[2].chave;
            nch->item[0].linkdir = pag->np;
            pag->item[2].chave = -1;
            pag->item[2].linkdir = -1;
        }
        else if (posicao == 1) {
            nch->item[1].linkdir = splitPos(pag, nch, nomearq, posicao);
            pag->item[2].chave = -1;
            pag->item[1].linkdir = nch->item[0].linkdir;
            nch->item[0].linkdir = pag->np;
        }
        pag->cont = 1;
        if (pag->np == *raiz) {
            fseek(arqw,0,SEEK_END);
            nch->np = ftell(arqw)/(sizeof (PAGINA));
            fwrite(nch, sizeof (PAGINA),1, arqw);
            *raiz = nch->np;
        }
        overflow = true;
    }
    fseek(arqw,(pag->np * sizeof (PAGINA)),SEEK_SET);
    fwrite(pag, sizeof (PAGINA),1, arqw);
    fclose(arqw);
    return overflow;
}

int buscaChaveR(int* raiz, PAGINA* pag, FILE* arq, int ch, int np, PAGINA* nch, char nomearq[], bool* desbalanceada) {
    *desbalanceada = true;
    int posicao = 0;
    int achei = 0;
    nch->item[1].chave = ch;
    fseek(arq, (np * sizeof (PAGINA)), SEEK_SET);
    fread(pag, sizeof (PAGINA), 1, arq);
    int i = 1;
    while (i <= pag->cont) {
        if (pag->item[i].chave == ch) {
            *desbalanceada = false;
            return 2;
        }
        if (pag->item[i-1].chave < ch && ch < pag->item[i].chave) {
            posicao = i-1;
            if (pag->item[i].linkdir > -1)
                achei = buscaChaveR(raiz, pag, arq, ch, pag->item[i-1].linkdir, nch, nomearq, desbalanceada);
        }
        else if (i == pag->cont && pag->item[i].chave < ch) {
            posicao = i;
            if (pag->item[i].linkdir > -1)
                achei = buscaChaveR(raiz, pag, arq, ch, pag->item[i].linkdir, nch, nomearq, desbalanceada);
        }
        if (achei != 0) break; // sai se achou ou chegou no fim
        i++;
    }
    fseek(arq, (np * sizeof (PAGINA)), SEEK_SET);
    fread(pag, sizeof (PAGINA), 1, arq);
    if (*desbalanceada) *desbalanceada = insercaoPos(raiz, pag, nch, nomearq, posicao);
    return 1;
}

void inserir(char *nomearq, int* raiz, int ch) {
    if (*raiz == -1) {
        PAGINA* nraiz = criaPaginaS(0, ch, -1, -1);
        *raiz = 0;
        FILE* arqw = fopen(nomearq, "rb+");
        fwrite(nraiz, sizeof (PAGINA),1, arqw);
        fclose(arqw);
        return;
    }
    PAGINA* pag = criaPaginaS(-1, -1, -1, -1);
    PAGINA* nch = criaPaginaS(-1, -1, -1, -1);
    bool* desbalanceada = (bool*) malloc(sizeof (bool));
    FILE* arq;
    if ((arq = fopen(nomearq, "rb")) == NULL) {
        printf("Arquivo nao encontrado...\n");
    } else {
        buscaChaveR(raiz, pag, arq, ch, *raiz, nch, nomearq, desbalanceada);
    }
    fclose(arq);
}

//---------------------------------------------------------
// use main() para fazer chamadas de teste ao seu programa
// mas nao entregue o codigo de main() nem inclua nada
// abaixo deste ponto
//---------------------------------------------------------

int main()
{
}



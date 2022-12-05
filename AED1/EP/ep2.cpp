#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <malloc.h>
#include <cstring>



const char* nroUSP() {
    return("");
}


const char* nome() {
    return("");
}

// elemento da pilha
typedef struct estrutura {
    struct estrutura *prox;
    int tipo; // 1=char e 2=float
    union {
        char simbolo;
        float valor;
    };
} NO;

// funcao principal (retorna float e int)
float calcular(char* expressao, int* codigo);

// funcao para verificar o tipo de cada caracter da expressao
int verifTipo (char c);

// funcao para separar as variaveis para efetuar o calculo
NO* pushCalc (NO* item, int* codigo);

// efetua o calculo com as variaveis passadas como parametro
float calcParenteses (float p1, char op, float p2);

// apaga um item da pilha e retorna o proximo
NO* freeNo(NO* item);

//------------------------------------------
// O EP consiste em implementar esta funcao
// e outras funcoes auxiliares que esta
// necessitar
//------------------------------------------

int verifTipo (char c) {
    int tipo = 0;
    if (c == ')' || c == '('|| c == '+' || c == '-' || c == '/' || c == '*') tipo = 1;
    else tipo = 2;
    return tipo;
}
float calcParenteses (float p1, char op, float p2){
    float res = 0;
    switch (op) {
        case '+': res = p1 + p2;
            break;
        case '-': res = p1 - p2;
            break;
        case '/': res = p1 / p2;
            break;
        case '*': res = p1 * p2;
            break;
        default: res = 0;
    }
    return res;
}
NO* freeNo(NO* item){
    NO* aux = NULL;
    aux = item->prox;
    free(item);
    item = aux;
    return item;
}
NO* pushCalc (NO* item, int* codigo) {
    *codigo = 999;
    float resp = 0.0;
    float p1 = 0.0;
    float p2 = 0.0;
    char op = NULL;
    bool readyP2 = false;
    bool readyOp = false;
    bool readyP1 = false;
    do {
        char s = item->simbolo;
        if (*codigo == -1 || *codigo == 0) return item;
        if (item->simbolo == '('){
            item->simbolo = NULL;
            item->tipo = 2;
            item->valor = resp;
            return item;
        }
        if (item->tipo == 1) {
            if (item->prox->tipo != 2) *codigo = -1;
            if (s == '*' || s == '/' || s == '+' || s == '-') {
                op = item->simbolo;
                item = freeNo(item);
                readyOp = true;
            }
            else item = freeNo(item);
        }
        if (item->tipo == 2) {
            if (!readyP2) {
                if(!item->prox) *codigo = -1;
                p2 = item->valor;
                if (p2 == 0 && item->prox->simbolo == '/') *codigo = 0;
                item = freeNo(item);
                resp = p2;
                readyP2 = true;
            } else if (!readyP1) {
                if (item->prox->simbolo != '(') *codigo = -1;
                p1 = item->valor;
                item = freeNo(item);
                readyP1 = true;
            }
        }
        if(readyP2 && readyOp && readyP1) resp = calcParenteses(p1, op, p2);
        } while (item);
    return item;
}
float calcular(char* expressao, int* codigo){
    *codigo = 999;
    float resp = 0.0;
    NO* ant = NULL;
    NO* novo = NULL;
    int i = 0;
    int count = 0;
    do {
        if (expressao[0] != '(') *codigo = -1;
        novo = (NO*) malloc(sizeof(NO));
        novo->tipo = verifTipo(expressao[i]);
        if (novo->tipo == 1) novo->simbolo = expressao[i];
        else novo->valor = expressao[i]-48;
        if (!ant) novo->prox = NULL;
        else novo->prox = ant;
        if (novo->simbolo == ')') count++;
        if (novo->simbolo == '(') count--;
        if (novo->simbolo == ')') novo = pushCalc(novo, codigo);
        if (*codigo == 0) return resp;
        if (*codigo == -1) return resp;
        ant = novo;
        i++;
    } while (expressao[i]);
    expressao = NULL;
    if (count != 0) {
        *codigo = -1;
        return resp;
    }
    resp = novo->valor;
    *codigo = 1;
    return resp;
}

//----------------------------------------------------------------
// use main() apenas para fazer chamadas de teste ao seu programa
//----------------------------------------------------------------

int main() {

    // o EP sera testado com chamadas deste tipo

}

// por favor nao inclua nenhum código abaixo da função main()
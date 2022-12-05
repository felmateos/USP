#include <conio.h>
#include <fstream>
#include <iostream>
#include <stdio.h>
#include <string>
#define MAX 30
using namespace std;

struct tipo_registro{
    char nrousp[7];
    char nome[20];
    int idade;
    int valido; // 1=ok; 0=excluido.
};

// function to perform the task
// accepts the parameter str as the text to
// be stored in text file

void Exibir(struct tipo_registro vetor_alunos[], int proximo){
    int i;
    printf("\n--------------------------------------\n");
    printf("Ha %d alunos cadastrados.\n", proximo);
    for (i=0; i< proximo; i++)
    {
        if(vetor_alunos[i].valido == 0) printf("\nexcluido");
        else {
            cout << endl;
            cout << vetor_alunos[i].nrousp << " ";
            cout << vetor_alunos[i].nome << " ";
            printf("%d ", vetor_alunos[i].idade);
            cout << vetor_alunos[i].valido;
        }
    }
    printf("\n--------------------------------------\n");
}

void Switch(FILE* arq, int r1, int r2, char str[]) {
    char str2[30];
    ofstream ofs;
    int numChar = 0;

    // created text file
    ofs.open("C:\\Users\\Acer\\Desktop\\Reverse.txt", ios::out);

    for (int i = 0; str[i] != '\0'; i++) {
        // writing into the file
        ofs.put(str[i]);
        numChar++;
    }
    cout << numChar;

    // storing the position of  end of the file
    int pos = ofs.tellp();
    ofs.close();

    // object for reading the contents of the
    // first text file
    ifstream ifs;
    ifs.open("C:\\Users\\Acer\\Desktop\\Reverse.txt", ios::in);


    for (int i = 0; str[i] != '\0'; i++) {
        // writing into the file
        ifs >> str2[i];
    }
    ifs.close();

    ofstream ofs2;
    ofs2.open("C:\\Users\\Acer\\Desktop\\Reverse.txt", ios::out);

    for (int i = 0; i == numChar; i++) {
        // writing into the file
        ofs.put('\0');
    }

    ifs.open("C:\\Users\\Acer\\Desktop\\Reverse.txt", ios::in);
    ifs.seekg(--pos);
    while (numChar >= 0) {
        ofs2.put(str2[numChar]);
        numChar--;
    }

    ifs.close();
    ofs2.close();
    return;
}

int main(){
    struct tipo_registro vetor_alunos[MAX];
    FILE* arq;
    Switch(arq, 1, 2,"teste");
    return 0;
}
#include <stdio.h>

int verificarOrdem(int vetor[], int N)
{
    int c = 0;
    int d = 0;
    for (int i = 0; i < (N-1); i++) {
        if(vetor[i] < vetor[i+1]) c++;
        if(vetor[i] > vetor[i+1]) d++;
    }
    if (c == (N-1)) return 1;
    if (d == (N-1)) return -1;
    if (c == 0 && d == 0) return 0;
    return 99;
}

Multiplica(int A[3][3], int B[3][3])
{
    int C[3][3] = {0,0,0,0,0,0,0,0,0};
    int count = 0;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
            C[i][j] = C[i][j] + A[i][k] * B[k][j];
            printf("\n%d", C[i][j]);
            count++;
                printf("\n%d", count);
            }
        }
    }
    return C;
}
int main()
{
    //int vetor[] = {2,-3,-4,-10};
    //int N = sizeof(vetor) / sizeof(vetor[0]);

    //int z = verificarOrdem(vetor, N);
    //printf("result: %i", z);
    int A[3][3] = {0,0,0,0,0,0,0,0,0};
    int B[3][3] = {0,0,0,0,0,0,0,0,0};

    int n = 3;
    Multiplica(A, B);


    return 0;
}
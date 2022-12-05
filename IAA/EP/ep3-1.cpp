#include <iostream>
using namespace std;

//typedef std::pair<int, int> pair;



int f, n;
bool positivo[50], negativo[50];
int entrada[50];

map<pair<int,int>bool> memo;

bool resolvedor(int id, int soma) {
    if (id >= n && soma == f) return true;
    else if (id >= n) return false;

    bool ent = false, saida = false;

    ent = resolvedor(id+1, soma+entrada[id]);
    saida = resolvedor(id+1, soma-entrada[id]);

    if (ent && !saida) positivo[id] = true;
    else if (!ent && saida) negativo[id] = true;
    else if (ent) positivo[id] = negativo[id] = true;
    return (ent || saida);

}

int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}

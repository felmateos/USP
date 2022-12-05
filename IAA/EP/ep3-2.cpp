#include <iostream>
#include <map>

using namespace std;

bool pos[50], neg[50];
int input[50];
int F, N;
map<pair<int, int>, bool> mem;

bool verify(int id, int sum) {
    if (id >= N && sum == F)//m
        return true;//0m
    else if (id >= N)//m
        return false;//0m
    if (mem.count(make_pair(sum, id)) != 0)//tm-1
        return mem[make_pair(sum,id)];//tm-1

    bool in = false, out = false;//m

    in = verify(id+1, sum + input[id]);//m
    out = verify(id+1, sum - input[id]);//m

    if (in && !out)//m
        pos[id] = true;//0m
    else if (!in && out)//m
        neg[id] = true;//0m
    else if (in && out)//m
        pos[id] = neg[id] = true;//0m
    return mem[make_pair(sum,id)] = (in || out);//m
}

int main() {
    while (scanf("%d %d", &N, &F) && N > 0) {
        mem.clear();

        for (int i = 0; i < N; i++) {
            pos[i] = neg[i] = false;
            scanf("%d", &input[i]);
        }

        bool valid = verify(0, 0);

        for (int i = 0; i < N && valid; i++) {
            if (pos[i] && neg[i])
                printf("?");
            else if (pos[i])
                printf("+");
            else
                printf("-");
        }

        if (!valid)
            printf("*");

        printf("\n");
    }
    return (0);
}

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <string.h>

#define MAX_N 1000
#define MAX_M 10

typedef struct {
    int profit;
    int weight[MAX_M];
} Item;

int n, m; // 物品数, 维度数
int capacity[MAX_M];
Item items[MAX_N];
int solution[MAX_N];
int best_solution[MAX_N];
int best_value = 0;

// 解析输入数据
void read_input(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        printf("无法打开文件 %s\n", filename);
        exit(1);
    }

    fscanf(file, "%d %d", &n, &m);
    for (int j = 0; j < n; j++) {
        fscanf(file, "%d", &items[j].profit);
        for (int i = 0; i < m; i++) {
            fscanf(file, "%d", &items[j].weight[i]);
        }
    }
    for (int i = 0; i < m; i++) {
        fscanf(file, "%d", &capacity[i]);
    }

    fclose(file);
}

// 计算目标函数
int evaluate(int *sol) {
    int total_profit = 0;
    int total_weight[MAX_M] = {0};

    for (int j = 0; j < n; j++) {
        if (sol[j]) {
            total_profit += items[j].profit;
            for (int i = 0; i < m; i++) {
                total_weight[i] += items[j].weight[i];
                if (total_weight[i] > capacity[i]) {
                    return -1; // 违反约束
                }
            }
        }
    }
    return total_profit;
}

// 贪心初始化解
void greedy_initial_solution() {
    for (int j = 0; j < n; j++) {
        solution[j] = 0;
    }

    for (int j = 0; j < n; j++) {
        int valid = 1;
        for (int i = 0; i < m; i++) {
            if (items[j].weight[i] > capacity[i]) {
                valid = 0;
                break;
            }
        }
        if (valid) {
            solution[j] = 1;
        }
    }
}

// 生成邻域解
void generate_neighbor() {
    int index = rand() % n;
    solution[index] = 1 - solution[index]; // 翻转随机位
}

// 模拟退火算法
void simulated_annealing(double ts, double tf, double beta, int iter) {
    double T = ts;
    greedy_initial_solution();
    best_value = evaluate(solution);

    while (T > tf) {
        for (int i = 0; i < iter; i++) {
            int old_value = evaluate(solution);
            int old_solution[MAX_N];
            for (int j = 0; j < n; j++) old_solution[j] = solution[j];

            generate_neighbor();
            int new_value = evaluate(solution);

            if (new_value > old_value || (new_value > 0 && exp((new_value - old_value) / T) > ((double)rand() / RAND_MAX))) {
                if (new_value > best_value) {
                    best_value = new_value;
                    for (int j = 0; j < n; j++) best_solution[j] = solution[j];
                }
            } else {
                for (int j = 0; j < n; j++) solution[j] = old_solution[j]; // 退回原解
            }
        }
        T = T / (1 + beta * T);
    }
}

// 输出结果
void write_output(const char *filename) {
    FILE *file = fopen(filename, "w");
    if (!file) {
        printf("无法写入文件 %s\n", filename);
        exit(1);
    }

    fprintf(file, "1\n%d\n", best_value);
    for (int j = 0; j < n; j++) {
        fprintf(file, "%d ", best_solution[j]);
    }
    fprintf(file, "\n");

    fclose(file);
}

// 主函数
int main(int argc, char *argv[]) {
    if (argc != 7 || strcmp(argv[1], "-s") || strcmp(argv[3], "-o") || strcmp(argv[5], "-t")) {
        printf("使用方式: %s -s data_file -o solution_file -t max_time\n", argv[0]);
        return 1;
    }

    srand(time(NULL));

    read_input(argv[2]);
    double ts = 1000, tf = 1, beta = 0.001;
    int iter = 1000;
    simulated_annealing(ts, tf, beta, iter);
    write_output(argv[4]);

    return 0;
}

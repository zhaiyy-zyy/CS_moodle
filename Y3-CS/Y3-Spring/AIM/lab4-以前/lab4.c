#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

#define MAX_ITEMS 1000
#define MAX_CONSTRAINTS 10

// Problem instance variables
int n, m;
double profits[MAX_ITEMS];
double constraints[MAX_CONSTRAINTS][MAX_ITEMS];
double capacities[MAX_CONSTRAINTS];

// Solution representation
int solution[MAX_ITEMS];

// Function to read the input file
void read_input(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        printf("Error opening file!\n");
        exit(1);
    }
    fscanf(file, "%d %d", &n, &m);
    
    for (int i = 0; i < n; i++) {
        fscanf(file, "%lf", &profits[i]);
    }
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            fscanf(file, "%lf", &constraints[i][j]);
        }
    }
    
    for (int i = 0; i < m; i++) {
        fscanf(file, "%lf", &capacities[i]);
    }
    fclose(file);
}

// Greedy heuristic for initial solution
void greedy_initialization() {
    for (int i = 0; i < n; i++) {
        solution[i] = 0;
    }
    
    double profit_density[MAX_ITEMS];
    int indices[MAX_ITEMS];
    
    for (int i = 0; i < n; i++) {
        profit_density[i] = profits[i];
        indices[i] = i;
    }
    
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (profit_density[i] < profit_density[j]) {
                double temp = profit_density[i];
                profit_density[i] = profit_density[j];
                profit_density[j] = temp;
                
                int temp_index = indices[i];
                indices[i] = indices[j];
                indices[j] = temp_index;
            }
        }
    }
    
    double current_weight[MAX_CONSTRAINTS] = {0};
    
    for (int i = 0; i < n; i++) {
        int item = indices[i];
        int feasible = 1;
        
        for (int j = 0; j < m; j++) {
            if (current_weight[j] + constraints[j][item] > capacities[j]) {
                feasible = 0;
                break;
            }
        }
        
        if (feasible) {
            solution[item] = 1;
            for (int j = 0; j < m; j++) {
                current_weight[j] += constraints[j][item];
            }
        }
    }
}

// Objective function to evaluate solution
double evaluate_solution() {
    double total_profit = 0;
    for (int i = 0; i < n; i++) {
        if (solution[i]) {
            total_profit += profits[i];
        }
    }
    return total_profit;
}

// Generate a neighboring solution by flipping a random bit
void generate_neighbor() {
    int rand_index = rand() % n;
    solution[rand_index] = 1 - solution[rand_index];
}

// Simulated Annealing algorithm
void simulated_annealing(double ts, double tf, double beta, int iter) {
    srand(time(NULL));
    
    double temperature = ts;
    double best_value = evaluate_solution();
    int best_solution[MAX_ITEMS];
    
    for (int i = 0; i < n; i++) {
        best_solution[i] = solution[i];
    }
    
    while (temperature > tf) {
        for (int i = 0; i < iter; i++) {
            int old_solution[MAX_ITEMS];
            for (int j = 0; j < n; j++) {
                old_solution[j] = solution[j];
            }
            
            generate_neighbor();
            double new_value = evaluate_solution();
            double delta = new_value - best_value;
            
            if (delta > 0 || (exp(delta / temperature) > ((double)rand() / RAND_MAX))) {
                if (new_value > best_value) {
                    best_value = new_value;
                    for (int j = 0; j < n; j++) {
                        best_solution[j] = solution[j];
                    }
                }
            } else {
                for (int j = 0; j < n; j++) {
                    solution[j] = old_solution[j];
                }
            }
        }
        
        temperature = temperature / (1 + beta * temperature);
    }
    
    printf("Best solution found: %.2lf\n", best_value);
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("Usage: %s <data_file> <beta>\n", argv[0]);
        return 1;
    }
    
    read_input(argv[1]);
    double beta = atof(argv[2]);
    
    greedy_initialization();
    simulated_annealing(1000.0, 1e-3, beta, 100);
    
    return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <string.h> // For memcpy

#define NUM_OF_ITEMS 100
#define CAPACITY 1000

int p[] = {10, 11, 11, 10, 12, 3, 5, 13, 6, 10, 12, 19, 8, 3, 19, 3, 7, 8, 17, 8, 3, 5, 3, 5, 5, 13, 9, 9, 4, 11, 20, 18, 5, 17, 19, 14, 4, 14, 7, 17, 6, 9, 7, 12, 20, 3, 9, 12, 4, 7, 13, 20, 3, 18, 4, 12, 10, 3, 20, 4, 4, 13, 17, 16, 4, 5, 16, 18, 8, 18, 20, 9, 10, 10, 16, 18, 20, 3, 5, 17, 16, 13, 11, 3, 10, 20, 12, 6, 6, 11, 18, 18, 14, 19, 6, 18, 9, 14, 12, 10};

int v[] = {36, 93, 108, 82, 67, 82, 88, 77, 60, 138, 135, 93, 92, 128, 134, 144, 75, 148, 133, 150, 103, 79, 82, 106, 143, 99, 101, 80, 90, 84, 62, 90, 95, 119, 75, 101, 93, 85, 118, 81, 64, 87, 113, 117, 142, 88, 134, 134, 100, 112, 103, 90, 149, 122, 145, 92, 63, 103, 140, 62, 138, 60, 103, 145, 143, 109, 117, 63, 65, 124, 78, 121, 114, 96, 99, 110, 125, 95, 92, 70, 70, 149, 115, 148, 85, 81, 62, 99, 124, 91, 101, 118, 130, 140, 86, 97, 138, 89, 63, 122};

// Global parameters for GA
#define POPULATION_SIZE 1000
#define NUM_GENERATIONS 1000000
#define MUTATION_RATE 0.1
#define MAX_TIME 300  // Maximum time in seconds

// Structure to represent an individual (binary string)
typedef struct {
    int genes[NUM_OF_ITEMS];
    int fitness;
} individual;

// Function to calculate the total weight of an individual
int calculate_total_weight(individual *ind, int *weights) {
    int total_weight = 0;
    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        if (ind->genes[i] == 1) {
            total_weight += weights[i];
        }
    }
    return total_weight;
}

// Function to create a random feasible individual
individual create_feasible_individual(int *weights) {
    individual ind;
    memset(ind.genes, 0, sizeof(ind.genes));
    int current_weight = 0;
    int items_left[NUM_OF_ITEMS];
    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        items_left[i] = i;
    }
    int num_items_left = NUM_OF_ITEMS;

    while (current_weight < CAPACITY && num_items_left > 0) {
        int random_index_in_left = rand() % num_items_left;
        int item_index = items_left[random_index_in_left];

        if (current_weight + weights[item_index] <= CAPACITY) {
            ind.genes[item_index] = 1;
            current_weight += weights[item_index];
            // Remove the selected item from the remaining items
            items_left[random_index_in_left] = items_left[num_items_left - 1];
            num_items_left--;
        } else {
            break; // Cannot add more items without exceeding capacity
        }
    }
    ind.fitness = 0;
    return ind;
}

// Function to calculate the fitness of an individual
int calculate_fitness(individual *ind, int *profits, int *weights) {
    int total_weight = 0;
    int total_profit = 0;
    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        if (ind->genes[i] == 1) {
            total_weight += weights[i];
            total_profit += profits[i];
        }
    }
    return total_profit; // No need to check capacity here as we ensure feasibility
}

// Comparison function for sorting individuals by fitness (descending)
int compare_individuals(const void *a, const void *b) {
    individual *indA = (individual *)a;
    individual *indB = (individual *)b;
    return (indB->fitness - indA->fitness);
}

// Function for selection using roulette wheel selection
individual* selection(individual *population, int total_fitness) {
    if (total_fitness <= 0) {
        return &population[rand() % POPULATION_SIZE];
    }
    int random_number = rand() % total_fitness;
    int cumulative_fitness = 0;
    for (int i = 0; i < POPULATION_SIZE; i++) {
        cumulative_fitness += population[i].fitness;
        if (cumulative_fitness > random_number) {
            return &population[i];
        }
    }
    return &population[rand() % POPULATION_SIZE];
}

// Function for crossover (single-point) that produces feasible offspring
void crossover(individual *parent1, individual *parent2, individual *child1, individual *child2, int *weights) {
    int crossover_point = 1 + (rand() % (NUM_OF_ITEMS - 1));
    for (int i = 0; i < crossover_point; i++) {
        child1->genes[i] = parent1->genes[i];
        child2->genes[i] = parent2->genes[i];
    }
    for (int i = crossover_point; i < NUM_OF_ITEMS; i++) {
        child1->genes[i] = parent2->genes[i];
        child2->genes[i] = parent1->genes[i];
    }
    child1->fitness = 0;
    child2->fitness = 0;

    // Ensure feasibility of children (simple repair: remove items if over capacity)
    while (calculate_total_weight(child1, weights) > CAPACITY) {
        int index_to_remove = rand() % NUM_OF_ITEMS;
        if (child1->genes[index_to_remove] == 1) {
            child1->genes[index_to_remove] = 0;
        }
    }
    while (calculate_total_weight(child2, weights) > CAPACITY) {
        int index_to_remove = rand() % NUM_OF_ITEMS;
        if (child2->genes[index_to_remove] == 1) {
            child2->genes[index_to_remove] = 0;
        }
    }
}

// Function for mutation (bit-flip) that maintains feasibility
void mutate(individual *ind, int *weights) {
    int index_to_mutate = rand() % NUM_OF_ITEMS;
    int current_weight = calculate_total_weight(ind, weights);

    // If the item is not in the knapsack, try to add it
    if (ind->genes[index_to_mutate] == 0) {
        if (current_weight + weights[index_to_mutate] <= CAPACITY) {
            ind->genes[index_to_mutate] = 1;
        }
    }
    // If the item is in the knapsack, try to remove it
    else {
        ind->genes[index_to_mutate] = 0;
    }
    ind->fitness = 0;
}

int main() {
    srand(0); // Seed the random number generator

    individual population[POPULATION_SIZE];
    individual next_generation[POPULATION_SIZE];
    individual best_individual;
    best_individual.fitness = 0;

    time_t start_time = time(NULL);

    printf("Genetic Algorithm Progress (Population Size = %d, Generations = %d, Mutation Rate = %.2f):\n", POPULATION_SIZE, NUM_GENERATIONS, MUTATION_RATE);

    // Initialize population with feasible individuals
    for (int i = 0; i < POPULATION_SIZE; i++) {
        population[i] = create_feasible_individual(v);
    }

    for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
        if (time(NULL) - start_time > MAX_TIME) {
            printf("Time limit exceeded, stopping Genetic Algorithm.\n");
            break;
        }

        int total_fitness = 0;
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i].fitness = calculate_fitness(&population[i], p, v);
            total_fitness += population[i].fitness;
            if (population[i].fitness > best_individual.fitness) {
                best_individual = population[i]; // Copy the best individual
            }
        }

        if (generation % 1000 == 0)
            printf("Generation %d, Best Fitness: %d\n", generation, best_individual.fitness);

        // Sort the population by fitness
        qsort(population, POPULATION_SIZE, sizeof(individual), compare_individuals);

        // Create the next generation
        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            individual *parent1 = selection(population, total_fitness);
            individual *parent2 = selection(population, total_fitness);
            crossover(parent1, parent2, &next_generation[i], &next_generation[i + 1], v);
            mutate(&next_generation[i], v);
            mutate(&next_generation[i + 1], v);
        }

        // Replace the current population with the next generation
        memcpy(population, next_generation, sizeof(population));
    }

    printf("\nGenetic Algorithm Solution:\n");
    printf("  Total Profit: %d\n", best_individual.fitness);
    int total_weight = 0;
    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        if (best_individual.genes[i] == 1) {
            total_weight += v[i];
        }
    }
    printf("  Total Weight: %d\n", total_weight);
    printf("  Capacity Left: %d\n", CAPACITY - total_weight);
    printf("  Selected Item Indices: ");
    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        if (best_individual.genes[i] == 1) {
            printf("%d ", i);
        }
    }
    printf("\n");

    return 0;
}
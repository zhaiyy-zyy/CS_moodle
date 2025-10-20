#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>

// Global parameters
int MAX_NUM_OF_ITERS = 1000; // Increased iterations
int MAX_TIME = 1000; // Increased max time
#define MOVES_PER_TEMP 100 // Define moves per temperature

/* The following is problem data. For convenience, we
   include in the program. For real applications, the data
   should be loaded from file(s). */
int NUM_OF_ITEMS = 100;
int CAPACITY = 1000;

int p[] = {10, 11, 11, 10, 12, 3, 5, 13, 6, 10, 12, 19, 8, 3, 19, 3, 7, 8, 17, 8, 3, 5, 3, 5, 5, 13, 9, 9, 4, 11, 20, 18, 5, 17, 19, 14, 4, 14, 7, 17, 6, 9, 7, 12, 20, 3, 9, 12, 4, 7, 13, 20, 3, 18, 4, 12, 10, 3, 20, 4, 4, 13, 17, 16, 4, 5, 16, 18, 8, 18, 20, 9, 10, 10, 16, 18, 20, 3, 5, 17, 16, 13, 11, 3, 10, 20, 12, 6, 6, 11, 18, 18, 14, 19, 6, 18, 9, 14, 12, 10};

int v[] = {36, 93, 108, 82, 67, 82, 88, 77, 60, 138, 135, 93, 92, 128, 134, 144, 75, 148, 133, 150, 103, 79, 82, 106, 143, 99, 101, 80, 90, 84, 62, 90, 95, 119, 75, 101, 93, 85, 118, 81, 64, 87, 113, 117, 142, 88, 134, 134, 100, 112, 103, 90, 149, 122, 145, 92, 63, 103, 140, 62, 138, 60, 103, 145, 143, 109, 117, 63, 65, 124, 78, 121, 114, 96, 99, 110, 125, 95, 92, 70, 70, 149, 115, 148, 85, 81, 62, 99, 124, 91, 101, 118, 130, 140, 86, 97, 138, 89, 63, 122};

struct item {
    int indx; // remember the index of items after sorting
    int p;
    int v;
    double ratio;
};

struct problem {
    int C; // knapsack capacity
    int num_of_items;
    struct item* items;
};

struct solution {
    int objective;
    int cap_left;
    struct problem* prob;
    int* x;
};

// Initialize problem struct with global variables v and p.
struct problem* init_prob() {
    struct item* my_item = malloc(sizeof(struct item) * NUM_OF_ITEMS);
    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        my_item[i].indx = i;
        my_item[i].p = p[i];
        my_item[i].v = v[i];
    }
    struct problem* my_prob = malloc(sizeof(struct problem));
    my_prob->C = CAPACITY;
    my_prob->num_of_items = NUM_OF_ITEMS;
    my_prob->items = my_item;
    return my_prob;
}

void free_problem(struct problem* my_prob) {
    if (my_prob != NULL) {
        free(my_prob->items); // free item array
        free(my_prob);
    }
}

// Create an empty solution
struct solution* create_empty_sol(struct problem* my_prob) {
    struct solution* my_sln = malloc(sizeof(struct solution));
    my_sln->prob = my_prob;
    my_sln->objective = 0;
    my_sln->cap_left = my_prob->C;
    my_sln->x = malloc(sizeof(int) * my_prob->num_of_items);
    for (int i = 0; i < my_prob->num_of_items; i++) my_sln->x[i] = 0;
    return my_sln;
}

struct solution* copy_from(struct solution* sln) {
    struct solution* my_sln = malloc(sizeof(struct solution));
    my_sln->prob = sln->prob;  // shallow copy
    my_sln->objective = sln->objective;
    my_sln->cap_left = sln->cap_left;
    my_sln->x = malloc(sizeof(int) * sln->prob->num_of_items);
    for (int i = 0; i < sln->prob->num_of_items; i++)
        my_sln->x[i] = sln->x[i];
    return my_sln;
}

void free_solution(struct solution* sln) {
    if (sln != NULL) {
        free(sln->x);
        free(sln);
    }
}

// Function to calculate the total capacity used by the solution
int calculate_objective(struct solution* sln) {
    int objective = 0;
    for (int i = 0; i < sln->prob->num_of_items; i++) {
        if (sln->x[i] > 0) {
            objective += sln->prob->items[i].p;
        }
    }
    return objective;
}


int calculate_capacity_used(struct solution* sln) {
    int total_capacity = 0;
    for (int i = 0; i < sln->prob->num_of_items; i++) {
        if (sln->x[i] > 0) {
            total_capacity += sln->prob->items[i].v;
        }
    }
    return total_capacity;
}

void print_sol(struct solution* sln, char* sol_name) {
    printf("%s's objective = %d, cap left = %d\n", sol_name, sln->objective, sln->cap_left);

    // Checking anomaly in the solution struct.
    int cap = 0, obj = 0;
    for (int i = 0; i < sln->prob->num_of_items; i++) {
        if (sln->x[sln->prob->items[i].indx] > 0) {
            obj += sln->prob->items[i].p;
            cap += sln->prob->items[i].v;
        }
    }
    if (obj != sln->objective) {
        printf("Inconsistent objective value. Correct val = %d\n", obj);
    }
    if (sln->prob->C - cap != sln->cap_left) {
        printf("Inconsistent residual capacity value. Correct val = %d\n", sln->prob->C - cap);
    }

    // Print the total capacity used
    int total_capacity_used = calculate_capacity_used(sln);
    printf("Total capacity used: %d\n", total_capacity_used);

    int objective = calculate_objective(sln);
    printf("Objective calculated: %d\n", objective);

    if (total_capacity_used <= sln->prob->C && objective==sln->objective) {
        printf("Capacity and objective check: PASSED\n\n");
    } else {
        printf("Capacity and objective check check: FAILED\n\n");
    }
}

int cmpfunc(const void* a, const void* b) {
    const struct item* item1 = a;
    const struct item* item2 = b;
    if (item1->ratio > item2->ratio) return -1;
    if (item1->ratio < item2->ratio) return 1;
    return 0;
}

int cmpfunc2(const void* a, const void* b) {
    const struct item* item1 = a;
    const struct item* item2 = b;
    if (item1->indx > item2->indx) return 1;
    if (item1->indx < item2->indx) return -1;
    return 0;
}

// Greedy heuristic by packing highest pi/vi
struct solution* greedy_heuristic(struct problem* prob) {
    if (prob == NULL) {
        printf("Missing problem data, please check!\n");
        exit(1);
    }
    struct solution* greedy_sln = create_empty_sol(prob);
    int n = prob->num_of_items;
    for (int i = 0; i < n; i++) {
        prob->items[i].ratio = (double)prob->items[i].p / prob->items[i].v;
    }
    // Sort items according to ratio
    qsort(prob->items, n, sizeof(struct item), cmpfunc);
    greedy_sln->cap_left = prob->C;
    greedy_sln->objective = 0;
    for (int i = 0; i < n; i++) {
        if (greedy_sln->cap_left >= prob->items[i].v) {
            greedy_sln->x[prob->items[i].indx] = 1;
            greedy_sln->cap_left -= prob->items[i].v;
            greedy_sln->objective += prob->items[i].p;
        }
    }
    // Make sure original order of items is restored to match index in x
    qsort(prob->items, n, sizeof(struct item), cmpfunc2);
    return greedy_sln;
}

struct solution* first_descent(struct solution* curt_sln) {
    int item1, item2;
    struct solution* new_sln = copy_from(curt_sln);
    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        if (curt_sln->x[i] > 0) {
            item1 = i;
            for (int j = 0; j < NUM_OF_ITEMS; j++) {
                int v1 = curt_sln->prob->items[item1].v;
                int v2 = curt_sln->prob->items[j].v;
                if (i != j && curt_sln->x[j] == 0 && curt_sln->cap_left + v1 >= v2) {
                    item2 = j;
                    int delta = curt_sln->prob->items[item2].p - curt_sln->prob->items[item1].p;
                    if (delta > 0) { // Only accept strictly better solutions
                        new_sln->x[item1] = 0;
                        new_sln->x[item2] = 1;
                        new_sln->objective = curt_sln->objective + delta;
                        new_sln->cap_left = curt_sln->cap_left + curt_sln->prob->items[item1].v - curt_sln->prob->items[item2].v;
                        return new_sln;
                    }
                }
            }
        }
    }
    return NULL;
}

struct solution* local_search(struct solution* init_sln) {
    struct solution *nb_sln, *curt_sln, *best_local_sln; // Track best local solution
    curt_sln = copy_from(init_sln);
    best_local_sln = copy_from(init_sln); // Initialize best with initial solution

    for (int k = 0; k < MAX_NUM_OF_ITERS; k++) { // Keep MAX_NUM_OF_ITERS same for comparison
        nb_sln = first_descent(curt_sln);
        if (nb_sln != NULL) {
            free_solution(curt_sln);
            curt_sln = copy_from(nb_sln);
            free_solution(nb_sln);
            if (curt_sln->objective > best_local_sln->objective) { // Update best if current is better
                free_solution(best_local_sln);
                best_local_sln = copy_from(curt_sln);
            }
        } else {
            break; // Stop when no improvement in First Descent
        }
    }
    free_solution(curt_sln); // Free last curt_sln
    return best_local_sln; // Return the best solution found during descent
}

// Structure to represent a move (item to add, item to remove)
struct move {
    int add_item;
    int remove_item;
};

// Function to generate a neighbor by swapping items (can be 1-1, 1-2, or 2-1)
struct solution* generate_neighbor(struct solution* sln, int k) {
    struct solution* neighbor = copy_from(sln);
    int num_in = 0;
    int in_items[NUM_OF_ITEMS];
    int num_out = 0;
    int out_items[NUM_OF_ITEMS];

    for (int i = 0; i < NUM_OF_ITEMS; i++) {
        if (neighbor->x[i] > 0) {
            in_items[num_in++] = i;
        } else {
            out_items[num_out++] = i;
        }
    }

    if (num_in == 0 && num_out == 0) {
        free_solution(neighbor);
        return NULL;
    }

    int move_type = rand() % 3; // 0: 1-1, 1: 1-2, 2: 2-1
    

    if (move_type >= 0 && num_in > 0 && num_out > 0) { // 1-1 swap
        int item_out = in_items[rand() % num_in];
        int item_in = out_items[rand() % num_out];

        int weight_out = neighbor->prob->items[item_out].v;
        int weight_in = neighbor->prob->items[item_in].v;

        if (neighbor->cap_left + weight_out >= weight_in) {
            neighbor->x[item_out] = 0;
            neighbor->x[item_in] = 1;
            neighbor->objective += neighbor->prob->items[item_in].p - neighbor->prob->items[item_out].p;
            neighbor->cap_left += weight_out - weight_in;
            return neighbor;
        }
    } else if (move_type == 1 && num_in > 0 && num_out >= 2) { // 1-2 swap
        int item_out = in_items[rand() % num_in];
        int item_in1 = out_items[rand() % num_out];
        int item_in2;
        do {
            item_in2 = out_items[rand() % num_out];
        } while (item_in2 == item_in1);

        int weight_out = neighbor->prob->items[item_out].v;
        int weight_in1 = neighbor->prob->items[item_in1].v;
        int weight_in2 = neighbor->prob->items[item_in2].v;

        if (neighbor->cap_left + weight_out >= weight_in1 + weight_in2) {
            neighbor->x[item_out] = 0;
            neighbor->x[item_in1] = 1;
            neighbor->x[item_in2] = 1;
            neighbor->objective += neighbor->prob->items[item_in1].p + neighbor->prob->items[item_in2].p - neighbor->prob->items[item_out].p;
            neighbor->cap_left += weight_out - (weight_in1 + weight_in2);
            return neighbor;
        }
    } else if (move_type == 2 && num_in >= 2 && num_out > 0) { // 2-1 swap
        int item_out1 = in_items[rand() % num_in];
        int item_out2;
        do {
            item_out2 = in_items[rand() % num_in];
        } while (item_out2 == item_out1);
        int item_in = out_items[rand() % num_out];

        int weight_out1 = neighbor->prob->items[item_out1].v;
        int weight_out2 = neighbor->prob->items[item_out2].v;
        int weight_in = neighbor->prob->items[item_in].v;

        if (neighbor->cap_left + weight_out1 + weight_out2 >= weight_in) {
            neighbor->x[item_out1] = 0;
            neighbor->x[item_out2] = 0;
            neighbor->x[item_in] = 1;
            neighbor->objective += neighbor->prob->items[item_in].p - neighbor->prob->items[item_out1].p - neighbor->prob->items[item_out2].p;
            neighbor->cap_left += (weight_out1 + weight_out2) - weight_in;
            return neighbor;
        }
    }

    free_solution(neighbor);
    return NULL;
}

// Tabu Search Implementation
struct solution* tabu_search(struct solution* init_sln, int tabu_list_size, int max_iterations) {
    struct solution* current_sln = copy_from(init_sln);
    struct solution* best_sln = copy_from(init_sln);
    struct move* tabu_list = malloc(sizeof(struct move) * tabu_list_size);
    for (int i = 0; i < tabu_list_size; i++) {
        tabu_list[i].add_item = -1;
        tabu_list[i].remove_item = -1;
    }
    int tabu_list_index = 0;

    clock_t start_time = clock();

    printf("Tabu Search Progress (Tabu Size = %d, Max Iterations = %d):\n", tabu_list_size, max_iterations);

    for (int iter = 0; iter < max_iterations; iter++) {
        if ((double)(clock() - start_time) / CLOCKS_PER_SEC > MAX_TIME) {
            printf("Time limit exceeded, stopping Tabu Search.\n");
            break;
        }

        struct solution* best_neighbor = NULL;
        struct move best_move;
        best_move.add_item = -1;
        best_move.remove_item = -1;
        int found_neighbor = 0;

        for (int i = 0; i < NUM_OF_ITEMS; i++) {
            if (current_sln->x[i] > 0) { // Item i is in the current solution (candidate for removal)
                for (int j = 0; j < NUM_OF_ITEMS; j++) {
                    if (current_sln->x[j] == 0) { // Item j is not in the current solution (candidate for addition)
                        int weight_in = current_sln->prob->items[i].v;
                        int weight_out = current_sln->prob->items[j].v;

                        if (current_sln->cap_left + weight_in >= weight_out) {
                            struct solution* neighbor = copy_from(current_sln);
                            neighbor->x[i] = 0;
                            neighbor->x[j] = 1;
                            neighbor->objective += neighbor->prob->items[j].p - neighbor->prob->items[i].p;
                            neighbor->cap_left += weight_in - weight_out;

                            // Check if the move (j, i) is in the tabu list
                            int is_tabu = 0;
                            for (int k = 0; k < tabu_list_size; k++) {
                                if (tabu_list[k].add_item == j && tabu_list[k].remove_item == i) {
                                    is_tabu = 1;
                                    break;
                                }
                            }

                            // Aspiration criteria: If the neighbor is better than the best solution found so far, accept it even if it's tabu
                            if (!is_tabu || neighbor->objective > best_sln->objective) {
                                if (best_neighbor == NULL || neighbor->objective > best_neighbor->objective) {
                                    if (best_neighbor != NULL) {
                                        free_solution(best_neighbor);
                                    }
                                    best_neighbor = neighbor;
                                    best_move.add_item = j;
                                    best_move.remove_item = i;
                                    found_neighbor = 1;
                                } else {
                                    free_solution(neighbor);
                                }
                            } else {
                                free_solution(neighbor);
                            }
                        }
                    }
                }
            }
        }

        if (found_neighbor) {
            free_solution(current_sln);
            current_sln = best_neighbor;
            if (current_sln->objective > best_sln->objective) {
                free_solution(best_sln);
                best_sln = copy_from(current_sln);
                printf("Iter %d, Current Objective: %d, Best Objective: %d (TS)\n", iter, current_sln->objective, best_sln->objective);
            }

            // Update tabu list (add the reverse move)
            tabu_list[tabu_list_index].add_item = best_move.remove_item;
            tabu_list[tabu_list_index].remove_item = best_move.add_item;
            tabu_list_index = (tabu_list_index + 1) % tabu_list_size;
        } else {
            // If no improving non-tabu neighbor is found, you might want to consider other strategies
            // like accepting the best tabu neighbor or diversifying the search. For simplicity, we break here.
            printf("Iter %d, No improving non-tabu neighbor found (TS).\n", iter);
            break;
        }
    }

    free(tabu_list);
    free_solution(current_sln);
    return best_sln;
}

// Variable Neighborhood Search Implementation
struct solution* variable_neighborhood_search(struct solution* init_sln, int max_neighborhood, int max_iterations_vns) {
    struct solution* current_sln = copy_from(init_sln);
    struct solution* best_sln = copy_from(init_sln);
    clock_t start_time = clock();

    printf("Variable Neighborhood Search Progress (Max Neighborhood = %d, Max Iterations = %d):\n", max_neighborhood, max_iterations_vns);

    for (int iter = 0; iter < max_iterations_vns; iter++) {
        if ((double)(clock() - start_time) / CLOCKS_PER_SEC > MAX_TIME) {
            printf("Time limit exceeded, stopping VNS.\n");
            break;
        }

        for (int k = 1; k <= max_neighborhood; k++) {
            // Shaking: Generate a neighbor in the k-th neighborhood (k swaps)
            struct solution* neighbor = copy_from(current_sln);
            for (int s = 0; s < k; s++) {
                struct solution* temp_neighbor = generate_neighbor(neighbor, k);
                free_solution(neighbor);
                if (temp_neighbor == NULL) {
                    neighbor = copy_from(current_sln); // Reset if neighbor generation fails
                    break;
                }
                neighbor = temp_neighbor;
            }

            if (neighbor == NULL) continue; // Skip if shaking failed

            // Local Search (First Descent from the neighbor)
            struct solution* local_opt = local_search(neighbor);
            free_solution(neighbor);

            if (local_opt->objective > current_sln->objective) {
                free_solution(current_sln);
                current_sln = copy_from(local_opt);
                if (current_sln->objective > best_sln->objective) {
                    free_solution(best_sln);
                    best_sln = copy_from(current_sln);
                    printf("Iter %d, Neighborhood %d, Current Objective: %d, Best Objective: %d (VNS)\n", iter, k, current_sln->objective, best_sln->objective);
                }
                break; // Move to the next iteration with the improved solution
            }
            free_solution(local_opt);
        }
    }

    free_solution(current_sln);
    return best_sln;
}

int main() {
    srand(1548489535); // Seed the random number generator
    struct problem* prob = init_prob();
    struct solution* init_sln = greedy_heuristic(prob); // greedy heuristic
    print_sol(init_sln, "Initial Greedy Solution");

    struct solution* local_optimum_sln;
    local_optimum_sln = local_search(copy_from(init_sln)); // Run Local Search (First Descent)
    print_sol(local_optimum_sln, "First Descent Local Optima Solution");

    // Tabu Search
    struct solution* ts_optimum_sln;
    int tabu_list_size = 10;
    int max_iterations_ts = 50000;
    ts_optimum_sln = tabu_search(copy_from(init_sln), tabu_list_size, max_iterations_ts);
    print_sol(ts_optimum_sln, "Tabu Search Solution");

    // Variable Neighborhood Search
    struct solution* vns_optimum_sln;
    int max_neighborhood = 3;
    int max_iterations_vns = 50000;
    vns_optimum_sln = variable_neighborhood_search(copy_from(init_sln), max_neighborhood, max_iterations_vns);
    print_sol(vns_optimum_sln, "Variable Neighborhood Search Solution");

    // Free memory for all pointers
    free_problem(prob);
    free_solution(init_sln);
    free_solution(local_optimum_sln);
    free_solution(ts_optimum_sln);
    free_solution(vns_optimum_sln);
    return 0;
}
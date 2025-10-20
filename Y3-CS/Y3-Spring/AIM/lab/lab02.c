#include <stdio.h>
#include <math.h>
#include <stdlib.h>

// Global parameters
int MAX_NUM_OF_ITERS = 100;
int MAX_TIME = 30; // max 30 sec comp time.

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
    
    if (total_capacity_used <= sln->prob->C) {
        printf("Capacity check: PASSED\n\n");
    } else {
        printf("Capacity check: FAILED\n\n");
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
    struct solution* new_sln = copy_from(curt_sln); // Copy current solution to explore new solutions
    for (int i = 0; i < NUM_OF_ITEMS; i++) // Iterate over all items
    {
        if (curt_sln->x[i] > 0) // If item i is currently included in the solution
        {
            item1 = i;
            for (int j = 0; j < NUM_OF_ITEMS; j++) // Iterate over all items again for potential swaps
            {
                int v1 = curt_sln->prob->items[item1].v;
                int v2 = curt_sln->prob->items[j].v;
                // Check if item j is the same item and not in the solution, and swapping is feasible (enough capacity)
                if (i != j && curt_sln->x[j] == 0 && curt_sln->cap_left + v1 >= v2) {
                    item2 = j;
                    int delta = curt_sln->prob->items[item2].p - curt_sln->prob->items[item1].p;
                    
                    // If swapping item1 for item2 improves the objective
                    if (delta > 0) {
                        new_sln->x[item1] = 0; // Remove item1 from solution
                        new_sln->x[item2] = 1; // Add item2 to solution
                        new_sln->objective = curt_sln->objective + delta;  // Update objective value
                        new_sln->cap_left = new_sln->cap_left + curt_sln->prob->items[item1].v - curt_sln->prob->items[item2].v; // Update residual capacity
                        return new_sln; // Return the new solution after swapping
                    }
                }
            }
        }
    }
    return NULL; // Return NULL if no improvement was found
}

struct solution* local_search(struct solution* init_sln) {
    struct solution *nb_sln, *curt_sln;
    curt_sln = copy_from(init_sln);
    for (int k = 0; k < MAX_NUM_OF_ITERS; k++) {
        nb_sln = first_descent(curt_sln);  // best descent local search
        if (nb_sln != NULL) {
            free_solution(curt_sln);
            curt_sln = copy_from(nb_sln);
            free_solution(nb_sln);
        }
    }
    return curt_sln;
}

int main() {
    struct problem* prob = init_prob();
    struct solution* init_sln = greedy_heuristic(prob); // greedy heuristic
    print_sol(init_sln, "Initial Solution");
    
    struct solution* local_optimum_sln;
    local_optimum_sln = local_search(init_sln); // local search
    print_sol(local_optimum_sln, "First Descent Local Optima Solution");
    
    // Free memory for all pointers
    free_problem(prob);
    free_solution(init_sln);
    free_solution(local_optimum_sln);
    return 0;
}
import random
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation

# --- Set fixed random seed for reproducibility ---
FIXED_SEED = 42 # You can change this number
random.seed(FIXED_SEED)
np.random.seed(FIXED_SEED)

# --- Problem Definition ---
# Example 2-objective knapsack problem instance
# Number of items
NUM_ITEMS = 100
# Weights of items (still uniform for simplicity)
WEIGHTS = np.random.randint(1, 20, NUM_ITEMS)

# Value 1 and Value 2 of items follow a normal distribution
# Using mean=50, std_dev=20, and clipping to ensure positive values
MEAN_VALUE = 50
STD_DEV_VALUE = 20
VALUES1 = np.maximum(1, np.round(np.random.normal(MEAN_VALUE, STD_DEV_VALUE, NUM_ITEMS))).astype(int)
VALUES2 = np.maximum(1, np.round(np.random.normal(MEAN_VALUE, STD_DEV_VALUE, NUM_ITEMS))).astype(int)

# Knapsack capacity - Increased capacity to make feasible solutions appear earlier
CAPACITY = int(np.sum(WEIGHTS) * 0.6) # Increased example capacity to 60%

# --- NSGA-II Parameters ---
POPULATION_SIZE = 100
NUM_GENERATIONS = 200
MUTATION_RATE = 0.1
CROSSOVER_RATE = 0.9
TOURNAMENT_SIZE = 2 # Size of tournament for selection

# --- Helper Functions ---

def evaluate_solution(solution):
    """Evaluates a knapsack solution (binary vector).
       Returns total weight, total value 1, total value 2.
       Returns (inf, -inf, -inf) if infeasible.
    """
    total_weight = np.sum(WEIGHTS * solution)
    total_value1 = np.sum(VALUES1 * solution)
    total_value2 = np.sum(VALUES2 * solution)

    if total_weight > CAPACITY:
        return float('inf'), -float('inf'), -float('inf') # Penalize infeasible solutions
    else:
        return total_weight, total_value1, total_value2

def fast_non_dominated_sort(objective_values):
    """Performs fast non-dominated sorting.
       Returns a list of fronts, where each front is a list of indices of solutions.
    """
    num_solutions = len(objective_values)
    # dominance_count[i] = number of solutions that dominate solution i
    dominance_count = np.zeros(num_solutions, dtype=int)
    # dominated_solutions[i] = list of solutions dominated by solution i
    dominated_solutions = [[] for _ in range(num_solutions)]
    # front[k] = list of solutions in front k
    fronts = [[]]
    # rank[i] = rank of solution i
    rank = np.zeros(num_solutions, dtype=int)

    for p in range(num_solutions):
        for q in range(num_solutions):
            if p != q:
                # Check if p dominates q (for maximization problems)
                # p dominates q if p is better than q in at least one objective
                # and no worse than q in all other objectives.
                # Note: We are maximizing values, so 'better' means greater value.
                p_dominates_q = all(objective_values[p][k] >= objective_values[q][k] for k in range(len(objective_values[p]))) \
                                and any(objective_values[p][k] > objective_values[q][k] for k in range(len(objective_values[p])))

                if p_dominates_q:
                    dominated_solutions[p].append(q)
                elif all(objective_values[q][k] >= objective_values[p][k] for k in range(len(objective_values[q]))) \
                     and any(objective_values[q][k] > objective_values[p][k] for k in range(len(objective_values[q]))):
                    # q dominates p
                    dominance_count[p] += 1

        if dominance_count[p] == 0:
            # If no solution dominates p, it belongs to the first front
            rank[p] = 0
            fronts[0].append(p)

    # Corrected loop structure to avoid IndexError
    current_front_index = 0
    while current_front_index < len(fronts):
        current_front = fronts[current_front_index]
        if not current_front:
            # If the current front is empty, we have processed all solutions
            break

        next_front = []
        for p in current_front:
            for q in dominated_solutions[p]:
                dominance_count[q] -= 1
                if dominance_count[q] == 0:
                    rank[q] = current_front_index + 1
                    next_front.append(q)

        if next_front:
            fronts.append(next_front)

        current_front_index += 1


    return fronts, rank

def calculate_crowding_distance(front_indices, objective_values):
    """Calculates the crowding distance for solutions in a given front."""
    if not front_indices:
        return {} # Return empty dictionary if front is empty

    num_solutions_in_front = len(front_indices)
    num_objectives = len(objective_values[front_indices[0]])
    crowding_distances = {idx: 0 for idx in front_indices}

    # Handle single solution case
    if num_solutions_in_front <= 2:
        for idx in front_indices:
             crowding_distances[idx] = float('inf') # Boundary solutions get infinite distance
        return crowding_distances


    for obj_idx in range(num_objectives):
        # Sort front by the current objective value
        sorted_front_indices = sorted(front_indices, key=lambda idx: objective_values[idx][obj_idx])

        # Boundary solutions get infinite distance
        crowding_distances[sorted_front_indices[0]] = float('inf')
        crowding_distances[sorted_front_indices[-1]] = float('inf')

        # Calculate distance for intermediate solutions
        obj_min = objective_values[sorted_front_indices[0]][obj_idx]
        obj_max = objective_values[sorted_front_indices[-1]][obj_idx]
        obj_range = obj_max - obj_min

        for i in range(1, num_solutions_in_front - 1):
            # Add check for zero or near-zero range to avoid division warning
            if obj_range > 1e-9: # Use a small epsilon to handle floating point
                 crowding_distances[sorted_front_indices[i]] += \
                    (objective_values[sorted_front_indices[i+1]][obj_idx] - objective_values[sorted_front_indices[i-1]][obj_idx]) / obj_range
            # If obj_range is zero or near-zero, the contribution to crowding distance is 0,
            # so we don't need to add anything.


    return crowding_distances

def tournament_selection(population, objective_values, rank, crowding_distances, tournament_size):
    """Performs tournament selection based on rank and crowding distance."""
    pop_size = len(population)
    selected_indices = []
    for _ in range(pop_size):
        # Randomly select individuals for the tournament
        tournament_indices = random.sample(range(pop_size), tournament_size)
        best_index = tournament_indices[0]
        for current_index in tournament_indices[1:]:
            # Compare using crowded comparison operator
            # Prefer lower rank, then higher crowding distance
            if rank[current_index] < rank[best_index]:
                best_index = current_index
            elif rank[current_index] == rank[best_index]:
                if crowding_distances[current_index] > crowding_distances[best_index]:
                    best_index = current_index
        selected_indices.append(best_index)
    return [population[i] for i in selected_indices]

def crossover(parent1, parent2):
    """Performs uniform crossover."""
    child = np.copy(parent1)
    for i in range(len(child)):
        if random.random() < 0.5:
            child[i] = parent2[i]
    return child

def mutate(solution, mutation_rate):
    """Performs bit-flip mutation."""
    mutated_solution = np.copy(solution)
    for i in range(len(mutated_solution)):
        if random.random() < mutation_rate:
            mutated_solution[i] = 1 - mutated_solution[i] # Flip the bit
    return mutated_solution

def initialize_population(pop_size, num_items):
    """Initializes a random binary population."""
    return np.random.randint(0, 2, size=(pop_size, num_items))


# --- NSGA-II Algorithm ---

def nsga2(num_generations, pop_size, num_items):
    """Runs the NSGA-II algorithm and stores historical objective values."""

    # 1. Initialization
    population = initialize_population(pop_size, num_items)

    # List to store objective values at each generation for animation
    history_objective_values = []

    # 2. Evolution
    for gen in range(num_generations):
        # print(f"Generation {gen+1}/{num_generations}") # Optional: print progress

        # Evaluate current population
        current_objective_values = [evaluate_solution(sol)[1:] for sol in population]

        # Store objective values for this generation (only feasible ones for plotting)
        feasible_objectives_current = [obj for obj in current_objective_values if obj[0] != -float('inf')]
        history_objective_values.append(feasible_objectives_current)


        # Create offspring population
        offspring_population = []
        # Select parents using tournament selection
        # Need to evaluate objectives, sort, and calculate crowding distance for the current population
        current_objective_values_for_selection = [evaluate_solution(sol)[1:] for sol in population] # Re-evaluate for selection
        current_fronts, current_rank = fast_non_dominated_sort(current_objective_values_for_selection)
        # Need to get indices of all solutions in all fronts for crowding distance calculation
        all_front_indices = [idx for front in current_fronts for idx in front]
        current_crowding_distances = calculate_crowding_distance(all_front_indices, current_objective_values_for_selection)


        parents = tournament_selection(population, current_objective_values_for_selection, current_rank, current_crowding_distances, TOURNAMENT_SIZE)

        # Generate offspring using crossover and mutation
        for i in range(0, pop_size, 2):
            parent1 = parents[i]
            parent2 = parents[i+1] # Assuming pop_size is even

            if random.random() < CROSSOVER_RATE:
                child1 = crossover(parent1, parent2)
                child2 = crossover(parent2, parent1) # Another child from reversed parents
            else:
                child1 = np.copy(parent1)
                child2 = np.copy(parent2)

            offspring1 = mutate(child1, MUTATION_RATE)
            offspring2 = mutate(child2, MUTATION_RATE)

            offspring_population.extend([offspring1, offspring2])

        # Combine parent and offspring populations
        combined_population = population + offspring_population
        combined_objective_values = [evaluate_solution(sol)[1:] for sol in combined_population] # Corrected: use combined_population

        # Perform fast non-dominated sorting on the combined population
        fronts, rank = fast_non_dominated_sort(combined_objective_values)

        new_population = []
        current_pop_size = 0

        # Select individuals for the next generation based on fronts and crowding distance
        for front_indices in fronts:
            if current_pop_size + len(front_indices) <= pop_size:
                # If the whole front fits, add it to the new population
                new_population.extend([combined_population[i] for i in front_indices])
                current_pop_size += len(front_indices)
            else:
                # If the front is too large, select individuals based on crowding distance
                remaining_space = pop_size - current_pop_size
                # Calculate crowding distance only for the current front being considered for truncation
                crowding_distances = calculate_crowding_distance(front_indices, combined_objective_values)
                # Sort by crowding distance in descending order and take the top 'remaining_space' individuals
                sorted_front_indices = sorted(front_indices, key=lambda idx: crowding_distances.get(idx, -float('inf')), reverse=True) # Use .get for safety
                new_population.extend([combined_population[i] for i in sorted_front_indices[:remaining_space]])
                current_pop_size += remaining_space
                break # Population size reached

        population = new_population # Update population for the next generation

    # After all generations, get the objective values of the final population
    final_objective_values = [evaluate_solution(sol)[1:] for sol in population]

    # Return the final population, its objective values, and the history
    return population, final_objective_values, history_objective_values

# --- Animation Function ---

def create_animation(history_objective_values):
    """Creates an animation of the Pareto front evolution."""

    fig, ax = plt.subplots(figsize=(10, 6))
    ax.set_title('NSGA-II Pareto Front Evolution') # Main title without generation number
    ax.set_xlabel('Total Value 1')
    ax.set_ylabel('Total Value 2')
    ax.grid(True)

    # Initialize plot with empty data
    scatter = ax.scatter([], [], label='Pareto Front Approximation')
    ax.legend()

    # --- Explicitly calculate overall min/max values across all generations ---
    all_values1 = []
    all_values2 = []
    for gen_data in history_objective_values:
        for obj in gen_data:
            if obj[0] != -float('inf'): # Only consider feasible solutions
                all_values1.append(obj[0])
                all_values2.append(obj[1])

    if all_values1 and all_values2:
        # Calculate max values
        max_v1 = max(all_values1)
        max_v2 = max(all_values2)

        # Add a slightly larger buffer to the upper limits
        buffer_v1 = (max_v1) * 0.15 # Increased buffer relative to max
        buffer_v2 = (max_v2) * 0.15 # Increased buffer relative to max

        # Set limits starting from 0
        ax.set_xlim(0, max_v1 + buffer_v1)
        ax.set_ylim(0, max_v2 + buffer_v2)
    else:
         # Default limits if no feasible solutions found
         ax.set_xlim(0, 100)
         ax.set_ylim(0, 100)

    # Add a text annotation for the generation number
    # Position it in the upper left corner (adjust x and y coordinates as needed)
    # transform=ax.transAxes means the coordinates are relative to the axes (0,0 is bottom-left, 1,1 is top-right)
    gen_text = ax.text(0.05, 0.95, '', transform=ax.transAxes, ha='left', va='top', fontsize=10)


    def update(frame):
        """Updates the scatter plot and generation text annotation for each frame (generation).
           'frame' corresponds to the generation number (0-indexed).
        """
        current_objectives = history_objective_values[frame]
        # Filter out infeasible solutions for plotting (already done when storing history)
        feasible_objectives = current_objectives # History only stores feasible ones

        value1_results = [obj[0] for obj in feasible_objectives]
        value2_results = [obj[1] for obj in feasible_objectives]

        # Update the data in the scatter plot
        scatter.set_offsets(np.c_[value1_results, value2_results])

        # Update the generation number in the text annotation
        # Add 1 to frame to display 1-indexed generation number
        gen_text.set_text(f'Generation: {frame + 1}')

        # Return the updated artists (scatter plot and text annotation)
        return scatter, gen_text

    # Create the animation
    # frames is the total number of frames (number of generations stored in history)
    # interval is the delay between frames in milliseconds
    ani = animation.FuncAnimation(fig, update, frames=len(history_objective_values), blit=True, interval=50)

    # To save the animation, uncomment the following line and install imagemagick or ffmpeg
    # ani.save('nsga2_evolution.gif', writer='imagemagick', fps=20)

    plt.show()

# --- Run the algorithm and plot results ---

if __name__ == "__main__":
    print("Running NSGA-II for 2-Objective Knapsack Problem...")
    final_population, final_objective_values, history_objective_values = nsga2(
        NUM_GENERATIONS,
        POPULATION_SIZE,
        NUM_ITEMS
    )

    print("Algorithm finished. Creating animation...")
    create_animation(history_objective_values)

    # You can still plot the final Pareto front separately if you like
    # feasible_objectives = [obj for obj in final_objective_values if obj[0] != -float('inf')]
    # value1_results = [obj[0] for obj in feasible_objectives]
    # value2_results = [obj[1] for obj in feasible_objectives]
    # plt.figure(figsize=(10, 6))
    # plt.scatter(value1_results, value2_results, label='Final Pareto Front Approximation')
    # plt.title('NSGA-II Final Results for 2-Objective Knapsack Problem')
    # plt.xlabel('Total Value 1')
    # plt.ylabel('Total Value 2')
    # plt.grid(True)
    # plt.legend()
    # plt.show()

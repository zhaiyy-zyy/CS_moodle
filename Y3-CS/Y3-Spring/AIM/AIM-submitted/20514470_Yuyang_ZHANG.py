import json
import random
import time
from datetime import datetime
from functools import lru_cache

# NOTE: Although multiple heuristic algorithms (Next-Fit, First-Fit, Best-Fit, etc.) are implemented above,
# this execution only uses the Genetic Algorithm (optimized_genetic_packing) as the solver for all instances.
# The alternative heuristics are not invoked in this run but are kept for comparison.

# Start measuring the execution time
start_time = time.time()

# Function to read bin packing instances from a JSON file
def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file) # Read the JSON file and load its content
    return instances

# Next-Fit algorithm for bin packing: put items into bins until the current box is full, then use a new box
def next_fit(items, capacity):
    bins = [] # Store a list of all bins
    current_bin = [] # Current bin

    # Process each item
    for item in items:
        if sum(current_bin) + item <= capacity: # If the current item can be put into the current bin
            current_bin.append(item)
        else:
            bins.append(current_bin) # The current bin is full, add it to the bin list and create a new bin
            current_bin = [item]
    bins.append(current_bin) # Add the last bin

    return bins

# First-Fit algorithm for bin packing: put each item into the first bin that can hold it
def first_fit(items, capacity):
    bins = [] # Store a list of all bins
    for item in items:
        placed = False # Mark whether the item has found a suitable bin
        for bin in bins:
            if sum(bin) + item <= capacity: # If the item can be put into the current bin
                bin.append(item)
                placed = True
                break
        if placed is False: # If the item doesn't find a suitable bin, create a new bin
            bins.append([item])

    return bins

# First-Fit Decreasing algorithm: sort the items by size first, then apply the First-Fit algorithm
def first_fit_decreasing(items, capacity):
    # Sort the items from largest to smallest first
    sorted_items = sorted(items, reverse=True)
    return first_fit(sorted_items, capacity)

# Worst-Fit algorithm: put items into the bin with the most free space
def worst_fit(items, capacity):
    bins = [] # Store a list of all bins
    
    # Process each item
    for item in items:
        max_space = -9999  # Initialize the maximum remaining space to a very small value
        selected_bin = None  # Selected bins
        
        # Search the bin with the most remaining space
        for bin in bins:
            space = capacity - sum(bin)
            if space >= item and space > max_space:  # If there is enough space and it is the largest
                selected_bin = bin
                max_space = space
        
        # If finding a suitable bin, put the item in it; otherwise, open a new box.
        if selected_bin is not None:
            selected_bin.append(item)
        else:
            bins.append([item])
    
    return bins

# Best-Fit algorithm: put items into the bin with the least amount of free space
def best_fit(items, capacity):
    bins = []  # Store a list of all bins
    
    # Process each items
    for item in items:
        best_bin = None
        min_space = capacity  # Initialize the minimum remaining space to the bin capacity

        # Find the bin with the smallest remaining space
        for bin in bins:
            space = capacity - sum(bin)
            if space >= item and space < min_space:  # If there is enough space and the minimum
                best_bin = bin
                min_space = space
        
        # If a suitable bin is found, put the item in this bin; otherwise, create a new bin
        if best_bin is not None:
            best_bin.append(item)
        else:
            bins.append([item])
    
    return bins 


# Create individuals for individual creation in genetic algorithm
def create_individual(items, greedy_prob=0.3):
    """create individual: 50% probability using greedy strategy, 50% random arrangement"""
    if random.random() < greedy_prob:
        # If using the greedy strategy, sort item size in descending order
        return sorted(range(len(items)), key=lambda i: -items[i])
    else: 
        return random.sample(range(len(items)), len(items)) # Otherwise, return a random order of items

# Create the initial population, which is population initialization in genetic algorithm
def create_population(pop_size, items):
    """initialize population"""
    return [create_individual(items) for _ in range(pop_size)]

# Use cache to optimize the decoding process,which is decoding operation in genetic algorithm
@lru_cache(maxsize=10000)
def cached_decode(chromosome_key, items_tuple, capacity):
    """decode"""
    chromosome = list(chromosome_key)
    items = list(items_tuple)
    bins = []
    
    # Process each gene (i.e. item)
    for gene in chromosome:
        item = items[gene]
        best_bin = None
        min_space = float('inf')
        
        # look for the best bin
        for index, current_bin in enumerate(bins):
            space = capacity - (sum(current_bin) + item)
            if space >= 0 and space < min_space:
                best_bin = index
                min_space = space
        
        # If a suitable bin is found, put the item into it; otherwise, create a new bin
        if best_bin is not None:
            bins[best_bin].append(item)
        else:
            bins.append([item])
    
    return tuple(tuple(bin) for bin in bins)

# Fitness cache system in genetic algorithm
class FitnessCache:
    """fitness cache system to avoid repeated calculations and improve the efficiency of genetic algorithm"""
    def __init__(self, items, capacity):
        self.items = tuple(items) # List of items to be packed, converted to immutable tuples
        self.capacity = capacity  # The capacity of each bin
        self.cache = {}  # Cache dictionary: key is chromosome (tuple), value is its fitness value
        self.hits = 0    # Cache hit count
        self.misses = 0  # Cache miss count
    
    def get_fitness(self, chromosome):
        """retrieve fitness. If it is already in the cache, return it directly. Otherwise, calculate and cache it."""
        chromosome_key = tuple(chromosome)  # Convert list to tuple
        if chromosome_key in self.cache:
            self.hits += 1
            return self.cache[chromosome_key]
        
        # calculate and cache the new value
        self.misses += 1

        # Use cached_decode function to "decode" chromosomes into bin packaging scheme
        bins = cached_decode(chromosome_key, self.items, self.capacity)
        bin_count = len(bins)
        total_used = sum(sum(bin) for bin in bins)
        utilization = total_used / (bin_count * self.capacity)
        #Fitness function = fewer bins, better + higher utilization, better
        fitness = (1 / bin_count) + 0.1 * utilization
        
        self.cache[chromosome_key] = fitness # Cache the current result
        return fitness

def crossover(parent1, parent2, crossover_rate=0.85):
    """Use Order Crossover(OX) to perform crossover operation of genetic algorithm"""

   # If no crossover is triggered, both parents are copied directly
    if random.random() > crossover_rate:
        return parent1.copy(), parent2.copy()
    
    size = len(parent1)
    # Randomly select a crossover interval [start, end)
    start, end = sorted(random.sample(range(size), 2))
    
    # Execute OX operation
    def order_crossover (child, other_parent):
        segment = set(child[start:end]) # Get the middle gene retained in the current offspring
        # Select the elements that are not in the segment from another parent
        remaining = [g for g in other_parent if g not in segment]
        # Construct a new child: front section + middle section + back section (fill the remaining elements in the order in other_parent)
        return remaining[:start] + child[start:end] + remaining[start:]
    
    # Generate two children: embed the middle part of parent1, and the rest comes from parent2
    child1 = order_crossover (parent1.copy(), parent2)

    # Generate two children: embed the middle part of parent2, and the rest comes from parent1
    child2 = order_crossover (parent2.copy(), parent1)
    return child1, child2

# Mutation operation in genetic algorithm
def mutation(chromosome, mutation_rate=0.15):
    """Mutation"""
    if random.random() < mutation_rate:
        index1, index2 = random.sample(range(len(chromosome)), 2)
        chromosome[index1], chromosome[index2] = chromosome[index2], chromosome[index1]
    return chromosome

# Genetic algorithm optimized packing function
def optimized_genetic_packing(
    items, 
    capacity, 
    pop_size=100, 
    generations=6,
    elite_ratio=0.10, # Elite ratio (the individuals with the highest fitness are directly retained)
    tournament_size=3
):
    """Use Genetic Algorithms to optimize the order of item packaging to increase bin utilization and reduce the number of items used"""
    # FitnessCache is a caching system used to avoid repeated fitness calculations for the same chromosome.
    cache = FitnessCache(items, capacity)
    population = create_population(pop_size, items) # Population is a collection of chromosomes. Initialize the population (each individual is the order of items)
    best_solution = None
    best_fitness = -float('inf')
    elite_size = max(2, int(pop_size * elite_ratio)) # Number of elites, at least two elite individuals are retained
    
    # Perform multiple generations of evolution
    for generations in range(generations):
        # incremental fitness calculation 
        new_individuals = [individual for individual in population if tuple(individual) not in cache.cache]
        for individual in new_individuals:
            cache.get_fitness(individual)
        
        # get fintnesses 
        fitnesses = [cache.get_fitness(individual) for individual in population]
        
        # Elite selection: Select the top elite_size individuals with the highest fitness from the current population
        elites = sorted(
            zip(population, fitnesses),  # Bind individuals to their fitness
            key=lambda x: x[1],  # Sort by fitness (x[1])
            reverse=True  # Sort in descending order, with the highest fitness at the front
        )[:elite_size]  # Extract the first elite_size individuals

        # Unpack elites, keep only the individual itself (excluding fitness), and make a deep copy to prevent genetic modification from contaminating the original data
        # elites is a list of individuals (chromosomes) with the highest fitness.
        elites = [individual.copy() for individual, _ in elites]
        

        # best_fitness represents the highest fitness value encountered in all generations
        # Get the optimal fitness value in the current generation
        current_best = max(fitnesses)

        # If the optimal fitness in the current generation is better than the historical optimal value, update the global optimal solution
        if current_best > best_fitness:
            best_idx = fitnesses.index(current_best)  # Find the index of the best individual
            best_solution = population[best_idx].copy()  # Copy and save the individual as the current optimal solution
            best_fitness = current_best    # Update the global optimal fitness
        
        # generate the next generation
        # Initialize the new generation of population, first put in the best individuals (elites) of the previous generation
        new_population = elites.copy()
        # When the number of individuals in the new generation has not reached the target population size pop_size, new individuals are continuously generated to fill the population.
        while len(new_population) < pop_size:
            # Randomly draw tournament_size * 2 individuals from the current population as candidates
            parents = random.sample(population, tournament_size*2)
            parent1 = max(parents[:tournament_size], key=lambda x: cache.get_fitness(x))
            parent2 = max(parents[tournament_size:], key=lambda x: cache.get_fitness(x))
            
            # cross and mutation
            # Perform crossover and mutation on the parent generation to generate two offspring
            child1, child2 = crossover(parent1, parent2) # Genetic recombination
            child1 = mutation(child1)  # Random perturbations
            child2 = mutation(child2)
            
            # Join the new population
            new_population.extend([child1, child2])
        
        population = new_population[:pop_size]  # Keep the population size fixed to prevent generating too many individuals
        
    # decode
    final_bins = cached_decode(tuple(best_solution), tuple(items), capacity)
    return [list(bin) for bin in final_bins]


# Random search to find the best solution
def random_search_fit(items, capacity, fit_fun, iterations=1):
    best_solution = None
    min_bins = float('inf')

    for _ in range(iterations):
        random.shuffle(items)
        current_solution = fit_fun(items, capacity)
        #if the number of bins used in the current solution is less than the known minimum number of bins, update best min_bins
        current_bin_count = len(current_solution)
        if current_bin_count < min_bins:
            best_solution = current_solution
            min_bins = current_bin_count
    
    return best_solution

if __name__ =="__main__":
    # Example usage 
    random.seed(0)
    json_file_path = 'CW_ins.json'
    output_filename = '20514470_Yuyang_ZHANG.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {}
    output_json['date'] = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    output_json['time'] = 0
    output_json['res'] = []


    #
    #######################################################################
    for ins in instances:
        start_time_sol = time.time()
        
        #find solutions 
        fit_algorithm = optimized_genetic_packing
        solution = random_search_fit(ins['items'], ins['capacity'], fit_algorithm)
        # solution = random_search_fit(ins['items'], ins['capacity'], next_fit)
        
        #save & print output
        output_json['res'].append({})
        output_json['res'] [-1]['name'] = ins['name']
        output_json['res'] [-1]['capacity'] = ins['capacity']
        output_json['res'] [-1]['solution'] = solution
        bin_used = len(solution)

        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")
        total_bins += len(solution)
    ########################################################################


    total_time = time.time() - start_time
    
    with open(output_filename, 'w+') as f:
        output_json['time'] = total_time
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")

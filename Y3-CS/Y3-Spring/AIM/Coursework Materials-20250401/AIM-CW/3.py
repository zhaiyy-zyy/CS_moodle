import json
import random
import time
from datetime import datetime
from functools import lru_cache

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
def cached_decode(chromosome_hash, items_tuple, capacity):
    """decode"""
    chromosome = list(chromosome_hash)
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
    """fitness cache system"""
    def __init__(self, items, capacity):
        self.items = tuple(items)
        self.capacity = capacity
        self.cache = {}
        self.hits = 0
        self.misses = 0
    
    def get_fitness(self, chromosome):
        """retrieve fitness"""
        chromosome_key = tuple(chromosome)
        if chromosome_key in self.cache:
            self.hits += 1
            return self.cache[chromosome_key]
        
        # calculate and cache the new value
        self.misses += 1
        bins = cached_decode(chromosome_key, self.items, self.capacity)
        bin_count = len(bins)
        total_used = sum(sum(bin) for bin in bins)
        utilization = total_used / (bin_count * self.capacity)
        fitness = (1 / bin_count) + 0.1 * utilization
        
        self.cache[chromosome_key] = fitness
        return fitness
    
    # def hit_rate(self):
    #     """calculate cache hit rate"""
    #     total = self.hits + self.misses
    #     return self.hits / total if total > 0 else 0

def crossover(parent1, parent2, crossover_rate=0.85):
    """OX"""
    if random.random() > crossover_rate:
        return parent1.copy(), parent2.copy()
    
    size = len(parent1)
    start, end = sorted(random.sample(range(size), 2))
    
    def _ox(child, other_parent):
        segment = set(child[start:end])
        remaining = [g for g in other_parent if g not in segment]
        return remaining[:start] + child[start:end] + remaining[start:]
    
    child1 = _ox(parent1.copy(), parent2)
    child2 = _ox(parent2.copy(), parent1)
    return child1, child2

# Mutation operation in genetic algorithm
def mutation(chromosome, mutation_rate=0.15):
    """Mutation"""
    if random.random() < mutation_rate:
        idx1, idx2 = random.sample(range(len(chromosome)), 2)
        chromosome[idx1], chromosome[idx2] = chromosome[idx2], chromosome[idx1]
    return chromosome

# Genetic algorithm optimized packing function
def optimized_genetic_packing(
    items, 
    capacity, 
    pop_size=100, 
    generations=6,
    elite_ratio=0.10,
    tournament_size=3
):
    """Genetic Algorithm"""
    cache = FitnessCache(items, capacity)
    population = create_population(pop_size, items)
    best_solution = None
    best_fitness = -float('inf')
    elite_size = max(2, int(pop_size * elite_ratio))
    
    for gen in range(generations):
        # incremental fitness calculation 
        new_individuals = [ind for ind in population if tuple(ind) not in cache.cache]
        for ind in new_individuals:
            cache.get_fitness(ind)
        
        # get fintnesses 
        fitnesses = [cache.get_fitness(ind) for ind in population]
        
        # Select elite individuals
        elites = sorted(
            zip(population, fitnesses),
            key=lambda x: x[1],
            reverse=True
        )[:elite_size]
        elites = [ind.copy() for ind, _ in elites]
        

        current_best = max(fitnesses)
        if current_best > best_fitness:
            best_idx = fitnesses.index(current_best)
            best_solution = population[best_idx].copy()
            best_fitness = current_best
        
        # generate the next generation
        new_population = elites.copy()
        while len(new_population) < pop_size:
            # 
            parents = random.sample(population, tournament_size*2)
            parent1 = max(parents[:tournament_size], key=lambda x: cache.get_fitness(x))
            parent2 = max(parents[tournament_size:], key=lambda x: cache.get_fitness(x))
            
            # cross and mutation
            child1, child2 = crossover(parent1, parent2)
            child1 = mutation(child1)
            child2 = mutation(child2)
            
            new_population.extend([child1, child2])
        
        population = new_population[:pop_size]
        

        # if (gen + 1) % 5 == 0 or gen == 0:
        #     current_bins = cached_decode(tuple(best_solution), tuple(items), capacity)
        #     avg_fitness = sum(fitnesses)/len(fitnesses)
        #     print(f"Gen {gen + 1:3d} | bin: {len(current_bins)} | fitness: {avg_fitness:.3f}")
    
    # decode
    final_bins = cached_decode(tuple(best_solution), tuple(items), capacity)
    return [list(bin) for bin in final_bins]

if __name__ =="__main__":
    # Example usage 
    random.seed(0)
    json_file_path = 'CW_ins.json'
    output_filename = '20514470_yuyang_zhang.json'
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
        
        
        solution = optimized_genetic_packing(
        items=ins['items'],
    capacity=ins['capacity'],
    pop_size=100,          # 可根据需要调整
    generations=6,
    elite_ratio=0.10,
    tournament_size=3
)
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

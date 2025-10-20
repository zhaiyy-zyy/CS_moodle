import json
import random
import time
from datetime import datetime
from functools import lru_cache

start_time = time.time()

# Function to read bin packing instances from a JSON file
def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

# Best-Fit algorithm: put items into the bin with the least amount of free space
def best_fit(items, capacity):
    bins = []
    for item in items:
        best_bin = None
        min_space = capacity
        for bin in bins:
            space = capacity - sum(bin)
            if space >= item and space < min_space:
                best_bin = bin
                min_space = space
        if best_bin is not None:
            best_bin.append(item)
        else:
            bins.append([item])
    return bins

# Create individuals for genetic algorithm
def create_individual(items, greedy_prob=0.3):
    if random.random() < greedy_prob:
        return sorted(range(len(items)), key=lambda i: -items[i])
    else:
        return random.sample(range(len(items)), len(items))

# Create population
def create_population(pop_size, items):
    return [create_individual(items) for _ in range(pop_size)]

# Cached decode
@lru_cache(maxsize=10000)
def cached_decode(chromosome_hash, items_tuple, capacity):
    chromosome = list(chromosome_hash)
    items = list(items_tuple)
    bins = []
    for gene in chromosome:
        item = items[gene]
        best_bin = None
        min_space = float('inf')
        for idx, bin in enumerate(bins):
            space = capacity - (sum(bin) + item)
            if space >= 0 and space < min_space:
                best_bin = idx
                min_space = space
        if best_bin is not None:
            bins[best_bin].append(item)
        else:
            bins.append([item])
    return tuple(tuple(bin) for bin in bins)

# Fitness cache
class FitnessCache:
    def __init__(self, items, capacity):
        self.items = tuple(items)
        self.capacity = capacity
        self.cache = {}
    
    def get_fitness(self, chromosome):
        chrom_hash = tuple(chromosome)
        if chrom_hash in self.cache:
            return self.cache[chrom_hash]
        bins = cached_decode(chrom_hash, self.items, self.capacity)
        bin_count = len(bins)
        total_used = sum(sum(b) for b in bins)
        utilization = total_used / (bin_count * self.capacity)
        fitness = (1 / bin_count) + 0.1 * utilization
        self.cache[chrom_hash] = fitness
        return fitness

# Crossover
def crossover(parent1, parent2, crossover_rate=0.85):
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

# Mutation
def mutation(chromosome, mutation_rate=0.15):
    if random.random() < mutation_rate:
        idx1, idx2 = random.sample(range(len(chromosome)), 2)
        chromosome[idx1], chromosome[idx2] = chromosome[idx2], chromosome[idx1]
    return chromosome

# Genetic Algorithm
def optimized_genetic_packing(
    items, 
    capacity, 
    pop_size=100, 
    generations=6,
    elite_ratio=0.10,
    tournament_size=3
):
    cache = FitnessCache(items, capacity)
    population = create_population(pop_size, items)
    best_solution = None
    best_fitness = -float('inf')
    elite_size = max(2, int(pop_size * elite_ratio))
    
    for gen in range(generations):
        new_individuals = [ind for ind in population if tuple(ind) not in cache.cache]
        for ind in new_individuals:
            cache.get_fitness(ind)
        
        fitnesses = [cache.get_fitness(ind) for ind in population]
        
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
        
        new_population = elites.copy()
        while len(new_population) < pop_size:
            parents = random.sample(population, tournament_size * 2)
            parent1 = max(parents[:tournament_size], key=lambda x: cache.get_fitness(x))
            parent2 = max(parents[tournament_size:], key=lambda x: cache.get_fitness(x))
            child1, child2 = crossover(parent1, parent2)
            child1 = mutation(child1)
            child2 = mutation(child2)
            new_population.extend([child1, child2])
        
        population = new_population[:pop_size]
    
    final_bins = cached_decode(tuple(best_solution), tuple(items), capacity)
    return [list(bin) for bin in final_bins]

# Random search between best-fit and genetic algorithm
def random_search_fit(items, capacity, iterations=1):
    best_solution = None
    min_bins = float('inf')
    
    for _ in range(iterations):
        random.shuffle(items)
        if random.random() < 0.5:
            current_solution = best_fit(items, capacity)
        else:
            current_solution = optimized_genetic_packing(items, capacity)
        current_bin_count = len(current_solution)
        if current_bin_count < min_bins:
            best_solution = current_solution
            min_bins = current_bin_count
    
    return best_solution

if __name__ == "__main__":
    random.seed(0)
    json_file_path = 'CW_ins.json'
    output_filename = '20514470_yuyang_zhang.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {}
    output_json['date'] = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    output_json['time'] = 0
    output_json['res'] = []

    for ins in instances:
        start_time_sol = time.time()
        solution = random_search_fit(ins['items'], ins['capacity'])
        output_json['res'].append({
            'name': ins['name'],
            'capacity': ins['capacity'],
            'solution': solution
        })
        bin_used = len(solution)
        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time() - start_time_sol:.4f}s)")
        total_bins += bin_used

    total_time = time.time() - start_time
    output_json['time'] = total_time

    with open(output_filename, 'w+') as f:
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
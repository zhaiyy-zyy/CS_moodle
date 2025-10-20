import json
import random
import time
from datetime import datetime
from functools import lru_cache

start_time = time.time()

def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def next_fit(items, capacity):
    bins = []
    current_bin = []

    for item in items:
        if sum(current_bin) + item <= capacity:
            current_bin.append(item)
        else:
            bins.append(current_bin)
            current_bin = [item]
    bins.append(current_bin) # Add the last bin

    return bins

def first_fit(items, capacity):
    bins = []
    for item in items:
        fit = False
        for bin in bins:
            if sum(bin) + item <= capacity:
                bin.append(item)
                fit = True
                break
        if fit is False:
            bins.append([item])

    return bins

def first_fit_decreasing(items, capacity):
    # 先对物品从大到小排序
    sorted_items = sorted(items, reverse=True)
    return first_fit(sorted_items, capacity)

def worst_fit(items, capacity):
    bins = []
    
    for item in items:
        max_space = -9999
        selected_bin = None
        
        # 寻找剩余空间最大的箱子
        for bin in bins:
            space = capacity - sum(bin)
            if space >= item and space > max_space:
                selected_bin = bin
                max_space = space
        
        # 如果找到合适的箱子，放入物品；否则开新箱子
        if selected_bin is not None:
            selected_bin.append(item)
        else:
            bins.append([item])
    
    return bins

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



def create_individual(items, greedy_prob=0.3):
    """create individual: 50% probability using greedy strategy, 50% random arrangement"""
    if random.random() < greedy_prob:
        # sort in descending order
        return sorted(range(len(items)), key=lambda i: -items[i])
    else:
        return random.sample(range(len(items)), len(items))

def create_population(pop_size, items):
    """initialize population"""
    return [create_individual(items) for _ in range(pop_size)]

@lru_cache(maxsize=10000)
def cached_decode(chromosome_hash, items_tuple, capacity):
    """decode"""
    chromosome = list(chromosome_hash)
    items = list(items_tuple)
    bins = []
    
    for gene in chromosome:
        item = items[gene]
        best_bin = None
        min_space = float('inf')
        
        # look for the best box
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
        chrom_hash = tuple(chromosome)
        if chrom_hash in self.cache:
            self.hits += 1
            return self.cache[chrom_hash]
        
        # calculate and cache the new value
        self.misses += 1
        bins = cached_decode(chrom_hash, self.items, self.capacity)
        bin_count = len(bins)
        total_used = sum(sum(b) for b in bins)
        utilization = total_used / (bin_count * self.capacity)
        fitness = (1 / bin_count) + 0.1 * utilization
        
        self.cache[chrom_hash] = fitness
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

def mutation(chromosome, mutation_rate=0.15):
    """Mutation"""
    if random.random() < mutation_rate:
        idx1, idx2 = random.sample(range(len(chromosome)), 2)
        chromosome[idx1], chromosome[idx2] = chromosome[idx2], chromosome[idx1]
    return chromosome

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
    output_filename = '123456_xinan_chen.json'
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

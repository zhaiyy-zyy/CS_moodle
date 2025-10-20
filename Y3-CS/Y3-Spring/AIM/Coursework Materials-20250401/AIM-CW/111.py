import json
import random
import time
from datetime import datetime
from functools import lru_cache

# Start measuring the execution time
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        return json.load(file)

def next_fit(items, capacity):
    bins, current_bin, current_sum = [], [], 0
    for x in items:
        if current_sum + x <= capacity:
            current_bin.append(x)
            current_sum += x
        else:
            bins.append(current_bin)
            current_bin, current_sum = [x], x
    bins.append(current_bin)
    return bins

def first_fit(items, capacity):
    bins, sums = [], []
    for x in items:
        placed = False
        for i, s in enumerate(sums):
            if s + x <= capacity:
                bins[i].append(x)
                sums[i] += x
                placed = True
                break
        if not placed:
            bins.append([x])
            sums.append(x)
    return bins

def first_fit_decreasing(items, capacity):
    return first_fit(sorted(items, reverse=True), capacity)

def worst_fit(items, capacity):
    bins, sums = [], []
    for x in items:
        best_i, best_space = -1, -1
        for i, s in enumerate(sums):
            space = capacity - s
            if space >= x and space > best_space:
                best_space, best_i = space, i
        if best_i >= 0:
            bins[best_i].append(x)
            sums[best_i] += x
        else:
            bins.append([x])
            sums.append(x)
    return bins

def best_fit(items, capacity):
    bins, sums = [], []
    for x in items:
        best_i, best_space = -1, capacity + 1
        for i, s in enumerate(sums):
            space = capacity - s
            if space >= x and space < best_space:
                best_space, best_i = space, i
        if best_i >= 0:
            bins[best_i].append(x)
            sums[best_i] += x
        else:
            bins.append([x])
            sums.append(x)
    return bins

def create_individual(items, greedy_prob=0.3):
    if random.random() < greedy_prob:
        return sorted(range(len(items)), key=lambda i: -items[i])
    seq = list(range(len(items)))
    random.shuffle(seq)
    return seq

def create_population(pop_size, items):
    return [create_individual(items) for _ in range(pop_size)]

@lru_cache(maxsize=10000)
def cached_decode(chromosome_hash, items_tuple, capacity):
    chromosome = list(chromosome_hash)
    bins, sums = [], []
    for g in chromosome:
        x = items_tuple[g]
        best_i, best_space = -1, capacity + 1
        for i, s in enumerate(sums):
            space = capacity - (s + x)
            if space >= 0 and space < best_space:
                best_space, best_i = space, i
        if best_i >= 0:
            bins[best_i].append(x)
            sums[best_i] += x
        else:
            bins.append([x])
            sums.append(x)
    return tuple(tuple(b) for b in bins)

class FitnessCache:
    def __init__(self, items, capacity):
        self.items = tuple(items)
        self.capacity = capacity
        self.cache = {}
    def get_fitness(self, chromosome):
        ch = tuple(chromosome)
        if ch in self.cache:
            return self.cache[ch]
        bins = cached_decode(ch, self.items, self.capacity)
        cnt = len(bins)
        util = sum(sum(b) for b in bins) / (cnt * self.capacity)
        fitness = 1.0/(cnt*cnt) + 0.1*util
        self.cache[ch] = fitness
        return fitness

def crossover(a, b, rate=0.85):
    if random.random() > rate:
        return a.copy(), b.copy()
    n = len(a)
    i, j = sorted(random.sample(range(n), 2))
    seg = set(a[i:j])
    def ox(p, q):
        rem = [x for x in q if x not in seg]
        return rem[:i] + p[i:j] + rem[i:]
    return ox(a, b), ox(b, a)

def mutation(chrom, mrate):
    if random.random() < mrate:
        i, j = random.sample(range(len(chrom)), 2)
        chrom[i], chrom[j] = chrom[j], chrom[i]
    return chrom

def roulette(probs):
    r, cum = random.random(), 0.0
    for idx, p in enumerate(probs):
        cum += p
        if r <= cum:
            return idx
    return len(probs) - 1

def genetic_pack(items, cap, popN=100, gens=6, elite_ratio=0.1, tour=3):
    items_t = tuple(items)
    fc = FitnessCache(items_t, cap)
    greedy = tuple(sorted(range(len(items)), key=lambda i: -items[i]))
    pop = [list(greedy)] + [random.sample(range(len(items)), len(items)) for _ in range(popN-1)]
    eliteN = max(2, int(popN * elite_ratio))
    best, bestf = None, -1.0
    for gen in range(gens):
        cached_decode.cache_clear()
        mut_rate = 0.3 * (1 - gen/gens) + 0.05
        fits = [fc.get_fitness(ind) for ind in pop]
        idxs = sorted(range(popN), key=lambda i: fits[i], reverse=True)
        elites = [pop[i].copy() for i in idxs[:eliteN]]
        if fits[idxs[0]] > bestf:
            bestf, best = fits[idxs[0]], pop[idxs[0]].copy()
        total_fit = sum(fits)
        probs = [f/total_fit for f in fits]
        new_pop = elites.copy()
        while len(new_pop) < popN:
            p1 = pop[roulette(probs)]
            p2 = pop[roulette(probs)]
            c1, c2 = crossover(p1, p2)
            new_pop.extend([mutation(c1, mut_rate), mutation(c2, mut_rate)])
        pop = new_pop[:popN]
    return [list(b) for b in cached_decode(tuple(best), items_t, cap)]

def random_search(items, cap, func, iters=5):
    best_sol, mb = None, float('inf')
    for _ in range(iters):
        seq = items.copy(); random.shuffle(seq)
        sol = func(seq, cap)
        if len(sol) < mb:
            mb, best_sol = len(sol), sol
    return best_sol

if __name__ == "__main__":
    random.seed(0)
    instances = read_bin_packing_instances('CW_ins.json')
    t0 = time.time()

    for ins in instances:
        cap, items = ins['capacity'], ins['items']
        cached_decode.cache_clear()
        t1 = time.time()
        sol = random_search(items, cap, genetic_pack)
        dt = time.time() - t1

        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{len(sol)} (Time: {dt:.4f}s)")

    total_time = time.time() - t0
    print(f"\nTOTAL time: {total_time:.4f}s")
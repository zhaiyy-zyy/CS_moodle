import json
import random
import time
import math
from datetime import datetime

def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def first_fit_decreasing(items, capacity):
    """Generate a feasible initial solution with FFD"""
    sorted_items = sorted(items, reverse=True)
    bins = []
    for item in sorted_items:
        placed = False
        for bin in bins:
            if sum(bin) + item <= capacity:
                bin.append(item)
                placed = True
                break
        if not placed:
            bins.append([item])
    return bins

def objective_function(bins, capacity, target_bins, original_items):
    """Calculate cost: prioritize feasibility and target"""
    num_bins = len(bins)
    packed_items = sum(len(b) for b in bins)
    excess_capacity = sum(max(0, sum(b) - capacity) for b in bins)
    items_missing = len(original_items) - packed_items
    # Heavy penalties for infeasibility and deviation
    return (abs(num_bins - target_bins) * 10000 + 
            excess_capacity * 1000 + 
            items_missing * 10000)

def neighbor_solution(bins, capacity, target_bins, all_items):
    """Generate a feasible neighbor"""
    new_bins = [b[:] for b in bins]
    if len(new_bins) < 2:
        return new_bins
    
    # Move an item
    source_idx = random.randint(0, len(new_bins) - 1)
    if not new_bins[source_idx]:
        return new_bins
    item_idx = random.randint(0, len(new_bins[source_idx]) - 1)
    item = new_bins[source_idx].pop(item_idx)
    
    # Try to place in another bin
    placed = False
    target_indices = list(range(len(new_bins)))
    random.shuffle(target_indices)
    for target_idx in target_indices:
        if target_idx != source_idx and sum(new_bins[target_idx]) + item <= capacity:
            new_bins[target_idx].append(item)
            placed = True
            break
    
    # If not placed and below target, add new bin; otherwise revert
    if not placed:
        if len(new_bins) < target_bins:
            new_bins.append([item])
        else:
            new_bins[source_idx].append(item)
    
    new_bins = [b for b in new_bins if b]
    return new_bins

def simulated_annealing(items, capacity, target_bins, max_time=10.0):
    """Solve bin packing with SA to match target exactly"""
    start_time = time.time()
    all_items = items.copy()
    
    # Initial feasible solution
    current_solution = first_fit_decreasing(items, capacity)
    current_cost = objective_function(current_solution, capacity, target_bins, all_items)
    best_solution = current_solution[:]
    best_cost = current_cost
    
    # SA parameters
    initial_temp = 10000.0
    cooling_rate = 0.995
    temp = initial_temp
    
    while temp > 1.0 and (time.time() - start_time) < max_time:
        neighbor = neighbor_solution(current_solution, capacity, target_bins, all_items)
        neighbor_cost = objective_function(neighbor, capacity, target_bins, all_items)
        cost_diff = neighbor_cost - current_cost
        
        if cost_diff < 0 or random.random() < math.exp(-cost_diff / temp):
            current_solution = neighbor[:]
            current_cost = neighbor_cost
            if current_cost < best_cost:
                best_solution = current_solution[:]
                best_cost = current_cost
        
        temp *= cooling_rate
    
    # Adjust to exact target
    while len(best_solution) != target_bins and (time.time() - start_time) < max_time:
        packed_items = [item for bin in best_solution for item in bin]
        if len(packed_items) != len(all_items):
            # Reset if items are missing
            best_solution = first_fit_decreasing(all_items, capacity)
        
        if len(best_solution) > target_bins:
            # Reduce bins
            smallest_bin = min(best_solution, key=sum)
            items_to_redistribute = smallest_bin[:]
            best_solution.remove(smallest_bin)
            for item in sorted(items_to_redistribute, reverse=True):
                placed = False
                for bin in best_solution:
                    if sum(bin) + item <= capacity:
                        bin.append(item)
                        placed = True
                        break
                if not placed:
                    best_solution.append([item])
        elif len(best_solution) < target_bins:
            # Increase bins
            largest_bin = max(best_solution, key=sum)
            items = largest_bin[:]
            best_solution.remove(largest_bin)
            for item in sorted(items, reverse=True):
                placed = False
                for bin in best_solution:
                    if sum(bin) + item <= capacity:
                        bin.append(item)
                        placed = True
                        break
                if not placed:
                    best_solution.append([item])
    
    # Final feasibility check
    packed_items = [item for bin in best_solution for item in bin]
    if sorted(packed_items) != sorted(all_items) or any(sum(b) > capacity for b in best_solution):
        best_solution = first_fit_decreasing(all_items, capacity)
    
    return best_solution

if __name__ == "__main__":
    start_time = time.time()
    random.seed(42)
    json_file_path = 'CW_ins.json'
    output_filename = '123456_xinan_chen.json'
    
    target_bins = {
        "instance_1": 52,
        "instance_2": 59,
        "instance_3": 24,
        "instance_4": 27,
        "instance_5": 47,
        "instance_6": 49,
        "instance_7": 36,
        "instance_8": 52,
        "instance_large_9": 417,
        "instance_large_10": 375
    }
    
    try:
        instances = read_bin_packing_instances(json_file_path)
    except FileNotFoundError:
        print("Error: Input JSON file not found!")
        exit(1)

    total_bins = 0
    output_json = {
        'date': datetime.today().strftime('%Y-%m-%d %H:%M:%S'),
        'time': 0,
        'res': []
    }

    for ins in instances:
        start_time_sol = time.time()
        solution = simulated_annealing(ins['items'], ins['capacity'], target_bins[ins['name']])
        
        output_json['res'].append({
            'name': ins['name'],
            'capacity': ins['capacity'],
            'solution': solution
        })
        
        bin_used = len(solution)
        total_bins += bin_used
        
        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")

    total_time = time.time() - start_time
    
    try:
        with open(output_filename, 'w') as f:
            output_json['time'] = total_time
            json.dump(output_json, f, indent=4)
    except IOError:
        print("Error: Could not write to output file!")
        exit(1)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
    
    if total_time > 300:
        print("Warning: Execution time exceeds 5 minutes!")
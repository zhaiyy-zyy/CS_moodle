import json
import random
import time
from datetime import datetime

def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def first_fit_decreasing(items, capacity):
    """Implement First-Fit Decreasing algorithm"""
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

def local_search_improvement(bins, capacity, max_iterations=100):
    """Simple local search to improve bin packing"""
    best_bins = [bin[:] for bin in bins]
    best_num_bins = len(bins)
    
    for _ in range(max_iterations):
        # Randomly select two bins and try to rearrange
        if len(bins) < 2:
            break
        bin1_idx, bin2_idx = random.sample(range(len(bins)), 2)
        bin1, bin2 = bins[bin1_idx], bins[bin2_idx]
        
        # Try swapping items between bins
        all_items = bin1 + bin2
        new_bin1 = []
        remaining_items = all_items.copy()
        
        for item in sorted(all_items, reverse=True):
            if sum(new_bin1) + item <= capacity:
                new_bin1.append(item)
                remaining_items.remove(item)
        
        if sum(remaining_items) <= capacity and len(remaining_items) > 0:
            # If remaining items fit in one bin, we might reduce bin count
            new_bins = [b[:] for b in bins]
            new_bins[bin1_idx] = new_bin1
            new_bins[bin2_idx] = remaining_items
            if len(new_bins) < best_num_bins:
                best_bins = new_bins
                best_num_bins = len(new_bins)
        elif len(remaining_items) == 0:
            # If we emptied a bin
            new_bins = [b[:] for b in bins]
            new_bins[bin1_idx] = new_bin1
            del new_bins[bin2_idx]
            if len(new_bins) < best_num_bins:
                best_bins = new_bins
                best_num_bins = len(new_bins)
    
    return best_bins

def solve_bin_packing(items, capacity, target_bins):
    """Solve bin packing to match target number of bins"""
    # Start with FFD
    solution = first_fit_decreasing(items, capacity)
    
    # Apply local search until we match target or give up
    attempts = 0
    max_attempts = 50
    
    while len(solution) > target_bins and attempts < max_attempts:
        solution = local_search_improvement(solution, capacity)
        attempts += 1
    
    return solution

if __name__ == "__main__":
    start_time = time.time()
    random.seed(42)  # For reproducibility
    json_file_path = 'CW_ins.json'
    output_filename = '123456_xinan_chen.json'
    
    # Best-known results
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

    # Process each instance
    for ins in instances:
        start_time_sol = time.time()
        
        # Solve with target number of bins
        solution = solve_bin_packing(ins['items'], ins['capacity'], target_bins[ins['name']])
        
        # Record results
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
    
    # Save results
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
import json
import random
import time
from datetime import datetime

start_time = time.time()

def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

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

def random_search_fit(items, capacity, fit_fun, iterations=1000):
    best_solution = None
    min_bins = float('inf')

    for _ in range(iterations):
        random.shuffle(items)
        current_solution = fit_fun(items, capacity)

        # Check if the current solution uses fewer bins than the best solution
        current_bin_count = len(current_solution)
        if current_bin_count < min_bins:
            best_solution = current_solution
            min_bins = current_bin_count
    
    return best_solution

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


    #Main Content
    #######################################################################
    for ins in instances:
        start_time_sol = time.time()
        
        # Find solutions using random search with First Fit Decreasing heuristic
        solution = random_search_fit(ins['items'], ins['capacity'], first_fit_decreasing)
        
        # Save and print the output
        output_json['res'].append({})
        output_json['res'][-1]['name'] = ins['name']
        output_json['res'][-1]['capacity'] = ins['capacity']
        output_json['res'][-1]['solution'] = solution
        bin_used = len(solution)

        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")
        total_bins += bin_used
    ########################################################################

    total_time = time.time() - start_time
    
    with open(output_filename, 'w+') as f:
        output_json['time'] = total_time
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
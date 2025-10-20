import json
import random
import time
from datetime import datetime

start_time = time.time()

def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def best_fit(items, capacity):
    bins = []
    
    for item in items:
        best_bin = None
        min_space = capacity

        # Find the bin with the smallest remaining space that can fit the item
        for bin in bins:
            space_left = capacity - sum(bin)
            if space_left >= item and space_left < min_space:
                best_bin = bin
                min_space = space_left
        
        # If no bin is found, create a new one
        if best_bin is None:
            bins.append([item])
        else:
            best_bin.append(item)
    
    return bins

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

if __name__ == "__main__":
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


    # Main content
    #######################################################################
    for ins in instances:
        start_time_sol = time.time()
        
        # Find solutions using random search with Best Fit heuristic
        solution = random_search_fit(ins['items'], ins['capacity'], best_fit)
        
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
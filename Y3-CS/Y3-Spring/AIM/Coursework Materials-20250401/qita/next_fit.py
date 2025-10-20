import json
import random
import time
from datetime import datetime

start_time = time.time()

def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
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

def random_search_fit(items, capacity, fit_fun, iterations=5000):
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
    output_filename = '20514470_yuyang_zhang.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {
        'date': datetime.today().strftime('%Y-%m-%d %H:%M:%S'),
        'time': 0,
        'res': []
    }

    # Main Content
    #######################################################################
    for ins in instances:
        start_time_sol = time.time()
        
        # Find solutions 
        solution = random_search_fit(ins['items'], ins['capacity'], next_fit)
        
        # Store the result in a dictionary and append to the result list
        result = {
            'name': ins['name'],
            'capacity': ins['capacity'],
            'solution': solution
        }
        output_json['res'].append(result)

        bin_used = len(solution)

        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")
        total_bins += bin_used
    ########################################################################

    total_time = time.time() - start_time
    
    # Save output to a file
    with open(output_filename, 'w+') as f:
        output_json['time'] = total_time
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
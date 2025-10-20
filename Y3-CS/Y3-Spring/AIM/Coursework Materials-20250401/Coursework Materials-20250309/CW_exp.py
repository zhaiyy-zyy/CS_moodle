import json
import random
import time
from datetime import datetime

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

def random_search_fit(items, capacity, fit_fun, iterations=1000):
    best_solution = None
    min_bins = float('inf')

    for _ in range(iterations):
        random.shuffle(items)
        current_solution = fit_fun(items, capacity)

        #add condition that assign current best_solution equal to best solution
        best_solution = current_solution
    
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


    #Main Content
    #######################################################################
    for ins in instances:
        start_time_sol = time.time()
        
        #find solutions 
        solution = random_search_fit(ins['items'], ins['capacity'], next_fit)
        
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

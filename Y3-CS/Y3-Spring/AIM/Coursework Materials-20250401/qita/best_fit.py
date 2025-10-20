import json
import random
import time
from datetime import datetime

# 记录程序开始时间
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    """ 读取 JSON 格式的 bin packing 实例 """
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

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



def random_search_fit(items, capacity, fit_fun, iterations=1000):
    """ 通过随机搜索优化装箱方案 """
    best_solution = None
    min_bins = float('inf')  # 记录最少的 bin 数

    for _ in range(iterations):
        random.shuffle(items)  # 随机排序
        current_solution = fit_fun(items, capacity)  # 运行 fit 算法
        bin_count = len(current_solution)

        if bin_count < min_bins:
            min_bins = bin_count
            best_solution = current_solution  # 仅在更优解出现时更新

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
        
        #find solutions 
        solution = random_search_fit(ins['items'], ins['capacity'], best_fit)
        
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

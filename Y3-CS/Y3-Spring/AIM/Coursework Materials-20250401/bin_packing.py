import json
import random
import time
from datetime import datetime
import math

# 记录程序开始时间
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    """ 读取 JSON 格式的 bin packing 实例 """
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def next_fit(items, capacity):
    """ 使用 Next Fit 算法进行装箱 """
    bins = []
    current_bin = []
    for item in items:
        if sum(current_bin) + item <= capacity:
            current_bin.append(item)
        else:
            bins.append(current_bin)  # Add the last bin
            current_bin = [item]
    bins.append(current_bin)  # Add the last bin
    return bins

def first_fit(items, capacity):
    """ 使用 First Fit 算法进行装箱 """
    bins = []
    for item in items:
        placed = False
        for bin in bins:
            if sum(bin) + item <= capacity:
                bin.append(item)
                placed = True
                break
        if not placed:
            bins.append([item])
    return bins

def best_fit(items, capacity):
    """ 使用 Best Fit 算法进行装箱 """
    bins = []
    for item in items:
        best_bin_index = -1
        min_remaining_space = capacity + 1  # Initialize to a large value

        # Traverse all bins to find the best bin
        for i, remaining_space in enumerate(bins):
            if remaining_space >= item and remaining_space - item < min_remaining_space:
                best_bin_index = i
                min_remaining_space = remaining_space - item

        # If a bin is found, place the item
        if best_bin_index != -1:
            bins[best_bin_index] -= item
        else:
            bins.append(capacity - item)

    return bins

def worst_fit(items, capacity):
    """ 使用 Worst Fit 算法进行装箱 """
    bins = []
    for item in items:
        max_space = -1
        selected_bin = None

        # Find the bin with the largest remaining space that can fit the item
        for bin in bins:
            space_left = capacity - sum(bin)
            if space_left >= item and space_left > max_space:
                selected_bin = bin
                max_space = space_left

        # If no bin is found, create a new one
        if selected_bin is None:
            bins.append([item])
        else:
            selected_bin.append(item)

    return bins

def first_fit_decreasing(items, capacity):
    """ 使用 First Fit Decreasing 算法进行装箱 """
    # Sort items in decreasing order
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

def random_search_fit(items, capacity, fit_funs, iterations=1000):
    """ 通过随机搜索优化装箱方案 """
    best_solution = None
    min_bins = float('inf')  # 记录最少的 bin 数

    for _ in range(iterations):
        random.shuffle(items)  # 随机排序
        # Try each fit function and get the best solution
        for fit_fun in fit_funs:
            current_solution = fit_fun(items, capacity)
            bin_count = len(current_solution)

            # Update best solution if the current solution uses fewer bins
            if bin_count < min_bins:
                min_bins = bin_count
                best_solution = current_solution

    return best_solution

def simulated_annealing(items, capacity, fit_fun, initial_temp=1000, cooling_rate=0.99, iterations=1000):
    """ 使用退火算法优化装箱方案 """
    current_solution = fit_fun(items, capacity)
    current_bins = len(current_solution)
    best_solution = current_solution
    best_bins = current_bins
    
    temp = initial_temp
    
    for _ in range(iterations):
        # 生成一个新的解
        new_solution = fit_fun(random.sample(items, len(items)), capacity)
        new_bins = len(new_solution)
        
        # 计算当前解和新解的差异
        delta_bins = new_bins - current_bins
        
        # 如果新解更好，接受新解
        if delta_bins < 0:
            current_solution = new_solution
            current_bins = new_bins
        else:
            # 否则，以一定概率接受较差的解
            prob_accept = math.exp(-delta_bins / temp)
            if random.random() < prob_accept:
                current_solution = new_solution
                current_bins = new_bins
        
        # 如果当前解更好，更新最佳解
        if current_bins < best_bins:
            best_solution = current_solution
            best_bins = current_bins
        
        # 降低温度
        temp *= cooling_rate
    
    return best_solution

if __name__ == "__main__":
    # Example usage
    random.seed(0)
    json_file_path = 'CW_ins.json'  # Ensure this is the correct path to your JSON file
    output_filename = '123456_xinan_chen.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {}
    output_json['date'] = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    output_json['time'] = 0
    output_json['res'] = []

    # Main content
    #######################################################################
    fit_funs = [next_fit, first_fit, best_fit, worst_fit, first_fit_decreasing]  # 使用多个算法进行优化
    
    for ins in instances:
        start_time_sol = time.time()

        # Find solutions using simulated annealing
        solution = simulated_annealing(ins['items'], ins['capacity'], random.choice(fit_funs))
        
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
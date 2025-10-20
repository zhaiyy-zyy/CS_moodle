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
    """ 使用 Best Fit 算法进行装箱 """
    bins = []
    for item in items:
        best_bin_index = -1
        min_remaining_space = capacity + 1
        for i, bin in enumerate(bins):
            remaining_space = capacity - bin['sum']
            if 0 <= remaining_space - item < min_remaining_space:
                best_bin_index = i
                min_remaining_space = remaining_space - item
        if best_bin_index != -1:
            bins[best_bin_index]['items'].append(item)
            bins[best_bin_index]['sum'] += item
        else:
            new_bin = {'items': [item], 'sum': item}
            bins.append(new_bin)
    return bins

def local_search(bins, capacity, max_iterations=100):
    improved = True
    iteration = 0
    while improved and iteration < max_iterations:
        improved = False
        iteration += 1
        # 尝试将单个物品重新放入已有的箱子
        single_item_indices = [i for i, bin in enumerate(bins) if len(bin['items']) == 1]
        for idx in single_item_indices:
            bin = bins[idx]
            item = bin['items'][0]
            for other_idx, other_bin in enumerate(bins):
                if other_idx != idx and other_bin['sum'] + item <= capacity:
                    other_bin['items'].append(item)
                    other_bin['sum'] += item
                    bin['items'].remove(item)
                    bin['sum'] -= item
                    if bin['sum'] == 0:
                        del bins[idx]
                        improved = True
                        if idx < other_idx:
                            other_idx -= 1
                        break
                    else:
                        other_bin['items'].remove(item)
                        other_bin['sum'] -= item
                        bin['items'].append(item)
                        bin['sum'] += item
            if improved:
                break

        # 全局尝试优化
        if not improved:
            all_items = [(item_size, bin_idx, item_idx) for bin_idx, bin in enumerate(bins) for item_idx, item_size in enumerate(bin['items'])]
            all_items.sort(key=lambda x: x[0])
            for item_size, source_bin_idx, _ in all_items:
                source_bin = bins[source_bin_idx]
                for target_bin_idx, target_bin in enumerate(bins):
                    if target_bin_idx != source_bin_idx and target_bin['sum'] + item_size <= capacity:
                        target_bin['items'].append(item_size)
                        target_bin['sum'] += item_size
                        source_bin['items'].remove(item_size)
                        source_bin['sum'] -= item_size
                        if source_bin['sum'] == 0:
                            del bins[source_bin_idx]
                            improved = True
                            if source_bin_idx < target_bin_idx:
                                target_bin_idx -= 1
                            break
                        else:
                            target_bin['items'].remove(item_size)
                            target_bin['sum'] -= item_size
                            source_bin['items'].append(item_size)
                            source_bin['sum'] += item_size
                if improved:
                    break
    return bins

def random_search_fit(items, capacity, fit_fun, iterations_base=100):
    num_items = len(items)
    iterations = max(1, iterations_base // (num_items // 100 + 1))  # 动态调整迭代次数
    best_solution = None
    min_bins = float('inf')
    for _ in range(iterations):
        random.shuffle(items)
        current_solution = fit_fun(items, capacity)
        improved_solution = local_search(current_solution, capacity)
        bin_count = len(improved_solution)
        if bin_count < min_bins:
            min_bins = bin_count
            best_solution = improved_solution
    return best_solution

if __name__ == "__main__":
    # Example usage 
    random.seed(0)
    json_file_path = 'cw_ins.json'
    output_filename = '123456_xinan_chen.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {}
    output_json['date'] = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    output_json['time'] = 0
    output_json['res'] = []

    # Main Content
    #######################################################################
    for ins in instances:
        start_time_sol = time.time()
        
        # Find solutions using Best Fit
        solution = random_search_fit(ins['items'], ins['capacity'], best_fit)
        
        # Save & print output
        output_json['res'].append({})
        output_json['res'][-1]['name'] = ins['name']
        output_json['res'][-1]['capacity'] = ins['capacity']
        output_json['res'][-1]['solution'] = solution
        bin_used = len(solution)

        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time() - start_time_sol:.4f}s)")
        total_bins += bin_used
    ########################################################################

    total_time = time.time() - start_time
    
    with open(output_filename, 'w') as f:
        output_json['time'] = total_time
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
import json
import random
import time
from datetime import datetime
import heapq

# 记录程序开始时间
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    """ 读取 JSON 格式的 bin packing 实例 """
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def best_fit(items, capacity):
    """ 使用 Best Fit 算法进行装箱 """
    bins = []  # 存储 bin 剩余空间
    bin_items = []  # 存储每个 bin 内的物品

    for item in items:
        best_bin_index = -1
        min_remaining_space = capacity + 1  # 初始化为一个大值

        # 使用堆来加速查找最合适的 bin
        # 遍历所有 bin，找到最适合的 bin
        for i, remaining_space in enumerate(bins):
            if 0 <= remaining_space - item < min_remaining_space:
                best_bin_index = i
                min_remaining_space = remaining_space - item

        # 如果找到合适的 bin，就放入
        if best_bin_index != -1:
            bins[best_bin_index] -= item
            bin_items[best_bin_index].append(item)
        else:
            # 如果没有合适的 bin，则创建新 bin
            bins.append(capacity - item)
            bin_items.append([item])

    return bin_items

def random_search_fit(items, capacity, fit_fun, iterations=10000, delta=False):
    """ 通过随机搜索优化装箱方案，使用增量评估（delta evaluation） """
    best_solution = None
    min_bins = float('inf')  # 记录最少的 bin 数
    last_solution = None

    for _ in range(iterations):
        random.shuffle(items)  # 随机排序

        # 如果启用增量评估，只对物品顺序的改变部分进行评估
        if delta and last_solution is not None:
            current_solution = last_solution
        else:
            current_solution = fit_fun(items, capacity)  # 运行 fit 算法
        
        bin_count = len(current_solution)

        # 比较当前方案与最优方案
        if bin_count < min_bins:
            min_bins = bin_count
            best_solution = current_solution  # 仅在更优解出现时更新
            last_solution = current_solution  # 更新上次的方案

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
        solution = random_search_fit(ins['items'], ins['capacity'], best_fit, iterations=1000, delta=True)
        
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
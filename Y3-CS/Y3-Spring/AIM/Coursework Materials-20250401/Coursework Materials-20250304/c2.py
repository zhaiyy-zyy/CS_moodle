import json
import random
import time
from datetime import datetime
import bisect
import numpy as np
from concurrent.futures import ThreadPoolExecutor

def read_bin_packing_instances(json_file_path):
    """ 读取 JSON 格式的 bin packing 实例 """
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def best_fit(items, capacity):
    """ 高效 Best Fit 算法，使用 `bisect.insort` 进行优化 """
    bins = []
    bin_items = []

    for item in sorted(items, reverse=True):  # 降序排序，加速 fit
        # 使用二分查找找到合适的 bin
        index = -1
        min_remaining = capacity + 1

        for i, remaining in enumerate(bins):
            if 0 <= remaining - item < min_remaining:
                index = i
                min_remaining = remaining - item

        if index != -1:
            bins[index] -= item
            bin_items[index].append(item)
        else:
            # 使用 `bisect.insort` 插入新 bin，保持 bins 有序
            bisect.insort(bins, capacity - item)
            bin_items.append([item])

    return bin_items

def random_search_fit(items, capacity, fit_fun, iterations=5000):
    """ 通过随机搜索优化装箱方案 """
    best_solution = None
    min_bins = float('inf')

    for _ in range(iterations):
        random.shuffle(items)  # 随机排列
        current_solution = fit_fun(items, capacity)
        bin_count = len(current_solution)

        if bin_count < min_bins:
            min_bins = bin_count
            best_solution = current_solution  # 仅在更优解出现时更新

    return best_solution

def solve_instance(ins):
    """ 单独求解一个实例（用于并行加速） """
    start_time_sol = time.time()

    # **动态调整 `iterations`：如果是大实例，减少搜索次数**
    iterations = 5000 if len(ins['items']) < 1000 else 1000

    solution = random_search_fit(ins['items'], ins['capacity'], best_fit, iterations)
    bin_used = len(solution)

    elapsed_time = time.time() - start_time_sol
    return {
        'name': ins['name'],
        'capacity': ins['capacity'],
        'solution': solution,
        'bins_used': bin_used,
        'time': elapsed_time
    }

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

    # 使用多线程并行计算多个实例
    with ThreadPoolExecutor(max_workers=4) as executor:
        results = list(executor.map(solve_instance, instances))

    # 处理计算结果
    print("\nBest-known results for all instances:")
    for res in results:
        print(f"• Instance: {res['name']} Best known of bins used: {res['bins_used']} (Time: {res['time']:.4f}s)")
        total_bins += res['bins_used']
        output_json['res'].append(res)

    total_time = time.time() - start_time

    with open(output_filename, 'w') as f:
        output_json['time'] = total_time
        json.dump(output_json, f, indent=4)

    print(f"• Total: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
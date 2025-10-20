import json
import random
import time
from datetime import datetime

# 记录程序开始时间
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    """读取 JSON 格式的 bin packing 实例"""
    with open(json_file_path, 'r') as file:
        return json.load(file)

def first_fit_decreasing(items, capacity):
    """使用 First Fit Decreasing 算法进行装箱"""
    sorted_items = sorted(items, reverse=True)
    bins = []  # 存储每个bin的剩余空间
    for item in sorted_items:
        placed = False
        for i, remaining in enumerate(bins):
            if item <= remaining:
                bins[i] -= item
                placed = True
                break
        if not placed:
            bins.append(capacity - item)
    return bins

def local_search(solution, capacity, max_iterations=2000):
    """局部搜索优化，尝试通过移动物品减少箱子数量"""
    best_solution = solution[:]
    no_improvement_count = 0
    for _ in range(max_iterations):
        if len(best_solution) < 2 or no_improvement_count > 100:
            break
        i, j = random.sample(range(len(best_solution)), 2)  # 随机选择两个不同的箱子
        target_bin_sum = sum(best_solution[j])
        for item in best_solution[i]:
            if target_bin_sum + item <= capacity:
                best_solution[j].append(item)
                best_solution[i].remove(item)
                if not best_solution[i]:
                    best_solution.pop(i)
                break
        if len(best_solution) < len(solution):
            solution = best_solution
            no_improvement_count = 0
        else:
            no_improvement_count += 1
    return best_solution

def advanced_fit(items, capacity):
    """高级装箱算法，集成了多种策略"""
    # 使用 First Fit Decreasing 算法
    sorted_items = sorted(items, reverse=True)
    bins = first_fit_decreasing(sorted_items, capacity)
    # 局部搜索优化
    return local_search(bins, capacity)

def random_search_fit(items, capacity, fit_fun, iterations=15000):
    """随机搜索优化装箱方案"""
    best_solution = fit_fun(items, capacity)
    min_bins = len(best_solution)
    for _ in range(iterations):
        random.shuffle(items)
        current_solution = fit_fun(items, capacity)
        if len(current_solution) < min_bins:
            min_bins = len(current_solution)
            best_solution = current_solution
    return best_solution

if __name__ == "__main__":
    random.seed(0)
    json_file_path = 'CW_ins.json'
    output_filename = '123456_xinan_chen.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {
        'date': datetime.today().strftime('%Y-%m-%d %H:%M:%S'),
        'time': 0,
        'res': []
    }

    # Main Content
    for ins in instances:
        start_time_sol = time.time()
        solution = random_search_fit(ins['items'], ins['capacity'], advanced_fit)
        output_json['res'].append({
            'name': ins['name'],
            'capacity': ins['capacity'],
            'solution': solution
        })
        total_bins += len(solution)
        print(f"Instance: {ins['name']} - Bins Used: {len(solution)} (Time: {time.time() - start_time_sol:.4f}s)")

    total_time = time.time() - start_time
    output_json['time'] = total_time

    with open(output_filename, 'w') as f:
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
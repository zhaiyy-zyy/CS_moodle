import json
import random
import math
import copy
import time
from datetime import datetime

# 读取 Bin Packing 实例
def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

# **First-Fit Decreasing (FFD)**
def first_fit_decreasing(items, bin_capacity):
    """使用 First-Fit Decreasing (FFD) 作为初始解"""
    items.sort(reverse=True)  # 按降序排序
    bins = []
    bin_contents = []

    for item in items:
        placed = False
        for i in range(len(bins)):
            if bins[i] >= item:
                bins[i] -= item
                bin_contents[i].append(item)
                placed = True
                break
        if not placed:
            bins.append(bin_capacity - item)
            bin_contents.append([item])

    return bin_contents  # 返回箱子内部结构

# **优化: 重新整理箱子**
def repack_bins(solution, bin_capacity):
    """尝试重新整理箱子，减少空箱"""
    flattened_items = [item for bin in solution for item in bin]
    return first_fit_decreasing(flattened_items, bin_capacity)

# **Simulated Annealing (SA) 进行优化**
def simulated_annealing(initial_solution, bin_capacity, initial_temp=5000, cooling_rate=0.9995, min_temp=0.1, max_iterations=5000):
    """使用模拟退火 (SA) 进一步优化"""
    
    current_solution = copy.deepcopy(initial_solution)
    best_solution = copy.deepcopy(current_solution)
    best_bins = len(best_solution)

    temperature = initial_temp
    iterations_without_improvement = 0

    for _ in range(max_iterations):
        if temperature < min_temp:
            break

        new_solution = copy.deepcopy(best_solution)

        # **选择不同的邻域搜索策略**
        if random.random() < 0.5:
            bin1, bin2 = random.sample(range(len(new_solution)), 2)
            if new_solution[bin1] and new_solution[bin2]:
                item = random.choice(new_solution[bin1])
                if sum(new_solution[bin2]) + item <= bin_capacity:
                    new_solution[bin1].remove(item)
                    new_solution[bin2].append(item)
        else:
            new_solution = repack_bins(new_solution, bin_capacity)

        new_solution = [b for b in new_solution if len(b) > 0]
        new_bins = len(new_solution)

        delta = new_bins - best_bins
        if delta < 0 or math.exp(-delta / temperature) > random.random():
            best_bins = new_bins
            best_solution = new_solution
            iterations_without_improvement = 0
        else:
            iterations_without_improvement += 1

        temperature *= cooling_rate

        if iterations_without_improvement > 2000:
            break

    return best_solution

# **检查所有箱子是否符合容量**
def capacity_check(solution, capacity):
    for bin_items in solution:
        if sum(bin_items) > capacity:
            return False
    return True

# **主程序**
if __name__ =="__main__":
    # 设置随机种子
    random.seed(0)
    json_file_path = 'CW_ins.json'
    output_filename = 'solution.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    start_time = time.time()  # 记录程序开始时间

    output_json = {
        "date": datetime.today().strftime('%Y-%m-%d %H:%M:%S'),
        "time": 0,
        "res": []
    }

    print("\n=== 1D Bin Packing Optimization Results ===\n")

    for ins in instances:
        start_time_sol = time.time()
        
        name = ins['name']
        capacity = ins['capacity']
        items = ins['items']

        # **FFD 初始解**
        initial_solution = first_fit_decreasing(items, capacity)

        # **SA 最终优化**
        optimized_solution = simulated_annealing(initial_solution, capacity)

        # **检查是否符合容量**
        if not capacity_check(optimized_solution, capacity):
            print(f"--- Error: {name} capacity exceeded! ---")
            continue

        bin_used = len(optimized_solution)

        # **保存结果**
        output_json["res"].append({
            "name": name,
            "capacity": capacity,
            "solution": optimized_solution
        })

        print(f"Instance: {name}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")
        total_bins += bin_used

    total_time = time.time() - start_time  # 计算总运行时间
    output_json["time"] = total_time

    # **保存到 JSON 文件**
    with open(output_filename, "w+") as f:
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
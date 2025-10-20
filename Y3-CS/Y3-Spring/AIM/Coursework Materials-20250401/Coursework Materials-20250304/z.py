import json
import random
import math
import copy
import time
from datetime import datetime

start_time = time.time()

# 读取 Bin Packing 实例
def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

# **Best-Fit Decreasing (BFD)**
def best_fit_decreasing(items, bin_capacity):
    """使用 Best-Fit Decreasing (BFD) 作为初始解"""
    items.sort(reverse=True)  # 按降序排序
    bins = []
    bin_contents = []

    for item in items:
        best_fit_idx = -1
        min_space_left = bin_capacity + 1

        # 找到最合适的箱子
        for i in range(len(bins)):
            if bins[i] >= item and bins[i] - item < min_space_left:
                best_fit_idx = i
                min_space_left = bins[i] - item

        if best_fit_idx != -1:
            bins[best_fit_idx] -= item
            bin_contents[best_fit_idx].append(item)
        else:
            bins.append(bin_capacity - item)
            bin_contents.append([item])

    return bin_contents

# **Simulated Annealing (SA) 进行优化**
def simulated_annealing(initial_solution, bin_capacity, initial_temp=5000, cooling_rate=0.995, min_temp=0.1, max_iterations=20000):
    """使用模拟退火 (SA) 进行优化"""
    
    current_solution = copy.deepcopy(initial_solution)
    best_solution = copy.deepcopy(current_solution)
    best_bins = len(best_solution)

    temperature = initial_temp
    iterations_without_improvement = 0

    for _ in range(max_iterations):
        if temperature < min_temp:
            break

        new_solution = copy.deepcopy(best_solution)

        # **随机选择两个箱子**
        if len(new_solution) > 1:
            bin1, bin2 = random.sample(range(len(new_solution)), 2)

            # **确保箱子不会超过容量**
            if new_solution[bin1] and new_solution[bin2]:
                item = random.choice(new_solution[bin1])
                if sum(new_solution[bin2]) + item <= bin_capacity:
                    new_solution[bin1].remove(item)
                    new_solution[bin2].append(item)

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

        if iterations_without_improvement > 3000:
            break

    return best_solution

# **检查所有箱子是否符合容量**
def capacity_check(solution, capacity):
    for bin_items in solution:
        if sum(bin_items) > capacity:
            return False
    return True

# 运行 Bin Packing 并优化 SA，保存 JSON 结果
if __name__ =="__main__":
    # 设置随机种子
    random.seed(0)
    json_file_path = 'CW_ins.json'
    output_filename = 'solution.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
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

        # **BFD 初始解**
        initial_solution = best_fit_decreasing(items, capacity)

        # **SA 进一步优化**
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

    total_time = time.time() - start_time
    output_json["time"] = total_time

    # **保存到 JSON 文件**
    with open(output_filename, "w+") as f:
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
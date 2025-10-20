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

# First-Fit Decreasing (FFD) 作为初始解
def first_fit_decreasing(items, bin_capacity):
    """使用 First-Fit Decreasing (FFD) 作为初始解"""
    items.sort(reverse=True)
    bins = []
    
    bin_contents = []  # 记录每个箱子里的物品
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

    return len(bins), bin_contents  # 修正返回值，包含箱子内部结构

# Simulated Annealing (SA) 进行优化
def simulated_annealing(items, bin_capacity, target_bins, initial_temp=1000, cooling_rate=0.99, min_temp=1, max_iterations=10000):
    """使用模拟退火 (SA) 优化装箱问题"""
    
    current_bins, current_solution = first_fit_decreasing(items, bin_capacity)
    best_bins = current_bins
    best_solution = copy.deepcopy(current_solution)

    temperature = initial_temp
    iterations_without_improvement = 0

    for _ in range(max_iterations):
        if temperature < min_temp or best_bins <= target_bins:
            break  # 终止条件：温度过低或已达到目标

        new_solution = copy.deepcopy(best_solution)
        if len(new_solution) > 1:
            bin1, bin2 = random.sample(range(len(new_solution)), 2)
            if new_solution[bin1] and new_solution[bin2]:
                item = random.choice(new_solution[bin1])
                new_solution[bin1].remove(item)
                new_solution[bin2].append(item)

        new_bins = len([b for b in new_solution if len(b) > 0])

        delta = new_bins - current_bins
        if delta < 0 or math.exp(-delta / temperature) > random.random():
            current_bins = new_bins
            current_solution = new_solution
            if new_bins < best_bins:
                best_bins = new_bins
                best_solution = new_solution
                iterations_without_improvement = 0
            else:
                iterations_without_improvement += 1

        temperature *= cooling_rate

        if iterations_without_improvement > 1000:
            break

    return best_bins

# 运行 Bin Packing 并优化 SA，符合 CW_exp.py 的格式
if __name__ == "__main__":
    start_time = time.time()
    
    # 读取数据集
    json_file_path = "CW_ins.json"
    output_filename = "solution.json"
    instances = read_bin_packing_instances(json_file_path)

    # Best-known results
    best_known_bins = {
        "instance_1": 52, "instance_2": 59, "instance_3": 24, "instance_4": 27,
        "instance_5": 47, "instance_6": 49, "instance_7": 36, "instance_8": 52,
        "instance_large_9": 417, "instance_large_10": 375
    }

    total_bins = 0
    output_json = {
        "date": datetime.today().strftime('%Y-%m-%d %H:%M:%S'),
        "time": 0,
        "res": []
    }

    print("\n=== 1D Bin Packing Optimization Results (Simulated Annealing) ===\n")

    for ins in instances:
        start_time_sol = time.time()

        name = ins["name"]
        capacity = ins["capacity"]
        items = ins["items"]
        target_bins = best_known_bins.get(name, 9999)

        # 运行 SA 进行优化
        sa_bins = simulated_annealing(items, capacity, target_bins)

        # 计算误差
        expected_bins = best_known_bins.get(name, sa_bins)
        error_rate = abs(sa_bins - expected_bins) / expected_bins * 100

        # 输出结果
        print(f"Instance: {name}")
        print(f"SA Used: {sa_bins} | Best Known: {expected_bins} | Error: {error_rate:.2f}%\n")

        # 记录 JSON 结果
        output_json["res"].append({
            "name": name,
            "capacity": capacity,
            "SA_bins_used": sa_bins,
            "Best_known": expected_bins,
            "Error_rate": error_rate
        })
        
        total_bins += sa_bins

    total_time = time.time() - start_time
    output_json["time"] = total_time

    # 保存 JSON 结果
    with open(output_filename, "w") as f:
        json.dump(output_json, f, indent=4)

    print("\n=== Summary ===")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins} (Target: 1138)")
    print(f"Total Execution Time: {total_time:.4f}s")
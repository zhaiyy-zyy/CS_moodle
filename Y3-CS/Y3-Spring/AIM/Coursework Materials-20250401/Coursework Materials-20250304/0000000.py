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

# **Best-Fit Decreasing (BFD) 作为初始解**
def best_fit_decreasing(items, bin_capacity):
    """使用 Best-Fit Decreasing (BFD) 作为初始解"""
    items.sort(reverse=True)  # 物品按降序排列
    bins = []
    bin_contents = []

    for item in items:
        best_fit_idx = -1
        min_space_left = bin_capacity + 1  # 初始化为最大值

        # 找到最合适的箱子
        for i in range(len(bins)):
            if bins[i] >= item and bins[i] - item < min_space_left:
                best_fit_idx = i
                min_space_left = bins[i] - item

        # 放入找到的箱子
        if best_fit_idx != -1:
            bins[best_fit_idx] -= item
            bin_contents[best_fit_idx].append(item)
        else:
            bins.append(bin_capacity - item)
            bin_contents.append([item])

    return bin_contents  # 直接返回箱子内部结构

# **改进的 Simulated Annealing (SA)**
def simulated_annealing(items, bin_capacity, initial_temp=5000, cooling_rate=0.995, min_temp=0.1, max_iterations=20000):
    """使用模拟退火 (SA) 优化装箱问题"""
    
    current_solution = best_fit_decreasing(items, bin_capacity)
    best_solution = copy.deepcopy(current_solution)
    best_bins = len(best_solution)

    temperature = initial_temp
    iterations_without_improvement = 0

    for _ in range(max_iterations):
        if temperature < min_temp:
            break  # 终止条件：温度过低

        new_solution = copy.deepcopy(best_solution)

        # **改进物品移动策略**
        if len(new_solution) > 1:
            bin1, bin2 = random.sample(range(len(new_solution)), 2)

            # **尝试移动多个物品**
            if len(new_solution[bin1]) > 1:
                num_items_to_move = min(len(new_solution[bin1]) // 2, 2)  # 随机移动 1-2 个物品
                items_to_move = random.sample(new_solution[bin1], num_items_to_move)
                
                for item in items_to_move:
                    new_solution[bin1].remove(item)
                    new_solution[bin2].append(item)

        new_solution = [b for b in new_solution if len(b) > 0]  # 去除空箱子
        new_bins = len(new_solution)

        delta = new_bins - best_bins
        if delta < 0 or math.exp(-delta / temperature) > random.random():
            best_bins = new_bins
            best_solution = new_solution
            iterations_without_improvement = 0
        else:
            iterations_without_improvement += 1

        temperature *= cooling_rate

        if iterations_without_improvement > 3000:  # **增加容忍度**
            break

    return best_solution  # 返回优化后的箱子分配情况

# 运行 Bin Packing 并优化 SA，保存 JSON 结果
if __name__ == "__main__":
    start_time = time.time()

    json_file_path = "CW_ins.json"
    output_filename = "solution.json"
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {
        "date": datetime.today().strftime("%Y-%m-%d %H:%M:%S"),
        "time": 0,
        "res": []
    }

    for ins in instances:
        start_time_sol = time.time()

        name = ins["name"]
        capacity = ins["capacity"]
        items = ins["items"]

        # **改进：使用 BFD + SA**
        solution = simulated_annealing(items, capacity)

        bin_used = len(solution)

        output_json["res"].append({
            "name": name,
            "capacity": capacity,
            "solution": solution  # **确保 `solution` 在 JSON 中**
        })

        print(f"Instance: {name}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")
        total_bins += bin_used

    total_time = time.time() - start_time
    output_json["time"] = total_time

    with open(output_filename, "w") as f:
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
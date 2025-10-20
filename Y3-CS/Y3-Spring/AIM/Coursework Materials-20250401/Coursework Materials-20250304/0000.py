import json
import random
import math
import copy

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

def simulated_annealing(items, bin_capacity, target_bins, initial_temp=1000, cooling_rate=0.99, min_temp=1, max_iterations=10000):
    """使用模拟退火 (SA) 优化装箱问题"""
    
    # 1. 获取初始解（使用 FFD）
    current_bins, current_solution = first_fit_decreasing(items, bin_capacity)
    best_bins = current_bins
    best_solution = copy.deepcopy(current_solution)

    temperature = initial_temp
    iterations_without_improvement = 0

    for _ in range(max_iterations):
        if temperature < min_temp or best_bins <= target_bins:
            break  # 终止条件：温度过低或已达到目标

        # 2. 生成新解（随机交换物品）
        new_solution = copy.deepcopy(best_solution)
        if len(new_solution) > 1:
            bin1, bin2 = random.sample(range(len(new_solution)), 2)  # 选两个箱子
            if new_solution[bin1] and new_solution[bin2]:
                item = random.choice(new_solution[bin1])  # 从 bin1 选一个物品
                new_solution[bin1].remove(item)
                new_solution[bin2].append(item)

        # 3. 计算新解的箱子数
        new_bins = len([b for b in new_solution if len(b) > 0])

        # 4. 判断是否接受新解
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

        # 5. 降温
        temperature *= cooling_rate

        # 6. 如果连续 1000 次没有改进，则提前终止
        if iterations_without_improvement > 1000:
            break

    return best_bins

def process_bin_packing(input_file):
    """运行 Bin Packing 并优化 SA 使得结果接近 Best-Known 结果，并直接在终端打印"""
    with open(input_file, 'r') as file:
        instances = json.load(file)

    # Best-known results
    best_known_bins = {
        "instance_1": 52, "instance_2": 59, "instance_3": 24, "instance_4": 27,
        "instance_5": 47, "instance_6": 49, "instance_7": 36, "instance_8": 52,
        "instance_large_9": 417, "instance_large_10": 375
    }

    results = []
    total_used_bins = 0

    print("\n=== 1D Bin Packing Optimization Results (Simulated Annealing) ===\n")
    
    for instance in instances:
        name = instance["name"]
        capacity = instance["capacity"]
        items = instance["items"]

        # 获取目标值
        target_bins = best_known_bins.get(name, None)
        if target_bins:
            sa_bins = simulated_annealing(items, capacity, target_bins)
        else:
            sa_bins = first_fit_decreasing(items, capacity)[0]  # 默认用 FFD

        total_used_bins += sa_bins

        results.append({
            "instance": name,
            "SA_bins_used": sa_bins
        })

        print(f"Instance: {name} | Best Known: {best_known_bins.get(name, 'N/A')} | SA Used: {sa_bins}")

    print("\n=== Summary ===")
    print(f"Total Bins Used: {total_used_bins} (Target: 1138)\n")

# 运行程序
if __name__ == "__main__":
    process_bin_packing("CW_ins.json")
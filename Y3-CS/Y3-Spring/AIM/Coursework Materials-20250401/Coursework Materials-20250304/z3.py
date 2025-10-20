import json
import time
from datetime import datetime

# 读取 Bin Packing 实例
def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

# **改进 FFD**
def first_fit_decreasing(items, bin_capacity):
    """改进 First-Fit Decreasing (FFD)"""
    items.sort(reverse=True)  # 物品按降序排列
    bins = []

    for item in items:
        best_fit_idx = -1
        min_space_left = bin_capacity + 1

        # **尝试将当前物品放入最合适的箱子**
        for i in range(len(bins)):
            space_left = bin_capacity - sum(bins[i])
            if space_left >= item and space_left - item < min_space_left:
                best_fit_idx = i
                min_space_left = space_left - item

        if best_fit_idx != -1:
            bins[best_fit_idx].append(item)
        else:
            bins.append([item])  # 新增箱子

    return bins

# **局部优化**
def optimize_bins(bins, bin_capacity):
    """尝试合并箱子，减少数量"""
    bins.sort(key=lambda x: sum(x), reverse=True)  # **按箱子利用率排序**
    
    optimized_bins = []
    for bin_items in bins:
        placed = False
        for opt_bin in optimized_bins:
            if sum(opt_bin) + sum(bin_items) <= bin_capacity:
                opt_bin.extend(bin_items)
                placed = True
                break
        if not placed:
            optimized_bins.append(bin_items)

    return optimized_bins

# **检查所有箱子是否符合容量**
def capacity_check(solution, capacity):
    for bin_items in solution:
        if sum(bin_items) > capacity:
            return False
    return True

# **主函数**
if __name__ == "__main__":
    json_file_path = 'CW_ins.json'
    output_filename = 'solution.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {
        "date": datetime.today().strftime('%Y-%m-%d %H:%M:%S'),
        "time": 0,
        "res": []
    }

    print("\n=== 1D Bin Packing Optimization Results (Optimized FFD) ===\n")

    for ins in instances:
        start_time_sol = time.time()
        
        name = ins['name']
        capacity = ins['capacity']
        items = ins['items']

        # **FFD 进行 Bin Packing**
        solution = first_fit_decreasing(items, capacity)

        # **优化箱子数量**
        optimized_solution = optimize_bins(solution, capacity)

        # **检查是否符合容量**
        if not capacity_check(optimized_solution, capacity):
            print(f"--- Error: {name} capacity exceeded! ---")
            continue

        bin_used = len(optimized_solution)

        output_json["res"].append({
            "name": name,
            "capacity": capacity,
            "solution": optimized_solution
        })

        print(f"Instance: {name}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")
        total_bins += bin_used

    total_time = time.time() - time.time()
    output_json["time"] = total_time

    with open(output_filename, "w+") as f:
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")
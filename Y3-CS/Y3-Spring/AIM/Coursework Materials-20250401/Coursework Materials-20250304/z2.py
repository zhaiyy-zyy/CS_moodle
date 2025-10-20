import json
import time
from datetime import datetime

# 读取 Bin Packing 实例
def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

# **First-Fit Decreasing (FFD)**
def first_fit_decreasing(items, bin_capacity):
    """使用 First-Fit Decreasing (FFD) 进行 Bin Packing"""
    items.sort(reverse=True)  # 按降序排序
    bins = []
    
    for item in items:
        placed = False
        for bin_items in bins:
            if sum(bin_items) + item <= bin_capacity:
                bin_items.append(item)
                placed = True
                break
        if not placed:
            bins.append([item])  # 开一个新箱子

    return bins

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

    print("\n=== 1D Bin Packing Optimization Results (FFD Only) ===\n")

    for ins in instances:
        start_time_sol = time.time()
        
        name = ins['name']
        capacity = ins['capacity']
        items = ins['items']

        # **FFD 进行 Bin Packing**
        solution = first_fit_decreasing(items, capacity)

        # **检查是否符合容量**
        if not capacity_check(solution, capacity):
            print(f"--- Error: {name} capacity exceeded! ---")
            continue

        bin_used = len(solution)

        output_json["res"].append({
            "name": name,
            "capacity": capacity,
            "solution": solution
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
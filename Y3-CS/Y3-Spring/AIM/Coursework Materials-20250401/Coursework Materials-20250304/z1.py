import json
import random
import math
import copy
import time
from datetime import datetime

start_time = time.time()

# **读取 Bin Packing 实例**
def read_bin_packing_instances(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

# **First-Fit Decreasing (FFD)**
def first_fit_decreasing(items, bin_capacity):
    items.sort(reverse=True)
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

    return bin_contents

# **Best-Fit Decreasing (BFD)**
def best_fit_decreasing(items, bin_capacity):
    items.sort(reverse=True)
    bins = []
    bin_contents = []

    for item in items:
        best_fit_idx = -1
        min_space_left = bin_capacity + 1

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

# **改进版 Simulated Annealing (SA)**
def simulated_annealing(initial_solution, bin_capacity, initial_temp=20000, cooling_rate=0.9995, min_temp=0.1, max_iterations=50000):
    current_solution = copy.deepcopy(initial_solution)
    best_solution = copy.deepcopy(current_solution)
    best_bins = len(best_solution)

    temperature = initial_temp
    iterations_without_improvement = 0

    for _ in range(max_iterations):
        if temperature < min_temp:
            break

        new_solution = copy.deepcopy(best_solution)

        if len(new_solution) > 1:
            action = random.choices(["move", "swap", "triple-swap"], weights=[50, 40, 10])[0]
            bin1, bin2 = random.sample(range(len(new_solution)), 2)

            if action == "move":  # **单个物品移动**
                if new_solution[bin1]:
                    item = max(new_solution[bin1])  # **优先移动最大物品**
                    if sum(new_solution[bin2]) + item <= bin_capacity:
                        new_solution[bin1].remove(item)
                        new_solution[bin2].append(item)

            elif action == "swap":  # **两个物品交换**
                if new_solution[bin1] and new_solution[bin2]:
                    item1 = random.choice(new_solution[bin1])
                    item2 = random.choice(new_solution[bin2])

                    if (
                        sum(new_solution[bin1]) - item1 + item2 <= bin_capacity and
                        sum(new_solution[bin2]) - item2 + item1 <= bin_capacity
                    ):
                        new_solution[bin1].remove(item1)
                        new_solution[bin2].remove(item2)
                        new_solution[bin1].append(item2)
                        new_solution[bin2].append(item1)

            elif action == "triple-swap":  # **三箱优化**
                if len(new_solution) > 2:
                    bin3 = random.choice([i for i in range(len(new_solution)) if i not in [bin1, bin2]])
                    if new_solution[bin1] and new_solution[bin2] and new_solution[bin3]:
                        item1 = random.choice(new_solution[bin1])
                        item2 = random.choice(new_solution[bin2])
                        item3 = random.choice(new_solution[bin3])

                        if (
                            sum(new_solution[bin1]) - item1 + item2 <= bin_capacity and
                            sum(new_solution[bin2]) - item2 + item3 <= bin_capacity and
                            sum(new_solution[bin3]) - item3 + item1 <= bin_capacity
                        ):
                            new_solution[bin1].remove(item1)
                            new_solution[bin2].remove(item2)
                            new_solution[bin3].remove(item3)
                            new_solution[bin1].append(item2)
                            new_solution[bin2].append(item3)
                            new_solution[bin3].append(item1)

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

        if iterations_without_improvement > 7000:
            break

    return best_solution

if __name__ =="__main__":
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

        ffd_solution = first_fit_decreasing(items, capacity)
        bfd_solution = best_fit_decreasing(items, capacity)

        initial_solution = min(ffd_solution, bfd_solution, key=len)

        optimized_solution = simulated_annealing(initial_solution, capacity)

        bin_used = len(optimized_solution)

        output_json["res"].append({
            "name": name,
            "capacity": capacity,
            "solution": optimized_solution
        })

        print(f"Instance: {name}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time()-start_time_sol:.4f}s)")
        total_bins += bin_used

    with open(output_filename, "w+") as f:
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
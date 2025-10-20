import json
import random
import math
import copy
import subprocess

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

def process_bin_packing(input_file, output_file):
    """运行 Bin Packing 并优化 SA 使得结果接近 Best-Known 结果"""
    with open(input_file, 'r') as file:
        instances = json.load(file)

    results = []
    total_used_bins = 0

    for instance in instances:
        name = instance["name"]
        capacity = instance["capacity"]
        items = instance["items"]

        # 运行 SA 算法
        sa_bins = simulated_annealing(items, capacity, target_bins=9999)  # 默认目标值较大

        total_used_bins += sa_bins

        results.append({
            "instance": name,
            "SA_bins_used": sa_bins
        })

    with open(output_file, 'w') as file:
        json.dump(results, file, indent=4)

    print(f"\n✅ 结果已保存至 {output_file}")

def run_marker_and_compare(solution_file):
    """运行 CW_marker.py 并比较误差率"""
    print("\n=== 📊 正在运行 CW_marker.py 进行误差对比... ===")

    # 运行 CW_marker.py
    try:
        result = subprocess.run(["python3", "CW_marker.py", solution_file], capture_output=True, text=True)
        expected_output = json.loads(result.stdout)  # 解析期望的最佳解
    except Exception as e:
        print(f"❌ 运行 CW_marker.py 失败: {e}")
        return

    # 读取我们生成的 solution.json
    with open(solution_file, 'r') as file:
        sa_output = json.load(file)

    print("\n=== 📊 误差分析 ===")
    print(f"{'Instance':<20}{'SA Used':<10}{'Expected':<10}{'Error %':<10}")
    print("-" * 50)

    total_error = 0
    total_instances = 0

    for instance in sa_output:
        name = instance["instance"]
        sa_bins_used = instance["SA_bins_used"]
        expected_bins_used = expected_output.get(name, sa_bins_used)  # 获取最佳值

        # 计算误差
        error_rate = abs(sa_bins_used - expected_bins_used) / expected_bins_used * 100

        print(f"{name:<20}{sa_bins_used:<10}{expected_bins_used:<10}{error_rate:.2f}%")

        total_error += error_rate
        total_instances += 1

    avg_error = total_error / total_instances if total_instances > 0 else 0
    print("\n=== 📊 误差总结 ===")
    print(f"✅ 平均误差率: {avg_error:.2f}%")

if __name__ == "__main__":
    solution_file = "solution.json"

    # 1. 运行 Bin Packing 生成 solution.json
    process_bin_packing("CW_ins.json", solution_file)

    # 2. 运行 CW_marker.py 并计算误差率
    run_marker_and_compare(solution_file)
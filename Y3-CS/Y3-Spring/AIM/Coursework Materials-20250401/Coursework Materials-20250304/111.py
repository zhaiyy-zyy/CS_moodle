import json
import time
import random
import math

# 读取 JSON 数据
with open("CW_ins.json", "r") as file:
    instances = json.load(file)

# Best-Fit Decreasing (BFD) 算法
def best_fit_decreasing(items, bin_capacity):
    bins = []
    items.sort(reverse=True)  # 按体积降序排序

    for item in items:
        best_bin_index = -1
        min_space_left = float('inf')

        # 在已有箱子中寻找最适合的
        for i, bin in enumerate(bins):
            space_left = bin_capacity - sum(bin)
            if space_left >= item and space_left < min_space_left:
                best_bin_index = i
                min_space_left = space_left

        if best_bin_index != -1:
            bins[best_bin_index].append(item)
        else:
            bins.append([item])  # 开新箱子

    return bins

# Simulated Annealing (SA) 算法优化
def simulated_annealing(bins, bin_capacity, initial_temp=100, cooling_rate=0.95, min_temp=0.1):
    current_bins = bins[:]
    best_bins = bins[:]
    temperature = initial_temp

    while temperature > min_temp:
        # 随机选择两个不同的箱子
        bin1, bin2 = random.sample(range(len(current_bins)), 2)

        if current_bins[bin1] and current_bins[bin2]:  # 确保两个箱子都不为空
            # 从 bin1 取出一个随机物品，尝试放入 bin2
            item_index = random.randint(0, len(current_bins[bin1]) - 1)
            item = current_bins[bin1].pop(item_index)

            if sum(current_bins[bin2]) + item <= bin_capacity:
                current_bins[bin2].append(item)  # 成功交换
                if not current_bins[bin1]:  # 如果 bin1 变空了，删除这个箱子
                    current_bins.pop(bin1)

                # 计算新的方案
                if len(current_bins) < len(best_bins):  # 更少箱子，更新最优解
                    best_bins = current_bins[:]

            else:
                current_bins[bin1].append(item)  # 不能交换，恢复原状

        # 退火（降低温度）
        temperature *= cooling_rate

    return best_bins

# 处理所有实例
solution = {}
total_bins = 0
output_filename = "123456_xinan_chen.json"

start_time = time.time()

for instance in instances:
    name = instance["name"]
    capacity = instance["capacity"]
    items = instance["items"]
    best_known = instance.get("best_known", "N/A")  # 预期最佳值

    instance_start_time = time.time()

    # 1️⃣ 先使用 Best-Fit Decreasing (BFD) 生成初始解
    initial_bins = best_fit_decreasing(items, capacity)

    # 2️⃣ 再使用 Simulated Annealing (SA) 进行优化
    optimized_bins = simulated_annealing(initial_bins, capacity)

    instance_time = time.time() - instance_start_time

    solution[name] = {
        "bins_used": len(optimized_bins),
        "bins": optimized_bins,
        "time": round(instance_time, 4),
        "best_known": best_known
    }

    total_bins += len(optimized_bins)

    print(f"Instance: {name}")
    print(f"Bins Used:\t{len(optimized_bins)} (Time: {instance_time:.4f}s) | Best Known: {best_known}")

# 计算总执行时间
total_time = time.time() - start_time

# **保存 JSON 文件**
with open(output_filename, 'w') as f:
    json.dump(solution, f, indent=4)

print("\n--- Summary ---")
print(f"Output saved to {output_filename}")
print(f"Total Used Bins: {total_bins}")
print(f"Total Execution Time: {total_time:.4f}s")
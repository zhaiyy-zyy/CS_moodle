import json
import time

# 读取 JSON 数据
with open("CW_ins.json", "r") as file:
    instances = json.load(file)

# Offline Search (回溯 + 剪枝)
def offline_search(items, bin_capacity, bins=[], best_solution=None):
    if best_solution is None:
        best_solution = [None]  # 用于存储最优解（使用 list 以便修改）

    # 剪枝条件：如果当前解已经比最优解差，就终止
    if best_solution[0] is not None and len(bins) >= len(best_solution[0]):
        return

    # 所有物品都已放入箱子，更新最优解
    if not items:
        best_solution[0] = bins[:]
        return

    # 取出当前物品
    item = items[0]
    remaining_items = items[1:]

    # 尝试放入已有的箱子
    for i, bin in enumerate(bins):
        if sum(bin) + item <= bin_capacity:
            bin.append(item)
            offline_search(remaining_items, bin_capacity, bins, best_solution)
            bin.pop()  # 回溯，尝试其他分配方式

    # 尝试开一个新箱子
    new_bin = [item]
    bins.append(new_bin)
    offline_search(remaining_items, bin_capacity, bins, best_solution)
    bins.pop()  # 回溯

    return best_solution[0]  # 返回最优解

# 处理所有实例
solution = {}
total_bins = 0  # 统计总使用箱子数
output_filename = "123456_xinan_chen.json"

start_time = time.time()  # 记录开始时间

for instance in instances:
    name = instance["name"]
    capacity = instance["capacity"]
    items = instance["items"]
    best_known = instance.get("best_known", "N/A")  # 预期最佳值

    instance_start_time = time.time()  # 记录单个实例执行时间
    bins = offline_search(items, capacity)
    instance_time = time.time() - instance_start_time  # 计算单个实例执行时间

    solution[name] = {
        "bins_used": len(bins),
        "bins": bins,
        "time": round(instance_time, 4),  # 保留 4 位小数
        "best_known": best_known
    }

    total_bins += len(bins)

    # **按照格式打印每个实例的结果**
    print(f"Instance: {name}")
    print(f"Bins Used:\t{len(bins)} (Time: {instance_time:.4f}s) | Best Known: {best_known}")

# 计算总执行时间
total_time = time.time() - start_time

# **保存结果到 JSON 文件**
with open(output_filename, 'w') as f:
    json.dump(solution, f, indent=4)

# **打印最终 Summary**
print("\n--- Summary ---")
print(f"Output saved to {output_filename}")
print(f"Total Used Bins: {total_bins}")
print(f"Total Execution Time: {total_time:.4f}s")
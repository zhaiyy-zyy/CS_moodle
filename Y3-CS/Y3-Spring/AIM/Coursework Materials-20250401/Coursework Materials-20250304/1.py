import json
import time

# 读取 JSON 数据
with open("CW_ins.json", "r") as file:
    instances = json.load(file)

# First-Fit Decreasing (FFD) 算法实现
def first_fit_decreasing(items, bin_capacity):
    bins = []  # 存储所有箱子
    items.sort(reverse=True)  # 按体积从大到小排序

    for item in items:
        placed = False
        for bin in bins:
            if sum(bin) + item <= bin_capacity:
                bin.append(item)
                placed = True
                break
        if not placed:
            bins.append([item])  # 开一个新箱子

    return bins

# 处理所有实例
solution = {}
total_bins = 0  # 统计总使用箱子数
output_filename = "123456_xinan_chen.json"

start_time = time.time()  # 记录开始时间

for instance in instances:
    name = instance["name"]
    capacity = instance["capacity"]
    items = instance["items"]

    instance_start_time = time.time()  # 记录单个实例执行时间
    bins = first_fit_decreasing(items, capacity)
    instance_time = time.time() - instance_start_time  # 计算单个实例执行时间

    solution[name] = {
        "bins_used": len(bins),
        "bins": bins,
        "time": round(instance_time, 4)  # 保留 4 位小数
    }

    total_bins += len(bins)

    # **按照格式打印每个实例的结果**
    print(f"Instance: {name}")
    print(f"Bins Used:\t{len(bins)} (Time: {instance_time:.4f}s)")

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
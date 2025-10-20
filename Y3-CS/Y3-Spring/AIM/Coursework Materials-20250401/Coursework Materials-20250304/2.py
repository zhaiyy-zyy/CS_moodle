import json

# 读取 JSON 数据
with open("CW_ins.json", "r") as file:
    instances = json.load(file)

# Best-Fit Decreasing (BFD) 算法
def best_fit_decreasing(items, bin_capacity):
    bins = []
    items.sort(reverse=True)  # 先按降序排列

    for item in items:
        best_bin_index = -1
        min_space_left = bin_capacity + 1

        # 找到当前剩余空间最少但仍能放入 item 的箱子
        for i in range(len(bins)):
            space_left = bin_capacity - sum(bins[i])
            if space_left >= item and space_left < min_space_left:
                best_bin_index = i
                min_space_left = space_left

        if best_bin_index != -1:
            bins[best_bin_index].append(item)
        else:
            bins.append([item])  # 开新箱子

    return bins

# 局部优化（Local Search）优化箱子使用率
def advanced_local_search(bins, bin_capacity):
    """
    进行更强的局部优化：尝试跨箱交换物品以减少箱子数
    """
    improved = True
    while improved:
        improved = False
        bins.sort(key=len, reverse=True)

        for i in range(len(bins)):
            for j in range(i + 1, len(bins)):
                space_i = bin_capacity - sum(bins[i])
                space_j = bin_capacity - sum(bins[j])

                # 尝试交换两个箱子的物品
                for item in bins[i][:]:
                    if item <= space_j:
                        bins[j].append(item)
                        bins[i].remove(item)
                        space_j -= item
                        improved = True

                if len(bins[i]) == 0:
                    bins.pop(i)
                    improved = True
                    break

    return bins

# 处理所有实例
solution = {}
for instance in instances:
    name = instance["name"]
    capacity = instance["capacity"]
    items = instance["items"]
    
    # Step 1: 使用 BFD 进行初步装箱
    bins = best_fit_decreasing(items, capacity)
    
    # Step 2: 运行局部优化，减少箱子数
    bins = local_search(bins, capacity)

    solution[name] = {
        "bins_used": len(bins),
        "bins": bins
    }

# 保存 JSON 结果
with open("solution.json", "w") as file:
    json.dump(solution, file, indent=4)

print("Optimized solution saved as solution.json")
#!/usr/bin/env python3
"""
声明：本代码在编写过程中使用了 AI 辅助，但所有代码均为本人理解后独立编写。
"""

import json
import math
import sys
import time

# 为防止递归层数过深，适当增大递归上限
sys.setrecursionlimit(10000)


def ffd_upper_bound(items, capacity):
    """
    First-Fit Decreasing (FFD) 算法获得一个上界
    输入：
        items: 物品体积列表
        capacity: bin 的容量
    返回：
        使用 bin 数量的上界
    """
    bins = []
    for item in sorted(items, reverse=True):
        placed = False
        for i in range(len(bins)):
            if bins[i] >= item:
                bins[i] -= item
                placed = True
                break
        if not placed:
            bins.append(capacity - item)
    return len(bins)


def optimal_bin_packing(items, capacity):
    """
    分支界限法求解 1D Bin Packing 的最优解。
    输入：
        items: 物品体积列表
        capacity: bin 的容量
    返回：
        best_bins: 最优使用的 bin 数量
        best_solution: 最优解对应的 bin 分配方案（每个 bin 列表中保存放入物品的体积）
    """
    # 对物品降序排序，排序后只需要关心物品体积
    sorted_items = sorted(items, reverse=True)
    n = len(sorted_items)
    
    # 初始上界：使用 FFD 算法获得的解
    best_bins = ffd_upper_bound(items, capacity)
    best_solution = None

    def search(index, bins_remaining, current_solution):
        nonlocal best_bins, best_solution

        # 当所有物品都已经安排完毕时，更新最优解
        if index == n:
            if len(bins_remaining) < best_bins:
                best_bins = len(bins_remaining)
                # 复制当前解（深拷贝每个 bin 的列表）
                best_solution = [bin_items.copy() for bin_items in current_solution]
            return

        # 计算下界：已用 bin 数量 + 剩余物品总体积的最少需要 bin 数
        remaining_sum = sum(sorted_items[index:])
        lower_bound = len(bins_remaining) + math.ceil(remaining_sum / capacity)
        if lower_bound >= best_bins:
            return

        item = sorted_items[index]
        used = set()  # 记录已经尝试过的剩余空间，避免重复状态
        # 尝试将当前物品放入已有的 bin
        for i in range(len(bins_remaining)):
            if bins_remaining[i] >= item and bins_remaining[i] not in used:
                used.add(bins_remaining[i])
                bins_remaining[i] -= item
                current_solution[i].append(item)
                search(index + 1, bins_remaining, current_solution)
                current_solution[i].pop()
                bins_remaining[i] += item

        # 尝试新开一个 bin 放置当前物品
        bins_remaining.append(capacity - item)
        current_solution.append([item])
        search(index + 1, bins_remaining, current_solution)
        bins_remaining.pop()
        current_solution.pop()

    search(0, [], [])
    return best_bins, best_solution


def solve_instance(instance):
    """
    求解单个实例，返回一个字典：
      - name: 实例名称
      - capacity: bin 容量
      - bins_used: 最优解使用的 bin 数量
      - bin_assignment: 每个 bin 内放置的物品体积列表
    """
    name = instance["name"]
    capacity = instance["capacity"]
    items = instance["items"]

    print(f"开始求解实例 {name} (bin容量={capacity}, 物品数={len(items)}) ...")
    start_time = time.time()
    bins_used, bin_assignment = optimal_bin_packing(items, capacity)
    end_time = time.time()
    print(f"实例 {name} 求解结束：使用 {bins_used} 个 bin，耗时 {end_time - start_time:.2f} 秒")
    
    return {
        "name": name,
        "capacity": capacity,
        "bins_used": bins_used,
        "bin_assignment": bin_assignment
    }


def main():
    # 读取输入 JSON 文件（假设文件名为 "CW_ins.json"）
    with open("CW_ins.json", "r") as infile:
        instances = json.load(infile)

    # 对每个实例求解
    solutions = []
    for instance in instances:
        sol = solve_instance(instance)
        solutions.append(sol)

    # 将所有实例的求解结果写入输出 JSON 文件
    with open("CW_solution.json", "w") as outfile:
        json.dump(solutions, outfile, indent=4)

    print("所有实例求解完毕，结果保存在 CW_solution.json 文件中。")


if __name__ == '__main__':
    main()
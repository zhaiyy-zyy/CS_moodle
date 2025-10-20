import json
import random
import time
from datetime import datetime
import copy

# 记录程序开始时间
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    """ 读取 JSON 格式的 bin packing 实例 """
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def first_fit_decreasing(items, capacity):
    """ 使用 First Fit Decreasing 算法进行装箱 """
    # 对物品按大小降序排列
    sorted_items = sorted(items, reverse=True)
    bins = []  # 存储每个bin的剩余空间
    bin_items = []  # 存储每个bin内的物品
    
    for item in sorted_items:
        placed = False
        for i, remaining in enumerate(bins):
            if item <= remaining:
                bins[i] -= item
                bin_items[i].append(item)
                placed = True
                break
        
        if not placed:
            bins.append(capacity - item)
            bin_items.append([item])
            
    return bin_items

def best_fit_decreasing(items, capacity):
    """ 使用 Best Fit Decreasing 算法进行装箱 """
    # 对物品按大小降序排列
    sorted_items = sorted(items, reverse=True)
    bins = []  # 存储每个bin的剩余空间
    bin_items = []  # 存储每个bin内的物品
    
    for item in sorted_items:
        best_bin_index = -1
        min_remaining_space = capacity + 1
        
        for i, remaining in enumerate(bins):
            if item <= remaining and remaining - item < min_remaining_space:
                best_bin_index = i
                min_remaining_space = remaining - item
        
        if best_bin_index != -1:
            bins[best_bin_index] -= item
            bin_items[best_bin_index].append(item)
        else:
            bins.append(capacity - item)
            bin_items.append([item])
    
    return bin_items

def best_fit(items, capacity):
    """ 使用 Best Fit 算法进行装箱 """
    bins = []  # 存储 bin 剩余空间
    bin_items = []  # 存储每个 bin 内的物品

    for item in items:
        best_bin_index = -1
        min_remaining_space = capacity + 1  # 初始化为一个大值

        # 遍历所有 bin，找到最适合的 bin
        for i, remaining_space in enumerate(bins):
            if item <= remaining_space and remaining_space - item < min_remaining_space:
                best_bin_index = i
                min_remaining_space = remaining_space - item

        # 如果找到合适的 bin，就放入
        if best_bin_index != -1:
            bins[best_bin_index] -= item
            bin_items[best_bin_index].append(item)
        else:
            # 如果没有合适的 bin，则创建新 bin
            bins.append(capacity - item)
            bin_items.append([item])

    return bin_items

def local_search(solution, capacity, max_iterations=2000):
    """ 局部搜索优化，尝试通过移动物品减少箱子数量 """
    best_solution = [bin[:] for bin in solution]  # 使用列表切片代替deepcopy
    best_bin_count = len(solution)
    no_improvement_count = 0
    
    for _ in range(max_iterations):
        if len(best_solution) < 2 or no_improvement_count > 100:  # 增加提前终止条件
            break
            
        # 随机选择两个不同的箱子
        i = random.randint(0, len(best_solution) - 1)
        j = random.randint(0, len(best_solution) - 1)
        while i == j:
            j = random.randint(0, len(best_solution) - 1)
            
        # 预先计算目标箱子的总和
        target_bin_sum = sum(best_solution[j])
            
        # 尝试将物品从箱子i移动到箱子j
        for item_idx, item in enumerate(best_solution[i]):
            if target_bin_sum + item <= capacity:
                # 直接修改当前解而不是创建新解
                best_solution[j].append(item)
                best_solution[i].pop(item_idx)
                
                # 如果箱子i变空，移除它
                if not best_solution[i]:
                    best_solution.pop(i)
                    
                # 如果新解更好，更新最佳解和计数器
                current_bin_count = len(best_solution)
                if current_bin_count < best_bin_count:
                    best_bin_count = current_bin_count
                    no_improvement_count = 0
                    break
                else:
                    no_improvement_count += 1
                    break
    
    return best_solution

def post_process_optimization(solution, capacity):
    """ 后处理优化，尝试合并箱子 """
    # 按箱子内物品总和排序
    sorted_bins = sorted(solution, key=lambda x: sum(x))
    new_solution = []
    
    used = [False] * len(sorted_bins)
    
    for i in range(len(sorted_bins)):
        if used[i]:
            continue
            
        current_bin = sorted_bins[i].copy()
        used[i] = True
        
        # 尝试合并其他箱子
        for j in range(i+1, len(sorted_bins)):
            if used[j]:
                continue
                
            if sum(current_bin) + sum(sorted_bins[j]) <= capacity:
                current_bin.extend(sorted_bins[j])
                used[j] = True
        
        new_solution.append(current_bin)
    
    return new_solution

def next_fit(items, capacity):
    bins = []
    current_bin = []

    for item in items:
        if sum(current_bin) + item <= capacity:
            current_bin.append(item)
        else:
            bins.append(current_bin)
            current_bin = [item]
    bins.append(current_bin) # Add the last bin

    return bins

def advanced_fit(items, capacity):
    """高级装箱算法，集成了多种策略"""
    # 首先尝试多种确定性算法
    solutions = []
    
    # 1. 使用First Fit Decreasing
    items_sorted = sorted(items, reverse=True)
    ffd_solution = first_fit_decreasing(items_sorted, capacity)
    solutions.append(ffd_solution)
    
    # 2. 使用Best Fit Decreasing
    bfd_solution = best_fit_decreasing(items_sorted, capacity)
    solutions.append(bfd_solution)
    
    # 3. 使用Best Fit
    bf_solution = best_fit(items, capacity)
    solutions.append(bf_solution)
    
    # 选择最佳基础解
    best_solution = min(solutions, key=len)
    
    # 应用局部搜索和后处理优化
    optimized_solution = local_search(best_solution, capacity, max_iterations=2000)
    if len(optimized_solution) < len(best_solution):
        best_solution = optimized_solution
    
    post_solution = post_process_optimization(best_solution, capacity)
    if len(post_solution) < len(best_solution):
        best_solution = post_solution
    
    return best_solution

import math

def simulated_annealing(items, capacity, fit_fun, initial_temperature=1000, cooling_rate=0.995, max_iterations=10000):
    """ 使用模拟退火算法优化装箱方案 """
    # 生成初始解
    current_solution = fit_fun(items, capacity)
    current_bins = len(current_solution)
    
    # 设置初始温度和温度衰减率
    temperature = initial_temperature
    best_solution = current_solution
    best_bins = current_bins

    for iteration in range(max_iterations):
        if temperature < 1e-3:  # 温度低于阈值，退出
            break

        # 生成邻域解：通过局部搜索产生新解
        new_solution = local_search(current_solution, capacity, max_iterations=200)  # 可以调用局部搜索方法
        new_bins = len(new_solution)

        # 计算接受概率
        if new_bins < current_bins:  # 如果新解更好，直接接受
            current_solution = new_solution
            current_bins = new_bins
        else:
            # 如果新解更差，则以一定的概率接受
            acceptance_probability = math.exp((current_bins - new_bins) / temperature)
            if random.random() < acceptance_probability:
                current_solution = new_solution
                current_bins = new_bins

        # 如果找到更好的解，更新最佳解
        if current_bins < best_bins:
            best_solution = current_solution
            best_bins = current_bins

        # 降低温度
        temperature *= cooling_rate

    return best_solution
def advanced_fit_with_sa(items, capacity):
    """ 使用模拟退火算法进行装箱优化 """
    return simulated_annealing(items, capacity, advanced_fit)

if __name__ == "__main__":
    # 设置随机种子以保证可复现性
    random.seed(0)
    json_file_path = 'CW_ins.json'
    output_filename = '123456_xinan_chen.json'
    instances = read_bin_packing_instances(json_file_path)

    total_bins = 0
    output_json = {}
    output_json['date'] = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    output_json['time'] = 0
    output_json['res'] = []

    # Main Content
    #######################################################################
    for ins in instances:
        start_time_sol = time.time()
        
        # 使用高级装箱算法
        solution = advanced_fit_with_sa(ins['items'], ins['capacity'])
        
        # 保存结果
        output_json['res'].append({})
        output_json['res'][-1]['name'] = ins['name']
        output_json['res'][-1]['capacity'] = ins['capacity']
        output_json['res'][-1]['solution'] = solution
        bin_used = len(solution)

        print(f"Instance: {ins['name']}")
        print(f"Bins Used:\t{bin_used} (Time: {time.time() - start_time_sol:.4f}s)")
        total_bins += bin_used
    ########################################################################

    total_time = time.time() - start_time
    
    with open(output_filename, 'w') as f:
        output_json['time'] = total_time
        json.dump(output_json, f, indent=4)

    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins}")
    print(f"Total Execution Time: {total_time:.4f}s")

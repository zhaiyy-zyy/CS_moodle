import json
import time
from datetime import datetime
import random
# 记录程序开始时间
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    """ 读取 JSON 格式的 bin packing 实例 """
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def first_fit_decreasing(items, capacity):
    """ 使用 First Fit Decreasing 算法进行装箱 """
    sorted_items = sorted(items, reverse=True)  # 按物品大小降序排序
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
    sorted_items = sorted(items, reverse=True)  # 按物品大小降序排序
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

        for i, remaining_space in enumerate(bins):
            if item <= remaining_space and remaining_space - item < min_remaining_space:
                best_bin_index = i
                min_remaining_space = remaining_space - item

        if best_bin_index != -1:
            bins[best_bin_index] -= item
            bin_items[best_bin_index].append(item)
        else:
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
                best_solution[j].append(item)
                best_solution[i].pop(item_idx)
                
                if not best_solution[i]:
                    best_solution.pop(i)
                    
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
    sorted_bins = sorted(solution, key=lambda x: sum(x))
    new_solution = []
    
    used = [False] * len(sorted_bins)
    
    for i in range(len(sorted_bins)):
        if used[i]:
            continue
            
        current_bin = sorted_bins[i].copy()
        used[i] = True
        
        for j in range(i+1, len(sorted_bins)):
            if used[j]:
                continue
                
            if sum(current_bin) + sum(sorted_bins[j]) <= capacity:
                current_bin.extend(sorted_bins[j])
                used[j] = True
        
        new_solution.append(current_bin)
    
    return new_solution

def advanced_fit(items, capacity):
    """高级装箱算法，集成了多种策略"""
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

def heuristic_search_fit(items, capacity, fit_fun, iterations=15000):
    """ 使用启发式搜索替代随机搜索进行装箱优化 """
    best_solution = None
    min_bins = float('inf')
    
    # 保存原始物品列表
    original_items = items.copy()
    
    # 使用提供的装箱函数获得初始解
    initial_solution = fit_fun(original_items.copy(), capacity)
    best_solution = initial_solution
    min_bins = len(initial_solution)
    
    # 启发式搜索优化
    for i in range(iterations):
        items_copy = original_items.copy()
        
        # 1. 按照物品的大小降序排序
        items_copy.sort(reverse=True)  
        
        # 2. 使用启发式算法进行装箱
        current_solution = fit_fun(items_copy, capacity)
        
        # 更新最佳解
        if len(current_solution) < min_bins:
            min_bins = len(current_solution)
            best_solution = current_solution
        
        # 应用局部搜索和后处理优化
        if i % 5 == 0:  # 每5次迭代进行一次局部搜索
            current_solution = local_search(current_solution, capacity, max_iterations=1000)
            current_solution = post_process_optimization(current_solution, capacity)
    
    return best_solution

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
        
        # 使用启发式搜索算法
        solution = heuristic_search_fit(ins['items'], ins['capacity'], advanced_fit)
        
        # 保存结果
        output_json['res'].append({})
        output_json['res'][-1]['name'] = ins['name']
        output_json['res'][-1]['capacity'] = ins['capacity']
        output_json['res'][-1]['solution'] = solution
        bin_used = len(solution)

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
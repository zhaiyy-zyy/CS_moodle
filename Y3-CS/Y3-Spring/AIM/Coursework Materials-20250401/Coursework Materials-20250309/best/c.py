import json
import random
import time
from datetime import datetime

# 记录程序开始时间
start_time = time.time()

def read_bin_packing_instances(json_file_path):
    """ 读取 JSON 格式的 bin packing 实例 """
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

def best_fit(items, capacity):
    """ 使用 Best Fit 算法进行装箱 """
    bins = []  # 存储每个 bin 的剩余空间
    bin_items = []  # 存储 bin 里面的物品

    for item in items:
        best_bin_index = -1
        min_remaining_space = capacity + 1  # 设置一个大于最大可能剩余空间的值

        # 找到当前最适合的 bin（剩余空间最小但能容纳物品）
        for i, remaining_space in enumerate(bins):
            if 0 <= remaining_space - item < min_remaining_space:
                best_bin_index = i
                min_remaining_space = remaining_space - item

        # 如果找到了合适的 bin，则放入
        if best_bin_index != -1:
            bins[best_bin_index] -= item
            bin_items[best_bin_index].append(item)
        else:
            # 如果没有合适的 bin，则创建新 bin
            bins.append(capacity - item)
            bin_items.append([item])

    return bin_items  # 返回所有的 bin

def random_search_fit(items, capacity, fit_fun, iterations=10000):
    """ 通过随机搜索优化装箱方案 """
    best_solution = None
    min_bins = float('inf')  # 记录最少的 bin 数

    for _ in range(iterations):
        random.shuffle(items)  # 随机排序
        current_solution = fit_fun(items, capacity)  # 运行 fit 算法
        bin_count = len(current_solution)

        if bin_count < min_bins:
            min_bins = bin_count
            best_solution = current_solution  # 仅在更优解出现时更新

    return best_solution

if __name__ == "__main__":
    # 设置随机种子以保证可复现性
    random.seed(0)

    json_file_path = 'cw_ins.json'
    output_filename = '123456_xinan_chen.json'
    instances = read_bin_packing_instances(json_file_path)

    # 预设最佳 bin 数量
    best_known_bins = {
        "instance 1": 52,
        "instance 2": 59,
        "instance 3": 24,
        "instance 4": 27,
        "instance 5": 47,
        "instance 6": 49,
        "instance 7": 36,
        "instance 8": 52,
        "instance large 9": 417,
        "instance large 10": 375
    }

    total_bins = 0
    output_json = {
        'date': datetime.today().strftime('%Y-%m-%d %H:%M:%S'),
        'time': 0,
        'res': []
    }

    print("\n--- Best-known results ---")

    # 处理所有 bin packing 实例
    for ins in instances:
        start_time_sol = time.time()
        
        # 执行 Best Fit + 随机搜索
        solution = random_search_fit(ins['items'], ins['capacity'], best_fit)
        
        # 计算 bin 使用数量
        bin_used = len(solution)
        total_bins += bin_used

        # 获取最佳已知解
        best_known = best_known_bins.get(ins['name'], "Unknown")

        # 保存并打印输出
        output_json['res'].append({
            'name': ins['name'],
            'capacity': ins['capacity'],
            'solution': solution,
            'bins_used': bin_used,
            'best_known': best_known
        })

        elapsed_time = time.time() - start_time_sol
        print(f"• Instance: {ins['name']} Best known of bins used: {best_known} | Found: {bin_used} | Time: {elapsed_time:.4f}s")

    # 计算总执行时间
    total_time = time.time() - start_time
    output_json['time'] = total_time

    # 写入 JSON 文件
    with open(output_filename, 'w') as f:
        json.dump(output_json, f, indent=4)

    # 总结结果
    print("\n--- Summary ---")
    print(f"Output saved to {output_filename}")
    print(f"Total Used Bins: {total_bins} (Best Known Total: 1138)")
    print(f"Total Execution Time: {total_time:.4f}s")
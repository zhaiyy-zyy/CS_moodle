# dataset_analyzer.py
import json
from collections import defaultdict


def read_json(json_file_path):
    with open(json_file_path, 'r') as file:
        return json.load(file)


def analyze_dataset(json_file_path):
    """分析装箱问题数据集"""
    print(f"{'Instance Name':<20} | {'Item Count':>10} | {'Capacity':>10} | {'Avg Size':>10} | {'Max Size':>10}")
    print("-" * 75)

    stats = defaultdict(list)
    try:
        instances = read_json("CW_ins.json")
    except FileNotFoundError:
        print(f"Error: File {json_file_path} not found!")
        return
    except json.JSONDecodeError:
        print(f"Error: Invalid JSON format in {json_file_path}!")
        return

    for instance in instances:
        name = instance.get('name', 'Unnamed_Instance')
        items = instance.get('items', [])
        capacity = instance.get('capacity', 0)

        # 基础统计
        count = len(items)
        avg_size = round(sum(items) / count, 2) if count > 0 else 0
        max_size = max(items) if items else 0

        # 记录统计数据
        stats['total_items'].extend(items)
        stats['instance_counts'].append(count)

        print(f"{name:<20} | {count:>10} | {capacity:>10} | {avg_size:>10} | {max_size:>10}")

    # 输出汇总统计
    print("\n=== Summary Statistics ===")
    print(f"Total Instances: {len(instances)}")
    print(f"Total Items: {len(stats['total_items'])}")

    if instances:
        avg_items = sum(stats['instance_counts']) / len(instances)
        print(f"Average Items/Instance: {round(avg_items, 1)}")
        print(f"Largest Instance: {max(stats['instance_counts'])} items")
        print(f"Smallest Instance: {min(stats['instance_counts'])} items")
    else:
        print("No valid instances found in the dataset")


if __name__ == "__main__":
    import sys

    if len(sys.argv) != 2:
        print("Usage: python dataset_analyzer.py <input_json_file>")
        sys.exit(1)

    analyze_dataset(sys.argv[1])

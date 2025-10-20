import json

def read_json(json_file_path):
    with open(json_file_path, 'r') as file:
        instances = json.load(file)
    return instances

instances = read_json("CW_ins.json")
solution = read_json("123456_xinan_chen.json")
best_known = [
    52,
    59,
    24,
    27,
    47,
    49,
    36,
    52,
    417,
    375
]


#check 
passed = 1
if solution['time'] > 300: 
    print("Time exceed limit!")
else:
    results = solution['res']
    total_bin_num = 0
    total_mark = 0
    total_bonus = 0
    for ins, res, bk in zip(instances, results, best_known):
        items = ins['items']
        capacity = ins['capacity']
        name  = ins['name']

        sol = res['solution']
        sol_items = []
        bin_num = len(sol)

        if name != res['name']:
            print("\n--- Error ---")
            print('Instance: '+ name, "\t Instance name error!")
            print("Instance name error!\n")
            passed = False

        for bin in sol:
            if sum(bin) > capacity:
                print("\n--- Error ---")
                print('Instance: '+ name, "\t Capacity error!\n")
                passed = False
            sol_items+=bin
        

        if sorted(items) != sorted(sol_items):
            print("\n--- Error ---")
            print('Instance: '+ name, "\t Item list error!\n")
            passed = False

        gap = bin_num - bk
        mark = 0
        bonus  = 0
        if gap < 0:
            bonus = 3
            mark = 3
        elif gap ==0:
            mark = 3
        elif gap <= 1:
            mark = 2
        elif gap <= 2:
            mark = 1
        elif gap <= 3:
            mark = 0.5

        print('Instance: '+ name, "\tMark:", mark, "\tBonus:", bonus, "\tBins used/Best known:", str(bin_num)+'/'+str(bk))
        total_bin_num += bin_num
        total_mark += mark
        total_bonus += bonus


    if passed:
        print("\n--- Summary ---")
        print("Total Bin:", total_bin_num,
              "\nRun Time: ",round(solution['time'],2), "s",  
              "\nBonus mark:", total_bonus,
              "\nTotal mark:", total_mark+total_bonus,"/ 30", 
              "\nPassed")
    else:
        print("\n--- Summary ---")
        print("Total Bin:", total_bin_num,
              "\nRun Time: ",round(solution['time'],2), "s",  
              "\nTotal mark: 0 / 30", 
              "\nFaild")
    



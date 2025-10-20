def hybrid_best_fit(items, bin_capacity):
    bins = []
    items.sort(reverse=True)  # 降序排列

    for item in items:
        best_bin_index = -1
        min_space_left = bin_capacity + 1

        # 先尝试 Best-Fit
        for i in range(len(bins)):
            space_left = bin_capacity - sum(bins[i])
            if space_left >= item and space_left < min_space_left:
                best_bin_index = i
                min_space_left = space_left

        if best_bin_index != -1:
            bins[best_bin_index].append(item)
        else:
            bins.append([item])  # 开新箱子

    # 运行 First-Fit 优化
    bins = first_fit_optimization(bins, bin_capacity)
    return bins

def first_fit_optimization(bins, bin_capacity):
    """ 在 BFD 结果上运行 First-Fit 进行调整 """
    new_bins = []

    for bin in bins:
        for item in bin:
            placed = False
            for new_bin in new_bins:
                if sum(new_bin) + item <= bin_capacity:
                    new_bin.append(item)
                    placed = True
                    break
            if not placed:
                new_bins.append([item])

    return new_bins
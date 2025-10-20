class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a,b)->Integer.compare(a[0], b[0]));
        List<int[]> list = new ArrayList<>();
        int[] curr = intervals[0];
        for(int i=1;i<intervals.length;i++){
            if(curr[1]>=intervals[i][0]){
                curr[1] = Math.max(curr[1], intervals[i][1]);
            }else{
                list.add(curr);
                curr = intervals[i];
            }
        }
        list.add(curr);
        return list.toArray(new int[list.size()][]);
    }
}
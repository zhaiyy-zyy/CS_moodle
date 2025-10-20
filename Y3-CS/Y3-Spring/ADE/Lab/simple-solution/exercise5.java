class Solution {
    List<List<Integer>> ans=new ArrayList<>();
    public List<List<Integer>> permute(int[] nums){
        List<Integer> al=new ArrayList<>();
        HashSet<Integer> hs=new HashSet<>();
        findAns(0,nums,hs,al);
        return ans;
    }
    public void findAns(int index,int[] nums,HashSet<Integer> hs,List<Integer> al){
        if(hs.size()==nums.length){
            ans.add(new ArrayList<>(al));
        }
        for(int i=0;i<nums.length;i++){
            if(hs.contains(i)){
                continue;
            }
            al.add(nums[i]);
            hs.add(i);
            findAns(i+1,nums,hs,al);
            al.remove(al.size()-1);
            hs.remove(i);
        }
    }
}
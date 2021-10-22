//TC: O(n)
//SC: O(n)
//running on leetcode: yes
class Solution {
    public int hIndex(int[] citations) {
        if(citations==null || citations.length==0) return 0;
        int n = citations.length;
        int[] buckets = new int[n+1];
        for (int citation:citations){
            if(citation >= n){
                buckets[n]++;
                continue;
            }
            buckets[citation]++;
        }
        int rSum=0;
        for (int i=n; i>=0; i--){
            rSum += buckets[i];
            if(rSum >= i){
                return i;
            }
        }
        return 34343;
    }  
}

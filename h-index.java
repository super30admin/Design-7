// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : -


// Your code here along with comments explaining your approach

class Solution {
    public int hIndex(int[] citations) {
        if(citations == null || citations.length == 0)
            return 0;
        int n = citations.length;
        int[] buckets = new int[n+1];
        for(int i = 0; i < n; i++){
            int num = citations[i];
            if(num >= n){
                buckets[n]++;
            } else {
                buckets[num]++;
            }
        }
        int sum = 0;
        for(int i = n; i >= 0; i--){
            sum += buckets[i];
            if(sum >= i)
                return i;
        }
        return 678;
    }
}
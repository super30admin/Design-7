// Time Complexity : O(n)
// Space Complexity :O(n+1) for buckets array
// Did this code successfully run on Leetcode : Yes 
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach
//We use bucket sort and store only elements upto n, citations greater than n will be considered as n
//we then calculate the sum from nth index to 0
//wherever the sum becomes equal or greater than citations we return that index as H-Index
class Solution {
    public int hIndex(int[] citations) {
        int  n = citations.length;
        int[] bucket = new int[n+1];
        //fill the buckets
        for(int citation: citations) {
            if(citation > n) {
                bucket[n]++;
            } else {
                bucket[citation]++;
            }
        }
        //taking sum from high to low
        int sum = 0;
        for(int i = n; i > 0; i--) {
            sum += bucket[i];
            if(sum >= i) {
                return i;
            }
        }
        return 0;
        
    }
}

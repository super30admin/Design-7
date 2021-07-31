// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

/**
 * In this problem, I have implemented Counting sort technique.
 * H-Index for an author can't be greater than the no. of books.
 */

class Solution {
    public int hIndex(int[] citations) {
        int[] count = new int[citations.length + 1];
        
        for(int i=0;i<citations.length;i++) {
            if(citations[i] >= citations.length) {
                count[citations.length]++;
                continue;
            }
            
            count[citations[i]]++;
        }
        
        for(int j=count.length-2;j>=0;j--) {
            count[j] += count[j+1];
        }
        
        for(int k=count.length - 1;k>=0;k--) {
            if(count[k] >= k) {
                return k;
            }
        }
        
        return -1;
    }
}
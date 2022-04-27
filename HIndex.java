// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
// First create an array of size n
// Put the count of number of citations in respective indexes, 
// For citations greater than n put count in index n 
// Now calculate suffix sum of this array
// Now start with hindex = n and look at its position if it can be a hindex
// Our check would be the value at that index in hash suffix array should be 
// greater or equal. If we find this we can return the hindex
class Solution {
    public int hIndex(int[] citations) {
        //store hash
        int n = citations.length;
        int[] hashArray = new int[n+1];
        for(int i = 0; i < n; i++){
            int x = citations[i];
            if(x > n)
                hashArray[n] += 1;
            else
                hashArray[x] += 1;
        }
        //suffix sum
        for(int i = n-1; i > 0; i--){
            hashArray[i] = hashArray[i] + hashArray[i+1];
        }
        int hindex = n;
        while(hindex > 0){
            if(hashArray[hindex] >= hindex)
                return hindex;
            hindex--;
        }
        return hindex;
    }
}
// TC: O(n)
// SC: O(n)

// Approach: Hindex value can only be from 1 to n
// Any value more than n will be considered in the same bucket

class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];

        for (int cit : citations) {
            if (cit >= n) {
                buckets[n] += 1;
            } else {
                buckets[cit] += 1;
            }
        }

        int currentCitations = 0;
        for (int i = buckets.length - 1; i >= 0; i--) {
            currentCitations += buckets[i];
            if (currentCitations >= i) {
                return i;
            }
        }

        throw new IllegalArgumentException("Invalid input");
    }
}
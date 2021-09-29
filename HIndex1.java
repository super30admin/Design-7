//https://leetcode.com/problems/h-index/
/*
Time: O(n) where n = citations.length
Space: O(n)
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : None
*/
public class HIndex1 {
    public int hIndex(int[] citations) {

        int n = citations.length;
        int[] bucket = new int[n + 1];// range = 0 to no: of citations

        // make a count of each element present in your initial array
        for (int citation : citations) {
            if (citation >= n) // when value exceeds the length of bucket array
                bucket[n]++;

            else
                bucket[citation]++;

        }

        int runningSum = 0;

        for (int bucketIndex = n; bucketIndex >= 0; bucketIndex--) // right to left
        {
            runningSum += bucket[bucketIndex];
            if (runningSum >= bucketIndex)
                return bucketIndex;
        }

        return 0;

    }

}

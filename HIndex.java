// This solution uses bucket sort of all the citations where we maintain range from 0 to citations.length and increment the bucket with element of that index
// If at all the element is more than the citations length then we increment last index as it falls under that bucket
// At the end we start from max bucket index and keep a running sum. At any point, if at all the sum is greater than index that means there are n elements greater than n which is our required h index
class Solution {
    public int hIndex(int[] citations) {
        int[] buckets = new int[citations.length+1];
        for(int i:citations) {
            if(i<=citations.length) {
                buckets[i]++;
            } else {
                buckets[buckets.length-1]++;
            }
        }

        int sum = 0;
        for(int i=buckets.length-1;i>=0;i--) {
            if(sum+buckets[i]>=i) return i;
            sum+=buckets[i];
        }
        return -1;
    }
}

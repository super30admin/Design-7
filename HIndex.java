//Leetcode Problem 274: H-Index
//T.C: O(n) :: S.C: O(n)

class HIndex {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) return 0;

        int n = citations.length;
        int[] nums = new int[n + 1];

        for (int i = 0; i < n; i++) {
            if (citations[i] > n) {
                nums[n]++;
            } else {
                nums[citations[i]]++;
            }
        }
        
        int rsum = 0;

        for (int i = n; i >= 0; i--) {
            rsum += nums[i];
            if (rsum >= i) {
                return i;
            }            
        }

        return 0;
    }
}
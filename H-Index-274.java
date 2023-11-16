// Time Complexity: O(n log n) where n is number of citations
// Space Complexity: O(1)
class Solution {
    public int hIndex(int[] citations) {
        if(citations == null || citations.length == 0){
            return 0;
        }

        int n = citations.length;

        Arrays.sort(citations);
        for(int i = 0; i < citations.length; i++){
            if(citations[i] >= n - i){
                return n - i;
            }
        }
        return 0;
    }
}

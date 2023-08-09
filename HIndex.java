class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] bucket = new int[n+1];
        for(int i=0;i<n;i++){
            if(citations[i]>=n){
                bucket[n]++;
            }else{
                bucket[citations[i]]++;
            }
        }
        int sum = 0;
        for(int i=n;i>=0;i--){
            sum = sum + bucket[i];
            if(sum>=i){
                return i;
            }
        }
        return 0;
    }
}

// class Solution {
//     public int hIndex(int[] citations) {
//         Arrays.sort(citations);
//         for(int i=0;i<citations.length;i++){
//             int diff = citations.length - i;
//             if(citations[i]>=diff){
//                 return diff;
//             }
//         }
//         return 0;
//     }
// }
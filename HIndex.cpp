O(N) time, O(N) space
class Solution {
public:
    int hIndex(vector<int>& citations) {
        int n = citations.size();
        vector<int> buckets(n+1);
        for(int i=0;i<n;i++){
            if(citations[i]>=n){
                buckets[n]++;
            }else{
                buckets[citations[i]]++;
            }
        }
        int sum=0;
        for(int i=n;i>=0;i--){
            sum+=buckets[i];
            if(sum>=i)
                return i;
        }
        return 0;
    }
};

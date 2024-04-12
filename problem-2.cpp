// t-c: n;
class Solution {
public:
    int hIndex(vector<int>& citations) {
        sort(citations.begin(),citations.end());
        int n = citations.size();
        vector<int> freq(n);
        for(int i = 0; i < n; i++){
            if(i != 0 && citations[i-1] == citations[i]){
                freq[i] = freq[i-1];
            }
            else{
                freq[i] = n - i;
            }
        }
        for(int i = n-1; i >= 0; i--){
            if((freq[i] >= citations[i]) ){
                return citations[i];
            }
            else if(i != 0 && freq[i] >= citations[i-1]){
                return freq[i];
            }
        }
        return freq[0];
    }
};

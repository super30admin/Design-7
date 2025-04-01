// https://leetcode.com/problems/h-index/
// Time Complexity: O(n)
// Space Complexity: O(n)

class Solution {
    public:
        int hIndex(vector<int>& citations) {
            int n = citations.size();
            int ans = 0;
            vector<int> res(n+1, 0);
            for(int i=0; i<n; i++){
                if(citations[i] > n) res[n]++;
                else res[citations[i]]++;
            }
    
            for(int i=res.size()-1; i>=0; i--){
                ans += res[i];
                if(ans >= i) return i;
            }
            return 0;
        }
    };
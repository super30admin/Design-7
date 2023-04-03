274. H-Index

/*
TC O(n)
SC O(1)
*/

class Solution {
public:
    int hIndex(vector<int>& citations) {
        int n = citations.size();
        if (n ==0) return 0;
                int result = 0;
        //sort(citations.begin(), citations.end());
        int buckets[n+1];

        for (int i=0; i < n; i++) {
            if (citations[i] >= n) {
                buckets[n]++;
            } else {
                int idx = citations[i];
                buckets[idx]++;
            }
        }
            int sum = 0;
            for (int i=n; i>=0; i--) {
                sum += buckets[i];
                if (sum >= i) {
                    return i;
                }
            }
            return 0;
        }
};


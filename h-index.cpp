// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes

#include <vector>
  
using namespace std;

class Solution {
public:
    int hIndex(std::vector<int>& citations) {
        int total = 0;
        int n = citations.size();
        std::vector<int> temp(n + 1, 0);

        for (int c : citations) {
            if (c >= n) {
                temp[n]++;
            } else {
                temp[c]++;
            }
        }

        for (int i = n; i >= 0; i--) {
            total += temp[i];
            if (total >= i) {
                return i;
            }
        }

        return 0;
    }
};

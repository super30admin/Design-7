// Problem: H-index
//  Time Complexity : O(logn)
//  Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
//  Any problem you faced while coding this : No

// Your code here along with comments explaining your approach in three
// sentences only
// 1. Here we use binary search to find the smallest index where n - mid >=
// citations[mid]
// 2. If we found the exact value, then that is the h-index
// 3. Otherwise, n - whereever low ends is the h-index

#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
  int hIndex(vector<int> &citations) {
    int n = (int)citations.size();
    int low = 0;
    int high = n - 1;
    while (low <= high) {
      int mid = low + (high - low) / 2;
      int diff = n - mid;
      if (diff == citations[mid]) {
        return diff;
      } else if (diff > citations[mid]) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }
    return n - low;
  }
};

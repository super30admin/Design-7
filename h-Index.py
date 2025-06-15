# Time Complexity : O(n)
# Space Complexity : O(n)
class Solution:
    def hIndex(self, citations):
        n = len(citations)
        papers = [0] * (n+1)
        count = 0
        for i in range(n):
            p = citations[i]
            if p > n:
                papers[n] += 1
            else:
                papers[p] += 1
        for i in range(n, -1, -1):
            count += papers[i]
            if count >= i:
                return i
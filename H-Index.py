# Time Complexity: O(n)
# Space Complexity: O(n)
class Solution:
    def hIndex(self, citations: List[int]) -> int:
        if not citations:
            return 0

        n = len(citations)
        buckets = [0] * (n + 1)

        for c in citations:
            if c >= n:
                buckets[n] += 1
            else:
                buckets[c] += 1

        papers = 0
        for i in range(n, -1, -1):
            papers += buckets[i]
            if papers >= i:
                return i

        return 0
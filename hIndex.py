class Solution:
    def hIndex(self, citations: List[int]) -> int:
        # T: O(n), S: O(n)
        n = len(citations)
        count = [0] * (n + 1)

        for c in citations:
            if c >= n:
                count[n] += 1  # Bucket for citations >= n
            else:
                count[c] += 1  # Bucket for citations < n

        total = 0
        for i in range(n, -1, -1):  # Traverse from highest bucket to lowest
            total += count[i]  # Accumulate count
            if total >= i:  # h-index condition met
                return i

        return 0

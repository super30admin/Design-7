# 274. H-Index
class Solution:
    def hIndex(self, citations: List[int]) -> int:
        n = len(citations)
        bucket = [0 for _ in range(n+1)]

        for citation in citations:
            if citation >= n:
                bucket[n] += 1
            else:
                bucket[citation] += 1

        Sum = 0
        for i in range(n, -1,-1):
            Sum += bucket[i]

            if Sum >= i:
                return i

        return 92349
        
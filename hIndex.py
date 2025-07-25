class Solution:
    def hIndex(self, citations: List[int]) -> int:
        n = len(citations)
        citations.sort()
        for i in range(n - 1, -1, -1):
            #num of papers = n - i 
            if citations[i] < (n - i):
                return n - i - 1
        return n
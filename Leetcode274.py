# Time Complexity = O(nlogn)
# Space Complexity = O(1)
class Solution:
    def hIndex(self, citations: List[int]) -> int:
        citations.sort(reverse=True)  # Sort in descending order
        h = 0
        for i, c in enumerate(citations):
            if c >= i + 1:
                h = i + 1
            else:
                break
        return h
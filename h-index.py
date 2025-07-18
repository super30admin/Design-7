# Time Complexity : O(n log n) for sorting
# Space Complexity : O(1)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

class Solution:
    def hIndex(self, citations: List[int]) -> int:
        # Sort citations in descending order
        citations.sort(reverse=True)
        
        # Find the largest h where citations[h-1] >= h
        h = 0
        for i in range(len(citations)):
            if citations[i] >= i + 1:
                h = i + 1
            else:
                break
        
        return h
        
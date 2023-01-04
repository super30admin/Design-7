# Time Complexity : O(n*logn), Where n is number of elements in citations 
# Space Complexity : O(1)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : Nothing specific

from typing import List
class Solution:
    def hIndex(self, citations: List[int]) -> int:
        citations.sort()
        print(citations)
        n=len(citations)
        for i in range(0,n):
            if(citations[i]>=n-i):
                return n-i
        return 0
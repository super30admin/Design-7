-------------------------H index------------------------------------------
# Time Complexity : O(n) 
# Space Complexity : O(N)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# 
# to check the H-index, we will do bucket sort where we will place the H-index, so when the hindex greater then the array
# it will be counted and incremented to the last element. and we will iterate from the end and add the number of citations,
# and when we cross the number of citations and h-index return that index.



class Solution:
    def hIndex(self, citations: List[int]) -> int:
        if not citations:
            return 0
        
        so = [0]*(len(citations)+1)
        n = len(citations)
        for i in citations:
            so[min(i,n)] +=1
        
        
        temp = 0
        for j in range(n, -1, -1):
            temp += so[j]
            if temp >= j:
                return j
        return -1
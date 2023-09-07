# Time Complexity: O(n)
# Space Complexity: O(n)
# Did this problem run on Leetcode: Yes
# Any issues faced doing this problem: No

class Solution(object):
    def hIndex(self, citations):
        """
        :type citations: List[int]
        :rtype: int
        """
        citations.sort(reverse = True)
        n = len(citations)
        h = 0
        for i in range(n):
            if citations[i] >= (i + 1):
                h = i + 1
        return h
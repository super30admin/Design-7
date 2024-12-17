# Can be done in O(n) or O(log(n)) - Binary Search
# Single Pass
# class Solution:
#     def hIndex(self, citations: List[int]) -> int:
#         n = len(citations)

#         for i in range(n):

#             if citations[i] >= (n - i):
#                 return n-i
#         return 0


# Binary Search
class Solution:
    def hIndex(self, citations: List[int]) -> int:
        l = 0
        r = len(citations) - 1
        n = len(citations)
        h_max = 0
        while l <= r:
            mid = (l + r) // 2
            h = n - mid
            if citations[mid] >= h:
                r = mid - 1
                h_max = max(h_max, h)
            else:
                l = mid + 1

        return h_max
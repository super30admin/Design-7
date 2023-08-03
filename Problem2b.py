class Solution(object):
    def jump(self, nums):
        """
        Find the minimum number of jumps required to reach the last index.

        Time complexity: O(n)
        Space complexity: O(1)

        :type nums: List[int]
        :rtype: int
        """
        if nums is None or len(nums) == 0:
            return 0
        if len(nums) < 2:
            return 0

        jumps = 0  # Initialize the jumps count.
        # Initialize the current index and next interval.
        currIdx, nextInt = 0, 0

        for i in range(len(nums) - 1):
            nextInt = max(nextInt, i + nums[i])  # Update the next interval.

            # If we've reached the current interval, update current index and increment jumps.
            if i == currIdx:
                currIdx = nextInt
                jumps += 1

        return jumps  # Return the minimum jumps.

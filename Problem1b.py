class Solution(object):
    def canJump(self, nums):
        """
        Determine if you can reach the last index of the array.

        Time complexity: O(n)
        Space complexity: O(1)

        :type nums: List[int]
        :rtype: bool
        """
        if nums is None or len(nums) == 0:
            return False
        if len(nums) < 2:
            return True

        # Initialize the target index as the last index of the array.
        target = len(nums) - 1

        # Traverse the array in reverse order.
        # The loop starts from len(nums) - 2 and goes down to -1 with step -1.
        for i in range(len(nums) - 2, -1, -1):
            if i + nums[i] >= target:
                # If the current index plus the jump length can reach or go beyond the target,
                # update the target to the current index.
                target = i

        # Check if the final target index is 0, meaning you can reach the start from the last index.
        return target == 0

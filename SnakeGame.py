# Time Complexity : O(N)
# Space Complexity : O(N) where N is max snake length. + O(w * h) for visited
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# Approach : We keep track of the snake’s body using a linked list and update its head on every move.
# We check if the new head hits the wall or the body (excluding the last tail, which may move).
# If the new head is at a food location, we grow the snake; otherwise, we move forward by adding head and removing tail.

from collections import deque

class SnakeGame:
    def __init__(self, width: int, height: int, food: list[list[int]]):
        self.h = height
        self.w = width
        self.food = food
        self.idx = 0
        self.snakeBody = deque()
        self.snakeBody.appendleft([0, 0])

    def move(self, direction: str) -> int:
        head = self.snakeBody[0]
        r, c = head[0], head[1]

        if direction == "L":
            c -= 1
        elif direction == "R":
            c += 1
        elif direction == "D":
            r += 1
        elif direction == "U":
            r -= 1

        if r < 0 or c < 0 or r == self.h or c == self.w:
            return -1

        for i in range(len(self.snakeBody) - 1):
            temp = self.snakeBody[i]
            if temp[0] == r and temp[1] == c:
                return -1

        if self.idx < len(self.food):
            if self.food[self.idx][0] == r and self.food[self.idx][1] == c:
                self.snakeBody.appendleft([r, c])
                self.idx += 1
                return len(self.snakeBody) - 1

        self.snakeBody.appendleft([r, c])
        self.snakeBody.pop()
        return len(self.snakeBody) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
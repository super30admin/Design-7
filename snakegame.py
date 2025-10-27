# Time Complexity : O(N) where N is the length of the snake
# Space Complexity : O(N) where N is the length of the snake
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach:
# I am using a deque to represent the snake's body and a set to keep track of the positions occupied by the snake for quick collision detection
# The snake starts at position (0,0) and moves according to the input directions
# In the move method, I calculate the new head position based on the direction
# I check for collisions with the walls and the snake's own body
# If the snake eats food, I increase its length by adding the new head position to the front of the deque and increment the food index
# If the snake does not eat food, I add the new head position to the front of the deque and remove the tail position to maintain the same length
# Finally, I return the current length of the snake after each move


from collections import deque
from typing import List

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = deque([(0,0)]) 
        self.set = {(0,0)}
        self.h = height
        self.w = width
        self.idx = 0
        self.food = food
    def move(self, direction: str) -> int:
        head = self.snake[0]
        r ,c= head
        if direction == "U":
            r -= 1
        if direction == "D":
            r += 1
        if direction == "L":
            c -= 1
        if direction == "R":
            c += 1
        if r < 0 or c < 0 or r == self.h or c == self.w:
            return -1
        for i in range(len(self.snake)-1):
            temp = self.snake[i]
            if (r,c) == (temp[0],temp[1]):
                return -1
        if self.idx < len(self.food):
            if [r,c] ==  self.food[self.idx]:
                self.snake.appendleft([r,c])
                self.idx += 1
                return len(self.snake) -1
        self.snake.appendleft([r,c])
        self.snake.pop()
        return len(self.snake) - 1


        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
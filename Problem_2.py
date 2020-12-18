"""
Time Complexity : O(1) for move
Space Complexity : O(w*h)- as at max, the snake can cover the whole grid
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No


Your code here along with comments explaining your approach:
we have 4 conditions to check before moving further:
1. If snake hits the wall- return -1
2. if snake hits itself, return -1
3. If snake reaches food, increase its length
4. Just move in other case.
"""
from collections import deque


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.width = width
        self.height = height
        self.food = food
        self.snake = deque()
        self.snakePositions = set()
        self.snake.append((0, 0))
        self.snakePositions.add((0, 0))
        self.foodPosition = 0

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        hx, hy = self.snake[-1]
        if direction == 'U':
            hx -= 1
        elif direction == 'L':
            hy -= 1
        elif direction == 'R':
            hy += 1
        elif direction == 'D':
            hx += 1
        if hx < 0 or hx >= self.height or hy < 0 or hy >= self.width:
            return -1
        tempset = (hx, hy)
        if tempset != self.snake[0] and tempset in self.snakePositions:
            return -1
        if self.foodPosition < len(self.food):
            nextFood = self.food[self.foodPosition]
        else:
            nextFood = None
        if self.foodPosition < len(self.food) and nextFood[0] == hx and nextFood[1] == hy:
            self.foodPosition += 1
        else:
            self.snakePositions.remove(self.snake.popleft())
        self.snake.append((hx, hy))
        self.snakePositions.add((hx, hy))
        return len(self.snakePositions)-1


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)

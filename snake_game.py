"""
Time Complexity for move(): O(m*n) where m is the height and n is the width of the screen. This can be improved by using set()
Space Complexity: O(m*n) where m is the height and n is the width of the screen. In worst case, snake can occupy the whole screen
"""
from collections import deque


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = deque()
        self.foodItems = deque(food)
        self.snakeHead = [0, 0]
        self.width = width
        self.height = height

        self.snake.append(self.snakeHead[:])

    def move(self, direction: str) -> int:
        if direction == "R":
            self.snakeHead[1] += 1
        elif direction == "L":
            self.snakeHead[1] -= 1
        elif direction == "U":
            self.snakeHead[0] -= 1
        elif direction == "D":
            self.snakeHead[0] += 1

        # Check if snake hit the boundary
        if self.snakeHead[0] < 0 or self.snakeHead[0] >= self.height or self.snakeHead[1] < 0 or self.snakeHead[1] >= self.width:
            return -1

        # Check if snake hit the self
        for i in range(1, len(self.snake)):
            curr = self.snake[i]
            if curr[0] == self.snakeHead[0] and curr[1] == self.snakeHead[1]:
                return -1

        # Check for the food item
        if len(self.foodItems) > 0:
            foodItem = self.foodItems[0]
            if foodItem[0] == self.snakeHead[0] and foodItem[1] == self.snakeHead[1]:
                self.foodItems.popleft()
                self.snake.append(self.snakeHead[:])
                return len(self.snake) - 1

        # Otherwise
        self.snake.append(self.snakeHead[:])
        self.snake.popleft()
        return len(self.snake) - 1


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)

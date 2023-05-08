# Time Complexity : O(1)  for all methods
# Space Complexity : O(n), where n is the maximum length of the snake
from collections import deque
from typing import List

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.snake = deque([(0, 0)])
        self.score = 0
        self.food_index = 0
        self.directions = {'U': (-1, 0), 'L': (0, -1), 'R': (0, 1), 'D': (1, 0)}

    def move(self, direction: str) -> int:
        row, col = self.snake[0]
        dx, dy = self.directions[direction]
        new_row, new_col = row + dx, col + dy

        if (
            new_row < 0
            or new_row >= self.height
            or new_col < 0
            or new_col >= self.width
            or (new_row, new_col) in self.snake
            and (new_row, new_col) != self.snake[-1]
        ):
            return -1

        if self.food_index < len(self.food) and [new_row, new_col] == self.food[self.food_index]:
            self.score += 1
            self.food_index += 1
        else:
            self.snake.pop()

        self.snake.appendleft((new_row, new_col))
        return self.score


# Time complexity:
# __init__: O(n) where n is the number of food positions.
# move: O(1)
# Space complexity:
# O(n + m) 
from collections import deque

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = deque(food)
        self.snake = deque([(0, 0)])
        self.score = 0
        self.dirs = {'U': (-1, 0), 'D': (1, 0), 'L': (0, -1), 'R': (0, 1)}

    def move(self, direction: str) -> int:
        head = self.snake[0]
        dx, dy = self.dirs[direction]
        new_head = (head[0] + dx, head[1] + dy)
        
        if new_head[0] < 0 or new_head[0] >= self.height or new_head[1] < 0 or new_head[1] >= self.width:
            return -1
        
        if new_head in list(self.snake)[1:]:
            return -1
        
        self.snake.appendleft(new_head)
        
        if self.food and list(self.food)[0] == list(new_head):
            self.score += 1
            self.food.popleft()
        else:
            self.snake.pop()
        
        return self.score

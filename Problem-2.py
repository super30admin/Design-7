# Time Complexity : O(1) per move
# Space Complexity : O(width * height), for snake body positions
# Did this code successfully run on Leetcode : No
# Any problem you faced while coding this : No

# Explanation:
# 1. Use a deque to track snake body positions (head at end).
# 2. Use a set for O(1) collision detection with the body.
# 3. On each move, compute new head position.
# 4. If food present at new head, grow snake, otherwise move tail forward.
# 5. Return current score or -1 if snake hits wall or itself.

from collections import deque

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = deque(food)
        self.snake = deque([(0, 0)])
        self.snake_set = set([(0, 0)])
        self.score = 0

    def move(self, direction: str) -> int:
        dir_map = {'U': (-1, 0), 'D': (1, 0), 'L': (0, -1), 'R': (0, 1)}
        head_x, head_y = self.snake[-1]
        dx, dy = dir_map[direction]
        new_head = (head_x + dx, head_y + dy)

        # Check wall collision
        if not (0 <= new_head[0] < self.height and 0 <= new_head[1] < self.width):
            return -1
        # Check self collision (exclude tail which moves forward)
        tail = self.snake[0]
        if new_head in self.snake_set and new_head != tail:
            return -1

        # Move snake
        self.snake.append(new_head)
        self.snake_set.add(new_head)

        # Check food
        if self.food and list(self.food[0]) == list(new_head):
            self.food.popleft()
            self.score += 1
        else:
            removed = self.snake.popleft()
            self.snake_set.remove(removed)
        
        return self.score
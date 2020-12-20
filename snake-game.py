# score is hte size of the LL
# O(n)- move-time
# space-O(w*h)
# Class Node:
#     def __init__(self,val):
#         self.val=val
#         self.next=None
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
        self.snake = deque()
        self.w = width
        self.h = height
        self.food = food
        self.snake.append((0, 0))
        self.snakepostions = set()
        self.snakepostions.add((0, 0))
        self.foodposition = 0
        # snakeposition=Node(1)

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body.
        """
        x, y = self.snake[-1]
        if direction == 'U':
            x -= 1
        elif direction == 'L':
            y -= 1
        elif direction == 'D':
            x += 1
        elif direction == 'R':
            y += 1
        if x < 0 or x >= self.h or y < 0 or y >= self.w:
            return -1
        temp = (x, y)
        if temp != self.snake[0] and temp in self.snakepostions:
            return -1
        if self.foodposition >= len(self.food):
            nextfood = None
        else:
            nextfood = self.food[self.foodposition]
            # print(temp,nextfood)
        if self.foodposition < len(self.food) and x == nextfood[0] and y == nextfood[1]:
            self.foodposition += 1
        else:
            self.snakepostions.remove(self.snake.popleft())
        self.snakepostions.add(temp)
        self.snake.append(temp)
        return len(self.snakepostions) - 1
# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
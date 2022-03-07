# Time complexity: move = O(m*n). in worst case length of the snake could be m*n
#Space Complexity : o(m*n).in worst case length of the snake could be m*n
from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = deque()
        self.height = height
        self.width = width
        self.foodItems= deque(food)
        self.snakeHead = [0,0]
        self.snake.append(self.snakeHead)

    def move(self, direction: str) -> int:
        # move a snacke Head
        if direction == "U":
            self.snakeHead[0] -= 1
        elif direction == "L":
            self.snakeHead[1] -= 1
        elif direction == "R":
            self.snakeHead[1] += 1
        elif direction == "D":
            self.snakeHead[0] += 1

        # check if snake touches boundry
        if self.snakeHead[0] < 0 or self.snakeHead[0] == self.height or self.snakeHead[1] < 0 or self.snakeHead[1] == self.width:
            return -1


        # check if snake bit itself
        for i in range(1,len(self.snake)):
            curr = self.snake[i]
            if self.snakeHead[0] == curr[0] and self.snakeHead[1] == curr[1]:
                return -1

        # check foodItems
        if len(self.foodItems) > 0:
            food = self.foodItems[0]
            if food[0] == self.snakeHead[0] and food[1] == self.snakeHead[1]:
                self.snake.append([self.snakeHead[0],self.snakeHead[1]])
                self.foodItems.popleft()
                return len(self.snake)-1

        self.snake.append([self.snakeHead[0],self.snakeHead[1]])
        self.snake.popleft()
        return len(self.snake)-1

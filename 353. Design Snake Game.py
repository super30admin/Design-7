#Time : O(1)
#Space : o(hxw)
from collections import deque

class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.foodList = food
        self.snakeHead = [0, 0]
        self.w = width
        self.h = height
        self.visited = [[False for _ in range(width)] for _ in range(height)]
        self.visited[0][0] = True
        self.snake = deque([self.snakeHead])
        self.i = 0

    def move(self, direction: str) -> int:
        # Update the newHead position based on the direction
        newHead = [self.snakeHead[0], self.snakeHead[1]]
        if direction == "D":
            newHead[0] += 1
        elif direction == "U":
            newHead[0] -= 1
        elif direction == "L":
            newHead[1] -= 1
        else:
            newHead[1] += 1

        # Check if the next head position is valid (within the grid boundaries)
        if newHead[0] == self.h or newHead[1] == self.w or newHead[0] < 0 or newHead[1] < 0:
            return -1

        # Check if the snake hits itself
        if self.visited[newHead[0]][newHead[1]]:
            return -1

        # Check if the snake eats food
        if self.i < len(self.foodList) and newHead[0] == self.foodList[self.i][0] and newHead[1] == self.foodList[self.i][1]:
            self.snakeHead = newHead  # Update the snake's head position after eating
            self.snake.append(newHead)
            self.visited[newHead[0]][newHead[1]] = True
            
            self.i += 1
            return len(self.snake) - 1

        # Normal move
        tail = self.snake.popleft()
        self.visited[tail[0]][tail[1]] = False
        self.snake.append(newHead)
        self.visited[newHead[0]][newHead[1]] = True
        self.snakeHead = newHead  # Update the snake's head position after a normal move
        return len(self.snake) - 1
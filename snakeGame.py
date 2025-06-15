# Time Complexity : O(1)
# Space Complexity  : O(width * height) + O(snake length)
    # We are maintaining a visited matrix of size width * height
    # and we are using a deque to store the snake body. 
from collections import deque
class SnakeGame:
    def __init__(self, width: int, height: int, food):
        self.w = width
        self.h = height
        self.food = food
        self.idx = 0

        self.visited = [[False for _ in range(self.w)] for _ in range(self.h)]
        self.snakeBody = deque()
        self.snakeBody.appendleft([0,0])

    def move(self, direction: str) -> int:
        head = self.snakeBody[0]
        r, c = head[0], head[1]

        if direction == "R":
            c += 1
        elif direction == "D":
            r += 1
        elif direction == "U":
            r -= 1
        elif direction == "L":
            c -= 1

        if r < 0 or c < 0 or r == self.h or c == self.w or self.visited[r][c]:
            return -1

        if self.idx < len(self.food):
            if self.food[self.idx][0] == r and self.food[self.idx][1] == c:
                self.snakeBody.appendleft([r, c])
                self.visited[r][c] = True
                self.idx += 1
                return len(self.snakeBody)-1

        self.snakeBody.appendleft([r, c])
        self.visited[r][c] = True
        self.snakeBody.pop()
        row, col = self.snakeBody[-1]
        self.visited[row][col] = False
        return len(self.snakeBody)-1
# Time Complexity : O(1)
# Space Complexity : O(1)
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.h = height
        self.w = width
        self.food = food
        self.snake = deque()
        self.head = [0, 0]
        self.snake.append(self.head)
        self.i = 0

    def move(self, direction: str) -> int:
        if direction == "L":
            self.head[1] -= 1
        elif direction == "R":
            self.head[1] += 1
        elif direction == "U":
            self.head[0] -= 1
        else:  # D
            self.head[0] += 1
        if self.head[0] < 0 or self.head[1] < 0 or self.head[0] == self.h or self.head[1] == self.w:
            return -1

        for i in range(1, len(self.snake)):
            curr = self.snake[i]
            if curr[0] == self.head[0] and curr[1] == self.head[1]: return -1

        if self.i < len(self.food):
            currFood = self.food[self.i]
            if currFood[0] == self.head[0] and currFood[1] == self.head[1]:
                self.snake.append([self.head[0], self.head[1]])
                self.i += 1
                return len(self.snake) - 1

        self.snake.append([self.head[0], self.head[1]])
        self.snake.popleft()
        return len(self.snake) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
import collections


class SnakeGame:
    def __init__(self, width, height, food):
        self.h = height
        self.w = width
        self.visited = [[False]*self.w for _ in range(self.h)]
        self.food = food
        self.idx = 0
        self.snakeBody = collections.deque()
        self.snakeBody.appendleft([0, 0])

    def move(self, direction):
        head = self.snakeBody[0]
        r, c = head[0], head[1]
        if direction == "L":
            c -= 1
        elif direction == "R":
            c += 1
        elif direction == "D":
            r += 1
        elif direction == "U":
            r -= 1

        if r < 0 or c < 0 or r == self.h or c == self.w or self.visited[r][c]:
            return -1

        if self.idx < len(self.food):
            if self.food[self.idx][0] == r and self.food[self.idx][1] == c:
                self.snakeBody.appendleft([r, c])
                self.visited[r][c] = True
                self.idx += 1
                return len(self.snakeBody) - 1

        self.snakeBody.appendleft([r, c])
        self.visited[r][c] = True
        self.snakeBody.pop()
        tail = self.snakeBody[-1]
        self.visited[tail[0]][tail[1]] = False
        return len(self.snakeBody) - 1

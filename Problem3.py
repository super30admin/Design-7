# 353. Design Snake Game
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.idx = 0

        self.snakeBody = deque([(0,0)])
        self.visited = [[False] * width for _ in range(height)]
        self.visited[0][0] = True
        
    def move(self, direction: str) -> int:
        head = self.snakeBody[0]
        r,c = head[0], head[1]
        if direction == "U":
            r -= 1
        elif direction == "D":
            r += 1
        elif direction == "L":
            c -= 1
        elif direction == "R":
            c += 1

        if r < 0 or r >= self.height or c < 0 or c >= self.width: return -1

        tail = self.snakeBody.pop()
        self.visited[tail[0]][tail[1]] = False

        if self.visited[r][c]: return -1

        self.snakeBody.appendleft((r,c))
        self.visited[r][c] = True


        if self.idx < len(self.food) and r == self.food[self.idx][0] and c == self.food[self.idx][1]:
            self.idx += 1
            self.snakeBody.append(tail)
            self.visited[tail[0]][tail[1]] = True
        
        return len(self.snakeBody) - 1
        
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
"""
TC: O(1)
SP:O(m*n)
"""
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.h = height
        self.w = width
        self.snakebody = deque([(0, 0)])
        self.visited = {(0, 0)}
        self.food = food
        self.idx = 0
        self.c = 0
        self.r = 0
        self.size = 0

    def move(self, direction: str) -> int:
        if direction == "R":
            self.c += 1
        elif direction == "L":
            self.c -= 1
        elif direction == "D":
            self.r += 1
        elif direction == "U":
            self.r -= 1
        if (
            0 <= self.r < self.h
            and 0 <= self.c < self.w
            and (self.r, self.c) not in self.visited
        ):

            if self.idx < len(self.food) and [self.r, self.c] == self.food[self.idx]:
                self.idx += 1
            else:
                tail = self.snakebody.pop()
                self.visited.remove(tail)
            self.snakebody.appendleft((self.r, self.c))
            self.visited.add((self.r, self.c))
        elif (self.r, self.c) in self.visited and self.snakebody[-1] == (
            self.r,
            self.c,
        ):
            tail = self.snakebody.pop()
            self.visited.remove(tail)
            self.snakebody.appendleft((self.r, self.c))
            self.visited.add((self.r, self.c))
        else:
            return -1
        return len(self.snakebody) - 1


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)

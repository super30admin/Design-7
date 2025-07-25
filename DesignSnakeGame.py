class Node:
    def __init__(self, nxt=None, row=0, col=0):
        self.nxt = nxt
        self.row = row
        self.col = col

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.w = width
        self.h = height
        self.food = food
        self.idx = 0
        self.occupied = set()
        self.occupied.add((0, 0))
        self.head = Node()
        self.tail = self.head
        self.dir = {"R": [0, 1], "L":[0, -1], "U":[-1, 0], "D":[1, 0]}
        self.row, self.col = 0, 0
        self.len = 1

    def move(self, direction: str) -> int:

        x, y = self.dir[direction]
        
        self.row, self.col = self.row + x, self.col + y
        if ((self.row, self.col) != (self.tail.row, self.tail.col) and (self.row, self.col) in self.occupied) or (self.row < 0 or self.col < 0 or self.row >= self.h or self.col >= self.w):
            return -1

        
        newHead = Node(None, self.row, self.col)
        self.head.nxt = newHead
        self.head = newHead
        
        if self.idx < len(self.food):
            foodx, foody = self.food[self.idx][0], self.food[self.idx][1]
        
        if self.idx < len(self.food) and self.row == foodx and self.col == foody:
            self.idx += 1
            self.len += 1
        else:
            self.occupied.remove((self.tail.row, self.tail.col))
            self.tail = self.tail.nxt
        self.occupied.add((self.row, self.col))
        return self.len - 1

        



# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
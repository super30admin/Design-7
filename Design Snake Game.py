class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):

        self.maxcol = width
        self.maxrow = height
        self.head = (0, 0)
        self.size = collections.deque()
        self.size.append((0, 0))
        self.sizeset = set()
        self.sizeset.add((0, 0))
        self.food = food
        self.currfood = 0
        self.dirs = {'U': [-1, 0], 'D': [1, 0], 'L': [0, -1], 'R': [0, 1]}
        self.score = 0

    def move(self, direction: str) -> int:
        # print(self.dirs[direction][0])
        # print(self.dirs[direction][1])
        # print("Food",self.food[self.currfood])
        newloc = (self.head[0] + self.dirs[direction][0], self.head[1] + self.dirs[direction][1])
        # print(newloc)

        # Check if newloc is at food's location
        if self.currfood < len(self.food) and list(newloc) == self.food[
            self.currfood]:  # If so, then just add the head, no need to move the tail
            # print("Here", newloc)
            self.size.append(newloc)
            self.sizeset.add(newloc)
            self.score += 1
            self.currfood += 1
        else:  # Move head and also tail
            # Check if head is moving over snake's body or out of bound
            tail = self.size.popleft()
            self.sizeset.remove(tail)

            if newloc[0] < 0 or newloc[0] == self.maxrow or newloc[1] < 0 or newloc[
                1] == self.maxcol or newloc in self.sizeset:
                # print("Failed at", newloc)
                # print(newloc in self.sizeset)
                return -1

            self.size.append(newloc)
            self.sizeset.add(newloc)
        self.head = newloc
        # print("Size", self.size)

        return self.score

        # if self.size:
        #     tail = self.size.popleft()
        #     self.sizeset.remove(tail)
        # if tuple(newloc) in self.sizeset or newloc[0] < 0 or newloc[0] < self.maxrow or newloc[1] < 0 or newloc[1] < self.maxcol:
        #     return -1
        # self.size.appendleft(tail)
        # self.sizeset.add(tail)
        # if newloc == self.food[self.currfood]:
        #     self.size.append(newloc)
        # else:
        #     self.size.append(newloc)
        #     tail = self.size.popleft()
        #     self.sizeset.remove(tail)
        # return len(self.size)

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
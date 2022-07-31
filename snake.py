# TC and SC both O(1) as board is always finite, so len of queue we maintain and iterate over is 
# always going to be finite
from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.h = height
        self.w = width
        self.food = food
        # 0th index is tail of the snake, and head is going to keep growing
        # so we keep appending it
        self.snake = deque()
        self.head = [0,0]
        self.snake.append(self.head)
        self.i = 0

    def move(self, direction: str) -> int:
        # update the head of the snake
        if direction == "L":
            self.head[1] -= 1
        elif direction == "R":
            self.head[1] += 1
        elif direction == "U":
            self.head[0] -= 1
        else: # D
            self.head[0] += 1
        # if snake hits the borders
        if self.head[0] < 0 or self.head[1] < 0 or self.head[0] == self.h or self.head[1] == self.w:
            return -1
        # iterate over snake list, except first element, and ensure new head doesn't equal to any of it
        # 1st element at 0th index will never be touched by the snake as that's tail and everytime head moves
        # tail will move, snake can't eat its own tail in our game
        for i in range(1,len(self.snake)):
            curr = self.snake[i]
            # when it hits itself
            if curr[0] == self.head[0] and curr[1] == self.head[1]: return -1
            
        # food move, consume food at curr index if our head is at same location as self.food[self.i]
        if self.i < len(self.food):
            currFood = self.food[self.i]
            if currFood[0] == self.head[0] and currFood[1] == self.head[1]:
                # if food was eaten append head to the snake list
                self.snake.append([self.head[0], self.head[1]])
                # don't pop tail as in food move, tail won't move.
                self.i += 1
                return len(self.snake) - 1
        # normal move
        self.snake.append([self.head[0], self.head[1]])
        self.snake.popleft() # remove 0th element as it is our tail and tail always moves one step forward in normal move
        return len(self.snake) - 1 

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
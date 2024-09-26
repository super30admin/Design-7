'''
Time Complexity :O(1) 
Space Complexity :O(1) 
Did this code successfully run on Leetcode : Yes
'''
from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        
        self.width = width
        self.height = height
        self.food = food
        
        # food ptr 
        self.foodPtr = 0
        
        # direction matrix
        self.dirMatrix = {
            "R": [0,1],
            "L": [0,-1],
            "U": [-1,0],
            "D": [1,0]
        }
        
        # initialize snake queue
        self.snake = deque([])
        self.snake.appendleft([0,0]) # snake will start from [0,0]
        
        # initialize head of the snake
        self.head = [0,0]

    def move(self, direction: str) -> int:
        
        # 1. get the ptr to the new head
        self.head[0] = self.head[0]+self.dirMatrix[direction][0]
        self.head[1] = self.head[1]+self.dirMatrix[direction][1]
        
        # 2. chk for the conditions
        # die -- i.e. hit the wall
        if (self.head[0] < 0 or self.head[0] >= self.height) or (self.head[1] < 0 or self.head[1] >= self.width):
            return -1
        
        # die -- i.e. eat itself
        # ignore the tail of the snake
        for i in range(1,len(self.snake)):
            if self.head == self.snake[i]:
                return -1
        
        # eat food -- expand the snake length
        if self.foodPtr < len(self.food) and self.food[self.foodPtr] == self.head:
            # add current head to the end of the queue
            self.snake.append(self.head.copy())
            # update the foodPtr
            self.foodPtr += 1
            # return the snake size
            return len(self.snake)-1
        
        # just move around
        
        # add current head to the end
        self.snake.append(self.head.copy())
        
        # remove the tail snake
        self.snake.popleft()
        
        # return the snake size
        return len(self.snake)-1
        
            


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
        
# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
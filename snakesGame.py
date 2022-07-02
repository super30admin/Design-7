'''
Time Complexity :O(1) 
Space Complexity :O(1) 
Did this code successfully run on Leetcode : no, passing 440 cases
'''
from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        
        # define a grid
        self.row = height
        self.col = width
        # self.grid = [["" for col in range(0,width)] for row in range(0,height)]
        
        # position of snake, initially is (0,0)
        self.queue = deque([])
        self.queue.append((0,0))
        
        # initialize head and tail of the snake
        self.head = self.queue[0]  # tail of snake
        self.tail = self.queue[-1] # head of the snake
        self.length = len(self.queue) # length of the snake
        
        # initialize the set to mark the current indexes of the snake
        self.snakeIndexes = set()
        self.snakeIndexes.add((0,0)) # snake will start from 0th index
        
        # initialize the queue for the food
        self.foodQueue = deque([])
        for f in food:
            ft = (f[0],f[1])
            self.foodQueue.append(ft)
        
        # for directions
        self.directions = {
            "R" : (0,1),
            "L" : (0,-1),
            "U" : (-1,0),
            "D" : (1,0)
        }
        
        # snake eats count
        self.snakeEats = 0
        
    def move(self, direction: str) -> int:
        
        # get the head of the snake(tail)
        curr = self.tail
        
        # next position to move
        idx = self.directions[direction]
        nxt = None
        tempR = curr[0]+idx[0]
        tempC = curr[1]+idx[1]
        
        if tempR>=0 and tempR<self.row and tempC>=0 and tempC<self.col:
            nxt = (tempR,tempC)
            
            if len(self.foodQueue)!= 0 and nxt == self.foodQueue[0]:
                # chk if its the position where food is served
                # food to consume --- just add it to the queue and set
                self.queue.append(nxt) # add the nxt pair to the queue
                self.snakeIndexes.add(nxt) # add the nxt pair to snakeIndexes set
                self.foodQueue.popleft() # remove the tuple pair from the queue i.e food is consumed
                self.snakeEats += 1 # snake continuously consumed the food
                self.tail = self.queue[-1] # update the tail i.e. head of the snake
                self.length = len(self.queue) # update the length of the queue
                return self.snakeEats # return the count of consumed
            
            elif self.head != nxt and nxt not in self.snakeIndexes:
                # chk if snake is not trying to eat itself!!
                # just move
                self.queue.append(nxt) # add the nxt pair to the queue
                self.snakeIndexes.add(nxt) # add the nxt pair to the set
                self.tail = self.queue[-1] # update the tail i.e. head of the snake
                ele = self.queue.popleft() # remove the current head i.e. tail of the snake
                if len(self.snakeIndexes) != 0 and ele in self.snakeIndexes:
                    self.snakeIndexes.remove(ele) # remove the index pair from the set
                self.head = self.queue[0] # update the head i.e. tail of the snake
                self.length = len(self.queue) # update the length of the queue
                return self.snakeEats # return the count of consumed
                
            else:
                return self.snakeEats             
                    
        else:
            # boundary breech --- end game
            return -1
        
# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
from queue import Queue
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.height = height
        self.width = width
        self.food = food
        self.snakeBody = deque([[0,0]])
        self.biteCheck = set()
        
        self.biteCheck.add((0,0))
        self.index = 0
        self.directions = {"D":[1,0],"U": [-1,0],"R": [0,1],"L": [0,-1]}
    def move(self, direction: str) -> int:
        
        currHead = self.snakeBody[-1]
        updatedHead = [0,0]
        updatedHead[0] = currHead[0] + self.directions[direction][0]
        updatedHead[1]= currHead[1] + self.directions[direction][1]

        row, col = updatedHead
        
        if row<0 or row>=self.height or col<0 or col>=self.width or ((row,col) in self.biteCheck and [row,col] != self.snakeBody[0]):
            return -1

        elif self.index<len(self.food) and self.food[self.index] == updatedHead:
            self.index+=1
    
        else:
            currTail = self.snakeBody.popleft()
            r, c = currTail
            self.biteCheck.remove((r,c))
            
        self.biteCheck.add((row,col))
        self.snakeBody.append(updatedHead)
        return len(self.snakeBody) - 1
        
 Time: O(1)
 Space: O(WH)

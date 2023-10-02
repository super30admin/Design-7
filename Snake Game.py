# Time Complexity : O(1) for each move
# Space Complexity : O(height*width)
# The code ran on LeetCode
# Maintain a queue to store the body of the snake

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        
        self.width = width
        self.height = height
        self.food = food
        self.score = 0
        self.positions:List[List[int]] = [[0,0]] 
        

    def move(self, direction: str) -> int:
        head = self.positions[0]       
        
        if direction == 'U':            
            newHead = [head[0] - 1, head[1]]
        elif direction == 'D':
            newHead = [head[0] + 1, head[1]]
        elif direction == 'L':
            newHead = [head[0], head[1] - 1]
        elif direction == 'R':
            newHead = [head[0], head[1] + 1]
        #Snake hits itself
        if self.positions and newHead in self.positions:    
            if self.positions[-1] != newHead:
                return -1
        
        if newHead[0] == -1 or newHead[0] == self.height or newHead[1] == -1 or newHead[1] == self.width:
            #If snake head crosses the screen boundary, game over
            return -1                                   
        #If snake didn't kill itself, then it's good to move to new location
        self.positions.insert(0, newHead)       
        #Now check if the new position is where food is found        
        if self.food and newHead == self.food[0]:     
            #If food is found, remove the food from the positions  
            self.food.pop(0)                            
            self.score += 1
        else:        
            self.positions.pop()
        
        return self.score

        



# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)

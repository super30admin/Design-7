"""
// Time Complexity : o(n), to check if the snake is hitting itself
// Space Complexity : o(n), queue size
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
"""
from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.q = deque() #using queue for snakes body
        self.w = width
        self.h = height
        self.f = food
        
        self.q.append([0,0]) #append first cooridnates of the snake

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        cur = self.q[-1] #get current position of head
        
        if direction == "U":
            r = cur[0] - 1
            c = cur[1]
        
        elif direction == "D":
            r = cur[0] + 1
            c = cur[1]
            
        elif direction == "L":
            r = cur[0] 
            c = cur[1] - 1
            
        elif direction == "R":
            r = cur[0] 
            c = cur[1] + 1
        
        if r < 0 or c < 0 or r >= self.h or c >= self.w: #check for borders
            return -1
            
        if self.f: #if food is present
            if r == self.f[0][0] and c == self.f[0][1]: #check if we reached a cell with food
                self.q.append([r,c]) #add it to head of snake, tail will be unmoved
                self.f.pop(0) #remove food
                return len(self.q)-1 #return score, size of snake-1
        
        for i in range(1,len(self.q)): #check if snake is hitting itself
            if self.q[i][0] == r and self.q[i][1] == c:
                return -1
        
        self.q.append([r,c]) #else, move head and tail
        self.q.popleft()
        
        return len(self.q)-1
            
                
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
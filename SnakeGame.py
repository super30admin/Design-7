--------------------------Snake Game------------------------------------------
# Time Complexity : O(1) 
# Space Complexity : O(N)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# 
# In snake game, a snake will move forward with the head pointer, but if the newhead is equal to body of itself,
# except the tail pointer, the game will stop. If we have new food at position of new head we will add newhead to head of snake,
#but not remove the tail pointer, when the food is not there, move snake ahead and remove tail pointer.


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        
        self.width = width
        self.height = height
        self.food = food
        self.positions = [[0,0]]
        self.score = 0
        

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        head = self.positions[0]
        
        if direction == 'U':
            newhead = [head[0]-1, head[1]]
        elif direction == 'L':
            newhead = [head[0], head[1]-1]
        elif direction == 'R':
            newhead = [head[0], head[1]+1]
        else:
            newhead = [head[0]+1, head[1]]
        
        
        if newhead[0] <0 or newhead[0]>=self.height or newhead[1]<0 or newhead[1]>=self.width:
            return -1
        
        if newhead in self.positions:
            if newhead != self.positions[-1]:
                return -1
        
        self.positions.insert(0, newhead)
        if self.food and newhead == self.food[0]:
            self.food.pop(0)
            self.score+=1
        else:
            self.positions.pop()
        
        return self.score
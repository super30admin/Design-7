# time complexity - O(1)
# space complexity - O(1)
# did this solution run on leetcode? - yes
# 
# by maintaining a deque
# we start by maintaining an index on the food list.
# snake keeps moving one step in the direction sent as an input by the user.
# when it consumes food, points increase by 1 and the length of the snake also increases by 1.
# return -1 when snake strikes the boundary, or current location is where the snake body is currently present.

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
        self.width = width
        self.height = height
        self.food = food                    # list of food positions
        self.food.reverse()
        self.food_idx = len(self.food)-1    # begin by pointing to the last position in the reversed food list.
        self.points = 0                     # total points a snake earned for eating food. 
        self.snakeq = deque([[0, 0]])       # store the snake positions in a queue.
        

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        if self.points == -1:
            return -1
        
        r, c = self.snakeq[-1] # current location will be the last element in the queue.
        
        # snake moves in the direction - upwards
        # decrement the row index by 1, column stays the same.
        if direction == 'U':    r -= 1                    
        elif direction == 'D':  r += 1  # snake moves in the direction - downwards # increase the row index by 1, column stays the same. 
        elif direction == 'R':  c += 1
        elif direction == 'L':  c -= 1
        
        # append to the current position to the queue.
        # this will also be used to maintain the length of the snake.
        curr_position = [r, c]
        
        # decrement the points if row is less than 0 or greater than the maximum height
        # or, if the column is less than 0 or greater than the maximum width.
        if r < 0 or r >= self.height or c < 0 or c >= self.width:
            self.points = -1
        # also, check if the current location collides with the tail.
        elif curr_position in self.snakeq and curr_position!=self.snakeq[0]:  
            self.points = -1
        elif self.food_idx!=-1 and self.food[self.food_idx] == curr_position:
            self.points += 1
            self.food.pop()
            self.food_idx -= 1
        else:
            self.snakeq.popleft()
            
        self.snakeq.append([r, c])
          
        return self.points
        

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
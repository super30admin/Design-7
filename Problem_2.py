# Design Snake Game

# Time Complexity : O(width*height)
# Space Complexity : O(width*height)
# Did this code successfully run on Leetcode : Yes, with Runtime: 248 ms and Memory Usage: 15.9 MB
# Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
# Approach
"""
Using linked list data structure as every time snake moves its head and tail changes.
Two Linkedlists are maintained 1 for foodlist and 1 snake. Width and height is there.
In directions length of snake changes when it moves Left, Righht, Up and Down.
The game ends and -1 is returned when the snake touches itself and snake touches boundries.  

"""
 class SnakeGame:
    
    def __init__(self, width: int, height: int, food: List[List[int]]):

    """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
    """
        self.snake=[[0,0]] # at [0,0] snakehead will be there 
        self.width=width
        self.height=height
        self.food=food
        self.eat=0

    def move(self, direction: str) -> int:

    """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.

    """
        x,y=self.snake[0]
        if(direction==("U")):
            x=x-1
        if(direction==("D")):
            x=x+1
        if(direction==("L")):
            y=y-1
        if(direction==("R")):
            y=y+1
        if 0 <=  x < self.height and 0 <= y < self.width and [x, y] not in self.snake[:-1]: # Checking for boundries and Checking for snake touches itself
            self.snake.insert(0, [x, y])
        else:
            return -1
        
        if self.eat < len(self.food) and self.snake[0] == self.food[self.eat]: # Food items present and eaten
            self.eat += 1
        else:
            del self.snake[-1]
        return self.eat

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
"""
Approach: Using Deque

Here we will maintain a deque like strucure becuase snake increases in size from rear and also it moves on the board. So we need to change corrdinates of its tail and head.

We will start from snake as [[0,0]].

Now depending on the directions it moves in we will add or substract 1 from either of its two r,c values.

U: r-1
D: r+1
L: c-1
R: c+1

Now whenever snake eats/lands on the food we add the coordinate to body of the snake. 

Also whenever a snake moves to legal direction we need to pop the left most coordinates from snake and add the current coordinates to its rear.

TC: O(n); n = size of snake
SC: O(n); n = size of snake
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
        self.snake = deque()
        self.foodList = food
        self.width = width
        self.height = height
        self.snakeHead = [0,0]
        self.snake.append(self.snakeHead)
    
    # TC: O(n); n = length of the snake. But its maximum lenght can width * height. So it becomes 
    # O(width * height)
    # SC: O(width * height)
    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        if direction == 'U':
            self.snakeHead[0] -= 1
            
        if direction == 'D':
            self.snakeHead[0] += 1
            
        if direction == 'L':
            self.snakeHead[1] -= 1
            
        if direction == 'R':
            self.snakeHead[1] += 1
            
        # Touches borders
        if self.snakeHead[0] < 0 or self.snakeHead[0] >= self.height or self.snakeHead[1] < 0 or self.snakeHead[1] >= self.width:
            return -1
        
        # Touches itself. We start from 1 because when snake moves one step ahead its tail also moves. Thus avoiding the touch to its body
        for i in range(1,len(self.snake)):
            curr = self.snake[i]
            if curr[0] == self.snakeHead[0] and curr[1] == self.snakeHead[1]:
                return -1
        
        if len(self.foodList):
            curr = self.foodList[0]
            
            if self.snakeHead[0] == curr[0] and self.snakeHead[1] == curr[1]:
                self.snake.append(self.foodList.pop(0))
                return len(self.snake) - 1

        
        self.snake.popleft()
        self.snake.append([self.snakeHead[0], self.snakeHead[1]])
        return len(self.snake) - 1
# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
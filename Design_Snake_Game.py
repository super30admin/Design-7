# Time Complexity : O(W × H + F)
# Space Complexity : O(W × H + F)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach
# Using 2 queues for the snake and food
# Initialize width, height, score, food, snake, grid[width][height] = False, game_over, self.grid[0][0] = True
# In the move function we will get the row,col by self.snake[-1] since the head will be at end of queue because the head will be increasing
# If the direction is up decrease row by 1 down increase row by 1 left decrease col by 1 and right increase col by 1
# Check if the snake hits the boundary of screen then self.game_over = True and return -1 since we dont want to make any more move calls
# If the new location is food then we will popleft the food from food queue and increase score by 1 
# Else we will remove the tail by popleft the snake queue and making its grid[r][c] = False
# Then If grid[row][col] = True we set game_over to True and return -1
# For both we will add new head to the snake queue and make grid[row][col] to True if the grid[row][col] = False
# In the end return score

from collections import deque
class SnakeGame:
    def __init__(self, width, height, food):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.snake = deque([(0,0)])
        self.width = width
        self.height = height
        self.food = deque(food)
        self.score = 0
        self.grid = [[False for _ in range(self.width)] for _ in range(self.height)]
        self.grid[0][0] = True
        self.game_over = False

    
    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        if self.game_over:
            return -1
        row, col = self.snake[-1]
        if direction == "U":
            row -= 1
        elif direction == "L":
            col -= 1
        elif direction == "R":
            col += 1
        else:
            row += 1
        if row < 0 or row >= self.height or col < 0 or col >= self.width:
            self.game_over = True
            return -1
        if self.food and row == self.food[0][0] and col == self.food[0][1]:
            self.food.popleft()
            self.score += 1
        else:
            r, c = self.snake.popleft()
            self.grid[r][c] = False
        if self.grid[row][col] == True:
            self.game_over = True
            return -1
        self.grid[row][col] = True
        self.snake.append((row,col))
        return self.score


# Your SnakeGame object will be instantiated and called as such:
snake = SnakeGame(3, 2, [[1, 2], [0, 1]])
print(snake.move("R"))
print(snake.move("D"))
print(snake.move("R"))
print(snake.move("U"))
print(snake.move("L"))
print(snake.move("U"))
